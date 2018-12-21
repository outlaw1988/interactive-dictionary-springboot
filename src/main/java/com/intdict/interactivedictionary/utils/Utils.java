package com.intdict.interactivedictionary.utils;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Utils {

	public static boolean isSetEmpty(HttpServletRequest request) {
		
		boolean isEmpty = true;
		
		java.util.Set<String>  params = request.getParameterMap().keySet();
		
		for (String param : params) {
			
			if (param.startsWith("left_field_")) {
				int number = Integer.parseInt(param.substring(11));
				String word1 = request.getParameter("left_field_" + number);
				String word2 = request.getParameter("right_field_" + number);
				
				if (!word1.equals("") || !word2.equals("")) {
					isEmpty = false;
				}
			}
		}
		
		return isEmpty;
	}
	
	public static ArrayList<Integer> createShuffleList(int size) {
		
		ArrayList<Integer> outArr = new ArrayList<>();
		
		for (int i = 0; i < size; i++) {
			outArr.add(i);
		}
		
		Collections.shuffle(outArr);
		return outArr;
	}
	
	public static ArrayList<Integer> initAnswersList(int size) {
		
		ArrayList<Integer> outArr = new ArrayList<>();
		
		for (int i = 0; i < size; i++) {
			outArr.add(-1);
		}
		
		return outArr;
	}
	
	public static ArrayList<Integer> assignValueToAnswersList(int idx, int value, 
			ArrayList<Integer> answersList) {
		
		answersList.set(idx, value);
		return answersList;
	}

	public static String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();

		return principal.toString();
	}

}
