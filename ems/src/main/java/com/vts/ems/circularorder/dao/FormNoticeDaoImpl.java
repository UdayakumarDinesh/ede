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
import com.vts.ems.circularorder.model.EMSNotice;
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
	public long EMSFormEdit(EMSForms form)throws Exception
	{
		manager.merge(form);
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
	
	private static final String  GETEMSNOTICELIST="SELECT NoticeId,ReferenceNo,Description,NoticeDate,ToDate FROM ems_notice WHERE IsActive=1 AND (NoticeDate BETWEEN :FromDate AND :ToDate ) ORDER BY NoticeDate DESC";
	
	@Override
	public List<Object[]> getEmsNoticeList(String FromDate, String ToDate) throws Exception
	{
		Query query =  manager.createNativeQuery(GETEMSNOTICELIST);
		query.setParameter("FromDate", FromDate);
		query.setParameter("ToDate", ToDate);
		List<Object[]> GetEmsDepType=(List<Object[]>)query.getResultList();
		return GetEmsDepType;
	}
	
	@Override
	public long EMSNoticeAdd(EMSNotice Notice)throws Exception
	{
		manager.persist(Notice);
		manager.flush();
		return Notice.getNoticeId();
	}
	
	private static final String MAXOFEMSNOTICEID = "SELECT IFNULL(MAX(NoticeId),0) as 'MAX' FROM ems_notice ";
	@Override
	public long MaxOfEmsNoticeId() throws Exception 
	{
		try {
			Query query =  manager.createNativeQuery(MAXOFEMSNOTICEID);
			BigInteger GetEmsDepType=(BigInteger)query.getSingleResult();
			return GetEmsDepType.longValue();
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside DAO MaxOfEmsNoticeId "+ e);
			return 0;
		}
		
	}
	
	@Override
	public EMSNotice GetEMSNotice(String NoticeId)throws Exception
	{
		try {
			EMSNotice Notice = manager.find(EMSNotice.class, Long.parseLong(NoticeId));
			return Notice;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetEMSNotice() "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public long EMSNoticeEdit(EMSNotice Notice)throws Exception
	{
		manager.merge(Notice);
		manager.flush();
		return Notice.getNoticeId();
	}
	
	
	
	private static final String  GETEMSTODAYNOTICES="SELECT noticeid, referenceno,description,noticedate,todate FROM ems_notice WHERE (CURDATE() BETWEEN noticedate AND todate) AND isactive=1";
	
	@Override
	public List<Object[]> getEmsTodayNotices() throws Exception
	{
		Query query =  manager.createNativeQuery(GETEMSTODAYNOTICES);
		List<Object[]> GetEmsDepType=(List<Object[]>)query.getResultList();
		return GetEmsDepType;
	}
	
}
