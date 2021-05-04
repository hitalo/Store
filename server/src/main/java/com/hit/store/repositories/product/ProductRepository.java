package com.hit.store.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{}
