package org.lukawska.shop_client.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_client.dto.ProductApiRequest;
import org.lukawska.shop_client.dto.ProductApiResponse;
import org.lukawska.shop_client.service.ProductApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api_products")
class ProductApiController {

	private final ProductApiService service;

	@GetMapping("/{id}")
	ResponseEntity<ProductApiResponse> getProductById(@PathVariable Long id) {
		ProductApiResponse product = service.getProduct(id);
		return ResponseEntity.ok(product);
	}

	@GetMapping("/headers/{id}")
	ResponseEntity<ProductApiResponse> getProductWithHeaders(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String authHeader) {
		ProductApiResponse product = service.getProductWithHeaders(id, authHeader);
		return ResponseEntity.ok(product);
	}

	@PostMapping
	ResponseEntity<ProductApiResponse> createProduct(@RequestBody ProductApiRequest request) {
		ProductApiResponse product = service.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@PutMapping("/{id}")
	ResponseEntity<ProductApiResponse> updateProduct(@PathVariable Long id, @RequestBody ProductApiRequest request) {
		ProductApiResponse product = service.updateProduct(id, request);
		return ResponseEntity.ok(product);
	}

	@DeleteMapping("/{id}")
	ResponseEntity<ProductApiResponse> deleteProduct(@PathVariable Long id) {
		service.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}
}
