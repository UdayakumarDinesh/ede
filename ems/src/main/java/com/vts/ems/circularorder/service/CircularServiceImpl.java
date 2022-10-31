package com.vts.ems.circularorder.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vts.ems.circularorder.dao.CircularDao;
import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.Zipper;



@Service
public class CircularServiceImpl implements CircularService {
	
	@Autowired
	CircularDao dao;
	
	private static final Logger logger=LogManager.getLogger(CircularServiceImpl.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Value("${File_Path}")
    private String FilePath;



	@Override
	public long CircularUpload(CircularUploadDto cirdto,EMSCircular circular) throws Exception {
		

		logger.info(new Date() +"Inside CircularUpload");
	
	long maxCircularId=0;
	String CirFileName=null;	
	String year=null;
	long count=0;	
	try {	
		
		String cirDate = cirdto.getCircularDate();
		maxCircularId = dao.GetCircularMaxId();
		maxCircularId = maxCircularId+1;
		System.out.println("maxCircularId  :"+maxCircularId);
		String[] cirSplit = cirDate.split("-");
		CirFileName ="C"+maxCircularId+cirSplit[0]+cirSplit[1]+cirSplit[2];
		cirdto.setCirFileName(CirFileName);
		
		year = cirSplit[2];
		String FullDir = FilePath+"\\EMS\\Circular\\"+year+"\\";
		File theDir = new File(FullDir);
		 if (!theDir.exists()){
		     theDir.mkdirs();
		 }
		
		
	     Zipper zip=new Zipper();
		 String Pass= cirdto.getAutoId();
		
		
		//query to add details to to ems_circular table
		circular.setCircularNo(cirdto.getCircularNo());
		circular.setCircularDate(DateTimeFormatUtil.dateConversionSql(cirDate).toString());
		circular.setCirSubject(cirdto.getCirSubject());
		circular.setCirFileName(cirdto.getOriginalName());
		circular.setCircularPath("\\EMS\\Circular\\"+year+"\\"+CirFileName+".zip");
		circular.setCircularKey(cirdto.getAutoId());
		circular.setCreatedBy(cirdto.getCreatedBy());
		circular.setCreatedDate(cirdto.getCreatedDate());
		circular.setIsActive(1);
		
	    count =  dao.CircularAdd(circular);
		
	    if(count>0) {
		
		zip.pack(cirdto.getOriginalName(),cirdto.getIS(),FullDir,cirdto.getCirFileName(),Pass);
		
	    }
	
	
	}catch (Exception e) {
		 e.printStackTrace();
	   count=0;
	}

	
	   return count;
	
		
	}
	
	@Override
	public List<Object[]> selectAllList() throws Exception 
	{
		return dao.selectAllList();
	}

	@Override
	public List<Object[]> GetCircularList(String fromdate, String todate) throws Exception {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
		LocalDate ToDate= LocalDate.parse(todate, formatter);
		return dao.GetCircularList(Fromdate , ToDate);
	}

}
