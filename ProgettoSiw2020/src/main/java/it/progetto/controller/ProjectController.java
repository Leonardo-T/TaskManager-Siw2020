package it.progetto.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.progetto.controller.session.SessionData;
import it.progetto.controller.validator.ProjectValidator;
import it.progetto.model.Credentials;
import it.progetto.model.FormView;
import it.progetto.model.Project;
import it.progetto.model.Task;
import it.progetto.model.User;
import it.progetto.service.CredentialsService;
import it.progetto.service.ProjectService;
import it.progetto.service.UserService;

@Controller
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	CredentialsService credentialsService;
	
	/*(1) Caso d'uso: visualizzare i miei progetti */
	@RequestMapping(value = {"/projects"}, method = RequestMethod.GET)
	public String myOwnedProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = projectService.retrieveProjectsOwnedBy(loggedUser);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsList", projectsList);
		return "projects";
	}
	
	
	@RequestMapping(value = {"/projects/{projectId}"}, method = RequestMethod.GET)
	public String project(Model model,
						  @PathVariable Long projectId) {
		Project project = projectService.getProject(projectId);
		if(project==null)
			return "redirect:/projects";
		List<User> members = userService.getMembers(project);
		User loggedUser = sessionData.getLoggedUser();
		if(!project.getOwner().equals(loggedUser) && !members.contains(loggedUser))
			return "redirect:/projects";
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		return "project";
	}
	/*(1)*/
	
	
	/*(2) Caso d'uso: condividere un mio progetto con un utente */
	@RequestMapping(value = {"/project/share/{projectId}"}, method = RequestMethod.GET)
	public String usersList(Model model,
							@PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		Project project = this.projectService.getProject(projectId);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project",project);
		model.addAttribute("credentialsList", allCredentials);
		model.addAttribute("projectForm", new FormView());
		return "shareProject";
	}
	
	@RequestMapping(value = {"/project/share/"}, method = RequestMethod.POST)
	public String removeUser(Model model, @ModelAttribute("projectForm") FormView formView) {
		Project project = this.projectService.getProject(formView.getProjectId());
		Credentials credentials = this.credentialsService.getCredentials(formView.getUsername());
		User user = credentials.getUser();
		this.projectService.shareProjectWithUser(project, user);
		return "redirect:/projects/" + project.getId();
	}
	/*(2)*/
	
	
	/*(3) Caso d'uso: Creare un nuovo progetto */
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.GET)
	public String createProjectForm(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		return "addProject";
	}
	
	@RequestMapping(value = {"/projects/add"}, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("projectForm") Project project,
								BindingResult projectBindingResult, Model model) {
		User loggedUser = sessionData.getLoggedUser();
		
		projectValidator.validate(project, projectBindingResult);
		if(!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId();
		}
		
		model.addAttribute("loggedUser", loggedUser);
		return "addProject";
	}
	/*(3)*/
	
	
	/*(4) Caso d'uso: cancellare un mio progetto */
	@RequestMapping(value = {"/project/delete/{projectId}"}, method = RequestMethod.POST)
	public String removeProject(Model model, @PathVariable Long projectId) {
		Project project = this.projectService.getProject(projectId);
		this.projectService.deleteProject(project);
		return "redirect:/projects/";
	}
	/*(4)*/
	
	
	/*(5) Caso d'uso: visualizzare i progetti condivisi con me */
	@RequestMapping(value = {"/projects/shared"}, method = RequestMethod.GET)
	public String projectsShared(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projects = this.projectService.retrieveProjectsWithMember(loggedUser);
		model.addAttribute("projects", projects);
		return "projectsShared";
	}
	/*(5)*/
	
	
	 /*(6) Caso d'uso: aggiornare i dati di un mio progetto */
	 @RequestMapping(value = {"/project/update/{projectId}"}, method = RequestMethod.GET)
	 public String updateProjectForm(Model model, @PathVariable Long projectId) {
		 User loggedUser = sessionData.getLoggedUser();
		 Project project = this.projectService.getProject(projectId);	
		 model.addAttribute("loggedUser", loggedUser);
		 model.addAttribute("projectForm", project);
		 return "updateProject";
	 }
	 
	 @RequestMapping(value = {"/project/update/{projectId}"}, method = RequestMethod.POST)
		public String updateProject(@ModelAttribute("projectForm") Project project,
									Model model, @PathVariable Long projectId) {
		 		Project oldProject = this.projectService.getProject(projectId);
		 		List<Task> tasks = oldProject.getTasks();
		 		List<User> members = this.userService.getMembers(oldProject);
		 		project.setId(projectId);
		 		project.setTasks(tasks);
		 		for(User m : members) {
		 			this.projectService.shareProjectWithUser(project, m);
		 		}
				
		 		User loggedUser = sessionData.getLoggedUser();
				project.setOwner(loggedUser);
				this.projectService.saveProject(project);
				model.addAttribute("loggedUser", loggedUser);
				return "redirect:/projects/" + project.getId();
			
			
	}
	/*(6)*/ 

}
