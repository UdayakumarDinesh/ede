<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
    
<!DOCTYPE html>
<html>
<%
Object[] MobFormData = (Object[])request.getAttribute("MobFormData");
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
					content: "Emp No: <%=MobFormData[1] %>";
				
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

</style>
<meta charset="ISO-8859-1">
<title>Mobile Form</title>
</head>
<body>
<%
String LabLogo = (String)request.getAttribute("LabLogo");
String LoginType = (String)session.getAttribute("LoginType");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");
Date date = new Date();

List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");
SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
%>

<div class="center">
             <div style="width: 100%;float:left;">
				<div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
				<div style="margin-left:auto; margin-right:auto;"><h3 ><span style="margin-left: -85px; "> FORM FOR INTIMATION OF CHANGE OF  </span></h3> </div>
				<div style="margin-left:auto; margin-right:auto;margin-top:-10px;"><h3 ><span style="margin-left: -85px; ">MOBILE NUMBER </span></h3> </div>
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
						  <td style="border: 0;width:78%">From <label style="margin-left: 30px;">:</label>&nbsp;<%if(MobFormData!=null && MobFormData[8]!=null){ %> <%=MobFormData[8] %> <%} %>  </td>
						  <td style="border: 0;width:17%;">To &nbsp;: &nbsp;P&A Dept</td>
						 </tr>					
						 <tr>  <td style="border: 0;">Emp. No. <label style="margin-left: 6px;">:</label>&nbsp;<%if(MobFormData!=null && MobFormData[1]!=null){ %> <%=MobFormData[1] %> <%} %></td> </tr>
						  <tr> 
						  	<td style="border: 0;">Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;<%for(Object[] apprInfo : ApprovalEmpData){ %> <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>		<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %> <%break;} %><%} %> </td>	 
					   	</tr>	
						  <tr> <td style="border: 0;"></td> </tr>
						  <tr> <td style="border: 0;"></td> </tr>
						 <tr> 	
						   <td style="border: 0;margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						     This is to inform you that I have changed my mobile number with effect from.&nbsp;
						     <input type="text" value="<%if(MobFormData!=null && MobFormData[4]!=null){ %><%=DateTimeFormatUtil.SqlToRegularDate(MobFormData[4]+"")%> <%} %>" style="width:15%;text-align:center;" readonly>
						     &nbsp; and the present mobile number and the alternate mobile number is as under:
						   </td> 
						 </tr> 
						    <tr> <td style="border: 0;"></td> </tr>
						 								 				
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(MobFormData!=null && MobFormData[2]!=null){ %> <%="Mobile: "+MobFormData[2]%> <%} %> " readonly></td> </tr>	
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(MobFormData!=null && MobFormData[3]!=null){ %> <%="Alt Mobile: "+MobFormData[3]%> <%}%>" readonly></td> </tr>	
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;"> </td> </tr>
						 	<tr> <td style="border: 0;">The same may be recorded in the office records.</td> </tr>	
						 						       
					    </tbody>
					</table>
					<br>	
					<div style="width:100%;text-align: right;margin-left:-5px;">	
						Signature of Employee<br>
						<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<%} %>
				   		<%} %> 
				   		
				   		
					</div>		
					<br>								     
				   <hr style="border:solid 1px;margin-left:20px;">
				   <br><br>
				  	<div style="width: 100%;border: 0;text-align: center;"> <b style="font-size:18px;text-decoration:underline">FOR ADMINISTRATION USE</b> </div>
				    <br>
				  <%--  <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						Intimation of change of address received on  &nbsp;<input type="text" value="<%if(ResFormData!=null && ResFormData[16]!=null){ %> <%=DateTimeFormatUtil.getDateTimeToRegularDate(ResFormData[16].toString()) %> <%}else if(PerFormData!=null && PerFormData[13]!=null){ %> <%=DateTimeFormatUtil.getDateTimeToRegularDate(PerFormData[13].toString()) %> <%} %>" style="width:15%;text-align:center;">&nbsp;. The same has been updated in<br>  the personal records.																		
				   </div> --%>
				   
				    <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						Intimation of change of mobile number received on  &nbsp;<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
				   				<span style="text-decoration: underline;"><%=rdf.format(sdtf.parse(apprInfo[4].toString())) %></span>
				   				
				   			<%
				   				break;
				   			} %>
				   		<%} %> . The same has been updated in<br>  the personal records.																		
				   </div>
				   
				   <br><br>
				   <div style="width:100%;text-align: right;margin-left:-5px;"> </div>	
				   <div style="border:0px;width: 100%; text-align: right;"> 
				   		Incharge-P&A
				   		<br>
				   		<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("VPA")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<%} %>
				   		<%} %> 
				   
				   </div>
				   <br>				   				  
			   </div>			   			  
			
</body>

</html>

