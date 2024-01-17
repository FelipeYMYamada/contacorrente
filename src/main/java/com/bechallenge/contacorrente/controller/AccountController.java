package com.bechallenge.contacorrente.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
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
import com.bechallenge.contacorrente.exception.AccountInsufficientBalanceException;
import com.bechallenge.contacorrente.exception.AccountNotFoundException;
import com.bechallenge.contacorrente.exception.CustomerNotFoundException;
import com.bechallenge.contacorrente.mapper.MapStructMapper;
import com.bechallenge.contacorrente.model.Account;
import com.bechallenge.contacorrente.model.Customer;
import com.bechallenge.contacorrente.service.AccountService;
import com.bechallenge.contacorrente.service.CustomerService;

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
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "List all accounts", description = "List all accounts", tags = "Account",
		responses = {
			@ApiResponse(description = "Sucess", responseCode = "200", content = {@Content(mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = AccountRespDTO.class)))}),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
	})
	public List<AccountRespDTO> findAll() {
		List<AccountRespDTO> list = MapStructMapper.INSTANCE.toListAccountDTO(service.findAll());
		
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
		AccountRespDTO response = MapStructMapper.INSTANCE.toAccountRespDTO(
				service.findById(id)
					.orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id)));
		
		return addSelfHateos(response);
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
		List<AccountRespDTO> list = MapStructMapper.INSTANCE.toListAccountDTO(service.findByDocument(document));
		
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
		Customer customer = customerService.findByDocument(
				request.getCustomerDocument())
					.orElseThrow(() -> new CustomerNotFoundException("Customer not found with document: " + request.getCustomerDocument()));
		
		Account entity = service.create(MapStructMapper.INSTANCE.toAccount(request));
		entity.setCustomerId(customer);
		
		return addSelfHateos(MapStructMapper.INSTANCE.toAccountRespDTO(entity));
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
		Account entity = verifyAccountAgency(agency);
		entity.setStatus(!entity.getStatus());
		
		AccountRespDTO response = MapStructMapper.INSTANCE.toAccountRespDTO(service.update(entity)); 
		return addSelfHateos(response);
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
		Account entity = verifyAccountAgency(request.getAgency());
		Account destinationEntity = verifyAccountAgency(request.getDestinationAgency());
		
		if(checkAccountBalance(entity, request.getValue()))
			throw new AccountInsufficientBalanceException("Account with agency " + request.getAgency() + " does not have sufficient balance for transaction");
		
		updateBalance(destinationEntity, request.getValue(), true);
		
		AccountRespDTO response = MapStructMapper.INSTANCE.toAccountRespDTO(updateBalance(entity, request.getValue(), false)); 
		return addSelfHateos(response);
	}
	
	/**
	 * Método responsável por verificar disponibilidade de saldo na conta
	 * @param Account - Conta de onde vai ser retirado o dinheiro
	 * @param BigDecimal - Valor que vai ser transferido
	 * @return
	 */
	private boolean checkAccountBalance(Account account, BigDecimal value) {
		return account.getBalance().compareTo(value) <= 0;
	}
	
	/**
	 * Verifca se existe uma conta com a agência informada
	 * @param Integer - Número da agência
	 * @return Account - Conta encontrada com a agência informada 
	 */
	private Account verifyAccountAgency(Integer agency) {
		return service.findByAgency(agency)
				.orElseThrow(() -> new AccountNotFoundException("Account not found with agency: " + agency));
	}
	
	/**
	 * Atualiza saldo que atualiza saldo com o valor passado
	 * @param Account - Conta que terá seu saldo atualizado
	 * @param BigDecimal - Valor da transação
	 * @param boolean - true: valor deverá somar / false: valor deverá subtrair
	 * @return Account - Retorna Conta com seu valor atualizado
	 */
	private Account updateBalance(Account account, BigDecimal value, boolean accDestination) {
		if(accDestination)
			account.setBalance(account.getBalance().add(value));
		else
			account.setBalance(account.getBalance().subtract(value));
		
		return service.update(account);
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
