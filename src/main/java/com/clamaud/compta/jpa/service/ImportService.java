package com.clamaud.compta.jpa.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.clamaud.compta.jpa.account.Account;

public interface ImportService {
	
	public abstract List<Account> getAccountsFromFile(File file) throws FileNotFoundException, IOException, NumberFormatException, ParseException;

}
