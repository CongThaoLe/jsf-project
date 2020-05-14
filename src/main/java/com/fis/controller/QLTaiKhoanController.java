package com.fis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.fis.dao.QLTaiKhoanDAO;
import com.fis.entities.User;
import com.fis.utils.BaseController;
import com.fis.utils.Constants;
import com.fis.utils.ExecuteObjectController;
import com.fis.utils.Utils;

@ManagedBean(name = "qlTaiKhoanController")
@ViewScoped
public class QLTaiKhoanController extends BaseController implements Serializable, ExecuteObjectController<User> {
	private static final long serialVersionUID = 1L;
	private User user = new User();
	private QLTaiKhoanDAO service = new QLTaiKhoanDAO();
	private List<User> list = new ArrayList<User>();
	private int flag;
	private String password;

	@PostConstruct
	public void init() {
		user.setCreated(new Date());
		checkUserIsLogin();
		onShowData();
	}

	@Override
	public void onShowData() {
		list = service.onShowData();
	}

	@Override
	public void onSearch() {
		list = service.onSearchData(user);
	}

	@Override
	public void prepareInsert() {
		flag = 1;
		user = new User();
	}

	@Override
	public void onInsert() {
		user.setPassWord(password);
		if (service.InsertData(user)) {
			Utils.addMessage(Constants.INS_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
		} else {
			Utils.errMessage(Constants.INS_FAIL);
		}
	}

	public void onUpdate(int entity) {
		user = service.reloadUser(entity);
		flag = 2;
	}

	@Override
	public void onComfirmUpdate() {
		user.setPassWord(password);
		if (service.UpdateData(user)) {
			Utils.addMessage(Constants.UPD_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
			user = new User();
		} else {
			Utils.errMessage(Constants.UPD_FAIL);
		}
	}

	@Override
	public void onDelete(User entity) {
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
		user = new User();
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	public void onUpdate(User entity) {

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
