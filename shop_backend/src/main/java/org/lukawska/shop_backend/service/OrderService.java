package org.lukawska.shop_backend.service;

import lombok.RequiredArgsConstructor;
import org.lukawska.shop_backend.dto.OrderRequest;
import org.lukawska.shop_backend.dto.OrderResponse;
import org.lukawska.shop_backend.entity.Order;
import org.lukawska.shop_backend.exception.ExceptionEnum;
import org.lukawska.shop_backend.exception.RestException;
import org.lukawska.shop_backend.mapper.OrderMapper;
import org.lukawska.shop_backend.repository.OrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	private final OrderMapper orderMapper;

	public OrderResponse getOrder(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new RestException(ExceptionEnum.ORDER_NOT_FOUND));
		return orderMapper.toResponse(order);
	}

	public List<OrderResponse> getAllOrders() {
		return orderRepository.findAll().stream()
				.map(orderMapper::toResponse)
				.toList();
	}

	public OrderResponse createOrder(OrderRequest orderRequest) {
		try {
			Order order = mapToOrder(orderRequest);
			Order savedOrder = orderRepository.save(order);
			return orderMapper.toResponse(savedOrder);
		} catch (DataIntegrityViolationException e) {
			throw new RestException(ExceptionEnum.ORDER_ALREADY_EXISTS);
		}
	}

	private Order mapToOrder(OrderRequest orderRequest) {
		return orderMapper.toOrder(orderRequest);
	}
}