package com.example.gatewaymvcsample;

import java.time.Duration;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.caffeine.CaffeineProxyManager;
import io.github.bucket4j.distributed.proxy.AsyncProxyManager;
import io.github.bucket4j.distributed.remote.RemoteBucketState;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.Bucket4jFilterFunctions.rateLimit;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
public class Route12Bucket4jFilter {

	// http -h :8080/anything/ratelimit
	// execute above twice withing 5 sends and the first should have a 200 status
	// subsequent should have a 429 (Too Many Requests) status.
	// https://github.com/bucket4j/bucket4j#bucket4j-distributed-features
	@Bean
	public RouterFunction<ServerResponse> ratelimitRoute() {
		return route("ratelimit_route")
				.GET("/anything/ratelimit", http())
				.before(new HttpbinUriResolver())
				.filter(rateLimit(c -> c.setCapacity(1)
						.setPeriod(Duration.ofSeconds(5))
						.setKeyResolver(request -> "ratelimitttest1")))
				.before(addRequestHeader("X-Test", "ratelimit"))
				.build();
	}

	@SuppressWarnings("unchecked")
	@Bean
	public AsyncProxyManager<String> caffeineProxyManager() {
		Caffeine<String, RemoteBucketState> builder = (Caffeine) Caffeine.newBuilder().maximumSize(100);
		return new CaffeineProxyManager<>(builder, Duration.ofMinutes(1)).asAsync();
	}

}
