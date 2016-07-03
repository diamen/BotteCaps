package com.stobinski.bottlecaps.ejb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.jboss.logging.Logger;

@SuppressWarnings("unchecked")
public class DaoService {

	@PersistenceContext(type = PersistenceContextType.EXTENDED, unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private Logger log;

	public Object retrieveSingleData(StringQuery query) {
		log.debug(query.toString());
		
		return query.getValues() == null && query.getLikeValues() == null ? 
			entityManager.createQuery(query.toString()).getSingleResult() :
			query.getValues() != null ?		
			attachParameters(entityManager.createQuery(query.toString()), query.getValues()).getSingleResult() :
			attachLikeParameters(entityManager.createQuery(query.toString()), query.getLikeValues()).getSingleResult();	
	}
	
	public List<Serializable> retrieveData(StringQuery query) {
		log.debug(query.toString());
		
		return query.getValues() == null && query.getLikeValues() == null ? 
				entityManager.createQuery(query.toString()).getResultList() :
				query.getValues() != null ?		
				attachParameters(entityManager.createQuery(query.toString()), query.getValues()).getResultList() :
				attachLikeParameters(entityManager.createQuery(query.toString()), query.getLikeValues()).getResultList();	
	}
	
	public List<Serializable> retrieveData(StringQuery query, int limit, int offset) {
		log.debug(query.toString());
		
		return query.getValues() == null && query.getLikeValues() == null ? 
				entityManager.createQuery(query.toString()).setFirstResult(offset).setMaxResults(limit).getResultList() :
				query.getValues() != null ?		
				attachParameters(entityManager.createQuery(query.toString()), query.getValues()).setFirstResult(offset).setMaxResults(limit).getResultList() :
				attachLikeParameters(entityManager.createQuery(query.toString()), query.getLikeValues()).setFirstResult(offset).setMaxResults(limit).getResultList();	
	}
	
	public void persist(Object object) {
		entityManager.persist(object);
		entityManager.flush();
	}
	
	public void remove(Object object) {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		entityManager.flush();
	}
	
	private Query attachParameters(Query query, Object[] values) {
		for(int i = 1; i <= values.length; i++) {
			query.setParameter(i, values[i - 1]);
		}
		return query;
	}

	private Query attachLikeParameters(Query query, Object[] values) {
		for(int i = 1; i <= values.length; i++) {
			query.setParameter(StringQuery.LIKE_VALUE + i, "%" + values[i - 1] + "%");
		}
		return query;
	}
	
}
