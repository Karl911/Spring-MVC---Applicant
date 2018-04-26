package com.kfa.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kfa.model.BankAccountModel;


@Component
public class BankValidator implements Validator {
	
	
	public boolean supports(Class<?> clazz) {
		return clazz == BankAccountModel.class;
	}

	public void validate(Object target, Errors errors) {
		BankAccountModel bankAccountInfo = (BankAccountModel) target;
		
		ValidationUtils.rejectIfEmpty(errors,"fullName", "NotEmpty.bankAccountForm.fullName");
		ValidationUtils.rejectIfEmpty(errors,"balance", "NotEmpty.bankAccountForm.balance");		
	}
}
