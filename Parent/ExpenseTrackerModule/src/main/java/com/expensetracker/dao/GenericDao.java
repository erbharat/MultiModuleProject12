package com.expensetracker.dao;


public interface GenericDao {
	public Object read(Object id) throws Exception;
	public void delete(String id) throws Exception;
	public void update(String id,String key) throws Exception;
	public void save(Object object) throws Exception;
	public void update(Object object);
}
