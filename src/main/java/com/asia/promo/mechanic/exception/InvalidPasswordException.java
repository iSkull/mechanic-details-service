package com.asia.promo.mechanic.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.asia.promo.mechanic.resource.ErrorCode;

public class InvalidPasswordException extends WebApplicationException {

	private static final long serialVersionUID = -7713240062714506685L;

	public InvalidPasswordException(String message) {
		super(Response.status(Status.BAD_REQUEST)
				.entity(new ErrorItem(
						ErrorCode.INVALID_PASSWORD_EXCEPTION.getCode(), 
						ErrorCode.INVALID_PASSWORD_EXCEPTION.name(), message))
				.type(MediaType.APPLICATION_JSON).build());
	}
}
