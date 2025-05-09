package com.at.controller;

import java.sql.Connection;

import com.at.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class BaseController {

	protected Connection conn = null;
	SessionFactory sessionFactory;

	public void save(Object obj) {

		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(obj);	
		transaction.commit();
		session.close();
	}
	
	public void delete(Object obj) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.joinTransaction();
		session.delete(obj);
		session.flush();
		transaction.commit();
		session.close();
	}

	public Session getSession() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		return sessionFactory.openSession();	// TODO potentially try: Session session = HibernateUtil.getSessionFactory().openSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}

/*
 * A session factory represents a database. 
 * A session represents a database connection.
 * 
 */
