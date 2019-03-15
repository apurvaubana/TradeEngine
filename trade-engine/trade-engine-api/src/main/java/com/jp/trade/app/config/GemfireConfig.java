/** */
package com.jp.trade.app.config;

import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import com.jp.trade.data.entity.TradeEntity;

/**
 * Configuration for the Gemfire
 *
 * @author apoorva.ubana
 */
@Configuration
@EnableGemfireRepositories(basePackages = "com.jp.trade.data.repository")
@ClientCacheApplication(name = "TradeDataGemFire", logLevel = "ERROR")
@EnableEntityDefinedRegions(basePackageClasses = {
		TradeEntity.class }, clientRegionShortcut = ClientRegionShortcut.LOCAL)
public class GemfireConfig {
}
