package com.vts.ems.Admin.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.Admin.dao.AdminDao;
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.FormRoleAccess;
import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicineList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.EmployeeDesig;
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminDao dao;
	@Autowired
	CHSSDao chssdao;
	
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
			test.setModifiedBy(item.getModifiedBy());
			test.setModifiedDate(item.getModifiedDate());
		return dao.EditItem(test);
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
	public List<Object[]> getMedicineList()throws Exception
	{
		return dao.getMedicineList();
	}
	@Override
	public List<Object[]> getMedicineListByTreatment(String treatmentname)throws Exception
	{
		
		if("A".equalsIgnoreCase(treatmentname)) {
			return dao.getMedicineList();
		}else {
			return dao.getMedicineListByTreatment(treatmentname);
		}	
	}
	@Override
	public List<Object[]> GetTreatmentType()throws Exception
	{
	return dao.GetTreatmentType();	
	}
	@Override
	public int Checkduplicate(String medicinename,String treatid)throws Exception{
		return dao.Checkduplicate(medicinename,treatid);
	}
	@Override
	public CHSSMedicineList getCHSSMedicine(long medicineid) throws Exception {
		return dao.getCHSSMedicine(medicineid);
	}
	
	@Override
	public Long AddMedicine(CHSSMedicineList medicine)throws Exception
	{
		return dao.AddMedicine(medicine);
	}
	
	@Override
	public Long EditMedicine(CHSSMedicineList medicine)throws Exception{
		
		
		CHSSMedicineList mlist  = dao.getCHSSMedicine(medicine.getMedicineId());
		
		mlist.setMedicineId(medicine.getMedicineId());
		mlist.setMedicineName(medicine.getMedicineName());
		mlist.setTreatTypeId(medicine.getTreatTypeId());
		
		return dao.EditMedicine(mlist);
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
		
		List<Object[]> adminlist =dao.CHSSApprovalAuth2("A");
		System.out.println(adminlist.size());
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
	public List<Object[]> GethandlingOverList(String fromdate , String todate)throws Exception
	{	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
		LocalDate ToDate= LocalDate.parse(todate, formatter);
		return dao.GethandlingOverList(Fromdate,ToDate);
	}
	
	@Override
	public List<Object[]> GetDoctorList()throws Exception
	{
		return dao.GetDoctorList();
	}
	
	@Override
	public CHSSDoctorRates getCHSSDocRate(long docrateid) throws Exception
	{
		return dao.getCHSSDocRate(docrateid);
	}
	
	@Override
	public int EditDoctorMaster(CHSSDoctorRates docrate) throws Exception
	{	
		CHSSDoctorRates doctor = dao.getCHSSDocRate(docrate.getDocRateId());
		
		//doctor.setTreatTypeId(docrate.getTreatTypeId());
		//doctor.setDocQualification(docrate.getDocQualification());
		//doctor.setDocRating(docrate.getDocRating());
		doctor.setConsultation_1(docrate.getConsultation_1());
		doctor.setConsultation_2(docrate.getConsultation_2());
		doctor.setModifiedBy(docrate.getModifiedBy());
		doctor.setModifiedDate(docrate.getModifiedDate());
		
		return dao.EditDoctorMaster(doctor);
	}
	
	@Override
	public Object[] getLabDetails()throws Exception
	{
		return dao.getLabDetails();
	}
	
	@Override
	public LabMaster GetLabDetailsToEdit(long labid)throws Exception
	{
		return dao.GetLabDetailsToEdit(labid);
	}
	
	@Override
	public List<Object[]> getLabsList()throws Exception
	{
		return dao.getLabsList();
	}
	
	@Override
	public long EditLabMaster(LabMaster labmater)throws Exception
	{
		
		LabMaster lab = dao.GetLabDetailsToEdit(labmater.getLabMasterId());
		
		lab.setLabCode(labmater.getLabCode());
		lab.setLabName(labmater.getLabName());
		lab.setLabAddress(labmater.getLabAddress());
		lab.setLabCity(labmater.getLabCity());
		lab.setLabEmail(labmater.getLabEmail());
		lab.setLabPin(labmater.getLabPin());
		lab.setLabUnitCode(labmater.getLabUnitCode());
		lab.setLabTelNo(labmater.getLabTelNo());
		lab.setLabFaxNo(labmater.getLabFaxNo());
		lab.setLabAuthority(labmater.getLabAuthority());
		lab.setLabAuthorityId(labmater.getLabAuthorityId());
		lab.setLabId(labmater.getLabId());
		lab.setLabRfpEmail(labmater.getLabRfpEmail());
		lab.setModifiedBy(labmater.getModifiedBy());
		lab.setModifiedDate(labmater.getModifiedDate());
		return dao.EditLabMaster(lab);
	}
	@Override
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception
	{ 
		
		return dao.checkAlreadyPresentForSameEmpidAndSameDates(FromEmpid, ToEmpid, FromDate, ToDate);
	}
	
	
	@Override
	public int AddHandingOver(LeaveHandingOver addhanding)throws Exception
	{
		return dao.AddHandingOver(addhanding);
	}
	
	
	@Override
	public int updateRevokeInHandingOver(long empid ,String UserId , String HandingOverId)throws Exception
	{
		return dao.updateRevokeInHandingOver(empid,UserId,HandingOverId);
	}
	
	@Override
	public List<Object[]> GetOtherItemAmlountList(String id)throws Exception
	{
		return dao.GetOtherItemAmlountList(id);
	}
	
	@Override
	public long AddOtherItemAmt(CHSSOtherPermitAmt otheramt)throws Exception
	{
		return dao.AddOtherItemAmt(otheramt);
	}
	
	@Override
	public long updateOtherAmt(String chssOtheramtid, String admAmt, String UserId)throws Exception
	{
		return dao.updateOtherAmt(chssOtheramtid, admAmt, UserId);
	}
	
	public long updateOtherItemAmt(String chssOtheramtid, String admAmt, String UserId,String basicto)throws Exception
	{
		return dao.updateOtherItemAmt(chssOtheramtid, admAmt, UserId,basicto);
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
	public int CheckduplicateItem(String treatmentname)throws Exception
	{
		return dao.CheckduplicateItem(treatmentname);
	}
	
	@Override
	public int CheckduplicateTest(String testname)throws Exception
	{
		return dao.CheckduplicateTest(testname);
	}
	
	@Override
	public long DeleteOtherAmt(String chssOtheramtid, String userid)throws Exception
	{
		return dao.DeleteOtherAmt(chssOtheramtid , userid);
	}
	
	@Override
	public List<Object[]> GetDesignation()throws Exception
	{
		return dao.GetDesignation();
	}
	@Override
	public long AddDesignation(EmployeeDesig desig)throws Exception
	{
		return dao.AddDesignation(desig);
	}
	@Override
	public EmployeeDesig GetDesignationToEdit(long desigid)throws Exception
	{
		return dao.GetDesignationToEdit(desigid);
	}
	
	@Override
	public long EditDesignation(EmployeeDesig desig)throws Exception
	{
		EmployeeDesig designation = dao.GetDesignationToEdit(desig.getDesigId());
		designation.setDesigId(desig.getDesigId());
		designation.setDesigCode(desig.getDesigCode());
		designation.setDesigLimit(desig.getDesigLimit());
		designation.setDesignation(desig.getDesignation());
		return dao.EditDesignation(designation);
	}
	

	@Override
	public Object[] DesignationAddCheck(String desigcode,String designation) throws Exception
	{
		Object[] returnobj=new Object[2];
		returnobj[0]=dao.DesignationCodeCheck(desigcode)[0].toString();
		returnobj[1]=dao.DesignationCheck(designation)[0].toString();
		return returnobj;
	}
	
	@Override
	public Object[] DesignationEditCheck(String desigcode,String designation,String desigid) throws Exception
	{
		Object[] returnobj=new Object[2];
		returnobj[0]=dao.DesignationCodeEditCheck(desigcode,desigid)[0].toString();
		returnobj[1]=dao.DesignationEditCheck(designation,desigid)[0].toString();
		return returnobj;
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
	public int GetMaxMedNo(String treatmenttype)throws Exception
	{
		return dao.GetMaxMedNo(treatmenttype);
	}
	@Override
	public int CheckduplicateTestCode(String testcode)throws Exception
	{
		return dao.CheckduplicateTestCode(testcode);
	}
	
	
	@Override
	public int updateformroleaccess(String formroleaccessid,String detailsid,String isactive,String logintype, String UserId)throws Exception{
		
			if(isactive!=null && isactive.equals("0")){
				isactive="1";
			}else {
				isactive="0";
			}
//		System.out.println("formroleaccessid  :"+formroleaccessid);
//		System.out.println("detailsid         :"+detailsid);
//		System.out.println("isactive          :"+isactive);
//		System.out.println("logintype         :"+logintype);
		int result = dao.checkavaibility(logintype,detailsid);
		System.out.println("result  :"+result);
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
	
}
