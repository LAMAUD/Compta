package com.clamaud.compta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.account.AccountDTO;
import com.clamaud.compta.jpa.account.Category;
import com.clamaud.compta.jpa.account.CategoryUtils;
import com.clamaud.compta.jpa.repository.AccountCriteria;
import com.clamaud.compta.jpa.repository.AccountRepository;

@Controller
public class ComptaDisplayController {

	@Autowired
	private AccountRepository accountRepository;
	
	
	@Autowired
    private ModelMapper modelMapper;
	
	@GetMapping("/all")
	public String list(Model model) {
		
		List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
		
		Date date = accountRepository.findLatestDateAccount();
		
		AccountCriteria criteria = new AccountCriteria();
		
		model.addAttribute("date", date);
		model.addAttribute("criteria", criteria);
		model.addAttribute("accounts", accountsDTO);
		return "display";
	}

	
	@PostMapping("/search")
	public String searchAccounts (Model model, @ModelAttribute AccountCriteria criteria) {
		
		Iterable<Account> accounts = accountRepository.multiCriteriaSearch(criteria);
		Date date = accountRepository.findLatestDateAccount();
		List<AccountDTO> accountsDTO = getAccountsWithBalance(accounts);
		
		model.addAttribute("date", date);
		model.addAttribute("accounts", accountsDTO);
		model.addAttribute("criteria", criteria);
		
		return "display";
	}
	
	@GetMapping("/getCategory")
	public String getCategory(@RequestParam("category") String category, Model model) {
		
		Category catego = CategoryUtils.findCategory(category);
		AccountCriteria criteria = new AccountCriteria();
		criteria.setCategory(catego);
		
		Date date = accountRepository.findLatestDateAccount();
		
		model.addAttribute("date", date);
		model.addAttribute("category", catego);
		model.addAttribute("criteria", criteria);
		
		return "display :: form";
	}
	
	@GetMapping("/account")
	@ResponseBody
	public Account getAccount(@RequestParam("id") Integer id) {
		
		Account account = accountRepository.findById(id).get();
		
		return account;
	}
	
	@PostMapping("/updateAccountRecap")
	@ResponseBody
	public Account updateAccount(@RequestParam("id") Integer id, @RequestParam("category") String category,
			@RequestParam("subCategory") String subCategory) {
		System.out.println("------------------------------------------ DEDANS ------------------------------------------");
		Account account = accountRepository.findById(id).get();
		account.setCategory(CategoryUtils.findCategory(category));
		account.setSubCategory(CategoryUtils.findSubCategory(subCategory));
		
		return account;
	}
	
	private AccountDTO convertToDto(Account account) {
		AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
	    return accountDTO;
	}
	
	private List<AccountDTO> getAccountsWithBalance(Iterable<Account> accounts) {
		List<AccountDTO> accountsDTO = StreamSupport.stream(accounts.spliterator(), false)
				.map(account -> convertToDto(account))
				.collect(Collectors.toList());
		
		double balance = 0;
		
		for (AccountDTO account : accountsDTO) {
			balance += account.getAmount();
			account.setBalance(Math.round(balance * 100) / 100.00);
		}
		return accountsDTO;
	}
}
