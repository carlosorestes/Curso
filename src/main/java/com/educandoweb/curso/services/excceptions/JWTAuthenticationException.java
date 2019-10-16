package com.educandoweb.curso.services.excceptions;

public class JWTAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public JWTAuthenticationException(String msg){
		super(msg);
	}
}
