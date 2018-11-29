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
import org.springframework.web.bind.annotation.PathVariable;
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
	public String addFreeSetGet(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		session.setAttribute("hasErrorMode", false);
		
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
		HttpSession session = request.getSession();
		
		for (Set setIter : sets) {
			if (setIter.getName().equals(set.getName())) {
				result.rejectValue("name", "error.name", "This set already exists");
			}
		}
		
		if (Utils.isSetEmpty(request)) {
			result.rejectValue("name", "error.name", "Set must contain at least one word");
		}
		
		if (set.getSrcLanguage() == null) {
			result.rejectValue("srcLanguage", "error.srcLanguage", "must not be null");
		}
		
		if (set.getSrcLanguage() != null && set.getTargetLanguage() != null) {
			if (set.getSrcLanguage().equals(set.getTargetLanguage())) {
				result.rejectValue("targetLanguage", "error.targetLanguage", "Languages must be different");
			}
		}
				
		if (result.hasErrors()) {
					
			session.setAttribute("hasErrorMode", true);
			
			// keep cache data in ArrayList and pass to model
			List<List<String>> wordsList = new ArrayList<List<String>>();
			wordsList = getWordsList(request, set);
			model.put("words", wordsList);
			
			// TODO change
			if (set.getTargetSide().equals("left")) {
				model.put("targetSide", "left");
				model.put("srcSide", "right");
			} else {
				model.put("srcSide", "left");
				model.put("targetSide", "right");
			}
			
			List<Language> languages = languageRepository.findByUser(user);
			
			model.put("languages", languages);
			
			return "add-free-set";
		}
		
		set.setUser(user);
		set.setIsFree(1);
		
		setRepository.save(set);
		
		addWordsToDb(request, set);
		
		return "redirect:/free-sets";
	}
	
	@RequestMapping(value = "update-free-set-{setId}", method = RequestMethod.GET)
	public String updateFreeSetGet(HttpServletRequest request, ModelMap model, 
								@PathVariable(value = "setId") int setId) {
		Set set = setRepository.findById(setId).get();
		List<Word> words = wordRepository.findBySetOrderByIdAsc(set);
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName(model)).get(0);
		List<Language> languages = languageRepository.findByUser(user);
		model.put("languages", languages);
		
		model.put("size", words.size());
		model.put("words", words);
		model.addAttribute("set", set);
		
		HttpSession session = request.getSession();
		session.setAttribute("currentSetName", set.getName());
		
		if (set.getTargetSide().equals("left")) {
			model.put("targetSide", "left");
			model.put("srcSide", "right");
		} else {
			model.put("srcSide", "left");
			model.put("targetSide", "right");
		}
		
		return "update-free-set";
	}
	
	@RequestMapping(value = "update-free-set-{setId}", method = RequestMethod.POST)
	public String updateFreeSetPost(HttpServletRequest request, ModelMap model,  
			@Valid Set set, BindingResult result, @PathVariable(value = "setId") int setId) {
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName(model)).get(0);
		List<Set> sets = setRepository.findByUserAndIsFree(user, 1);
		
		HttpSession session = request.getSession();
		String currentSetName = (String) session.getAttribute("currentSetName");
		
		// Prevents set name duplication, but allows to edit set
		for (Set setIter : sets) {
			if (setIter.getName().equals(set.getName())) {
				if (!set.getName().equals(currentSetName)) {
					result.rejectValue("name", "error.name", "This set already exists");
				}
			}
		}
		
		if (Utils.isSetEmpty(request)) {
			result.rejectValue("name", "error.name", "Set must contain at least one word");
		}
		
		if (set.getSrcLanguage() == null) {
			result.rejectValue("srcLanguage", "error.srcLanguage", "must not be null");
		}
		
		if (set.getSrcLanguage() != null && set.getTargetLanguage() != null) {
			if (set.getSrcLanguage().equals(set.getTargetLanguage())) {
				result.rejectValue("targetLanguage", "error.targetLanguage", "Languages must be different");
			}
		}
		
		if (result.hasErrors()) {
			
			List<Word> words = wordRepository.findBySetOrderByIdAsc(set);
			
			List<Language> languages = languageRepository.findByUser(user);
			model.put("languages", languages);
			
			model.put("size", words.size());
			model.put("words", words);
			model.addAttribute("set", set);
			
			if (set.getTargetSide().equals("left")) {
				model.put("targetSide", "left");
				model.put("srcSide", "right");
			} else {
				model.put("srcSide", "left");
				model.put("targetSide", "right");
			}
			
			return "update-free-set";
		}
		
		set.setUser(user);
		set.setIsFree(1);
		
		setRepository.save(set);
		
		// clean up old records
		cleanUpWords(set);
		
		addWordsToDb(request, set);
		
		return "redirect:/free-sets";
	}
	
	///////////////////// AUXILIARY METHODS \\\\\\\\\\\\\\\\\\\\
	
	public void addWordsToDb(HttpServletRequest request, Set set) {
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
		
	}
	
	public List<List<String>> getWordsList(HttpServletRequest request, Set set) {
		java.util.Set<String>  params = request.getParameterMap().keySet();
		
		List<List<String>> result = new ArrayList<List<String>>();
		
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
				
				List<String> arr = new ArrayList<String>();
				arr.add(srcWord);
				arr.add(targetWord);
				result.add(arr);
			}
		}
		
		return result;
	}
	
	public void cleanUpWords(Set set) {
		List<Word> words = wordRepository.findBySet(set);
		for (Word word : words) {
			wordRepository.delete(word);
		} 
	}
		
	
}
