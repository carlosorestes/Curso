package com.educandoweb.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.curso.dto.ProductDTO;
import com.educandoweb.curso.dto.UserDTO;
import com.educandoweb.curso.dto.UserInsertDTO;
import com.educandoweb.curso.entities.Product;
import com.educandoweb.curso.entities.User;
import com.educandoweb.curso.repositories.ProductRepository;
import com.educandoweb.curso.services.excceptions.DatabaseExcception;
import com.educandoweb.curso.services.excceptions.ResourceNotFoundExcception;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll();
		return list.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList());
	}
	
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundExcception(id));
		return new ProductDTO(entity);
	}
	
	public ProductDTO insert(ProductDTO dto) {
		Product entity = dto.toEntity();
		entity =  repository.save(entity);
		return new ProductDTO(entity);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundExcception(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseExcception(e.getMessage());
		}
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);
			updateData(entity, dto);
			entity =  repository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundExcception(id);
		}
	}

	private void updateData(Product entity, ProductDTO dto) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
	}
}
