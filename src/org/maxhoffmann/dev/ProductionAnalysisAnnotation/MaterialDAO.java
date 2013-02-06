package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.maxhoffmann.dev.util.HibernateUtil;

public class MaterialDAO {
	
	private static final Logger LOGGER = Logger.getLogger(MaterialDAO.class);
	
	@SuppressWarnings("unchecked")
	public void listMaterial() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Material");
			query.setMaxResults(10);
			List<Material> materials = query.list();
			LOGGER.info("\n");
			for (Material material : materials) {
				int materialId = material.getId();
				int materialNo = material.getMaterialNo();
				String description = material.getDescription();
				LOGGER.info("ID: " + materialId + "  Material No: " + materialNo + "  Description: " + description);
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