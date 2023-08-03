package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.header;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.host;

@Configuration(proxyBeanMethods = false)
public class Route4HostPredicate {

	// http :8080/get Host:www.myhost.org
	@Bean
	public RouterFunction<ServerResponse> hostRoute() {
		return route("host_route")
				.route(host("**.myhost.org"), http())
				.before(new HttpbinUriResolver())
				.build();
	}

}
