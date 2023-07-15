package com.quecart.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.quecart.dto.proxy.InventoryResponse;
import com.quecart.dto.request.OrderItemDto;
import com.quecart.dto.request.OrderRequest;
import com.quecart.exception.OrderException;
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

	private final WebClient.Builder webClientBuilder;

	@Override
	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setUuid(UUID.randomUUID().toString());
		List<OrderItem> items = orderRequest.getOrderItemDtos().stream().map(this::buildOrderItem).toList();
		order.setOrderItems(items);

		List<String> skuCodes = order.getOrderItems().stream().map(OrderItem::getSkuCode).toList();

		// talk to inventory service
		List<InventoryResponse> inventoryResponseList = webClientBuilder.build().get()
				.uri("http://inventory-service/quecart/inventory/get",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<InventoryResponse>>() {})
				.block();

		boolean allProductsInStock = inventoryResponseList.stream().allMatch(InventoryResponse::isInStock);
		
		if (!allProductsInStock || inventoryResponseList.size() != skuCodes.size()) {
			throw new OrderException("Item is out of stock");
		}

		orderRepo.save(order);

		log.info("Order placed successfully");
		return "Order placed successfully";
	}

	private OrderItem buildOrderItem(OrderItemDto dto) {
		return OrderItem.builder().skuCode(dto.getSkuCode()).price(dto.getPrice()).quantity(dto.getQuantity()).build();
	}
}
