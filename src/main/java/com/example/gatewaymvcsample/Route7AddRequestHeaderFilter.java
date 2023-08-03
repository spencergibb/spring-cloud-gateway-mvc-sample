package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
public class Route7AddRequestHeaderFilter {

	// http :8080/anything/addrequestheader
	@Bean
	public RouterFunction<ServerResponse> addRequestHeaderRoute() {
		return route("addrequestheader_route")
				.GET("/anything/addrequestheader", http())
				.before(new HttpbinUriResolver())
				.before(addRequestHeader("X-Foo", "Bar"))
				.build();
	}

}
