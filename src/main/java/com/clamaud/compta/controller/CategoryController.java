package com.clamaud.compta.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.repository.AccountRepository;

@Controller
public class CategoryController {

	@Autowired
	private AccountRepository accountRepository;
	
	
	@GetMapping("/category")
	public String list(Model model) {
		
		Iterable<Account> accounts = accountRepository.findAll();
		
		Account accountToSend = null;
		
		for (Account account : accounts) {
			if (account.getSubCategory() == null) {
				accountToSend = account;
				break;
			}
			
		}
		
		
		model.addAttribute("account", accountToSend);
		return "category";
	}
	
	@PostMapping("/updateAccount")
	@Transactional
	public RedirectView updateAccount (@ModelAttribute Account account) {
		
		accountRepository.save(account);
		
		return new RedirectView("/category");
	}
	
}
