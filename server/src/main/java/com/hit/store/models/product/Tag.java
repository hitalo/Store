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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tag", schema="product")
@JsonIgnoreProperties("products")
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="product_tag",
			schema="product",
			joinColumns={@JoinColumn(name="tag_id")},
			inverseJoinColumns= {@JoinColumn(name="product_id")}
	)
	@JsonIgnoreProperties("tags")
	private List<Product> products;
}
