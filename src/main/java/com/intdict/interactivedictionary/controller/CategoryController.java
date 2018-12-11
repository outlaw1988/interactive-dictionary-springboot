package com.intdict.interactivedictionary.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.model.CreateGroup;
import com.intdict.interactivedictionary.model.Language;
import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.User;
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.LanguageRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.UserRepository;
import com.intdict.interactivedictionary.service.WordRepository;
import com.intdict.interactivedictionary.utils.Utils;

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
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
	public String index(ModelMap model, HttpServletRequest request) {

		User user = userRepository.findByUsername(Utils.getLoggedInUserName()).get(0);
		
		List<Category> categories = repository.findByUser(user);
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
		
		HttpSession session = request.getSession();
		session.setAttribute("username", Utils.getLoggedInUserName());

		return "index";
	}

	@RequestMapping(value = "/add-category", method = RequestMethod.GET)
	public String addCategoryShow(ModelMap model) {
		
		model.addAttribute("category", new Category(""));
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName()).get(0);
		List<Language> languages = languageRepository.findByUser(user);
		
		model.put("languages", languages);
		
		return "add-category";
	}

	// @Validated({CreateGroup.class})
	@RequestMapping(value = "/add-category", method = RequestMethod.POST)
	public String addCategoryPost(ModelMap model, @Valid Category category, BindingResult result) {

		User user = userRepository.findByUsername(Utils.getLoggedInUserName()).get(0);
		List<Category> categoriesToCheck = repository.findByNameAndUser(category.getName(), user);
		
		if (categoriesToCheck.size() > 0) {
			result.rejectValue("name", "error.name", "This category already exists");
		}
		
		if ((category.getDefaultSrcLanguage() != null) && (category.getDefaultTargetLanguage() != null)) {
			
			if (category.getDefaultSrcLanguage().equals(category.getDefaultTargetLanguage())) {
				result.rejectValue("defaultTargetLanguage", 
									"error.defaultTargetLanguage", "Languages must be different");
			}
		}
		
		if (result.hasErrors()) {
			System.out.println("Has errors!!!!!!!");
			List<Language> languages = languageRepository.findByUser(user);
			
			model.put("languages", languages);
			
			return "add-category";
		}
		
		category.setUser(user);

		repository.save(category);
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/remove-category-{categoryId}", method = RequestMethod.GET)
	public String removeCategory(ModelMap model, @PathVariable(value="categoryId") int categoryId) {
		
		Category category = repository.findById(categoryId).get();
		model.put("category", category);
		
		return "remove-category";
	}
	
	@RequestMapping(value = "/remove-category-{categoryId}", method = RequestMethod.POST)
	public String removeCategoryPost(HttpServletRequest request, 
									@PathVariable(value="categoryId") int categoryId) {
		
		java.util.Set<String> params = request.getParameterMap().keySet();
		
		if (params.contains("yes")) {
			Category category = repository.findById(categoryId).get();
			List<Set> sets = setRepository.findByCategory(category);
			
			for (Set set : sets) {
				
				List<Word> words = wordRepository.findBySet(set);
				for (Word word : words) {
					wordRepository.delete(word);
				}
				
				setRepository.delete(set);
			}
			
			repository.deleteById(categoryId);
		}
		
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/update-category-{categoryId}", method = RequestMethod.GET)
	public String updateCategoryGet(ModelMap model, @PathVariable(value="categoryId") int categoryId) {
		
		Category category = repository.findById(categoryId).get();
		model.addAttribute("category", category);
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName()).get(0);
		List<Language> languages = languageRepository.findByUser(user);
		
		model.put("languages", languages);
		
		return "update-category";
	}
	
	@RequestMapping(value = "/update-category-{categoryId}", method = RequestMethod.POST)
	public String updateCategoryPost(ModelMap model, @Valid Category category, BindingResult result) {
		
		// TODO validation category exists etc.
		
		if (result.hasErrors()) {
			User user = userRepository.findByUsername(Utils.getLoggedInUserName()).get(0);
			List<Language> languages = languageRepository.findByUser(user);
			
			model.put("languages", languages);
			
			return "update-category";
		}
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName()).get(0);
		category.setUser(user);
		
		repository.save(category);
		
		return "redirect:/index";
	}

}
