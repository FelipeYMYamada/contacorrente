package com.bechallenge.contacorrente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bechallenge.contacorrente.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
