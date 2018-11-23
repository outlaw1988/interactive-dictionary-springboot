package com.intdict.interactivedictionary.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intdict.interactivedictionary.model.Role;


@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
