package com.fis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.fis.dao.QLSanPhamDAO;
import com.fis.entities.Product;
import com.fis.utils.Utils;

@ManagedBean(name = "gioHangController")
@SessionScoped
public class QLGioHangController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

	private Double total;
	
	private QLSanPhamDAO service = new QLSanPhamDAO();

	private HashMap<Integer, Product> cartItems;
	private Product cate;

	public static final String ADD_SP = "Đã thêm vào Tủ sách";
	public static final String DEL_SP = "Đã xóa sách!";

	public QLGioHangController() {
		cartItems = new HashMap<Integer, Product>();
	}

	public void viewDetail(Product entity) {
		cate = service.reloadProduct(entity.getId());
		RequestContext.getCurrentInstance().update("@([id$=detail])");
	}

	public void back() {
		cate = new Product();
	}

	public ArrayList<Product> listSP() {
		total = 0D;
		ArrayList<Product> tmp = new ArrayList<Product>();
		for (Map.Entry<Integer, Product> entry : cartItems.entrySet()) {
			tmp.add(entry.getValue());
			if (entry.getValue().getPriceSale() != null) {
				total += (entry.getValue().getCount() * entry.getValue().getPrice()) - entry.getValue().getPriceSale();
			} else {
				total += entry.getValue().getCount() * entry.getValue().getPrice();
			}
		}
		session.setAttribute("listCart", tmp);
		return tmp;
	}

	public void insertToCart(Integer key, Product item) {
		item = cate;
		Product bln = cartItems.get(key);
		if (bln != null) {
			Utils.addMessage(ADD_SP);
			item.setCount(item.getCount() + 1);
			cartItems.put(key, item);
			listSP();
		} else {
			Utils.addMessage(ADD_SP);
			item.setCount(1);
			cartItems.put(key, item);
			listSP();
		}
		session.setAttribute("listSP", cartItems);
	}

	public double total() {
		Double count = 0D;
		for (Map.Entry<Integer, Product> list : cartItems.entrySet()) {
			count += (list.getValue().getPrice() * list.getValue().getCount()) - list.getValue().getPrice();
		}
		session.setAttribute("total", count);
		return count;

	}

	public void removeToCart(Integer product) {
		cartItems.remove(product);
		listSP();
	}

	public Double getMoney() {
		Double rs = (Double) session.getAttribute("total");
		return rs;
	}

	public QLGioHangController(HashMap<Integer, Product> cartItems) {
		this.cartItems = cartItems;
	}

	public HashMap<Integer, Product> getCartItems() {
		return cartItems;
	}

	public void setCartItems(HashMap<Integer, Product> cartItems) {
		this.cartItems = cartItems;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Product getCate() {
		return cate;
	}

	public void setCate(Product cate) {
		this.cate = cate;
	}

}
