package com.vts.ems.circularorder.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vts.ems.circularorder.model.EMSForms;
import com.vts.ems.utils.DateTimeFormatUtil;

@Repository
@Transactional
public class FormNoticeDaoImpl implements FormNoticeDao 
{
	private static final Logger logger = LogManager.getLogger(FormNoticeDaoImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@Autowired
	EntityManager manager;
	
	
	private static final String  GETEMSFORMSLIST="SELECT EMSFormId,DepTypeId,FormNo,Description,FormUploadDate FROM ems_forms WHERE IsActive=1 AND CASE WHEN 'A'=:DepTypeId THEN 1=1 ELSE  DeptypeId = :DepTypeId END ORDER BY FormUploadDate DESC";
	
	@Override
	public List<Object[]> getEmsFormsList(String DepTypeId) throws Exception
	{
		
		Query query =  manager.createNativeQuery(GETEMSFORMSLIST);
		query.setParameter("DepTypeId", DepTypeId);
		List<Object[]> GetEmsDepType=(List<Object[]>)query.getResultList();
		return GetEmsDepType;
		
		
//		CriteriaBuilder cb= manager.getCriteriaBuilder();
//		CriteriaQuery<EMSForms> cq= cb.createQuery(EMSForms.class);
//		Root<EMSForms> root= cq.from(EMSForms.class);				
//		if(!DepTypeId.equalsIgnoreCase("A")) {
//			Predicate p1=cb.equal(root.get("DepTypeId") , Integer.parseInt(DepTypeId));
//			Predicate p2=cb.equal(root.get("IsActive") ,1);
//			cq=cq.select(root).where(p1,p2);
//			cq=cq.select(root).where(p1);
//		}else
//		{
//			Predicate p1=cb.equal(root.get("IsActive") ,1);
//			cq=cq.select(root).where(p1);
//		}
//		
//		TypedQuery<EMSForms> allquery = manager.createQuery(cq);
//		return allquery.getResultList();
	}
	
	
	private static final String GETEMSDEPTYPELIST = "SELECT DepTypeId,DepName,DepShorrtName FROM ems_dep_type WHERE IsActive=1";
	@Override
	public List<Object[]> GetEmsDepTypes() throws Exception 
	{
	
		Query query =  manager.createNativeQuery(GETEMSDEPTYPELIST);
		List<Object[]> GetEmsDepType=(List<Object[]>)query.getResultList();
		return GetEmsDepType;
		
		
	}
	
	private static final String MAXOFEMSFORMID = "SELECT IFNULL(MAX(EmsFormId),0) as 'MAX' FROM ems_forms ";
	@Override
	public long MaxOfEmsFormId() throws Exception 
	{
		try {
			Query query =  manager.createNativeQuery(MAXOFEMSFORMID);
			BigInteger GetEmsDepType=(BigInteger)query.getSingleResult();
			return GetEmsDepType.longValue();
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside DAO MaxOfEmsFormId "+ e);
			return 0;
		}
		
	}
	
	@Override
	public long EMSFormAdd(EMSForms form)throws Exception
	{
		manager.persist(form);
		manager.flush();
		return form.getEMSFormId();
	}
	
	@Override
	public EMSForms GetEMSForm(String formId)throws Exception
	{
		try {
			EMSForms form = manager.find(EMSForms.class, Long.parseLong(formId));
			return form;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetEMSForm() "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String GETFORMNOCOUNT = "SELECT COUNT(formNo) FROM `ems_forms` WHERE isactive=1 AND formno=:formNo";
	@Override
	public long getFormNoCount(String formNo) throws Exception 
	{
		try {
			Query query =  manager.createNativeQuery(GETFORMNOCOUNT);
			query.setParameter("formNo", formNo.trim());
			BigInteger GetEmsDepType=(BigInteger)query.getSingleResult();
			return GetEmsDepType.longValue();
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside DAO MaxOfEmsFormId "+ e);
			return 0;
		}
		
	}
	
	
}
