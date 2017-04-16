package com.asia.promo.mechanic.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.asia.promo.mechanic.resource.ErrorCode;

public class UserFoundException extends WebApplicationException {

	private static final long serialVersionUID = 2919543476844751100L;

	public UserFoundException(String message) {
		super(Response.status(Status.CONFLICT)
				.entity(new ErrorItem(
						ErrorCode.USER_FOUND_EXCEPTION.getCode(), 
						ErrorCode.USER_FOUND_EXCEPTION.name(), message))
				.type(MediaType.APPLICATION_JSON).build());
	}
}

