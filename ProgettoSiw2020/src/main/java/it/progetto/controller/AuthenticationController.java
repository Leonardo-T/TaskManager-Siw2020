package it.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

import it.progetto.controller.session.SessionData;
import it.progetto.model.Credentials;
import it.progetto.model.User;
import it.progetto.service.CredentialsService;
import it.progetto.service.UserService;

@Controller
public class AuthenticationController {
	
	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	UserValidator userValidator;
	
	@Autowired
	CredentialsValidator credentialsValidator;
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	UserService userService;
	
	
	/*(1) Caso d'uso: Registrazione utente */
	@RequestMapping(value = { "/users/register" }, method = RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("credentialsForm", new Credentials());
		return "registerUser";
	}
	
	@RequestMapping(value = { "/users/register" }, method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("userForm")User user,
							   BindingResult userBindingResult,
							   @Valid @ModelAttribute("credentialsForm") Credentials credentials,
							   BindingResult credentialsBindingResult,
							   Model model) {
		
		this.userValidator.validate(user, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);
		
		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "index";
		}
		
		return "registerUser";
		
	}
	/*(1)*/

}
