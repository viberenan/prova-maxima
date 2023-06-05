package com.renan.maxima.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -6763230595777390028L;

	public NaoEncontradoException(String mensagem) {
		super(mensagem);
	}
}
