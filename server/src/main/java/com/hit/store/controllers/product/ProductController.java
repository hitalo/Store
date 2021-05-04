package com.hit.store.controllers.product;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hit.store.models.dto.ProductDTO;
import com.hit.store.models.product.Product;
import com.hit.store.repositories.product.ProductRepository;
import com.hit.store.utils.Validator;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@GetMapping("/getAll")
	public List<Product> getAll() {
		return productRepository.findAll();
	}
	
	
	@GetMapping("/getById")
	public Product getById(@RequestBody ProductDTO productDTO) {
		final Long id = productDTO.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Optional<Product> result = productRepository.findById(id);
		if(!result.isPresent()) throw new IllegalArgumentException("Can't find product with id: " + id);
		return result.get();
	}
	
	
	@PostMapping("/addOne")
	public Long addOneProduct(@ModelAttribute ProductDTO productDTO) throws IOException {
		final Product product = productDTO.getProduct();
		if(!Validator.validateProduct(product)) throw new IllegalArgumentException("Product invalid");
		if(product.getStock() == null) product.setStock(0.0);			//because of not null constraint
		return productRepository.save(product).getId();
	}
	
	
	@PutMapping("/update")
	public Long updateProduct(@ModelAttribute ProductDTO productDTO) throws IOException {
		final Product product = productDTO.getProduct();
		if(product.getId() == null) throw new IllegalArgumentException("id can't be null");
		if(!Validator.validateProduct(product)) throw new IllegalArgumentException("Product invalid");
		if(product.getStock() == null) product.setStock(0.0);
		return productRepository.save(product).getId();
	}
	
	
	@PutMapping("/updateStock")
	public Long updateProductStock(@RequestBody ProductDTO productDTO) {
		final Double stock = productDTO.getStock();
		if(stock == null) throw new IllegalArgumentException("stock can't be null");
		final Long id = productDTO.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Optional<Product> result = productRepository.findById(id);
		if(!result.isPresent()) throw new IllegalArgumentException("Can't find product with id: " + id);
		final Product product = result .get();
		product.setStock(stock);
		return productRepository.save(product).getId();
	}
	
	
	@DeleteMapping("/delete")
	@SuppressWarnings("all")
	public HashMap deleteProduct(@RequestBody ProductDTO productDTO) {
		final Long id = productDTO.getId();
		if(id == null) return null;
		final Optional<Product> result = productRepository.findById(id);
		if(!result.isPresent()) return null;
		productRepository.deleteById(id);
		return new HashMap(){{put("deleted", 1);}};
	}
}
