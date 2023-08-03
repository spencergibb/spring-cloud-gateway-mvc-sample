package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.host;

@Configuration(proxyBeanMethods = false)
public class Route8SetPathFilter {

	// http :8080/get Host:www.setpath.org
	@Bean
	public RouterFunction<ServerResponse> setPathRoute() {
		return route("host_route")
				.route(host("{sub}.setpath.org"), http())
				.before(new HttpbinUriResolver())
				.before(setPath("/anything/{sub}"))
				.build();
	}

}
