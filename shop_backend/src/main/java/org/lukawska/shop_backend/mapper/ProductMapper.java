package org.lukawska.shop_backend.mapper;

import org.lukawska.shop_backend.dto.ProductRequest;
import org.lukawska.shop_backend.dto.ProductResponse;
import org.lukawska.shop_backend.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductResponse toResponse(Product product);

	Product toProduct(ProductRequest request);

	void updateProductFromRequest(ProductRequest request, @MappingTarget Product product);
}

