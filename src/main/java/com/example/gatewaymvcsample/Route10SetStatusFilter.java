package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions.setStatus;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
public class Route10SetStatusFilter {

	// http :8080/status/502
	@Bean
	public RouterFunction<ServerResponse> setStatusRoute() {
		return route("setstatus_route")
				.GET("/status/**", http())
				.before(new HttpbinUriResolver())
				.after(setStatus(401))
				.build();
	}

}
