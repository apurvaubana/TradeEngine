package com.jp.trade.service.bo;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jp.trade.service.bo.enums.SettlementIndicator;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A business class representation for the daily settlement report.
 *
 * @author apoorva.ubana
 */
@Data
@AllArgsConstructor
public class SettlementReportBO {

	private LocalDate date;
	private BigDecimal amount;
	private SettlementIndicator indicator;
}
