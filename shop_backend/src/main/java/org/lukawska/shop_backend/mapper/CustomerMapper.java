package org.lukawska.shop_backend.mapper;

import org.lukawska.shop_backend.dto.CustomerRequest;
import org.lukawska.shop_backend.dto.CustomerResponse;
import org.lukawska.shop_backend.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerResponse toResponse(Customer customer);

	Customer toCustomer(CustomerRequest customerRequest);
}
