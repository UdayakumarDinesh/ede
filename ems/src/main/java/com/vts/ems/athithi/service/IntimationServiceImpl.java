package com.vts.ems.athithi.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.athithi.dao.IntimationDao;
import com.vts.ems.athithi.dto.NewIntimation;
import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;
import com.vts.ems.athithi.model.Intimation;
import com.vts.ems.athithi.model.IntimationEmp;

@Service
public class IntimationServiceImpl implements IntimationService {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private  SimpleDateFormat sdf4=new SimpleDateFormat("dd-MMM-yyyy");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf3 = new SimpleDateFormat("hh:mm:ss");
	
	Calendar now = Calendar.getInstance();
	
	
	@Autowired
	IntimationDao dao;
	
	@Override
	public List<Object[]> getCompnyList(String trem) throws Exception {
		
		return dao.getCompnyList(trem);
	}

	@Override
	public List<Object[]> addNewCompany(Company company) throws Exception {
	
		return dao.addNewCompany(company);
	}
	
	@Override
	public List<Object[]> getCompEmp(String companyId)throws Exception{
		
		return dao.getCompEmp(companyId);
	}
	public Long addNewEmployee(CompanyEmployee newEmp)throws Exception{
		
		return dao.addNewEmployee(newEmp);
	}
	
	public List<Object[]> getOfficerList(String groupId)throws Exception{
		return dao.getOfficerList(groupId);
	}

	@Override
	public Long addNewIntimation(NewIntimation intimation) throws Exception {
	     	java.util.Date a= new  java.util.Date(); 
		    String todayDate = sdf.format(a.getTime());
		
	        Intimation inti = new Intimation();
	        inti.setIntimationByEmpNo(intimation.getIntimationByEmpNo());
	        inti.setIntimationDate(new java.sql.Date(sdf.parse(todayDate).getTime()));
	        inti.setCompanyId( Long.parseLong(intimation.getCompnayId()));
	        inti.setFromDate(new Date(sdf.parse(intimation.getFdate()).getTime()));
	        inti.setToDate(new Date(sdf.parse(intimation.getTdate()).getTime()));
	        inti.setDuration(intimation.getDuration());
	        inti.setExpectedTime(intimation.getExpectedTime());
	        inti.setPurpose(intimation.getPurpose());
	        inti.setSpecialPermission(intimation.getSpermission());
	        inti.setOfficerEmpId(intimation.getOfficer());
	        inti.setVpStatus("I");
	        inti.setCreatedBy(intimation.getCreateBy());
	        inti.setCreatedDate(sdf1.format(new  java.util.Date()));
		    inti.setIsActive(1);
	        inti.setIntimationNo(genIntimationNo());
		    Long newIntimationId=dao.addNewIntimation(inti);
        		    
		    String[] visitors= intimation.getVisitors();
		    
		    for(String visitor:visitors) {
		    	IntimationEmp newVisitor =new IntimationEmp();
		    	newVisitor.setCompanyEmpId(Long.parseLong(visitor));
		    	newVisitor.setInitmationId(newIntimationId);
		    	newVisitor.setCreatedBy(intimation.getCreateBy());
		    	newVisitor.setCreatedDate(sdf1.format(new  java.util.Date())); 
		    	newVisitor.setIsActive(1);
		    	dao.addVisitor(newVisitor);
		    }
			return newIntimationId;
	}

	

	private String genIntimationNo() throws Exception {
		java.util.Date a= new  java.util.Date(); 
	    String todayDate = sdf.format(a.getTime());
		Date ScheduledDate= (new java.sql.Date(sdf.parse(todayDate).getTime()));
	    String number= sdf4.format(ScheduledDate).toString().toUpperCase().replace("-", "")+"/";
	    
		int IntimationCount=dao.todayIntionCount();
         if(IntimationCount==0) {
        	 number=number+1;
         }else {
        	 number=number+ ++IntimationCount; 
         }
		
		return number;
	}
	
	@Override
	public List<Object[]> getItimationList(String groupId)throws Exception{
		
		return dao.getItimationList(groupId);
	}
	

}
