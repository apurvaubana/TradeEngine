package com.jp.trade.service.bo;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jp.trade.service.bo.enums.SettlementIndicator;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A business class representation for the trade.
 *
 * @author apoorva.ubana
 */
@Getter
@Setter
@NoArgsConstructor
public class TradeBO {

	/* financial entity whose shares are to be bought or sold */
	private String entityName;

	/* Buy/Sell flag */
	private SettlementIndicator buySellIndicator;

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

	/*
	 * The actual date on which instruction will be settleted.It would be the
	 * respective weekday for that reign.
	 */
	private LocalDate calculatedSettlementDate;

	/* Number of shares to be bought or sold */
	private Integer units;

	/* Price per unit */
	private BigDecimal price;

	@Getter(value = AccessLevel.NONE)
	private BigDecimal amount;

	/**
	 * @return the calculated amount
	 */
	public BigDecimal getAmount() {
		return this.price.multiply(BigDecimal.valueOf(this.units)).multiply(this.agreedFxRate);
	}

	@Builder
	public TradeBO(String entityName, SettlementIndicator buySellIndicator, BigDecimal agreedFxRate, String currency,
			LocalDate instructionDate, LocalDate settlementDate, LocalDate calculatedSettlementDate, Integer units,
			BigDecimal price) {
		super();
		this.entityName = entityName;
		this.buySellIndicator = buySellIndicator;
		this.agreedFxRate = agreedFxRate;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.calculatedSettlementDate = calculatedSettlementDate;
		this.units = units;
		this.price = price;
	}

}
