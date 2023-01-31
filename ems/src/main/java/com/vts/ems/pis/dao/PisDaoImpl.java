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
import com.vts.ems.pis.model.Appointments;
import com.vts.ems.pis.model.Awards;
import com.vts.ems.pis.model.DisciplineCode;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.pis.model.PISEmpFamilyDeclaration;
import com.vts.ems.pis.model.Passport;
import com.vts.ems.pis.model.PassportForeignVisit;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisEmpFamilyForm;
import com.vts.ems.pis.model.PisFamFormMembers;
import com.vts.ems.pis.model.PisPayLevel;
import com.vts.ems.pis.model.Property;
import com.vts.ems.pis.model.PropertyDetails;
import com.vts.ems.pis.model.Publication;
import com.vts.ems.pis.model.Qualification;
import com.vts.ems.pis.model.QualificationCode;
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
	private static final String EMPLOYEEDETAILS = "SELECT   e.empid,  e.srno,  e.empno,  e.empname,  ee.Title,  ee.dob,  ee.DOJL,  ee.DOA,  ee.DOR,  ee.gender,  ee.BloodGroup,  ee.maritalStatus, ee.Religion,  ee.pan,  ee.punchcard,  ee.uid,  e.email,  e.desigid,  e.divisionid,  ee.groupid,  ee.SBIAccNo,  ee.CategoryId,   ed.designation,  dm.divisionname,  dm.DivisionCode,  dg.groupname,  dg.GroupCode,ee.hometown, ee.quarters ,ee.photo, ee.phoneno , pp.paylevel,e.extno ,pp.paygrade,ee.basicpay FROM   employee e,  division_master dm,  division_group dg,  employee_desig ed , employee_details ee , pis_pay_level pp WHERE e.isactive = 1  AND e.desigid = ed.desigid  AND e.divisionid = dm.divisionid  AND dm.groupid = dg.groupid AND e.empno=ee.empno and ee.paylevelid = pp.paylevelid  AND empid =:empid ORDER BY e.srno DESC";
	private static final String EMPLOYEENO = "SELECT COUNT(empno) FROM employee_details WHERE empno=:empno";
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
		Query query = manager.createNativeQuery(EMPLOYEEDETAILSLIST);
		return (List<Object[]>) query.getResultList();
	}

	@Override
	public Object[] EmployeeDetails(String empid) throws Exception {
		try {
			Query query = manager.createNativeQuery(EMPLOYEEDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> list =(List<Object[]>)query.getResultList();
			Object[] result=null;
			if(list!=null && list.size()>0) {
				result=list.get(0);
			}
			return result;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeDetails "+e);
			e.printStackTrace();
			return null;
		}
	}
	private static final String GETALLDETAILS="SELECT e.empid,  e.srno,  e.empno,  e.empname,  ee.Title,  ee.dob,  ee.DOJL,  ee.DOA,  ee.DOR,  ee.gender,  ee.BloodGroup,  ee.maritalStatus, ee.Religion,  ee.pan,  ee.punchcard,  ee.uid,  e.email,     ee.SBIAccNo,  c.Category_type,   ed.designation,  dm.divisionname,  dm.DivisionCode, ee.gpfno, dg.GroupCode,ee.hometown,  ee.quarters ,  ee.photo,   ee.phoneno ,   pp.paylevel,  ee.pranno ,  pp.paygrade,  ee.basicpay,  cat.cat_name,  cad.cadre  ,  ee.perpassno,  ee.exserviceman, ee.servicestatus,  ee.empstatus ,  ee.ph FROM   employee e,  division_master dm,  division_group dg,  employee_desig ed , employee_details ee , pis_pay_level pp ,pis_category c, pis_cat_class cat ,pis_cadre cad WHERE cat.cat_id=ee.catid AND ee.categoryid=c.category_id AND ee.cadreid=cad.cadreid AND  e.isactive = 1  AND e.desigid = ed.desigid  AND e.divisionid = dm.divisionid  AND  dm.groupid = dg.groupid AND e.empno=ee.empno AND ee.paylevelid = pp.paylevelid  AND empid =:empid ORDER BY e.srno DESC";
	@Override
	public Object[] GetAllEmployeeDetails(String empid) throws Exception {
		try {
			Query query = manager.createNativeQuery(GETALLDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> list =(List<Object[]>) query.getResultList();
			Object[] result=null;
			if(list!=null &&list.size()>0) {
				result =list.get(0);
			}
			return result;
					
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetAllEmployeeDetails "+e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DivisionMaster> DivisionList() throws Exception {
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
			logger.error(new Date() + "Inside DAO DivisionList "+e);
			e.printStackTrace();
		}
		return divlist;
	}

	@Override
	public List<EmployeeDesig> DesigList() throws Exception {
		List<EmployeeDesig> desiglist = new ArrayList<EmployeeDesig>();
		try {


			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmployeeDesig> cq = cb.createQuery(EmployeeDesig.class);

			cq.from(EmployeeDesig.class);
						

			TypedQuery<EmployeeDesig> allquery = manager.createQuery(cq);
			desiglist = allquery.getResultList();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DesigList "+e);
			e.printStackTrace();
		}
		return desiglist;
	}

	@Override
	public List<PisPayLevel> PayLevelList() throws Exception {
		List<PisPayLevel> pispaylevel = new ArrayList<PisPayLevel>();
		try {


			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisPayLevel> cq = cb.createQuery(PisPayLevel.class);
			 cq.from(PisPayLevel.class);						
			TypedQuery<PisPayLevel> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PayLevelList "+e);
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public List<PisCadre> PisCaderList() throws Exception {
		List<PisCadre> pispaylevel = new ArrayList<PisCadre>();
		try {

			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisCadre> cq = cb.createQuery(PisCadre.class);
        	 cq.from(PisCadre.class);						

			TypedQuery<PisCadre> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PisCaderList "+e);
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public List<PisCatClass> PisCatClassList() throws Exception {
		List<PisCatClass> pispaylevel = new ArrayList<PisCatClass>();
		try {


			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisCatClass> cq = cb.createQuery(PisCatClass.class);				
			cq.from(PisCatClass.class);						

			TypedQuery<PisCatClass> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PisCatClassList "+e);
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public List<PisCategory> PisCategoryList() throws Exception {
		List<PisCategory> pispaylevel = new ArrayList<PisCategory>();
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisCategory> cq = cb.createQuery(PisCategory.class);
			cq.from(PisCategory.class);						
			TypedQuery<PisCategory> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PisCategoryList "+e);
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public List<EmpStatus> EmpStatusList() throws Exception {
		List<EmpStatus> pispaylevel = new ArrayList<EmpStatus>();
		try {


			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EmpStatus> cq = cb.createQuery(EmpStatus.class);
			cq.from(EmpStatus.class);						
			TypedQuery<EmpStatus> allquery = manager.createQuery(cq);
			pispaylevel = allquery.getResultList();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmpStatusList "+e);
			e.printStackTrace();
		}
		return pispaylevel;
	}

	@Override
	public long EmployeeAddSubmit(Employee emp) throws Exception
	{

		try {
			manager.persist(emp);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeAddSubmit "+e);
			e.printStackTrace();
		}
		return emp.getEmpId();
	}

	@Override
	public long EmployeeEditSubmit(Employee emp) throws Exception {
		try {
			manager.merge(emp);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeEditSubmit "+e);
			e.printStackTrace();
		}
		return emp.getEmpId();
	}
	
	@Override
	public long EmployeeDetailsAddSubmit(EmployeeDetails emp) throws Exception
	{

		try {
			manager.persist(emp);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeDetailsAddSubmit "+e);
			e.printStackTrace();
		}
		return emp.getEmpDetailsId();
	}

	@Override
	public long EmployeeDetailsEditSubmit(EmployeeDetails emp) throws Exception {
		try {
			manager.merge(emp);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeDetailsEditSubmit "+e);
			e.printStackTrace();
		}
		return emp.getEmpDetailsId();
	}

	@Override
	public long getempno() throws Exception {
		long result = 0;
		try {
			Query query = manager.createNativeQuery("SELECT max(empno) FROM employee");

			Object o = query.getSingleResult();
			result = Long.parseLong(o.toString());
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getempno "+e);
			e.printStackTrace();
		}
		return result;

	}
	
	@Override
	public Employee getEmp(String empid) throws Exception {
		Employee employee = null;
		try {
			Query query = manager.createQuery("FROM Employee WHERE EmpId=:empid AND IsActive='1'");
			query.setParameter("empid",Long.parseLong(empid));
			employee =(Employee)query.getSingleResult();
			
	       } catch (Exception e) {
	    	   logger.error(new Date() + "Inside DAO getEmp "+e);
		   e.printStackTrace();
	     }
	return employee;
	}

	@Override
	public EmployeeDetails getEmployee(String empdetailsid) throws Exception {
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
			logger.error(new Date() + "Inside DAO getEmployee "+e);
			e.printStackTrace();
		}
		return employee;
	}

	
	@Override
	public EmployeeDetails getEmployeeDetailsData(String empno) throws Exception
	{
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
			logger.error(new Date() + "Inside DAO getEmployeeDetailsData "+e);
			e.printStackTrace();
		}
		return employee;
	}
	
	@Override
	public int PunchcardList(String empno) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(EMPLOYEENO);
			query.setParameter("empno", empno);
			Object o = query.getSingleResult();

			Integer value = Integer.parseInt(o.toString());
			int result = value;

			return result;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PunchcardList "+e);
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public String PhotoPath(String empno) throws Exception {
		try {
			Query query = manager.createNativeQuery(PHOTOPATH);
			query.setParameter("empno", empno);
			String EmpName = (String) query.getSingleResult();
			return EmpName;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PhotoPath "+e);
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public int PhotoPathUpdate(String Path, String empno) throws Exception {
		try {
			Query query = manager.createNativeQuery(PHOTOUPDATE);
			query.setParameter("Path", Path);
			query.setParameter("empno", empno);
			int count = (int) query.executeUpdate();

			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PhotoPathUpdate "+e);
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public List<Object[]> LoginMasterList() throws Exception {
		try {
			Query query = manager.createNativeQuery(LOGINMASTER);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO LoginMasterList "+e);
			e.printStackTrace();
			return null;
				
		}
		
	}

	
	@Override
	public List<Object[]> getEmpList() throws Exception {
		try {
			Query query = manager.createNativeQuery(EMPLIST);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmpList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public List<Object[]> getLoginTypeList()throws Exception{
		try {
			Query query = manager.createNativeQuery(LOGINLIST);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getLoginTypeList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	@Override
	public int UserManagerDelete(String username , String loginid)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(DELETEUSERMANAGER);
			query.setParameter("modifiedby", username);
			query.setParameter("loginid", loginid);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			query.setParameter("isactive", 0);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO UserManagerDelete "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	
	@Override
	public int UserNamePresentCount(String UserName)throws Exception{
		try {
			Query query = manager.createNativeQuery(USERNAMEPRESENTCOUNT);
			query.setParameter("username", UserName);
			BigInteger UserNamePresentCount = (BigInteger) query.getSingleResult();
			return   UserNamePresentCount.intValue();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO UserNamePresentCount "+e);
			e.printStackTrace();
			return 0;
		}
		
		
	}
	
	@Override
	public Long UserManagerAdd(Login login) throws Exception {
	
		try {
			manager.persist(login);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO UserManagerAdd "+e);
			e.printStackTrace();
		}
		return login.getLoginId();
	}

	
	@Override
	public Login getLoginEditData(Long LoginId)throws Exception{
		Login UserManagerEditData =null;
		try {
			Query query = manager.createQuery(LOGINEDITDATA);
			query.setParameter("LoginId", LoginId);
			 UserManagerEditData = (Login) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getLoginEditData "+e);
			e.printStackTrace();
		}
		
		return UserManagerEditData;
	}

	@Override
	public int UserManagerEdit(Login login)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(EDITUSERMANAGER);
			query.setParameter("modifiedby", login.getModifiedBy());
			query.setParameter("logintype", login.getLoginType());
			query.setParameter("modifieddate", login.getModifiedDate());
			query.setParameter("loginid", login.getLoginId());
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO UserManagerEdit "+e);
				e.printStackTrace();
				return 0;
		}
		
		
	}
	
	@Override
	public List<Object[]> getFamilyMembersList(String empid)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(FAMILYLIST);
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getFamilyMembersList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Object[] GetEmpData(String empid)throws Exception{
		try {
			Query query = manager.createNativeQuery(EMPDATA);
			query.setParameter("empid", empid);
			return (Object[]) query.getResultList().get(0);
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetEmpData "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@Override
	public List<Object[]> getFamilyRelation()throws Exception{
		
		try {
			Query query = manager.createNativeQuery(FAMILYRELATION);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getFamilyRelation "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	@Override
	public List<Object[]> getFamilyStatus()throws Exception{
		try {
			Query query = manager.createNativeQuery(FAMILYSTATUS);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getFamilyStatus "+e);
			e.printStackTrace();
			return null;
		}
		
	}

	
	@Override
	public Long AddFamilyDetails(EmpFamilyDetails Details) throws Exception {
	
		try {
			manager.persist(Details);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddFamilyDetails "+e);
			e.printStackTrace();
		}
		return Details.getFamily_details_id();
	}
	
	@Override
	public long PisFamFormMembersAdd(PisFamFormMembers Details) throws Exception 
	{
	
		try {
			manager.persist(Details);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PisFamFormMembersAdd "+e);
			e.printStackTrace();
		}
		return Details.getFormMemberId();
	}
	
	
	@Override
	public int DeleteMeber(String familyid , String Username)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(DELETEMEMBER);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("familyid",familyid );
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteMeber "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public EmpFamilyDetails	getMemberDetails(String familyid)throws Exception
	{
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
			logger.error(new Date() + "Inside DAO getMemberDetails "+e);
			e.printStackTrace();
		}
		return memeber;
	}
	
	
	@Override
	public EmpFamilyDetails getMember(String familyid) throws Exception {
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
			logger.error(new Date() + "Inside DAO getMember "+e);
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public PisFamFormMembers getPisFamFormMembers(String formmemberid) throws Exception 
	{
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
			logger.error(new Date() + "Inside DAO getPisFamFormMembers "+e);
			e.printStackTrace();
		}
		return formmemeber;
	}
	
	@Override
	public long PisFamFormMemberEdit(PisFamFormMembers formmember) throws Exception 
	{
		try {
			manager.merge(formmember);
			manager.flush();
			return formmember.getFamilyFormId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PisFamFormMemberEdit "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	
	@Override
	public Long EditFamilyDetails(EmpFamilyDetails Details) throws Exception {
	
		try {
			manager.merge(Details);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditFamilyDetails "+e);
			e.printStackTrace();
		}
		return Details.getFamily_details_id();
	}
	private static final String PERMNENTADDRESS="SELECT empid,address_per_id,per_addr,from_per_addr,mobile,alt_mobile,landline,state,city,pin  FROM pis_address_per  WHERE empid=:empid";
	@Override
	public Object[] getPerAddress(String Empid)throws Exception{
		try {
			Query query = manager.createNativeQuery(PERMNENTADDRESS);
			query.setParameter("empid", Empid);
	        List<Object[]> list  =(List<Object[]> )query.getResultList();
			
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPerAddress "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String STATE="SELECT StateId,StateName FROM pis_states";
	@Override
	public List<Object[]> getStates()throws Exception{
		try {
			Query query = manager.createNativeQuery(STATE);		
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getStates "+e);
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Long AddPerAddress(AddressPer peraddress)throws Exception{
		try {
			manager.persist(peraddress);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddPerAddress "+e);
			e.printStackTrace();
		}
		return peraddress.getAddress_per_id();
	}
	private static final String PERADDRESS="FROM AddressPer WHERE empid=:empid";
	@Override
	public AddressPer getPerAddressData(String empid)throws Exception{
		AddressPer addres=null;
		try {
			Query query = manager.createQuery(PERADDRESS);
			query.setParameter("empid", empid);
			addres = (AddressPer) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPerAddressData "+e);
			e.printStackTrace();
		}		
		return addres;
	}
	
	@Override
	public AddressPer getPeraddress(long addressid) throws Exception {
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
			logger.error(new Date() + "Inside DAO getPeraddress "+e);
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public Long EditPerAddress(AddressPer address) throws Exception {
	
		try {
			manager.merge(address);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditPerAddress "+e);
			e.printStackTrace();
		}
		return address.getAddress_per_id();
	}
	
	private static final String RESADDRESS="SELECT empid,address_res_id,res_addr,from_res_addr,mobile,QtrType,EmailOfficial,ext  FROM pis_address_res  WHERE empid=:empid AND IsActive=1";
	@Override
	public List<Object[]> getResAddress(String empid)throws Exception{
		try {
			Query query = manager.createNativeQuery(RESADDRESS);	
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getResAddress "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String KINADDRESS="SELECT empid,address_kin_id,nextkin_addr,from_per_addr,mobile,alt_mobile,landline,state,city,pin  FROM pis_address_kin  WHERE empid=:empid";
	@Override
	public Object[] getKinAddress(String Empid)throws Exception{
		try {
			Query query = manager.createNativeQuery(KINADDRESS);
			query.setParameter("empid", Empid);
		    List<Object[]> list  =(List<Object[]> )query.getResultList();
			
					if(list.size()>0) {
						return list.get(0);				
					}
					return null;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getKinAddress "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String EMEADDRESS="SELECT empid,address_emer_id,emer_addr,from_per_addr,mobile,alt_mobile,landline,state,city,pin  FROM pis_address_emer  WHERE empid=:empid";
	@Override
	public Object[] getEmeAddress(String Empid)throws Exception{
		try {
			Query query = manager.createNativeQuery(EMEADDRESS);
			query.setParameter("empid", Empid);
		    List<Object[]> list  =(List<Object[]> )query.getResultList();
			
					if(list.size()>0) {
						return list.get(0);				
					}
					return null;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmeAddress "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Long AddResAddress(AddressRes resaddress)throws Exception{
		try {
			manager.persist(resaddress);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddResAddress "+e);
			e.printStackTrace();
		}
		return resaddress.getAddress_res_id();
	}
	
	@Override
	public AddressRes getResAddressData(String addressid) throws Exception {
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
			logger.error(new Date() + "Inside DAO getResAddressData "+e);
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public Long EditResAddress(AddressRes address) throws Exception {
	
		try {
			manager.merge(address);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditResAddress "+e);
			e.printStackTrace();
		}
		return address.getAddress_res_id();
	}
	private static final String DELETERES="UPDATE pis_address_res  SET isactive=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE address_res_id=:addressid";
	@Override
	public int deleteResAdd(String addressid , String Username)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(DELETERES);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("addressid",addressid);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO deleteResAdd "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public Long AddNextAddress(AddressNextKin nextkinaddress)throws Exception{
		try {
			manager.persist(nextkinaddress);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddNextAddress "+e);
			e.printStackTrace();
		}
		return nextkinaddress.getAddress_kin_id();
	}
	@Override
	public AddressNextKin getNextKinaddress(long addressid) throws Exception {
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
			logger.error(new Date() + "Inside DAO getNextKinaddress "+e);
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public Long EditNextKinAddress(AddressNextKin address) throws Exception {
	
		try {
			manager.merge(address);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditNextKinAddress "+e);
			e.printStackTrace();
		}
		return address.getAddress_kin_id();
	}
	private static final String NEXTKINADDRESS="FROM AddressNextKin WHERE empid=:empid";
	@Override
	public AddressNextKin getNextKinAddressData(String empid)throws Exception{
		AddressNextKin addres=null;
		try {
			Query query = manager.createQuery(NEXTKINADDRESS);
			query.setParameter("empid", empid);
			addres = (AddressNextKin) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getNextKinAddressData "+e);
			e.printStackTrace();
		}		
		return addres;
	}
	@Override
	public Long AddEmecAddress(AddressEmec Emecaddress)throws Exception{
		try {
			manager.persist(Emecaddress);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddEmecAddress "+e);
			e.printStackTrace();
		}
		return Emecaddress.getAddress_emer_id();
	}
	
	@Override
	public AddressEmec getEmecaddress(long addressid) throws Exception {
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
			logger.error(new Date() + "Inside DAO getEmecaddress "+e);
			e.printStackTrace();
		}
		return memeber;
	}
	
	@Override
	public Long EditEmecAddress(AddressEmec address) throws Exception {
	
		try {
			manager.merge(address);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditEmecAddress "+e);
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
		AddressEmec addres=null;
		try {
			Query query = manager.createQuery(EMECADDRESS);
			query.setParameter("empid", empid);
			addres = (AddressEmec) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmecAddressData "+e);
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
		
		return AuditStampingList;
	}
	private static final String OLDPASSWORD="select password from login where loginid=:loginid";
	@Override
	public String OldPassword(String UserId) throws Exception {
		Query query = manager.createNativeQuery(OLDPASSWORD);
		query.setParameter("loginid", UserId);
		
		String OldPassword = (String) query.getSingleResult();
		return   OldPassword;
	}
	private static final String PASSWORDUPDATECHANGE="update login set password=:newpassword,modifiedby=:modifiedby,modifieddate=:modifieddate where loginid=:loginid ";
	@Override
	public int PasswordChange(String OldPassword, String NewPassword ,String loginid, String ModifiedDate,String UserName)throws Exception {
		
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
		try {
			Query query = manager.createNativeQuery(NEXTKINADDRESSDETAILS);
			query.setParameter("empid", empid);
			 List<Object[]> list  =(List<Object[]> )query.getResultList();			
				if(list.size()>0) {
					return list.get(0);				
				}
				return null;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeNextAddressDetails "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String PERADDRESSDETAILS="SELECT alt_mobile , city ,from_per_addr , hometown ,landline , mobile , per_addr , pin ,state FROM pis_address_per WHERE empid =:empid";
	@Override
	public Object[] EmployeePerAddressDetails(String empid) throws Exception {
		try {
			Query query = manager.createNativeQuery(PERADDRESSDETAILS);
			query.setParameter("empid", empid);
	       List<Object[]> list  =(List<Object[]> )query.getResultList();			
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeePerAddressDetails "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String RESADDRESSDETAILS="SELECT alt_mobile , city ,from_res_addr  ,landline , mobile , res_addr , pin ,state,ext,qtrno,qtrtype,emailofficial FROM pis_address_res WHERE empid =:empid";
	@Override
	public List<Object[]> EmployeeResAddressDetails(String empid) throws Exception {
		try {
			Query query = manager.createNativeQuery(RESADDRESSDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeResAddressDetails "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String EMECADDRESSDETAILS="SELECT alt_mobile , city ,from_per_addr , hometown ,landline , mobile , emer_addr , pin ,state FROM pis_address_emer WHERE empid =:empid";
	@Override
	public Object[] EmployeeEmeAddressDetails(String empid) throws Exception {
		try {
			Query query = manager.createNativeQuery(EMECADDRESSDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> list  =(List<Object[]> )query.getResultList();
			
			if(list.size()>0) {
				return list.get(0);				
			}
			
			return null;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeEmeAddressDetails "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String FAMILYDETAILS="SELECT a.member_name, b.relation_name, a.dob, a.med_dep, a.blood_group, a.emp_unemp,   a.MemberOccupation,  a.MemberIncome FROM  pis_emp_family_details a,pis_emp_family_relation b WHERE a.relation_id=b.relation_id AND a.isactive='1'  AND a.empid=:empid ORDER BY b.SerialNo ASC ;  ";
	@Override
	public List<Object[]> getFamilydetails(String empid) throws Exception 
	{
		try {
			Query query = manager.createNativeQuery(FAMILYDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getFamilydetails "+e);
			e.printStackTrace();
			return null;
		}
	}

	
	private static final String RESETPASSWORD="UPDATE login SET PASSWORD=:resetpwd,modifiedby=:modifiedby,modifieddate=:modifieddate WHERE loginid=:loginid";
	@Override
	public int ResetPassword(String loginid, String password,String UserName )throws Exception
	{
		
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
		try {
			Query query = manager.createNativeQuery(ALLEMPLIST);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			return list;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetAllEmployee "+e);
			e.printStackTrace();
			return null;
		}
	}
	private static final String GETPHONENO="SELECT a.loginid,  e.empname,  e.phoneno FROM login a ,  employee e WHERE  a.isactive=1 AND a.empid=e.empid  AND a.loginid=:loginid";
	@Override
	public Object[] GetEmpPhoneNo(String loginid) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(GETPHONENO);
			query.setParameter("loginid", loginid);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetEmpPhoneNo "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String GETEMPLIST="SELECT a.loginid, a.empid,a.username, e.EmpName FROM login a , employee e WHERE e.isactive=1 AND a.isactive=1 AND a.EmpId=e.EmpId";
	@Override
	public List<Object[]> GetEmployeeList()throws Exception
	{
		try {
			Query query = manager.createNativeQuery(GETEMPLIST);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			return list;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetEmployeeList "+e);
			e.printStackTrace();
			return null;
		}

	}
	
	private static final String GETEMPLOYEELOGINDATA="SELECT a.loginid, a.empid,a.username, e.EmpName FROM login a , employee e WHERE e.isactive=1 AND a.EmpId=e.EmpId AND a.loginid= :loginid";
	@Override
	public List<Object[]> GetEmployeeLoginData(String loginid)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(GETEMPLOYEELOGINDATA);
			query.setParameter("loginid", loginid);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			return list;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetEmployeeLoginData "+e);
			e.printStackTrace();
			return null;
		}

	}
	
	@Override
	public long loginHisAddSubmit(LoginPasswordHistory model) throws Exception
	{
		try {
			manager.persist(model);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO loginHisAddSubmit "+e);
			e.printStackTrace();
		}
		return model.getPasswordHistoryId(); 
	}
	private static final String EMPDETAILS="SELECT  a.SrNo,a.empid, a.empno, a.empname, b.designation, a.extno, a.email, c.divisionname, a.desigid, a.divisionid   FROM employee a,employee_desig b, division_master c WHERE a.desigid= b.desigid AND a.divisionid= c.divisionid AND a.isactive='1' AND a.empid=:empid";
	@Override
	public Object[] GetEmpDetails(String empid)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(EMPDETAILS);
			query.setParameter("empid", empid);
			List<Object[]> list=(List<Object[]>) query.getResultList();
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetEmpDetails "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private final static String LISTOFSENIORITYNUMBER="SELECT SrNo, EmpId FROM employee WHERE SrNo !=0 and isactive=1 ORDER BY SrNo ASC ";
	private final static String UPDATESRNO="UPDATE employee SET SrNo=:srno WHERE EmpId=:empid";
	
	@Override
	public List<Object[]> UpdateAndGetList(Long empId, String newSeniorityNumber)throws Exception
	{
	    Query query=manager.createNativeQuery(LISTOFSENIORITYNUMBER);
	    List<Object[]> listSeni=(List<Object[]>)query.getResultList();
	    
//	    Query updatequery=manager.createNativeQuery(UPDATESRNO);
//	    updatequery.setParameter("empid", empId);
//        updatequery.setParameter("srno", newSeniorityNumber);  	   
//        updatequery.executeUpdate();
        
	    return listSeni;
	}
	
	@Override
	public int UpdateAllSeniority(Long empIdL, Long long1)throws Exception
	{
		    Query updatequery=manager.createNativeQuery(UPDATESRNO);
		    updatequery.setParameter("empid", empIdL);
	        updatequery.setParameter("srno", long1);  	 
	        return updatequery.executeUpdate();
	}
	
	private static final String VALIDATESRNO="select SrNo, EmpId,empname FROM employee where empId=:empId";
	@Override
	public Object[] Getemp(Long empId)throws Exception
	{
		 Query query=manager.createNativeQuery(VALIDATESRNO);
		 query.setParameter("empId", empId);
		    List<Object[]> listSeni=(List<Object[]>)query.getResultList();
		   
		    Object[] result=null;
		    if(listSeni!=null && listSeni.size()>0) {
		    	result=listSeni.get(0);
		    }
		    
		    return result;
	}
	
	@Override
	public EmpFamilyDetails getFamilyMemberModal(String familydetailsid) throws Exception
	{
		try {
			return manager.find(EmpFamilyDetails.class, Long.parseLong(familydetailsid));			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO getFamilyMemberModal "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final  String GETFAMILYDETAILSFWD="SELECT fd.family_details_id, fd.member_name, fr.relation_name, fd.dob, fd.med_dep, fd.blood_group, fd.emp_unemp, fd.MemberOccupation,  fd.MemberIncome ,   fd.relation_id,fd.empid, fd.med_dep_from ,ff.FormStatus,ff.familyformid,ff.formtype,   ffm.formmemberid , ffm.Comments , ffm.IncExcDate,ffm.AttachFilePath,fd.isactive,ffm.incexc FROM pis_emp_family_form ff ,pis_fam_form_members ffm ,  pis_emp_family_details fd,pis_emp_family_relation fr  WHERE  ffm.isactive=1 AND ff.FamilyFormId = ffm.FamilyFormId AND ffm.FamilyDetailsId=fd.family_details_id AND fd.relation_id=fr.relation_id AND ff.FamilyFormId = :formid ORDER BY ffm.formmemberid ASC;  "; 
	@Override
	public List<Object[]> GetFormMembersList(String formid) throws Exception 
	{
		try {
			Query query = manager.createNativeQuery(GETFAMILYDETAILSFWD);
			query.setParameter("formid",formid);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
			
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetFormMembersList "+e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	
	private static final String GETEMPLOYEEINFO="Select a.empid,a.empno,a.empname,a.desigid,b.basicpay,b.gender,b.bloodgroup,a.email,b.phoneno,b.paylevelid,b.dob,b.BasicPay,  ed.Designation from employee a, employee_details b,employee_desig ed where a.empno=b.empno AND a.DesigId = ed.DesigId AND a.isactive='1' AND a.empid=:empid ";
	
	@Override
	public  Object[] getEmployeeInfo(String empid) throws Exception
	{
		Query query =manager.createNativeQuery(GETEMPLOYEEINFO);
		Object[] result = null;
		query.setParameter("empid", empid);
		
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmployeeInfo "+e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static final String EMPLOYEERESADDR="SELECT empid,res_addr,mobile,landline FROM pis_address_res WHERE  empid= :empid AND isactive=1 ORDER BY from_res_addr DESC LIMIT 1;";
	
	@Override
	public  Object[] employeeResAddr(String empid) throws Exception
	{
		Query query =manager.createNativeQuery(EMPLOYEERESADDR);
		Object[] result = null;
		query.setParameter("empid", empid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO employeeResAddr "+e);
		}
		return result;
	}
	
//	private static final String UPDATEMEMBERSTATUS = "UPDATE  pis_emp_family_form SET FormStatus = :status ,ForwardedDateTime = :ForwardedDateTime WHERE FamilyFormId = :formid "; 
	@Override
	public long UpdateMemberStatus(PisEmpFamilyForm famform)throws Exception
	{
		try {
			manager.persist(famform);	
			manager.flush();
			
			return famform.getFamilyFormId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO UpdateMemberStatus "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	@Override
	public PisEmpFamilyForm getPisEmpFamilyForm(String familyformid1) throws Exception
	{
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
			logger.error(new Date() + "Inside DAO getPisEmpFamilyForm "+e);
			e.printStackTrace();
		}
		return famform;
	}
	
	
	private static final String FAMMEMFWDEMPLIST="SELECT  fd.familyformid,  e.empid,empname,fd.formstatus,DATE(fd.ForwardedDateTime) AS ForwardedDate, e.empno ,fd.formtype FROM  pis_emp_family_form fd,  employee e WHERE e.empid = fd.empid  AND fd.formstatus IN ('F');";
	@Override
	public List<Object[]> FamMemFwdEmpList() throws Exception 
	{
		try {
			Query query = manager.createNativeQuery(FAMMEMFWDEMPLIST);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO FamMemFwdEmpList "+e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	private static final String FAMMEMAPPROVEDLIST="SELECT fd.familyformid,  e.empid,empname,fd.formstatus,DATE(fd.ForwardedDateTime) AS ForwardedDate, e.empno ,fd.formtype, DATE(fd.ApprovedDateTime) AS 'Approved Date' FROM  pis_emp_family_form fd,  employee e WHERE e.empid = fd.empid  AND fd.formstatus IN ('A') ORDER BY fd.ApprovedDateTime DESC;";
	@Override
	public List<Object[]> FamMemApprovedList() throws Exception 
	{
		try {
			Query query = manager.createNativeQuery(FAMMEMAPPROVEDLIST);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO FamMemApprovedList "+e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}

	
	private static final String FAMILYRELATIONLIST="SELECT relation_id, relation_name,Gender FROM pis_emp_family_relation WHERE isactive=1 AND relation_name NOT LIKE 'self' ORDER BY SerialNo ASC";
	@Override
	public List<Object[]> familyRelationList()throws Exception
	{
		Query query = manager.createNativeQuery(FAMILYRELATIONLIST);
		List<Object[]> List=(List<Object[]>) query.getResultList();
		return  List;
	}
	
	
	private static final String RELATIONSHIPDATA="SELECT relation_id, relation_name,Gender FROM pis_emp_family_relation WHERE relation_id= :relationid ";
	
	@Override
	public  Object[] RelationshipData(String relationid) throws Exception
	{
		Query query =manager.createNativeQuery(RELATIONSHIPDATA);
		Object[] result = null;
		query.setParameter("relationid", relationid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO RelationshipData "+e);
			e.printStackTrace();
		}
		return result;
	}
	
	private static final String GETMEMBERDATA="SELECT   a.family_details_id,  a.member_name,  b.relation_name,  a.dob,  a.med_dep,  a.blood_group,  a.emp_unemp,   a.MemberOccupation,  a.MemberIncome,     ffm.Comments ,  ffm.AttachFilePath,  ffm.IncExcDate,   a.relation_id,a.empid, a.isactive   FROM  pis_emp_family_details a,  pis_emp_family_relation b , pis_fam_form_members ffm WHERE a.relation_id = b.relation_id AND ffm.familydetailsid=a.family_details_id  AND ffm.formmemberid = :formmemberid";
	
	@Override
	public  Object[] getMemberdata(String formmemberid) throws Exception
	{
		Query query =manager.createNativeQuery(GETMEMBERDATA);
		Object[] result = null;
		query.setParameter("formmemberid", formmemberid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO getMemberdata "+e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	private static final String EMPFAMFORMSLIST="SELECT ff.FamilyFormId,ff.Empid,ff.FormType,ff.FormStatus,DATE(ff.ForwardedDateTime) AS 'ForwardedDateTime',ff.ApprovedBy,DATE(ff.ApprovedDateTime) AS 'ApprovedDateTime',e.empname  FROM pis_emp_family_form ff, employee e WHERE ff.isactive=1 AND ff.empid=e.empid AND ff.empid =:empid";
	
	@Override
	public List<Object[]> EmpFamFormsList(String empid,String status) throws Exception
	{
		Query query =manager.createNativeQuery(EMPFAMFORMSLIST);
		query.setParameter("empid", empid);
		List<Object[]> result = new ArrayList<>();
		try {
			result = (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmpFamFormsList "+e);
			e.printStackTrace();
		}
		return result;
	}
	
	private static final String FAMTABLEUPDATE = "UPDATE pis_emp_family_details fd, pis_fam_form_members  ff SET fd.isactive=1 , fd.med_dep=:medDepStatus, fd.modifiedby =:username ,fd.modifieddate=:ModifiedDate   WHERE ff.FamilyDetailsId = fd.family_details_id AND ff.isactive=1 AND ff.incexc=:IncExc AND ff.FamilyFormId=:formid ;";
	@Override
	public int FamilyMemIncConfirm(String formid, String empid,String username,String medDepStatus,String incexc)throws Exception
	{
		try {
		    Query query=manager.createNativeQuery(FAMTABLEUPDATE);
		    query.setParameter("formid", formid);
		    query.setParameter("medDepStatus", medDepStatus);
		    query.setParameter("username", username);
		    query.setParameter("ModifiedDate", DateTimeFormatUtil.getSqlDateAndTimeFormat().format(new Date()));
		    query.setParameter("IncExc", incexc);
	        return query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO FamilyMemIncConfirm "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long EmpFamilyFormAdd(PisEmpFamilyForm form) throws Exception
	{

		try {
			manager.persist(form);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmpFamilyFormAdd "+e);
			e.printStackTrace();
		}
		return form.getFamilyFormId();
	}
	
private static final String GETFAMFORMDATA="SELECT ff.FamilyFormId,ff.Empid,ff.FormType,ff.FormStatus,DATE(ff.ForwardedDateTime) AS 'ForwardedDateTime',ff.ApprovedBy,DATE(ff.ApprovedDateTime) AS 'ApprovedDateTime',e.empname ,e1.empname AS 'approvedemp',e1.designation,ff.remarks FROM  employee e,pis_emp_family_form ff    LEFT JOIN (SELECT e2.empid,e2.empname,e2.desigid,ed.designation FROM employee e2, employee_desig ed WHERE e2.desigid=ed.desigid )e1 ON (ff.approvedby = e1.empid) WHERE ff.isactive=1 AND ff.empid=e.empid  AND ff.FamilyFormId = :familyformid";
	
	@Override
	public  Object[] GetFamFormData(String familyformid) throws Exception
	{
		Query query =manager.createNativeQuery(GETFAMFORMDATA);
		Object[] result = null;
		query.setParameter("familyformid", familyformid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetFamFormData "+e);
		}
		return result;
	}
	
	private static final String FAMILYMEMBERDELETE = "DELETE FROM `pis_fam_form_members` WHERE formmemberid = :formmemberid"; 
	@Override
	public int FormFamilyMemberDelete(String formmemberid)throws Exception
	{
		try {
		    Query query=manager.createNativeQuery(FAMILYMEMBERDELETE);
		    query.setParameter("formmemberid", formmemberid);
	        return query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO FormFamilyMemberDelete "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String FORMFAMILYMEMBERHARDDELETE = "DELETE FROM pis_emp_family_details WHERE family_details_id =:familydetailsid ; "; 
	@Override
	public int FormFamilyMemberHardDelete(String familydetailsid)throws Exception
	{
		try {
		    Query query=manager.createNativeQuery(FORMFAMILYMEMBERHARDDELETE);
		    query.setParameter("familydetailsid", familydetailsid);
	        return query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO FormFamilyMemberHardDelete "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	private static final String  EMPBLOODGROPUEDIT = "UPDATE employee_details SET bloodgroup  =:bloodgroup WHERE empno=:empno";
	
	@Override
	public int EmpBloodGropuEdit(String empno , String bloodgroup)throws Exception {
		try {
			Query query = manager.createNativeQuery(EMPBLOODGROPUEDIT);
			query.setParameter("empno", empno);
			query.setParameter("bloodgroup", bloodgroup);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmpBloodGropuEdit "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long NotificationAdd(EMSNotification notification ) throws Exception
	{
		try {
			manager.persist(notification);
			manager.flush();
			
			return notification.getNotificationId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO NotificationAdd "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String LOGINTYPEEMPDATA="SELECT loginid, empid, username, logintype FROM login WHERE isactive=1 AND  logintype=:logintype ";
	
	@Override
	public List<Object[]> loginTypeEmpData(String logintype) throws Exception
	{
		Query query =manager.createNativeQuery(LOGINTYPEEMPDATA);
		query.setParameter("logintype", logintype);
		List<Object[]> result = new ArrayList<>();
		try {
			result = (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO loginTypeEmpData "+e);
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	private static final String EMPFAMMEMBERSLISTMEDDEP="SELECT fd.family_details_id, fd.empid,fd.member_name,fd.relation_id,fr.relation_name FROM pis_emp_family_details fd, pis_emp_family_relation fr WHERE fd.isactive=1 AND fd.relation_id=fr.relation_id AND med_dep= 'Y' AND fd.empid = :empid AND  fd.family_details_id NOT IN  (SELECT fm.familydetailsid FROM  pis_emp_family_form ff,pis_fam_form_members fm WHERE ff.isactive=1 AND ff.formtype='E' AND fm.isactive=1 AND fm.FamilyFormId = ff.FamilyFormId AND ff.Formstatus IN ('C','F','R') AND ff.empid=6 AND ff.FamilyFormId <> :formid );";
	
	@Override
	public List<Object[]> EmpFamMembersListMedDep(String empid,String formid) throws Exception
	{
		Query query =manager.createNativeQuery(EMPFAMMEMBERSLISTMEDDEP);
		query.setParameter("empid", empid);
		query.setParameter("formid", formid);
		List<Object[]> result = new ArrayList<>();
		try {
			result = (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmpFamMembersListMedDep "+e);
			e.printStackTrace();
		}
		return result;
	}
	
	private static final String EMPFAMMEMBERSNOTMEDDEP="SELECT fd.family_details_id, fd.empid,fd.member_name,fd.relation_id,fr.relation_name,fd.dob,fd.memberoccupation, fd.memberincome FROM pis_emp_family_details fd, pis_emp_family_relation fr WHERE fd.isactive=1 AND fd.relation_id=fr.relation_id AND med_dep= 'N' AND fd.empid = :empid  AND  fd.family_details_id NOT IN  (SELECT fm.familydetailsid FROM  pis_emp_family_form ff,pis_fam_form_members fm WHERE ff.isactive=1 AND ff.formtype='I' AND fm.isactive=1 AND fm.FamilyFormId = ff.FamilyFormId AND ff.Formstatus IN ('C','F','R') AND ff.empid=6 AND ff.FamilyFormId <> :formid );";
	
	@Override
	public List<Object[]> EmpFamMembersNotMedDep(String empid,String formid) throws Exception
	{
		Query query =manager.createNativeQuery(EMPFAMMEMBERSNOTMEDDEP);
		query.setParameter("empid", empid);
		query.setParameter("formid", formid);
		List<Object[]> result = new ArrayList<>();
		try {
			result = (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmpFamMembersNotMedDep "+e);
			e.printStackTrace();
		}
		return result;
	}
	
	private static final  String GETEXCFORMMEMBERSLIST="SELECT fd.family_details_id, fd.member_name, fr.relation_name, fd.dob, fd.med_dep, fd.blood_group, fd.emp_unemp, fd.MemberOccupation,  fd.MemberIncome ,   fd.relation_id,fd.empid, fd.med_dep_from ,ff.FormStatus,ff.familyformid,ff.formtype,   ffm.formmemberid , ffm.Comments , ffm.IncExcDate,ffm.AttachFilePath,ffm.incexc FROM pis_emp_family_form ff ,pis_fam_form_members ffm ,  pis_emp_family_details fd,pis_emp_family_relation fr   WHERE  ffm.isactive=1 AND ff.FamilyFormId = ffm.FamilyFormId AND ffm.FamilyDetailsId=fd.family_details_id AND fd.relation_id=fr.relation_id AND fd.isactive=1 AND ff.FamilyFormId = :formid ORDER BY ffm.formmemberid ASC;  ";
	
	@Override
	public List<Object[]> GetExcFormMembersList(String formid) throws Exception 
	{
		try {
			Query query = manager.createNativeQuery(GETEXCFORMMEMBERSLIST);
			query.setParameter("formid",formid);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
			
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetExcFormMembersList "+e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	private static final String EDUCATIONLIST="SELECT A.qualification_id, A.empid, B.quali_title,C.disci_title,A.university,A.yearofpassing,A.cgpa,A.division,A.specialization,A.sponsored,A.acq_bef_aft FROM pis_qualification A, pis_quali_code B,pis_disci_code C WHERE A.quali_id = B.quali_id AND A.disci_id=C.disci_id AND A.empid=:empid AND A.is_active='1' order by A.qualification_id desc";
	@Override
	public List<Object[]> getEducationList(String empid)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(EDUCATIONLIST);
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEducationList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String QUALIFICATIONLIST="SELECT quali_id,quali_title FROM pis_quali_code WHERE is_active='1'";
	@Override
	public List<Object[]> getQualificationList()throws Exception{
		
		try {
			Query query = manager.createNativeQuery(QUALIFICATIONLIST);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getQualificationList "+e);
			e.printStackTrace();
			return null;
		}
	}
	private static final String DISCILIST="SELECT disci_id,disci_title FROM pis_disci_code WHERE is_active='1'";
	@Override
	public List<Object[]> getDiscipline()throws Exception{
		try {
			Query query = manager.createNativeQuery(DISCILIST);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getDiscipline "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Qualification getQualificationDetails(int qualificationid)throws Exception
	{
		Qualification memeber = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<Qualification> cq = cb.createQuery(Qualification.class);
			Root<Qualification> root = cq.from(Qualification.class);
			Predicate p1 = cb.equal(root.get("qualification_id"), qualificationid);
			cq = cq.select(root).where(p1);
			TypedQuery<Qualification> allquery = manager.createQuery(cq);
			memeber = allquery.getResultList().get(0);
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getQualificationDetails "+e);
			e.printStackTrace();
		}
		return memeber;
	}
	private static final String DELETEQUALIFICATION="  UPDATE  pis_qualification SET is_active=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE qualification_id=:qualificationid";
	@Override
	public int DeleteQualification(String qualificationid,String Username)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(DELETEQUALIFICATION);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("qualificationid",qualificationid );
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteQualification "+e);
			e.printStackTrace();
			return 0;
		}
	}
	

	@Override
	public int AddQualification(Qualification Details) throws Exception {
	
		try {
			manager.persist(Details);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddQualification "+e);
			e.printStackTrace();
		}
		return Details.getQualification_id();
	}
	
	@Override
	public int EditQualification(Qualification Details) throws Exception {
	
		try {
			manager.merge(Details);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditQualification "+e);
			e.printStackTrace();
		}
		return Details.getQualification_id();
	}
	
	private static final String APPOINTMENTLIST="SELECT A.appointment_id,A.empid,A.org_lab,A.drdo_others,A.mode_recruitment,B.mode_recruitment_title,A.desig_id,C.Designation,A.from_date,A.to_date FROM pis_appointments A, pis_mode_recruitment_code B, employee_desig C WHERE A.mode_recruitment=B.recruitment_id AND A.desig_id=C.DesigId AND A.empid=:empid AND A.is_active='1' ORDER BY A.appointment_id desc";
	@Override
	public List<Object[]> getAppointmentList(String empid)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(APPOINTMENTLIST);
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getAppointmentList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String RECRUITMENTLIST="SELECT recruitment_id,mode_recruitment_title FROM pis_mode_recruitment_code";
	@Override
	public List<Object[]> getRecruitment()throws Exception{
		
		try {
			Query query = manager.createNativeQuery(RECRUITMENTLIST);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getRecruitment "+e);
			e.printStackTrace();
			return null;
		}
	}
	private static final String PisAwardsList="SELECT  DesigId,Designation  FROM employee_desig";
	@Override
	public List<Object[]> getDesignationList()throws Exception{
		
		try {
			Query query = manager.createNativeQuery(DESIGNATIONLIST);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getDesignationList "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String DELETEAPPOINTMENT="UPDATE  pis_appointments SET is_active=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE appointment_id=:appointmentid";
	@Override
	public int DeleteAppointment(String appointmentid,String Username)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(DELETEAPPOINTMENT);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("appointmentid",appointmentid );
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteAppointment "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public Appointments getAppointmentsDetails(int appointmentsid)throws Exception
	{
		Appointments list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<Appointments> cq = cb.createQuery(Appointments.class);
			Root<Appointments> root = cq.from(Appointments.class);
			Predicate p1 = cb.equal(root.get("appointment_id"), appointmentsid);
			cq = cq.select(root).where(p1);
			TypedQuery<Appointments> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getAppointmentsDetails "+e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public int AddAppointment(Appointments app)throws Exception{
	
		try {
			manager.persist(app);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddAppointment "+e);
			e.printStackTrace();
		}
		return app.getAppointment_id();
	}
	
	@Override
	public int EditAppointment(Appointments app)throws Exception{
	
		try {
			manager.merge(app);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditAppointment "+e);
			e.printStackTrace();
		}
		return app.getAppointment_id();
	}
	
	private static final String AWARDSLIST="SELECT a.awards_id,a.empid,b.AwardName,a.award_by,a.details,a.award_date,a.certificate,CASE WHEN a.certificate='Y' THEN 'YES' ELSE 'NO' END,a.citation,CASE WHEN a.citation='Y' THEN 'YES' ELSE 'NO' END,a.medallion,CASE WHEN medallion='Y' THEN 'YES' ELSE 'NO' END,a.award_cat,a.cash ,CASE WHEN a.cash='Y' THEN 'YES' ELSE 'NO' END,a.cash_amt  FROM pis_awards a ,pis_award_list b  WHERE a.empid=:empid AND a.AwardListId=b.AwardListId  AND a.is_active='1' ";
	@Override
	public List<Object[]> getAwardsList(String empid)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(AWARDSLIST);
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getAwardsList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String DESIGNATIONLIST="SELECT AwardListId,AwardName FROM pis_award_list";
	@Override
	public List<Object[]> getPisAwardsList()throws Exception{
		
		try {
			Query query = manager.createNativeQuery(DESIGNATIONLIST);	
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPisAwardsList "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String DELETEAWARDS="UPDATE  pis_awards SET is_active=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE awards_id=:awardsid";
	@Override
	public int DeleteAwards(String awardsid,String Username)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(DELETEAWARDS);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("awardsid",awardsid );
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteAwards "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public Awards getAwardsDetails(int awardsid)throws Exception
	{
		Awards list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<Awards> cq = cb.createQuery(Awards.class);
			Root<Awards> root = cq.from(Awards.class);
			Predicate p1 = cb.equal(root.get("awards_id"), awardsid);
			cq = cq.select(root).where(p1);
			TypedQuery<Awards> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getAwardsDetails "+e);
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int AddAwards(Awards app)throws Exception{
	
		try {
			manager.persist(app);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddAwards "+e);
			e.printStackTrace();
		}
		return app.getAwards_id();
	}
	
	@Override
	public int EditAwards(Awards app)throws Exception{
	
		try {
			manager.merge(app);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditAwards "+e);
			e.printStackTrace();
		}
		return app.getAwards_id();
	}
	
	private static final String PROPERTYLIST="SELECT a.empid ,a.property_id, a.movable, a.value,a.details,a.dop,a.acquired_type,a.noting_on , a.remarks FROM  pis_property a WHERE a.empid=:empid AND a.is_active='1'";
	@Override
	public List<Object[]> getPropertyList(String empid)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(PROPERTYLIST);
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPropertyList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Property getPropertyDetails(int propertyid)throws Exception
	{
		Property list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<Property> cq = cb.createQuery(Property.class);
			Root<Property> root = cq.from(Property.class);
			Predicate p1 = cb.equal(root.get("property_id"), propertyid);
			cq = cq.select(root).where(p1);
			TypedQuery<Property> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPropertyDetails "+e);
			e.printStackTrace();
		}
		return list;
	}
	private static final String DELETEPROPERTY="UPDATE  pis_property SET is_active=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE property_id=:propertyid";
	@Override
	public int DeleteProperty(String propertyid,String Username)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(DELETEPROPERTY);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("propertyid",propertyid );
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteProperty "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int AddProperty(Property app)throws Exception
	{
	
		try {
			manager.persist(app);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddProperty "+e);
			e.printStackTrace();
		}
		return app.getProperty_id();
	}
	
	@Override
	public int EditProperty(Property app)throws Exception
	{
	
		try {
			manager.merge(app);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditProperty "+e);
			e.printStackTrace();
		}
		return app.getProperty_id();
	}
	
	private static final String PUBLICATIONLIST="SELECT a.empid,a.publication_id,a.pub_type,a.authors,a.discipline,a.title,a.country,a.pub_date ,a.pub_name_vno_pno,a.patent_no FROM pis_publication  a  WHERE a.empid=:empid AND a.is_active='1' ORDER BY a.publication_id DESC";
	@Override
	public List<Object[]> getPublicationList(String empid)throws Exception
	{
		List<Object[]>list=null;
		try {
			Query query = manager.createNativeQuery(PUBLICATIONLIST);
			query.setParameter("empid", empid);
		      list=(List<Object[]>) query.getResultList();
		      System.out.println("insidedao"+list);
			return list;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPublicationList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	private static final String PISSTATELIST="SELECT a.stateid , a.statename FROM pis_states a";
	@Override
	public List<Object[]> getPisStateList()throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(PISSTATELIST);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPisStateList "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Publication getPublicationDetails(int publicationid)throws Exception
	{
		Publication list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<Publication> cq = cb.createQuery(Publication.class);
			Root<Publication> root = cq.from(Publication.class);
			Predicate p1 = cb.equal(root.get("publication_id"), publicationid);
			cq = cq.select(root).where(p1);
			TypedQuery<Publication> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPublicationDetails "+e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public int AddPublication(Publication app)throws Exception{
	
		try {
			manager.persist(app);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddPublication "+e);
			e.printStackTrace();
		}
		return app.getPublication_id();
	}
	@Override
	public int EditPublication(Publication app)throws Exception
	{
	
		try {
			manager.merge(app);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditPublication "+e);
			e.printStackTrace();
		}
		return app.getPublication_id();
	}
	
	private static final String PASSPORTVISITLIST="SELECT PassportVisitId,EmpId,CountryName,NocLetterNo,NocIssuedFrom,Purpose,visitfromdate,visittodate  FROM pis_passport_visit  WHERE  empid=:empid AND IsActive='1'";
	@Override
	public List<Object[]> getPassportVisitList(String empid)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(PASSPORTVISITLIST);
			query.setParameter("empid", empid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditPublication "+e);
			e.printStackTrace();
			return null;
		}	
	}
	
	
	private static final String PASSPORTLIST="SELECT EmpId,PassportType,ValidFrom,ValidTo,PassportNo,STATUS FROM pis_passport  WHERE empid=:empid ";
	@Override
	public Object[] getPassportList(String empid) throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(PASSPORTLIST);
			query.setParameter("empid", empid);
			List<Object[]> list= (List<Object[]>) query.getResultList();
			Object[] result = null;
			if(list!=null && list.size()>0){
				result=list.get(0);
			}
			return result;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPassportList "+e);
			e.printStackTrace();
			return null;
		}	
	}
	
	
	
	private static final String PASSPORT="FROM Passport WHERE empid=:empid";
	@Override
	public Passport getPassportData(String empid)throws Exception
	{
		Passport passport=null;
		try {
			Query query = manager.createQuery(PASSPORT);
			query.setParameter("empid", empid);
			passport = (Passport) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPassportData "+e);
			e.printStackTrace();
		}		
		return passport;
	}
	
	@Override
	public int AddPassport(Passport passport)throws Exception{
	
		try {
			manager.persist(passport);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddPassport "+e);
			e.printStackTrace();
		}
		return passport.getPassportId();
	}
	
	private static final String EDITPASSPORT="UPDATE  pis_passport SET  status=:status,passportno=:passportno ,validto=:validto, validfrom=:validfrom, passporttype=:passporttype  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE passportid=:passportid";
	@Override
	public int EditPassport(Passport passport)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(EDITPASSPORT);
			
			query.setParameter("modifiedby", passport.getModifiedBy());
			query.setParameter("modifieddate",passport.getModifiedDate());
			query.setParameter("passporttype",passport.getPassportType());
			query.setParameter("validfrom",passport.getValidFrom());
			query.setParameter("validto",passport.getValidTo());
			query.setParameter("passportno",passport.getPassportNo());
			query.setParameter("status",passport.getStatus());
			query.setParameter("passportid",passport.getPassportId());
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditPassport "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public PassportForeignVisit getForeignVisitData(int foreignvisitid)throws Exception
	{
		PassportForeignVisit list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PassportForeignVisit> cq = cb.createQuery(PassportForeignVisit.class);
			Root<PassportForeignVisit> root = cq.from(PassportForeignVisit.class);
			Predicate p1 = cb.equal(root.get("PassportVisitId"), foreignvisitid);
			cq = cq.select(root).where(p1);
			TypedQuery<PassportForeignVisit> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
			
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getForeignVisitData "+e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String DELETEFOREIGNVISIT="UPDATE pis_passport_visit  SET isactive=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE passportvisitid=:passportvisitid";
	@Override
	public int deleteForeignVisit(String foreignvisitid , String Username)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(DELETEFOREIGNVISIT);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("passportvisitid",foreignvisitid);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO deleteForeignVisit "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public int AddForeignVisit(PassportForeignVisit pfv)throws Exception{
	
		try {
			manager.persist(pfv);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddForeignVisit "+e);
			e.printStackTrace();
		}
		return pfv.getPassportVisitId();
	}
	
	@Override
	public int EditForeignVisit(PassportForeignVisit pfv)throws Exception
	{
	
		try {
			manager.merge(pfv);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditForeignVisit "+e);
			e.printStackTrace();
		}
		return pfv.getPassportVisitId();
	}
	
	private static final String DELETEEDUCATIONQUALIFICATION="UPDATE pis_quali_code  SET is_active=:IsActive  , modified_by =:modifiedby , modified_date=:modifieddate  WHERE quali_id=:qualiid";
	@Override
	public int DeleteEducationQualification(String id,String Username)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(DELETEEDUCATIONQUALIFICATION);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("qualiid",id);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteEducationQualification "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String EDITEDUCATIONQUALIFICATION="UPDATE pis_quali_code  SET quali_title=:qualititle , modified_by =:modifiedby , modified_date=:modifieddate  WHERE quali_id=:qualiid";
	@Override
	public int EditEducationQualification(String id,String qualification,String Username)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(EDITEDUCATIONQUALIFICATION);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("qualititle",qualification);
			query.setParameter("qualiid",id);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditEducationQualification "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public int AddEducationQualification(QualificationCode qc)throws Exception
	{
	
		try {
			manager.persist(qc);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddEducationQualification "+e);
			e.printStackTrace();
		}
		return qc.getQuali_id();
	}
	

	private static final String DELETEEDISCIPLINE="UPDATE pis_disci_code  SET is_active=:IsActive  , modified_by =:modifiedby , modified_date=:modifieddate  WHERE disci_id=:id";
	@Override
	public int DeleteDiscipline(String id,String Username)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(DELETEEDISCIPLINE);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("IsActive",0);
			query.setParameter("id",id);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteDiscipline "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String EDITDISCIPLINE="UPDATE pis_disci_code  SET disci_title=:discititle , modified_by =:modifiedby , modified_date=:modifieddate  WHERE disci_id=:id";
	@Override
	public int EditDiscipline(String id,String discipline,String Username)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(EDITDISCIPLINE);
			
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate",sdf.format(new Date()) );
			query.setParameter("discititle",discipline);
			query.setParameter("id",id);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditDiscipline "+e);
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public int AddDiscipline(DisciplineCode dc)throws Exception
	{
	
		try {
			manager.persist(dc);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddDiscipline "+e);
			e.printStackTrace();
		}
		return dc.getDisci_id();
	}
	
	@Override
	public Object[] getEmpData(String EmpNo) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		Object[] empdata=null;
		try {
			Query query = manager.createNativeQuery("CALL mt_employeedata(:empno);");
			query.setParameter("empno", EmpNo);
			list = (List<Object[]>)query.getResultList();
		
			if(list!=null && list.size()>0) {
				empdata=list.get(0);
			}
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO getEmpData "+e);
			e.printStackTrace();
		}
		return empdata;
	}
	
	private static  final String GETEMPSTATUS="select emp_status ,emp_status_name  from emp_status";
	@Override
	public List<Object[]> GetEmpStatusList() throws Exception {
		try {
			Query query = manager.createNativeQuery(GETEMPSTATUS);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetEmpStatusList "+e);
			e.printStackTrace();
			return null;
				
		}
		
	}
	
	private static  final String GETGROUPNAME="SELECT  divisionID,divisionname FROM division_master";
	@Override
	public List<Object[]> getGroupName() throws Exception
	{
		try {
			Query query = manager.createNativeQuery(GETGROUPNAME);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getGroupName "+e);
			e.printStackTrace();
			return null;	
		}
	}
	
	private static  final String GETDESIGNATION="SELECT  DesigId,Designation  FROM employee_desig";
	@Override
	public List<Object[]> getDesignation() throws Exception
	{
		try {
			Query query = manager.createNativeQuery(GETDESIGNATION);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getDesignation "+e);
			e.printStackTrace();
			return null;	
		}
	}

	private static final String ALLEMPLOYEEDETAIL="SELECT a.SrNo, a.empName,b.Designation FROM employee a,employee_desig b,employee_details c WHERE a.empno=c.empno AND  a.DesigId=b.DesigId AND c.EmpStatus='P' AND c.CatId<>'Z' ORDER BY SrNo";
	@Override
	public List<Object[]> fetchAllEmployeeDetail() throws Exception {
		try {
			Query query = manager.createNativeQuery(ALLEMPLOYEEDETAIL);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO fetchAllEmployeeDetail "+e);
			e.printStackTrace();
			return null;
				
		}
		
	}
	
	private static final String EMPLOYEESTATUSWISE ="SELECT a.SrNo, a.empName,b.Designation FROM employee a,employee_desig b ,employee_details c WHERE a.empno=c.empno AND a.DesigId=b.DesigId AND c.EmpStatus=:empStatus AND c.CatId<>'Z'  ORDER BY SrNo";
	@Override
	public List<Object[]> getEmployeeStatusWise(String empstatus) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(EMPLOYEESTATUSWISE);
			query.setParameter("empStatus", empstatus);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmployeeStatusWise "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String EMPLOYEEGROUPORDIVISIONWISE="SELECT a.SrNo, a.empName,b.Designation FROM employee a,employee_desig b,employee_details c WHERE a.empno=c.empno AND a.DesigId=b.DesigId AND a.divisionId=:divisionid AND c.EmpStatus='P' AND c.CatId<>'Z' ORDER BY SrNo";
	@Override
	public List<Object[]> getEmployeeDivOrGroupWise(int id) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(EMPLOYEEGROUPORDIVISIONWISE);
			query.setParameter("divisionid", id);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmployeeDivOrGroupWise "+e);
			e.printStackTrace();
			return null;	
		}	
	}
	
	private static final String EMPLOYEEDESIGNATIONWISE="SELECT a.SrNo, a.empName,b.Designation FROM employee a,employee_desig b,employee_details c WHERE a.empno=c.empno AND a.DesigId=b.desigid AND a.DesigId=:desigid  AND c.EmpStatus='P' AND c.CatId<>'Z' ORDER BY SrNo";
	@Override
	public List<Object[]> getEmployeeDesignationWise(int id) throws Exception
	{
		logger.info(new Date() + "Inside DAO getEmployeeDesignationWise()");
		try {
			Query query = manager.createNativeQuery(EMPLOYEEDESIGNATIONWISE);
			query.setParameter("desigid", id);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmployeeDesignationWise "+e);
			e.printStackTrace();
			return null;	
		}	
	}

	private static final String EMPLOYEEGENDERWISE="SELECT a.SrNo, a.empName,b.Designation FROM employee a,employee_desig b,employee_details c WHERE a.empno=c.empno AND a.DesigId=b.desigid AND c.Gender=:gender AND c.EmpStatus='P' AND c.CatId<>'Z' ORDER BY SrNo";
	@Override
	public List<Object[]> getEmployeeGenderWise(String gender) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(EMPLOYEEGENDERWISE);
			query.setParameter("gender", gender);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmployeeGenderWise "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	private static final String FETCHALLPERSONALDETAIL="SELECT  a.EmpId,a.empName,c.Designation,b.divisionname,a.empno,a.SrNo FROM employee a ,division_master b,employee_desig c ,employee_details d WHERE a.empno=d.empno AND a.divisionid=b.divisionid  AND a.DesigId=c.DesigId AND d.EmpStatus='P' AND d.CatId<>'Z' ORDER BY a.SrNo";
	@Override
	public List<Object[]> fetchAllPersonalDetail() throws Exception
	{
		try {
			Query query = manager.createNativeQuery(FETCHALLPERSONALDETAIL);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO fetchAllPersonalDetail "+e);
			e.printStackTrace();
			return null;		
		}
	}
	@Override
	public List<Object[]> fetchPersonalDetailsNGOorCGO(String cattype) throws Exception
	{
         List<Object[]> PersonalDetailsNGOorCGO=new ArrayList<>();
      try
      {
	        if("N".equalsIgnoreCase(cattype)) { 
	         Query q=manager.createNativeQuery("SELECT  a.EmpId,a.empName,c.Designation,b.divisionname,a.empno  FROM employee a ,division_master b,employee_desig c ,employee_details d WHERE a.empno=d.empno AND a.divisionid=b.divisionid  AND a.DesigId=c.DesigId AND d.EmpStatus='P' AND d.CatId IN('N','p','T') ORDER BY a.SrNo");
	         PersonalDetailsNGOorCGO=(List<Object[]>) q.getResultList();
	        }else {  
	      	   Query q=manager.createNativeQuery("SELECT  a.EmpId,a.empName,c.Designation,b.divisionname,a.empno FROM employee a ,division_master b,employee_desig c ,employee_details d WHERE a.empno=d.empno AND a.divisionid=b.divisionid  AND a.DesigId=c.DesigId AND d.EmpStatus='P' AND d.CatId IN ('C','Q','S') ORDER BY SrNo");
		           PersonalDetailsNGOorCGO=(List<Object[]>) q.getResultList();  
	        }
	        return PersonalDetailsNGOorCGO;
        }catch(Exception e){
        	logger.error(new Date() + "Inside DAO fetchPersonalDetailsNGOorCGO " +e);
    	  e.printStackTrace();
			return null;	                   
		}
		
           
	}
	
	@Override
	public List<Object[]> getConfigurableReportList(String ConfigurableReportQuery)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(ConfigurableReportQuery);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getConfigurableReportList "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	private static final String ALLEMPLOYEELIST="SELECT a.EmpId,a.empName,c.Designation,a.empno FROM employee a, employee_details b, employee_desig c WHERE a.empno=b.empno AND b.EmpStatus='P' AND b.CatId<>'Z' AND a.DesigId=c.DesigId ORDER BY a.SrNo";
	@Override
	public List<Object[]> getAllEmployeeList()throws Exception
	{
		try {
			Query query = manager.createNativeQuery(ALLEMPLOYEELIST);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getAllEmployeeList "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	private static final String DATEREPORT="SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM Employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.DOB)=:years AND d.CatId<>'Z'";
	@Override
	public List<Object[]> getDefaultReport(int year)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(DATEREPORT);
			query.setParameter("years", year);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getDefaultReport "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	@Override
	public List<Object[]> getDobReport(int year, int month)throws Exception
	{
		List<Object[]> dobreports = new ArrayList<>();
		try {
			
			if (year != 0 & month == 0) {
					Query q = manager.createNativeQuery("SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM Employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.DOB)=:years AND d.CatId<>'Z'");
					q.setParameter("years", year);
					dobreports = q.getResultList();
			}else if(year != 0 & month != 0) {
					Query q = manager.createNativeQuery("SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM Employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.DOB)=:years AND MONTH(d.DOB)=:months AND d.CatId<>'Z'");
					q.setParameter("years", year);
					q.setParameter("months", month);
					dobreports = q.getResultList();
			}
			
			return dobreports;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getDobReport "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	@Override
	public List<Object[]> getDoaReport(int year, int month)throws Exception
	{
		List<Object[]> doareports = new ArrayList<>();
		try {
			
			if (year != 0 & month == 0) {
					Query q = manager.createNativeQuery("SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.doa)=:years AND d.CatId<>'Z'");
					q.setParameter("years", year);
					doareports = q.getResultList();
			}else if(year != 0 & month != 0) {
					Query q = manager.createNativeQuery("SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.doa)=:years AND MONTH(d.doa)=:months AND d.CatId<>'Z'");
					q.setParameter("years", year);
					q.setParameter("months", month);
					doareports = q.getResultList();
			}
			
			return doareports;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getDoaReport "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	@Override
	public List<Object[]> getDorReport(int year, int month)throws Exception
	{
		List<Object[]> dorreports = new ArrayList<>();
		try {
			
			if (year != 0 & month == 0) {
					Query q = manager.createNativeQuery("SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM Employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.dor)=:years AND d.CatId<>'Z'");
					q.setParameter("years", year);
					dorreports = q.getResultList();
			}else if(year != 0 & month != 0) {
					Query q = manager.createNativeQuery("SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM Employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.dor)=:years AND MONTH(d.dor)=:months AND d.CatId<>'Z'");
					q.setParameter("years", year);
					q.setParameter("months", month);
					dorreports = q.getResultList();
			}
			
			return dorreports;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getDorReport "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	@Override
	public List<Object[]> getDojReport(int year, int month)throws Exception
	{
		List<Object[]> dojreports = new ArrayList<>();
		try {
			
			if (year != 0 & month == 0) {
					Query q = manager.createNativeQuery("SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM Employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.dojl)=:years AND d.CatId<>'Z'");
					q.setParameter("years", year);
					dojreports = q.getResultList();
			}else if(year != 0 & month != 0) {
					Query q = manager.createNativeQuery("SELECT  d.DOB,d.doa,d.DOR,d.DOJL,a.EmpId,a.empName,c.Designation,b.divisionname,d.PunchCard FROM Employee a ,division_master b, employee_desig c ,employee_details d WHERE a.DivisionId=b.DivisionId  AND a.DesigId=c.DesigId AND a.empno =d.empno AND d.EmpStatus='P' AND YEAR(d.dojl)=:years AND MONTH(d.dojl)=:months AND d.CatId<>'Z'");
					q.setParameter("years", year);
					q.setParameter("months", month);
					dojreports = q.getResultList();
			}
			
			return dojreports;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getDojReport "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	@Override
	public List<Object[]> fetchCadreNameCode()throws Exception
	{
		try {
			Query query = manager.createNativeQuery("SELECT CadreId,Cadre FROM pis_cadre WHERE IsActive='1'");
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO fetchCadreNameCode "+e);
			e.printStackTrace();
			return null;		
		}
	}
	
	@Override
	public List<Object[]> EmployeeList(String cadreid)throws Exception
	{
		try {
			Query query = manager.createNativeQuery("CALL employee_rptStrength(:cadreId);");
			query.setParameter("cadreId",cadreid);
			return (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeList "+e);
			e.printStackTrace();
			return null;		
		}
	}
	private static final String MAXSENIORNO = "SELECT MAX(srno) FROM employee WHERE isactive=1";

	@Override
	public int GetMaxSeniorityNo()throws Exception
	{
				try {
					Query query = manager.createNativeQuery(MAXSENIORNO);
					Object o = query.getSingleResult();
					Integer value =0;
					if(o!=null) {
						value = Integer.parseInt(o.toString());
					} 
					int result = value;
		
					return result;
				} catch (Exception e) {
					logger.error(new Date() + "Inside DAO GetMaxSeniorityNo "+e);
					e.printStackTrace();
					return 0;
				}
	}
	
	
	
	@Override
	public Long AddPropertyDetails(PropertyDetails details) throws Exception 
	{
		manager.persist(details);
		manager.flush();
		return details.getPropertyId();
	}

	private static final String PropertyDetails = "select PropertyId, Description,Address, PropertyValue,PartnerInfo,ModeOfProperty, AnnualIncome,Remarks from pis_property_details where PropertyId=:PropertyId";

	@Override
	public List<Object[]> editPropertyDetails(String propertyId) throws Exception 
	{
		List<Object[]> list = null;

		Query query = manager.createNativeQuery(PropertyDetails);
		query.setParameter("PropertyId", propertyId);
		list = (List<Object[]>) query.getResultList();
		manager.flush();
		return list;
	}

	private static final String UpdatePropertyDetails = "update pis_property_details set Description=:Description,Address=:Address,PropertyValue=:PropertyValue,PartnerInfo=:PartnerInfo,ModeOfProperty=:ModeOfProperty,AnnualIncome=:AnnualIncome,Remarks=:Remarks where PropertyId=:propertyId";

	@Override
	public Long updatePropertyDetails(PropertyDetails details, String propertyId) throws Exception 
	{
		Long result = null;
		try {
			Query query = manager.createNativeQuery(UpdatePropertyDetails);
			query.setParameter("Description", details.getDescription());
			query.setParameter("Address", details.getAddress());
			query.setParameter("PropertyValue", details.getPropertyValue());
			query.setParameter("PartnerInfo", details.getPartnerInfo());
			query.setParameter("ModeOfProperty", details.getModeOfProperty());
			query.setParameter("AnnualIncome", details.getAnnualIncome());
			query.setParameter("Remarks", details.getRemarks());
			query.setParameter("propertyId", propertyId);
			result = (long) query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	private static final String LabDetails = "select LabName ,LabAddress,LabCity,LabPin from lab_master";

	@Override
	public List<Object[]> getLabDetails() throws Exception 
	{
		List<Object[]> list = null;
		try {
			Query query = manager.createNativeQuery(LabDetails);
			list = (List<Object[]>) query.getResultList();
			manager.flush();
		} catch (Exception e) {

		}

		return list;
	}

	private static final String EmpDetails = "SELECT emp.empname,emp.empno,emp.ExtNo,ppl.paygrade,ppl.paylevel,dm.divisionname,dm.divisioncode,DATE_FORMAT(det.dojl,'%d-%m-%Y') AS dojl,ed.designation,det.basicpay\n"
			+ "\n" + "\n"
			+ "FROM employee emp, employee_desig ed, division_master dm, employee_details det, pis_pay_level ppl\n"
			+ "WHERE emp.desigid=ed.desigid AND emp.divisionid=dm.divisionid AND emp.empno = det.empno AND det.PayLevelId=ppl.PayLevelId\n"
			+ "AND emp.empid=:EmpId";

	@Override
	public List<Object[]> GetEmpdetails(String empId) throws Exception 
	{
		List<Object[]> list = null;
		try {
			Query query = manager.createNativeQuery(EmpDetails);
			query.setParameter("EmpId", empId);
			list = (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return list;
	}

	private static final String propertiesYearwise = "select PropertyId,PropertyYear, Description,Address,FORMAT(PropertyValue,2,'en_IN') as PropertyValue,PartnerInfo,ModeOfProperty,FORMAT(AnnualIncome,2,'en_IN') as AnnualIncome,Remarks from pis_property_details where EmpId=:EmpId and PropertyYear=:PropertyYear and isactive=1";

	@Override
	public List<Object[]> getPropertiesYearWise(int year, String empId) throws Exception 
	{
		List<Object[]> list = null;
		try {
			Query query = manager.createNativeQuery(propertiesYearwise);
			query.setParameter("PropertyYear", year);
			query.setParameter("EmpId", empId);

			list = (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private static final String DeletePropertyDetails = "update  pis_property_details SET isactive=:IsActive  , modifiedby =:modifiedby , modifieddate=:modifieddate  WHERE PropertyId=:propertyId ";

	@Override
	public int deletePropertyDetails(String propertyId, String Username) throws Exception 
	{
		int result = 0;
		logger.info(new Date() + "Inside DAO deletePropertyDetails()");
		try {
			Query query = manager.createNativeQuery(DeletePropertyDetails);
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate", sdf.format(new Date()));
			query.setParameter("IsActive", 0);
			query.setParameter("propertyId", propertyId);

			result = query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	private static final String FAMILYDETAILSLIST = "SELECT fd.family_details_id, fd.member_name, fd.relation_id, fd.dob, fd.family_status_id, fd.status_from, fd.blood_group, fr.relation_name,fd.gender,fd.med_dep_from, fd.MemberOccupation,fd.MemberIncome FROM pis_emp_family_details fd,  pis_emp_family_relation fr, pis_emp_family_status fs WHERE fd.IsActive = 1 AND fd.relation_id = fr.relation_id   AND fd.family_status_id = fs.family_status_id AND fs.family_status_id IN (1, 2) AND fd.med_dep ='Y' AND empid = :empid ORDER BY fr.SerialNo ASC ";
	
	@Override
	public List<Object[]> familyDetailsList(String empid) throws Exception
	{
		Query query =manager.createNativeQuery(FAMILYDETAILSLIST);
		List<Object[]> resultList = new ArrayList<Object[]>();		
		query.setParameter("empid", empid);
		resultList = (List<Object[]>)query.getResultList();				
		return resultList;
	}
	
	@Override
	public long EmpFamilyDeclarationAdd(PISEmpFamilyDeclaration declare)throws Exception
	{
		manager.persist(declare);
		manager.flush();
		return declare.getFamilyDeclarationId();
	}
	
	private static final String GETEMPFAMILYDECLARATION = "from PISEmpFamilyDeclaration where FamilyFormId =:formid";
	@Override
	public PISEmpFamilyDeclaration getEmpFamilyDeclaration(String formid)throws Exception
	{
		Query query =manager.createQuery(GETEMPFAMILYDECLARATION);
		query.setParameter("formid", Long.parseLong(formid));
		PISEmpFamilyDeclaration declare = (PISEmpFamilyDeclaration)query.getSingleResult();				
		return declare;
	}
}
	
