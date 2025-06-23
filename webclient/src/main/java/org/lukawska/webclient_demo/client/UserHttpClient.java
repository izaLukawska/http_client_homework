package org.lukawska.webclient_demo.client;

import org.lukawska.webclient_demo.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/users")
public interface UserHttpClient {

	@GetExchange("/{id}")
	UserResponse getUserById(@PathVariable Long id);
}