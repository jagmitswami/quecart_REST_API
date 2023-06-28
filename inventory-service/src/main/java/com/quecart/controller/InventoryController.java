package com.quecart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quecart.service.InventoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/quecart/inventory")
@AllArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;

	@GetMapping("/get/{skuCode}")
	public ResponseEntity<Boolean> isInStock(@PathVariable("skuCode") String skuCode) {
		return new ResponseEntity<>(inventoryService.isInStock(skuCode), HttpStatus.OK);
	}

}
