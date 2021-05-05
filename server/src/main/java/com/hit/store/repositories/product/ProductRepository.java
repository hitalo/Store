package com.hit.store.repositories.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	Optional<List<Product>> findByTagsId(Long tagId);
}
