package com.asia.promo.mechanic.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.asia.promo.mechanic.resource.ErrorCode;

public class InvalidInputException extends WebApplicationException {

	private static final long serialVersionUID = 96714939341728437L;

	public InvalidInputException(String message) {
		super(Response.status(Status.BAD_REQUEST)
				.entity(new ErrorItem(
						ErrorCode.INVALID_INPUT_PARAMETER.getCode(), 
						ErrorCode.INVALID_INPUT_PARAMETER.name(), message))
				.type(MediaType.APPLICATION_JSON).build());
	}
}
