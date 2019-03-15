package com.jp.trade.remote.jms;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.jp.trade.exchange.jms.dto.BuySellIndicator;
import com.jp.trade.exchange.jms.dto.TradeDTO;
import com.jp.trade.remote.mapper.RemoteServiceMapper;
import com.jp.trade.service.factory.ReportServiceFactory;

/**
 * This class is responsible for receiving the data from JMS trade queue and
 * make a service call for saving data in gemfire.
 *
 * @author apoorva.ubana
 */
@Component
public class QueueReceiver {

	private static final Logger logger = LogManager.getLogger();

	private static final String TRADE_QUEUE = "trade-queue";

	@Autowired
	private ReportServiceFactory serviceFactory;

	@Autowired
	private RemoteServiceMapper remoteServiceMapper;

	@JmsListener(destination = TRADE_QUEUE, containerFactory = "jmsListenerContainerFactory")
	public void receive(@Payload List<Map<String, Object>> tradeList) {

		logger.info("receiving data from trade queue {} ", tradeList.getClass());

		List<TradeDTO> tradeDTOs = new ArrayList<>();

		Consumer<Map<String, Object>> consumer = map -> {
			TradeDTO tradeDTO = new TradeDTO();
			tradeDTO.setEntityName((String) map.get("entityName"));
			tradeDTO.setBuySellIndicator(BuySellIndicator.valueOf((String) map.get("buySellIndicator")));
			tradeDTO.setAgreedFxRate(
					new BigDecimal((double) map.get("agreedFxRate")).setScale(2, BigDecimal.ROUND_HALF_UP));
			tradeDTO.setCurrency((String) map.get("currency"));

			Map<Object, Object> instructionDate = (LinkedHashMap<Object, Object>) map.get("instructionDate");
			Map<Object, Object> settlementDate = (LinkedHashMap<Object, Object>) map.get("settlementDate");

			tradeDTO.setInstructionDate(LocalDate.of((int) instructionDate.get("year"),
					Month.valueOf((String) instructionDate.get("month")), (int) instructionDate.get("dayOfMonth")));

			tradeDTO.setSettlementDate(LocalDate.of((int) settlementDate.get("year"),
					Month.valueOf((String) settlementDate.get("month")), (int) settlementDate.get("dayOfMonth")));
			tradeDTO.setUnits((int) map.get("units"));
			tradeDTO.setPrice(new BigDecimal((double) map.get("price")).setScale(2, BigDecimal.ROUND_HALF_UP));
			tradeDTOs.add(tradeDTO);
		};

		tradeList.forEach(consumer);
		serviceFactory.getTradeService().saveTradeCollection(remoteServiceMapper.tradeDtoToTradeBoList(tradeDTOs));
	}

}
