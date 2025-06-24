package org.lukawska.shop_backend.dto;

import java.util.List;

public record OrderResponse(Long id, List<ProductResponse> products, CustomerResponse customer) {
}
