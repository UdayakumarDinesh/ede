package com.vts.ems.Tour.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
			apply.setDivisionId(dto.getDivisionId());
			apply.setEarliestTime(dto.getEarliestTime());
			apply.setEarliestDate(dto.getEarliestDate());
			apply.setEarliestPlace(dto.getEarliestPlace());
			apply.setCreatedBy(dto.getCreatedBy());
			apply.setCreatedDate(dto.getCreatedDate());
			apply.setIsActive(dto.getIsActive());
			apply.setInitiatedDate(dto.getInitiatedDate());
			apply.setRemarks(dto.getRemarks());
			apply.setTourStatusCode(dto.getTourStatusCode());
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
	@Override
	public String[]	checkTDAlreadyPresentForSameEmpidAndSameDates(String empno,String DepartureDate,String ArrivalDate)throws Exception
	{
		String[] Result=new String[5]; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date checkStartDate = sdf.parse(DepartureDate);
		Date checkEndDate = sdf.parse(ArrivalDate);

		long difference = checkEndDate.getTime() - checkStartDate.getTime();
		long daysdifference = (difference / 86400000);

		
		if (checkStartDate.before(checkEndDate) || checkStartDate.equals(checkEndDate)) {
			if (daysdifference <= 180) {
				
					try {
						String fromdate = DateTimeFormatUtil.RegularToSqlDate(DepartureDate);
						String todate = DateTimeFormatUtil.RegularToSqlDate(ArrivalDate);
					Long checkTDAlreadyPresentForSameEmpidAndSameDate = dao.checkTDAlreadyPresentForSameEmpidAndSameDates(empno,fromdate,todate);
							if (checkTDAlreadyPresentForSameEmpidAndSameDate >0) {
								Result[0]="You Can Apply";
								Result[1]="Pass";
								return Result;
							} else {
								Result[0]="TD Already Present For Same Period";
								Result[1]="Fail";
								Result[2]=String.valueOf(checkTDAlreadyPresentForSameEmpidAndSameDate);
								return Result;
							}
					} catch (Exception e) {
						Result[0]=" Please Enter Advance Amount Correctly";
						Result[1]="Fail";
						return Result;
					}
			} else {
				Result[0]="You Cannot Take Tour Days More Than 180 Days";
				Result[1]="Fail";
				return Result;
			}
		} else {
			Result[0]="Arrival Date Should Not Be Less Than Depature Date!";
			Result[1]="Fail";
			return Result;
		}
	}
	@Override
	public TourApply getTourApplyData(Long tourid)throws Exception
	{
		return dao.getTourApplyData(tourid);
	}
	@Override
	public List<TourOnwardReturn> getTourOnwardReturnData(Long tourid)throws Exception
	{
		return dao.getTourOnwardReturnData(tourid);
	}
	@Override
	public Long EditTourApply(TourApplyDto dto)throws Exception
	{
		TourApply apply = dao.getTourApplyData(dto.getTourApplyId());
		apply.setStayFrom(dto.getStayFrom());
		apply.setStayTo(dto.getStayTo());
		apply.setStayPlace(dto.getStayPlace());
		apply.setPurpose(dto.getPurpose());
		apply.setAirTravJust(dto.getAirTravJust());
		apply.setAdvancePropsed(dto.getAdvancePropsed());
		apply.setEarliestTime(dto.getEarliestTime());
		apply.setEarliestDate(dto.getEarliestDate());
		apply.setEarliestPlace(dto.getEarliestPlace());
		apply.setModifiedBy(dto.getModifiedBy());
		apply.setModifiedDate(dto.getModifiedDate());
		apply.setInitiatedDate(sdtf.format(new Date()));
		apply.setRemarks(dto.getRemarks());
		Long tourapplyid = dao.UpdateTourApply(apply); 
		
		int details = dao.DeleteOnwardReturnData(dto.getTourApplyId());
		
		if(details>0) {
			for(int i=0;i<dto.getTourDates().length;i++) {
				TourOnwardReturn tourdetails = new TourOnwardReturn(); 
					tourdetails.setTourApplyId(tourapplyid);
					tourdetails.setTourDate(DateTimeFormatUtil.dateConversionSql(dto.getTourDates()[i]));
					tourdetails.setTourTime(dto.getTourTimes()[i]);
					tourdetails.setModeId(Long.parseLong(dto.getModeofTravel()[i]));
					tourdetails.setFromCityId(Long.parseLong(dto.getFromCity()[i]));
					tourdetails.setToCityId(Long.parseLong(dto.getToCity()[i]));
				dao.AddTourOnwardReturn(tourdetails);
			}
		}
		return tourapplyid;
	}

	@Override
	public int ForwardTour(String tourapplyid , String empno)throws Exception
	{
		int result = dao.ForwardTour(tourapplyid , empno );
		
		TourTransaction transaction = new TourTransaction();
		transaction.setActionBy(empno);
		transaction.setActionDate(sdtf.format(new Date()));
		transaction.setTourApplyId(Long.parseLong(tourapplyid));
		transaction.setTourStatusCode("FWD");
	
		dao.AddTourTransaction(transaction);
		
		return result;
	}
	
}
