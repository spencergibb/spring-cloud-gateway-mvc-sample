package com.example.gatewaymvcsample;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.fn;

@Configuration(proxyBeanMethods = false)
public class Route19FunctionHandler {

	// http POST :8080/fn/upper name=myname
	// http GET :8080/hellosupplier
	@Bean
	Function<String, String> upper() {
		return s -> s.toUpperCase(Locale.ROOT);
	}

	@Bean
	Supplier<String> hello() {
		return () -> "hello";
	}

	@Bean
	public RouterFunction<ServerResponse> gatewayRouterFunctionsSimpleFunction() {
		// @formatter:off
		return route("templatedfunction")
					.POST("/fn/{fnName}", fn("{fnName}"))
					.build()
				.and(route("hellosupplierfunction")
					.GET("/hellosupplier", fn("hello"))
					.build());
		// @formatter:on
	}

}
