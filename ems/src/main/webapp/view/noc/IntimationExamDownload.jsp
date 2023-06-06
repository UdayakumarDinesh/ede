<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.util.Arrays"%>
    
<!DOCTYPE html>
<html>

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
	 List<Object[]> IntimationApprovalData=(List<Object[]>)request.getAttribute("IntimationApprovalData");
	 List<String> RecommendStatus = Arrays.asList("VDG","VSO");
	
	 int slno=0;
%>

<%-- <div class="center">
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
						 	
						   <td style="border: 0;margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 17px;" align="left">
						      This is to inform you that I have <span class="text-blue"><%=obj[3] %> </span> on <span class="text-blue"><%=rdf.format(sdf.parse(obj[4].toString())) %></span>. 
						   </td> 
						
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
					</div> --%>
					
					 <div class="center">

				 <div style="width: 100%;float:left;">
		         <div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 60px; height: 70px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
                 <div style="width: 90%; height: 75px; border: 0; text-align: center;"><h3 style="text-align:center">INTIMATION FOR EXAM </h3></div>
		          </div>
						 
							<table style="border: 0px;margin-top:-10px; width: 100%;">
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Name and Designation</td>
									<td colspan="2" style="color: blue;"><%=obj[3] %>&nbsp;&nbsp; <%=obj[4] %>
									</td>
									
								</tr>
								
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Emp.No</td> 
									
									<td colspan="2" style="color: blue;"><%=obj[2] %></td>
								</tr>
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Exam Name </td>
								 	<td colspan="2"style="color: blue;"><%=obj[13] %></td>
								</tr> 
								
								<tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Probable Date</td>
								 	<td colspan="2"style="color: blue;"><%=rdf.format(sdf.parse(obj[14].toString())) %></td>
								</tr> 
								
								 <tr>
									<td style="border-bottom: 0;width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="border-bottom: 0;width: 5%;text-align: left">Advertisement Number & Date</td>
									<td style="width:20%;text-align: center">Advertisement No</td>
									<td style="width:20%;text-align: center">Advertisement Date</td>
									
								
								</tr>
								<tr>
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[5] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=rdf.format(sdf.parse(obj[6].toString())) %></td>
								
								   
								</tr> 
								
								
								  
								 <tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td>Organization Name </td>
								   <td colspan="2" style="color: blue;"><%=obj[7] %></td>
								 </tr>
								  
								<tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td>Place </td>
								   <td colspan="2" style="color: blue;"><%=obj[8] %></td>
								 </tr>
								 
								 <tr>
								 
									<td style="border-bottom: 0;width: 5%;text-align: center"><%=++slno%>.</td>
									
									<td style="width:20%;text-align: center">Name of the Post</td>
									<td style="width:20%;text-align: center">Post Code / Post No.</td>
									<td style="width:20%;text-align: center">PayLevel</td>
								 	
								</tr> 
								
								 <tr>
								 
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[9] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[10] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[11] %></td>
									
								</tr> 
								
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Application Through  </td>
								 	<td colspan="2"style="color: blue;"><%=obj[12] %></td>
								</tr> 
								
							</table>
					</div>	
			
				    <br>
					<br>
					<br>
						
					<div style="width:100%;text-align: right;margin-left:-100px;">	
						Signature of Employee<br>
				   	</div>	
				
				<% if(obj[18]!=null){ %><div style="width:100%;text-align: right;">Forwarded On : <span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[18].toString().substring(0, 10)) +" "+obj[18].toString().substring(11,19) %></span></div><%} %>									     
				
				<br>
				
				   <% 
	 
	 for(Object[] ad :IntimationApprovalData) {
		 
		if( RecommendStatus.contains(ad[8].toString()) ){%>
				
			
		
		<div align="left" style="margin-left:7px !important;">Recommended By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
	    <div align="left" style="margin-left:7px !important;">Recommended On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
	    <br>
	 
	   <%}
			else if(ad[8].toString().equalsIgnoreCase("VPA")){%>
		
				<div align="left" style="margin-left:420px !important;">Verified By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
			    <div align="left" style="margin-left:420px !important;">Verified On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
		
		
	<% }} %> 
		
			
		
	
	 

				
				
		 
				
				
				
				
		
				
					   			  	
</body>

</html>

