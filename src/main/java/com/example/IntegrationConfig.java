package com.example;

import java.time.Duration;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.jdbc.core.DataClassRowMapper;

@Configuration
public class IntegrationConfig {
	@Bean
	public MessageSource<Object> jdbcMessageSource(DataSource dataSource) {
		JdbcPollingChannelAdapter jdbcPollingChannelAdapter = new JdbcPollingChannelAdapter(dataSource,
				"SELECT id, first_name, last_name, minutes, data_usage, created_at FROM usage WHERE seen_at IS NULL ORDER BY id LIMIT 1000");
		jdbcPollingChannelAdapter.setUpdateSql("UPDATE usage SET seen_at = CURRENT_TIMESTAMP WHERE id IN (:id)");
		jdbcPollingChannelAdapter.setRowMapper(new DataClassRowMapper<>(Usage.class));
		return jdbcPollingChannelAdapter;
	}

	@Bean
	public IntegrationFlow messagePolling(MessageSource<Object> jdbcMessageSource) {
		return IntegrationFlows
				.from(jdbcMessageSource, c -> c.poller(Pollers.fixedRate(Duration.ofSeconds(10L)).transactional()))
				.channel("jdbcPollingChannel")
				.get();
	}

	@Bean
	public IntegrationFlow stdout(UsageHandler usageHandler) {
		return IntegrationFlows
				.from("jdbcPollingChannel")
				.handle(usageHandler)
				.get();
	}
}
