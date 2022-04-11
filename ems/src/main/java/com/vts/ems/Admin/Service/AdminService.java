package com.vts.ems.Admin.Service;

import java.util.List;

public interface AdminService {

	
	
	public List<Object[]> LoginTypeRoles() throws Exception;
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception ;
	public List<Object[]> FormModulesList() throws Exception;
	public List<Object[]> HeaderSchedulesList(String Logintype,String FormModuleId) throws Exception;
	public List<Object[]> FormModuleList(String LoginType)throws Exception ;
	public Long FormRoleActive(String formroleaccessid) throws Exception;
}
