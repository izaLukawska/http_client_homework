package org.lukawska.shop_backend.controller;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_backend.dto.ProductRequest;
import org.lukawska.shop_backend.dto.ProductResponse;
import org.lukawska.shop_backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/{id}")
	public ProductResponse getProduct(@PathVariable Long id, @RequestHeader(
			value = "Authorization",
			required = false) String authHeader){
		System.out.println("Received Authorization: " + authHeader);
		return productService.getProduct(id);
	}


	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request){
		return ResponseEntity.status(201).body(productService.createProduct(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
														 @RequestBody ProductRequest request) {
		ProductResponse updated = productService.updateProduct(id, request);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id){
		productService.deleteProduct(id);
	}
}

