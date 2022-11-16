package com.vts.ems.circularorder.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
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
import com.vts.ems.circularorder.dao.OfficeOrderDao;
import com.vts.ems.circularorder.dto.OfficeOrderUploadDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.CustomEncryptDecrypt;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.Zipper;



@Service
public class OfficeOrderServiceImpl implements OfficeOrderService 
{
	private static final Logger logger=LogManager.getLogger(OfficeOrderServiceImpl.class);
	
	@Autowired
	OfficeOrderDao dao;
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Value("${EMSFilesPath}")
    private String FilePath;

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
			String orderPath = "EMS\\orders\\"+year+"\\";
			
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
	        
	        System.out.println("pathcir"+uploadorderdto.getOrderPath());
	        if(!uploadorderdto.getOrderPath().isEmpty()) {
	        	
	        	new File(FilePath+Order.getOrderPath()).delete();
	        	String OrigFilelName = uploadorderdto.getOrderPath().getOriginalFilename();
	        	String CirFileName = "C"+OrderId+cirSplit[0]+cirSplit[1]+cirSplit[2];
	        	String CircularPath = "EMS\\Circulars\\"+year+"\\";
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
	public EmployeeDetails getEmpdataData(String empNo) throws Exception
	{
		return dao.getEmpdataData(empNo);
	}

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
//			        float x=420;
//			        float y=297.5f;
//			        float rotationRad = (float) Math.toRadians(53);
//			        canvas2.showTextAligned(watermark, pageSize.getLeft()+20,20, pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
//			        rotationRad = (float) Math.toRadians(-51);
//			        canvas2.showTextAligned(watermark, x, y, pageNum, TextAlignment.CENTER, VerticalAlignment.MIDDLE , rotationRad);
			        
			        if(pageSize.getRight() < pageSize.getTop())
				       {
					        float rotationRad = (float) Math.toRadians(55);
					        canvas2.showTextAligned(watermark, pageSize.getLeft(),pageSize.getBottom(), pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
					        rotationRad = (float) Math.toRadians(-55);
					        canvas2.showTextAligned(watermark, pageSize.getLeft(),pageSize.getTop(), pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
				       }
				       else
				       {
				    	   float rotationRad = (float) Math.toRadians(-145);
					        canvas2.showTextAligned(watermark, pageSize.getRight(),pageSize.getTop(), pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
					        rotationRad = (float) Math.toRadians(-35);
					        canvas2.showTextAligned(watermark, pageSize.getLeft(),pageSize.getTop(), pageNum, TextAlignment.LEFT, VerticalAlignment.MIDDLE , rotationRad);
				       }
			        
		        }
		     
		        canvas.restoreState();
		    }
		    pdfDoc.close();
		 }catch (Exception e)
		{
			 logger.error(new Date() +"Inside SERVICE EncryptAddWaterMarkAndMetadatatoPDF ");
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
	public long OfficeOrderTransactionAdd(EMSOfficeOrderTrans  OrderTrans) throws Exception 
	{
		return dao.OfficeOrderTransactionAdd(OrderTrans);
	}
	
}
