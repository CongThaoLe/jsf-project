package com.fis.utils;

public interface ExecuteObjectController<T> {

	public void onShowData();

	public void onSearch();

	public void prepareInsert();

	public void onInsert();

	public void onUpdate(T entity);

	public void onComfirmUpdate();

	public void onDelete(T entity);

	public void resetDialogForm();
}
