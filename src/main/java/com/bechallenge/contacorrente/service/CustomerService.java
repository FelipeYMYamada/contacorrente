package com.bechallenge.contacorrente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bechallenge.contacorrente.dto.CustomerReqDTO;
import com.bechallenge.contacorrente.dto.CustomerRespDTO;
import com.bechallenge.contacorrente.exception.CustomerDocumentDuplicatedException;
import com.bechallenge.contacorrente.exception.CustomerNotFoundException;
import com.bechallenge.contacorrente.mapper.MapStructMapper;
import com.bechallenge.contacorrente.model.Customer;
import com.bechallenge.contacorrente.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;
	
	public List<CustomerRespDTO> findAll() {
		return MapStructMapper.INSTANCE.toListCustomerDTO(repository.findAll());
	}
	
	public CustomerRespDTO findByDocument(Long document) {
		Customer customer = repository.findById(document)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with this document: " + document));
		return MapStructMapper.INSTANCE.toCustormerRespDTO(customer);
	}
	
	public CustomerRespDTO create(CustomerReqDTO request, String entityType) {
		if(repository.findById(request.getDocument()).isPresent())
			throw new CustomerDocumentDuplicatedException("Customer already register with the document: " + request.getDocument());
		
		Customer entity = MapStructMapper.INSTANCE.toCustomer(request);
		entity.setEntityType(entityType);
		
		return MapStructMapper.INSTANCE.toCustormerRespDTO(repository.save(entity));
	}
	
	public CustomerRespDTO update(CustomerReqDTO request) {
		Customer entity = repository.findById(request.getDocument())
						.orElseThrow(() -> new CustomerNotFoundException("Customer not found with this document: " + request.getDocument()));
		
		entity.setName(request.getName());
		entity.setAddress(request.getAddress());
		entity.setPassword(request.getPassword());
		
		return MapStructMapper.INSTANCE.toCustormerRespDTO(repository.save(entity));
	}
	
	public void delete(Long document) {
		findByDocument(document);
		
		repository.deleteById(document);
	}
	
}
