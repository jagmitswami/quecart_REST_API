package com.quecart.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quecart.dto.request.OrderItemDto;
import com.quecart.dto.request.OrderRequest;
import com.quecart.model.Order;
import com.quecart.model.OrderItem;
import com.quecart.repository.OrderRepository;
import com.quecart.service.OrderService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepo;

	@Override
	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		List<OrderItem> items = orderRequest.getOrderItemDtos().stream().map(this::buildOrderItem).toList();
		order.setOrderItems(items);
		orderRepo.save(order);

		log.info("Order placed successfully");
		return "Order placed successfully";
	}

	private OrderItem buildOrderItem(OrderItemDto dto) {
		return OrderItem.builder().skuCode(dto.getSkuCode()).price(dto.getPrice()).quantity(dto.getQuantity()).build();
	}
}
