package com.intdict.interactivedictionary.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intdict.interactivedictionary.model.Category;
import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.CategoryRepository;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.service.WordRepository;
import com.intdict.interactivedictionary.utils.Utils;

@Controller
public class ExamController {

	@Autowired
	SetRepository setRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	WordRepository wordRepository;
	
	@RequestMapping(value = "/exam-{setId}", method=RequestMethod.GET)
	public String examInit(HttpServletRequest request, ModelMap model, @PathVariable(value="setId") int setId) {
		
		HttpSession session = request.getSession();
		
		Set set = setRepository.findById(setId).get();
		session.setAttribute("set", set);
		
		if (set.getIsFree() == 0) {
			Category category = categoryRepository.findById(set.getCategory().getId()).get();
			session.setAttribute("category", category);
		}
		
		List<Word> words = wordRepository.findBySetOrderByIdAsc(set);
		int size = words.size();
		session.setAttribute("words", words);
		session.setAttribute("size", size);
		
		ArrayList<Integer> shuffledIdxs = Utils.createShuffleList(size);
		session.setAttribute("shuffledIdxs", shuffledIdxs);
		
		session.setAttribute("currWordIdx", 0);
		session.setAttribute("currWordIdxToShow", 1);
		session.setAttribute("corrAnsNum", 0);
		
		Word wordsToShow = words.get(shuffledIdxs.get(0));
		session.setAttribute("currCorrAnswer", wordsToShow.getTargetWord());
		
		model.put("srcWord", wordsToShow.getSrcWord());
		
		ArrayList<Integer> answersList = Utils.initAnswersList(size);
		session.setAttribute("answersList", answersList);
		
		session.setAttribute("setId", setId);
		session.setAttribute("isCheckClicked", false);
		
		session.setAttribute("countdownDuration", set.getCountdownDuration());
		
		return "exam";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="exam-summary", method=RequestMethod.GET)
	public String examSummary(HttpServletRequest request, ModelMap model) {
		
		HttpSession session = request.getSession();
		
		Set set = setRepository.findById((int) session.getAttribute("setId")).get();
		
		model.put("lastResult", set.getLastResult());
		model.put("bestResult", set.getBestResult());
		model.put("srcLanguage", set.getSrcLanguage());
		model.put("targetLanguage", set.getTargetLanguage());
		
		ArrayList<Integer> answersList = (ArrayList<Integer>) session.getAttribute("answersList");
		
		List<Word> words = (List<Word>) session.getAttribute("words");
		
		ArrayList<String> srcWords = new ArrayList<>();
		ArrayList<String> targetWords = new ArrayList<>();
		
		for (Word word : words) {
			srcWords.add(word.getSrcWord());
			targetWords.add(word.getTargetWord());
		}
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		String srcWordsJson = gson.toJson(srcWords);
		String targetWordsJson = gson.toJson(targetWords);	
		
		model.put("srcWords", srcWordsJson);
		model.put("targetWords", targetWordsJson);
		
		return "exam-summary";
	}
	
}
