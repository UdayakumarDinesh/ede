package com.vts.ems.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.dao.EmsDao;
import com.vts.ems.model.AuditStamping;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;
@Service
public class EMSMainServiceImpl implements EMSMainService 
{
	private static final Logger logger=LogManager.getLogger(EMSMainServiceImpl.class);
	
	private SimpleDateFormat sdf1=DateTimeFormatUtil.getSqlDateAndTimeFormat();
//	private  SimpleDateFormat sdf=util.getRegularDateFormat();
//	private SimpleDateFormat sdf2=util.getSqlDateFormat();
	
	@Autowired
	EmsDao dao;

	@Override
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception {		
		return dao.LoginStampingInsert(Stamping);
	}

	@Override
	public int LoginStampingUpdate(String Logid, String LogoutType) throws Exception {
		AuditStamping stamping=new AuditStamping();
        stamping.setAuditStampingId(dao.LastLoginStampingId(Logid));
        stamping.setLogOutType(LogoutType);
        stamping.setLogOutDateTime(sdf1.format(new Date()));
		return dao.LoginStampingUpdate(stamping);
	}

	@Override
	public Employee EmployeeInfo(long EmpId)throws Exception
	{
		return dao.EmployeeInfo(EmpId);
	}
	
	@Override
	public Object[] EmployeeData(String EmpId)throws Exception
	{
		return dao.EmployeeData(EmpId);
	}
	
	@Override
	public List<EMSNotification> NotificationList(long EmpId)throws Exception
	{
		return dao.NotificationList(EmpId);
	}
	
	@Override
	public int NotificationUpdate(String NotificationId) throws Exception
	{
		return dao.NotificationUpdate(NotificationId);
	}
	
	@Override
	public Object[] LoginExistCheck(String username) throws Exception
	{
		return dao.LoginExistCheck(username);
	}
}
