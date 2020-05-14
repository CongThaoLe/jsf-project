package com.fis.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.District;
import com.fis.models.HibernateUtil;

@SuppressWarnings("unchecked")
public class QLDistrictDAO  {
	private List<District> listQuanHuyen = new ArrayList<>();
	private static final Logger LOGGER = Logger.getLogger(QLDistrictDAO.class);

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	public ArrayList<District> getDistrictByProvinceId() {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			String SQL = "FROM District ORDER BY DISTRICT_NAME";
			Query query = session.createQuery(SQL);
			listQuanHuyen = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listQuanHuyen = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return (ArrayList<District>) listQuanHuyen;
	}
}
