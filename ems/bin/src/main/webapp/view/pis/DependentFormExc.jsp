<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">

.break
{
	page-break-after: always;
} 
	
#pageborder
{
      /* position:fixed; */
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      
}     
@page {             
          size: 790px 1120px;
          margin-top: 40px;
          margin-left: 50px;
          margin-right: 40px;
          margin-buttom: 40px; 	
          border: 1px solid black;  
            
          @bottom-right {          		
             content: "Page " counter(page) " of " counter(pages);
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
p
{
	text-align: justify;
  	text-justify: inter-word;
}
p,td,th
{
  word-wrap: break-word;
  word-break: normal ;
}
div
{
	max-width: 680px; 
	margin:0px;
}
th,td
{
	border : 1px solid black;
	padding: 5px;
}
table{
	max-width: 670px;
	border-collapse: collapse;
}

ol > li::marker {
  font-weight: bold;
}

.nobordertd
{
	border: 0px;
	margin: 0px;
	padding : 0px;
}


</style>
<meta charset="ISO-8859-1">
<title>CHSS FORM - 3</title>
</head>

<%

String labcode=(String) session.getAttribute("LabCode");


List<Object[]> ExcMemberDetails = (List<Object[]>)request.getAttribute("ExcMemberDetails");
List<Object[]> FamilymemDropdown = (List<Object[]>)request.getAttribute("FamilymemDropdown");
List<Object[]> relationtypes = (List<Object[]>)request.getAttribute("relationtypes");
Object[] empdetails = (Object[])request.getAttribute("empdetails");
Object[] employeeResAddr = (Object[])request.getAttribute("employeeResAddr");
Object[] formdetails = (Object[])request.getAttribute("formdetails");

String LabLogo = (String)request.getAttribute("LabLogo");
%>

<body >
	
	<div style="width: 100%;">
			<div style="width: 20%; border: 0;float:left;" >
				<img style="width: 80px; height: 90px; margin-left:0px;margin-top: 5px;"   src="data:image/png;base64,<%=LabLogo%>">
			</div>
		</div>	

	<div id="pageborder" align="center" style="max-width:100%;margin-top: -5px;margin-left: 10px;" >
		<!-- <div align="right">
			<h3 style="text-decoration: underline;">CHSS FORM - 3</h3>
		</div> -->
		<div align="center">
			<br>
			<h3>Contributory Health Service Scheme of SITAR Society</h3>
			<span style="text-decoration: underline; font-size: 15px;"><b>Application for exclusion of names of beneficiaries</b></span>
		</div>
		<div align="left">
			<br>
			<h3>UNIT : <%=labcode.toUpperCase() %></h3>
		</div>
		<div align="left">
			<table>
				<tr>
					<th style="width:23% " >Name</th>
					<th style="width:10% " >Emp.No.</th>
					<th style="width:17% " >Designation</th>
					<th style="width: 10% " >Level In Pay Matrix</th>
					<th style="width: 15% " >Basic Pay</th>
				</tr>
				<tr>
						<td><%=empdetails[2] %></td>
						<td><%=empdetails[1] %></td>
						<td><%=empdetails[12] %></td>
						<td><%=empdetails[9] %></td>
						<td><%=empdetails[11] %></td>
					</tr>
			</table>
			Residential Address :
			<%if(employeeResAddr!=null){ %>
						<%=employeeResAddr[1] %>
					<%} %>
			<br>
			<br><br><br>
		</div>
		<div align="left">
			<b style="text-decoration: underline; ">Particulars of family members</b><br>
			<table>
				<tr>
					<th style="width:5% " >SN</th>
					<th style="width:20% " >Name</th>
					<th style="width:12% " >Relationship With the Employee</th>
					<th style="width: 15% " >Effective Date of Exclusion</th>
					<th style="width: 15% " >Reason for Exclusion </th>
				</tr>
				<%int i=0;
					ArrayList<String> addedlist = new ArrayList<String>();
					for(;i<ExcMemberDetails.size();i++)
					{%>
				
				
				<tr >
						
							<td style="text-align: center;" ><%=i+1%></td>
							<td><%=ExcMemberDetails.get(i)[1] %></td>
							<td><%=ExcMemberDetails.get(i)[2] %></td>
							<td><%=DateTimeFormatUtil.SqlToRegularDate(ExcMemberDetails.get(i)[17].toString()) %></td>							
							<td>
								<%if(ExcMemberDetails.get(i)[16]!=null){ %>
									<%=ExcMemberDetails.get(i)[16] %>
								<%}else{ %>
									-
								<%} %>
							</td>
							
					</tr>
				<%} %>
			</table>
						<div class="row" align="left"> 
					<div class="col-md-12">
					<br><br><br><br>
					<table style="float: left;">
						<tr>
							<td class="nobordertd" ><b style="text-decoration: underline;">APPLIED BY</b></td>
						</tr>
						<tr>
							<td class="nobordertd" ><b><%=empdetails[2] %>,</b></td>
						</tr>
						<tr>
							<td class="nobordertd"  > <%=empdetails[12] %></td>
						</tr>
						<tr>
							<td class="nobordertd" >Date: &nbsp; <%if(formdetails!=null && formdetails[4]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(formdetails[4].toString()) %>.<%} %></td>
						</tr>
					</table>
					
					<%if(formdetails!=null && formdetails[3].toString().equals("A")){ %>
					<table style="float: right;">
						<tr >
							<td class="nobordertd" ><b style="text-decoration: underline;" >APPROVED BY</b></td>
						</tr>
						<tr>
							<td class="nobordertd" ><b><%=formdetails[8] %>,</b></td>
						</tr>
						<tr>
							<td class="nobordertd" > <%=formdetails[9] %></td>
						</tr>
						<tr>
							<td class="nobordertd" >Date: &nbsp; <%=DateTimeFormatUtil.SqlToRegularDate(formdetails[6].toString()) %>.</td>
						</tr>
					</table>
					<%} %>
					</div>
				</div>
				<br><br>
		</div>
		<br><br><br><br>
		<div>
			<b>-------------------------------------------------------------------------------------------------------------------------------</b>
			<div align="center"> <h4 style="text-decoration: underline; ">FOR USE IN P&A DEPARTMENT</h4>		</div>
			<div align="left">
				<table style="width:100%; ">
					<tr>
						<td style="border: 0">Excluded with effect from______________________<br><br></td>
					</tr>
					<tr>
						<td style="border: 0"> Date : </td>
					
						<td style="border: 0; text-align: right;">Incharge - P&A</td>
					</tr>
					
				</table>
			</div>			
		</div>
		
	</div>
</body>
</html>

