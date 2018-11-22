package com.intdict.interactivedictionary.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intdict.interactivedictionary.model.User;
import com.intdict.interactivedictionary.service.UserService;

@Controller
public class AuthenticationController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value="/registration", method=RequestMethod.GET)
    public String registration(ModelMap model){
		
		model.addAttribute("user", new User());
		
        return "registration";
    }
	
	@RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registrationPost(ModelMap model, @Valid User user, 
    		BindingResult bindingResult){
	
		int loginSize = userService.findUserByLogin(user.getLogin()).size();
		
		if (loginSize > 0) {
			bindingResult.rejectValue("login", "error.login",
                    "This login already exists");
		}
		
		int emailSize = userService.findUserByEmail(user.getEmail()).size();
		
		if (emailSize > 0) {
			bindingResult.rejectValue("email", "error.email",
                    "This email already exists");
		}
		
		if (bindingResult.hasErrors()) {
					
			return "registration";
		}
		
		userService.saveUser(user);
		
		model.addAttribute("user", new User());
		model.put("successMessage", "User has been registered successfully");
		
        return "registration";
    }
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}
	
}
