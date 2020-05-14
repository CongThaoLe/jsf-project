package com.fis.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.Producer;
import com.fis.models.HibernateUtil;
import com.fis.utils.ExecuteObjectDAO;
import com.fis.utils.StringEx;

@SuppressWarnings("unchecked")
public class QLNhaXuatBanDAO implements ExecuteObjectDAO<Producer> {
	private List<Producer> listProducer;
	private static final Logger LOGGER = Logger.getLogger(QLNhaXuatBanDAO.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	@Override
	public List<Producer> onShowData() {
		listProducer = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Producer ORDER BY ID DESC";
			Query query = session.createQuery(SQL);
			listProducer = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listProducer = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listProducer;
	}

	public Producer reloadEntity(int input) {
		Producer rs = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String sql = "FROM Producer WHERE ID = :id";
			Query query = session.createQuery(sql);
			query.setParameter("id", input);
			rs = (Producer) query.getSingleResult();
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
	public List<Producer> onSearchData(Producer userIn) {
		listProducer = new ArrayList<Producer>();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			String hql = "SELECT * FROM TBL_PRODUCERS WHERE 1=1";
			if (!StringEx.IsNullOrEmpty(userIn.getFullName())) {
				hql += " AND NAME LIKE N'%" + userIn.getFullName() + "%'";
			} else if (!StringEx.IsNullOrEmpty(userIn.getCode())) {
				hql += " AND CODE LIKE N'%" + userIn.getCode() + "%'";
			} else if (!StringEx.IsNullOrEmpty(userIn.getKeyWord())) {
				hql += " AND KEYWORD LIKE N'%" + userIn.getKeyWord()+"%'";
			} else if (!StringEx.IsNullOrEmpty(userIn.getCreated())) {
				hql += " AND CREATED = " + userIn.getCreated();
			}
			Query query = session.createNativeQuery(hql, Producer.class);
			listProducer = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listProducer = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listProducer;
	}

	@Override
	public boolean InsertData(Producer userIns) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			userIns.setCreated(new Date());
			session.save(userIns);
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
	public boolean DeleteData(Producer userDel) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			userDel.setCreated(new Date());
			session.delete(userDel);
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
	public boolean UpdateData(Producer userUpdate) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
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
