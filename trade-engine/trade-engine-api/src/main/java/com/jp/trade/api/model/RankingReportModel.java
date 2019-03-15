package com.jp.trade.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A model representation for the Ranking of entities.
 *
 * @author apoorva.ubana
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankingReportModel {

	@ApiModelProperty(notes = "A financial entity whose shares are to be bought or sold")
	private String entityName;
	@ApiModelProperty(notes = "A USD amount of a trade")
	private BigDecimal amount;
	@ApiModelProperty(notes = "A outgoing/incoming indicator of a trade")
	private SettlementEnum indicator;
	@ApiModelProperty(notes = "A ranking of entities based on incoming and outgoing amount")
	private int rank;

}
