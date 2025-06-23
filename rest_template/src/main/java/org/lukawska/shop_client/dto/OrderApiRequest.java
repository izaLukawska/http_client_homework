package org.lukawska.shop_client.dto;

import java.util.List;

public record OrderApiRequest(List<ProductApiResponse> products) {
}
