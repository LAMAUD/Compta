package com.clamaud.compta.jpa.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clamaud.compta.jpa.account.Account;

public class AccountRepositoryImpl {

	@PersistenceContext
	private EntityManager em;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepositoryImpl.class);

	public Account findByDateAndLabelAndAmount(Date startDate, Date endDate, String label, double amount){
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a FROM Account a WHERE a.label like CONCAT('%',:label,'%') AND a.amount=:amount AND a.date BETWEEN :startDate AND :endDate");
		
		TypedQuery<Account> query = em.createQuery(sb.toString(), Account.class);
		query.setParameter("label", label.trim());
		query.setParameter("amount", amount);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		
		List<Account> accounts = query.getResultList();
		
		return accounts.isEmpty() ? null : accounts.get(0);
	}
	
	
	public Iterable<Account> multiCriteriaSearch(AccountCriteria criteria) {

		if (criteria == null) {
			return new ArrayList<>();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("select a from Account a");
		sb.append(" where (:category_id is null or a.categoryEntity.id = :category_id)");
		sb.append(" and (:subCategory_id is null or a.subCategoryEntity.id = :subCategory_id)");
		sb.append(" and (:dateFrom is null or a.date >= :dateFrom)");
		sb.append(" and (:dateTo is null or a.date <= :dateTo)");
		
		if (criteria.isExpensesOnly()) {
			sb.append(" and (a.amount < 0)");
		}
		
		if(criteria.isNonCategorised()) {
			sb.append(" and (a.categoryEntity IS NULL)");
		}
		
		sb.append(" order by a.date");
		
		
		TypedQuery<Account> query = em.createQuery(sb.toString(), Account.class);

		query.setParameter("category_id", criteria.getCategory_id());
        query.setParameter("subCategory_id", criteria.getSubCategory_id());
        query.setParameter("dateFrom", criteria.getDateFrom());
        LOGGER.info(String.format("Date FROM : %s", criteria.getDateFrom()));
        query.setParameter("dateTo", criteria.getDateTo());
        LOGGER.info(String.format("Date TO : %s", criteria.getDateTo()));
        
		return query.getResultList();
	}

}
