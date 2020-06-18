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
import it.progetto.model.FormView;
import it.progetto.model.Project;
import it.progetto.model.Task;
import it.progetto.model.User;
import it.progetto.service.ProjectService;
import it.progetto.service.TaskService;
import it.progetto.service.UserService;

@Controller
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SessionData sessionData;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	
	/*(1) Caso d'uso: aggiungere un nuovo task a un mio progetto */
	@RequestMapping(value = "/task/add/{projectId}", method = RequestMethod.GET)
	public String taskForm(Model model, @PathVariable Long projectId){
		User loggedUser = this.sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectId",projectId);
		model.addAttribute("taskForm", new Task());
		return "addTask";
	}
	
	@RequestMapping(value = "/task/add/{projectId}", method = RequestMethod.POST)
	public String addTask(@ModelAttribute("taskForm") Task task,Model model,
						  @PathVariable Long projectId){
		User loggedUser = this.sessionData.getLoggedUser();
		this.taskService.saveTask(task);
		Project project = this.projectService.getProject(projectId);
		this.projectService.addTask(project, task);
		model.addAttribute("loggedUser", loggedUser);
		return "addTask";
	}
	/*(1)*/
	
	
	/*(2) Caso d'uso: cancellare un task da un mio progetto */
	@RequestMapping(value = "/task/delete/{taskId}", method = RequestMethod.POST)
	public String deleteTask(Model model, @PathVariable Long taskId) {
		Task task = this.taskService.getTask(taskId);
		this.taskService.deleteTask(task);
		return "redirect:/projects/";
	}
	/*(2)*/
	
	
	/*(3) Caso d'uso: assegnare un task di un mio progetto a un utente che ha visibilità sul mio progetto*/
	@RequestMapping(value = "/task/assign/{taskId}/{projectId}", method = RequestMethod.GET)
	public String assignTask(Model model, @PathVariable Long taskId,
							@PathVariable Long projectId) {
		Project project = this.projectService.getProject(projectId);
		Task task = this.taskService.getTask(taskId);
		List<User> members = this.userService.getMembers(project);
		model.addAttribute("members", members);
		model.addAttribute("task",task);
		model.addAttribute("projectForm", new FormView());
		return "assignTask";
	}
	
	@RequestMapping(value = "/task/assign/", method = RequestMethod.POST)
	public String assign(Model model, @ModelAttribute("projectForm") FormView formView) {
		Task task = this.taskService.getTask(formView.getProjectId());
		User user = this.userService.getUser(formView.getUserId());
		this.taskService.setUser(task, user);
		return "redirect:/projects";
	}
	/*(3)*/
	
	
	/*(4) Visualizza i dati di un task*/
	@RequestMapping(value = "/task/{taskId}/{projectId}", method = RequestMethod.GET)
	public String task(Model model, @PathVariable Long taskId,
					   @PathVariable Long projectId) {
		Task task = this.taskService.getTask(taskId);
		User user = this.taskService.getUser(task);
		model.addAttribute("task", task);
		model.addAttribute("user", user);
		return "task";
	}
	/*(4)*/
	
	
	/*(5) Caso d'uso: aggiornare un task di un mio progetto*/
	@RequestMapping(value = "/task/update/{taskId}", method = RequestMethod.GET)
	public String updateTaskForm(Model model, @PathVariable Long taskId) {
		Task task = this.taskService.getTask(taskId);
		User loggedUser = this.sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("task", task);
		return "updateTask";
	}
	
	@RequestMapping(value = "/task/update/{taskId}", method = RequestMethod.POST)
	public String updateTask(Model model, @ModelAttribute("task") Task task,
							 @PathVariable Long taskId) {
		Task oldTask = this.taskService.getTask(taskId);
		User user = this.taskService.getUser(oldTask);
		task.setId(taskId);
		this.taskService.setUser(task, user);
		this.taskService.saveTask(task);
		User loggedUser = this.sessionData.getLoggedUser();
		model.addAttribute(loggedUser);
		return "redirect:/projects";
	}
	/*(5)*/

}
