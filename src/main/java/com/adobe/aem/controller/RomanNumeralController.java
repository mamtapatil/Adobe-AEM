package com.adobe.aem.controller;

import javax.annotation.Resource;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.adobe.aem.exceptions.OutOfRangeException;
import com.adobe.aem.service.RomanNumeralService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**.
* Controller to convert number to roman numeral 
* @author  Mamta Patil
* @version 1.0
*/

@RestController
public class RomanNumeralController {

	Logger logger = LoggerFactory.getLogger(RomanNumeralController.class);
	
	@Resource
	RomanNumeralService romanNumeralService;
	
	@GetMapping("/romannumeral")
	public ModelMap convertRomanNumber(@RequestParam(value = "query", required = true) int number) {
		ModelAndView mv = new ModelAndView();
		String output = romanNumeralService.convertToRomanNumber(number);	
		mv.getModel().put("input", number);
		mv.getModel().put("output", output);
		return mv.getModelMap();
	}
	
	@ExceptionHandler(value = OutOfRangeException.class)
    public ModelMap handleOutOfRangeException(OutOfRangeException outOfRangeException) {
		ModelAndView mv = new ModelAndView();
		mv.getModel().put("error", outOfRangeException.getMessage());
		logger.warn("Out of range exception occurred.");
		return mv.getModelMap();
    }
	
	@ExceptionHandler(value = Exception.class)
    public ModelMap handleeException(Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.getModel().put("error", "Invalid input. Please provide a number in the range 1 - 3999.");
		logger.warn("Exception occurred - invalid input");
		return mv.getModelMap();
    }
}
