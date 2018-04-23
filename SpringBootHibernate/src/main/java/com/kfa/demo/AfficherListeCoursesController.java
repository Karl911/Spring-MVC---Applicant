package com.kfa.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kfa.demo.dao.IServiceListeCourses;
import com.kfa.demo.entity.Course;

@Controller
@RequestMapping(value="/afficherListeCourses")
public class AfficherListeCoursesController {
	
	@Autowired
	private IServiceListeCourses service;
	
	@RequestMapping(method=RequestMethod.GET)
	public String afficher(ModelMap pModel) 
	{
		final List<Course> lListeCourses = service.rechercherCourses();
		pModel.addAttribute("listeCourses", lListeCourses);
		return "listeCourses";
	}
	
	@RequestMapping(value="/error", method=RequestMethod.POST)
	public String afficherError(ModelMap pModel) 
	{
		return "error";
	}

}
