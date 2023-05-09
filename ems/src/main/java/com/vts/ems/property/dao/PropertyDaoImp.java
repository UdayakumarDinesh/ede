package com.vts.ems.property.dao;

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

@Transactional
@Repository
public class PropertyDaoImp implements PropertyDao{

    private static final Logger logger = LogManager.getLogger(PIDaoImp.class);
	
	@PersistenceContext
	EntityManager manager;

	private static final String IMMPROPDETAILS = "SELECT a.ImmPropertyId,a.EmpNo,a.Purpose,a.TransState,a.TransDate,a.Mode,a.Location,a.Price,a.ImmStatus,a.Remarks,b.EmpName,c.PISStatus,c.PisStatusColor,c.PISStatusCode FROM pis_immovable_property a,employee b,pis_approval_status c WHERE a.EmpNo=b.EmpNo AND a.PisStatusCode=c.PisStatusCode AND a.IsActive=1 AND a.EmpNo=:EmpNo ORDER BY a.ImmPropertyId DESC";
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
	
}
