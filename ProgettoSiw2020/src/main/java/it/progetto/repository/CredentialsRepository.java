package it.progetto.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.progetto.model.Credentials;

@Repository
public interface CredentialsRepository extends CrudRepository<Credentials, Long>{
	
	public Optional<Credentials> findByUserName(String username);

}
