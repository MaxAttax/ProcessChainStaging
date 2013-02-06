package org.maxhoffmann.dev.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import org.maxhoffmann.dev.ProductionAnalysisAnnotation.Material;
import org.maxhoffmann.dev.ProductionAnalysisAnnotation.Order;
import org.maxhoffmann.dev.ProductionAnalysisAnnotation.ProductionOrderHistory;
import org.maxhoffmann.dev.ProductionAnalysisAnnotation.Source;
import org.maxhoffmann.dev.ProductionAnalysisAnnotation.ResourceGroup;


public class HibernateUtil {
	
	private static final SessionFactory sessionFactory;
	
	static {
		try {
			sessionFactory = new AnnotationConfiguration()
								 .configure()
								 .addPackage("org.maxhoffmann.dev.ProductionAnalysisAnnotation")
								 .addAnnotatedClass(Source.class)
								 .addAnnotatedClass(Material.class)
								 .addAnnotatedClass(Order.class)
								 .addAnnotatedClass(ResourceGroup.class)
								 .addAnnotatedClass(ProductionOrderHistory.class)
								 .buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed. Error: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}