package it.progetto.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.progetto.model.*;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	public List<User> findByVisibleProjects(Project project);
}
