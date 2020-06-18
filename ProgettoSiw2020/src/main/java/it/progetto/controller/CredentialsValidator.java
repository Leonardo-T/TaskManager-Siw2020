package it.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.progetto.model.Credentials;
import it.progetto.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator{
	
	@Autowired
	CredentialsService credentialsService;
	
	final Integer MAX_USERNAME_LENGTH = 20;
	final Integer MIN_USERNAME_LENGTH = 4;
	final Integer MAX_PASSWORD_LENGTH = 20;
	final Integer MIN_PASSWORD_LENGTH = 6;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials = (Credentials) target;
		String userName = credentials.getUserName().trim();
		String password = credentials.getPassword().trim();
		
		if(userName.isBlank())	
			errors.rejectValue("userName", "required");
		else if (userName.length() < MIN_USERNAME_LENGTH || userName.length() > MAX_USERNAME_LENGTH)
			errors.rejectValue("userName", "size");
		else if (this.credentialsService.getCredentials(userName) != null)
			errors.rejectValue("userName", "duplicate");
		
		if(password.isBlank())
			errors.rejectValue("password", "required");
		else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
			errors.rejectValue("password", "size");
	}

}
