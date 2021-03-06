package it.progetto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.progetto.model.Project;
import it.progetto.model.User;
import it.progetto.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User getUser(Long id) {
		Optional<User> result = this.userRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}
	
	@Transactional
	public List<User> findAllUsers(){
		ArrayList<User> lista = new ArrayList<>();
		Iterable<User> i = this.userRepository.findAll();
		for(User u : i) {
			lista.add(u);
		}
		return lista;
	}
	
	@Transactional
	public List<User> getMembers(Project project){
		return this.userRepository.findByVisibleProjects(project);
	}
	
	
}
