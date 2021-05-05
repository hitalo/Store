package com.hit.store.controllers.product;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hit.store.models.people.User;
import com.hit.store.models.product.Cart;
import com.hit.store.models.product.Product;
import com.hit.store.repositories.people.UserRepository;
import com.hit.store.repositories.product.CartRepository;
import com.hit.store.repositories.product.ProductRepository;
import com.hit.store.utils.Validator;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	
	
	@GetMapping("/getAll")
	public List<Cart> getAll() {
		return cartRepository.findAll();
	}
	
	
	@GetMapping("/getByUser")
	public List<Cart> getCartByUser(@RequestBody Cart cart) {
		final Cart.CartId id = cart.getCartId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Long userId = cart.getCartId().getUserId();
		if(userId == null) throw new IllegalArgumentException("user.id can't be null");
		final Optional<List<Cart>> result = cartRepository.findByCartIdUserId(userId);
		if(!result.isPresent()) return null;
		return result.get();
	}
	
	
	@PutMapping("/addOrUpdateCart")
	public Long addOrUpdateCart(@RequestBody Cart cart) {
		final Long userId = cart.getUser().getId();
		final Long productId = cart.getProduct().getId();
		
		if(!Validator.validateCart(cart)) throw new IllegalArgumentException("Cart invalid");
		final Optional<User> resultUser = userRepository.findById(userId);
		if(!resultUser.isPresent()) throw new IllegalArgumentException("Can't find user informed");
		final Optional<Product> resultProduct = productRepository.findById(productId);
		if(!resultProduct.isPresent()) throw new IllegalArgumentException("Can't find product informed");
		
		cart.getCartId().setUserId(userId);
		cart.getCartId().setProductId(productId);
		cart.setUser(resultUser.get());
		cart.setProduct(resultProduct.get());
		cartRepository.save(cart);
		return userId;
	}
	
	
	@DeleteMapping("/removeItem")
	@SuppressWarnings("all")
	public HashMap removeItemCart(@RequestBody Cart cart) {
		final Cart.CartId id = cart.getCartId();
		if(id == null) return null;
		final Optional<Cart> result = cartRepository.findById(id);
		if(!result.isPresent()) return null;
		cartRepository.deleteById(cart.getCartId());
		return new HashMap(){{put("deleted", 1);}};
	}
	
	
	@DeleteMapping("/delete")
	@SuppressWarnings("all")
	public HashMap deleteCart(@RequestBody Cart cart) {
		final Long userId = cart.getCartId().getUserId();
		if(userId == null) return null;
		final Optional<List<Cart>> result = cartRepository.findByCartIdUserId(userId);
		if(!result.isPresent()) return null;
		cartRepository.deleteAll(result.get());
		return new HashMap(){{put("deleted", 1);}};
	}
}
