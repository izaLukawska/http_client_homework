package org.lukawska.shop_client.service;

import org.lukawska.shop_client.dto.OrderApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class OrderApiService {

	private final RestTemplate restTemplate;

	private static final String BASE_URL = "http://localhost:8080";

	public OrderApiService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<OrderApiResponse> getAllOrders(){
		String url = BASE_URL + "/orders";
		OrderApiResponse[] orders = restTemplate.getForObject(url, OrderApiResponse[].class);
		System.out.println("Fetched: " + Arrays.toString(orders));
		return orders != null ? Arrays.asList(orders) : Collections.emptyList();
	}
}