package org.lukawska.shop_backend.dto;

import lombok.Builder;

@Builder
public record UserRequest(String username) {}
