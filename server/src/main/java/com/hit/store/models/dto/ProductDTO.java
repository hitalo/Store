package com.hit.store.models.dto;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hit.store.models.product.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private MultipartFile productImage;
	private Long id;
	private String name;
	private String description;
	private Double value;
	private String unit;
	private Double stock;
	
	public Product getProduct() throws IOException {
		return new Product(id, name, description, value, unit, productImage.getBytes(), stock);
	}
	
}
