package com.vts.ems.Admin.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.Admin.dao.AdminDao;
import com.vts.ems.Admin.dao.AdminDaoImpl;
import com.vts.ems.Admin.model.CalendarEvents;
import com.vts.ems.Admin.model.ContractEmployeeData;
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.FormRoleAccess;
import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.master.dto.MasterEditDto;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.model.EMSNotification;
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminDao dao;
	@Autowired
	CHSSDao chssdao;
	private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@Override
	public List<Object[]> LoginTypeRoles() throws Exception 
	{
		return dao.LoginTypeRoles();
	}
	
	@Override
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception 
	{		

		return dao.FormDetailsList(LoginType,ModuleId);
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
		logger.info(new Date() +"Inside SERVICE FormRoleActive ");

		List<BigInteger> FormRoleActiveList=dao.FormRoleActiveList(formroleaccessid);
		
		Long Value=null;
		
		for(int i=0; i<FormRoleActiveList.size();i++ ) {
			 Value=FormRoleActiveList.get(i).longValue();	
		}

		long ret=dao.FormRoleActive(formroleaccessid,Value);
			
		return ret;
	}
	
	

	
	@Override
	public Object[] getChssAprovalList()throws Exception
	{
		Object[] result= dao.getChssAprovalList();
		
		return result;
	}
	
	@Override
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id,String userid)throws Exception{
		return dao.UpdateApprovalAuth(processing,verification,approving,id,userid); 
	}
	
	@Override
	public long AddApprovalAuthority(CHSSApproveAuthority approve)throws Exception
	{
		return dao.AddApprovalAuthority(approve);
	}
	
	
	@Override
	public List<Object[]> GetRequestMessageList(String empid)throws Exception
	{
	return dao.GetRequestMessageList(empid);	
	}
	@Override
	public int DeleteRequestMsg(String requestid ,String id)throws Exception
	{
		return dao.DeleteRequestMsg(requestid, id);
	}
	@Override
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception
	{
		return dao.AddRequestMsg(reqmsg);
	}
	@Override
	public long EmpRequestNotification(EMSNotification notification)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EmpRequestNotification ");
		List<Object[]> adminlist =dao.CHSSApprovalAuth2("P");
	
		if(adminlist.size()>0) {
			long id=0;
			for(Object[] obj:adminlist) {
				EMSNotification notifi = new EMSNotification ();
				notifi.setCreatedBy(notification.getCreatedBy());
				notifi.setCreatedDate(notification.getCreatedDate());
				notifi.setIsActive(notification.getIsActive());
				notifi.setNotificationBy(notification.getNotificationBy());
				notifi.setNotificationDate(notification.getNotificationDate());
				notifi.setNotificationMessage(notification.getNotificationMessage());
				notifi.setNotificationValue(notification.getNotificationValue());
				notifi.setNotificationUrl(notification.getNotificationUrl());
				notifi.setEmpId(Long.parseLong(obj[0].toString()));
				long result = dao.AddRequestMsgNotification(notifi);
				if(result>0) {
					id++;
				}
			}
		return id;	
		}else {
			return 0l;
		}
		
	}
	@Override
	public long EmpRequestNotification1(EMSNotification notification)throws Exception
	{
		return  dao.AddRequestMsgNotification(notification);
	}
	
	@Override
	public List<Object[]> GethandlingOverList(String fromdate , String todate)throws Exception
	{	
		logger.info(new Date() +"Inside SERVICE GethandlingOverList ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
		LocalDate ToDate= LocalDate.parse(todate, formatter);
		return dao.GethandlingOverList(Fromdate,ToDate);
	}
	
	@Override
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception
	{ 
		
		return dao.checkAlreadyPresentForSameEmpidAndSameDates(FromEmpid, ToEmpid, FromDate, ToDate);
	}
	
	
	@Override
	public long AddHandingOver(LeaveHandingOver addhanding)throws Exception
	{
		return dao.AddHandingOver(addhanding);
	}
	
	
	@Override
	public int updateRevokeInHandingOver(long empid ,String UserId , String HandingOverId)throws Exception
	{
		return dao.updateRevokeInHandingOver(empid,UserId,HandingOverId);
	}
	

	
	@Override
	public List<Object[]> GetReqListFromUser()throws Exception
	{
		return dao.GetReqListFromUser();
	}
	
	@Override
	public int UpdateAdminResponse(String  responsemsg , String requestid, String UserId)throws Exception
	{
		return dao.UpdateAdminResponse( responsemsg ,  requestid ,UserId);
	}
	
	
	@Override
	public List<Object[]> GetReqResMessagelist(String emp , String fromdate , String todate )throws Exception
	{
		logger.info(new Date() +"Inside SERVICE GetReqResMessagelist ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
		LocalDate ToDate= LocalDate.parse(todate, formatter);
		return dao.GetReqResMessagelist(emp,Fromdate,ToDate);
	}
	@Override
	public List<Object[]> AllNotificationLists(long emp)throws Exception
	{
		return dao.AllNotificationLists(emp);
	}
	
	
	@Override
	public List<Object[]> GetFromemployee()throws Exception
	{
		return dao.GetFromemployee();
	}
	
	@Override
	public List<Object[]> GetToemployee()throws Exception
	{
		return dao.GetToemployee();
	}

	@Override
	public int updateformroleaccess(String formroleaccessid,String detailsid,String isactive,String logintype, String UserId)throws Exception{
		logger.info(new Date() +"Inside SERVICE updateformroleaccess ");
			if(isactive!=null && isactive.equals("0")){
				isactive="1";
			}else {
				isactive="0";
			}
		int result = dao.checkavaibility(logintype,detailsid);
		
		if(result == 0) {
			FormRoleAccess formrole = new FormRoleAccess();
			formrole.setLoginType(logintype);
			formrole.setFormDetailId(Long.parseLong(detailsid));
			formrole.setIsActive(1);
			formrole.setCreatedBy(UserId);
			formrole.setCreatedDate(sdf1.format(new Date()));	
			Long value=dao.insertformroleaccess(formrole);
			return value.intValue();
		}else {
		
			return dao.updateformroleaccess(formroleaccessid,isactive,UserId);
		}
		
	}
	@Override
	public List<Object[]> getEventTypeList() throws Exception {
		
		return dao.getEventTypeList();
	}

	@Override
	public Long addCalendarEvents(CalendarEvents events) throws Exception {
	
		return dao.addCalendarEvents(events);
	}

	@Override
	public List<Object[]> getEventsList(String year) throws Exception {
		
		return dao.getEventsList(year);
	}

	@Override
	public Object[] editCalendarEvent(String eMSEventId) throws Exception {
		
		return dao.editCalendarEvent(eMSEventId);
	}

	@Override
	public long deleteCalendarEvent(String eMSEventId) throws Exception {
		
		return dao.deleteCalendarEvent(eMSEventId);
	}

	@Override
	public Long updateCalendarEvent(String eMSEventId, String eventDate, String eventType, String eventName,
			String eventDescription) throws Exception {
	
		return dao.updateCalendarEvent(eMSEventId,eventDate,eventType,eventName,eventDescription);
	}

	@Override
	public List<Object[]> AllDepCircularSearchList(String search) throws Exception {
		
		return dao. AllDepCircularSearchList(search);
	}

	@Override
	public List<Object[]> getCircularOrdersNotice() throws Exception {
	
		return dao.getCircularOrdersNotice();
	}

	@Override
	public Long AddContractEmployeeData(ContractEmployeeData cemp) throws Exception {
		
		return dao.AddContractEmployeeData(cemp);
	}

	@Override
	public List<Object[]> getContractEmployeeList() throws Exception {
		
		return dao.getContractEmployeeList();
	}

	@Override
	public Object[] getContractEmployeeData(String contractEmpId) throws Exception {
		
		return dao.getContractEmployeeData(contractEmpId);
	}

	@Override
	public Long UpdateContractEmployeeData(ContractEmployeeData cemp) throws Exception {
		ContractEmployeeData emp=dao.getContractEmpDetails(cemp.getContractEmpId());
		emp.setContractEmpId(cemp.getContractEmpId());
		emp.setUserName(cemp.getUserName());
		emp.setEmpName(cemp.getEmpName());
		emp.setDateOfBirth(cemp.getDateOfBirth());
		emp.setEmailId(cemp.getEmailId());
		
		emp.setMobileNo(cemp.getMobileNo());
		emp.setModifiedBy(cemp.getModifiedBy());
		emp.setModifiedDate(cemp.getModifiedDate());		
		return dao.UpdateContractEmployeeData(emp);
	}

	@Override
	public String getMaxContractEmpNo() throws Exception {
		
		return dao.getMaxContractEmpNo();
	}

	@Override
	public BigInteger contractEmpAddcheck(String cuserName) throws Exception {
		
		return dao.contractEmpAddcheck(cuserName);
	}

	@Override
	public BigInteger contractEmpEditcheck(String username, String contractEmpId)throws Exception {
		
		return dao.contractEmpEditcheck(username,contractEmpId);
	}
	public static void saveFile(String CircularFilePath, String fileName, MultipartFile multipartFile)throws IOException 
	{
		logger.info(new Date() + "Inside SERVICE saveFile ");
		Path uploadPath = Paths.get(CircularFilePath);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	@Override
	public Long ContractEmpEditComments(MasterEdit masteredit, MasterEditDto masterdto) throws Exception {
		logger.info(new Date() +"Inside SERVICE AddDeptEditComments ");
		Timestamp instant= Timestamp.from(Instant.now());
		
		String timestampstr = instant.toString().replace(" ","").replace(":", "").replace("-", "").replace(".","");
		
		   if(!masterdto.getFilePath().isEmpty()) {
				String name =masterdto.getFilePath().getOriginalFilename();
				String filename= "MasterEditFile-"+timestampstr +"."+FilenameUtils.getExtension(masterdto.getFilePath().getOriginalFilename());
				String filepath=emsfilespath+"EMS//MastersEditFilePath";							
				masteredit.setFilePath(filepath+File.separator+filename);
				masteredit.setOriginalName(name);
			    saveFile(filepath , filename, masterdto.getFilePath());
				
			}	
		return dao.ContractEmpEditComments(masteredit);
	}

	
}