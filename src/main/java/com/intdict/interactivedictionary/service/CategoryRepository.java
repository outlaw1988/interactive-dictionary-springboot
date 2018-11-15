package com.intdict.interactivedictionary.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.model.Language;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	List<Category> findByDefaultSrcLanguage(Language language);
	List<Category> findByDefaultTargetLanguage(Language language);
	List<Category> findByName(String name);
	
}
