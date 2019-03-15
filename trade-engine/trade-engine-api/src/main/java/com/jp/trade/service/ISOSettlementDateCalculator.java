package com.jp.trade.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jp.trade.service.bo.TradeBO;
import com.jp.trade.service.exception.TradeEngineServiceException;

/**
 * Implement settlement date calculator where week starts from Monday and ends
 * on Friday.
 *
 * @author apoorva.ubana
 */
@Component(value = "ISO")
public class ISOSettlementDateCalculator implements SettlementDateCalculator {

	@Override
	public TradeBO getSettlementDate(TradeBO trade) {

		LocalDate settlementDate = Optional.ofNullable(trade.getSettlementDate())
				.orElseThrow(() -> new TradeEngineServiceException(ILLEGAL_SETTLEMENT_DATE_VALUE));

		int dayOfWeek = SettlementDateCalculator.DAYS_OF_WEEK.get(settlementDate.getDayOfWeek());

		if (dayOfWeek == 6) {
			trade.setCalculatedSettlementDate(settlementDate.plusDays(2));
			return trade;
		}
		if (dayOfWeek == 7) {

			trade.setCalculatedSettlementDate(settlementDate.plusDays(1));
			return trade;
		}
		trade.setCalculatedSettlementDate(settlementDate);
		return trade;
	}
}
