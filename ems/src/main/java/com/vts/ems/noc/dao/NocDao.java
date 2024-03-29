package com.vts.ems.noc.dao;

import java.util.List;

import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.noc.model.ExamIntimation;
import com.vts.ems.noc.model.ExamIntimationTrans;
import com.vts.ems.noc.model.NocHigherEducation;
import com.vts.ems.noc.model.NocHigherEducationTrans;
import com.vts.ems.noc.model.NocPassport;
import com.vts.ems.noc.model.NocPassportTrans;
import com.vts.ems.noc.model.NocProceedingAbroad;
import com.vts.ems.noc.model.NocProceedingAbroadTrans;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.Passport;

public interface NocDao {
	

	 public Object[] getNocEmpList(String EmpId) throws Exception;

	public Object[] getEmpPassportData(String  empId) throws Exception;

	public long NocPassportAdd(NocPassport noc) throws Exception;

	public long MaxOfNocPassportId() throws Exception;

	public List<Object[]> getnocPassportList(String empNo) throws Exception;

	public NocPassport getNocPassportId(long nocPassportId)throws Exception;
	public Employee getEmpDataByEmpNo(String empNo) throws Exception;

	public long NOCPassportUpdate(NocPassport noc)throws Exception;

	public Object[] getPassportFormDetails(String passportid)throws Exception;

	public long NocPassportTransactionAdd(NocPassportTrans transaction)throws Exception;

	public List<Object[]> NOCPassportTransactionList(String passportid)throws Exception;
	
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

	public long NOCPassportForward(NocPassport noc)throws Exception;

	public long AddNotifications(EMSNotification notification) throws Exception;
	
	public String GetEmpGHEmpNo(String empno) throws Exception;
	public String GetEmpDHEmpNo(String empno) throws Exception;
	public String GetEmpDGMEmpNo(String empno) throws Exception;
	public Long EditNoc(NocPassport noc) throws Exception;
	public DivisionMaster GetDivisionData(long DivisionId) throws Exception;

	public List<Object[]> NocApprovalsList(String empNo)throws Exception;

	public long PandAFromUpdate(NocPassport noc)throws Exception;

	public long getMaxOfProcAbroadId()throws Exception;

	public long NocProcAbroadAdd(NocProceedingAbroad nocpa) throws Exception;

	public long NocProcAbroadTransactionAdd(NocProceedingAbroadTrans trans)throws Exception;

	public List<Object[]> getProcAbroadList(String empNo)throws Exception;

	public List<Object[]> NOCProcAbroadTransactionList(String procAbrId)throws Exception;

	public NocProceedingAbroad getNocProceedingAbroadById(long procAbrId)throws Exception;

	public long NocProcAbroadUpdate(NocProceedingAbroad nocpa)throws Exception;

	public Object[] getNocProcAbroadDetails(String procAbrId)throws Exception;

	public Long EditNocpa(NocProceedingAbroad noc) throws Exception;

	public long AddPassport(Passport pport)throws Exception;

	public List<LabMaster> getLabMasterDetails()throws Exception;
	
	public long GetPassportCount(String empid)throws Exception;

	public List<Object[]> getPassportRemarksHistory(String passportid)throws Exception;

	public List<Object[]> getNocApprovedList(String empNo, String fromdate, String todate)throws Exception;
	
	public Object[] getEmpNameDesig(String EmpNo) throws Exception;

    public List<Object[]> getProceedinAbraodRemarksHistory(String procAbrId)throws Exception;

	public long DeptDetailsUpdate(NocProceedingAbroad nocpa)throws Exception;

	public long ProcAbroadPandAFromUpdate(NocProceedingAbroad nocpa)throws Exception;

	public Object[] getEmpGender(String procAbrId)throws Exception;

	public long ExamDetailsAdd(ExamIntimation exam)throws Exception;

	public List<Object[]> getExamIntimationDetails(String empNo)throws Exception;

	public long ExamIntimationTransAdd(ExamIntimationTrans transaction)throws Exception;

	public ExamIntimation getExamId(long examId)throws Exception;

	public long ExamDetailsUpdate(ExamIntimation exam)throws Exception;

	public List<Object[]> getExamIntimationTransactionList(String examId)throws Exception;

	public Object[] getIntimationData(String examId)throws Exception;

	public ExamIntimation IntimatedExam(String examId)throws Exception;

	public long IntimationDataEdit(ExamIntimation exam)throws Exception;

	public List<Object[]> getIntimationRemarks(String examId)throws Exception;

	public long HigherEducationAdd(NocHigherEducation edu)throws Exception;

	public long getMaxOfNocEducId()throws Exception;

	public long NocHigherEducTransactionAdd(NocHigherEducationTrans transaction)throws Exception;

	public List<Object[]> getNOCHigherEducationList(String empNo)throws Exception;

	public List<Object[]> getHigherEducationTransactionList(String nOCHigherEducId)throws Exception;

	public NocHigherEducation getNocHigherEducationById(long NOCHigherEducId)throws Exception;

	public long HigherEducationUpdate(NocHigherEducation edu)throws Exception;

	public Object[] getHigherEducationDetails(String NOCHigherEducId)throws Exception;

	public List<Object[]> getNocHigherEducationRemarks(String NOCHigherEducId)throws Exception;

	public NocHigherEducation HigherEducation(String nOCHigherEducId)throws Exception;

	public Long EditNocHe(NocHigherEducation noc)throws Exception;

	public Long NocHigherEducationTransAdd(NocHigherEducationTrans transaction)throws Exception;
	public List<String> GetSOEmpNos() throws Exception;

	public List<Object[]> getHigherEducationApprovalData(String nOCHigherEducId) throws Exception;

	public Object[] getNocApprovalFlow(String empNo) throws Exception;

	public Object[] getPandAName(String NOCHigherEducId)throws Exception;

	public Object[] getEmpQualification(String empNo)throws Exception;

	public long getmaxIntimationId()throws Exception;

	public ExamIntimation getIntimationById(long examId)throws Exception;

	public List<Object[]> getIntimationApprovalData(String examId)throws Exception;

	public Object[] getEmpGenderPassport(String passportid)throws Exception;

	public List<Object[]>  getNOCPassportApprovalData(String passportid)throws Exception;

	public List<Object[]> getNocProcAbroadApprovalData(String procAbrId)throws Exception;

	




	
}
