package com.vts.ems.attendance.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vts.ems.attendance.dao.AttendanceDaoImpl;
import com.vts.ems.attendance.dto.PunchInfoSQLDto;
import com.vts.ems.attendance.model.AttendancePunchData;
import com.vts.ems.leave.dao.LeaveApplRepo;
import com.vts.ems.utils.DateTimeFormatUtil;



@Service
public class AttendanceServiceImpl implements AttendanceService{
	
	private static final Logger logger = LogManager.getLogger(AttendanceServiceImpl.class);
	DateTimeFormatUtil DFU=new DateTimeFormatUtil();
	
	SimpleDateFormat md_df = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM-dd");
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	@Autowired
	LeaveApplRepo applrepo;
	@Autowired
	private AttendanceDaoImpl dao;
	

	
	@Value("${EMSFilesPath}")
	private String emsfilespath;


	@Override
	public long syncAttendancePunchData() throws Exception 
	{
		long count=0;
		LocalDate yesterday = LocalDate.now().minusDays(1);
		
		List<PunchInfoSQLDto> PunchDataFromSQLServer = null;
		if(dao.PunchListSize()==0) {
			PunchDataFromSQLServer = dao.getPunchInfoAll();
		}else {
			PunchDataFromSQLServer = dao.getPunchInfo(yesterday.toString());
			if(dao.AttendPunchInfo(yesterday.toString()).size()>0) {
				dao.DeletePunchInfo(yesterday.toString());
			}
		}
		
		List<AttendancePunchData> punchlist = new ArrayList<AttendancePunchData>();
		for(PunchInfoSQLDto PunchData :PunchDataFromSQLServer) {
//			if(yesterday.equals(LocalDate.parse(PunchData.getPunchDate()))) {
			AttendancePunchData punch =  AttendancePunchData.builder()
											.AttendanceDate(PunchData.getPunchDate())
											.PunchInTime(PunchData.getPunchInTime())
											.PunchOutTime(PunchData.getPunchOutTime())
											.EmpNo(String.valueOf(Long.parseLong(PunchData.getUserId())))
											.Status(PunchData.getStatus())
											.Half(PunchData.getHalf())
											.WorkTime(PunchData.getWorkTime())
											.CreatedDate(sdtf.format(new Date()))
											.build();
			punchlist.add(punch);
//			count = dao.insertPunchInfo(punch);
//			}
		}
			count = dao.insertPunchInfo(punchlist);
		return count;
	}
	
	
}
