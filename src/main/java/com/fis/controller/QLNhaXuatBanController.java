package com.fis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.fis.dao.QLNhaXuatBanDAO;
import com.fis.entities.Producer;
import com.fis.utils.BaseController;
import com.fis.utils.Constants;
import com.fis.utils.ExecuteObjectController;
import com.fis.utils.Utils;

@ManagedBean(name = "qlNhaCungCaptroller")
@ViewScoped
public class QLNhaXuatBanController extends BaseController implements Serializable, ExecuteObjectController<Producer> {
	private static final long serialVersionUID = 1L;
	private Producer entity = new Producer();
	private QLNhaXuatBanDAO service = new QLNhaXuatBanDAO();
	private List<Producer> list = new ArrayList<Producer>();
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
		entity = new Producer();
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
	public void onUpdate(Producer entity) {
	}

	public void onUpdate(int id) {
		entity = service.reloadEntity(id);
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
	public void onDelete(Producer entity) {
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
		entity = new Producer();
	}

	public Producer getEntity() {
		return entity;
	}

	public void setEntity(Producer entity) {
		this.entity = entity;
	}

	public List<Producer> getList() {
		return list;
	}

	public void setList(List<Producer> list) {
		this.list = list;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
