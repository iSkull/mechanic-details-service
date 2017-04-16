package com.asia.promo.mechanic.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.NotSupportedException;

import com.asia.promo.mechanic.exception.InvalidInputException;
import com.asia.promo.mechanic.exception.TableNotFoundException;
import com.asia.promo.mechanic.interfaces.IDatabaseConfiguration;
import com.asia.promo.mechanic.interfaces.ITableQueryStatement;
import com.asia.promo.mechanic.resource.Literal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

@SuppressWarnings("unchecked")
public class PostgreTableQueryImpl<T> implements ITableQueryStatement<T> {
	private final ObjectMapper mapper = new ObjectMapper();

	private Connection connection;
	private Statement statement;

	private final Class<T> clazz;
	private final IDatabaseConfiguration databaseConfig;

	@Inject
	public PostgreTableQueryImpl(IDatabaseConfiguration databaseConfig, TypeLiteral<T> type) throws Exception {
		this.databaseConfig = databaseConfig;
		this.clazz = (Class<T>) type.getRawType();

		initConnection();
	}

	@Override
	public T select(String column, String value) throws Exception {
		if (Strings.isNullOrEmpty(databaseConfig.getTable()))
			throw new TableNotFoundException("Table is empty.");

		if (Strings.isNullOrEmpty(column))
			throw new InvalidInputException("Column field is empty.");
		
		if (Strings.isNullOrEmpty(value))
			throw new InvalidInputException("Value field is empty.");
		
		ResultSet resultSet = statement.executeQuery(
				String.format("SELECT * FROM %s WHERE %s = '%s';", databaseConfig.getTable(), column, value));
		List<HashMap<String, Object>> map = convertResultSetToList(resultSet);
		resultSet.close();

		List<?> mapTo = mapper.convertValue(map, List.class);
		for (Object item : mapTo) {
			T obj = mapper.convertValue(item, clazz);
			return obj;
		}

		return null;
	}

	@Override
	public void insert(T obj) throws Exception {
		if (Strings.isNullOrEmpty(databaseConfig.getTable()))
			throw new TableNotFoundException("Table is empty.");
		
		if(obj == null) 
			throw new InvalidInputException("Object is empty.");
		
		StringBuilder values = new StringBuilder();
		StringBuilder columns = new StringBuilder();

		Map<String, Object> map = mapProperties(obj);
		Iterator<?> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> pair = (Entry<String, Object>) it.next();
			if (pair.getValue() != null) {
				values.append(String.format("'%s',", pair.getValue()));
				columns.append(String.format("%s,", pair.getKey()));
			}
		}

		String sql = String.format("INSERT INTO %s (%s) VALUES (%s);", databaseConfig.getTable(),
				columns.substring(0, columns.length() - 1), values.substring(0, values.length() - 1));

		statement.executeUpdate(sql);
	}

	@Override
	public List<T> selectAll() throws Exception {
		if (Strings.isNullOrEmpty(databaseConfig.getTable()))
			throw new TableNotFoundException("Table is empty.");

		List<T> users = new ArrayList<T>();

		ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s;", databaseConfig.getTable()));
		List<HashMap<String, Object>> map = convertResultSetToList(resultSet);
		resultSet.close();

		List<T> mapTo = mapper.convertValue(map, List.class);
		for (Object item : mapTo) {
			T obj = mapper.convertValue(item, clazz);
			users.add(obj);
		}

		return users;
	}

	@Override
	public void update(String column, String value, T obj) throws Exception {
		if (Strings.isNullOrEmpty(databaseConfig.getTable()))
			throw new TableNotFoundException("Table is empty.");

		if (Strings.isNullOrEmpty(column))
			throw new InvalidInputException("Column field is empty.");

		if (Strings.isNullOrEmpty(value))
			throw new InvalidInputException("Value field is empty.");

		if (obj == null)
			throw new InvalidInputException("Object is empty.");

		// TODO Auto-generated method stub
		throw new NotSupportedException();
	}

	@Override
	public void delete(String column, String value) throws Exception {
		if (Strings.isNullOrEmpty(databaseConfig.getTable()))
			throw new TableNotFoundException("Table is empty.");

		if (Strings.isNullOrEmpty(column))
			throw new InvalidInputException("Column field is empty.");

		if (Strings.isNullOrEmpty(value))
			throw new InvalidInputException("Value field is empty.");
		// TODO Auto-generated method stub
		throw new NotSupportedException();
	}

	private void initConnection() throws Exception {
		int reconnect = 0;
		while (reconnect < Literal.RECONNECT_TRIES && connection == null) {
			try {
				Class.forName(databaseConfig.getDriver());
				connection = DriverManager.getConnection(databaseConfig.getUrl(), databaseConfig.getUsername(),
						databaseConfig.getPassword());
			} catch (Exception e) {
				reconnect++;
			}
		}

		if (connection == null)
			throw new Exception(
					String.format("Fail to establish connection with database(%s", databaseConfig.getUrl()));

		statement = connection.createStatement();
	}

	private List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		while (rs.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>(columns);
			for (int i = 1; i <= columns; ++i) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
		}

		return list;
	}

	private static Map<String, Object> mapProperties(Object bean) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		for (PropertyDescriptor property : Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors()) {
			String name = property.getName();
			if (!name.equals("class")) {
				Object value = property.getReadMethod().invoke(bean);
				properties.put(name, value);
			}
		}
		return properties;
	}
}
