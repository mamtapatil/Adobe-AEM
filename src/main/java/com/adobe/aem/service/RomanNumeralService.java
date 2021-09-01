package com.adobe.aem.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.adobe.aem.controller.RomanNumeralController;
import com.adobe.aem.exceptions.OutOfRangeException;

@Service
public class RomanNumeralService {
	
	public static final int MIN_RANGE = 1;

	public static final int MAX_RANGE = 3999;
	
	public static final Map<Integer,String> romanNumberMap = new LinkedHashMap<Integer,String>();
	
	Logger logger = LoggerFactory.getLogger(RomanNumeralService.class);
	
	static {
		romanNumberMap.put(1000, "M");
		romanNumberMap.put(900, "CM");
		romanNumberMap.put(500, "D");
		romanNumberMap.put(400, "CD");
		romanNumberMap.put(100, "C");
		romanNumberMap.put(90, "XC");
		romanNumberMap.put(50, "L");
		romanNumberMap.put(40, "XL");
		romanNumberMap.put(10, "X");
		romanNumberMap.put(9, "IX");
		romanNumberMap.put(5, "V");
		romanNumberMap.put(4, "IV");
		romanNumberMap.put(1, "I");
	}
	
	public String convertToRomanNumber(int number) {
		StringBuilder output = new StringBuilder();
		logger.info("Converting " + number + " to roman numeral.");
		if (number < MIN_RANGE || number > MAX_RANGE) {
			throw new OutOfRangeException("Only numbers within the range " + MIN_RANGE + " - " + MAX_RANGE + " are allowed");
		}
		
		for (int key : romanNumberMap.keySet()) {
			while (number >= key) {
				number -= key;
				output.append(romanNumberMap.get(key));
			}
			if (number <= 0) {
				break;
			}
		}
		
		return output.toString();
	}
}
