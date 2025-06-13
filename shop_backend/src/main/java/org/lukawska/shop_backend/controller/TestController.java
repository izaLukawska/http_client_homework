package org.lukawska.shop_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/info")
	public String info() {
		return "Test info endpoint is working!";
	}
}
