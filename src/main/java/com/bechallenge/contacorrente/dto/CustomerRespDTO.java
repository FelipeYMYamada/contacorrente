package com.bechallenge.contacorrente.dto;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomerRespDTO extends RepresentationModel<CustomerRespDTO> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long document;
	private String entityType;
	private String name;
	private String address;
	@JsonIgnore
	private String password;
	
	public CustomerRespDTO() {
	}

	public CustomerRespDTO(Long document, String entityType, String name, String address) {
		this.document = document;
		this.name = name;
		this.address = address;
	}

	public Long getDocument() {
		return document;
	}

	public void setDocument(Long document) {
		this.document = document;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, document, entityType, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerRespDTO other = (CustomerRespDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(document, other.document)
				&& Objects.equals(entityType, other.entityType) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "CustomerRespDTO [document=" + document + ", entityType=" + entityType + ", name=" + name + ", address="
				+ address + "]";
	}

}
