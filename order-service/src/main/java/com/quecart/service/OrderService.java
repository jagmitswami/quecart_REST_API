package com.quecart.service;

import com.quecart.dto.request.OrderRequest;

public interface OrderService {

	String placeOrder(OrderRequest orderRequest);

}
