package it.progetto;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.progetto.model.*;
import it.progetto.repository.*;
import it.progetto.service.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProgettoSiw2020ApplicationTests {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProjectService projectService;
	
	@Before
	public void deleteAll() {
		System.out.println("Deleting all data... \n");
		this.userRepository.deleteAll();
		this.taskRepository.deleteAll();
		this.projectRepository.deleteAll();
		System.out.println("Done \n");
	}

	@Test
	void testUpdateUser() {
		
		

	} 
	
}

