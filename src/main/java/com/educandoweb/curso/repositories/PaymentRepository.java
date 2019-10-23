package com.educandoweb.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.curso.entities.Category;
import com.educandoweb.curso.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
