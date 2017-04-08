package com.mango.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mango.dto.SignInDto;

public class SignInDtoValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return SignInDto.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "email", "email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "password", "password.empty");
	}

}
