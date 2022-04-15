package com.vts.ems.Admin.Service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.Admin.dao.AdminDao;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestSub;
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminDao dao;
	@Override
	public List<Object[]> LoginTypeRoles() throws Exception 
	{
		return dao.LoginTypeRoles();
	}
	
	@Override
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception 
	{		
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
	public List<Object[]> FormModulesList() throws Exception 
	{
		return dao.FormModulesList();
	}
	
	@Override
	public List<Object[]> HeaderSchedulesList(String Logintype,String FormModuleId) throws Exception
	{
		return dao.HeaderSchedulesList(Logintype,FormModuleId);
	}
	
	@Override
	public List<Object[]> FormModuleList(String LoginType)throws Exception 
	{
		return dao.FormModuleList(LoginType);
	}
	
	@Override
	public Long FormRoleActive(String formroleaccessid) throws Exception 
	{
		

		List<BigInteger> FormRoleActiveList=dao.FormRoleActiveList(formroleaccessid);
		
		Long Value=null;
		
		for(int i=0; i<FormRoleActiveList.size();i++ ) {
			 Value=FormRoleActiveList.get(i).longValue();	
		}

		long ret=dao.FormRoleActive(formroleaccessid,Value);
			
		return ret;
	}
	
	@Override
	public List<Object[]> OtherItems() throws Exception 
	{
		return dao.OtherItems();
	}
	
	@Override
	public List<Object[]> ChssTestMain() throws Exception
	{
		return dao.ChssTestMain();	
	}
	
	@Override
	public List<Object[]> ChssTestSub() throws Exception
	{
		return dao.ChssTestSub();	
	}
	
	@Override
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception
	{
		return dao.AddTestSub(TestSub);
	}
	
	@Override
	public CHSSTestSub testSub(String subid)throws Exception
	{
		return dao.testSub(subid);
	}
	
	@Override
	public Long EditTestSub(CHSSTestSub TestSub)throws Exception
	{
		CHSSTestSub test=dao.getTestSub(TestSub.getTestSubId());
			test.setTestMainId(TestSub.getTestMainId());
			test.setTestName(TestSub.getTestName());
			test.setTestRate(TestSub.getTestRate());
			test.setModifiedBy(TestSub.getModifiedBy());
			test.setModifiedDate(TestSub.getModifiedDate());
		return dao.EditTestSub(test);
	}
	
	@Override
	public CHSSOtherItems getOtherItem(int itemid) throws Exception
	{
		return dao.getOtherItem(itemid);
	}
	@Override
	public int AddOtherItem(CHSSOtherItems item)throws Exception
	{
		return dao.AddOtherItem(item);
	}
	
	@Override
	public int EditItem(CHSSOtherItems item)throws Exception
	{
		CHSSOtherItems test=dao.getOtherItem(item.getOtherItemId());
			test.setOtherItemName(item.getOtherItemName());
			test.setPayLevel1(item.getPayLevel1());
			test.setPayLevel2(item.getPayLevel2());
			test.setPayLevel3(item.getPayLevel3());
			test.setPayLevel4(item.getPayLevel4());
			test.setModifiedBy(item.getModifiedBy());
			test.setModifiedDate(item.getModifiedDate());
		return dao.EditItem(test);
	}
	
	@Override
	public Object[] getChssAprovalList()throws Exception{
		Object[] result= dao.getChssAprovalList();
		
		return result;
	}
}
