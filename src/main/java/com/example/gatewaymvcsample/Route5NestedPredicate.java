package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.host;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration(proxyBeanMethods = false)
public class Route5NestedPredicate {

	// http :8080/anything/nested/nested1
	// http :8080/anything/nested/nested2
	@Bean
	public RouterFunction<ServerResponse> nestedRoute() {
		return route("nested0")
				.nest(path("/anything/nested"), () ->
						route("nested1")
								.GET("/nested1", http())
								.before(new HttpbinUriResolver())
								.build().and(
						route("nested2")
								.GET("/nested2", http())
								.before(new HttpbinUriResolver())
								.build()
				))
				.build();
	}

}
