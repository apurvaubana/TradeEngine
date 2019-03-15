
package com.jp.trade.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jp.trade.service.factory.ReportServiceFactory;

/**
 * configuration for the services
 *
 * @author apoorva.ubana
 */
@Configuration
@ComponentScan(value = { "com.jp.trade.service", "com.jp.trade.service.mapper" })
public class ServiceConfig {

	@Bean(name = "ReportServiceFactory")
	public ReportServiceFactory reportFactory() {
		return ReportServiceFactory.getInstance();
	}
}
