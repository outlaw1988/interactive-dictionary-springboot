package com.intdict.interactivedictionary.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.Word;

public interface WordRepository extends JpaRepository<Word, Integer> {

	List<Word> findBySet(Set set);
}
