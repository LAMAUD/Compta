package com.clamaud.compta.jpa.account;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Account {

	public enum AccountType {
		
		VIREMENT,
		ACHAT,
		CREDIT,
		CHEQUE;
		
	}
	
	public enum User {
		
		MATHILDE("154"),
		CEDRIC("843");
		
		private String code;
		
		private User (String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
	}
	
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private Date date;
	
	private double amount;
	
	private String type;
	
	private String code;
	
	private String user;

	@Transient
	private String label;
	
	
	public Account(Date date, String label, double amount) {
		super();
		this.date = date;
		this.amount = amount;
		
		for (AccountType type : AccountType.values()) {
			if (label.contains(type.toString())) {
				this.type = type.toString();
			}
		}
		
		for (User user : User.values()) {
			if (type.equals(AccountType.ACHAT)) {
				if (label.contains(user.getCode())) {
					this.user = user.toString();
				}
			}
		}
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Integer getId() {
		return id;
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
	
}
