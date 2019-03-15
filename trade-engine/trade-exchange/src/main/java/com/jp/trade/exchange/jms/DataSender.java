package com.jp.trade.exchange.jms;

import java.util.List;

import com.jp.trade.exchange.jms.dto.TradeDTO;

public interface DataSender {

	void send(List<TradeDTO> tradeList);

}