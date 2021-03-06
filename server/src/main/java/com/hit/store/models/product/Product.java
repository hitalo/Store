package com.hit.store.models.product;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product", schema="product")
@JsonIgnoreProperties("cart")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	private String description;
	@Column(nullable = false)
	private Double value;
	@Column(nullable = false)
	private String unit;
	private byte[] image;
	private Double stock;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="product_tag",
			schema="product",
			joinColumns={@JoinColumn(name="product_id")},
			inverseJoinColumns= {@JoinColumn(name="tag_id")}
	)
	@JsonIgnoreProperties("products")
	private List<Tag> tags;
	
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<Cart> cart;
}
