package com.educandoweb.curso.dto;

import java.io.Serializable;
import java.time.Instant;

import com.educandoweb.curso.entities.Order;
import com.educandoweb.curso.entities.User;
import com.educandoweb.curso.entities.enums.OrderStatus;

public class OrderDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant moment;
	private OrderStatus orderStatus;
	private Long clientId;
	private String clientNome;
	private String clientEmail;
	

	public OrderDTO() {
	}


	public OrderDTO(Long id, Instant moment, OrderStatus orderStatus, Long clientId, String clientNOme,
			String clientEmail) {
		super();
		this.id = id;
		this.moment = moment;
		this.orderStatus = orderStatus;
		this.clientId = clientId;
		this.clientNome = clientNOme;
		this.clientEmail = clientEmail;
	}
	
	public OrderDTO(Order entity) {
		if (entity.getClient() == null) {
			throw new IllegalArgumentException("Error instantiating OrderDTO: client was null");
		}
		this.id = entity.getId();
		this.moment = entity.getMoment();
		this.orderStatus = entity.getOrderStatus();
		this.clientId = entity.getClient().getId();
		this.clientNome = entity.getClient().getName();
		this.clientEmail = entity.getClient().getEmail();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Instant getMoment() {
		return moment;
	}


	public void setMoment(Instant moment) {
		this.moment = moment;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}


	public Long getClientId() {
		return clientId;
	}


	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}


	public String getClientNOme() {
		return clientNome;
	}


	public void setClientNOme(String clientNOme) {
		this.clientNome = clientNOme;
	}


	public String getClientEmail() {
		return clientEmail;
	}


	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	
	public Order toEntity() {
		User client = new User(clientId, clientNome, clientEmail, null, null);
		return new Order(clientId, moment, client, orderStatus);
	}
}
