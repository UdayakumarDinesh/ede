
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


.text-blue
{
	color: blue;
	font-weight:400px;
	font-size:17px;
}
/* table {
	border-collapse: collapse;
	
	width: 620px;
}

.table tr, th, td {
	border: 1px solid black;
	padding: 0px;
}
.table tr{
	height: 10px;
} */
 
</style>

<title>NOC Passport Certificate</title>
</head>
<body>


	<%
	
	   Object[] obj= (Object[])request.getAttribute("NocPassportDetails");
	   SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
       LocalDate date = LocalDate.now(); 
       Object[] empdetails =(Object[])request.getAttribute("EmpGenderPassport");
       Object[] empData=(Object[])request.getAttribute("EmpData");
    
	%>


       <div style="text-align:center;margin-top:180px;">
         <div align="left" style="margin-left:10px !important;" >Ref:STARC/P&A/PERS(<%=obj[0] %>) :-&nbsp;<span class="text-blue"><%=obj[17] %></span></div>
          <div align="right" style="margin-top:-20px;margin-right:10px;" >Date:&nbsp;<span class="text-blue"><%=rdf.format(sdf.parse(date.toString())) %></span></div>
          <br>
          <br>
           <br>
            <br>
          <br>
            <div class="square" style="text-align:center">Applicant photo</div>
            <br>
            <br>
            <br>
            <br>
            <br>
           
            
            
		      <h3 style="text-align: center;text-decoration: underline;">NO OBJECTION CERTIFICATE</h3>
	
	
	      <div style="margin-left: 10px;text-align: justify;width:650px; text-justify: inter-word;font-size: 17px;line-height:1.5" align="left">
				
				<% if(empdetails[1].toString().equalsIgnoreCase("M")){ %>Mr<%} else if(empdetails[1].toString().equalsIgnoreCase("F")){ %>Mrs<%} %>.&nbsp;<span class="text-blue" ><%=obj[1] %></span> son of / Daughter of Mr. <%if(empdetails[2]!=null){ %> <span class="text-blue" ><%=empdetails[2] %></span><%} else{ %><span class="text-blue"> NA </span> <%} %>&nbsp;who is an Indian national,is employed in this office 
          as from &nbsp;<span class="text-blue" style="font-size:16px;" ><%=rdf.format(sdf.parse(obj[27].toString())) %></span>&nbsp; till <span class="text-blue" style="font-size:16px;"> <%=rdf.format(sdf.parse(obj[28].toString())) %></span> .This office has no objection for obtaining his Passport.	   			
			   			
				   																			
	</div>
		
		<br>
		<br>
         <br>
		
		 <div style="margin-right:60px !important;margin-top:20px;text-align:right;"> 
						        <span class="text-blue" style=" text-transform: uppercase;"><%=empData[1]%></span><br>
								<span  class="text-blue" style="font-weight: 400; font-size: 16px; "><%=empData[2]%></span><br>
								<span style="font-weight: 400; font-size: 16px;">TelNo.<%=empData[6] %>/<% if(!empData[7].toString().equalsIgnoreCase("0") || !empData[7].toString().equalsIgnoreCase("")){%><%=empData[7] %><%} %>(Extn-<%=empData[8] %></span>)<br>
								<span style="font-weight: 400; font-size: 16px;">E-Mail id:&nbsp;<span style="text-decoration: underline;"><%=empData[9] %></span></span><br>
		 </div>
	   
	
</div>
		
		

</body>
</html>