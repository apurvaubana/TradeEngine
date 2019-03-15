package com.jp.trade.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

import com.jp.trade.app.enums.BuySellIndicator;
import com.jp.trade.data.entity.TradeEntity;
import com.jp.trade.service.bo.TradeBO;
import com.jp.trade.service.bo.enums.SettlementIndicator;

/**
 * Trade Mapper for the services. Map BO to Entity and vice-versa.
 *
 * @author apoorva.ubana
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TradeServiceMapper {

	TradeBO tradeEntityToTradeBO(TradeEntity entity);

	List<TradeBO> tradeEntityToTradeBOList(List<TradeEntity> entityList);

	List<TradeEntity> tradeBoToTradeEntityList(List<TradeBO> entityList);

	TradeEntity tradeBoToTradeEntity(TradeBO tradeBO);

	@ValueMappings({ @ValueMapping(source = "BUY", target = "OUTGOING"),
			@ValueMapping(source = "SELL", target = "INCOMING") })
	SettlementIndicator BuySellIndicatorToSettlementIndicator(BuySellIndicator buySellIndicator);

	@ValueMappings({ @ValueMapping(source = "OUTGOING", target = "BUY"),
			@ValueMapping(source = "INCOMING", target = "SELL") })
	BuySellIndicator SettlementIndicatorToBuySellIndicator(SettlementIndicator buySellIndicator);
}
