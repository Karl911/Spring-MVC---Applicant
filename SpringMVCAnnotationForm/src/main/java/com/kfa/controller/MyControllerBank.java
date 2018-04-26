package com.kfa.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kfa.dao.BankAccountDAO;
import com.kfa.form.SendMoneyForm;
import com.kfa.model.BankAccountModel;
import com.kfa.validator.BankValidator;

@Controller
//Enable Hibernate Transaction.
@Transactional
//Need To use RedirectAttributes
@EnableWebMvc
public class MyControllerBank {
	
	@Autowired
	private BankAccountDAO bankAccountDao;
	
	@Autowired
	private BankValidator bankAccountValidator;
	
	@RequestMapping(value="/")
	public String homePage(Model model)
	{
		return bankAccountList(model);
	}
	
	@RequestMapping("/bankAccountList")
	public String bankAccountList(Model model) {
		List<BankAccountModel> list = bankAccountDao.listBankAccountsModels();
		
		// doc sur les streams : https://openclassrooms.com/courses/apprenez-a-programmer-en-java/manipulez-vos-donnees-avec-les-streams
		
		//List<String> strings = Arrays.asList("girafe", "chameau", "chat", "poisson", "cachalot");
		//strings.stream().filter(x->x.contains("cha")).map(x->x.substring(0,1).toUpperCase()+x.substring(1)).sorted().forEach(System.out::println);
		//list.stream().map(x->x.getName().substring(0, 1).toUpperCase()+x.getName().substring(1)).sorted().forEach(System.out::println);
		/*
		List<BankAccountModel> sortedList = list.stream().sorted(new java.util.Comparator<BankAccountModel>() {
			public int compare(BankAccountModel m1, BankAccountModel m2)
			{
				String initial1 = m1.getFullName().toUpperCase();
				String initial2 = m2.getFullName().toUpperCase();
				return initial1.compareTo(initial2);
			}
		}).collect(Collectors.toList());
		model.addAttribute("bankAccountInfos", sortedList);
		*/
		model.addAttribute("bankAccountInfos", list);
		return "bankAccountList";
	}
	
	   private String formBankAccount(Model model, BankAccountModel bankAccountInfo) {
		   
	       model.addAttribute("bankAccountForm", bankAccountInfo);
	 
	       if (bankAccountInfo.getId() == null) {
	           model.addAttribute("formTitle", "Create Bank Account");
	       } else {
	           model.addAttribute("formTitle", "Edit Bank Account");
	       }
	 
	       return "formBankAccount";
	   }
	 
	   @RequestMapping("/createBankAccount")
	   public String createBankAccount(Model model) {
	 
		   BankAccountModel bankAccountInfo = new BankAccountModel();
	 
	       return this.formBankAccount(model, bankAccountInfo);
	   }
	 
	   @RequestMapping("/editBankAccount")
	   public String editBankAccount(Model model, @RequestParam("id") Long id) {
	       BankAccountModel bankAccountInfo = null;
	       if (id != null) {
	    	   bankAccountInfo = this.bankAccountDao.findBankAccountModel(id);
	       }
	       if (bankAccountInfo == null) {
	           return "redirect:/bankAccountList";
	       }
	 
	       return this.formBankAccount(model, bankAccountInfo);
	   }
	 
	   @RequestMapping("/deleteBankAccount")
	   public String deleteBankAccount(Model model, @RequestParam("id") Long id) {
	       if (id != null) {
	           this.bankAccountDao.delete(id);
	       }
	       return "redirect:/bankAccountList";
	   }
	   
		@RequestMapping("/transfertMoney")
		public String transfertMoney(Model model)
		{
			List<BankAccountModel> list = bankAccountDao.listBankAccountsModels();
			model.addAttribute("bankAccountInfos", list);
			model.addAttribute("formTitle", "Transfert Money");
		   // model.addAttribute("bankAccountForm", list);
			//
			
			Map<Long, String> accountMap = new LinkedHashMap<Long, String>();
			for (BankAccountModel bam : list)
			{
				accountMap.put(bam.getId(), bam.getFullName());
			}
			model.addAttribute("accountMap", accountMap);
			
			SendMoneyForm form = new SendMoneyForm();
		    
	        model.addAttribute("sendMoneyForm", form);
					
			//return "transfertMoney";
			return "sendMoneyPage";
		}
		
		  // Save or update Applicant
		   // 1. @ModelAttribute bind form value
		   // 2. @Validated form validator
		   // 3. RedirectAttributes for flash value
		   @RequestMapping(value = "/saveTransfertMoney", method = RequestMethod.POST)
		   public String processTransfertMoney(Model model,  SendMoneyForm sendMoneyForm, final RedirectAttributes redirectAttributes)
		           //@ModelAttribute("SendMoneyForm") @Validated BankAccountModel bankAccountInfo, //
		           //BindingResult result), //
		   {
		    /*
		       if (result.hasErrors()) {
		    	   return this.formBankAccount(model, bankAccountInfo);
		       }
		       */
		       try {
		    	   this.bankAccountDao.transfertMoney(sendMoneyForm.getAmount(), sendMoneyForm.getFromAccountId(), sendMoneyForm.getToAccountId());
		       }
		       catch (Exception e){
		    	    model.addAttribute("errorMessage", "Error: " + e.getMessage());
		            return "/sendMoneyPage";
		       }
		       		 
		       // Important!!: Need @EnableWebMvc
		       // Add message to flash scope
		       redirectAttributes.addFlashAttribute("message", "Transfert Money Successful");
		 
		       return "redirect:/bankAccountList";
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
	 
	       if (target.getClass() == BankAccountModel.class) {
	           dataBinder.setValidator(bankAccountValidator);
	       }
	   }
	 
	   // Save or update Bank Account
	   // 1. @ModelAttribute bind form value
	   // 2. @Validated form validator
	   // 3. RedirectAttributes for flash value
	   @RequestMapping(value = "/saveBankAccount", method = RequestMethod.POST)
	   public String saveBankAccount(Model model, //
	           @ModelAttribute("bankAccountForm") @Validated BankAccountModel bankAccountInfo, //
	           BindingResult result, //
	           final RedirectAttributes redirectAttributes) {
	 
	       if (result.hasErrors()) {
	           return this.formBankAccount(model, bankAccountInfo);
	       }
	 
	       this.bankAccountDao.saveAccount(bankAccountInfo);
	 
	       // Important!!: Need @EnableWebMvc
	       // Add message to flash scope
	       redirectAttributes.addFlashAttribute("message", "Save Bank Account Successful");
	 
	       return "redirect:/bankAccountList";
	   }
}
