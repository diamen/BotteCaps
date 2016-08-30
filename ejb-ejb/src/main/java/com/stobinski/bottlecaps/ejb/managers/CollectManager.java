package com.stobinski.bottlecaps.ejb.managers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CollectManager {

	@PersistenceContext(unitName = "bottlecaps")
	private EntityManager entityManager;
	
}
