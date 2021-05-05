package com.hit.store.models.product;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hit.store.models.people.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cart", schema="product")
public class Cart {
	
	@EmbeddedId
	private CartId cartId = new CartId();

	
	@ManyToOne
	@MapsId("userId")
	@JsonIgnoreProperties({"permissions", "roles", "cart"})
	private User user;
	@ManyToOne
	@MapsId("productId")
	@JsonIgnoreProperties({"cart"})
	private Product product;
	private Double amount;
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Embeddable
	public static class CartId implements Serializable {
		private static final long serialVersionUID = 1L;
		private Long userId;
		private Long productId;
	}
}
