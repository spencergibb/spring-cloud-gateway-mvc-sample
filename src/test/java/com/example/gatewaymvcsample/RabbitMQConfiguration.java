package com.example.gatewaymvcsample;

import org.testcontainers.containers.RabbitMQContainer;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;

@TestConfiguration(proxyBeanMethods = false)
public class RabbitMQConfiguration {

	@Bean
	@ServiceConnection
	public RabbitMQContainer rabbitMQContainer() {
		return new RabbitMQContainer("rabbitmq:3.7.25-management-alpine");
	}

}
