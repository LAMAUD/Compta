package com.clamaud.compta.jpa.service;

import java.util.List;

import com.clamaud.compta.jpa.account.Account;

public interface AccountService {
	
	public void saveAccounts(List<Account> accounts);

}
