package com.kfa.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kfa.demo.entity.Course;

@Repository
public class ListeCoursesImpl implements IListeCoursesDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	public Course findById(Long id)
	{
		return this.entityManager.find(Course.class, id);
	}
	*/
	
	public List<Course> rechercherCourse() {
		
		Session session = sessionFactory.getCurrentSession();
		
		final CriteriaBuilder lCriteriaBuilder = session.getCriteriaBuilder();
		//final CriteriaBuilder lCriteriaBuilder = entityManager.getCriteriaBuilder();
		
		final CriteriaQuery<Course> lCriteriaQuery = lCriteriaBuilder.createQuery(Course.class);
		final Root<Course> lRoot = lCriteriaQuery.from(Course.class);
		lCriteriaQuery.select(lRoot);
		
		final Query<Course> lTypedQuery = session.createQuery(lCriteriaQuery);
		//final TypedQuery<Course> lTypedQuery = entityManager.createQuery(lCriteriaQuery);
		
		return lTypedQuery.getResultList();
	}
	
	public void creerCourse(final Course course)
	{
		entityManager.persist(course);
	}

}
