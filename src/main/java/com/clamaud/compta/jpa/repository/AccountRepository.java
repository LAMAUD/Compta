package com.clamaud.compta.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.clamaud.compta.jpa.account.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {

}
