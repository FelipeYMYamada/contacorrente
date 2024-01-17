package com.bechallenge.contacorrente.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bechallenge.contacorrente.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
	@Query(value = "SELECT a.* FROM account a WHERE a.customer_id = ?", nativeQuery = true)
	List<Account> findByDocument(@Param("document") Long document);
	
	@Query(value = "SELECT a.* FROM account a WHERE a.agency = ?", nativeQuery = true)
	Optional<Account> findByAgency(@Param("agency") Integer agency);
}
