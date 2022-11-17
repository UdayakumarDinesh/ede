package com.vts.ems.circularorder.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.circularorder.dao.FormNoticeDao;
import com.vts.ems.circularorder.dto.FormUploadDto;
import com.vts.ems.circularorder.model.EMSForms;
import com.vts.ems.utils.DateTimeFormatUtil;



@Service
public class FormNoticeServiceImpl implements FormNoticeService 
{
	private static final Logger logger=LogManager.getLogger(FormNoticeServiceImpl.class);
	
	@Autowired
	FormNoticeDao dao;
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Value("${EMSFilesPath}")
    private String FilePath;

	@Override
	public List<Object[]> getEmsFormsList(String DepTypeId) throws Exception
	{
		return dao.getEmsFormsList(DepTypeId);
	}
	
	@Override
	public List<Object[]> GetEmsDepTypes() throws Exception 
	{
		return dao.GetEmsDepTypes();
	}
	
	@Override
	public long EmsFormAdd(FormUploadDto formDto) throws Exception 
	{
		MultipartFile FormFile = formDto.getFormFile();
		long maxFormId = dao.MaxOfEmsFormId()+1;
		String storagePath="";
		
		try
		{
			while(new File(FilePath+"EMS//forms//Form-"+maxFormId+"-"+formDto.getFormNo()+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename())).exists())
			{
				maxFormId++;
			}
			
			saveFile(FilePath+"EMS//forms//","Form-"+maxFormId+"-"+formDto.getFormNo()+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename()),FormFile);
			storagePath=FilePath+"EMS//forms//Form-"+maxFormId+"-"+formDto.getFormNo()+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename());
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		EMSForms form = new EMSForms().builder()
				.FormNo(formDto.getFormNo().trim())
				.Description(formDto.getDescription())
				.DepTypeId(Integer.parseInt(formDto.getDepTypeId()))
				.FormName(FormFile.getOriginalFilename())
				.FormPath(storagePath)
				.FormUploadDate(LocalDate.now().toString())
				.CreatedBy(formDto.getCreatedBy())
				.CreatedDate(sdtf.format(new Date()))
				.IsActive(1)
				.build();
		
		return dao.EMSFormAdd(form);
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
	    }     
	}
	 
	@Override
	public EMSForms GetEMSForm(String formId)throws Exception
	{
		return dao.GetEMSForm(formId);
	}
	
	@Override
	public long getFormNoCount(String formNo) throws Exception 
	{
		return dao.getFormNoCount(formNo);
	}
}
