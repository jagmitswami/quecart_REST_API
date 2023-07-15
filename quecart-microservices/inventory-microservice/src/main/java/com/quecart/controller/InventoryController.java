package com.quecart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.quecart.dto.response.InventoryResponse;
import com.quecart.service.InventoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/quecart/inventory")
@AllArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/get")
	@ResponseStatus(value = HttpStatus.OK)
	public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
		return inventoryService.isInStock(skuCode);
	}

}
