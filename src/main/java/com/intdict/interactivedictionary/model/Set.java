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
	
	@OneToOne
	private Language srcLanguage;
	
	@OneToOne
	private Language targetLanguage;
	
	private String targetSide;
	
	private int lastResult;
	
	private int bestResult;
	
	private int countdownDuration;
	
	private int isFree;
	
	@OneToOne
	private User user;

	public Set() {
		//empty
	}
	
	public Set(int id, @Size(min = 2, message = "Enter at least 2 characters...") String name, Category category,
			Language srcLanguage, Language targetLanguage, String targetSide, int lastResult, int bestResult,
			int countdownDuration, int isFree, User user) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.srcLanguage = srcLanguage;
		this.targetLanguage = targetLanguage;
		this.targetSide = targetSide;
		this.lastResult = lastResult;
		this.bestResult = bestResult;
		this.countdownDuration = countdownDuration;
		this.isFree = isFree;
		this.user = user;
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

	public Language getSrcLanguage() {
		return srcLanguage;
	}

	public void setSrcLanguage(Language srcLanguage) {
		this.srcLanguage = srcLanguage;
	}

	public Language getTargetLanguage() {
		return targetLanguage;
	}

	public void setTargetLanguage(Language targetLanguage) {
		this.targetLanguage = targetLanguage;
	}

	public String getTargetSide() {
		return targetSide;
	}

	public void setTargetSide(String targetSide) {
		this.targetSide = targetSide;
	}

	public int getLastResult() {
		return lastResult;
	}

	public void setLastResult(int lastResult) {
		this.lastResult = lastResult;
	}

	public int getBestResult() {
		return bestResult;
	}

	public void setBestResult(int bestResult) {
		this.bestResult = bestResult;
	}

	public int getCountdownDuration() {
		return countdownDuration;
	}

	public void setCountdownDuration(int countdownDuration) {
		this.countdownDuration = countdownDuration;
	}

	public int getIsFree() {
		return isFree;
	}

	public void setIsFree(int isFree) {
		this.isFree = isFree;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
