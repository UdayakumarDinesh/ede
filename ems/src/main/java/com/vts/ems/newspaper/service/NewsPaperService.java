package com.vts.ems.newspaper.service;

import java.util.List;
import java.util.Map;

import com.vts.ems.master.model.LabMaster;
import com.vts.ems.newspaper.model.Newspaper;
import com.vts.ems.newspaper.model.NewspaperContingent;
import com.vts.ems.pis.model.Employee;

public interface NewsPaperService {

	public long AddNewspaperClaim(String Empid, String ClaimMonth, String ClaimYear, String ClaimAmount, String RestrictedAmount, String PayLevelId, String username)  throws Exception;
	public int EditNewspaperClaim(String Empid, String ClaimAmount, String NewspaperId, String RestrictedAmount)throws Exception;
	public int NewsApproval(Map<String, String> map, String FromDate, String ToDate, String Empid) throws Exception;
	public List<Object[]> getNewspaperClaimList(String empno) throws Exception;
	public List<Object[]> getNewspaperClaimApprovedList() throws Exception;
	public List<Object[]> getNewspaperApprovalList() throws Exception;
	public Object[] getPayLevelAndNewsRectrictAmt(String empno) throws Exception;
	public Object[] getCheckPeriodOfNewsAlreadyPresentOrNot(String empno, String ClaimMonth, String ClaimYear)	throws Exception;
	public Object[] getCheckNewspaperApproveOrNot(String NewspaperId) throws Exception;
	public Object[] getNewspaperEditDetails(String NewspaperId) throws Exception;
	public Object[] getNewspaperUserPrintData(String NewspaperId) throws Exception;
	public Object[] getNewspaperApprovalPeriodEditDetails(String NewspaperBillId) throws Exception;
	public int UpdateNewsPeriod(String empno, String NewspaperBillId, String FromDate, String ToDate) throws Exception;
	public List<Object[]> getNewspaperReportPrintData(String NewspaperBillId) throws Exception;
	public Object[] getNewspaperContingentBillPrintData(String NewspaperBillId) throws Exception;
	public LabMaster getLabDetails() throws Exception;
	public List<Object[]> NewspaperAllApprovedOrNot(String claimMonth, String claimYear) throws Exception;
	public List<String> GetEmpDGMEmpNo() throws Exception;
	public long editNewspaper(Newspaper newspaper) throws Exception;
	public List<Object[]> newspapersByContingentBill(Long ContingentId)throws Exception;
	public long forwardNewspaper(long newspaperApplyId, String empNo, String remarks, String userName, String Action) throws Exception;
	public List<Object[]> findDGMNewspaperList(String empNo, String LoginType) throws Exception;
	public Object[] empOnLogintype(String Logintype) throws Exception;
	public List<Object[]> findPONewspaperList() throws Exception;
	public List<Object[]> findAONewspaperList() throws Exception;
	public String GetDGMEmpNo(String empno) throws Exception;
	public Employee findEmpByEmpNo(String empNo) throws Exception ;
	public List<Object[]> findApprovedNewspaperList(String toDate) throws Exception;
	public long ContingentGenerate(String[] CHSSApplyId, String Username, String logintype,String EmpId,String genTilldate ) throws Exception;
	public List<Object[]> getNewspaperContingentList(String logintype,String fromdate,String todate) throws Exception;
	public List<Object[]> NewspaperContingentClaimList(String contingentid) throws Exception;
	public List<Object[]> newspaperTransaById(long NewspaperId) throws Exception;
	public Object[] newspaperContingentData(String contingentid) throws Exception;
	public long ContingentClaimDrop(String[] newspaperids,String Username) throws Exception ;
	public long NewspaperContingentDelete(String contingentid,String Username) throws Exception;
	public NewspaperContingent findNewspaperContingent(String ContingentId) throws Exception ;
	public long newspaperClaimsApprove(String contingentid,String Username, String action, String remarks,String LoginType,String EmpNo, Long EmpId ) throws Exception;
	public List<Object[]> newspaperContningentApprTransById(Long ContingentId)throws Exception;
	public List<Object[]> newsPaperRemarksHistory(String NewspaperId) throws Exception;
	
	
	
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
	
	public List<Object[]> getTelephoneUserPrintSingleDataByMonth(String TeleForwardId,String teleId);
	
	public int AddTelephoneClaim(String sessionEmpid, String TotalBasic, String TotalTax, String GrossTotal,
			String RestrictedAmount, String[] TeleFromDate, String[] TeleToDate, String[] TeleUsersId,
			String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount, String[] TotalAmount,
			String PayLevelId, String ClaimMonth, String ClaimYear, String IsBroadBand);
	
	public int EditTelephoneResult(String sessionEmpid, String TeleId, String TotalBasic, String TotalTax,
			String GrossTotal, String RestrictedAmount, String[] TeleDId, String[] TeleFromDate, String[] TeleToDate,
			String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount, String[] TotalAmount,
			String IsBroadBand, String UserRemark, String id);
	public int TeleApproval(Map<String, String> map, String FromDate, String ToDate, String Empid);
	public int TeleSendback(Map<String, String> map, String FromDate, String ToDate, String Empid);
	public List<Object[]> TelePhoneAllApprovedOrNot(String claimMonth, String claimYear)throws Exception;
	
}
