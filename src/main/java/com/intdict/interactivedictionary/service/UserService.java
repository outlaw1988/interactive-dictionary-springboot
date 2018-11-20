package com.intdict.interactivedictionary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intdict.interactivedictionary.model.User;

@Service("userService")
public class UserService {

	private UserRepository userRepository;
	
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> findUserByLogin(String login) {
    	return userRepository.findByLogin(login);
    }
    
    public void saveUser(User user) {
    	//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }
    
}

