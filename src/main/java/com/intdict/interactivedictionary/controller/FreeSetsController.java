package com.intdict.interactivedictionary.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intdict.interactivedictionary.model.Language;
import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.User;
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.LanguageRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.UserRepository;
import com.intdict.interactivedictionary.service.WordRepository;
import com.intdict.interactivedictionary.utils.Utils;

@Controller
public class FreeSetsController {
	
	@Autowired
	SetRepository setRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LanguageRepository languageRepository;
	
	@Autowired
	WordRepository wordRepository;

	@RequestMapping(value = "free-sets", method = RequestMethod.GET)
	public String freeSetsInit(ModelMap model) {
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName(model)).get(0);
		List<Set> sets = setRepository.findByUserAndIsFree(user, 1);
		
		List<Integer> wordCounters = new ArrayList<>();
		List<Integer> lastResults = new ArrayList<>();
		List<Integer> bestResults = new ArrayList<>();
		List<String> srcLanguages = new ArrayList<>();
		List<String> targetLanguages = new ArrayList<>();
		
		for (Set set : sets) {
			wordCounters.add(wordRepository.findBySet(set).size());
			lastResults.add(set.getLastResult());
			bestResults.add(set.getBestResult());
			srcLanguages.add(set.getSrcLanguage().getName());
			targetLanguages.add(set.getTargetLanguage().getName());
		}
		
		model.put("srcLanguages", srcLanguages);
		model.put("targetLanguages", targetLanguages);
		
		model.put("sets", sets);
		
		model.put("wordCounters", wordCounters);
		model.put("lastResults", lastResults);
		model.put("bestResults", bestResults);
		
		return "free-sets";
	}
	
	@RequestMapping(value = "add-free-set", method = RequestMethod.GET)
	public String addFreeSetGet(ModelMap model) {
		
		model.addAttribute("set", new Set());
		model.put("targetSide", "right");
		model.put("srcSide", "left");
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName(model)).get(0);
		List<Language> languages = languageRepository.findByUser(user);
		
		model.put("languages", languages);
		
		return "add-free-set";
	}
	
	@RequestMapping(value = "add-free-set", method = RequestMethod.POST)
	public String addFreeSetPost(HttpServletRequest request, ModelMap model, 
							@Valid Set set, BindingResult result) {
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName(model)).get(0);
		List<Set> sets = setRepository.findByUserAndIsFree(user, 1);
		
		for (Set setIter : sets) {
			if (setIter.getName().equals(set.getName())) {
				result.rejectValue("name", "error.name", "This set already exists");
			}
		}
		
		if (Utils.isSetEmpty(request)) {
			result.rejectValue("name", "error.name", "Set must contain at least one word");
		}
		
		// TODO target lan != src lan
				
		if (result.hasErrors()) {
					
			model.put("targetSide", "right");
			model.put("srcSide", "left");
			
			List<Language> languages = languageRepository.findByUser(user);
			
			model.put("languages", languages);
			
			return "add-set";
		}
		
		set.setUser(user);
		set.setIsFree(1);
		
		setRepository.save(set);
		
		java.util.Set<String>  params = request.getParameterMap().keySet();
		
		for (String param : params) {
			
			if (param.startsWith("left_field_")) {
				int number = Integer.parseInt(param.substring(11));
				
				String srcWord = "";
				String targetWord = "";
				
				if (set.getTargetSide().equals("left")) {
					srcWord = request.getParameter("right_field_" + number);
					targetWord = request.getParameter("left_field_" + number);
				} else if (set.getTargetSide().equals("right")) {
					srcWord = request.getParameter("left_field_" + number);
					targetWord = request.getParameter("right_field_" + number);
				}
				
				if (srcWord.equals("") || targetWord.equals("")) continue;
				
				Word word = new Word(set, srcWord, targetWord);
				wordRepository.save(word);
			}
		}
		
		return "redirect:/free-sets";
	}
	
}
