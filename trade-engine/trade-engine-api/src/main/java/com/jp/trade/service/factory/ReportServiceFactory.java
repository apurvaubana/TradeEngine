package com.jp.trade.service.factory;

import org.springframework.beans.factory.annotation.Autowired;

import com.jp.trade.service.ReportService;
import com.jp.trade.service.TradeService;

/**
 * Factory of the services in the trade report application.
 *
 * <p>
 * All external systems or high level layers in the application will access to
 * the services through this factory. It is implemented as a singleton,
 * following the singleton pattern proposed by Bill Pugh.
 *
 * <p>
 * In this implementation it uses autowiring of the services and return the
 * autowired instance but can be changed any time to return any implementation
 * of services with out effecting the other layers.
 *
 * @author apoorva.ubana
 */
public class ReportServiceFactory {

	@Autowired
	private ReportService reportService;

	@Autowired
	private TradeService tradeService;

	/** Private constructor for the factory which prevents new instance */
	private ReportServiceFactory() {
	}

	/**
	 * Holder class for the singleton factory instance. It is loaded on the first
	 * execution of SimpleStockFactory#getInstance()}
	 */
	private static class SingletonHelper {
		private static final ReportServiceFactory INSTANCE = new ReportServiceFactory();
	}

	/**
	 * Gets the singleton instance of the factory of the services.
	 *
	 * @return An object of the ReportServiceFactory, which represents the factory
	 *         to access to all services in the application.
	 */
	public static ReportServiceFactory getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * Gets the instance ReportService through ReportServiceFactory instance
	 *
	 * @return An object of the ReportService
	 */
	public ReportService getReportService() {
		return reportService;
	}

	/**
	 * Gets the instance of tradeService through ReportServiceFactory instance
	 *
	 * @return An object of the tradeService
	 */
	public TradeService getTradeService() {
		return tradeService;
	}
}
