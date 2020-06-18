package it.progetto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.progetto.model.Credentials;
import it.progetto.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Transactional
	public Credentials getCredentials(long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUserName(username);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialsRepository.save(credentials);
	}
	
	@Transactional
	public List<Credentials> getAllCredentials(){
		Iterable<Credentials> c = this.credentialsRepository.findAll();
		ArrayList<Credentials> credentials = new ArrayList<>();
		for(Credentials i : c) {
			credentials.add(i);
		}
		
		return credentials;
	}
	
	@Transactional
	public void deleteCredentials(String userName) {
		Credentials credentials = this.getCredentials(userName);
		this.credentialsRepository.delete(credentials);
	}

}
