package com.jp.trade.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jp.trade.app.enums.BuySellIndicator;
import com.jp.trade.data.entity.TradeEntity;
import com.jp.trade.data.entity.TradeEntity.TradeEntityBuilder;
import com.jp.trade.data.repository.TradeRepository;
import com.jp.trade.service.bo.RankingReportBO;
import com.jp.trade.service.bo.SettlementReportBO;
import com.jp.trade.service.bo.enums.SettlementIndicator;
import com.jp.trade.service.mapper.TradeServiceMapperImpl;

/**
 * Test class for the Report Service.
 *
 * @author apoorva.ubana
 */
@DisplayName("Report Service Test")
@ContextConfiguration(classes = { ReportServiceImpl.class, TradeServiceMapperImpl.class,
		ISOSettlementDateCalculator.class, ArabicSettlementDateCalculator.class })
@ExtendWith(SpringExtension.class)
public class ReportServiceTest {

	@MockBean
	private TradeRepository tradeRepository;

	@Autowired
	private ReportService reportService;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

	@Test
	@DisplayName("Amount in USD settled outgoing everyday test")
	public void getSettlementAmountOutgoingTest() {

		List<TradeEntity> tradeEntities = new ArrayList<>();
		TradeEntity tradeEntity = getTradeEntityBuilder().build();
		TradeEntity tradeEntity1 = getTradeEntityBuilder().entityName("bar").currency("AED").build();
		tradeEntities.add(tradeEntity);
		tradeEntities.add(tradeEntity1);

		when(this.tradeRepository.findByBuySellIndicator(BuySellIndicator.BUY)).thenReturn(tradeEntities);
		List<SettlementReportBO> settlementReportBOs = reportService.getSettlementAmount(SettlementIndicator.OUTGOING);
		assertAll("cheking the settlement report", () -> {
			assertEquals(2, settlementReportBOs.size());
			assertEquals("2019-03-11", settlementReportBOs.get(0).getDate().toString());
			assertTrue(BigDecimal.valueOf(10025.000).compareTo(settlementReportBOs.get(0).getAmount()) == 0);
			assertEquals("OUTGOING", settlementReportBOs.get(0).getIndicator().toString());
		});
	}

	@Test
	@DisplayName("Amount in USD settled incoming everyday test")
	public void getSettlementAmountIncomingvTest() {

		List<TradeEntity> tradeEntities = new ArrayList<>();
		TradeEntity tradeEntity = getTradeEntityBuilder().buySellIndicator(BuySellIndicator.SELL).build();
		TradeEntity tradeEntity1 = getTradeEntityBuilder().entityName("bar").buySellIndicator(BuySellIndicator.SELL)
				.currency("AED").build();
		TradeEntity tradeEntity2 = getTradeEntityBuilder().buySellIndicator(BuySellIndicator.SELL).build();
		tradeEntities.add(tradeEntity);
		tradeEntities.add(tradeEntity1);
		tradeEntities.add(tradeEntity2);

		when(this.tradeRepository.findByBuySellIndicator(BuySellIndicator.SELL)).thenReturn(tradeEntities);

		// calling service
		List<SettlementReportBO> settlementReportBOs = reportService.getSettlementAmount(SettlementIndicator.INCOMING);
		assertAll("cheking the settlement report", () -> {
			assertEquals(2, settlementReportBOs.size());

			assertAll("checking first element of list",
					() -> assertEquals("2019-03-11", settlementReportBOs.get(0).getDate().toString()),
					() -> assertTrue(
							BigDecimal.valueOf(20050.000).compareTo(settlementReportBOs.get(0).getAmount()) == 0),
					() -> assertEquals("INCOMING", settlementReportBOs.get(0).getIndicator().toString()));

			assertAll("checking second element of list",
					() -> assertEquals("2019-03-10", settlementReportBOs.get(1).getDate().toString()),
					() -> assertTrue(
							BigDecimal.valueOf(10025.000).compareTo(settlementReportBOs.get(1).getAmount()) == 0),
					() -> assertEquals("INCOMING", settlementReportBOs.get(1).getIndicator().toString()));
		});
	}

	@Test
	@DisplayName("Ranking of entities based on incoming and outgoing amount test")
	public void getRankingTest() {

		List<TradeEntity> tradeEntities = new ArrayList<>();
		TradeEntity tradeEntity = getTradeEntityBuilder().buySellIndicator(BuySellIndicator.SELL).build();// incoming
																											// foo
		TradeEntity tradeEntity1 = getTradeEntityBuilder().buySellIndicator(BuySellIndicator.SELL).build();// incoming
																											// foo
		TradeEntity tradeEntity2 = getTradeEntityBuilder().build();// outgoing foo

		TradeEntity tradeEntity3 = getTradeEntityBuilder().entityName("bar").currency("AED")
				.price(new BigDecimal(500.00)).build();// outgoing bar

		tradeEntities.add(tradeEntity);
		tradeEntities.add(tradeEntity1);
		tradeEntities.add(tradeEntity2);
		tradeEntities.add(tradeEntity3);
		when(this.tradeRepository.findAll()).thenReturn(tradeEntities);
		Map<String, List<RankingReportBO>> report = reportService.getRanking();

		assertAll("cheking the ranking report", () -> {

			assertAll("checking first rank in incoming",
					() -> assertEquals("foo", report.get("INCOMING").get(0).getEntityName()),
					() -> assertTrue(
							BigDecimal.valueOf(20050.000).compareTo(report.get("INCOMING").get(0).getAmount()) == 0),
					() -> assertEquals(1, report.get("INCOMING").get(0).getRank()),
					() -> assertEquals("INCOMING", report.get("INCOMING").get(0).getIndicator().toString()));

			assertAll("checking first rank in outgoing",
					() -> assertEquals("bar", report.get("OUTGOING").get(0).getEntityName()),
					() -> assertTrue(
							BigDecimal.valueOf(50000.000).compareTo(report.get("OUTGOING").get(0).getAmount()) == 0),
					() -> assertEquals(1, report.get("OUTGOING").get(0).getRank()),
					() -> assertEquals("OUTGOING", report.get("OUTGOING").get(0).getIndicator().toString()));

			assertAll("checking second rank in outgoing",
					() -> assertEquals("foo", report.get("OUTGOING").get(1).getEntityName()),
					() -> assertTrue(
							BigDecimal.valueOf(10025.000).compareTo(report.get("OUTGOING").get(1).getAmount()) == 0),
					() -> assertEquals(2, report.get("OUTGOING").get(1).getRank()),
					() -> assertEquals("OUTGOING", report.get("OUTGOING").get(1).getIndicator().toString()));
		});
	}

	private TradeEntityBuilder getTradeEntityBuilder() {
		String date = "09 Mar 2019";
		return TradeEntity.builder().entityName("foo").buySellIndicator(BuySellIndicator.BUY).currency("SGP")
				.instructionDate(LocalDate.now()).settlementDate(LocalDate.parse(date, formatter)).units(200)
				.agreedFxRate(new BigDecimal(.50)).price(new BigDecimal(100.25));
	}
}