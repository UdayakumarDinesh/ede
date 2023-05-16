package com.vts.ems.athithi.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;
import com.vts.ems.athithi.model.Intimation;
import com.vts.ems.athithi.model.IntimationEmp;
import com.vts.ems.athithi.model.VpIntimationTrans;

@Repository
@Transactional
public class IntimationDaoImpl implements IntimationDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	 private static final String uersDetalis="SELECT CompanyId , CompanyName FROM vp_company";
	@Override
	public List<Object[]> getCompnyList(String trem) throws Exception {
		
		Query query =entityManager.createNativeQuery(uersDetalis);
	
		List<Object[]> detalis=(List<Object[]>)query.getResultList();
				
		return detalis;
	
	}
	
	@Override
	@Transactional
	public List<Object[]> addNewCompany(Company company) throws Exception {

        entityManager.persist(company);
        entityManager.flush();

        Query query =entityManager.createNativeQuery(uersDetalis);
		List<Object[]> detalis=(List<Object[]>)query.getResultList();
		
		return detalis;
	}
	private static final String empList="SELECT CompanyEmpId , CompanyEmpName ,CompanyId FROM vp_company_emp WHERE CompanyId=:COMPID AND IsActive='1'";
	@Override
	public List<Object[]> getCompEmp(String companyId)throws Exception{
		Query query =entityManager.createNativeQuery(empList);
		query.setParameter("COMPID", companyId);
		List<Object[]> cmpEmpList=(List<Object[]>)query.getResultList();
				
		return cmpEmpList;
	}
	
	@Override
	public Long addNewEmployee(CompanyEmployee newEmp)throws Exception{
		
		entityManager.persist(newEmp);
        entityManager.flush();
        return newEmp.getCompanyEmpId();
	}

	private static final String officerList="SELECT e.EmpId , e.EmpName, d.Designation, e.SrNo FROM employee e, employee_desig d, employee_details ed WHERE e.desigid=d.desigid AND ed.EmpStatus='P' AND e.empno=ed.empno  AND e.IsActive=1  ORDER BY  e.SrNo ASC";
	@Override
	public List<Object[]> getOfficerList(String groupId)throws Exception{
		Query query =entityManager.createNativeQuery(officerList);
//		query.setParameter("GROUPID", groupId);  
		List<Object[]> cmpEmpList=(List<Object[]>)query.getResultList();
				
		return cmpEmpList;
		
	}

	@Override
	public Long addNewIntimation(Intimation inti) throws Exception 
	{
		entityManager.persist(inti);
        entityManager.flush();
        return inti.getIntimationId();
	}

	@Override
	public void addVisitor(IntimationEmp newVisitor) throws Exception {
		entityManager.persist(newVisitor);
        entityManager.flush();
	}

	private static final String intimationCount=" SELECT COUNT(*) FROM vp_intimation WHERE IntimationDate=CURDATE() ";
	@Override
	public int todayIntionCount() throws Exception {
		Query query =entityManager.createNativeQuery(intimationCount);
        Object n= query.getSingleResult();
        int count=  Integer.parseInt(n.toString());
		return count;
	}
	@Override
	public List<Object[]> getItimationList(String EmpNo)throws Exception{
		
		Query query =entityManager.createNativeQuery("CALL vp_intimation_list(:EmpNo)");
        query.setParameter("EmpNo", EmpNo);
		List<Object[]> itimationList=(List<Object[]>)query.getResultList();
				
		return itimationList;
	}
	
	@Override
	public Long addVpIntimationTrans(VpIntimationTrans transaction) throws Exception{
		try {
			entityManager.persist(transaction);
			entityManager.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return transaction.getVpTransactionId();
	}

	@Override
	public Intimation getIntimationById(Long intimationId) throws Exception {
		
		try {
			return entityManager.find(Intimation.class, intimationId);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long editNewIntimation(Intimation intimation) throws Exception {
		try {
			entityManager.merge(intimation);
			entityManager.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return intimation.getIntimationId();
	}
	
	private static final String GETRECEMPNO  ="SELECT e.EmpNo FROM login l,employee e WHERE l.EmpId=e.EmpId AND l.loginType ='R' LIMIT 1;";
	@Override
	public String GetReceptionistEmpNo() throws Exception
	{
		try {			
			Query query= entityManager.createNativeQuery(GETRECEMPNO);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String VPAPPROVALSLIST = "CALL Vp_Intimation_Approval(:EmpNo)";
	@Override
	public List<Object[]> visitorPassApprovalList(String EmpNo) throws Exception {
		
		try {			
			Query query= entityManager.createNativeQuery(VPAPPROVALSLIST);
			query.setParameter("EmpNo", EmpNo);
			return  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	private static final String VPTRANSLIST="SELECT tra.VpTransactionId,emp.EmpNo,emp.EmpName,des.Designation,tra.ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor FROM vp_intimation_trans tra, pis_approval_status sta,employee emp,employee_desig des,vp_intimation par WHERE par.IntimationId = tra.IntimationId AND tra.PisStatusCode = sta.PisStatusCode AND tra.ActionBy=emp.EmpNo AND emp.DesigId=des.DesigId AND par.IntimationId =:IntimationId ORDER BY tra.ActionDate";
	@Override
	public List<Object[]> vpTransactionList(String IntimationId) throws Exception {
		
		try {
		Query query = entityManager.createNativeQuery(VPTRANSLIST);
		query.setParameter("IntimationId",Long.parseLong(IntimationId));
		return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
