package com.quecart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.quecart.model.Product;

public interface ProductRepository extends MongoRepository<Product, Long> {

}
