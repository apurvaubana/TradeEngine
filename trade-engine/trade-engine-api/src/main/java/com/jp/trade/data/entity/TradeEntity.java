package com.jp.trade.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import com.jp.trade.app.enums.BuySellIndicator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** A entity class for trade record containing the trade information.
*
* <p>It is used to hold data in Gemfire trade region
*
* @author apoorva.ubana
*/

/** Representing the trade region in Gemfire */
@Region("Trade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The trade identifier used as key in trade region */
	@Id
	private String tradeIdentifier;

	/* financial entity whose shares are to be bought or sold */
	private String entityName;

	/* Buy/Sell flag */
	private BuySellIndicator buySellIndicator;

	/* foreign exchange rate with respect to USD that was agreed */
	private BigDecimal agreedFxRate;

	/* trade currency */
	private String currency;

	/* Date on which the instruction was sent to JP Morgan by various clients */
	private LocalDate instructionDate;
	/*
	 * The date on which the client wished for the instruction to be settled with
	 * respect to Instruction Date
	 */
	private LocalDate settlementDate;

	/* Number of shares to be bought or sold */
	private Integer units;

	/* Price per unit */
	private BigDecimal price;
}
