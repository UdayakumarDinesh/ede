package com.vts.ems.Tour.dao;

import java.util.List;

import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourAdvance;
import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.model.TourTransaction;
import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.model.EMSNotification;

public interface TourDao {

	public List<Object[]> GetEmployeeList()throws Exception;
	public List<Object[]> GetModeofTravel() throws Exception;
	public List<Object[]> GetCityList() throws Exception;
	public Long AddTourapply(TourApply apply)throws Exception;
	public Long AddTouradvance(TourAdvance advance)throws Exception;
	public Long AddTourOnwardReturn(TourOnwardReturn tourdetails)throws Exception;
	public Long AddTourTransaction(TourTransaction transaction)throws Exception;
	public List<Object[]> GetTourApplyList(String empno)throws Exception;
	public List<Object[]> GetApplyStatusList(String empno ,  String fromdate , String todate)throws Exception;
	public Long  checkTDAlreadyPresentForSameEmpidAndSameDates(String empid,String DepartureDate,String ArrivalDate);
	public Long checkTourAlreadyPresentForSameEmpidAndSameDates(String tourid, String empid,String DepartureDate,String ArrivalDate)throws Exception;
	public TourApply  getTourApplyData(Long tourid) throws Exception;
	public List<TourOnwardReturn> getTourOnwardReturnData(Long tourid)throws Exception;
	public Long  UpdateTourApply(TourApply apply) throws Exception;
	public int DeleteOnwardReturnData(Long tourid) throws Exception;
	public List<Object[]> GetTourApprovalList(String empno)throws Exception;
	public List<Object[]> GetTourCancelList(String empno)throws Exception;
	public int getTourUpdate(ApprovalDto dto) throws Exception;
	public int TourUpdateFromCEO (ApprovalDto dto , String Tourno)throws Exception;
	public List<Object[]> TourStatusDetails(String tourapplyid)throws Exception;
	public List<Object[]> TourCancelStatusDetails(String tourapplyid)throws Exception;
	public int UpdateTourAppply(TourApplyDto apply)throws Exception;
	public Object[] GetApprovalEmp(String empno)throws Exception;
	public List<Object[]> getFinancialyearWiseTourMONoList(String Financialyear)throws Exception;
	public Long EmpNotificationForTour(EMSNotification notification)throws Exception;
	public int TourUpdateFromPandA (ApprovalDto dto, String remarks )throws Exception;
	public Long RevokeTour(ApprovalDto dto)throws Exception;
	public List<Object[]> GetSanctionList(String empno , String fromdate , String todate)throws Exception;
	public List<Object[]> GetCancelList(String empno)throws Exception;
	public Object[] GetPAFADetails()throws Exception; 
	public int CancelTour(ApprovalDto dto)throws Exception;
	public List<Object[]> GetTourCancelList(String empno ,  String fromdate , String todate) throws Exception ;
	public Object[] GetTourDetails(String tourapplyid) throws Exception;
	public Object[] GetTourAdvanceDetails(String tourapplyid)throws Exception;
	public List<Object[]> getTourOnwardReturnDetails(String tourapplyid)throws Exception;
	public List<Object[]> GetTourApprovedList(String empno , String fromdate , String todate )throws Exception;
	public TourAdvance  GetTourAdvanceData(Long tourid) throws Exception;
	public Long UpdateTourAdvance(TourAdvance advance)throws Exception; 
	public Object[] GetPAAndFA()throws Exception;
	public List<Object[]> GetPAndAList()throws Exception;
	public long UpdateIssueOrder(String tourapplyid , String  issueddate ,String  issueby ,String Username)throws Exception;
	public long UpdateTourAdvanceRelesed(String tourapplyid , String tourAdvance  ,String remarks)throws Exception;
	public List<Object[]> TourCancelStatusDetailsTrack(String tourapplyid)throws Exception;
	public int CancelForwardTour(String tourapplyid , String empno)throws Exception;
	public int ForwardTour(String tourapplyid , String empno)throws Exception;

	


}
