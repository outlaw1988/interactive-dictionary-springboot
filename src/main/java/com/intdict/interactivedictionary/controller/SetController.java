package com.intdict.interactivedictionary.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.Setup;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.SetupRepository;
import com.intdict.interactivedictionary.service.WordRepository;

@Controller
public class SetController {

	@Autowired
	SetRepository setRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	SetupRepository setupRepository;
	
	@Autowired
	WordRepository wordRepository;
	
	@RequestMapping(value = "/category-{id}", method=RequestMethod.GET)
	public String showSets(ModelMap model, @PathVariable(value="id") int categoryId) {
		model.put("categoryId", categoryId);
	
		Category category = categoryRepository.findById(categoryId).get();
		List<Set> sets = setRepository.findByCategory(category);
		
		model.put("sets", sets);
		model.put("category", category);
		
		return "category-sets-list";
	}
	
	@RequestMapping(value = "/add-set-{id}", method = RequestMethod.GET)
	public String addSetShow(ModelMap model, @PathVariable(value="id") int categoryId) {
		
		Category category = categoryRepository.findById(categoryId).get();
		model.addAttribute("set", new Set("", category));
		model.addAttribute("setup", new Setup());
//		model.addAttribute("word", new Word());
		
		model.put("category", category);
		model.put("targetSide", category.getDefaultTargetSide());
		if (category.getDefaultTargetSide().equals("left")) {
			model.put("srcSide", "right");
		} else {
			model.put("srcSide", "left");
		}
		
		return "add-set";
	}
	
	@RequestMapping(value = "/add-set-{id}", method = RequestMethod.POST)
	public String addSetPost(ModelMap model, @PathVariable(value="id") int categoryId, 
							@Valid Set set, BindingResult resultSet, 
							@Valid Setup setup, BindingResult resultSetup) {

		if (resultSet.hasErrors() || resultSetup.hasErrors()) {
			return "add-set";
		}
		
		Category category = categoryRepository.findById(categoryId).get();
		set.setCategory(category);
		Set setReturned = setRepository.save(set);
		
		setup.setSet(setReturned);
		
		// case when target language has changed
		if (setup.getTargetLanguage().getId() != category.getDefaultTargetLanguage().getId()) {
			setup.setSrcLanguage(category.getDefaultTargetLanguage());
		} else {
			// target language not changed
			setup.setSrcLanguage(category.getDefaultSrcLanguage());
		}
		
		setupRepository.save(setup);
		
		return "redirect:/category-" + categoryId;
	}
}
