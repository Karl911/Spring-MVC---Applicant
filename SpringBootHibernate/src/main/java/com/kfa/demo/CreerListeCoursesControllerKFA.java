package com.kfa.demo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kfa.demo.dao.IServiceListeCourses;
import com.kfa.demo.entity.Course;

@Controller
public class CreerListeCoursesControllerKFA {
	
	@Autowired
	private IServiceListeCourses service;
	
	@RequestMapping(value="/afficherCreationListeCoursesKFA", method = RequestMethod.GET)
	public String afficher(final ModelMap pModel) {
		final List<Course> lListeCourses = service.rechercherCourses();
		pModel.addAttribute("listeCourses", lListeCourses);
		if (pModel.get("creation") == null) {
		pModel.addAttribute("creation", new CreationForm());
		}
		return "creation";
	}
	
	@RequestMapping(value="creerCourse", method=RequestMethod.POST)
	public String creerCourse(@Valid @ModelAttribute(value="creation") final CreationForm pCreation,
			final BindingResult pBindingResult, final ModelMap pModel)
	{
		if (!pBindingResult.hasErrors())
		{
			final String lLibelle = pCreation.getLibelle();
			final Integer lIntQuantity = Integer.valueOf(pCreation.getQuantity());
			service.creerCourse(lLibelle, lIntQuantity);
		}
		return afficher(pModel);
	}

}
