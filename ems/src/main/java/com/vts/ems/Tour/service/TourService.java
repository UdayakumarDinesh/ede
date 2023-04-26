package com.vts.ems.Tour.service;

import java.util.List;

import com.vts.ems.Tour.dto.TourApplyDto;

public interface TourService {

	
	public List<Object[]> GetEmployeeList()throws Exception;
	public List<Object[]> GetModeofTravel()throws Exception;
	public List<Object[]> GetCityList() throws Exception;
	public Long AddTourApply(TourApplyDto dto)throws Exception;
	public List<Object[]> GetTourApplyList()throws Exception;
}
