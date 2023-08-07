package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.method;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration(proxyBeanMethods = false)
public class Route02SecondRoute {

	// http :8080/anything/second
	@Bean
	public RouterFunction<ServerResponse> secondRoute() {
		return route("second_route")
				.route(method(HttpMethod.GET).and(path("/anything/second")), http())
				.before(new HttpbinUriResolver())
				.build();
	}

}
