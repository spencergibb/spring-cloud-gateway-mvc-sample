package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions.modifyResponseBody;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
public class Route18ModifyResponseBody {

	// http GET :8080/anything/modifyresponsebody X-Foo:fooval
	@Bean
	public RouterFunction<ServerResponse> modifyResponseBodyGatewayRoute() {
		// @formatter:off
		return route("modify_response_body")
				.GET("/anything/modifyresponsebody", http())
				.before(new HttpbinUriResolver())
				.after(modifyResponseBody(String.class, String.class, null,
						(_, _, s) -> s.replace("fooval", "FOOVAL")))
				.build();
		// @formatter:on
	}

}
