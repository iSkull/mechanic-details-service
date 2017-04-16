package com.asia.promo.mechanic.config.database;

import com.asia.promo.mechanic.interfaces.IMongoConfiguration;

import lombok.Getter;

@Getter
public class MongoConfiguration implements IMongoConfiguration {
	private String url;
	private String database;
	private String table;

	public MongoConfiguration(String url, String database, String table) {
		this.database = database;
		this.url = url;
		this.table = table;
	}
}
