package com.vts.ems.Tour.service;

import java.util.List;

import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.model.TourOnwardReturn;

public interface TourService {

	
	public List<Object[]> GetEmployeeList()throws Exception;
	public List<Object[]> GetModeofTravel()throws Exception;
	public List<Object[]> GetCityList() throws Exception;
	public Long AddTourApply(TourApplyDto dto)throws Exception;
	public List<Object[]> GetTourApplyList()throws Exception;
	public String[]	checkTDAlreadyPresentForSameEmpidAndSameDates(String empno,String DepartureDate,String ArrivalDate)throws Exception;
	public TourApply getTourApplyData(Long tourid)throws Exception;
	public List<TourOnwardReturn> getTourOnwardReturnData(Long tourid)throws Exception;
	public Long EditTourApply(TourApplyDto dto)throws Exception;
	public int ForwardTour(String tourapplyid , String empno)throws Exception;

}
