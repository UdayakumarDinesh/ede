package com.vts.ems.attendance.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vts.ems.attendance.dto.PunchInfoSQLDto;
import com.vts.ems.attendance.model.AttendancePunchData;



@Repository
@Transactional
public class AttendanceDaoImpl implements AttendanceDao{
	

	@PersistenceContext
    EntityManager manager;
	
	
	@PersistenceContext(unitName = "EntityManagerMSSQL")
    EntityManager SQLMnager;
	
	@Autowired
	AttendancePunchDataRepo attendancepunchdatarepo;
	
	private static final Logger logger=LogManager.getLogger(AttendanceDaoImpl.class);
	
	private static final String  GETPUNCHINFO= "SELECT  UserID,Status ,Row, Half, PDate ,UserName ,ProcessDate ,Punch1 ,Punch1_Date ,Punch1_Time  ,WorkTime ,WorkTime_HHMM ,FirstHalf ,SecondHalf ,OutPunch ,OutPunch_Date ,OutPunch_Time FROM EMSPunchView WHERE ProcessDate=:ProcessDate" ;
	@Override
	public List<PunchInfoSQLDto> getPunchInfo(String Date) throws Exception
	{
		Query  query = SQLMnager.createNativeQuery(GETPUNCHINFO);
		query.setParameter("ProcessDate", Date);
		List<Object[]> punchinfolist= (List<Object[]>)query.getResultList();
		List<PunchInfoSQLDto> PunchDTOList = new ArrayList<>();
		
		punchinfolist.stream().forEach(punchinfo -> 
						{
							PunchInfoSQLDto pinchdto=PunchInfoSQLDto.builder()
													.UserId(punchinfo[0].toString())
													.Status(punchinfo[1].toString())
													.Half(punchinfo[2].toString())
													.PunchDate(punchinfo[6].toString())
													.PunchInTime(punchinfo[7]!=null?punchinfo[7].toString():null)
													.PunchOutTime(punchinfo[14]!=null?punchinfo[14].toString():null)
													.WorkTime(punchinfo[11]!=null?punchinfo[11].toString():null)
													.build();
							PunchDTOList.add(pinchdto);			
						});
		
		return PunchDTOList;
	}
	
	
	private static final String  GETPUNCHINFOALL= "SELECT  UserID,Status ,Row, Half, PDate ,UserName ,ProcessDate ,Punch1 ,Punch1_Date ,Punch1_Time  ,WorkTime ,WorkTime_HHMM ,FirstHalf ,SecondHalf ,OutPunch ,OutPunch_Date ,OutPunch_Time FROM EMSPunchView  WHERE ProcessDate>:ProcessDate ORDER BY ProcessDate ASC";
	@Override
	public List<PunchInfoSQLDto> getPunchInfoAllAfter(String date) throws Exception
	{
		Query  query = SQLMnager.createNativeQuery(GETPUNCHINFOALL);
		query.setParameter("ProcessDate", date);
		List<Object[]> punchinfolist= (List<Object[]>)query.getResultList();
		List<PunchInfoSQLDto> PunchDTOList = new ArrayList<>();
		punchinfolist.stream().forEach(punchinfo -> 
						{
							PunchInfoSQLDto pinchdto=PunchInfoSQLDto.builder()
													.UserId(punchinfo[0].toString())
													.Status(punchinfo[1].toString())
													.Half(punchinfo[2].toString())
													.PunchDate(punchinfo[6].toString())
													.PunchInTime(punchinfo[7]!=null?punchinfo[7].toString():null)
													.PunchOutTime(punchinfo[14]!=null?punchinfo[14].toString():null)
													.WorkTime(punchinfo[11]!=null?punchinfo[11].toString():null)
													.build();
							PunchDTOList.add(pinchdto);			
						});
		
		return PunchDTOList;
	}
	
	
	@Override
	public long insertPunchInfo(List<AttendancePunchData> punchlist) throws Exception
	{
		punchlist = attendancepunchdatarepo.saveAll(punchlist);
		return punchlist.size();
	}
	
	private static final String ATTENDPUNCHINFO = "from AttendancePunchData where AttendanceDate =:AttendanceDate";
	@Override
	public List<AttendancePunchData> AttendPunchInfo(String AttendanceDate) throws Exception
	{
		Query  query = manager.createQuery(ATTENDPUNCHINFO);
		query.setParameter("AttendanceDate", AttendanceDate);
		List<AttendancePunchData> punchinfolist= (List<AttendancePunchData>)query.getResultList();
		return punchinfolist;
	}
	
	private static final String DELETEPUNCHINFO = " DELETE FROM attand_punch_data WHERE AttendanceDate =:attendancepunchdata";
	
	@Override
	public long DeletePunchInfo(String AttendanceDate) throws Exception
	{
		Query  query = manager.createNativeQuery(DELETEPUNCHINFO);
		query.setParameter("attendancepunchdata", AttendanceDate);
		return query.executeUpdate();
	}
	
	private static final String PUNCHLISTSIZE = "SELECT COUNT(AttendPunchId) FROM attand_punch_data";
	
	@Override
	public long PunchListSize() throws Exception
	{
		Query  query = manager.createNativeQuery(PUNCHLISTSIZE);
		BigInteger count= (BigInteger)query.getSingleResult();
		return count.longValue();
	}
	
	private static final String EMPLIST="select e.EmpNo,e.EmpName,e.EmpId from employee e ,employee_details ed where e.EmpNo=ed.EmpNo and ed.EmpStatus='P'and  e.isactive=1"; 
	@Override
	public List<Object[]> EmployeeList() throws Exception 
	{
		List<Object[]> list=null;
		try {
			Query query = manager.createNativeQuery(EMPLIST);
			 list =(List<Object[]>) query.getResultList();
			    manager.flush();
		} catch (Exception e) {
			logger.error(new Date() +" Inside DAO EmployeeList "+ e);
			e.printStackTrace();
		}
		return list;
	}
	
	
	private static final String ATTENDANCEREPORT="select EmpNo ,status,AttendanceDate,PunchInTime,PunchOutTime,time_format(Worktime,'%H:%i') from attand_punch_data where AttendanceDate>=:FromDate and AttendanceDate<=:ToDate and EmpNo=:EmpNo ORDER BY AttendanceDate DESC";  
	@Override
	public List<Object[]> getAttendanceDetails(String empNo, String fromDate, String toDate) throws Exception 
	{
		List<Object[]> list=null;			
		try {
			
			Query query = manager.createNativeQuery(ATTENDANCEREPORT);				
			query.setParameter("FromDate",fromDate);
			query.setParameter("ToDate", toDate);
			query.setParameter("EmpNo", empNo);				
		 list = query.getResultList();				
		} catch (Exception e) {
			logger.error(new Date() +" Inside DAO getAttendanceDetails "+ e);
			e.printStackTrace();
		}
		return list;
	}
	
	private final static String LASTSYNCDATETIME="select max(CreatedDate) from attand_punch_data";
	@Override
	public Object getlastSyncDateTime() throws Exception {
		Query query = manager.createNativeQuery(LASTSYNCDATETIME);
					
		return query.getSingleResult();
	}
}
