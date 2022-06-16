package com.vts.ems.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.chss.controller.CHSSController;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicineList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.master.dto.CircularListDto;
import com.vts.ems.master.model.CHSSEmpanelledHospital;
import com.vts.ems.master.model.CircularList;
import com.vts.ems.master.model.DoctorList;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.master.service.MasterService;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.service.EMSMainService;
import com.vts.ems.utils.DateTimeFormatUtil;
@Controller
public class MasterController {
	private static final Logger logger = LogManager.getLogger(CHSSController.class);

	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	
	@Autowired
	MasterService service;
	                      
	@Autowired
	EMSMainService emsservice;
	
	@Autowired
	private PisService pisservice;
	
	@Value("${Image_uploadpath}")
	private String uploadpath;
	
	
	   @RequestMapping(value = "OtherItems.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	    public  String Otheritem(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)throws Exception
	    {
	    	String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside Otheritem.htm "+UserId);
			try {				
				List<Object[]> otheritem = service.OtherItems(); 
				req.setAttribute("itemlist", otheritem);
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
			return "masters/OtherItems";
	     }
	    
	    @RequestMapping(value = "TestSub.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	    public  String ChssTestMain(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)throws Exception
	    {
	    	String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside TestSub.htm "+UserId);
			try {				 
				List<Object[]> ChssTestMain = service.ChssTestSub(); 
				req.setAttribute("ChssTestMain", ChssTestMain);
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
			return "masters/CHSSTestSub";
	    }
	                                                                                                     
	    @RequestMapping(value="ChssTestSub.htm" , method=RequestMethod.POST)
	    public String ChssTestSubAddEdit(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)throws Exception
	    {
	    	String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside ChssTestSub.htm "+UserId);
			try {
				 String action= (String)req.getParameter("action");
				
				 List<Object[]> TestMain = service.ChssTestMain(); 
				 if("ADD".equalsIgnoreCase(action)){
					
					 req.setAttribute("TestMain", TestMain);						 
					 return "masters/CHSSTestSubADDEDIT";
					 
				 }else if ("EDIT".equalsIgnoreCase(action)){
						String subid = (String)req.getParameter("SubId");
						CHSSTestSub sub = service.testSub(subid);
						req.setAttribute("TestMain",TestMain);
						req.setAttribute("subdata",sub);
					return "masters/CHSSTestSubADDEDIT";
					
				 }else {
					 
					 if(req.getParameter("SubId")!=null){
						 //Test Edit code 
						 String testmain =(String)req.getParameter("Main");
					 	 String testName =(String)req.getParameter("Name");
					 	 String Rate     =(String)req.getParameter("Rate");
					 	 String subid    =(String)req.getParameter("SubId");
					 	 String testcode =(String)req.getParameter("TestCode");
					 	CHSSTestSub  sub = new CHSSTestSub();
					 	 	sub.setTestSubId(Long.parseLong(subid));
						 	sub.setTestRate(Integer.parseInt(Rate)); 
						 	sub.setTestName(WordUtils.capitalizeFully(testName.trim())); 
						 	sub.setTestMainId(Long.parseLong(testmain));				 	 
						 	sub.setModifiedDate(sdtf.format(new Date()));
						 	sub.setModifiedBy(UserId);
						 	sub.setTestCode(testcode);
						 	long result =service.EditTestSub(sub);
						 	
						 	String comments = (String)req.getParameter("comments");
					    	   MasterEdit masteredit  = new MasterEdit();
					    	   masteredit.setCreatedBy(UserId);
					    	   masteredit.setCreatedDate(sdtf.format(new Date()));
					    	   masteredit.setTableRowId(Long.parseLong(subid));
					    	   masteredit.setComments(comments);
					    	   masteredit.setTableName("chss_test_sub");
					    	   
					    	   service.AddMasterEditComments(masteredit);

						 	
						 	if (result != 0) {
								redir.addAttribute("result", "Test details Updated");
							} else {
								redir.addAttribute("resultfail", "Test details Failed to Update");
							}
						return "redirect:/TestSub.htm";
					 	 
					 }else {
						//Test Add code 
					 	String testmain =(String)req.getParameter("Main");
					 	String testName =(String)req.getParameter("Name");
					 	String Rate     =(String)req.getParameter("Rate");
					 	String testcode =(String)req.getParameter("TestCode");
					 	CHSSTestSub  sub = new CHSSTestSub();
					 	sub.setTestRate(Integer.parseInt(Rate)); 
					 	sub.setTestName(WordUtils.capitalizeFully(testName.trim())); 
					 	sub.setTestMainId(Long.parseLong(testmain));
					 	sub.setIsActive(1);						 	 
					 	sub.setCreatedDate(sdtf.format(new Date()));
					 	sub.setCreatedBy(UserId);
					 	sub.setTestCode(testcode);
					 	long result =  service.AddTestSub(sub);
					 	if (result != 0) {
							redir.addAttribute("result", "Test details saved");
						} else {
							redir.addAttribute("resultfail", "Test details Failed to save");
						}
					return "redirect:/TestSub.htm";
					 }
				 } 
				 
				
	       }catch(Exception e){
	    	   redir.addAttribute("resultfail", "Internal Error!");
	    	   e.printStackTrace();
	    	   return "redirect:/TestSub.htm";
	       }
			
	    }
	    
	    @RequestMapping(value = "OtherItemAddEdit.htm" ,method= {RequestMethod.POST,RequestMethod.GET})
	    public String OtherItemAddEdit(HttpSession ses ,HttpServletRequest req , RedirectAttributes redir)throws Exception
	    {
   	
	    	String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside OtherItemAddEdit.htm "+UserId);
			try {	
			if (req.getParameter("item")!=null) {
				
						String itemid   = (String)req.getParameter("item");	
						String name = "ItemName"+itemid;
						String itemname = (String)req.getParameter(name);
						CHSSOtherItems item = new CHSSOtherItems();
					
						item.setOtherItemId(Integer.parseInt(itemid));
						item.setModifiedDate(sdtf.format(new Date()));
						item.setModifiedBy(UserId);
						item.setOtherItemName(WordUtils.capitalizeFully(itemname.trim()));
						int result = service.EditItem(item);
						
						String comments = (String)req.getParameter("comments");
				    	   MasterEdit masteredit  = new MasterEdit();
				    	   masteredit.setCreatedBy(UserId);
				    	   masteredit.setCreatedDate(sdtf.format(new Date()));
				    	   masteredit.setTableRowId(Long.parseLong(itemid));
				    	   masteredit.setComments(comments);
				    	   masteredit.setTableName("chss_other_items");
				    	   
				    	   service.AddMasterEditComments(masteredit);
					 	if (result != 0) {
							redir.addAttribute("result", "Item Edited Successful");
						} else {
							redir.addAttribute("resultfail", "Item Edited UnSuccessful");
						}
						return "redirect:/OtherItems.htm";
				 	
				}else{
				
					String itemname = (String)req.getParameter("ItemName");
					
					CHSSOtherItems item = new CHSSOtherItems();
	
					item.setCreatedBy(UserId);
					item.setCreatedDate(sdtf.format(new Date()));
					item.setOtherItemName(WordUtils.capitalizeFully(itemname.trim()));
					item.setIsActive(1);
					int result = service.AddOtherItem(item);
				 	if (result != 0) {
						redir.addAttribute("result", "Item Added Successful");
					} else {
						redir.addAttribute("resultfail", "Item Added UnSuccessful");
					}
				 	return "redirect:/OtherItems.htm";
				}
				 
			}catch (Exception e) {
				redir.addAttribute("resultfail", "Internal Error!");
				e.printStackTrace();
				return "redirect:/OtherItems.htm";
			}
			
	    }
	    
	    
		@RequestMapping(method= {RequestMethod.POST,RequestMethod.GET},value ="MedicineList.htm")
		public String MedicineList(HttpServletRequest req , HttpSession ses )throws Exception
		{
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside MedicineList.htm "+UserId);
			try {
				String name  = (String)req.getParameter("tratementname");		
				if(name!=null) {
				
					List<Object[]>  list = service.getMedicineListByTreatment(name);
					List<Object[]> treatment = service.GetTreatmentType();
					req.setAttribute("treatment", treatment);
					req.setAttribute("MedicineList", list);
					req.setAttribute("treat", name);
				} else {
					
					List<Object[]>  list = service.getMedicineListByTreatment("1");
					List<Object[]> treatment = service.GetTreatmentType();
					req.setAttribute("treatment", treatment);
					req.setAttribute("MedicineList", list);
					req.setAttribute("treat", name);
				}
			
				return "masters/CHSSMedicineList";
			} catch (Exception e) {
				e.printStackTrace();
				return "masters/CHSSMedicineList";
			}
			
		}
		@RequestMapping(value="ChssMedicine.htm", method= {RequestMethod.POST,RequestMethod.GET})
		public String ADDEDITMedicine(HttpServletRequest req ,HttpSession ses ,RedirectAttributes redir)throws Exception
		{
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside ChssMedicine.htm "+UserId);
			try {
				
				String action = (String)req.getParameter("Action");
			
				if("ADD".equalsIgnoreCase(action)){
					String treatid = (String)req.getParameter("tratementname");
					
					List<Object[]> treatment = service.GetTreatmentType();
					req.setAttribute("treatment", treatment);
					return "masters/CHSSMedicineADDEDIT";
					
				}else if ("EDIT".equalsIgnoreCase(action)) {
					
					String medicineid = (String)req.getParameter("MedicineId");
					CHSSMedicineList  medicinelist=service.getCHSSMedicine(Long.parseLong(medicineid));
					List<Object[]> treatment = service.GetTreatmentType();
					req.setAttribute("treatment", treatment);
					req.setAttribute("medicinelist", medicinelist);
					return "masters/CHSSMedicineADDEDIT";
					
				}else if ("EDITMEDICINE".equalsIgnoreCase(action)) {
					
					String medicineId = (String)req.getParameter("medicineId");
					String tratementname = (String)req.getParameter("tratementname");
					String MedicineName  = (String)req.getParameter("MedicineName");
					String IsAdmissible  = (String)req.getParameter("IsAdmissible");
					
					CHSSMedicineList  medicinelist = new CHSSMedicineList();
					medicinelist.setMedicineId(Long.parseLong(medicineId));
					medicinelist.setMedicineName( WordUtils.capitalizeFully(MedicineName.trim()) );
					medicinelist.setTreatTypeId(Long.parseLong(tratementname));
					medicinelist.setIsAdmissible(IsAdmissible);
					long result =service.EditMedicine(medicinelist);
					
					
					String comments = (String)req.getParameter("comments");
					MasterEdit masteredit = new MasterEdit();
					masteredit.setTableRowId(Long.parseLong(medicineId));
					masteredit.setTableName("chss_medicines_list");
					masteredit.setCreatedBy(UserId);
					masteredit.setCreatedDate(sdtf.format(new Date()));
					masteredit.setComments(comments);
					service.AddMasterEditComments(masteredit);
					if (result != 0) {
		    			redir.addAttribute("result", "Medicine Edited Successfully");
					} else {
						redir.addAttribute("resultfail", "Medicine Edited UnSuccessful");
					}
					return "redirect:/MedicineList.htm";
					
				}else if ("ADDMEDICINE".equalsIgnoreCase(action)) {
					
					String tratementname = (String)req.getParameter("tratementname");
					String MedicineName  = (String)req.getParameter("MedicineName");
					CHSSMedicineList  medicinelist = new CHSSMedicineList();
					if(tratementname.equalsIgnoreCase("1")) {
						medicinelist.setIsAdmissible("N");
					}else {
						medicinelist.setIsAdmissible("Y");
					}
					int  MedNo = service.GetMaxMedNo(tratementname);
					
					medicinelist.setMedNo(Long.valueOf(++MedNo));
					medicinelist.setMedicineName(WordUtils.capitalizeFully(MedicineName.trim()));
					medicinelist.setTreatTypeId(Long.parseLong(tratementname));
					medicinelist.setCategoryId(0l);
					medicinelist.setIsActive(1);
					long result = service.AddMedicine(medicinelist);
					if (result != 0) {
		    			redir.addAttribute("result", "Medicine added successfully");
					} else {
						redir.addAttribute("resultfail", "Medicine added UnSuccessful");
					}
					return "redirect:/MedicineList.htm";
				}	
				return "redirect:/MedicineList.htm";
			}catch (Exception e){
				e.printStackTrace();
				redir.addAttribute("resultfail", "SOME PROBLE OCCURE!");
				return "redirect:/MedicineList.htm";
			}
		}
		
		@RequestMapping(value ="DuplicateMedicine.htm",method=RequestMethod.GET)
		public @ResponseBody String CheckDuplicateMedicine(HttpSession ses , HttpServletRequest req)throws Exception
		{
			int count =0;
			Gson json = new Gson();
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside CheckDuplicateMedicine.htm()"+UserId);
			try {
				String Medicinename = (String)req.getParameter("MedicineName");
				String treatid = (String)req.getParameter("Treatmentid");
				count = service.Checkduplicate( Medicinename ,treatid);
				
				 return json.toJson(count);
			}catch (Exception e){
				e.printStackTrace();
				 return json.toJson(count);
			}
		}
		
		@RequestMapping(value ="DuplicateOtherItem.htm",method=RequestMethod.GET)
		public @ResponseBody String CheckDuplicateOtherItems(HttpSession ses , HttpServletRequest req)throws Exception
		{
			int count =0;
			Gson json = new Gson();
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DuplicateOtherItem.htm"+UserId);
			try {
				String treatmentName = (String)req.getParameter("treatmentName");
				
				count = service.CheckduplicateItem( treatmentName );
				
				 return json.toJson(count);
			}catch (Exception e){
				e.printStackTrace();
				 return json.toJson(count);
			}
		}
		
		
		
		@RequestMapping(value = "DoctorsMaster.htm" , method= {RequestMethod.GET,RequestMethod.POST})
		public String DoctorsMasters(HttpSession ses , HttpServletRequest req ,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				String action = (String)req.getParameter("Action");
				
			
			if("EDITDOCRATE".equalsIgnoreCase(action)){
					
					String Rateid = (String)req.getParameter("DocRateid");
					String con1= "Consultation1"+Rateid;
					String con2= "Consultation2"+Rateid;
					String Consultation1 = (String)req.getParameter(con1);
					String Consultation2 = (String)req.getParameter(con2);
					
					CHSSDoctorRates  DocRate = new CHSSDoctorRates();
					
					DocRate.setDocRateId(Integer.parseInt(Rateid));				
					DocRate.setConsultation_1(Integer.parseInt(Consultation1));
					DocRate.setConsultation_2(Integer.parseInt(Consultation2));
					DocRate.setModifiedBy(UserId);
					DocRate.setModifiedDate(sdtf.format(new Date()));
					int result = service.EditDoctorMaster(DocRate);
					String comments = (String)req.getParameter("comments");
			    	   MasterEdit masteredit  = new MasterEdit();
			    	   masteredit.setCreatedBy(UserId);
			    	   masteredit.setCreatedDate(sdtf.format(new Date()));
			    	   masteredit.setTableRowId(Long.parseLong(Rateid));
			    	   masteredit.setComments(comments);
			    	   masteredit.setTableName("chss_doctor_rates");
			    	   
			    	   service.AddMasterEditComments(masteredit);
					
					if (result != 0) {
		    			redir.addAttribute("result", "Doctor Details Updated");
					} else {
						redir.addAttribute("resultfail", "Doctor Details Failed to Update");
					}
					return "redirect:/DoctorsMaster.htm";
				}else {
					List<Object[]> doctorlist = service.GetDoctorList();
					req.setAttribute("doctorlist", doctorlist);
					return "masters/CHSSDoctorsList";
				}		
			} catch (Exception e) {
				req.setAttribute("resultfail", "Internal Error!");
				e.printStackTrace();
				return "masters/CHSSDoctorsList";
			}
			
		}                                
		
		@RequestMapping(value = "UnitMaster.htm",method= {RequestMethod.POST,RequestMethod.GET})
		public String LabMaster(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside UnitMaster.htm "+UserId);
			try {
				String action =(String)req.getParameter("Action");
			
				if("EDIT".equalsIgnoreCase(action)) {
					String labmasterId = (String)req.getParameter("labmasterId");
					LabMaster lab = service.GetLabDetailsToEdit(Long.parseLong(labmasterId));
					List<Object[]> labslist=service.getLabsList();
					req.setAttribute("emplist", pisservice.GetAllEmployee());
					req.setAttribute("labslist", labslist);
					req.setAttribute("labdetails", lab);
					       return "masters/LabMasterEdit";
				}else if("EDITLAB".equalsIgnoreCase(action)){
					String labcode = (String)req.getParameter("LabCode");
					String labname = (String)req.getParameter("LabName");
					String labaddress = (String)req.getParameter("LabAddress");
					String labcity = (String)req.getParameter("LabCity");
					String labemail = (String)req.getParameter("LabEmail");
					String labpin = (String)req.getParameter("LabPin");
					String labunitcode = (String)req.getParameter("LabUnitCode");
					String labtelph = (String)req.getParameter("LabTelephone");
					String labfxno = (String)req.getParameter("LabFaxNo");
					String labauthority = (String)req.getParameter("LabAuthority");
					String labauthorityname = (String)req.getParameter("LabAuthorityName");
					String Lab  = (String)req.getParameter("labid");
					String LabRfp  = (String)req.getParameter("LabRFPEmail");
					String LabMasterId = (String)req.getParameter("LabMasterId");
					LabMaster lab = new LabMaster ();
					
					lab.setLabCode(labcode);
					lab.setLabName(labname);
					lab.setLabAddress(labaddress);
					lab.setLabCity(labcity);
					lab.setLabEmail(labemail);
					lab.setLabPin(labpin);
					lab.setLabUnitCode(labunitcode);
					lab.setLabTelNo(labtelph);
					lab.setLabFaxNo(labfxno);
					lab.setLabAuthority(labauthority);
					lab.setLabAuthorityId(Integer.parseInt(labauthorityname));
					lab.setLabId(Integer.parseInt(Lab));
					lab.setLabRfpEmail(LabRfp);
					lab.setLabMasterId(Long.parseLong(LabMasterId));
					lab.setModifiedBy(UserId);
					lab.setModifiedDate(sdtf.format(new Date()));
					long result = service.EditLabMaster(lab);					
					
					String comments = (String)req.getParameter("comments");
			    	   MasterEdit masteredit  = new MasterEdit();
			    	   masteredit.setCreatedBy(UserId);
			    	   masteredit.setCreatedDate(sdtf.format(new Date()));
			    	   masteredit.setTableRowId(Long.parseLong(LabMasterId));
			    	   masteredit.setComments(comments);
			    	   masteredit.setTableName("lab_master");
			    	   
			    	   service.AddMasterEditComments(masteredit);
					if (result != 0) {
		    			redir.addAttribute("result", "Unit details updated successfully");
					}else {
						redir.addAttribute("resultfail", "Unit details updated Unsuccessfull");
					}
					return "redirect:/UnitMaster.htm";
				}else {                                      
					Object[] labdetails = service.getLabDetails();
					String labmasterId =""+labdetails[0];
					LabMaster lab = service.GetLabDetailsToEdit(Long.parseLong(labmasterId));
					List<Object[]> labslist=service.getLabsList();
					req.setAttribute("emplist", pisservice.GetAllEmployee());
					req.setAttribute("labslist", labslist);
					req.setAttribute("labdetails", lab);
									
					   return "masters/LabMasterEdit";
			           
				}
			} catch (Exception e) {
				req.setAttribute("resultfail", "Internal Error!");
				e.printStackTrace();
				return "masters/LabMasterEdit";
			}
			
		}
		
		@RequestMapping(value = "OtherItemAmount.htm" , method = {RequestMethod.POST,RequestMethod.GET})
		public String OtherItem( Model model, HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside OtherItemAmount.htm "+UserId);
			try {
				String tratementid =(String)req.getParameter("tratementid");
				
				  if(tratementid==null) {
					   Map md=model.asMap();
					   tratementid=(String)md.get("tratementid");	
				   }
				if(tratementid!=null) {
					List<Object[]> otheritem = service.OtherItems();			
					req.setAttribute("itemlist", otheritem);
					List<Object[]> list = service.GetOtherItemAmlountList(tratementid);
				
					req.setAttribute("tratementid", tratementid);
					req.setAttribute("list", list);
				}else {
				List<Object[]> otheritem = service.OtherItems();
				List<Object[]> list = service.GetOtherItemAmlountList("1");
				req.setAttribute("list", list);
				req.setAttribute("tratementid", "1");
				req.setAttribute("itemlist", otheritem);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "masters/OtherItemAmount";
		}
		
		
		@RequestMapping(value = "AddOtherItemAmount.htm" , method = {RequestMethod.GET,RequestMethod.POST})
		public String AddOtherItemAmount(HttpSession ses  , HttpServletRequest req ,RedirectAttributes redir)throws Exception
		{

			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside AddOtherItemAmount.htm "+UserId);
			try {
				String action = (String)req.getParameter("Action");
				if("ADDAMT".equalsIgnoreCase(action)){
					String basicfrom = (String)req.getParameter("basicfrom");
					String basicto   = (String)req.getParameter("basicto");
					String adsamt    = (String)req.getParameter("admAmt");
					String treatid   = (String)req.getParameter("treateid");

					CHSSOtherPermitAmt other = new CHSSOtherPermitAmt();

					other.setOtherItemId(Integer.parseInt(treatid));
					other.setBasicFrom(Long.parseLong(basicfrom));
					other.setBasicTo(Long.parseLong(basicto));
					other.setItemPermitAmt(Integer.parseInt(adsamt));
					other.setIsActive(1);
					other.setCreatedBy(UserId);
					other.setCreatedDate(sdtf.format(new Date()));
					long result = service.AddOtherItemAmt(other);
					if (result != 0) {
						 redir.addAttribute("result", "Permit Amount Added Successfull");
					} else {
						 redir.addAttribute("resultfail", "Permit Amount Added Unsuccessfull");
					}
					 redir.addFlashAttribute("tratementid", treatid);
					List<Object[]> otheritem = service.OtherItems();			
					req.setAttribute("itemlist", otheritem);
					List<Object[]> list = service.GetOtherItemAmlountList(treatid);
					req.setAttribute("tratementid", treatid);
					req.setAttribute("list", list);
				}
				
				return "redirect:/OtherItemAmount.htm";
				
			} catch (Exception e) {
				 redir.addAttribute("resultfail", "Internal Error!");
				e.printStackTrace();
				return "redirect:/OtherItemAmount.htm";
			}
			
		}
		@RequestMapping(value = "EDITOtherAmt.htm" , method = RequestMethod.POST)
		public String EDITOtherAmt(HttpSession ses ,HttpServletRequest req,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside EDITOtherAmt.htm "+UserId);
			try {
				String chssOtheramtid = (String)req.getParameter("chssOtheramtid");
				if(chssOtheramtid!=null) {
					String adm="admAmt1"+chssOtheramtid;
					String basicto = "basicto1"+chssOtheramtid;
					String admAmt1 = (String)req.getParameter(adm);
					String treatid = (String)req.getParameter("treateid");
					String basicto2 = (String)req.getParameter(basicto);
				
					long result = 0l;
					if(basicto2!=null) {
						 result = service.updateOtherItemAmt(chssOtheramtid ,admAmt1, UserId , basicto2);
					}else {
						 result = service.updateOtherAmt(chssOtheramtid ,admAmt1, UserId);
					}
					
					if (result != 0) {
						 redir.addAttribute("result", "Permit Amount Edited Successful");
					} else {
						 redir.addAttribute("resultfail", "Permit Amount Edited UnSuccessful");
					}
					 redir.addFlashAttribute("tratementid", treatid);
					 return "redirect:/OtherItemAmount.htm";
				}else {
					String chssother = (String) req.getParameter("otheritemid");
					String treatid = (String)req.getParameter("treateid");
					long result = 0l;
				
					result = service.DeleteOtherAmt(chssother ,UserId);
					
					
					if (result != 0) {
						 redir.addAttribute("result", "Permit Amount Deleted Successfully");
					} else {
						 redir.addAttribute("resultfail", "Permit Amount Failed to Delete");
					}
					 redir.addFlashAttribute("tratementid", treatid);
					 return "redirect:/OtherItemAmount.htm";
				}
			
			} catch (Exception e) {
				 redir.addAttribute("resultfail", "Internal Error!");
					e.printStackTrace();
					return "redirect:/OtherItemAmount.htm";
			}
			
		}
		

		@RequestMapping(value ="DuplicateTest.htm",method=RequestMethod.GET)
		public @ResponseBody String CheckDuplicateTest(HttpSession ses , HttpServletRequest req)throws Exception
		{
			int count =0;
			Gson json = new Gson();
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DuplicateTest.htm"+UserId);
			try {
				String testname = (String)req.getParameter("testName");
				
				count = service.CheckduplicateTest( testname );
				
				 return json.toJson(count);
			}catch (Exception e){
				e.printStackTrace();
				 return json.toJson(count);
			}
		}
		
		@RequestMapping(value = "Designation.htm" , method = {RequestMethod.POST,RequestMethod.GET})
		public String GetDesignation(HttpServletRequest req, HttpSession ses )throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");			
			logger.info(new Date() +"Inside Designation.htm "+UserId);
			
			try {			
				String action = (String)req.getParameter("action");
				if("ADD".equalsIgnoreCase(action)) {
					
					  return "masters/DesignationAddEdit";
				}else if ("EDIT".equalsIgnoreCase(action)) {
					String desigid = (String)req.getParameter("desigid");
					EmployeeDesig desig =service.GetDesignationToEdit(Long.parseLong(desigid));
					req.setAttribute("desig", desig);
					  return "masters/DesignationAddEdit";
				}else {
				  List<Object[]>  list = service.GetDesignation();
				  req.setAttribute("designation", list);				
			    return "masters/Designation";
				}
			}catch (Exception e){
				e.printStackTrace();
				return "static/Error";
			}
		}
		
		@RequestMapping(value = "DesignationAddEdit.htm" , method =RequestMethod.POST )
		public String DesgnationAddEdit (HttpServletRequest req, HttpSession ses , RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");			
			logger.info(new Date() +" Inside DesignationAddEdit.htm "+UserId);
			
			try {			
				String designationid = (String)req.getParameter("deisignationid");
				if(designationid!=null) {
					
					String code  = (String)req.getParameter("Designationcode");
					String name  = (String)req.getParameter("DesignationName");
					String limit = (String)req.getParameter("Designationlimit");
					EmployeeDesig desig = new EmployeeDesig();
					desig.setDesigId(Long.parseLong(designationid));
					desig.setDesigCode(code.toUpperCase());
					desig.setDesignation(WordUtils.capitalizeFully(name.trim()));
					desig.setDesigLimit(Long.parseLong(limit.trim()));
					
					String comments = (String)req.getParameter("comments");
			    	   MasterEdit masteredit  = new MasterEdit();
			    	   masteredit.setCreatedBy(UserId);
			    	   masteredit.setCreatedDate(sdtf.format(new Date()));
			    	   masteredit.setTableRowId(Long.parseLong(designationid));
			    	   masteredit.setComments(comments);
			    	   masteredit.setTableName("employee_desig");
			    	   
			    	   service.AddMasterEditComments(masteredit);
					long result = service.EditDesignation(desig);
					if (result != 0) {
						 redir.addAttribute("result", "Designation Updated Successfully");
					} else {
						 redir.addAttribute("resultfail", "Designation Updated Unsuccessfull");
					}
					return "redirect:/Designation.htm";
					
				}else{
					String code  = (String)req.getParameter("Designationcode");
					String name  = (String)req.getParameter("DesignationName");
					String limit = (String)req.getParameter("Designationlimit");
					
					EmployeeDesig desig = new EmployeeDesig();
					desig.setDesigCode(code.toUpperCase());
					desig.setDesignation(WordUtils.capitalizeFully(name.trim()));
					desig.setDesigLimit(Long.parseLong(limit.trim()));
					
					long result = service.AddDesignation(desig);
					if (result != 0) {
						 redir.addAttribute("result", "Designation Added Successfully");
					} else {
						 redir.addAttribute("resultfail", "Designation Added Unsuccessfull");
					}
					return "redirect:/Designation.htm";
				}				
				}catch (Exception e){
					e.printStackTrace();
					redir.addAttribute("resultfail", "Internal Error!");
					return "redirect:/Designation.htm";
				}
		}
		
	    @RequestMapping(value = "DesignationAddCheck.htm", method = RequestMethod.GET)
			  public @ResponseBody String DesignationAddCheck(HttpSession ses, HttpServletRequest req) throws Exception 
			  {
				String UserId=(String)ses.getAttribute("Username");
				Object[] DisDesc = null;
				logger.info(new Date() +"Inside DesignationAddCheck.htm "+UserId);
				try
				{	  
					String desigcode=req.getParameter("dcode");
					String designation=req.getParameter("dname");
					DisDesc =service.DesignationAddCheck(desigcode,designation);
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +"Inside DesignationAddCheck.htm "+UserId,e);
				}
				  Gson json = new Gson();
				  return json.toJson(DisDesc);           
				  
			}
		    
		    @RequestMapping(value = "DesignationEditCheck.htm", method = RequestMethod.GET)
			  public @ResponseBody String DesignationEditCheck(HttpSession ses, HttpServletRequest req) throws Exception 
			  {
				String UserId=(String)ses.getAttribute("Username");
				Object[] DisDesc = null;
				logger.info(new Date() +"Inside DesignationEditCheck.htm "+UserId);
				try
				{	  
					String desigcode=req.getParameter("dcode");
					String designation=req.getParameter("dname");
					String desigid=req.getParameter("desigid");
					DisDesc =service.DesignationEditCheck(desigcode,designation,desigid);
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +"Inside DesignationEditCheck.htm "+UserId,e);
				}
				  Gson json = new Gson();
				  return json.toJson(DisDesc); 
				  
			}
		    
		    
			@RequestMapping(value ="DuplicateTestCode.htm",method=RequestMethod.GET)
			public @ResponseBody String CheckDuplicateTestCode(HttpSession ses , HttpServletRequest req)throws Exception
			{
				int count =0;
				Gson json = new Gson();
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside DuplicateTestCode.htm"+UserId);
				try {
					String testCode = (String)req.getParameter("TestCode");
					
					count = service.CheckduplicateTestCode( testCode );
					
					 return json.toJson(count);
				}catch (Exception e){
					e.printStackTrace();
					 return json.toJson(count);
				}
			}
			

			@RequestMapping(value="DoctorList.htm" , method = {RequestMethod.POST,RequestMethod.GET})
			public String DoctorsList(HttpServletRequest req, HttpSession ses, HttpServletResponse res)throws Exception
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside DoctorList.htm "+UserId);
				List<Object[]> doctorlist = new ArrayList<Object[]>();
			   	 try {
					String action = (String)req.getParameter("action");
					if("ADD".equalsIgnoreCase(action)) {
						
						return "masters/DoctorAddEdit";
					}else if("EDIT".equalsIgnoreCase(action)) {
						String doctorid = (String)req.getParameter("doctorId");
						DoctorList list = service.GetDoctor(Long.parseLong(doctorid));
						req.setAttribute("doctor", list);
						return "masters/DoctorAddEdit";
					}else {
						doctorlist=emsservice.GetDoctorList();
						req.setAttribute("doctorlist", doctorlist);
						return "masters/DoctorList";
					}		   		 
				} catch (Exception e) {
					e.printStackTrace();
					return "masters/DoctorList";
				}
			}
			
			
			@RequestMapping(value="DoctorAddEdit.htm" , method = RequestMethod.POST)
			public String DoctorsAddEdit(HttpServletRequest req, HttpSession ses, HttpServletResponse res , RedirectAttributes redir)throws Exception
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside DoctorsAddEdit.htm "+UserId);
				try {
					String action = (String)req.getParameter("action");
					
					if("ADDDOCTOR".equalsIgnoreCase(action)) {
					String name = (String)req.getParameter("DoctorName");
					String qualification = (String)req.getParameter("Qualification");
					String address = (String)req.getParameter("address");
					String phoneno = (String)req.getParameter("phoneno");
					DoctorList doctor = new DoctorList();
					doctor.setDoctorName(WordUtils.capitalizeFully(name.trim()));
					doctor.setQualification(qualification.toUpperCase());
					doctor.setAddress(address);
					doctor.setPhoneNo(phoneno.trim());
					doctor.setCreatedBy(UserId);
					doctor.setCreatedDate(sdtf.format(new Date()));
					
					long result = service.DoctorsAdd(doctor);
					if (result != 0) {
						 redir.addAttribute("result", "Doctor Added Successfully");
					} else {
						 redir.addAttribute("resultfail", "Doctor Added Unsuccessfull");
					}
					}else{
						String name = (String)req.getParameter("DoctorName");
						String qualification = (String)req.getParameter("Qualification");
						String doctorId = (String)req.getParameter("doctorId");
						String address = (String)req.getParameter("address");
						String phoneno = (String)req.getParameter("phoneno");
						DoctorList doctor = new DoctorList();
						doctor.setDoctorId(Long.parseLong(doctorId));
						doctor.setDoctorName(WordUtils.capitalizeFully(name.trim()));
						doctor.setQualification(qualification.toUpperCase());
						doctor.setAddress(address);
						doctor.setPhoneNo(phoneno.trim());
						doctor.setModifiedBy(UserId);
						doctor.setModifiedDate(sdtf.format(new Date()));
						long result = service.DoctorsEdit(doctor);
						
					     String comments = (String)req.getParameter("comments");
				    	   MasterEdit masteredit  = new MasterEdit();
				    	   masteredit.setCreatedBy(UserId);
				    	   masteredit.setCreatedDate(sdtf.format(new Date()));
				    	   masteredit.setTableRowId(Long.parseLong(doctorId));
				    	   masteredit.setComments(comments);
				    	   masteredit.setTableName("chss_doctor_list");
				    	   
				    	   service.AddMasterEditComments(masteredit);
						
						
						
						if (result != 0) {
							 redir.addAttribute("result", "Doctor Updated Successfully");
						} else {
							 redir.addAttribute("resultfail", "Doctor Updated Unsuccessfull");
						}
						
					}
					 return "redirect:/DoctorList.htm";
				} catch (Exception e) {
					e.printStackTrace();
					 redir.addAttribute("resultfail", "Internal Error!");
					 return "redirect:/DoctorList.htm";
				}
				
			}	
			
			@RequestMapping(value="CircularLists.htm", method = { RequestMethod.POST ,RequestMethod.GET })
			public String circularList(HttpSession ses, HttpServletRequest req )throws Exception
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside CircularLists.htm "+UserId);
				List<Object[]> circulatlist = new ArrayList<Object[]>();
			   	 try {
			   		 String action = (String)req.getParameter("action");
			   		 if("ADD".equalsIgnoreCase(action)){
			   			 
			   			 return "masters/CircularAddEdit";
			   			 
			   		 }else if ("EDIT".equalsIgnoreCase(action)){
			   			 
			   			String circularid = (String)req.getParameter("circulatId");
						CircularList circular = service.GetCircularToEdit(Long.parseLong(circularid));
						req.setAttribute("circular", circular);
			   			return "masters/CircularAddEdit";
			   			
			   		 }else{
			   			 
			   			 String fromdate = (String)req.getParameter("fromdate");
			   			 String todate = (String)req.getParameter("todate");
			   			 
			   			 if(fromdate==null && todate == null) {
			   				 fromdate = DateTimeFormatUtil.getFinancialYearStartDateRegularFormat();
			   				 todate  = DateTimeFormatUtil.SqlToRegularDate( ""+LocalDate.now());
			   			 }
			   				
			   			 circulatlist = service.GetCircularList(fromdate , todate );
				   		 req.setAttribute("circulatlist", circulatlist);
				   		 req.setAttribute("fromdate", fromdate);	
						 req.setAttribute("todate",todate);
				   		return "masters/CircularList";
			   		 }
			   		                                                      
				} catch (Exception e) {
					e.printStackTrace();
					return "masters/CircularList";
				}
				
			}
			
			@RequestMapping(value ="CircularADDEDIT.htm" , method = RequestMethod.POST)
			public String CirculatAddEdit(HttpServletRequest req,HttpSession ses, @RequestPart("selectedFile") MultipartFile selectedFile, RedirectAttributes redir) throws Exception
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside CircularADDEDIT.htm "+UserId);
				try {
					String action = (String)req.getParameter("action");
					
					if("CircularAdd".equalsIgnoreCase(action)) {
						
						String circulardate   =(String)req.getParameter("circulardate");
						String description = (String)req.getParameter("description");
						String todate = (String)req.getParameter("todate");
						CircularList circular = new CircularList();
						
						circular.setCircularDate(DateTimeFormatUtil.dateConversionSql(circulardate).toString());
						circular.setDescription(description.trim());
						circular.setToDate(DateTimeFormatUtil.dateConversionSql(todate).toString());
						circular.setCreatedBy(UserId);
						circular.setCreatedDate(sdtf.format(new Date()));
						CircularListDto filecircular = new CircularListDto();
					
						filecircular.setPath(selectedFile);
						
						long result = service.CircularListAdd(circular , filecircular);
						if (result != 0) {
							 redir.addAttribute("result", "Circular Added Successfully");
						} else {
							 redir.addAttribute("resultfail", "Circular Added Unsuccessfull");
						}
					}else {
					
						String circulardate   = (String)req.getParameter("circulardate");
						String todate   = (String)req.getParameter("todate");
						String description = (String)req.getParameter("description");
						String circularid = (String)req.getParameter("circular");
						CircularList circular = new CircularList();
					
						circular.setCircularDate(DateTimeFormatUtil.dateConversionSql(circulardate).toString());
						circular.setToDate(DateTimeFormatUtil.dateConversionSql(todate).toString());
						circular.setDescription(description.trim());
						circular.setCircularId(Long.parseLong(circularid));
						circular.setModifiedBy(UserId);
						circular.setModifiedDate(sdtf.format(new Date()));
						CircularListDto filecircular = new CircularListDto();
					
						filecircular.setPath(selectedFile);
						long result = service.CircularListEdit(circular , filecircular);
						
						String comments = (String)req.getParameter("comments");
				    	   MasterEdit masteredit  = new MasterEdit();
				    	   masteredit.setCreatedBy(UserId);
				    	   masteredit.setCreatedDate(sdtf.format(new Date()));
				    	   masteredit.setTableRowId(Long.parseLong(circularid));
				    	   masteredit.setComments(comments);
				    	   masteredit.setTableName("chss_circular_list");
				    	   
				    	   service.AddMasterEditComments(masteredit);
						
						
						if (result != 0) {
							 redir.addAttribute("result", "Circular Updated Successfully");
						} else {
							 redir.addAttribute("resultfail", "Circular Updated Unsuccessfull");
						}
						
					}
					return "redirect:/CircularLists.htm";
				}catch (Exception e){
					e.printStackTrace();
					redir.addAttribute("resultfail", "Internal Error!");
					return "redirect:/CircularLists.htm";
				}
				
			}
			
			@RequestMapping(value = "download-CircularFile-attachment",method = {RequestMethod.GET,RequestMethod.POST})
		    public void downloadCircularAttachment(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception 
			{				
				try {
					
					String path = (String)req.getParameter("path");
					String arr[] = path.split("//");
					res.setContentType("Application/octet-stream");	
					
					File my_file = new File(arr[0]);
					 res.setHeader("Content-disposition","attachment; filename="+arr[1]);
				      OutputStream out = res.getOutputStream();
				        FileInputStream in = new FileInputStream(my_file);
				        byte[] buffer = new byte[4096];
				        int length;
				        while ((length = in.read(buffer)) > 0){
				           out.write(buffer, 0, length);
				        }
				        in.close();
				        out.flush();
				}catch(Exception e) {
					e.printStackTrace();
				}
		    }
			
			@RequestMapping(value="EmpanneledHospitalList.htm", method = { RequestMethod.POST ,RequestMethod.GET })
			public String EmpanelledHospitalList(HttpServletRequest req, HttpSession ses, HttpServletResponse res , RedirectAttributes redir)throws Exception
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside EmpanneledHospital.htm "+UserId);
				List<Object[]> empanelledhospital = new ArrayList<Object[]>();
				try {
					String action = (String)req.getParameter("action");
					if("ADD".equalsIgnoreCase(action)) {
						return "masters/EmpanelledHospitalAddEdit";
					
					}else if("EDIT".equalsIgnoreCase(action)){
						String empanelledid  = (String)req.getParameter("empanelledId");
						CHSSEmpanelledHospital empanelled = service.GetEmpanelled(Long.parseLong(empanelledid));
						req.setAttribute("empanelled", empanelled);
						return "masters/EmpanelledHospitalAddEdit";
					
					}else{
						empanelledhospital=emsservice.GetEmpanelledHostpitalList();
						req.setAttribute("empanelledhospital", empanelledhospital);
						return "masters/EmpanelledHospitalList";
					}	
				} catch (Exception e) {
					e.printStackTrace();
					redir.addAttribute("resultfail", "Internal Error!");
					return "masters/EmpanelledHospitalList";
				}
				
			}
			
			@RequestMapping(value="EmpanelledHospitalAddEdit.htm" , method = RequestMethod.POST)
			public String EmpanelledHospitalAddEdit(HttpServletRequest req, HttpSession ses, HttpServletResponse res , RedirectAttributes redir)throws Exception
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside EmpanelledHospitalAddEdit.htm "+UserId);
				try {
					String action = (String)req.getParameter("action");
					
					if("ADDHOSPITAL".equalsIgnoreCase(action)) {
					String name = (String)req.getParameter("HospitalName");
					String address = (String)req.getParameter("HospitalAddress");
					CHSSEmpanelledHospital hospital = new CHSSEmpanelledHospital();
					hospital.setHospitalAddress(address);
					hospital.setHospitalName(name.trim());
					hospital.setCreatedBy(UserId);
					hospital.setCreatedDate(sdtf.format(new Date()));
					hospital.setIsActive("1");
					long result = service.EmpanelledHospitalAdd(hospital);
					if (result != 0) {
						 redir.addAttribute("result", "Empanelled Hospital Added Successfully");
					} else {
						 redir.addAttribute("resultfail", "Empanelled Hospital Added Unsuccessfull");
					}
					}else{
						String name = (String)req.getParameter("HospitalName");		
						String address = (String)req.getParameter("HospitalAddress");
						String empanelledId = (String)req.getParameter("empanelledId");
						CHSSEmpanelledHospital hospital = new CHSSEmpanelledHospital();
						hospital.setHospitalAddress(address);
						hospital.setEmpanelledHospitalId(Long.parseLong(empanelledId));
						hospital.setHospitalName(name);
						hospital.setModifiedBy(UserId);
						hospital.setModifiedDate(sdtf.format(new Date()));
						long result =service.EmpanelledHospitalEdit(hospital);
						
						String comments = (String)req.getParameter("comments");
				    	   MasterEdit masteredit  = new MasterEdit();
				    	   masteredit.setCreatedBy(UserId);
				    	   masteredit.setCreatedDate(sdtf.format(new Date()));
				    	   masteredit.setTableRowId(Long.parseLong(empanelledId));
				    	   masteredit.setComments(comments);
				    	   masteredit.setTableName("chss_empanelledhospital");
				    	   
				    	   service.AddMasterEditComments(masteredit);
						
						if (result != 0) {
							 redir.addAttribute("result", "Empanelled Hospital Updated Successfully");
						} else {
							 redir.addAttribute("resultfail", "Empanelled Hospital Updated Unsuccessfull");
						}
						
					}
					 return "redirect:/EmpanneledHospitalList.htm";
				} catch (Exception e) {
					e.printStackTrace();
					 redir.addAttribute("resultfail", "Internal Error!");
					 return "redirect:/EmpanneledHospitalList.htm";
				}
				                                                                     
			}
}
