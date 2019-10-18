package com.educandoweb.curso.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.educandoweb.curso.services.excceptions.DatabaseExcception;
import com.educandoweb.curso.services.excceptions.JWTAuthenticationException;
import com.educandoweb.curso.services.excceptions.JWTAuthorizationException;
import com.educandoweb.curso.services.excceptions.ResourceNotFoundExcception;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundExcception.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundExcception e,
			HttpServletRequest request){
		String error = "Resource not Found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseExcception.class)
	public ResponseEntity<StandardError> database(DatabaseExcception e,
			HttpServletRequest request){
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e,
			HttpServletRequest request){
		String error = "Validation error";
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError err = new ValidationError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		for (FieldError x: e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(JWTAuthenticationException.class)
	public ResponseEntity<StandardError> jwtAuthentication(JWTAuthenticationException e,
			HttpServletRequest request){
		String error = "Authentication error";
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(JWTAuthorizationException.class)
	public ResponseEntity<StandardError> jwtAuthorization(JWTAuthorizationException e,
			HttpServletRequest request){
		String error = "Authorization error";
		HttpStatus status = HttpStatus.FORBIDDEN;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
}
