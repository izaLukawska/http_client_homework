package org.lukawska.shop_backend.mapper;

import org.lukawska.shop_backend.dto.OrderRequest;
import org.lukawska.shop_backend.dto.OrderResponse;
import org.lukawska.shop_backend.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CustomerMapper.class})
public interface OrderMapper {

	@Mapping(target = "products", source = "products")
	@Mapping(target = "customer", source = "customer")
	OrderResponse toResponse(Order order);

	Order toOrder(OrderRequest orderRequest);
}
