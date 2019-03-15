package com.jp.trade.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jp.trade.data.entity.TradeEntity;
import com.jp.trade.data.repository.TradeRepository;
import com.jp.trade.service.bo.RankingReportBO;
import com.jp.trade.service.bo.SettlementReportBO;
import com.jp.trade.service.bo.TradeBO;
import com.jp.trade.service.bo.enums.CurrencyEnum;
import com.jp.trade.service.bo.enums.SettlementIndicator;
import com.jp.trade.service.mapper.TradeServiceMapper;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private TradeServiceMapper tradeServiceMapper;

	@Autowired
	@Qualifier("ISO")
	private SettlementDateCalculator isoDateCalculator;

	@Autowired
	@Qualifier("ARABIC")
	private SettlementDateCalculator arabicDateCalculator;

	private static final Logger logger = LogManager.getLogger();

	@Override
	public List<SettlementReportBO> getSettlementAmount(SettlementIndicator indicator) {

		logger.info("Get {} settlement report", indicator.toString());
		// Getting the incoming/outgoing trade from repository.
		List<TradeEntity> tradeEntities = tradeRepository
				.findByBuySellIndicator(tradeServiceMapper.SettlementIndicatorToBuySellIndicator(indicator));

		logger.debug("settlement fetched from gemfire", tradeEntities);

		// Mapping Entity to business object.
		List<TradeBO> tradeBOs = tradeServiceMapper.tradeEntityToTradeBOList(tradeEntities);
		// Group By date by summing the amount
		Map<LocalDate, BigDecimal> reportMap = tradeBOs.stream().map(this::getSettlementDate)
				.collect(Collectors.groupingBy(TradeBO::getCalculatedSettlementDate,
						Collectors.mapping(TradeBO::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
		// Converting map to list of SettlementReportBO
		return reportMap.entrySet().stream()
				.map(report -> new SettlementReportBO(report.getKey(), report.getValue(), indicator))
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, List<RankingReportBO>> getRanking() {

		logger.info("Get ranking report");
		// Getting all trades from the repository.
		List<TradeEntity> tradeEntities = (List<TradeEntity>) tradeRepository.findAll();

		logger.debug("settlement fetched from gemfire", tradeEntities);
		// mapping trade entity to BO
		List<TradeBO> tradeBOs = tradeServiceMapper.tradeEntityToTradeBOList(tradeEntities);

		// used for ranking
		AtomicInteger rankIncoming = new AtomicInteger(0);
		AtomicInteger rankOutgoing = new AtomicInteger(0);

		// Group By Entity Name for incoming list by summing the amount
		Map<String, BigDecimal> incomingEntityMap = tradeBOs.stream()
				.filter(trade -> trade.getBuySellIndicator() == SettlementIndicator.INCOMING)
				.collect(Collectors.groupingBy(TradeBO::getEntityName,
						Collectors.mapping(TradeBO::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

		// Converting map to list of RankingReportBO and sort according to Amount.
		List<RankingReportBO> incomingList = incomingEntityMap.entrySet().stream()
				.map(report -> new RankingReportBO(report.getKey(), report.getValue(), SettlementIndicator.INCOMING, 0))
				.sorted(Comparator.comparing(RankingReportBO::getAmount).reversed()).map(report -> {
					// setting the rank after sorting
					report.setRank(rankIncoming.incrementAndGet());
					return report;
				}).collect(Collectors.toCollection(ArrayList::new));

		logger.debug("Incoming rank is {} ", incomingList);

		// Group By Entity Name for outgoing list by summing the amount
		Map<String, BigDecimal> outgoingEntityMap = tradeBOs.stream()
				.filter(trade -> trade.getBuySellIndicator() == SettlementIndicator.OUTGOING)
				.collect(Collectors.groupingBy(TradeBO::getEntityName,
						Collectors.mapping(TradeBO::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

		// Converting map to list of RankingReportBO and sort according to Amount.
		List<RankingReportBO> outgoingList = outgoingEntityMap.entrySet().stream()
				.map(report -> new RankingReportBO(report.getKey(), report.getValue(), SettlementIndicator.OUTGOING, 0))
				.sorted(Comparator.comparing(RankingReportBO::getAmount).reversed()).map(report -> {
					// setting the rank after sorting
					report.setRank(rankOutgoing.incrementAndGet());
					return report;
				}).collect(Collectors.toCollection(ArrayList::new));

		logger.debug("Outgoing rank is {} ", outgoingList);

		// Adding incoming list and outgoing list to map
		Map<String, List<RankingReportBO>> report = new HashMap<>();
		report.put("INCOMING", incomingList);
		report.put("OUTGOING", outgoingList);

		return report;

	}

	/**
	 * Calculates the a settlement date by passing different strategy objects
	 */

	private TradeBO getSettlementDate(TradeBO trade) {
		if (trade.getCurrency().equalsIgnoreCase(CurrencyEnum.AED.toString())
				|| trade.getCurrency().equalsIgnoreCase(CurrencyEnum.SAR.toString())) {
			return ReportService.calculateSettlement(trade, this.arabicDateCalculator);
		} else {
			return ReportService.calculateSettlement(trade, this.isoDateCalculator);
		}
	}
}
