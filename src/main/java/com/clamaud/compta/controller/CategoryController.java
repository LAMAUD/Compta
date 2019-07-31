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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.account.AccountDTO;
import com.clamaud.compta.jpa.account.Category;
import com.clamaud.compta.jpa.account.CategoryUtils;
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
		
		List<AccountDTO> accountsToSend = accountsDTO
				.stream()
				.filter(a -> a.getSubCategory() == null)
				.collect(Collectors.toList());
		
		
		if (accountsToSend.isEmpty()) {
			return "index";
		}
		model.addAttribute("category", Category.MAISON);
		model.addAttribute("account", accountsToSend.get(0));
		return "category";
	}
	
	@GetMapping("/getAccountCategory")
	public String getCategory(@RequestParam("category") String category, @RequestParam("id") Integer id, Model model) {
		
		
		Category catego = CategoryUtils.findCategory(category);
		
		
		Account account = accountRepository.findById(id).get();
		
		model.addAttribute("category", catego);
		model.addAttribute("account", account);
		account.setCategory(catego);
		
		return "category :: form";
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
