package com.vts.ems.property.dao;

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

import com.vts.ems.pi.dao.PIDaoImp;
import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;
import com.vts.ems.property.model.PisMovableProperty;
import com.vts.ems.property.model.PisMovablePropertyTrans;

@Transactional
@Repository
public class PropertyDaoImp implements PropertyDao{

    private static final Logger logger = LogManager.getLogger(PIDaoImp.class);
	
	@PersistenceContext
	EntityManager manager;

	private static final String IMMPROPDETAILS = "SELECT a.ImmPropertyId,a.EmpNo,a.Purpose,a.TransState,a.TransDate,a.Mode,a.Location,a.District,a.State,a.Pincode,a.Price,a.ImmStatus,a.Remarks,b.EmpName,c.PISStatus,c.PisStatusColor,c.PISStatusCode FROM pis_immovable_property a,employee b,pis_approval_status c WHERE a.EmpNo=b.EmpNo AND a.PisStatusCode=c.PisStatusCode AND a.IsActive=1 AND a.EmpNo=:EmpNo ORDER BY a.ImmPropertyId DESC";
	@Override
	public List<Object[]> ImmPropDetails(String EmpNo) throws Exception {
		
		List<Object[]> immpropdata=null;
		try {
			Query query = manager.createNativeQuery(IMMPROPDETAILS);
			query.setParameter("EmpNo",EmpNo);
			immpropdata =(List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() + "Inside DAO ImmPropDetails "+e);
	
		}
		return immpropdata;
	}
	
	@Override
	public PisImmovableProperty getImmovablePropertyById(Long ImmPropertyId) throws Exception {
		
		try {	
			return manager.find(PisImmovableProperty.class, ImmPropertyId);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() + "Inside DAO getImmovablePropertyById "+e);
	        return null;
		}
	}

	@Override
	public Long addImmovableProperty(PisImmovableProperty immovable) throws Exception {
		
		try {
			manager.persist(immovable);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO addImmovableProperty "+e);
			e.printStackTrace();
		}
		return immovable.getImmPropertyId();
	}

	@Override
	public Long editImmovableProperty(PisImmovableProperty immovable) throws Exception {
		try {
			manager.merge(immovable);
			manager.flush();
		}catch (Exception e) {
			logger.info(new Date()+"Inside editImmovableProperty"+e);
			e.printStackTrace();
		}
		return immovable.getImmPropertyId();
	}

	@Override
	public Long addImmovablePropertyTransaction(PisImmovablePropertyTrans transaction) throws Exception {
		
		manager.persist(transaction);
		manager.flush();
		return transaction.getImmTransactionId();
	}

	private static final String IMMPROPTRANSLIST="SELECT tra.ImmTransactionId,emp.empno,emp.empname,des.designation,tra.actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor  FROM pis_immovable_property_trans tra, pis_approval_status sta,employee emp,employee_desig des,pis_immovable_property par WHERE par.ImmPropertyId = tra.ImmPropertyId AND tra.PisStatusCode = sta.PisStatusCode AND tra.actionby=emp.empno AND emp.desigid=des.desigid AND par.ImmPropertyId =:ImmPropertyId ORDER BY actiondate";
	@Override
	public List<Object[]> immmovablePropertyTransList(String immPropertyId) throws Exception {
		
		Query query = manager.createNativeQuery(IMMPROPTRANSLIST);
		query.setParameter("ImmPropertyId",Long.parseLong(immPropertyId));
		return (List<Object[]>)query.getResultList();
	}
	
	private static final String IMMFORMEMPDATA = "SELECT DISTINCT a.EmpNo,a.EmpName,b.Designation,d.PayLevel,c.BasicPay,c.Title FROM employee a,employee_desig b,employee_details c,pis_pay_level d WHERE a.DesigId=b.DesigId AND a.IsActive=1 AND a.EmpNo=c.EmpNo AND d.PayLevelId=c.PayLevelId AND a.EmpNo=:EmpNo LIMIT 1";
	@Override
	public Object[] getEmpNameDesig(String EmpNo) throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(IMMFORMEMPDATA);
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
	
	private static final String IMMPROPTRANSACTIONAPPROVALDATA="SELECT tra.ImmTransactionId,emp.EmpNo,emp.EmpName,des.Designation,MAX(tra.ActionDate) AS ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor,sta.PisStatusCode FROM pis_immovable_property_trans tra,pis_approval_status sta,employee emp,employee_desig des,pis_immovable_property par WHERE par.ImmPropertyId=tra.ImmPropertyId AND tra.PisStatusCode =sta.PisStatusCode AND tra.Actionby=emp.EmpNo AND emp.DesigId=des.DesigId AND sta.PisStatusCode IN ('FWD','VPA','APR') AND par.ImmPropertyId=:ImmPropertyId GROUP BY sta.PisStatusCode ORDER BY ActionDate ASC";
	@Override
	public List<Object[]> immPropTransactionApprovalData(String ImmPropertyId) throws Exception {
		
		Query query = manager.createNativeQuery(IMMPROPTRANSACTIONAPPROVALDATA);
		query.setParameter("ImmPropertyId", Long.parseLong(ImmPropertyId));
		return (List<Object[]>) query.getResultList();
	}

	private static final String PROPERTYAPPROVALSLIST = "CALL Property_Approval(:EmpNo)";
	@Override
	public List<Object[]> propertyApprovalList(String EmpNo) throws Exception {
		
		try {			
			Query query= manager.createNativeQuery(PROPERTYAPPROVALSLIST);
			query.setParameter("EmpNo", EmpNo);
			return  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO propertyApprovalList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}

	private static final String MOVPROPDETAILS = "SELECT a.MovPropertyId,a.EmpNo,a.Purpose,a.TransState,a.TransDate,a.Description,a.MakeAndModel,a.Mode,a.Price,a.MovStatus,a.Remarks,b.EmpName,c.PISStatus,c.PisStatusColor,c.PISStatusCode FROM pis_movable_property a,employee b,pis_approval_status c WHERE a.EmpNo=b.EmpNo AND a.PisStatusCode=c.PisStatusCode AND a.IsActive=1 AND a.EmpNo=:EmpNo ORDER BY a.MovPropertyId DESC";
	@Override
	public List<Object[]> movPropDetails(String empNo) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(MOVPROPDETAILS);
			query.setParameter("EmpNo", empNo);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.info(new Date()+"Inside Inside DAO movPropDetails"+e);
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public PisMovableProperty getMovablePropertyById(Long movPropertyId) throws Exception {
		try {
			return manager.find(PisMovableProperty.class, movPropertyId);
		}catch (Exception e) {
			logger.info(new Date()+"Inside Inside DAO getMovablePropertyById"+e);
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Long addMovableProperty(PisMovableProperty movable) throws Exception {
		
		try {
			manager.persist(movable);
			manager.flush();
			
		}catch (Exception e) {
			logger.info(new Date()+"Inside DAO addMovableProperty"+e);
			e.printStackTrace();
		}
		return movable.getMovPropertyId();
	}

	@Override
	public Long editMovableProperty(PisMovableProperty movable) throws Exception {
		
		try {
			manager.merge(movable);
			manager.flush();
		}catch (Exception e) {
			logger.info(new Date()+"Inside DAO editMovableProperty"+e);
			e.printStackTrace();
		}
		return movable.getMovPropertyId();
	}

	@Override
	public Long addMovablePropertyTransaction(PisMovablePropertyTrans transaction) throws Exception {
		try {
			manager.persist(transaction);
			manager.flush();
		}catch (Exception e) {
			logger.info(new Date()+"Inside DAO addMovablePropertyTransaction"+e);
			e.printStackTrace();
		}
		return transaction.getMovTransactionId();
	}

	private static final String MOVPROPTRANSLIST = "SELECT tra.MovTransactionId,emp.EmpNo,emp.EmpName,des.Designation,tra.ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor FROM pis_movable_property_trans tra,pis_approval_status sta,employee emp,employee_desig des,pis_movable_property par WHERE par.MovPropertyId = tra.MovPropertyId AND tra.PisStatusCode = sta.PisStatusCode AND tra.ActionBy=emp.EmpNo AND emp.DesigId = des.DesigId AND par.MovPropertyId=:MovPropertyId ORDER BY tra.ActionDate";
	@Override
	public List<Object[]> movablePropertyTransList(String movPropertyId) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(MOVPROPTRANSLIST);
			query.setParameter("MovPropertyId", movPropertyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.info(new Date()+"Inside DAO movablePropertyTransList"+e);
			e.printStackTrace();
			return null;
		}
		
	}

	private static final String MOVPROPTRANSAPPROVALDATA = "SELECT tra.MovTransactionId,emp.EmpNo,emp.EmpName,des.Designation,MAX(tra.ActionDate) AS ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor,sta.PisStatusCode FROM pis_movable_property_trans tra,pis_approval_status sta,employee emp,employee_desig des,pis_movable_property par WHERE par.MovPropertyId=tra.MovPropertyId AND tra.PisStatusCode =sta.PisStatusCode AND tra.Actionby=emp.EmpNo AND emp.DesigId=des.DesigId AND sta.PisStatusCode IN ('FWD','VPA','APR') AND par.MovPropertyId=:MovPropertyId GROUP BY sta.PisStatusCode ORDER BY ActionDate ASC";
	@Override
	public List<Object[]> movPropTransactionApprovalData(String movPropertyId) {
		
		try {
			Query query = manager.createNativeQuery(MOVPROPTRANSAPPROVALDATA);
			query.setParameter("MovPropertyId", movPropertyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.info(new Date()+"Inside DAO movPropTransactionApprovalData"+e);
			e.printStackTrace();
			return null;
		}
		
	}
}
