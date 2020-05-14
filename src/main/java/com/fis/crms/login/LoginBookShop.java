package com.fis.crms.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.User;
import com.fis.models.HibernateUtil;
import com.fis.utils.Constants;
import com.fis.utils.Utils;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBookShop implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(LoginBookShop.class);
	private String username;
	private String password;
	private String imgLoading = "";
	private User user;
	private List<User> listUser;
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	FacesContext facesContext = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	private int activeTabIndex;

	@PostConstruct
	public void init() {

	}

	@SuppressWarnings("unchecked")
	public User checkExisted() {
		User rs = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "SELECT * FROM TBL_USERS WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password
					+ "'";
			Query query = session.createNativeQuery(SQL, User.class);
			List<User> listRs = new ArrayList<>();
			listRs = query.getResultList();
			if (!listRs.isEmpty()) {
				rs = listRs.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("Lỗi : ", e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return rs;

	}

	public LoginBookShop() {
		String rootPath = "/";
		imgLoading = rootPath + "resources/img/default.svg";
	}

	public void doLogin() {
		try {
			user = checkExisted();
			if (user != null) {
				if ((user.getRole() == 1 || user.getRole() == 2) && user.getStatus() == 1) {
					session.setAttribute("fullName", user.getFullName());
					session.setAttribute("loai", user.getRole());
					session.setAttribute("username", user.getUserName());
					facesContext.getExternalContext().redirect("adminIndex.xhtml");
				} else {
					Utils.errMessage(Constants.NOT_ACTIVE);
				}
			} else {
				Utils.errMessage(Constants.AUTH_FAIL);
			}
		} catch (Exception e) {
			LOGGER.error("Lỗi : ", e);
		}
	}

	public void onTabChange() {
		Map<String, String> paramMap = facesContext.getExternalContext().getRequestParameterMap();
		String paramIndex = paramMap.get("activeIndex");
		setActiveTabIndex(Integer.valueOf(paramIndex));
	}

	public void doLogout() {
		try {
			facesContext.getExternalContext().invalidateSession();
			String rootPath = facesContext.getExternalContext().getRequestContextPath() + "/";
			facesContext.getExternalContext().redirect(rootPath + "login.xhtml");
		} catch (Exception ex) {
			ex.printStackTrace();
			ex.getMessage();
		}
	}

	public String getUserName() {
		String username = "";
		username = (String) session.getAttribute("username");
		return username;
	}

	public String getFullName() {
		String fullName = "";
		fullName = (String) session.getAttribute("fullName");
		return fullName;
	}

	public String getRoleType() {
		int role;
		role = (int) session.getAttribute("loai");
		if (role == 1) {
			return "Quản trị";
		} else {
			return "Nhân viên bán hàng";
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImgLoading() {
		return imgLoading;
	}

	public void setImgLoading(String imgLoading) {
		this.imgLoading = imgLoading;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getListUser() {
		return listUser;
	}

	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public int getActiveTabIndex() {
		return activeTabIndex;
	}

	public void setActiveTabIndex(int activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}

}
