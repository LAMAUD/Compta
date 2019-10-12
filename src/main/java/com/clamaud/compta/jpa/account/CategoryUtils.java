package com.clamaud.compta.jpa.account;

import org.apache.commons.lang3.StringUtils;

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
