package com.example.gatewaymvcsample;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {GatewayMvcApplication.class, HttpbinConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GatewayMvcApplicationTests {

	@Test
	void contextLoads() {
	}

}
