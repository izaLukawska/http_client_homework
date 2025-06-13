package org.lukawska.shop_backend.service;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_backend.dto.CustomerRequest;
import org.lukawska.shop_backend.dto.CustomerResponse;
import org.lukawska.shop_backend.entity.Customer;
import org.lukawska.shop_backend.exception.ExceptionEnum;
import org.lukawska.shop_backend.exception.RestException;
import org.lukawska.shop_backend.mapper.CustomerMapper;
import org.lukawska.shop_backend.repository.CustomerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	public CustomerResponse getCustomer(Long id) {
		return customerMapper.toResponse(customerRepository.findById(id).orElseThrow(() -> new RestException(ExceptionEnum.CUSTOMER_NOT_FOUND)));
	}

	public List<CustomerResponse> getAllCustomers() {
		return customerRepository.findAll().stream().map(customerMapper::toResponse).toList();
	}

	public CustomerResponse createCustomer(CustomerRequest customerRequest) {
		try {
			Customer toSave = customerRepository.save(customerMapper.toCustomer(customerRequest));
			return customerMapper.toResponse(toSave);
		} catch (DataIntegrityViolationException e) {
			throw new RestException(ExceptionEnum.CUSTOMER_ALREADY_EXISTS);
		}
	}
}
