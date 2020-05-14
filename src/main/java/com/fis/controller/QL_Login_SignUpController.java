package com.fis.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.fis.dao.QLKhacHangDAO;
import com.fis.entities.Customer;
import com.fis.entities.Product;
import com.fis.utils.StringEx;
import com.fis.utils.Utils;

@ManagedBean(name = "qlLoginSignUpController")
@ViewScoped
public class QL_Login_SignUpController implements Serializable {
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(QL_Login_SignUpController.class);
	private Customer user = new Customer();
	private QLKhacHangDAO service = new QLKhacHangDAO();
	private List<Customer> list = new ArrayList<>();
	private int flag;
	private String userName = "";
	private String alert;
	private int cusId;

	@PostConstruct
	public void init() {

	}

	public void doLogOut() {
		try {
			session.setAttribute("loginUser", null);
			session.setAttribute("listCart", null);
			user = new Customer();
			FacesContext.getCurrentInstance().getExternalContext().redirect("/jsf-project/client/trang-chu.xhtml");
		} catch (IOException e) {
			logger.error("Lỗi : ", e);
		}
	}

	public void homePage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/jsf-project/client/trang-chu.xhtml");
		} catch (IOException e) {
			logger.error("Lỗi : ", e);
		}
	}

	public boolean preparedSignUp() {
		Customer isExisted = new Customer();
		isExisted = service.checkExistedUsername(userName);
		if (isExisted == null) {
			return true;
		} else {
			return false;
		}
	}

	public String CheckExistedUsername() {
		String rs = "";
		if (!StringEx.IsNullOrEmpty(userName)) {
			if (preparedSignUp()) {
				flag = 2;
				rs = "Tên tài khoản hợp lệ";
			} else {
				flag = 1;
				rs = "Tên tài khoản đã tồn tại";
			}
		}
		return rs;
	}

	public void login() {
		try {
			setUser(service.checkExisted(user.getUserName(), user.getPassWord()));
			if (user != null) {
				session.setAttribute("loginUser", user);
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/jsf-project/client/trang-chu.xhtml");
				RequestContext.getCurrentInstance().execute("PF('bestSale').hide()");
				session.setAttribute("loginUser", user);
			} else {
				Utils.errMessage("Tài khoản không tồn tại!");
			}
		} catch (Exception e) {
			logger.error("Lỗi : ", e);
		}
	}

	public void onPrepareSignUp() {
		user = new Customer();
	}

	public void doSignUp() {
		if (flag == 2) {
			user.setUserName(userName);
			if (service.InsertData(user)) {
				RequestContext.getCurrentInstance().execute("PF('dlgSignUp').hide()");
				login();
				RequestContext.getCurrentInstance().execute("PF('bestSale').hide()");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Product> getListCart() {
		ArrayList<Product> rs = (ArrayList<Product>) session.getAttribute("listCart");
		return rs;
	}

	public Customer getParamCusLogin() {
		Customer rs = (Customer) session.getAttribute("loginUser");
		return rs;
	}

	public List<Customer> getList() {
		return list;
	}

	public void setList(List<Customer> list) {
		this.list = list;
	}

	public Customer getUser() {
		return user;
	}

	public void setUser(Customer user) {
		this.user = user;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public int getCusId() {
		return cusId;
	}

	public void setCusId(int cusId) {
		this.cusId = cusId;
	}

}
