package org.lukawska.shop_client.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_client.dto.OrderApiResponse;
import org.lukawska.shop_client.service.OrderApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api_orders")
public class OrderApiController {

	private final OrderApiService service;

	@GetMapping
	public List<OrderApiResponse> getAllOrders() {
		return service.getAllOrders();
	}
}
