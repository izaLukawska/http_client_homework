package org.lukawska.shop_backend.dto;

import lombok.Builder;

@Builder
public record UserResponse(Long id, String username) {}
