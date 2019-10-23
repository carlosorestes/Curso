package com.educandoweb.curso.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.curso.dto.OrderDTO;
import com.educandoweb.curso.dto.OrderItemDTO;
import com.educandoweb.curso.entities.Order;
import com.educandoweb.curso.entities.OrderItem;
import com.educandoweb.curso.entities.Product;
import com.educandoweb.curso.entities.User;
import com.educandoweb.curso.entities.enums.OrderStatus;
import com.educandoweb.curso.repositories.OrderItemRepository;
import com.educandoweb.curso.repositories.OrderRepository;
import com.educandoweb.curso.repositories.ProductRepository;
import com.educandoweb.curso.repositories.UserRepository;
import com.educandoweb.curso.services.excceptions.ResourceNotFoundExcception;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}
	
	public OrderDTO findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundExcception(id));
		authService.validateOwnOrderOrAdmin(entity);
		return new OrderDTO(entity);
	}
	
	public List<OrderDTO> findByClient(){
		User client = authService.authenticated();
		List<Order> list = repository.findByClient(client);
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<OrderItemDTO> findItems(Long id) {
		Order order = repository.getOne(id);
		authService.validateOwnOrderOrAdmin(order);
		Set<OrderItem> set = order.getItems();
		return set.stream().map(e -> new OrderItemDTO(e)).collect(Collectors.toList());
	}

	@Transactional
	public List<OrderDTO> findByClientId(Long clientId) {
		User client = userRepository.getOne(clientId);
		List<Order> list = repository.findByClient(client);
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}

	@Transactional
	public OrderDTO placeOrder(List<OrderItemDTO> dto) {
		User client = authService.authenticated();
		Order order = new Order(null, Instant.now(), client, OrderStatus.WAITING_PAYMENT);
		
		for(OrderItemDTO itemDTO: dto) {
			Product product = productRepository.getOne(itemDTO.getProductId());
			OrderItem item  = new OrderItem(order, product, itemDTO.getQuantity(), itemDTO.getPrice());
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO update(Long id, OrderDTO dto) {
		try {
			Order entity = repository.getOne(id);
			updateData(entity, dto);
			entity = repository.save(entity);
			return new OrderDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundExcception(id);
		}
	}
	
	private void updateData(Order entity, OrderDTO dto) {
		entity.setOrderStatus(dto.getOrderStatus());
	}
}
