package com.bechallenge.contacorrente.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NaturalId
	@NotNull(message = "Agency can not be null")
	private Integer agency;
	
	@NotNull(message = "Balance can not be null")
	private BigDecimal balance;
	
	@NotNull(message = "Status can not be null")
	private Boolean status;
	
	@JoinColumn( name = "customerId", referencedColumnName = "document" )
	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	private Customer customerId;

	public Account() {
	}

	public Account(Long id,
			@NotNull(message = "Agency can not be null") Integer agency,
			@NotNull(message = "Balance can not be null") BigDecimal balance,
			@NotNull(message = "Status can not be null") Boolean status, Customer customerId) {
		this.id = id;
		this.agency = agency;
		this.balance = balance;
		this.status = status;
		this.customerId = customerId;
	}
	
	public Account(
			@NotNull(message = "Agency can not be null") Integer agency,
			@NotNull(message = "Balance can not be null") BigDecimal balance,
			@NotNull(message = "Status can not be null") Boolean status, Customer customerId) {
		this.agency = agency;
		this.balance = balance;
		this.status = status;
		this.customerId = customerId;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAgency() {
		return agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agency, balance, customerId, id, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(balance, other.balance)
				&& Objects.equals(customerId, other.customerId) && Objects.equals(id, other.id)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", agency=" + agency + ", balance=" + balance + ", status=" + status
				+ ", customerId=" + customerId + "]";
	}

}
