package com.vts.ems.Admin.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.Admin.model.CircularList;
import com.vts.ems.Admin.model.DoctorList;
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.FormRoleAccess;
import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.chss.dao.CHSSDaoImpl;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicineList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.utils.DateTimeFormatUtil;
@Transactional
@Repository
public class AdminDaoImpl implements AdminDao{

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	@PersistenceContext
	EntityManager manager;
	
	
	
	private static final String LOGINTYPEROLES="SELECT LoginTypeId,LoginType,LoginDesc FROM login_type";
	@Override
	public List<Object[]> LoginTypeRoles() throws Exception {

		Query query=manager.createNativeQuery(LOGINTYPEROLES);
		List<Object[]> LoginTypeRoles=(List<Object[]>)query.getResultList();
		
		return LoginTypeRoles;
	}
	
	private static final String FORMDETAILSLIST="SELECT b.formroleaccessid,a.formdetailid,a.formmoduleid,a.formdispname,b.isactive , b.logintype FROM  (SELECT fd.formdetailid,fd.formmoduleid,fd.formdispname FROM form_detail fd WHERE fd.isactive=1 AND  CASE WHEN :formmoduleid <> '0' THEN fd.formmoduleid =:formmoduleid ELSE 1=1 END) AS a LEFT JOIN  (SELECT b.formroleaccessid,b.formdetailid AS 'detailid' ,b.logintype,b.isactive FROM form_detail a ,form_role_access b  WHERE a.formdetailid=b.formdetailid AND b.logintype=:logintype AND CASE WHEN :formmoduleid <> '0' THEN a.formmoduleid =:formmoduleid ELSE 1=1 END ) AS b ON a.formdetailid = b.detailid";
	@Override
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception {

		Query query=manager.createNativeQuery(FORMDETAILSLIST);
		query.setParameter("logintype", LoginType);
		query.setParameter("formmoduleid", ModuleId);
		List<Object[]> FormDetailsList=(List<Object[]>)query.getResultList();
		
		return FormDetailsList;
	}
	private static final String FORMMODULELIST="SELECT FormModuleId,FormModuleName,ModuleUrl,Isactive,IsActive FROM form_module WHERE isactive=1";
	@Override
	public List<Object[]> FormModulesList() throws Exception {
		
		Query query=manager.createNativeQuery(FORMMODULELIST);
		List<Object[]> FormModulesList=(List<Object[]>)query.getResultList();
		
		return FormModulesList;
	}
	private static final String HEADERSCHEDULELIST="SELECT a.formname,a.formurl,a.formdetailid FROM form_detail a , form_role_access b WHERE a.formdetailid=b.formdetailid AND a.formmoduleid=:formmoduleid AND  b.isactive='1'AND b.logintype=:logintype";
	@Override
	public List<Object[]> HeaderSchedulesList(String FormModuleId,String Logintype) throws Exception {

		logger.info(new Date() +"Inside HeaderSchedulesList");
		Query query=manager.createNativeQuery(HEADERSCHEDULELIST);
		query.setParameter("logintype",Logintype);
		query.setParameter("formmoduleid", FormModuleId);
		List<Object[]> HeaderSchedulesList=(List<Object[]>)query.getResultList();		
			
		return HeaderSchedulesList;
	}
	
	private static final String FROMMODULELIST = "SELECT DISTINCT a.formmoduleid , a.formmodulename  , a.moduleurl,a.isactive ,a.moduleicon FROM form_module a, form_detail b, form_role_access c WHERE a.isactive='1' AND a.formmoduleid=b.formmoduleid AND b.formdetailid=c.formdetailid AND c.logintype=:LOGINTYPE AND c.isactive=1 ORDER BY a.formmoduleid";
	@Override
	public List<Object[]> FormModuleList(String LoginType) throws Exception {
		logger.info(new Date() +"Inside FormModuleList");	
		Query query = manager.createNativeQuery(FROMMODULELIST);
		query.setParameter("LOGINTYPE", LoginType);
		List<Object[]> FormModuleList= query.getResultList();
		return FormModuleList;
	}
	private static final String FORMROLEACTIVELIST="SELECT isactive FROM form_role_access WHERE formroleaccessid=:formroleaccessid";
	@Override
	public List<BigInteger> FormRoleActiveList(String formroleaccessid) throws Exception {
		
		Query query=manager.createNativeQuery(FORMROLEACTIVELIST);
		query.setParameter("formroleaccessid", formroleaccessid);
		List<BigInteger> FormRoleActiveList=(List<BigInteger>)query.getResultList();
		
		return FormRoleActiveList;
	}
	

	private static final String FORMROLEACTIVE0="UPDATE form_role_access SET isactive=:isactive WHERE formroleaccessid=:formroleaccessid";
	private static final String FORMROLEACTIVE1="UPDATE form_role_access SET isactive=:isactive WHERE formroleaccessid=:formroleaccessid";
	
	@Override
	public Long FormRoleActive(String formroleaccessid, Long Value) throws Exception {

		logger.info(new java.util.Date() +"Inside FormRoleActive");
		int count=0;
		
		System.out.println("Inside UPDATE " + formroleaccessid + Value);
		
		if(Value.equals(1L)) {
			
			System.out.println("Inside if " + formroleaccessid + Value);
			
			Query query=manager.createNativeQuery(FORMROLEACTIVE0);
			query.setParameter("formroleaccessid", formroleaccessid);
			query.setParameter("isactive", "0");
			count=query.executeUpdate();
		}
		if(Value.equals(0L)) {
			Query query=manager.createNativeQuery(FORMROLEACTIVE1);
			query.setParameter("formroleaccessid", formroleaccessid);
			query.setParameter("isactive", "1");
			count=query.executeUpdate();
		}
		
		return (long) count;
	}
	
	

	private static final String OTHERITEM = "SELECT  otheritemid , otheritemname FROM chss_other_items ORDER BY otheritemid DESC";
	@Override
	public List<Object[]> OtherItems() throws Exception {
		logger.info(new Date() +"Inside OtherItems");	
		Query query = manager.createNativeQuery(OTHERITEM);
		
		List<Object[]> FormModuleList= query.getResultList();
		return FormModuleList;
	}
	
	private static final String TESTMAIN = "SELECT a.testsubid ,a.testname , a.testrate ,b.testmainname ,a.testcode FROM chss_test_sub a , chss_test_main b WHERE isactive='1' AND a.testmainid=b.testmainid ORDER BY a.testsubid DESC";
	@Override
	public List<Object[]> ChssTestSub() throws Exception
	{
		 logger.info(new Date() +"Inside ChssTestMain");	
		 Query query = manager.createNativeQuery(TESTMAIN);
		 List<Object[]> FormModuleList= query.getResultList();
	return FormModuleList;
    } 
	private static final String TESTSUB="SELECT testmainid ,testmainname ,testmaintype FROM chss_test_main";
	@Override
	public List<Object[]>ChssTestMain () throws Exception
	{
		 logger.info(new Date() +"Inside ChssTestSub");	
		 Query query = manager.createNativeQuery(TESTSUB);
		 List<Object[]> FormModuleList= query.getResultList();
	return FormModuleList;
    }
	
	@Override
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception
	{
		logger.info(new Date() + "Inside AddTestSub()");
		try {
			manager.persist(TestSub);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TestSub.getTestSubId();
	}
	
	private static final String GETTESTSUB="FROM CHSSTestSub WHERE TestSubId=:TestSubId";
	
	@Override
	public CHSSTestSub testSub(String TestSubId)throws Exception
	{
		logger.info(new Date() + "Inside testSub()");
		
		try {
			return manager.find(CHSSTestSub.class, Long.parseLong(TestSubId));		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
		
	}
	
	@Override
	public CHSSTestSub getTestSub(long subid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getTestSub()");
		try {
			return manager.find(CHSSTestSub.class, subid);
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public long EditTestSub(CHSSTestSub test) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditTestSub()");
		try {
			manager.merge(test);
			manager.flush();
			
			return test.getTestSubId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
	@Override
	public CHSSOtherItems getOtherItem(int itemid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getOtherItem()");
		try {
			return manager.find(CHSSOtherItems.class, itemid);			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	@Override
	public int AddOtherItem(CHSSOtherItems item)throws Exception
	{
		logger.info(new Date() + "Inside AddOtherItem()");
		try {
			manager.persist(item);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return item.getOtherItemId();
	}
	@Override
	public int EditItem(CHSSOtherItems item) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditItem()");
		try {
			manager.merge(item);
			manager.flush();		
			return item.getOtherItemId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	private static final String CHSSAPPROVAL="SELECT a.approveauthlistid ,a.po , a.vo, a.ao FROM chss_approve_auth a ,employee b, employee c ,employee d WHERE a.po=b.empid AND a.vo=c.empid AND a.ao=d.empid AND a.isactive='1'";
	@Override
	public Object[]   getChssAprovalList() throws Exception
	{
		 logger.info(new Date() +"Inside getChssAprovalList()");	
		 try {
			 Query query = manager.createNativeQuery(CHSSAPPROVAL);
			 
			 List<Object[]> list = (List<Object[]>)query.getResultList();
			 
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}	
    }
	
	private static final String APPROVALAUTH="UPDATE chss_approve_auth SET po=:processing , vo=:verification , ao=:approving , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE ApproveAuthListId=:id";
	@Override
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id ,String userid)throws Exception
	{
         logger.info(new Date() + "Inside UpdateApprovalAuth()");
		
		try {
			Query query = manager.createNativeQuery(APPROVALAUTH);
			
			query.setParameter("modifiedby", userid);
			query.setParameter("modifieddate",sdtf.format(new Date()) );
			query.setParameter("processing",processing);
			query.setParameter("verification",verification );
			query.setParameter("approving",approving);
			query.setParameter("id",id);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long AddApprovalAuthority(CHSSApproveAuthority approva)throws Exception
	{
		logger.info(new Date() + "Inside AddApprovalAuthority()");
		try {
			manager.persist(approva);
			manager.flush();
			return approva.getApproveAuthListId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
		
	}
	private static final String MEDICINELIST="SELECT a.medicineid , b.treatmentname , a.medicinename FROM chss_medicines_list a ,chss_treattype b WHERE a.treattypeid=b.treattypeid ORDER BY a.medicineid DESC";
	@Override
	public List<Object[]>  getMedicineList()throws Exception
	{
		 logger.info(new Date() +"Inside GetMedicineList()");	
		 try {
			 Query query = manager.createNativeQuery(MEDICINELIST);
			 List<Object[]> List= query.getResultList();
			 return List;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String MEDICINELISTBYTREATMENT="SELECT a.medicineid , b.treatmentname , a.medicinename,a.medno  FROM chss_medicines_list a ,chss_treattype b WHERE a.treattypeid=b.treattypeid AND a.treattypeid =:treatmentid";
	@Override
	public List<Object[]>  getMedicineListByTreatment(String treatmentname)throws Exception
	{
		 logger.info(new Date() +"Inside GetMedicineList()");	
		 try {
			 Query query = manager.createNativeQuery(MEDICINELISTBYTREATMENT);
			 query.setParameter("treatmentid", treatmentname);
			 List<Object[]> List= query.getResultList();
			 return List;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String TREATMENTTYPE="SELECT treattypeid , treatmentname FROM chss_treattype";
	@Override
	public List<Object[]> GetTreatmentType()throws Exception
	{
		 logger.info(new Date() +"Inside GetTrateMentType()");	
		 try {
			 Query query = manager.createNativeQuery(TREATMENTTYPE);
			 List<Object[]> List= query.getResultList();
			 return List;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String CHECKMEDICINE="SELECT COUNT(medicineid) FROM chss_medicines_list WHERE medicinename=:medicinename AND treattypeid=:treatid";
	@Override
	public int Checkduplicate(String medicinename ,String treatid)throws Exception
	{
		 logger.info(new Date() +"Inside Checkduplicate()");	
		 try {
			Query query = manager.createNativeQuery(CHECKMEDICINE);
			query.setParameter("medicinename", medicinename);
			query.setParameter("treatid", treatid);
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

				return result;
		  }catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public CHSSMedicineList getCHSSMedicine(long medicineid) throws Exception {
		logger.info(new Date() + "Inside getCHSSMedicine()");
		CHSSMedicineList memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<CHSSMedicineList> cq = cb.createQuery(CHSSMedicineList.class);
			Root<CHSSMedicineList> root = cq.from(CHSSMedicineList.class);
			Predicate p1 = cb.equal(root.get("MedicineId"), medicineid);
			cq = cq.select(root).where(p1);
			TypedQuery<CHSSMedicineList> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	@Override
	public Long AddMedicine(CHSSMedicineList medicine)throws Exception
	{
		logger.info(new Date() + "Inside AddMedicine()");
		try {
			manager.persist(medicine);
			manager.flush();
			return medicine.getMedicineId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
		
	}
	
	@Override
	public Long EditMedicine(CHSSMedicineList item) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditMedicine()");
		try {
			manager.merge(item);
			manager.flush();		
			return item.getMedicineId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}		
	}
	
	private static final String REQUESTMSGLIST = "SELECT emprequestid, requestmessage ,responsemessage FROM ems_emp_request WHERE empid=:empid and isactive='1'";
	@Override
	public List<Object[]> GetRequestMessageList(String empid) throws Exception {
		logger.info(new Date() +"Inside GetRequestMessageList()");	
		try {
			Query query = manager.createNativeQuery(REQUESTMSGLIST);
			query.setParameter("empid", empid);
			List<Object[]> MsgList= query.getResultList();
			return MsgList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	private static final String DELETEREQUESTMSG="UPDATE ems_emp_request SET IsActive=:isactive ,ModifiedBy=:modifiedby ,ModifiedDate=:modifieddate WHERE EmpRequestId=:requestid";
	@Override
	public int DeleteRequestMsg(String requestid ,String modifiedby)throws Exception
	{
         logger.info(new Date() + "Inside DeleteRequestMsg()");
		
		try {
			Query query = manager.createNativeQuery(DELETEREQUESTMSG);			
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate",sdtf.format(new Date()) );			
			query.setParameter("isactive","0");
			query.setParameter("requestid",requestid);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception
	{
		logger.info(new Date() + "Inside AddRequestMsg()");
		try {
			manager.persist(reqmsg);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqmsg.getEmpRequestId();
	}
	
	@Override
	public long AddRequestMsgNotification(EMSNotification notification)throws Exception
	{
		logger.info(new Date() + "Inside AdDRequestMsgNotification()");
		try {
			manager.persist(notification);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return notification.getNotificationId();
	}
	
	
	private static final String CHSSAPPROVALAUTH2  ="SELECT e.empid,e.empname,ed.Designation, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.DesignationId = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType  ";
	@Override
	public List<Object[]> CHSSApprovalAuth2(String Logintype) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApprovalAuth2()");
		try {
			
			Query query= manager.createNativeQuery(CHSSAPPROVALAUTH2);
			query.setParameter("loginType", Logintype);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String HANDLINGOVERLIST="SELECT a.handingover_id , b.empname as 'fromemp' ,c.empname as 'toemp',a.from_date, a.to_date , a.applied_date , a.status FROM leave_ra_sa_handingover a , employee b ,employee c WHERE a.from_empid=b.empid AND a.to_empid=c.empid AND is_active='1' AND(( a.from_date BETWEEN  :fromdate AND  :todate ) OR( a.to_date BETWEEN  :fromdate AND  :todate )OR ( a.from_date > :fromdate AND a.to_date < :todate )) ORDER BY a.handingover_id DESC";
	@Override
	public List<Object[]> GethandlingOverList(LocalDate FromDate, LocalDate Todate)throws Exception
	{
		logger.info(new Date() +"Inside DAO GethandlingOverList()");
		try {
			
			Query query= manager.createNativeQuery(HANDLINGOVERLIST);
			query.setParameter("fromdate",FromDate );
			query.setParameter("todate", Todate);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String DOCTORLIST="SELECT a.docrateid , b.treatmentname , a.docqualification ,a.docrating ,a.consultation_1 ,a.consultation_2  FROM chss_doctor_rates a , chss_treattype b WHERE a.isactive='1' AND a.treattypeid = b.treattypeid";
	
	@Override
	public List<Object[]> GetDoctorList()throws Exception
	{
		logger.info(new Date() +"Inside DAO GetDoctorList()");
		try {
			
			Query query= manager.createNativeQuery(DOCTORLIST);			
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public CHSSDoctorRates getCHSSDocRate(long docrateid) throws Exception 
	{
		logger.info(new Date() + "Inside getCHSSDocRate()");
		CHSSDoctorRates memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<CHSSDoctorRates> cq = cb.createQuery(CHSSDoctorRates.class);
			Root<CHSSDoctorRates> root = cq.from(CHSSDoctorRates.class);
			Predicate p1 = cb.equal(root.get("DocRateId"), docrateid);
			cq = cq.select(root).where(p1);
			TypedQuery<CHSSDoctorRates> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	@Override
	public int EditDoctorMaster(CHSSDoctorRates Docrate) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditDoctorMaster()");
		try {
			manager.merge(Docrate);
			manager.flush();		
			return Docrate.getDocRateId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
	private static final String LABDETAILS="SELECT labmasterid, labcode ,labname ,labunitcode, labaddress, labcity, labpin FROM lab_master";
	@Override
	public Object[] getLabDetails()throws Exception
	{
		 logger.info(new Date() +"Inside getLabDetails()");	
		 try {
			 Query query = manager.createNativeQuery(LABDETAILS);
			 
			 List<Object[]> list = (List<Object[]>)query.getResultList();
			 
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}	
	}
	@Override
	public LabMaster GetLabDetailsToEdit(long labid)throws Exception
	{
		logger.info(new Date() + "Inside getCHSSDocRate()");
		LabMaster memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<LabMaster> cq = cb.createQuery(LabMaster.class);
			Root<LabMaster> root = cq.from(LabMaster.class);
			Predicate p1 = cb.equal(root.get("LabMasterId"), labid);
			cq = cq.select(root).where(p1);
			TypedQuery<LabMaster> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String LABSLIST="SELECT a.labid , a.labname ,a.labcode FROM cluster_lab a ,cluster b WHERE a.clusterid=b.clusterid";
	
	@Override
	public List<Object[]> getLabsList()throws Exception
	{
		logger.info(new Date() +"Inside DAO getLabsList()");
		try {
			
			Query query= manager.createNativeQuery(LABSLIST);			
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public long EditLabMaster(LabMaster labmatster) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditLabMaster()");
		try {
			manager.merge(labmatster);
			manager.flush();		
			return labmatster.getLabMasterId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	private static final String  CHECKHANDINGDATA="SELECT login_type, from_empid FROM leave_ra_sa_handingover  WHERE  to_empid=:toemp AND STATUS='A' AND (:fromDate BETWEEN from_date  AND to_date  OR :toDate BETWEEN from_date  AND to_date  OR from_date BETWEEN :fromDate AND :toDate) AND is_active='1'";
	@Override
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception
	{
		logger.info(new Date() +"Inside DAO checkAlreadyPresentForSameEmpidAndSameDates()");
		try {
			
			Query query= manager.createNativeQuery(CHECKHANDINGDATA);				
			//query.setParameter("fromemp", FromEmpid);
			query.setParameter("toemp", ToEmpid);
			query.setParameter("fromDate", DateTimeFormatUtil.dateConversionSql(FromDate));
			query.setParameter("toDate",DateTimeFormatUtil.dateConversionSql(ToDate) );
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public int AddHandingOver(LeaveHandingOver handinfover)throws Exception
	{
		logger.info(new Date() + "Inside AddHandingOver()");
		try {
			manager.persist(handinfover);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return handinfover.getHandingover_id();
	}
	
	private static final String REVOKEHANDINGOVER="UPDATE leave_ra_sa_handingover SET revokedate=:revokedate , revokeempid=:revokeempId , revokestatus=:revokestatus ,status=:status ,modifiedby=:modifiedby , modifieddate=:modifieddate WHERE handingover_id=:HandingOverId";
	@Override
	public int updateRevokeInHandingOver(long empid ,String UserId, String HandingOverId)throws Exception
	{
		logger.info(new Date() + "Inside updateRevokeInHandingOver()");
		int count=0;
		try {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Query query= manager.createNativeQuery(REVOKEHANDINGOVER);				
			query.setParameter("revokeempId", empid);
			query.setParameter("HandingOverId", HandingOverId);
			query.setParameter("revokestatus","Y");
			query.setParameter("status","R");
			query.setParameter("revokedate",sdf.format(d));
			query.setParameter("modifiedby", UserId);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			count = query.executeUpdate();
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
		
	}
	
	private static final String GETOTHEREMSLIST="SELECT a.basicfrom , a.basicto , a.itempermitamt , a.CHSSOtherAmtId FROM chss_other_perm_amt a , chss_other_items b WHERE a.otheritemid=b.otheritemid AND a.isactive='1' AND a.otheritemid=:id"; 
	@Override
	public List<Object[]> GetOtherItemAmlountList(String id)throws Exception
	{
		logger.info(new Date() +"Inside DAO GetOtherItemAmlountList()");
		try {
			
			Query query= manager.createNativeQuery(GETOTHEREMSLIST);			
			query.setParameter("id", id);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long AddOtherItemAmt(CHSSOtherPermitAmt otheramt)throws Exception
	{
		logger.info(new Date() + "Inside AddOtherItemAmt()");
		try {
			manager.persist(otheramt);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return otheramt.getCHSSOtherAmtId();
	}
	private static final String UPDATEOTHERITEMAMT="UPDATE chss_other_perm_amt SET itempermitamt=:admAmt  ,modifiedby=:modifiedby , modifieddate=:modifieddate WHERE CHSSOtherAmtId=:chssOtheramtid";
	@Override
	public long updateOtherAmt(String chssOtheramtid, String admAmt, String UserId)throws Exception
	{
		logger.info(new Date() + "Inside updateOtherAmt()");
		int count=0;
		try {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Query query= manager.createNativeQuery(UPDATEOTHERITEMAMT);				
			query.setParameter("chssOtheramtid", chssOtheramtid);
			query.setParameter("admAmt",admAmt);
			query.setParameter("modifiedby", UserId);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			count = query.executeUpdate();
			return count;
			} catch (Exception e) {
				e.printStackTrace();
				return count;
			}
	}
	
	private static final String UPDATEOTHERITEMAMTWITHBASICTOAMT="UPDATE chss_other_perm_amt SET itempermitamt=:admAmt  ,modifiedby=:modifiedby , modifieddate=:modifieddate , basicto=:basicto WHERE CHSSOtherAmtId=:chssOtheramtid";
	@Override
	public long updateOtherItemAmt(String chssOtheramtid, String admAmt, String UserId ,String basicto)throws Exception
	{
		logger.info(new Date() + "Inside updateOtherAmt()");
		int count=0;
		try {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Query query= manager.createNativeQuery(UPDATEOTHERITEMAMTWITHBASICTOAMT);				
			query.setParameter("chssOtheramtid", chssOtheramtid);
			query.setParameter("admAmt",admAmt);
			query.setParameter("basicto",basicto);
			query.setParameter("modifiedby", UserId);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			count = query.executeUpdate();
			return count;
			} catch (Exception e) {
				e.printStackTrace();
				return count;
			}
	}
	private static final String GETREQLIST="SELECT a.emprequestid , b.empname , a.requestmessage ,a.responsemessage ,a.empid FROM ems_emp_request a , employee b WHERE a.empid=b.empid AND a.isactive='1' ORDER BY a.emprequestid DESC";
	@Override
	public List<Object[]> GetReqListFromUser()throws Exception
	{

		logger.info(new Date() + "Inside GetReqListFromUser()");
		
		try {
			Query query= manager.createNativeQuery(GETREQLIST);			
		
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	
	private static final String UPDATEADMINREPLY="UPDATE ems_emp_request SET modifiedby=:modifiedby , modifieddate=:modifieddate , responsemessage=:responsemsg WHERE emprequestid=:emprequestid";
	@Override
	public int UpdateAdminResponse(String  responsemsg , String requestid, String UserId)throws Exception
	{
		logger.info(new Date() + "Inside UpdateAdminResponse()");
		int count=0;
		try {
			
			Query query= manager.createNativeQuery(UPDATEADMINREPLY);				
			query.setParameter("responsemsg", responsemsg);
			query.setParameter("emprequestid",requestid);
			query.setParameter("modifiedby", UserId);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			count = query.executeUpdate();
			return count;
			} catch (Exception e) {
				e.printStackTrace();
				return count;
			}
	}
	private static final String GETREQRESMESSAGELIST="SELECT a.emprequestid , b.empname , a.requestmessage  , a.responsemessage FROM ems_emp_request a , employee b WHERE a.empid=b.empid AND a.empid=:emp AND a.isactive='1' AND a.requestdate BETWEEN :FromDate AND :Todate  ORDER BY a.requestdate DESC";
	
	@Override
	public List<Object[]> GetReqResMessagelist(String emp ,LocalDate FromDate, LocalDate Todate)throws Exception
	{
		logger.info(new Date() + "Inside GetReqResMessagelist()");
		
		try {
			Query query= manager.createNativeQuery(GETREQRESMESSAGELIST);			
			query.setParameter("emp", emp);
			query.setParameter("FromDate",FromDate);
			query.setParameter("Todate", Todate);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
		   }
	}
	
	private static final String ALLNOTIFICATION="SELECT notificationdate,notificationmessage,notificationurl,notificationid FROM ems_notification WHERE empid=:empid AND isactive=1";
	public List<Object[]> AllNotificationLists(long empid)throws Exception
	{
		logger.info(new Date() + "Inside AllNotificationLists()");
		try {
			Query query= manager.createNativeQuery(ALLNOTIFICATION);			
			query.setParameter("empid", empid);
		
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
		   }
	}
	
	private static final String CHECKITEM="SELECT COUNT(otheritemid) FROM chss_other_items WHERE otheritemname=:treatmentname";
	
	@Override
	public int CheckduplicateItem(String treatmentname)throws Exception
	{
		 logger.info(new Date() +"Inside CheckduplicateItem()");	
		 try {
			Query query = manager.createNativeQuery(CHECKITEM);
			query.setParameter("treatmentname", treatmentname);		
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

				return result;
		  }catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	private static final String CHECKTEST = "SELECT COUNT(testname) FROM chss_test_sub WHERE testname=:testname";
	
	@Override
	public int CheckduplicateTest(String testname)throws Exception
	{
		 logger.info(new Date() +"Inside CheckduplicateTest()");	
		 try {
			Query query = manager.createNativeQuery(CHECKTEST);
			query.setParameter("testname", testname);		
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

			return result;
		  }catch (Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String DELETEITEM="UPDATE chss_other_perm_amt SET modifiedby=:modifiedby , modifieddate=:modifieddate , isactive=:isactive WHERE chssotheramtid=:chssOtheramtid";
	
	@Override
	public long DeleteOtherAmt(String chssOtheramtid, String userid)throws Exception
	{
		logger.info(new Date() + "Inside DeleteOtherAmt()");
		long count=0;
		try {
			
			Query query= manager.createNativeQuery(DELETEITEM);				
			query.setParameter("chssOtheramtid", chssOtheramtid);
			query.setParameter("isactive","0");
			query.setParameter("modifiedby", userid);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			
			count = query.executeUpdate();
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}
	
	private static final String DESIGNATION ="SELECT  desigid , desigcode , designation , desiglimit FROM employee_desig order by desigid desc";
	
	@Override
	public List<Object[]> GetDesignation()throws Exception
	{
		logger.info(new Date() + "Inside GetDesignation()");
		try {
				Query query= manager.createNativeQuery(DESIGNATION);			
				List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
		   }
	}
	
	
	@Override
	public long AddDesignation(EmployeeDesig desig)throws Exception
	{
		logger.info(new Date() + "Inside AddDesignation()");
		try {
			manager.persist(desig);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return desig.getDesigId();
	}
	
	@Override
	public EmployeeDesig GetDesignationToEdit(long desigid)throws Exception
	{
		logger.info(new Date() + "Inside GetDesignationToEdit()");
		EmployeeDesig memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmployeeDesig> cq = cb.createQuery(EmployeeDesig.class);
			Root<EmployeeDesig> root = cq.from(EmployeeDesig.class);
			Predicate p1 = cb.equal(root.get("DesigId"), desigid);
			cq = cq.select(root).where(p1);
			TypedQuery<EmployeeDesig> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
			return memeber;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long EditDesignation(EmployeeDesig desig) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditDesignation()");
		try {
			manager.merge(desig);
			manager.flush();		
			return desig.getDesigId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	private static final String DESIGNATIONCODECHECK="SELECT COUNT(desigcode),'desigcode' FROM employee_desig WHERE desigcode=:desigcode";
	@Override
	public Object[] DesignationCodeCheck(String desigcode)throws Exception
	{
		Query query =manager.createNativeQuery(DESIGNATIONCODECHECK);
		query.setParameter("desigcode", desigcode);
		return (Object[])query.getSingleResult();
	}
	
	private static final String DESIGNATIONCHECK="SELECT COUNT(designation),'designation' FROM employee_desig WHERE designation=:designation";
	@Override
	public Object[] DesignationCheck(String designation)throws Exception
	{
		Query query =manager.createNativeQuery(DESIGNATIONCHECK);
		query.setParameter("designation", designation);
		return (Object[])query.getSingleResult();
	}
	
	private static final String DESIGNATIONCODEEDITCHECK="SELECT COUNT(desigcode),'desigcode' FROM employee_desig WHERE desigcode=:desigcode AND desigid<>:desigid";
	@Override
	public Object[] DesignationCodeEditCheck(String desigcode,String desigid )throws Exception
	{
		Query query =manager.createNativeQuery(DESIGNATIONCODEEDITCHECK);
		query.setParameter("desigcode", desigcode);
		query.setParameter("desigid", desigid);
		return (Object[])query.getSingleResult();
	}
	
	private static final String DESIGNATIONEDITCHECK="SELECT COUNT(designation),'designation' FROM employee_desig WHERE designation=:designation AND desigid<>:desigid";
	@Override
	public Object[] DesignationEditCheck(String designation,String desigid)throws Exception
	{
		Query query =manager.createNativeQuery(DESIGNATIONEDITCHECK);
		query.setParameter("designation", designation);
		query.setParameter("desigid", desigid);
		return (Object[])query.getSingleResult();
	}
	private static final String FROMEMPLOYEE="SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT po FROM `chss_approve_auth` WHERE isactive='1') UNION  SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT vo FROM `chss_approve_auth` WHERE isactive='1') UNION SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT ao FROM `chss_approve_auth` WHERE isactive='1')";
	
	@Override
	public List<Object[]> GetFromemployee()throws Exception
	{
		Query query = manager.createNativeQuery(FROMEMPLOYEE);
		List<Object[]> list = (List<Object[]>)query.getResultList();	
		return list; 
	}
	
	private static final String TOEMPLOYEE="SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid NOT IN (SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT po FROM `chss_approve_auth` WHERE isactive='1') UNION  SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT vo FROM `chss_approve_auth` WHERE isactive='1') UNION SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT ao FROM `chss_approve_auth` WHERE isactive='1'))";
	@Override
	public List<Object[]> GetToemployee()throws Exception
	{
		Query query = manager.createNativeQuery(TOEMPLOYEE);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		
		return list; 
	}
	private static final String MAXMEDNO="SELECT MAX(medno) FROM chss_medicines_list WHERE treattypeid=:treattype";
	@Override
	public int GetMaxMedNo(String treatmenttype)throws Exception
	{
		Query query = manager.createNativeQuery(MAXMEDNO);
		query.setParameter("treattype", treatmenttype);
		Integer result = (Integer) query.getSingleResult();
		return result;
	}
	
	
	private static final String CHECKTESTCODE = "SELECT COUNT(testcode) FROM chss_test_sub WHERE testcode=:testcode";
	
	@Override
	public int CheckduplicateTestCode(String testcode)throws Exception
	{
		 logger.info(new Date() +"Inside CheckduplicateTestCode()");	
		 try {
			Query query = manager.createNativeQuery(CHECKTESTCODE);
			query.setParameter("testcode", testcode);		
			Object o = query.getSingleResult();
			Integer value = Integer.parseInt(o.toString());
			int result = value;

			return result;
		  }catch (Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public int checkavaibility(String logintype,String detailsid)throws Exception{
		Query query = manager.createNativeQuery("SELECT COUNT(formroleaccessid)  FROM `form_role_access` WHERE logintype=:logintype  AND  formdetailid=:detailsid");
		query.setParameter("logintype", logintype);
		query.setParameter("detailsid",detailsid );
		
		BigInteger result = (BigInteger) query.getSingleResult();
		return result.intValue();
	}
	
	@Override
	public int updateformroleaccess(String formroleid,String active,String auth)throws Exception{
		Query query = manager.createNativeQuery("UPDATE form_role_access SET isactive=:isactive , modifieddate=:modifieddate , modifiedby=:modifiedby WHERE formroleaccessid=:formroleaccessid");
		query.setParameter("formroleaccessid", formroleid);
		query.setParameter("isactive", active);
		query.setParameter("modifieddate",sdtf.format(new Date()));
		query.setParameter("modifiedby", auth);
		
		return query.executeUpdate();
	}
	
	@Override
	public Long insertformroleaccess(FormRoleAccess main) throws Exception {
		logger.info(new Date() + "Inside insertformroleaccess()");
		try {
			manager.persist(main);
			manager.flush();
			return (long)main.getFormRoleAccessId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	
	}
	
	@Override
	public long CircularListAdd(CircularList circular)throws Exception
	{
		logger.info(new Date() + "Inside CircularListAdd()");
		try {
			manager.persist(circular);
			manager.flush();
			return (long)circular.getCircularId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
		
	}
	
	@Override
	public CircularList GetCircularToEdit(Long  circularid)throws Exception
	{
		logger.info(new Date() + "Inside GetCircularToEdit()");
		CircularList list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<CircularList> cq = cb.createQuery(CircularList.class);
			Root<CircularList> root = cq.from(CircularList.class);
			Predicate p1 = cb.equal(root.get("CircularId"), circularid);
			cq = cq.select(root).where(p1);
			TypedQuery<CircularList> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long EditCircular(CircularList circular) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditCircular()");
		try {
			manager.merge(circular);
			manager.flush();		
			return circular.getCircularId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	@Override
	public long GetCircularMaxId()throws Exception
	{
		logger.info(new Date() +"Inside DAO GetCircularMaxId()");
		try {
			Query query = manager.createNativeQuery("SELECT MAX(circularid)  FROM chss_circular_list");	
			
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	private static final String CIRCULARLIST = "SELECT circularid , description , path , CircularDate  ,OriginalName,todate FROM chss_circular_list WHERE ( CircularDate BETWEEN  :fromdate AND :todate )  ORDER BY circularid DESC";
	
	@Override
	 public List<Object[]> GetCircularList(LocalDate fromdate , LocalDate todate) throws Exception
	 {
		 logger.info(new Date() +"Inside DAO CirculatList()");	
		 try {
				Query query =  manager.createNativeQuery(CIRCULARLIST);
				 query.setParameter("fromdate", fromdate);
				 query.setParameter("todate", todate);
				return (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() +" Inside DAO CirculatList "+ e);
			e.printStackTrace();
			return null;
		}
	
	 }
	 
		@Override
		public DoctorList GetDoctor(Long  doctorid)throws Exception
		{
			logger.info(new Date() + "Inside GetDoctor()");
			DoctorList list = null;
			try {
				CriteriaBuilder cb = manager.getCriteriaBuilder();
				CriteriaQuery<DoctorList> cq = cb.createQuery(DoctorList.class);
				Root<DoctorList> root = cq.from(DoctorList.class);
				Predicate p1 = cb.equal(root.get("DoctorId"), doctorid);
				cq = cq.select(root).where(p1);
				TypedQuery<DoctorList> allquery = manager.createQuery(cq);
				list = allquery.getResultList().get(0);
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		public long DoctorsAdd(DoctorList doctor)throws Exception
		{
			logger.info(new Date() + "Inside DoctorsAdd()");
			try {
				manager.persist(doctor);
				manager.flush();
				return (long)doctor.getDoctorId();
			} catch (Exception e) {
				e.printStackTrace();
				return 0l;
			}
		}
		
		@Override
		public long DoctorsEdit(DoctorList doctor)throws Exception
		{
			logger.info(new Date() + "Inside DoctorsEdit()");
			try {
				manager.merge(doctor);
				manager.flush();
				return (long)doctor.getDoctorId();
			} catch (Exception e) {
				e.printStackTrace();
				return 0l;
			}
		}
}
