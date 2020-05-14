package com.fis.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fis.entities.Province;
import com.fis.models.HibernateUtil;

@SuppressWarnings("unchecked")
public class QLProvinceDAO {
	private List<Province> listTp;
	private static final Logger LOGGER = Logger.getLogger(QLProvinceDAO.class);
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;
	
	public List<Province> onShowData() {
		listTp = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Province ORDER BY PROVINCE_NAME";
			Query query = session.createQuery(SQL);
			listTp = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listTp = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listTp;
	}
	
}
