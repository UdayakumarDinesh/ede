package com.vts.ems.newspaper.dao;

import java.util.List;
import java.util.Map;

import com.vts.ems.master.model.LabMaster;

public interface NewsPaperDao {

	public Object[] getNewspaperContingentBillPrintData(String NewspaperBillId) throws Exception;
	public List<Object[]> getNewspaperReportPrintData(String NewspaperBillId) throws Exception;
	public int UpdateNewsPeriod(String Empid, String NewspaperBillId, String FromDate, String ToDate) throws Exception;
	public Object[] getNewspaperApprovalPeriodEditDetails(String NewspaperBillId) throws Exception;
	public int NewspaperApprove_UpdateNewspaper(Map<String, String> map, String Empid, int MaxNewspaperBillId)	throws Exception;
	public int getMaxNewspaperBillId() throws Exception;
	public int NewspaperApprove_AddNewspaperBill(String Empid, double totalAmount, String FromDate, String ToDate,	Map<String, String> map) throws Exception;
	public List<Object[]> getNewspaperApprovalList() throws Exception;
	public List<Object[]> getNewspaperClaimApprovedList() throws Exception;
	public Object[] getNewspaperUserPrintData(String NewspaperId) throws Exception;
	public int DeleteNewspaperClaim(String NewspaperId) throws Exception;
	public int EditNewspaperClaim(String Empid, double ClaimAmount, String NewspaperId, double PayableAmount) throws Exception;
	public Object[] getNewspaperEditDetails(String NewspaperId) throws Exception;
	public Object[] getCheckNewspaperApproveOrNot(String NewspaperId) throws Exception;
	public Object[] getCheckPeriodOfNewsAlreadyPresentOrNot(String Empid, String ClaimMonth, String ClaimYear)	throws Exception;
	public long AddNewspaperClaim(String Empid, String ClaimMonth, String ClaimYear, double ClaimAmount,double RestrictedAmount, double PayableAmount, String PayLevelId) throws Exception;
	public Object[] getPayLevelAndNewsRectrictAmt(String Empid) throws Exception;
	public List<Object[]> getNewspaperClaimList(String empid) throws Exception;
	public LabMaster getLabDetails() throws Exception;
	
	public List<Object[]> getDeviceList(String empid) throws Exception;
	public List<Object[]> getTeleDeviceList(String empid) throws Exception;
	public int AddTeleUsers(String Empid, String deviceId, String deviceNo) throws Exception;
	public Object[] getTeleDeviceEditDetails(String empid, String TeleUsersId);
	public int UpdateTeleUser(String Empid, String TeleUsersId, String deviceNo);
	public int DeleteTeleUser(String Empid, String TeleUsersId);
	public 	List<Object[]> getTeleClaimList(String Empid);
	public Object[] getPayLevelAndTeleRectrictAmt(String Empid);
	public Object[] getTeleSpecialpermission(String Empid);
	public int addTeleClaim(String Empid, String TotalBasic, String TotalTax, double GrossTotal,double RestrictedAmount, double PayableAmount, String PayLevelId, String ClaimMonth, String ClaimYear,
			String IsBroadBand, String[] TeleFromDate, String[] TeleToDate, String[] TeleUsersId,
			String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount,String[] TotalAmount);
	public 	int getMaxTeleId();
	public 	Object[] getCheckPeriodOfTeleAlreadyPresentOrNot(String Empid, String ClaimMonth, String ClaimYear);
	public List<Object[]> getTeleClaimEditDetails(String TeleId);
	public 	int updateTele(String Empid, String TeleId, String TotalBasic, String TotalTax, double GrossTotal,
			double PayableAmount, String IsBroadBand, String[] TeleDId, String[] TeleFromDate, String[] TeleToDate,
			String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount, String[] TotalAmount,	String UserRemark, int fid);
	public 	int DeleteTelephone(String TeleId, String sessionEmpid);
	public int InsertTeleUserFlagAndGenerateTeleForwardId(String Empid);
	public 	int getMaxTeleForwardId();
	public int UpdateTeleByTeleForwardId(String Empid, String[] TeleIdArray);
	public List<Object[]> getTelephoneSendbackData(String Empid);
	public List<Object[]> getTelephonePrintReportMultiData(String TeleBillId);
	public List<Object[]> getTelephonePrintReportSingleData(String TeleBillId);
	public Object[] getTelephoneContingentBillPrintData(String TeleBillId);
	public int UpdateTelePeriod(String Empid, String TeleBillId, String FromDate, String ToDate);
	public Object[] getTelephoneApprovalPeriodEditDetails(String TeleBillId);
	public Object[] getCheckTeleApproveForwardOrNot(String TeleId);
	public Object[] getCheckTeleApproveOrNot(String TeleId);
	public int TelephoneSendback(String Empid, double FinalAmount, String FromDate, String ToDate, Map<String, String> map);
	public int TelephoneApprove_AddTelephoneBill(String Empid, double FinalAmount, String FromDate, String ToDate,	Map<String, String> map);
	public List<Object[]> getTelephoneApprovalList();
	public List<Object[]> getTelephoneClaimApprovedList();
	public List<Object[]> getTelephoneUserPrintMultiData(String TeleForwardId);
	public List<Object[]> getTelephoneUserPrintSingleData(String TeleForwardId);

}