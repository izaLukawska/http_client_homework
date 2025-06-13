package org.lukawska.shop_backend.service;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_backend.dto.ProductRequest;
import org.lukawska.shop_backend.dto.ProductResponse;
import org.lukawska.shop_backend.entity.Product;
import org.lukawska.shop_backend.exception.ExceptionEnum;
import org.lukawska.shop_backend.exception.RestException;
import org.lukawska.shop_backend.mapper.ProductMapper;
import org.lukawska.shop_backend.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	private final ProductMapper productMapper;

	public ProductResponse getProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RestException(ExceptionEnum.PRODUCT_NOT_FOUND));
		return productMapper.toResponse(product);
	}

	public ProductResponse createProduct(ProductRequest request) {
		try {
			Product product = productMapper.toProduct(request);
			Product savedProduct = productRepository.save(product);
			return productMapper.toResponse(savedProduct);
		} catch (DataIntegrityViolationException e) {
			throw new RestException(ExceptionEnum.PRODUCT_ALREADY_EXISTS);
		}
	}

	public ProductResponse updateProduct(Long id, ProductRequest request) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new RestException(ExceptionEnum.PRODUCT_NOT_FOUND));

		productMapper.updateProductFromRequest(request, existingProduct);
		Product updatedProduct = productRepository.save(existingProduct);

		return productMapper.toResponse(updatedProduct);
	}

	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RestException(ExceptionEnum.PRODUCT_NOT_FOUND));
		productRepository.delete(product);
	}
}
