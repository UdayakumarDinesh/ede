package com.vts.ems.leave.service;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.vts.ems.leave.dao.LeaveDaoImpl;
import com.vts.ems.leave.dto.LeaveCheckDto;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;


@Service
public class LeaveServiceImpl implements LeaveService{

	private static final Logger logger = LogManager.getLogger(LeaveServiceImpl.class);
	DateTimeFormatUtil sdf=new DateTimeFormatUtil();
	
	
	@Autowired
	private LeaveDaoImpl dao;
	
	@Override
	public List<Object[]> PisHolidayList(String year) throws Exception {
		logger.info(new Date() +"Inside  PisHolidayList");
		return dao.PisHolidayList(year);
	}

	@Override
	public List<Object[]> LeaveCreditList(String month, String year) throws Exception {
		
		return dao.LeaveCreditList(month, year);
	}

	@Override
	public List<Employee> EmpList() throws Exception {
		
		return dao.EmpList();
	}

	@Override
	public List<Object[]> CreditList(String month) throws Exception {
		
		return dao.CreditList(month);
	}

	@Override
	public List<Object[]> LeaveCreditPreview(String month, String year,String emmNo) throws Exception {
		
		return dao.LeaveCreditPreview(month, year, emmNo);
	}

	@Override
	public long LeaveCredited(String month, String year, String emmNo) throws Exception {
		long result=0;
		try {
			for(Object[] obj:dao.LeaveCreditPreview(month, year, emmNo)) {
				LeaveRegister register=new LeaveRegister();
				register.setEMPID(obj[0].toString());
				if(obj[9]!=null&&"N".equalsIgnoreCase(obj[9].toString())){
				register.setCL(Double.parseDouble(obj[3].toString()));
				}else if(obj[9]!=null&&"Y".equalsIgnoreCase(obj[9].toString())){
					register.setCL(Double.parseDouble(obj[8].toString()));
				}
				register.setEL(Integer.parseInt(obj[4].toString()));
				register.setHPL(Integer.parseInt(obj[5].toString()));
				register.setCML(Integer.parseInt(obj[6].toString()));
				register.setRH(Integer.parseInt(obj[7].toString()));
				register.setEL_LAPSE(0);
				register.setML(0);
				register.setPL(0);
				register.setSL(0);
				register.setCCL(0);
				register.setADV_EL(0);
				register.setADV_HPL(0);
				register.setEOL(0);
				register.setMONTH(month);
				register.setYEAR(year);
				register.setSTATUS("LKU");
				if("January".equalsIgnoreCase(month)){
				register.setFROM_DATE(year+"-01-01");
				register.setTO_DATE(year+"-01-01");
				}else if("July".equalsIgnoreCase(month)){
					register.setFROM_DATE(year+"-07-01");
					register.setTO_DATE(year+"-07-01");
				}
				register.setAPPL_ID("0");
				register.setCREDITED_BY("SYSTEM");
				register.setCREDITED_ON(sdf.getSqlDateAndTimeFormat().format(new Date()));
				register.setREMARKS("SYSTEM");
				result=dao.LeaveCreditInsert(register);
				
			}
		}catch (Exception e) {
			logger.error(new Date() +"Inside LeaveCreditInsert"+e);	
		}
		return result;
	}

	@Override
	public List<Object[]> LeaveCreditInd(String month, String year, String emmNo) throws Exception {
		
		return dao.LeaveCreditInd(month, year, emmNo);
	}

	@Override
	public List<Object[]> LeaveCreditById(String registerId) throws Exception {
		// TODO Auto-generated method stub
		return dao.LeaveCreditById(registerId);
	}

	@Override
	public long LeaveCreditedAddUpdate(LeaveRegister register, String type) throws Exception {
		long result=0;
		if("U".equalsIgnoreCase(type)) {
			
			result=dao.LeaveCreditUpdateById(register);
		}else {
			result=dao.LeaveCreditAddById(register);
		}
		return result;
	}

	@Override
	public List<Object[]> GetHolidays(String Type) throws Exception {
		
		return dao.GetHolidays(Type);
	}

	@Override
	public List<Object[]> EmpDetails(String EmpNo) throws Exception {
		
		return dao.EmpDetails(EmpNo);
	}

	@Override
	public List<Object[]> EmployeeList() throws Exception {
		
		return dao.EmployeeList();
	}

	@Override
	public List<Object[]> LeaveCode(String EmpNo) throws Exception {
		
		return dao.LeaveCode(EmpNo);
	}

	@Override
	public List<Object[]> purposeList() throws Exception {
		
		return dao.purposeList();
	}

	@Override
	public String LeaveCheck(LeaveCheckDto dto) throws Exception {
		String Result="Please Try Again";
		return Result;
	}


	
	}
