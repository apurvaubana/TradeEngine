
package com.jp.trade.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.jp.trade.exchange.config.AsyncConfig;
import com.jp.trade.exchange.config.ExchangeServiceConfig;

/**
 * Configuration for the remote exchange service
 *
 * @author apoorva.ubana
 */
@Configuration
@Import({ ExchangeServiceConfig.class, AsyncConfig.class })
@ComponentScan(value = { "com.jp.trade.remote.jms", "com.jp.trade.remote.mapper" })
public class RemoteConfig {
}
