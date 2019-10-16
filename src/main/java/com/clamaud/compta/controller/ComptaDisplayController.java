package com.clamaud.compta.controller;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.account.AccountDTO;
import com.clamaud.compta.jpa.account.CategoryEntity;
import com.clamaud.compta.jpa.account.SubCategoryEntity;
import com.clamaud.compta.jpa.repository.AccountCriteria;
import com.clamaud.compta.jpa.repository.AccountRepository;
import com.clamaud.compta.jpa.repository.CategoryRepository;
import com.clamaud.compta.jpa.repository.SubCategoryRepository;
import com.google.gson.Gson;

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
		LinkedHashMap<String, Double> collect = new LinkedHashMap();
		double balance = 0.0;
		if (accountsDTO.size() > 0) {
			balance = accountsDTO.get(accountsDTO.size() - 1).getBalance();
		}
		
		if (criteria.getCategory_id() == null) {
			
			collect = accountsDTO.stream()
			.collect(Collectors.groupingBy( AccountDTO::getCategory_Name ,LinkedHashMap::new, Collectors.summingDouble(AccountDTO::getAmount)));
			
		} else if (criteria.getSubCategory_id() == null){
			collect = accountsDTO.stream()
					.collect(Collectors.groupingBy( AccountDTO::getSubCategory_Name, LinkedHashMap::new , Collectors.summingDouble(AccountDTO::getAmount)));
		} else {
			int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
			LinkedHashMap<Integer, Double>	transitions=	accountsDTO.stream()
					.filter(a -> a.getMonth() >= currentMonth - 4 )
					.collect(Collectors.toList())
					.stream().collect(Collectors.groupingBy(AccountDTO::getMonth, LinkedHashMap::new , Collectors.summingDouble(AccountDTO::getAmount)));
			
			
			for (Entry<Integer, Double> entry : transitions.entrySet()) {
				collect.put(getMonthForInt(entry.getKey()), (double) Math.round(entry.getValue()));
			}
			
		}
		Gson gson = new Gson();
		String json = gson.toJson(collect); 
		
		model.addAttribute("balance", balance);
		model.addAttribute("date", date);
		model.addAttribute("accounts", accountsDTO);
		model.addAttribute("criteria", criteria);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("subCategories", subCategoryRepository.findAll());
		model.addAttribute("accountDTO", new AccountDTO());
		model.addAttribute("collect", json);
		
		return "display";
	}
	
	
	@GetMapping("/updateSubCategories")
	public String updateSubCategories(Model model,
			@RequestParam("category") Integer category_id,
			@RequestParam("subCategory") Integer subCategory_id,
			@RequestParam("dateFrom") String dateFrom,
			@RequestParam("dateTo") String dateTo,
			@RequestParam("expensesOnly") boolean expensesOnly) throws ParseException {
		
		Date dateFromFormatted = null;
		Date dateToFormatted = null;
		
		CategoryEntity category = categoryRepository.findById(category_id).get();
		Set<SubCategoryEntity> subCategories = category.getSubCategories();
		
		if (!StringUtils.isEmpty(dateFrom) && dateFrom !=null) {
			dateFromFormatted =new SimpleDateFormat("dd/MM/yyyy").parse(dateFrom);  
		}
		
		if (!StringUtils.isEmpty(dateFrom) && dateTo != null) {
			dateToFormatted =new SimpleDateFormat("dd/MM/yyyy").parse(dateTo);
		}
		
		AccountCriteria criteria = new AccountCriteria(category_id, subCategory_id, dateFromFormatted, dateToFormatted, expensesOnly);
		
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("subCategories", subCategories);
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
	public String updateAccount(Model model, @ModelAttribute AccountDTO accountDTO) {
		System.out.println("------------------------------------------ DEDANS ------------------------------------------");
		
		Account account = accountRepository.findById(accountDTO.getId()).get();
		CategoryEntity category = categoryRepository.findById(accountDTO.getCategory_id()).get();
		SubCategoryEntity subCategory = subCategoryRepository.findById(accountDTO.getSubCategory_id()).get();
		
		account.setCategoryEntity(category);
		account.setSubCategoryEntity(subCategory);
		
		List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
		model.addAttribute("accounts", accountsDTO);
		accountRepository.save(account);
		
		return "display :: result";
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
	
	public String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
}
