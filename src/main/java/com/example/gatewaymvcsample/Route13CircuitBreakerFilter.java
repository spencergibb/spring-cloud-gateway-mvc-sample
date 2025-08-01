package com.example.gatewaymvcsample;

import java.net.URI;

import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.fallbackHeaders;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration(proxyBeanMethods = false)
public class Route13CircuitBreakerFilter {

	// http :8080/anything/circuitbreakergatewayfallback
	// see returned "Execution-Exception-*" headers
	@Bean
	public RouterFunction<ServerResponse> gatewayRouterFunctionsCircuitBreakerFallbackToGatewayRoute() {
		// @formatter:off
		return route("circuitbreakergatewayfallback")
				.route(path("/anything/circuitbreakergatewayfallback"), http())
				.before(uri("https://nonexistantdomain.com1234"))
				.filter(circuitBreaker("mycb2", "/anything/gatewayfallback"))
				.build()
				.and(route("testgatewayfallback")
						.route(path("/anything/gatewayfallback"), http())
						.before(new HttpbinUriResolver())
						.before(fallbackHeaders())
						.build());
		// @formatter:on
	}

	// http :8080/anything/circuitbreakernofallback
	@Bean
	public RouterFunction<ServerResponse> gatewayRouterFunctionsCircuitBreakerNoFallback() {
		// @formatter:off
		return route("circuitbreakernofallback")
				.GET("/anything/circuitbreakernofallback", http())
				.before(new HttpbinUriResolver())
				.filter(circuitBreaker("mycb3"))
				.filter(setPath("/delay/5"))
				.withAttribute(MvcUtils.GATEWAY_ROUTE_ID_ATTR, "testcircuitbreakernofallback")
				.build();
		// @formatter:on
	}

}
