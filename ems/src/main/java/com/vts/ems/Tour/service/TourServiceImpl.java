package com.vts.ems.Tour.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.Tour.dao.TourDao;
import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.model.TourTransaction;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class TourServiceImpl implements TourService {

	@Autowired
	private TourDao dao;
	
	private static final Logger logger = LogManager.getLogger(TourServiceImpl.class);

	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@Override
	public List<Object[]> GetEmployeeList()throws Exception
	{
		return dao.GetEmployeeList();
	}

	@Override
	public List<Object[]> GetModeofTravel() throws Exception {
		
		return dao.GetModeofTravel();
	}

	@Override
	public List<Object[]> GetCityList() throws Exception {
		
		return dao.GetCityList();
	}
	@Override
	public Long AddTourApply(TourApplyDto dto)throws Exception
	{
		TourApply apply = new TourApply();
			apply.setStayFrom(dto.getStayFrom());
			apply.setStayTo(dto.getStayTo());
			apply.setStayPlace(dto.getStayPlace());
			apply.setPurpose(dto.getPurpose());
			apply.setAirTravJust(dto.getAirTravJust());
			apply.setAdvancePropsed(dto.getAdvancePropsed());
			apply.setEmpNo(dto.getEmpNo());
			apply.setEarliestTime(dto.getEarliestTime());
			apply.setEarliestDate(dto.getEarliestDate());
			apply.setEarliestPlace(dto.getEarliestPlace());
			apply.setApplyEmpNo(dto.getApplyEmpNo());
			apply.setCreatedBy(dto.getCreatedBy());
			apply.setCreatedDate(dto.getCreatedDate());
			apply.setIsActive(dto.getIsActive());
			apply.setInitiatedDate(dto.getInitiatedDate());
			apply.setRemarks(dto.getRemarks());
		Long TourApplyId = dao.AddTourapply(apply);
		
		
		for(int i=0;i<dto.getTourDates().length;i++) {
			TourOnwardReturn tourdetails = new TourOnwardReturn(); 
				tourdetails.setTourApplyId(TourApplyId);
				tourdetails.setTourDate(DateTimeFormatUtil.dateConversionSql(dto.getTourDates()[i]));
				tourdetails.setTourTime(dto.getTourTimes()[i]);
				tourdetails.setModeId(Long.parseLong(dto.getModeofTravel()[i]));
				tourdetails.setFromCityId(Long.parseLong(dto.getFromCity()[i]));
				tourdetails.setToCityId(Long.parseLong(dto.getToCity()[i]));
			dao.AddTourOnwardReturn(tourdetails);
		}
		
		// Tour Transaction 
		TourTransaction transaction = new TourTransaction();
			transaction.setActionBy(dto.getApplyEmpNo());
			transaction.setActionDate(dto.getInitiatedDate());
			transaction.setTourApplyId(TourApplyId);
			transaction.setTourStatusCode("INI");
			transaction.setTourRemarks(dto.getRemarks());
		
		dao.AddTourTransaction(transaction);
		
		return TourApplyId;
	}

	@Override
	public List<Object[]> GetTourApplyList()throws Exception
	{
		 return dao.GetTourApplyList();
	}

	
}
