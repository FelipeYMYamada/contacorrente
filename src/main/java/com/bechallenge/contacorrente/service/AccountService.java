package com.bechallenge.contacorrente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bechallenge.contacorrente.model.Account;
import com.bechallenge.contacorrente.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;
	
	public List<Account> findAll() {
		return repository.findAll();
	}
	
	public Optional<Account> findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Account> findByDocument(Long document) {
		return repository.findByDocument(document);
	}
	
	public Optional<Account> findByAgency(Integer agency) {
		return repository.findByAgency(agency);
	}
	
	public Account create(Account account) {
		return repository.save(account);
	}
	
	public Account update(Account account) {
		return repository.save(account);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
