package it.progetto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.progetto.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{

}
