package com.intdict.interactivedictionary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "words_set")
public class Set {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Size(min=2, message="Enter at least 2 characters...")
	@Column(name = "name")
	private String name;
	
	@OneToOne
	private Category category;

	public Set() {
		//empty
	}
	
	public Set(@Size(min = 2, message = "Enter at least 2 characters...") String name, Category category) {
		super();
		this.name = name;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
