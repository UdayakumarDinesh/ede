package com.vts.ems.newspaper.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.master.model.LabMaster;
import com.vts.ems.newspaper.dao.NewsPaperDao;

@Service
public class NewPaperServiceImpl implements NewsPaperService {

	private static final Logger logger = LogManager.getLogger(NewPaperServiceImpl.class);
	@Autowired
	private NewsPaperDao dao;

	
	
	@Override
	public List<Object[]> getNewspaperClaimList(String empno)throws Exception
	{
		return dao.getNewspaperClaimList(empno);
	}
	
	@Override
	public long AddNewspaperClaim(String EmpNo, String ClaimMonth, String ClaimYear, String ClaimAmount,	String RestrictedAmount, String PayLevelId) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE AddNewspaperClaim");
		long AddNewspaperClaimResult = 0;
		try {
			double PayableAmount;
			double ClaimAmountInDecimal = Double.parseDouble(ClaimAmount);
			double RestrictedAmountDecimal = Double.parseDouble(RestrictedAmount);

			if (ClaimAmountInDecimal > RestrictedAmountDecimal) {
				PayableAmount = RestrictedAmountDecimal;
			} else {
				PayableAmount = ClaimAmountInDecimal;
			}

			AddNewspaperClaimResult = dao.AddNewspaperClaim(EmpNo, ClaimMonth, ClaimYear, ClaimAmountInDecimal,
					RestrictedAmountDecimal, PayableAmount, PayLevelId);
		} catch (Exception e) {
			logger.error("EXCEPTION OCCUR while CALCULATING PAYABLE AMOUNT OF NEWSPAPER data");

			AddNewspaperClaimResult = 0;
			e.printStackTrace();

		}
		return (AddNewspaperClaimResult);

	}

	@Override
	public int EditNewspaperClaim(String EmpNo, String ClaimAmount, String NewspaperId, String RestrictedAmount)throws Exception 
	{
		int EditNewspaperClaimResult = 0;
		logger.info(new Date() +"Inside SERVICE AddNewspaperClaim");
		try {

			double PayableAmount;
			double ClaimAmountInDecimal = Double.parseDouble(ClaimAmount);
			double RestrictedAmountDecimal = Double.parseDouble(RestrictedAmount);

			if (ClaimAmountInDecimal > RestrictedAmountDecimal) {
				PayableAmount = RestrictedAmountDecimal;
			} else {
				PayableAmount = ClaimAmountInDecimal;
			}

			EditNewspaperClaimResult = dao.EditNewspaperClaim(EmpNo, ClaimAmountInDecimal, NewspaperId, PayableAmount);

		} catch (Exception e) {
			logger.error("EXCEPTION OCCUR while CALCULATING PAYABLE AMOUNT OF NEWSPAPER data");
			EditNewspaperClaimResult = 0;
			e.printStackTrace();

		}

		return (EditNewspaperClaimResult);

	}

	@Override
	public int NewsApproval(Map<String, String> map, String FromDate, String ToDate, String EmpNo) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE NewsApproval");
		int ApprovalResult = 0;
		Map<String, String> map1 = new LinkedHashMap<>();
		try {

			double totalAmount = 0.0;

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String NewspaperId = entry.getKey();
				String Values = entry.getValue();
				String[] ValueArray = Values.split("_");
				totalAmount = totalAmount + Double.parseDouble(ValueArray[0]);
				map1.put(NewspaperId, ValueArray[1]);
			}

			ApprovalResult = dao.NewspaperApprove_AddNewspaperBill(EmpNo, totalAmount, FromDate, ToDate, map1);

		} catch (Exception e) {
			logger.error("EXCEPTION OCCUR while APPROVING  NEWSPAPER ");
			ApprovalResult = 0;
			e.printStackTrace();

		}
		return (ApprovalResult);

	}

	@Override
	public List<Object[]> getNewspaperClaimApprovedList()throws Exception
	{
		return dao.getNewspaperClaimApprovedList();
	}
	
	@Override
	public List<Object[]> getNewspaperApprovalList() throws Exception 
	{
		return dao.getNewspaperApprovalList();
	}
	
	@Override
	public Object[] getPayLevelAndNewsRectrictAmt(String empno) throws Exception
	{
		return dao.getPayLevelAndNewsRectrictAmt(empno);
	}
	
	@Override
	public Object[] getCheckPeriodOfNewsAlreadyPresentOrNot(String empno, String ClaimMonth, String ClaimYear) throws Exception
	{
		return dao.getCheckPeriodOfNewsAlreadyPresentOrNot(empno, ClaimMonth, ClaimYear);
	}
	
	@Override
	public Object[] getCheckNewspaperApproveOrNot(String NewspaperId) throws Exception
	{
		return dao.getCheckNewspaperApproveOrNot(NewspaperId);
	}
	@Override
	public Object[] getNewspaperEditDetails(String NewspaperId) throws Exception
	{
		return dao.getNewspaperEditDetails(NewspaperId);
	}
	
	@Override
	public Object[] getNewspaperUserPrintData(String NewspaperId)throws Exception
	{
		return dao.getNewspaperUserPrintData(NewspaperId);
	}

	@Override
	public int DeleteNewspaperClaim(String NewspaperId) throws Exception
	{
		return dao.DeleteNewspaperClaim(NewspaperId);
	}
	
	
	@Override
	public Object[] getNewspaperApprovalPeriodEditDetails(String NewspaperBillId)throws Exception
	{
		return dao.getNewspaperApprovalPeriodEditDetails(NewspaperBillId);
	}
		
	@Override
	public int UpdateNewsPeriod(String empno, String NewspaperBillId, String FromDate, String ToDate)throws Exception
	{
		return dao.UpdateNewsPeriod(empno, NewspaperBillId, FromDate, ToDate);
	}
	
	@Override
	public List<Object[]> getNewspaperReportPrintData(String NewspaperBillId) throws Exception
	{
		return dao.getNewspaperReportPrintData(NewspaperBillId);
	}
	
	@Override
	public Object[] getNewspaperContingentBillPrintData(String NewspaperBillId) throws Exception
	{
		return dao.getNewspaperContingentBillPrintData(NewspaperBillId);
	}
	
	@Override
	public LabMaster getLabDetails() throws Exception
	{
		return dao.getLabDetails();
	}
	
	
/////////////////////////////////////////////////Telephone///////////////////////////////////////////////
	
	
	
	@Override
	public List<Object[]> getDeviceList(String empid) throws Exception
	{
		return dao.getDeviceList(empid);
	}

	@Override
	public List<Object[]> getTeleDeviceList(String empid) throws Exception {
		
		return dao.getTeleDeviceList(empid) ;
	}

	@Override
	public int AddTeleUsers(String Empid, String deviceId, String deviceNo) throws Exception {
		
		return dao.AddTeleUsers(Empid, deviceId, deviceNo) ;
	}

	@Override
	public Object[] getTeleDeviceEditDetails(String empid, String TeleUsersId) {
		
		return dao.getTeleDeviceEditDetails(empid, TeleUsersId)  ;
	}

	@Override
	public int UpdateTeleUser(String Empid, String TeleUsersId, String deviceNo) {
		
		return dao.UpdateTeleUser(Empid, TeleUsersId, deviceNo)  ;
	}

	@Override
	public int DeleteTeleUser(String Empid, String TeleUsersId) {
		
		return dao.DeleteTeleUser(Empid, TeleUsersId)  ;
	}

	@Override
	public List<Object[]> getTeleClaimList(String Empid) {
		
		return dao.getTeleClaimList(Empid)  ;
	}

	@Override
	public Object[] getPayLevelAndTeleRectrictAmt(String Empid) {
		
		return dao.getPayLevelAndTeleRectrictAmt(Empid)  ;
	}

	@Override
	public Object[] getTeleSpecialpermission(String Empid) {
		
		return dao.getTeleSpecialpermission(Empid)  ;
	}

	@Override
	public int addTeleClaim(String Empid, String TotalBasic, String TotalTax, double GrossTotal,
			double RestrictedAmount, double PayableAmount, String PayLevelId, String ClaimMonth, String ClaimYear,
			String IsBroadBand, String[] TeleFromDate, String[] TeleToDate, String[] TeleUsersId, String[] TeleBillNo,
			String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount, String[] TotalAmount) {
		
		return dao.addTeleClaim(Empid, TotalBasic, TotalTax, GrossTotal, RestrictedAmount, PayableAmount, PayLevelId, ClaimMonth, ClaimYear, IsBroadBand, TeleFromDate, TeleToDate, TeleUsersId, TeleBillNo, TeleBillDate, BasicAmount, TaxAmount, TotalAmount)  ;
	}

	@Override
	public int getMaxTeleId() {
		
		return dao.getMaxTeleId()  ;
	}

	@Override
	public Object[] getCheckPeriodOfTeleAlreadyPresentOrNot(String Empid, String ClaimMonth, String ClaimYear) {
		
		return dao.getCheckPeriodOfTeleAlreadyPresentOrNot(Empid, ClaimMonth, ClaimYear)  ;
	}

	@Override
	public List<Object[]> getTeleClaimEditDetails(String TeleId) {
		
		return dao.getTeleClaimEditDetails(TeleId) ;
	}

	@Override
	public int updateTele(String Empid, String TeleId, String TotalBasic, String TotalTax, double GrossTotal,
			double PayableAmount, String IsBroadBand, String[] TeleDId, String[] TeleFromDate, String[] TeleToDate,
			String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount, String[] TotalAmount,
			String UserRemark, int fid) {
		
		return dao.updateTele(Empid, TeleId, TotalBasic, TotalTax, GrossTotal, PayableAmount, IsBroadBand, TeleDId, TeleFromDate, TeleToDate, TeleBillNo, TeleBillDate, BasicAmount, TaxAmount, TotalAmount, UserRemark, fid)  ;
	}

	@Override
	public int DeleteTelephone(String TeleId, String sessionEmpid) {
		
		return dao.DeleteTelephone(TeleId, sessionEmpid)  ;
	}

	@Override
	public int InsertTeleUserFlagAndGenerateTeleForwardId(String Empid) {
		
		return dao.InsertTeleUserFlagAndGenerateTeleForwardId(Empid)  ;
	}

	@Override
	public int getMaxTeleForwardId() {
		
		return dao.getMaxTeleForwardId()  ;
	}

	@Override
	public int UpdateTeleByTeleForwardId(String Empid, String[] TeleIdArray) {
		
		return dao.UpdateTeleByTeleForwardId(Empid, TeleIdArray)  ;
	}

	@Override
	public List<Object[]> getTelephoneSendbackData(String Empid) {
		
		return dao.getTelephoneSendbackData(Empid)  ;
	}

	@Override
	public List<Object[]> getTelephonePrintReportMultiData(String TeleBillId) {
		
		return dao.getTelephonePrintReportMultiData(TeleBillId)  ;
	}

	@Override
	public List<Object[]> getTelephonePrintReportSingleData(String TeleBillId) {
		
		return dao.getTelephonePrintReportSingleData(TeleBillId)  ;
	}

	@Override
	public Object[] getTelephoneContingentBillPrintData(String TeleBillId) {
		
		return dao.getTelephoneContingentBillPrintData(TeleBillId)  ;
	}

	@Override
	public int UpdateTelePeriod(String Empid, String TeleBillId, String FromDate, String ToDate) {
		
		return dao.UpdateTelePeriod(Empid, TeleBillId, FromDate, ToDate)  ;
	}

	@Override
	public Object[] getTelephoneApprovalPeriodEditDetails(String TeleBillId) {
		
		return dao.getTelephoneApprovalPeriodEditDetails(TeleBillId)  ;
	}

	@Override
	public Object[] getCheckTeleApproveForwardOrNot(String TeleId) {
		
		return dao.getCheckTeleApproveForwardOrNot(TeleId)  ;
	}

	@Override
	public Object[] getCheckTeleApproveOrNot(String TeleId) {
		
		return dao.getCheckTeleApproveOrNot(TeleId)  ;
	}

	@Override
	public int TelephoneSendback(String Empid, double FinalAmount, String FromDate, String ToDate,
			Map<String, String> map) {
		
		return dao.TelephoneSendback(Empid, FinalAmount, FromDate, ToDate, map)  ;
	}

	@Override
	public int TelephoneApprove_AddTelephoneBill(String Empid, double FinalAmount, String FromDate, String ToDate,
			Map<String, String> map) {
		
		return dao.TelephoneApprove_AddTelephoneBill(Empid, FinalAmount, FromDate, ToDate, map)  ;
	}

	@Override
	public List<Object[]> getTelephoneApprovalList() {
		
		return dao.getTelephoneApprovalList()  ;
	}

	@Override
	public List<Object[]> getTelephoneClaimApprovedList() {
		
		return dao.getTelephoneClaimApprovedList()  ;
	}

	@Override
	public List<Object[]> getTelephoneUserPrintMultiData(String TeleForwardId) {
		
		return dao.getTelephoneUserPrintMultiData(TeleForwardId)  ;
	}

	@Override
	public List<Object[]> getTelephoneUserPrintSingleData(String TeleForwardId) {
		
		return dao.getTelephoneUserPrintSingleData(TeleForwardId)  ;
	}
	
//////////////////////telephone//////////////////////////////////////
	  
	@Override
	public int AddTelephoneClaim(String sessionEmpid, String TotalBasic, String TotalTax, String GrossTotal,
			String RestrictedAmount, String[] TeleFromDate, String[] TeleToDate, String[] TeleUsersId,
			String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount, String[] TotalAmount,
			String PayLevelId, String ClaimMonth, String ClaimYear, String IsBroadBand) {

		int AddTelephoneClaim = 0;
		try {

			double PayableAmount = 0.0;
			double ClaimAmountInDecimal = Double.parseDouble(GrossTotal);
			double RestrictedAmountDecimal = Double.parseDouble(RestrictedAmount);

			if (ClaimAmountInDecimal > RestrictedAmountDecimal) {
				PayableAmount = RestrictedAmountDecimal;
			} else {
				PayableAmount = ClaimAmountInDecimal;
			}

			AddTelephoneClaim = dao.addTeleClaim(sessionEmpid, TotalBasic, TotalTax, ClaimAmountInDecimal,
					RestrictedAmountDecimal, PayableAmount, PayLevelId, ClaimMonth, ClaimYear, IsBroadBand,
					TeleFromDate, TeleToDate, TeleUsersId, TeleBillNo, TeleBillDate, BasicAmount, TaxAmount,
					TotalAmount);

		} catch (Exception e) {
			logger.info("EXCEPTION OCCUR while CALCULATING PAYABLE AMOUNT OF TELE data");
			AddTelephoneClaim = 0;
			e.printStackTrace();

		}

		return (AddTelephoneClaim);

	}
	
	@Override
	public int EditTelephoneResult(String sessionEmpid, String TeleId, String TotalBasic, String TotalTax,
			String GrossTotal, String RestrictedAmount, String[] TeleDId, String[] TeleFromDate, String[] TeleToDate,
			String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount, String[] TotalAmount,
			String IsBroadBand, String UserRemark, String id) {
		int EditTelephoneResult = 0;

		try {

			double PayableAmount = 0.0;
			double ClaimAmountInDecimal = Double.parseDouble(GrossTotal);
			double RestrictedAmountDecimal = Double.parseDouble(RestrictedAmount);

			if (ClaimAmountInDecimal > RestrictedAmountDecimal) {
				PayableAmount = RestrictedAmountDecimal;
			} else {
				PayableAmount = ClaimAmountInDecimal;
			}
			int fid = Integer.parseInt(id);

			EditTelephoneResult = dao.updateTele(sessionEmpid, TeleId, TotalBasic, TotalTax, ClaimAmountInDecimal,
					PayableAmount, IsBroadBand, TeleDId, TeleFromDate, TeleToDate, TeleBillNo, TeleBillDate,
					BasicAmount, TaxAmount, TotalAmount, UserRemark, fid);

		} catch (Exception e) {
			logger.info("EXCEPTION OCCUR while CALCULATING PAYABLE AMOUNT OF TELE data");
			EditTelephoneResult = 0;
			e.printStackTrace();

		}
		return (EditTelephoneResult);

	}

	@Override
	public int TeleApproval(Map<String, String> map, String FromDate, String ToDate, String Empid) {
		int ApprovalResult = 0;
		Map<String, String> map1 = new LinkedHashMap<>();
		try {

			double FinalAmount = 0.0;

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String TeleForwardId = entry.getKey();
				String Values = entry.getValue();
				String[] ValueArray = Values.split("_");
				FinalAmount = FinalAmount + Double.parseDouble(ValueArray[0]);
				map1.put(TeleForwardId, ValueArray[1]);
			}

			ApprovalResult = dao.TelephoneApprove_AddTelephoneBill(Empid, FinalAmount, FromDate, ToDate, map1);

		} catch (Exception e) {
			logger.info("EXCEPTION OCCUR while Approving TELEPHONE");
			ApprovalResult = 0;
			e.printStackTrace();

		}
		return (ApprovalResult);

	}

	@Override
	public int TeleSendback(Map<String, String> map, String FromDate, String ToDate, String Empid)
	{
		int ApprovalResult = 0;
		Map<String, String> map1 = new LinkedHashMap<>();
		try {

			double FinalAmount = 0.0;

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String TeleForwardId = entry.getKey();
				String Values = entry.getValue();
				map1.put(TeleForwardId, Values);
			}

			ApprovalResult = dao.TelephoneSendback(Empid, FinalAmount, FromDate, ToDate, map1);

		} catch (Exception e) {
			logger.info("EXCEPTION OCCUR while sending back TELEPHONE");
			ApprovalResult = 0;
			e.printStackTrace();

		}
		return (ApprovalResult);

	}



///////////////////*telephone*//////////////////////////////////////
}
