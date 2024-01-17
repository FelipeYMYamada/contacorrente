package com.bechallenge.contacorrente.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.bechallenge.contacorrente.dto.AccountReqDTO;
import com.bechallenge.contacorrente.dto.AccountRespDTO;
import com.bechallenge.contacorrente.dto.CustomerReqDTO;
import com.bechallenge.contacorrente.dto.CustomerRespDTO;
import com.bechallenge.contacorrente.model.Account;
import com.bechallenge.contacorrente.model.Customer;

@Mapper
public interface MapStructMapper {

	MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);
	
	List<CustomerRespDTO> toListCustomerDTO(List<Customer> listRequest);
	
	@Mapping(target = "entityType", ignore = true)
	@Mapping(target = "collectionAccount", ignore = true)
	Customer toCustomer(CustomerReqDTO request);
	
	CustomerReqDTO toCustomerDTO(Customer request);
	
	@Mapping(target = "add", ignore = true)
	CustomerRespDTO toCustormerRespDTO(Customer request);
	
	List<AccountRespDTO> toListAccountDTO(List<Account> listRequest);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "customerDocument", target = "customerId.document")
	Account toAccount(AccountReqDTO request);
	
	@Mapping(source = "customerId.document", target = "customerDocument")
	AccountReqDTO toAccounReqDTO(Account request);
	
	@Mapping(target = "add", ignore = true)
	@Mapping(target = "customerId.add", ignore = true)
	AccountRespDTO toAccountRespDTO(Account request);
}
