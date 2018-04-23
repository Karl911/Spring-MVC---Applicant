package com.kfa.demo;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@SessionAttributes({ "container" })
// indique que le contr�leur traite les requ�tes GET dont l'URI est � /bonjour �.

@RequestMapping(value="rest")
public class BonjourRestController {
	
	@RequestMapping(value="rest/{personne}", method=RequestMethod.GET)
	public String afficherBonjour(@PathVariable("personne") String personne)
	{
		return personne;
	}
	/*
	@RequestMapping(value="m05/{a}/x/{b}", method = RequestMethod.GET)
	public Map<String, Object> m05 (@PathVariable("a") String a, @PathVariable("b") String b)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", a);
		map.put("b", b);
		return map;
	}
	*/
}
