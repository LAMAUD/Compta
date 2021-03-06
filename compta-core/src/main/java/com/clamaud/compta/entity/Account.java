package com.clamaud.compta.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

@Entity
public class Account {

	public enum AccountType {
		
		VIREMENT("VIREMENT", 999),
		ACHAT("ACHAT CB", 15),
		CREDIT("CREDIT CARTE BANCAIRE", 16),
		CHEQUE("CHEQUE", 11),
		REMISE_COMMERCIALE("REMISE COMMERCIALE", 8),
		PRELEVEMENT("PRELEVEMENT", 999),
		FORFAIT_TRIMESTRIEL("MINIMUM FORFAITAIRE TRIMESTRIEL D UTILISATION DU DECOUVERT", 0),
		AVANTAGE_CREDIT_IMMOBILIER("AVANTAGE CREDIT IMMOBILIER SUR COTISATION FORMULE DE COMPTE", 0),
		RETRAIT("CARTE X", 0),
		COTISATION_ANNUELLE("COTISATION ANNUELLE CARTE VISA CLASSIC",0),
		COTISATION_TRIMESTRIELLE("COTISATION TRIMESTRIELLE DE VOTRE FORMULE DE COMPTE", 0);
		
		private String label;
		
		private int codeLength;

		private AccountType(String label, int codeLength) {
			this.label = label;
			this.codeLength = codeLength;
		}
		
		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public int getCodeLength() {
			return codeLength;
		}

		public void setCodeLength(int codeLength) {
			this.codeLength = codeLength;
		}
		
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
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Temporal(TemporalType.DATE)
	private Date importDate;
	
	private double amount;
	
	private String type;
	
	private String code;
	
	private String user;

	private String label;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@Enumerated(EnumType.STRING)
	private SubCategory subCategory;
	
	
	@ManyToOne
	private CategoryEntity categoryEntity;
	
	@ManyToOne	
	private SubCategoryEntity subCategoryEntity;
	
	
	public Account() {
		super();
	}

	public Account(Date date, String label, double amount) {
		super();
		this.date = date;
		this.amount = amount;
		this.label = label;
		
		for (AccountType type : AccountType.values()) {
			if (label.contains(type.getLabel())) {
				this.type = type.getLabel();
				int startSubstring = label.indexOf(type.getLabel()) + type.getLabel().length();
				if (type.getCodeLength() < 30) {
					if (StringUtils.equals("CARTE X", type.getLabel())) {
						startSubstring = label.indexOf("RETRAIT DAB");
						this.code = label.substring(startSubstring);
					} else {
						this.code = label.substring(startSubstring, startSubstring + type.getCodeLength());
					}
				} else {
					if (label.contains(":")) {
						this.code = label.substring(startSubstring, label.indexOf(":"));
					} else {
						this.code = label.substring(startSubstring, 30);
					}
				}
				continue;
			}
		}
		
		for (User user : User.values()) {
			if (StringUtils.equalsIgnoreCase(type, AccountType.ACHAT.getLabel())) {
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
	
	public void setId(Integer id) {
		this.id = id;
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


	public CategoryEntity getCategoryEntity() {
		return categoryEntity;
	}

	public void setCategoryEntity(CategoryEntity categoryEntity) {
		this.categoryEntity = categoryEntity;
	}

	public SubCategoryEntity getSubCategoryEntity() {
		return subCategoryEntity;
	}

	public void setSubCategoryEntity(SubCategoryEntity subCategoryEntity) {
		this.subCategoryEntity = subCategoryEntity;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
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
	
}
