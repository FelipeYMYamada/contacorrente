package com.bechallenge.contacorrente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bechallenge.contacorrente.dto.CustomerReqDTO;
import com.bechallenge.contacorrente.dto.CustomerRespDTO;
import com.bechallenge.contacorrente.exception.CustomerDocumentDuplicatedException;
import com.bechallenge.contacorrente.exception.CustomerNotFoundException;
import com.bechallenge.contacorrente.model.Customer;
import com.bechallenge.contacorrente.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	
	@Mock
	private CustomerRepository repository;
	
	@InjectMocks
	private CustomerService service;
	
	@Test
	void findAll() {
		List<Customer> list = List.of(
				new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123"),
				new Customer(2L, "PF", "Joao", "Rua Teste 2", "312"));
		
		Mockito.when(repository.findAll())
			.thenReturn(list);
		
		List<CustomerRespDTO> list_response = service.findAll();
		
		assertNotNull(list_response);
		assertEquals(list_response.size(), list.size());
		
		CustomerRespDTO dto = list_response.get(0);
		assertNotNull(dto);
		assertNotNull(dto.getDocument());
		assertEquals(dto.getDocument(), 1L);
		assertEquals(dto.getEntityType(), "PF");
		assertEquals(dto.getName(), "Felipe");
		assertEquals(dto.getAddress(), "Rua Teste 1");
	}
	
	@Test
	void findByDocument() {
		Optional<Customer> mock = Optional.of(new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123"));
		
		Mockito.when(repository.findById(1L))
			.thenReturn(mock);
		
		CustomerRespDTO response = service.findByDocument(1L);
		
		assertNotNull(response);
		assertEquals(response.getDocument(), 1L);
		assertEquals(response.getEntityType(), "PF");
		assertEquals(response.getName(), "Felipe");
		assertEquals(response.getAddress(), "Rua Teste 1");
	}
	
	@Test
	void findByDocumentFail() {
		Exception e = assertThrows(CustomerNotFoundException.class, () -> {
			service.findByDocument(1L);
		});
		
		String expectedMessage = "Customer not found with this document: 1";
		String actualMessage = e.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void create() {
		CustomerReqDTO request = new CustomerReqDTO(1L, "Felipe", "Rua Teste 1", "123");
		Customer mock = new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123");
		
		Mockito.when(repository.save(mock))
			.thenReturn(mock);
		
		CustomerRespDTO response = service.create(request, "PF");
		
		assertNotNull(response);
		assertEquals(response.getDocument(), 1L);
		assertEquals(response.getEntityType(), "PF");
		assertEquals(response.getName(), "Felipe");
		assertEquals(response.getAddress(), "Rua Teste 1");
	}
	
	@Test
	void createFail() {
		Optional<Customer> mock = Optional.of(new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123"));
		CustomerReqDTO request = new CustomerReqDTO(1L, "Felipe", "Rua Teste 1", "123");
		
		Mockito.when(repository.findById(1L))
			.thenReturn(mock);
		
		Exception e = assertThrows(CustomerDocumentDuplicatedException.class, () -> {
			service.create(request, "PF");
		});
		
		String expectedMessage = "Customer already register with the document: 1";
		String actualMessage = e.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void update() {
		CustomerReqDTO request = new CustomerReqDTO(1L, "Felipe", "Rua Teste 10", "123");
		Optional<Customer> mockOld = Optional.of(new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123"));
		Customer mock = new Customer(1L, "PF", "Felipe", "Rua Teste 10", "123");
		
		Mockito.when(repository.findById(request.getDocument()))
			.thenReturn(mockOld);
		
		Mockito.when(repository.save(mock))
			.thenReturn(mock);
		
		CustomerRespDTO response = service.update(request);
		
		assertNotNull(response);
		assertEquals(response.getDocument(), request.getDocument());
		assertEquals(response.getEntityType(), "PF");
		assertEquals(response.getName(), request.getName());
		assertEquals(response.getAddress(), request.getAddress());
	}
	
	@Test
	void updateFail() {
		CustomerReqDTO request = new CustomerReqDTO(1L, "Felipe", "Rua Teste 10", "123");
		Exception e = assertThrows(CustomerNotFoundException.class, () -> {
			service.update(request);
		});
		
		String expectedMessage = "Customer not found with this document: 1";
		String actualMessage = e.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void delete() {
		Optional<Customer> mock = Optional.of(new Customer(1L, "PF", "Felipe", "Rua Teste 1", "123"));
		
		Mockito.when(repository.findById(1L))
			.thenReturn(mock);
		
		service.delete(mock.get().getDocument());
	}
	
	@Test
	void deleteFail() {
		Exception e = assertThrows(CustomerNotFoundException.class, () -> {
			service.delete(1L);
		});
		
		String expectedMessage = "Customer not found with this document: 1";
		String actualMessage = e.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
}
