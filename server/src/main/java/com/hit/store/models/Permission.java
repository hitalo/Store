package com.hit.store.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
}
