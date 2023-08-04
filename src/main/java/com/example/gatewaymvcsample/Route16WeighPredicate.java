package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.weight;

@Configuration(proxyBeanMethods = false)
public class Route16WeighPredicate {

	// http :8080/anything/weight
	@Bean
	public RouterFunction<ServerResponse> weightRoute1() {
		// @formatter:off
		return route("weight_route_1")
				.route(weight("group1", 5).and(path("/anything/weight")), http())
				.before(new HttpbinUriResolver())
				.before(addRequestHeader("X-Route", "route1"))
				.build();
		// @formatter:on
	}

	// TODO: fix this issue where weighted routes need to be separate RouterFunction beans
	@Bean
	public RouterFunction<ServerResponse> weightRoute2() {
		// @formatter:off
		return route("weight_route_2")
				.route(weight("group1", 5).and(path("/anything/weight")), http())
				.before(new HttpbinUriResolver())
				.before(addRequestHeader("X-Route", "route2"))
				.build();
		// @formatter:on
	}
}
