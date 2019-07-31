package com.clamaud.compta.jpa.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.clamaud.compta.jpa.account.Account;

@Service
public class ImportServiceImpl implements ImportService {

	private static final String COMMA_DELIMITER = ";";
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");	

	@Override
	public List<Account> getAccountsFromFile(File file) throws FileNotFoundException, IOException, NumberFormatException, ParseException {
		
		List<List<String>> recordsFromFile = getRecordsFromFile(file);
		List<Account> accounts = getAccountsFromRecords(recordsFromFile);
		return accounts;
	}

	private List<Account> getAccountsFromRecords(List<List<String>> recordsFromFile) throws ParseException {
		List<Account> accounts = new ArrayList<>();
		
		List<List<String>> records = recordsFromFile.stream().filter(r -> r.size() > 2 && !r.get(0).contains("Date")).collect(Collectors.toList());
		
		for (List<String> record : records) {
			
			String date = record.get(0);
			String label = record.get(1);
			String amount = record.get(2);
			
			Account account = new Account(dateFormat.parse(date), label, Double.valueOf(amount.replace(",", ".")));
			accounts.add(account);
			
		}
		
		return accounts;
	}
	
	private List<List<String>> getRecordsFromFile(File file) throws FileNotFoundException, IOException {
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER);
		        records.add(Arrays.asList(values));
		    }
		}
		return records;
	}

}
