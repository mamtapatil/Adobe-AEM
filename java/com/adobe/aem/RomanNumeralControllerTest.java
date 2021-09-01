package com.adobe.aem;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.adobe.aem.controller.RomanNumeralController;
import com.adobe.aem.exceptions.OutOfRangeException;
import com.adobe.aem.service.RomanNumeralService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RomanNumeralController.class)
public class RomanNumeralControllerTest {

	public static final int MIN_RANGE = 1;

	public static final int MAX_RANGE = 3999;
	
	@Autowired
    MockMvc mockMvc;
	
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
	private RomanNumeralService romanNumeralService;
    
	@Test
	public void romanNumeralTestI() throws Exception {
		when(romanNumeralService.convertToRomanNumber(1)).thenReturn("I");
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/romannumeral?query=1")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.output").value("I"));
	}
	
	@Test
	public void romanNumeralTestII() throws Exception {
		when(romanNumeralService.convertToRomanNumber(3999)).thenReturn("MMMCMXCIX");
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/romannumeral?query=3999")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.output").value("MMMCMXCIX"));
	}
	
	@Test
	public void romanNumeralTestIII() throws Exception {
		when(romanNumeralService.convertToRomanNumber(100)).thenReturn("C");
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/romannumeral?query=100")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.output").value("C"));
	}
	
	@Test
	public void romanNumeralTestIV() throws Exception {
		when(romanNumeralService.convertToRomanNumber(450)).thenReturn("CDL");
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/romannumeral?query=450")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.output").value("CDL"));
	}
	
	@Test
	public void outOfRangeException() throws Exception {
		when(romanNumeralService.convertToRomanNumber(4000)).thenThrow(new OutOfRangeException("Only numbers within the range " + MIN_RANGE + " - " + MAX_RANGE + " are allowed"));
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/romannumeral?query=4000")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(result -> assertTrue(result.getResolvedException() instanceof OutOfRangeException))
	            .andExpect(jsonPath("$.error").value("Only numbers within the range " + MIN_RANGE + " - " + MAX_RANGE + " are allowed"));
	}
	
	@Test
	public void invalidInputAlphabetsException() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/romannumeral?query=asd")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
	            .andExpect(jsonPath("$.error").value("Invalid input. Please provide a number in the range 1 - 3999."));
	}
	
	@Test
	public void invalidInputLongException() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
	            .get("/romannumeral?query=12345678934648484")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
	            .andExpect(jsonPath("$.error").value("Invalid input. Please provide a number in the range 1 - 3999."));
	}
}