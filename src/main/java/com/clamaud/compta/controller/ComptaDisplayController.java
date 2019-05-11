package com.clamaud.compta.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.account.AccountDTO;
import com.clamaud.compta.jpa.repository.AccountRepository;

@Controller
public class ComptaDisplayController {

	@Autowired
	private AccountRepository accountRepository;
	
	
	@Autowired
    private ModelMapper modelMapper;
	
	@GetMapping("/all")
	public String list(Model model) {
		
		Iterable<Account> accounts = accountRepository.findAllByOrderByDateAsc();
		
		List<AccountDTO> accountsDTO = StreamSupport.stream(accounts.spliterator(), false)
				   .map(account -> convertToDto(account))
			      .collect(Collectors.toList());
		
		double balance = 0;
		
		for (AccountDTO account : accountsDTO) {
			balance += account.getAmount();
			account.setBalance(Math.round(balance * 100) / 100);
		}
		
		model.addAttribute("accounts", accountsDTO);
		return "display";
	}
	
	
	private AccountDTO convertToDto(Account account) {
		AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
	    return accountDTO;
	}
	
}
