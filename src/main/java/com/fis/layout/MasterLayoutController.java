package com.fis.layout;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DynamicMenuModel;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fis.utils.Constants;

@ViewScoped
@ManagedBean(name = "masterController")
public class MasterLayoutController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(MasterLayoutController.class);
	private MenuModel mainMenuModel;
	private DefaultMenuItem currentMenu;
	private static Map<Long, Object> hsdnSearchMap = new HashedMap<>();
	private int activeTabIndex;
	private String curMenu = "";
	private String priMenu = "";
	private boolean isShow;
	public HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
			.getSession(true);

	@PostConstruct
	public void init() {
	}

	public MasterLayoutController() {
		if (mainMenuModel == null) {
			mainMenuModel = genMainMenu();
		}

		getCurrentMenu();
	}

	private MenuModel genMainMenu() {
		DynamicMenuModel mainMenuModel = new DynamicMenuModel();
		ResourceBundle resources = ResourceBundle.getBundle(Constants.RESOURCE_FILENAME,
				new Locale(Constants.LANGUAGE_VI, Constants.COUNTRY_VI));

		mainMenuModel.addElement(new DefaultMenuItem(resources.getString(Constants.QLTK), "fa fa-bank",
				"/admin/qlTaiKhoan/ql-tai-khoan.xhtml"));

		mainMenuModel.addElement(new DefaultMenuItem(resources.getString(Constants.QLDMSP), "fa fa-bank",
				"/admin/qlDMSanPham/ql-dm-san-pham.xhtml"));

		mainMenuModel.addElement(new DefaultMenuItem(resources.getString(Constants.QLSP), "fa fa-bank",
				"/admin/qlSanPham/ql-san-pham.xhtml"));

		mainMenuModel.addElement(new DefaultMenuItem(resources.getString(Constants.QLHD), "fa fa-bank",
				"/admin/qlHoaDon/ql-hoa-don.xhtml"));

		mainMenuModel.addElement(new DefaultMenuItem(resources.getString(Constants.QLNHACC), "fa fa-bank",
				"/admin/qlNhaXuatBan/ql-nha-xuat-ban.xhtml"));

		mainMenuModel.addElement(new DefaultMenuItem(resources.getString(Constants.QLCUSTOMER), "fa fa-bank",
				"/admin/qlKhachHang/ql-khach-hang.xhtml"));

		mainMenuModel.addElement(new DefaultMenuItem(resources.getString(Constants.THONGKE_DOANHTHU),
				"fa fa-line-chart", "/admin/qlThongKe/thong-ke-doanh-thu.xhtml"));
		return mainMenuModel;
	}

	public MenuModel getMainMenuModel() {
		return mainMenuModel;
	}

	public void setMainMenuModel(MenuModel mainMenuModel) {
		this.mainMenuModel = mainMenuModel;
	}

	public DefaultMenuItem getCurrentMenu() {
		String path = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		if (!priMenu.equals(path)) {
			activeTabIndex = 0;
			priMenu = path;
		}

		List<MenuElement> menuItems = mainMenuModel.getElements();
		for (MenuElement me : menuItems) {
			DefaultMenuItem dmi = (DefaultMenuItem) me;
			if (path.equals(dmi.getUrl().split("\\?")[0])) {
				dmi.setContainerStyleClass("current-menu");
				dmi.setStyleClass("current-menu");
				currentMenu = dmi;
			} else {
				dmi.setStyleClass("uncurrent-menu");
			}
		}
		return currentMenu;
	}

	public void setCurrentMenu(DefaultMenuItem currentMenu) {
		this.currentMenu = currentMenu;
	}

	public static Map<Long, Object> getHsdnSearchMap() {
		return hsdnSearchMap;
	}

	public static void setHsdnSearchMap(Map<Long, Object> hsdnSearchMap) {
		MasterLayoutController.hsdnSearchMap = hsdnSearchMap;
	}

	public int getActiveTabIndex() {
		return activeTabIndex;
	}

	public void setActiveTabIndex(int activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}

	public String getCurMenu() {
		return curMenu;
	}

	public void setCurMenu(String curMenu) {
		this.curMenu = curMenu;
	}

	public String getPriMenu() {
		return priMenu;
	}

	public void setPriMenu(String priMenu) {
		this.priMenu = priMenu;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public static Logger getLogger() {
		return logger;
	}

}
