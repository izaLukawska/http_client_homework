package org.lukawska.shop_backend.dto;

import lombok.Builder;

@Builder
public record CustomerRequest(String username) {}
