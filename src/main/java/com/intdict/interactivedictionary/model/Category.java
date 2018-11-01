package com.intdict.interactivedictionary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Category {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Size(min=2, message="Enter at least 2 characters...")
	private String name;
	
	public Category() {
		//empty
	}

	public Category(String name) {
		super();
		this.name = name;
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
	
	
	
}
