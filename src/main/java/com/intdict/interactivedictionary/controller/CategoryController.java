package com.intdict.interactivedictionary.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.model.Language;
import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.LanguageRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.WordRepository;

@Controller
public class CategoryController {

	@Autowired
	CategoryRepository repository;
	
	@Autowired
	LanguageRepository languageRepository;
	
	@Autowired
	SetRepository setRepository;
	
	@Autowired
	WordRepository wordRepository;

	@RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
	public String index(ModelMap model) {

		List<Category> categories = repository.findAll();
		model.put("categories", categories);
		
		List<Integer> setCounters = new ArrayList<>();
		List<Integer> wordCounters = new ArrayList<>();
		
		for (Category category : categories) {
			List<Set> sets = setRepository.findByCategory(category);
			setCounters.add(sets.size());
			
			Integer wordCount = 0;
			for (Set set : sets) {
				wordCount += wordRepository.findBySet(set).size();
			}
			wordCounters.add(wordCount);
		}
		
		model.put("setCounters", setCounters);
		model.put("wordCounters", wordCounters);

		return "index";
	}

	@RequestMapping(value = "/add-category", method = RequestMethod.GET)
	public String addCategoryShow(ModelMap model) {
		
		model.addAttribute("category", new Category(""));
		
		List<Language> languages = languageRepository.findAll();
		model.put("languages", languages);
		
		return "add-category";
	}

	@RequestMapping(value = "/add-category", method = RequestMethod.POST)
	public String addCategoryPost(ModelMap model, @Valid Category category, BindingResult result) {

		if (result.hasErrors()) {
			return "add-category";
		}
		
		// System.out.println("Category id: " + category.getId() + " name: " + category.getName());

		repository.save(category);
		return "redirect:/index";
	}

}
