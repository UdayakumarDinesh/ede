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

import com.vts.ems.login.Login;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
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

	private static final String EMPLOYEEDETAILSLIST = "SELECT e.empid,e.empno,e.empname,e.srno,dm.divisionname,dm.DivisionCode,dg.groupname,dg.GroupCode, ed.designation FROM employee e, division_master dm,division_group dg, employee_desig ed WHERE e.isactive=1 AND e.designationid=ed.desigid AND e.divisionid=dm.divisionid AND dm.groupid=dg.groupid ORDER BY e.srno DESC";
	private static final String EMPLOYEEDETAILS = "SELECT   e.empid,  e.srno,  e.empno,  e.empname,  e.Title,  e.dob,  e.DOJL,  e.DOA,  e.DOR,  e.gender,  e.BloodGroup,  e.maritalStatus,  e.Religion,  e.pan,  e.punchcard,  e.uid,  e.email,  e.designationid,  e.divisionid,  e.groupid,  e.SBIAccNo,  e.CategoryId,  ed.designation,  dm.divisionname,  dm.DivisionCode,  dg.groupname,  dg.GroupCode,e.hometown, e.quarters ,e.photo FROM  employee e,  division_master dm,  division_group dg,  employee_desig ed WHERE e.isactive = 1  AND e.designationid = ed.desigid  AND e.divisionid = dm.divisionid  AND dm.groupid = dg.groupid  AND empid = :empid ORDER BY e.srno DESC";
	private static final String PUNCHCARD = "SELECT COUNT(PunchCard) FROM employee WHERE PunchCard=:punchCard";
	private static final String PHOTOPATH = "select photo from employee where empid=:empid";
	private static final String PHOTOUPDATE = "update employee set photo=:Path where empid=:EmpId";
	private static final String LOGINMASTER = "SELECT a.loginid, a.username, b.divisionname, 'Y' , e.empname, d.designation ,lt.logindesc FROM login a , division_master b , employee e, employee_desig d  ,  login_type lt WHERE e.divisionid=b.divisionid AND a.isactive=1 AND a.empid=e.empid  AND e.designationid=d.desigid AND a.logintype=lt.logintype";
	private static final String EMPLIST = "SELECT empid ,empname FROM employee WHERE isactive=1 ORDER BY srno";
	private static final String USERNAMEPRESENTCOUNT="SELECT COUNT(*) FROM login WHERE username=:username AND isactive='1'";
	private static final String LOGINEDITDATA="FROM Login WHERE LOGINID=:LoginId";
	private static final String EDITUSERMANAGER="UPDATE login SET logintype=:logintype , modifiedby=:modifiedby , modifieddate=:modifieddate , empid=:empid WHERE loginid=:loginid";
	private static final String FAMILYLIST="SELECT a.family_details_id , a.member_name  , b.relation_name , a.dob , c.family_status FROM pis_emp_family_details a , pis_emp_family_relation b , pis_emp_family_status c WHERE a.family_status_id = c.family_status_id   AND a.relation_id = b.relation_id AND a.IsActive='1' AND a.empid=:empid";
	private static final String DELETEUSERMANAGER="UPDATE login SET isactive=:isactive , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE loginid=:loginid";
	private static final String LOGINLIST="SELECT logintype,logindesc FROM login_type";
	
	@Override
	public List<Object[]> EmployeeDetailsList(String LoginType, String Empid) throws Exception {
		logger.info(new Date() + "Inside EmployeeDetailsList()");
		Query query = manager.createNativeQuery(EMPLOYEEDETAILSLIST);
		return (List<Object[]>) query.getResultList();
	}

	@Override
	public Object[] EmployeeDetails(String empid) throws Exception {
		logger.info(new Date() + "Inside EmployeeDetails()");
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
		logger.info(new Date() + "Inside DivisionList()");
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
		logger.info(new Date() + "Inside DesigList()");
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
		logger.info(new Date() + "Inside PayLevelList");
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
		logger.info(new Date() + "Inside PisCaderList()");
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
		logger.info(new Date() + "Inside PisCatClassList()");
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
		logger.info(new Date() + "Inside PisCategoryList()");
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
		logger.info(new Date() + "Inside EmpStatusList()");
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
		logger.info(new Date() +"Inside EmployeeAddSubmit()");

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
		logger.info(new Date() + "Inside EmployeeEditSubmit()");
		try {
			manager.merge(emp);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp.getEmpId();
	}

	@Override
	public long getempno() throws Exception {
		logger.info(new Date() + "Inside EmployeeEditSubmit()");
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
	public Employee getEmployee(String empid) throws Exception {
		logger.info(new Date() + "Inside PisCaderList()");
		Employee employee = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
			Root<Employee> root = cq.from(Employee.class);
			Predicate p1 = cb.equal(root.get("EmpId"), Long.parseLong(empid));
			cq = cq.select(root).where(p1);
			TypedQuery<Employee> allquery = manager.createQuery(cq);
			employee = allquery.getResultList().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public int PunchcardList(String Punchcard) throws Exception {
		logger.info(new Date() + "Inside PunchcardList()");
		Query query = manager.createNativeQuery(PUNCHCARD);
		query.setParameter("punchCard", Punchcard);
		Object o = query.getSingleResult();

		Integer value = Integer.parseInt(o.toString());
		int result = value;

		return result;
	}

	@Override
	public String PhotoPath(String empid) throws Exception {
		logger.info(new Date() + "Inside PunchcardList()");
		Query query = manager.createNativeQuery(PHOTOPATH);
		query.setParameter("empid", empid);
		String EmpName = (String) query.getSingleResult();
		return EmpName;
	}

	@Override
	public int PhotoPathUpdate(String Path, String EmpId) throws Exception {
		logger.info(new Date() + "Inside PhotoPathUpdate()");
		Query query = manager.createNativeQuery(PHOTOUPDATE);
		query.setParameter("Path", Path);
		query.setParameter("EmpId", EmpId);
		int count = (int) query.executeUpdate();

		return count;
	}

	@Override
	public List<Object[]> LoginMasterList(String LoginType, String Empid) throws Exception {
		logger.info(new Date() + "Inside LoginMasterList()");
		Query query = manager.createNativeQuery(LOGINMASTER);
		return (List<Object[]>) query.getResultList();
	}

	
	@Override
	public List<Object[]> getEmpList() throws Exception {
		logger.info(new Date() + "Inside EmployeeList()");
		Query query = manager.createNativeQuery(EMPLIST);
		return (List<Object[]>) query.getResultList();
	}
	
	
	@Override
	public List<Object[]> getLoginTypeList()throws Exception{
		logger.info(new Date() + "Inside getLoginTypeList()");
		Query query = manager.createNativeQuery(LOGINLIST);
		return (List<Object[]>) query.getResultList();
	}
	
	
	
	@Override
	public int UserManagerDelete(String username , String loginid)throws Exception{
		logger.info(new Date() + "Inside UserManagerDelete()");
		Query query = manager.createNativeQuery(DELETEUSERMANAGER);
		query.setParameter("modifiedby", username);
		query.setParameter("loginid", loginid);
		query.setParameter("modifieddate", sdtf.format(new Date()));
		query.setParameter("isactive", 0);
		int count = (int) query.executeUpdate();
		return count;
	}
	
	
	
	
	@Override
	public int UserNamePresentCount(String UserName)throws Exception{
		logger.info(new Date() + "Inside UserNamePresentCount()");
		Query query = manager.createNativeQuery(USERNAMEPRESENTCOUNT);
		query.setParameter("username", UserName);
		BigInteger UserNamePresentCount = (BigInteger) query.getSingleResult();
		return   UserNamePresentCount.intValue();
	}
	
	@Override
	public Long UserManagerAdd(Login login) throws Exception {
	
		logger.info(new Date() + "Inside UserManagerAdd()");
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
		logger.info(new Date() + "Inside UserManagerAdd()");
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
		
		logger.info(new Date() + "Inside UserManagerEdit()");
		Query query = manager.createNativeQuery(EDITUSERMANAGER);
		query.setParameter("modifiedby", login.getModifiedBy());
		query.setParameter("logintype", login.getLoginType());
		query.setParameter("modifieddate", login.getModifiedDate());
		query.setParameter("empid", login.getEmpId());
		query.setParameter("loginid", login.getLoginId());
		int count = (int) query.executeUpdate();
		return count;
		
	}
	
	@Override
	public List<Object[]> getFamilyMembersList(String empid)throws Exception{
		logger.info(new Date() + "Inside getFamilyMembersList()");
		Query query = manager.createNativeQuery(FAMILYLIST);
		query.setParameter("empid", empid);
		return (List<Object[]>) query.getResultList();
	}
	private static final String EMPDATA="SELECT a.empname , b.designation,a.empid FROM employee a ,employee_desig b WHERE b.desigid=a.designationid AND  empid=:empid";
	
	@Override
	public Object[] GetEmpData(String empid)throws Exception{
		logger.info(new Date() + "Inside GetEmpData()");
		try {
			Query query = manager.createNativeQuery(EMPDATA);
			query.setParameter("empid", empid);
			return (Object[]) query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String FAMILYRELATION="SELECT relation_id,relation_name FROM pis_emp_family_relation WHERE IsActive='1'";
	@Override
	public List<Object[]> getFamilyRelation()throws Exception{
		logger.info(new Date() + "Inside getFamilyRelation()");
		Query query = manager.createNativeQuery(FAMILYRELATION);	
		return (List<Object[]>) query.getResultList();
	}
	
	private static final String FAMILYSTATUS="SELECT family_status_id,family_status FROM pis_emp_family_status";
	@Override
	public List<Object[]> getFamilyStatus()throws Exception{
		logger.info(new Date() + "Inside getFamilyStatus()");
		Query query = manager.createNativeQuery(FAMILYSTATUS);	
		return (List<Object[]>) query.getResultList();
	}

	
	@Override
	public Long AddFamilyDetails(EmpFamilyDetails Details) throws Exception {
	
		logger.info(new Date() + "Inside AddFamilyDetails()");
		try {
			manager.persist(Details);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Details.getFamily_details_id();
	}
	private static final String DELETEMEMBER="UPDATE  pis_emp_family_details SET isactive=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE family_details_id=:familyid";
	@Override
	public int DeleteMeber(String familyid , String Username)throws Exception{
		logger.info(new Date() + "Inside UserManagerDelete()");
		Query query = manager.createNativeQuery(DELETEMEMBER);
		
		query.setParameter("modifiedby", Username);
		query.setParameter("modifieddate",sdf.format(new Date()) );
		query.setParameter("IsActive",0);
		query.setParameter("familyid",familyid );
		int count = (int) query.executeUpdate();
		return count;
	}
	private static final String  MEMBEREDITDATA="FROM EmpFamilyDetails WHERE family_details_id=:familyid";
	@Override
	public EmpFamilyDetails	getMemberDetails(String familyid)throws Exception{
		logger.info(new Date() + "Inside getMemberDetails()");
		EmpFamilyDetails memberEditData =null;
		try {
			Query query = manager.createQuery(MEMBEREDITDATA);
			query.setParameter("familyid", Long.parseLong(familyid));
			 memberEditData = (EmpFamilyDetails) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return memberEditData;
	}
	
	
	@Override
	public EmpFamilyDetails getMember(String familyid) throws Exception {
		logger.info(new Date() + "Inside PisCaderList()");
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
	public Long EditFamilyDetails(EmpFamilyDetails Details) throws Exception {
	
		logger.info(new Date() + "Inside EditFamilyDetails()");
		try {
			manager.merge(Details);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Details.getFamily_details_id();
	}
}