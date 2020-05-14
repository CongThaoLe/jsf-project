package com.fis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.fis.dao.QLDMSanPhamDAO;
import com.fis.entities.Category;
import com.fis.utils.BaseController;
import com.fis.utils.Constants;
import com.fis.utils.ExecuteObjectController;
import com.fis.utils.Utils;

@ManagedBean(name = "qlDMSanPhamController")
@ViewScoped
public class QLDMSanPhamController extends BaseController implements Serializable, ExecuteObjectController<Category> {
	private static final long serialVersionUID = 1L;
	private Category cate = new Category();
	private QLDMSanPhamDAO service = new QLDMSanPhamDAO();
	private List<Category> list = new ArrayList<Category>();
	private int flag;

	@PostConstruct
	public void init() {
		checkUserIsLogin();
		onShowData();
	}

	@Override
	public void onShowData() {
		list = service.onShowData();
	}

	@Override
	public void onSearch() {
		list = service.onSearchData(cate);
	}

	@Override
	public void prepareInsert() {
		flag = 1;
		cate = new Category();
	}

	@Override
	public void onInsert() {
		if (service.InsertData(cate)) {
			Utils.addMessage(Constants.INS_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
		} else {
			Utils.errMessage(Constants.INS_FAIL);
		}

	}

	@Override
	public void onUpdate(Category entity) {
	}

	public void onUpdate(int id) {
		cate = service.reloadEntity(id);
		flag = 2;
	}

	@Override
	public void onComfirmUpdate() {
		if (service.UpdateData(cate)) {
			Utils.addMessage(Constants.UPD_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
		} else {
			Utils.errMessage(Constants.UPD_FAIL);
		}

	}

	@Override
	public void onDelete(Category entity) {
		if (service.DeleteData(entity)) {
			Utils.addMessage(Constants.DEL_SUCCESS);
			onShowData();
		} else {
			Utils.errMessage(Constants.DEL_FAIL);
		}

	}

	@Override
	public void resetDialogForm() {
		onShowData();
		cate = new Category();
	}

	public Category getCate() {
		return cate;
	}

	public void setCate(Category cate) {
		this.cate = cate;
	}

	public QLDMSanPhamDAO getService() {
		return service;
	}

	public void setService(QLDMSanPhamDAO service) {
		this.service = service;
	}

	public List<Category> getList() {
		return list;
	}

	public void setList(List<Category> list) {
		this.list = list;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
