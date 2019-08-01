package com.clamaud.compta.jpa.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.account.Category;
import com.clamaud.compta.jpa.account.SubCategory;
import com.clamaud.compta.jpa.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public void saveAccounts(List<Account> accounts) {
		 
		for (Account account : accounts) {
      	   
      	   
      	   Account accountBdd = accountRepository.findByDateAndLabelAndAmount(account.getDate(), account.getLabel(), account.getAmount());
      	   if (accountBdd == null) {
      		   if (!StringUtils.isEmpty(account.getCode())) {
      			   List<Account> accountsfindByCode = accountRepository.findByCode(account.getCode());
      			   if (accountsfindByCode != null && !accountsfindByCode.isEmpty()) {
      				   List<Category> categories = accountsfindByCode.stream().map(a -> a.getCategory()).collect(Collectors.toList());
      				   
      				   Category category = categories
      						   .stream()
      						   .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
      						   .entrySet()
      						   .stream()
      						   .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
      						   .get()
      						   .getKey();
      				   
      				    SubCategory subCategory = accountsfindByCode
      				    		.stream()
      				    		.filter(a -> a.getCategory() == category).collect(Collectors.toList())
		      				    .stream()
		      				    .map(a -> a.getSubCategory())
		      				    .collect(Collectors.toList())
		      				    .stream()
								.collect(Collectors.groupingBy(c -> c, Collectors.counting()))
								.entrySet()
								.stream()
								.max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
								.get()
								.getKey();
      				   
      				   account.setCategory(category);
      				   account.setSubCategory(subCategory);
      			   }
				}
      		   accountRepository.save(account);
      	   }
         }
		
	}

}
