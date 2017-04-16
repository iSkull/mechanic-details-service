package com.asia.promo.mechanic.interfaces;

import java.util.List;

public interface ITableQueryStatement<T> {
	T select(String column, String value) throws Exception;

	void insert(T obj) throws Exception;

	void update(String column, String value, T obj) throws Exception;

	void delete(String column, String value) throws Exception;

	List<T> selectAll() throws Exception;
}
