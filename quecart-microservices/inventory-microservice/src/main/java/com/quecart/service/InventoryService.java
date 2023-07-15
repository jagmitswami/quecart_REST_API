package com.quecart.service;

import java.util.List;

import com.quecart.dto.response.InventoryResponse;

public interface InventoryService {

	List<InventoryResponse> isInStock(List<String> skuCode);

}
