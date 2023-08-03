package com.example.gatewaymvcsample;

import java.net.URI;
import java.util.function.Function;

import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.BodyFilterFunctions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BodyFilterFunctions.adaptCachedBody;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.host;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.readBody;

@Configuration(proxyBeanMethods = false)
public class Route6ReadBodyPredicate {

	// http POST :8080/post hello=world
	@Bean
	public RouterFunction<ServerResponse> readBodyRoute() {
		return route("readbody_route")
				.POST(path("/post").and(readBody(String.class, s -> s.contains("hello"))), http())
				.before(new HttpbinUriResolver())
				// I forgot this :-|
				.before(adaptCachedBody())
				.build();
	}

}
