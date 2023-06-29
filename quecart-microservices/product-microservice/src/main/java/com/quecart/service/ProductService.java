package com.quecart.service;

import java.util.List;

import com.quecart.dto.request.ProductRequest;
import com.quecart.dto.response.ProductResponse;

public interface ProductService {
	ProductResponse addProduct(ProductRequest productRequest);

	ProductResponse getProductById(Long productId);

	List<ProductResponse> getAllProducts();
}
