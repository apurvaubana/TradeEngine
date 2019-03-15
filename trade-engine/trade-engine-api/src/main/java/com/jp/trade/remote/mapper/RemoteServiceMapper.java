package com.jp.trade.remote.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

import com.jp.trade.exchange.jms.dto.BuySellIndicator;
import com.jp.trade.exchange.jms.dto.TradeDTO;
import com.jp.trade.service.bo.TradeBO;
import com.jp.trade.service.bo.enums.SettlementIndicator;

/**
 * Trade Mapper for the remote services. Map BO to DTO and vice-versa.
 *
 * @author apoorva.ubana
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RemoteServiceMapper {

	List<TradeBO> tradeDtoToTradeBoList(List<TradeDTO> tradeDTOs);

	TradeBO tradeDtoToTradeBo(TradeDTO tradeDTOs);

	@ValueMappings({ @ValueMapping(source = "BUY", target = "OUTGOING"),
			@ValueMapping(source = "SELL", target = "INCOMING") })
	SettlementIndicator BuySellIndicatorToSettlementIndicator(BuySellIndicator buySellIndicator);

}
