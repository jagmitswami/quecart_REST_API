package com.quecart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quecart.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
