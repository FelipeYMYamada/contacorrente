package com.bechallenge.contacorrente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bechallenge.contacorrente.model.Customer;
import com.bechallenge.contacorrente.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	public List<Customer> findAll() {
		return repository.findAll();
	}
	
	public Optional<Customer> findByDocument(Long document) {
		return repository.findById(document);
	}
	
	public Customer create(Customer costumer) {
		
		return repository.save(costumer);
	}
	
	public Customer update(Customer costumer) {
		return repository.save(costumer);
	}
	
	public void delete(Long document) {
		repository.deleteById(document);
	}
	
}
