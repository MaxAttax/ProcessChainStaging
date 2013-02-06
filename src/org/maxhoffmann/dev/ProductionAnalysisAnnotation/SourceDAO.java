package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.maxhoffmann.dev.ProductionAnalysisAnnotation.Source;
import org.maxhoffmann.dev.util.HibernateUtil;


public class SourceDAO {
	
	private static final Logger LOGGER = Logger.getLogger(SourceDAO.class);
	
	@SuppressWarnings("unchecked")
	public void listSources() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Source");
			List<Source> sources = query.list();
			LOGGER.info("\n");
			for (Source source : sources) {
				int sourceId = source.getId();
				String sourceStatus = source.getStatus();
				LOGGER.info("ID:\t" + sourceId + "\t  Source-Status:   " + sourceStatus);
			}
			transaction.commit();
		} catch ( HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public Integer addSource(String projectName) {
		Session sessionAdd = HibernateUtil.getSessionFactory().openSession();
		Transaction transactionAdd = null;
		Integer newProjectId = null;
		try {
			transactionAdd = sessionAdd.beginTransaction();
			Source addStatus = new Source();
			addStatus.setStatus(projectName);
			newProjectId = (Integer) sessionAdd.save(addStatus);
			transactionAdd.commit();
		} catch (HibernateException e) {
			transactionAdd.rollback();
			e.printStackTrace();
		} finally {
			sessionAdd.close();
		}
		return newProjectId;
	}
	
	public void deleteProject(Long deleteProjectId) {
		Session sessionDelete = HibernateUtil.getSessionFactory().openSession();
		Transaction transactionDelete = null;
		try {
			transactionDelete = sessionDelete.beginTransaction();
			Source projectDelete = (Source) sessionDelete.get(Source.class, deleteProjectId);
			sessionDelete.delete(projectDelete);
			transactionDelete.commit();
		} catch ( HibernateException e ) {
			transactionDelete.rollback();
			e.printStackTrace();
		} finally {
			sessionDelete.close();
		}
	}
	
	public void deleteProjectByStatus(String statusToDelete) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "delete from Project where Status = :setProjectStatus";
			Query query = session.createQuery(hql);
			query.setParameter("setProjectStatus", statusToDelete);
			query.executeUpdate();
			LOGGER.info("SQL Query: " + hql);
			transaction.commit();
		} catch ( HibernateException e ) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}
	
}
