package com.bechallenge.contacorrente.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bechallenge.contacorrente.exception.CustomerNotFoundException;
import com.bechallenge.contacorrente.model.Customer;
import com.bechallenge.contacorrente.repository.CustomerRepository;
import com.bechallenge.contacorrente.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

	@Mock
	CustomerRepository repository;
	
	@InjectMocks
	private CustomerService service;
	
	@Test
	void findByDocument() {
		Long document = 1L;
		Optional<Customer> customer = Optional.of(new Customer(document, "PF", "Felipe", "Rua Teste", "123"));
		
		Mockito.when(repository.findById(document))
			.thenReturn(customer);
		
		Optional<Customer> _customer = service.findByDocument(document);
		assertNotNull(_customer.get());
		assertEquals(_customer.get().getDocument(), document);
	}
	
	@Test
	void documentDontExists() {
		Long document = 1L;
		
		Mockito.when(repository.findById(document))
			.thenThrow(CustomerNotFoundException.class);
		
		assertThrows(CustomerNotFoundException.class, () -> {
			service.findByDocument(document);
		});
	}
	
	@Test
	void createPFSuccess() {
		Long document = 1L;
		Customer customer = new Customer(document, "PF", "Felipe", "Rua Teste", "123");
		
		Mockito.when(repository.findById(document))
			.thenReturn(null);
		
		Mockito.when(repository.save(customer))
			.thenReturn(customer);
		
		Optional<Customer> customerNull = service.findByDocument(document);
		Customer _customer = service.create(customer);
		
		assertNull(customerNull);
		assertNotNull(_customer);
		assertEquals(_customer.getDocument(), document);
		assertEquals(_customer.getEntityType(), "PF");
	}
	
}
