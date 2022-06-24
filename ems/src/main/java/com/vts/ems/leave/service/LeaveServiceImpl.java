package com.vts.ems.leave.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.leave.dao.LeaveDaoImpl;
import com.vts.ems.leave.dto.LeaveApplyDto;
import com.vts.ems.leave.model.LeaveAppl;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.leave.model.LeaveTransaction;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;


@Service
public class LeaveServiceImpl implements LeaveService{

	private static final Logger logger = LogManager.getLogger(LeaveServiceImpl.class);
	DateTimeFormatUtil sdf=new DateTimeFormatUtil();
	
	
	@Autowired
	private LeaveDaoImpl dao;
	
	@Override
	public List<Object[]> PisHolidayList(String year) throws Exception {
		logger.info(new Date() +"Inside  PisHolidayList");
		return dao.PisHolidayList(year);
	}

	@Override
	public List<Object[]> LeaveCreditList(String month, String year) throws Exception {
		
		return dao.LeaveCreditList(month, year);
	}

	@Override
	public List<Employee> EmpList() throws Exception {
		
		return dao.EmpList();
	}

	@Override
	public List<Object[]> CreditList(String month) throws Exception {
		
		return dao.CreditList(month);
	}

	@Override
	public List<Object[]> LeaveCreditPreview(String month, String year,String emmNo) throws Exception {
		
		return dao.LeaveCreditPreview(month, year, emmNo);
	}

	@Override
	public long LeaveCredited(String month, String year, String emmNo) throws Exception {
		long result=0;
		try {
			for(Object[] obj:dao.LeaveCreditPreview(month, year, emmNo)) {
				LeaveRegister register=new LeaveRegister();
				register.setEMPID(obj[0].toString());
				if(obj[9]!=null&&"N".equalsIgnoreCase(obj[9].toString())){
				register.setCL(Double.parseDouble(obj[3].toString()));
				}else if(obj[9]!=null&&"Y".equalsIgnoreCase(obj[9].toString())){
					register.setCL(Double.parseDouble(obj[8].toString()));
				}
				register.setEL(Integer.parseInt(obj[4].toString()));
				register.setHPL(Integer.parseInt(obj[5].toString()));
				register.setCML(Integer.parseInt(obj[6].toString()));
				register.setRH(Integer.parseInt(obj[7].toString()));
				register.setEL_LAPSE(0);
				register.setML(0);
				register.setPL(0);
				register.setSL(0);
				register.setCCL(0);
				register.setADV_EL(0);
				register.setADV_HPL(0);
				register.setEOL(0);
				register.setMONTH(month);
				register.setYEAR(year);
				register.setSTATUS("LKU");
				if("January".equalsIgnoreCase(month)){
				register.setFROM_DATE(year+"-01-01");
				register.setTO_DATE(year+"-01-01");
				}else if("July".equalsIgnoreCase(month)){
					register.setFROM_DATE(year+"-07-01");
					register.setTO_DATE(year+"-07-01");
				}
				register.setAPPL_ID("0");
				register.setCREDITED_BY("SYSTEM");
				register.setCREDITED_ON(sdf.getSqlDateAndTimeFormat().format(new Date()));
				register.setREMARKS("SYSTEM");
				result=dao.LeaveCreditInsert(register);
				
			}
		}catch (Exception e) {
			logger.error(new Date() +"Inside LeaveCreditInsert"+e);	
		}
		return result;
	}

	@Override
	public List<Object[]> LeaveCreditInd(String month, String year, String emmNo) throws Exception {
		
		return dao.LeaveCreditInd(month, year, emmNo);
	}

	@Override
	public List<Object[]> LeaveCreditById(String registerId) throws Exception {
		// TODO Auto-generated method stub
		return dao.LeaveCreditById(registerId);
	}

	@Override
	public long LeaveCreditedAddUpdate(LeaveRegister register, String type) throws Exception {
		long result=0;
		if("U".equalsIgnoreCase(type)) {
			
			result=dao.LeaveCreditUpdateById(register);
		}else {
			result=dao.LeaveCreditAddById(register);
		}
		return result;
	}

	@Override
	public List<Object[]> GetHolidays(String Type) throws Exception {
		
		return dao.GetHolidays(Type);
	}

	@Override
	public List<Object[]> EmpDetails(String EmpNo) throws Exception {
		
		return dao.EmpDetails(EmpNo);
	}

	@Override
	public List<Object[]> EmployeeList() throws Exception {
		
		return dao.EmployeeList();
	}

	@Override
	public List<Object[]> LeaveCode(String EmpNo) throws Exception {
		
		return dao.LeaveCode(EmpNo);
	}

	@Override
	public List<Object[]> purposeList() throws Exception {
		
		return dao.purposeList();
	}

	@Override
	public String[] LeaveCheck(LeaveApplyDto dto) throws Exception {
		String[] Result=new String[3]; 
		LabMaster lab=dao.getLabDetails().get(0);
		LeaveRegister register=CheckRegister(dto.getEmpNo());
		long days=0;
		Date startDate=sdf.getRegularDateFormat().parse(dto.getFromDate());
		Date endDate=sdf.getRegularDateFormat().parse(dto.getToDate());
		LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println(sdf.getSqlDateFormatLocalDate().format(end));
		  if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(end), "G")>0||dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(start), "G")>0) {
			Result[0]="From/To date should be on working day!";
			Result[1]="Fail";
			return Result;
			}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(end), "H")>0||dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(start), "H")>0) {
				Result[0]="From/To date should be on working day!";
				Result[1]="Fail";
				return Result;
			}else if(start.getDayOfWeek().toString().equals("SATURDAY")||start.getDayOfWeek().toString().equals("SUNDAY")||end.getDayOfWeek().toString().equals("SATURDAY")||end.getDayOfWeek().toString().equals("SUNDAY")) {
				Result[0]="From/To date should be on working day!";
				Result[1]="Fail";
				return Result;
			}
		    if(dto.getHandingOverEmpid()!=null&&!dto.getHandingOverEmpid().equalsIgnoreCase("NotSelected")) {
				
			}
	
		
		if(!lab.getLabCode().equalsIgnoreCase("STARC")) {
		   if(dto.getLeaveType().equalsIgnoreCase("0001")) {
				
				
				int rhcount=0;
				for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
					
					if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
				       
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
						
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
		                  
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		                	 rhcount++;
		                  }
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
		               	    	if(rhcount>0&&dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].toString().equals("A")) {
		               	    		Result[0]="You Cannot Prefix/Sufix RH";
		               				Result[1]="Fail";
		               				return Result;
		               	    	}
		               	    }else {
		               	    	Result[0]="CL Cannot Be Clubbed With Other Leaves Except RH";
	               				Result[1]="Fail";
	               				return Result;
		               	    }
							
		                 }else {
		                	 break; 
		                 }
					}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
						
					}else {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
		               	    	if(rhcount>0&&dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].toString().equals("A")) {
		               	    		Result[0]="You Cannot Prefix/Sufix with RH";
		               				Result[1]="Fail";
		               				return Result;
		               	    	}
		               	    }else {
		               	    	Result[0]="CL Cannot Be Clubbed With Other Leaves Except RH";
	               				Result[1]="Fail";
	               				return Result;
		               	    }
							
		                 }else {
		                	 break; 
		                 }
					}
					
				}
				

				
				for (LocalDate date = start; date.isBefore(end)|| date.isEqual(end) ; date = date.plusDays(1)) {
				    if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
					       
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
							
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
			                  
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
			                  }
							 days++;
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
			                 }
							 days++;
						}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {      
						}else {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
				            }else {
				            	days++;
				            	 
				            }
						}
				}
				
				for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
					   
					if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
					       
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
						
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
		                  
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		                	 rhcount++;
		                  }
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
		               	    	if(rhcount>0&&dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].toString().equals("F")) {
		               	    		Result[0]="You Cannot Prefix/Sufix with RH";
		               				Result[1]="Fail";
		               				return Result;
		               	    	}
		               	    }else {
		               	    	Result[0]="CL Cannot Be Clubbed With Other Leaves Except RH";
	               				Result[1]="Fail";
	               				return Result;
		               	    }
							
		                 }else {
		                	 break; 
		                 }
					}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
						
					}else {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].toString().equals("0001")) {
		               	    	if(rhcount>0&&dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].toString().equals("F")) {
		               	    		Result[0]="You Cannot Prefix/Sufix with RH";
		               				Result[1]="Fail";
		               				return Result;
		               	    	}
		               	    }else {
		               	    	Result[0]="CL Cannot Be Clubbed With Other Leaves Except RH";
	               				Result[1]="Fail";
	               				return Result;
		               	    }
							
		                 }else {
		                	 break; 
		                 }
					}
				}
			   
			   if(register.getCL()>=(dto.getHalfOrFull().equalsIgnoreCase("X")?(double)days:(double)days/2)) {
				   
				   Result[0]="You can Apply CL";
      				Result[1]="Pass";
      				Result[2]=String.valueOf(dto.getHalfOrFull().equalsIgnoreCase("X")?(double)days:(double)days/2);
      				return Result;
			   }else {
				   Result[0]="Insufficient Balance";
      				Result[1]="Fail";
      				return Result;
			   }
			   
		   }else if(dto.getLeaveType().equalsIgnoreCase("0002")&&register.getEL()>=days) {
				
				
				long holidayCount=0;
				for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
					
					if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
				       holidayCount++;
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
						holidayCount++;
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
		                  
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		                	 break; 
		                  }
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		               	 break; 
		                 }
					}else if(date.getDayOfWeek().equals("SATURDAY")||date.getDayOfWeek().equals("SUNDAY")) {
						holidayCount++;
					}else {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
			               	 break; 
			            }else {
			            	holidayCount=0;
			            	 break;
			            	 
			            }
					}
					
				}
				

				
				for (LocalDate date = start; date.isBefore(end)|| date.isEqual(end) ; date = date.plusDays(1)) {
				   
				    if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
					       if(!"0001".equalsIgnoreCase(dto.getLeaveType())) {
					    	   days++;
					       }
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
							if(!"0001".equalsIgnoreCase(dto.getLeaveType())) {
						    	   days++;
						       }
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
			                  
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist.";
	               				Result[1]="Fail";
	               				return Result;
			                  }else if("0005".equalsIgnoreCase(dto.getLeaveType())) {
						    	   days++;
						       }else if(!"0005".equalsIgnoreCase(dto.getLeaveType())) {
						    	   Result[0]="Its not RH Leave";
		               				Result[1]="Fail";
		               				return Result;  
						       }
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
			                 }
							 days++;
						}else if(date.getDayOfWeek().equals("SATURDAY")||date.getDayOfWeek().equals("SUNDAY")) {
							if(!"0001".equalsIgnoreCase(dto.getLeaveType())) {
						    	   days++;
						       }
						}else {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist.";
	               				Result[1]="Fail";
	               				return Result;
				            }else {
				            	days++;
				            	 
				            }
						}
				}
				
				for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
					   
				   
				}
				Result[0]="You Can Apply EL";
   				Result[1]="Pass";
   				Result[2]=String.valueOf(days);
   				return Result;
		   }else {
			   Result[0]="Insufficient Balance";
  				Result[1]="Fail";
  				return Result;
		   }
		   
			
		}else {
			Result[0]="Sitara Please Try Again";
			Result[1]="Fail";
			return Result;

		}
		
	}

	@Override
	public List<Object[]> OfficerDetails(String EmpNo) throws Exception {
		
		return dao.OfficerDetails(EmpNo);
	}

	@Override
	public LeaveRegister getRegister(String EmpNo) throws Exception {
		List<Object[]> regiList=dao.getRegister(EmpNo);
		double CL=0.0;
		int EL=0;
		int EL_LAPSE=0;
		int HPL=0;
		int CML=0;
		int RH=0;
		int ML=0;
		int PL=0;
		int CCL=0;
		int SL=0;
		int ADV_EL=0;
		int ADV_HPL=0;
		int EOL=0;
		if(regiList!=null&&regiList.size()>0) {
		for(Object[] obj:regiList) {
			if("LAU".equalsIgnoreCase(obj[14].toString())||"LSO".equalsIgnoreCase(obj[14].toString())) {
				CL-=Double.parseDouble(obj[2].toString());
				EL-=Integer.parseInt(obj[3].toString());
				HPL-=Integer.parseInt(obj[4].toString());
				CML-=Integer.parseInt(obj[5].toString());
				RH-=Integer.parseInt(obj[6].toString());
				CML-=Integer.parseInt(obj[7].toString());
				CCL-=Integer.parseInt(obj[8].toString());
				SL-=Integer.parseInt(obj[9].toString());
				ML-=Integer.parseInt(obj[10].toString());
				PL-=Integer.parseInt(obj[11].toString());
			}else {
				CL+=Double.parseDouble(obj[2].toString());
				EL+=Integer.parseInt(obj[3].toString());
				HPL+=Integer.parseInt(obj[4].toString());
				CML+=Integer.parseInt(obj[5].toString());
				RH+=Integer.parseInt(obj[6].toString());
				CML+=Integer.parseInt(obj[7].toString());
				CCL+=Integer.parseInt(obj[8].toString());
				SL+=Integer.parseInt(obj[9].toString());
				ML+=Integer.parseInt(obj[10].toString());
				PL+=Integer.parseInt(obj[11].toString());
			}
		}
		}
		LeaveRegister register=new LeaveRegister();
		register.setEMPID(EmpNo);
   		register.setCL(CL);
		register.setEL(EL);
		register.setHPL(HPL);
		register.setCML(CML);
		register.setRH(RH);
		register.setCCL(CCL);
		register.setEOL(EOL);
		register.setPL(PL);
		register.setML(ML);
		register.setSL(SL);
		return register;
	}

	public LeaveRegister CheckRegister(String EmpNo) throws Exception {
		List<Object[]> regiList=dao.getRegister(EmpNo);
		double CL=0.0;
		int EL=0;
		int EL_LAPSE=0;
		int HPL=0;
		int CML=0;
		int RH=0;
		int ML=0;
		int PL=0;
		int CCL=0;
		int SL=0;
		int ADV_EL=0;
		int ADV_HPL=0;
		int EOL=0;
		if(regiList!=null&&regiList.size()>0) {
		for(Object[] obj:regiList) {
			if("LAU".equalsIgnoreCase(obj[14].toString())||"LSO".equalsIgnoreCase(obj[14].toString())) {
				CL-=Double.parseDouble(obj[2].toString());
				EL-=Integer.parseInt(obj[3].toString());
				HPL-=Integer.parseInt(obj[4].toString());
				CML-=Integer.parseInt(obj[5].toString());
				RH-=Integer.parseInt(obj[6].toString());
				CML-=Integer.parseInt(obj[7].toString());
				CCL-=Integer.parseInt(obj[8].toString());
				SL-=Integer.parseInt(obj[9].toString());
				ML-=Integer.parseInt(obj[10].toString());
				PL-=Integer.parseInt(obj[11].toString());
			}else {
				CL+=Double.parseDouble(obj[2].toString());
				EL+=Integer.parseInt(obj[3].toString());
				HPL+=Integer.parseInt(obj[4].toString());
				CML+=Integer.parseInt(obj[5].toString());
				RH+=Integer.parseInt(obj[6].toString());
				CML+=Integer.parseInt(obj[7].toString());
				CCL+=Integer.parseInt(obj[8].toString());
				SL+=Integer.parseInt(obj[9].toString());
				ML+=Integer.parseInt(obj[10].toString());
				PL+=Integer.parseInt(obj[11].toString());
			}
		}
		}
		LeaveRegister register=new LeaveRegister();
		register.setEMPID(EmpNo);
   		register.setCL(CL);
		register.setEL(EL);
		register.setHPL(HPL);
		register.setCML(CML);
		register.setRH(RH);
		register.setCCL(CCL);
		register.setEOL(EOL);
		register.setPL(PL);
		register.setML(ML);
		register.setSL(SL);
		return register;
	}

	@Override
	public String[] applyLeaveAdd(LeaveApplyDto dto) throws Exception {
		String [] leaveChecked=LeaveCheck(dto);
		if(leaveChecked[1].equalsIgnoreCase("Pass")) {
			LeaveAppl appl=new LeaveAppl();
			long id=dao.getLeaveApplId(sdf.getYearFromRegularDate(dto.getFromDate()));
				appl.setApplId(sdf.getYearFromRegularDate(dto.getFromDate())+"/"+id+1);
				appl.setCreatedBy(dto.getUserId());
				appl.setCreatedDate(sdf.getSqlDateAndTimeFormat().format(new Date()));
				appl.setEmpId(dto.getEmpNo());
				appl.setFnAn(dto.getAnFN());
				appl.setFromDate(sdf.dateConversionSql(dto.getFromDate()));
				appl.setToDate(sdf.dateConversionSql(dto.getToDate()));
				appl.setLeaveAddress(dto.getLeaveAddress());
				appl.setLeaveAmend(0);
				appl.setLeaveCode(dto.getLeaveType());
				appl.setLeaveYear(String.valueOf(sdf.getYearFromRegularDate(dto.getFromDate())));
				appl.setLtc(dto.getLTC());
				appl.setPurLeave(dto.getPurLeave());
				appl.setRemarks(dto.getRemarks());
				appl.setTotalDays(Double.parseDouble(leaveChecked[2]));
				appl.setStatus("LAU");
				appl.setDivId(dao.EmpDetails(dto.getEmpNo()).get(0)[3].toString());
				if(dao.LeaveApplInsert(appl)>0) {
					
				 	
					Date startDate=sdf.getRegularDateFormat().parse(dto.getFromDate());
					Date endDate=sdf.getRegularDateFormat().parse(dto.getToDate());
					LocalDate start =LocalDate.parse(sdf.getSqlDateFormat().format(startDate));
					LocalDate end =LocalDate.parse(sdf.getSqlDateFormat().format(endDate));
					if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
						LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
						LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
						long dayslast = ChronoUnit.DAYS.between(start, lastdate)+1;
						long daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;

						
						
					}else {
						LeaveRegister register=new LeaveRegister();
					      register.setEMPID(dto.getEmpNo());
					      register.setCL(0);
						  register.setEL(0);
						  register.setHPL(0);
						  register.setCML(0);
						  register.setRH(0);
						  register.setEL_LAPSE(0);
						  register.setML(0);
						  register.setPL(0);
						  register.setSL(0);
						  register.setCCL(0);
						  register.setADV_EL(0);
						  register.setADV_HPL(0);
						  register.setEOL(0);
						  register.setMONTH(sdf.getMonthValFromRegularDate(dto.getFromDate()));
						  register.setYEAR(String.valueOf(sdf.getYearFromRegularDate(dto.getFromDate())));
						  register.setSTATUS("LAU");
							register.setAPPL_ID(appl.getApplId());
							register.setCREDITED_BY("SYSTEM");
							register.setCREDITED_ON(sdf.getSqlDateAndTimeFormat().format(new Date()));
							register.setREMARKS("APPLIED");
						    register.setFROM_DATE(sdf.getSqlDateFormat().format(startDate));
							register.setTO_DATE(sdf.getSqlDateFormat().format(endDate));
							long result=dao.LeaveCreditInsert(register);
					}
					
					
			
			        LeaveTransaction transaction=new LeaveTransaction();
				}   
		}
		return leaveChecked;
	}
	
	}
