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
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.SetRepository;

@Controller
public class SetController {

	@Autowired
	SetRepository setRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@RequestMapping(value = "/category-{id}", method=RequestMethod.GET)
	public String showSets(ModelMap model, @PathVariable(value="id") int categoryId) {
		model.put("categoryId", categoryId);
	
		Category category = categoryRepository.findById(categoryId).get();
		List<Set> sets = setRepository.findByCategory(category);
		
		model.put("sets", sets);
		model.put("category", category);
		
		return "category-sets-list";
	}
	
	// TODO Consider passing by session
	@RequestMapping(value = "/add-set-{id}", method = RequestMethod.GET)
	public String addSetShow(ModelMap model, @PathVariable(value="id") int categoryId) {
		
		Category category = categoryRepository.findById(categoryId).get();
		model.addAttribute("set", new Set("", category));
		
		model.put("category", category);
		
		return "add-set";
	}
	
	@RequestMapping(value = "/add-set-{id}", method = RequestMethod.POST)
	public String addSetPost(ModelMap model, @PathVariable(value="id") int categoryId, 
							@Valid Set set, BindingResult result) {

		if (result.hasErrors()) {
			return "add-set";
		}

		Category category = categoryRepository.findById(categoryId).get();
		set.setCategory(category);
		
		setRepository.save(set);
		return "redirect:/category-" + categoryId;
	}
}
