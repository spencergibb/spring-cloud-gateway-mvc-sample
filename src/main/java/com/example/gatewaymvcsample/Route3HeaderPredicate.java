package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.header;

@Configuration(proxyBeanMethods = false)
public class Route3HeaderPredicate {

	// http :8080/get X-Foo:bar
	@Bean
	public RouterFunction<ServerResponse> headerRoute() {
		return route("header_route")
				.route(header("X-Foo", "b.r"), http())
				.before(new HttpbinUriResolver())
				.build();
	}

}
