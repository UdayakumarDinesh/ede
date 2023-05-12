<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDate"%>   
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">



<style type="text/css">
/* @media print {
	#printPageButton {
		display: none;
	}
}
 */
 #pageborder {
				position: fixed;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
				border: 2px solid black;
			}

			@page {
				size: 790px 1120px;
				margin-top: 49px;
				margin-left: 72px;
				margin-right: 39px;
				margin-buttom: 49px;
				border: 2px solid black;

				@bottom-right {
					counter-increment: page;
  					counter-reset: page 2;
					content: "Page "counter(page) " of "counter(pages);
					margin-bottom: 30px;
					margin-right: 10px;
				}


				@top-right {
					content: "";
					margin-top: 30px;
					margin-right: 10px;
				}

				@top-left {
					margin-top: 30px;
					margin-left: 10px;
					content: "";
				}

				@top-center {
					margin-top: 30px;
					content: "";

				}

				@bottom-center {
					margin-bottom: 30px;
					content: "";
				}
				
				
		}
 

.square{
  background-color: white;
  width: 70px;
  height:70px;
  border: 1px solid black;
  padding: 20px;
  margin-left: 500px;
  align:right;

}

  .text-center {
	text-align: center;
} 

.break
	 {
	  	page-break-before:always;
	 }
.text-blue
{
	color: blue;
	font-weight:400px;
}

	 
</style>

<title>NOC Passport Certificate</title>
</head>
<body>


	<%
	
	   Object[] obj= (Object[])request.getAttribute("NocPassportDetails");
	   SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
       LocalDate date = LocalDate.now(); 
    
    
	%>


      

       <div style="text-align:center;margin-top:250px;">
        <div class="square" style="text-align:center">Applicant photo</div>
        <br>
		<h3 style="text-align: center;text-decoration: underline;">NO OBJECTION CERTIFICATE</h3>
	
	
	 <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 18px;" align="left">
				
		Mr.&nbsp;<span class="text-blue" style="text-decoration: underline;"><%=obj[1] %></span> son of/Daughter of Mr.<%if(obj[35]!=null){ %> <span class="text-blue" style="text-decoration: underline;"><%=obj[35] %></span><%} else if(obj[12].toString().equalsIgnoreCase("F")){ %><span class="text-blue" style="text-decoration: underline;"><%=obj[13] %></span><%} %>&nbsp;who is an Indian national,is employed in this office 
          as from&nbsp;<span class="text-blue"style="text-decoration: underline;font-size:16px;" ><%=rdf.format(sdf.parse(obj[21].toString())) %></span>&nbsp;till<span class="text-blue" style="text-decoration: underline;font-size:16px;"> <%=rdf.format(sdf.parse(obj[22].toString())) %></span> &nbsp;date.This office has no objection for obtaining his Passport.	   			
			   			
				   																			
		</div>
		
		<br>
		<div align="left" style="margin-left:10px !important;" >Date:&nbsp;<span class="text-blue"><%=rdf.format(sdf.parse(date.toString())) %></span></div>	
		<br>
		<br>
	    <div style="margin-left:450px !important;" ><span class="text-blue" style=" text-transform: uppercase;"><%=obj[33] %></span></div>	
	    <div style="margin-left:250px !important;">Dy.Manager-Personnel & Administration </div> 
	    <div style="margin-left:220px !important;">TelNo.080-2565 3588 / 2566 9802(Extn-9840) </div> 
	    <div style="margin-left:380px !important;">Fax No.080-25651702</div>
	     <!-- <div style="margin-left:450px !important;">E-Mail id:</div>  -->
	 <br>
		
   
	
	
	 
	
</div>
		
		

</body>
</html>