package com.vts.ems.noc.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.noc.model.ExamIntimation;
import com.vts.ems.noc.model.ExamIntimationTrans;
import com.vts.ems.noc.model.NocHigherEducation;
import com.vts.ems.noc.model.NocHigherEducationTrans;
import com.vts.ems.noc.model.NocPassport;
import com.vts.ems.noc.model.NocPassportTrans;
import com.vts.ems.noc.model.NocProceedingAbroad;
import com.vts.ems.noc.model.NocProceedingAbroadTrans;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.Passport;
@Transactional
@Repository
public class NocDaoImpl implements NocDao {
	private static final Logger logger = LogManager.getLogger(NocDaoImpl.class);
	
	@PersistenceContext
	EntityManager manager;

	private static final String EMPDATA="SELECT e.EmpName,ed.Designation ,dm.DivisionName ,res.res_addr,per.per_addr,e.EmpNo,res.city AS res_city,res.state AS res_state,res.pin AS res_pin,per.city AS per_city,per.state AS per_state,per.pin AS per_pin FROM employee e, employee_desig ed,division_master dm,pis_address_res res,pis_address_per per WHERE ed.DesigId=e.DesigId  AND e.divisionid=dm.divisionid AND res.Empid=e.Empid AND per.Empid=e.Empid AND res.ResAdStatus='A' AND per.PerAdStatus='A' AND  e.EmpId=:EmpId";
	@Override
	public Object[] getNocEmpList(String EmpId) throws Exception {
		
		
		Query query=manager.createNativeQuery(EMPDATA);
		query.setParameter("EmpId", EmpId);
		List<Object[]> list =(List<Object[]>) query.getResultList();
		
		Object[] result=null;
		if(list!=null &&list.size()>0) {
			result =list.get(0);
		}
		return result;
		
	}
	
	private static final String PASSPORTDATA="SELECT PassportType,PassportNo,ValidFrom,ValidTo FROM pis_passport WHERE EmpId=:EmpId";
	@Override
	public Object[] getEmpPassportData(String empId) throws Exception {
		

		Query query=manager.createNativeQuery(PASSPORTDATA);
		query.setParameter("EmpId", empId);
		
		List<Object[]> list =(List<Object[]>) query.getResultList();
		Object[] result=null;
		if(list!=null &&list.size()>0) {
			result =list.get(0);
		}
		return result;
		/* return (Object[])query.getSingleResult(); */
	}
	
	@Override
	public long NocPassportAdd(NocPassport noc) throws Exception {
		

		try {
			manager.persist(noc);
			manager.flush();		
			return noc.getNocPassportId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO NocPassportAdd() "+e);
			e.printStackTrace();
			return 0L;
		}
		
		
	}

	private static final String MAXOFNOCPASSPORTID="SELECT IFNULL(MAX(NocPassportId),0) as 'MAX' FROM noc_passport";
	@Override
	public long MaxOfNocPassportId() throws Exception {
		
		try {
			Query query =  manager.createNativeQuery(MAXOFNOCPASSPORTID);
			BigInteger PassportId=(BigInteger)query.getSingleResult();
			return PassportId.longValue();
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside DAO MaxOfNocPassportId "+ e);
			return 0;
		}
	}

	private static final String NOCPASSPORTLIST="SELECT n.NocPassportId,n.NocPassportNo,n.PassportStatus,n.Remarks,e.EmpName,c.PISStatus,c.PisStatusColor,c.pisstatuscode,n.InitiatedDate FROM noc_passport n,pis_approval_status c,employee e  WHERE n.isActive='1' AND n.NocstatusCode=c.PisStatuscode AND e.EmpNo=n.EmpNo AND n.EmpNo=:EmpNo ORDER BY n.NocPassportId DESC";
	@Override
	public List<Object[]> getnocPassportList(String empNo) throws Exception {
		
		Query query=manager.createNativeQuery(NOCPASSPORTLIST);
		query.setParameter("EmpNo", empNo);
		return (List<Object[]>)query.getResultList();
	}

	@Override
	public NocPassport getNocPassportId(long nocPassportId) throws Exception {
		
		try {
			NocPassport noc = manager.find(NocPassport.class,(nocPassportId));
			return noc ;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getNocPassportId() "+e);
			e.printStackTrace();
			return null;
		}	
		
	}

	@Override
	public long NOCPassportUpdate(NocPassport noc) throws Exception {
		
		manager.merge(noc);
		manager.flush();
		return noc.getNocPassportId();
	
   }
	private static final String NOCPASSORTFORMDETAILS="CALL Noc_Passport(:NocPassportId)";
		
	@Override
	public Object[] getPassportFormDetails(String passportid) throws Exception {
		
		Query query=manager.createNativeQuery(NOCPASSORTFORMDETAILS);
		query.setParameter("NocPassportId", passportid);
		
		List<Object[]> list =(List<Object[]>) query.getResultList();
		Object[] result=null;
		if(list!=null &&list.size()>0) {
			result =list.get(0);
		}
		return result;
	}

	@Override
	public long NocPassportTransactionAdd(NocPassportTrans transaction) throws Exception {
	
		
			manager.persist(transaction);
			manager.flush();
			return transaction.getNocPassportTransId();
		
	}

	private static final String NOCPASSPORTTRANSACTIONLIST="SELECT tra.NocPassportTransId,e.empno,e.empname,des.designation,tra.ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor FROM noc_passport_trans tra,noc_passport noc,employee e,employee_desig des,pis_approval_status sta WHERE tra.NocPassportId=noc.NocPassportId  AND tra.ActionBy=e.EmpNo AND e.desigid=des.desigid AND tra.NocStatusCode=sta.PisStatusCode AND noc.NocPassportId=:passportid ORDER BY actiondate";
	@Override
	public List<Object[]> NOCPassportTransactionList(String passportid) throws Exception {
		
		Query query = manager.createNativeQuery(NOCPASSPORTTRANSACTIONLIST);
		query.setParameter("passportid",passportid);
		return (List<Object[]>) query.getResultList();
	}

	private static final String GETCEOEMPNO  ="SELECT empno FROM lab_master, employee WHERE labauthorityid=empid";
	@Override
	public String GetCEOEmpNo() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETCEOEMPNO);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetCEOEmpNo " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String GETPANDAADMINEMPNOS="SELECT a.EmpNo FROM employee a, pis_admins b WHERE a.EmpNo=b.EmpAdmin AND b.IsActive=1 AND b.AdminType='P'";
	@Override
	public List<String> GetPandAAdminEmpNos()throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETPANDAADMINEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
			return list;
		}
		catch (Exception e) 
		{
			logger.error(new Date()  + "Inside DAO GetPandAAdminEmpNos " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}
	
	private static final String GETDGMEMPNOS  ="SELECT dgmempno FROM dgm_master WHERE isactive=1";
	@Override
	public List<String> GetDGMEmpNos() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETDGMEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
			return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetDGMEmpNo " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}
		
	}
	
	
	private static final String GETDHEMPNOS  ="SELECT divisionheadid FROM division_master WHERE isactive=1";
	@Override
	public List<String> GetDHEmpNos() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETDHEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
				return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetDHEmpNo " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}
		
	}
	
	private static final String GETGHEMPNOS  ="SELECT Groupheadid FROM division_Group WHERE isactive=1;";
	@Override
	public List<String> GetGHEmpNos() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETGHEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
			return list;			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetGHEmpNo " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}		
	}
	
	private static final String CEOEMPNO  ="SELECT empno,empname FROM lab_master, employee WHERE labauthorityid=empid";
	@Override
	public Object[] GetCeoName() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(CEOEMPNO);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetCeoName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String GETPANDAEMPNAME  ="SELECT DISTINCT a.EmpNo,a.EmpName FROM employee a, pis_admins b WHERE a.EmpNo=b.EmpAdmin AND b.IsActive=1 AND b.AdminType='P' LIMIT 1";
	@Override
	public Object[] GetPandAEmpName() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETPANDAEMPNAME);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetPandAEmpName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	
	
	private static final String GETEMPDGMPANDAEMPNO  ="SELECT dgm.dgmempno , e2.empname FROM employee e, division_master dm,dgm_master dgm , employee e2 WHERE e.divisionid=dm.divisionid AND dm.dgmid=dgm.dgmid AND dgm.dgmempno=e2.empno AND e.empno=:empno";
	@Override
	public Object[] GetEmpDGMEmpName(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPDGMPANDAEMPNO);
			query.setParameter("empno", empno);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpDGMEmpName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String DIVISIONHEADEMPNO  ="SELECT emp.EmpNo,emp.EmpName FROM employee emp WHERE emp.EmpNo=(SELECT d.DivisionHeadId FROM division_master d WHERE d.DivisionId = (SELECT a.DivisionId FROM employee a WHERE a.EmpNo=:EmpNo))";
	@Override
	public Object[] GetDivisionHeadName(String EmpNo) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(DIVISIONHEADEMPNO);
			query.setParameter("EmpNo", EmpNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetDivisionHeadName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String GROUPHEADEMPNO  ="SELECT emp.EmpNo,emp.EmpName FROM employee emp WHERE emp.EmpNo=(SELECT b.GroupHeadId FROM division_group b WHERE b.GroupId = (SELECT a.GroupId FROM employee a WHERE  a.EmpNo=:EmpNo) )";
	@Override
	public Object[] GetGroupHeadName(String EmpNo) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GROUPHEADEMPNO);
			query.setParameter("EmpNo", EmpNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetGroupHeadName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String GETEMPDATA="FROM Employee WHERE EmpId=:EmpId";
	@Override
	public Employee getEmpData(String EmpId)throws Exception
	{
		Employee emp=null;
		try {
			Query query = manager.createQuery(GETEMPDATA);
			query.setParameter("EmpId", Long.parseLong(EmpId));
			emp = (Employee) query.getSingleResult();
			return emp;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmpData "+e);
			e.printStackTrace();
			return null;
		}				
	}
	
	private static final String GETEMPDATABYEMPNO="FROM Employee WHERE EmpNo=:EmpNo";
	@Override
	public Employee getEmpDataByEmpNo(String EmpNo) throws Exception {
		
		Employee emp=null;
		try {
			Query query = manager.createQuery(GETEMPDATABYEMPNO);
			query.setParameter("EmpNo", EmpNo);
			emp = (Employee) query.getSingleResult();
			return emp;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmpDataByEmpNo "+e);
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public long AddNotifications(EMSNotification notification) throws Exception
	{
		manager.persist(notification);
		manager.flush();
		return notification.getNotificationId();
	}
	

	private static final String GETEMPDGMEMPNO  ="SELECT dgm.dgmempno FROM employee e, division_master dm,dgm_master dgm WHERE e.divisionid=dm.divisionid AND dm.dgmid=dgm.dgmid AND e.empno=:empno";
	@Override
	public String GetEmpDGMEmpNo(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPDGMEMPNO);
			query.setParameter("empno", empno);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpDHEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String GETEMPGHEMPNO  ="SELECT dg.groupheadid FROM employee e, division_group dg WHERE e.groupid=dg.groupid AND e.empno=:empno";
	@Override
	public String GetEmpGHEmpNo(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPGHEMPNO);
			query.setParameter("empno", empno);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpGHEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	
	private static final String GETEMPDHEMPNO  ="SELECT dm.divisionheadid FROM employee e, division_master dm WHERE e.divisionid=dm.divisionid AND e.empno=:empno";
	@Override
	public String GetEmpDHEmpNo(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPDHEMPNO);
			query.setParameter("empno", empno);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpDHEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	@Override
	public Long EditNoc(NocPassport noc) throws Exception {
	
		try {
			manager.merge(noc);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditNoc "+e);
			e.printStackTrace();
		}
		return noc.getNocPassportId();
	}
	
	@Override
	public DivisionMaster GetDivisionData(long DivisionId) throws Exception
	{
		return manager.find(DivisionMaster.class, DivisionId);
	}
	
	@Override
	public long NOCPassportForward(NocPassport noc) throws Exception {
		
		return 0;
	}

	public final static String NOCAPPROVALSLIST="CALL Noc_Approval(:EmpNo)";
	@Override
	public List<Object[]> NocApprovalsList(String empNo) throws Exception {
		
		try {			
			Query query= manager.createNativeQuery(NOCAPPROVALSLIST);
			query.setParameter("EmpNo", empNo);
			
			List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO IntimationApprovalsList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}

	@Override
	public long PandAFromUpdate(NocPassport noc) throws Exception {
		
		manager.merge(noc);
		manager.flush();
		return noc.getNocPassportId();
	}

	public static final String MAXOFPROCABROADID="SELECT IFNULL(MAX(NocProcId),0) as 'MAX' FROM noc_proceeding_abroad";
	@Override
	public long getMaxOfProcAbroadId() throws Exception {
		
		try {
			Query query =  manager.createNativeQuery(MAXOFPROCABROADID);
			BigInteger ProcAbroadId=(BigInteger)query.getSingleResult();
			return ProcAbroadId.longValue();
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside DAO getMaxOfProcAbroadId "+ e);
			return 0;
		}
	}

	@Override
	public long NocProcAbroadAdd(NocProceedingAbroad nocpa) throws Exception {
		
		 manager.persist(nocpa);
			manager.flush();
			
			return nocpa.getNocProcId();
	}

	@Override
	public long NocProcAbroadTransactionAdd(NocProceedingAbroadTrans trans) throws Exception {
		

		manager.persist(trans);
		manager.flush();
		return trans.getNocProcAbroadTransId();
		
		
	}

	public static final String NOCPROCABROADTLIST="SELECT pa.NocProcId,pa.NocProcAbroadNo,pa.ProcAbroadStatus,pa.Remarks,e.EmpName,c.PISStatus,c.PisStatusColor,c.pisstatuscode,pa.InitiatedDate FROM noc_proceeding_abroad pa,pis_approval_status c,employee e WHERE pa.isActive='1' AND pa.NocstatusCode=c.PisStatuscode AND e.EmpNo=pa.EmpNo AND pa.EmpNo=:EmpNo ORDER BY pa.NocProcId DESC";
	@Override
	public List<Object[]> getProcAbroadList(String empNo) throws Exception {
		
		Query query=manager.createNativeQuery(NOCPROCABROADTLIST);
		query.setParameter("EmpNo", empNo);
		return (List<Object[]>)query.getResultList();
	}

	public static final String NOCPROCABROADTRANSACTION="SELECT tra.NocProcAbroadTransId,e.empno,e.empname,des.designation,tra.ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor FROM noc_proc_abroad_trans tra,noc_proceeding_abroad noc,employee e,employee_desig des,pis_approval_status sta WHERE tra.NocProcId=noc.NocProcId  AND tra.ActionBy=e.EmpNo AND e.desigid=des.desigid AND tra.NocStatusCode=sta.PisStatusCode AND noc.NocProcId=:procAbrId ORDER BY actiondate";
	@Override
	public List<Object[]> NOCProcAbroadTransactionList(String procAbrId) throws Exception {
		
		Query query=manager.createNativeQuery(NOCPROCABROADTRANSACTION);
		query.setParameter("procAbrId", procAbrId);
		return (List<Object[]>)query.getResultList();
	}

	@Override
	public NocProceedingAbroad getNocProceedingAbroadById(long procAbrId) throws Exception {
		
		try {
			NocProceedingAbroad noc = manager.find(NocProceedingAbroad.class,(procAbrId));
			return noc ;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getNocProceedingAbroadById() "+e);
			e.printStackTrace();
			return null;
		}	
		
	}

	@Override
	public long NocProcAbroadUpdate(NocProceedingAbroad nocpa) throws Exception {
		
		manager.merge(nocpa);
		manager.flush();
		return nocpa.getNocProcId();
	}

	public static final String NOCPROCEEDINGABROADDETAILS="CALL Noc_Proc_Abroad(:NocProcId)";
	@Override
	public Object[] getNocProcAbroadDetails(String procAbrId) throws Exception {
		

		
		try {			
			Query query= manager.createNativeQuery(NOCPROCEEDINGABROADDETAILS);
			query.setParameter("NocProcId", procAbrId);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getNocProcAbroadDetails " + e);
			e.printStackTrace();
			return null;
		}		
		
	}
	
	@Override
	public Long EditNocpa(NocProceedingAbroad noc) throws Exception {

		try {
			manager.merge(noc);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditNocpa "+e);
			e.printStackTrace();
		}
		return noc.getNocProcId();
	}

	@Override
	public long AddPassport(Passport pport) throws Exception {
		
		try {
			manager.persist(pport);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddPassport "+e);
			e.printStackTrace();
		}
		return pport.getPassportId();
	}

	public static final String LABMASTERDETAILS="FROM LabMaster";
	@Override
	public List<LabMaster> getLabMasterDetails() throws Exception {
		
		
		Query query=manager.createQuery(LABMASTERDETAILS);
		List<LabMaster> List=(List<LabMaster>)query.getResultList();
		return List;
		
		
	
	}

	private static final String GETCOUNT="SELECT  COUNT(*) FROM pis_passport WHERE empid=:empid";
	@Override
	public long GetPassportCount(String empid) throws Exception {
		
		try {
			Query query =  manager.createNativeQuery(GETCOUNT);
			query.setParameter("empid", empid);
			BigInteger PassportId=(BigInteger)query.getSingleResult();
			return PassportId.longValue();
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside DAO GetPassportCount "+ e);
			return 0;
		}
	}

	private static final String NOCPASSPORTREMARKHISTORY="SELECT trans.NocPassportId,trans.Remarks,e.EmpName FROM noc_passport_trans trans,employee e WHERE trans.ActionBy=e.EmpNo AND trans.NocPassportId=:passportid ORDER BY trans.ActionDate ASC";
	@Override
	public List<Object[]> getPassportRemarksHistory(String passportid) throws Exception {
		
		Query query=manager.createNativeQuery(NOCPASSPORTREMARKHISTORY);
		query.setParameter("passportid", passportid);
		return (List<Object[]>)query.getResultList();
	}
	private static final String NOCAPPROVEDLIST="CALL Noc_Approved_List(:EmpNo,:FromDate,:ToDate)";
	@Override
	public List<Object[]> getNocApprovedList(String empNo, String fromdate, String todate) throws Exception {
		

		Query query=manager.createNativeQuery(NOCAPPROVEDLIST);
		query.setParameter("EmpNo", empNo);
		query.setParameter("FromDate", fromdate);
		query.setParameter("ToDate", todate);
		return (List<Object[]>)query.getResultList();
	}
	
	private static final String NAMEANDDESIG = "SELECT DISTINCT a.EmpNo,a.EmpName,b.Designation,d.PayLevel,c.BasicPay,c.Title,c.PhoneNo,c.AltPhoneNo,a.ExtNo,a.Email FROM employee a,employee_desig b,employee_details c,pis_pay_level d WHERE a.DesigId=b.DesigId AND a.IsActive=1 AND a.EmpNo=c.EmpNo AND d.PayLevelId=c.PayLevelId AND a.EmpNo=:EmpNo LIMIT 1";
	@Override
	public Object[] getEmpNameDesig(String EmpNo) throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(NAMEANDDESIG);
			query.setParameter("EmpNo", EmpNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getEmpNameDesig " + e);
			e.printStackTrace();
			return null;
		}		
	}

	

	private static final String NOCPROCEEDINGABROADREMARKHISTORY="SELECT trans.NocProcId,trans.Remarks,e.EmpName,trans.ActionDate,trans.NocStatusCode FROM noc_proc_abroad_trans trans,employee e WHERE trans.ActionBy=e.EmpNo AND trans.NocProcId=:procAbrId ORDER BY trans.ActionDate ASC";
	@Override
	public List<Object[]> getProceedinAbraodRemarksHistory(String procAbrId) throws Exception {
		
		Query query=manager.createNativeQuery(NOCPROCEEDINGABROADREMARKHISTORY);
		query.setParameter("procAbrId", procAbrId);
		return (List<Object[]>)query.getResultList();
	}

	@Override
	public long DeptDetailsUpdate(NocProceedingAbroad nocpa) throws Exception {
		
		
		manager.merge(nocpa);
		manager.flush();
		return nocpa.getNocProcId();
		
	}

	@Override
	public long ProcAbroadPandAFromUpdate(NocProceedingAbroad nocpa) throws Exception {
		
		
		manager.merge(nocpa);
		manager.flush();
		return nocpa.getNocProcId();
	}

	
	private static final String EMPGENDER="SELECT empd.Title,empd.Gender FROM employee_details empd,noc_proceeding_abroad n WHERE n.EmpNo = empd.EmpNo AND n.isActive='1' AND  n.NocProcId=:procAbrId";
	@Override
	public Object[] getEmpGender(String procAbrId) throws Exception {
		
		 try {
				
				Query query= manager.createNativeQuery(EMPGENDER);
				query.setParameter("procAbrId", procAbrId);
				List<Object[]> list =  (List<Object[]>)query.getResultList();
				if(list.size()>0) {
					return list.get(0);
				}else {
					return null;
				}
				
			} catch (Exception e) {
				logger.error(new Date()  + "Inside DAO getEmpGender " + e);
				e.printStackTrace();
				return null;
			}		
		}

	@Override
	public long ExamDetailsAdd(ExamIntimation exam) throws Exception {
		
		manager.persist(exam);
		manager.flush();
		return exam.getExamId();
		
	}

	private static final String EXAMINTIMATIONLIST="SELECT a.ExamId,a.IntimationExamNo,DATE(InitiatedDate),c.PISStatus,c.PisStatusColor,c.pisstatuscode,a.IntimationStatus,a.IntimateStatusCode FROM intimation_exam a,pis_approval_status c WHERE a.isActive='1' AND a.IntimateStatusCode=c.PisStatuscode AND a.EmpNo=:empNo ORDER BY a.ExamId DESC";
	@Override
	public List<Object[]> getExamIntimationDetails(String empNo) throws Exception {
		
		Query query=manager.createNativeQuery(EXAMINTIMATIONLIST);
		query.setParameter("empNo", empNo);
		return (List<Object[]>)query.getResultList();
	}

	@Override
	public long ExamIntimationTransAdd(ExamIntimationTrans transaction) throws Exception {
		
		manager.persist(transaction);
		manager.flush();
		return transaction.getIntimationTransId();
	}

	@Override
	public ExamIntimation getExamId(long examId) throws Exception {
		
			
			try {
				ExamIntimation exam = manager.find(ExamIntimation.class,(examId));
				return exam ;
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO getExamId() "+e);
				e.printStackTrace();
				return null;
			}	
			
		}

	@Override
	public long ExamDetailsUpdate(ExamIntimation exam) throws Exception {
		
		manager.merge(exam);
		manager.flush();
		return exam.getExamId();
		
	}	
	
private static final String INTIMATIONEXAMTRANSCATION="SELECT tra.IntimationTransId,e.empno,e.empname,des.designation,tra.ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor FROM intimation_exam_trans tra,intimation_exam ie,employee e,employee_desig des,pis_approval_status sta WHERE tra.ExamId=ie.ExamId  AND tra.ActionBy=e.EmpNo AND e.desigid=des.desigid  AND tra.IntimateStatusCode=sta.PisStatusCode AND ie.ExamId=:examId ORDER BY actiondate";
	@Override
	public List<Object[]> getExamIntimationTransactionList(String examId) throws Exception {
	
		
		Query query=manager.createNativeQuery(INTIMATIONEXAMTRANSCATION);
		query.setParameter("examId", examId);
		return (List<Object[]>)query.getResultList();
	}

	private static final String EXAMINTIMATIONDETAILS="CALL Intimation_Exam(:examId)";
	@Override
	public Object[] getIntimationData(String examId) throws Exception {
		
		
		try {
			
			Query query= manager.createNativeQuery(EXAMINTIMATIONDETAILS);
			query.setParameter("examId", examId);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getIntimationData " + e);
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public ExamIntimation IntimatedExam(String examId) throws Exception {
		
		ExamIntimation intimate= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<ExamIntimation> cq= cb.createQuery(ExamIntimation.class);
			Root<ExamIntimation> root= cq.from(ExamIntimation.class);					
			Predicate p1=cb.equal(root.get("ExamId") , Long.parseLong(examId));
			cq=cq.select(root).where(p1);
			TypedQuery<ExamIntimation> allquery = manager.createQuery(cq);
			intimate= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside DAO IntimatedExam " + e);
		}
		return intimate;
	}

	@Override
	public long IntimationDataEdit(ExamIntimation exam) throws Exception {
		
		try {
			manager.merge(exam);
			manager.flush();
			
			return exam.getExamId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO IntimationDataEdit " + e);
			e.printStackTrace();
			return 0;
		}
	}

	private static final String INTIMATIONREMARK="SELECT trans.ExamId,trans.Remarks,e.EmpName,trans.ActionDate,trans.IntimateStatusCode FROM intimation_exam_trans trans,employee e WHERE trans.ActionBy=e.EmpNo AND trans.ExamId=:examId  ORDER BY trans.ActionDate ASC";
	@Override
	public List<Object[]> getIntimationRemarks(String examId) throws Exception {
	
		Query query=manager.createNativeQuery(INTIMATIONREMARK);
		query.setParameter("examId", examId);
		return (List<Object[]>)query.getResultList();
	}

	@Override
	public long HigherEducationAdd(NocHigherEducation edu) throws Exception {
		
		manager.persist(edu);
		manager.flush();
		return edu.getNocEducationId();
	}

	public static final String MAXOFNOCHIGHEREDUCATIONID="SELECT IFNULL(MAX(NocEducationId),0) as 'MAX' FROM noc_higher_education";
	@Override
	public long getMaxOfNocEducId() throws Exception {
		
		try {
				Query query =  manager.createNativeQuery(MAXOFNOCHIGHEREDUCATIONID);
				BigInteger NocHigherEducId=(BigInteger)query.getSingleResult();
				return NocHigherEducId.longValue();
			}catch ( NoResultException e ) {
				logger.error(new Date() +"Inside DAO getMaxOfNocEducId "+ e);
				return 0;
			}
		}

	@Override
	public long NocHigherEducTransactionAdd(NocHigherEducationTrans transaction) throws Exception {
		
		manager.persist(transaction);
		manager.flush();
		return transaction.getEducationTransId();
	}

	public static final String NOCHIGHEREDUCATIONLIST="SELECT a.NocEducationId,a.NocEducationNo,DATE(InitiatedDate),a.NocStatusCode,a.HigherEducationStatus,c.PISStatus,c.PisStatusColor,c.pisstatuscode FROM noc_higher_education a,pis_approval_status c  WHERE a.isActive='1' AND a.NocStatusCode=c.PisStatuscode AND a.EmpNo=:empNo ORDER BY a.NocEducationId DESC";
	@Override
	public List<Object[]> getNOCHigherEducationList(String empNo) throws Exception {
	
		Query query=manager.createNativeQuery(NOCHIGHEREDUCATIONLIST);
		query.setParameter("empNo", empNo);
		return (List<Object[]>)query.getResultList();
	}

	public static final String NOCHIGHEREDUCATIONTRANSCATIONLIST="SELECT tra.EducationTransId,e.empno,e.empname,des.designation,tra.ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor FROM noc_higher_education_trans tra,noc_higher_education n,employee e,employee_desig des,pis_approval_status sta WHERE tra.NocEducationId=n.NocEducationId  AND tra.ActionBy=e.EmpNo AND e.desigid=des.desigid  AND tra.NocStatusCode=sta.PisStatusCode AND n.NocEducationId=:EducId  ORDER BY actiondate";
	@Override
	public List<Object[]> getHigherEducationTransactionList(String nOCHigherEducId) throws Exception {
		
		
		Query query=manager.createNativeQuery(NOCHIGHEREDUCATIONTRANSCATIONLIST);
		query.setParameter("EducId", nOCHigherEducId);
		return (List<Object[]>)query.getResultList();
	}

	@Override
	public NocHigherEducation getNocHigherEducationById(long NOCHigherEducId) throws Exception {
		
		try {
			NocHigherEducation heduc = manager.find(NocHigherEducation.class,(NOCHigherEducId));
			return heduc ;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getNocHigherEducationById() "+e);
			e.printStackTrace();
			return null;
		}	
		
	}

	@Override
	public long HigherEducationUpdate(NocHigherEducation edu) throws Exception {
		
		manager.merge(edu);
		manager.flush();
		return edu.getNocEducationId();
		
	}

	public static final String NOCHIGHEREDUCATION="CALL Noc_Higher_Education(:EducationId)";
	@Override
	public Object[] getHigherEducationDetails(String NOCHigherEducId) throws Exception {
		
	try {
			Query query= manager.createNativeQuery(NOCHIGHEREDUCATION);
			query.setParameter("EducationId", NOCHigherEducId);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getHigherEducationDetails " + e);
			e.printStackTrace();
			return null;
		}		
	  }

	private static final String NOCHIGHEREDUCATIONREMARK="SELECT trans.NocEducationId,trans.Remarks,e.EmpName,trans.ActionDate,trans.NocStatusCode FROM  noc_higher_education_trans trans,employee e WHERE trans.ActionBy=e.EmpNo AND trans.NocEducationId=:NOCHigherEducId  ORDER BY trans.ActionDate ASC ";
	@Override
	public List<Object[]> getNocHigherEducationRemarks(String NOCHigherEducId) throws Exception {
		
		Query query=manager.createNativeQuery(NOCHIGHEREDUCATIONREMARK);
		query.setParameter("NOCHigherEducId", NOCHigherEducId);
		return (List<Object[]>)query.getResultList();
	}

	@Override
	public NocHigherEducation HigherEducation(String nOCHigherEducId) throws Exception {
		
		NocHigherEducation noc= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<NocHigherEducation> cq= cb.createQuery(NocHigherEducation.class);
			Root<NocHigherEducation> root= cq.from(NocHigherEducation.class);					
			Predicate p1=cb.equal(root.get("NocEducationId") , Long.parseLong(nOCHigherEducId));
			cq=cq.select(root).where(p1);
			TypedQuery<NocHigherEducation> allquery = manager.createQuery(cq);
			noc= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside DAO IntimatedExam " + e);
		}
		return noc;
	}

	@Override
	public Long EditNocHe(NocHigherEducation noc) throws Exception {
		
		try {
			manager.merge(noc);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditNocHe "+e);
			e.printStackTrace();
		}
		return noc.getNocEducationId();
	}
		
	

	@Override
	public Long NocHigherEducationTransAdd(NocHigherEducationTrans transaction) throws Exception {
		
		
		manager.persist(transaction);
		manager.flush();
		return transaction.getNocEducationId();
	}
	
	private static final String GETSOEMPNOS="SELECT a.EmpAdmin FROM pis_admins a WHERE a.AdminType IN ('S') AND a.IsActive=1";
	@Override
	public List<String> GetSOEmpNos()throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETSOEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
			return list;
		}
		catch (Exception e) 
		{
			logger.error(new Date()  + "Inside DAO GetSOEmpNos " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	private static final String NOCHIGHEREDUCATIONAPPROVALDATA=" SELECT tra.NocEducationId,\r\n"
			+ "	(SELECT empno FROM noc_higher_education_trans t , employee e  WHERE e.empno = t.actionby AND t.NocStatusCode =  sta.pisstatuscode ORDER BY t.EducationTransId DESC LIMIT 1) AS 'empno',\r\n"
			+ "(SELECT empname FROM noc_higher_education_trans t , employee e  WHERE e.empno = t.actionby AND t.NocStatusCode =  sta.pisstatuscode ORDER BY t.EducationTransId DESC LIMIT 1) AS 'empname',\r\n"
			+ "(SELECT designation FROM noc_higher_education_trans t , employee e,employee_desig des WHERE e.empno = t.actionby AND e.desigid=des.desigid AND t.NocStatusCode =  sta.pisstatuscode ORDER BY t.EducationTransId DESC LIMIT 1) AS 'Designation',\r\n"
			+ "MAX(tra.actiondate) AS actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor ,sta.pisstatuscode \r\n"
			+ "FROM  noc_higher_education_trans tra, pis_approval_status sta,employee emp,noc_higher_education par \r\n"
			+ "WHERE par.NocEducationId = tra.NocEducationId AND tra.NocStatusCode = sta.pisstatuscode AND \r\n"
			+ "tra.actionby=emp.empno AND sta.pisstatuscode IN ('VGI','VDI','VDG','VSO','VPA','APR','DPR') AND par.NocEducationId =:NOCHigherEducId \r\n"
			+ "GROUP BY sta.pisstatuscode ORDER BY actiondate ASC";
	@Override
	public List<Object[]> getHigherEducationApprovalData(String nOCHigherEducId) throws Exception {
		

		Query query=manager.createNativeQuery(NOCHIGHEREDUCATIONAPPROVALDATA);
		query.setParameter("NOCHigherEducId", nOCHigherEducId);
		return (List<Object[]>)query.getResultList();
	}

	private static final String NOCAPPROVALFLOW="CALL Noc_ApprovalFlow(:EmpNo)";
	@Override
	public Object[] getNocApprovalFlow(String empNo) throws Exception {
	
		try {
			Query query= manager.createNativeQuery(NOCAPPROVALFLOW);
			query.setParameter("EmpNo", empNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getNocApprovalFlow " + e);
			e.printStackTrace();
			return null;
		}		
	  }

	private static final String PANDANAME= "SELECT e.EmpName,dg.Designation FROM employee e,noc_higher_education_trans trans,employee_desig dg WHERE  e.desigid=dg.desigid AND e.EmpNo=trans.ActionBy AND trans.NocStatusCode IN('VPA','FWD') AND trans.NoceducationId=:HigherEducId LIMIT 1";
	@Override
	public Object[] getPandAName(String NOCHigherEducId) throws Exception {
		
		try {
			Query query= manager.createNativeQuery(PANDANAME);
			query.setParameter("HigherEducId", NOCHigherEducId);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getPandAName " + e);
			e.printStackTrace();
			return null;
		}		
	}

	public static final String EMPQUALIFICATION="(SELECT  q.quali_id,q.quali_title FROM pis_quali_code q,pis_qualification pis,employee a WHERE \r\n"
			+ "\r\n"
			+ "	pis.quali_id=q.quali_id AND a.EmpId=pis.EmpId AND a.EmpNo=:empNo\r\n"
			+ "	ORDER BY qualification_id DESC LIMIT 1) ";
	@Override
	public Object[] getEmpQualification(String empNo) throws Exception {
		
		try {
			Query query= manager.createNativeQuery(EMPQUALIFICATION);
			query.setParameter("empNo", empNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getEmpQualification " + e);
			e.printStackTrace();
			return null;
		}		
	}

	public static final String MAXOFINTIMATIONID="SELECT IFNULL(MAX(ExamId),0) as 'MAX' FROM intimation_exam";
	@Override
	public long getmaxIntimationId() throws Exception {
		
		try {
			Query query =  manager.createNativeQuery(MAXOFINTIMATIONID);
			BigInteger IntimationId=(BigInteger)query.getSingleResult();
			return IntimationId.longValue();
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside DAO getmaxIntimationId "+ e);
			return 0;
		}
	}

	@Override
	public ExamIntimation getIntimationById(long examId) throws Exception {
		

		try {
			ExamIntimation exam = manager.find(ExamIntimation.class,(examId));
			return exam ;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getIntimationById() "+e);
			e.printStackTrace();
			return null;
		}	
		
	   }

	
	public static final String INTIMATIONAPPROVALDATA="SELECT tra.IntimationTransId,(SELECT empno FROM  intimation_exam_trans t , employee e \r\n"
			+ " WHERE e.empno = t.actionby AND t.IntimateStatusCode =  sta.pisstatuscode ORDER BY t.IntimateStatusCode DESC LIMIT 1) AS 'empno',\r\n"
			+ "(SELECT empname FROM intimation_exam_trans t , employee e  WHERE e.empno = t.actionby AND t.IntimateStatusCode =  sta.pisstatuscode ORDER BY t.IntimationTransId DESC LIMIT 1) AS 'empname',\r\n"
			+ "\r\n"
			+ "(SELECT designation FROM intimation_exam_trans t , employee e,employee_desig des WHERE e.empno = t.actionby AND e.desigid=des.desigid AND t.IntimateStatusCode =  sta.pisstatuscode ORDER BY t.IntimationTransId DESC LIMIT 1) AS 'Designation',\r\n"
			+ "MAX(tra.actiondate) AS actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor ,sta.pisstatuscode \r\n"
			+ "FROM  intimation_exam_trans tra, pis_approval_status sta,employee emp,intimation_exam par \r\n"
			+ "WHERE par.ExamId = tra.ExamId AND tra.IntimateStatusCode = sta.pisstatuscode AND \r\n"
			+ "tra.actionby=emp.empno AND sta.pisstatuscode IN ('VDG','VSO','VPA','APR','DPR') AND par.ExamId =:examId \r\n"
			+ "GROUP BY sta.pisstatuscode ORDER BY actiondate ASC";
	
	@Override
	public List<Object[]> getIntimationApprovalData(String examId) throws Exception {
		
		Query query=manager.createNativeQuery(INTIMATIONAPPROVALDATA);
		query.setParameter("examId", examId );
		return (List<Object[]>)query.getResultList();
	}

	public static final String EMPGENDERPASSPORT="SELECT empd.Title,empd.Gender,\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "(SELECT p.member_name FROM pis_emp_family_details p,pis_emp_family_relation r\r\n"
			+ " WHERE  p.relation_id = r.relation_id AND r.relation_id=3 AND p.EmpId IN \r\n"
			+ "(SELECT e.EmpId FROM employee e,noc_passport n,pis_emp_family_details pisf WHERE e.EmpNo=n.EmpNo AND n.NocPassportId=:passportid)) AS'FatherName'\r\n"
			+ "\r\n"
			+ "FROM employee_details empd,noc_passport n WHERE n.EmpNo=empd.EmpNo\r\n"
			+ "AND n.isActive='1'  AND  n.NocPassportId=:passportid";
	@Override
	public Object[] getEmpGenderPassport(String passportid) throws Exception {
		

		try {
			Query query= manager.createNativeQuery(EMPGENDERPASSPORT);
			query.setParameter("passportid", passportid);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getEmpGenderPassport " + e);
			e.printStackTrace();
			return null;
		}		
	}

	public static final String NOCPASSPORTAPPROVALDATA="SELECT tra.NocPassportTransId,\r\n"
			+ "(SELECT empno FROM  noc_passport_trans t , employee e WHERE e.empno = t.actionby AND t.NocStatusCode =  sta.pisstatuscode AND  n.NocPassportId=t.NocPassportId ORDER BY t.NocStatusCode DESC LIMIT 1) AS 'empno',\r\n"
			+ "(SELECT empname FROM noc_passport_trans t , employee e  WHERE e.empno = t.actionby AND t.NocStatusCode =  sta.pisstatuscode AND n.NocPassportId=t.NocPassportId ORDER BY t.NocPassportTransId DESC LIMIT 1) AS 'empname',\r\n"
			+ "\r\n"
			+ "(SELECT designation FROM noc_passport_trans t , employee e,employee_desig des WHERE e.empno = t.actionby AND e.desigid=des.desigid AND t.NocStatusCode =  sta.pisstatuscode  AND n.NocPassportId=t.NocPassportId ORDER BY t.NocPassportTransId DESC LIMIT 1) AS 'Designation',\r\n"
			+ "MAX(tra.actiondate) AS actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor ,sta.pisstatuscode \r\n"
			+ "FROM  noc_passport_trans tra, pis_approval_status sta,employee emp,noc_passport n\r\n"
			+ "WHERE n.NocPassportId = tra.NocPassportId AND tra.NocStatusCode = sta.pisstatuscode AND \r\n"
			+ "tra.actionby=emp.empno AND sta.pisstatuscode IN ('VGI','VDI','VDG','VSO','VPA','APR','DPR') AND n.NocPassportId = :passportid \r\n"
			+ "GROUP BY sta.pisstatuscode ORDER BY actiondate ASC";
	
	@Override
	public List<Object[]> getNOCPassportApprovalData(String passportid) throws Exception {
		
		Query query=manager.createNativeQuery(NOCPASSPORTAPPROVALDATA);
		query.setParameter("passportid", passportid );
		return (List<Object[]>)query.getResultList();
	}
	

}
		
	
	



	





	

		
		
	

	

	


	
	


