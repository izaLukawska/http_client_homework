package org.lukawska.shop_client.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_client.dto.CustomerApiResponse;
import org.lukawska.shop_client.service.CustomerApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api_customers")
@RequiredArgsConstructor
public class CustomerApiController {

	private final CustomerApiService customerApiService;

	@GetMapping
	public List<CustomerApiResponse> getAllCustomers(){
		return customerApiService.getAllCustomers();
	}
}