package com.asia.promo.mechanic.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.asia.promo.mechanic.resource.ErrorCode;

public class UserNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 2003705608664942614L;

	public UserNotFoundException(String message) {
		super(Response.status(Status.NOT_FOUND)
				.entity(new ErrorItem(
						ErrorCode.USER_NOT_FOUND_EXCEPTION.getCode(), 
						ErrorCode.USER_NOT_FOUND_EXCEPTION.name(), message))
				.type(MediaType.APPLICATION_JSON).build());
	}
}