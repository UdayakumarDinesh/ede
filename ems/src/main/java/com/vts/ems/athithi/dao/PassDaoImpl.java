package com.vts.ems.athithi.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vts.ems.athithi.model.Pass;
import com.vts.ems.athithi.model.PassEmp;
@Repository
@Transactional
public class PassDaoImpl implements PassDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public List<Object[]> pendingIntimations(String LoginType) throws Exception {
		Query query =entityManager.createNativeQuery("CALL vp_pending_intimations(:LoginType)");
		query.setParameter("LoginType", LoginType);
		List<Object[]> panddingItim=(List<Object[]>)query.getResultList();
		return panddingItim;
	}
	
	private static final String intimationDetail="SELECT i.IntimationId, i.IntimationDate, i.FromDate, i.Todate, i.Duration, i.Purpose, i.SpecialPermission, c.CompanyName, e.EmpName, d.Designation FROM vp_intimation i, vp_company c, employee e, employee_desig d WHERE i.CompanyId=c.CompanyId AND e.EmpId=i.OfficerEmpId AND e.desigid=d.desigid AND i.IntimationId=:INTIMATIONID";
	@Override
	public List<Object[]> intimationDetail(String intimationId) throws Exception{
		Query query=entityManager.createNativeQuery(intimationDetail);
		query.setParameter("INTIMATIONID",intimationId );
		List<Object[]>detail=(List<Object[]>)query.getResultList();
		return detail;
	}
	
	private static final String visitorList="SELECT ce.CompanyEmpId, ce.CompanyEmpName, ce.Designation,ce.MobileNo, ce.EmpPhoto FROM vp_company_emp ce,vp_intimation_emp ie WHERE ie.CompanyEmpId=ce.CompanyEmpId AND ie.InitmationId=:INTIMATIONID AND ie.CompanyEmpId NOT IN (SELECT CompanyEmpId FROM vp_pass_emp WHERE PassDate=CURDATE())";
	public List<Object[]> intimationVisitor(String intimationId) throws Exception{
		Query query=entityManager.createNativeQuery(visitorList);
		query.setParameter("INTIMATIONID",intimationId);
		List<Object[]>detail=(List<Object[]>)query.getResultList();
		return detail;
		
	}
	private static final String updatePhoto="UPDATE vp_company_emp SET EmpPhoto=:PHOTONAME  WHERE CompanyEmpId=:VISITORID";
	@Override
	public int updatePhoto(String visitorId, String photoName)throws Exception{
		Query query=entityManager.createNativeQuery(updatePhoto);
		query.setParameter("PHOTONAME",photoName);
		query.setParameter("VISITORID",visitorId);
	    int result=	query.executeUpdate();	
		return result;
	}
	private static final String passCount=" SELECT COUNT(*) FROM vp_pass WHERE PassDate=CURDATE() ";
	@Override
	public int todayPassCount() throws Exception {
		Query query =entityManager.createNativeQuery(passCount);
        Object n= query.getSingleResult();
        int count=  Integer.parseInt(n.toString());
		return count;
	
	}
	
	@Override
	public Long createPass(Pass pass)throws Exception{
		entityManager.persist(pass);
        entityManager.flush();
        return pass.getPassId();
	}

	@Override
	public Long addVisitor(PassEmp visitor) throws Exception {
		
		entityManager.persist(visitor);
        entityManager.flush();
        return visitor.getPassId();
	}
	
	private static final String  intimationDetails="SELECT i.IntimationDate, i.IntimationNo, p.PassDate, p.PassNo FROM vp_intimation i, vp_pass p WHERE i.IntimationId=:intimationid  AND p.PassId=:passid  ";
	@Override
	public List<Object[]> getIntimationDetails(String intimationId, String passId)throws Exception {
		Query query=entityManager.createNativeQuery("CALL vp_pass_details(:intimationid , :passid)");
		query.setParameter("intimationid",intimationId);
		query.setParameter("passid",passId);
		List<Object[]>detail=(List<Object[]>)query.getResultList();
		return detail;
	}
	
	private static final String visitorList2="SELECT ce.CompanyEmpName, ce.Designation, ce.MobileNo, ce.EmpPhoto FROM vp_company_emp ce, vp_pass_emp pe WHERE  pe.PassId=:passid AND pe.CompanyEmpId=ce.CompanyEmpId";
	@Override
	public List<Object[]> getPassVisitorList(String intimationId, String passId)throws Exception{
		Query query=entityManager.createNativeQuery(visitorList2);
		query.setParameter("passid",passId);
		List<Object[]>detail=(List<Object[]>)query.getResultList();
		return detail;
	}
	
	private static final String udateStatus="UPDATE vp_intimation SET VpStatus='G' WHERE IntimationId=:INTIMATIONID";
	@Override
	public int updateIntimationStatus(String intimationId)throws Exception 
	{
		Query query=entityManager.createNativeQuery(udateStatus);
		query.setParameter("INTIMATIONID",intimationId);

	    int result=	query.executeUpdate();	
		return result;
	}


	@Override
	public List<Object[]> getCreatedPassList() throws Exception {

         Query query=entityManager.createNativeQuery("CALL vp_pass_list()");
		 List<Object[]>detail=(List<Object[]>)query.getResultList();
		 return detail;
	}
    private static final String passVisitorList=" SELECT p.PassEmpId, p.PassId, p.BadgeNo, p.TimeIn, p.TimeOut, ce.CompanyEmpName, ce.Designation FROM vp_pass_emp p , vp_company_emp ce WHERE p.PassId=:PASSID AND p.CompanyEmpId=ce.CompanyEmpId";
	@Override
	public List<Object[]> getPassVisitorList(String passId) throws Exception {
		     Query query=entityManager.createNativeQuery(passVisitorList);
	         query.setParameter("PASSID",passId);
			 List<Object[]>list=(List<Object[]>)query.getResultList();
			 return list;
	}
   private static final String updatePassEmp=" UPDATE vp_pass_emp SET TimeOut=:TIMEOUT WHERE PassEmpId=:PASSEMPID"; 
	@Override
	public int passVisitorSubmit(String passId) throws Exception {
		Query query=entityManager.createNativeQuery(updatePassEmp);
		query.setParameter("TIMEOUT",sdf1.format(new  java.util.Date()));
		query.setParameter("PASSEMPID",passId);
	    int result=	query.executeUpdate();	
		return result;
	}

	@Override
	public List<Object[]> getPassReport(String LoginType,String EmpNo,String fDate, String tDate) throws Exception {

        Query query=entityManager.createNativeQuery("CALL vp_pass_report(:LoginType,:EmpNo,:FROMDATE,:TODATE)");
        query.setParameter("LoginType", LoginType);
        query.setParameter("EmpNo", EmpNo);
        query.setParameter("FROMDATE",fDate);
        query.setParameter("TODATE",tDate);
		 List<Object[]>detail=(List<Object[]>)query.getResultList();
		 return detail;
	}
	
	@Override
	public String OldPassword(String UserId) throws Exception {
		Query query = entityManager.createNativeQuery("select password from login where EmpId=:username");
		query.setParameter("username", UserId);
		
		String OldPassword = (String) query.getSingleResult();
		return   OldPassword;
	}
	
	
	private static final String changepassword="update login set password=:newpassword,ModifiedBy=:modifiedby,ModifiedDate=:modifieddate where EmpId=:employeeid";
	@Override
	public int changepassword(String password,String empid,String Modifieddate)throws Exception{
		Query query = entityManager.createNativeQuery(changepassword);
		query.setParameter("newpassword", password);
		query.setParameter("employeeid", empid);
		query.setParameter("modifiedby", empid);
		query.setParameter("modifieddate", Modifieddate);
		int result = query.executeUpdate();
		return result;
	}
	
	private static final String LABINFO="SELECT LabCode,LabName,LabAddress, LabCity,LabPin FROM lab_master";
	@Override
	public Object[] LabInfo()throws Exception
	{
		Query query = entityManager.createNativeQuery(LABINFO);
		Object[] result =(Object[]) query.getSingleResult();
		return result;
	}
	
}
