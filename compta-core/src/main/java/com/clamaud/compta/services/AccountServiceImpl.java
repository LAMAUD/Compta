package com.clamaud.compta.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clamaud.compta.entity.Account;
import com.clamaud.compta.entity.CategoryEntity;
import com.clamaud.compta.entity.SubCategoryEntity;
import com.clamaud.compta.jpa.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public void saveAccounts(List<Account> accounts) {
		 
		for (Account account : accounts) {
      	   
      	   Date dayBefore = Date.from(convertToLocalDateViaInstant(account.getDate()).minusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
      	   Date dayAfter = Date.from(convertToLocalDateViaInstant(account.getDate()).plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
      	 
      	   Account accountBdd = accountRepository.findByDateAndLabelAndAmount(dayBefore, dayAfter, account.getLabel(), account.getAmount());
      	   if (accountBdd == null) {
      		   if (!StringUtils.isEmpty(account.getCode())) {
      			   List<Account> accountsfindByCode = accountRepository.findByCode(account.getCode());
      			   if (accountsfindByCode != null && !accountsfindByCode.isEmpty()) {
      				   List<CategoryEntity> categories = accountsfindByCode.stream().map(a -> a.getCategoryEntity()).collect(Collectors.toList());
      				   
      				   categories = categories.stream().filter(c -> c != null).collect(Collectors.toList());
      				   
      				   if (!categories.isEmpty()) {
						
      					 CategoryEntity category = categories
      							   .stream()
      							   .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
      							   .entrySet()
      							   .stream()
      							   .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
      							   .get()
      							   .getKey();
      					   
      					   SubCategoryEntity subCategory = accountsfindByCode
      							   .stream()
      							   .filter(a -> a.getCategoryEntity().equals(category)).collect(Collectors.toList())
      							   .stream()
      							   .map(a -> a.getSubCategoryEntity())
      							   .collect(Collectors.toList())
      							   .stream()
      							   .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
      							   .entrySet()
      							   .stream()
      							   .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
      							   .get()
      							   .getKey();
      					   
      					   account.setCategoryEntity(category);
      					   account.setSubCategoryEntity(subCategory);
					 }
      			   }
				}
      		   account.setImportDate(new Date());
      		   accountRepository.save(account);
      	   }
         }
		
	}
	
	private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}

}
