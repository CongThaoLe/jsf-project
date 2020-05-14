package com.fis.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.Product;
import com.fis.models.HibernateUtil;
import com.fis.utils.StringEx;

public class TrangChuDAO {
	private static final Logger LOGGER = Logger.getLogger(TrangChuDAO.class);
	private List<Product> listItemBestSale;
	private List<Product> listItem;
	private List<Product> listSearch;
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	@SuppressWarnings("unchecked")
	public List<Product> onShowData() {
		listItemBestSale = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Product WHERE NUMBER_BUY >= 1000 ORDER BY NAME";
			Query query = session.createQuery(SQL);
			listItemBestSale = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listItemBestSale = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listItemBestSale;
	}

	@SuppressWarnings("unchecked")
	public List<Product> ListData() {
		listItem = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Product ORDER BY NAME ASC";
			Query query = session.createQuery(SQL);
			listItem = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listItem = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listItem;
	}

	@SuppressWarnings("unchecked")
	public List<Product> SearchListData(Product userIn) {
		listSearch = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "SELECT * FROM TBL_PRODUCTS WHERE (1=1) ";

			if (!StringEx.IsNullOrEmpty(userIn.getName())) {
				SQL += " AND NAME LIKE N'%"+userIn.getName()+"%'";
			} else if (userIn.getCatId() != 0) {
				SQL += " AND CATID = " + userIn.getCatId();
			} else if (userIn.getOrderName() != null && userIn.getOrderName() == 1) {
				SQL += " ORDER BY NAME ASC";
			} else if (userIn.getOrderName() != null && userIn.getOrderName() == 2) {
				SQL += " ORDER BY NAME DESC";
			} else if (userIn.getOrderName() != null && userIn.getOrderName() == 3) {
				SQL += "ORDER BY PRICE ASC";
			} else if (userIn.getOrderName() != null && userIn.getOrderName() == 4) {
				SQL += "ORDER BY PRICE DESC";
			}

			Query query;
			query = session.createNativeQuery(SQL, Product.class);
			listSearch = query.getResultList();

			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listSearch = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listSearch;
	}

}
