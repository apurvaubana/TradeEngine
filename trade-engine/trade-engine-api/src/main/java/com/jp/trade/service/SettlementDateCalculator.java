/**
 * 
 */
package com.jp.trade.service;

import java.time.DayOfWeek;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jp.trade.service.bo.TradeBO;

/**
 * Defines method to calculate the settlement date for the trade.
 * 
 * @author apoorva.ubana
 *
 */
public interface SettlementDateCalculator {

	Map<DayOfWeek, Integer> DAYS_OF_WEEK = Stream
			.of(new AbstractMap.SimpleImmutableEntry<>(DayOfWeek.MONDAY, 1),
					new AbstractMap.SimpleImmutableEntry<>(DayOfWeek.TUESDAY, 2),
					new AbstractMap.SimpleImmutableEntry<>(DayOfWeek.WEDNESDAY, 3),
					new AbstractMap.SimpleImmutableEntry<>(DayOfWeek.THURSDAY, 4),
					new AbstractMap.SimpleImmutableEntry<>(DayOfWeek.FRIDAY, 5),
					new AbstractMap.SimpleImmutableEntry<>(DayOfWeek.SATURDAY, 6),
					new AbstractMap.SimpleImmutableEntry<>(DayOfWeek.SUNDAY, 7))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	String ILLEGAL_SETTLEMENT_DATE_VALUE = "Illegal value of Settlement Date";

	/**
	 * Calculates the settlement date for a given trade.
	 *
	 * @param trade
	 * @return A LocalDate value which represents a actual settlement date for a
	 *         given stock.
	 */
	TradeBO getSettlementDate(TradeBO trade);
}
