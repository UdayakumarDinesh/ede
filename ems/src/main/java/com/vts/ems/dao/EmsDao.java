package com.vts.ems.dao;

import com.vts.ems.model.AuditStamping;

public interface EmsDao
{
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception;
}
