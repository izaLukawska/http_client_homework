package org.lukawska.shop_backend.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderResponse(Long id, List<ProductResponse> products, CustomerResponse customer) {
}
