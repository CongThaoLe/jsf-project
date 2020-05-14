package com.fis.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.Product;
import com.fis.models.HibernateUtil;
import com.fis.utils.ExecuteObjectDAO;
import com.fis.utils.StringEx;

@SuppressWarnings("unchecked")
public class QLSanPhamDAO implements ExecuteObjectDAO<Product> {
	private List<Product> listItem;
	private static final Logger LOGGER = Logger.getLogger(QLSanPhamDAO.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	@Override
	public List<Product> onShowData() {
		listItem = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Product ORDER BY ID DESC";
			Query query = session.createQuery(SQL);
			listItem = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			listItem = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listItem;
	}

	public Product reloadProduct(int input) {
		Product rs = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String sql = "FROM Product WHERE ID = :id";
			Query query = session.createQuery(sql);
			query.setParameter("id", input);
			rs = (Product) query.getSingleResult();
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

	@Override
	public List<Product> onSearchData(Product userIn) {
		listItem = new ArrayList<Product>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String hql = "SELECT * FROM TBL_PRODUCTS WHERE 1=1";
			if (!StringEx.IsNullOrEmpty(userIn.getName())) {
				hql += " AND NAME LIKE N'%"+userIn.getName()+"%'";
			} else if (!StringEx.IsNullOrEmpty(userIn.getAlias())) {
				hql += " AND ALIAS = " + userIn.getAlias();
			} else if (userIn.getNumber() != null) {
				hql += " AND NUMBER = " + userIn.getNumber();
			} else if (!StringEx.IsNullOrEmpty(userIn.getIdProducer())) {
				hql += " AND IDPRODUCER = " + userIn.getIdProducer();
			} else if (userIn.getSale() != null) {
				hql += " AND SALE = " + userIn.getSale();
			} else if (userIn.getPrice() != null) {
				hql += " AND PRICE = " + userIn.getPrice();
			} else if (userIn.getStatus() != null) {
				hql += " AND STATUS = " + userIn.getStatus();
			}
			hql += " ORDER BY NAME ASC";
			Query query = session.createNativeQuery(hql, Product.class);
			listItem = query.getResultList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			listItem = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listItem;
	}

	@Override
	public boolean InsertData(Product item) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			item.setCreated(new Date());
			session.save(item);
			transaction.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean DeleteData(Product item) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			session.delete(item);
			transaction.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean UpdateData(Product userUpdate) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			session.update(userUpdate);
			transaction.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			session.close();
		}
	}

}
