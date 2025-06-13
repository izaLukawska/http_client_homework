package org.lukawska.shop_client.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderApiResponse(Long id, List<ProductApiResponse> products) {
}
