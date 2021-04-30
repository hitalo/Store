package com.hit.store.models;

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
@Table(name="role", schema="people")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable =  false)
	private String name;
	private String description;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name="role_permission",
			schema="people",
			joinColumns={@JoinColumn(name="role_id")},
			inverseJoinColumns={@JoinColumn(name="permission_id")}
	)
	@JsonIgnoreProperties({ "roles", "users" })
	public List<Permission> permissions;
	
	
	@ManyToMany(mappedBy="roles", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("roles")				//avoid loop in json
	private List<User> users;
}
