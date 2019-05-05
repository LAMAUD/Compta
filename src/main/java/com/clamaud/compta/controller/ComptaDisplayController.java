package com.clamaud.compta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.repository.AccountRepository;

@Controller
public class ComptaDisplayController {

	@Autowired
	private AccountRepository accountRepository;
	
	
	@GetMapping("/all")
	public String list(Model model) {
		
		Iterable<Account> accounts = accountRepository.findAll();
		model.addAttribute("accounts", accounts);
		return "display";
	}
	
}
