package com.vts.ems.pis.service;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.Admin.model.LoginPasswordHistory;
import com.vts.ems.login.Login;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.dao.PisDao;
import com.vts.ems.pis.dto.UserManageAdd;
import com.vts.ems.pis.model.AddressEmec;
import com.vts.ems.pis.model.AddressNextKin;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.Appointments;
import com.vts.ems.pis.model.Awards;
import com.vts.ems.pis.model.DisciplineCode;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.pis.model.PISEmpFamilyDeclaration;
import com.vts.ems.pis.model.Passport;
import com.vts.ems.pis.model.PassportForeignVisit;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisEmpFamilyForm;
import com.vts.ems.pis.model.PisFamFormMembers;
import com.vts.ems.pis.model.PisPayLevel;
import com.vts.ems.pis.model.Property;
import com.vts.ems.pis.model.PropertyDetails;
import com.vts.ems.pis.model.Publication;
import com.vts.ems.pis.model.Qualification;
import com.vts.ems.pis.model.QualificationCode;
import com.vts.ems.utils.CharArrayWriterResponse;
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
		logger.info(new Date() +"Inside SERVICE getimage ");
		String result=null;
		try {
			String photoname=dao.PhotoPath(empid);
			String path = uploadpath+"//empimages";
			File f = new File(path+"//"+photoname);
			if(f.exists()) {
				result=encodeFileToBase64Binary(f);
			}                                     
		}catch (Exception e) {
			logger.error(new Date() + "Inside SERVICE getimage "+ e);
			e.printStackTrace();		
		}
		
		return result;
	}

	private static String encodeFileToBase64Binary(File file) {
		logger.info(new Date() +"Inside SERVICE encodeFileToBase64Binary ");
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
		logger.info(new Date() +"Inside SERVICE EmployeeAddSubmit ");
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
		logger.info(new Date() +"Inside SERVICE EmployeeEditSubmit ");
		Employee employee = dao.getEmp(String.valueOf(emp.getEmpId()));
		employee.setEmpName(emp.getEmpName());
		employee.setEmail(emp.getEmail());
		employee.setExtNo(emp.getExtNo());
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
		logger.info(new Date() +"Inside SERVICE EmployeeDetailsEditSubmit ");
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
		employee.setExServiceMan(emp.getExServiceMan());
		employee.setPerPassNo(emp.getPerPassNo());
		employee.setPhoneNo(emp.getPhoneNo());
		employee.setAltPhoneNo(emp.getAltPhoneNo());		
		employee.setDOP(emp.getDOP());
		employee.setCHSSNo(emp.getCHSSNo());
		employee.setBenovelentFundNo(emp.getBenovelentFundNo());
		employee.setDCMAFNo(emp.getDCMAFNo());
		employee.setITICreditSocNo(emp.getITICreditSocNo());
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
		 logger.info(new Date() +"Inside SERVICE saveFile ");
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
		logger.info(new Date() +"Inside SERVICE saveEmpImage ");
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
		logger.info(new Date() +"Inside SERVICE UserManagerAdd ");
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
		logger.info(new Date() +"Inside SERVICE UserMangerEdit ");
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
	public long PisFamFormMembersAdd(PisFamFormMembers formmember) throws Exception 
	{
		formmember.setCreatedDate(sdtf.format(new Date()));
		return dao.PisFamFormMembersAdd(formmember);
	}
	
	@Override
	public long PisFamFormMemberEdit(PisFamFormMembers formmember) throws Exception 
	{
//		PisFamFormMembers fetch = dao.getPisFamFormMembers(String.valueOf(formmember.getFormMemberId()));
		formmember.setCreatedDate(sdtf.format(new Date()));
		return dao.PisFamFormMemberEdit(formmember);
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
		logger.info(new Date() +"Inside SERVICE EditFamilyDetails ");
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
		logger.info(new Date() +"Inside SERVICE EditPerAddress ");
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
		logger.info(new Date() +"Inside SERVICE EditResAddress ");
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
		logger.info(new Date() +"Inside SERVICE EditNextKinAddress ");
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
		logger.info(new Date() +"Inside SERVICE EditEmecAddress ");
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
		public String ExistingPassword(String loginid) throws Exception 
		{
			return dao.OldPassword(loginid);
		}
		
		
		@Override
		public int PasswordChange(String OldPassword, String NewPassword, String loginid,String username)throws Exception {

			logger.info(new Date() +"Inside SERVICE PasswordChange ");
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
			logger.info(new Date() +"Inside SERVICE ResetPassword() ");
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
				logger.error(new Date() +"Inside SERVICE ResetPassword() ");
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
			logger.info(new Date() +"Inside SERVICE GetEmployeeList() ");
			try {
				return dao.GetEmployeeList();
			}catch (Exception e) {
				logger.error(new Date() +"Inside SERVICE GetEmployeeList() ");
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
		public Object[] Getemp(Long empId)throws Exception
		{
			return dao.Getemp(empId);
		}
		
		@Override
		public int UpdateSeniorityNumber(String empid, String newSeniorityNumber)throws Exception
		{
			logger.info(new Date() +"Inside Service UpdateSeniorityNumber ");
			try {
			
			Long empId=Long.parseLong(empid);
			Long SeniorityNumber=Long.parseLong(newSeniorityNumber);
			int result= 0;
			Long newSeniorityNumberL=SeniorityNumber;
			Object[] emp =dao.Getemp(empId);
			List<Object[]> EmpSenHaveUpdate=dao.UpdateAndGetList(empId,newSeniorityNumber); 
			result=dao.UpdateAllSeniority(empId,newSeniorityNumberL);
			List<Object[]> result1=EmpSenHaveUpdate.stream().filter(srno-> Long.parseLong(srno[0].toString())>=SeniorityNumber && Long.parseLong(srno[1].toString())!=empId  ).collect(Collectors.toList());

			Long srno=0l;
			if(emp!=null&&emp[0]!=null) {
				srno=Long.parseLong(emp[0].toString());
			}
			if(SeniorityNumber > srno) {
				Long srno3=srno;
				if(srno!=0) {
				List<Object[]> result2=EmpSenHaveUpdate.stream().filter(srno1-> Long.parseLong(srno1[0].toString())>srno3 && Long.parseLong(srno1[0].toString())<=SeniorityNumber  ).collect(Collectors.toList());
				
				for(Object[] obj:result2) {
					Long empIdL=Long.parseLong(obj[1].toString());
					Long serialno=Long.parseLong(obj[0].toString());
					result= dao.UpdateAllSeniority(empIdL, --serialno);
				}
				}else {
					List<Object[]> result2=EmpSenHaveUpdate.stream().filter(srno1-> SeniorityNumber >= Long.parseLong(srno1[0].toString())).collect(Collectors.toList());
					
					for(Object[] obj:result2) {
						Long empIdL=Long.parseLong(obj[1].toString());
						Long serialno=Long.parseLong(obj[0].toString());
						result= dao.UpdateAllSeniority(empIdL, --serialno);
					}
					
					int srNumber = Integer.parseInt(EmpSenHaveUpdate.stream().map(e-> e[0]).sorted(Collections.reverseOrder()).findFirst().get().toString());
							
					Object[] obj = EmpSenHaveUpdate.stream().filter(e-> e[0].toString().equalsIgnoreCase(String.valueOf(srNumber))).collect(Collectors.toList()).get(0);
					
					Long id=Long.parseLong(obj[1].toString());
					Long snum=Long.valueOf(srNumber) ;
					result=dao.UpdateAllSeniority(id,++snum);
				}
			}else {
			
				for(Object[] data:result1){	
				  Long empIdL=Long.parseLong(data[1].toString());
				  result= dao.UpdateAllSeniority(empIdL, ++newSeniorityNumberL);
				}
			
			}
			return result;
			}catch (Exception e){
				logger.error(new Date() +"Inside Service UpdateSeniorityNumber ");
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
		public List<Object[]> GetFormMembersList(String formid)throws Exception
		{
			return dao.GetFormMembersList(formid);
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
		public long FamilyMemDetailsForward(String formid,String action,String usernmae,String empid,String Remarks,HttpServletRequest req, HttpServletResponse res) throws Exception
		{
			logger.info(new Date() +"Inside SERVICE FamilyMemDetailsForward ");
			long count=0;
			
			Object[] formdata =  dao.GetFamFormData(formid);
			String formtype= formdata[2].toString();
			
			PisEmpFamilyForm familyform =dao.getPisEmpFamilyForm(formid);
			
			familyform.setFormStatus(action);
			familyform.setRemarks(Remarks);
			
			int approvalcount=0;
			EMSNotification notify = new EMSNotification();
			if(action.equalsIgnoreCase("F")) 
			{
				familyform.setForwardedDateTime(DateTimeFormatUtil.getSqlDateAndTimeFormat().format(new Date()).toString());
				
				
				List<Object[]> loginTypeEmpData = dao.loginTypeEmpData("P");
				if(loginTypeEmpData.size()>0) 
				{
					notify.setNotificationUrl("FamFormsApproveList.htm");
					notify.setNotificationDate(LocalDate.now().toString());
					notify.setEmpId(Long.parseLong(loginTypeEmpData.get(0)[1].toString()));
					notify.setNotificationBy(Long.parseLong(formdata[1].toString()));
					
					if(formtype.equalsIgnoreCase("I")) 
					{
						notify.setNotificationMessage("Family Member(s) Inclusion Form Recieved");
					}
					else if(formtype.equalsIgnoreCase("E")) 
					{
						notify.setNotificationMessage("Family Member(s) Exclusion Form Recieved");
					}
					else if(formtype.equalsIgnoreCase("D")) 
					{
						notify.setNotificationMessage("Family Member(s) Declaration Form Recieved");
					}
				}
				
			}else if(action.equalsIgnoreCase("R") ) 
			{
				
				if(formtype.equalsIgnoreCase("I")) 
				{
					notify.setNotificationMessage("Family Member(s) Inclusion Form Returned");
				}
				else if(formtype.equalsIgnoreCase("E")) 
				{
					notify.setNotificationMessage("Family Member(s) Exclusion Form Returned");
				}
				else if(formtype.equalsIgnoreCase("D")) 
				{
					notify.setNotificationMessage("Family Member(s) Declaration Form Returned");
				}
				
				notify.setNotificationUrl("FamIncExcFwdList.htm");
				notify.setNotificationDate(LocalDate.now().toString());
				notify.setEmpId(Long.parseLong(formdata[1].toString()));
				notify.setNotificationBy(Long.parseLong(empid));
				
			}
			else if(action.equalsIgnoreCase("A"))
			{
				
				familyform.setApprovedBy(Long.parseLong(empid));
				familyform.setApprovedDateTime(DateTimeFormatUtil.getSqlDateAndTimeFormat().format(new Date()).toString());
				
				if(formtype.equalsIgnoreCase("I")) 
				{
					approvalcount = FamilyMemIncConfirm(formid, empid, usernmae);
					notify.setNotificationMessage("Family Member(s) Inclusion Form Approved");
					DepIncFormFreeze(req, res, formid);
				}
				else if(formtype.equalsIgnoreCase("E")) 
				{
					ExcFormApprove(formid, empid,usernmae);
					notify.setNotificationMessage("Family Member(s) Exclusion Form Approved");
					DepExcFormFreeze(req, res, formid);
				}
				else if(formtype.equalsIgnoreCase("D")) 
				{
					FamilyMemIncConfirm(formid, empid, usernmae);
					ExcFormApprove(formid, empid,usernmae);
					notify.setNotificationMessage("Family Member(s) Declaration Form Approved");
					DepDeclareFormFreeze(req, res, formid);
				}
				notify.setNotificationUrl("FamIncExcFwdList.htm");
				notify.setNotificationDate(LocalDate.now().toString());
				notify.setEmpId(Long.parseLong(formdata[1].toString()));
				notify.setNotificationBy(Long.parseLong(empid));
			}
				
			
			count =dao.UpdateMemberStatus(familyform);
			
			notify.setIsActive(1);
			notify.setCreatedBy(usernmae);
			notify.setCreatedDate(sdtf.format(new Date()));
			if(notify.getEmpId()>0 && count>0) {
				dao.NotificationAdd(notify);
			}
		
			return count;
		}
		
		public void DepIncFormFreeze(HttpServletRequest req, HttpServletResponse res,String formid)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE DepIncFormFreeze ");
			try {
				
				
				String empid = "0";
				Object[] formdata = GetFamFormData(formid);
				if(formdata!=null) {
					empid = formdata[1].toString();
				}
				req.setAttribute("formdetails" , formdata);
				req.setAttribute("FwdMemberDetails",GetFormMembersList(formid));
				req.setAttribute("empdetails",getEmployeeInfo(empid) );
				req.setAttribute("employeeResAddr",employeeResAddr(empid) );
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				String filename="Dependent Addition Form";
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path); //DependentIncFormView.js
					        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/pis/DependentFormInc.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();                  
		        
		        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
		         
		        File file=new File(path +File.separator+ filename+".pdf");
		        
		        
		        String fname="DependantInclusion-"+formid;
				String filepath = "\\DependantDeclaration";
				int count=0;
				while(new File(uploadpath+filepath+"\\"+fname+".pdf").exists())
				{
					fname = "DependantInclusion-"+formid;
					fname = fname+" ("+ ++count+")";
				}
		        
		        saveFile(uploadpath+filepath, fname+".pdf", file);
		        
		        PISEmpFamilyDeclaration declare = PISEmpFamilyDeclaration.builder()
		        									.EmpId(Long.parseLong(formdata[1].toString()))
		        									.FamilyFormId(Long.parseLong(formid))
		        									.FilePath(filepath+"\\"+fname+".pdf")
		        									.build();
		        		
		        dao.EmpFamilyDeclarationAdd(declare);
		        									
		        		
		        
		        Path pathOfFile= Paths.get( path+File.separator+filename+".pdf"); 
		        Files.delete(pathOfFile);		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		public void DepExcFormFreeze(HttpServletRequest req, HttpServletResponse res,String formid)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE DepExcFormFreeze ");
			try {
				
				
				Object[] formdata = GetFamFormData(formid);
				
				String empid = formdata[1].toString();
				
				req.setAttribute("formdetails" , formdata);
				req.setAttribute("empdetails",getEmployeeInfo(empid) );				
				req.setAttribute("employeeResAddr",employeeResAddr(empid) );
//				req.setAttribute("FamilymemDropdown",service.EmpFamMembersListMedDep(empid,formid));
				req.setAttribute("ExcMemberDetails",GetExcFormMembersList(formid));
				
				req.setAttribute("relationtypes" , familyRelationList() );				
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
//				return "pis/DependentFormExc";
				
				String filename="Dependent Exclusion Form";
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
					        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/pis/DependentFormExc.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();             
		        
		        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
		         
		        File file=new File(path +File.separator+ filename+".pdf");
		        
		        
		        String fname="DependantExclusion-"+formid;
				String filepath = "\\DependantDeclaration";
				int count=0;
				while(new File(uploadpath+filepath+"\\"+fname+".pdf").exists())
				{
					fname = "DependantExclusion-"+formid;
					fname = fname+" ("+ ++count+")";
				}
		        
		        saveFile(uploadpath+filepath, fname+".pdf", file);
		        
		        PISEmpFamilyDeclaration declare = PISEmpFamilyDeclaration.builder()
		        									.EmpId(Long.parseLong(formdata[1].toString()))
		        									.FamilyFormId(Long.parseLong(formid))
		        									.FilePath(filepath+"\\"+fname+".pdf")
		        									.build();
		        		
		        dao.EmpFamilyDeclarationAdd(declare);
		        									
		        		
		        
		        Path pathOfFile= Paths.get( path+File.separator+filename+".pdf"); 
		        Files.delete(pathOfFile);		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public void DepDeclareFormFreeze(HttpServletRequest req, HttpServletResponse res,String formid)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE DepDeclareFormFreeze ");
			try {
				
				String empid; 
				Object[] formdata = GetFamFormData(formid);
				empid = formdata[1].toString();
					
				req.setAttribute("formdetails" , formdata);
				
				req.setAttribute("empdetails",getEmployeeInfo(empid) );
				req.setAttribute("employeeResAddr",employeeResAddr(empid) );
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				req.setAttribute("CHSSEligibleFamList", familyDetailsList(empid));
				
				String filename="DependantDeclaration";
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
				CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/pis/DependentFormDec.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();        
		        
		        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
		         
		        File file=new File(path +File.separator+ filename+".pdf");
		        
		        
		        String fname="DependantDeclaration-"+formid;
				String filepath = "\\DependantDeclaration";
				int count=0;
				while(new File(uploadpath+filepath+"\\"+fname+".pdf").exists())
				{
					fname = "DependantDeclaration-"+formid;
					fname = fname+" ("+ ++count+")";
				}
		        
		        saveFile(uploadpath+filepath, fname+".pdf", file);
		        
		        PISEmpFamilyDeclaration declare = PISEmpFamilyDeclaration.builder()
		        									.EmpId(Long.parseLong(formdata[1].toString()))
		        									.FamilyFormId(Long.parseLong(formid))
		        									.FilePath(filepath+"\\"+fname+".pdf")
		        									.build();
		        		
		        dao.EmpFamilyDeclarationAdd(declare);
		        									
		        		
		        
		        Path pathOfFile= Paths.get( path+File.separator+filename+".pdf"); 
		        Files.delete(pathOfFile);		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public PISEmpFamilyDeclaration getEmpFamilyDeclaration(String formid)throws Exception
		{
			return dao.getEmpFamilyDeclaration(formid);
		}
		

		public void saveFile(String uploadpath, String fileName, File fileToSave) throws IOException 
		{
		   logger.info(new Date() +"Inside SERVICE saveFile ");
		   Path uploadPath = Paths.get(uploadpath);
		          
		   if (!Files.exists(uploadPath)) {
			   Files.createDirectories(uploadPath);
		   }
		        
		   try (InputStream inputStream = new FileInputStream(fileToSave)) {
			   Path filePath = uploadPath.resolve(fileName);
		       Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		   } catch (IOException ioe) {       
			   throw new IOException("Could not save file: " + fileName, ioe);
		   }     
		}
		
		@Override
		public int FamilyMemIncConfirm(String formid,String empid,String username ) throws Exception
		{
			return dao.FamilyMemIncConfirm(formid, empid, username,"Y","I");
		}
		
		@Override
		public int ExcFormApprove(String formid,String empid,String username) throws Exception 
		{
			return dao.FamilyMemIncConfirm(formid, empid, username,"N","E");
		}
		
		@Override
		public List<Object[]> FamMemFwdEmpList() throws Exception 
		{
			return dao.FamMemFwdEmpList();
		}
		
		@Override
		public List<Object[]> FamMemApprovedList() throws Exception 
		{
			return dao.FamMemApprovedList();
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
		public  Object[] getMemberdata(String formmemberid) throws Exception
		{
			return dao.getMemberdata(formmemberid);
		}
		
		
		
		@Override
		public Long DepMemEditSubmit(EmpFamilyDetails Details)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE DepMemEditSubmit ");
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
			
//			member.setIncComment(Details.getIncComment());
//			
//			if(Details.getIncFilePath()!=null && !Details.getIncFilePath().trim().equals(""))
//			{
//				member.setIncFilePath(Details.getIncFilePath());
//			}
			
				
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
		public int FormFamilyMemberDelete(String formmemberid)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE FormFamilyMemberDelete ");
			Object[] member =dao.getMemberdata(formmemberid);
			if(member[14].toString().equals("0")) {
				dao.FormFamilyMemberHardDelete(member[0].toString());
			}
			
			return dao.FormFamilyMemberDelete(formmemberid);
		}
		
		@Override
		public int EmpBloodGropuEdit(String empno , String bloodgroup)throws Exception
		{
			return dao.EmpBloodGropuEdit(empno, bloodgroup);
		}
		
		@Override
		public List<Object[]> EmpFamMembersListMedDep(String empid,String formid) throws Exception
		{
			return dao.EmpFamMembersListMedDep(empid,formid);
		}
		
		@Override
		public List<Object[]> EmpFamMembersNotMedDep(String empid,String formid) throws Exception
		{
			return dao.EmpFamMembersNotMedDep(empid,formid);
		}
		
		
		@Override
		public List<Object[]> GetExcFormMembersList(String formid) throws Exception 
		{
			return dao.GetExcFormMembersList(formid);
		}
		
		@Override
		public List<Object[]> getEducationList(String empid)throws Exception
		{
			return dao.getEducationList(empid);
		}
		
		@Override
		public List<Object[]> getQualificationList()throws Exception{
			return dao.getQualificationList();
		}
		
		@Override
		public List<Object[]> getDiscipline()throws Exception{
			return dao.getDiscipline();
		}
		
		@Override
		public Qualification getQualificationDetails(int qualificationid)throws Exception{
			return dao.getQualificationDetails(qualificationid);
		}
		
		@Override
		public int DeleteQualification(String qualificationid,String Username)throws Exception{
			return dao.DeleteQualification(qualificationid,Username);
			
		}
		
		@Override
		public int AddQualification(Qualification Details)throws Exception{
			return dao.AddQualification( Details);
		}
		
		@Override
		public int EditQualification(Qualification Details)throws Exception{
			logger.info(new Date() +"Inside SERVICE EditQualification ");
			Qualification quali = dao.getQualificationDetails(Details.getQualification_id());
			
			  quali.setQuali_id(Details.getQuali_id());			
	    	  quali.setSponsored(Details.getSponsored());
	    	  quali.setDisci_id(Details.getDisci_id());
	    	  quali.setHindi_prof(Details.getHindi_prof());
	    	  quali.setUniversity(Details.getUniversity());
	    	  quali.setDivision(Details.getDivision());
	    	  quali.setSpecialization(Details.getSpecialization());
	    	  quali.setHonours(Details.getHonours());
	    	  quali.setAcq_bef_aft(Details.getAcq_bef_aft());
	    	  quali.setYearofpassing(Details.getYearofpassing());
	    	  quali.setCgpa(Details.getCgpa());
	    	  quali.setModifiedby(Details.getModifiedby());
	    	  quali.setModifieddate(Details.getModifieddate());
	    	  
			return dao.EditQualification( quali);
		}
		
		@Override
		public List<Object[]> getAppointmentList(String empid)throws Exception
		{
			return dao.getAppointmentList(empid);
		}
		
		@Override
		public List<Object[]> getDesignationList()throws Exception{
			return dao.getDesignationList();
		}
		@Override
		public List<Object[]> getRecruitment()throws Exception{
			return dao.getRecruitment();
		}
		
		@Override
		public int DeleteAppointment(String appointmentid,String Username)throws Exception{
			return dao.DeleteAppointment(appointmentid,Username);
			
		}
		
		@Override
		public int AddAppointment(Appointments app)throws Exception{
			return dao.AddAppointment( app);
		}
		
		@Override
		public Appointments getAppointmentsDetails(int appointmentsid)throws Exception
		{
			return dao.getAppointmentsDetails(appointmentsid);
		}
		
		@Override
		public int EditAppointment(Appointments app)throws Exception{
			logger.info(new Date() +"Inside SERVICE EditAppointment ");
			Appointments appintment = dao.getAppointmentsDetails(app.getAppointment_id());
			
			appintment.setMode_recruitment(app.getMode_recruitment());
			appintment.setDrdo_others(app.getDrdo_others());
			appintment.setDesig_id(app.getDesig_id());
			appintment.setOrg_lab(app.getOrg_lab());
			appintment.setFrom_date(app.getFrom_date());
			appintment.setTo_date(app.getTo_date());
			appintment.setCeptam_cycle(app.getCeptam_cycle());
			appintment.setVacancy_year(app.getVacancy_year());
			appintment.setRecruitment_year(app.getRecruitment_year());	    	  
			appintment.setAppointment_id(app.getAppointment_id());
	    	appintment.setModifiedby(app.getModifiedby());
	    	appintment.setModifieddate(app.getModifieddate());
	    	  
			return dao.EditAppointment( appintment);
		}
		
		@Override
		public List<Object[]> getAwardsList(String empid)throws Exception
		{
			return dao.getAwardsList(empid);
		}
		@Override
		public List<Object[]> getPisAwardsList()throws Exception{
			return dao.getPisAwardsList();
		}
		
		@Override
		public int DeleteAwards(String awardsid,String Username)throws Exception
		{
			return dao.DeleteAwards(awardsid,Username);
		}
		@Override
		public Awards getAwardsDetails(int awardsid)throws Exception
		{
			return dao.getAwardsDetails(awardsid);
		}
		
		@Override
		public int AddAwards(Awards app)throws Exception
		{
			return dao.AddAwards(app);
		}
		@Override
		public int EditAwards(Awards app)throws Exception{
			logger.info(new Date() +"Inside SERVICE EditAwards ");
			Awards appintment = dao.getAwardsDetails(app.getAwards_id());
			
			  appintment.setAwardListId(app.getAwardListId());
			  appintment.setCertificate(app.getCertificate());
	    	  appintment.setAward_by(app.getAward_by());
	    	  appintment.setCitation(app.getCitation());
	    	  appintment.setAward_date(app.getAward_date());
	    	  appintment.setDetails(app.getDetails());
	    	  appintment.setAward_cat(app.getAward_cat());
	    	  appintment.setCash(app.getCash());
	    	  appintment.setCash_amt(app.getCash_amt());
	    	  appintment.setAward_year(app.getAward_year());
	    	  appintment.setMedallion(app.getMedallion());
	    	  appintment.setModifiedby(app.getModifiedby());
		      appintment.setModifieddate(app.getModifieddate());
	    	  
			return dao.EditAwards( appintment);
		}
		
		@Override
		public List<Object[]> getPropertyList(String empid)throws Exception
		{
			return dao.getPropertyList(empid);
		}
		@Override
		public Property getPropertyDetails(int awardsid)throws Exception
		{
			return dao.getPropertyDetails(awardsid);
		}
		@Override
		public int DeleteProperty(String propertyid,String Username)throws Exception
		{
			return dao.DeleteProperty(propertyid,Username);
		}
		
		@Override
		public int AddProperty(Property app)throws Exception
		{
			return dao.AddProperty(app);
		}
		
		@Override
		public int EditProperty(Property app)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE EditProperty ");
			Property pro = dao.getPropertyDetails(app.getProperty_id()); 
				pro.setMovable(app.getMovable());
				pro.setAcquired_type(app.getAcquired_type());
				pro.setDetails(app.getDetails());
				pro.setDop(app.getDop());
				pro.setNoting_on(app.getNoting_on());
				pro.setRemarks(app.getRemarks());
				pro.setValue(app.getValue());
				pro.setProperty_id(app.getProperty_id());
				
				return dao.EditProperty(pro);
		}
		
		@Override
		public List<Object[]> getPublicationList(String empid)throws Exception
		{
			return dao.getPublicationList(empid);
		}
		@Override
		public List<Object[]> getPisStateList()throws Exception{
			return dao.getPisStateList();
		}
		
		@Override
		public Publication getPublicationDetails(int publicationid)throws Exception
		{
			return dao.getPublicationDetails(publicationid);
		}
		@Override
		public int AddPublication(Publication app)throws Exception
		{
			return dao.AddPublication(app);
		}
		@Override
		public int EditPublication(Publication app)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE EditPublication ");
			Publication pro = dao.getPublicationDetails(app.getPublication_id()); 
			
				pro.setPublication_id(app.getPublication_id());
				pro.setPub_type(app.getPub_type());
				pro.setAuthors(app.getAuthors());
				pro.setDiscipline(app.getDiscipline());
				pro.setTitle(app.getTitle());
				pro.setPub_name_vno_pno(app.getPub_name_vno_pno());
				pro.setPub_date(app.getPub_date());
				pro.setPatent_no(app.getPatent_no());
				pro.setCountry(app.getCountry());
				pro.setModifiedby(app.getModifiedby());
				pro.setModifieddate(app.getModifieddate());
				
			return dao.EditPublication(pro);
		}
		
		@Override
		public List<Object[]> getPassportVisitList(String empid)throws Exception
		{
			return dao.getPassportVisitList(empid);
		}
		@Override
		public Object[] getPassportList(String empid) throws Exception
		{
			return dao.getPassportList(empid);
		}
		
		@Override
		public Passport getPassportData(String empid)throws Exception
		{
			return dao.getPassportData(empid);
		}
		@Override
		public int AddPassport(Passport passport)throws Exception
		{
			return dao.AddPassport(passport);
		}
		@Override
		public int EditPassport(Passport passport)throws Exception
		{
			return dao.EditPassport(passport);
		}
		@Override
		public PassportForeignVisit getForeignVisitData(int foreignvisitid)throws Exception{
			return dao.getForeignVisitData(foreignvisitid);
		}
		@Override
		public int deleteForeignVisit(String addresid,String Username)throws Exception{
			return dao.deleteForeignVisit(addresid,Username);
			
		}
		
		@Override
		public int AddForeignVisit(PassportForeignVisit pfv)throws Exception{
			return dao.AddForeignVisit(pfv);
		}
		
		@Override
		public int EditForeignVisit(PassportForeignVisit app)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE EditForeignVisit ");
			PassportForeignVisit pfv = dao.getForeignVisitData(app.getPassportVisitId()); 
			
			pfv.setEmpId(app.getEmpId());
			pfv.setCountryName(app.getCountryName());
			pfv.setVisitFromDate(app.getVisitFromDate());
			pfv.setVisitToDate(app.getVisitToDate());
			pfv.setNocLetterNo(app.getNocLetterNo());
			pfv.setNocLetterDate(app.getNocLetterDate());
			pfv.setPurpose(app.getPurpose());
			pfv.setNocIssuedFrom(app.getNocIssuedFrom());
			pfv.setRemarks(app.getRemarks());
		    pfv.setModifiedBy(app.getModifiedBy());
			pfv.setModifiedDate(app.getModifiedDate());
				
			return dao.EditForeignVisit(pfv);
		}
		
		@Override
		public int DeleteEducationQualification(String id,String Username)throws Exception
		{
			return dao.DeleteEducationQualification(id,Username);
		}
		@Override
		public int EditEducationQualification(String id,String qualification,String Username)throws Exception
		{
			return dao.EditEducationQualification(id,qualification,Username);
		}
		
		@Override
		public int AddEducationQualification(QualificationCode qc)throws Exception 
		{
		
			return dao.AddEducationQualification(qc);
		}
		@Override
		public int DeleteDiscipline(String id,String Username)throws Exception
		{
			return dao.DeleteDiscipline(id,Username);
		}
		@Override
		public int EditDiscipline(String id,String discipline,String Username)throws Exception
		{
			return dao.EditDiscipline(id,discipline,Username);
		}
		
		@Override
		public int AddDiscipline(DisciplineCode dc)throws Exception 
		{
		
			return dao.AddDiscipline(dc);
		}
		

		@Override
		public Object[] getEmpData(String EmpNo) throws Exception {
		
			return dao.getEmpData(EmpNo);
		}
		
		@Override
		public List<Object[]> GetEmpStatusList() throws Exception
		{
			return dao.GetEmpStatusList();
		}
		@Override
		public List<Object[]> getGroupName() throws Exception
		{
			return dao.getGroupName();
		}
		
		@Override
		public List<Object[]> getDesignation() throws Exception
		{
			return dao.getDesignation();
		}
		
		@Override
		public List<Object[]> fetchAllEmployeeDetail() throws Exception
		{
			return dao.fetchAllEmployeeDetail();
		}

		@Override
		public List<Object[]> getEmployeeStatusWise(String empstatus) throws Exception {
			return dao.getEmployeeStatusWise(empstatus);
		}

		@Override
		public List<Object[]> getEmployeeDivOrGroupWise(int id) throws Exception {
			return dao.getEmployeeDivOrGroupWise(id);
		}

		@Override
		public List<Object[]> getEmployeeDesignationWise(int id) throws Exception {
			return dao.getEmployeeDesignationWise(id);
		}

		@Override
		public List<Object[]> getEmployeeGenderWise(String id) throws Exception {
			return dao.getEmployeeGenderWise(id);
		}
		
		@Override
		public List<Object[]> fetchAllPersonalDetail() throws Exception{
			return dao.fetchAllPersonalDetail();
		}
		
		@Override
		public List<Object[]> fetchPersonalDetailsNGOorCGO(String cattype) throws Exception
		{
			logger.info(new Date() +"Inside SERVICE fetchPersonalDetailsNGOorCGO ");
			 if("A".equalsIgnoreCase(cattype)) {
				 return dao.fetchAllPersonalDetail();
			 }else {
				 return dao.fetchPersonalDetailsNGOorCGO(cattype);
			 }
			
		}
		@Override
		public List<Object[]> getAllEmployeeList()throws Exception
		{
			return dao.getAllEmployeeList();
		}
		
		@Override
		public Object[] GetAllEmployeeDetails(String empid) throws Exception
		{
			return dao.GetAllEmployeeDetails(empid);
		}
		
		@Override
		public List<Object[]> getConfigurableReportList(String name,String DesigId,String DivisionId,String CatId,String Gender,String CadreId,String ServiceStatus,String CategoryId,String BG,String modeOfRecruitId,String AwardId)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE getConfigurableReportList ");
			  String DesigWise="";
			  String DivisionWise="";
			  String CatWise="";
			  String GenderWise="";
			  String CadreWise="";
			  String ServiceStatusWise="";
			  String CategoryWise="";
			  String BGWise="";
			 
			  String modeOfRecruitWise="";
			  String modeOfRecruitComparison="";
			  String modeOfRecruitTableName="";
			  
			  String AwardsWise="";
			  String AwardsWiseComparison="";
			  String AwardsWiseTableName="";
			  
			  if("Select".equalsIgnoreCase(DesigId)){DesigWise="";}else{DesigWise="AND  a.DesigId='"+DesigId+"'";}
			  if("Select".equalsIgnoreCase(DivisionId)){DivisionWise="";}else{DivisionWise="AND  a.divisionId='"+DivisionId+"'";}
			  if("Select".equalsIgnoreCase(CatId)){CatWise="";}else{CatWise="AND  j.CatId='"+CatId+"'";}
			  if("Select".equalsIgnoreCase(Gender)){GenderWise="";}else{GenderWise="AND  j.Gender='"+Gender+"'";}
			  if("Select".equalsIgnoreCase(CadreId)){CadreWise="";}else{CadreWise="AND  j.CadreId='"+CadreId+"'";}
			  if("Select".equalsIgnoreCase(ServiceStatus)){ServiceStatusWise="";}else{ServiceStatusWise="AND  j.ServiceStatus='"+ServiceStatus+"'";}
			  if("Select".equalsIgnoreCase(CategoryId)){CategoryWise="";}else{CategoryWise="AND  j.CategoryId='"+CategoryId+"'";}
			  if("Select".equalsIgnoreCase(BG)){BGWise="";}else{BGWise="AND  j.BloodGroup='"+BG+"'";}
			  
			  
			  if("Select".equalsIgnoreCase(modeOfRecruitId))
			  { modeOfRecruitWise="";
			    modeOfRecruitComparison="";
			    modeOfRecruitTableName="";
			    
			  }else{
			    modeOfRecruitWise="AND  g.mode_recruitment='"+modeOfRecruitId+"'";
			    modeOfRecruitComparison="AND a.EmpId=g.empid AND g.mode_recruitment=h.recruitment_id";
			    modeOfRecruitTableName=",pis_appointments g ,pis_mode_recruitment_code h";
			   }
			  
			  if("Select".equalsIgnoreCase(AwardId))
			  {   AwardsWise="";
			      AwardsWiseComparison="";
			      AwardsWiseTableName="";
			    
			  }else{
				  AwardsWise="AND  i.AwardListId="+AwardId+"";
				  AwardsWiseComparison="AND a.EmpId=i.empid";
				  AwardsWiseTableName=",pis_awards i ";
			   }

			    String ConfigurableReportQuery=" SELECT a.EmpId,a.empName,b.Designation,a.SrNo,j.Gender,j.ServiceStatus,"+
	                                       " j.BloodGroup,c.divisionNAME,d.cat_name,e.Cadre,f.category_type "+
	                                       " FROM employee a ,employee_desig b ,division_master c ,pis_cat_class d ,pis_cadre e,pis_category f,employee_details j "+modeOfRecruitTableName+" "+AwardsWiseTableName+" "+
	                                       " WHERE  j.EmpStatus='P' and j.CatId<>'Z' and  a.empName LIKE '%"+name+"%' "+DivisionWise+" "+DesigWise+" "+CatWise+"  "+GenderWise+"  "+CadreWise+"  "+ServiceStatusWise+" "+
	                                       " "+CategoryWise+" "+BGWise+"   "+modeOfRecruitWise+" "+AwardsWise+" "+
	                                       " AND j.empno=a.empno AND a.DesigId=b.DesigId  AND a.divisionId=c.divisionId AND "+
	                                       " j.CatId=d.cat_id AND j.CadreId=e.CadreId AND j.CategoryId=f.category_id " +
	                                       " "+modeOfRecruitComparison+" "+AwardsWiseComparison+" GROUP BY a.EmpId ";
	                                       
			 
			return dao.getConfigurableReportList(ConfigurableReportQuery);
		}
		
		
		public List<Object[]> getconfigurablereportselectionwise(String name, String designation, String groupDivision,
				String catClass, String gender, String cadre, String serviceStatus, String pay_Level, String qualification,
				String propertyType, String pubType, String category, String bG, String quarter, String physicalHandicap,
				String religion, String appointment, String awards)throws Exception {

			logger.info(new Date() +"Inside SERVICE getconfigurablereportselectionwise ");
			List<Object[]> list = null;
				try {
				//Conditions to get records
				String cname="",cdesignation="",cgroupDivision="",ccatClass="",cgender="",ccadre="",
						cserviceStatus="",cpay_Level="",cqualification="",cpropertyType="",cpubType="",
						ccategory="",cbG="",cquarter="",cphysicalHandicap="",creligion="",cappointment="",cawards="";
				
				// For Dynamic columns to get the records from DB
				String desigcolumn="",gendercol="",groupcol="",catcol="",cadrecol="",
						bgcolumn="",servicestatuscol="",religioncol="",quartercol="",
						physicalHandicapcol="",pay_levelcol="",categorycol="",qualificationcol="",
						pub_typecol="",propertyTypecol="",appointmentcol="",awardscol="";
				
				// For DB Tables
				String desigtab="",grouptab="",cattab="",cadretab="",pay_leveltab="",categorytab="",
						qualificationtabs="",pub_typetab="",propertyTypetab="",appointmenttab="",awardstab="";
				
				
				if(!"".equals(name)) {
					cname=name;
				}
				if(null !=serviceStatus && !("".equals(serviceStatus))) {servicestatuscol=",ed.ServiceStatus";cserviceStatus="AND ed.ServiceStatus="+"'"+serviceStatus+"'";}
				
				if(null !=propertyType && !("".equals(propertyType))) {propertyTypecol=",o.acquired_type";propertyTypetab=",pis_property o";cpropertyType="AND a.EmpId=o.empid AND o.acquired_type="+"'"+propertyType+"'";}
				
				if(null !=pubType && !("".equals(pubType))) {pub_typecol=",n.pub_type";pub_typetab=",pis_publication n";cpubType="AND a.EmpId=n.empid  AND n.pub_type="+"'"+pubType+"'";}
				
				if(null !=bG && !("".equals(bG))) {bgcolumn=",ed.BloodGroup";cbG="AND ed.BloodGroup="+"'"+bG+"'";}
				
				if(null !=quarter && !("".equals(quarter))) {quartercol=",ed.Quarters";cquarter="AND ed.Quarters="+"'"+quarter.split("#")[0]+"'";}
				
				if(null !=physicalHandicap && !("".equals(physicalHandicap))) {physicalHandicapcol=",ed.PH";cphysicalHandicap="AND ed.PH="+"'"+physicalHandicap.split("#")[0]+"'";}
				
				if(null !=religion && !("".equals(religion))) {religioncol=",ed.Religion";creligion="AND ed.Religion="+"'"+religion+"'";}
				
				if(null != awards && !("".equals(awards))) {
					String[] AwardArray = awards.split("#");
					String AwardId = AwardArray[0];
					//String AwardName = AwardArray[1];
					awardscol=",r.awardname";
					awardstab=",pis_awards t,pis_award_list r";
					cawards="AND a.EmpId = t.empid AND t.AwardListId = r.AwardListId AND t.AwardListId ="+AwardId;
				}
				
				if (null != designation && !("".equals(designation))) {
					String[] DesigIdAndDesignationArray = designation.split("#");
					String DesigId = DesigIdAndDesignationArray[0];
					//String DesigName = DesigIdAndDesignationArray[1];
					desigtab=",employee_desig b";
					desigcolumn=",b.Designation";
					cdesignation="AND  a.DesigId=b.DesigId AND b.DesigId='"+DesigId+"'";
				}
				
				if(null !=groupDivision && !("".equals(groupDivision))) {
					String[] DivisionIdAndDivisionNameArray = groupDivision.split("#");
					String divisionid = DivisionIdAndDivisionNameArray[0];
					//String DivisionName1 = DivisionIdAndDivisionNameArray[1];
					grouptab=",division_master c";
					groupcol=",c.divisionname";
					cgroupDivision="AND a.divisionId=c.divisionId AND  c.divisionId='"+divisionid+"'";
				}
				
				if(null !=catClass && !("".equals(catClass))) {
					String[] CatIdAndCatClassArray = catClass.split("#");
					String CatId = CatIdAndCatClassArray[0];
					//String CatClass1 = CatIdAndCatClassArray[1];
					cattab=",pis_cat_class d";
					catcol=",d.cat_name";
					ccatClass="AND ed.CatId=d.cat_id AND  d.cat_id='"+CatId+"'";
				}
				
				if(null !=gender && !("".equals(gender))){
					String[] GenderIdAndGenderNameArray = gender.split("#");
					//String Gender = GenderIdAndGenderNameArray[0];
					String GenderName = GenderIdAndGenderNameArray[1];
					gendercol=",ed.Gender";
					cgender="AND  ed.gender='"+GenderName+"'";;
				}
				
				if(null !=cadre && !("".equals(cadre))) {
					String[] CadreIdAndCadreNameArray = cadre.split("#");
					String CadreId = CadreIdAndCadreNameArray[0];
					//String CadreName = CadreIdAndCadreNameArray[1];
					cadretab=",pis_cadre e";
					cadrecol=",e.Cadre";
					ccadre="AND ed.CadreId=e.CadreId AND  e.CadreId='"+CadreId+"'";
				}
				
				if(null !=pay_Level && !("".equals(pay_Level))) {
					String[] pay_LevelNameArray = pay_Level.split("#");
					String pay_LevelId = pay_LevelNameArray[0];
					//String pay_LevelName = pay_LevelNameArray[1];
					pay_levelcol=",g.PayLevel";
					pay_leveltab=",pis_pay_level g";
					cpay_Level="AND ed.PayLevelId=g.PayLevelId AND g.PayLevelId="+"'"+pay_LevelId+"'";
				}
				
				if(null !=category && !("".equals(category))) {
					String[] CategoryIdAndCategoryNameArray = category.split("#");
					String CategoryId = CategoryIdAndCategoryNameArray[0];
					//String CategoryName = CategoryIdAndCategoryNameArray[1];
					categorycol=",f.category_type";
					categorytab=",pis_category f";
					ccategory=" AND ed.CategoryId=f.CATEGORY_ID AND f.CATEGORY_ID="+"'"+CategoryId+"'";
				}
				
				if(null !=qualification && !("".equals(qualification))) {
					String[] qualificationNameArray = qualification.split("#");
					String qualificationd = qualificationNameArray[0];
					//String qualificationName = qualificationNameArray[1];
					qualificationcol=",l.quali_title ";
					qualificationtabs=",pis_qualification m,pis_quali_code  l";
					cqualification="AND a.EmpId=m.empid AND m.quali_id=l.quali_id AND l.quali_id="+"'"+qualificationd+"'";
				}
				
				if(null !=appointment && !("".equals(appointment))) {
					String[] appointmentNameArray = appointment.split("#");
					String appointmentid = appointmentNameArray[0];
					//String appointmentName = appointmentNameArray[1];
					appointmentcol=",p.mode_recruitment_title";
					appointmenttab=",pis_appointments q,pis_mode_recruitment_code p";
					cappointment="AND a.EmpId=q.empid AND q.mode_recruitment=p.recruitment_id AND p.recruitment_id='"+appointmentid+"'";
				}
				
				String ConfigurableReportQuery="SELECT a.EmpId,a.empName" + desigcolumn+ groupcol+ catcol+gendercol+cadrecol+
												servicestatuscol+categorycol+bgcolumn+religioncol+quartercol+physicalHandicapcol+pay_levelcol+
												qualificationcol+pub_typecol+propertyTypecol+appointmentcol+awardscol+
												" FROM Employee a , employee_details ed"+desigtab+ grouptab+cattab+cadretab+pay_leveltab+categorytab+
												qualificationtabs+pub_typetab+propertyTypetab+appointmenttab+awardstab+
												" WHERE  ed.EmpStatus='P' and a.empno=ed.empno and ed.CatId<>'Z' and  a.empName LIKE '%"+cname+"%'" +
												cdesignation + cgender + cgroupDivision+ ccatClass + ccadre+ cserviceStatus +cbG
												+creligion+cquarter+cphysicalHandicap+cpay_Level+ccategory+cqualification+cpubType+
												cpropertyType+cappointment+cawards+"";
				 
								
				  list=dao.getConfigurableReportList(ConfigurableReportQuery);
				 
				}catch (Exception e) {
					logger.error(new Date() +"Inside SERVICE getconfigurablereportselectionwise ");
					e.printStackTrace();
				}
				return list;
		}
		
		@Override
		public List<Object[]> getDefaultReport()throws Exception {
			logger.info(new Date() +"Inside SERVICE getDefaultReport ");
			Calendar now = Calendar.getInstance();
			int year=now.get(Calendar.YEAR);
			
			List<Object[]> defaultreports=dao.getDefaultReport(year);
			
			return defaultreports;
		}
		
		@Override
		public List<Object[]> getDobReport(int year, int month)throws Exception {
			
			List<Object[]> dobreports=dao.getDobReport(year,month);
			
			return dobreports;
		}
		
		@Override
		public List<Object[]> getDoaReport(int year, int month)throws Exception
		{	
			List<Object[]> doareports=dao.getDoaReport(year,month);
				return doareports;
			
		}
		
		@Override
		public List<Object[]> getDorReport(int year, int month)throws Exception
		{	
			List<Object[]> doareports=dao.getDorReport(year,month);
				return doareports;
			
		}
		
		@Override
		public List<Object[]> getDojReport(int year, int month)throws Exception
		{	
			List<Object[]> doareports=dao.getDojReport(year,month);
				return doareports;
			
		}
		@Override
		public List<Object[]> fetchCadreNameCode()throws Exception
		{
			return dao.fetchCadreNameCode();
		}
		@Override
		public List<Object[]> EmployeeList(String cadreid)throws Exception
		{
			return dao.EmployeeList(cadreid);
		}
		@Override
		public int GetMaxSeniorityNo()throws Exception
		{
			return dao.GetMaxSeniorityNo();
		}
		
		@Override
		public Long AddPropertyDetails(PropertyDetails details) throws Exception {

			return dao.AddPropertyDetails(details);
		}

		@Override
		public List<Object[]> editPropertyDetails(String PropertyId) throws Exception {

			return dao.editPropertyDetails(PropertyId);
		}

		@Override
		public Long updatePropertyDetails(PropertyDetails details, String propertyId) throws Exception {

			return dao.updatePropertyDetails(details, propertyId);
		}

		@Override
		public List<Object[]> getLabDetails() throws Exception {
			return dao.getLabDetails();
		}

		@Override
		public List<Object[]> getEmpDetails(String empId) throws Exception {

			return dao.GetEmpdetails(empId);
		}

		@Override
		public List<Object[]> getPropertiesYearwise(int year, String empId) throws Exception {

			return dao.getPropertiesYearWise(year, empId);
		}

		@Override
		public int deletePropertyDetails(String propertyId, String Username) throws Exception {

			return dao.deletePropertyDetails(propertyId, Username);
		}
		@Override
		public List<Object[]> familyDetailsList(String empid) throws Exception
		{
			return dao.familyDetailsList(empid);
		}

		@Override
		public BigInteger getFormYear(int year,Long empId) throws Exception {
			
			return dao.getFormYear(year,empId);
		}
}
