package com.vts.ems.noc.service;

import java.util.List;

import com.vts.ems.master.model.LabMaster;
import com.vts.ems.noc.model.ExamIntimation;
import com.vts.ems.noc.model.ExamIntimationDto;
import com.vts.ems.noc.model.NocHigherEducation;
import com.vts.ems.noc.model.NocHigherEducationDto;
import com.vts.ems.noc.model.NocPassport;
import com.vts.ems.noc.model.NocPassportDto;
import com.vts.ems.noc.model.NocProceedingAbroad;
import com.vts.ems.noc.model.NocProceedingAbroadDto;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.Passport;

public interface NocService {

	 public Object[] getNocEmpList(String EmpId) throws Exception;

	public Object[] getEmpPassportData(String empId) throws Exception;

	public long NocPassportAdd(NocPassportDto dto,String UserId, Passport pport) throws Exception;
	
	public String GetCEOEmpNo() throws Exception;
	public List<String> GetPandAAdminEmpNos() throws Exception;
	public List<String> GetDGMEmpNos() throws Exception;
	public List<String> GetDHEmpNos() throws Exception;
	public List<String> GetGHEmpNos() throws Exception;
 
	public Object[] GetCeoName() throws Exception;
	public Object[] GetPandAEmpName() throws Exception;
	public Object[] GetEmpDGMEmpName(String empno) throws Exception;
	public Object[] GetDivisionHeadName(String EmpNo) throws Exception;
	public Object[] GetGroupHeadName(String EmpNo) throws Exception;
	
	public Employee getEmpData(String empid)throws Exception;
	public List<Object[]> getnocPassportList(String empNo) throws Exception;

	public NocPassport getNocPassportId(long NocPassportId )throws Exception;

	public long NOCPassportUpdate(NocPassportDto dto, String userId,Passport pport )throws Exception;

	public Object[] getPassportFormDetails(String passportid)throws Exception;

	public List<Object[]> NOCPassportTransactionList(String passportid)throws Exception;

	public long NOCPassportForward(String passportid, String userId, String action,String remarks, String EmpNo,String loginType)throws Exception;

	public NocPassport getNocPassportById(long passportid)throws Exception;

	public List<Object[]> NocApprovalsList(String empNo)throws Exception;

	public long PandAFromUpdate(NocPassportDto dto, String userId)throws Exception;

	public long NocProcAbroadAdd(NocProceedingAbroadDto dto, String userId)throws Exception;

	public List<Object[]> getProcAbroadList(String empNo)throws Exception;

	public List<Object[]> NOCProcAbroadTransactionList(String procAbrId)throws Exception;

	public NocProceedingAbroad getNocProceedingAbroadById(long ProcAbrId)throws Exception;

	public long NocProcAbroadUpdate(NocProceedingAbroadDto dto, String userId)throws Exception;

	public Object[] getNocProcAbroadDetails(String procAbrId)throws Exception;

	public long NOCProcAbraodForward(String procAbroadId, String userId, String action, String remarks, String empNo,String loginType);

	public List<LabMaster> getLabMasterDetails()throws Exception;

	public List<Object[]> getPassportRemarksHistory(String passportid)throws Exception;

	public List<Object[]> NocApprovedList(String empNo, String fromdate, String todate)throws Exception;
	public Object[] getEmpNameDesig(String EmpNo) throws Exception;

	public Object[] getEmpTitleDetails(String passportid)throws Exception;

	public  List<Object[]>  getProceedinAbraodRemarksHistory(String procAbrId)throws Exception;

	public long DeptDetailsUpdate(NocProceedingAbroadDto dto, String userId)throws Exception;

	public long ProcAbroadPandAFromUpdate(NocProceedingAbroadDto dto, String userId)throws Exception;

	public Object[] getEmpGender(String procAbrId) throws Exception;

	public long ExamDetailsAdd(ExamIntimationDto dto, String userId)throws Exception;

	public  List<Object[]> getExamIntimationDetails(String empNo)throws Exception;

	public long ExamDetailsUpdate(ExamIntimationDto dto, String userId) throws Exception;

	public List<Object[]> ExamIntimationTransactionList(String examId)throws Exception;

	public Object[] getIntimationData(String examId)throws Exception;

	public ExamIntimation IntimatedExam(String examId) throws Exception;

	public long IntimationForExamForward(String examId, String username, String action, String remarks, String empNo,
			String loginType) throws Exception;

	public List<Object[]> getIntimationRemarks(String examId)throws Exception;

	public long HigherEducationAdd(NocHigherEducationDto dto, String userId)throws Exception;

	public List<Object[]> getNOCHigherEducationList(String empNo)throws Exception;

	public List<Object[]> HigherEducationTransactionList(String nOCHigherEducId)throws Exception;

	public NocHigherEducation getNocHigherEducationById(long  NOCHigherEducId)throws Exception;

	public long NOCHigherEducationUpdate(NocHigherEducationDto dto, String userId)throws Exception;

	public Object[] getHigherEducationDetails(String NOCHigherEducId)throws Exception;

	public List<Object[]> getNocHigherEducationRemarks(String NOCHigherEducId)throws Exception;
	

	

}
