package com.asia.promo.controller;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.asia.promo.mechanic.exception.InvalidInputException;
import com.asia.promo.mechanic.interfaces.ITableNonQueryStatement;
import com.asia.promo.mechanic.interfaces.IWorkshopServiceController;
import com.asia.promo.mechanic.model.WorkshopDeleteResponse;
import com.asia.promo.mechanic.model.WorkshopPostResponse;
import com.asia.promo.mechanic.model.WorkshopPutResponse;
import com.asia.promo.mechanic.model.WorkshopService;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class WorkshopServiceController implements IWorkshopServiceController {

	private final ITableNonQueryStatement<WorkshopService> databaseTable;

	@Inject
	public WorkshopServiceController(
			@Named("mongoWorkshopImpl") ITableNonQueryStatement<WorkshopService> databaseTable) {
		// TODO Auto-generated constructor stub
		this.databaseTable = databaseTable;
	}

	@Override
	public Response get(String id) throws WebApplicationException {
		if (Strings.isNullOrEmpty(id))
			throw new InvalidInputException("Invalid identity.");

		WorkshopService resp;
		try {
			resp = databaseTable.get(id);
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}

		// TODO Auto-generated method stub
		return Response.ok(resp).build();
	}

	@Override
	public Response create(WorkshopService workshopService) throws WebApplicationException {
		if(workshopService == null)
			throw new InvalidInputException("Invalid entry. No object found.");
		// TODO Auto-generated method stub
		WorkshopPostResponse resp = new WorkshopPostResponse();
		resp.setSuccess(true);

		try {
			databaseTable.create(workshopService);
		} catch (Exception e) {
			resp.setSuccess(false);
		}

		return Response.ok(resp).build();
	}

	@Override
	public Response update(String id, WorkshopService workshopService) throws WebApplicationException {
		if (Strings.isNullOrEmpty(id))
			throw new InvalidInputException("Invalid identity.");

		if (workshopService == null)
			throw new InvalidInputException("Invalid entry. No object found.");

		WorkshopPutResponse resp = new WorkshopPutResponse();
		resp.setSuccess(true);

		try {
			databaseTable.update(id, workshopService);
		} catch (Exception e) {
			resp.setSuccess(false);
		}

		return Response.ok(resp).build();
	}

	@Override
	public Response delete(String id) throws WebApplicationException {
		if (Strings.isNullOrEmpty(id))
			throw new InvalidInputException("Invalid identity.");
		// TODO Auto-generated method stub
		
		WorkshopDeleteResponse resp = new WorkshopDeleteResponse();
		resp.setSuccess(true);
		
		try {
			databaseTable.delete(id);
		} catch (Exception e) {
			resp.setSuccess(false);
		}
		
		return Response.ok(resp).build();
	}

	@Override
	public Response getAll(String userId) throws WebApplicationException {
		if (Strings.isNullOrEmpty(userId))
			throw new InvalidInputException("Invalid identity.");
		// TODO Auto-generated method stub
		List<WorkshopService> resp = null;
		try {
			resp = databaseTable.getAll("userId", userId);
		} catch (Exception e) {

		}
		
		return Response.ok(resp).build();
	}

}
