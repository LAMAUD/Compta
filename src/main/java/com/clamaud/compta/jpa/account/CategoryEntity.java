package com.clamaud.compta.jpa.account;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Category")
public class CategoryEntity {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String code;
	
	private String label;
	
	private String description;
	
	@ManyToMany
	private Set<SubCategoryEntity> subCategories;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<SubCategoryEntity> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<SubCategoryEntity> subCategories) {
		this.subCategories = subCategories;
	}
	
	public void addSubCategory(SubCategoryEntity subCategory) {
		subCategories.add(subCategory);
	}
	
	public Integer getId() {
		return id;
	}
	
}
