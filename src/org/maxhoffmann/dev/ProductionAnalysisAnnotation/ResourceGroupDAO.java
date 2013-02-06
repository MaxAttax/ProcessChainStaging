package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.maxhoffmann.dev.util.HibernateUtil;

public class ResourceGroupDAO {
	
	private static final Logger LOGGER = Logger.getLogger(ResourceGroupDAO.class);
	
	@SuppressWarnings("unchecked")
	public void listResourceGroups(int whereSourceId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from ResourceGroup where sourceId = :sourceId");
			query.setParameter("sourceId", whereSourceId);
			List<ResourceGroup> resourceGroups = query.list();
			LOGGER.info("\n");
			
			for ( ResourceGroup resourceGroup : resourceGroups ) {
				/*
				 * In order to list the foreign key (Primary Key of "Project" in the ResourceGroups List
				 * the get method command has to call the getId() method of the class project
				 */
				int resourceGroupId = resourceGroup.getResourceGroupId();
				int sourceId = resourceGroup.getSource().getId();
				String label = resourceGroup.getLabel();
				String description = resourceGroup.getDescription();
				LOGGER.info("ID: " + resourceGroupId 
						+ "\t  Source-ID: " + sourceId
						+ "    Label: " + label + "    Description: " + description);
			}
			transaction.commit();
		} catch ( HibernateException e ) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public void addResourceGroup(String description, String label, Integer soruceId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Source source = new Source();			// An empty project has to be initialized in order to to
														// add the foreign key constraint of the project to the ResourceGroup
			
			ResourceGroup newResourceGroup = new ResourceGroup();
			newResourceGroup.setDescription(description);
			newResourceGroup.setLabel(label);
			
			newResourceGroup.setSource(source); 		// Add the project property to ResourceGroup -> ProjectId constraint
			// source.setId(sourceId);						// assign a certain projectId (type long) to the added project
			
			session.save(newResourceGroup);				// save the new data set
			transaction.commit();						// commit the transaction execution to the database
			
		} catch (HibernateException e) {				// common hibernate procedure to catch errors
			transaction.rollback();						
			e.printStackTrace();
			
		} finally {										// close the session in both cases
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String searchResourceGroupDescription(String label) {
		String resourceGroupDescription = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			
			Query query = session.createQuery("from ResourceGroup");
			List<ResourceGroup> resourceGroupEntry = query.list();
							
			for (ResourceGroup resourceGroup : resourceGroupEntry) {
				if (resourceGroup.getLabel().equals(label)) {
					resourceGroupDescription = resourceGroup.getDescription();
				}
			}
			
			/*
			Query query = session.createQuery("select rg.Description from resourcegroup rg where Label := setResourceGroupLabel");
			query.setParameter("setResourceGroupLabel", label);
			resourceGroupDescription = (String) query.executeUpdate();
			*/
			
		} catch ( HibernateException e ) {
			transaction.rollback();						
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		if (resourceGroupDescription == null) {
			LOGGER.info("\nThe label expression '" + label + "' doesn't exist!");
		} else {
			LOGGER.info("\nThe label expression '" + label + "' is equivalent to the descpription '" + resourceGroupDescription + "'.");		}
		
		return resourceGroupDescription;
	}
	
	
}
