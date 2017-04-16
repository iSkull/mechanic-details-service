package com.asia.promo.mechanic.config.database;

import com.asia.promo.mechanic.interfaces.IDatabaseConfiguration;

import lombok.Getter;

@Getter
public class PostgreConfiguration implements IDatabaseConfiguration {
	private String username;
	private String password;
	private String database;
	private String driver;
	private String url;
	private String table;

	public PostgreConfiguration(String driver,String url,String username,String password,String database,String table) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.database = database;
		this.table = table;
	}
}
