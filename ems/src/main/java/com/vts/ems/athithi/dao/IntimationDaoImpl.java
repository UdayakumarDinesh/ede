package com.vts.ems.athithi.dao;

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
    @Transactional
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
	public List<Object[]> getItimationList(String groupId)throws Exception{
		
		Query query =entityManager.createNativeQuery("CALL vp_intimation(:GROUPID)");
        query.setParameter("GROUPID", groupId);
		List<Object[]> itimationList=(List<Object[]>)query.getResultList();
				
		return itimationList;
	}
}
