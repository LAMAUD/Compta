package com.clamaud.compta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
import com.clamaud.compta.jpa.account.CategoryEntity;
import com.clamaud.compta.jpa.account.CategoryUtils;
import com.clamaud.compta.jpa.account.SubCategoryEntity;
import com.clamaud.compta.jpa.repository.AccountCriteria;
import com.clamaud.compta.jpa.repository.AccountRepository;
import com.clamaud.compta.jpa.repository.CategoryRepository;
import com.clamaud.compta.jpa.repository.SubCategoryRepository;

@Controller
public class ComptaDisplayController {

	@Autowired
	private AccountRepository accountRepository;
	
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@GetMapping("/all")
	public String list(Model model) {
		
		List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
		
		Date date = accountRepository.findLatestDateAccount();
		
		AccountCriteria criteria = new AccountCriteria();
		
		model.addAttribute("date", date);
		model.addAttribute("criteria", criteria);
		model.addAttribute("accounts", accountsDTO);
		model.addAttribute("accountDTO", new AccountDTO());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("subCategories", subCategoryRepository.findAll());
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
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("subCategories", subCategoryRepository.findAll());
		model.addAttribute("accountDTO", new AccountDTO());
		
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
		
		return "display :: searchAccount";
	}
	
	@GetMapping("/updateSubCategory")
	public String updateSubCategory(Model model, @RequestParam("category") Integer category_id,
			@RequestParam("id") Integer account_id) {
		
		CategoryEntity category = categoryRepository.findById(category_id).get();
		Set<SubCategoryEntity> subCategories = category.getSubCategories();
		
		Account account = accountRepository.findById(account_id).get();
		account.setCategoryEntity(category);
		AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
		
		model.addAttribute("accountDTO", accountDTO);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("subCategories", subCategories);
		
		return "display :: formEditPopup";
	}
	
	
	@GetMapping("/account")
	public String getAccount(Model model, @RequestParam("id") Integer id) {
		
		Account account = accountRepository.findById(id).get();
		Integer categoryID = account.getCategoryEntity() != null ? account.getCategoryEntity().getId() : null;
		Integer subCategoryID = account.getSubCategoryEntity() != null ? account.getSubCategoryEntity().getId() : null;
		AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
		accountDTO.setCategory_id(categoryID);
		accountDTO.setSubCategory_id(subCategoryID);
		model.addAttribute("accountDTO", accountDTO);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("subCategories", subCategoryRepository.findAll());
		
		return "display :: formEditPopup";
	}
	
	@PostMapping("/updateAccountCategories")
	@ResponseBody
	public Account updateAccount(Model model, @ModelAttribute AccountDTO accountDTO) {
		System.out.println("------------------------------------------ DEDANS ------------------------------------------");
		
		Account account = modelMapper.map(accountDTO, Account.class);
		CategoryEntity category = categoryRepository.findById(accountDTO.getCategory_id()).get();
		SubCategoryEntity subCategory = subCategoryRepository.findById(accountDTO.getSubCategory_id()).get();
		
		account.setCategoryEntity(category);
		account.setSubCategoryEntity(subCategory);
		
		accountRepository.save(account);
		
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
