package org.lukawska.shop_backend.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(Long id, String username) { }
