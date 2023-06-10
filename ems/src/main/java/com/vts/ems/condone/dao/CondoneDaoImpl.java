package com.vts.ems.condone.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.condone.model.SEIPCondone;
import com.vts.ems.condone.model.SEIPCondoneAttach;
import com.vts.ems.condone.model.SEIPCondoneTransac;
import com.vts.ems.condone.model.SEIPCondoneType;
import com.vts.ems.master.model.LabMaster;

@Transactional
@Repository
public class CondoneDaoImpl {

	private static final Logger logger = LogManager.getLogger(CondoneDaoImpl.class);
	
	@PersistenceContext
	EntityManager manager;
	
	private static final String EmpCondoneList = "SELECT co.CondoneId,co.EmpNo,co.CondoneStatusCode,co.CondoneTypeId,co.Subject,co.CondoneDate,co.ForwardedDate,co.IsAccepted,co.Remark,e.empname,cs.statusDesc,ct.condonetype,ct.description FROM seip_condone co, Employee e,seip_condone_status cs, seip_condone_type ct WHERE co.empno=e.empno AND co.CondoneStatusCode=cs.CondoneStatusCode AND co.CondoneTypeId=ct.CondoneTypeId AND co.EmpNo=:EmpNo";

	public List<Object[]> EmpCondoneList(String EmpNo) throws Exception
	{
		Query query = manager.createNativeQuery(EmpCondoneList);
		query.setParameter("EmpNo", EmpNo);
		List<Object[]> EmpCondoneList =(List<Object[]>) query.getResultList();
		return EmpCondoneList;
	}
	
	
	private static final String EmpCondone = "SELECT co.CondoneId,co.EmpNo,co.CondoneStatusCode,co.CondoneTypeId,co.Subject,co.MainContent,co.CondoneDate,co.ForwardedDate,co.IsAccepted,co.Remark,e.empname,cs.statusDesc,ct.condonetype,ct.description FROM seip_condone co, Employee e,seip_condone_status cs, seip_condone_type ct WHERE co.empno=e.empno AND co.CondoneStatusCode=cs.CondoneStatusCode AND co.CondoneTypeId=ct.CondoneTypeId AND co.CondoneId=:CondoneId";
	public Object[] getCondoneData(String CondoneId) throws Exception
	{
		Query query = manager.createNativeQuery(EmpCondone);
		query.setParameter("CondoneId", CondoneId);
		Object[] EmpCondoneList =(Object[]) query.getSingleResult();
		return EmpCondoneList;
	}
	
	public SEIPCondone getSEIPCondone(String CondoneId) throws Exception
	{
		return manager.find(SEIPCondone.class, Long.parseLong(CondoneId));
	}
	
	private static final String CondoneTypesList = "select * from seip_condone_type ";
	public List<SEIPCondoneType> CondoneTypesList() throws Exception
	{
		Query query = manager.createNativeQuery(CondoneTypesList, SEIPCondoneType.class);
		List<SEIPCondoneType> condoneTypes= (List<SEIPCondoneType>)query.getResultList();
		return condoneTypes;
	}
	
	private static final String GETEMPLOYEEINFO = "SELECT e.empid,e.empno,e.empname,e.desigid,ed.designation,e.divisionid,dm.divisionname FROM employee e, employee_desig ed, division_master dm WHERE e.desigid=ed.desigid AND e.divisionid=dm.divisionid AND e.empno=:Empno ";
	public Object[] getEmployeeInfo(String Empno) throws Exception
	{
		Query query = manager.createNativeQuery(GETEMPLOYEEINFO);
		query.setParameter("Empno", Empno);
		Object[] EmployeeInfo= (Object[])query.getSingleResult();
		return EmployeeInfo;
	}
	
	private static final String GETLABINFO = "SELECT * FROM lab_master ";
	public LabMaster getLabInfo() throws Exception
	{
		Query query = manager.createNativeQuery(GETLABINFO, LabMaster.class);
		LabMaster labMaster= (LabMaster)query.getResultList().get(0);
		return labMaster;
	}
	
	public long CondoneAddSubmit(SEIPCondone condone) throws Exception
	{
		manager.persist(condone);
		return condone.getCondoneId();
	}
	
	public long CondoneEditSubmit(SEIPCondone condone) throws Exception
	{
		manager.persist(condone);
		return condone.getCondoneId();
	}
	
	public long CondoneAttachAddSubmit(SEIPCondoneAttach condone) throws Exception
	{
		manager.persist(condone);
		return condone.getCondoneAttachId();
	}
	
	public long CondoneAttachEditSubmit(SEIPCondoneAttach condone) throws Exception
	{
		manager.persist(condone);
		return condone.getCondoneAttachId();
	}
	
	public SEIPCondoneAttach getCondoneAttach(Long condoneAttachId) throws Exception
	{
		return manager.find(SEIPCondoneAttach.class, condoneAttachId);
	}
	
	private static final String GETCONDONEATTACHLIST = "select * from seip_condone_attach where CondoneId=:condoneId and IsActive=1  ";
	public List<SEIPCondoneAttach> getCondoneAttachList(Long condoneId) throws Exception
	{
		Query query = manager.createNativeQuery(GETCONDONEATTACHLIST, SEIPCondoneAttach.class);
		query.setParameter("condoneId", condoneId);
		List<SEIPCondoneAttach> attachList= (List<SEIPCondoneAttach>)query.getResultList();
		return attachList;
	}
	
	private static final String GETDGMEMPNOS  ="SELECT dgmempno FROM dgm_master WHERE isactive=1";
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
	
	private static final String GETCEOEMPNO  ="SELECT empno FROM lab_master, employee WHERE labauthorityid=empid";
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
	
	public long CondoneTransacSubmit(SEIPCondoneTransac condoneTransac) throws Exception
	{
		manager.persist(condoneTransac);
		return condoneTransac.getCondoneTransacId();
	}
	
}
