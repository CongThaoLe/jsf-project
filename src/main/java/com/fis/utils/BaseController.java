package com.fis.utils;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class BaseController {
    protected FacesContext facesContext = FacesContext.getCurrentInstance();
    protected HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

	public boolean checkUserIsLogin() {
		try {
			if (getUserName() == null || getFullName() == null || getRoleType() == null) {
				facesContext.getExternalContext().invalidateSession();
				String rootPath = facesContext.getExternalContext().getRequestContextPath() + "/";
				facesContext.getExternalContext().redirect(rootPath + "login.xhtml");
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
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
}
