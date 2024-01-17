package com.bechallenge.contacorrente.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long document;
	
	@NotNull(message = "Entity type can not be null")
	@NotBlank(message = "Entity type can not be blank")
	private String entityType;
	
	@NotNull(message = "Name can not be null")
	@NotBlank(message = "Name can not be empty")
	private String name;
	
	
	@NotNull(message = "Address can not be null")
	@NotBlank(message = "Address can not be empty")
	private String address;
	
	@NotNull(message = "Password can not be null")
	@NotBlank(message = "Password can not be empty")
	@Column(name = "password")
	private String password;
	
	@OneToMany( cascade = CascadeType.ALL, mappedBy = "customerId" )
	@JsonIgnore
	private Collection<Account> collectionAccount;

	public Customer() {
	}

	public Customer(Long document,
			@NotNull(message = "Entity type can not be null") @NotBlank(message = "Entity type can not be blank") String entityType,
			@NotNull(message = "Name can not be null") @NotBlank(message = "Name can not be empty") String name,
			@NotNull(message = "Address can not be null") @NotBlank(message = "Address can not be empty") String address,
			@NotNull(message = "Password can not be null") @NotBlank(message = "Password can not be empty") String password) {
		this.document = document;
		this.entityType = entityType;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Account> getCollectionAccount() {
		return collectionAccount;
	}

	public void setCollectionAccount(Collection<Account> collectionAccount) {
		this.collectionAccount = collectionAccount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, document, entityType, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(document, other.document)
				&& Objects.equals(entityType, other.entityType) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "Customer [document=" + document + ", entityType=" + entityType + ", name=" + name + ", address="
				+ address + ", password=" + password + "]";
	}

}
