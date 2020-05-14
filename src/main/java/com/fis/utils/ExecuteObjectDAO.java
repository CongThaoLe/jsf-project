package com.fis.utils;

import java.util.List;

public interface ExecuteObjectDAO<T> {

	public List<T> onShowData();

	public List<T> onSearchData(T entity);

	public boolean InsertData(T entity);

	public boolean UpdateData(T entity);

	public boolean DeleteData(T entity);

}
