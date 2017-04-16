package com.asia.promo.controller;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.asia.promo.mechanic.exception.InvalidInputException;
import com.asia.promo.mechanic.exception.UserNotFoundException;
import com.asia.promo.mechanic.interfaces.ITableQueryStatement;
import com.asia.promo.mechanic.interfaces.IUserController;
import com.asia.promo.mechanic.model.User;
import com.asia.promo.mechanic.model.UserAuthenticationResponse;
import com.asia.promo.mechanic.model.UserRegistrationResponse;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class UserController implements IUserController {

	private final ITableQueryStatement<User> databaseTable;

	@Inject
	public UserController(@Named("postgreUserImpl") ITableQueryStatement<User> databaseTable) {
		// TODO Auto-generated constructor stub
		this.databaseTable = databaseTable;
	}

	@Override
	public Response authenticate(String name, String pass) throws WebApplicationException {
		// TODO Auto-generated method stub
		if (Strings.isNullOrEmpty(name))
			throw new InvalidInputException("Username is empty.");

		if (Strings.isNullOrEmpty(pass))
			throw new InvalidInputException("Password is empty.");

		User user;
		boolean userAuthenticated = false;

		try {
			user = databaseTable.select("username", name);
			userAuthenticated = user.getPassword().equals(pass);

			UserAuthenticationResponse resp = new UserAuthenticationResponse();
			resp.setAuthenticated(userAuthenticated);
			resp.setUserId(user.getId());

			return Response.ok(resp).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new WebApplicationException(e);
		}
	}

	@Override
	public Response register(User user) throws WebApplicationException {
		if (user == null)
			throw new InvalidInputException("User object is not found.");

		User exist;
		try {
			exist = databaseTable.select("username", user.getUsername());
			if (exist != null) {
				throw new InvalidInputException("Username has already been taken.");
			}

			databaseTable.insert(user);

			User newUser = databaseTable.select("username", user.getUsername());
			if (newUser == null)
				throw new UserNotFoundException("User not found.");

			UserRegistrationResponse resp = new UserRegistrationResponse();
			resp.setRegistered(true);
			resp.setUserId(newUser.getId());

			return Response.ok(resp).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new WebApplicationException(e);
		}

	}

}
