package com.fis.dao;

import java.math.BigDecimal;

import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.fis.entities.Order;
import com.fis.entities.OrderDetails;
import com.fis.entities.Product;
import com.fis.models.HibernateUtil;

public class QLThongKeDAO {
	private Product product;
	private Order hoaDon;
	private OrderDetails gioHang;

	private static final Logger LOGGER = Logger.getLogger(QLThongKeDAO.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	public Double getTongDoanhThu(int month) {
		BigDecimal rs = new BigDecimal(0);
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "SELECT SUM(orders.money) FROM Order orders WHERE MONTH(orders.orderDate) = " + month;
			Query query = session.createQuery(SQL);
			if (query.getSingleResult() != null) {
				rs = (BigDecimal) query.getSingleResult();
			}
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return rs.doubleValue();
	}

	public Double getAllDoanhThu() {
		BigDecimal rs = new BigDecimal(0);
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "SELECT SUM(orders.money) FROM Order orders";
			Query query = session.createQuery(SQL);
			if (query.getSingleResult() != null) {
				rs = (BigDecimal) query.getSingleResult();
			}
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return rs.doubleValue();
	}

	public Long getTongSP(int month) {
		Long rs = 0L;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "SELECT SUM(b.count) FROM Order a "
					+ "INNER JOIN OrderDetails b ON a.id = b.orderId where MONTH(a.orderDate) = " + month;
			Query query = session.createQuery(SQL);
			if (query.getSingleResult() != null) {
				rs = (Long) query.getSingleResult();
			}
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return rs;
	}
	
	public Long getSumNumberBuy() {
		Long rs = 0L;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "SELECT SUM(numberBuy) FROM Product";
			Query query = session.createQuery(SQL);
			if (query.getSingleResult() != null) {
				rs = (Long) query.getSingleResult();
			}
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return rs;
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

	public OrderDetails getGioHang() {
		return gioHang;
	}

	public void setGioHang(OrderDetails gioHang) {
		this.gioHang = gioHang;
	}

}
