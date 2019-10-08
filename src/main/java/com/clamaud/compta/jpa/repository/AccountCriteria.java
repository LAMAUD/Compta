package com.clamaud.compta.jpa.repository;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.clamaud.compta.jpa.account.Category;
import com.clamaud.compta.jpa.account.SubCategory;

public class AccountCriteria {
	
	private Category category;
	
	private SubCategory subCategory;
	
	@DateTimeFormat (pattern="dd/MM/yyyy")
	private Date dateFrom;
	
	@DateTimeFormat (pattern="dd/MM/yyyy")
	private Date dateTo;
	
	private boolean expensesOnly;

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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public boolean isExpensesOnly() {
		return expensesOnly;
	}

	public void setExpensesOnly(boolean expensesOnly) {
		this.expensesOnly = expensesOnly;
	}

	

}
