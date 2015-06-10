package com.expensetracker.dao;

import java.util.List;





import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.expensetracker.gui.pojo.Job;
import com.expensetracker.model.AggregateShare;
import com.expensetracker.util.HibernateUtil;

public class AggregateShareDao implements GenericDao {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	private static AggregateShareDao aggregateShareDao = new AggregateShareDao();

	public static AggregateShareDao getAggregateShareDao() {
		// TODO Auto-generated method stub
		return aggregateShareDao;
	}

	public Object read(Object userName) throws Exception {
		AggregateShare aggregateShare = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session!=null) {
				aggregateShare = (AggregateShare) session.get(AggregateShare.class, (String)userName);
			}
		} finally{
			if (session!=null) {
				session.close();
			}
		}
		
		return aggregateShare;
	}

	public void delete(String userName) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if(session!=null){
				session.delete(userName, AggregateShare.class);
			}
			session.getTransaction().commit();
		} finally{
			if(session!=null){
				session.close();
			}
		}
	}
	
	public void update(String userName, String currentShare) throws Exception {
		AggregateShare aggregateShare = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				aggregateShare = (AggregateShare) session.get(AggregateShare.class, userName);
				aggregateShare.setCurrentShare(Double.parseDouble(currentShare));
				session.update(aggregateShare);

			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public void update(Object object)  {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				session.update(object);

			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public void save(Object object) throws Exception {		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if(session!=null){
				session.save(object);
				session.flush();
			}
			session.getTransaction().commit();
		} finally{
			if(session!=null){
				session.close();
			}
		}
	}

	public List<AggregateShare> getShareReport(Job job) throws Exception{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(AggregateShare.class);

		List<AggregateShare> aggregateShareList = null;

		try {
			if (session != null) {
				aggregateShareList = (List<AggregateShare>) criteria
						.list();

			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return aggregateShareList;
	}
}
