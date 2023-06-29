package com.quecart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quecart.dto.request.OrderRequest;
import com.quecart.service.OrderService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/quecart/orders")
@AllArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/place")
	public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {

		return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.CREATED);

	}

}
