package it.progetto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.progetto.controller.session.SessionData;
import it.progetto.model.Credentials;
import it.progetto.model.User;
import it.progetto.service.CredentialsService;
import it.progetto.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = {"/home"}, method = RequestMethod.GET)
	public String home(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		return "home";
	}
	
	
	
	@RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
	public String admin(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		return "admin";
	}
	
	
	
	@RequestMapping(value = {"/admin/users"}, method = RequestMethod.GET)
	public String usersList(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", allCredentials);
		return "allUsers";
	}
	
	@RequestMapping(value = {"/admin/users/{username}/delete"}, method = RequestMethod.POST)
	public String removeUser(Model model, @PathVariable String userName) {
		this.credentialsService.deleteCredentials(userName);
		return "redirect:/admin/users";
	}
	
	
	/*(1) Caso d'uso: Visualizzare il mio profilo */
	@RequestMapping(value = {"/users/me"}, method = RequestMethod.GET)
	public String profile(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials credentials = sessionData.getLoggedCredentials();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentials",credentials);
		return "userProfile";	
		 }
	/*(1)*/
	
	
	/*(2)Caso d'uso: Aggiornare il mio profilo */
	@RequestMapping(value = { "/users/update" }, method = RequestMethod.GET)
	public String showUpdateForm(Model model) {
		User user = this.sessionData.getLoggedUser();
		model.addAttribute("loggedUser",user);
		model.addAttribute("userForm", user);
		return "updateUser";
	}
	
	@RequestMapping(value = { "/users/update" }, method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userForm")User user,
							   Model model) {
		User userCorrente = this.sessionData.getLoggedUser();
			Long id = userCorrente.getId();
			user.setId(id);
			this.userService.saveUser(user);
			model.addAttribute("loggedUser", user);
			Credentials credentials = this.sessionData.getLoggedCredentials();
			model.addAttribute("credentials", credentials);
			return "userProfile";
	}
	
	/*(2)*/

}
