package com.clamaud.compta.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.clamaud.compta.jpa.account.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

	
}
