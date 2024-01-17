package com.bechallenge.contacorrente.exception;

public class AccountInsufficientBalanceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AccountInsufficientBalanceException(String message) {
		super(message);
	}

}
