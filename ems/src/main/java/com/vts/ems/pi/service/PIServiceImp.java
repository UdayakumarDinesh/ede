package com.vts.ems.pi.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.service.CHSSServiceImpl;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pi.dao.PIDao;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class PIServiceImp implements PIService{

	@Autowired
	PIDao dao;
	
	@Autowired
	CHSSDao chssdao;
	
	private static final Logger logger = LogManager.getLogger(PIService.class);
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Override
	public List<Object[]> ResAddressDetails(String EmpId) throws Exception {
		
		return dao.EmployeeAddressDetails(EmpId);
	}

	@Override
	public Object[] ResAddressFormData(String addressResId) throws Exception {
		
		return dao.ResAddressFormData(addressResId);
	}

	@Override
	public List<Object[]> PermanentAddressDetails(String EmpId) throws Exception {
	
		return dao.PermanentAddressDetails(EmpId);
	}

	@Override
	public AddressPer getPerAddressData(String addressperid) throws Exception {
		
		return dao.getPerAddressData(addressperid);
	}

	@Override
	public Object[] ResToAddressId(String EmpId) throws Exception {

		return dao.ResToAddressId(EmpId);
	}

	@Override
	public long ResUpdatetoDate(Date toDate, String resAddressId) throws Exception {
		
		return dao.ResUpdatetoDate(toDate, resAddressId);
	}

	@Override
	public AddressRes ResAddressIntimated(String resaddressid) throws Exception {
		
		return dao.ResAddressIntimated(resaddressid);
	}

	@Override
	public long ResAddressForward(String resAddressId, String username, String action, String remarks, String empId,String empNo, String loginType) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ResAddressForward");
		
		AddressRes resadd = dao.getResAddressDet(resAddressId);
		int pisstatusid = resadd.getPISStatusId();
		EMSNotification notify = new EMSNotification();
		String mailbody = "";
		String Email="";
		
		if(action.equalsIgnoreCase("F")) 
		{			
			notify.setNotificationUrl("IntimationVerification.htm");
			notify.setNotificationMessage("intimation of Address change Application Received");
			if(pisstatusid==1 || pisstatusid==3 || pisstatusid==6) 
			{
				resadd.setPISStatusId(2);
				if(pisstatusid==1) {
				resadd.setSubmittedOn(sdtf.format(new Date()));
				}
				Object[] notifyto = dao.AddressIntimationAuth("");
				if(notifyto==null) {
					notify.setEmpNo((notifyto[0].toString()));
				}else {
					notify.setEmpNo((notifyto[0].toString()));
					if(notifyto[5]!=null) { 	Email = notifyto[5].toString();		}
				}
			}
			else if(pisstatusid==2) 
			{
				resadd.setPISStatusId(4);
				resadd.setVerifiedOn(sdtf.format(new Date()));
				Object[] notifyto = dao.AddressIntimationAuth("");
				if(notifyto==null) {
					notify.setEmpNo((notifyto[0].toString()));
				}else {
					notify.setEmpNo((notifyto[0].toString()));
					if(notifyto[5]!=null) { 	Email = notifyto[5].toString();		}
				}
			}
			else if(pisstatusid==4) 
			{
				resadd.setPISStatusId(7);
				resadd.setApprovedOn(sdtf.format(new Date()));
			}
			
			
			mailbody = "Intimation of Address change Application Received for Verification";
						
		}
		else if(action.equalsIgnoreCase("R"))
		{
			notify.setNotificationMessage("Intimation of Address change Application Returned");
			mailbody = "Intimation of Address change Application is Returned";
			
			if(pisstatusid==2 || pisstatusid==4) 
			{
				if(pisstatusid==2) {
				    resadd.setPISStatusId(3);
				}else if(pisstatusid==4) {
					resadd.setPISStatusId(6);
				}
				notify.setEmpNo(resadd.getEmpid().toString());
				 Object[] emp= chssdao.getEmployee(resadd.getEmpid().toString());				
				if( emp[7]!=null) { 	Email =  emp[7].toString();		}
				notify.setNotificationUrl("PersonalIntimation.htm");
			}
						

		}
		resadd.setModifiedBy(username);
		resadd.setModifiedDate(sdtf.format(new Date()));
		
		if( notify.getEmpNo()!=null)
		{		
			notify.setNotificationDate(LocalDate.now().toString());
			notify.setNotificationBy(empNo);
			notify.setIsActive(1);
			notify.setCreatedBy(username);
			notify.setCreatedDate(sdtf.format(new Date()));
			dao.NotificationAdd(notify);
		}
		
		long count = dao.AddressResEdit(resadd);
		
		return count;
	}

	@Override
	public Object[] PerAddressFormData(String addressPerId) throws Exception {
		
		return dao.PerAddressFormData(addressPerId);
	}
}
