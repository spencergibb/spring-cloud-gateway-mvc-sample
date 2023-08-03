package com.example.gatewaymvcsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@SpringBootApplication
public class GatewayMvcApplication {

	// http :8080/hello
	@Bean
	public RouterFunction<ServerResponse> helloRoute() {
		return RouterFunctions.route()
				.GET("/hello", request -> ServerResponse.ok().body("Hello World"))
				.build();
	}

	// http :8080/hi
	@Bean
	public RouterFunction<ServerResponse> hiRoute() {
		return RouterFunctions.route().GET("/hi", this::hi).build();
	}

	public ServerResponse hi(ServerRequest request) {
		return ServerResponse.ok().body("Hi World");
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayMvcApplication.class, args);
	}

}
