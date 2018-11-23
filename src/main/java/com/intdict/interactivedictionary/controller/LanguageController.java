package com.intdict.interactivedictionary.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.model.Language;
import com.intdict.interactivedictionary.model.User;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.LanguageRepository;
import com.intdict.interactivedictionary.service.UserRepository;
import com.intdict.interactivedictionary.utils.Utils;

@Controller
public class LanguageController {

	@Autowired
	LanguageRepository repository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/languages", method = RequestMethod.GET)
	public String showLanguages(ModelMap model) {
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName(model)).get(0);
		
//		List<Language> languages = repository.findAll();
		List<Language> languages = repository.findByUser(user);
		
		model.put("languages", languages);
		
		return "languages";
	}
	
	@RequestMapping(value = "/add-language", method = RequestMethod.GET)
	public String addLanguageShow(ModelMap model) {
		model.addAttribute("language", new Language("Language name"));
		return "add-language";
	}

	@RequestMapping(value = "/add-language", method = RequestMethod.POST)
	public String addLanguagePost(ModelMap model, @Valid Language language, BindingResult result) {

		if (result.hasErrors()) {
			return "add-language";
		}

		User user = userRepository.findByUsername(Utils.getLoggedInUserName(model)).get(0);
		language.setUser(user);
		
		repository.save(language);
		return "redirect:/languages";
	}
	
	@RequestMapping(value = "/remove-language-{languageId}", method = RequestMethod.GET)
	public String removeLanguage(ModelMap model, @PathVariable(value="languageId") int languageId) {
		
		Language language = repository.findById(languageId).get();
		List<Category> categoriesWithSrcLan = categoryRepository.findByDefaultSrcLanguage(language);
		List<Category> categoriesWithTargetLan = categoryRepository.findByDefaultTargetLanguage(language);
		
		boolean isInUse = false;
		
		if (categoriesWithSrcLan.size() > 0 || categoriesWithTargetLan.size() > 0) {
			isInUse = true;
		}
		
		model.put("isInUse", isInUse);
		model.put("language", language);
		
		return "remove-language";
	}
	
	@RequestMapping(value = "/remove-language-{languageId}", method = RequestMethod.POST)
	public String removeLanguagePost(HttpServletRequest request, 
			@PathVariable(value="languageId") int languageId) {
		
		java.util.Set<String> params = request.getParameterMap().keySet();
		Language language = repository.findById(languageId).get();
		
		if (params.contains("yes")) {
			repository.delete(language);
		}
		
		return "redirect:/languages";
	}
	
}
