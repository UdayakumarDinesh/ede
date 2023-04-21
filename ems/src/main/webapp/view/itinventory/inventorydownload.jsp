<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%> 
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
 <% 
 List<Object[]> itinventoryqty=( List<Object[]>)request.getAttribute("inventoryqty");
 %>
<head>
<meta charset="ISO-8859-1">

<title>Inventory Details</title>
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

<% for(Object[] itqty:itinventoryqty){%>
				@top-right {
					content: " <%=itqty[34].toString().substring(0,4) %>";
					margin-top: 30px;
					margin-right: 10px;
				}

				@top-left {
					margin-top: 30px;
					margin-left: 10px;
					content: "Emp No: <%=itqty[2]%>";
				}
<%}%>
				@top-center {
					margin-top: 30px;
					content: "";

				}

				@bottom-center {
					margin-bottom: 30px;
					content: "";
				}
				
				
		}



table{
	align: left;
	width: 620px !important;
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
	
}
tr.noBorder td {
  border: 0;
}

.center{

	text-align: center;
}

.right
{
	text-align: right;
}
.btn-history
{
	float: right;
	background-color: #FFD24C;
}
.text-blue
{
	color: blue;
	font-weight:400px;
}

.break
	 {
	  	page-break-before:always;
	 }
 
 .float{
	position:fixed;
	width:50px;
	height:50px;
	bottom:40px;
	right:40px;
	color:#FFF;
	border-radius:50px;
	text-align:center;
	box-shadow: 2px 2px 3px #999;
}



</style>


</head>
<body>

<%	
    SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
    
    String LabLogo = (String)request.getAttribute("LabLogo");
    List<Object[]> inventoryquantity=( List<Object[]>)request.getAttribute("inventoryqty");
    List<Object[]> inventoryconfigure=(List<Object[]>)request.getAttribute("inventoryconfigure");
    

%>



	 <div class="page card dashboard-card"> 
		<div class="card-body"  >
			<div class="card" style="padding-top:0px;margin-top: -15px;">
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"  align="center">
				<form action="##" id="forward">
				<div class="" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
				
								<div align="center">
								  <div style="width: 100%;">
									<div style="width: 100%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
									<div ><h3 ><span style="margin-left: -80px;font-weight: 600; ">INFORMATION TECHNOLOGY(IT) DEPARTMENT</span></h3></div>
									<div ><span style="margin-left: -90px;font-weight: 600">INVENTORY DECLARATION FORM</span></div>
									<div ><span style="margin-left: -88px;">(To be submitted to IT Department)</span></div>
								  </div>
								</div>
								<%for(Object[] inventoryqty:inventoryquantity ){ %>
								<%if(inventoryqty[32]!=null){ %>
							 <div style="margin-left:450px;"><span style="border: 0px;font-weight: 600">Date :&nbsp;<span class="text-blue" ><%=rdf.format(sdf.parse(inventoryqty[32].toString())) %></span> </span>
								</div>
								<%} %>
								 
								<table style="margin-top: 5px;">	
									<tbody>
										
										<tr>
											<th>Name</th>
											<th>Emp No</th>
											<th>Dept</th>
											<th>Designation</th>
										</tr>
									<tr>
										
											<td class="text-blue" style="text-transform: uppercase;"><%=inventoryqty[1]%></td>
											<td class="text-blue" ><%=inventoryqty[2]%></td>
											<td class="text-blue" ><%=inventoryqty[3]%></td>
											<td class="text-blue" ><%=inventoryqty[4]%></td>
									</tr>
										
									</tbody>
								</table>
								
						<div class="row">
								<div class="col-md-12" align="center">
								<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;">Inventory Details</span>
							</div>
						
							
				<table>
						<tr>
							<th style="width:5%;text-align:center;">SN</th>
							<th colspan="5" style="width:20%;" >Items</th>
							<th style="width:5%;text-align:center;">Qty</th>
							<th style="width:15%;">Intended By</th>
							<th style="width:50%;">Remark</th>
										
						</tr>
						
						<% int count=0; %>
						<% if(!inventoryqty[5].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Desktop/Computer</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[5]%></td>
								    <td class="text-blue" style="width: 15%;"><%if(inventoryqty[6]!=null){%><%=inventoryqty[6]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[7].toString().equalsIgnoreCase("")){%><%=inventoryqty[7]%><%}  else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[8].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Laptop</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[8]%></td>
								    <td class="text-blue" ><%if(inventoryqty[9]!=null){%><%=inventoryqty[9]%><%} else{ %>-<%} %></td>
									<td class="text-blue"style="width: 2%"><%if(!inventoryqty[10].toString().equalsIgnoreCase("")){%><%=inventoryqty[10]%><%}  else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[11].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >USB Pendrive</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[11]%></td>
								    <td  class="text-blue" ><%if(inventoryqty[12]!=null){%><%=inventoryqty[12]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[13].toString().equalsIgnoreCase("")){%><%=inventoryqty[13]%><%}  else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[14].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Printer</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[14]%></td>
								    <td class="text-blue" ><%if(inventoryqty[15]!=null){%><%=inventoryqty[15]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[16].toString().equalsIgnoreCase("")){%><%=inventoryqty[16]%><%}  else{ %>--<%} %></td>
								
						</tr>
						<%} %>
					<%if(!inventoryqty[17].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Telephone</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[17]%></td>
								    <td class="text-blue" ><%if(inventoryqty[18]!=null){%><%=inventoryqty[18]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[19].toString().equalsIgnoreCase("")){%><%=inventoryqty[19]%><%} else{ %>--<%} %></td>
						
						</tr>
						<%} %>
						<%if(!inventoryqty[20].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Fax Machine</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[20]%></td>
								    <td class="text-blue" ><%if(inventoryqty[21]!=null){%><%=inventoryqty[21]%><%}  else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[22].toString().equalsIgnoreCase("")){%><%=inventoryqty[22]%><%} else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[23].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Scanner</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[23]%></td>
								    <td class="text-blue" ><%if(inventoryqty[24]!=null){%><%=inventoryqty[24]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[25].toString().equalsIgnoreCase("")){%><%=inventoryqty[25]%><%} else{ %>--<%} %></td>
								
						</tr>
						<%} %>
						<%if(!inventoryqty[26].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Xerox machine</td>
								    <td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[26]%></td>
								    <td class="text-blue" ><%if(inventoryqty[27]!=null){%><%=inventoryqty[27]%><%} else{ %>-<%} %></td>
									<td class="text-blue" style="width: 2%"><%if(!inventoryqty[28].toString().equalsIgnoreCase("")){%><%=inventoryqty[28]%><%} else{ %>--<%} %></td>
						</tr>
						<%} %>
						<%if(!inventoryqty[29].toString().equals("0")) {%>
						<tr>
						            <td style="width: 2%; text-align: center;"><%=++count %></td>
									<td colspan="5"  style="width: 2%" >Miscellaneous </td>
									<td class="text-blue" style="width: 2%;text-align: center;"><%=inventoryqty[29]%></td>
									<td class="text-blue" ><%=inventoryqty[30]%></td>
								    <td class="text-blue" style="width: 2%"><%if(!inventoryqty[31].toString().equalsIgnoreCase("")){%><%=inventoryqty[31]%><%} else{ %>--<%} %></td>
						</tr>
						<%} %>
					</table>
					</div>
					
					<%if (inventoryqty[33].toString().equalsIgnoreCase("A")){ %>
					      <div style="margin-left:0px !important;margin-top:20px;text-align:left;"> 
						        <span style="font-weight: 400; font-size: 16px;">Forwarded By:&nbsp;<span class="text-blue" ><%=inventoryqty[1]%></span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Forwarded On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(inventoryqty[36].toString().substring(0, 10)) +" "+inventoryqty[36].toString().substring(11,19) %></span></span><br>
						 </div>
						 <div style="margin-left:0px !important;margin-top:20px;text-align:left;"> 
						        <span style="font-weight: 400; font-size: 16px;">Approved By:&nbsp;<span class="text-blue" ><%=inventoryqty[37]%> </span></span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(inventoryqty[32].toString().substring(0, 10)) +" "+inventoryqty[32].toString().substring(11,19) %></span></span><br>
						 </div>
					<%} %>
					       <div style="margin-left:450px !important;margin-top:20px;text-align:left;"> 
						        <span style="font-weight: 600; font-size: 18px;">Signature </span><br>
								<span style="font-weight: 600; font-size: 18px; ">Name:&nbsp;<span class="text-blue" ><%=inventoryqty[1]%></span></span><br>
								<span style="font-weight: 600; font-size: 18px;">EmpNo:&nbsp;<span class="text-blue" ><%=inventoryqty[2]%></span></span>
				          </div>
								
					 <%if(inventoryconfigure.size()>0) {%>
						 <h1 class="break"></h1>
					 <%} %>  
					      
					       <div class="row">
							<%if(inventoryconfigure.size()>0) {%>
							<div class="col-md-12" align="center">
								<span style="font-weight: 600; font-size: 20px;color: #CA4E79;text-decoration: underline;">Inventory Configuration </span>
								 <%} %> 
							 </div>
							<table >
								
								 <% String LAN="LAN";
						            String Internet="INTERNET";
						            String StandAlone="STAND ALONE";
						            String Windows="Windows";
						            String Linux="Linux";
				                    String Computer="Desktop";
						            String Laptop="Laptop";
						            String Yes="YES";
						            String No="NO";
						            int t=0; 
						            for(Object[] Config:inventoryconfigure) {
						            	
						         %>
						       <tr>
									<td colspan="2">
										<b >Connection Type :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;" ><%if(Config[2].toString().equals("L")){%><%=LAN %><%}else if(Config[2].toString().equals("I")){%><%=Internet %><%} else{%><%=StandAlone%><%} %></span></b>
									</td>
									<td   colspan="3">
										<b>CPU :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;" ><%=Config[3] %></span></b>
								</tr>
								 
								<tr>
								
								  <td colspan="2" >
										<b>Monitor :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;" ><%=Config[4] %></span></b>
									</td>
									 <td colspan="1" >
										<b>Keyboard  :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;" ><%=Config[7] %></span></b>
									</td>
									  <td colspan="2" >
										<b>RAM :&nbsp;&nbsp;<span class="text-blue" style="text-transform: lowercase;font-weight: 400;" ><%=Config[5] %></span></b>
									</td>	 
								</tr>
								
								<tr>
								     <td colspan="0">
										<b>Additional RAM :&nbsp;&nbsp;<span class="text-blue" style="text-transform: lowercase;font-weight: 400;"><%=Config[6] %></span></b>
									</td> 
									 <td colspan="2" >
										<b>Mouse :&nbsp;&nbsp;<span class="text-blue" style="font-weight: 400;"  ><%=Config[8] %></span></b>
									</td> 
									
									<td  colspan="2">
										<b>External harddisk  :&nbsp;&nbsp;<span class="text-blue" style="text-transform: lowercase;font-weight: 400;"><%=Config[9] %></span></b>
									</td>
									
								</tr>
								<tr>
									 <td >
										<b>Extra Internal harddisk :&nbsp;&nbsp;<span class="text-blue" style="text-transform: lowercase;font-weight: 400;" ><%=Config[10] %></span></b>
									</td>
									<td colspan="3">
										<b >Office :&nbsp;&nbsp; <span class="text-blue" style="text-transform: lowercase;font-weight: 400;" ><%=Config[11] %></span></b>
										
									</td> 
									<td colspan="1">
										<b>OS:&nbsp;&nbsp;<span class="text-blue" style="text-transform: lowercase;font-weight: 400;" ><%if(Config[12].toString().equals("W")){%><%=Windows %><%}else{ %><%=Linux %><%} %></span></b>
									</td>
								</tr>
								
								<tr>
									 <td colspan="1">
										<b>Browser:&nbsp;&nbsp; <span class="text-blue" style="font-weight: 400;" ><%=Config[14] %></span></b>
										
									</td>
									<td  colspan="3">
										<b>PDF:&nbsp;&nbsp; <span class="text-blue" style="font-weight: 400;" ><%if(Config[13].toString().equals("Y")){ %><%=Yes%><%}else{ %><%=No %><%} %></span></b>
										 
									</td>
									<td  colspan="1">
										<b>Kavach:&nbsp;&nbsp; <span class="text-blue" style="font-weight: 400;"  ><%if(Config[15].toString().equals("Y")){ %><%=Yes%><%}else{ %><%=No %><%} %></span></b>
										 
									</td> 
									
								</tr>
								<tr class="noBorder"><td colspan="5">&nbsp;</td></tr>
								
								<% } %>
								
							</table>
						
					</div>
					<%if(inventoryconfigure.size()>0) {%>
					
					 
					      <div style="margin-left:0px !important;margin-top:20px;text-align:left;"> 
						        <span style="font-weight: 400; font-size: 16px;">Forwarded By:&nbsp;<span class="text-blue" ><%=inventoryqty[1]%></span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Forwarded On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(inventoryqty[36].toString().substring(0, 10)) +" "+inventoryqty[36].toString().substring(11,19) %></span></span><br>
						 </div>
						 <div style="margin-left:0px !important;margin-top:20px;text-align:left;"> 
						        <span style="font-weight: 400; font-size: 16px;">Approved By:&nbsp;<span class="text-blue" ><%=inventoryqty[37]%> </span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(inventoryqty[32].toString().substring(0, 10)) +" "+inventoryqty[32].toString().substring(11,19) %></span></span><br>
						 </div>
				
					<div style="margin-left:450px !important;margin-top:20px;text-align:left;"> 
						        <span style="font-weight: 600; font-size: 18px;">Signature </span><br>
								<span style="font-weight: 600; font-size: 18px; ">Name:&nbsp;<span class="text-blue" ><%=inventoryqty[1]%></span></span><br>
								<span style="font-weight: 600; font-size: 18px;">EmpNo:&nbsp;<span class="text-blue" ><%=inventoryqty[2]%></span></span>
				 </div>
				 <%} %>
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
			    </div>
			<%} %>
				</form>
			
			</div>		
			
		</div>
	
	 </div>
	  </div> 

	
</body>



</html>