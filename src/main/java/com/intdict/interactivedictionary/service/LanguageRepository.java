package com.intdict.interactivedictionary.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.intdict.interactivedictionary.model.Language;
import com.intdict.interactivedictionary.model.User;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

	List<Language> findByUser(User user);
	List<Language> findByUserOrderByIdAsc(User user);
	List<Language> findByUserAndName(User user, String name);
	
}
