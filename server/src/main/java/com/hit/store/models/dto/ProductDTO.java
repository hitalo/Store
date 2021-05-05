package com.hit.store.models.dto;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hit.store.models.product.Product;
import com.hit.store.models.product.Tag;

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
	private List<Tag> tags;
	
	
	public Product getProduct() throws IOException {
		return new Product(id, name, description, value, unit, productImage.getBytes(), stock, null, null);
	}
	
	
//	private String tags;
	
//	public Product getProduct() throws IOException {
//		return new Product(
//				id, name, description, value, unit, productImage.getBytes(), 
//				stock, Arrays.asList(new Gson().fromJson(tags, Tag[].class))
//				, null);
//	}
	
}
