package com.clamaud.compta.jpa.account;

public enum SubCategory {
	
	TRAVAUX(Category.MAISON.toString()),
	MEUBLES(Category.MAISON.toString()),
	ELECTROMENAGER(Category.MAISON.toString()),
	REMBOURSEMENT(Category.MAISON.toString()),
	PRET(Category.MAISON.toString()),
	EAU(Category.MAISON.toString()),
	ELECTRICITE(Category.MAISON.toString()),
	IMPOT(Category.MAISON.toString()),
	CREDIT_LOGEMENT(Category.MAISON.toString()),
	ASSURANCE(Category.MAISON.toString()),
	COURSES(Category.NOURRITURE.toString()),
	RESTAURANT(Category.NOURRITURE.toString()),
	ROCK(Category.LOISIRS.toString()),
	AUTRE(Category.LOISIRS.toString()),
	DIVERS(Category.VACANCES.toString()),
	CEDRIC(Category.CREDIT.toString()),
	MATHILDE(Category.CREDIT.toString()),
	DEBUT(Category.CREDIT.toString()),
	AGIO(Category.FRAIS_BANCAIRE.toString()),
	ANNUELS(Category.FRAIS_BANCAIRE.toString()),
	TRIMESTRIELS(Category.FRAIS_BANCAIRE.toString()),
	ESPECE(Category.ESPECE.toString()),
	PERSO_MATHILDE(Category.PERSO.toString()),
	PERSO_CEDRIC(Category.PERSO.toString()),
	SANTE(Category.SANTE.toString()),
	CHAT(Category.CHAT.toString());
	
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
