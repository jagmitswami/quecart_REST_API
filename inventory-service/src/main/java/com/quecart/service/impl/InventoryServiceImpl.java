package com.quecart.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quecart.repository.InventoryRepository;
import com.quecart.service.InventoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepo;

	@Override
	@Transactional(readOnly = true)
	public boolean isInStock(String skuCode) {
		return inventoryRepo.findBySkuCode(skuCode).isPresent();
	}

}
