package com.kfa.demo.dao;

import java.util.List;

import com.kfa.demo.entity.Course;

public interface IServiceListeCourses {
	
	List<Course> rechercherCourses();
	void creerCourse(final String libelle, final Integer quantity);

}
