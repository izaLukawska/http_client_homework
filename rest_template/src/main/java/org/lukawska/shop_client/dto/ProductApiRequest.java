package org.lukawska.shop_client.dto;

import lombok.Builder;

@Builder
public record ProductApiRequest(String name) {
}
