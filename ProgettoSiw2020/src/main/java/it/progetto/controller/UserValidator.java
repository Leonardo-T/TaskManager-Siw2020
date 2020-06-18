package it.progetto.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.progetto.model.User;

@Component
public class UserValidator implements Validator{
	
	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		String firstName = user.getFirstName().trim();
		String lastName = user.getLastName().trim();
		
		if(firstName.isBlank())
			errors.rejectValue("firstName", "required");
		else if(firstName.length() < MIN_NAME_LENGTH || firstName.length() > MAX_NAME_LENGTH)
			errors.rejectValue("firstName", "size");
		
		if(lastName.isBlank())
			errors.rejectValue("lastName", "required");
		else if(lastName.length() < MIN_NAME_LENGTH || lastName.length() > MAX_NAME_LENGTH)
			errors.rejectValue("lastName", "size");
		
	}

}
