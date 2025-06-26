package org.lukawska.shop_client.dto;

import java.util.List;

public record OrderApiResponse(Long id, List<ProductApiResponse> products) {
}
