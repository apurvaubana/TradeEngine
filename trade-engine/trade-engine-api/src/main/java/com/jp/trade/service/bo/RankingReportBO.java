package com.jp.trade.service.bo;

import java.math.BigDecimal;

import com.jp.trade.service.bo.enums.SettlementIndicator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A business class representation for the Ranking of entities.
 *
 * @author apoorva.ubana
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingReportBO {

	private String entityName;
	private BigDecimal amount;
	private SettlementIndicator indicator;
	private int rank;

}
