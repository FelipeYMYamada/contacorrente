package com.bechallenge.contacorrente.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bechallenge.contacorrente.dto.CustomerReqDTO;
import com.bechallenge.contacorrente.dto.CustomerRespDTO;
import com.bechallenge.contacorrente.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {
	
	private final String URI = "/api/customer";

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	private CustomerService service;
	
	@Test
	void findAll() throws Exception {
		List<CustomerRespDTO> list_mock = List.of(
				new CustomerRespDTO(1L, "PF", "Felipe", "Rua Teste 1"),
				new CustomerRespDTO(2L, "PF", "Joao", "Rua Teste 3"));
		
		when(service.findAll())
			.thenReturn(list_mock);
		
		mockMvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].document").value(1L))
			.andExpect(jsonPath("$.[0].entityType").value("PF"))
			.andExpect(jsonPath("$.[0].name").value("Felipe"))
			.andExpect(jsonPath("$.[0].address").value("Rua Teste 1"))
			.andExpect(jsonPath("$.[0].links.[0].href").value("http://localhost/api/customer/1"))
			.andExpect(jsonPath("$.length()").value(2));
	}
	
	@Test
	void findByDocument() throws Exception {
		CustomerRespDTO mock = new CustomerRespDTO(1L, "PF", "Felipe", "Rua Teste 1");
		
		when(service.findByDocument(1L))
			.thenReturn(mock);
		
		mockMvc.perform(get(URI + "/{document}", 1L).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.document").value(1L))
			.andExpect(jsonPath("$.entityType").value("PF"))
			.andExpect(jsonPath("$.name").value("Felipe"))
			.andExpect(jsonPath("$.address").value("Rua Teste 1"))
			.andExpect(jsonPath("$._links.self.href").value("http://localhost/api/customer/1"));
	}
	
	@Test
	void createPF() throws JsonProcessingException, Exception {
		CustomerReqDTO request = new CustomerReqDTO(1L, "Felipe", "Rua Teste 1", "123");
		CustomerRespDTO response = new CustomerRespDTO(1L, "PF", "Felipe", "Rua Teste 1");
		
		when(service.create(request, "PF")).thenReturn(response);
		
		mockMvc.perform(post(URI + "/PF")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.document").value(1L))
			.andExpect(jsonPath("$.entityType").value("PF"));
	}
	
	@Test
	void createPJ() throws JsonProcessingException, Exception {
		CustomerReqDTO request = new CustomerReqDTO(1L, "Google", "Rua Teste 1", "123");
		CustomerRespDTO response = new CustomerRespDTO(1L, "PJ", "Google", "Rua Teste 1");
		
		when(service.create(request, "PJ")).thenReturn(response);
		
		mockMvc.perform(post(URI + "/PJ")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.document").value(1L))
			.andExpect(jsonPath("$.entityType").value("PJ"));
	}
	
	@Test
	void update() throws JsonProcessingException, Exception {
		CustomerReqDTO request = new CustomerReqDTO(1L, "Felipe", "Rua Teste 10", "123");
		CustomerRespDTO response = new CustomerRespDTO(1L, "PF", "Felipe", "Rua Teste 10");
		
		when(service.create(request, "PF")).thenReturn(response);
		
		mockMvc.perform(post(URI + "/PF")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.document").value(1L))
			.andExpect(jsonPath("$.entityType").value("PF"))
			.andExpect(jsonPath("$.name").value("Felipe"))
			.andExpect(jsonPath("$.address").value("Rua Teste 10"))
			.andExpect(jsonPath("$._links.self.href").value("http://localhost/api/customer/1"));
	}
	
	@Test
	void deleteTest() throws Exception {
		mockMvc.perform(delete(URI + "/{document}", 1L))
			.andExpect(status().isOk());
	}
	
}
