package com.kfa.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kfa.model.ApplicantModel;

@Component
public class ApplicantValidator implements Validator {
	
	private EmailValidator emailValidator = EmailValidator.getInstance();
	
	public boolean supports(Class<?> clazz) {
		return clazz == ApplicantModel.class;
	}

	public void validate(Object target, Errors errors) {
		ApplicantModel applicantInfo = (ApplicantModel) target;
		
		ValidationUtils.rejectIfEmpty(errors,"name", "NotEmpty.applicantForm.name");
		ValidationUtils.rejectIfEmpty(errors,"name", "NotEmpty.applicantForm.email");
		ValidationUtils.rejectIfEmpty(errors,"name", "NotEmpty.applicantForm.position");
		ValidationUtils.rejectIfEmpty(errors,"name", "NotEmpty.applicantForm.gender");
		
		if (!emailValidator.isValid(applicantInfo.getEmail()))
		{
			errors.rejectValue("email", "Pattern.applicantForm.email");
		}
		if (applicantInfo.getSkills() == null | applicantInfo.getSkills().length == 0)
		{
			errors.rejectValue("skills", "Select.applicantForm.skills");
		}		
	}
}
