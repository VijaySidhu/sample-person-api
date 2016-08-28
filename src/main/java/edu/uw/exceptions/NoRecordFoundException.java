package edu.uw.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Record found")
public class NoRecordFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7487103496690639994L;

}
