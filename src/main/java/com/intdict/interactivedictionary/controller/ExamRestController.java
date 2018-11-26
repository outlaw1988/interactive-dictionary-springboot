package com.intdict.interactivedictionary.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intdict.interactivedictionary.model.Set;
import com.intdict.interactivedictionary.model.Word;
import com.intdict.interactivedictionary.service.SetRepository;
import com.intdict.interactivedictionary.utils.Utils;

@RestController
public class ExamRestController {

	@Autowired
	SetRepository setRepository;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exam-check", method = RequestMethod.POST)
	public Response examCheck(HttpServletRequest request, @RequestBody Answer answer) {

		System.out.println("Exam check called!!!!");
		
		HttpSession session = request.getSession();
		session.setAttribute("isCheckClicked", true);
		
		ArrayList<Integer> shuffledIdxes = (ArrayList<Integer>) session.getAttribute("shuffledIdxs");
		
		int currWordIdx = (int) session.getAttribute("currWordIdx");
		int shuffledIdx = shuffledIdxes.get(currWordIdx);
		
		Response response = new Response();
		String currCorrAnswer = (String) session.getAttribute("currCorrAnswer");
		
		System.out.println("Current correct answer: " + currCorrAnswer);
		
		ArrayList<Integer> answersList = (ArrayList<Integer>) session.getAttribute("answersList");
		
		if (answer.answer.equals(currCorrAnswer)) {
			
			response.setMessage("OK");
			
			int corrAnsNum = (int) session.getAttribute("corrAnsNum") + 1;
			System.out.println("Correct answers num: " + corrAnsNum);
			session.setAttribute("corrAnsNum", corrAnsNum);
			answersList = Utils.assignValueToAnswersList(shuffledIdx, 1, answersList);
			session.setAttribute("answersList", answersList);
			
		} else {
			
			response.setMessage("WRONG, right answer is: " + currCorrAnswer);
			
			answersList = Utils.assignValueToAnswersList(shuffledIdx, 0, answersList);
			session.setAttribute("answersList", answersList);
		}

		// TODO json request deserialization without model
//		JSONObject jsonObj = new JSONObject(jsonString);
//		String answer = jsonObj.getString("answer");
		
//		System.out.println("Provided answer is: " + answer);
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exam-next", method = RequestMethod.POST)
	public ResponseNextWord examNext(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		// BEFORE - check not clicked
		int currWordIdx = (int) session.getAttribute("currWordIdx");
		
		System.out.println("Current word idx BEFORE: " + currWordIdx);
		
		ArrayList<Integer> shuffledIdxes = (ArrayList<Integer>) session.getAttribute("shuffledIdxs");
		int shuffledIdxBefore = shuffledIdxes.get(currWordIdx);
		
		if ((boolean)session.getAttribute("isCheckClicked") == false) {
			System.out.println("Giving zero for only next clicked...");
			ArrayList<Integer> answersList = (ArrayList<Integer>) session.getAttribute("answersList");
			answersList = Utils.assignValueToAnswersList(shuffledIdxBefore, 0, answersList);
			
			System.out.println("Answers list: " + answersList);
			
			session.setAttribute("answersList", answersList);
		}
		
		System.out.println("Check clicked? " + (boolean)session.getAttribute("isCheckClicked"));
		session.setAttribute("isCheckClicked", false);
		
		/////////////////////////////
		
		currWordIdx += 1;
		session.setAttribute("currWordIdx", currWordIdx);
		System.out.println("Current word index: " + currWordIdx);
		
		int size = (int) session.getAttribute("size");
		System.out.println("Size: " + size);
		
		List<Word> words = (List<Word>) session.getAttribute("words");
		
		if (currWordIdx < size) {
			int shuffledIdx = shuffledIdxes.get(currWordIdx);
			System.out.println("Shuffled index: " + shuffledIdx);
			
			ResponseNextWord response = new ResponseNextWord();
			Word wordsToShow = words.get(shuffledIdx);
			response.srcWord = wordsToShow.getSrcWord();
			
			session.setAttribute("currCorrAnswer", wordsToShow.getTargetWord());
			response.wordIdxToShow = (int)session.getAttribute("currWordIdxToShow") + 1; // wordIdxToShow is greater by 1
			session.setAttribute("currWordIdxToShow", (int)session.getAttribute("currWordIdxToShow") + 1);
		
			return response;
			
		} else if (currWordIdx == size) {
			
			System.out.println("Last word called... currWordIdx: " + currWordIdx);
			
			int corrAnsNum = (int) session.getAttribute("corrAnsNum");
			int result = (int)(((float) corrAnsNum / (float) size) * 100.0);
			
			//System.out.println("Result is: " + result);
			
			Set set = setRepository.findById((int) session.getAttribute("setId")).get();
			
			if (result > set.getBestResult()) {
				set.setBestResult(result);
				session.setAttribute("bestResult", result);
			}
			
			set.setLastResult(result);
			session.setAttribute("lastResult", result);
			
			setRepository.save(set);
			
			// dummy
			ResponseNextWord response = new ResponseNextWord();
			response.srcWord = "";
			response.wordIdxToShow = -1;
			
			return response;
		}
		
		return new ResponseNextWord();
	}
}

// Auxiliary classes
class Answer {
	public String answer;
}


class Response {
	public String message;
	
	public void setMessage(String message) {
		this.message = message;
	}
}


class ResponseNextWord {
	
	public String srcWord;
	public int wordIdxToShow;
	
	public ResponseNextWord() {
		this.srcWord = "";
		this.wordIdxToShow = -1;
	}
}
	
