package com.intdict.interactivedictionary.utils;

import java.util.Set;

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
}
