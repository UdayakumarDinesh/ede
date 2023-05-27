package com.vts.ems.Tour.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourAdvance;
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
	public String[]	checkTDAlreadyPresentForSameEmpidAndSameDates(String empno,String DepartureDate,String ArrivalDate , String action , String tourid)throws Exception;
	public long checkTourCount(String empno,String DepartureDate,String ArrivalDate)throws Exception;
	public TourApply getTourApplyData(Long tourid)throws Exception;
	public List<TourOnwardReturn> getTourOnwardReturnData(Long tourid)throws Exception;
	public Long EditTourApply(TourApplyDto dto)throws Exception;
	public List<Object[]> GetTourApprovalList(String empno)throws Exception; 
	public List<Object[]> GetTourCancelList(String empno)throws Exception;
	public int GetDeptInchApproved(ApprovalDto dto ,HttpServletRequest req)throws Exception;
	public int GetCancelApproved(ApprovalDto dto ,HttpServletRequest req)throws Exception;
	public List<Object[]> TourStatusDetails(String tourapplyid)throws Exception;
	public List<Object[]> TourCancelStatusDetails(String tourapplyid)throws Exception;
	public Object[] GetApprovalEmp(String empno)throws Exception;
	public Long RevokeTour(ApprovalDto dto)throws Exception;
	public List<Object[]> GetSanctionList(String empno , String from , String todate)throws Exception;
	public List<Object[]> GetCancelList(String empno)throws Exception;
	public int CancelTour(ApprovalDto dto)throws Exception;
	public List<Object[]> GetTourCancelList(String empno ,  String fromdate , String todate)throws Exception;
	public Object[] GetTourDetails(String tourapplyid) throws Exception;
	public Object[] GetTourAdvanceDetails(String tourapplyid)throws Exception;
	public List<Object[]> getTourOnwardReturnDetails(String tourapplyid)throws Exception;
	public Long checkTourAlreadyPresentForSameEmpidAndSameDates(String tourid, String empid,String DepartureDate,String ArrivalDate)throws Exception;
	public Long ModifyTourApply(TourApplyDto dto)throws Exception;
	public List<Object[]> GetTourApprovedList(String empno , String fromdate , String todate )throws Exception;
	public TourAdvance  GetTourAdvanceData(Long tourid) throws Exception;
	public Object[] GetPAAndFA()throws Exception;
	public List<Object[]> GetPAndAList()throws Exception;
	public long UpdateIssueOrder(String tourapplyid , String  issueddate ,String  issueby ,String Username , String remraks)throws Exception;
	public long UpdateTourAdvanceRelesed(String tourapplyid ,String empno, String tourAdvance ,String Username ,String remarks)throws Exception;
	public int CancelForwardTour(String tourapplyid , String empno , String remarks)throws Exception;
	public int ForwardTour(String tourapplyid , String empno , String remarks)throws Exception;
	public List<Object[]> TourCancelStatusDetailsTrack(String tourapplyid)throws Exception;

	
	
}
