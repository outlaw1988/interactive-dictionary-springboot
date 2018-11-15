package com.intdict.interactivedictionary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.intdict.interactivedictionary.form.FieldMatch;

@Entity
@Table(name = "Category")
@FieldMatch(first = "defaultSrcLanguage", second = "defaultTargetLanguage", message = "Languages must be different")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Size(min=2, message="Enter at least 2 characters...")
	@Column(name = "name")
	private String name;
	
	@OneToOne
	@NotNull
	private Language defaultSrcLanguage;
	
	@OneToOne
	@NotNull
	private Language defaultTargetLanguage;
	
	private String defaultTargetSide;
	
	private int defaultCountdownDuration;
	
	public Category() {
		//empty
		//System.out.println("Empty constructor called!!");
	}

	public Category(String name) {
		super();
		//System.out.println("Name constructor called!!");
		this.name = name;
	}
	
	public Category(String name, Language defaultSrcLanguage, Language defaultTargetLanguage) {
		super();
		//System.out.println("Three elements constructor called!!");
		this.name = name;
		this.defaultSrcLanguage = defaultSrcLanguage;
		this.defaultTargetLanguage = defaultTargetLanguage;

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

	public Language getDefaultSrcLanguage() {
		return defaultSrcLanguage;
	}

	public void setDefaultSrcLanguage(Language defaultSrcLanguage) {
		this.defaultSrcLanguage = defaultSrcLanguage;
	}

	public Language getDefaultTargetLanguage() {
		return defaultTargetLanguage;
	}

	public void setDefaultTargetLanguage(Language defaultTargetLanguage) {
		this.defaultTargetLanguage = defaultTargetLanguage;
	}

	public String getDefaultTargetSide() {
		return defaultTargetSide;
	}

	public void setDefaultTargetSide(String defaultTargetSide) {
		this.defaultTargetSide = defaultTargetSide;
	}

	public int getDefaultCountdownDuration() {
		return defaultCountdownDuration;
	}

	public void setDefaultCountdownDuration(int defaultCountdownDuration) {
		this.defaultCountdownDuration = defaultCountdownDuration;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
}
