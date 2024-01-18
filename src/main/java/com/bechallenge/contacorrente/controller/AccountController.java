package com.bechallenge.contacorrente.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bechallenge.contacorrente.dto.AccountReqDTO;
import com.bechallenge.contacorrente.dto.AccountRespDTO;
import com.bechallenge.contacorrente.dto.AccountTransferDTO;
import com.bechallenge.contacorrente.dto.CustomerRespDTO;
import com.bechallenge.contacorrente.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
@Tag(name = "Account", description = "Endpoint for managing accounts")
public class AccountController {

	@Autowired
	private AccountService service;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "List all accounts", description = "List all accounts", tags = "Account",
		responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", content = {@Content(mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = AccountRespDTO.class)))}),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public List<AccountRespDTO> findAll() {
		List<AccountRespDTO> list = service.findAll();
		
		list.stream().map(a -> addSelfHateos(a))
			.collect(Collectors.toList());
		
		return list;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Search an account by ID", description = "Search an account by ID",
	tags = "Account",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = AccountRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public AccountRespDTO findById(@PathVariable Long id) {
		return addSelfHateos(service.findById(id));
	}

	@GetMapping(value = "/customer/{document}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Search an account by customer document", description = "Search an account by customer document",
	tags = "Account",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = AccountRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public List<AccountRespDTO> findByDocument(@PathVariable Long document) {
		List<AccountRespDTO> list = service.findByDocument(document);
		
		list.stream().map(a -> addSelfHateos(a))
			.collect(Collectors.toList());
		
		return list;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Creates an account", description = "Creates an account",
	tags = "Account",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = AccountRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public AccountRespDTO create(@Valid @RequestBody AccountReqDTO request) {
		return addSelfHateos(service.create(request));
	}
	
	@PutMapping(value = "/status/{agency}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Changes account status", description = "Changes account status",
	tags = "Account",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = AccountRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public AccountRespDTO changeAccountStatus(@PathVariable Integer agency) {
		return addSelfHateos(service.updateStatus(agency));
	}
	
	@PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Transfer money from an accont to another", description = "Transfer money from an accont to another",
	tags = "Account",
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = AccountRespDTO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public AccountRespDTO accountTransfer(@Valid @RequestBody AccountTransferDTO request) {
		return addSelfHateos(service.accontTransfer(request));
	}
	
	private AccountRespDTO addSelfHateos(AccountRespDTO response) {
		response.add(
				linkTo(
						methodOn(AccountController.class).findById(response.getId()))
				.withSelfRel());
		
		response.setCustomerId(addCustomerSelfHateos(response.getCustomerId()));
		return response;
	}
	
	private CustomerRespDTO addCustomerSelfHateos(CustomerRespDTO response) {
		response.add(
				linkTo(
						methodOn(CustomerController.class).findByDocument(response.getDocument()))
				.withSelfRel());
		return response;
	}
}
