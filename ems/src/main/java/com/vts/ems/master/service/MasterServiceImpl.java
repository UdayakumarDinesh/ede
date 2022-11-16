package com.vts.ems.master.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.chss.controller.CHSSController;
import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.master.dao.MasterDao;
import com.vts.ems.master.dto.CircularListDto;
import com.vts.ems.master.dto.MasterEditDto;
import com.vts.ems.master.model.CHSSDoctorRates;
import com.vts.ems.master.model.CHSSEmpanelledHospital;
import com.vts.ems.master.model.CircularList;
import com.vts.ems.master.model.DoctorList;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.pis.model.EmployeeDesig;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	MasterDao dao;
	
	private static final Logger logger = LogManager.getLogger(MasterServiceImpl.class);
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	@Override
	public List<Object[]> OtherItems() throws Exception 
	{
		return dao.OtherItems();
	}
	
	@Override
	public List<Object[]> ChssTestMain() throws Exception
	{
		return dao.ChssTestMain();	
	}
	
	@Override
	public List<Object[]> ChssTestSub() throws Exception
	{
		return dao.ChssTestSub();	
	}
	
	@Override
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception
	{
		return dao.AddTestSub(TestSub);
	}
	
	@Override
	public CHSSTestSub testSub(String subid)throws Exception
	{
		return dao.testSub(subid);
	}
	
	@Override
	public Long EditTestSub(CHSSTestSub TestSub)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EditTestSub ");
		CHSSTestSub test=dao.getTestSub(TestSub.getTestSubId());
			test.setTestMainId(TestSub.getTestMainId());
			test.setTestName(TestSub.getTestName());
			test.setTestRate(TestSub.getTestRate());
			test.setTestCode(TestSub.getTestCode());
			test.setModifiedBy(TestSub.getModifiedBy());
			test.setModifiedDate(TestSub.getModifiedDate());
		return dao.EditTestSub(test);
	}
	
	@Override
	public CHSSOtherItems getOtherItem(int itemid) throws Exception
	{
		return dao.getOtherItem(itemid);
	}
	@Override
	public int AddOtherItem(CHSSOtherItems item)throws Exception
	{
		return dao.AddOtherItem(item);
	}
	
	@Override
	public int EditItem(CHSSOtherItems item)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EditItem ");
		CHSSOtherItems test=dao.getOtherItem(item.getOtherItemId());
			test.setOtherItemName(item.getOtherItemName());
			test.setModifiedBy(item.getModifiedBy());
			test.setModifiedDate(item.getModifiedDate());
		return dao.EditItem(test);
	}
	
	@Override
	public List<Object[]> getMedicineList()throws Exception
	{
		return dao.getMedicineList();
	}
	@Override
	public List<Object[]> getMedicineListByTreatment(String treatmentname)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE getMedicineListByTreatment ");
		if("A".equalsIgnoreCase(treatmentname)) {
			return dao.getMedicineList();
		}else {
			return dao.getMedicineListByTreatment(treatmentname);
		}	
	}
	@Override
	public List<Object[]> GetTreatmentType()throws Exception
	{
	return dao.GetTreatmentType();	
	}
	@Override
	public int Checkduplicate(String medicinename,String treatid)throws Exception{
		return dao.Checkduplicate(medicinename,treatid);
	}
	@Override
	public CHSSMedicinesList getCHSSMedicine(long medicineid) throws Exception {
		return dao.getCHSSMedicine(medicineid);
	}
	
	@Override
	public CHSSDoctorRates getCHSSDoctorRates(int DocRateId) throws Exception {
		return dao.getCHSSDoctorRates(DocRateId);
	}
	
	@Override
	public Long AddMedicine(CHSSMedicinesList medicine)throws Exception
	{
		return dao.AddMedicine(medicine);
	}
	
	@Override
	public int AddDocQualification(CHSSDoctorRates  DocRate)throws Exception
	{
		return dao.AddDocQualification(DocRate);
	}
	
	@Override
	public Long EditMedicine(CHSSMedicinesList medicine)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EditMedicine ");
		CHSSMedicinesList mlist  = dao.getCHSSMedicine(medicine.getMedicineId());
		
		mlist.setMedicineName(medicine.getMedicineName());
		mlist.setTreatTypeId(medicine.getTreatTypeId());
		mlist.setModifiedBy(mlist.getModifiedBy());
		mlist.setModifiedDate(mlist.getModifiedDate());
		return dao.EditMedicine(mlist);
	}
	
	@Override
	public Long EditDelete(CHSSMedicinesList medicine)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EditDelete ");
		CHSSMedicinesList mlist  = dao.getCHSSMedicine(medicine.getMedicineId());
		
		mlist.setIsActive(0);
		mlist.setModifiedBy(mlist.getModifiedBy());
		mlist.setModifiedDate(mlist.getModifiedDate());
		return dao.EditMedicine(mlist);
	}
	

	@Override
	public List<Object[]> GetDoctorList()throws Exception
	{
		return dao.GetDoctorList();
	}
	
	@Override
	public CHSSDoctorRates getCHSSDocRate(long docrateid) throws Exception
	{
		return dao.getCHSSDocRate(docrateid);
	}
	
	@Override
	public int EditDoctorMaster(CHSSDoctorRates docrate) throws Exception
	{	
		logger.info(new Date() +"Inside SERVICE EditDoctorMaster ");
		CHSSDoctorRates doctor = dao.getCHSSDocRate(docrate.getDocRateId());
		
//		doctor.setTreatTypeId(docrate.getTreatTypeId());
//		doctor.setDocQualification(docrate.getDocQualification());
//		doctor.setDocRating(docrate.getDocRating());				
		doctor.setConsultation_1(docrate.getConsultation_1());
		doctor.setConsultation_2(docrate.getConsultation_2());
		doctor.setModifiedBy(docrate.getModifiedBy());
		doctor.setModifiedDate(docrate.getModifiedDate());
		
		return dao.EditDoctorMaster(doctor);
	}
	
	@Override
	public Object[] getLabDetails()throws Exception
	{
		return dao.getLabDetails();
	}
	
	@Override
	public LabMaster GetLabDetailsToEdit(long labid)throws Exception
	{
		return dao.GetLabDetailsToEdit(labid);
	}
	
	@Override
	public List<Object[]> getLabsList()throws Exception
	{
		return dao.getLabsList();
	}
	
	@Override
	public long EditLabMaster(LabMaster labmater)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EditLabMaster ");
		LabMaster lab = dao.GetLabDetailsToEdit(labmater.getLabMasterId());
		
		lab.setLabCode(labmater.getLabCode());
		lab.setLabName(labmater.getLabName());
		lab.setLabAddress(labmater.getLabAddress());
		lab.setLabCity(labmater.getLabCity());
		lab.setLabEmail(labmater.getLabEmail());
		lab.setLabPin(labmater.getLabPin());
		lab.setLabUnitCode(labmater.getLabUnitCode());
		lab.setLabTelNo(labmater.getLabTelNo());
		lab.setLabFaxNo(labmater.getLabFaxNo());
		lab.setLabAuthority(labmater.getLabAuthority());
		lab.setLabAuthorityId(labmater.getLabAuthorityId());
		lab.setLabId(labmater.getLabId());
		lab.setLabRfpEmail(labmater.getLabRfpEmail());
		lab.setModifiedBy(labmater.getModifiedBy());
		lab.setModifiedDate(labmater.getModifiedDate());
		return dao.EditLabMaster(lab);
	}
	@Override
	public List<Object[]> GetOtherItemAmlountList(String id)throws Exception
	{
		return dao.GetOtherItemAmlountList(id);
	}
	
	@Override
	public long AddOtherItemAmt(CHSSOtherPermitAmt otheramt)throws Exception
	{
		return dao.AddOtherItemAmt(otheramt);
	}
	
	@Override
	public long updateOtherAmt(String chssOtheramtid, String admAmt, String UserId)throws Exception
	{
		return dao.updateOtherAmt(chssOtheramtid, admAmt, UserId);
	}
	
	@Override
	public int CheckduplicateItem(String treatmentname)throws Exception
	{
		return dao.CheckduplicateItem(treatmentname);
	}
	
	@Override
	public int CheckduplicateTest(String testname)throws Exception
	{
		return dao.CheckduplicateTest(testname);
	}
	
	@Override
	public long DeleteOtherAmt(String chssOtheramtid, String userid)throws Exception
	{
		return dao.DeleteOtherAmt(chssOtheramtid , userid);
	}
	
	@Override
	public List<Object[]> GetDesignation()throws Exception
	{
		return dao.GetDesignation();
	}
	@Override
	public long AddDesignation(EmployeeDesig desig)throws Exception
	{
		return dao.AddDesignation(desig);
	}
	@Override
	public EmployeeDesig GetDesignationToEdit(long desigid)throws Exception
	{
		return dao.GetDesignationToEdit(desigid);
	}
	
	@Override
	public long EditDesignation(EmployeeDesig desig)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EditDesignation ");
		EmployeeDesig designation = dao.GetDesignationToEdit(desig.getDesigId());
		designation.setDesigId(desig.getDesigId());
		designation.setDesigCode(desig.getDesigCode());
		designation.setDesigLimit(desig.getDesigLimit());
		designation.setDesignation(desig.getDesignation());
		return dao.EditDesignation(designation);
	}
	

	@Override
	public Object[] DesignationAddCheck(String desigcode,String designation) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE DesignationAddCheck ");
		Object[] returnobj=new Object[2];
		returnobj[0]=dao.DesignationCodeCheck(desigcode)[0].toString();
		returnobj[1]=dao.DesignationCheck(designation)[0].toString();
		return returnobj;
	}
	
	@Override
	public Object[] DesignationEditCheck(String desigcode,String designation,String desigid) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE DesignationEditCheck ");
		Object[] returnobj=new Object[2];
		returnobj[0]=dao.DesignationCodeEditCheck(desigcode,desigid)[0].toString();
		returnobj[1]=dao.DesignationEditCheck(designation,desigid)[0].toString();
		return returnobj;
	}
	
	
	@Override
	public DoctorList GetDoctor(long doctorId)throws Exception
	{
		return dao.GetDoctor(doctorId);
	}
	
	@Override
	public long DoctorsAdd(DoctorList doctor)throws Exception
	{
		return dao.DoctorsAdd( doctor);
	}
	
	@Override
	public long DoctorsEdit(DoctorList doc)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE DoctorsEdit ");
		DoctorList doctor = dao.GetDoctor(doc.getDoctorId());
		doctor.setDoctorName(doc.getDoctorName());
		doctor.setQualification(doc.getQualification());
		doctor.setCreatedBy(doc.getCreatedBy());
		doctor.setModifiedDate(doc.getModifiedDate());
		doctor.setAddress(doc.getAddress());
		doctor.setPhoneNo(doc.getPhoneNo());
		return dao.DoctorsEdit( doctor);
	}
	
	@Override
	public int GetMaxMedNo(String treatmenttype)throws Exception
	{
		return dao.GetMaxMedNo(treatmenttype);
	}
	@Override
	public int CheckduplicateTestCode(String testcode)throws Exception
	{
		return dao.CheckduplicateTestCode(testcode);
	}
	@Override
	public int DuplicateDocQualification(String treatment,String qualification)throws Exception
	{
		return dao.DuplicateDocQualification(treatment , qualification);
	}
	@Override
	public long updateOtherItemAmt(String chssOtheramtid, String admAmt, String UserId,String basicto)throws Exception
	{
		return dao.updateOtherItemAmt(chssOtheramtid, admAmt, UserId,basicto);
	}
	

	@Override
	public long CircularListAdd(CircularList circular ,CircularListDto filecirculardto)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CircularListAdd ");
		long value = dao.GetCircularMaxId();
	    if(!filecirculardto.getPath().isEmpty()) {
		String name =filecirculardto.getPath().getOriginalFilename();
		String filename= "Circular-"+(++value) +"."+FilenameUtils.getExtension(filecirculardto.getPath().getOriginalFilename());
		
		String path = emsfilespath+"EMS//CircularFiles";
				
		circular.setPath(path+"//"+filename);
		circular.setOriginalName(name);
		saveFile(path, filename, filecirculardto.getPath());
		}		
		return dao.CircularListAdd(circular);
	}
	
	   public static void saveFile(String CircularFilePath, String fileName, MultipartFile multipartFile) throws IOException 
	   {
		   logger.info(new Date() +"Inside SERVICE saveFile ");
	        Path uploadPath = Paths.get(CircularFilePath);
	          
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
	   public CircularList GetCircularToEdit(Long circularid)throws Exception
	   {
		   return dao.GetCircularToEdit(circularid);
	   }
	   
	   
		@Override
		public long CircularListEdit(CircularList circular ,CircularListDto filecirculardto)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE CircularListEdit ");				
			CircularList circularlist = dao.GetCircularToEdit(circular.getCircularId());
			if(circular.getPath()!=null) {
				String path =emsfilespath+"EMS//CircularFiles";
			  Path uploadPath = Paths.get(path);
			  Path filePath = uploadPath.resolve(circular.getPath());
              File f = filePath.toFile();
              if(f.exists()) {
            	  f.delete();
              }
			}
          	
    	    if(!filecirculardto.getPath().isEmpty()) {
    		String name =filecirculardto.getPath().getOriginalFilename();
    		String filename= "Circular-"+circularlist.getCircularId()+"."+FilenameUtils.getExtension(filecirculardto.getPath().getOriginalFilename());
    		String path =emsfilespath+"EMS//CircularFiles";
    		circularlist.setPath(path+"//"+filename);
    		circularlist.setOriginalName(name);
    			saveFile(path, filename, filecirculardto.getPath());
    		}	
    	    circularlist.setCircularDate(circular.getCircularDate());
    	    circularlist.setToDate(circular.getToDate());
    	    circularlist.setDescription(circular.getDescription());
    	    circularlist.setReferenceNo(circular.getReferenceNo());
    	    circularlist.setModifiedBy(circular.getModifiedBy());
    	    circularlist.setModifiedDate(circular.getModifiedDate());
            return dao.EditCircular(circularlist);
		}
	   
		
		@Override
		public List<Object[]> GetCircularList(String fromdate , String todate)throws Exception
		{
			logger.info(new Date() +"Inside SERVICE GetCircularList ");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
			LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
			LocalDate ToDate= LocalDate.parse(todate, formatter);
			return dao.GetCircularList(Fromdate , ToDate);
		}
		
		@Override
		public long EmpanelledHospitalAdd(CHSSEmpanelledHospital hospital)throws Exception
		{
			return dao.EmpanelledHospitalAdd( hospital);
		}
		
		@Override
		public long EmpanelledHospitalEdit(CHSSEmpanelledHospital hospital)throws Exception
		{ 
			logger.info(new Date() +"Inside SERVICE EmpanelledHospitalEdit ");
			CHSSEmpanelledHospital empanelled = dao.GetEmpanelled(hospital.getEmpanelledHospitalId());
			empanelled.setHospitalName(hospital.getHospitalName());
			empanelled.setHospitalAddress(hospital.getHospitalAddress());
			empanelled.setCreatedBy(hospital.getCreatedBy());
			empanelled.setModifiedDate(hospital.getModifiedDate());
			return dao.EmpanelledHospitalEdit( empanelled);
		}
		@Override
		public CHSSEmpanelledHospital GetEmpanelled(Long  empanelledid)throws Exception
		{
			return dao.GetEmpanelled(empanelledid);
		}
		
		@Override
		public Long AddMasterEditComments(MasterEdit masteredit , MasterEditDto masterdto )throws Exception
		{
			logger.info(new Date() +"Inside SERVICE AddMasterEditComments ");
			Timestamp instant= Timestamp.from(Instant.now());
			String timestampstr = instant.toString().replace(" ","").replace(":", "").replace("-", "").replace(".","");
			
			   if(!masterdto.getFilePath().isEmpty()) {
					String name =masterdto.getFilePath().getOriginalFilename();
					String filename= "MasterEditFile-"+timestampstr +"."+FilenameUtils.getExtension(masterdto.getFilePath().getOriginalFilename());
					String filepath=emsfilespath+"EMS//MastersEditFilePath";
							
					masteredit.setFilePath(filepath+File.separator+filename);
					masteredit.setOriginalName(name);
				    saveFile(filepath , filename, masterdto.getFilePath());
					
				}	
			return dao.AddMasterEditComments(masteredit);
		}
}
