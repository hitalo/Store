package com.hit.store.repositories.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.product.Cart;

public interface CartRepository extends JpaRepository<Cart, Cart.CartId>{
	Optional<List<Cart>> findByCartIdUserId(Long userId);
}
