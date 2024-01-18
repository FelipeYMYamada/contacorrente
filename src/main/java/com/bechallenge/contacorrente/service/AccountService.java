package com.bechallenge.contacorrente.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bechallenge.contacorrente.dto.AccountReqDTO;
import com.bechallenge.contacorrente.dto.AccountRespDTO;
import com.bechallenge.contacorrente.dto.AccountTransferDTO;
import com.bechallenge.contacorrente.exception.AccountInsufficientBalanceException;
import com.bechallenge.contacorrente.exception.AccountNotFoundException;
import com.bechallenge.contacorrente.exception.CustomerNotFoundException;
import com.bechallenge.contacorrente.mapper.MapStructMapper;
import com.bechallenge.contacorrente.model.Account;
import com.bechallenge.contacorrente.model.Customer;
import com.bechallenge.contacorrente.repository.AccountRepository;
import com.bechallenge.contacorrente.repository.CustomerRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<AccountRespDTO> findAll() {
		return MapStructMapper.INSTANCE.toListAccountDTO(repository.findAll());
	}
	
	public AccountRespDTO findById(Long id) {
		AccountRespDTO response = MapStructMapper.INSTANCE.toAccountRespDTO(
				repository.findById(id)
					.orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id)));
		
		return response;
	}
	
	public List<AccountRespDTO> findByDocument(Long document) {
		List<AccountRespDTO> list = MapStructMapper.INSTANCE.toListAccountDTO(
				repository.findByDocument(document));
		return list;
	}
	
	public Optional<Account> findByAgency(Integer agency) {
		return repository.findByAgency(agency);
	}
	
	public AccountRespDTO create(AccountReqDTO request) {
		Customer customer = customerRepository.findById(request.getCustomerDocument())
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with document: " + request.getCustomerDocument()));
		
		Account entity = new Account(request.getAgency(), request.getBalance(), true, customer);
		
		return MapStructMapper.INSTANCE.toAccountRespDTO(repository.save(entity));
	}
	
	public AccountRespDTO updateStatus(Integer agency) {
		Account entity = verifyAccountAgency(agency);
		entity.setStatus(!entity.getStatus());
		
		return MapStructMapper.INSTANCE.toAccountRespDTO(repository.save(entity));
	}
	
	public AccountRespDTO accontTransfer(AccountTransferDTO request) {
		Account entity = verifyAccountAgency(request.getAgency());
		Account destinationEntity = verifyAccountAgency(request.getDestinationAgency());
		
		if(checkAccountBalance(entity, request.getValue()))
			throw new AccountInsufficientBalanceException("Account with agency " + request.getAgency() + " does not have sufficient balance for transaction");
		
		updateBalance(destinationEntity, request.getValue(), true);
		updateBalance(entity, request.getValue(), false);
		
		return MapStructMapper.INSTANCE.toAccountRespDTO(entity);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	/**
	 * Verifca se existe uma conta com a agência informada
	 * @param Integer - Número da agência
	 * @return Account - Conta encontrada com a agência informada 
	 */
	private Account verifyAccountAgency(Integer agency) {
		return findByAgency(agency)
				.orElseThrow(() -> new AccountNotFoundException("Account not found with agency: " + agency));
	}
	
	/**
	 * Método responsável por verificar disponibilidade de saldo na conta
	 * @param Account - Conta de onde vai ser retirado o dinheiro
	 * @param BigDecimal - Valor que vai ser transferido
	 * @return
	 */
	private boolean checkAccountBalance(Account account, BigDecimal value) {
		return account.getBalance().compareTo(value) <= 0;
	}
	
	/**
	 * Atualiza saldo que atualiza saldo com o valor passado
	 * @param Account - Conta que terá seu saldo atualizado
	 * @param BigDecimal - Valor da transação
	 * @param boolean - true: valor deverá somar / false: valor deverá subtrair
	 * @return Account - Retorna Conta com seu valor atualizado
	 */
	private Account updateBalance(Account account, BigDecimal value, boolean accDestination) {
		if(accDestination)
			account.setBalance(account.getBalance().add(value));
		else
			account.setBalance(account.getBalance().subtract(value));
		
		return repository.save(account);
	}
}
