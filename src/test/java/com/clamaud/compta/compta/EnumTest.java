package com.clamaud.compta.compta;

import org.junit.Test;

import com.clamaud.compta.jpa.account.SubCategory;

public class EnumTest {

	@Test
	public void enumTest() {
		
		for (SubCategory subCategory : SubCategory.values()) {
			System.out.println(subCategory);
			System.out.println(subCategory.getCategory());
		}
	}
	
}
