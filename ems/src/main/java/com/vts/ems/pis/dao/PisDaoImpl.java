package com.vts.ems.pis.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vts.ems.Admin.model.LoginPasswordHistory;
import com.vts.ems.login.Login;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.AddressEmec;
import com.vts.ems.pis.model.AddressNextKin;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisEmpFamilyForm;
import com.vts.ems.pis.model.PisFamFormMembers;
import com.vts.ems.pis.model.PisPayLevel;
import com.vts.ems.utils.DateTimeFormatUtil;

@Repository
@Transactional
public class PisDaoImpl implements PisDao {
	private static final Logger logger = LogManager.getLogger(PisDaoImpl.class);

	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	@PersistenceContext
	EntityManager manager;

	private static final String EMPLOYEEDETAILSLIST = "SELECT e.empid,e.empno,e.empname,e.srno, ed.designation ,eds.dob FROM employee e, employee_desig ed,employee_details eds WHERE  e.empno=eds.empno  AND   e.isactive=1 AND e.desigid=ed.desigid ORDER BY e.srno=0,e.srno ";
	private static final String EMPLOYEEDETAILS = "SELECT   e.empid,  e.srno,  e.empno,  e.empname,  ee.Title,  ee.dob,  ee.DOJL,  ee.DOA,  ee.DOR,  ee.gender,  ee.BloodGroup,  ee.maritalStatus, ee.Religion,  ee.pan,  ee.punchcard,  ee.uid,  e.email,  e.desigid,  e.divisionid,  ee.groupid,  ee.SBIAccNo,  ee.CategoryId,   ed.designation,  dm.divisionname,  dm.DivisionCode,  dg.groupname,  dg.GroupCode,ee.hometown, ee.quarters ,ee.photo, ee.phoneno , pp.paylevel,e.extno FROM   employee e,  division_master dm,  division_group dg,  employee_desig ed , employee_details ee , pis_pay_level pp WHERE e.isactive = 1  AND e.desigid = ed.desigid  AND e.divisionid = dm.divisionid  AND dm.groupid = dg.groupid AND e.empno=ee.empno and ee.paylevelid = pp.paylevelid  AND empid =:empid ORDER BY e.srno DESC";
	private static final String PUNCHCARD = "SELECT COUNT(PunchCard) FROM employee_details WHERE PunchCard=:punchCard";
	private static final String PHOTOPATH = "select photo from employee_details where empno=:empno";
	private static final String PHOTOUPDATE = "update employee_details set photo=:Path where empno=:empno";
	private static final String LOGINMASTER = "SELECT a.loginid, a.username, b.divisionname, 'Y' , e.empname, d.designation ,lt.logindesc FROM login a , division_master b , employee e, employee_desig d  ,  login_type lt WHERE e.divisionid=b.divisionid AND a.isactive=1 AND a.empid=e.empid  AND e.desigid=d.desigid AND a.logintype=lt.logintype order by a.username asc";
	private static final String EMPLIST = " SELECT empid,empname FROM employee e WHERE e.isactive='1' AND empid NOT IN (SELECT empid FROM login WHERE isactive=1) ORDER BY srno ";
	private static final String USERNAMEPRESENTCOUNT="SELECT COUNT(*) FROM login WHERE username=:username AND isactive='1'";
	private static final String LOGINEDITDATA="FROM Login WHERE LOGINID=:LoginId";
	private static final String EDITUSERMANAGER="UPDATE login SET logintype=:logintype , modifiedby=:modifiedby , modifieddate=:modifieddate  WHERE loginid=:loginid";
	private static final String FAMILYLIST="SELECT a.family_details_id , a.member_name  , b.relation_name , a.dob  FROM pis_emp_family_details a , pis_emp_family_relation b  WHERE  a.relation_id = b.relation_id  AND  a.IsActive='1' AND a.empid=:empid ORDER BY b.SerialNo ASC  ";
	private static final String DELETEUSERMANAGER="UPDATE login SET isactive=:isactive , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE loginid=:loginid";
	private static final String LOGINLIST="SELECT logintype,logindesc FROM login_type";
	private static final String EMPDATA="SELECT a.empname , b.designation,a.empid ,a.empno FROM employee a ,employee_desig b WHERE b.desigid=a.desigid AND  empid=:empid";
	private static final String FAMILYRELATION="SELECT relation_id,relation_name FROM pis_emp_family_relation WHERE IsActive='1' ORDER BY SerialNo ASC  ";
	private static final String FAMILYSTATUS="SELECT family_status_id,family_status FROM pis_emp_family_status";
	private static final String DELETEMEMBER="UPDATE  pis_emp_family_details SET isactive=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE family_details_id=:familyid";
//	private static final String MEMBEREDITDATA="FROM EmpFamilyDetails WHERE family_details_id=:familyid";
	
	@Override
	public List<Object[]> EmployeeDetailsList(String LoginType, String Empid) throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeDetailsList()");
		Query query = manager.createNativeQuery(EMPLOYEEDETAILSLIST);
		return (List<Object[]>) query.getResultList();
	}

	@Override
	public Object[] EmployeeDetails(String empid) throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeDetails()");
		try {
			Query query = manager.createNativeQuery(EMPLOYEEDETAILS);
			query.setParameter("empid", empid);
			return (Object[]) query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DivisionMaster> DivisionList() throws Exception {
		logger.info(new Date() + "Inside DAO DivisionList()");
		List<DivisionMaster> divlist = new ArrayList<DivisionMaster>();
		try {

			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<DivisionMaster> cq = cb.createQuery(DivisionMaster.class);
			Root<DivisionMaster> root = cq.from(DivisionMaster.class);

			Predicate p1 = cb.equal(root.get("IsActive"), 1);

			cq = cq.select(root).where(p1);

			TypedQuery<DivisionMaster> allquery = manager.createQuery(cq);
			divlist = allquery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return divlist;
	}

	@Override
	public List<EmployeeDesig> DesigList() throws Exception {
		logger.info(new Date() + "Inside DAO DesigList()");
		List<EmployeeDesig> desiglist = new ArrayList<EmployeeDesig>();
		try {


			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmployeeDesig> cq = cb.createQuery(EmployeeDesig.class);

			cq.from(EmployeeDesig.class);
						

			TypedQuery<EmployeeDesig> allquery = manager.createQuery(cq);
			desiglist = allquery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return desiglist;
	}

	@Override
	public List<PisPayLevel> PayLevelList() throws Exception {
		logger.info(new Date() + "Inside DAO PayLevelList");
		List<PisPayLevel> pispaylevel = new ArrayList<PisPayLevel>();
		try {


			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisPayLevel> cq = cb.createQuery(PisPayLevel.class);
			 cq.from(PisPayLevel.class);						
			TypedQuery<PisPayLevel> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public List<PisCadre> PisCaderList() throws Exception {
		logger.info(new Date() + "Inside DAO PisCaderList()");
		List<PisCadre> pispaylevel = new ArrayList<PisCadre>();
		try {

			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisCadre> cq = cb.createQuery(PisCadre.class);
        	 cq.from(PisCadre.class);						

			TypedQuery<PisCadre> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public List<PisCatClass> PisCatClassList() throws Exception {
		logger.info(new Date() + "Inside DAO PisCatClassList()");
		List<PisCatClass> pispaylevel = new ArrayList<PisCatClass>();
		try {


			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisCatClass> cq = cb.createQuery(PisCatClass.class);				
			cq.from(PisCatClass.class);						

			TypedQuery<PisCatClass> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public List<PisCategory> PisCategoryList() throws Exception {
		logger.info(new Date() + "Inside DAO PisCategoryList()");
		List<PisCategory> pispaylevel = new ArrayList<PisCategory>();
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisCategory> cq = cb.createQuery(PisCategory.class);
			cq.from(PisCategory.class);						
			TypedQuery<PisCategory> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public List<EmpStatus> EmpStatusList() throws Exception {
		logger.info(new Date() + "Inside DAO EmpStatusList()");
		List<EmpStatus> pispaylevel = new ArrayList<EmpStatus>();
		try {


			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmpStatus> cq = cb.createQuery(EmpStatus.class);
			cq.from(EmpStatus.class);						
			TypedQuery<EmpStatus> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public long EmployeeAddSubmit(Employee emp) throws Exception
	{
		logger.info(new Date() +"Inside DAO EmployeeAddSubmit()");

		try {
			manager.persist(emp);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp.getEmpId();
	}

	@Override
	public long EmployeeEditSubmit(Employee emp) throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeEditSubmit()");
		try {
			manager.merge(emp);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp.getEmpId();
	}
	
	@Override
	public long EmployeeDetailsAddSubmit(EmployeeDetails emp) throws Exception
	{
		logger.info(new Date() +"Inside DAO EmployeeDetailsDetailsAddSubmit()");

		try {
			manager.persist(emp);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp.getEmpDetailsId();
	}

	@Override
	public long EmployeeDetailsEditSubmit(EmployeeDetails emp) throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeDetailsEditSubmit()");
		try {
			manager.merge(emp);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp.getEmpDetailsId();
	}

	@Override
	public long getempno() throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeEditSubmit()");
		long result = 0;
		try {
			Query query = manager.createNativeQuery("SELECT max(empno) FROM employee");

			Object o = query.getSingleResult();
			result = Long.parseLong(o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	@Override
	public Employee getEmp(String empid) throws Exception {
		logger.info(new Date() + "Inside DAO PisCaderList()");
		Employee employee = null;
		try {
			Query query = manager.createQuery("FROM Employee WHERE EmpId=:empid AND IsActive='1'");
			query.setParameter("empid",Long.parseLong(empid));
			employee =(Employee)query.getSingleResult();
			
	       } catch (Exception e) {
		   e.printStackTrace();
	     }
	return employee;
	}

	@Override
	public EmployeeDetails getEmployee(String empdetailsid) throws Exception {
		logger.info(new Date() + "Inside DAO getEmployee()");
		EmployeeDetails employee = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmployeeDetails> cq = cb.createQuery(EmployeeDetails.class);
			Root<EmployeeDetails> root = cq.from(EmployeeDetails.class);
			Predicate p1 = cb.equal(root.get("EmpDetailsId"),  Long.parseLong(empdetailsid));
			cq = cq.select(root).where(p1);
			TypedQuery<EmployeeDetails> allquery = manager.createQuery(cq);
			employee = allquery.getResultList().get(0);		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	
	@Override
	public EmployeeDetails getEmployeeDetailsData(String empno) throws Exception
	{
		logger.info(new Date() + "Inside DAO getEmployeeDetailsData()");
		EmployeeDetails employee = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmployeeDetails> cq = cb.createQuery(EmployeeDetails.class);
			Root<EmployeeDetails> root = cq.from(EmployeeDetails.class);
			Predicate p1 = cb.equal(root.get("EmpNo"),  Long.parseLong(empno));
			cq = cq.select(root).where(p1);
			TypedQuery<EmployeeDetails> allquery = manager.createQuery(cq);
			employee = allquery.getResultList().get(0);		

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	@Override
	public int PunchcardList(String Punchcard) throws Exception {
		
		logger.info(new Date() + "Inside DAO PunchcardList()");
		
		
		try {
			Query query = manager.createNativeQuery(PUNCHCARD);
			query.setParameter("punchCard", Punchcard);
			Object o = query.getSingleResult();

			Integer value = Integer.parseInt(o.toString());
			int result = value;

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public String PhotoPath(String empno) throws Exception {
		logger.info(new Date() + "Inside DAO PhotoPath()");
		try {
			Query query = manager.createNativeQuery(PHOTOPATH);
			query.setParameter("empno", empno);
			String EmpName = (String) query.getSingleResult();
			return EmpName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public int PhotoPathUpdate(String Path, String empno) throws Exception {
		logger.info(new Date() + "Inside DAO PhotoPathUpdate()");
		try {
			Query query = manager.createNativeQuery(PHOTOUPDATE);
			query.setParameter("Path", Path);
			query.setParameter("empno", empno);
			int count = (int) query.executeUpdate();

			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public List<Object[]> LoginMasterList() throws Exception {
		logger.info(new Date() + "Inside DAO LoginMasterList()");
		try {
			Query query = manager.createNativeQuery(LOGINMASTER);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
				
		}
		
	}

	
	@Override
	public List<Object[]> getEmpList() throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeList()");
		try {
			Query query = manager.createNativeQuery(EMPLIST);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public List<Object[]> getLoginTypeList()throws Exception{
		logger.info(new Date() + "Inside DAO getLoginTypeList()");
		try {
			Query query = manager.createNativeQuery(LOGINLIST);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	@Override
	public int UserManagerDelete(String username , String loginid)throws Exception{
		logger.info(new Date() + "Inside DAO UserManagerDelete()");
		
		try {
			Query query = manager.createNativeQuery(DELETEUSERMANAGER);
			query.setParameter("modifiedby", username);
			query.setParameter("loginid", loginid);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			query.setParameter("isactive", 0);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	
	@Override
	public int UserNamePresentCount(String UserName)throws Exception{
		logger.info(new Date() + "Inside DAO UserNamePresentCount()");
		try {
			Query query = manager.createNativeQuery(USERNAMEPRESENTCOUNT);
			query.setParameter("username", UserName);
			BigInteger UserNamePresentCount = (BigInteger) query.getSingleResult();
			return   UserNamePresentCount.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		
	}
	
	@Override
	public Long UserManagerAdd(Login login) throws Exception {
	
		logger.info(new Date() + "Inside DAO UserManagerAdd()");
		try {
			manager.persist(login);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return login.getLoginId();
	}

	
	@Override
	public Login getLoginEditData(Long LoginId)throws Exception{
		logger.info(new Date() + "Inside DAO UserManagerAdd()");
		Login UserManagerEditData =null;
		try {
			Query query = manager.createQuery(LOGINEDITDATA);
			query.setParameter("LoginId", LoginId);
			 UserManagerEditData = (Login) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return UserManagerEditData;
	}

	@Override
	public int UserManagerEdit(Login login)throws Exception{
		
		logger.info(new Date() + "Inside DAO UserManagerEdit()"); 
		try {
			Query query = manager.createNativeQuery(EDITUSERMANAGER);
			query.setParameter("modifiedby", login.getModifiedBy());
			query.setParameter("logintype", login.getLoginType());
			query.setParameter("modifieddate", login.getModifiedDate());
			query.setParameter("loginid", login.getLoginId());
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
				e.printStackTrace();
				return 0;
		}
		
		
	}
	
	@Override
	public List<Object[]> getFamilyMembersList(String empid)throws Exception{
		logger.info(new Date() + "Inside DAO getFamilyMembersList()");
		
		try {
			Query query = manager.createNativeQuery(FAMILYLIST);
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Object[] GetEmpData(String empid)throws Exception{
		logger.info(new Date() + "Inside DAO GetEmpData()");
		try {
			Query query = manager.createNativeQuery(EMPDATA);
			query.setParameter("empid", empid);
			return (Object[]) query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@Override
	public List<Object[]> getFamilyRelation()throws Exception{
		logger.info(new Date() + "Inside DAO getFamilyRelation()");
		
		try {
			Query query = manager.createNativeQuery(FAMILYRELATION);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	@Override
	public List<Object[]> getFamilyStatus()throws Exception{
		logger.info(new Date() + "Inside DAO getFamilyStatus()");
		try {
			Query query = manager.createNativeQuery(FAMILYSTATUS);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
	@Override
	public Long AddFamilyDetails(EmpFamilyDetails Details) throws Exception {
	
		logger.info(new Date() + "Inside DAO AddFamilyDetails()");
		try {
			manager.persist(Details);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Details.getFamily_details_id();
	}
	
	@Override
	public long PisFamFormMembersAdd(PisFamFormMembers Details) throws Exception 
	{
	
		logger.info(new Date() + "Inside DAO AddFamilyDetails()");
		try {
			manager.persist(Details);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Details.getFormMemberId();
	}
	
	
	@Override
	public int DeleteMeber(String familyid , String Username)throws Exception{
		logger.info(new Date() + "Inside DAO UserManagerDelete()");
		
		try {
			Query query = manager.createNativeQuery(DELETEMEMBER);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("familyid",familyid );
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public EmpFamilyDetails	getMemberDetails(String familyid)throws Exception
	{
		logger.info(new Date() + "Inside DAO getMemberDetails()");
		EmpFamilyDetails memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmpFamilyDetails> cq = cb.createQuery(EmpFamilyDetails.class);
			Root<EmpFamilyDetails> root = cq.from(EmpFamilyDetails.class);
			Predicate p1 = cb.equal(root.get("family_details_id"), Long.parseLong(familyid));
			cq = cq.select(root).where(p1);
			TypedQuery<EmpFamilyDetails> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
	}
	
	
	@Override
	public EmpFamilyDetails getMember(String familyid) throws Exception {
		logger.info(new Date() + "Inside DAO PisCaderList()");
		EmpFamilyDetails memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmpFamilyDetails> cq = cb.createQuery(EmpFamilyDetails.class);
			Root<EmpFamilyDetails> root = cq.from(EmpFamilyDetails.class);
			Predicate p1 = cb.equal(root.get("family_details_id"), Long.parseLong(familyid));
			cq = cq.select(root).where(p1);
			TypedQuery<EmpFamilyDetails> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public PisFamFormMembers getPisFamFormMembers(String formmemberid) throws Exception 
	{
		logger.info(new Date() + "Inside DAO getPisFamFormMembers()");
		PisFamFormMembers formmemeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisFamFormMembers> cq = cb.createQuery(PisFamFormMembers.class);
			Root<PisFamFormMembers> root = cq.from(PisFamFormMembers.class);
			Predicate p1 = cb.equal(root.get("FormMemberId"), Long.parseLong(formmemberid));
			cq = cq.select(root).where(p1);
			TypedQuery<PisFamFormMembers> allquery = manager.createQuery(cq);
			formmemeber = allquery.getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return formmemeber;
	}
	
	@Override
	public long PisFamFormMemberEdit(PisFamFormMembers formmember) throws Exception 
	{
		logger.info(new Date() + "Inside DAO getPisFamFormMembers()");
		try {
			manager.merge(formmember);
			manager.flush();
			return formmember.getFamilyFormId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	@Override
	public Long EditFamilyDetails(EmpFamilyDetails Details) throws Exception {
	
		logger.info(new Date() + "Inside DAO EditFamilyDetails()");
		try {
			manager.merge(Details);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Details.getFamily_details_id();
	}
	private static final String PERMNENTADDRESS="SELECT empid,address_per_id,per_addr,from_per_addr,mobile,alt_mobile,landline,state,city,pin  FROM pis_address_per  WHERE empid=:empid";
	@Override
	public Object[] getPerAddress(String Empid)throws Exception{
		logger.info(new Date() + "Inside DAO GetPerAddress()");
		try {
			Query query = manager.createNativeQuery(PERMNENTADDRESS);
			query.setParameter("empid", Empid);
	        List<Object[]> list  =(List<Object[]> )query.getResultList();
			
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String STATE="SELECT StateId,StateName FROM pis_states";
	@Override
	public List<Object[]> getStates()throws Exception{
		logger.info(new Date() + "Inside DAO GetStates()");
		try {
			Query query = manager.createNativeQuery(STATE);		
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Long AddPerAddress(AddressPer peraddress)throws Exception{
		logger.info(new Date() + "Inside DAO AddPerAddress()");
		try {
			manager.persist(peraddress);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return peraddress.getAddress_per_id();
	}
	private static final String PERADDRESS="FROM AddressPer WHERE empid=:empid";
	@Override
	public AddressPer getPerAddressData(String empid)throws Exception{
		logger.info(new Date() + "Inside DAO getMemberDetails()");
		AddressPer addres=null;
		try {
			Query query = manager.createQuery(PERADDRESS);
			query.setParameter("empid", empid);
			addres = (AddressPer) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return addres;
	}
	
	@Override
	public AddressPer getPeraddress(long addressid) throws Exception {
		logger.info(new Date() + "Inside DAO getPeraddress()");
		AddressPer memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<AddressPer> cq = cb.createQuery(AddressPer.class);
			Root<AddressPer> root = cq.from(AddressPer.class);
			Predicate p1 = cb.equal(root.get("address_per_id"), addressid);
			cq = cq.select(root).where(p1);
			TypedQuery<AddressPer> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public Long EditPerAddress(AddressPer address) throws Exception {
	
		logger.info(new Date() + "Inside DAO EditPerAddress()");
		try {
			manager.merge(address);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return address.getAddress_per_id();
	}
	
	private static final String RESADDRESS="SELECT empid,address_res_id,res_addr,from_res_addr,mobile,QtrType,EmailOfficial,ext  FROM pis_address_res  WHERE empid=:empid AND IsActive='1'";
	@Override
	public List<Object[]> getResAddress(String empid)throws Exception{
		logger.info(new Date() + "Inside DAO GetStates()");
		try {
			Query query = manager.createNativeQuery(RESADDRESS);	
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String KINADDRESS="SELECT empid,address_kin_id,nextkin_addr,from_per_addr,mobile,alt_mobile,landline,state,city,pin  FROM pis_address_kin  WHERE empid=:empid";
	@Override
	public Object[] getKinAddress(String Empid)throws Exception{
		logger.info(new Date() + "Inside DAO GetKinAddress()");
		try {
			Query query = manager.createNativeQuery(KINADDRESS);
			query.setParameter("empid", Empid);
		    List<Object[]> list  =(List<Object[]> )query.getResultList();
			
					if(list.size()>0) {
						return list.get(0);				
					}
					return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String EMEADDRESS="SELECT empid,address_emer_id,emer_addr,from_per_addr,mobile,alt_mobile,landline,state,city,pin  FROM pis_address_emer  WHERE empid=:empid";
	@Override
	public Object[] getEmeAddress(String Empid)throws Exception{
		logger.info(new Date() + "Inside DAO GetEmeAddress()");
		try {
			Query query = manager.createNativeQuery(EMEADDRESS);
			query.setParameter("empid", Empid);
		    List<Object[]> list  =(List<Object[]> )query.getResultList();
			
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
	public Long AddResAddress(AddressRes resaddress)throws Exception{
		logger.info(new Date() + "Inside DAO AddPerAddress()");
		try {
			manager.persist(resaddress);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resaddress.getAddress_res_id();
	}
	
	@Override
	public AddressRes getResAddressData(String addressid) throws Exception {
		logger.info(new Date() + "Inside DAO getResAddressData()");
		AddressRes memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<AddressRes> cq = cb.createQuery(AddressRes.class);
			Root<AddressRes> root = cq.from(AddressRes.class);
			Predicate p1 = cb.equal(root.get("address_res_id"), Long.parseLong(addressid));
			cq = cq.select(root).where(p1);
			TypedQuery<AddressRes> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public Long EditResAddress(AddressRes address) throws Exception {
	
		logger.info(new Date() + "Inside DAO EditResAddress()");
		try {
			manager.merge(address);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return address.getAddress_res_id();
	}
	private static final String DELETERES="UPDATE pis_address_res  SET isactive=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE address_res_id=:addressid";
	@Override
	public int deleteResAdd(String addressid , String Username)throws Exception{
		logger.info(new Date() + "Inside DAO deleteResAdd()");
		
		try {
			Query query = manager.createNativeQuery(DELETERES);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("addressid",addressid);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public Long AddNextAddress(AddressNextKin nextkinaddress)throws Exception{
		logger.info(new Date() + "Inside DAO AddNextAddress()");
		try {
			manager.persist(nextkinaddress);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextkinaddress.getAddress_kin_id();
	}
	@Override
	public AddressNextKin getNextKinaddress(long addressid) throws Exception {
		logger.info(new Date() + "Inside DAO getNextKinaddress()");
		AddressNextKin memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<AddressNextKin> cq = cb.createQuery(AddressNextKin.class);
			Root<AddressNextKin> root = cq.from(AddressNextKin.class);
			Predicate p1 = cb.equal(root.get("address_kin_id"), addressid);
			cq = cq.select(root).where(p1);
			TypedQuery<AddressNextKin> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public Long EditNextKinAddress(AddressNextKin address) throws Exception {
	
		logger.info(new Date() + "Inside DAO EditNextKinAddress()");
		try {
			manager.merge(address);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return address.getAddress_kin_id();
	}
	private static final String NEXTKINADDRESS="FROM AddressNextKin WHERE empid=:empid";
	@Override
	public AddressNextKin getNextKinAddressData(String empid)throws Exception{
		logger.info(new Date() + "Inside DAO getMemberDetails()");
		AddressNextKin addres=null;
		try {
			Query query = manager.createQuery(NEXTKINADDRESS);
			query.setParameter("empid", empid);
			addres = (AddressNextKin) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return addres;
	}
	@Override
	public Long AddEmecAddress(AddressEmec Emecaddress)throws Exception{
		logger.info(new Date() + "Inside DAO AddEmecAddress()");
		try {
			manager.persist(Emecaddress);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Emecaddress.getAddress_emer_id();
	}
	
	@Override
	public AddressEmec getEmecaddress(long addressid) throws Exception {
		logger.info(new Date() + "Inside DAO getEmecaddress()");
		AddressEmec memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<AddressEmec> cq = cb.createQuery(AddressEmec.class);
			Root<AddressEmec> root = cq.from(AddressEmec.class);
			Predicate p1 = cb.equal(root.get("address_emer_id"), addressid);
			cq = cq.select(root).where(p1);
			TypedQuery<AddressEmec> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public Long EditEmecAddress(AddressEmec address) throws Exception {
	
		logger.info(new Date() + "Inside DAO EditEmecAddress()");
		try {
			manager.merge(address);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return address.getAddress_emer_id();
	}
	
	@Override
	public List<Object[]> ReqEmerAddajax(String Empid) throws Exception {
		
		Query query=manager.createNativeQuery("SELECT empid,address_per_id,per_addr,from_per_addr,mobile,alt_mobile,landline,state,city,pin  FROM pis_address_per  WHERE empid=:empid");
		query.setParameter("empid", Empid);
		List<Object[]> ModuleList=(List<Object[]>)query.getResultList();
		return ModuleList;
	}
	
	private static final String EMECADDRESS="FROM AddressEmec WHERE empid=:empid";
	@Override
	public AddressEmec getEmecAddressData(String empid)throws Exception{
		logger.info(new Date() + "Inside DAO getEmecAddressData()");
		AddressEmec addres=null;
		try {
			Query query = manager.createQuery(EMECADDRESS);
			query.setParameter("empid", empid);
			addres = (AddressEmec) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return addres;
	}
	

	private static final String  AUDITSTAMPING="SELECT a.username,a.logindate, a.logindatetime,a.ipaddress, a.macaddress,(CASE WHEN a.logouttype='L' THEN 'Logout' ELSE 'Session Expired' END) AS logouttype,  a.logoutdatetime FROM audit_stamping a , login b WHERE a.LoginDate BETWEEN :fromdate AND :todate AND  a.username=b.username AND CASE WHEN :loginid=0 THEN 1=1 ELSE a.loginid=:loginid END ORDER BY a.LoginDateTime DESC";
	@Override
	public List<Object[]> AuditStampingList(String loginid,String Fromdate,String Todate) throws Exception 
	{
		Query query = manager.createNativeQuery(AUDITSTAMPING);
		query.setParameter("loginid", loginid);
		query.setParameter("fromdate", Fromdate);
		query.setParameter("todate", Todate);
		 
		List<Object[]> AuditStampingList=(List<Object[]>) query.getResultList();
		System.out.println(AuditStampingList.size());
		return AuditStampingList;
	}
	private static final String OLDPASSWORD="select password from login where loginid=:loginid";
	@Override
	public String OldPassword(String UserId) throws Exception {
		logger.info(new Date() +"Inside DAO OldPassword");
		Query query = manager.createNativeQuery(OLDPASSWORD);
		query.setParameter("loginid", UserId);
		
		String OldPassword = (String) query.getSingleResult();
		return   OldPassword;
	}
	private static final String PASSWORDUPDATECHANGE="update login set password=:newpassword,modifiedby=:modifiedby,modifieddate=:modifieddate where loginid=:loginid ";
	@Override
	public int PasswordChange(String OldPassword, String NewPassword ,String loginid, String ModifiedDate,String UserName)throws Exception {
		
		logger.info(new Date() +"Inside DAO PasswordChange");
		Query query = manager.createNativeQuery(PASSWORDUPDATECHANGE);
		
		query.setParameter("newpassword", NewPassword);
		query.setParameter("loginid", loginid);
		query.setParameter("modifiedby", UserName);
		query.setParameter("modifieddate", ModifiedDate);
		int PasswordChange = (int) query.executeUpdate();
		return  PasswordChange;
	}
	
    private static final String NEXTKINADDRESSDETAILS="SELECT alt_mobile , city ,from_per_addr , hometown ,landline , mobile , nextkin_addr , pin ,state FROM pis_address_kin WHERE empid =:empid";
	@Override
	public Object[] EmployeeNextAddressDetails(String empid) throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeNextAddressDetails()");
		try {
			Query query = manager.createNativeQuery(NEXTKINADDRESSDETAILS);
			query.setParameter("empid", empid);
			 List<Object[]> list  =(List<Object[]> )query.getResultList();			
				if(list.size()>0) {
					return list.get(0);				
				}
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String PERADDRESSDETAILS="SELECT alt_mobile , city ,from_per_addr , hometown ,landline , mobile , per_addr , pin ,state FROM pis_address_per WHERE empid =:empid";
	@Override
	public Object[] EmployeePerAddressDetails(String empid) throws Exception {
		logger.info(new Date() + "Inside DAO EmployeePerAddressDetails()");
		try {
			Query query = manager.createNativeQuery(PERADDRESSDETAILS);
			query.setParameter("empid", empid);
	       List<Object[]> list  =(List<Object[]> )query.getResultList();			
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String RESADDRESSDETAILS="SELECT alt_mobile , city ,from_res_addr  ,landline , mobile , res_addr , pin ,state FROM pis_address_res WHERE empid =:empid";
	@Override
	public List<Object[]> EmployeeResAddressDetails(String empid) throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeResAddressDetails()");
		try {
			Query query = manager.createNativeQuery(RESADDRESSDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String EMECADDRESSDETAILS="SELECT alt_mobile , city ,from_per_addr , hometown ,landline , mobile , emer_addr , pin ,state FROM pis_address_emer WHERE empid =:empid";
	@Override
	public Object[] EmployeeEmeAddressDetails(String empid) throws Exception {
		logger.info(new Date() + "Inside DAO EmployeeEmeAddressDetails()");
		try {
			Query query = manager.createNativeQuery(EMECADDRESSDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> list  =(List<Object[]> )query.getResultList();
			
			if(list.size()>0) {
				return list.get(0);				
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String FAMILYDETAILS="SELECT a.member_name, b.relation_name, a.dob, a.med_dep, a.blood_group, a.emp_unemp,   a.MemberOccupation,  a.MemberIncome FROM  pis_emp_family_details a,pis_emp_family_relation b WHERE a.relation_id=b.relation_id AND a.isactive='1'  AND a.empid=:empid ORDER BY b.SerialNo ASC ;  ";
	@Override
	public List<Object[]> getFamilydetails(String empid) throws Exception 
	{
		logger.info(new Date() + "Inside DAO getFamilydetails()");
		try {
			Query query = manager.createNativeQuery(FAMILYDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	private static final String RESETPASSWORD="UPDATE login SET PASSWORD=:resetpwd,modifiedby=:modifiedby,modifieddate=:modifieddate WHERE loginid=:loginid";
	@Override
	public int ResetPassword(String loginid, String password,String UserName )throws Exception
	{
		
		logger.info(new Date() +"Inside DAO ResetPassword()");
		Query query = manager.createNativeQuery(RESETPASSWORD);
		
		query.setParameter("resetpwd", password);
		query.setParameter("loginid", loginid);
		query.setParameter("modifiedby", UserName);
		query.setParameter("modifieddate", sdf.format(new Date()));
		int resetpwd = (int) query.executeUpdate();
		
		return  resetpwd;
	}
	
	private static final String ALLEMPLIST="SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId";
	@Override
	public List<Object[]> GetAllEmployee()throws Exception
	{
		logger.info(new Date() + "Inside DAO GetAllEmployee()");
		try {
			Query query = manager.createNativeQuery(ALLEMPLIST);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String GETPHONENO="SELECT a.loginid,  e.empname,  e.phoneno FROM login a ,  employee e WHERE  a.isactive=1 AND a.empid=e.empid  AND a.loginid=:loginid";
	@Override
	public Object[] GetEmpPhoneNo(String loginid) throws Exception
	{
		logger.info(new Date() + "Inside DAO GetEmpPhoneNo()");
		try {
			Query query = manager.createNativeQuery(GETPHONENO);
			query.setParameter("loginid", loginid);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String GETEMPLIST="SELECT a.loginid, a.empid,a.username, e.EmpName FROM login a , employee e WHERE e.isactive=1 AND a.isactive=1 AND a.EmpId=e.EmpId";
	@Override
	public List<Object[]> GetEmployeeList()throws Exception
	{
		logger.info(new Date() + "Inside DAO GetAllEmployee()");
		try {
			Query query = manager.createNativeQuery(GETEMPLIST);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	private static final String GETEMPLOYEELOGINDATA="SELECT a.loginid, a.empid,a.username, e.EmpName FROM login a , employee e WHERE e.isactive=1 AND a.EmpId=e.EmpId AND a.loginid= :loginid";
	@Override
	public List<Object[]> GetEmployeeLoginData(String loginid)throws Exception
	{
		logger.info(new Date() + "Inside DAO GetEmployeeLoginData");
		try {
			Query query = manager.createNativeQuery(GETEMPLOYEELOGINDATA);
			query.setParameter("loginid", loginid);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	@Override
	public long loginHisAddSubmit(LoginPasswordHistory model) throws Exception
	{
		logger.info(new Date() +"Inside DAO loginHisAddSubmit");
		try {
			manager.persist(model);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model.getPasswordHistoryId(); 
	}
	private static final String EMPDETAILS="SELECT  a.SrNo,a.empid, a.empno, a.empname, b.designation, a.extno, a.email, c.divisionname, a.desigid, a.divisionid   FROM employee a,employee_desig b, division_master c WHERE a.desigid= b.desigid AND a.divisionid= c.divisionid AND a.isactive='1' AND a.empid=:empid";
	@Override
	public Object[] GetEmpDetails(String empid)throws Exception
	{
		logger.info(new Date() + "Inside DAO GetEmpDetails()");
		try {
			Query query = manager.createNativeQuery(EMPDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private final static String LISTOFSENIORITYNUMBER="SELECT SrNo, EmpId FROM employee WHERE SrNo !=0 ORDER BY SrNo ASC ";
	@Override
	public List<Object[]> UpdateAndGetList(Long empId, String newSeniorityNumber)throws Exception
	{
	    Query query=manager.createNativeQuery(LISTOFSENIORITYNUMBER);
	    List<Object[]> listSeni=(List<Object[]>)query.getResultList();
	    
	    Query updatequery=manager.createNativeQuery(UPDATESRNO);
	    updatequery.setParameter("empid", empId);
        updatequery.setParameter("srno", newSeniorityNumber);  	   
        updatequery.executeUpdate();
        
	    return listSeni;
	}
	
	private final static String UPDATESRNO="UPDATE employee SET SrNo=:srno WHERE EmpId=:empid";
	@Override
	public int UpdateAllSeniority(Long empIdL, Long long1)throws Exception
	{
		    Query updatequery=manager.createNativeQuery(UPDATESRNO);
		    updatequery.setParameter("empid", empIdL);
	        updatequery.setParameter("srno", long1);  	 
	        return updatequery.executeUpdate();
	}
	
	@Override
	public EmpFamilyDetails getFamilyMemberModal(String familydetailsid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getFamilyMemberModal");
		try {
			return manager.find(EmpFamilyDetails.class, Long.parseLong(familydetailsid));			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final  String GETFAMILYDETAILSFWD="SELECT fd.family_details_id, fd.member_name, fr.relation_name, fd.dob, fd.med_dep, fd.blood_group, fd.emp_unemp, fd.MemberOccupation,  fd.MemberIncome ,   fd.relation_id,fd.empid, fd.med_dep_from ,ff.FormStatus,ff.familyformid,ff.formtype,   ffm.formmemberid , ffm.Comments , ffm.IncExcDate,ffm.AttachFilePath,fd.isactive FROM pis_emp_family_form ff ,pis_fam_form_members ffm ,  pis_emp_family_details fd,pis_emp_family_relation fr  WHERE  ffm.isactive=1 AND ff.FamilyFormId = ffm.FamilyFormId AND ffm.FamilyDetailsId=fd.family_details_id AND fd.relation_id=fr.relation_id AND ff.FamilyFormId = :formid ORDER BY ffm.formmemberid ASC;  "; 
	@Override
	public List<Object[]> GetFormMembersList(String formid) throws Exception 
	{
		logger.info(new Date() + "Inside DAO getFamilydetails");
		try {
			Query query = manager.createNativeQuery(GETFAMILYDETAILSFWD);
			query.setParameter("formid",formid);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	
	private static final String GETEMPLOYEEINFO="Select a.empid,a.empno,a.empname,a.desigid,b.basicpay,b.gender,b.bloodgroup,a.email,b.phoneno,b.paylevelid,b.dob,b.BasicPay,  ed.Designation from employee a, employee_details b,employee_desig ed where a.empno=b.empno AND a.DesigId = ed.DesigId AND a.isactive='1' AND a.empid=:empid ";
	
	@Override
	public  Object[] getEmployeeInfo(String empid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getEmployeeInfo");
		Query query =manager.createNativeQuery(GETEMPLOYEEINFO);
		Object[] result = null;
		query.setParameter("empid", empid);
		
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static final String EMPLOYEERESADDR="SELECT empid,res_addr,mobile,landline FROM pis_address_res WHERE  empid= :empid AND isactive=1 ORDER BY from_res_addr DESC LIMIT 1;";
	
	@Override
	public  Object[] employeeResAddr(String empid) throws Exception
	{
		logger.info(new Date() +"Inside DAO employeeResAddr");
		Query query =manager.createNativeQuery(EMPLOYEERESADDR);
		Object[] result = null;
		query.setParameter("empid", empid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			
		}
		return result;
	}
	
//	private static final String UPDATEMEMBERSTATUS = "UPDATE  pis_emp_family_form SET FormStatus = :status ,ForwardedDateTime = :ForwardedDateTime WHERE FamilyFormId = :formid "; 
	@Override
	public long UpdateMemberStatus(PisEmpFamilyForm famform)throws Exception
	{
		logger.info(new Date() +"Inside DAO UpdateMemberStatus");
		try {
			manager.persist(famform);	
			manager.flush();
			
			return famform.getFamilyFormId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	@Override
	public PisEmpFamilyForm getPisEmpFamilyForm(String familyformid1) throws Exception
	{
		logger.info(new Date() + "Inside DAO getPisEmpFamilyForm");
		PisEmpFamilyForm famform = null;
		try {
			
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<PisEmpFamilyForm> cq= cb.createQuery(PisEmpFamilyForm.class);
			Root<PisEmpFamilyForm> root= cq.from(PisEmpFamilyForm.class);					
			Predicate p1=cb.equal(root.get("FamilyFormId") , Long.parseLong(familyformid1));
			cq=cq.select(root).where(p1);
			TypedQuery<PisEmpFamilyForm> allquery = manager.createQuery(cq);
			famform= allquery.getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return famform;
	}
	
	
	private static final String FAMMEMFWDEMPLIST="SELECT  fd.familyformid,  e.empid,empname,fd.formstatus,DATE(fd.ForwardedDateTime) AS ForwardedDate, e.empno ,fd.formtype FROM  pis_emp_family_form fd,  employee e WHERE e.empid = fd.empid  AND fd.formstatus IN ('F');";
	@Override
	public List<Object[]> FamMemFwdEmpList() throws Exception 
	{
		logger.info(new Date() + "Inside DAO FamMemFwdEmpList()");
		try {
			Query query = manager.createNativeQuery(FAMMEMFWDEMPLIST);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	
	private static final String FAMILYRELATIONLIST="SELECT relation_id, relation_name,Gender FROM pis_emp_family_relation WHERE isactive=1 AND relation_name NOT LIKE 'self' ORDER BY SerialNo ASC";
	@Override
	public List<Object[]> familyRelationList()throws Exception
	{
		logger.info(new Date() +"Inside DAO familyRelationList");
		Query query = manager.createNativeQuery(FAMILYRELATIONLIST);
		List<Object[]> List=(List<Object[]>) query.getResultList();
		return  List;
	}
	
	
	private static final String RELATIONSHIPDATA="SELECT relation_id, relation_name,Gender FROM pis_emp_family_relation WHERE relation_id= :relationid ";
	
	@Override
	public  Object[] RelationshipData(String relationid) throws Exception
	{
		logger.info(new Date() +"Inside DAO RelationshipData");
		Query query =manager.createNativeQuery(RELATIONSHIPDATA);
		Object[] result = null;
		query.setParameter("relationid", relationid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static final String GETMEMBERDATA="SELECT   a.family_details_id,  a.member_name,  b.relation_name,  a.dob,  a.med_dep,  a.blood_group,  a.emp_unemp,   a.MemberOccupation,  a.MemberIncome,     ffm.Comments ,  ffm.AttachFilePath,  ffm.IncExcDate,   a.relation_id,a.empid, a.isactive   FROM  pis_emp_family_details a,  pis_emp_family_relation b , pis_fam_form_members ffm WHERE a.relation_id = b.relation_id AND ffm.familydetailsid=a.family_details_id  AND ffm.formmemberid = :formmemberid";
	
	@Override
	public  Object[] getMemberdata(String formmemberid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getMemberdata");
		Query query =manager.createNativeQuery(GETMEMBERDATA);
		Object[] result = null;
		query.setParameter("formmemberid", formmemberid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	private static final String EMPFAMFORMSLIST="SELECT ff.FamilyFormId,ff.Empid,ff.FormType,ff.FormStatus,DATE(ff.ForwardedDateTime) AS 'ForwardedDateTime',ff.ApprovedBy,DATE(ff.ApprovedDateTime) AS 'ApprovedDateTime',e.empname  FROM pis_emp_family_form ff, employee e WHERE ff.isactive=1 AND ff.empid=e.empid AND ff.empid =:empid";
	
	@Override
	public List<Object[]> EmpFamFormsList(String empid,String status) throws Exception
	{
		logger.info(new Date() +"Inside DAO EmpFamFormsList");
		Query query =manager.createNativeQuery(EMPFAMFORMSLIST);
		query.setParameter("empid", empid);
		List<Object[]> result = new ArrayList<>();
		try {
			result = (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static final String FAMTABLEUPDATE = "UPDATE pis_emp_family_details fd, pis_fam_form_members  ff SET fd.isactive=1 , fd.med_dep=:medDepStatus, fd.modifiedby =:username ,fd.modifieddate=:ModifiedDate   WHERE ff.FamilyDetailsId = fd.family_details_id AND ff.isactive=1 AND ff.FamilyFormId=:formid ;";
	@Override
	public int FamilyMemIncConfirm(String formid, String empid,String username,String medDepStatus)throws Exception
	{
		logger.info(new Date() +"Inside DAO FamilyMemIncConfirm");
		try {
		    Query query=manager.createNativeQuery(FAMTABLEUPDATE);
		    query.setParameter("formid", formid);
		    query.setParameter("medDepStatus", medDepStatus);
		    query.setParameter("username", username);
		    query.setParameter("ModifiedDate", DateTimeFormatUtil.getSqlDateAndTimeFormat().format(new Date()));
	        return query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long EmpFamilyFormAdd(PisEmpFamilyForm form) throws Exception
	{
		logger.info(new Date() +"Inside DAO EmpFamilyFormAdd()");

		try {
			manager.persist(form);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return form.getFamilyFormId();
	}
	
private static final String GETFAMFORMDATA="SELECT ff.FamilyFormId,ff.Empid,ff.FormType,ff.FormStatus,DATE(ff.ForwardedDateTime) AS 'ForwardedDateTime',ff.ApprovedBy,DATE(ff.ApprovedDateTime) AS 'ApprovedDateTime',e.empname ,e1.empname AS 'approvedemp',e1.designation,ff.remarks FROM  employee e,pis_emp_family_form ff    LEFT JOIN (SELECT e2.empid,e2.empname,e2.desigid,ed.designation FROM employee e2, employee_desig ed WHERE e2.desigid=ed.desigid )e1 ON (ff.approvedby = e1.empid) WHERE ff.isactive=1 AND ff.empid=e.empid  AND ff.FamilyFormId = :familyformid";
	
	@Override
	public  Object[] GetFamFormData(String familyformid) throws Exception
	{
		logger.info(new Date() +"Inside DAO GetFamFormData");
		Query query =manager.createNativeQuery(GETFAMFORMDATA);
		Object[] result = null;
		query.setParameter("familyformid", familyformid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			
		}
		return result;
	}
	
	private static final String FAMILYMEMBERDELETE = "DELETE FROM `pis_fam_form_members` WHERE formmemberid = :formmemberid"; 
	@Override
	public int FormFamilyMemberDelete(String formmemberid)throws Exception
	{
		logger.info(new Date() +"Inside DAO FamilyMemberDelete");
		try {
		    Query query=manager.createNativeQuery(FAMILYMEMBERDELETE);
		    query.setParameter("formmemberid", formmemberid);
	        return query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String FORMFAMILYMEMBERHARDDELETE = "DELETE FROM pis_emp_family_details WHERE family_details_id =:familydetailsid ; "; 
	@Override
	public int FormFamilyMemberHardDelete(String familydetailsid)throws Exception
	{
		logger.info(new Date() +"Inside DAO FormFamilyMemberHardDelete");
		try {
		    Query query=manager.createNativeQuery(FORMFAMILYMEMBERHARDDELETE);
		    query.setParameter("familydetailsid", familydetailsid);
	        return query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	private static final String  EMPBLOODGROPUEDIT = "UPDATE employee_details SET bloodgroup  =:bloodgroup WHERE empno=:empno";
	
	@Override
	public int EmpBloodGropuEdit(String empno , String bloodgroup)throws Exception {
		logger.info(new Date() + "Inside DAO EmpBloodGropuEdit");
		try {
			Query query = manager.createNativeQuery(EMPBLOODGROPUEDIT);
			query.setParameter("empno", empno);
			query.setParameter("bloodgroup", bloodgroup);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long NotificationAdd(EMSNotification notification ) throws Exception
	{
		logger.info(new Date() +"Inside DAO NotificationAdd");
		try {
			manager.persist(notification);
			manager.flush();
			
			return notification.getNotificationId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String LOGINTYPEEMPDATA="SELECT loginid, empid, username, logintype FROM login WHERE isactive=1 AND  logintype=:logintype ";
	
	@Override
	public List<Object[]> loginTypeEmpData(String logintype) throws Exception
	{
		logger.info(new Date() +"Inside DAO loginTypeEmpData");
		Query query =manager.createNativeQuery(LOGINTYPEEMPDATA);
		query.setParameter("logintype", logintype);
		List<Object[]> result = new ArrayList<>();
		try {
			result = (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	private static final String EMPFAMMEMBERSLISTMEDDEP="SELECT fd.family_details_id, fd.empid,fd.member_name,fd.relation_id,fr.relation_name FROM pis_emp_family_details fd, pis_emp_family_relation fr WHERE fd.isactive=1 AND fd.relation_id=fr.relation_id AND med_dep= 'Y' AND fd.empid = :empid";
	
	@Override
	public List<Object[]> EmpFamMembersListMedDep(String empid) throws Exception
	{
		logger.info(new Date() +"Inside DAO EmpFamMembersListMedDep");
		Query query =manager.createNativeQuery(EMPFAMMEMBERSLISTMEDDEP);
		query.setParameter("empid", empid);
		List<Object[]> result = new ArrayList<>();
		try {
			result = (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static final String EMPFAMMEMBERSNOTMEDDEP="SELECT fd.family_details_id, fd.empid,fd.member_name,fd.relation_id,fr.relation_name,fd.dob,fd.memberoccupation, fd.memberincome FROM pis_emp_family_details fd, pis_emp_family_relation fr WHERE fd.isactive=1 AND fd.relation_id=fr.relation_id AND med_dep= 'N' AND fd.empid = :empid ;";
	
	@Override
	public List<Object[]> EmpFamMembersNotMedDep(String empid) throws Exception
	{
		logger.info(new Date() +"Inside DAO EmpFamMembersNotMedDep");
		Query query =manager.createNativeQuery(EMPFAMMEMBERSNOTMEDDEP);
		query.setParameter("empid", empid);
		List<Object[]> result = new ArrayList<>();
		try {
			result = (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static final  String GETEXCFORMMEMBERSLIST="SELECT fd.family_details_id, fd.member_name, fr.relation_name, fd.dob, fd.med_dep, fd.blood_group, fd.emp_unemp, fd.MemberOccupation,  fd.MemberIncome ,   fd.relation_id,fd.empid, fd.med_dep_from ,ff.FormStatus,ff.familyformid,ff.formtype,   ffm.formmemberid , ffm.Comments , ffm.IncExcDate,ffm.AttachFilePath FROM pis_emp_family_form ff ,pis_fam_form_members ffm ,  pis_emp_family_details fd,pis_emp_family_relation fr   WHERE  ffm.isactive=1 AND ff.FamilyFormId = ffm.FamilyFormId AND ffm.FamilyDetailsId=fd.family_details_id AND fd.relation_id=fr.relation_id AND fd.isactive=1 AND ff.FamilyFormId = :formid ORDER BY ffm.formmemberid ASC;  ";
	
	@Override
	public List<Object[]> GetExcFormMembersList(String formid) throws Exception 
	{
		logger.info(new Date() + "Inside DAO GetExcFormMembersList");
		try {
			Query query = manager.createNativeQuery(GETEXCFORMMEMBERSLIST);
			query.setParameter("formid",formid);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	
	
	
}
	
