package com.asia.promo.mechanic.interfaces;

import java.util.List;

public interface ITableNonQueryStatement<T> {
	T get(String id) throws Exception;

	void create(T obj) throws Exception;

	void update(String id, T obj) throws Exception;

	void delete(String id) throws Exception;

	List<T> getAll(String prop, String value) throws Exception;
}
