package com.kfa.demo.dao;

import java.util.List;

import com.kfa.demo.entity.Course;

public interface IListeCoursesDao {

	public List<Course> rechercherCourse();
	void creerCourse(final Course course);
	
}
