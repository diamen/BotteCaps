package com.stobinski.bottlecaps.ejb.common;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.stobinski.bottlecaps.ejb.entities.Caps;

public class DaoService {

	@PersistenceContext(type = PersistenceContextType.EXTENDED, unitName = "bottlecaps")
	private EntityManager entityManager;
	
	@Inject
	private Logger log;
	
	@SuppressWarnings("unchecked")
	public List<Serializable> retrieveAllData(Class<? extends Serializable> serial) {
		Query query = entityManager.createQuery("SELECT e FROM " + serial.getSimpleName() + " e");
		
		List<Serializable> list = query.getResultList();
		
		log.debug(list);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Serializable> retrieveDataWithLimit(Class<? extends Serializable> serial, int limit, int offset) {
		Query query = entityManager.createQuery("SELECT e FROM " + serial.getSimpleName() + " e")
				.setFirstResult(offset).setMaxResults(limit);
		
		List<Serializable> list = query.getResultList();
		
		log.debug(list);
		
		return list;
	}
	
	public Long getCount(Class<? extends Serializable> serial) {
		Query query = entityManager.createQuery("SELECT COUNT(e) FROM " + serial.getSimpleName() + " e");
		Long count = (Long) query.getSingleResult();
		
		log.debug(count);
		
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Serializable> getFilteredList(Class<? extends Serializable> serial, String field, Object value) {
		
		Query query = entityManager.createQuery("SELECT e FROM " + serial.getSimpleName() + " e WHERE e." + field + " = ?1")
				.setParameter(1, value);
		
		List<Serializable> list = query.getResultList();
		
		log.debug(list);
		
		return list;	
	}
 	
	@SuppressWarnings("unchecked")
	public List<Serializable> getListLike(Class<? extends Serializable> serial, String field, String value) {
		value = value.replace('*', '%');
		Query query = entityManager.createQuery("SELECT e FROM " + serial.getSimpleName() + " e WHERE e." + field + " LIKE :likeValue")
				.setParameter("likeValue", "%" + value + "%");
		
		List<Serializable> list = query.getResultList();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Integer getLastFileName() {
		String sQuery = "SELECT e.file_name FROM " + Caps.class.getSimpleName() + " e";
		Query query = entityManager.createQuery(sQuery);
		List<String> list = query.getResultList();
		return list.stream().mapToInt(e -> Integer.valueOf(e)).max().getAsInt();
	}
	
	public Serializable getSingle(Class<? extends Serializable> serial, String[] field, Object[] value) {
		
		StringBuilder sb = new StringBuilder("SELECT e FROM " + serial.getSimpleName() + " e WHERE ");
		
		for(int i = 1; i <= field.length; i++) {
			sb.append("e." + field[i - 1] + " = ?" + i);
			
			if(i != field.length)
				sb.append(" AND ");
		}
		
		Query query = entityManager.createQuery(sb.toString());
		
		for(int i = 1; i <= field.length; i++) {
			query.setParameter(i, value[i - 1]);
		}
		
		Serializable singleResult = (Serializable) query.getSingleResult();
		
		log.debug(singleResult);
		
		return singleResult;	
	}
	
	public void persist(Object object) {
		entityManager.persist(object);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insert(Class<? extends Serializable> serial, String[] field, Object[] value) {
		StringBuilder sb1 = new StringBuilder("INSERT INTO " + serial.getSimpleName() + " (");
		StringBuilder sb2 = new StringBuilder(") VALUES (");
		
		for(int i = 1; i <= field.length; i++) {
			sb1.append("" + field[i - 1]);
			sb2.append("?");
			
			if(i != field.length) {
				sb1.append(",");
				sb2.append(",");
			} else {
				sb2.append(")");
			}
		}
		
		String sQuery = sb1.toString() + sb2.toString();
		log.debug(sQuery);
		Query query = entityManager.createNativeQuery(sQuery);
		
		for(int i = 1; i <= field.length; i++) {
			query.setParameter(i, value[i - 1]);
		}
		
		query.executeUpdate();	
	}
	
}
