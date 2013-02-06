package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.maxhoffmann.dev.util.HibernateUtil;

public class OrderDAO {
	
	private static final Logger LOGGER = Logger.getLogger(OrderDAO.class);
	
	@SuppressWarnings("unchecked")
	public void listOrders() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Order");
			query.setMaxResults(10);
			List<Order> orders = query.list();
			LOGGER.info("\n");
			for ( Order order : orders ) {
				Integer orderId = order.getOrderId();
				Integer materialId = order.getMaterial().getId();
				Integer orderNo = order.getOrderNo();
				String orderType = order.getOrderType();
				LOGGER.info("ID: " + orderId + "  Order No: " + orderNo + "  Order Type: " + orderType + "  Material ID (FK): " + materialId);
			}
			transaction.commit();
		} catch ( HibernateException e ) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
}