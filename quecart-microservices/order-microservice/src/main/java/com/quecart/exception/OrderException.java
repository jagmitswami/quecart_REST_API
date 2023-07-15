package com.quecart.exception;

@SuppressWarnings("serial")
public class OrderException extends RuntimeException {

	public OrderException(String msg) {
		super(msg);
	}
}
