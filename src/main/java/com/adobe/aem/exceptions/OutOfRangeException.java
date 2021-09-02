package com.adobe.aem.exceptions;

/**
 * Custom Exception to handle out of range input
 * @author Mamta Patil
 * @version 1.0
 */

public class OutOfRangeException extends RuntimeException{
	
	public OutOfRangeException(String message) {
		super(message);
	}
}
