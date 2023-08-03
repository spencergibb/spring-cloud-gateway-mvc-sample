package com.example.gatewaymvcsample;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.ServiceInstanceListSuppliers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration(proxyBeanMethods = false)
@LoadBalancerClient(name = "httpbin", configuration = Route11LoadBalancerFilter.Httpbin.class)
public class Route11LoadBalancerFilter {

	// http :8080/anything/loadbalancer
	@Bean
	public RouterFunction<ServerResponse> loadBalancerRoute() {
		return route("loadbalancer_route")
				.GET("/anything/loadbalancer", http())
				.filter(lb("httpbin"))
				.before(addRequestHeader("X-Test", "loadbalancer"))
				.build();
	}

	public static class Httpbin {

		@Bean
		public ServiceInstanceListSupplier staticServiceInstanceListSupplier(Environment env) {
			return ServiceInstanceListSuppliers.from("httpbin", new DefaultServiceInstance("httpbin" + "-1", "httpbin",
					env.getProperty("httpbin.host"), env.getProperty("httpbin.port", Integer.class), false));
		}

	}
}
