package com.dvk.eastmedia.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CustomExceptionResponse {
	private final LocalDateTime timeStamp;
	private final String message;
	private final String details;
	public CustomExceptionResponse(LocalDateTime timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}
}
