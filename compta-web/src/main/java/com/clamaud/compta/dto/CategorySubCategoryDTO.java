package com.clamaud.compta.dto;

public class CategorySubCategoryDTO {
	
	private Integer account_id;
	
	private Integer category_id;
	
	private Integer subCategory_id;

	
	
	
	public CategorySubCategoryDTO() {
	}

	public CategorySubCategoryDTO(Integer account_id, Integer category_id, Integer subCategory_id) {
		this.account_id = account_id;
		this.category_id = category_id;
		this.subCategory_id = subCategory_id;
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

	public Integer getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}

}
