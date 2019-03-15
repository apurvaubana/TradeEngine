package org.trade.engine.integration.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

/**
 * Integration Test for all the operations available in daily trade engine Rest
 * API
 *
 * @author apoorva.ubana
 */
@DisplayName("Interation Test for API")
public class TradeIntegrationTest extends TradeBaseIntegrationTest {

	@Test
	@DisplayName("Interation Test for Amount in USD settled incoming")
	public void getIncomingTest() throws Exception {

		this.mockMvc.perform(get("/report/incomingReport")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(content().json(
						"[{\"date\":\"2019-03-10\",\"amount\":14899.5000,\"indicator\":\"INCOMING\"},{\"date\":\"2019-03-18\",\"amount\":15037.5000,\"indicator\":\"INCOMING\"}]"));

	}

	@Test
	@DisplayName("Interation Test for Amount in USD settled outgoing")
	public void getOutgoingTest() throws Exception {

		this.mockMvc.perform(get("/report/outgoingReport")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(content().json(
						"[{\"date\":\"2019-03-14\",\"amount\":1655.5000,\"indicator\":\"OUTGOING\"},{\"date\":\"2016-01-04\",\"amount\":10025.0000,\"indicator\":\"OUTGOING\"},{\"date\":\"2019-03-17\",\"amount\":49549.5000,\"indicator\":\"OUTGOING\"}]"));

	}

	@Test
	@DisplayName("Interation Test for Ranking of entities based on incoming and outgoing amount")
	public void getRankingTest() throws Exception {

		this.mockMvc.perform(get("/report/rankingReport")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(content().json(
						"{\"INCOMING\":[{\"entityName\":\"foo\",\"amount\":15037.5000,\"indicator\":\"INCOMING\",\"rank\":1},{\"entityName\":\"bar\",\"amount\":14899.5000,\"indicator\":\"INCOMING\",\"rank\":2}],\"OUTGOING\":[{\"entityName\":\"sar\",\"amount\":49549.5000,\"indicator\":\"OUTGOING\",\"rank\":1},{\"entityName\":\"foo\",\"amount\":10025.0000,\"indicator\":\"OUTGOING\",\"rank\":2},{\"entityName\":\"bar\",\"amount\":1655.5000,\"indicator\":\"OUTGOING\",\"rank\":3}]}"));

	}
}
