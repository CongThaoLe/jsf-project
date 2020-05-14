package com.fis.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.fis.dao.QLThongKeDAO;
import com.fis.entities.Order;
import com.fis.entities.Product;
import com.fis.utils.BaseController;

@ManagedBean(name = "thongKeDTController")
public class ThongKeController extends BaseController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ThongKeController.class);
	private Product product = new Product();
	private Order hoaDon = new Order();
	private BarChartModel bieuDoThongKe;
	private QLThongKeDAO service = new QLThongKeDAO();
	private int month;
	private int year;
	private Long totalProduct = 0L;

	@PostConstruct
	public void init() {
		try {
			if (!checkUserIsLogin()) {
				createAnimatedModels();
			}
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	private void createAnimatedModels() {

		bieuDoThongKe = initBarModel();
		bieuDoThongKe.setTitle("Biểu đồ thống kê số lượng sách bán ra hàng tháng");
		bieuDoThongKe.setAnimate(true);
		bieuDoThongKe.setLegendPosition("ne");
		Axis yAxis = bieuDoThongKe.getAxis(AxisType.Y);
		yAxis.setTickFormat("%d");
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		try {
			ChartSeries orders = new ChartSeries();
			orders.setLabel("Doanh thu");
			ChartSeries products = new ChartSeries();
			products.setLabel("Sách");
			for (month = 1; month <= 12; month++) {
				hoaDon.setMonthOfYear(month);
				Double count = service.getTongDoanhThu(month);
				if (count != null) {
					orders.set(month + "/" + year, count);
				} else {
					orders.set(month + "/" + year, 0);
				}
			}

			for (month = 1; month <= 12; month++) {
				product.setMonthOfYear(month);
				Long count = totalProduct(month);
				if (count != null) {
					products.set(month + "/" + year, count * 10000);
				} else {
					products.set(month + "/" + year, 0);
				}
			}
			model.addSeries(orders);
			model.addSeries(products);
			return model;
		} catch (Exception e) {
			return null;
		}
	}

	public Long totalProduct(int month) {
		try {
			if (service.getTongSP(month) != null) {
				totalProduct = service.getTongSP(month);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return totalProduct;
	}

	public String total() {
		double total = 0D;
		try {
			if (service.getAllDoanhThu() != null) {
				total = service.getAllDoanhThu();
			}
			return new DecimalFormat("###,###").format(total);
		} catch (Exception e) {
			logger.error("", e);
			return "";
		}
	}

	public Double sumAllProduct() {
		Long sum = 0L;
		sum = service.getSumNumberBuy();
		return sum.doubleValue();
	}

	public BarChartModel getBieuDoThongKe() {
		return bieuDoThongKe;
	}

	public void setBieuDoThongKe(BarChartModel bieuDoThongKe) {
		this.bieuDoThongKe = bieuDoThongKe;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(Order hoaDon) {
		this.hoaDon = hoaDon;
	}

	public Long getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(Long totalProduct) {
		this.totalProduct = totalProduct;
	}

}
