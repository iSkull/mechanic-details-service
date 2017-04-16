package com.asia.promo.mechanic;

import com.asia.promo.mechanic.config.MainConfiguration;
import com.asia.promo.mechanic.interfaces.IUserController;
import com.asia.promo.mechanic.interfaces.IWorkshopServiceController;
import com.asia.promo.mechanic.module.MainModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class Main extends Application<MainConfiguration> {

	private final IUserController userController;
	private final IWorkshopServiceController workshopServiceController;

	@Inject
	public Main(IUserController userController, IWorkshopServiceController workshopServiceController) {
		this.userController = userController;
		this.workshopServiceController = workshopServiceController;
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Injector injector = Guice.createInjector(new MainModule());
		Main main = injector.getInstance(Main.class);
		try {
			String[] arguments = new String[2];
			arguments[0] = "server";
			arguments[1] = "config.yml";
			main.run(arguments);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run(MainConfiguration arg0, Environment arg1) throws Exception {
		// TODO Auto-generated method stub
		arg1.jersey().register(userController);
		arg1.jersey().register(workshopServiceController);
	}
}
