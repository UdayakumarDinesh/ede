package com.vts.ems.leave.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.leave.dao.LeaveApplRepo;
import com.vts.ems.leave.dao.LeaveDaoImpl;
import com.vts.ems.leave.dao.LeaveSaRaRepo;
import com.vts.ems.leave.dao.McFcRepo;
import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.leave.dto.LeaveApplyDto;
import com.vts.ems.leave.dto.LeaveAttachmentDto;
import com.vts.ems.leave.model.LeaveAppl;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.leave.model.LeaveMC_FC;
import com.vts.ems.leave.model.LeaveRaSa;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.leave.model.LeaveTransaction;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;



@Service
public class LeaveServiceImpl implements LeaveService{

	private static final Logger logger = LogManager.getLogger(LeaveServiceImpl.class);
	DateTimeFormatUtil sdf=new DateTimeFormatUtil();
	
	@Autowired
	LeaveApplRepo applrepo;
	@Autowired
	private LeaveDaoImpl dao;
	
	@Autowired
	private LeaveSaRaRepo saRaRepo;
	
	@Autowired
	private McFcRepo mcFcRepo;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	@Override
	public List<Object[]> PisHolidayList(String year) throws Exception {
		return dao.PisHolidayList(year);
	}

	@Override
	public List<Object[]> LeaveCreditList(String month, String year) throws Exception {
		
		return dao.LeaveCreditList(month, year);
	}

	@Override
	public  List<Object[]> EmpList() throws Exception {
		
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
		logger.info(new Date() +"Inside SERVICE LeaveCredited ");
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
				register.setCML(0);
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
			logger.error(new Date() +"Inside SERVICE LeaveCreditInsert "+e);	
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
		logger.info(new Date() +"Inside SERVICE  LeaveCreditedAddUpdate ");
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
		logger.info(new Date() +"Inside SERVICE  LeaveCheck ");
		String[] Result=new String[5]; 
		LabMaster lab=dao.getLabDetails().get(0);
		LeaveRegister register=getRegister(dto.getEmpNo(),sdf.getCurrentYear());
		long days=0;
		long dayslast =0;
		long daysfirst =0;
		Date startDate=sdf.getRegularDateFormat().parse(dto.getFromDate());
		Date endDate=sdf.getRegularDateFormat().parse(dto.getToDate());
		LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if(dto.getLeaveType().equalsIgnoreCase("0001")||dto.getLeaveType().equalsIgnoreCase("0005")) {

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
			
			}
		
		if(lab.getLabCode().equalsIgnoreCase("STARC")) {
			//Leave Check for CL
		   if(dto.getLeaveType().equalsIgnoreCase("0001")) {
				
				
				int rhcount=0;
				//Leave Check for previous date
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
				//Leave Check for year split
				if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
					LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
					LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
					
					//Leave Check for applied  date in this yaer
				for (LocalDate date = start; date.isBefore(lastdate)|| date.isEqual(lastdate) ; date = date.plusDays(1)) {
				    if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
					       
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
							
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
			                  
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
			                  }
							dayslast++;
							days++;
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
			                 }
							dayslast++;
							days++;
						}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {      
						}else {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
				            }else {
				            	dayslast++;
				            	days++;
				            	 
				            }
						}
				}
				//Leave Check for applied next year
				for (LocalDate date = firstdate; date.isBefore(end)|| date.isEqual(end) ; date = date.plusDays(1)) {
				    if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
					       
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
							
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
			                  
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(firstdate), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
			                  }
							daysfirst++;
							days++;
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(firstdate), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
			                 }
							daysfirst++;
							days++;
						}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {      
						}else {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(firstdate), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="Leave Already Exist";
	               				Result[1]="Fail";
	               				return Result;
				            }else {
				            	daysfirst++;
				            	days++;
				            	 
				            }
						}
				}
				
				}else {
					//Leave Check for no year split case
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
					
				}
				//Leave Check for post date
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
      				Result[3]=String.valueOf(dayslast);
      				Result[4]=String.valueOf(daysfirst);
      				return Result;
			   }else {
				   Result[0]="Insufficient Balance";
      				Result[1]="Fail";
      				return Result;
			   }
			   
		   }else if(dto.getLeaveType().equalsIgnoreCase("0002")&&register.getEL()>=days) {
			 //Leave Check for EL
				
				long holidayCount=0;
				//Leave Check for pre date
				for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
					
					if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
						holidayCount++;
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
						holidayCount++;	
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
		                  
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
							Result[0]="EL Cannot Be Clubbed With  RH";
               				Result[1]="Fail";
               				return Result;
							
		                  }else {
		                	  holidayCount=0;
		                	  break;
		                	  	
		                  }
						
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		               	    if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
		               	          break;
		               	    }else {
		               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
			               	    	break;
			               	    }else {
			               	    	Result[0]="EL Cannot Be Clubbed With  CL";
		               				Result[1]="Fail";
		               				return Result;
			               	    }
		               	    }
							
		                 }else {
		                	  holidayCount=0;
		                	  break;
		                 }
					}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
						holidayCount++;
					}else {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
							 if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
		               	          break;
		               	    }else {
		               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
			               	    	break;
			               	    }else {
			               	    	Result[0]="EL Cannot Be Clubbed With  CL";
		               				Result[1]="Fail";
		               				return Result;
			               	    }
		               	    }
							
							
		                 }else {
		                	 holidayCount=0;
		                	  break; 
		                 }
					}
					
				}
				List<Object[]> list=dao.checkLeaveEl(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(end));
				//Leave Check for applied date
				if(list!=null&&list.size()>0) {
                       
					Result[0]="Already Leave Exist";
       				Result[1]="Fail";
       				return Result;
				}else{
					//Leave Check for split year
					if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
						LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
						LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
						dayslast = ChronoUnit.DAYS.between(start, lastdate)+1+holidayCount;
						daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;
						Result[2]=String.valueOf(dayslast+daysfirst);
	      				Result[3]=String.valueOf(dayslast);
	      				Result[4]=String.valueOf(daysfirst);
						
					}else {
						days= ChronoUnit.DAYS.between(start, end)+1;

					}
				
				}
				
				//Leave Check for post date
				for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
					if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
					       
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
						
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
		                  
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
							Result[0]="You Should Modify Future Leave";
               				Result[1]="Fail";
               				return Result;
		                  }else {
		                	  break;
		                  }
					}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
		               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
		               	    	break;
		               	    }else {
		               	    	Result[0]="You Should Modify Future Leave";
	               				Result[1]="Fail";
	               				return Result;
		               	    }
							
		                 }else {
		                	 break; 
		                 }
					}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
						
					}else {
						if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
							 if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
			               	    	break;
			               	    }else {
			               	    	Result[0]="You Should Modify Future Leave";
		               				Result[1]="Fail";
		               				return Result;
			               	    }
							
		                 }else {
		                	 break; 
		                 }
					}   
				   
				}
				Result[0]="You Can Apply EL";
   				Result[1]="Pass";
   				Result[2]=String.valueOf(days);
   				return Result;
		   }else if(dto.getLeaveType().equalsIgnoreCase("0003")&&(register.getHPL()/2)>=days) {
				 //Leave Check for EL
				
					long holidayCount=0;
					//Leave Check for pre date
					for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
						
						if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
							holidayCount++;
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
							holidayCount++;	
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
			                  
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="CML Cannot Be Clubbed With  RH";
	               				Result[1]="Fail";
	               				return Result;
								
			                  }else {
			                	  holidayCount=0;
			                	  break;
			                	  	
			                  }
							
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
			               	    if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
			               	          break;
			               	    }else {
			               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
				               	    	break;
				               	    }else {
				               	    	Result[0]="CML Cannot Be Clubbed With  CL";
			               				Result[1]="Fail";
			               				return Result;
				               	    }
			               	    }
								
			                 }else {
			                	  holidayCount=0;
			                	  break;
			                 }
						}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
							holidayCount++;
						}else {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								 if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
			               	          break;
			               	    }else {
			               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
				               	    	break;
				               	    }else {
				               	    	Result[0]="CML Cannot Be Clubbed With  CL";
			               				Result[1]="Fail";
			               				return Result;
				               	    }
			               	    }
								
								
			                 }else {
			                	 holidayCount=0;
			                	  break; 
			                 }
						}
						
					}
					List<Object[]> list=dao.checkLeaveEl(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(end));
					//Leave Check for applied date
					if(list!=null&&list.size()>0) {
	                       
						Result[0]="Already Leave Exist";
	       				Result[1]="Fail";
	       				return Result;
					}else{
						//Leave Check for split year
						if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
							LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
							LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
							dayslast = ChronoUnit.DAYS.between(start, lastdate)+1+holidayCount;
							daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;
							Result[2]=String.valueOf(dayslast+daysfirst);
		      				Result[3]=String.valueOf(dayslast);
		      				Result[4]=String.valueOf(daysfirst);
							
						}else {
							days= ChronoUnit.DAYS.between(start, end)+1;

						}
					
					}
					
					//Leave Check for post date
					for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
						if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
						       
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
							
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
			                  
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								Result[0]="You Should Modify Future Leave";
	               				Result[1]="Fail";
	               				return Result;
			                  }else {
			                	  break;
			                  }
						}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
			               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
			               	    	break;
			               	    }else {
			               	    	Result[0]="You Should Modify Future Leave";
		               				Result[1]="Fail";
		               				return Result;
			               	    }
								
			                 }else {
			                	 break; 
			                 }
						}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
							
						}else {
							if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
								 if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
				               	    	break;
				               	    }else {
				               	    	Result[0]="You Should Modify Future Leave";
			               				Result[1]="Fail";
			               				return Result;
				               	    }
								
			                 }else {
			                	 break; 
			                 }
						}   
					   
					}
					Result[0]="You Can Apply CML";
	   				Result[1]="Pass";
	   				Result[2]=String.valueOf(days);
	   				return Result;
			   }else if(dto.getLeaveType().equalsIgnoreCase("0004")&&register.getHPL()>=days) {
						 //Leave Check for EL
						
						long holidayCount=0;
						//Leave Check for pre date
						for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
							
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
								holidayCount++;
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								holidayCount++;	
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="HPL Cannot Be Clubbed With  RH";
		               				Result[1]="Fail";
		               				return Result;
									
				                  }else {
				                	  holidayCount=0;
				                	  break;
				                	  	
				                  }
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="HPL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
				                 }else {
				                	  holidayCount=0;
				                	  break;
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								holidayCount++;
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="HPL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
									
				                 }else {
				                	 holidayCount=0;
				                	  break; 
				                 }
							}
							
						}
						List<Object[]> list=dao.checkLeaveEl(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(end));
						//Leave Check for applied date
						if(list!=null&&list.size()>0) {
		                       
							Result[0]="Already Leave Exist";
		       				Result[1]="Fail";
		       				return Result;
						}else{
							//Leave Check for split year
							if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
								LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
								LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
								dayslast = ChronoUnit.DAYS.between(start, lastdate)+1+holidayCount;
								daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;
								Result[2]=String.valueOf(dayslast+daysfirst);
			      				Result[3]=String.valueOf(dayslast);
			      				Result[4]=String.valueOf(daysfirst);
								
							}else {
								days= ChronoUnit.DAYS.between(start, end)+1;

							}
						
						}
						
						//Leave Check for post date
						for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
							       
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="You Should Modify Future Leave";
		               				Result[1]="Fail";
		               				return Result;
				                  }else {
				                	  break;
				                  }
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
				               	    	break;
				               	    }else {
				               	    	Result[0]="You Should Modify Future Leave";
			               				Result[1]="Fail";
			               				return Result;
				               	    }
									
				                 }else {
				                	 break; 
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="You Should Modify Future Leave";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
									
				                 }else {
				                	 break; 
				                 }
							}   
						   
						}
						Result[0]="You Can Apply HPL";
		   				Result[1]="Pass";
		   				Result[2]=String.valueOf(days);
		   				return Result;
				   }else if(dto.getLeaveType().equalsIgnoreCase("0005")&&register.getRH()>=days) {
						 //Leave Check for RH
					   
					   if(startDate.before(new Date())) {
	               	    	Result[0]="Applied Date For RH  Should be Future Date";
              				Result[1]="Fail";
              				return Result;
	               	    }
						
						long holidayCount=0;
						//Leave Check for pre date
						for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
							
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									holidayCount++;	
									
				                  }else {
				                	  holidayCount=0;
				                	  break;
				                	  	
				                  }
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	    	Result[0]="RH Cannot Be Clubbed With other leave except CL";
			               				Result[1]="Fail";
			               				return Result;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	holidayCount++;	
					               	    }
				               	    }
									
				                 }else {
				                	  break;
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
										    Result[0]="RH Cannot Be Clubbed With other leave except CL";
				               				Result[1]="Fail";
				               				return Result;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	holidayCount++;
					               	    }
				               	    }
									
									
				                 }else {
				             
				                	  break; 
				                 }
							}
							
						}
						//leave Date check
						for (LocalDate date = start; date.isBefore(end)|| date.isEqual(end) ; date = date.plusDays(1)) {
							
						    if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
					                  
									if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
										Result[0]="Leave Already Exist";
			               				Result[1]="Fail";
			               				return Result;
					                  }
									 days++;
								}else {
									Result[0]="Leave Date Should be RH";
		               				Result[1]="Fail";
		               				return Result;
								}
						}
						//Leave Check for post date
						for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
         
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
			
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="You Should Modify Future Leave";
		               				Result[1]="Fail";
		               				return Result;
				                  }else {
				                	  break;
				                  }
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
				               	    	break;
				               	    }else {
				               	    	holidayCount++;
				               	    }
									
				                 }else {
				                	 break; 
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
					               	    	break;
					               	    }else {
					               	    	holidayCount++;
					               	    }
									
				                 }else {
				                	 break; 
				                 }
							}   
						   
						}
						if(holidayCount>1) {
							Result[0]="You Cannot Prefix and Sufix Leave With RH";
               				Result[1]="Fail";
               				return Result;
						}
						Result[0]="You Can Apply RH";
		   				Result[1]="Pass";
		   				Result[2]=String.valueOf(days);
		   				return Result;
				   }else if(dto.getLeaveType().equalsIgnoreCase("0006")) {
						 //Leave Check for EL
						
						long holidayCount=0;
						//Leave Check for pre date
						for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
							
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
								holidayCount++;
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								holidayCount++;	
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="CCL Cannot Be Clubbed With  RH";
		               				Result[1]="Fail";
		               				return Result;
									
				                  }else {
				                	  holidayCount=0;
				                	  break;
				                	  	
				                  }
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="CCL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
				                 }else {
				                	  holidayCount=0;
				                	  break;
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								holidayCount++;
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="CCL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
									
				                 }else {
				                	 holidayCount=0;
				                	  break; 
				                 }
							}
							
						}
						List<Object[]> list=dao.checkLeaveEl(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(end));
						//Leave Check for applied date
						if(list!=null&&list.size()>0) {
		                       
							Result[0]="Already Leave Exist";
		       				Result[1]="Fail";
		       				return Result;
						}else{
							//Leave Check for split year
							if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
								LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
								LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
								dayslast = ChronoUnit.DAYS.between(start, lastdate)+1+holidayCount;
								daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;
								Result[2]=String.valueOf(dayslast+daysfirst);
			      				Result[3]=String.valueOf(dayslast);
			      				Result[4]=String.valueOf(daysfirst);
								
							}else {
								days= ChronoUnit.DAYS.between(start, end)+1;

							}
						
						}
						
						//Leave Check for post date
						for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
							       
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="You Should Modify Future Leave";
		               				Result[1]="Fail";
		               				return Result;
				                  }else {
				                	  break;
				                  }
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
				               	    	break;
				               	    }else {
				               	    	Result[0]="You Should Modify Future Leave";
			               				Result[1]="Fail";
			               				return Result;
				               	    }
									
				                 }else {
				                	 break; 
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="You Should Modify Future Leave";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
									
				                 }else {
				                	 break; 
				                 }
							}   
						   
						}
						Result[0]="You Can Apply CCL";
		   				Result[1]="Pass";
		   				Result[2]=String.valueOf(days);
		   				return Result;
				   }else if(dto.getLeaveType().equalsIgnoreCase("0008")) {
						 //Leave Check for EL
						
						long holidayCount=0;
						//Leave Check for pre date
						for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
							
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
								holidayCount++;
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								holidayCount++;	
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="SL Cannot Be Clubbed With  RH";
		               				Result[1]="Fail";
		               				return Result;
									
				                  }else {
				                	  holidayCount=0;
				                	  break;
				                	  	
				                  }
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="SL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
				                 }else {
				                	  holidayCount=0;
				                	  break;
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								holidayCount++;
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="SL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
									
				                 }else {
				                	 holidayCount=0;
				                	  break; 
				                 }
							}
							
						}
						List<Object[]> list=dao.checkLeaveEl(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(end));
						//Leave Check for applied date
						if(list!=null&&list.size()>0) {
		                       
							Result[0]="Already Leave Exist";
		       				Result[1]="Fail";
		       				return Result;
						}else{
							//Leave Check for split year
							if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
								LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
								LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
								dayslast = ChronoUnit.DAYS.between(start, lastdate)+1+holidayCount;
								daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;
								Result[2]=String.valueOf(dayslast+daysfirst);
			      				Result[3]=String.valueOf(dayslast);
			      				Result[4]=String.valueOf(daysfirst);
								
							}else {
								days= ChronoUnit.DAYS.between(start, end)+1;

							}
						
						}
						
						//Leave Check for post date
						for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
							       
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="You Should Modify Future Leave";
		               				Result[1]="Fail";
		               				return Result;
				                  }else {
				                	  break;
				                  }
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
				               	    	break;
				               	    }else {
				               	    	Result[0]="You Should Modify Future Leave";
			               				Result[1]="Fail";
			               				return Result;
				               	    }
									
				                 }else {
				                	 break; 
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="You Should Modify Future Leave";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
									
				                 }else {
				                	 break; 
				                 }
							}   
						   
						}
						Result[0]="You Can Apply SL";
		   				Result[1]="Pass";
		   				Result[2]=String.valueOf(days);
		   				return Result;
				   }else if(dto.getLeaveType().equalsIgnoreCase("0010")) {
						 //Leave Check for EL
						
						long holidayCount=0;
						//Leave Check for pre date
						for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
							
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
								holidayCount++;
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								holidayCount++;	
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="PL Cannot Be Clubbed With  RH";
		               				Result[1]="Fail";
		               				return Result;
									
				                  }else {
				                	  holidayCount=0;
				                	  break;
				                	  	
				                  }
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="PL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
				                 }else {
				                	  holidayCount=0;
				                	  break;
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								holidayCount++;
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="PL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
									
				                 }else {
				                	 holidayCount=0;
				                	  break; 
				                 }
							}
							
						}
						List<Object[]> list=dao.checkLeaveEl(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(end));
						//Leave Check for applied date
						if(list!=null&&list.size()>0) {
		                       
							Result[0]="Already Leave Exist";
		       				Result[1]="Fail";
		       				return Result;
						}else{
							//Leave Check for split year
							if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
								LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
								LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
								dayslast = ChronoUnit.DAYS.between(start, lastdate)+1+holidayCount;
								daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;
								Result[2]=String.valueOf(dayslast+daysfirst);
			      				Result[3]=String.valueOf(dayslast);
			      				Result[4]=String.valueOf(daysfirst);
								
							}else {
								days= ChronoUnit.DAYS.between(start, end)+1;

							}
						
						}
						
						//Leave Check for post date
						for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
							       
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="You Should Modify Future Leave";
		               				Result[1]="Fail";
		               				return Result;
				                  }else {
				                	  break;
				                  }
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
				               	    	break;
				               	    }else {
				               	    	Result[0]="You Should Modify Future Leave";
			               				Result[1]="Fail";
			               				return Result;
				               	    }
									
				                 }else {
				                	 break; 
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="You Should Modify Future Leave";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
									
				                 }else {
				                	 break; 
				                 }
							}   
						   
						}
						Result[0]="You Can Apply PL";
		   				Result[1]="Pass";
		   				Result[2]=String.valueOf(days);
		   				return Result;
				   }else if(dto.getLeaveType().equalsIgnoreCase("0009")) {
						 //Leave Check for EL
						
						long holidayCount=0;
						//Leave Check for pre date
						for (LocalDate date = start.minusDays(1); date.isAfter(start.minusDays(5)); date = date.minusDays(1)) {
							
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
								holidayCount++;
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								holidayCount++;	
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="EOL Cannot Be Clubbed With  RH";
		               				Result[1]="Fail";
		               				return Result;
									
				                  }else {
				                	  holidayCount=0;
				                	  break;
				                	  	
				                  }
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="EOL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
				                 }else {
				                	  holidayCount=0;
				                	  break;
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								holidayCount++;
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(!dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[1].equals("0001")) {
				               	          break;
				               	    }else {
				               	    	if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("F")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="EOL Cannot Be Clubbed With  CL";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
				               	    }
									
									
				                 }else {
				                	 holidayCount=0;
				                	  break; 
				                 }
							}
							
						}
						List<Object[]> list=dao.checkLeaveEl(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(end));
						//Leave Check for applied date
						if(list!=null&&list.size()>0) {
		                       
							Result[0]="Already Leave Exist";
		       				Result[1]="Fail";
		       				return Result;
						}else{
							//Leave Check for split year
							if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
								LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
								LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
								dayslast = ChronoUnit.DAYS.between(start, lastdate)+1+holidayCount;
								daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;
								Result[2]=String.valueOf(dayslast+daysfirst);
			      				Result[3]=String.valueOf(dayslast);
			      				Result[4]=String.valueOf(daysfirst);
								
							}else {
								days= ChronoUnit.DAYS.between(start, end)+1;

							}
						
						}
						
						//Leave Check for post date
						for (LocalDate date = end.plusDays(1); date.isBefore(end.plusDays(5)) ; date = date.plusDays(1)) {
							if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "G")>0) {
							       
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "H")>0) {
								
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "R")>0) {
				                  
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									Result[0]="You Should Modify Future Leave";
		               				Result[1]="Fail";
		               				return Result;
				                  }else {
				                	  break;
				                  }
							}else if(dao.checkHoliday(sdf.getSqlDateFormatLocalDate().format(date), "W")>0) {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
				               	    if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
				               	    	break;
				               	    }else {
				               	    	Result[0]="You Should Modify Future Leave";
			               				Result[1]="Fail";
			               				return Result;
				               	    }
									
				                 }else {
				                	 break; 
				                 }
							}else if(date.getDayOfWeek().toString().equals("SATURDAY")||date.getDayOfWeek().toString().equals("SUNDAY")) {
								
							}else {
								if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))!=null) {
									 if(dao.checkLeave(dto.getEmpNo(),sdf.getSqlDateFormatLocalDate().format(start), sdf.getSqlDateFormatLocalDate().format(date))[2].equals("A")) {
					               	    	break;
					               	    }else {
					               	    	Result[0]="You Should Modify Future Leave";
				               				Result[1]="Fail";
				               				return Result;
					               	    }
									
				                 }else {
				                	 break; 
				                 }
							}   
						   
						}
						Result[0]="You Can Apply EOL";
		   				Result[1]="Pass";
		   				Result[2]=String.valueOf(days);
		   				return Result;
				   }else {
			   Result[0]="Insufficient Balance";
  				Result[1]="Fail";
  				return Result;
		   }
		   
			
		}else {
			Result[0]="Please Try Again";
			Result[1]="Fail";
			return Result;

		}
		
	}

	@Override
	public List<Object[]> OfficerDetails(String EmpNo) throws Exception {
		
		return dao.OfficerDetails(EmpNo);
	}

	@Override
	public LeaveRegister getRegister(String EmpNo,String yr) throws Exception {
		logger.info(new Date() +"Inside SERVICE  getRegister ");
		List<String> regiyrs=dao.getRegisterYrs(EmpNo,yr);
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
		if(regiyrs!=null&&regiyrs.size()>0) {
		for(String obj:regiyrs) {
			LeaveRegister yrlyRegister=yrlyRegister(EmpNo,obj);
			if(!obj.equals(yr)) {
				EL=(yrlyRegister.getEL()+EL>300?300:yrlyRegister.getEL()+EL);
				HPL+=yrlyRegister.getHPL();
			    ML+=yrlyRegister.getML();
				PL+=yrlyRegister.getPL();
				CCL+=yrlyRegister.getCCL();
				SL+=yrlyRegister.getSL();
			}else {
				CL+=yrlyRegister.getCL();
				EL=yrlyRegister.getEL()+EL;
				HPL+=yrlyRegister.getHPL();
				RH+=yrlyRegister.getRH();
			    ML+=yrlyRegister.getML();
				PL+=yrlyRegister.getPL();
				CCL+=yrlyRegister.getCCL();
				SL+=yrlyRegister.getSL();
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

	public LeaveRegister yrlyRegister(String EmpNo,String year) throws Exception {
		logger.info(new Date() +"Inside SERVICE yrlyRegister ");
		List<Object[]> regiList=dao.getRegisterByYear(EmpNo,year);
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
			if("LAU".equalsIgnoreCase(obj[14].toString())||"LSO".equalsIgnoreCase(obj[14].toString())
					||"LRO".equalsIgnoreCase(obj[14].toString())) {
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
            }else if("LCO".equalsIgnoreCase(obj[14].toString())
            		||"LMU".equalsIgnoreCase(obj[14].toString())){
				
			}else if("LOB".equalsIgnoreCase(obj[14].toString())
					||"LKU".equalsIgnoreCase(obj[14].toString())
					||"DDD".equalsIgnoreCase(obj[14].toString())){
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
			}else
			{
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
	
	
	private LeaveAppl getLeaveModified(LeaveApplyDto dto,String [] leaveChecked) throws Exception{
		logger.info(new Date() +"Inside SERVICE getLeaveModified ");
		LeaveAppl appl=applrepo.findByApplId(dto.getApplId());
		LeaveAppl modifiedAppl=new LeaveAppl();
		if(dto.getApplId()!=null) {
			modifiedAppl=appl;
		}
		LeaveTransaction transaction=new LeaveTransaction();
		modifiedAppl.setCreatedBy(dto.getUserId());
		modifiedAppl.setCreatedDate(sdf.getSqlDateAndTimeFormat().format(new Date()));
		modifiedAppl.setFnAn(dto.getAnFN()!=null?dto.getAnFN():"X");
		modifiedAppl.setFromDate(sdf.dateConversionSql(dto.getFromDate()));
		modifiedAppl.setToDate(sdf.dateConversionSql(dto.getToDate()));
		modifiedAppl.setLeaveAddress(dto.getLeaveAddress());
		modifiedAppl.setLeaveYear(String.valueOf(sdf.getYearFromRegularDate(dto.getFromDate())));
		modifiedAppl.setLtc(dto.getLTC());
		modifiedAppl.setPurLeave(dto.getPurLeave());
		modifiedAppl.setRemarks(dto.getRemarks());
		modifiedAppl.setTotalDays(Double.parseDouble(leaveChecked[2]));
		modifiedAppl.setStatus("LAU");
		if("LME".equals(dto.getType())) {
			modifiedAppl.setLeaveAmend(appl.getLeaveAmend()+1);
			String[] applId=appl.getApplId().split("/");
			appl.setApplId(applId[0]+"_"+(appl.getLeaveAmend()+1)+"_"+applId[1]);
			appl.setStatus("LMU");
			applrepo.save(appl);
			dao.updateTransaction(modifiedAppl.getApplId(), appl.getApplId());
			LeaveTransaction transaction2=new LeaveTransaction();
			transaction2.setActionBy(dto.getActEmpNo());
	        transaction2.setActionDate(appl.getCreatedDate());
	        transaction2.setLeaveApplId(appl.getApplId());
	        transaction2.setLeaveRemarks(appl.getRemarks());
	        transaction2.setLeaveStatus("LMU");
	        long trns=dao.LeaveTransInsert(transaction2);
			modifiedAppl.setLeaveApplId(0);
			dao.LeaveApplInsert(modifiedAppl);
			transaction.setLeaveStatus("LAU");
		}else if("LEU".equals(dto.getType())) {
			
			applrepo.save(modifiedAppl);
			transaction.setLeaveStatus("LEU");
		}else {
			 long id=dao.getLeaveApplId(sdf.getYearFromRegularDate(dto.getFromDate()))+1;
			 modifiedAppl.setApplId(sdf.getYearFromRegularDate(dto.getFromDate())+"/"+id);
			 modifiedAppl.setCreatedBy(dto.getUserId());
			 modifiedAppl.setCreatedDate(sdf.getSqlDateAndTimeFormat().format(new Date()));
			 modifiedAppl.setEmpId(dto.getEmpNo());
			 modifiedAppl.setFnAn(dto.getAnFN()!=null?dto.getAnFN():"X");
			 modifiedAppl.setFromDate(sdf.dateConversionSql(dto.getFromDate()));
			 modifiedAppl.setToDate(sdf.dateConversionSql(dto.getToDate()));
			 modifiedAppl.setLeaveAddress(dto.getLeaveAddress());
			 modifiedAppl.setLeaveAmend(0);
			 modifiedAppl.setLeaveCode(dto.getLeaveType());
			 modifiedAppl.setLeaveYear(String.valueOf(sdf.getYearFromRegularDate(dto.getFromDate())));
			 modifiedAppl.setLtc(dto.getLTC());
			 modifiedAppl.setPurLeave(dto.getPurLeave());
			 modifiedAppl.setRemarks(dto.getRemarks());
			 modifiedAppl.setTotalDays(Double.parseDouble(leaveChecked[2]));
			 modifiedAppl.setStatus("LAU");
			 modifiedAppl.setDivId(dao.EmpDetails(dto.getEmpNo()).get(0)[3].toString());
			 modifiedAppl.setLeaveApplId(0);
			 dao.LeaveApplInsert(modifiedAppl);
		     transaction.setLeaveStatus(modifiedAppl.getStatus());
			 
		}
        
        transaction.setActionBy(dto.getActEmpNo());
        transaction.setActionDate(modifiedAppl.getCreatedDate());
        transaction.setLeaveApplId(modifiedAppl.getApplId());
        transaction.setLeaveRemarks(modifiedAppl.getRemarks());
        long trns=dao.LeaveTransInsert(transaction);
		return modifiedAppl;
	}
	
	

	@Override
	public String[] applyLeaveAdd(LeaveApplyDto dto) throws Exception {
		logger.info(new Date() +"Inside SERVICE  applyLeaveAdd ");
		String [] leaveChecked=LeaveCheck(dto);
		if(leaveChecked[1].equalsIgnoreCase("Pass")) {
			LeaveAppl appl=getLeaveModified(dto,leaveChecked);
				if(appl.getLeaveApplId()>0) {
					Date startDate=sdf.getRegularDateFormat().parse(dto.getFromDate());
					Date endDate=sdf.getRegularDateFormat().parse(dto.getToDate());
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
					  register.setMONTH(sdf.getMonthValFullFromRegularDate(dto.getFromDate()));
					  register.setYEAR(String.valueOf(sdf.getYearFromRegularDate(dto.getFromDate())));
					  register.setSTATUS("LAU");
					  register.setAPPL_ID(appl.getApplId());
					  register.setCREDITED_BY("SYSTEM");
					  register.setCREDITED_ON(sdf.getSqlDateAndTimeFormat().format(new Date()));
					  register.setREMARKS("APPLIED");
					  register.setFROM_DATE(sdf.getSqlDateFormat().format(startDate));
					  register.setTO_DATE(sdf.getSqlDateFormat().format(endDate));			
				 	
					try {
					LocalDate start =LocalDate.parse(sdf.getSqlDateFormat().format(startDate));
					LocalDate end =LocalDate.parse(sdf.getSqlDateFormat().format(endDate));
					if(sdf.getYearFromRegularDate(dto.getToDate())>sdf.getYearFromRegularDate(dto.getFromDate())) {
						LocalDate lastdate=LocalDate.parse(start.getYear()+"-12-31");
						LocalDate firstdate=LocalDate.parse(end.getYear()+"-01-01");
						long dayslast =Long.parseLong(leaveChecked[3]);
						long daysfirst =Long.parseLong(leaveChecked[4]);		
						//long dayslast = ChronoUnit.DAYS.between(start, lastdate)+1;
						//long daysfirst = ChronoUnit.DAYS.between(firstdate,end)+1;
                        if(dayslast>0) {
                        	if("0001".equals(dto.getLeaveType())) {
                          	  register.setCL((double)dayslast);
                            }else  if("0002".equals(dto.getLeaveType())) {
                          	  register.setEL((int)dayslast);
                            }else  if("0003".equals(dto.getLeaveType())) {
                          	  register.setCML((int)dayslast);
                            }else  if("0004".equals(dto.getLeaveType())) {
                          	  register.setHPL((int)dayslast);
                            }else  if("0005".equals(dto.getLeaveType())) {
                          	  register.setRH((int)dayslast);
                            }else  if("0006".equals(dto.getLeaveType())) {
                          	  register.setML((int)dayslast);
                            }else  if("0007".equals(dto.getLeaveType())) {
                          	  register.setCCL((int)dayslast);
                            }else  if("0008".equals(dto.getLeaveType())) {
                          	  register.setSL((int)dayslast);
                            }else  if("0009".equals(dto.getLeaveType())) {
                          	  register.setEOL((int)dayslast);
                            }else  if("0010".equals(dto.getLeaveType())) {
                          	  register.setPL((int)dayslast);
                            }else  if("0011".equals(dto.getLeaveType())) {
                          	  register.setEL((int)dayslast);
                            }
                        	
                        	register.setMONTH(sdf.getMonthValFullFromRegularDate(dto.getFromDate()));
      					    register.setYEAR(String.valueOf(sdf.getYearFromRegularDate(dto.getFromDate())));
      					    register.setFROM_DATE(sdf.getSqlDateFormat().format(startDate));
    					    register.setTO_DATE(sdf.getSqlDateFormatLocalDate().format(lastdate));			
    				 	 
                        	 long result=dao.LeaveCreditInsert(register);
                        }
                        if(daysfirst>0) {
                        	LeaveRegister registernew=new LeaveRegister();
                        	registernew.setEMPID(dto.getEmpNo());
                        	registernew.setCL(0);
                        	registernew.setEL(0);
                        	registernew.setHPL(0);
                        	registernew.setCML(0);
                        	registernew.setRH(0);
                        	registernew.setEL_LAPSE(0);
                        	registernew.setML(0);
                        	registernew.setPL(0);
                        	registernew.setSL(0);
                        	registernew.setCCL(0);
                        	registernew.setADV_EL(0);
                        	registernew.setADV_HPL(0);
                        	registernew.setEOL(0);
                        	registernew.setSTATUS("LAU");
                        	registernew.setAPPL_ID(appl.getApplId());
                        	registernew.setCREDITED_BY("SYSTEM");
                        	registernew.setCREDITED_ON(sdf.getSqlDateAndTimeFormat().format(new Date()));
                        	registernew.setREMARKS("APPLIED");
                        	if("0001".equals(dto.getLeaveType())) {
                        		registernew.setCL((double)daysfirst);
                              }else  if("0002".equals(dto.getLeaveType())) {
                            	  registernew.setEL((int)daysfirst);
                              }else  if("0003".equals(dto.getLeaveType())) {
                            	  registernew.setCML((int)daysfirst);
                              }else  if("0004".equals(dto.getLeaveType())) {
                            	  registernew.setHPL((int)daysfirst);
                              }else  if("0005".equals(dto.getLeaveType())) {
                            	  registernew.setRH((int)daysfirst);
                              }else  if("0006".equals(dto.getLeaveType())) {
                            	  registernew.setML((int)daysfirst);
                              }else  if("0007".equals(dto.getLeaveType())) {
                            	  registernew.setCCL((int)daysfirst);
                              }else  if("0008".equals(dto.getLeaveType())) {
                            	  registernew.setSL((int)daysfirst);
                              }else  if("0009".equals(dto.getLeaveType())) {
                            	  registernew.setEOL((int)daysfirst);
                              }else  if("0010".equals(dto.getLeaveType())) {
                            	  registernew.setPL((int)daysfirst);
                              }else  if("0011".equals(dto.getLeaveType())) {
                            	  registernew.setEL((int)daysfirst);
                              }
                        	registernew.setMONTH(sdf.getMonthValFullFromRegularDate(dto.getToDate()));
                        	registernew.setYEAR(String.valueOf(sdf.getYearFromRegularDate(dto.getToDate())));
                        	registernew.setFROM_DATE(sdf.getSqlDateFormatLocalDate().format(firstdate));
                        	registernew.setTO_DATE(sdf.getSqlDateFormat().format(endDate));
                        	  long result=dao.LeaveCreditInsert(registernew);	
                        }
						
						
					}else {
                          if("0001".equals(dto.getLeaveType())) {
                        	  register.setCL(Double.parseDouble(leaveChecked[2]));
                          }else  if("0002".equals(dto.getLeaveType())) {
                        	  register.setEL(Integer.parseInt(leaveChecked[2]));
                          }else  if("0003".equals(dto.getLeaveType())) {
                        	  register.setCML(Integer.parseInt(leaveChecked[2]));
                          }else  if("0004".equals(dto.getLeaveType())) {
                        	  register.setHPL((int)Math.round(Double.parseDouble(leaveChecked[2])));
                          }else  if("0005".equals(dto.getLeaveType())) {
                        	  register.setRH(Integer.parseInt(leaveChecked[2]));
                          }else  if("0006".equals(dto.getLeaveType())) {
                        	  register.setML(Integer.parseInt(leaveChecked[2]));
                          }else  if("0007".equals(dto.getLeaveType())) {
                        	  register.setCCL(Integer.parseInt(leaveChecked[2]));
                          }else  if("0008".equals(dto.getLeaveType())) {
                        	  register.setSL(Integer.parseInt(leaveChecked[2]));
                          }else  if("0009".equals(dto.getLeaveType())) {
                        	  register.setEOL(Integer.parseInt(leaveChecked[2]));
                          }else  if("0010".equals(dto.getLeaveType())) {
                        	  register.setPL(Integer.parseInt(leaveChecked[2]));
                          }else  if("0011".equals(dto.getLeaveType())) {
                        	  register.setEL(Integer.parseInt(leaveChecked[2]));
                          }
						  long result=dao.LeaveCreditInsert(register);
					}
					
					
			

			        leaveChecked[0]="Leave Applied Successfully";
			        if(dto.getHandingOverEmpid()!=null&&!dto.getHandingOverEmpid().equalsIgnoreCase("NotSelected")) {
			        	LeaveHandingOver ho = new LeaveHandingOver();
						ho.setApplId(appl.getApplId());
						ho.setDivisionId(Long.parseLong(appl.getDivId()));
						ho.setFromEmpId(appl.getEmpId());
						ho.setToEmpId(dto.getHandingOverEmpid());
						ho.setFromDate(appl.getFromDate());
						ho.setToDate(appl.getToDate());
						ho.setStatus("A");
						ho.setLoginType(dao.EmpDetails(dto.getEmpNo()).get(0)[4].toString());
						ho.setAppliedDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
						ho.setCreatedBy(dto.getUserId());
						ho.setCreateDate(DateTimeFormatUtil.getSqlDateAndTimeFormat().format(new Date()));
						ho.setIsActive(1);
						dao.AddHandingOver(ho);
					}
					}
					catch (Exception e) {
						logger.error(new Date() +"Inside SERVICE  LeaveRegisterList "+e);
						 e.printStackTrace();
				         leaveChecked[1]="Fail";
				         leaveChecked[0]="Leave Apply unsuccessful, if added delete that leave please.";
					}
			        
				}   
		}
		return leaveChecked;
	}

	@Override
	public List<Object[]> getAppliedLeave(String EmpNo) throws Exception {
	
		return dao.getAppliedLeave(EmpNo);
	}

	@Override
	public List<LeaveRegister> LeaveRegisterList(String EmpNo, String yr) throws Exception {
		logger.info(new Date() +"Inside SERVICE  LeaveRegisterList ");
		List<Object[]> regiList=dao.getRegisterByYear(EmpNo,yr);
		List<LeaveRegister>  regiall=new ArrayList<LeaveRegister>();
 		if(regiList!=null&&regiList.size()>0) {
		for(Object[] obj:regiList) {
			if(!"LOB".equals(obj[14].toString())) {
			LeaveRegister register=new LeaveRegister();
			register.setSTATUS(obj[14].toString());
			register.setTO_DATE(sdf.getRegularDateFormat().format(obj[17]));
			register.setFROM_DATE(sdf.getRegularDateFormat().format(obj[16]));
			register.setAPPL_ID(obj[18].toString());
			register.setEMPID(EmpNo);
	   		register.setCL(Double.parseDouble(obj[2].toString()));
			register.setEL(Integer.parseInt(obj[3].toString()));
			register.setHPL(Integer.parseInt(obj[4].toString()));
			register.setCML(Integer.parseInt(obj[5].toString()));
			register.setRH(Integer.parseInt(obj[6].toString()));
			register.setCCL(Integer.parseInt(obj[7].toString()));
			//register.setEOL(Integer.parseInt(obj[8].toString()));
			register.setPL(Integer.parseInt(obj[10].toString()));
			register.setML(Integer.parseInt(obj[9].toString()));
			register.setSL(Integer.parseInt(obj[8].toString()));
			regiall.add(register);
			}
		}
		}
		
		return regiall;
	}
	
	
	@Override
	public LeaveRegister RegisterOpening(String EmpNo, String yr) throws Exception {
		logger.info(new Date() +"Inside SERVICE RegisterOpening ");
		LeaveRegister last=getRegister(EmpNo,String.valueOf(Integer.parseInt(yr)-1));
		LeaveRegister opening=new LeaveRegister();
		try {
			opening=dao.getOpeningBalance(EmpNo, yr);
		}catch (Exception e) {
		}
		LeaveRegister register=new LeaveRegister();
		register.setEMPID(EmpNo);
   		register.setCL(opening.getCL());
		register.setEL((last.getEL()>300?300:last.getEL())+opening.getEL());
		register.setHPL(last.getHPL()+opening.getHPL());
		register.setRH(opening.getRH());
		register.setCCL(last.getCCL()+opening.getCCL());
		register.setML(last.getML()+opening.getML());
		register.setPL(last.getPL()+opening.getPL());
		register.setEOL(last.getEOL()+opening.getEOL());
		register.setSL(last.getSL()+opening.getSL());
		
		return register;
	}

	@Override
	public Object[] getEmployee(String empno) throws Exception {
		
		return dao.getEmployee(empno);
	}

	@Override
	public List<Object[]> LeaveApprovalGh(String empNo) throws Exception {
		
		return dao.LeaveApprovalGh(empNo);
	}

	@Override
	public List<Object[]> LeaveTransaction(String applid) throws Exception {
		
		return dao.LeaveTransaction(applid);
	}

	@Override
	public Object[] LeavePrint(String applid) throws Exception {
		
		return dao.LeavePrint(applid);
	}

	@Override
	public Object[] getLabCode() throws Exception {
		
		return dao.getLabCode();
	}

	@Override
	public int getApproved(ApprovalDto dto,HttpServletRequest req) throws Exception {
		logger.info(new Date() +"Inside SERVICE getApproved ");
		int count=0;
		if(dto.getApprove()!=null) {
			for(int i=0;i<dto.getApprove().length;i++) {
				try {
					dto.setStatus(dto.getApprove()[i].split("_")[1]);
					dto.setApplId(dto.getApprove()[i].split("_")[0]);
					dao.getUpdateAppl(dto);
					dao.getUpdateRegister(dto);
					    LeaveTransaction transaction=new LeaveTransaction();
				        transaction.setActionBy(dto.getEmpNo());
				        transaction.setActionDate(sdf.getSqlDateAndTimeFormat().format(new Date()));
				        transaction.setLeaveApplId(dto.getApplId());
				        transaction.setLeaveStatus(dto.getStatus());
				        transaction.setLeaveRemarks(req.getParameter(dto.getApplId()));
				        long trns=dao.LeaveTransInsert(transaction);
				        count=1;
				}catch (Exception e) {
					count=0;
				}
			}
		}
		if(dto.getReject()!=null) {
			for(int i=0;i<dto.getReject().length;i++) {
				try {
					StringBuilder myName = new StringBuilder(dto.getReject()[i].split("_")[1]);
					myName.setCharAt(1, 'N');
					dto.setStatus(dto.getType().equals("DIR")?"DND":myName.toString());
					dto.setApplId(dto.getReject()[i].split("_")[0]);
					dao.getUpdateAppl(dto);
					dao.getUpdateRegister(dto);
					    LeaveTransaction transaction=new LeaveTransaction();
				        transaction.setActionBy(dto.getEmpNo());
				        transaction.setActionDate(sdf.getSqlDateAndTimeFormat().format(new Date()));
				        transaction.setLeaveApplId(dto.getApplId());
				        transaction.setLeaveStatus(dto.getStatus());
				        transaction.setLeaveRemarks(req.getParameter(dto.getApplId()));
				        long trns=dao.LeaveTransInsert(transaction);
				        count=1;
				}catch (Exception e) {
					count=0;
				}
			}
		}
		return count;
	}

	@Override
	public List<Object[]> getSanctionedLeave(String EmpNo) throws Exception {
	
		return dao.getSanctionedLeave(EmpNo);
	}

	@Override
	public int deleteLeave(ApprovalDto dto) throws Exception {
		logger.info(new Date() +"Inside SERVICE deleteLeave ");
		dao.deleteLeaveRegiHo(dto.getApplId());
		return dao.deleteLeave(dto);
	}
	
	@Override
	public int deleteLeaveRegiHo(String applid) throws Exception {
		
		return dao.deleteLeaveRegiHo(applid);
	}

	@Override
	public Object[] getLeaveData(String applid) throws Exception {
	
		return dao.getLeaveData(applid);
	}

	@Override
	public int getUpdateAppl(ApprovalDto dto) throws Exception {
		
		return dao.getUpdateAppl(dto);
	}

	@Override
	public int getUpdateRegister(ApprovalDto dto) throws Exception {
		
		return dao.getUpdateRegister(dto);
	}

	@Override
	public int getCancelLeave(ApprovalDto dto) throws Exception {
		logger.info(new Date() +"Inside SERVICE getCancelLeave ");
		int count=1;
		try {
		dto.setStatus("LCU");
		dao.getUpdateAppl(dto);
		dao.getUpdateRegister(dto);
		    LeaveTransaction transaction=new LeaveTransaction();
	        transaction.setActionBy(dto.getEmpNo());
	        transaction.setActionDate(sdf.getSqlDateAndTimeFormat().format(new Date()));
	        transaction.setLeaveApplId(dto.getApplId());
	        transaction.setLeaveStatus(dto.getStatus());
	        transaction.setLeaveRemarks("NA");
	        long trns=dao.LeaveTransInsert(transaction);
		}catch (Exception e) {
			logger.error(new Date() +"Inside SERVICE getCancelLeave "+ e);
			count=0;
		}     
		return count;
	}

	@Override
	public int laeveNotModified(String empno) throws Exception {
		
		return dao.laeveNotModified(empno);
	}

	@Override
	public List<Object[]> LeaveStatusList(String empNo) throws Exception {
		
		return dao.LeaveStatusList(empNo);
	}

	@Override
	public List<Object[]> LeaveApprovalDir(String empNo) throws Exception {
		
		return dao.LeaveApprovalGh(empNo);
	}

	@Override
	public List<Object[]> LeaveApprovalDirRecc(String empNo) throws Exception {
		
		return dao.LeaveApprovalDirRecc(empNo);
	}

	@Override
	public List<Object[]> LeaveApprovalDirNR(String empNo) throws Exception {
		// TODO Auto-generated method stub
		return dao.LeaveApprovalDirNR(empNo);
	}

	@Override
	public List<Object[]> LeaveApprovalAdm(String empNo) throws Exception {
		// TODO Auto-generated method stub
		return dao.LeaveApprovalAdm(empNo);
	}

	@Override
	public List<Object[]> AssignReccSanc() throws Exception {
		
		return dao.AssignReccSanc();
	}

	@Override
	public List<Object[]> getReccSanc(String empNo) throws Exception {
		
		return dao.getReccSanc(empNo);
	}

	@Override
	public LeaveRaSa getReccSancById(String Id) throws Exception {
		logger.info(new Date() +"Inside  getReccSancById");
		LeaveRaSa raSa=new LeaveRaSa();
		try {
		raSa=saRaRepo.findByEMPID(Id);
		}catch (Exception e) {
			logger.error(new Date() +"Inside SERVICE getReccSancById "+ e);
		}
		return raSa;
	}

	@Override
	public List<Object[]> getRaSaStatus() throws Exception {
		
		return dao.getRaSaStatus();
	}

	@Override
	public long saveRaSa(LeaveRaSa raSa) throws Exception {
		
		return saRaRepo.save(raSa).getLeaveSaRaId();
	}

	@Override
	public List<Object[]> UploadMcFc(String EmpId,String Year) throws Exception {
		
		return dao.UploadMcFc(EmpId,Year);
	}

	 public static void saveFile(String FilePath, String fileName, MultipartFile multipartFile) throws IOException 
	   {
		 logger.info(new Date() +"Inside SERVICE saveFile ");
	        Path uploadPath = Paths.get(FilePath);
	          
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	        
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {       
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }   catch (Exception e) {
	        	logger.error(new Date() +"Inside SERVICE saveFile "+ e);
	        	e.printStackTrace();
	        }
	    }

	@Override
	public long McFcAttachmentFile(LeaveAttachmentDto dto) throws Exception {
		logger.info(new Date() +"Inside SERVICE McFcAttachmentFile ");
		long result=0;
		String Path= emsfilespath+"\\MC_FC\\";
		

		File theDir = new File(Path);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		LeaveMC_FC model = new LeaveMC_FC();
        try {
        	model=mcFcRepo.findByApplId(dto.getApplId());
        }catch (Exception e) {
        	logger.error(new Date() +"Inside SERVICE McFcAttachmentFile "+ e);

		}
        if(model==null) {
        	model = new LeaveMC_FC();
    		model.setCreatedBy(dto.getCreatedBy());
    		model.setCreatedDate(dto.getCreatedDate());
    		model.setIsActive(1);
        }
		model.setApplId(dto.getApplId());
		if(dto.getMcFc().equals("M")) {
		model.setMC_file(dto.getMcFc()+"_"+dto.getApplId().replace("/", "_")+"."+FilenameUtils.getExtension(dto.getFile().getOriginalFilename()));
		}else if(dto.getMcFc().equals("F")) {
		model.setFC_file(dto.getMcFc()+"_"+dto.getApplId().replace("/", "_")+"."+FilenameUtils.getExtension(dto.getFile().getOriginalFilename()));
		}
		model.setModifiedBy(dto.getCreatedBy());
		model.setModifiedDate(dto.getCreatedDate());
		saveFile(Path, dto.getMcFc()+"_"+dto.getApplId().replace("/", "_")+"."+FilenameUtils.getExtension(dto.getFile().getOriginalFilename()), dto.getFile());
        result=mcFcRepo.save(model).getMcFcId();
		
		
	
		return result;
	}

	@Override
	public LeaveMC_FC getMcFc(String ApplId) throws Exception {
		return mcFcRepo.findByApplId(ApplId);
	}
	 

	

		
		
}
