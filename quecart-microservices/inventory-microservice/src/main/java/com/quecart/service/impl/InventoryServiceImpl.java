package com.quecart.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quecart.dto.response.InventoryResponse;
import com.quecart.model.Inventory;
import com.quecart.repository.InventoryRepository;
import com.quecart.service.InventoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepo;

	@Override
	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		return inventoryRepo.findBySkuCodeIn(skuCode).stream().map(this::mapToInventoryResponse).toList();
	}

	private InventoryResponse mapToInventoryResponse(Inventory i) {
		return InventoryResponse.builder().skuCode(i.getSkuCode()).isInStock(i.getQuantity() > 0).build();
	}

}
