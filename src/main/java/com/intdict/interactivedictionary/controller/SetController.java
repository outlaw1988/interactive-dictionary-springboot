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
import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.Setup;
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.SetupRepository;
import com.intdict.interactivedictionary.service.WordRepository;
import com.intdict.interactivedictionary.utils.Utils;

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
	public String addSetPost(HttpServletRequest request, ModelMap model, @PathVariable(value="id") int categoryId, 
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
		
		int highestWordIdx = Utils.findHighestWordIdxFromRequest(request.getParameterMap().keySet());
		
		for (int i = 1; i <= highestWordIdx; i++) {
			
			String srcWord = "";
			String targetWord = "";
			
			if (setup.getTargetSide().equals("left")) {
				srcWord = request.getParameter("right_field_" + i);
				targetWord = request.getParameter("left_field_" + i);
			} else if (setup.getTargetSide().equals("right")) {
				srcWord = request.getParameter("left_field_" + i);
				targetWord = request.getParameter("right_field_" + i);
			}
			
			if (srcWord.equals("") || targetWord.equals("")) continue;
			
			Word word = new Word(setReturned, srcWord, targetWord);
			wordRepository.save(word);
		}
		
		setupRepository.save(setup);
		
		return "redirect:/category-" + categoryId;
	}
	
	@RequestMapping(value = "/preview-{setId}", method = RequestMethod.GET)
	public String wordsPreview(ModelMap model, @PathVariable(value="setId") int setId) {
		
		Set set = setRepository.findById(setId).get();
		model.put("set", set);
		
		Setup setup = setupRepository.findBySet(set);
		model.put("setup", setup);
		
		List<Word> words = wordRepository.findBySet(set);
		model.put("words", words);
		
		return "words-preview";
	}
}
