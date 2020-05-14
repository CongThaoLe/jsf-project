package com.fis.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.User;
import com.fis.models.HibernateUtil;
import com.fis.utils.ExecuteObjectDAO;

@SuppressWarnings("unchecked")
public class QLTaiKhoanDAO implements ExecuteObjectDAO<User> {
	private List<User> listUser;
	private static final Logger LOGGER = Logger.getLogger(QLTaiKhoanDAO.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	@Override
	public List<User> onShowData() {
		listUser = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM User ORDER BY ID DESC";
			Query query = session.createQuery(SQL);
			listUser = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listUser = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listUser;
	}

	public User reloadUser(int input) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		User rs = null;
		try {
			String sql = "FROM User WHERE ID = :id";
			Query query = session.createQuery(sql);
			query.setParameter("id", input);
			rs = (User) query.getSingleResult();
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
	public List<User> onSearchData(User userIn) {
		listUser = new ArrayList<User>();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			String hql = "SELECT * FROM TBL_USERS WHERE 1=1";
			if (!userIn.getFullName().trim().equals("")) {
				hql += " AND FULLNAME LIKE N'%" + userIn.getFullName() + "%'";
			} else if (!userIn.getUserName().trim().equals("")) {
				hql += " AND USERNAME LIKE N'%" + userIn.getUserName() + "%'";
			} else if (userIn.getRole() != null) {
				hql += " AND ROLE = " + userIn.getRole();
			} else if (!userIn.getEmail().trim().equals("")) {
				hql += " AND EMAIL LIKE N'%" + userIn.getEmail() + "%'";
			} else if (userIn.getGender() != null) {
				hql += " AND GENDER = " + userIn.getGender();
			} else if (!userIn.getPhone().trim().equals("")) {
				hql += " AND PHONE LIKE '%" + userIn.getPhone() + "%'";
			} else if (userIn.getStatus() != null) {
				hql += " AND STATUS = " + userIn.getStatus();
			}
			Query query = session.createNativeQuery(hql, User.class);
			listUser = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listUser = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listUser;
	}

	@Override
	public boolean InsertData(User userIns) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			userIns.setCreated(new Date());
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
	public boolean DeleteData(User userDel) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
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
	public boolean UpdateData(User userUpdate) {
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
