package com.clamaud.compta.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.account.AccountDTO;
import com.clamaud.compta.jpa.repository.AccountRepository;

@Controller
public class CategoryController {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@GetMapping("/category")
	public String list(Model model) {
		
		Iterable<Account> accounts = accountRepository.findAll();
		
		List<AccountDTO> accountsDTO = StreamSupport.stream(accounts.spliterator(), false)
				   .map(account -> convertToDto(account))
			      .collect(Collectors.toList());
		
		AccountDTO accountToSend = null;
		
		for (AccountDTO account : accountsDTO) {
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
	public RedirectView updateAccount (@ModelAttribute AccountDTO accountDTO) {
		
		Account account = accountRepository.findById(accountDTO.getId()).get();
		account.setCategory(accountDTO.getCategory());
		account.setSubCategory(accountDTO.getSubCategory());
		accountRepository.save(account);
		
		return new RedirectView("/category");
	}
	
	private AccountDTO convertToDto(Account account) {
		AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
	    return accountDTO;
	}
	
}
