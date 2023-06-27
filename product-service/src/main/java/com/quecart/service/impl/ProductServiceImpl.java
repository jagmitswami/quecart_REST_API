package com.quecart.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.quecart.dto.request.ProductRequest;
import com.quecart.dto.response.ProductResponse;
import com.quecart.exception.ProductException;
import com.quecart.model.Product;
import com.quecart.repository.ProductRepository;
import com.quecart.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public ProductResponse addProduct(ProductRequest productRequest) {
		Product product = buildProduct(productRequest);
		productRepository.save(product);
		log.info("Product {} saved successfully", productRequest.getName());
		return buildProductResponse(product);
	}

	@Override
	public ProductResponse getProductById(Long productId) {
		Optional<Product> opt = productRepository.findById(productId);
		if (opt.isEmpty())
			throw new ProductException("Product not found with id : " + productId);
		return buildProductResponse(opt.get());
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		if (products.isEmpty())
			throw new ProductException("Products not found");
		return products.stream().map(this::buildProductResponse).toList();
	}

	private Product buildProduct(ProductRequest productRequest) {
		return Product.builder().productId(productRequest.getProductId()).name(productRequest.getName())
				.description(productRequest.getDescription()).price(productRequest.getPrice()).build();
	}

	private ProductResponse buildProductResponse(Product product) {
		return ProductResponse.builder().productId(product.getProductId()).name(product.getName())
				.description(product.getDescription()).price(product.getPrice()).build();
	}

}
