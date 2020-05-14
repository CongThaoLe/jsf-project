package com.fis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.fis.dao.QLHoaDonDAO;
import com.fis.dao.QLKhacHangDAO;
import com.fis.entities.Customer;
import com.fis.entities.Order;
import com.fis.entities.OrderDetails;
import com.fis.entities.Product;
import com.fis.utils.BaseController;
import com.fis.utils.Constants;
import com.fis.utils.ExecuteObjectController;
import com.fis.utils.Utils;

@ManagedBean(name = "qlHoaDonController")
@ViewScoped
public class QLHoaDonController extends BaseController implements Serializable, ExecuteObjectController<Order> {
	private static final long serialVersionUID = 1L;
	private Order cate = new Order();
	private QLHoaDonDAO service = new QLHoaDonDAO();
	private QLKhacHangDAO serviceCus = new QLKhacHangDAO();
	private List<OrderDetails> listDetail = new ArrayList<>();
	private Customer customer = new Customer();
	private List<Order> list = new ArrayList<>();
	private int flag;
	private Double total = 0D;

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
		cate = new Order();
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

	public Product getProductById(int id) {
		return service.getProductById(id);
	}

	public void onUpdate(int id) {
		cate = service.reloadOrder(id);
		flag = 2;
		total = cate.getMoney().doubleValue();
		listDetail = service.showListDetail(cate.getId());
		customer = serviceCus.checkInforCus(cate.getCustomerId());
	}

	@Override
	public void onComfirmUpdate() {
		if (service.UpdateData(cate)) {
			Utils.addMessage(Constants.UPD_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
			cate = new Order();
			customer = new Customer();
		} else {
			Utils.errMessage(Constants.UPD_FAIL);
		}
	}

	@Override
	public void onDelete(Order entity) {
		if (service.DeleteData(entity)) {
			Utils.addMessage(Constants.DEL_SUCCESS);
			onShowData();
		} else {
			Utils.errMessage(Constants.INS_FAIL);
		}
	}

	@Override
	public void resetDialogForm() {
		onShowData();
		cate = new Order();
	}

	public Customer getTenKhachHangById(int id) {
		return service.getTenKhachHangById(id);
	}

	public Order getCate() {
		return cate;
	}

	public void setCate(Order cate) {
		this.cate = cate;
	}

	public List<Order> getList() {
		return list;
	}

	public void setList(List<Order> list) {
		this.list = list;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<OrderDetails> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<OrderDetails> listDetail) {
		this.listDetail = listDetail;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void onUpdate(Order entity) {

	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
