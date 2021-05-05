package com.hit.store.models.people;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hit.store.models.product.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user", schema="people")
@JsonIgnoreProperties("cart")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String login;
	
	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 		  //avoid sending on GET
	private String password;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "people_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // because of lazy fetch
	private People people;
	
	@ManyToMany(fetch= FetchType.LAZY)
	@JoinTable(
			name="user_permission",
			schema="people",
			joinColumns={@JoinColumn(name="user_id")}, 
			inverseJoinColumns={@JoinColumn(name="permission_id")}
	)
	@JsonIgnoreProperties("users")				//avoid loop in json
	private List<Permission> permissions;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="user_role",
			schema="people",
			joinColumns={@JoinColumn(name="user_id")},
			inverseJoinColumns={@JoinColumn(name="role_id")}
	)
	@JsonIgnoreProperties("users")
	private List<Role> roles;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Cart> cart;
}
