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

import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.User;
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.UserRepository;
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
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/category-{id}", method=RequestMethod.GET)
	public String showSets(ModelMap model, @PathVariable(value="id") int categoryId) {
		model.put("categoryId", categoryId);
	
		Category category = categoryRepository.findById(categoryId).get();
		
		if (!category.getUser().getUsername().equals(Utils.getLoggedInUserName())) {
			return "forbidden";
		}
		
		List<Set> sets = setRepository.findByCategory(category);
		
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
		model.put("category", category);
		
		model.put("wordCounters", wordCounters);
		model.put("lastResults", lastResults);
		model.put("bestResults", bestResults);
		
		return "category-sets-list";
	}
	
	@RequestMapping(value = "/add-set-{id}", method = RequestMethod.GET)
	public String addSetShow(ModelMap model, HttpServletRequest request, 
							@PathVariable(value="id") int categoryId) {
		
		HttpSession session = request.getSession();
		session.setAttribute("hasErrorMode", false);
		
		Category category = categoryRepository.findById(categoryId).get();
		
		if (!category.getUser().getUsername().equals(Utils.getLoggedInUserName())) {
			return "forbidden";
		}
		
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

		Category category = categoryRepository.findById(categoryId).get();
		
		if (!category.getUser().getUsername().equals(Utils.getLoggedInUserName())) {
			return "forbidden";
		}
		
		List<Set> sets = setRepository.findByCategory(category);
		HttpSession session = request.getSession();
		
		for (Set setIter : sets) {
			if (setIter.getName().equals(set.getName())) {
				result.rejectValue("name", "error.name", "This set already exists");
			}
		}
		
		if (Utils.isSetEmpty(request)) {
			result.rejectValue("name", "error.name", "Set must contain at least one word");
		}
		
		// case when target language has changed
		if (set.getTargetLanguage().getId() != category.getDefaultTargetLanguage().getId()) {
			set.setSrcLanguage(category.getDefaultTargetLanguage());
		} else {
			// target language not changed
			set.setSrcLanguage(category.getDefaultSrcLanguage());
		}
		
		if (result.hasErrors()) {
			
			session.setAttribute("hasErrorMode", true);
			
			// keep cache data in ArrayList and pass to model
			List<List<String>> wordsList = new ArrayList<List<String>>();
			wordsList = getWordsList(request, set);
			model.put("words", wordsList);
			
			model.put("category", category);
			model.put("targetSide", set.getTargetSide());
			if (set.getTargetSide().equals("left")) {
				model.put("srcSide", "right");
			} else {
				model.put("srcSide", "left");
			}
			
			return "add-set";
		}
		

		set.setCategory(category);
		
		User user = userRepository.findByUsername(Utils.getLoggedInUserName()).get(0);
		set.setUser(user);
		set.setIsFree(0);
		
		setRepository.save(set);
		
		addWordsToDb(request, set);
		
		return "redirect:/category-" + categoryId;
	}
	
	@RequestMapping(value = "/preview-{setId}", method = RequestMethod.GET)
	public String wordsPreview(ModelMap model, @PathVariable(value="setId") int setId) {
		
		Set set = setRepository.findById(setId).get();
		
		if (!set.getUser().getUsername().equals(Utils.getLoggedInUserName())) {
			return "forbidden";
		}
		
		model.put("set", set);
		
		List<Word> words = wordRepository.findBySetOrderByIdAsc(set);
		model.put("words", words);
		
		return "words-preview";
	}
	
	@RequestMapping(value = "/remove-set-{setId}", method = RequestMethod.GET)
	public String removeSet(ModelMap model, @PathVariable(value="setId") int setId) {
		
		Set set = setRepository.findById(setId).get();
		
		if (!set.getUser().getUsername().equals(Utils.getLoggedInUserName())) {
			return "forbidden";
		}
		
		model.put("set", set);
		
		return "remove-set";
	}
	
	@RequestMapping(value = "/remove-set-{setId}", method = RequestMethod.POST)
	public String removeSetPost(HttpServletRequest request, 
									@PathVariable(value="setId") int setId) {
		
		java.util.Set<String> params = request.getParameterMap().keySet();
		Set set = setRepository.findById(setId).get();
		
		if (!set.getUser().getUsername().equals(Utils.getLoggedInUserName())) {
			return "forbidden";
		}
		
		if (params.contains("yes")) {
			
			List<Word> words = wordRepository.findBySet(set);
			for (Word word : words) {
				wordRepository.delete(word);
			}
			
			setRepository.delete(set);
		}
		
		if (set.getIsFree() == 1) {
			return "redirect:/free-sets";
		} else {
			return "redirect:/category-" + set.getCategory().getId();
		}
	}
	
	@RequestMapping(value = "update-set-{setId}", method = RequestMethod.GET)
	public String updateSetGet(HttpServletRequest request, ModelMap model, 
								@PathVariable(value = "setId") int setId) {
		
		Set set = setRepository.findById(setId).get();
		
		if (!set.getUser().getUsername().equals(Utils.getLoggedInUserName())) {
			return "forbidden";
		}
		
		List<Word> words = wordRepository.findBySetOrderByIdAsc(set);
		
		HttpSession session = request.getSession();
		session.setAttribute("hasErrorMode", false);
		
		model.put("size", words.size());
		model.put("words", words);
		model.addAttribute("set", set);
		
		session.setAttribute("currentSetName", set.getName());
		
		if (set.getTargetSide().equals("left")) {
			model.put("targetSide", "left");
			model.put("srcSide", "right");
		} else {
			model.put("srcSide", "left");
			model.put("targetSide", "right");
		}
		
		return "update-set";
	}
	
	@RequestMapping(value = "update-set-{setId}", method = RequestMethod.POST)
	public String updateSetPost(HttpServletRequest request, ModelMap model,  
			@Valid Set set, BindingResult result, @PathVariable(value = "setId") int setId) {
		
		Category category = setRepository.findById(setId).get().getCategory();
		
		if (!set.getUser().getUsername().equals(Utils.getLoggedInUserName())) {
			return "forbidden";
		}
		
		List<Set> sets = setRepository.findByCategory(category);
		
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
 		
		set.setCategory(category);
		
		// case when target language has changed
		if (set.getTargetLanguage().getId() != category.getDefaultTargetLanguage().getId()) {
			set.setSrcLanguage(category.getDefaultTargetLanguage());
		} else {
			// target language not changed
			set.setSrcLanguage(category.getDefaultSrcLanguage());
		}
		
		if (result.hasErrors()) {
			
			session.setAttribute("hasErrorMode", true);
			List<List<String>> wordsList = new ArrayList<List<String>>();
			wordsList = getWordsList(request, set);
			
			model.put("size", wordsList.size());
			model.put("words", wordsList);
			model.addAttribute("set", set);
			
			if (set.getTargetSide().equals("left")) {
				model.put("targetSide", "left");
				model.put("srcSide", "right");
			} else {
				model.put("srcSide", "left");
				model.put("targetSide", "right");
			}
			return "update-set";
		}
		
		setRepository.save(set);
		
		// clean up old records
		cleanUpWords(set);
		
		addWordsToDb(request, set);
		
		return "redirect:/category-" + category.getId();
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
