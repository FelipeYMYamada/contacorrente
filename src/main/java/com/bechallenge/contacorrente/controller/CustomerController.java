package com.bechallenge.contacorrente.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customer", description = "Endpoint for managing customers")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "List all accounts", description = "List all accounts", tags = "Customer",
		responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", content = {@Content(mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = CustomerRespDTO.class)))}),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public List<CustomerRespDTO> findAll() {
		List<CustomerRespDTO> list = MapStructMapper.INSTANCE.toListCustomerDTO(service.findAll());
		
		list.stream().map(c -> addSelfHateos(c))
			.collect(Collectors.toList());
		
		return list;
	}
	
	@GetMapping(value = "/{document}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Search a customer by document", description = "Search a customer by document",
	tags = "Customer",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = CustomerRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public CustomerRespDTO findByDocument(@PathVariable Long document) {
		CustomerRespDTO response = MapStructMapper.INSTANCE.toCustormerRespDTO(
				service.findByDocument(document)
					.orElseThrow(() -> new CustomerNotFoundException("Customer not found with this document: " + document)));
		
		return addSelfHateos(response);
	}
	
	@PostMapping(value = "/PF", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Creates an account", description = "Creates an account",
	tags = "Customer",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = CustomerRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public CustomerRespDTO createPF(@Valid @RequestBody CustomerReqDTO request) {
		if(service.findByDocument(request.getDocument()).isPresent())
			throw new CustomerDocumentDuplicatedException("Customer already register with this document ");
		else {
			Customer entity = MapStructMapper.INSTANCE.toCustomer(request);
			entity.setEntityType("PF");
			CustomerRespDTO response = MapStructMapper.INSTANCE.toCustormerRespDTO(service.create(entity));
			return addSelfHateos(response);
		}
	}
	
	@PostMapping(value = "/PJ", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Creates an account", description = "Creates an account",
	tags = "Customer",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = CustomerRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public CustomerRespDTO createPJ(@Valid @RequestBody CustomerReqDTO request) {
		if(service.findByDocument(request.getDocument()).isPresent())
			throw new CustomerDocumentDuplicatedException("Customer already register with this document ");
		else {
			Customer entity = MapStructMapper.INSTANCE.toCustomer(request);
			entity.setEntityType("PJ");
			CustomerRespDTO response = MapStructMapper.INSTANCE.toCustormerRespDTO(service.create(entity));
			return addSelfHateos(response);
		}
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Updates a customer", description = "Updates a customer",
	tags = "Customer",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = CustomerRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public CustomerRespDTO update(@Valid @RequestBody CustomerReqDTO customer) {
		Customer entity = service.findByDocument(customer.getDocument())
									.orElseThrow(() -> new CustomerNotFoundException("Customer not found with this document: " + customer.getDocument()));
		entity.setName(customer.getName());
		entity.setAddress(customer.getAddress());
		entity.setPassword(customer.getPassword());
		
		CustomerRespDTO response = MapStructMapper.INSTANCE.toCustormerRespDTO(service.update(entity));
		
		return addSelfHateos(response);
	}
	
	@DeleteMapping("/{document}")
	@Operation(summary = "Deletes a customer", description = "Deletes a customer",
	tags = "Customer",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public void delete(@PathVariable Long document) {
		service.findByDocument(document)
			.orElseThrow(() -> new CustomerNotFoundException("Customer not found with this document: " + document));
		
		service.delete(document);
	}
	
	private CustomerRespDTO addSelfHateos(CustomerRespDTO response) {
		response.add(
				linkTo(
						methodOn(CustomerController.class).findByDocument(response.getDocument()))
				.withSelfRel());
		return response;
	}
}
