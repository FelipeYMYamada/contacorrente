package com.bechallenge.contacorrente.dto;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AccountTransferDTO {

	@NotNull(message = "Agency can not be null")
	private Integer agency;
	
	@NotNull(message = "Destination agency can not be null")
	private Integer destinationAgency;
	
	@NotNull(message = "Value can not be null")
	@Positive(message = "Only positive numbers and greater than zero")
	private BigDecimal value;

	public AccountTransferDTO() {
	}

	public AccountTransferDTO(@NotNull(message = "Agency can not be null") Integer agency,
			@NotNull(message = "Destination agency can not be null") Integer destinationAgency,
			@NotNull(message = "Value can not be null") @Positive(message = "Only positive numbers and greater than zero") BigDecimal value) {
		this.agency = agency;
		this.destinationAgency = destinationAgency;
		this.value = value;
	}

	public Integer getAgency() {
		return agency;
	}

	public void setAgency(Integer agency) {
		this.agency = agency;
	}

	public Integer getDestinationAgency() {
		return destinationAgency;
	}

	public void setDestinationAgency(Integer destinationAgency) {
		this.destinationAgency = destinationAgency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agency, destinationAgency, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountTransferDTO other = (AccountTransferDTO) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(destinationAgency, other.destinationAgency)
				&& Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "AccountTransferDTO [agency=" + agency + ", destinationAgency=" + destinationAgency + ", value=" + value
				+ "]";
	}
	
}
