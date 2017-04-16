package com.asia.promo.mechanic.interfaces;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asia.promo.mechanic.model.WorkshopService;

@Path("/mechanic/workshop/services")
@Produces(MediaType.APPLICATION_JSON)
public interface IWorkshopServiceController {

	@GET
	@Path("/service")
	Response get(@QueryParam("id") String id) throws WebApplicationException;

	@POST
	Response create(WorkshopService workshopService) throws WebApplicationException;

	@PUT
	@Path("/service")
	Response update(@QueryParam("id") String id, WorkshopService workshopService) throws WebApplicationException;

	@DELETE
	@Path("/service")
	Response delete(@QueryParam("id") String id) throws WebApplicationException;

	@GET
	Response getAll(@QueryParam("userId") String userId) throws WebApplicationException;
}
