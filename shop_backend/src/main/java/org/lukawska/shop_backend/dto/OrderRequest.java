package org.lukawska.shop_backend.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequest(List<ProductRequest> products, CustomerResponse customerResponse) {}
