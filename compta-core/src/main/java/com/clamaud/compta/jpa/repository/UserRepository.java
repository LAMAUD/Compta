package com.clamaud.compta.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.clamaud.compta.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByEmail(String email);
	
}
