package com.hit.store.models.people;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name="permission", schema="people")
public class Permission {

	@Id
	private String value;
	@Column(nullable = false)
	private String name;
	private String description;
	
	
	@ManyToMany(mappedBy="permissions", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"permissions", "roles"})
	private List<User> users;
}
