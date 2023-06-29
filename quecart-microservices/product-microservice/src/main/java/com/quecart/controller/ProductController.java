package com.quecart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quecart.dto.request.ProductRequest;
import com.quecart.dto.response.ProductResponse;
import com.quecart.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quecart/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
		return new ResponseEntity<>(productService.addProduct(productRequest), HttpStatus.CREATED);
	}

	@GetMapping("/get/{productId}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable("productId") Long productId) {
		return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}

}
