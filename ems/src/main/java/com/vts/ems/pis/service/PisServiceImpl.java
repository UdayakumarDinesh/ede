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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.icu.impl.UResource.Array;
import com.vts.ems.Admin.model.LoginPasswordHistory;
import com.vts.ems.login.Login;
import com.vts.ems.pis.dao.PisDao;
import com.vts.ems.pis.dto.UserManageAdd;
import com.vts.ems.pis.model.AddressEmec;
import com.vts.ems.pis.model.AddressNextKin;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisEmpFamilyForm;
import com.vts.ems.pis.model.PisPayLevel;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class PisServiceImpl implements PisService 
{
	private static final Logger logger = LogManager.getLogger(PisServiceImpl.class);

	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	@Autowired
	private PisDao dao;
	
	@Value("${EMSFilesPath}")
	private String uploadpath;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public List<Object[]> EmployeeDetailsList(String LoginType,String Empid) throws Exception
	{
		return dao.EmployeeDetailsList(LoginType, Empid);
	}
	
	@Override
	public List<Object[]> LoginMasterList() throws Exception
	{
		return dao.LoginMasterList();
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
			String path = uploadpath+"//empimages";
			File f = new File(path+"//"+photoname);
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
	public long EmployeeAddSubmit(Employee emp,EmployeeDetails empd) throws Exception
	{
		emp.setCreatedDate(sdtf.format(new Date()));
		long result=dao.EmployeeAddSubmit(emp);
		if(result>0) {		
			empd.setCreatedDate(sdtf.format(new Date()));
			dao.EmployeeDetailsAddSubmit(empd);
		}
		
		return result;
		
	}
	
	@Override
	public long EmployeeEditSubmit(Employee emp) throws Exception
	{
		Employee employee = dao.getEmp(String.valueOf(emp.getEmpId()));
		employee.setEmpName(emp.getEmpName());
		employee.setEmail(emp.getEmail());
		employee.setDivisionId(emp.getDivisionId());
		employee.setDesigId(emp.getDesigId());
		employee.setEmpNo(emp.getEmpNo());
		employee.setModifiedBy(emp.getModifiedBy());
		employee.setModifiedDate(emp.getModifiedDate());
	     return dao.EmployeeEditSubmit(employee);
	}
	
	
	
	@Override
	public long EmployeeDetailsEditSubmit(EmployeeDetails emp) throws Exception
	{
		
		EmployeeDetails employee = dao.getEmployee(String.valueOf(emp.getEmpDetailsId()));
		employee.setUANNo(emp.getUANNo());
		employee.setTitle(emp.getTitle());
		employee.setEmpNo(emp.getEmpNo());
		employee.setDOB(emp.getDOB());
		employee.setDOA(emp.getDOA());
		employee.setDOJL(emp.getDOJL());
		employee.setDOR(emp.getDOR());
		employee.setCategoryId(emp.getCategoryId());
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
		employee.setHomeTown(emp.getHomeTown());
		employee.setServiceStatus(emp.getServiceStatus());
		employee.setPayLevelId(emp.getPayLevelId());
		employee.setSBIAccNo(emp.getSBIAccNo());
		employee.setIdMark(emp.getIdMark());
		employee.setHeight(emp.getHeight());
		employee.setBasicPay(emp.getBasicPay());
		employee.setModifiedBy(emp.getModifiedBy());
		employee.setModifiedDate(emp.getModifiedDate());
		employee.setInternalNumber(emp.getInternalNumber());
		employee.setSubCategary(emp.getSubCategary());
		employee.setEmpStatusDate(emp.getEmpStatusDate());	
		employee.setModifiedDate(sdtf.format(new Date()));
		employee.setPhoneNo(emp.getPhoneNo());
		employee.setAltPhoneNo(emp.getAltPhoneNo());
		return dao.EmployeeDetailsEditSubmit(employee);
	}
	
	
	@Override
	public EmployeeDetails getEmployee(String empid) throws Exception
	{
		return dao.getEmployee(empid);
	}
	
	@Override
	public EmployeeDetails getEmployeeDetailsData(String empno) throws Exception
	{
		return dao.getEmployeeDetailsData(empno);
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
	public int saveEmpImage(MultipartFile file ,String empno ,String uploadpath)throws Exception{
		int result =0;
		try {
			
			 String OriginalFilename[]=(file.getOriginalFilename()).split("\\.");		 
			 String fileName=empno+"."+OriginalFilename[1];
			  result =dao.PhotoPathUpdate(fileName,empno);
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
	public Long UserManagerAdd(UserManageAdd useradd)throws Exception
	{
		
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
	public int UserMangerEdit(String logintype, String username,String loginid )throws Exception
	{
		Login login = new Login();
		login.setModifiedBy(username);
		//login.setEmpId(Long.parseLong(empid));
		login.setLoginId(Long.parseLong(loginid));
		login.setModifiedDate(sdtf.format(new Date()));
		login.setLoginType(logintype);
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
	
	@Override
	public Long EditFamilyDetails(EmpFamilyDetails Details)throws Exception{
		
		EmpFamilyDetails member = dao.getMember(String.valueOf(Details.getFamily_details_id()));

		member.setMember_name(Details.getMember_name());
		member.setDob(Details.getDob());
		member.setRelation_id(Details.getRelation_id());
		member.setCghs_ben_id(Details.getCghs_ben_id());
		member.setFamily_status_id(Details.getFamily_status_id());
		member.setStatus_from(Details.getStatus_from());
		member.setBlood_group(Details.getBlood_group());
		member.setPH(Details.getPH());
		member.setGender(Details.getGender());
 	    member.setMed_dep(Details.getMed_dep());
 	    member.setMed_dep_from(Details.getMed_dep_from());
 	    member.setLtc_dep(Details.getLtc_dep());
        member.setLtc_dep_from(Details.getLtc_dep_from());
 	    member.setMar_unmarried(Details.getMar_unmarried());
    	member.setEmp_unemp(Details.getEmp_unemp());
//     	member.setEmpid(Details.getEmpid());
     	member.setModifiedBy(Details.getModifiedBy());
 	    member.setModifiedDate(Details.getModifiedDate());
		member.setEmpStatus(Details.getEmpStatus());
		member.setMemberOccupation(Details.getMemberOccupation());
		member.setMemberIncome(Details.getMemberIncome());
		
			
//		member.setIsActive(1);
		return dao.EditFamilyDetails( member);
	}
	
	
	@Override
	public Object[]  getPerAddress(String empid)throws Exception{
		return dao.getPerAddress(empid);
	}
	
	@Override
	public List<Object[]> GetAllEmployee()throws Exception
	{
		logger.info(new Date() +"Inside SERVICE GetAllEmployee()");
		try {
			return dao.GetAllEmployee();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Object[]> getStates()throws Exception{
		return dao.getStates();
	}
	
	
	@Override
	public Long AddPerAddress(AddressPer peraddress)throws Exception{
		return dao.AddPerAddress( peraddress);
	}
	
	@Override
	public AddressPer getPerAddressData(String empid)throws Exception{
		return dao.getPerAddressData(empid);
	}
	
	@Override
	public Long EditPerAddress(AddressPer address)throws Exception{
		
		AddressPer peraddress=dao.getPeraddress(address.getAddress_per_id());
		   peraddress.setLandline(address.getLandline());
	 	   peraddress.setFrom_per_addr(address.getFrom_per_addr());
	 	   peraddress.setMobile(address.getMobile());
	 	   peraddress.setPin(address.getPin());
	 	   peraddress.setState(address.getState());
	 	   peraddress.setCity(address.getCity());
	 	   peraddress.setAlt_mobile(address.getAlt_mobile());
	 	   peraddress.setEmpid(address.getEmpid());
	 	   peraddress.setPer_addr(address.getPer_addr());
	 	   peraddress.setAddress_per_id(address.getAddress_per_id());
		   peraddress.setModifiedBy(address.getModifiedBy());
		   peraddress.setModifiedDate(address.getModifiedDate());
		return dao.EditPerAddress(peraddress);
	}
	
	@Override
	public List<Object[]> getResAddress(String empid)throws Exception{
		return dao.getResAddress(empid);
	}
	@Override
	public Object[]  getKinAddress(String empid)throws Exception{
		return dao.getKinAddress(empid);
	}
	@Override
	public Object[]  getEmeAddress(String empid)throws Exception{
		return dao.getEmeAddress(empid);
	}
	
	@Override
	public Long AddResAddress(AddressRes ressRes)throws Exception{
		return dao.AddResAddress( ressRes);
	}
	@Override
	public AddressRes getResAddressData(String addressid)throws Exception{
		return dao.getResAddressData(addressid);
	}
	
	@Override
	public Long EditResAddress(AddressRes add)throws Exception{
		
		AddressRes address = dao.getResAddressData(Long.toString(add.getAddress_res_id()));
		
		address.setEmpid(add.getEmpid());
		address.setRes_addr(add.getRes_addr());
		address.setFrom_res_addr(add.getFrom_res_addr());
		address.setMobile(add.getMobile());
		address.setAlt_mobile(add.getAlt_mobile());
		address.setLandline(add.getLandline());
		address.setExt(add.getExt());
		address.setEmailDrona(add.getEmailDrona());
		address.setEmailOfficial(add.getEmailOfficial());
		address.setEmailPersonal(add.getEmailPersonal());
		address.setEmailOutlook(add.getEmailOutlook());
		address.setQtrNo(add.getQtrNo());
		address.setQtrType(add.getQtrType());
		address.setQtrDetails(add.getQtrDetails());
		address.setState(add.getState());
		address.setCity(add.getCity());
		address.setPin(add.getPin());
		address.setModifiedBy(add.getModifiedBy());
		address.setModifiedDate(add.getModifiedDate());
		address.setAddress_res_id(add.getAddress_res_id());
		
		
		return dao.EditResAddress(address);
	}
	
	@Override
	public int deleteResAdd(String addresid,String Username)throws Exception{
		return dao.deleteResAdd(addresid,Username);
		
	}
	
	@Override
	public Long AddNextAddress(AddressNextKin nextaddress)throws Exception{
		return dao.AddNextAddress( nextaddress);
	}
	@Override
	public Long EditNextKinAddress(AddressNextKin address)throws Exception{
		
		AddressNextKin peraddress=dao.getNextKinaddress(address.getAddress_kin_id());
		   peraddress.setLandline(address.getLandline());
	 	   peraddress.setFrom_per_addr(address.getFrom_per_addr());
	 	   peraddress.setMobile(address.getMobile());
	 	   peraddress.setPin(address.getPin());
	 	   peraddress.setState(address.getState());
	 	   peraddress.setCity(address.getCity());
	 	   peraddress.setAlt_mobile(address.getAlt_mobile());
	 	   peraddress.setEmpid(address.getEmpid());
	 	   peraddress.setNextkin_addr(address.getNextkin_addr());
	 	   peraddress.setAddress_kin_id(address.getAddress_kin_id());
		   peraddress.setModifiedBy(address.getModifiedBy());
		   peraddress.setModifiedDate(address.getModifiedDate());
		return dao.EditNextKinAddress(peraddress);
	}
	
	@Override
	public AddressNextKin getNextKinrAddressData(String empid)throws Exception{
		return dao.getNextKinAddressData(empid);
	}
	
	@Override
	public AddressEmec getEmecAddressData(String empid)throws Exception{
		return dao.getEmecAddressData(empid);
	}
	@Override
	public Long AddEmecAddress(AddressEmec emecaddress)throws Exception{
		return dao.AddEmecAddress( emecaddress);
	}
	
	@Override
	public Long EditEmecAddress(AddressEmec address)throws Exception{
		
		AddressEmec peraddress=dao.getEmecaddress(address.getAddress_emer_id());
		   peraddress.setLandline(address.getLandline());
	 	   peraddress.setFrom_per_addr(address.getFrom_per_addr());
	 	   peraddress.setMobile(address.getMobile());
	 	   peraddress.setPin(address.getPin());
	 	   peraddress.setState(address.getState());
	 	   peraddress.setCity(address.getCity());
	 	   peraddress.setAlt_mobile(address.getAlt_mobile());
	 	   peraddress.setEmpid(address.getEmpid());
	 	   peraddress.setEmer_addr(address.getEmer_addr());
	 	   peraddress.setAddress_emer_id(address.getAddress_emer_id());
		   peraddress.setModifiedBy(address.getModifiedBy());
		   peraddress.setModifiedDate(address.getModifiedDate());
		return dao.EditEmecAddress(peraddress);
	}
	
	@Override
	public List<Object[]> ReqEmerAddajax(String empid) throws Exception {
		
		return dao.ReqEmerAddajax(empid);
	}
	
	
	
	  @Override
		public List<Object[]> AuditStampingList(String Username,String Fromdate,String Todate)  throws Exception {
			
			 
			return dao.AuditStampingList(Username,Fromdate,Todate);
			
		}
		
		@Override
		public int PasswordChange(String OldPassword, String NewPassword, String loginid,String username)throws Exception {

			logger.info(new Date() +"Inside SERVICE PasswordChange");
			String actualoldpassword=dao.OldPassword(loginid);

			if(encoder.matches(OldPassword, actualoldpassword))
			{
			
				String oldpassword=encoder.encode(OldPassword);
				String newpassword=encoder.encode(NewPassword);
				int count=dao.PasswordChange(oldpassword, newpassword, loginid, sdtf.format(new Date()),username);
				if(count>0) {
					LoginPasswordHistory passHis= new LoginPasswordHistory();
					passHis.setLoginId(Long.parseLong(loginid));
					passHis.setPassword(newpassword);
					passHis.setActionBy(username);
					passHis.setActionDate(sdtf.format(new Date()));
					passHis.setActionType("CP");
					
					dao.loginHisAddSubmit(passHis);
				}
				
				return count;
			}
			return 0;
		}
		@Override
		public Object[] EmployeeEmeAddressDetails(String empid) throws Exception
		{
			return dao.EmployeeEmeAddressDetails(empid);
		}
		@Override
		public Object[] EmployeeNextAddressDetails(String empid) throws Exception
		{
			return dao.EmployeeNextAddressDetails(empid);
		}
		
		@Override
		public Object[] EmployeePerAddressDetails(String empid) throws Exception
		{
			return dao.EmployeePerAddressDetails(empid);
		}
		
		@Override
		public List<Object[]> EmployeeResAddressDetails(String empid) throws Exception
		{
			return dao.EmployeeResAddressDetails(empid);
		}
		
		@Override
		public List<Object[]> getFamilydetails(String empid) throws Exception
		{
			return dao.getFamilydetails(empid);
		}
		
		@Override
		public int ResetPassword(String loginid,String username)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE ResetPassword()");
			try {
				//Object[] empdata = dao.GetEmpPhoneNo(loginid);
				String resetpwd = null;
				//if(empdata!=null) {
				//	resetpwd = ""+empdata[2];
				//}else {
					resetpwd = "1234";
				//}
				 
				String pwd=encoder.encode(resetpwd);
				int count=dao.ResetPassword(loginid,pwd,  username);;
//				if(count>0) {
//					LoginPasswordHistory passHis= new LoginPasswordHistory();
//					passHis.setLoginId(Long.parseLong(loginid));
//					passHis.setPassword(pwd);
//					passHis.setActionBy(username);
//					passHis.setActionDate(sdtf.format(new Date()));
//					passHis.setActionType("AR");
//					
//					dao.loginHisAddSubmit(passHis);
//				}
				
				return count;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		@Override
		public List<Object[]> GetEmployeeLoginData(String loginid)throws Exception
		{
			return dao.GetEmployeeLoginData(loginid);
		}
		
	
		@Override
		public List<Object[]> GetEmployeeList()throws Exception
		{
			logger.info(new Date() +"Inside SERVICE GetEmployeeList()");
			try {
				return dao.GetEmployeeList();
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}

		@Override
		public Employee getEmp(String empid) throws Exception {
			
			return dao.getEmp(empid);
		}
		
		@Override
		public Object[] GetEmpDetails(String empid)throws Exception
		{
			return dao.GetEmpDetails(empid);
		}
		@Override
		public int UpdateSeniorityNumber(String empid, String newSeniorityNumber)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE UpdateSeniorityNumber");
			try {
			
			Long empId=Long.parseLong(empid);
			Long SeniorityNumber=Long.parseLong(newSeniorityNumber);
			int result= 0;
			Long newSeniorityNumberL=SeniorityNumber;
			List<Object[]> EmpSenHaveUpdate=dao.UpdateAndGetList(empId,newSeniorityNumber);
			List<Object[]> result1=EmpSenHaveUpdate.stream().filter(srno-> Long.parseLong(srno[0].toString())>=SeniorityNumber && Long.parseLong(srno[1].toString())!=empId  ).collect(Collectors.toList());
			
			for(Object[] data:result1) { 
			  Long empIdL=Long.parseLong(data[1].toString()); 
			  result= dao.UpdateAllSeniority(empIdL, ++newSeniorityNumberL);
			}
			
			return result;
			}catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		@Override
		public EmpFamilyDetails getFamilyMemberModal(String familydetailsid) throws Exception
		{
			return dao.getFamilyMemberModal(familydetailsid);
		}
		
		@Override
		public List<Object[]> GetFormMembersList(String empid,String formid)throws Exception
		{
			return dao.GetFormMembersList(empid, formid);
		}
		
		@Override
		public  Object[] getEmployeeInfo(String empid) throws Exception
		{
			return dao.getEmployeeInfo(empid);
		}
		
		@Override
		public  Object[] employeeResAddr(String empid) throws Exception
		{
			return dao.employeeResAddr(empid);
		}
		
		
		@Override
		public int FamilyMemDetailsForward(String formid) throws Exception
		{
			
			
			return dao.UpdateMemberStatus(formid, "F" );
		}
		
		
		@Override
		public int FamilyMemIncConfirm(String formid,String empid,String username ) throws Exception
		{
			return dao.FamilyMemIncConfirm(formid, empid, username);
		}
		
		
		@Override
		public List<Object[]> FamMemFwdEmpList() throws Exception 
		{
			return dao.FamMemFwdEmpList();
		}
		
		@Override
		public List<Object[]> familyRelationList()throws Exception
		{
			return dao.familyRelationList();
		}
		
		@Override
		public  Object[] RelationshipData(String relationid) throws Exception
		{
			return dao.RelationshipData(relationid);
		}
		
		@Override
		public  Object[] getMemberdata(String familydetailid) throws Exception
		{
			return dao.getMemberdata(familydetailid);
		}
		
		
		
		
		@Override
		public Long DepMemEditSubmit(EmpFamilyDetails Details)throws Exception
		{
			EmpFamilyDetails member = dao.getMember(String.valueOf(Details.getFamily_details_id()));

			member.setMember_name(Details.getMember_name());
			member.setDob(Details.getDob());
			member.setRelation_id(Details.getRelation_id());
			member.setCghs_ben_id(Details.getCghs_ben_id());
			member.setFamily_status_id(Details.getFamily_status_id());
			member.setStatus_from(Details.getStatus_from());
			member.setBlood_group(Details.getBlood_group());
			member.setPH(Details.getPH());
			member.setGender(Details.getGender());
	 	    member.setMed_dep(Details.getMed_dep());
	 	    member.setMed_dep_from(Details.getMed_dep_from());
	 	    member.setLtc_dep(Details.getLtc_dep());
	        member.setLtc_dep_from(Details.getLtc_dep_from());
	 	    member.setMar_unmarried(Details.getMar_unmarried());
	    	member.setEmp_unemp(Details.getEmp_unemp());
	     	member.setModifiedBy(Details.getModifiedBy());
	 	    member.setModifiedDate(Details.getModifiedDate());
	 	    
			member.setEmpStatus(Details.getEmpStatus());
			member.setMemberOccupation(Details.getMemberOccupation());
			member.setMemberIncome(Details.getMemberIncome());
			
			member.setIncComment(Details.getIncComment());
			
			if(Details.getIncFilePath()!=null && !Details.getIncFilePath().trim().equals(""))
			{
				member.setIncFilePath(Details.getIncFilePath());
			}
			
				
			return dao.EditFamilyDetails( member);
		}
		
		@Override
		public List<Object[]> EmpFamFormsList(String empid,String status) throws Exception
		{
			return dao.EmpFamFormsList(empid, status);
		}
		
		@Override
		public long EmpFamilyFormAdd(PisEmpFamilyForm form) throws Exception
		{
			return dao.EmpFamilyFormAdd(form);
		}
		
		@Override
		public Object[] GetFamFormData(String familyformid) throws Exception
		{
			return dao.GetFamFormData(familyformid);
		}
		
		@Override
		public int FamilyMemberDelete(String familydetailsid)throws Exception
		{
			return dao.FamilyMemberDelete(familydetailsid);
		}
		
		@Override
		public int IncFormReturn(String familyformid, String remarks)throws Exception
		{
			return dao.IncFormReturn(familyformid, remarks);
		}
		
		@Override
		public int EmpBloodGropuEdit(String empno , String bloodgroup)throws Exception
		{
			return dao.EmpBloodGropuEdit(empno, bloodgroup);
		}
}
