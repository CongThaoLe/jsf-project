package com.fis.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.fis.dao.QLHoaDonDAO;
import com.fis.entities.Customer;
import com.fis.entities.Order;
import com.fis.entities.OrderDetails;
import com.fis.entities.Product;
import com.fis.utils.Constants;
import com.fis.utils.Utils;

@ManagedBean(name = "qlThanhToanController")
@ViewScoped
public class QLThanhToanController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(QLThanhToanController.class);
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
	private Order cate = new Order();
	private Customer rs = new Customer();
	private QLHoaDonDAO service = new QLHoaDonDAO();
	private List<Order> list = new ArrayList<>();

	private static final String ALPHA_NUMERIC_STRING = "0123456789";
	private String randomStr;

	@ManagedProperty(value = "#{qlLoginSignUpController}")
	private QL_Login_SignUpController loginBean;

	@ManagedProperty(value = "#{gioHangController}")
	private QLGioHangController gioHangBean;

	public Customer getCustomerLogin() {
		rs = loginBean.getParamCusLogin();
		return rs;
	}

	public Double getTotalMoney() {
		Double money = gioHangBean.getTotal();
		return money;
	}

	public void preparePayment() {
		randomStr = randomAlphaNumeric(5);
		rs = getCustomerLogin();
		cate.setOrderCode("ORDER" + randomStr);
		cate.setCustomerId(rs.getId());
		cate.setFullName(rs.getFullName());
		cate.setAddress(rs.getAddress());
		cate.setTinhTp(rs.getTinhTp());
		cate.setQuanHuyen(rs.getQuanHuyen());
		cate.setPhone(rs.getPhone());
		BigDecimal money = BigDecimal.valueOf(getTotalMoney());
		cate.setMoney(money);
	}

	public void onSubmitPayment() {
		if (service.InsertData(cate)) {
			int id = service.SelectIdOfOrder("ORDER" + randomStr);
			for (Entry<Integer, Product> entry : gioHangBean.getCartItems().entrySet()) {
				// Insert chi tiết hóa đơn
				OrderDetails details = new OrderDetails();
				details.setOrderId(id);
				details.setProductId(entry.getValue().getId());
				details.setCount(entry.getValue().getCount());
				details.setMoney(new BigDecimal(entry.getValue().getPrice()));
				service.InsertDataToOderDetail(details);

				// Update lại số lượng,số lượng mua sản phẩm sau khi thanh toán
				Product product = service.getProductToUpdate(entry.getValue().getId());
				product.setCount(entry.getValue().getCount());
				service.UpdateProductWhenPayment(product);
			}

			session.setAttribute("listCart", null);
			session.setAttribute("total", null);
			gioHangBean.getCartItems().entrySet().clear();
			Utils.addMessage(Constants.BUY_SUCCESS);
			RequestContext.getCurrentInstance().execute("PF('loadDlg').hide()");
			RequestContext.getCurrentInstance().execute("PF('dlgPaySucces').show()");
		} else {
			Utils.errMessage(Constants.BUY_FAIL);
		}
	}

	public String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public void thongtinPage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("thong-tin-khach-hang.xhtml");
		} catch (Exception e) {
			LOGGER.error("Lỗi : ", e);
		}
	}

	public List<Product> getListCart() {
		List<Product> listDetail = new ArrayList<>();
		if (gioHangBean.getCartItems().entrySet() != null && !gioHangBean.getCartItems().entrySet().isEmpty()) {
			for (Entry<Integer, Product> entry : gioHangBean.getCartItems().entrySet()) {
				listDetail.add(entry.getValue());
			}
		}
		return listDetail;
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

	public QL_Login_SignUpController getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(QL_Login_SignUpController loginBean) {
		this.loginBean = loginBean;
	}

	public Customer getRs() {
		return rs;
	}

	public void setRs(Customer rs) {
		this.rs = rs;
	}

	public QLGioHangController getGioHangBean() {
		return gioHangBean;
	}

	public void setGioHangBean(QLGioHangController gioHangBean) {
		this.gioHangBean = gioHangBean;
	}

}
