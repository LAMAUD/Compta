package com.clamaud.compta.jpa.account;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;

public class AccountDTO {
	
	private Integer id;
	
	@DateTimeFormat (pattern="yyyy-MM-dd") 
	private Date date;
	
	private double amount;
	
	private String type;
	
	private String code;
	
	private String user;

	private String label;
	
	private double balance;
	
	private CategoryDTO categoryEntity;
	
	private SubCategoryDTO subCategoryEntity;
	
	private Integer category_id;
	
	private Integer subCategory_id;

	@Enumerated(EnumType.STRING)
	private Category category;
	
	@Enumerated(EnumType.STRING)
	private SubCategory subCategory;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public CategoryDTO getCategoryEntity() {
		return categoryEntity;
	}

	public void setCategoryEntity(CategoryDTO categoryEntity) {
		this.categoryEntity = categoryEntity;
	}

	public SubCategoryDTO getSubCategoryEntity() {
		return subCategoryEntity;
	}

	public void setSubCategoryEntity(SubCategoryDTO subCategoryEntity) {
		this.subCategoryEntity = subCategoryEntity;
	}

	public Integer getCategory_id() {
		
		return categoryEntity != null ? categoryEntity.getId() : category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public Integer getSubCategory_id() {
		return subCategoryEntity != null ? subCategoryEntity.getId() : subCategory_id;
	}

	public void setSubCategory_id(Integer subCategory_id) {
		this.subCategory_id = subCategory_id;
	}
	


}
