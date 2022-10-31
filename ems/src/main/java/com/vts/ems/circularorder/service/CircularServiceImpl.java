package com.vts.ems.circularorder.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.vts.ems.circularorder.dao.CircularDao;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.pis.model.EmployeeDetails;

@Service
public class CircularServiceImpl implements CircularService 
{
	@Autowired
	private CircularDao dao;
	
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
			pdfDoc.getDocumentInfo().setTitle("Circular");
	        pdfDoc.getDocumentInfo().setMoreInfo("EMP NO", dto.getEmpNo());
	        pdfDoc.getDocumentInfo().setMoreInfo("EMP Name",dto.getEmpName());
	        pdfDoc.getDocumentInfo().setMoreInfo("Downloaded On",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(new Date().getTime()) ) );
			
			
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

	
}
