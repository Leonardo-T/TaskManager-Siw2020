
package it.progetto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.progetto.model.Project;
import it.progetto.model.Task;
import it.progetto.model.User;

import it.progetto.repository.ProjectRepository;


@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Transactional
	public Project getProject(Long id) {
		Optional<Project> result = this.projectRepository.findById(id);
		return result.orElse(null);
		
	}
	
	@Transactional
	public Project saveProject(Project project) {
		return this.projectRepository.save(project);
		
	}
	
	@Transactional
	public void deleteProject(Project project) {
		this.projectRepository.delete(project);
	}
	
	@Transactional
	public Project shareProjectWithUser(Project project, User user) {
		project.addMember(user);
		return this.projectRepository.save(project);
	}
	
	@Transactional
	public List<Project> retrieveProjectsOwnedBy(User owner) {
		return this.projectRepository.findByOwner(owner);
	}
	
	@Transactional
	public Project addTask (Project project, Task task) {
		project.addTask(task);
		return this.projectRepository.save(project);
	}
	
	@Transactional
	public List<Project> retrieveProjectsWithMember(User member){
		return this.projectRepository.findByMembers(member);
	}
	

}
