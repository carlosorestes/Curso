package com.educandoweb.curso.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.curso.entities.Order;
import com.educandoweb.curso.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByClient(User client);

}
