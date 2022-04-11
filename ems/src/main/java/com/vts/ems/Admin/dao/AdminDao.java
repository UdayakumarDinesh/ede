package com.vts.ems.Admin.dao;

import java.math.BigInteger;
import java.util.List;

public interface AdminDao {

	
	public List<Object[]> LoginTypeRoles() throws Exception ;
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception;
	public List<Object[]> FormModulesList() throws Exception;
	public List<Object[]> HeaderSchedulesList(String FormModuleId,String Logintype) throws Exception ;
	public List<Object[]> FormModuleList(String LoginType) throws Exception ;
	public List<BigInteger> FormRoleActiveList(String formroleaccessid) throws Exception;
	public Long FormRoleActive(String formroleaccessid, Long Value) throws Exception;
}
