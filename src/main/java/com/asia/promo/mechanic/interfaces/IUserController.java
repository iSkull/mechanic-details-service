package com.asia.promo.mechanic.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asia.promo.mechanic.model.User;

@Path("/mechanic")
@Produces(MediaType.APPLICATION_JSON)
public interface IUserController {

	@GET
	@Path("/authenticate")
	Response authenticate(@QueryParam("name") String name, @QueryParam("pass") String pass)
			throws WebApplicationException;

	@POST
	@Path("/register")
	Response register(User user) throws WebApplicationException;
}
