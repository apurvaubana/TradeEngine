package com.jp.trade.exchange.jms;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.jp.trade.exchange.jms.dto.TradeDTO;

/**
 * This class is responsible for sending the data to the JMS trade queue.
 *
 * @author apoorva.ubana
 */
@Component
public class QueueSender implements DataSender {

	private static final String TRADE_QUEUE = "trade-queue";

	@Autowired
	private JmsTemplate jmsTemplate;

	private static final Logger logger = LogManager.getLogger();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jp.trade.exchange.jms.DataSender#send(java.util.List)
	 */
	@Override
	public void send(List<TradeDTO> tradeList) {
		logger.info("sending data to the trade queue " + tradeList);
		jmsTemplate.convertAndSend(TRADE_QUEUE, tradeList);
		logger.info("sending data complete");
	}
}
