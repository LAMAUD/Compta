package com.clamaud.compta.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.clamaud.compta.jpa.account.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {

	public Account findByDateAndLabelAndAmount(Date date, String label, double amount);
	
	public List<Account> findByCode(String code);
	
	public List<Account> findAllByOrderByDateAsc();

}
