package com.intdict.interactivedictionary.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intdict.interactivedictionary.model.Word;

public interface WordRepository extends JpaRepository<Word, Integer> {

}
