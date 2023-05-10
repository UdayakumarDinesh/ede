package com.vts.ems.Tour.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.leave.dto.ApprovalDto;

public interface TourService {

	
	public List<Object[]> GetEmployeeList()throws Exception;
	public List<Object[]> GetModeofTravel()throws Exception;
	public List<Object[]> GetCityList() throws Exception;
	public Long AddTourApply(TourApplyDto dto)throws Exception;
	public List<Object[]> GetTourApplyList(String empno)throws Exception;
	public List<Object[]> GetApplyStatusList(String empno ,  String fromdate , String todate)throws Exception;
	public String[]	checkTDAlreadyPresentForSameEmpidAndSameDates(String empno,String DepartureDate,String ArrivalDate)throws Exception;
	public TourApply getTourApplyData(Long tourid)throws Exception;
	public List<TourOnwardReturn> getTourOnwardReturnData(Long tourid)throws Exception;
	public Long EditTourApply(TourApplyDto dto)throws Exception;
	public int ForwardTour(String tourapplyid , String empno)throws Exception;
	public List<Object[]> GetTourApprovalList(String empno)throws Exception; 
	public List<Object[]> GetTourCancelList(String empno)throws Exception;
	public int GetDeptInchApproved(ApprovalDto dto ,HttpServletRequest req)throws Exception;
	public int GetCancelApproved(ApprovalDto dto ,HttpServletRequest req)throws Exception;
	public List<Object[]> TourStatusDetails(String tourapplyid)throws Exception;
	public List<Object[]> TourCancelStatusDetails(String tourapplyid)throws Exception;
	public Object[] GetApprovalEmp(String empno)throws Exception;
	public Long RevokeTour(ApprovalDto dto)throws Exception;
	public List<Object[]> GetSanctionList(String empno)throws Exception;
	public int CancelTour(ApprovalDto dto)throws Exception;
	public List<Object[]> GetTourCancelList(String empno ,  String fromdate , String todate)throws Exception;
}
