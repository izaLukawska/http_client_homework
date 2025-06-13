package org.lukawska.shop_backend.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_backend.dto.CustomerRequest;
import org.lukawska.shop_backend.dto.CustomerResponse;
import org.lukawska.shop_backend.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping
	public List<CustomerResponse> getAllCustomers(){
		return customerService.getAllCustomers();
	}

	@PostMapping
	public CustomerResponse createCustomer(@RequestBody CustomerRequest request){
		return customerService.createCustomer(request);
	}
}
