package com.clamaud.compta.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
import com.clamaud.compta.jpa.account.CategoryDTO;
import com.clamaud.compta.jpa.account.CategoryEntity;
import com.clamaud.compta.jpa.account.CategorySubCategoryDTO;
import com.clamaud.compta.jpa.account.CategoryUtils;
import com.clamaud.compta.jpa.account.SubCategoryDTO;
import com.clamaud.compta.jpa.account.SubCategoryEntity;
import com.clamaud.compta.jpa.repository.AccountRepository;
import com.clamaud.compta.jpa.repository.CategoryRepository;
import com.clamaud.compta.jpa.repository.SubCategoryRepository;

@Controller
public class CategoryController {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@GetMapping("/category")
	public String list(Model model) {
		
		Iterable<Account> accounts = accountRepository.findAll();
		
		List<AccountDTO> accountsDTO = StreamSupport.stream(accounts.spliterator(), false)
				   .map(account -> convertToDto(account))
			      .collect(Collectors.toList());
		
		List<AccountDTO> accountsToSend = accountsDTO
				.stream()
				.filter(a -> a.getSubCategory() == null)
				.sorted(Comparator.comparing(AccountDTO::getDate))
				.collect(Collectors.toList());
		
		
		if (accountsToSend.isEmpty()) {
			return "index";
		}
		model.addAttribute("category", Category.MAISON);
		model.addAttribute("account", accountsToSend.get(0));
		return "category";
	}
	
	
	@GetMapping("/categoryRegister")
	public String listAndAddCategory(Model model) {
		
		
		model.addAttribute("category", new CategoryEntity());
		model.addAttribute("subCategory", new SubCategoryEntity());
		model.addAttribute("categorySubCategoryDTO", new CategorySubCategoryDTO());
		
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("subCategories", subCategoryRepository.findAll());
		
		return "categoryRegister";
	}
	
	@PostMapping("/saveCategory")
	public String saveCategory(Model model, @ModelAttribute CategoryDTO categoryDTO) {
		
		
		CategoryEntity category = modelMapper.map(categoryDTO, CategoryEntity.class);
		categoryRepository.save(category);
		
		return listAndAddCategory(model);
	}
	
	@PostMapping("/saveSubCategory")
	public String saveSubCategory(Model model, @ModelAttribute SubCategoryDTO subCategoryDTO) {
		
		
		SubCategoryEntity subCategory = modelMapper.map(subCategoryDTO, SubCategoryEntity.class);
		subCategoryRepository.save(subCategory);
		
		return listAndAddCategory(model);
	}
	
	@PostMapping("/saveCategorySubCategoryDTO")
	public String saveCategorySubCategoryDTO(Model model, @ModelAttribute CategorySubCategoryDTO categorySubCategoryDTO) {
		
		CategoryEntity categoryEntity = categoryRepository.findById(categorySubCategoryDTO.getCategory_id()).get();
		SubCategoryEntity subCategoryEntity = subCategoryRepository.findById(categorySubCategoryDTO.getSubCategory_id()).get();
		categoryEntity.addSubCategory(subCategoryEntity);
		
		categoryRepository.save(categoryEntity);
		
		return listAndAddCategory(model);
	}
	
	
	@PostMapping("/deleteCatego")
	public String deleteCatego(Model model, @RequestParam("id") Integer id) {
		
		CategoryEntity category = categoryRepository.findById(id).get();
		categoryRepository.delete(category);
		model.addAttribute("categories", categoryRepository.findAll());
		
		return "categoryRegister :: resultCatego";
	}
	
	@PostMapping("/deleteSubCatego")
	public String deleteSubCatego(Model model, @RequestParam("id") Integer id) {
		
		SubCategoryEntity subCategory = subCategoryRepository.findById(id).get();
		subCategoryRepository.delete(subCategory);
		model.addAttribute("subCategories", subCategoryRepository.findAll());
		
		return "categoryRegister :: resultSubCatego";
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
