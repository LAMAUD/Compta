package com.clamaud.compta.jpa.repository;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.clamaud.compta.jpa.account.Account;

public class AccountRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	
	public Iterable<Account> multiCriteriaSearch(AccountCriteria criteria) {

		if (criteria == null) {
			return new ArrayList<>();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("select a from Account a");
		sb.append(" where (:category is null or a.category = :category)");
		sb.append(" and (:subCategory is null or a.subCategory = :subCategory)");
		sb.append(" and (:dateFrom is null or a.date >= :dateFrom)");
		sb.append(" and (:dateTo is null or a.date <= :dateTo)");
		sb.append(" order by a.date");
		
                
		TypedQuery<Account> query = em.createQuery(sb.toString(), Account.class);

		query.setParameter("category", criteria.getCategory());
        query.setParameter("subCategory", criteria.getSubCategory());
        query.setParameter("dateFrom", criteria.getDateFrom());
        query.setParameter("dateTo", criteria.getDateTo());
		
		return query.getResultList();
	}

}
