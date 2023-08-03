package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
public class Route1FirstRoute {

	// http :8080/anything/first
	@Bean
	public RouterFunction<ServerResponse> firstRoute() {
		return route("first_route")
				.GET("/anything/first", http())
				.before(new HttpbinUriResolver())
				.build();
	}

}
