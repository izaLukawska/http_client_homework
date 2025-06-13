package org.lukawska.shop_backend.dto;

import lombok.Builder;

@Builder
public record ProductResponse(Long id, String name) {}
