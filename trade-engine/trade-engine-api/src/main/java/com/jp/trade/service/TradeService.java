package com.jp.trade.service;

import java.util.List;

import com.jp.trade.service.bo.TradeBO;

/**
 * It has methods to save the external trade instructions.
 *
 * @author apoorva.ubana
 */
public interface TradeService {

	/**
	 * Saves the list of trade instruction in Gemfire Region
	 *
	 * @param trade
	 *            list
	 * 
	 */
	public void saveTradeCollection(List<TradeBO> tradeList);

	/**
	 * Saves the trade instruction in Gemfire Region
	 *
	 * @param trade
	 * 
	 */
	public void saveTrade(TradeBO trade);
}
