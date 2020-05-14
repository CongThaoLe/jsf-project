package com.fis.dao;

import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.fis.entities.OrderDetails;
import com.fis.models.HibernateUtil;

@SuppressWarnings("unchecked")
public class QLChiTietHoaDonDAO {
	private List<OrderDetails> listItem;
	private static final Logger LOGGER = Logger.getLogger(QLChiTietHoaDonDAO.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	public List<OrderDetails> onShowData() {
		listItem = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM OrderDetails ORDER BY ID DESC";
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

}
