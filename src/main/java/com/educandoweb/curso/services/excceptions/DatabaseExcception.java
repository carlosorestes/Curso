package com.educandoweb.curso.services.excceptions;

public class DatabaseExcception extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DatabaseExcception(String msg){
		super(msg);
	}
}
