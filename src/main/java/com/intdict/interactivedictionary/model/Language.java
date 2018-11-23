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
@Table(name = "Language")
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Size(min=2, message="Enter at least 2 characters...")
	@Column(name = "name")
	private String name;
	
	@OneToOne
	private User user;

	public Language() {
		//empty
	}

	public Language(@Size(min = 2, message = "Enter at least 2 characters...") String name) {
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
	
	@Override
	public String toString() {
		return name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
