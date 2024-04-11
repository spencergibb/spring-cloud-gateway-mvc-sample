package com.example.gatewaymvcsample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.modifyRequestBody;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
public class Route17ModifyRequestBody {

	// http POST :8080/anything/modifyrequestbody hello=world
	@Bean
	public RouterFunction<ServerResponse> modifyRequestBodyGatewayRoute() {
		// @formatter:off
		return route("modify_request_body")
				.POST("/anything/modifyrequestbody", http())
				.before(new HttpbinUriResolver())
				.before(modifyRequestBody(String.class, String.class, null, (request, s) -> s.toUpperCase()))
				.build();
		// @formatter:on
	}

}
