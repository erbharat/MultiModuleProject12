package com.expensetracker.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.expensetracker.gui.pojo.Job;
import com.expensetracker.gui.pojo.ReportRequest;
import com.expensetracker.gui.util.DateUtil;
import com.expensetracker.logger.Logger;
import com.expensetracker.model.Purchase;
import com.expensetracker.model.User;
import com.expensetracker.util.HibernateUtil;

public class PurchaseDao implements GenericDao {

	private static PurchaseDao purchaseDao = new PurchaseDao();;
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public static PurchaseDao getPurchaseDao() {
		return purchaseDao;
	}

	@Override
	public Purchase read(Object purchaseId) throws Exception {
		Purchase purchase = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				purchase = (Purchase) session.get(Purchase.class,
						(int) purchaseId);

			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return purchase;
	}

	@Override
	public void delete(String purchaseId) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				session.delete(purchaseId, Purchase.class);
			}
			session.getTransaction().commit();

		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public void update(String purchaseId, String userId) throws Exception {
		User user = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				user = (User) session.get(Purchase.class, userId);
				user.setEmailId("rajivranjan8589@gmail.com");
				session.update(user);

			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	@Override
	public void save(Object purchase) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Purchase purchases = (Purchase) purchase;
		try {
			if (session != null) {
				session.save(purchase);
			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Purchase> getPurchaseReport(Job job) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Purchase.class);

		List<Purchase> purchaseList = null;

		try {
			if (session != null) {
				String from = ((ReportRequest) job.getJob()).getFromDate();
				String to = ((ReportRequest) job.getJob()).getToDate();
			/*	String from = DateFormatUtils.format(fromdate, "yyyy-mm-dd");
				String to = DateFormatUtils.format(todate, "yyyy-mm-dd");
				*/Logger.logMessage("PurchaseDao.getPurchaseList(.) : fromdate  " + from + " to date " + to );
				purchaseList = (List<Purchase>) criteria.add(
						Restrictions.between("purchaseDate", from, to)).list();

			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return purchaseList;
	}

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub
		
	}
}
