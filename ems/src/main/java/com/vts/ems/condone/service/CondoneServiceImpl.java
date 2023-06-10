package com.vts.ems.condone.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.condone.dao.CondoneDaoImpl;
import com.vts.ems.condone.model.SEIPCondone;
import com.vts.ems.condone.model.SEIPCondoneAttach;
import com.vts.ems.condone.model.SEIPCondoneTransac;
import com.vts.ems.condone.model.SEIPCondoneType;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class CondoneServiceImpl {

	@Autowired
	CondoneDaoImpl dao;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	private static final Logger logger = LogManager.getLogger(CondoneServiceImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	public List<Object[]> EmpCondoneList(String EmpNo) throws Exception
	{
		return dao.EmpCondoneList(EmpNo);
	}
	
	public List<SEIPCondoneType> CondoneTypesList() throws Exception
	{
		return dao.CondoneTypesList();
	}
	public Object[] getEmployeeInfo(String Empno) throws Exception
	{
		return dao.getEmployeeInfo(Empno);
	}
	public LabMaster getLabInfo() throws Exception
	{
		return dao.getLabInfo();
	}
	public  long CondoneAddSubmit(SEIPCondone condone,MultipartFile[] condone_attach) throws Exception
	{
		
		condone.setCondonationNo("");
		
		long condoneId = dao.CondoneAddSubmit(condone);
		
		if(condone_attach!=null) {
			for(MultipartFile file :condone_attach) {			
	
			   if(file !=null && !file.isEmpty() && file.getSize()>0 ) {
					   
				   SEIPCondoneAttach attach = new SEIPCondoneAttach();
					   
				   String name =file.getOriginalFilename();
				   String filename= "Condone-"+UUID.randomUUID() +"."+StringUtils.getFilenameExtension(name);
				   String filepath=emsfilespath+"//CondonationAttachments";		
				   attach.setCondoneId(condoneId);
				   attach.setAttachPath("//CondonationAttachments");
				   attach.setFileName(filename);
				   attach.setOriginalFileName(name);
				   attach.setIsActive(1);
				   attach.setCreatedBy(condone.getCreatedBy());
				   attach.setCreatedDate(condone.getCreatedDate());
				   saveFile(filepath , filename,file);
				   dao.CondoneAttachAddSubmit(attach);
				}	
			}
		}
		
		return condoneId;
	}
	
	
	public  long CondoneEditSubmit(SEIPCondone condone) throws Exception
	{
		long condoneId = dao.CondoneEditSubmit(condone);
		return condoneId;
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
	
	public Object[] getCondoneData(String CondoneId)throws Exception
	{
		return dao.getCondoneData(CondoneId);
	}
	
	public SEIPCondone getSEIPCondone(String CondoneId) throws Exception
	{
		return dao.getSEIPCondone(CondoneId);
	}

	public List<SEIPCondoneAttach> getCondoneAttachList(String condoneId) throws Exception
	{
		return dao.getCondoneAttachList(Long.parseLong(condoneId));
	}
	
	public  long CondoneAttachSubmit(String condoneId,MultipartFile[] condone_attach) throws Exception
	{
		long count=0;
		
		for(MultipartFile file :condone_attach) {			
			
		   if(file !=null && !file.isEmpty() && file.getSize()>0 ) {
				   
			   SEIPCondoneAttach attach = new SEIPCondoneAttach();
				   
			   String name =file.getOriginalFilename();
			   String filename= "Condone-"+UUID.randomUUID() +"."+StringUtils.getFilenameExtension(name);
			   String filepath=emsfilespath+"//CondonationAttachments";		
			   attach.setCondoneId(Long.parseLong(condoneId));
			   attach.setAttachPath("//CondonationAttachments");
			   attach.setFileName(filename);
			   attach.setOriginalFileName(name);
			   attach.setIsActive(1);
			   saveFile(filepath , filename,file);
			   count = dao.CondoneAttachAddSubmit(attach);
			}	
		}
		
		return count;
	}
	
	public long CondoneAttachDelete(String CondoneAttachId,String userName,String modifiedDate) throws Exception
	{
		SEIPCondoneAttach attach =dao.getCondoneAttach(Long.parseLong(CondoneAttachId));
		attach.setIsActive(0);
		attach.setModifiedBy(userName);
		attach.setModifiedDate(modifiedDate);
		return dao.CondoneAttachEditSubmit(attach);
	}
	
	public void condonationForward(String CondoneId,String action,String remarks,String EmpNo) throws Exception{
		
		SEIPCondone condone = dao.getSEIPCondone(CondoneId);
		String formempno = condone.getEmpNo();
		String CEO = dao.GetCEOEmpNo();
		List<String> DGMs = dao.GetDGMEmpNos();
		String CondoneStatus = condone.getCondoneStatusCode();
		String CondoneNextStatus = condone.getCondoneNextStatus();
		
		List<String> statusWithEmp = List.of("INI", "SDG", "SCE");
		
		if(action.equalsIgnoreCase("A")) {
			
			// first time forwarding
			if(statusWithEmp.contains(CondoneStatus)) {
				
				if(CEO.equalsIgnoreCase(formempno) ) { 
					
					condone.setCondoneStatusCode("ACE");
					condone.setCondoneNextStatus("ACE");
					condone.setIsAccepted("Y");
					
				}else if(DGMs.contains(formempno)) 
				{
					condone.setCondoneStatusCode("FWD");
					condone.setCondoneNextStatus("ACE");
				}else {
					condone.setCondoneStatusCode("FWD");
					condone.setCondoneNextStatus("RDG");
				}
				
			}
		}
		else
		{
			condone.setCondoneNextStatus(CondoneNextStatus);
				
			if(CondoneNextStatus.equalsIgnoreCase("RDG")) 
			{
				condone.setCondoneStatusCode("FWD");
			}
			else if(CondoneNextStatus.equalsIgnoreCase("ACE")) 
			{	
				condone.setIsAccepted("Y");
			}
		}
		
		dao.CondoneEditSubmit(condone);
		
		SEIPCondoneTransac transac = SEIPCondoneTransac.builder()
										.CondoneId(condone.getCondoneId())
										.ActionBy(EmpNo)
										.ActionDate(sdtf.format(new Date()))
										.CondoneStatusCode(condone.getCondoneStatusCode())
										.Remark(remarks)
										.build();
										
		dao.CondoneTransacSubmit(transac);
		
	}
			
		
	
}
