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
import com.fis.entities.Order;
import com.fis.entities.OrderDetails;
import com.fis.entities.Product;
import com.fis.models.HibernateUtil;
import com.fis.utils.ExecuteObjectDAO;
import com.fis.utils.StringEx;

@SuppressWarnings("unchecked")
public class QLHoaDonDAO implements ExecuteObjectDAO<Order> {
	private List<Order> listItem;
	private static final Logger LOGGER = Logger.getLogger(QLHoaDonDAO.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session = null;
	private Transaction transaction = null;

	@Override
	public List<Order> onShowData() {
		listItem = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Order ORDER BY ID DESC";
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

	public Order reloadOrder(int input) {
		Order rs = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String sql = "FROM Order WHERE ID = :id";
			Query query = session.createQuery(sql);
			query.setParameter("id", input);
			rs = (Order) query.getSingleResult();
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
	public List<Order> onSearchData(Order userIn) {
		listItem = new ArrayList<Order>();
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String hql = "SELECT * FROM TBL_ORDERS WHERE 1=1";
			if (!StringEx.IsNullOrEmpty(userIn.getFullName())) {
				hql += " AND FULLNAME LIKE N'%" + userIn.getFullName() + "%'";
			} else if (!StringEx.IsNullOrEmpty(userIn.getOrderCode())) {
				hql += " AND ORDERCODE LIKE N'%" + userIn.getOrderCode() + "%'";
			} else if (!StringEx.IsNullOrEmpty(userIn.getPhone())) {
				hql += " AND PHONE = " + userIn.getPhone();
			} else if (!StringEx.IsNullOrEmpty(userIn.getAddress())) {
				hql += " AND ADDRESS LIKE N'%" + userIn.getAddress() + "%'";
			} else if (userIn.getTinhTp() != null) {
				hql += " AND PROVINCE = " + userIn.getTinhTp();
			} else if (userIn.getQuanHuyen() != null) {
				hql += " AND DISTRICT = " + userIn.getQuanHuyen();
			} else if (userIn.getMoney() != null) {
				hql += " AND MONEY = " + userIn.getMoney();
			} else if (userIn.getStatus() != null) {
				hql += " AND STATUS = " + userIn.getStatus();
			}
			Query query = session.createNativeQuery(hql, Order.class);
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

	public int SelectIdOfOrder(String orderCode) {
		int id = 0;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String sql = "SELECT ID FROM TBL_ORDERS WHERE ORDERCODE = :orderCode";
			Query query = session.createNativeQuery(sql);
			query.setParameter("orderCode", orderCode);
			id = (int) query.getSingleResult();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return id;
	}

	public void UpdateProductWhenPayment(Product item) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			item.setNumber(item.getNumber() - item.getCount());
			item.setNumberBuy(item.getNumberBuy() + item.getCount());
			session.update(item);
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
	}

	public Product getProductToUpdate(int id) {
		Product rs = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String sql = "SELECT * FROM TBL_PRODUCTS WHERE ID = :id";
			Query query = session.createNativeQuery(sql, Product.class);
			query.setParameter("id", id);
			rs = (Product) query.getSingleResult();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return rs;
	}

	@Override
	public boolean InsertData(Order item) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			item.setStatus(0);
			item.setOrderDate(new Date());
			session.save(item);
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

	public boolean InsertDataToOderDetail(OrderDetails item) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			session.save(item);
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
	public boolean DeleteData(Order item) {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		try {
			String sql = "DELETE FROM TBL_ORDERS WHERE ID = :ID";
			Query query = session.createNativeQuery(sql);
			query.setParameter("ID", item.getId());
			query.executeUpdate();
			transaction.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error("Lỗi :", e);
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean UpdateData(Order item) {
		Query query = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String sql = "UPDATE TBL_ORDERS SET " + " STATUS =:p_STATUS" + " WHERE ID =:ID";

			query = session.createNativeQuery(sql);
			query.setParameter("ID", item.getId());
			query.setParameter("p_STATUS", item.getStatus());
			query.executeUpdate();
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

	public Customer getTenKhachHangById(int id) {
		Customer cus = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Customer WHERE ID = " + id;
			Query query = session.createQuery(SQL, Customer.class);
			if (query.getSingleResult() != null) {
				cus = (Customer) query.getSingleResult();
			}
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			cus = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return cus;
	}

	public List<Order> showListOrder(int cusId) {
		List<Order> listOrder = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Order WHERE CUSTOMERID = " + cusId + " ORDER BY ID DESC ";
			Query query = session.createQuery(SQL);
			listOrder = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listOrder = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listOrder;
	}

	public List<OrderDetails> showListDetail(int orderId) {
		List<OrderDetails> listOrder = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM OrderDetails WHERE ORDERID = " + orderId + " ORDER BY ID DESC ";
			Query query = session.createQuery(SQL);
			listOrder = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			listOrder = null;
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return listOrder;
	}

	public Product getProductById(int id) {
		Product product = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			String SQL = "FROM Product WHERE ID = " + id;
			Query query = session.createQuery(SQL, Product.class);
			product = (Product) query.getSingleResult();
			transaction.commit();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
		}
		return product;
	}

}
