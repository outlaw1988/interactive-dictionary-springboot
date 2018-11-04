package com.intdict.interactivedictionary.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intdict.interactivedictionary.model.Language;
import com.intdict.interactivedictionary.service.LanguageRepository;

@Controller
public class LanguageController {

	@Autowired
	LanguageRepository repository;
	
	@RequestMapping(value = "/languages", method = RequestMethod.GET)
	public String showLanguages(ModelMap model) {
		List<Language> languages = repository.findAll();
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

		repository.save(language);
		return "redirect:/languages";
	}
	
}
