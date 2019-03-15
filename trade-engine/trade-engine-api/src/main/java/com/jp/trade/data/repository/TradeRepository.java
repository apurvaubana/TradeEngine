package com.jp.trade.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jp.trade.app.enums.BuySellIndicator;
import com.jp.trade.data.entity.TradeEntity;

/**
 * It performs the CRUD operation on Trade region by extending Spring data
 * CrudRepository
 *
 * @author apoorva.ubana
 */
@Repository
public interface TradeRepository extends CrudRepository<TradeEntity, String> {

	List<TradeEntity> findByBuySellIndicator(BuySellIndicator indicator);
}
