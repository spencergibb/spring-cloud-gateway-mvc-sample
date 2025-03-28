package com.example.gatewaymvcsample;

import java.util.function.Consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.stream;

@Configuration(proxyBeanMethods = false)
public class Route20StreamHandler {

	Log log = LogFactory.getLog(getClass());

	// echo -n World | http POST :8080/stream/hello
	@Bean
	public RouterFunction<ServerResponse> gatewayRouterFunctionsStream() {
		// @formatter:off
		return route("testtemplatedstream")
				.POST("/stream/{name}", stream("{name}-out-0"))
				.build();
		// @formatter:on
	}

	@Bean
	public Consumer<String> consumeHello() {
		return message -> log.error("Hello " + message);
	}

}
