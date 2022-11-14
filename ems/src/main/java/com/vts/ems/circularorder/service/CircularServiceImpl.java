package com.vts.ems.circularorder.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.vts.ems.circularorder.dao.CircularDao;
import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.dto.DepCircularDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.CustomEncryptDecrypt;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.Zipper;



@Service
public class CircularServiceImpl implements CircularService 
{
	private static final Logger logger=LogManager.getLogger(CircularServiceImpl.class);
	
	@Autowired
	CircularDao dao;
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Value("${EMSFilesPath}")
    private String FilePath;

	@Override
	public long CircularAdd(CircularUploadDto cirdto) throws Exception 
	{
		logger.info(new Date() +"Inside CircularAdd");
		
		long maxCircularId=0;
		String CirFileName=null;	
		String year=null;
		long count=0;	
		try {	
			
			String cirDate = cirdto.getCircularDate();
			maxCircularId = dao.GetCircularMaxId()+1;
			String[] cirSplit = cirDate.split("-");
			CirFileName ="C"+maxCircularId+cirSplit[0]+cirSplit[1]+cirSplit[2];
	
			
			year = cirSplit[2];
			String CircularPath = "EMS\\Circulars\\"+year+"\\";
			
			String FullDir = FilePath+CircularPath;
			File theDir = new File(FullDir);
			if (!theDir.exists()){
				theDir.mkdirs();
			}
		     
			
			EMSCircular circular = new EMSCircular();
			//query to add details to to ems_circular table
			circular.setCircularNo(cirdto.getCircularNo());
			circular.setCircularDate(DateTimeFormatUtil.dateConversionSql(cirDate).toString());
			circular.setCirSubject(cirdto.getCirSubject());
			circular.setCirFileName(cirdto.getOriginalName());
			circular.setCircularPath(CircularPath+CirFileName+".zip");
			circular.setCircularKey(CustomEncryptDecrypt.encryption(cirdto.getAutoId().toCharArray()));
			circular.setCreatedBy(cirdto.getCreatedBy());
			circular.setCreatedDate(cirdto.getCreatedDate());
			circular.setIsActive(1);
			
			Zipper zip=new Zipper();
			zip.pack(cirdto.getOriginalName(),cirdto.getIS(),FullDir,CirFileName,cirdto.getAutoId());
				
			
		    count =  dao.AddCircular(circular);
		
		}catch (Exception e) {
			 e.printStackTrace();
		   count=0;
		}
	
		
		   return count;
		
			
	}
	
	@Override
	public long GetMaxCircularId() throws Exception
	{
		return dao.GetCircularMaxId();
	}
	
	@Override
	public long GetDepCircularMaxIdEdit(String DepTypeId) throws Exception 
	{
		return dao.GetDepCircularMaxIdEdit(DepTypeId);
	}
	@Override
	public long CircularUpdate(CircularUploadDto cirdto) throws Exception 
	{
		logger.info(new Date() +"Inside CircularUpdate");

		String year=null;
		long count=0;	
		try {	
			
			long CircularId = cirdto.getCircularId();
			EMSCircular circular = dao.GetCircularDetailsToEdit(CircularId);
			String cirDate = cirdto.getCircularDate();
	        String[] cirSplit = cirDate.split("-");
	        year = cirSplit[2];
	        
	        if(!cirdto.getCircularPath().isEmpty()) {
	        	
	        	new File(FilePath+circular.getCircularPath()).delete();
	        	String OrigFilelName = cirdto.getCircularPath().getOriginalFilename();
	        	String CirFileName = "C"+CircularId+cirSplit[0]+cirSplit[1]+cirSplit[2];
	        	String CircularPath = "EMS\\Circulars\\"+year+"\\";
	        	String FullDir = FilePath+CircularPath;
	        	
	        	File theDir = new File(FullDir);
				if (!theDir.exists()){
					theDir.mkdirs();
				}
	        	
	        	Zipper zip=new Zipper();
				zip.pack(cirdto.getOriginalName(),cirdto.getIS(),FullDir,CirFileName,cirdto.getAutoId());
	        	
	        	circular.setCirFileName(OrigFilelName);
				circular.setCircularPath(CircularPath+CirFileName+".zip");

	        
	        }
		
			//query to update details to to ems_circular table
			circular.setCircularId(cirdto.getCircularId());
			circular.setCircularNo(cirdto.getCircularNo());
			circular.setCircularDate(DateTimeFormatUtil.dateConversionSql(cirDate).toString());
			circular.setCirSubject(cirdto.getCirSubject());
			circular.setCircularKey(CustomEncryptDecrypt.encryption(cirdto.getAutoId().toCharArray()));
			circular.setModifiedBy(cirdto.getModifiedBy());
			circular.setModifiedDate(cirdto.getModifiedDate());
			circular.setIsActive(1);
			
			
				
			count = dao.EditCircular(circular); 
		
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
		logger.info(new Date() +"Inside GetCircularList");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
		LocalDate ToDate= LocalDate.parse(todate, formatter);
		return dao.GetCircularList(Fromdate , ToDate);
	}
	
	@Override
	public int CircularDelete(Long CircularId, String Username)throws Exception{
		return dao.CircularDelete(CircularId,Username);
	}
	
	
	@Override
	   public EMSCircular GetCircularDetailsToEdit(Long CircularId)throws Exception
	   {
		   return dao.GetCircularDetailsToEdit(CircularId);
	   }
	
	


	@Override
	public List<Object[]> GetSearchList(String search) throws Exception {
		
		return dao.GetSearchList(search);
	}

	@Override
	public List<Object[]> DepCircularSearchList(String search,String id) throws Exception {
		
		if(Integer.parseInt(id)==0) {
			return dao.DepCircularSearchList(search);
		}
		
		return dao.DepCircularSearchList(search,id);
	}



	@Override
	public EMSCircular getCircularData(String CircularId) throws Exception
	{
		return dao.getCircularData(CircularId);
	}
	
	@Override
	public EmployeeDetails getEmpdataData(String empNo) throws Exception
	{
		return dao.getEmpdataData(empNo);
	}

	@Override
	public File EncryptAddWaterMarkAndMetadatatoPDF(PdfFileEncryptionDataDto dto) throws Exception
	{
		logger.info(new Date() +"Inside EncryptAddWaterMarkAndMetadatatoPDF");
		File pdffile = new File(dto.getSourcePath());
		OutputStream tofile = new FileOutputStream(new File(dto.getTargetPath()));
		
		String WMText = "";
        for(int i=1;i<=25;i++)
        {
     	   WMText = WMText+dto.getWatermarkText()+" ";
        }
        String WMTextLine=WMText;
        WMTextLine +="\n";
        WMTextLine +="\n";
        WMTextLine += WMText;
        
        //create and Encrypt the File
		try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdffile), new PdfWriter(tofile, new WriterProperties().setStandardEncryption(dto.getPassword().getBytes(), "0123456789".getBytes(),
	            /*EncryptionConstants.ALLOW_PRINTING*/ 0, EncryptionConstants.ENCRYPTION_AES_128 | EncryptionConstants.DO_NOT_ENCRYPT_METADATA)))) {
			
			//Adding Metadata to the pdf file
			String timestamp =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(new Date().getTime()) );
			pdfDoc.getDocumentInfo().setTitle(dto.getWatermarkText()+" : "+timestamp);
	        pdfDoc.getDocumentInfo().setMoreInfo("EMP NO", dto.getEmpNo());
	        pdfDoc.getDocumentInfo().setMoreInfo("EMP Name",dto.getEmpName());
	        pdfDoc.getDocumentInfo().setMoreInfo("Downloaded On",timestamp );
			
			// Adding Watermark to the PDF
		    PdfFont helvetica = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
		    for (int pageNum = 1; pageNum <= pdfDoc.getNumberOfPages(); pageNum++) {
		        PdfPage page = pdfDoc.getPage(pageNum);
		        PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
	
		        PdfExtGState gstate = new PdfExtGState();
		        gstate.setFillOpacity(.25f);
		        canvas = new PdfCanvas(page);
		        canvas.saveState();
		        canvas.setExtGState(gstate);
		        try (Canvas canvas2 = new Canvas(canvas,  page.getPageSize())) {
			        Paragraph watermark = new Paragraph(WMTextLine)
			                   .setFont(helvetica)
			                   .setFontSize(10)
			                   .setPaddings(10, 10,10, 10);
		            
		      
			        Rectangle pageSize = page.getPageSize();
			        float x=420;
			        float y=297.5f;
			        float rotationRad = (float) Math.toRadians(53);
			        canvas2.showTextAligned(watermark, pageSize.getLeft()+20,20, pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
			        rotationRad = (float) Math.toRadians(-51);
			        canvas2.showTextAligned(watermark, x, y, pageNum, TextAlignment.CENTER, VerticalAlignment.MIDDLE , rotationRad);
		        }
		     
		        canvas.restoreState();
		    }
		    pdfDoc.close();
		 }catch (Exception e)
		{
			 e.printStackTrace();
		}
		return new File(dto.getTargetPath());
	}
	
//	public void encrypt(String source, String target, byte[] password) throws IOException 
//	{
//		
//	    PdfReader reader = new PdfReader(new FileInputStream(source));
//	    PdfWriter writer = new PdfWriter( new FileOutputStream(target), new WriterProperties().setStandardEncryption(password, "123456789".getBytes(),
//	            /*EncryptionConstants.ALLOW_PRINTING*/ 0, EncryptionConstants.ENCRYPTION_AES_128 | EncryptionConstants.DO_NOT_ENCRYPT_METADATA));
//	    
//	    new PdfDocument(reader, writer).close();
//	}

	@Override
	public long CircularTransactionAdd(EMSCircularTrans cirTrans) throws Exception 
	{
		return dao.CircularTransactionAdd(cirTrans);
	}
	
	@Override
	public long DepCircularTransactionAdd(DepEMSCircularTrans cirTrans) throws Exception 
	{
		return dao.DepCircularTransactionAdd(cirTrans);
	}
	
	@Override
	public List<Object[]> GetDepCircularList(String fromdate, String toDate,String DepTypeId) throws Exception 
	{
		return dao.GetDepCircularList(fromdate, toDate, DepTypeId);
	}
	
	@Override
	public Object[] GetEmsDepType(String DepTypeId) throws Exception 
	{
		return dao.GetEmsDepType(DepTypeId);
	}
	
	@Override
	public long DepCircularAdd(DepCircularDto dto) throws Exception 
	{
		logger.info(new Date() +"Inside DepCircularAdd");
		EMSDepCircular cir=dto.getCircular();
		String CircularPath = "EMS\\DepartmentCirculars\\"+LocalDate.parse(cir.getDepCircularDate()).getYear()+"\\";
		String FullDir = FilePath+CircularPath;
		File theDir = new File(FullDir);
		if (!theDir.exists()){
			theDir.mkdirs();
		}
		
		String cirDate = cir.getDepCircularDate();
		long maxCircularId = dao.GetDepCircularMaxId()+1;
		String[] cirSplit = cirDate.split("-");
		String CirFileName ="C"+maxCircularId+cirSplit[0]+cirSplit[1]+cirSplit[2];

	    
		String filename=dto.getCirFile().getOriginalFilename();
//		int count=0;
//		String tempName = filename;
//		while(new File(FullDir+tempName).exists())
//		{
//			count++;
//			tempName = FilenameUtils.getName(filename)+"("+count+")."+FilenameUtils.getExtension(filename);
//		}
		
		
//		CustomEncryptDecrypt
		String key  = UUID.randomUUID().toString();
		cir.setDepCircularKey(CustomEncryptDecrypt.encryption(key.toCharArray()));
		cir.setIsActive(1);
		Zipper zip=new Zipper();
		zip.pack(filename,dto.getCirFile().getInputStream(),FullDir,CirFileName,key);
		
		
		cir.setCreatedDate(sdtf.format(new Date()));
		cir.setDepCircularPath(CircularPath+CirFileName+".zip");
		cir.setDepCirFileName(filename);
		return dao.EmsDepCircularAdd(cir);
	}
	
	
	
	public static void saveFile(String CircularFilePath, String fileName, MultipartFile multipartFile) throws IOException 
	{
		logger.info(new Date() +"Inside saveFile");
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
	public EMSDepCircular getEmsDepCircular(String circularId) throws Exception
	{
		return dao.getEmsDepCircular(circularId);
	}
	
	@Override
	public long DepCircularEdit(DepCircularDto dto) throws Exception 
	{
		logger.info(new Date() +"Inside DepCircularEdit");
		EMSDepCircular cir = dto.getCircular();
		EMSDepCircular fetch = dao.getEmsDepCircular(String.valueOf(cir.getDepCircularId()));
		fetch.setDepCircularDate(cir.getDepCircularDate());
		fetch.setDepCircularNo(cir.getDepCircularNo());
		fetch.setDepCirSubject(cir.getDepCirSubject());
		fetch.setModifiedBy(cir.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(!dto.getCirFile().isEmpty()) 
		{
			String CircularPath = "EMS\\DepartmentCirculars\\"+LocalDate.parse(cir.getDepCircularDate()).getYear()+"\\";
			String FullDir = FilePath+CircularPath;
			File theDir = new File(FullDir);
			if (!theDir.exists()){
				theDir.mkdirs();
			}
			
			String cirDate = cir.getDepCircularDate();
			long maxCircularId = dao.GetDepCircularMaxId()+1;
			String[] cirSplit = cirDate.split("-");
			String CirFileName ="C"+maxCircularId+cirSplit[0]+cirSplit[1]+cirSplit[2];
		    
			String filename=dto.getCirFile().getOriginalFilename();
	
			String key =  UUID.randomUUID().toString();
			
			Zipper zip=new Zipper();
			zip.pack(filename,dto.getCirFile().getInputStream(),FullDir,CirFileName,key);
			
			new File(FilePath+fetch.getDepCircularPath()).delete();
			fetch.setDepCircularKey(CustomEncryptDecrypt.encryption(key.toCharArray()));
			fetch.setDepCircularPath(CircularPath+CirFileName+".zip");
			fetch.setDepCirFileName(filename);
		}
		
		return dao.EmsDepCircularEdit(fetch);
	}
	
	@Override
	public long DepCircularDelete(String circularId,String Username) throws Exception 
	{
		logger.info(new Date() +"Inside DepCircularDelete");
		EMSDepCircular fetch = dao.getEmsDepCircular(circularId);
		fetch.setModifiedBy(Username);
		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setIsActive(0);
		return dao.EmsDepCircularEdit(fetch);
	}
	
	@Override
	public List<Object[]> GetEmsDepType() throws Exception
	{
		return dao.GetEmsDepType();
	}
	
}
