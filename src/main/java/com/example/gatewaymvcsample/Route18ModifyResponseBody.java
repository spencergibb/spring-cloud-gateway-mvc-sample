package com.example.gatewaymvcsample;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

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
				.after((request, response) -> {
					Object o = request.attributes().get(MvcUtils.CLIENT_RESPONSE_INPUT_STREAM_ATTR);
					if (o instanceof InputStream) {
						try {
							byte[] bytes = StreamUtils.copyToByteArray((InputStream) o);
							String s = new String(bytes, StandardCharsets.UTF_8);
							String replace = s.replace("fooval", "FOOVAL");
							ByteArrayInputStream bais = new ByteArrayInputStream(replace.getBytes());
							request.attributes().put(MvcUtils.CLIENT_RESPONSE_INPUT_STREAM_ATTR, bais);
						}
						catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
					return response;
				})
				.build();
		// @formatter:on
	}

}
