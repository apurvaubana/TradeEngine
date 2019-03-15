package com.jp.trade.service;

import java.util.List;
import java.util.Map;

import com.jp.trade.service.bo.RankingReportBO;
import com.jp.trade.service.bo.SettlementReportBO;
import com.jp.trade.service.bo.TradeBO;
import com.jp.trade.service.bo.enums.SettlementIndicator;

/**
 * ReportService interface exposes the method to generate different kind of
 * reports.
 *
 * @author apoorva.ubana
 */
public interface ReportService {

	/**
	 * Calculates the amount in USD settled incoming/outgoing everyday.
	 *
	 * @return A list of {@link SettlementReportBO} which represents the incoming
	 *         amount settled everyday.
	 */
	List<SettlementReportBO> getSettlementAmount(SettlementIndicator indicator);

	/**
	 * Ranking of entities based on incoming and outgoing amount.
	 *
	 * @return Map - containing list of incoming and outgoing ranking
	 */
	Map<String, List<RankingReportBO>> getRanking();

	/**
	 * calculates the settlement date , depending on the Currency of trade.
	 *
	 * @param tradeBO
	 *            trade object
	 * @param settlementDateCalculator
	 *            strategy to choose for calculation
	 */
	static TradeBO calculateSettlement(TradeBO tradeBO, SettlementDateCalculator settlementDateCalculator) {
		return settlementDateCalculator.getSettlementDate(tradeBO);

	}
}
