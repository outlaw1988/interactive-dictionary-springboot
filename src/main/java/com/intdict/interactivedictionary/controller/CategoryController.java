package com.intdict.interactivedictionary.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.intdict.interactivedictionary.model.Setup;
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.LanguageRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.SetupRepository;
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
	
	@Autowired
	SetupRepository setupRepository;

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
		model.put("name", getLoggedInUserName(model));

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
	public String addCategoryPost(ModelMap model, @Validated({CreateGroup.class}) Category category, BindingResult result) {

		if (result.hasErrors()) {
			List<Language> languages = languageRepository.findAll();
			model.put("languages", languages);
			
			return "add-category";
		}

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
				
				Setup setup = setupRepository.findBySet(set);
				setupRepository.delete(setup);
				
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
		
		List<Language> languages = languageRepository.findAll();
		model.put("languages", languages);
		
		return "update-category";
	}
	
	@RequestMapping(value = "/update-category-{categoryId}", method = RequestMethod.POST)
	public String updateCategoryPost(ModelMap model, @Valid Category category, BindingResult result) {
		
		//System.out.println("Category id: " + category.getId());
		
		if (result.hasErrors()) {
			List<Language> languages = languageRepository.findAll();
			model.put("languages", languages);
			
			return "update-category";
		}
		
		repository.save(category);
		
		return "redirect:/index";
	}
	
	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();

		return principal.toString();
	}

}
