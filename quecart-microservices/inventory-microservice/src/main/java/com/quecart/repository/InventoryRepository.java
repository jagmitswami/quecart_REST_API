package com.quecart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quecart.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Optional<Inventory> findBySkuCode(String skuCode);

}
