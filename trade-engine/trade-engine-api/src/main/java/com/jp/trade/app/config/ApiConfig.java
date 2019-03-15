package com.jp.trade.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for APIs.
 *
 * @author apoorva.ubana
 */
@Configuration
@ComponentScan(value = { "com.jp.trade.api", "com.jp.trade.api.advice", "com.jp.trade.api.mapper" })
public class ApiConfig {
}
