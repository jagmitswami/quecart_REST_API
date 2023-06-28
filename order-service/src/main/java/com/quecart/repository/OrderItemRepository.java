package com.quecart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quecart.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
