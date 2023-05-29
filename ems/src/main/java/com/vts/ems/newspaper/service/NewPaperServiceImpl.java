package com.vts.ems.newspaper.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.newspaper.dao.NewsPaperDao;
import com.vts.ems.newspaper.model.Newspaper;
import com.vts.ems.newspaper.model.NewspaperApplyTransaction;
import com.vts.ems.newspaper.model.NewspaperContingent;
import com.vts.ems.newspaper.model.NewspaperContingentTrans;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class NewPaperServiceImpl implements NewsPaperService {

	private static final Logger logger = LogManager.getLogger(NewPaperServiceImpl.class);

	@Autowired
	private NewsPaperDao dao;

	SimpleDateFormat sdtf=DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public List<Object[]> getNewspaperClaimList(String empno)throws Exception
	{
		return dao.getNewspaperClaimList(empno);
	}

	@Override
	public long AddNewspaperClaim(String EmpNo, String ClaimMonth, String ClaimYear, String ClaimAmount,	String RestrictedAmount, String PayLevelId, String userName) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE AddNewspaperClaim ");
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

			Newspaper n = new Newspaper();


			String newsAppliedDate = sdf.format(new Date());
			java.sql.Date sqlnewsAppliedDate = DateTimeFormatUtil.dateConversionSql(newsAppliedDate);


			n.setEmpNo(EmpNo);
			n.setClaimMonth(ClaimMonth);
			n.setClaimYear(ClaimYear);
			n.setClaimAmount(ClaimAmountInDecimal);
			n.setRestrictedAmount(RestrictedAmountDecimal);
			n.setPayableAmount(PayableAmount);
			n.setPayLevelId(Integer.parseInt(PayLevelId));
			n.setNewsAppliedDate(sqlnewsAppliedDate);
			n.setIsActive(1);
			n.setCreatedBy(userName);
			n.setCreatedDate(sdtf.format(new Date()));
			n.setNewspaperStatusCode("CRT");
			n.setContingentId(0);
			dao.addNewspaper(n);


			return n.getNewspaperId();

			//			AddNewspaperClaimResult = dao.AddNewspaperClaim(EmpNo, ClaimMonth, ClaimYear, ClaimAmountInDecimal,
			//					RestrictedAmountDecimal, PayableAmount, PayLevelId);

		} catch (Exception e) {
			logger.error("EXCEPTION OCCUR while CALCULATING PAYABLE AMOUNT OF NEWSPAPER data");

			AddNewspaperClaimResult = 0;
			e.printStackTrace();

		}
		return (AddNewspaperClaimResult);

	}

	@Override
	public long forwardNewspaper(long newspaperApplyId, String empNo, String remarks, String userName, String Action) throws Exception{
		long l=0;
		try {
			Newspaper newspaper=dao.findNewspaperApply(newspaperApplyId);
			NewspaperApplyTransaction tr=new NewspaperApplyTransaction();
			EMSNotification notification =new EMSNotification();

			notification.setCreatedDate(sdtf.format(new Date()));
			//			List<String> DGMs =dao.GetEmpDGMEmpNo();
			Employee emp=dao.findEmpByEmpNo(empNo);
			String DGM=dao.GetDGMEmpNo(empNo);
			String poEmpno= (String) dao.empOnLogintype("K")[0];
			String aoEmpno= (String) dao.empOnLogintype("W")[0];

			if(Action.equalsIgnoreCase("FWD")) {
				newspaper.setNewspaperStatusCode("FWD");

				tr.setNewspapertatusCode("FWD");
				
				notification.setEmpNo(DGM);
				notification.setNotificationUrl("NewspaperApproval.htm");
				notification.setNotificationMessage("Received Newspaper Claim Application From <br>"+emp.getEmpName());
			} else if(Action.equalsIgnoreCase("DGM-A")) {
				newspaper.setNewspaperStatusCode("VDG");

				tr.setNewspapertatusCode("VDG");
				
				notification.setEmpNo(poEmpno);
				notification.setNotificationUrl("NewspaperApproval.htm");
				notification.setNotificationMessage("Received Newspaper Claim Application From <br>"+dao.findEmpByEmpNo(newspaper.getEmpNo()).getEmpName());
			} else if(Action.equalsIgnoreCase("DGM-R")) {
				newspaper.setNewspaperStatusCode("SDG");

				tr.setNewspapertatusCode("SDG");
				
				notification.setEmpNo(newspaper.getEmpNo());
				notification.setNotificationUrl("NewspaperList.htm");
				notification.setNotificationMessage("Newspaper Claim Application Returned");
				
			} else if(Action.equalsIgnoreCase("PO-A")) {
				newspaper.setNewspaperStatusCode("VBP");

				tr.setNewspapertatusCode("VBP");
				
				notification.setEmpNo(aoEmpno);
				notification.setNotificationUrl("NewspaperApproval.htm");
				notification.setNotificationMessage("Received Newspaper Claim Application From <br>"+dao.findEmpByEmpNo(newspaper.getEmpNo()).getEmpName());
				
			}else if(Action.equalsIgnoreCase("PO-R")) {
				newspaper.setNewspaperStatusCode("SBP");

				tr.setNewspapertatusCode("SBP");
				
				notification.setEmpNo(newspaper.getEmpNo());
				notification.setNotificationUrl("NewspaperList.htm");
				notification.setNotificationMessage("Newspaper Claim Application Returned");
				
			} else if(Action.equalsIgnoreCase("AO-A")) {
				newspaper.setNewspaperStatusCode("VBA");

				tr.setNewspapertatusCode("VBA");
				
				
			}else if(Action.equalsIgnoreCase("AO-R")) {
				newspaper.setNewspaperStatusCode("SBA");

				tr.setNewspapertatusCode("SBA");
				
				notification.setEmpNo(newspaper.getEmpNo());
			
			}
			dao.editNewspaper(newspaper);

			tr.setNewspaperApplyId(newspaper.getNewspaperId());
			tr.setRemark(remarks);
			tr.setActionBy(empNo);
			tr.setActionDate(sdtf.format(new Date()));

			dao.addNewspaperTrans(tr);

			notification.setNotificationBy(empNo);
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(userName);
			if(!Action.equalsIgnoreCase("AO-A") || !Action.equalsIgnoreCase("AO-R")) {
			dao.addNotification(notification);
			}
			
			l=newspaper.getNewspaperId();

		} catch (Exception e) {
			logger.error(new Date() +" Inside SERVICE forwardNewspaper "+ e);
			e.printStackTrace();
			return 0;
		}
		return l;
	}

	@Override
	public long editNewspaper(Newspaper newspaper) throws Exception{
		return dao.editNewspaper(newspaper);
	}
	
	@Override
	public Object[] empOnLogintype(String Logintype) throws Exception{
		return dao.empOnLogintype(Logintype);
	}
	
	@Override
	public List<Object[]> findPONewspaperList() throws Exception{
		return dao.findPONewspaperList();
	}
	
	@Override
	public List<Object[]> findAONewspaperList() throws Exception{
		return dao.findAONewspaperList();
	}
	
	@Override
	public String GetDGMEmpNo(String empno) throws Exception{
		return dao.GetDGMEmpNo(empno);
	}
	
	public Employee findEmpByEmpNo(String empNo) throws Exception {
		return dao.findEmpByEmpNo(empNo);
	}
	
	@Override
	public List<Object[]> findApprovedNewspaperList(String todate) throws Exception{
		return dao.findApprovedNewspaperList(todate);
	}
	
	public List<Object[]> getNewspaperContingentList(String logintype,String fromdate,String todate) throws Exception{
		return dao.getNewspaperContingentList(logintype, fromdate, todate);
	}
	
	@Override
	public long ContingentGenerate(String NewspaperId[],String Username, String logintype,String EmpNo,String genTilldate) throws Exception 
	{	
		logger.info(new Date() +"Inside SERVICE ContingentGenerate ");
		NewspaperContingent continnew =new NewspaperContingent();
		long contingentid=0;

//		continnew.setContingentBillNo();			

//		continnew.setContingentDate(LocalDate.now().toString()); 
		continnew.setClaimsCount(NewspaperId.length);
		continnew.setContingentStatusCode("CGT");
//		continnew.setRemarks(billcontent);
		continnew.setIsActive(1);
		continnew.setCreatedBy(Username);
		continnew.setCreatedDate(sdf.format(new Date()));
		continnew.setPO(0L);
		continnew.setVO(0L);
		continnew.setAO(0L);
		continnew.setCEO(0L);
		continnew.setGenTillDate(genTilldate);
		contingentid=dao.addNewspaperContingent(continnew);
		
		long count=0;
		for(String claimid  : NewspaperId)
		{
			Newspaper claim = dao.findNewspaperApply(Long.parseLong(claimid));
			
//			claim.setCHSSStatusId(8);
			claim.setContingentId(contingentid);
			claim.setModifiedBy(Username);
			claim.setModifiedDate(sdtf.format(new Date()));
			count=dao.editNewspaper(claim);
			

		}
			
		NewspaperContingentTrans transac =new NewspaperContingentTrans();
		transac.setContingentId(contingentid);
		transac.setStatusCode("CGT");
		transac.setRemarks("");
		transac.setActionBy(EmpNo);
		transac.setActionDate(sdtf.format(new Date()));
		dao.addNewspaperContingentTrans(transac);

		
		return contingentid;
	}

	@Override
	public int EditNewspaperClaim(String EmpNo, String ClaimAmount, String NewspaperId, String RestrictedAmount)throws Exception 
	{
		int EditNewspaperClaimResult = 0;
		logger.info(new Date() +"Inside SERVICE AddNewspaperClaim ");
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
		logger.info(new Date() +"Inside SERVICE NewsApproval ");
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

	@Override
	public List<Object[]> NewspaperAllApprovedOrNot(String claimMonth, String claimYear) throws Exception{
		return dao.NewspaperAllApprovedOrNot(claimMonth, claimYear);
	}

	@Override
	public List<String> GetEmpDGMEmpNo() throws Exception{
		return dao.GetEmpDGMEmpNo();
	}

	@Override
	public List<Object[]> findDGMNewspaperList(String empNo) throws Exception{
		return dao.findDGMNewspaperList(empNo);
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

	@Override
	public List<Object[]> getTelephoneUserPrintSingleDataByMonth(String TeleForwardId,String teleId) {

		return dao.getTelephoneUserPrintSingleDataByMonth(TeleForwardId, teleId);
	}

	//////////////////////telephone//////////////////////////////////////

	@Override
	public int AddTelephoneClaim(String sessionEmpid, String TotalBasic, String TotalTax, String GrossTotal,
			String RestrictedAmount, String[] TeleFromDate, String[] TeleToDate, String[] TeleUsersId,
			String[] TeleBillNo, String[] TeleBillDate, String[] BasicAmount, String[] TaxAmount, String[] TotalAmount,
			String PayLevelId, String ClaimMonth, String ClaimYear, String IsBroadBand) {

		logger.info(new Date() + "Inside SERVICE AddTelephoneClaim ");
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
			logger.error("EXCEPTION OCCUR while CALCULATING PAYABLE AMOUNT OF TELE data");
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
		logger.info(new Date() + "Inside SERVICE EditTelephoneResult ");

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
			logger.error("EXCEPTION OCCUR while CALCULATING PAYABLE AMOUNT OF TELE data");
			EditTelephoneResult = 0;
			e.printStackTrace();

		}
		return (EditTelephoneResult);

	}

	@Override
	public int TeleApproval(Map<String, String> map, String FromDate, String ToDate, String Empid) {
		int ApprovalResult = 0;
		logger.info(new Date() + "Inside SERVICE TeleApproval ");
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
			logger.error("EXCEPTION OCCUR while Approving TELEPHONE");
			ApprovalResult = 0;
			e.printStackTrace();

		}
		return (ApprovalResult);

	}

	@Override
	public int TeleSendback(Map<String, String> map, String FromDate, String ToDate, String Empid)
	{
		logger.info(new Date() + "Inside SERVICE TeleSendback ");
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

		} 
		catch (Exception e) 
		{
			logger.error("EXCEPTION OCCUR while sending back TELEPHONE");
			ApprovalResult = 0;
			e.printStackTrace();

		}
		return (ApprovalResult);

	}

	@Override
	public List<Object[]> TelePhoneAllApprovedOrNot(String claimMonth, String claimYear)throws Exception{
		return dao.TelePhoneAllApprovedOrNot(claimMonth, claimYear);
	}


	///////////////////*telephone*//////////////////////////////////////
}
