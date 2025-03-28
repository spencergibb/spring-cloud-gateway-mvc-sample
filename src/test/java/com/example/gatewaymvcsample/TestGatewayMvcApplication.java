package com.example.gatewaymvcsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestGatewayMvcApplication {

	public static void main(String[] args) {
		SpringApplication.from(GatewayMvcApplication::main).with(HttpbinConfiguration.class, RabbitMQConfiguration.class).run(args);
	}
}
