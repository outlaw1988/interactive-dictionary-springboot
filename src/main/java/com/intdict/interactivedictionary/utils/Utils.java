package com.intdict.interactivedictionary.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;

public class Utils {

	public static int findHighestWordIdxFromRequest(Set<String> keys) {
		
		int highestIdx = 0;
		
		for (String key : keys) {
			
			if (key.startsWith("left_field_")) {
				int number = Integer.parseInt(key.substring(11));
				
				if (number > highestIdx) highestIdx = number;
			}
		}
		
		return highestIdx;
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

	public static String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();

		return principal.toString();
	}

}
