package com.jp.trade.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jp.trade.api.mapper.TradeApiMapper;
import com.jp.trade.api.model.RankingReportModel;
import com.jp.trade.api.model.SettlementReportModel;
import com.jp.trade.service.bo.enums.SettlementIndicator;
import com.jp.trade.service.factory.ReportServiceFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller handling all api calls for trade report.
 *
 * @author apoorva.ubana
 */

@RestController
@RequestMapping("/report")
@Api(tags = { "Report Operations" })
public class TradeReportController {

	@Autowired
	private TradeApiMapper mapper;

	@Autowired
	private ReportServiceFactory serviceFactory;

	/**
	 * RESTful API endpoint to get amount in USD settled incoming.
	 *
	 * @return List - Amount in USD settled incoming.
	 */
	@ApiOperation(value = "Get the amount in USD settled incoming")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "BAD_REQUEST"),
			@ApiResponse(code = 401, message = "Request was not authorised"),
			@ApiResponse(code = 404, message = "NOT_FOUND"),
			@ApiResponse(code = 500, message = "Syetem is currently experiencing problems.") })
	@GetMapping(path = "/incomingReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SettlementReportModel> getIncomingSettlementReport() {

		return mapper.settlementReportBOToModelList(
				serviceFactory.getReportService().getSettlementAmount(SettlementIndicator.INCOMING));
	}

	/**
	 * RESTful API endpoint to get amount in USD settled outgoing.
	 *
	 * @return List - Amount in USD settled outgoing.
	 */
	@ApiOperation(value = "Get the amount in USD settled outgoing")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "BAD_REQUEST"),
			@ApiResponse(code = 401, message = "Request was not authorised"),
			@ApiResponse(code = 404, message = "NOT_FOUND"),
			@ApiResponse(code = 500, message = "Syetem is currently experiencing problems.") })
	@GetMapping(path = "/outgoingReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SettlementReportModel> getOutgoingSettlementReport() {

		return mapper.settlementReportBOToModelList(
				serviceFactory.getReportService().getSettlementAmount(SettlementIndicator.OUTGOING));
	}

	/**
	 * RESTful API endpoint to get ranking of entities based on incoming and
	 * outgoing amount.
	 *
	 * @return Map
	 */
	@ApiOperation(value = "Get the ranking of entities based on incoming and outgoing amount")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "BAD_REQUEST"),
			@ApiResponse(code = 401, message = "Request was not authorised"),
			@ApiResponse(code = 404, message = "NOT_FOUND"),
			@ApiResponse(code = 500, message = "Syetem is currently experiencing problems.") })
	@GetMapping(path = "/rankingReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, List<RankingReportModel>> getRanking() {
		return mapper.reportMapToModelMap(serviceFactory.getReportService().getRanking());
	}
}
