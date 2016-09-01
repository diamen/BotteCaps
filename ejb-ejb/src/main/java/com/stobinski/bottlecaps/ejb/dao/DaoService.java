package com.stobinski.bottlecaps.ejb.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.ArrayUtils;
import org.jboss.logging.Logger;

@SuppressWarnings("unchecked")
public class DaoService {
	
	@Inject
	private Logger log;

	private volatile int index = 1;

	public void update(EntityManager em, StringQuery stringQuery) {
		log.debug(stringQuery.toString());
		
		Query query;
		
		if(stringQuery.getLikeValues() == null) {
			Object[] values = ArrayUtils.addAll(stringQuery.getSetValues(), stringQuery.getWhereValues());
			query = attachParameters(em.createQuery(stringQuery.toString()), values);
		} else {
			Object[] values = ArrayUtils.addAll(stringQuery.getSetValues(), stringQuery.getLikeValues());
			query = attachLikeParameters(em.createQuery(stringQuery.toString()), values);
		}
		
		query.executeUpdate();
	}
	
	public List<Serializable> retrieveData(EntityManager em, StringQuery query) {
		log.debug(query.toString());
		
		return query.getWhereValues() == null && query.getLikeValues() == null && query.getInValues() == null ? 
				em.createQuery(query.toString()).getResultList() :
				query.getWhereValues() != null ?		
				attachParameters(em.createQuery(query.toString()), query.getWhereValues()).getResultList() :
				query.getLikeValues() != null ?	
				attachLikeParameters(em.createQuery(query.toString()), query.getLikeValues()).getResultList() :
				attachInParameters(em.createQuery(query.toString()), query.getInValues()).getResultList();
	}
	

	public List<Serializable> retrieveData(EntityManager em, StringQuery query, int limit, int offset) {
		log.debug(query.toString());
		
		return query.getWhereValues() == null && query.getLikeValues() == null && query.getInValues() == null ? 
				em.createQuery(query.toString()).setFirstResult(offset).setMaxResults(limit).getResultList() :
				query.getWhereValues() != null ?		
				attachParameters(em.createQuery(query.toString()), query.getWhereValues()).setFirstResult(offset).setMaxResults(limit).getResultList() :
				query.getLikeValues() != null ?	
				attachLikeParameters(em.createQuery(query.toString()), query.getLikeValues()).getResultList() :
				attachInParameters(em.createQuery(query.toString()), query.getInValues()).getResultList();
	}
	
	
	public void remove(EntityManager em, StringQuery query) {
		log.debug(query.toString());
		
		Query q = query.getWhereValues() == null && query.getLikeValues() == null && query.getInValues() == null ? 
				em.createQuery(query.toString()) :
				query.getWhereValues() != null ?		
				attachParameters(em.createQuery(query.toString()), query.getWhereValues()) :
				query.getLikeValues() != null ?	
				attachLikeParameters(em.createQuery(query.toString()), query.getLikeValues()) :
				attachInParameters(em.createQuery(query.toString()), query.getInValues());
				
		int numberOfRemoved = q.executeUpdate();	
		
		log.debug(String.format("Deleted %d records", numberOfRemoved));
	}
	
	private Query attachParameters(Query query, Object[] values) {
		for(int i = 1; i <= values.length; i++) {
			query.setParameter(i, values[i - 1]);
			index = i;
		}
		return query;
	}

	private Query attachLikeParameters(Query query, Object[] values) {
		for(int i = 1; i <= values.length; i++) {
			query.setParameter(StringQuery.LIKE_VALUE + index, "%" + values[i - 1] + "%");
		}
		return query;
	}
	
	private Query attachInParameters(Query query, Set<Object> params) {
		query.setParameter("params", params);
		return query;
	}
	
}
