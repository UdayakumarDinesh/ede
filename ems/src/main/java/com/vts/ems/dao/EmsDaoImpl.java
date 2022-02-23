package com.vts.ems.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vts.ems.model.AuditStamping;

@Transactional
@Repository
public class EmsDaoImpl implements EmsDao 
{
	@PersistenceContext
	EntityManager manager;
	
	@Override
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception 
	{
		manager.persist(Stamping);
		manager.flush();
		return Stamping.getAuditStampingId();
	}
}
