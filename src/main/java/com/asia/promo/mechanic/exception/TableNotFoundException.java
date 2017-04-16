package com.asia.promo.mechanic.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.asia.promo.mechanic.resource.ErrorCode;

public class TableNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 4920148589861534045L;

	public TableNotFoundException(String message) {
		super(Response.status(Status.NOT_FOUND)
				.entity(new ErrorItem(
						ErrorCode.TABLE_NOT_FOUND_EXCEPTION.getCode(), 
						ErrorCode.TABLE_NOT_FOUND_EXCEPTION.name(), message))
				.type(MediaType.APPLICATION_JSON).build());
	}
}
