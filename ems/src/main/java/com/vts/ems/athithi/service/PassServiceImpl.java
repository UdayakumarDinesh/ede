package com.vts.ems.athithi.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vts.ems.athithi.dao.PassDao;
import com.vts.ems.athithi.model.Pass;
import com.vts.ems.athithi.model.PassEmp;
@Service
public class PassServiceImpl implements PassService {
	
	
	
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	private int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	private  SimpleDateFormat sdf4=new SimpleDateFormat("dd-MMM-yyyy");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf3 = new SimpleDateFormat("hh:mm:ss");
	
	Calendar now = Calendar.getInstance();
	@Autowired
	PassDao dao;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public List<Object[]> pendingIntimations() throws Exception {
		
		return dao.pendingIntimations();
	}

	@Override
	public List<Object[]> intimationDetail(String intimationId) throws Exception {
		
		return dao.intimationDetail(intimationId);
	}
	
	@Override
	public List<Object[]>  intimationVisitor(String intimationId)throws Exception {
		
		return dao.intimationVisitor(intimationId);
		
	}
	@Override
	public int updatePhoto(String visitorId, String photoName)throws Exception{
		return dao.updatePhoto(visitorId,photoName);
	}
	@
    Override	
    public Long createPass(String empId, String intimationId, String[] visitorId, String[] visitorBatchId)throws Exception{
		
		java.util.Date a= new  java.util.Date(); 
	    String todayDate = sdf.format(a.getTime());
	    
	    Pass pass=new Pass();
		pass.setInitimationId(Long.parseLong(intimationId));
	    pass.setPassDate(new java.sql.Date(sdf.parse(todayDate).getTime()));
	    pass.setPassNo(getMaxPassNo());
	    pass.setCreatedBy(empId);
	    pass.setCreatedDate(sdf1.format(new  java.util.Date()));
	    Long passId= dao.createPass(pass);
	    
	    for(int i=0;i<visitorId.length;i++) {
	    PassEmp visitor=new PassEmp();
	    visitor.setPassId(passId);
	    visitor.setCompanyEmpId(Long.parseLong(visitorId[i]));
	    visitor.setBadgeNo(visitorBatchId[i]);
	    visitor.setTimeIn(sdf1.format(new  java.util.Date()));
	    visitor.setPassDate(new java.sql.Date(sdf.parse(todayDate).getTime()));
	    visitor.setCreatedBy(empId);
	    visitor.setCreatedDate(sdf1.format(new  java.util.Date()));
	    dao.addVisitor(visitor);
	    }
	    dao.updateIntimationStatus(Long.parseLong(intimationId)+"");
	    return passId;
	}
	
	private String getMaxPassNo() throws Exception {
		java.util.Date a= new  java.util.Date(); 
	    String todayDate = sdf.format(a.getTime());
		Date ScheduledDate= (new java.sql.Date(sdf.parse(todayDate).getTime()));
	    String number= sdf4.format(ScheduledDate).toString().toUpperCase().replace("-", "")+"/";
	    
		int IntimationCount=dao.todayPassCount();
         if(IntimationCount==0) {
        	 number=number+1;
         }else {
        	 number=number+ ++IntimationCount; 
         }
		
		return number;
	}

	@Override
	public List<Object[]> getIntimationDetails(String intimationId, String passId) throws Exception {
		
		return dao.getIntimationDetails(intimationId,passId);
	}
	
	@Override
	public List<Object[]> getPassVisitorList(String intimationId, String passId)throws Exception{
		return dao.getPassVisitorList(intimationId,passId);
	}

	@Override
	public List<Object[]> getCreatedPassList(String fDate, String tDate) throws Exception {
	
		return dao.getCreatedPassList(fDate,tDate);
	}

	@Override
	public List<Object[]> getPassVisitorList(String passId) throws Exception {
	
		return dao.getPassVisitorList(passId);
	}

	@Override
	public int passVisitorSubmit(String passId) throws Exception {
		
		return dao.passVisitorSubmit(passId);
	}

	@Override
	public List<Object[]> getPassReport(String fdate, String tdate) throws Exception {
		
		return dao.getPassReport(fdate,tdate);
	}
	@Override
	public int changepassword(String oldpwd,String newpwd,String empid)throws Exception{
	
		String actualoldpassword = dao.OldPassword(empid);

		if(encoder.matches(oldpwd, actualoldpassword)) {

		String newpassword=encoder.encode(newpwd);
		
		return dao.changepassword(newpassword, empid, sdf1.format(new java.util.Date()));
	}else {
		return 0;
	}
		
	}

	@Override
	public Object[] LabInfo()throws Exception
	{
		return dao.LabInfo();
	}
}
