package com.fis.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import com.fis.dao.QLHoaDonDAO;
import com.fis.dao.QLKhacHangDAO;
import com.fis.entities.Customer;
import com.fis.entities.Order;
import com.fis.entities.OrderDetails;
import com.fis.entities.Product;
import com.fis.utils.Utils;

@ManagedBean(name = "qlThongTinKhachHangController")
@ViewScoped
public class QLThongTinKhachHangController implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Order> listOrder = new ArrayList<>();
	private List<OrderDetails> cartList = new ArrayList<>();
	private Order order = new Order();
	private QLHoaDonDAO service = new QLHoaDonDAO();
	private QLKhacHangDAO serviceCus = new QLKhacHangDAO();
	private Product product = new Product();
	private Double total = 0D;
	private Customer customer = new Customer();

	@ManagedProperty(value = "#{qlLoginSignUpController}")
	private QL_Login_SignUpController loginBean;

	@PostConstruct
	public void init() {
		if (loginBean.getParamCusLogin() != null) {
			onShowListOrder();
			LoadProfile();
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/jsf-project/client/trang-chu.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void Refresh() {
		onShowListOrder();
		order = new Order();
	}

	public void LoadProfile() {
		customer = serviceCus.checkExistedUsername(loginBean.getParamCusLogin().getUserName());
	}

	public Product getProductById(int id) {
		return service.getProductById(id);
	}

	public void onShowListOrder() {
		listOrder = service.showListOrder(loginBean.getParamCusLogin().getId());
	}

	public void onShowDetail() {
		cartList = service.showListDetail(order.getId());
		if (!cartList.isEmpty()) {
			for (OrderDetails orderDetails : cartList) {
				total += orderDetails.getCount() * orderDetails.getMoney().intValue();
			}
		}
	}

	public void onDelete(Order delItem) {
		if (service.DeleteData(delItem)) {
			Utils.addMessage("Đã hủy đơn hàng thành công!");
			onShowDetail();
		} else {
			Utils.errMessage("Hủy đơn hàng thất bại!");
		}
	}

	public void UpdateProfile() {
		if (serviceCus.UpdateData(customer)) {
			Utils.addMessage("Cập nhật thông tin thành công!");
			onShowDetail();
		} else {
			Utils.errMessage("Cập nhật thông tin thất bại!");
		}
	}

	public void onBack() {
		order = new Order();
		total = new Double(0);
	}

	public void onViewDetail(Order input) {
		order = input;
		onShowDetail();
	}

	public List<Order> getListOrder() {
		return listOrder;
	}

	public void setListOrder(List<Order> listOrder) {
		this.listOrder = listOrder;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public QL_Login_SignUpController getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(QL_Login_SignUpController loginBean) {
		this.loginBean = loginBean;
	}

	public List<OrderDetails> getCartList() {
		return cartList;
	}

	public void setCartList(List<OrderDetails> cartList) {
		this.cartList = cartList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
