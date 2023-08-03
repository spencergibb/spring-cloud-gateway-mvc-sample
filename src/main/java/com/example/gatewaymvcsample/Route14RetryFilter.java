package com.example.gatewaymvcsample;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.fallbackHeaders;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.prefixPath;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.RetryFilterFunctions.retry;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration(proxyBeanMethods = false)
public class Route14RetryFilter {

	// http :8080/retry
	@Bean
	public RouterFunction<ServerResponse> gatewayRouterFunctionsRetry() {
		// @formatter:off
		return route("testretry")
				.route(path("/retry"), http())
				.before(request-> {
					ApplicationContext context = MvcUtils.getApplicationContext(request);
					Integer port = context.getEnvironment().getProperty("local.server.port", Integer.class);
					URI uri = URI.create("http://localhost:" + port);
					MvcUtils.setRequestUrl(request, uri);
					return request;
				})
				.filter(retry(3))
				.filter(prefixPath("/do"))
				.build();
		// @formatter:on
	}



	@RestController
	protected static class RetryController {

		Log log = LogFactory.getLog(getClass());

		ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

		@GetMapping("/do/retry")
		public ResponseEntity<String> retry(@RequestParam("key") String key,
											@RequestParam(name = "count", defaultValue = "3") int count,
											@RequestParam(name = "failStatus", required = false) Integer failStatus) {
			AtomicInteger num = getCount(key);
			int i = num.incrementAndGet();
			log.warn("Retry count: " + i);
			String body = String.valueOf(i);
			if (i < count) {
				HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				if (failStatus != null) {
					httpStatus = HttpStatus.resolve(failStatus);
				}
				return ResponseEntity.status(httpStatus).header("X-Retry-Count", body).body("temporarily broken");
			}
			return ResponseEntity.status(HttpStatus.OK).header("X-Retry-Count", body).body(body);
		}

		AtomicInteger getCount(String key) {
			return map.computeIfAbsent(key, s -> new AtomicInteger());
		}

	}
}
