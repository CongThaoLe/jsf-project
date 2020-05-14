package com.fis.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.Customer;
import com.fis.models.HibernateUtil;
import com.fis.utils.ExecuteObjectDAO;

@SuppressWarnings("unchecked")
public class QLKhacHangDAO implements ExecuteObjectDAO<Customer> {
	private List<Customer> listUser;
	private static final Logger LOGGER = Logger.getLogger(QLKhacHangDAO.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	@Override
	public List<Customer> onShowData() {
		listUser = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Customer ORDER BY ID DESC";
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

	public Customer checkExisted(String username, String password) {
		Customer rs = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Customer WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
			Query query = session.createQuery(SQL);
			List<Customer> list = new ArrayList<>();
			list = query.getResultList();
			if (!list.isEmpty()) {
				rs = list.get(0);
			}
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
		return rs;

	}

	public Customer checkExistedUsername(String username) {
		Customer rs = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Customer WHERE USERNAME = '" + username + "'";
			Query query = session.createQuery(SQL);
			List<Customer> list = new ArrayList<>();
			list = query.getResultList();
			if (!list.isEmpty()) {
				rs = list.get(0);
			}
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
		return rs;

	}

	@Override
	public List<Customer> onSearchData(Customer userIn) {
		listUser = new ArrayList<Customer>();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			String hql = "SELECT * FROM TBL_CUSTOMERS WHERE 1=1";
			if (!userIn.getFullName().trim().equals("")) {
				hql += " AND FULLNAME LIKE N'%" + userIn.getFullName() + "%'";
			} else if (!userIn.getUserName().trim().equals("")) {
				hql += " AND USERNAME LIKE N'%" + userIn.getUserName() + "%'";
			} else if (!userIn.getEmail().trim().equals("")) {
				hql += " AND EMAIL LIKE N'%" + userIn.getEmail() + "%'";
			} else if (!userIn.getAddress().trim().equals("")) {
				hql += " AND ADDRESS LIKE N'%" + userIn.getAddress() + "%'";
			} else if (userIn.getGender() != null) {
				hql += " AND GENDER = " + userIn.getGender();
			} else if (userIn.getBirthDay() != null) {
				hql += " AND BIRTHDAY = " + userIn.getBirthDay();
			} else if (!userIn.getPhone().trim().equals("")) {
				hql += " AND PHONE LIKE '%" + userIn.getPhone() + "%'";
			} else if (userIn.getStatus() != null) {
				hql += " AND STATUS = " + userIn.getStatus();
			}
			Query query = session.createNativeQuery(hql, Customer.class);
			listUser = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
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
	public boolean InsertData(Customer userIns) {
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
	public boolean DeleteData(Customer userDel) {
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
	public boolean UpdateData(Customer userUpdate) {
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

	public Customer checkInforCus(int cusId) {
		Customer rs = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Customer WHERE ID = '" + cusId + "'";
			Query query = session.createQuery(SQL, Customer.class);
			rs = (Customer) query.getSingleResult();
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

}
