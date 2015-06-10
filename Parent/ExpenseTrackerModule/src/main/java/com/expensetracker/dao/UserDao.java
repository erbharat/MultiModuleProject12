package com.expensetracker.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.expensetracker.model.User;
import com.expensetracker.util.HibernateUtil;

public class UserDao implements GenericDao {

	private static UserDao userDao = new UserDao();

	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public static UserDao getUserDao() {
		return userDao;
	}

	public User read(Object userName) throws Exception {
		User user = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				user = (User) session.get(User.class, (String) userName);

			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return user;
	}

	public void delete(String userName) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				session.delete(userName, User.class);
			}
			session.getTransaction().commit();

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void update(String userName, String password) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				User user = (User)session.get(User.class, (String) userName);
				user.setPassword(password);
				session.update(user);
			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public void updateUserStatus(String userName, byte status) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				User user = (User)session.get(User.class, (String) userName);
				user.setStatus(status);
				session.update(user);
			}
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void save(Object object) throws Exception {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				session.save(object);
			}
			session.getTransaction().commit();

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public List<User> getUserList() throws Exception{
		List<User> userList = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				Criteria criteri = session.createCriteria(User.class);
				//criteri.add(Restrictions.));
				userList = (List<User>) criteri.list();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return userList;
	}

	public List<User> getUserListForRent() throws Exception{
		List<User> userList = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				Criteria criteri = session.createCriteria(User.class);
				criteri.add(Restrictions.ne("status", 2));
				userList = (List<User>) criteri.list();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return userList;
	}
	public List<User> getActiveUserList() throws Exception {
		List<User> userList = null;
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			if (session != null) {
				Criteria criteri = session.createCriteria(User.class);
				criteri.add(Restrictions.eq("status", 1));
				userList = (List<User>) criteri.list();
			}

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return userList;
	}

	@Override
	public void update(Object object) {
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
}
