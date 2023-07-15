package com.quecart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quecart.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	List<Inventory> findBySkuCodeIn(List<String> skuCode);

}
