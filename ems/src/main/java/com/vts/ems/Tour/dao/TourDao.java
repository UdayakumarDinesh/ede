package com.vts.ems.Tour.dao;

import java.util.List;

import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.model.TourTransaction;

public interface TourDao {

	public List<Object[]> GetEmployeeList()throws Exception;
	public List<Object[]> GetModeofTravel() throws Exception;
	public List<Object[]> GetCityList() throws Exception;
	public Long AddTourapply(TourApply apply)throws Exception;
	public Long AddTourOnwardReturn(TourOnwardReturn tourdetails)throws Exception;
	public Long AddTourTransaction(TourTransaction transaction)throws Exception;
	public List<Object[]> GetTourApplyList()throws Exception;

}
