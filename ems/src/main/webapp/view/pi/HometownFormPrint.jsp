<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
    
<!DOCTYPE html>
<html>
<%
Object[] HomFormData = (Object[])request.getAttribute("HomFormData");
%>
<title>Hometown Form</title>
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
					content: "Emp No: <%=HomFormData[1] %>";
				
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
width:280px;
}
input:focus {
  outline: none;
}

</style>
<meta charset="ISO-8859-1">
<title>Hometown Form</title>
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
				<div style="margin-left:auto; margin-right:auto;"><h3 ><span style="margin-left: -85px; "> DECLARATION OF HOMETOWN </span></h3> </div>
				<div style="margin-left:auto; margin-right:auto;margin-top:-10px;"><h3 ><span style="margin-left: -85px; "> </span></h3> </div>
			</div>	
			<br><br>	        					
					<table style="margin-top: 30px;border:0;">	
					  <tbody>
					    <tr> <td style="border: 0;"> </td> </tr>
					    <tr> <td style="border: 0;"></td> </tr>
					    <tr> <td style="border: 0;"> </td> </tr>
					    <tr> <td style="border: 0;"></td> </tr>
					    <tr> <td style="border: 0;"> </td> </tr>
					    <tr> <td style="border: 0;"></td> </tr>
					    <tr> <td style="border: 0;"> </td> </tr>
						<tr> <td style="border: 0;font-weight:bold;font-size:16px;">To,</td> </tr>	
						<tr> <td style="border: 0;font-weight:bold;font-size:16px;">The Chief Executive Officer</td> </tr>	
						<tr> <td style="border: 0;font-size:16px;">STARC</td> </tr>	
						<tr> <td style="border: 0;font-size:16px;">Bangalore</td> </tr>	
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;text-decoration:underline;">Kind Attn: Incharge - P&A</td> </tr>
						<tr> <td style="border: 0;"> I here by declare my hometown as under for entering the same in personal / official records.</td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;">NAME<label style="margin-left:174px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[8]!=null) {%> <%=HomFormData[8] %> <%} %>" readonly style="color: blue;">  </td> </tr>
						<tr> <td style="border: 0;">EMP. NO.<label style="margin-left:156px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[1]!=null) {%> <%=HomFormData[1] %> <%} %>" readonly style="color: blue;"> </td> </tr>
						<tr> <td style="border: 0;">DESIGNATION<label style="margin-left:121px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[9]!=null) {%> <%=HomFormData[9] %> <%} %>" readonly style="color: blue;"> </td> </tr>
						<tr> <td style="border: 0;">HOMETOWN<label style="margin-left:132px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[2]!=null) {%> <%=HomFormData[2] %> <%} %>" readonly style="color: blue;"> </td> </tr>
						<tr> <td style="border: 0;">STATE<label style="margin-left:172px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[4]!=null) {%> <%=HomFormData[4] %> <%} %>" readonly style="color: blue;"> </td> </tr>
						<tr> <td style="border: 0;">NEAREST RAILWAYSTATION<label style="margin-left:22px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[3]!=null) {%> <%=HomFormData[3] %> <%} %>" readonly style="color: blue;"> </td> </tr>
					    <tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;color: blue;"><%if(HomFormData!=null && HomFormData[8]!=null) {%> <%=HomFormData[8] %> <%} %></td> </tr>
						<tr> <td style="border: 0;"> <b>Signature of Employee</b> </td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"> <b>Date</b>  &nbsp;&nbsp;:&nbsp;&nbsp;
						                            <label style="color: blue;">
						                            <%for(Object[] apprInfo : ApprovalEmpData){ %>
							                        <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
					   			                	<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %>
					   		                    	<%break;
					   		                     	} %>
						   	                    	<%} %></label>
						   	  </td> 
					    </tr>
					  </tbody>					  
					</table>										   			
				   <br>	
				   <div style="border:0px;width: 100%; text-align: right;"> <b>Incharge-P&A </b>
				   <br>	
				   <br><label style="color: blue;">
				   		<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("VPA")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;} %>
				   		<%} %> 
				   		</label>
				   </div>			   				  
			   </div>			   			  
			
</body>

</html>

