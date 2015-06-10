package com.expensetracker.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.expensetracker.config.ResourceConfig;
import com.expensetracker.constants.Constants;
import com.expensetracker.logger.Logger;

public class HibernateUtil {
	private static ResourceConfig config = ResourceConfig.getInstance();
	private static SessionFactory sessionFactory=null;

	static Configuration cfg = new Configuration();
	
	
	
	public static SessionFactory getSessionFactory() {
		
		if(sessionFactory==null){
			//cfg.setProperty("", "org.hibernate.dialect.MySQLDialect");
			cfg.configure();
			cfg.setProperty(Constants.DRIVER_NAME_PROPERTY_KEY, config.getDriverName());
			cfg.setProperty(Constants.URL_PROPERTY_KEY, config.getDBConnectionURL());
			cfg.setProperty(Constants.USERNAME_PROPERTY_KEY, config.getDBUsername());
			cfg.setProperty(Constants.PASSWORD_PROPERTY_KEY, config.getDBPassword());
			
			sessionFactory = cfg.buildSessionFactory();
		}
		Logger.logMessage("HibernateUtil.getSessionFactory() : jdbc connection url " + cfg.getProperty(Constants.URL_PROPERTY_KEY));
		return sessionFactory;
	}	
}
