/**
 * 
 */
package com.jp.trade.exchange.jms.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

/**
 * This class act as a data transfer object for the remote operation of loading
 * the instructions sent by various clients to the JP Morgan
 *
 * @author apoorva.ubana
 *
 */
@Getter
@Setter
public class TradeDTO implements Serializable {

	private static final long serialVersionUID = -3482577698539940122L;

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
