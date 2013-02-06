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
	public void listOrders(int whereSourceId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Order where sourceId = :sourceId");
			query.setParameter("sourceId", whereSourceId);
			query.setMaxResults(10);
			List<Order> orders = query.list();
			LOGGER.info("\n");
			for (Order order : orders) {
				int orderId = order.getOrderId();
				int sourceId = order.getSource().getId();
				int materialId = order.getMaterial().getId();
				int orderNo = order.getOrderNo();
				String orderType = order.getOrderType();
				LOGGER.info("ID:\t" + orderId
						+ "\t  Source Id:\t" + sourceId
						+ "\t  Order No:  " + orderNo
						+ "\t  Order Type:  " + orderType 
						+ "\t  Material ID (FK):  " + materialId);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}