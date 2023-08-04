/*
 * Copyright 2013-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gatewaymvcsample;

import java.net.URI;
import java.util.function.Function;

import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.servlet.function.ServerRequest;

public class HttpbinUriResolver
		implements Function<ServerRequest, ServerRequest> {

	protected URI uri(ServerRequest request) {
		ApplicationContext context = MvcUtils.getApplicationContext(request);
		Integer port = context.getEnvironment().getProperty("httpbin.port", Integer.class);
		String host = context.getEnvironment().getProperty("httpbin.host");
		Assert.hasText(host, "httpbin.host is not set, did you initialize HttpbinTestcontainers?");
		Assert.notNull(port, "httpbin.port is not set, did you initialize HttpbinTestcontainers?");
		return URI.create(String.format("http://%s:%d", host, port));
	}

	@Override
	public ServerRequest apply(ServerRequest request) {
		URI uri = uri(request);
		MvcUtils.setRequestUrl(request, uri);
		return request;
	}

}
