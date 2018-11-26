package com.intdict.interactivedictionary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.intdict.interactivedictionary.model.Role;
import com.intdict.interactivedictionary.model.User;

@Service("userService")
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> findUserByUsername(String login) {
    	return userRepository.findByUsername(login);
    }
    
    public List<User> findUserByEmail(String email) {
    	return userRepository.findByEmail(email);
    }
    
    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setPasswordConfirm(new BCryptPasswordEncoder().encode(user.getPasswordConfirm()));
        user.setEnable(1);
        User userReturned = userRepository.save(user);
        
        Role role = new Role();
        role.setUsername(userReturned.getUsername());
        role.setAuthority("ROLE_USER");
        
        roleRepository.save(role);
    }
    
}

