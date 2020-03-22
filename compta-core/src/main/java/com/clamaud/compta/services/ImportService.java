package com.clamaud.compta.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.clamaud.compta.entity.Account;

public interface ImportService {
	
	public abstract List<Account> getAccountsFromFile(File file) throws FileNotFoundException, IOException, NumberFormatException, ParseException;

}
