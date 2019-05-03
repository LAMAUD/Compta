package com.clamaud.compta.jpa.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.clamaud.compta.jpa.account.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {

	Account findByDateAndLabelAndAmount(Date date, String label, double amount);

}
