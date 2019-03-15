package com.jp.trade.exchange.io;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jp.trade.exchange.exception.ExchangeException;
import com.jp.trade.exchange.jms.DataSender;
import com.jp.trade.exchange.jms.dto.TradeDTO;

/**
 * This class loads the trade data and call the JMS queue sender to send the
 * data to the trade queue. It runs on the context start up and continue to run
 * every day at 7 o clock periodically to load the EOD files into the system.
 *
 * @author apoorva.ubana
 */
@Component
@PropertySource("classpath:/com/jp/trade/exchange/tradeExchange.properties")
public class LoadExchangeData {

	private static final Logger logger = LoggerFactory.getLogger(LoadExchangeData.class);
	private static final String UNABLE_TO_READ_FILE = "unable to load the exchange data";

	@Autowired
	private ExcelFileReader fileReader;
	@Autowired
	private DataSender tradeQueueSender;

	private CompletableFuture<List<TradeDTO>> futureTradeList;

	// Exchange file location from the property file.
	@Value("${trade.dataFile}")
	private String dataFile;

	// Runs once on loading of context
	@PostConstruct
	public void onStartup() {

		logger.info("Loading Stock data into System");
		futureTradeList = fileReader.readStockDataFromExcelFile(dataFile);
		while (true) {
			if (futureTradeList.isDone()) {
				try {
					// JMS queue sender
					tradeQueueSender.send(futureTradeList.get());
					break;
				} catch (InterruptedException | ExecutionException e) {
					logger.error(e.getMessage());
					throw new ExchangeException(UNABLE_TO_READ_FILE, new Throwable(UNABLE_TO_READ_FILE));
				}
			}
		}
	}

	// Runs periodically at specific time of day decided by cron expression
	@Scheduled(cron = "${trade.cron}")
	public void onSchedule() {

		logger.info("Loading Stock data into System periodically");
		// JMS queue sender
		futureTradeList = fileReader.readStockDataFromExcelFile(dataFile);
		while (true) {
			if (futureTradeList.isDone()) {
				try {
					tradeQueueSender.send(futureTradeList.get());
					break;
				} catch (InterruptedException | ExecutionException e) {
					logger.error(e.getMessage());
					throw new ExchangeException(UNABLE_TO_READ_FILE, new Throwable(UNABLE_TO_READ_FILE));
				}
			}
		}
	}
}
