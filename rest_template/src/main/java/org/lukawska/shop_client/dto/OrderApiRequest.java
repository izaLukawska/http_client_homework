package org.lukawska.shop_client.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderApiRequest(List<ProductApiResponse> products) {
}
