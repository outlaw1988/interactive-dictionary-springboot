package com.intdict.interactivedictionary.controller;

import java.util.ArrayList;
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
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.WordRepository;
import com.intdict.interactivedictionary.utils.Utils;

@Controller
public class SetController {

	@Autowired
	SetRepository setRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	WordRepository wordRepository;
	
	@RequestMapping(value = "/category-{id}", method=RequestMethod.GET)
	public String showSets(ModelMap model, @PathVariable(value="id") int categoryId) {
		model.put("categoryId", categoryId);
	
		Category category = categoryRepository.findById(categoryId).get();
		List<Set> sets = setRepository.findByCategory(category);
		
		List<Integer> wordCounters = new ArrayList<>();
		List<Integer> lastResults = new ArrayList<>();
		List<Integer> bestResults = new ArrayList<>();
		List<String> srcLanguages = new ArrayList<>();
		List<String> targetLanguages = new ArrayList<>();
		
		for (Set set : sets) {
			wordCounters.add(wordRepository.findBySet(set).size());
			//Setup setup = setupRepository.findBySet(set);
			lastResults.add(set.getLastResult());
			bestResults.add(set.getBestResult());
			srcLanguages.add(set.getSrcLanguage().getName());
			targetLanguages.add(set.getTargetLanguage().getName());
		}
		
		model.put("srcLanguages", srcLanguages);
		model.put("targetLanguages", targetLanguages);
		
		model.put("sets", sets);
		model.put("category", category);
		
		model.put("wordCounters", wordCounters);
		model.put("lastResults", lastResults);
		model.put("bestResults", bestResults);
		
		return "category-sets-list";
	}
	
	@RequestMapping(value = "/add-set-{id}", method = RequestMethod.GET)
	public String addSetShow(ModelMap model, @PathVariable(value="id") int categoryId) {
		
		Category category = categoryRepository.findById(categoryId).get();
		model.addAttribute("set", new Set());
		
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
							@Valid Set set, BindingResult result) {

		if (result.hasErrors()) {
			Category category = categoryRepository.findById(categoryId).get();
			model.addAttribute("set", new Set());
			
			model.put("category", category);
			model.put("targetSide", category.getDefaultTargetSide());
			if (category.getDefaultTargetSide().equals("left")) {
				model.put("srcSide", "right");
			} else {
				model.put("srcSide", "left");
			}
			
			return "add-set";
		}
		
		Category category = categoryRepository.findById(categoryId).get();
		set.setCategory(category);
		
		// case when target language has changed
		if (set.getTargetLanguage().getId() != category.getDefaultTargetLanguage().getId()) {
			set.setSrcLanguage(category.getDefaultTargetLanguage());
		} else {
			// target language not changed
			set.setSrcLanguage(category.getDefaultSrcLanguage());
		}
		
		int highestWordIdx = Utils.findHighestWordIdxFromRequest(request.getParameterMap().keySet());
		
		setRepository.save(set);
		
		for (int i = 1; i <= highestWordIdx; i++) {
			
			String srcWord = "";
			String targetWord = "";
			
			if (set.getTargetSide().equals("left")) {
				srcWord = request.getParameter("right_field_" + i);
				targetWord = request.getParameter("left_field_" + i);
			} else if (set.getTargetSide().equals("right")) {
				srcWord = request.getParameter("left_field_" + i);
				targetWord = request.getParameter("right_field_" + i);
			}
			
			if (srcWord.equals("") || targetWord.equals("")) continue;
			
			Word word = new Word(set, srcWord, targetWord);
			wordRepository.save(word);
		}
		
		//setupRepository.save(setup);
		
		
		return "redirect:/category-" + categoryId;
	}
	
	@RequestMapping(value = "/preview-{setId}", method = RequestMethod.GET)
	public String wordsPreview(ModelMap model, @PathVariable(value="setId") int setId) {
		
		Set set = setRepository.findById(setId).get();
		model.put("set", set);
		
		List<Word> words = wordRepository.findBySet(set);
		model.put("words", words);
		
		return "words-preview";
	}
	
	@RequestMapping(value = "/remove-set-{setId}", method = RequestMethod.GET)
	public String removeSet(ModelMap model, @PathVariable(value="setId") int setId) {
		
		Set set = setRepository.findById(setId).get();
		model.put("set", set);
		
		return "remove-set";
	}
	
	@RequestMapping(value = "/remove-set-{setId}", method = RequestMethod.POST)
	public String removeSetPost(HttpServletRequest request, 
									@PathVariable(value="setId") int setId) {
		
		java.util.Set<String> params = request.getParameterMap().keySet();
		Set set = setRepository.findById(setId).get();
		
		if (params.contains("yes")) {
			
			List<Word> words = wordRepository.findBySet(set);
			for (Word word : words) {
				wordRepository.delete(word);
			}
			
			setRepository.delete(set);
		}
		
		return "redirect:/category-" + set.getCategory().getId();
	}
}
