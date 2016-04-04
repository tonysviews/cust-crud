package com.tonysviews.cust_crud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception being used when a request to fetch a non-existing
 * customer is done.
 * 
 * @author Tony
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Customer not found.")
public class InvalidCustomerException extends RuntimeException {
	private static final long serialVersionUID = -5688702501118644308L;

}
