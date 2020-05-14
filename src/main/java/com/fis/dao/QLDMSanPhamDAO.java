package com.fis.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.Category;
import com.fis.models.HibernateUtil;
import com.fis.utils.ExecuteObjectDAO;

@SuppressWarnings("unchecked")
public class QLDMSanPhamDAO implements ExecuteObjectDAO<Category> {
	private List<Category> listCate;
	private static final Logger LOGGER = Logger.getLogger(QLDMSanPhamDAO.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	@Override
	public List<Category> onShowData() {
		listCate = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Category ORDER BY name";
			Query query = session.createQuery(SQL);
			listCate = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listCate = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listCate;
	}

	public Category reloadEntity(int input) {
		Category rs = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String sql = "FROM Category WHERE ID = :id";
			Query query = session.createQuery(sql);
			query.setParameter("id", input);
			rs = (Category) query.getSingleResult();
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
	public List<Category> onSearchData(Category userIn) {
		listCate = new ArrayList<Category>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String hql = "SELECT * FROM TBL_CATEGORIES WHERE 1=1";
			if (!userIn.getName().trim().equals("")) {
				hql += " AND NAME LIKE N'%" + userIn.getName() + "%'";
			} else if (userIn.getStatus() != null) {
				hql += " AND STATUS = " + userIn.getStatus();
			}
			Query query = session.createNativeQuery(hql, Category.class);
			listCate = query.getResultList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listCate = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listCate;
	}

	@Override
	public boolean InsertData(Category userIns) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(userIns);
			transaction.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean DeleteData(Category userDel) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.delete(userDel);
			transaction.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean UpdateData(Category userUpdate) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(userUpdate);
			transaction.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			session.close();
		}
	}

}
