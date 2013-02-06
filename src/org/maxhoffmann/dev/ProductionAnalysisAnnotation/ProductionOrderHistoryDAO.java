package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import java.util.List;

// import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.maxhoffmann.dev.util.HibernateUtil;

public class ProductionOrderHistoryDAO {
	
	// private static final Logger LOGGER = Logger.getLogger(ProductionOrderHistoryDAO.class);
	
	@SuppressWarnings("unchecked")
	public List<ProductionOrderHistory> listProductionOrderHistories() {
		List<ProductionOrderHistory> productionOrderResult = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from ProductionOrderHistory");
			query.setMaxResults(600);
			List<ProductionOrderHistory> productionOrderHistories = query.list();
			productionOrderResult = productionOrderHistories;
			/*
			LOGGER.info("\n");
			for ( ProductionOrderHistory productionOrderHistory : productionOrderHistories ) {
				int productionOrderHistoryId = productionOrderHistory.getProductionOrderHistoryId();
				int workplanNo = productionOrderHistory.getWorkplanNo();
				Date startProduction = productionOrderHistory.getStartProduction();
				double operationTime = productionOrderHistory.getOperationTime();
				int orderId = productionOrderHistory.getOrder().getOrderId();
				int resourceGroupId = productionOrderHistory.getResourceGroup().getResourceGroupId();
				String resourceGroupLabel = productionOrderHistory.getResourceGroup().getLabel();
				LOGGER.info("ID: " + productionOrderHistoryId 
						+ "  Workplan No.: " + workplanNo 
						+ "  Start Production: " + startProduction
						+ "  Operation Time: " + operationTime 
						+ "  Order-ID: " + orderId 
						+ "  Order-No: " + productionOrderHistory.getOrder().getOrderNo()   
						+ "  ResourceGroup-ID: " + resourceGroupId
						+ "  ResourceGroup-Label: " + resourceGroupLabel);
			}
			LOGGER.info("\n");
			*/
		} catch ( HibernateException e ) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return productionOrderResult;
	}	
}