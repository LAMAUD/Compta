package com.clamaud.compta.jpa.account;

public enum SubCategory {
	
	TRAVAUX(Category.MAISON.toString()),
	MEUBLES(Category.MAISON.toString()),
	ELECTROMENAGER(Category.MAISON.toString()),
	REMBOURSEMENT(Category.MAISON.toString()),
	PRET(Category.MAISON.toString()),
	EAU(Category.MAISON.toString()),
	ELECTRICITE(Category.MAISON.toString()),
	COURSES(Category.NOURRITURE.toString()),
	RESTAURANT(Category.NOURRITURE.toString()),
	ROCK(Category.LOISIRS.toString()),
	AUTRE(Category.LOISIRS.toString()),
	CEDRIC(Category.CREDIT.toString()),
	MATHILDE(Category.CREDIT.toString());
	
	private String category;
	
	

	private SubCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
