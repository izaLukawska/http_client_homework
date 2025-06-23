package org.lukawska.shop_backend.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_backend.dto.OrderRequest;
import org.lukawska.shop_backend.dto.OrderResponse;
import org.lukawska.shop_backend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService service;

	@GetMapping
	public List<OrderResponse> getAllOrders(){
		return service.getAllOrders();
	}

	@GetMapping({"/{id}"})
	public OrderResponse getOrder(@PathVariable Long id){
		return service.getOrder(id);
	}

	@PostMapping
	public OrderResponse createOrder(@RequestBody OrderRequest request) {
		return service.createOrder(request);
	}
}
