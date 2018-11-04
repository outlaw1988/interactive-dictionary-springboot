package com.intdict.interactivedictionary.service;

import org.springframework.data.jpa.repository.JpaRepository;
import com.intdict.interactivedictionary.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

}
