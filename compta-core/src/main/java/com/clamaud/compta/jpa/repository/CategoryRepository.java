package com.clamaud.compta.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.clamaud.compta.entity.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

	public CategoryEntity findByLabel(String label);
	
}
