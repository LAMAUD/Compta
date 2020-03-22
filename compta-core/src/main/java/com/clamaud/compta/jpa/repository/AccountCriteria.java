package com.clamaud.compta.jpa.repository;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class AccountCriteria {
	
	private Integer category_id;
	
	private Integer subCategory_id;
	
	@DateTimeFormat (pattern="dd/MM/yyyy")
	private Date dateFrom;
	
	@DateTimeFormat (pattern="dd/MM/yyyy")
	private Date dateTo;
	
	private boolean expensesOnly;
	
	private boolean nonCategorised;


	
	
	public AccountCriteria() {
		super();
	}

	public AccountCriteria(Integer category_id, Integer subCategory_id, Date dateFrom, Date dateTo,
			boolean expensesOnly) {
		super();
		this.category_id = category_id;
		this.subCategory_id = subCategory_id;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.expensesOnly = expensesOnly;
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

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public Integer getSubCategory_id() {
		return subCategory_id;
	}

	public void setSubCategory_id(Integer subCategory_id) {
		this.subCategory_id = subCategory_id;
	}

	public boolean isNonCategorised() {
		return nonCategorised;
	}

	public void setNonCategorised(boolean nonCategorised) {
		this.nonCategorised = nonCategorised;
	}

	

}
