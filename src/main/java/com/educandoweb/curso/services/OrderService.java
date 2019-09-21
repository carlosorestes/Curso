package com.educandoweb.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.curso.dto.OrderDTO;
import com.educandoweb.curso.dto.UserDTO;
import com.educandoweb.curso.entities.Order;
import com.educandoweb.curso.entities.User;
import com.educandoweb.curso.repositories.OrderRepository;
import com.educandoweb.curso.services.excceptions.ResourceNotFoundExcception;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}
	
	public OrderDTO findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundExcception(id));
		return new OrderDTO(entity);
	}
}
