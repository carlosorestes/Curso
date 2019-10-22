package com.educandoweb.curso.services.excceptions;

public class ResourceNotFoundExcception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundExcception (Object id) {
		super("Resoucce not found. Id " + id);
	}
	
	public ResourceNotFoundExcception(String msg) {
		super(msg);
	}
}
