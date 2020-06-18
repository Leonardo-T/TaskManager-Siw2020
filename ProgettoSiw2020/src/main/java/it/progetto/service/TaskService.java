package it.progetto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.progetto.model.Task;
import it.progetto.model.User;
import it.progetto.repository.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Transactional
	public Task getTask(Long id) {
		Optional<Task> result = this.taskRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public void deleteTask(Task task) {
		this.taskRepository.delete(task);
	}
	
	@Transactional
	public Task saveTask(Task task) {
		return this.taskRepository.save(task);
	}
	
	@Transactional
	public Task setCompleted(Task task) {
		task.setCompleted(true);
		return this.taskRepository.save(task);
	}
	
	@Transactional
	public Task setUser(Task task,User user) {
		task.setUser(user);
		return this.taskRepository.save(task);
	}
	
	@Transactional
	public User getUser(Task task) {
		return task.getUser();
	}
	
}
