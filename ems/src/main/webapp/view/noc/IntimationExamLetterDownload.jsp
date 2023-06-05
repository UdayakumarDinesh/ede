<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
    
<!DOCTYPE html>
<html>
<%
Object[] PerFormData = (Object[])request.getAttribute("PerFormData");
Object[] ResFormData = (Object[])request.getAttribute("ResFormData");
%>
<head>
<style type="text/css">

			.break {
				page-break-after: always;
			}

			#pageborder {
				position: fixed;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
				border: 2px solid black;
			}

			@page {
				size: 790px 950px;
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
					<% if(ResFormData!=null){%>
					content: "Emp No: <%=ResFormData[12] %>";
					<%} else if(PerFormData!=null){%>
					content: "Emp No: <%=PerFormData[9] %>";
					<% } %>
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

p {
	text-align: justify;
	text-justify: inter-word;
}
body
{
	font-size:14px !important;
}
div
{
	width: 650px !important;
}
table{
	align: left;
	width: 650px !important;
	max-width: 650px !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	margin-left:10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
	
	 -ms-word-break: break-all;
     word-break: break-all;

     /* Non standard for WebKit */
     word-break: break-word;

-webkit-hyphens: auto;
   -moz-hyphens: auto;
        hyphens: auto;
	
}
.center{

	text-align: center;
}

.right
{
	text-align: right;
}
input{
border-width: 0 0 1px 0;
width:80%;
}
input:focus {
  outline: none;
}
.text-blue
{
	color: blue;
	font-weight:500px;
	font-size: 16px;
}

</style>
<meta charset="ISO-8859-1">
<title>Intimation For Exam</title>
</head>
<body>
 <%
  
    SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	String LabLogo = (String)request.getAttribute("LabLogo");
	String LoginType = (String)session.getAttribute("LoginType");
	
	Object[] obj=(Object[])request.getAttribute("ExamIntimationDetails");
	 List<Object[]> RemarksHistory=(List<Object[]>)request.getAttribute("RemarksHistory");
	
%>

<div class="center">
             <div style="width: 100%;float:left;">
				<div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
				<div style="margin-left:auto; margin-right:auto;"><h3 ><span style="margin-left: -85px; "> INTIMATION FOR EXAM</span></h3> </div>

			</div>	
			<br><br>	        					
					<table style="margin-top: 40px;border-collapse: collapse;width:100%;">	
					  <tbody>
					  <tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;"> </td> </tr>
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;"> </td> </tr>
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;"> </td> </tr>
						<tr>		
						  <td style="border: 0;width:78%">From <label style="margin-left: 30px;">:</label>&nbsp;<span class="text-blue"><%=obj[2] %></span> </td>
						  <td style="border: 0;width:17%;">To &nbsp;: &nbsp;P&A Dept</td>
						 </tr>					
						 <tr>  <td style="border: 0;">Emp. No. <label style="margin-left: 6px;">:</label>&nbsp; <span class="text-blue"><%=obj[1] %></span></td></tr>
						  <tr> 
						  	<td style="border: 0;">Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp; <span class="text-blue"><%=rdf.format(sdf.parse(obj[5].toString())) %></span></td>	 
					   	</tr>	
						  <tr> <td style="border: 0;"></td> </tr>
						  <tr> <td style="border: 0;"></td> </tr>
						   <tr> <td style="border: 0;"></td> </tr>
						    
						
						
						 <tr>  <td style="border: 0;">Exam Name  <label style="margin-left: 17px;">:</label>&nbsp; <span class="text-blue"><%=obj[3] %></span></td></tr>
						  <tr> 
						  	<td style="border: 0;">Probable Date &nbsp;&nbsp;:&nbsp; <span class="text-blue"><%=rdf.format(sdf.parse(obj[4].toString())) %></span></td>	 
					   	</tr>	
						 	
						   <%-- <td style="border: 0;margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 17px;" align="left">
						      This is to inform you that I have <span class="text-blue"><%=obj[3] %> </span> on <span class="text-blue"><%=rdf.format(sdf.parse(obj[4].toString())) %></span>. 
						   </td>  --%>
						
						    <!--  <tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;"> <input type="text" value="" readonly></td> </tr>			
						 	<tr> 
						 	<td style="border: 0;">
						 	  <input type="text" value="" readonly>
						 	 </td> 
						 	 </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="" readonly></td> </tr>	
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;"> </td> </tr>
						 	 -->
						 						       
					    </tbody>
					</table>
					</div>
					<br>
					<br>
					<br>
					<br>	
					<div style="width:100%;text-align: right;margin-left:-100px;">	
						Signature of Employee<br>
				   		
				</div>	
				<% if(obj[7]!=null){ %><div style="width:100%;text-align: right;">Forwarded On : <span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[7].toString().substring(0, 10)) +" "+obj[7].toString().substring(11,19) %></span></div><%} %>									     
				
				<br>
				<br>
				<br>
				
				<% for(Object[] rh:RemarksHistory) {
		 
		 if(rh[4].toString().equalsIgnoreCase("VDG")){%>
		 
		  <div align="left" style="margin-left:17px !important;">Recommended By :<span class="text-blue"><%=rh[2] %></span> </div>
	        <div style="margin-left:-180px !important;" >Recommended On : <span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(rh[3].toString().substring(0, 10)) +" "+rh[3].toString().substring(11,19) %></span>
	
	     <%}} %>
		 
				
				
				<br>
				<br>
				<br>
				<br>
				<br>
				<br>
				<% if(obj[6].toString().equalsIgnoreCase("VPA")){ 
				
				for(Object[] rh:RemarksHistory) {
		 
		 if(rh[4].toString().equalsIgnoreCase("VPA")){%>
		 <div  align="center"style="margin-left:160px;text-align:center;"> 
		                         <span style="font-weight: 600; font-size: 14px;margin-left:0px;">APPROVED</span><br><br>
						        <span style="font-weight: 500; font-size: 14px;margin-left:0px;">P&A InCharge : &nbsp;<span class="text-blue"><%=rh[2] %></span></span><br>
								<span style="font-weight: 400; font-size: 14px; ">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[7].toString().substring(0, 10)) +" "+obj[7].toString().substring(11,19) %></span></span><br>
		</div>	
		<%}}} %>
		
				
				</div>		   			  	
</body>

</html>

