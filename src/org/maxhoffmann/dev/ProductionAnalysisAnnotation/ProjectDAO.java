package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.maxhoffmann.dev.ProductionAnalysisAnnotation.Project;
import org.maxhoffmann.dev.util.HibernateUtil;


public class ProjectDAO {
	
	private static final Logger LOGGER = Logger.getLogger(ProjectDAO.class);
	
	@SuppressWarnings("unchecked")
	public void listProjects() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from Project");
			List<Project> projects = query.list();
			LOGGER.info("\n");
			for (Project project : projects) {
				int ProjectId = project.getId();
				String projectStatus = project.getStatus();
				LOGGER.info("ID: " + ProjectId + "  Projektstatus: " + projectStatus);
			}
			transaction.commit();
		} catch ( HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public Integer addProject(String projectName) {
		Session sessionAdd = HibernateUtil.getSessionFactory().openSession();
		Transaction transactionAdd = null;
		Integer newProjectId = null;
		try {
			transactionAdd = sessionAdd.beginTransaction();
			Project addStatus = new Project();
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
			Project projectDelete = (Project) sessionDelete.get(Project.class, deleteProjectId);
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
