package com.jp.trade.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A model representation for the daily settlement report.
 *
 * @author apoorva.ubana
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SettlementReportModel {

	@ApiModelProperty(notes = "A settlement date")
	private LocalDate date;
	@ApiModelProperty(notes = "Amount in USD settled on particular date")
	private BigDecimal amount;
	@ApiModelProperty(notes = "A outgoing/incoming indicator of a trade")
	private SettlementEnum indicator;
}
