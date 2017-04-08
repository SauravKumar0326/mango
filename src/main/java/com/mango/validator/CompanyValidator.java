package com.mango.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mango.dto.CompanyDto;

public class CompanyValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean supports(Class<?> clazz) {
		return CompanyDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CompanyDto company = (CompanyDto) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "companyName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyPhone", "companyPhone.empty");
		if (!company.getEmail().matches(EMAIL_PATTERN)) {
			errors.rejectValue("Email", "Envalid Email");
		}
	}

}
