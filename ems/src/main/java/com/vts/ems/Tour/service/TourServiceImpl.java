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

import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.Admin.dao.AdminDao;
import com.vts.ems.Tour.dao.TourDao;
import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.model.TourTransaction;
import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.leave.model.LeaveTransaction;
import com.vts.ems.master.dao.MasterDao;
import com.vts.ems.master.service.MasterService;
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
			apply.setRemarks(dto.getRemarks());
			apply.setTourStatusCode(dto.getTourStatusCode());
		Long TourApplyId = dao.AddTourapply(apply);
		
		
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
			transaction.setTourRemarks(dto.getRemarks());
		
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
	public String[]	checkTDAlreadyPresentForSameEmpidAndSameDates(String empno,String DepartureDate,String ArrivalDate)throws Exception
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
					Long checkTDAlreadyPresentForSameEmpidAndSameDate = dao.checkTDAlreadyPresentForSameEmpidAndSameDates(empno,fromdate,todate);
					if (checkTDAlreadyPresentForSameEmpidAndSameDate >=0) {
								Result[0]="You Can Apply";
								Result[1]="Pass";
								return Result;
							} else {
								Result[0]="TD Already Present For Same Period";
								Result[1]="Fail";
								Result[2]=String.valueOf(checkTDAlreadyPresentForSameEmpidAndSameDate);
								return Result;
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
		apply.setRemarks(dto.getRemarks());
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
	public int ForwardTour(String tourapplyid , String empno)throws Exception
	{
		int result = dao.ForwardTour(tourapplyid , empno );
		
		TourTransaction transaction = new TourTransaction();
		transaction.setActionBy(empno);
		transaction.setActionDate(sdtf.format(new Date()));
		transaction.setTourApplyId(Long.parseLong(tourapplyid));
		transaction.setTourStatusCode("FWD");
		
		dao.AddTourTransaction(transaction);

		return result;
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
		Object[]  fapa = dao.GetPAFADetails();
		if(dto.getApprove()!=null) {
			for(int i=0;i<dto.getApprove().length;i++) {
				try {
					   
					String status = dto.getApprove()[i].split("_")[1];
					if(status.equalsIgnoreCase("FWD")) {
						//notification.setEmpNo("");
						dto.setStatus("VDH");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
						dao.getTourUpdate(dto);
					}else if(status.equalsIgnoreCase("VDH")) {
						//notification.setEmpNo("");
						dto.setStatus("ABD");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
						dao.getTourUpdate(dto);
					}else if(status.equalsIgnoreCase("ABD")) {
						//notification.setEmpNo("");
						String funds=req.getParameter("Funds"+dto.getApprove()[i].split("_")[0]);
						dto.setFunds(funds!=null && funds.equalsIgnoreCase("true")? "Y" : "N");
						dto.setStatus("ABF");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
						dao.getTourUpdate(dto);
					}else if(status.equalsIgnoreCase("ABF")) {
						//notification.setEmpNo("");
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
						
						if(fapa!=null && fapa.length>0) {
							EMSNotification notification = new EMSNotification(); 
							notification.setEmpNo(fapa[1].toString());
							notification.setCreatedBy(dto.getUserName());
							notification.setCreatedDate(sdtf.format(new Date()));
							notification.setNotificationBy(dto.getEmpNo());
							notification.setNotificationMessage("Tour Sanctioned by "+dto.getUserName());
							notification.setNotificationDate(sdtf.format(new Date()));
							notification.setNotificationUrl("TourSanctionedlist.htm");
							notification.setIsActive(1);
							long value= dao.EmpNotificationForTour(notification);
						}
						EMSNotification notification2 = new EMSNotification(); 
						notification2.setEmpNo(req.getParameter("EmpNo"+dto.getApplId()));
						notification2.setCreatedBy(dto.getUserName());
						notification2.setCreatedDate(sdtf.format(new Date()));
						notification2.setNotificationBy(dto.getEmpNo());
						notification2.setNotificationMessage("Tour Sanctioned by "+dto.getUserName());
						notification2.setNotificationDate(sdtf.format(new Date()));
						notification2.setNotificationUrl("TourSanctionedlist.htm");
						notification2.setIsActive(1);
						long value1= dao.EmpNotificationForTour(notification2);
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
					String status = dto.getReject()[i].split("_")[1];
					if(status.equalsIgnoreCase("FWD")) {
						dto.setStatus("RDH");
					}else if(status.equalsIgnoreCase("VDH")) {
						dto.setStatus("RDG");
					}else if(status.equalsIgnoreCase("ABD")) {
						dto.setStatus("RBF");
					}else if(status.equalsIgnoreCase("ABF")) {
						dto.setStatus("RBP");
					}else if(status.equalsIgnoreCase("ABP")) {
						dto.setStatus("RBC");
					}
					dto.setApplId(dto.getReject()[i].split("_")[0]);

					dao.getTourUpdate(dto);
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
		if(dto.getApprove()!=null) {
			for(int i=0;i<dto.getApprove().length;i++) {
				try {
					   
					String status = dto.getApprove()[i].split("_")[1];
					if(status.equalsIgnoreCase("CBU")) {
						dto.setStatus("CAA");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}else if(status.equalsIgnoreCase("CAA")) {
						dto.setStatus("CAG");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}else if(status.equalsIgnoreCase("CAG")) {
						dto.setStatus("CAF");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}else if(status.equalsIgnoreCase("CAF")) {
						dto.setStatus("CAP");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}else if(status.equalsIgnoreCase("CAP")) {
						dto.setStatus("CAC");
						dto.setApplId(dto.getApprove()[i].split("_")[0]);
					}
					dao.getTourUpdate(dto);
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
					String status = dto.getReject()[i].split("_")[1];
					if(status.equalsIgnoreCase("CBU")) {
						dto.setStatus("CAD");
					}else if(status.equalsIgnoreCase("CAA")) {
						dto.setStatus("CDG");
					}else if(status.equalsIgnoreCase("CAG")) {
						dto.setStatus("CDF");
					}else if(status.equalsIgnoreCase("CAF")) {
						dto.setStatus("CDP");
					}else if(status.equalsIgnoreCase("CAP")) {
						dto.setStatus("CDC");
					}
					dto.setApplId(dto.getReject()[i].split("_")[0]);

					dao.getTourUpdate(dto);
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
	public List<Object[]> GetSanctionList(String empno)throws Exception
	{
		return dao.GetSanctionList(empno);
	}
	@Override
	public int CancelTour(ApprovalDto dto)throws Exception
	{
		int result =  dao.CancelTour(dto);
		if(result>0) {
			TourTransaction transaction=new TourTransaction();
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


}
