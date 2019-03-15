/**
 * 
 */
package com.jp.trade.exchange.jms.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * Buy/Sell Indicator for the trade.
 * 
 * @author apoorva.ubana
 *
 */

@Getter
public enum BuySellIndicator {

	/** Indicates that a trade is outgoing. */
	BUY("B"),
	/** Indicates that a trade is incoming. */
	SELL("S");

	String value;
	private static final Map<String, BuySellIndicator> valuesMap = new HashMap<>();

	BuySellIndicator(String value) {
		this.value = value;
	}

	static {
		for (BuySellIndicator myEnum : values()) {
			valuesMap.put(myEnum.getValue(), myEnum);
		}
	}

	public static BuySellIndicator getByValue(String value) {
		return valuesMap.get(value);
	}
}
