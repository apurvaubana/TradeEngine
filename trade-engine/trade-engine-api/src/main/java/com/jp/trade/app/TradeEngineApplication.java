package com.jp.trade.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.jp.trade.app.config.ActiveMQConfig;
import com.jp.trade.app.config.ApiConfig;
import com.jp.trade.app.config.GemfireConfig;
import com.jp.trade.app.config.RemoteConfig;
import com.jp.trade.app.config.ServiceConfig;
import com.jp.trade.app.config.SwaggerConfig;

/**
 * Main Configuration Class.
 *
 * @author apoorva.ubana
 */
@Import(value = { ApiConfig.class, GemfireConfig.class, ServiceConfig.class, SwaggerConfig.class, RemoteConfig.class,
		ActiveMQConfig.class })
@SpringBootApplication
public class TradeEngineApplication {

	public static void main(String[] args) {

		SpringApplication.run(TradeEngineApplication.class, args);
	}
}
