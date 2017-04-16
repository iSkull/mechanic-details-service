package com.asia.promo.mechanic.impl;

import java.util.ArrayList;
import java.util.List;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import com.asia.promo.mechanic.exception.InvalidInputException;
import com.asia.promo.mechanic.exception.TableNotFoundException;
import com.asia.promo.mechanic.interfaces.IMongoConfiguration;
import com.asia.promo.mechanic.interfaces.ITableNonQueryStatement;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoTableNonQueryImpl<T> implements ITableNonQueryStatement<T> {

	private final Class<T> clazz;
	private final IMongoConfiguration mongoConfig;

	private DBCollection collection;
	private JacksonDBCollection<T, String> jacksonCollecton;

	@Inject
	@SuppressWarnings("unchecked")
	public MongoTableNonQueryImpl(IMongoConfiguration mongoConfig, TypeLiteral<T> type) {
		this.mongoConfig = mongoConfig;
		this.clazz = (Class<T>) type.getRawType();

		initMongo();
	}

	@SuppressWarnings({ "deprecation", "resource" })
	private void initMongo() {
		if (Strings.isNullOrEmpty(mongoConfig.getTable()))
			throw new TableNotFoundException("Table is empty.");

		// To connect to mongodb server
		MongoClient mongoClient = new MongoClient(mongoConfig.getUrl(), 27017);

		// Now connect to your databases
		DB db = mongoClient.getDB(mongoConfig.getDatabase());

		collection = db.getCollection(mongoConfig.getTable());

		jacksonCollecton = JacksonDBCollection.wrap(collection, clazz, String.class);
	}

	@Override
	public T get(String id) throws Exception {
		if (Strings.isNullOrEmpty(id))
			throw new InvalidInputException("Invalid identity.");

		// TODO Auto-generated method stub
		T obj = jacksonCollecton.findOneById(id);

		return obj;
	}

	@Override
	public void create(T obj) throws Exception {
		// TODO Auto-generated method stub
		if (obj == null)
			throw new InvalidInputException("Object is empty.");

		jacksonCollecton.insert(obj);
	}

	@Override
	public void update(String id, T obj) throws Exception {
		// TODO Auto-generated method stub
		if (Strings.isNullOrEmpty(id))
			throw new InvalidInputException("Invalid identity.");

		if (obj == null)
			throw new InvalidInputException("Object is empty.");

		jacksonCollecton.updateById(id, obj);
	}

	@Override
	public void delete(String id) throws Exception {
		if (Strings.isNullOrEmpty(id))
			throw new InvalidInputException("Invalid identity.");
		// TODO Auto-generated method stub
		jacksonCollecton.removeById(id);
	}

	@Override
	public List<T> getAll(String prop, String value) throws Exception {
		// TODO Auto-generated method stub

		DBCursor<T> cursor = jacksonCollecton.find().is(prop, value);
		cursor.sort(new BasicDBObject("_id", -1));
		List<T> items = new ArrayList<T>();
		while (cursor.hasNext()) {
			items.add(cursor.next());
		}

		return items;
	}
}
