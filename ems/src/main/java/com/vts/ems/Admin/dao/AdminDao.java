package com.vts.ems.Admin.dao;

import java.math.BigInteger;
import java.util.List;

import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestSub;

public interface AdminDao {

	
	public List<Object[]> LoginTypeRoles() throws Exception ;
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception;
	public List<Object[]> FormModulesList() throws Exception;
	public List<Object[]> HeaderSchedulesList(String FormModuleId,String Logintype) throws Exception ;
	public List<Object[]> FormModuleList(String LoginType) throws Exception ;
	public List<BigInteger> FormRoleActiveList(String formroleaccessid) throws Exception;
	public Long FormRoleActive(String formroleaccessid, Long Value) throws Exception;
	public List<Object[]> OtherItems() throws Exception ;
	public List<Object[]> ChssTestMain() throws Exception;
	public List<Object[]> ChssTestSub() throws Exception;
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception;
	public CHSSTestSub testSub(String TestSubId)throws Exception;
	public CHSSTestSub getTestSub(long subid) throws Exception;
	public long EditTestSub(CHSSTestSub test) throws Exception;
	public CHSSOtherItems getOtherItem(int itemid) throws Exception;
	public int AddOtherItem(CHSSOtherItems item)throws Exception;
	public int EditItem(CHSSOtherItems item) throws Exception;
	public Object[] getChssAprovalList() throws Exception;
}
