package com.expensetracker.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.expensetracker.model.IndividualShare;
import com.expensetracker.util.HibernateUtil;

public class IndividualShareDao implements GenericDao{
	
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	private static IndividualShareDao individualShareDao = new IndividualShareDao();

	public static IndividualShareDao getIndividualShareDao() {
		// TODO Auto-generated method stub
		return individualShareDao;
	}
	public Object read(Object shareId) throws Exception {
		IndividualShare individualShare = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session!=null) {
				individualShare = (IndividualShare) session.get(IndividualShare.class, (int)shareId);
				
			}
			session.getTransaction().commit();
		} finally{
			if (session!=null) {
				session.close();
			}
		}
		
		return individualShare;
	}

	public void delete(String shareId) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if(session!=null){
				session.delete(shareId, IndividualShare.class);
			}
			session.getTransaction().commit();
		} finally{
			if(session!=null){
				session.close();
			}
		}
	}

	public void update(String shareId, String key) throws Exception {
		IndividualShare individualShare = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				individualShare = (IndividualShare) session.get(IndividualShare.class, shareId);
				individualShare.setAmount(Double.parseDouble(key));
				session.update(individualShare);
			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}

	public void save(Object individualShare) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if(session!=null){
				session.save(individualShare);
			}
			session.getTransaction().commit();
			
		} finally{
			if(session!=null){
				session.close();
			}
		}
	}
	public void update(Object object) {
		// TODO Auto-generated method stub
		
	}
}
