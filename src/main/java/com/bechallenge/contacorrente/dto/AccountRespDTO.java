package com.bechallenge.contacorrente.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class AccountRespDTO extends RepresentationModel<AccountRespDTO> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer agency;
	private BigDecimal balance;
	private Boolean status;
	private CustomerRespDTO customerId;
	
	public AccountRespDTO() {
	}

	public AccountRespDTO(Long id, Integer agency, BigDecimal balance, Boolean status, CustomerRespDTO customerId) {
		this.id = id;
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

	public CustomerRespDTO getCustomerId() {
		return customerId;
	}

	public void setCustomerId(CustomerRespDTO customerId) {
		this.customerId = customerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(agency, balance, customerId, id, status);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountRespDTO other = (AccountRespDTO) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(balance, other.balance)
				&& Objects.equals(customerId, other.customerId) && Objects.equals(id, other.id)
				&& Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "AccountRespDTO [id=" + id + ", agency=" + agency + ", balance=" + balance + ", status=" + status
				+ ", customerId=" + customerId + "]";
	}
	
}
