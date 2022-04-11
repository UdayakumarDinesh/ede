package com.vts.ems.Admin.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.Admin.dao.AdminDao;
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminDao dao;
	@Override
	public List<Object[]> LoginTypeRoles() throws Exception {

		return dao.LoginTypeRoles();
	}
	
	@Override
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception {
		
		String logintype="A";
		if(LoginType!=null) {
			logintype=LoginType;
		}
		String moduleid="A";
		if(ModuleId!=null) {
			moduleid=ModuleId;
		}	
		return dao.FormDetailsList(logintype,moduleid);
	}
	
	@Override
	public List<Object[]> FormModulesList() throws Exception {

		return dao.FormModulesList();
	}
	
	@Override
	public List<Object[]> HeaderSchedulesList(String Logintype,String FormModuleId) throws Exception {

		return dao.HeaderSchedulesList(Logintype,FormModuleId);
	}
	@Override
	public List<Object[]> FormModuleList(String LoginType)throws Exception {
		
		
		return dao.FormModuleList(LoginType);
	}
	
	@Override
	public Long FormRoleActive(String formroleaccessid) throws Exception {
		
		
		
		List<BigInteger> FormRoleActiveList=dao.FormRoleActiveList(formroleaccessid);
		
		Long Value=null;
		
		for(int i=0; i<FormRoleActiveList.size();i++ ) {
			 Value=FormRoleActiveList.get(i).longValue();	
		}

		long ret=dao.FormRoleActive(formroleaccessid,Value);
			
		return ret;
	}
}
