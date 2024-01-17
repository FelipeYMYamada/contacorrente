package com.bechallenge.contacorrente.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

	private LocalDateTime timestamp;
	private Integer status;
	private String error;
	private List<String> messages = new ArrayList<String>();
	
	public ErrorResponse() {
	}

	public ErrorResponse(LocalDateTime timestamp, Integer status, String error, List<String> messages) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.messages = messages;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public void addMessage(String message) {
		messages.add(message);
	}
}
