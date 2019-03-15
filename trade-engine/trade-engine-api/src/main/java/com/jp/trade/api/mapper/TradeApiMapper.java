package com.jp.trade.api.mapper;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

import com.jp.trade.api.model.RankingReportModel;
import com.jp.trade.api.model.SettlementEnum;
import com.jp.trade.api.model.SettlementReportModel;
import com.jp.trade.service.bo.RankingReportBO;
import com.jp.trade.service.bo.SettlementReportBO;
import com.jp.trade.service.bo.enums.SettlementIndicator;

/**
 * It is responsible for handling mapping between Model and BO object.
 *
 * @author apoorva.ubana
 */
@Mapper
public interface TradeApiMapper {

	List<SettlementReportModel> settlementReportBOToModelList(List<SettlementReportBO> reportBO);

	List<RankingReportModel> rankingReportBOToModelList(List<RankingReportBO> rankingReportBOs);

	Map<String, List<RankingReportModel>> reportMapToModelMap(Map<String, List<RankingReportBO>> map);

	@ValueMappings({ @ValueMapping(source = "INCOMING", target = "INCOMING"),
			@ValueMapping(source = "OUTGOING", target = "OUTGOING") })
	SettlementEnum settlementIndicatorMapping(SettlementIndicator indicator);
}
