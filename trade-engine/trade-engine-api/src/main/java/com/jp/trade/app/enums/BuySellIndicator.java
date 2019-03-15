/**
 * 
 */
package com.jp.trade.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Buy/Sell Indicator for the trade.
 * 
 * @author apoorva.ubana
 *
 */
@AllArgsConstructor
@Getter
public enum BuySellIndicator {

	/** Indicates that a trade is outgoing. */
	BUY("B"),
	/** Indicates that a trade is incoming. */
	SELL("S");

	String value;

}
