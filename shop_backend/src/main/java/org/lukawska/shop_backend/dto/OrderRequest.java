package org.lukawska.shop_backend.dto;

import java.util.List;

public record OrderRequest(List<ProductRequest> products, CustomerResponse customerResponse) {}
