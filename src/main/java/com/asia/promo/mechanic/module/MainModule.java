package com.asia.promo.mechanic.module;

import java.io.IOException;
import java.util.Properties;

import com.asia.promo.controller.UserController;
import com.asia.promo.controller.WorkshopServiceController;
import com.asia.promo.mechanic.config.database.MongoConfiguration;
import com.asia.promo.mechanic.config.database.PostgreConfiguration;
import com.asia.promo.mechanic.impl.MongoTableNonQueryImpl;
import com.asia.promo.mechanic.impl.PostgreTableQueryImpl;
import com.asia.promo.mechanic.interfaces.IDatabaseConfiguration;
import com.asia.promo.mechanic.interfaces.IMongoConfiguration;
import com.asia.promo.mechanic.interfaces.ITableNonQueryStatement;
import com.asia.promo.mechanic.interfaces.ITableQueryStatement;
import com.asia.promo.mechanic.interfaces.IUserController;
import com.asia.promo.mechanic.interfaces.IWorkshopServiceController;
import com.asia.promo.mechanic.model.User;
import com.asia.promo.mechanic.model.WorkshopService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		Names.bindProperties(binder(), loadProperties("mongoConfig.properties"));
		Names.bindProperties(binder(), loadProperties("dbConfig.properties"));
		 
		bind(new TypeLiteral<ITableQueryStatement<User>>() {})
			.annotatedWith(Names.named("postgreUserImpl"))
			.to(new TypeLiteral<PostgreTableQueryImpl<User>>() {});
		
		bind(new TypeLiteral<ITableNonQueryStatement<WorkshopService>>() {})
			.annotatedWith(Names.named("mongoWorkshopImpl"))
			.to(new TypeLiteral<MongoTableNonQueryImpl<WorkshopService>>() {});
		
		bind(IUserController.class).to(UserController.class);
		bind(IWorkshopServiceController.class).to(WorkshopServiceController.class);
	}

	@Provides
	public IDatabaseConfiguration getPostgreConfiguration(
			@Named("dbDriver") String driver,
			@Named("dbUrl") String url,
			@Named("dbUsername") String username,
			@Named("dbPassword") String password,
			@Named("dbDatabase") String database,
			@Named("dbTable") String table) {
		PostgreConfiguration conf = new PostgreConfiguration(driver, url, username, password, database, table);
		return conf;
	}
	
	@Provides
	public IMongoConfiguration getMongoConfiguration(
			@Named("mongoUrl") String url,
			@Named("mongoDatabase") String database,
			@Named("mongoTable") String table) {
		MongoConfiguration conf = new MongoConfiguration(url, database, table);
		return conf;
	}

	private Properties loadProperties(String propFile) {
		Properties prop = new Properties();
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream(propFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
}
