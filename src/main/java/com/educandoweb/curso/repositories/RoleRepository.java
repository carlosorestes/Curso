package com.educandoweb.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.curso.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
