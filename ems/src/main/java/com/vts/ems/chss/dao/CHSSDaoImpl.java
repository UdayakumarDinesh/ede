package com.vts.ems.chss.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class CHSSDaoImpl implements CHSSDao {

	@PersistenceContext
	EntityManager manager;
	
	
}
