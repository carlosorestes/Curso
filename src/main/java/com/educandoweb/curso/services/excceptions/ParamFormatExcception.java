package com.educandoweb.curso.services.excceptions;

public class ParamFormatExcception extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ParamFormatExcception(String msg){
		super(msg);
	}
}
