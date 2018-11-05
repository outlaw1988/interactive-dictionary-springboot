package com.intdict.interactivedictionary.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Setup")
public class Setup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Set set;
	
	@OneToOne
	private Language srcLanguage;
	
	@OneToOne
	private Language targetLanguage;
	
	private String targetSide;
	
	private int lastResult;
	
	private int bestResult;
	
	private int countdownDuration;
	
	public Setup() {
		//empty
	}

	public Setup(Set set, Language srcLanguage, Language targetLanguage, String targetSide, int lastResult,
			int bestResult, int countdownDuration) {
		super();
		this.set = set;
		this.srcLanguage = srcLanguage;
		this.targetLanguage = targetLanguage;
		this.targetSide = targetSide;
		this.lastResult = lastResult;
		this.bestResult = bestResult;
		this.countdownDuration = countdownDuration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
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
	
	
}
