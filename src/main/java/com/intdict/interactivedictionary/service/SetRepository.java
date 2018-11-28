package com.intdict.interactivedictionary.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.User;

public interface SetRepository extends JpaRepository<Set, Integer>{

	List<Set> findByCategory(Category category);
	List<Set> findByUserAndIsFree(User user, int isFree);
	
}
