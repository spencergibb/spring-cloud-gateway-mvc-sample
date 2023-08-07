package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions.addResponseHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
public class Route09AddResponseHeaderFilter {

	// http :8080/anything/addresponseheader
	@Bean
	public RouterFunction<ServerResponse> addResponseHeaderRoute() {
		return route("addresponseheader_route")
				.GET("/anything/addresponseheader", http())
				.before(new HttpbinUriResolver())
				.after(addResponseHeader("X-After", "AfterVal"))
				.build();
	}

}
