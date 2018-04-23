package com.kfa.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfa.demo.entity.Course;

@Service
public class ServiceListeCoursesImpl implements IServiceListeCourses{
	
	@Autowired
	private IListeCoursesDao dao;
	
	@Transactional(readOnly=true)
	public List<Course> rechercherCourses()
	{
		return dao.rechercherCourse();
	}

	@Transactional
	public void creerCourse(final String libelle, final Integer quantity) 
	{
		final Course course = new Course();
		course.setLibelle(libelle);
		course.setQuantity(quantity);
		dao.creerCourse(course);
	}

}
