package com.example.gatewaymvcsample;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.utility.DockerImageName;

import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;

public class HttpbinConfiguration {
	// https://github.com/mccutchen/go-httpbin
	// https://hub.docker.com/r/mccutchen/go-httpbin
	private static final DockerImageName HTTPBIN_IMAGE_NAME = DockerImageName.parse("mccutchen/go-httpbin");
	private static final int DEFAULT_PORT = 8080;

	@Bean
	public GenericContainer<?> httpbinContainer(DynamicPropertyRegistry properties) {
		GenericContainer<?> container = new GenericContainer<>(HTTPBIN_IMAGE_NAME).withExposedPorts(DEFAULT_PORT)
				.waitingFor(new HttpWaitStrategy().forPort(DEFAULT_PORT));
		properties.add("httpbin.host", container::getHost);
		properties.add("httpbin.port", () -> container.getMappedPort(DEFAULT_PORT));
		return container;
	}


}
