package com.fis.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.fis.dao.QLDMSanPhamDAO;
import com.fis.dao.QLDistrictDAO;
import com.fis.dao.QLNhaXuatBanDAO;
import com.fis.dao.QLProvinceDAO;
import com.fis.entities.Category;
import com.fis.entities.District;
import com.fis.entities.Producer;
import com.fis.entities.Province;

@ManagedBean(name = "danhmucUtils")
@ViewScoped
public class Utils implements Serializable {
	private static final long serialVersionUID = 1L;

	public static List<Province> dmhcProvince;
	public static List<District> dmhcDistrict;
	public static List<Category> listCategory;
	public static List<Producer> listProducer;

	public static void facesError(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
		RequestContext.getCurrentInstance()
				.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public static void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Thành công!", summary);
		FacesContext.getCurrentInstance().addMessage("Thành công!", message);
	}

	public static void errMessage(String errMsg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Thất bại!", errMsg);
		FacesContext.getCurrentInstance().addMessage("Lỗi!", message);
	}

	private List<Province> loadDMHCProvince() {
		try {
			dmhcProvince = new QLProvinceDAO().onShowData();
			return dmhcProvince;
		} catch (Exception e) {
		}
		return new ArrayList<Province>();
	}

	public List<Province> getDmhcProvince() {
		if (null == dmhcProvince) {
			dmhcProvince = loadDMHCProvince();
		}
		return dmhcProvince;
	}

	public Province getDmhcProvinceById(String provinId) {
		if (null == dmhcProvince) {
			dmhcProvince = getDmhcProvince();
		}
		for (Province tccn : dmhcProvince) {
			if (tccn.getProvinceId().equals(provinId)) {
				return tccn;
			}
		}
		return new Province();
	}

	public List<District> loadDmDistrict() {
		dmhcDistrict = new QLDistrictDAO().getDistrictByProvinceId();
		return dmhcDistrict;
	}
	
	public District getDistrictByProvinceId(String id) {
		if (null == dmhcDistrict) {
			dmhcDistrict = loadDmDistrict();
		}
		
		for (District obj : dmhcDistrict) {
			if (id.equals(obj.getDistrictId())) {
				return obj;
			}
		}
		return new District();
	}

	public List<District> getDmhcDistrictByProvinceId(String id) {
		if (null == dmhcDistrict) {
			dmhcDistrict = loadDmDistrict();
		}
		
		List<District> lstResult = new ArrayList<>();
		for (District obj : dmhcDistrict) {
			if (id.equals(obj.getProvinceId())) {
				lstResult.add(obj);
			}
		}
		return lstResult;
	}

	public static List<Category> loadDmCate() {
		listCategory = new QLDMSanPhamDAO().onShowData();
		return listCategory;
	}

	public String loadTenDM(int catId) {
		loadDmCate();
		String tenDm = "";
		for (Category item : listCategory) {
			if (catId == item.getId()) {
				tenDm = item.getName();
			}
		}
		return tenDm;
	}
	
	public static List<Producer> loadListProducer() {
		listProducer = new QLNhaXuatBanDAO().onShowData();
		return listProducer;
	}
	
	public String loadTenProducer(int idProducer) {
		loadListProducer();
		String tenTacGia = "";
		for (Producer item : listProducer) {
			if (idProducer == item.getIdProducer()) {
				tenTacGia = item.getFullName();
			}
		}
		return tenTacGia;
	}
	
	public List<District> getDmhcDistrict() {
		return dmhcDistrict;
	}

	public void setDmhcDistrict(List<District> dmhcDistrict) {
		Utils.dmhcDistrict = dmhcDistrict;
	}

	public List<Category> getListCategory() {
		return listCategory;
	}

	public void setListCategory(List<Category> listCategory) {
		Utils.listCategory = listCategory;
	}

	public void setDmhcProvince(List<Province> dmhcProvince) {
		Utils.dmhcProvince = dmhcProvince;
	}

}
