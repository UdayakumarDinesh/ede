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
import com.vts.ems.circularorder.dto.NoticeUploadDto;
import com.vts.ems.circularorder.model.EMSForms;
import com.vts.ems.circularorder.model.EMSNotice;
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
			storagePath="EMS//forms//Form-"+maxFormId+"-"+formDto.getFormNo()+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename());
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
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
	@Override
	public long EMSFormDelete(String EMSFormId, String userId)throws Exception
	{
		EMSForms form = dao.GetEMSForm(EMSFormId);
		form.setModifiedBy(userId);
		form.setModifiedDate(sdtf.format(new Date()));
		form.setIsActive(0);
		return dao.EMSFormEdit(form);
	}
	
	
	@Override
	public List<Object[]> getEmsNoticeList(String FromDate, String ToDate) throws Exception
	{
		return dao.getEmsNoticeList(FromDate, ToDate);
	}
	
	
	@Override
	public long EmsNoticeAdd(NoticeUploadDto noticeDto) throws Exception 
	{
		MultipartFile FormFile = noticeDto.getNoticeFile();
		long maxFormId = dao.MaxOfEmsNoticeId()+1;
		String storagePath="";
		
		try
		{
			while(new File(FilePath+"EMS//Notice//Notice-"+maxFormId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename())).exists())
			{
				maxFormId++;
			}
			saveFile(FilePath+"EMS//Notice//","Notice-"+maxFormId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename()),FormFile);
			storagePath="EMS//Notice//Notice-"+maxFormId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		EMSNotice Notice = new EMSNotice().builder()
				.ReferenceNo(noticeDto.getReferenceNo())
				.Description(noticeDto.getDescription())
				.FileOriginalName(FormFile.getOriginalFilename())
				.NoticePath(storagePath)
				.NoticeDate(noticeDto.getNoticeDate())
				.ToDate(noticeDto.getToDate())
				.CreatedBy(noticeDto.getCreatedBy())
				.CreatedDate(sdtf.format(new Date()))
				.IsActive(1)
				.build();
		
		return dao.EMSNoticeAdd(Notice);
	}
	
	@Override
	public EMSNotice GetEMSNotice(String NoticeId)throws Exception
	{
		return dao.GetEMSNotice(NoticeId);
	}
	
	@Override
	public long EMSNoticeEdit(NoticeUploadDto noticeDto) throws Exception 
	{
		MultipartFile FormFile = noticeDto.getNoticeFile();
		
		EMSNotice Notice = dao.GetEMSNotice(noticeDto.getNoticeId());
		String storagePath=Notice.getNoticePath();
		if(!FormFile.isEmpty()) {
			try
			{
				saveFile(
						FilePath+FilenameUtils.getPath(storagePath), 
						FilenameUtils.getBaseName(storagePath)+ "." + FilenameUtils.getExtension(storagePath),
						FormFile
						);
				Notice.setFileOriginalName(FormFile.getOriginalFilename());
			}
			catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
				
		Notice.setReferenceNo(noticeDto.getReferenceNo());
		Notice.setDescription(noticeDto.getDescription());
		
//		Notice.setNoticePath(storagePath);
		Notice.setNoticeDate(noticeDto.getNoticeDate());
		Notice.setToDate(noticeDto.getToDate());
		Notice.setModifiedBy(noticeDto.getModifiedBy());
		Notice.setModifiedDate(sdtf.format(new Date()));
		
		return dao.EMSNoticeEdit(Notice);
	}
	
	
	@Override
	public long EMSNoticeDelete(String NoticeId, String userId)throws Exception
	{
		EMSNotice Notice = dao.GetEMSNotice(NoticeId);
		Notice.setModifiedBy(userId);
		Notice.setModifiedDate(sdtf.format(new Date()));
		Notice.setIsActive(0);
		return dao.EMSNoticeEdit(Notice);
	}
	
	@Override
	public List<Object[]> getEmsTodayNotices() throws Exception
	{
		return dao.getEmsTodayNotices();
	}
	
}
