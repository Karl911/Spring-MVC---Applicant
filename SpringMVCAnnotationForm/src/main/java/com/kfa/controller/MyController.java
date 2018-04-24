package com.kfa.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kfa.dao.ApplicantDAO;
import com.kfa.model.ApplicantModel;
import com.kfa.validator.ApplicantValidator;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class MyController {
	
	@Autowired
	private ApplicantDAO applicantDao;
	
	@Autowired
	private ApplicantValidator applicantValidator;
	
	@RequestMapping(value="/")
	public String homePage(Model model)
	{
		return applicantList(model);
	}
	
	@RequestMapping("/applicantList")
	public String applicantList(Model model) {
		List<ApplicantModel> list = applicantDao.listApplicantModels();
		
		// doc sur les streams : https://openclassrooms.com/courses/apprenez-a-programmer-en-java/manipulez-vos-donnees-avec-les-streams
		
		//List<String> strings = Arrays.asList("girafe", "chameau", "chat", "poisson", "cachalot");
		//strings.stream().filter(x->x.contains("cha")).map(x->x.substring(0,1).toUpperCase()+x.substring(1)).sorted().forEach(System.out::println);
		//list.stream().map(x->x.getName().substring(0, 1).toUpperCase()+x.getName().substring(1)).sorted().forEach(System.out::println);
		List<ApplicantModel> sortedList = list.stream().sorted(new java.util.Comparator<ApplicantModel>() {
			public int compare(ApplicantModel m1, ApplicantModel m2)
			{
				String initial1 = m1.getName().toUpperCase();
				String initial2 = m2.getName().toUpperCase();
				return initial1.compareTo(initial2);
			}
		}).collect(Collectors.toList());
		model.addAttribute("applicantInfos", sortedList);
		return "applicantList";
	}

	@RequestMapping(value="applicantList/{position}", method = RequestMethod.GET)
	public String applicantList(Model model, @PathVariable("position") String pPosition) {
		List<ApplicantModel> list = null;
		if (pPosition == null)
		{
			list = applicantDao.listApplicantModels();
		}
		else {
			list = applicantDao.listApplicantModelByPosition(pPosition);
		}
		model.addAttribute("applicantInfos", list);
		return "applicantList";
	   }
	 private Map<String, String> dataForPositions() {
	       Map<String, String> positionMap = new LinkedHashMap<String, String>();
	       positionMap.put("Developer", "Developer");
	       positionMap.put("Leader", "Leader");
	       positionMap.put("Tester", "Tester");
	       return positionMap;
	   }
	 
	   private List<String> dataForSkills() {
	       List<String> list = new ArrayList<String>();
	       list.add("Java");
	       list.add("C/C++");
	       list.add("C#");
	       return list;
	   }
	 
	   private String formApplicant(Model model, ApplicantModel applicantInfo) {
	       model.addAttribute("applicantForm", applicantInfo);
	 
	       Map<String, String> positionMap = this.dataForPositions();
	 
	       model.addAttribute("positionMap", positionMap);
	 
	       List<String> list = dataForSkills();
	       model.addAttribute("skills", list);
	 
	       if (applicantInfo.getId() == null) {
	           model.addAttribute("formTitle", "Create Applicant");
	       } else {
	           model.addAttribute("formTitle", "Edit Applicant");
	       }
	 
	       return "formApplicant";
	   }
	 
	   @RequestMapping("/createApplicant")
	   public String createApplicant(Model model) {
	 
	       ApplicantModel applicantInfo = new ApplicantModel();
	 
	       return this.formApplicant(model, applicantInfo);
	   }
	 
	   @RequestMapping("/editApplicant")
	   public String editApplicant(Model model, @RequestParam("id") String id) {
	       ApplicantModel applicantInfo = null;
	       if (id != null) {
	           applicantInfo = this.applicantDao.findApplicantModel(id);
	       }
	       if (applicantInfo == null) {
	           return "redirect:/applicantList";
	       }
	 
	       return this.formApplicant(model, applicantInfo);
	   }
	 
	   @RequestMapping("/deleteApplicant")
	   public String deleteApplicant(Model model, @RequestParam("id") String id) {
	       if (id != null) {
	           this.applicantDao.deleteApplicant(id);
	       }
	       return "redirect:/applicantList";
	   }
	 
	   // Set a form validator
	   @InitBinder
	   protected void initBinder(WebDataBinder dataBinder) {
	       // Form target
	       Object target = dataBinder.getTarget();
	       if (target == null) {
	           return;
	       }
	       System.out.println("Target=" + target);
	 
	       if (target.getClass() == ApplicantModel.class) {
	           dataBinder.setValidator(applicantValidator);
	       }
	   }
	 
	   // Save or update Applicant
	   // 1. @ModelAttribute bind form value
	   // 2. @Validated form validator
	   // 3. RedirectAttributes for flash value
	   @RequestMapping(value = "/saveApplicant", method = RequestMethod.POST)
	   public String saveApplicant(Model model, //
	           @ModelAttribute("applicantForm") @Validated ApplicantModel applicantInfo, //
	           BindingResult result, //
	           final RedirectAttributes redirectAttributes) {
	 
	    
	       if (result.hasErrors()) {
	           return this.formApplicant(model, applicantInfo);
	       }
	 
	       this.applicantDao.saveApplicant(applicantInfo);
	 
	       // Important!!: Need @EnableWebMvc
	       // Add message to flash scope
	       redirectAttributes.addFlashAttribute("message", "Save Applicant Successful");
	 
	       return "redirect:/applicantList";
	   }
}
