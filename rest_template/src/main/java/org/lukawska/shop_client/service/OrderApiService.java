package org.lukawska.shop_client.service;

import lombok.extern.slf4j.Slf4j;
import org.lukawska.shop_client.dto.OrderApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class OrderApiService {

	private final RestTemplate restTemplate;

	@Value("${app.base-url}")
	private String baseUrl;

	public OrderApiService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<OrderApiResponse> getAllOrders(){
		OrderApiResponse[] orders = restTemplate.getForObject(baseUrl, OrderApiResponse[].class);
		log.info("Fetched: {}", Arrays.toString(orders));

		return orders != null ? Arrays.asList(orders) : Collections.emptyList();
	}
}
