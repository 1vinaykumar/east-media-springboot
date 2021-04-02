package com.dvk.eastmedia.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFound extends RuntimeException {
	
	private static final long serialVersionUID = -793729826955714762L;

	public ArticleNotFound(String message) {
		super(message);
	}
}
