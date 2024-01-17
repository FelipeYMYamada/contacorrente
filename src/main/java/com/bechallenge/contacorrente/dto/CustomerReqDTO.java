package com.bechallenge.contacorrente.dto;

import java.io.Serializable;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CustomerReqDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long document;
	
	@NotNull(message = "Name can not be null")
	@NotBlank(message = "Name can not be empty")
	private String name;
	
	@NotNull(message = "Address can not be null")
	@NotBlank(message = "Address can not be empty")
	private String address;
	
	@NotNull(message = "Password can not be null")
	@NotBlank(message = "Password can not be empty")
	private String password;

	public CustomerReqDTO() {
	}

	public CustomerReqDTO(Long document,
			@NotNull(message = "Name can not be null") @NotBlank(message = "Name can not be empty") String name,
			@NotNull(message = "Address can not be null") @NotBlank(message = "Address can not be empty") String address,
			@NotNull(message = "Password can not be null") @NotBlank(message = "Password can not be empty") String password) {
		this.document = document;
		this.name = name;
		this.address = address;
		this.password = password;
	}

	public Long getDocument() {
		return document;
	}

	public void setDocument(Long document) {
		this.document = document;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, document, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerReqDTO other = (CustomerReqDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(document, other.document)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "CustomerReqDTO [document=" + document + ", name=" + name + ", address="
				+ address + ", password=" + password + "]";
	}

}
