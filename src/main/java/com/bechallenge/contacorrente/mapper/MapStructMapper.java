package com.bechallenge.contacorrente.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.bechallenge.contacorrente.dto.CustomerReqDTO;
import com.bechallenge.contacorrente.dto.CustomerRespDTO;
import com.bechallenge.contacorrente.model.Customer;

@Mapper
public interface MapStructMapper {

	MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);
	
	List<CustomerRespDTO> toListCustomerDTO(List<Customer> listRequest);
	
	@Mapping(target = "entityType", ignore = true)
	Customer toCustomer(CustomerReqDTO request);
	
	CustomerReqDTO toCustomerDTO(Customer request);
	
	CustomerRespDTO toCustormerRespDTO(Customer request);
}
