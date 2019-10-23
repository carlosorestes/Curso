package com.educandoweb.curso.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.educandoweb.curso.dto.CategoryDTO;
import com.educandoweb.curso.dto.ProductCategoresDTO;
import com.educandoweb.curso.dto.ProductDTO;
import com.educandoweb.curso.entities.Category;
import com.educandoweb.curso.entities.Product;
import com.educandoweb.curso.repositories.CategoryRepository;
import com.educandoweb.curso.repositories.ProductRepository;
import com.educandoweb.curso.services.excceptions.DatabaseExcception;
import com.educandoweb.curso.services.excceptions.ResourceNotFoundExcception;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository; 
	
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		Page<Product> list = repository.findAll(pageable);
		return list.map(e -> new ProductDTO(e));
	}
	
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundExcception(id));
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO insert(ProductCategoresDTO dto) {
		Product entity = dto.toEntity();
		setProductCategories(entity, dto.getCategories());
		entity =  repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductCategoresDTO dto) {
		try {
			Product entity = repository.getOne(id);
			updateData(entity, dto);
			entity =  repository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundExcception(id);
		}
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

	private void updateData(Product entity, ProductCategoresDTO dto) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		
		if (dto.getCategories() != null && dto.getCategories().size() > 0) {
			setProductCategories(entity, dto.getCategories());
		}
	}
	private void setProductCategories(Product entity, List<CategoryDTO> categories) {
		entity.getCategories().clear();
		for(CategoryDTO dto: categories) {
			Category category = categoryRepository.getOne(dto.getId());
			entity.getCategories().add(category);
		}
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findByCategoryPaged(Long categoryId, Pageable pageable) {
		Category category = categoryRepository.getOne(categoryId);
		Page<Product> products = repository.findByCategory(category, pageable);
		return products.map(e -> new ProductDTO(e));
	}
}
