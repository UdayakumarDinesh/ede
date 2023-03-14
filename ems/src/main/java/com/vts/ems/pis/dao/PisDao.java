package com.vts.ems.pis.dao;

import java.math.BigInteger;
import java.util.List;

import com.vts.ems.Admin.model.LoginPasswordHistory;
import com.vts.ems.login.Login;
import com.vts.ems.model.EMSNotification;
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

public interface PisDao {

	public List<Object[]> EmployeeDetailsList(String LoginType, String Empid) throws Exception;
	public Object[] EmployeeDetails(String empid) throws Exception;
	public Object[] GetAllEmployeeDetails(String empid) throws Exception;
	public List<DivisionMaster> DivisionList() throws Exception;
	public List<EmployeeDesig> DesigList() throws Exception;
	public List<PisPayLevel> PayLevelList() throws Exception;
	public List<PisCadre> PisCaderList() throws Exception;
	public List<PisCatClass> PisCatClassList() throws Exception;
	public List<PisCategory> PisCategoryList() throws Exception;
	public List<EmpStatus> EmpStatusList() throws Exception;
	public long EmployeeAddSubmit(Employee emp) throws Exception;
	public long EmployeeEditSubmit(Employee emp) throws Exception;
	public long EmployeeDetailsAddSubmit(EmployeeDetails emp) throws Exception;
	public long EmployeeDetailsEditSubmit(EmployeeDetails emp) throws Exception;
	public EmployeeDetails getEmployee(String empno) throws Exception;
	public EmployeeDetails getEmployeeDetailsData(String empno) throws Exception;
	public Employee getEmp(String empid) throws Exception;
	public int PunchcardList(String value)throws Exception;
	public long getempno()throws Exception;
	public String PhotoPath(String empid)throws Exception;
	public int PhotoPathUpdate(String Path, String empno) throws Exception;
	public List<Object[]> LoginMasterList() throws Exception;
	public List<Object[]> getEmpList()throws Exception;
	public List<Object[]> getLoginTypeList()throws Exception;
	public int UserManagerDelete(String username , String loginid)throws Exception;
	public int UserNamePresentCount(String UserName)throws Exception;
	public Long UserManagerAdd(Login login) throws Exception;
	public Login getLoginEditData(Long LoginId)throws Exception;
	public int UserManagerEdit(Login login)throws Exception;
	public List<Object[]> getFamilyMembersList(String empid)throws Exception;
	public Object[] GetEmpData(String empid)throws Exception;
	public List<Object[]> getFamilyRelation()throws Exception;
	public List<Object[]> getFamilyStatus()throws Exception;
	public Long AddFamilyDetails(EmpFamilyDetails Details) throws Exception;
	public int DeleteMeber(String familyid,String Username)throws Exception;
	public EmpFamilyDetails	getMemberDetails(String familyid)throws Exception;
	public Long EditFamilyDetails(EmpFamilyDetails Details) throws Exception;
	public EmpFamilyDetails getMember(String familyid) throws Exception;
	public Object[] getPerAddress(String Empid)throws Exception;
	public List<Object[]> getStates ()throws Exception;
	public List<Object[]> GetAllEmployee()throws Exception;
	public Long AddPerAddress(AddressPer peraddress)throws Exception;
	public AddressPer getPerAddressData(String empid)throws Exception;
	public Long EditPerAddress(AddressPer address) throws Exception;
	public AddressPer getPeraddress(long addressid) throws Exception ;
	public List<Object[]> getResAddress(String empid)throws Exception;
	public Object[]  getKinAddress(String empid)throws Exception;
	public Object[]  getEmeAddress(String empid)throws Exception;
	public Long AddResAddress(AddressRes resaddress)throws Exception;
	public AddressRes getResAddressData(String addressid)throws Exception;
	public Long EditResAddress(AddressRes address) throws Exception;
	public int deleteResAdd(String addresid,String Username)throws Exception;
	public Long AddNextAddress(AddressNextKin nextaddress)throws Exception;
	public Long EditNextKinAddress(AddressNextKin address)throws Exception;
	public AddressNextKin getNextKinaddress(long addressid) throws Exception;
	public AddressNextKin getNextKinAddressData(String empid)throws Exception;
	public Long AddEmecAddress(AddressEmec Emecaddress)throws Exception;
	public AddressEmec getEmecaddress(long addressid) throws Exception;
	public Long EditEmecAddress(AddressEmec address) throws Exception ;
	public List<Object[]> ReqEmerAddajax(String Empid) throws Exception ;
	public AddressEmec getEmecAddressData(String empid)throws Exception;
	public String OldPassword(String UserId) throws Exception;
	public List<Object[]> AuditStampingList(String Username,String Fromdate,String Todate) throws Exception;
	public int PasswordChange(String OldPassword, String NewPassword ,String UserName, String ModifiedDate,String username)throws Exception;
	public Object[] EmployeeEmeAddressDetails(String empid) throws Exception;
	public Object[] EmployeeNextAddressDetails(String empid) throws Exception;
	public Object[] EmployeePerAddressDetails(String empid) throws Exception;
	public List<Object[]> EmployeeResAddressDetails(String empid) throws Exception;
	public List<Object[]> getFamilydetails(String empid) throws Exception;
	public int ResetPassword(String loginid, String password,String UserName )throws Exception;
	public Object[] GetEmpPhoneNo(String loginid) throws Exception;
	public List<Object[]> GetEmployeeList()throws Exception;
	public long loginHisAddSubmit(LoginPasswordHistory model) throws Exception;
	public Object[] GetEmpDetails(String empid)throws Exception;
	public List<Object[]> UpdateAndGetList(Long empId, String newSeniorityNumber)throws Exception;
	public int UpdateAllSeniority(Long empIdL, Long long1)throws Exception;
	public EmpFamilyDetails getFamilyMemberModal(String familydetailsid) throws Exception;
	public List<Object[]> FamMemFwdEmpList() throws Exception;
	public List<Object[]> GetFormMembersList(String formid) throws Exception;
	public Object[] getEmployeeInfo(String empid) throws Exception;
	public Object[] employeeResAddr(String empid) throws Exception;
	public List<Object[]> familyRelationList() throws Exception;
	public Object[] RelationshipData(String relationid) throws Exception;
	public Object[] getMemberdata(String formmemberid) throws Exception;
	public List<Object[]> EmpFamFormsList(String empid, String status) throws Exception;
	public int FamilyMemIncConfirm(String formid, String empid, String username,String medDepStatus,String incexc) throws Exception;
	public long EmpFamilyFormAdd(PisEmpFamilyForm form) throws Exception;
	public Object[] GetFamFormData(String familyformid) throws Exception;
	public int FormFamilyMemberDelete(String formmemberid) throws Exception;
	public int EmpBloodGropuEdit(String empno, String bloodgroup) throws Exception;
	public List<Object[]> GetEmployeeLoginData(String loginid) throws Exception;
	public long NotificationAdd(EMSNotification notification) throws Exception;
	public List<Object[]> loginTypeEmpData(String logintype) throws Exception;
	public PisEmpFamilyForm getPisEmpFamilyForm(String familyformid) throws Exception;
	public long UpdateMemberStatus(PisEmpFamilyForm famform) throws Exception;
	public List<Object[]> EmpFamMembersListMedDep(String empid,String formid) throws Exception;
	public List<Object[]> GetExcFormMembersList(String formid) throws Exception;
	public long PisFamFormMembersAdd(PisFamFormMembers formmember) throws Exception;
	public PisFamFormMembers getPisFamFormMembers(String formmemberid) throws Exception;
	public long PisFamFormMemberEdit(PisFamFormMembers formmember) throws Exception;
	public int FormFamilyMemberHardDelete(String familydetailsid) throws Exception;
	public List<Object[]> EmpFamMembersNotMedDep(String empid,String formid) throws Exception;
	public List<Object[]> getEducationList(String empid)throws Exception;
	public List<Object[]> getQualificationList()throws Exception;
	public List<Object[]> getDiscipline()throws Exception;
	public Qualification getQualificationDetails(int qualificationid)throws Exception;
	public int DeleteQualification(String qualificationid,String Username)throws Exception;
	public int AddQualification(Qualification Details)throws Exception;
	public int EditQualification(Qualification Details) throws Exception;
	public List<Object[]> getAppointmentList(String empid)throws Exception;
	public List<Object[]> getDesignationList()throws Exception;
	public List<Object[]> getRecruitment()throws Exception;
	public int DeleteAppointment(String appointmentid,String Username)throws Exception;
	public Appointments getAppointmentsDetails(int appointmentsid)throws Exception;
	public int AddAppointment(Appointments app)throws Exception;
	public int EditAppointment(Appointments app)throws Exception;
	public List<Object[]> getAwardsList(String empid)throws Exception;
	public List<Object[]> getPisAwardsList()throws Exception;
	public int DeleteAwards(String awardsid,String Username)throws Exception;
	public Awards getAwardsDetails(int awardsid)throws Exception;
	public int AddAwards(Awards app)throws Exception;
	public int EditAwards(Awards app)throws Exception;
	public List<Object[]> getPropertyList(String empid)throws Exception;
	public Property getPropertyDetails(int awardsid)throws Exception;
	public int DeleteProperty(String propertyid,String Username)throws Exception;
	public int AddProperty(Property app)throws Exception;
	public int EditProperty(Property app)throws Exception;
	public List<Object[]> getPublicationList(String empid)throws Exception;
	public List<Object[]> getPisStateList()throws Exception;
	public Publication getPublicationDetails(int publicationid)throws Exception;
	public int AddPublication(Publication app)throws Exception;
	public int EditPublication(Publication app)throws Exception;
	public List<Object[]> getPassportVisitList(String empid)throws Exception;
	public Object[] getPassportList(String empid) throws Exception;
	public Passport getPassportData(String empid)throws Exception;
	public int AddPassport(Passport passport)throws Exception;
	public int EditPassport(Passport passport)throws Exception;
	public PassportForeignVisit getForeignVisitData(int foreignvisitid)throws Exception;
	public int deleteForeignVisit(String addresid,String Username)throws Exception;
	public int AddForeignVisit(PassportForeignVisit pfv)throws Exception;
	public int EditForeignVisit(PassportForeignVisit pfv)throws Exception;
	public int DeleteEducationQualification(String id,String Username)throws Exception;
	public int EditEducationQualification(String id,String qualification,String Username)throws Exception;
	public int AddEducationQualification(QualificationCode qc)throws Exception ;
	public int DeleteDiscipline(String id,String Username)throws Exception;
	public int EditDiscipline(String id,String discipline,String Username)throws Exception;
	public int AddDiscipline(DisciplineCode dc)throws Exception;
	public Object[] getEmpData(String EmpNo) throws Exception;
	public List<Object[]> GetEmpStatusList() throws Exception;
	public List<Object[]> getDesignation() throws Exception;
	public List<Object[]> fetchAllEmployeeDetail() throws Exception;
	public List<Object[]> getGroupName()throws Exception;
	public List<Object[]> getEmployeeStatusWise(String empstatus) throws Exception;
	public List<Object[]> getEmployeeDivOrGroupWise(int id) throws Exception;
	public List<Object[]> getEmployeeDesignationWise(int id) throws Exception;
	public List<Object[]> getEmployeeGenderWise(String id) throws Exception;
	public List<Object[]> fetchAllPersonalDetail() throws Exception;
	public List<Object[]> fetchPersonalDetailsNGOorCGO(String cattype) throws Exception;
	public List<Object[]> getConfigurableReportList(String ConfigurableReportQuery)throws Exception;
	public List<Object[]> getAllEmployeeList()throws Exception;
	public List<Object[]> getDefaultReport(int year)throws Exception;
	public List<Object[]> getDobReport(int year, int month)throws Exception;
	public List<Object[]> getDoaReport(int year, int month)throws Exception;
	public List<Object[]> getDorReport(int year, int month)throws Exception;
	public List<Object[]> getDojReport(int year, int month)throws Exception;
	public List<Object[]> fetchCadreNameCode()throws Exception;
	public List<Object[]> EmployeeList(String cadreid)throws Exception;
	public int GetMaxSeniorityNo()throws Exception;
	public Object[] Getemp(Long empId)throws Exception;
	public List<Object[]> FamMemApprovedList() throws Exception;
	public Long AddPropertyDetails(PropertyDetails details) throws Exception;
	public List<Object[]> editPropertyDetails(String propertyId)throws Exception;
	public Long updatePropertyDetails(PropertyDetails details, String propertyId) throws Exception;
	public List<Object[]> getLabDetails() throws Exception;
	public List<Object[]> GetEmpdetails(String empId) throws Exception;
	public List<Object[]> getPropertiesYearWise(int year, String empId) throws Exception;
	public int deletePropertyDetails(String propertyId,String Username) throws Exception ;
	public List<Object[]> familyDetailsList(String empid) throws Exception;
	public long EmpFamilyDeclarationAdd(PISEmpFamilyDeclaration declare) throws Exception;
	public PISEmpFamilyDeclaration getEmpFamilyDeclaration(String formid) throws Exception;
	public Object[] getFormYear(Long empId)throws Exception;
	public void UpdateAnnualDeclarationAllEmp(String status) throws Exception;
	public int ChangeAnnualDeclarationStatus(String Empid, String status) throws Exception;
}
