package com.vts.ems.Tour.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.Tour.dao.TourDao;
import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourAdvance;
import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.model.TourTransaction;
import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.master.dao.MasterDao;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class TourServiceImpl implements TourService {

	@Autowired
	private TourDao dao;
	
	@Autowired
	MasterDao masterdao;
	private static final Logger logger = LogManager.getLogger(TourServiceImpl.class);

	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@Override
	public List<Object[]> GetEmployeeList()throws Exception
	{
		return dao.GetEmployeeList();
	}

	@Override
	public List<Object[]> GetModeofTravel() throws Exception {
		
		return dao.GetModeofTravel();
	}

	@Override
	public List<Object[]> GetCityList() throws Exception {
		
		return dao.GetCityList();
	}
	
	@Override
	public Long AddTourApply(TourApplyDto dto)throws Exception
	{
		TourApply apply = new TourApply();
			apply.setStayFrom(dto.getStayFrom());
			apply.setStayTo(dto.getStayTo());
			apply.setEJPFrom(dto.getEJPFrom());
			apply.setEJPTo(dto.getEJPTo());
			apply.setStayPlace(dto.getStayPlace());
			apply.setPurpose(dto.getPurpose());
			apply.setAirTravJust(dto.getAirTravJust());
			apply.setAdvancePropsed(dto.getAdvancePropsed());
			apply.setEmpNo(dto.getEmpNo());
			apply.setDivisionId(dto.getDivisionId());
			apply.setEarliestTime(dto.getEarliestTime());
			apply.setEarliestDate(dto.getEarliestDate());
			apply.setEarliestPlace(dto.getEarliestPlace());
			apply.setCreatedBy(dto.getCreatedBy());
			apply.setCreatedDate(dto.getCreatedDate());
			apply.setIsActive(dto.getIsActive());
			apply.setInitiatedDate(dto.getInitiatedDate());
			apply.setTourStatusCode(dto.getTourStatusCode());
			apply.setTourApplPreviousId(0l);
		Long TourApplyId = dao.AddTourapply(apply);
		if(dto.getAdvancePropsed().equalsIgnoreCase("Y") && TourApplyId >0 ) {
			TourAdvance touradvance = new TourAdvance();
				
				touradvance.setTotalProposedAmt(dto.getTotalProposedAmount());
				touradvance.setTourFare(dto.getTourFare());
				touradvance.setTourfareFrom(dto.getTourfareFrom());
				touradvance.setTourfareTo(dto.getTourfareTo());
				touradvance.setBoardingDays(dto.getBoardingDays());
				touradvance.setBoardingPerDay(dto.getBoardingPerDay());
				touradvance.setPerDayAllowance(dto.getPerDayAllowance());
				touradvance.setAllowanceDays(dto.getAllowanceDays());
				touradvance.setAllowanceFromDate(dto.getAllowanceFromDate());
				touradvance.setAllowanceToDate(dto.getAllowanceToDate());
				touradvance.setStatus(dto.getTourStatusCode());
				touradvance.setIsActive(dto.getIsActive());
				touradvance.setCreatedBy(dto.getCreatedBy());
				touradvance.setCreatedDate(dto.getCreatedDate());
				touradvance.setTourApplyId(TourApplyId);
				
			dao.AddTouradvance(touradvance);
		}
		
		for(int i=0;i<dto.getTourDates().length;i++) {
			TourOnwardReturn tourdetails = new TourOnwardReturn(); 
				tourdetails.setTourApplyId(TourApplyId);
				tourdetails.setTourDate(DateTimeFormatUtil.dateConversionSql(dto.getTourDates()[i]));
				tourdetails.setTourTime(dto.getTourTimes()[i]);
				tourdetails.setModeId(Long.parseLong(dto.getModeofTravel()[i]));
				tourdetails.setFromCityId(Long.parseLong(dto.getFromCity()[i]));
				tourdetails.setToCityId(Long.parseLong(dto.getToCity()[i]));
			dao.AddTourOnwardReturn(tourdetails);
		}
		
		// Tour Transaction 
		TourTransaction transaction = new TourTransaction();
			transaction.setActionBy(dto.getEmpNo());
			transaction.setActionDate(dto.getInitiatedDate());
			transaction.setTourApplyId(TourApplyId);
			transaction.setTourStatusCode("INI");
		
		dao.AddTourTransaction(transaction);
		
		return TourApplyId;
	}
	
	@Override
	public List<Object[]> GetTourApplyList(String empno)throws Exception
	{
		return dao.GetTourApplyList(empno);
	}

	@Override
	public List<Object[]> GetApplyStatusList(String empno ,  String fromdate , String todate)throws Exception
	{
		
		 return dao.GetApplyStatusList(empno , fromdate , todate);
	}
	
	@Override
	public String[]	checkTDAlreadyPresentForSameEmpidAndSameDates(String empno,String DepartureDate,String ArrivalDate , String action , String tourid)throws Exception
	{
		String[] Result=new String[5]; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date checkStartDate = sdf.parse(DepartureDate);
		Date checkEndDate = sdf.parse(ArrivalDate);

		long difference = checkEndDate.getTime() - checkStartDate.getTime();
		long daysdifference = (difference / 86400000);

		
		if (checkStartDate.before(checkEndDate) || checkStartDate.equals(checkEndDate)) {
			if (daysdifference <= 180) {
					try {
						String fromdate = DateTimeFormatUtil.RegularToSqlDate(DepartureDate);
						String todate = DateTimeFormatUtil.RegularToSqlDate(ArrivalDate);
						
					
						if(action!=null && action.equalsIgnoreCase("Edit")) {
							/* Tour Edit */
							Long checkTDAlreadyPresentForSameEmpidAndSameDate = dao.checkTourAlreadyPresentForSameEmpidAndSameDates(tourid, empno,fromdate,todate);
							
							if (checkTDAlreadyPresentForSameEmpidAndSameDate ==0) {
										Result[0]="You Can Apply";
										Result[1]="Pass";
										return Result;
								} else {
										Result[0]="Tour Already Present For Same Period";
										Result[1]="Fail";
										Result[2]=String.valueOf(checkTDAlreadyPresentForSameEmpidAndSameDate);
										return Result;
								}
						}else {
							/* First Tour Apply */
							Long checkTDAlreadyPresentForSameEmpidAndSameDate = dao.checkTDAlreadyPresentForSameEmpidAndSameDates(empno,fromdate,todate);
							
							
							if (checkTDAlreadyPresentForSameEmpidAndSameDate ==0) {
										Result[0]="You Can Apply";
										Result[1]="Pass";
										return Result;
								} else {
										Result[0]="Tour Already Present For Same Period";
										Result[1]="Fail";
										Result[2]=String.valueOf(checkTDAlreadyPresentForSameEmpidAndSameDate);
										return Result;
								}
						}
					
					} catch (Exception e) {
						Result[0]=" Please Enter Advance Amount Correctly";
						Result[1]="Fail";
						return Result;
					}
			} else {
				Result[0]="You Cannot Take Tour Days More Than 180 Days";
				Result[1]="Fail";
				return Result;
			}
		} else {
			Result[0]="Arrival Date Should Not Be Less Than Depature Date!";
			Result[1]="Fail";
			return Result;
		}
	}
	
	@Override
	public TourApply getTourApplyData(Long tourid)throws Exception
	{
		return dao.getTourApplyData(tourid);
	}
	
	@Override
	public List<TourOnwardReturn> getTourOnwardReturnData(Long tourid)throws Exception
	{
		return dao.getTourOnwardReturnData(tourid);
	}
	
	@Override
	public Long EditTourApply(TourApplyDto dto)throws Exception
	{		
		
		TourApply apply = dao.getTourApplyData(dto.getTourApplyId());
		apply.setStayFrom(dto.getStayFrom());
		apply.setStayTo(dto.getStayTo());
		apply.setStayPlace(dto.getStayPlace());
		apply.setPurpose(dto.getPurpose());
		apply.setAirTravJust(dto.getAirTravJust());
		apply.setAdvancePropsed(dto.getAdvancePropsed());
		apply.setEarliestTime(dto.getEarliestTime());
		apply.setEarliestDate(dto.getEarliestDate());
		apply.setEarliestPlace(dto.getEarliestPlace());
		apply.setModifiedBy(dto.getModifiedBy());
		apply.setModifiedDate(dto.getModifiedDate());
		apply.setInitiatedDate(sdtf.format(new Date()));
		
		
		if(dto.getAdvancePropsed().equalsIgnoreCase("Y")){
			TourAdvance  advance = new TourAdvance();
			advance.setTourFare(dto.getTourFare());
			advance.setTourfareFrom(dto.getTourfareFrom());
			advance.setTourfareTo(dto.getTourfareFrom());
			advance.setBoardingDays(dto.getBoardingDays());
			advance.setBoardingPerDay(dto.getBoardingPerDay());
			advance.setPerDayAllowance(dto.getPerDayAllowance());
			advance.setAllowanceDays(dto.getAllowanceDays());
			advance.setAllowanceFromDate(dto.getAllowanceFromDate());
			advance.setAllowanceToDate(dto.getAllowanceToDate());
			advance.setTourApplyId(dto.getTourApplyId());
			
			TourAdvance touradvdata = dao.GetTourAdvanceData(advance.getTourApplyId());
			if(touradvdata!=null ){
				
				touradvdata.setTourFare(dto.getTourFare());
				touradvdata.setTourfareFrom(dto.getTourfareFrom());
				touradvdata.setTourfareTo(dto.getTourfareFrom());
				touradvdata.setBoardingDays(dto.getBoardingDays());
				touradvdata.setBoardingPerDay(dto.getBoardingPerDay());
				touradvdata.setPerDayAllowance(dto.getPerDayAllowance());
				touradvdata.setAllowanceDays(dto.getAllowanceDays());
				touradvdata.setAllowanceFromDate(dto.getAllowanceFromDate());
				touradvdata.setAllowanceToDate(dto.getAllowanceToDate());
				touradvdata.setTourApplyId(dto.getTourApplyId());
				touradvdata.setTourAdvanceId(dto.getTourAdvanceId());
				touradvdata.setModifiedBy(dto.getModifiedBy());
				touradvdata.setModifiedDate(dto.getModifiedDate());
				dao.UpdateTourAdvance(touradvdata);
			}else {
				
				advance.setCreatedBy(dto.getModifiedBy());
				advance.setCreatedDate(dto.getModifiedDate());
				dao.AddTouradvance(advance);
			}
		}
		Long tourapplyid = dao.UpdateTourApply(apply); 
		
		int details = dao.DeleteOnwardReturnData(dto.getTourApplyId());
		
		if(details>0) {
			for(int i=0;i<dto.getTourDates().length;i++) {
				TourOnwardReturn tourdetails = new TourOnwardReturn(); 
					tourdetails.setTourApplyId(tourapplyid);
					tourdetails.setTourDate(DateTimeFormatUtil.dateConversionSql(dto.getTourDates()[i]));
					tourdetails.setTourTime(dto.getTourTimes()[i]);
					tourdetails.setModeId(Long.parseLong(dto.getModeofTravel()[i]));
					tourdetails.setFromCityId(Long.parseLong(dto.getFromCity()[i]));
					tourdetails.setToCityId(Long.parseLong(dto.getToCity()[i]));
				dao.AddTourOnwardReturn(tourdetails);
			}
		}
		return tourapplyid;
	}
	
	@Override
	public List<Object[]> GetTourApprovalList(String empno)throws Exception
	{
		return dao.GetTourApprovalList(empno);
	}
	@Override
	public List<Object[]> GetTourCancelList(String empno)throws Exception
	{
		return dao.GetTourCancelList(empno);
	}
	
	@Override
	public int GetDeptInchApproved(ApprovalDto dto ,HttpServletRequest req)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE GetDeptInchApproved ");
		int count=0;
		
		Object[] divisiondgmpafa = dao.GetDivisionHeadandDGMPAFA(dto.getValue());
		if(dto.getApprove()!=null) {
			for(int i=0;i<dto.getApprove().length;i++) {
				try {
					EMSNotification notification = new EMSNotification();
					notification.setCreatedBy(dto.getUserName());
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setNotificationBy(dto.getEmpNo());
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("TourApprovallist.htm");
					notification.setIsActive(1);
					 
					String status = dto.getApprove()[i].split("_")[1];
					if(status.equalsIgnoreCase("FWD")) {

						notification.setEmpNo(divisiondgmpafa[1].toString());
						notification.setNotificationMessage("Tour Forwarded by "+dto.getUserName());
						dao.EmpNotificationForTour(notification);

							dto.setStatus("VDH");
							dto.setApplId(dto.getApprove()[i].split("_")[0]);
						
							dao.getTourUpdate(dto);
					}else if(status.equalsIgnoreCase("VDH")) {
						
						notification.setEmpNo(divisiondgmpafa[2].toString());
						notification.setNotificationMessage("Tour Forwarded by "+dto.getUserName());
						dao.EmpNotificationForTour(notification);
							
						dto.setStatus("ABD");
							dto.setApplId(dto.getApprove()[i].split("_")[0]);
							dao.getTourUpdate(dto);
					}else if(status.equalsIgnoreCase("ABD")) {
						
						notification.setEmpNo(divisiondgmpafa[3].toString());
						notification.setNotificationMessage("Tour Verifed by "+dto.getUserName());
						dao.EmpNotificationForTour(notification);
						
						String funds=req.getParameter("Funds"+dto.getApprove()[i].split("_")[0]);

						dto.setFunds(funds!=null && funds.equalsIgnoreCase("true")? "Y" : "N");
						dto.setStatus("ABF");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
						dao.getTourUpdate(dto);
					}else if(status.equalsIgnoreCase("ABF")) {
						
						notification.setEmpNo(divisiondgmpafa[4].toString());
						notification.setNotificationMessage("Tour Verifed by "+dto.getUserName());
						dao.EmpNotificationForTour(notification);
						
						dto.setStatus("ABP");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
						dao.TourUpdateFromPandA(dto , req.getParameter(dto.getApplId()));
					}else if(status.equalsIgnoreCase("ABP")) {
						
						Calendar now = Calendar.getInstance();
						int month = (now.get(Calendar.MONTH) + 1);
						String financialYear;
						if ( month <= 3) 
						{
							int year = (now.get(Calendar.YEAR)-1);
							int nextyear = (now.get(Calendar.YEAR));
							String yr = String.valueOf(year);
							String ny = String.valueOf(nextyear);
							financialYear =  yr+ny.substring(2, 4);
						}else{
							int year = now.get(Calendar.YEAR);
							int nextyear = (now.get(Calendar.YEAR)+1);
							String yr = String.valueOf(year);
							String ny = String.valueOf(nextyear);
							financialYear=yr+ny.substring(2, 4);
						}
						Object[] labslist=masterdao.getLabDetails();
						String labcode ="";
						if(labslist!=null && labslist.length>0) {
							 labcode =labslist[1].toString();
							 
						}
						financialYear=labcode+"/"+financialYear;
						List<Object[]> FinancialyearWiseTDMONoList=dao.getFinancialyearWiseTourMONoList(financialYear);
					    List<Integer> arrayListOfMaxTdMoNo = new ArrayList<Integer>();
					    
					    for(Object[] ls:FinancialyearWiseTDMONoList)
					    {
					    	String [] ApplIdMaxTdMoNo= (ls[1].toString()).split("/");
                            String MaxTdMoNo= ApplIdMaxTdMoNo[2];
			              	arrayListOfMaxTdMoNo.add(Integer.parseInt(MaxTdMoNo));
					    }
					    String TDMoNo;
					    
					    if(FinancialyearWiseTDMONoList.size()==0)
					    {
					    	TDMoNo= financialYear+"/1";
					    }else{
					    	int TDMoNoInt=Collections.max(arrayListOfMaxTdMoNo)+1; 
					    	TDMoNo= financialYear+"/"+String.valueOf(TDMoNoInt);
					    }
						dto.setStatus("ABC");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
						dao.TourUpdateFromCEO(dto, TDMoNo);
						
						if(divisiondgmpafa!=null && divisiondgmpafa[3]!=null) {
							EMSNotification notification1 = new EMSNotification(); 
							notification1.setEmpNo(divisiondgmpafa[3].toString());
							notification1.setCreatedBy(dto.getUserName());
							notification1.setCreatedDate(sdtf.format(new Date()));
							notification1.setNotificationBy(dto.getEmpNo());
							notification1.setNotificationMessage("Tour Sanctioned by "+dto.getUserName());
							notification1.setNotificationDate(sdtf.format(new Date()));
							notification1.setNotificationUrl("TourApprovallist.htm");
							notification1.setIsActive(1);
							 dao.EmpNotificationForTour(notification1);
						}
						EMSNotification notification2 = new EMSNotification(); 
						notification2.setEmpNo(req.getParameter("EmpNo"+dto.getApplId()));
						notification2.setCreatedBy(dto.getUserName());
						notification2.setCreatedDate(sdtf.format(new Date()));
						notification2.setNotificationBy(dto.getValue());
						notification2.setNotificationMessage("Tour Approved by "+dto.getUserName());
						notification2.setNotificationDate(sdtf.format(new Date()));
						notification2.setNotificationUrl("TourSanctionedlist.htm");
						notification2.setIsActive(1);
						 dao.EmpNotificationForTour(notification2);
					}
					
				   	    TourTransaction transaction=new TourTransaction();
				        transaction.setActionBy(dto.getEmpNo());
				        transaction.setActionDate(sdtf.format(new Date()));
				        transaction.setTourApplyId(Long.parseLong(dto.getApplId()));
				        transaction.setTourStatusCode(dto.getStatus());
				        transaction.setTourRemarks(req.getParameter(dto.getApplId()));
				        long trns=dao.AddTourTransaction(transaction);
					
				        
				        count=1;
				}catch (Exception e) {
					e.printStackTrace();
					count=0;
				}
			}
		}
		if(dto.getReject()!=null) {
			for(int i=0;i<dto.getReject().length;i++) {
				try {
					EMSNotification notification = new EMSNotification();
					String status = dto.getReject()[i].split("_")[1];
					if(status.equalsIgnoreCase("FWD")) {
						notification.setNotificationMessage("Tour Returned by "+dto.getUserName());
						dto.setStatus("RDH");
					}else if(status.equalsIgnoreCase("VDH")) {
						notification.setNotificationMessage("Tour Returned by "+dto.getUserName());
						dto.setStatus("RDG");
					}else if(status.equalsIgnoreCase("ABD")) {
						notification.setNotificationMessage("Tour Returned by "+dto.getUserName());
						dto.setStatus("RBF");
					}else if(status.equalsIgnoreCase("ABF")) {
						notification.setNotificationMessage("Tour Returned by "+dto.getUserName());
						dto.setStatus("RBP");
					}else if(status.equalsIgnoreCase("ABP")) {
						notification.setNotificationMessage("Tour Disapproved by "+dto.getUserName());
						dto.setStatus("RBC");
					}
					dto.setApplId(dto.getReject()[i].split("_")[0]);

					dao.getTourUpdate(dto);
					
					notification.setEmpNo(dto.getValue());
					notification.setCreatedBy(dto.getUserName());
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setNotificationBy(dto.getEmpNo());
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("TourApplyList.htm");
					notification.setIsActive(1);
					 dao.EmpNotificationForTour(notification);

					    TourTransaction transaction=new TourTransaction();
				        transaction.setActionBy(dto.getEmpNo());
				        transaction.setActionDate(sdtf.format(new Date()));
				        transaction.setTourApplyId(Long.parseLong(dto.getApplId()));
				        transaction.setTourStatusCode(dto.getStatus());
				        transaction.setTourRemarks(req.getParameter(dto.getApplId()));
				        long trns=dao.AddTourTransaction(transaction);
				        count=1;
				}catch (Exception e) {
					e.printStackTrace();
					count=0;
				}
			}
		}
		return count;
	}
	@Override
	public int GetCancelApproved(ApprovalDto dto ,HttpServletRequest req)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE GetDeptInchApproved ");
		int count=0;
		Object[] divisiondgmpafa = dao.GetDivisionHeadandDGMPAFA(dto.getValue());
		if(dto.getApprove()!=null) {
			for(int i=0;i<dto.getApprove().length;i++) {
				try {
					
					EMSNotification notification = new EMSNotification();
					String status = dto.getApprove()[i].split("_")[1];
					
					if(status.equalsIgnoreCase("CBU")) {
						notification.setEmpNo(divisiondgmpafa[1].toString());
						notification.setNotificationMessage("Tour cancellation Forwarded by "+dto.getUserName());
						notification.setNotificationUrl("TourApprovallist.htm");

						
						dto.setStatus("CAA");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}else if(status.equalsIgnoreCase("CAA")) {
						notification.setEmpNo(divisiondgmpafa[2].toString());
						notification.setNotificationMessage("Tour cancellation Forwarded by "+dto.getUserName());
						notification.setNotificationUrl("TourApprovallist.htm");

						dto.setStatus("CAG");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}else if(status.equalsIgnoreCase("CAG")) {
						notification.setEmpNo(divisiondgmpafa[4].toString());
						notification.setNotificationMessage("Tour cancellation forwarded by "+dto.getUserName());
						notification.setNotificationUrl("TourApprovallist.htm");

						dto.setStatus("CAF");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}else if(status.equalsIgnoreCase("CAF")) {
						notification.setEmpNo(divisiondgmpafa[3].toString());
						notification.setNotificationMessage("Tour cancellation approved by "+dto.getUserName());
						notification.setNotificationUrl("TourApprovallist.htm");

						dto.setStatus("CAC");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}else if(status.equalsIgnoreCase("CAC")) {
						notification.setEmpNo(dto.getValue());
						notification.setNotificationMessage("Tour cancellation approved by "+dto.getUserName());
						notification.setNotificationUrl("TourCancelledlist.htm");

						dto.setStatus("CAP");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}
					
					dao.getTourUpdate(dto);
					
					notification.setCreatedBy(dto.getUserName());
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setNotificationBy(dto.getEmpNo());
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setIsActive(1);
					   dao.EmpNotificationForTour(notification);

				   	    TourTransaction transaction=new TourTransaction();
				        transaction.setActionBy(dto.getEmpNo());
				        transaction.setActionDate(sdtf.format(new Date()));
				        transaction.setTourApplyId(Long.parseLong(dto.getApplId()));
				        transaction.setTourStatusCode(dto.getStatus());
				        transaction.setTourRemarks(req.getParameter(dto.getApplId()));
				           dao.AddTourTransaction(transaction);
			        
				        count=1;
				}catch (Exception e) {
					e.printStackTrace();
					count=0;
				}
			}
		}
		if(dto.getReject()!=null) {
			for(int i=0;i<dto.getReject().length;i++) {
				try {
					
					EMSNotification notification = new EMSNotification();
					String status = dto.getReject()[i].split("_")[1];
					
					if(status.equalsIgnoreCase("CBU")) {
						dto.setStatus("CAD");
					}else if(status.equalsIgnoreCase("CAA")) {
						dto.setStatus("CDG");
					}else if(status.equalsIgnoreCase("CAG")) {
						dto.setStatus("CDF");
					}else if(status.equalsIgnoreCase("CAF")) {
						dto.setStatus("CDC");
					}else if(status.equalsIgnoreCase("CAC")) {
						dto.setStatus("CDP");
					}
					
					dto.setApplId(dto.getReject()[i].split("_")[0]);
						
					notification.setNotificationMessage("Tour cancellation Returned by "+dto.getUserName());
					notification.setEmpNo(dto.getValue());
					notification.setCreatedBy(dto.getUserName());
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setNotificationBy(dto.getEmpNo());
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("TourCancelledlist.htm");
					notification.setIsActive(1);
					 dao.EmpNotificationForTour(notification);
					 dao.getTourUpdate(dto);
					    TourTransaction transaction=new TourTransaction();
				        transaction.setActionBy(dto.getEmpNo());
				        transaction.setActionDate(sdtf.format(new Date()));
				        transaction.setTourApplyId(Long.parseLong(dto.getApplId()));
				        transaction.setTourStatusCode(dto.getStatus());
				        transaction.setTourRemarks(req.getParameter(dto.getApplId()));
				          dao.AddTourTransaction(transaction);
				        count=1;
				}catch (Exception e) {
					e.printStackTrace();
					count=0;
				}
			}
		}
		return count;
	}

	
	@Override
	public List<Object[]> TourStatusDetails(String tourapplyid)throws Exception
	{
		return dao.TourStatusDetails(tourapplyid);
	}
	@Override
	public List<Object[]> TourCancelStatusDetails(String tourapplyid)throws Exception
	{
		return dao.TourCancelStatusDetails(tourapplyid);
	}
	@Override
	public List<Object[]> TourCancelStatusDetailsTrack(String tourapplyid)throws Exception
	{
		return dao.TourCancelStatusDetailsTrack(tourapplyid);
	}
	@Override
	public Object[] GetApprovalEmp(String empno)throws Exception
	{
		return dao.GetApprovalEmp(empno);
	}
	@Override
	public Long RevokeTour(ApprovalDto dto)throws Exception
	{
		long count=0l;
		try {
			count=dao.RevokeTour(dto);
			if(count>0) {
				TourTransaction transaction=new TourTransaction();
		        transaction.setActionBy(dto.getEmpNo());
		        transaction.setActionDate(sdtf.format(new Date()));
		        transaction.setTourApplyId(Long.parseLong(dto.getApplId()));
		        transaction.setTourStatusCode(dto.getStatus());
		        long trns=dao.AddTourTransaction(transaction);
			}
		} catch (Exception e) {
			e.printStackTrace();
			count=0l;
		}
		return count;
	}

	@Override
	public List<Object[]> GetSanctionList(String empno , String from , String todate)throws Exception
	{
		return dao.GetSanctionList(empno,from,todate);
	}
	
	@Override
	public List<Object[]> GetCancelList(String empno)throws Exception
	{
		return dao.GetCancelList(empno);
	}
	
	@Override
	public int CancelTour(ApprovalDto dto)throws Exception
	{
		int result =  dao.CancelTour(dto);
		if(result>0) {
			
			TourTransaction transaction=new TourTransaction();
			transaction.setTourRemarks(dto.getValue());
	        transaction.setActionBy(dto.getEmpNo());
	        transaction.setActionDate(sdtf.format(new Date()));
	        transaction.setTourApplyId(Long.parseLong(dto.getApplId()));
	        transaction.setTourStatusCode(dto.getStatus());
	        long trns=dao.AddTourTransaction(transaction);
		}
		return result;
	}

	@Override
	public List<Object[]> GetTourCancelList(String empno ,  String fromdate , String todate) throws Exception {
		
		return dao.GetTourCancelList(empno, fromdate , todate);
	}

	@Override
	public Object[] GetTourDetails(String tourapplyid) throws Exception
	{
		return dao.GetTourDetails(tourapplyid);
	}
	@Override
	public Object[] GetTourAdvanceDetails(String tourapplyid)throws Exception
	{
		return dao.GetTourAdvanceDetails(tourapplyid);
	}
	
	@Override
	public List<Object[]> getTourOnwardReturnDetails(String tourapplyid)throws Exception
	{
		return dao.getTourOnwardReturnDetails(tourapplyid);
	}
	
	@Override
	public Long checkTourAlreadyPresentForSameEmpidAndSameDates(String tourid, String empid,String DepartureDate,String ArrivalDate)throws Exception
	{
		String fromdate = DateTimeFormatUtil.RegularToSqlDate(DepartureDate);
		String todate = DateTimeFormatUtil.RegularToSqlDate(ArrivalDate);
		return dao.checkTourAlreadyPresentForSameEmpidAndSameDates( tourid,  empid, fromdate, todate);
	}

	@Override
	public long checkTourCount(String empno, String DepartureDate, String ArrivalDate) throws Exception {
		String fromdate = DateTimeFormatUtil.RegularToSqlDate(DepartureDate);
		String todate = DateTimeFormatUtil.RegularToSqlDate(ArrivalDate);
		return dao.checkTDAlreadyPresentForSameEmpidAndSameDates(empno,fromdate,todate);
	}
	
	@Override
	public Long ModifyTourApply(TourApplyDto dto)throws Exception
	{
		long result =  dao.UpdateTourAppply(dto);
		
		if(result>0) {
			TourApply apply = new TourApply();
			apply.setEmpNo(dto.getEmpNo());
			apply.setTourApplPreviousId(dto.getTourApplyId());
			apply.setTourNo(dto.getTourNo().replace("/", "_"));
			apply.setStayFrom(dto.getStayFrom());
			apply.setStayTo(dto.getStayTo());
			apply.setStayPlace(dto.getStayPlace());
			apply.setEJPFrom(dto.getEJPFrom());
			apply.setEJPTo(dto.getEJPTo());
			apply.setPurpose(dto.getPurpose());
			apply.setAirTravJust(dto.getAirTravJust());
			apply.setAdvancePropsed(dto.getAdvancePropsed());
			apply.setEmpNo(dto.getEmpNo());
			apply.setDivisionId(dto.getDivisionId());
			apply.setEarliestTime(dto.getEarliestTime());
			apply.setEarliestDate(dto.getEarliestDate());
			apply.setEarliestPlace(dto.getEarliestPlace());
			apply.setCreatedBy(dto.getCreatedBy());
			apply.setCreatedDate(dto.getCreatedDate());
			apply.setIsActive(dto.getIsActive());
			apply.setInitiatedDate(dto.getInitiatedDate());
			apply.setTourStatusCode(dto.getTourStatusCode());
			apply.setIsActive(dto.getIsActive());
		Long TourApplyId = dao.AddTourapply(apply);
			
		if(TourApplyId>0) {
			
			if(dto.getAdvancePropsed().equalsIgnoreCase("Y") && TourApplyId >0 ) {
				TourAdvance touradvance = new TourAdvance();
					touradvance.setTotalProposedAmt(dto.getTotalProposedAmount());
					touradvance.setTourFare(dto.getTourFare());
					touradvance.setTourfareFrom(dto.getTourfareFrom());
					touradvance.setTourfareTo(dto.getTourfareTo());
					touradvance.setBoardingDays(dto.getBoardingDays());
					touradvance.setBoardingPerDay(dto.getBoardingPerDay());
					touradvance.setPerDayAllowance(dto.getPerDayAllowance());
					touradvance.setAllowanceDays(dto.getAllowanceDays());
					touradvance.setAllowanceFromDate(dto.getAllowanceFromDate());
					touradvance.setAllowanceToDate(dto.getAllowanceToDate());
					touradvance.setStatus(dto.getTourStatusCode());
					touradvance.setIsActive(dto.getIsActive());
					touradvance.setCreatedBy(dto.getCreatedBy());
					touradvance.setCreatedDate(dto.getCreatedDate());
					touradvance.setTourApplyId(TourApplyId);
				dao.AddTouradvance(touradvance);
			}
			
			for(int i=0;i<dto.getTourDates().length;i++) {
				TourOnwardReturn tourdetails = new TourOnwardReturn(); 
					tourdetails.setTourApplyId(TourApplyId);
					tourdetails.setTourDate(DateTimeFormatUtil.dateConversionSql(dto.getTourDates()[i]));
					tourdetails.setTourTime(dto.getTourTimes()[i]);
					tourdetails.setModeId(Long.parseLong(dto.getModeofTravel()[i]));
					tourdetails.setFromCityId(Long.parseLong(dto.getFromCity()[i]));
					tourdetails.setToCityId(Long.parseLong(dto.getToCity()[i]));
				dao.AddTourOnwardReturn(tourdetails);
			}
			
			// Tour Transaction 
			TourTransaction transaction = new TourTransaction();
				transaction.setActionBy(dto.getEmpNo());
				transaction.setActionDate(dto.getInitiatedDate());
				transaction.setTourApplyId(TourApplyId);
				transaction.setTourStatusCode("INI");				
			
			 dao.AddTourTransaction(transaction);
		}
		  return TourApplyId;
		  
		}else {
			
			return result;
		}
	}
	
	@Override	
	public List<Object[]> GetTourApprovedList(String empno , String fromdate , String todate )throws Exception
	{
		return dao.GetTourApprovedList(empno, fromdate , todate);
	}
	@Override
	public TourAdvance  GetTourAdvanceData(Long tourid) throws Exception
	{
		return dao.GetTourAdvanceData(tourid);
	}

	@Override
	public List<Object[]> GetPAndAList()throws Exception
	{
		return dao.GetPAndAList();
	}
	
	@Override
	public long UpdateIssueOrder(String tourapplyid , String  issueddate ,String  issueby ,String Username , String remraks)throws Exception
	{
		long result =  dao.UpdateIssueOrder(tourapplyid , issueddate , issueby , Username);
		if(result>0) {
			TourTransaction transaction=new TourTransaction();
			transaction.setTourRemarks(remraks);
	        transaction.setActionBy(issueby);
	        transaction.setActionDate(sdtf.format(new Date()));
	        transaction.setTourApplyId(Long.parseLong(tourapplyid));
	        transaction.setTourStatusCode("MOI");
	        long trns=dao.AddTourTransaction(transaction);
		}
		return result;
	}
	
	@Override
	public long UpdateTourAdvanceRelesed(String tourapplyid ,String empno, String tourAdvance ,String Username ,String remarks)throws Exception
	{
		long result =  dao.UpdateTourAdvanceRelesed(tourapplyid , tourAdvance , Username);
		if(result>0) {
			TourTransaction transaction=new TourTransaction();
			transaction.setTourRemarks(remarks);
	        transaction.setActionBy(empno);
	        transaction.setActionDate(sdtf.format(new Date()));
	        transaction.setTourApplyId(Long.parseLong(tourapplyid));
	        transaction.setTourStatusCode("TAR");
	        long trns=dao.AddTourTransaction(transaction);
		}
		return result;
	}
	
	@Override
	public int ForwardTour(String tourapplyid , String empno , String remarks)throws Exception
	{
		int result = dao.ForwardTour(tourapplyid , empno );
		
		TourTransaction transaction = new TourTransaction();
		transaction.setActionBy(empno);
		transaction.setActionDate(sdtf.format(new Date()));
		transaction.setTourApplyId(Long.parseLong(tourapplyid));
		transaction.setTourStatusCode("FWD");
		transaction.setTourRemarks(remarks);
		dao.AddTourTransaction(transaction);

		return result;
	}
	@Override
	public int CancelForwardTour(String tourapplyid , String empno , String remarks)throws Exception
	{
		int result = dao.CancelForwardTour(tourapplyid , empno );
		
		TourTransaction transaction = new TourTransaction();
		transaction.setActionBy(empno);
		transaction.setActionDate(sdtf.format(new Date()));
		transaction.setTourApplyId(Long.parseLong(tourapplyid));
		transaction.setTourStatusCode("CBU");
		transaction.setTourRemarks(remarks);
		dao.AddTourTransaction(transaction);

		return result;
	}
	
	@Override
	public Object[] GetDivisionHeadandDGMPAFA(String empno)throws Exception
	{
		return dao.GetDivisionHeadandDGMPAFA(empno);
	}
	@Override
	public Long EmpNotificationForTour(EMSNotification notification)throws Exception
	{
		return dao.EmpNotificationForTour(notification);
	}
	@Override
	public List<Object[]> GetTourAmendList(String empno)throws Exception
	{
		return dao.GetTourAmendList(empno);
	}
	@Override
	public List<Object[]> GetTourAmendedList(String empno)throws Exception
	{
		return dao.GetTourAmendedList(empno);
	}
}
