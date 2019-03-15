package com.jp.trade.exchange.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * configuration for the exchange services
 *
 * @author apoorva.ubana
 */
@Configuration
@ComponentScan(value = { "com.jp.trade.exchange.jms", "com.jp.trade.exchange.io" })
public class ExchangeServiceConfig {
}
