package com.vts.ems.pis.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.DateTimeFormatUtil;
import com.vts.ems.login.Login;
import com.vts.ems.pis.dao.PisDao;
import com.vts.ems.pis.dto.UserManageAdd;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisPayLevel;

@Service
public class PisServiceImpl implements PisService 
{
	private static final Logger logger = LogManager.getLogger(PisServiceImpl.class);

	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	@Autowired
	private PisDao dao;
	
	@Value("${Image_uploadpath}")
	private String uploadpath;
	
	
	@Override
	public List<Object[]> EmployeeDetailsList(String LoginType,String Empid) throws Exception
	{
		return dao.EmployeeDetailsList(LoginType, Empid);
	}
	
	@Override
	public List<Object[]> LoginMasterList(String LoginType,String Empid) throws Exception
	{
		return dao.LoginMasterList(LoginType, Empid);
	}
	
	@Override
	public Object[] EmployeeDetails(String empid) throws Exception
	{
		return dao.EmployeeDetails(empid);
	}
	
	@Override
	public String getimage(String empid)throws Exception
	{
		String result=null;
		try {
			String photoname=dao.PhotoPath(empid);
			File f = new File(uploadpath+"\\"+photoname);
			if(f.exists()) {
				result=encodeFileToBase64Binary(f);
			}
		}catch (Exception e) {
			e.printStackTrace();		
		}
		
		return result;
	}

	private static String encodeFileToBase64Binary(File file) {
		String encodedfile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
			fileInputStreamReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return encodedfile;
		}
		return encodedfile;
	}

	@Override
	public List<DivisionMaster> DivisionList() throws Exception
	{
		return dao.DivisionList();
	}
	
	@Override
	public List<EmployeeDesig> DesigList() throws Exception
	{
		return dao.DesigList();
	}

	@Override
	public List<PisPayLevel> PayLevelList() throws Exception
	{
		return dao.PayLevelList();
	}
	
	@Override
	public List<PisCategory> PisCategoryList() throws Exception
	{
		return dao.PisCategoryList();
	}
	
	@Override
	public List<PisCatClass> PisCatClassList() throws Exception
	{
		return dao.PisCatClassList();
	}
	
	@Override
	public List<PisCadre> PisCaderList() throws Exception
	{
		return dao.PisCaderList();
	}
	
	@Override
	public List<EmpStatus> EmpStatusList() throws Exception
	{
		return dao.EmpStatusList();
	}
	
	@Override
	public long EmployeeAddSubmit(Employee emp) throws Exception
	{
		emp.setCreatedDate(sdtf.format(new Date()));
		long empno = dao.getempno();
		long intemp = empno + 1;
		String empid2 = String.valueOf(intemp);
		String empid = StringUtils.leftPad(empid2, 7, "0");
		emp.setEmpNo(empid);
		return dao.EmployeeAddSubmit(emp);
	}
	
	@Override
	public long EmployeeEditSubmit(Employee emp) throws Exception
	{
		
		Employee employee = dao.getEmployee(String.valueOf(emp.getEmpId()));
	
		employee.setEmpName(emp.getEmpName());
		employee.setDesignationId(emp.getDesignationId());
		employee.setTitle(emp.getTitle());
		employee.setDOB(emp.getDOB());
		employee.setDOA(emp.getDOA());
		employee.setDOJL(emp.getDOJL());
		employee.setDOR(emp.getDOR());
		employee.setCategoryId(emp.getCategoryId());
		employee.setDivisionId(emp.getDivisionId());
		employee.setCadreId(emp.getCadreId());
		employee.setCatId(emp.getCatId());
		employee.setGender(emp.getGender());
		employee.setBloodGroup(emp.getBloodGroup());
		employee.setMaritalStatus(emp.getMaritalStatus());
		employee.setReligion(emp.getReligion());
		employee.setEmpStatus(emp.getEmpStatus());
		employee.setGPFNo(emp.getGPFNo());
		employee.setPAN(emp.getPAN());
		employee.setPINNo(emp.getPINNo());
		employee.setPunchCard(emp.getPunchCard());		
		employee.setUID(emp.getUID());
		employee.setQuarters(emp.getQuarters());
		employee.setPH(emp.getPH());
		employee.setEmail(emp.getEmail());
		employee.setHomeTown(emp.getHomeTown());
		employee.setServiceStatus(emp.getServiceStatus());
		employee.setPayLevelId(emp.getPayLevelId());
		employee.setSBIAccNo(emp.getSBIAccNo());
		employee.setIdMark(emp.getIdMark());
		employee.setHeight(emp.getHeight());
		employee.setModifiedBy(emp.getModifiedBy());
		employee.setModifiedDate(emp.getModifiedDate());
		employee.setInternalNumber(emp.getInternalNumber());
		employee.setSubCategary(emp.getSubCategary());
		employee.setEmpStatusDate(emp.getEmpStatusDate());	
		employee.setModifiedDate(sdtf.format(new Date()));
		
		return dao.EmployeeEditSubmit(employee);
	}
	
	
	@Override
	public Employee getEmployee(String empid) throws Exception
	{
		return dao.getEmployee(empid);
	}
	@Override
	public int PunchcardList(String puchcard)throws Exception{
		return dao.PunchcardList(puchcard);
	}
	@Override
	public String PhotoPath(String EmpId) throws Exception {
		
		return dao.PhotoPath(EmpId);
	}
	

	@Override
	public int PhotoPathUpdate(String Path, String EmpId) throws Exception {
		
		return dao.PhotoPathUpdate(Path, EmpId);
	}
	 public static void saveFile(String uploadpath, String fileName, MultipartFile multipartFile) throws IOException 
	    {
	        Path uploadPath = Paths.get(uploadpath);
	          
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	        
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {       
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }     
	    }
	@Override
	public int saveEmpImage(MultipartFile file ,String empid ,String uploadpath)throws Exception{
		int result =0;
		try {
			
			 String OriginalFilename[]=(file.getOriginalFilename()).split("\\.");		 
			 String fileName=empid+"."+OriginalFilename[1];
			  result =dao.PhotoPathUpdate(fileName,empid);
			 saveFile(uploadpath,fileName,file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	@Override
	public List<Object[]> getEmpList()throws Exception{
			return dao.getEmpList();
	}
	
	@Override
	public Login getLoginEditData(String LoginId)throws Exception{
		return dao.getLoginEditData(Long.parseLong(LoginId));
	}
	
	@Override
	public List<Object[]> getLoginTypeList()throws Exception{
			return dao.getLoginTypeList();
	}
	@Override
	public int UserManagerDelete(String username,String loginid)throws Exception{
		return dao.UserManagerDelete(username,loginid);
	}
	@Override
	public int UserNamePresentCount(String username)throws Exception{
		return dao.UserNamePresentCount( username);
	}
	@Override
	public Long UserManagerAdd(UserManageAdd useradd)throws Exception{
		
		Login login = new Login();
		
		login.setUsername(useradd.getUserName());
		login.setPassword("$2y$12$QTTMcjGKiCVKNvNa242tVu8SPi0SytTAMpT3XRscxNXHHu1nY4Kui");
		login.setEmpId(Long.parseLong(useradd.getEmpId()));
		login.setLoginType(useradd.getLoginType());
		login.setIsActive(1);		
		login.setCreatedBy(useradd.getCreatedBy());
		login.setCreatedDate(useradd.getCreatedDate());
		
		return dao.UserManagerAdd(login);
		
	}
	
	@Override
	public int UserMangerEdit(String empid , String loginttype , String username,String loginid)throws Exception
	{
		Login login = new Login();
		login.setModifiedBy(username);
		login.setEmpId(Long.parseLong(empid));
		login.setLoginId(Long.parseLong(loginid));
		login.setModifiedDate(sdf.format(new Date()));
		login.setLoginType(loginttype);
		return dao.UserManagerEdit(login);
	}
	
	@Override
	public List<Object[]> getFamilyMembersList(String empid)throws Exception
	{
		return dao.getFamilyMembersList(empid);
	}
	
	@Override
	public Object[] GetEmpData(String empid)throws Exception{
		return dao.GetEmpData(empid);
	}
	
	@Override
	public List<Object[]> getFamilyRelation()throws Exception{
		return dao.getFamilyRelation();
	}
	
	
	@Override
	public List<Object[]> getFamilyStatus()throws Exception{
		return dao.getFamilyStatus();
	}
	
	@Override
	public Long AddFamilyDetails(EmpFamilyDetails Details)throws Exception{
		return dao.AddFamilyDetails( Details);
	}
	
	@Override
	public int DeleteMeber(String familyid,String Username)throws Exception{
		return dao.DeleteMeber(familyid,Username);
		
	}
	
	@Override
	public EmpFamilyDetails	getMemberDetails(String familyid)throws Exception{
		return dao.getMemberDetails(familyid);
	}
}
