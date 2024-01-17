package com.bechallenge.contacorrente.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult().getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
		
		ErrorResponse response = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				errors);
		
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException e) {
		ErrorResponse response = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(),
				List.of(e.getMessage()));
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND.value())
				.body(response);
	}
	
	@ExceptionHandler(CustomerDocumentDuplicatedException.class)
	public ResponseEntity<ErrorResponse> handleCustomerDocumentDuplicatedException(CustomerDocumentDuplicatedException e) {
		ErrorResponse response = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				List.of(e.getMessage()));
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST.value())
				.body(response);
	}
}
