package com.bechallenge.contacorrente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bechallenge.contacorrente.dto.AccountReqDTO;
import com.bechallenge.contacorrente.dto.AccountRespDTO;
import com.bechallenge.contacorrente.dto.AccountTransferDTO;
import com.bechallenge.contacorrente.model.Account;
import com.bechallenge.contacorrente.model.Customer;
import com.bechallenge.contacorrente.repository.AccountRepository;
import com.bechallenge.contacorrente.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private AccountRepository repository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private AccountService service;
	
	@Test
	void findAll() {
		Customer customer = new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123");
		List<Account> list = List.of(
				new Account(1L, 1, new BigDecimal(200), true, customer),
				new Account(2L, 2, new BigDecimal(10), true, customer));
		
		Mockito.when(repository.findAll())
			.thenReturn(list);
		
		List<AccountRespDTO> list_response = service.findAll();
		
		assertNotNull(list_response);
		assertEquals(list_response.size(), list.size());
		
		AccountRespDTO dto = list_response.get(0);
		assertNotNull(dto);
		assertNotNull(dto.getId());
		assertEquals(dto.getAgency(), 1);
		assertEquals(dto.getBalance(), new BigDecimal(200));
		assertEquals(dto.getStatus(), true);
		assertNotNull(dto.getCustomerId());
	}
	
	@Test
	void findById() {
		Customer customer = new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123");
		Optional<Account> mock = Optional.of(new Account(1L, 1, new BigDecimal(200), true, customer));
		
		Mockito.when(repository.findById(1L))
			.thenReturn(mock);
		
		AccountRespDTO response = service.findById(1L);
		assertNotNull(response);
		assertNotNull(response.getId());
		assertEquals(response.getAgency(), 1);
		assertEquals(response.getBalance(), new BigDecimal(200));
		assertEquals(response.getStatus(), true);
		assertNotNull(response.getCustomerId());
	}
	
	@Test
	void findByDocument() {
		Customer customer = new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123");
		List<Account> list = List.of(
				new Account(1L, 1, new BigDecimal(200), true, customer),
				new Account(2L, 2, new BigDecimal(10), true, customer));
		
		Mockito.when(repository.findByDocument(1L))
			.thenReturn(list);
		
		List<AccountRespDTO> list_response = service.findByDocument(1L);
		
		assertNotNull(list_response);
		assertEquals(list_response.size(), list.size());
		
		AccountRespDTO dto = list_response.get(0);
		assertNotNull(dto);
		assertNotNull(dto.getId());
		assertEquals(dto.getAgency(), 1);
		assertEquals(dto.getBalance(), new BigDecimal(200));
		assertEquals(dto.getStatus(), true);
		assertNotNull(dto.getCustomerId());
	}
	
	@Test
	void findByAgency() {
		Customer customer = new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123");
		Optional<Account> mock = Optional.of(new Account(1L, 1, new BigDecimal(200), true, customer));
		
		Mockito.when(repository.findByAgency(1))
			.thenReturn(mock);
		
		Optional<Account> response = service.findByAgency(1);
		assertNotNull(response);
		assertNotNull(response.get().getId());
		assertEquals(response.get().getAgency(), 1);
		assertEquals(response.get().getBalance(), new BigDecimal(200));
		assertEquals(response.get().getStatus(), true);
		assertNotNull(response.get().getCustomerId());
	}
	
	@Test
	void create() {
		AccountReqDTO request = new AccountReqDTO(1, new BigDecimal(100), true, 1L);
		Customer customer = new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123");
		Account mock = new Account(1, new BigDecimal(100), true, customer);
		Account mock_created = new Account(1L, 1, new BigDecimal(100), true, customer);
		
		Mockito.when(repository.save(mock))
			.thenReturn(mock_created);
		
		Mockito.when(customerRepository.findById(1L))
			.thenReturn(Optional.of(customer));
		
		AccountRespDTO response = service.create(request);
		
		assertNotNull(response);
		assertNotNull(response.getId());
		assertEquals(response.getAgency(), 1);
		assertEquals(response.getBalance(), new BigDecimal(100));
		assertEquals(response.getStatus(), true);
		assertNotNull(response.getCustomerId());
	}
	
	@Test
	void updateStatus() {
		Customer customer = new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123");
		Account mock = new Account(1L, 1, new BigDecimal(200), true, customer);
		
		Mockito.when(repository.findByAgency(1))
			.thenReturn(Optional.of(mock));
		
		Mockito.when(repository.save(mock))
			.thenReturn(mock);
		
		AccountRespDTO response = service.updateStatus(1);
		assertNotNull(response);
		assertEquals(response.getAgency(), 1);
		assertEquals(response.getStatus(), false);
	}
	
	@Test
	void accontTransfer() {
		AccountTransferDTO request = new AccountTransferDTO(1, 2, new BigDecimal(100));
		Customer customer = new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123");
		Account mock = new Account(1L, 1, new BigDecimal(200), true, customer);
		Account mock_destination = new Account(2L, 2, new BigDecimal(100), true, customer);
		
		Mockito.when(repository.findByAgency(1))
			.thenReturn(Optional.of(mock));
		
		Mockito.when(repository.findByAgency(2))
			.thenReturn(Optional.of(mock_destination));
		
		Mockito.when(repository.save(mock))
			.thenReturn(mock);
		
		Mockito.when(repository.save(mock_destination))
		.thenReturn(mock_destination);
		
		AccountRespDTO response = service.accontTransfer(request);
		assertNotNull(response);
		assertEquals(response.getBalance(), new BigDecimal(100));
	}
}
