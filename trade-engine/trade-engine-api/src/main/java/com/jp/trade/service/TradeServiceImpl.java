package com.jp.trade.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.trade.data.entity.TradeEntity;
import com.jp.trade.data.repository.TradeRepository;
import com.jp.trade.service.bo.TradeBO;
import com.jp.trade.service.mapper.TradeServiceMapper;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private TradeServiceMapper tradeServiceMapper;

	private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

	@Override
	public void saveTradeCollection(List<TradeBO> tradeList) {
		logger.debug("saving in gemfire {} ", tradeList);

		List<TradeEntity> tradeEntities = tradeServiceMapper.tradeBoToTradeEntityList(tradeList);
		// assigning unique sequence ID.
		tradeEntities.forEach(entity -> entity.setTradeIdentifier(UUID.randomUUID().toString()));
		tradeRepository.saveAll(tradeEntities);

	}

	@Override
	public void saveTrade(TradeBO trade) {
		logger.debug("saving in gemfire {} ", trade);

		tradeRepository.save(tradeServiceMapper.tradeBoToTradeEntity(trade));

	}

}
