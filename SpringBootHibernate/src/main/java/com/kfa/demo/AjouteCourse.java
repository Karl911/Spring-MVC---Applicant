package com.kfa.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.kfa.demo.entity.Course;
	 
public class AjouteCourse {
	public static void main(String[] args) {
		Configuration config=new Configuration();
		config.configure();
		SessionFactory factory=config.buildSessionFactory();
		Session session=factory.openSession();
		
		Transaction txn=session.beginTransaction();
		Course course = new Course(8,"poivre", 10);
		session.saveOrUpdate(course);
		txn.commit();
	}
}
