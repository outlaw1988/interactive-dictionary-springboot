package com.intdict.interactivedictionary.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.Setup;

public interface SetupRepository extends JpaRepository<Setup, Integer> {

	Setup findBySet(Set set);
}
