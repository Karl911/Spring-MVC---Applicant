package com.kfa.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
// indique que le contr�leur traite les requ�tes GET dont l'URI est � /bonjour �.
@RequestMapping("/bonjour")
public class BonjourController {
	
	/*
	@RequestMapping(method=RequestMethod.GET)
	public String afficherBonjour(final ModelMap pModel)
	{
		pModel.addAttribute("personne", "Karl");
		return "bonjour";
	}
	*/
	@RequestMapping(method=RequestMethod.GET)
	public String afficherBonjour(final ModelMap pModel, @RequestParam(value="personne") final String pPersonne)
	{
		pModel.addAttribute("personne", pPersonne+"-"+"toto");
		return "bonjour";
	}
}
