package com.bechallenge.contacorrente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bechallenge.contacorrente.dto.CustomerReqDTO;
import com.bechallenge.contacorrente.dto.CustomerRespDTO;
import com.bechallenge.contacorrente.exception.CustomerDocumentDuplicatedException;
import com.bechallenge.contacorrente.exception.CustomerNotFoundException;
import com.bechallenge.contacorrente.mapper.MapStructMapper;
import com.bechallenge.contacorrente.model.Customer;
import com.bechallenge.contacorrente.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping
	public List<CustomerRespDTO> findAll() {
		return MapStructMapper.INSTANCE.toListCustomerDTO(service.findAll());
	}
	
	@GetMapping("/{document}")
	public CustomerRespDTO findByDocument(@PathVariable Long document) {
		return MapStructMapper.INSTANCE.toCustormerRespDTO(
					service.findByDocument(document)
						.orElseThrow(() -> new CustomerNotFoundException("Customer not found with this document: " + document)));
	}
	
	@PostMapping("/PF")
	public Customer createPF(@Valid @RequestBody CustomerReqDTO request) {
		if(service.findByDocument(request.getDocument()).isPresent())
			throw new CustomerDocumentDuplicatedException("Customer already register with this document ");
		else {
			Customer entity = MapStructMapper.INSTANCE.toCustomer(request);
			entity.setEntityType("PF");
			return service.create(entity);
		}
	}
	
	@PostMapping("/PJ")
	public Customer createPJ(@Valid @RequestBody CustomerReqDTO request) {
		if(service.findByDocument(request.getDocument()).isPresent())
			throw new CustomerDocumentDuplicatedException("Customer already register with this document ");
		else {
			Customer entity = MapStructMapper.INSTANCE.toCustomer(request);
			entity.setEntityType("PJ");
			return service.create(entity);
		}
	}
	
	@PutMapping("/{document}")
	public CustomerReqDTO update(@Valid @RequestBody CustomerReqDTO customer) {
		Customer entity = service.findByDocument(customer.getDocument())
									.orElseThrow(() -> new CustomerNotFoundException("Customer not found with this document: " + customer.getDocument()));
		entity.setName(customer.getName());
		entity.setAddress(customer.getAddress());
		entity.setPassword(customer.getPassword());
		
		return MapStructMapper.INSTANCE.toCustomerDTO(entity);
	}
	
	@DeleteMapping("/{document}")
	public void delete(@PathVariable Long document) {
		service.findByDocument(document)
			.orElseThrow(() -> new CustomerNotFoundException("Customer not found with this document: " + document));
		
		service.delete(document);
	}
}
