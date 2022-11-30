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
import com.vts.ems.circularorder.dao.CircularOrderDao;
import com.vts.ems.circularorder.dto.DepCircularDto;
import com.vts.ems.circularorder.dto.OfficeOrderUploadDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.circularorder.model.EMSGovtOrders;
import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.CustomEncryptDecrypt;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.Zipper;

@Service
public class CircularOrderServiceImpl implements CircularOrderService 
{
	private static final Logger logger=LogManager.getLogger(CircularOrderServiceImpl.class);
	
	@Autowired
	CircularOrderDao dao;
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Value("${EMSFilesPath}")
    private String FilePath;

	@Override
	public File EncryptAddWaterMarkAndMetadatatoPDF(PdfFileEncryptionDataDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EncryptAddWaterMarkAndMetadatatoPDF ");
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
			        
			       if(pageSize.getRight() < pageSize.getTop())
			       {
				        float rotationRad = (float) Math.toRadians(55);
				        canvas2.showTextAligned(watermark, pageSize.getLeft(),pageSize.getBottom(), pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
				        rotationRad = (float) Math.toRadians(-55);
				        canvas2.showTextAligned(watermark, pageSize.getLeft(),pageSize.getTop(), pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
			       }
			       else
			       {
			    	   float rotationRad = (float) Math.toRadians(35);
				       canvas2.showTextAligned(watermark, pageSize.getLeft(),pageSize.getBottom(), pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
				       rotationRad = (float) Math.toRadians(-35);
				       canvas2.showTextAligned(watermark, pageSize.getLeft(),pageSize.getTop(), pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
			       }
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

	
	
	
	@Override
	public long GetDepCircularMaxIdEdit(String DepTypeId) throws Exception 
	{
		return dao.GetDepCircularMaxIdEdit(DepTypeId);
	}
	
	@Override
	public List<Object[]> DepCircularSearchList(String search,String id) throws Exception {
		
		if(Integer.parseInt(id)==0) {
			return dao.DepCircularSearchList(search);
		}
		
		return dao.DepCircularSearchList(search,id);
	}



	@Override
	public EmployeeDetails getEmpdataData(String empNo) throws Exception
	{
		return dao.getEmpdataData(empNo);
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
		logger.info(new Date() +"Inside SERVICE DepCircularAdd ");
		EMSDepCircular cir=dto.getCircular();
		String CircularPath = "\\DepartmentCirculars\\"+LocalDate.parse(cir.getDepCircularDate()).getYear()+"\\";
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
		logger.info(new Date() +"Inside SERVICE saveFile ");
        Path uploadPath = Paths.get(CircularFilePath);
	         
	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	    }
	        
	    try (InputStream inputStream = multipartFile.getInputStream()) {
	        Path filePath = uploadPath.resolve(fileName);
	        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (IOException ioe) {       
	        throw new IOException("Could not save file: " + fileName, ioe);
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
		logger.info(new Date() +"Inside SERVICE DepCircularEdit ");
		EMSDepCircular cir = dto.getCircular();
		EMSDepCircular fetch = dao.getEmsDepCircular(String.valueOf(cir.getDepCircularId()));
		fetch.setDepCircularDate(cir.getDepCircularDate());
		fetch.setDepCircularNo(cir.getDepCircularNo());
		fetch.setDepCirSubject(cir.getDepCirSubject());
		fetch.setModifiedBy(cir.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(!dto.getCirFile().isEmpty()) 
		{
			String CircularPath = "\\DepartmentCirculars\\"+LocalDate.parse(cir.getDepCircularDate()).getYear()+"\\";
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
			new File(FilePath+fetch.getDepCircularPath()).delete();
			Zipper zip=new Zipper();
			zip.pack(filename,dto.getCirFile().getInputStream(),FullDir,CirFileName,key);
			
			
			fetch.setDepCircularKey(CustomEncryptDecrypt.encryption(key.toCharArray()));
			fetch.setDepCircularPath(CircularPath+CirFileName+".zip");
			fetch.setDepCirFileName(filename);
		}
		
		return dao.EmsDepCircularEdit(fetch);
	}
	
	@Override
	public long DepCircularDelete(String circularId,String Username) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE DepCircularDelete ");
		EMSDepCircular fetch = dao.getEmsDepCircular(circularId);
		fetch.setModifiedBy(Username);
		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setIsActive(0);
		return dao.EmsDepCircularEdit(fetch);
	}
	
	@Override
	public List<Object[]> GetEmsDepTypeList() throws Exception
	{
		return dao.GetEmsDepTypeList();
	}
	
	
	@Override
	public long OfficeOrderAdd(OfficeOrderUploadDto uploadorderdto) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE OfficeOrderAdd ");
		
		long maxOrderId=0;
		String OrderFileName=null;	
		String year=null;
		long count=0;	
		try {	
			
			String OrderDate = uploadorderdto.getOrderDate();
			maxOrderId = dao.GetOrderMaxId()+1;
			String[] OrderSplit = OrderDate.split("-");
			OrderFileName ="C"+maxOrderId+OrderSplit[0]+OrderSplit[1]+OrderSplit[2];
	
			
			year = OrderSplit[2];
			String orderPath = "\\orders\\"+year+"\\";
			
			String FullDir = FilePath+orderPath;
			File theDir = new File(FullDir);
			if (!theDir.exists()){
				theDir.mkdirs();
			}
		     
			
			EMSOfficeOrder order = new EMSOfficeOrder();
			//query to add details to to ems_circular table
			order.setOrderNo(uploadorderdto.getOrderNo());
			order.setOrderDate(DateTimeFormatUtil.dateConversionSql(OrderDate).toString());
			order.setOrderSubject(uploadorderdto.getOrderSubject());
			order.setOrderFileName(uploadorderdto.getOrderFileName());
			order.setOrderPath(orderPath+OrderFileName+".zip");
			order.setOrderKey(CustomEncryptDecrypt.encryption(uploadorderdto.getAutoId().toCharArray()));
			order.setCreatedBy(uploadorderdto.getCreatedBy());
			order.setCreatedDate(uploadorderdto.getCreatedDate());
			order.setIsActive(1);
			
			
			Zipper zip=new Zipper();
			zip.pack(uploadorderdto.getOrderFileName(),uploadorderdto.getIS(),FullDir,OrderFileName,uploadorderdto.getAutoId());
				
		    count =  dao.AddOfficeOrder(order);
		
		}catch (Exception e) {
			logger.error(new Date() +"Inside SERVICE OfficeOrderAdd ");
			 e.printStackTrace();
		   count=0;
		}
	
		
		   return count;
		
			
	}
	
	@Override
	public long GetMaxOrderId() throws Exception
	{
		return dao.GetOrderMaxId();
	}
	
	@Override
	public long OrderUpdate(OfficeOrderUploadDto uploadorderdto) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE OrderUpdate ");

		String year=null;
		long count=0;	
		try {	
			
			long OrderId = uploadorderdto.getOrderId();
			EMSOfficeOrder Order = dao.GetOrderDetailsToEdit(OrderId);
			String cirDate = uploadorderdto.getOrderDate();
	        String[] cirSplit = cirDate.split("-");
	        year = cirSplit[2];
	        
	        if(!uploadorderdto.getOrderPath().isEmpty()) {
	        	
	        	new File(FilePath+Order.getOrderPath()).delete();
	        	String OrigFilelName = uploadorderdto.getOrderPath().getOriginalFilename();
	        	String CirFileName = "C"+OrderId+cirSplit[0]+cirSplit[1]+cirSplit[2];
	        	String CircularPath = "\\Circulars\\"+year+"\\";
	        	String FullDir = FilePath+CircularPath;
	        	
	        	File theDir = new File(FullDir);
				if (!theDir.exists()){
					theDir.mkdirs();
				}
	        	
	        	Zipper zip=new Zipper();
				zip.pack(uploadorderdto.getOrderFileName(),uploadorderdto.getIS(),FullDir,CirFileName,uploadorderdto.getAutoId());
	        	
				Order.setOrderFileName(OrigFilelName);
				Order.setOrderPath(CircularPath+CirFileName+".zip");

	        
	        }
		
			//query to update details to to ems_circular table
	        Order.setOrderId(uploadorderdto.getOrderId());
	        Order.setOrderNo(uploadorderdto.getOrderNo());
	        Order.setOrderDate(DateTimeFormatUtil.dateConversionSql(cirDate).toString());
	        Order.setOrderSubject(uploadorderdto.getOrderSubject());
	        Order.setOrderKey(CustomEncryptDecrypt.encryption(uploadorderdto.getAutoId().toCharArray()));
	        Order.setModifiedBy(uploadorderdto.getModifiedBy());
	        Order.setModifiedDate(uploadorderdto.getModifiedDate());
	        Order.setIsActive(1);
			
			
				
			count = dao.EditOrder(Order); 
		
		}catch (Exception e) {
			logger.error(new Date() +"Inside SERVICE OrderUpdate ");
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
	public List<Object[]> GetOfficeOrderList(String fromdate, String todate) throws Exception {
		logger.info(new Date() +"Inside SERVICE GetOfficeOrderList ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
		LocalDate Todate= LocalDate.parse(todate, formatter);
		return dao.GetOfficeOrderList(Fromdate , Todate);
	}
	
	@Override
	public int OfficeOrderDelete(Long OrdersId, String Username)throws Exception{
		return dao.OfficeOrderDelete(OrdersId,Username);
	}
	
	
	@Override
	   public EMSOfficeOrder GetOrderDetailsToEdit(Long OrdersId)throws Exception
	   {
		   return dao.GetOrderDetailsToEdit(OrdersId);
	   }
	
	


	@Override
	public List<Object[]> GetSearchList(String search) throws Exception {
		
		return dao.GetSearchList(search);
	}



	@Override
	public EMSOfficeOrder getOrderData(String OrderId) throws Exception
	{
		return dao.getOrderData(OrderId);
	}
	

	@Override
	public long OfficeOrderTransactionAdd(EMSOfficeOrderTrans  OrderTrans) throws Exception 
	{
		return dao.OfficeOrderTransactionAdd(OrderTrans);
	}

	@Override
	public List<Object[]> GetGovtOrdersList(String fromdate, String toDate,String DepTypeId) throws Exception 
	{
		return dao.GetGovtOrdersList(fromdate, toDate, DepTypeId);
	}
	
	@Override
	public long GovtOrderAdd(EMSGovtOrders order,MultipartFile OrderFile) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE GovtOrderAdd ");
		String OrderPath = "\\GovtOrders\\"+LocalDate.parse(order.getOrderDate()).getYear()+"\\";
		String FullDir = FilePath+OrderPath;
		File theDir = new File(FullDir);
		if (!theDir.exists()){
			theDir.mkdirs();
		}
		
		String OrderDate = order.getOrderDate();
		long maxOrderId = dao.GetGovtOrderMaxId()+1;
		String[] OrderSplit = OrderDate.split("-");
		String OrderFileName ="GO"+maxOrderId+OrderSplit[0]+OrderSplit[1]+OrderSplit[2];
	    
		String filename=OrderFile.getOriginalFilename();
		
		
		saveFile(FilePath+OrderPath, OrderFileName+"."+FilenameUtils.getExtension(OrderFile.getOriginalFilename()),OrderFile);
		
		order.setCreatedDate(sdtf.format(new Date()));
		order.setOrderFilePath(OrderPath+OrderFileName+"."+FilenameUtils.getExtension(OrderFile.getOriginalFilename()));
		order.setOrderFileName(filename);
		order.setIsActive(1);
		return dao.EmsGovtOrderAdd(order);
	}
	
	@Override
	public EMSGovtOrders getEMSGovtOrder(String OrderId) throws Exception 
	{
		return dao.getEMSGovtOrder(OrderId);
		
	}
	@Override
	public long GovtOrderEdit(EMSGovtOrders order,MultipartFile OrderFile) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE DepCircularEdit ");
		EMSGovtOrders fetch = dao.getEMSGovtOrder(String.valueOf(order.getGovtOrderId()));
		fetch.setOrderDate(order.getOrderDate());
		fetch.setOrderNo(order.getOrderNo());
		fetch.setDescription(order.getDescription());
		fetch.setModifiedBy(order.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(!OrderFile.isEmpty()) 
		{
			String OrderPath = "\\GovtOrders\\"+LocalDate.parse(order.getOrderDate()).getYear()+"\\";
			String FullDir = FilePath+OrderPath;
			File theDir = new File(FullDir);
			if (!theDir.exists()){
				theDir.mkdirs();
			}
			
			String OrderDate = order.getOrderDate();
			String[] OrderSplit = OrderDate.split("-");
			String OrderFileName ="GO"+order.getGovtOrderId()+OrderSplit[0]+OrderSplit[1]+OrderSplit[2];
		    
			String filename=OrderFile.getOriginalFilename();
			
			new File(FilePath+fetch.getOrderFilePath()).delete();
			saveFile(FilePath+OrderPath, OrderFileName+"."+FilenameUtils.getExtension(OrderFile.getOriginalFilename()),OrderFile);
			
			fetch.setCreatedDate(sdtf.format(new Date()));
			fetch.setOrderFilePath(OrderPath+OrderFileName+"."+FilenameUtils.getExtension(OrderFile.getOriginalFilename()));
			fetch.setOrderFileName(filename);
		}
		
		return dao.EmsGovtOrderEdit(fetch);
	}
	
	
	@Override
	public long GovtOrderDelete(String OrderId,String Username) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE DepCircularDelete ");
		EMSGovtOrders fetch = dao.getEMSGovtOrder(OrderId);
		fetch.setModifiedBy(Username);
		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setIsActive(0);
		return dao.EmsGovtOrderEdit(fetch);
	}
	
	@Override
	public List<Object[]> GovtOrderSearchList(String search,String id) throws Exception
	{
		return dao.GovtOrderSearchList(search, id);
	}
}
