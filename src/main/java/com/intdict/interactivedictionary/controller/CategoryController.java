package com.intdict.interactivedictionary.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.service.CategoryRepository;

@Controller
public class CategoryController {

	@Autowired
	CategoryRepository repository;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(ModelMap model) {

		List<Category> categories = repository.findAll();
		model.put("categories", categories);

		return "index";
	}

	@RequestMapping(value = "/add-category", method = RequestMethod.GET)
	public String addCategoryShow(ModelMap model) {
		model.addAttribute("category", new Category("Category name"));
		return "add-category";
	}

	@RequestMapping(value = "/add-category", method = RequestMethod.POST)
	public String addCategoryPost(ModelMap model, @Valid Category category, BindingResult result) {

		if (result.hasErrors()) {
			return "add-category";
		}

		repository.save(category);
		return "redirect:/index";
	}

}
