package com.clamaud.compta.controller;

import org.apache.commons.lang3.StringUtils;

import com.clamaud.compta.entity.Category;
import com.clamaud.compta.entity.SubCategory;

public class CategoryUtils {
	
	
	public static Category findCategory(String category) {
		Category catego = null;
		for (Category categoryEnum : Category.values()) {
			if (StringUtils.equals(categoryEnum.toString(), category)) {
				catego = categoryEnum;
				break;
			}
			
		}
		return catego;
	}
	
	public static SubCategory findSubCategory(String subCategory) {
		SubCategory subCatego = null;
		for (SubCategory categoryEnum : SubCategory.values()) {
			if (StringUtils.equals(categoryEnum.toString(), subCategory)) {
				subCatego = categoryEnum;
				break;
			}
			
		}
		return subCatego;
	}

}
