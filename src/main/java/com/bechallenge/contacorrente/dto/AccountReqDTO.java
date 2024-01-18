package com.bechallenge.contacorrente.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Agency can not be null")
	private Integer agency;
	
	@NotNull(message = "Balance can not be null")
	private BigDecimal balance;
	
	@NotNull(message = "Status can not be null")
	private Boolean status;
	
	@NotNull(message = "Customer document can not be null")
	private Long customerDocument;

	public AccountReqDTO() {
	}

	public AccountReqDTO(
			@NotNull(message = "Agency can not be null") @NotBlank(message = "Agency can not be empty") Integer agency,
			@NotNull(message = "Balance can not be null") BigDecimal balance,
			@NotNull(message = "Status can not be null") Boolean status,
			@NotNull(message = "Customer document can not be null") Long customerDocument) {
		this.agency = agency;
		this.balance = balance;
		this.status = status;
		this.customerDocument = customerDocument;
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

	public Long getCustomerDocument() {
		return customerDocument;
	}

	public void setCustomerDocument(Long customerDocument) {
		this.customerDocument = customerDocument;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agency, balance, customerDocument, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountReqDTO other = (AccountReqDTO) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(balance, other.balance)
				&& Objects.equals(customerDocument, other.customerDocument) && Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "AccountReqDTO [agency=" + agency + ", balance=" + balance + ", status=" + status + ", customerDocument="
				+ customerDocument + "]";
	}
	
}
