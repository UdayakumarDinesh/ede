package com.vts.ems.attendance.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vts.ems.attendance.dto.PunchInfoSQLDto;
import com.vts.ems.attendance.model.AttendancePunchData;



@Repository
@Transactional
public class AttendanceDaoImpl implements AttendanceDao{
	
	private static final Logger logger = LogManager.getLogger(AttendanceDaoImpl.class);

	@PersistenceContext
    EntityManager manager;
	
	
	@PersistenceContext(unitName = "EntityManagerMSSQL")
    EntityManager SQLMnager;
	
	@Override
	public List<PunchInfoSQLDto> getPunchInfo(String Date) throws Exception
	{
		Query  query = SQLMnager.createNativeQuery("SELECT  UserID,Status ,Row, Half, PDate ,UserName ,ProcessDate ,Punch1 ,Punch1_Date ,Punch1_Time   ,WorkTime ,WorkTime_HHMM ,FirstHalf ,SecondHalf ,OutPunch ,OutPunch_Date ,OutPunch_Time FROM EMSPunchView;");
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
	public long insertPunchInfo(AttendancePunchData punch) throws Exception
	{
		manager.persist(punch);
		return punch.getAttendPunchId();
	}
	
}
