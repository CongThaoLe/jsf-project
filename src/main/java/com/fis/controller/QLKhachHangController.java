package com.fis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.fis.dao.QLKhacHangDAO;
import com.fis.entities.Customer;
import com.fis.utils.BaseController;
import com.fis.utils.Constants;
import com.fis.utils.ExecuteObjectController;
import com.fis.utils.Utils;

@ManagedBean(name = "qlKhachHangController")
@ViewScoped
public class QLKhachHangController extends BaseController implements Serializable, ExecuteObjectController<Customer> {
	private static final long serialVersionUID = 1L;
	private Customer entity = new Customer();
	private QLKhacHangDAO service = new QLKhacHangDAO();
	private List<Customer> list = new ArrayList<Customer>();
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
		list = service.onSearchData(entity);
	}

	@Override
	public void prepareInsert() {
		flag = 1;
		entity = new Customer();
	}

	@Override
	public void onInsert() {
		if (service.InsertData(entity)) {
			Utils.addMessage(Constants.INS_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
		} else {
			Utils.errMessage(Constants.INS_FAIL);
		}
	}

	@Override
	public void onUpdate(Customer entity) {
		this.entity = entity;
		flag = 2;
	}

	@Override
	public void onComfirmUpdate() {
		if (service.UpdateData(entity)) {
			Utils.addMessage(Constants.UPD_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
		} else {
			Utils.errMessage(Constants.UPD_FAIL);
		}
	}

	@Override
	public void onDelete(Customer entity) {
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
		entity = new Customer();
	}

	public Customer getEntity() {
		return entity;
	}

	public void setEntity(Customer entity) {
		this.entity = entity;
	}

	public QLKhacHangDAO getService() {
		return service;
	}

	public void setService(QLKhacHangDAO service) {
		this.service = service;
	}

	public List<Customer> getList() {
		return list;
	}

	public void setList(List<Customer> list) {
		this.list = list;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
