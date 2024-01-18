package com.bechallenge.contacorrente.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bechallenge.contacorrente.dto.AccountRespDTO;
import com.bechallenge.contacorrente.dto.CustomerRespDTO;
import com.bechallenge.contacorrente.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest {

	private final String URI = "/api/account";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	private AccountService service;
	
	@Test
	void findAll() throws Exception {
		CustomerRespDTO customer = new CustomerRespDTO(1L, "PF", "Felipe", "Rua Teste 1");
		List<AccountRespDTO> list_mock = List.of(
				new AccountRespDTO(1L, 1, new BigDecimal(200), true, customer),
				new AccountRespDTO(2L, 2, new BigDecimal(200), true, customer));
		
		when(service.findAll())
			.thenReturn(list_mock);
		
		mockMvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(1L))
			.andExpect(jsonPath("$.[0].agency").value(1))
			.andExpect(jsonPath("$.[0].balance").value(new BigDecimal(200)))
			.andExpect(jsonPath("$.[0].status").value(true))
			.andExpect(jsonPath("$.length()").value(2));
	}
	
	@Test
	void findById() throws Exception {
		CustomerRespDTO customer = new CustomerRespDTO(1L, "PF", "Felipe", "Rua Teste 1");
		AccountRespDTO mock = new AccountRespDTO(1L, 1, new BigDecimal(200), true, customer);
		
		when(service.findById(1L))
			.thenReturn(mock);
		
		mockMvc.perform(get(URI + "/{id}", 1L).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1L))
			.andExpect(jsonPath("$.agency").value(1))
			.andExpect(jsonPath("$.balance").value(new BigDecimal(200)))
			.andExpect(jsonPath("$.status").value(true));
	}
	
	@Test
	void findByDocument() throws Exception {
		CustomerRespDTO customer = new CustomerRespDTO(1L, "PF", "Felipe", "Rua Teste 1");
		List<AccountRespDTO> list_mock = List.of(
				new AccountRespDTO(1L, 1, new BigDecimal(200), true, customer),
				new AccountRespDTO(2L, 2, new BigDecimal(200), true, customer));
		
		when(service.findByDocument(1L))
			.thenReturn(list_mock);
		
		mockMvc.perform(get(URI + "/customer/{document}", 1L).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].id").value(1L))
			.andExpect(jsonPath("$.[0].agency").value(1))
			.andExpect(jsonPath("$.[0].balance").value(new BigDecimal(200)))
			.andExpect(jsonPath("$.[0].status").value(true))
			.andExpect(jsonPath("$.length()").value(2));
	}
}
