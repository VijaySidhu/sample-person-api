package edu.uw.exceptions;

public class PersonServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7097357760699416937L;

	public PersonServiceException() {

	}

	public PersonServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
