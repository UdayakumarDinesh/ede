<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
    <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Print Employee Report</title>
<style type="text/css">
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
  
}
table  thead tr{
background-color: #0e6fb6;
color: white;
}
@page { size: landscape; }

</style>
</head>
<body>
<%
 
Object[] labdetails = (Object[])request.getAttribute("labdetails");


Object[] employeedetails = (Object[]) request.getAttribute("employeedetails");
Object[] Emecdetails = (Object[]) request.getAttribute("emeaddressdetails");
Object[] Nextdetails = (Object[]) request.getAttribute("nextaddressdetails");
List<Object[]> Resdetails  = (List<Object[]>) request.getAttribute("resaddressdetails");
Object[] Perdetails  = (Object[]) request.getAttribute("peraddressdetails");
List<Object[]> familydetails = (List<Object[]>) request.getAttribute("familydetails");
List<Object[]>  appointmentDetailList =(List<Object[]>)request.getAttribute("Appointmentlist");
List<Object[]>  awardsDetailList = (List<Object[]>)request.getAttribute("Awardslist");
List<Object[]>	propertyDetailList = (List<Object[]>)request.getAttribute("Propertylist");
List<Object[]>	publicationDetailList =(List<Object[]>)request.getAttribute("Publicationlist");
List<Object[]>	qualificationDetailList =(List<Object[]>)request.getAttribute("Educationlist");
Object[]   PassportDetails=(Object[])request.getAttribute("PassportList"); 
List<Object[]>  foreignVisitDetails =(List<Object[]>)request.getAttribute("PassportVisitList");
%>
		
	<div class="card"  >
			<div class="card-head">
				<div align="center">
			 <%if(labdetails!=null){%><b style="font-size: 20px;"><%=labdetails[2] %> &nbsp;(<%=labdetails[1]%>)</b><%} %><br>
			    </div>
		    </div>	
			<div class="card-body" style="margin-top: 10px;">
						
				<b>Personal Details Of :-</b> <%=employeedetails[3]%> (<%=employeedetails[22]%>) <br>		
						 <table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Basic Details</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						
						<tr>
							<td> <b>Gender</b> </td>
							<td>
								<%if(employeedetails[9]!=null && employeedetails[9].toString().equalsIgnoreCase("M")){ %>
									Male
								<%}else if(employeedetails[9]!=null && employeedetails[9].toString().equalsIgnoreCase("F")){ %>
									Female
								<%}else{%>--<%}%> 
							</td>
							<td> <b>Group</b> </td>
							<td><%if(employeedetails[25]!=null && employeedetails[26]!=null){%><%=employeedetails[25] %>(<%=employeedetails[26] %>) <%}else{%>--<%}%></td>
							<td> <b>PAN</b> </td>
							<td><%if(employeedetails[13]!=null){ %><%=employeedetails[13].toString().toUpperCase()%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>DOJ</b> </td>
							<td><%if(employeedetails[6]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(employeedetails[6].toString())%><%}else{%>--<%}%></td>
							<td> <b>DOR</b> </td>
							<td><%if(employeedetails[8]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(employeedetails[8].toString())%><%}else{%>--<%}%></td>
							<td> <b>Mobile No</b> </td>
							<td><%if(employeedetails[30]!=null){%><%=employeedetails[30].toString()%><%}else{%>--<%}%></td>
							
						</tr>
						
						<tr>
							<td> <b>Internal Email</b> </td>
							<td><%if(employeedetails[16]!=null){%><%=employeedetails[16]%><%}else{%>--<%}%></td>
							<td> <b>Extension No</b> </td>
							<td><%if(employeedetails[32]!=null){%><%=employeedetails[32]%><%}else{%>--<%}%></td>
							<td> <b>Marital Status</b> </td>
							<td>
								<%if(employeedetails[11]!=null && (employeedetails[11]+"").equalsIgnoreCase("M")){ %>
									Married
								<%}else if(employeedetails[11]!=null && (employeedetails[11]+"").equalsIgnoreCase("U")){ %>
									UnMarried
								<%}else{%>--<%}%> 
							</td>
						</tr>
						
						<tr>
							<td> <b>SBI AccNo.</b> </td>
							<td><%if(employeedetails[20]!=null){%><%=employeedetails[20]%><%}else{%>--<%}%></td>
							<td> <b>Blood Group</b> </td>
							<td style="max-width: 30%;"><%=employeedetails[10] %></td>
							<td> <b>Home Town</b> </td>
							<td><%if(employeedetails[27]!=null){%><%=employeedetails[27]%><%}else{%>--<%}%></td>
						</tr>
					</tbody>
				</table>
				
				<br>
				<!------------------------------ Permanent Address ------------------------------------------------>			
				<b>Permanent Address :-</b><br>
				<%if(Perdetails!=null){ %> 
		 <table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Permanent Address</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						
						<tr>
							<td > <b>Per Address</b> </td>
							<td colspan="3"><%if(Perdetails[6]!=null){%><%=Perdetails[6]%><%}else{%>--<%}%></td>
							<td><b>City</b> </td>
							<td><%if(Perdetails[1]!=null){%><%=Perdetails[1]%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>Mobile</b> </td>
						    <td><%if(Perdetails[5]!=null){%><%=Perdetails[5]%><%}else{%>--<%}%></td>
							<td> <b>State</b> </td>
					    	<td><%if(Perdetails[8]!=null){%><%=Perdetails[8]%><%}else{%>--<%}%></td>
							<td> <b>From_Per_Address</b> </td>
							<td><%if(Perdetails[2]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(Perdetails[2].toString())%><%}else{%>--<%}%></td>
							
						</tr>
						
						<tr>
							<td> <b>Alt_Mobile</b> </td>
							<td><%if(Perdetails[0]!=null){%><%=Perdetails[0]%><%}else{%>--<%}%></td>
							<td> <b>Landline</b> </td>
							<td><%if(Perdetails[4]!=null){%><%=Perdetails[4]%><%}else{%>--<%}%></td>
							<td> <b>Pin</b> </td>
							<td><%if(Perdetails[7]!=null){%><%=Perdetails[7]%><%}else{%>--<%}%></td>
						</tr>
						
					
					</tbody>
				</table>
				
				<%}else{%>
				<table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;" > <b>Permanent Address</b></td>
						</tr>
			        	<tr></tr>
						<tr></tr>
						<tr></tr>
			    <tr><td align="center"> <h5>Permanent Address Not Added!</h5> </td></tr>
				</tbody>
				</table>
				<%} %>
	       <!------------------------------ Permanent Address ------------------------------------------------>			
				 <br>
				
				
				<b>Residential Address :-</b><br>
            <!---------------------------------- Residential Address  ---------------------------------------->
				<%if(Resdetails!=null&&Resdetails.size()!=0 ){ %>
				 <table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Residential  Address</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<%for(Object[] O:Resdetails){ %>
						<tr ><td colspan="4"></td><td></td><td></td>
						</tr>
						<tr>
						<td > <b>Res Address</b> </td>
							<td colspan="3"><%if(O[5]!=null){%><%=O[5]%><%}else{%>--<%}%></td>
							<td><b>City</b> </td>
							<td><%if(O[1]!=null){%><%=O[1]%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>Mobile</b> </td>
							<td><%if(O[4]!=null){%><%=O[4]%><%}else{%>--<%}%></td>
							<td> <b>State</b> </td>
							<td><%if(O[7]!=null){%><%=O[7]%><%}else{%>--<%}%></td>
							<td> <b>From_Res_Address</b> </td>
							<td><%if(O[2]!=null){%> <%=DateTimeFormatUtil.SqlToRegularDate(O[2].toString())%><%}else{%>--<%}%></td>
						</tr>
						
						<tr>
							<td><b>Alt_Mobile</b></td>
							<td><%if(O[0]!=null){%><%=O[0]%><%}else{%>--<%}%></td>
							<td><b>Landline</b></td>
							<td><%if(O[3]!=null){%><%=O[3]%><%}else{%>--<%}%></td>
							<td><b>Pin</b></td>
							<td><%if(O[6]!=null){%><%=O[6]%><%}else{%>--<%}%></td>
						</tr>
						
					<%}%>
					</tbody>
				</table>
				<%}else{ %>
				 <table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Residential  Address</b></td>
						</tr>
			        	<tr></tr>
						<tr></tr>
						<tr></tr>
			    <tr>	<td align="center"> <h5>Residential  Address Not Added!</h5> </td></tr>
				</tbody>
				</table>
				<%}%>
		<!---------------------------------- Residential Address  ------------------------------------->
				 <br>
				<b>Next kin Address :-</b><br>
		<!---------------------------------- Next kin Address  ---------------------------------------->	
				<%if(Nextdetails!=null){ %>
				 <table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Next kin Address</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						
						<tr>
						<td > <b>Next kin Address</b> </td>
							<td colspan="3"><%if(Nextdetails[6]!=null){%><%=Nextdetails[6]%><%}else{%>--<%}%></td>
							<td><b>City</b> </td>
							<td><%if(Nextdetails[1]!=null){%><%=Nextdetails[1]%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>Mobile</b> </td>
							<td><%if(Nextdetails[5]!=null){%><%=Nextdetails[5]%><%}else{%>--<%}%></td>
							<td> <b>State</b> </td>
							<td><%if(Nextdetails[8]!=null){%><%=Nextdetails[8]%><%}else{%>--<%}%></td>
							<td> <b>From_Next_kin_Address</b> </td>
							<td><%if(Nextdetails[2]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(Nextdetails[2].toString())%><%}else{%>--<%}%></td>
						</tr>
						
						<tr>
							<td> <b>Alt_Mobile</b> </td>
							<td><%if(Nextdetails[0]!=null){%><%=Nextdetails[0]%><%}else{%>--<%}%></td>
							<td> <b>Landline</b> </td>
							<td><%if(Nextdetails[4]!=null){%><%=Nextdetails[4]%><%}else{%>--<%}%></td>
							<td> <b>Pin</b> </td>
							<td><%if(Nextdetails[7]!=null){%><%=Nextdetails[7]%><%}else{%>--<%}%></td>
						</tr>		
					</tbody>
				</table>
				<%}else{%>		
				<table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Next kin  Address</b></td>
						</tr>
			        	<tr></tr>
						<tr></tr>
						<tr></tr>
			    <tr>	<td align="center"> <h5>Next kin  Address Not Added!</h5> </td></tr>
				</tbody>
				</table>				
				<%}%>
			<!---------------------------------- Next kin Address  ---------------------------------------->	
			 <br>
			<b>Emergency Address :-</b><br>
			<!---------------------------------- Emergency Address  ---------------------------------------->	
				<%if(Emecdetails!=null){ %>
				<table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Emergency Address</b></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						
							<tr>
						<td > <b>Emergency Address</b> </td>
							<td colspan="3"><%if(Emecdetails[6]!=null){%><%=Emecdetails[6]%><%}else{%>--<%}%></td>
							<td><b>City</b> </td>
							<td><%if(Emecdetails[1]!=null){%><%=Emecdetails[1]%><%}else{%>--<%}%></td>
						</tr>
						
						
						<tr>
							<td> <b>Mobile</b> </td>
							<td><%if(Emecdetails[5]!=null){%><%=Emecdetails[5]%><%}else{%>--<%}%></td>
							<td> <b>State</b> </td>
							<td><%if(Emecdetails[8]!=null){%><%=Emecdetails[8]%><%}else{%>--<%}%></td>
							<td> <b>From_Emec_Address</b> </td>
							<td><%if(Emecdetails[2]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(Emecdetails[2].toString())%><%}else{%>--<%}%></td>
						</tr>
						
						<tr>
							<td> <b>Alt_Mobile</b> </td>
							<td><%if(Emecdetails[0]!=null){%><%=Emecdetails[0]%><%}else{%>--<%}%></td>
							<td> <b>Landline</b> </td>
						    <td><%if(Emecdetails[4]!=null){%><%=Emecdetails[4]%><%}else{%>--<%}%></td>
							<td> <b>Pin</b> </td>
							<td><%if(Emecdetails[7]!=null){%><%=Emecdetails[7]%><%}else{%>--<%}%></td>
						</tr>	
						
					
					</tbody>
				</table>
				<%}else{ %>				
				<table class="table table-striped table-bordered" style="width: 100%;">
					<tbody>
						<tr>
							<td colspan="6" rowspan="3" style="background-color: #0e6fb6; color: white;"> <b>Emergency Address</b></td>
						</tr>
			        	<tr></tr>
						<tr></tr>
						<tr></tr>
			    <tr><td align="center"> <h5>Emergency Address Not Added!</h5> </td></tr>
				</tbody>
				</table>
				<%}%>
	<!---------------------------------- Emergency Address  ---------------------------------------->			
					 <br><br>	
					 
			<b>Appointment Detail List :-</b><br>		 
	<!---------------------------------- Appointment Detail List ---------------------------------------->	
		<table class="table table-striped table-bordered" style="width: 100%;">
					<thead>
						<tr>
						 <th>Designation</th>
						 <th>Org/Lab</th>
						 <th>DRDO/Others</th>
						 <th>From Date</th>
						 <th>To Date</th>
						</tr>
					</thead>
					<tbody>
					<%if(appointmentDetailList!=null&&appointmentDetailList.size()>0){
					for(Object[] obj:appointmentDetailList){ %>
					<tr>
			                                 <td> <%=obj[7]%> </td>
			                                 <td> <%=obj[2]%> </td>
			                                 <td> <%=obj[5]%> </td>
			                                 <td align="center"> <%if(obj[8]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[8].toString())%><%} %> </td>
			                                 <td align="center"> <%if(obj[9]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[9].toString())%><%} %> </td>
					</tr>
					<%}}else{%>
					<tr>
					    <td colspan="5" align="center"> <h5>Appointment Details Not Added!</h5></td>
					</tr>
					<%}%>
					</tbody>

				</table>
  <br>
 
           <b>Award Detail List :-</b><br>	
 <!---------------------------------- Award Detail List ---------------------------------------->	
		<table class="table table-striped table-bordered" style="width: 100%;">
					<thead>
					<tr>
						<th>Award Name</th>
						<th>Award By</th>
						<th>Details</th>
						<th>Award Date</th>
						<th>Certificate</th>
						<th>Citation</th>
						<th>Medallian</th>
						<th>Award Category</th>
						<th>Cash Ammount</th>
						</tr>
					</thead>
				
					<tbody>
					<%if(awardsDetailList!=null&&awardsDetailList.size()>0){
					  for(Object[] ls:awardsDetailList){%>
					  <tr>
						<td><%=ls[2]%><%if("7".equalsIgnoreCase(ls[9].toString())){%><%=" / "+ls[0]%><%}%></td>
						<td><%=ls[3]%></td>
						<td><%=ls[4]%></td>
						<td><%if(ls[5]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[5].toString()));}%></td>
						<td><%=ls[7]%></td>
						<td><%=ls[9]%></td>
						<td><%=ls[11]%></td>
						<td><%=ls[12]%></td>
						<td><%=ls[15]%></td>
					  </tr>
					<%}}else{%>
					<tr>
						<td colspan="9" align="center"> <h5>Awards Details Not Added!</h5> </td>
					</tr>
					<%}%>	
					</tbody>
              </table> 
 <br>
             <b>Property Detail List :-</b><br>
 <!---------------------------------- Property Detail List ---------------------------------------->	
		<table class="table table-striped table-bordered" style="width: 100%;">
					<thead><tr>
						<th>Movable</th>
						<th>value</th>
						<th>Details</th>
						<th>DOP</th>
						<th>Acquired Type</th>
						<th>Noting On</th>
						<th>Remarks</th>
					</tr></thead>
					<tbody>
					  <%if(propertyDetailList!=null&&propertyDetailList.size()>0){
					   for(Object[] ls:propertyDetailList){%>
					   <tr>
						<td><%if("N".equalsIgnoreCase(ls[2].toString())){%><a class="btn btn-primary btn-xs btn-outline" href="print?PropertyID=<%=ls[8]%>" target="blank">Immovable</a><%}else{%>Movable<%}%></td>
						<td><%=ls[3]%></td>
						<td><%=ls[4]%></td>
						<td align="center"><%if(ls[5]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[5].toString()));}%></td>
						<td><%=ls[6]%></td>
						<td align="center"><%if(ls[7]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[7].toString()));}%></td>
						<td><%=ls[8]%></td>

					  </tr>
					<%}}else{%>
					<tr>
						<td colspan="7" align="center"> <h5>Property Details Not Added!</h5> </td>
					</tr>
					<%}%>	
					</tbody>
              </table>
	 <br>
	 
	 <b> Publication Detail List :-</b><br>
	<!---------------------------------- Publication Detail List ---------------------------------------->	
		 <table class="table table-striped table-bordered" style="width: 100%;">
					<thead>
					<tr>
						<th>Publication Type</th>
						<th>Authors</th>
						<th>Discipline</th>
						<th>Title</th>
						<th>Publication Name</th>
						<th>Publication Date</th>
						<th>Patent No</th>
                     </tr>
					</thead>
					<tbody>
						<%if(publicationDetailList!=null&&publicationDetailList.size()>0){
						for(Object[] ls:publicationDetailList){%>
					  <tr>
						<td><%=ls[0]%></td>
						<td><%=ls[1]%></td>
						<td><%=ls[2]%></td>
						<td><%=ls[3]%></td>
						<td><%=ls[8]%></td>
						<td align="center"><%if(ls[7]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[7].toString()));}%></td>
						<td><%=ls[9]%></td>
					  </tr>
					<%}}else{%>
					<tr>
						<td colspan="7" align="center"> <h5>Publication Details Not Added!</h5> </td>
					</tr>
					<%}%>		
					</tbody>
                 </table>
	 <br>
	 
	 <b> Qualification Detail List :-</b><br>
		<!---------------------------------- Qualification Detail List ---------------------------------------->	
		<table class="table table-striped table-bordered" style="width: 100%;">
					<thead>
						<tr>
						<th>Quali Title</th>
						<th>Disci Title</th>
						<th>University</th>
						<th>Year Of Passing</th>
						<th>CGPA</th>
						<th>Specialization</th>
						<th>Sponsored</th>
						<th>Acq_Bef_Aft</th>
						
						</tr>
					</thead>
					<tbody>
						<%if(qualificationDetailList!=null&&qualificationDetailList.size()>0){
						for(Object[] ls:qualificationDetailList){%>
					  <tr>
							<td><%=ls[2]%></td>
							<td><%=ls[3]%></td>
							<td><%=ls[4]%></td>
							<td><%=ls[5]%></td>
							<td><%=ls[6]%></td>
							<td><%=ls[8]%></td>
							<td><%=ls[7]%></td>
							<td><%if(ls[9]!=null && "Y".equalsIgnoreCase(ls[9].toString())){%> Yes <%}else{%>  No <%}%></td>
						  
					  </tr>
					<%}}else{%>
					<tr>
						<td colspan="8" align="center"> <h5>Qualification Details Not Added!</h5> </td>
					</tr>
					<%}%>	
					</tbody>
                </table>
	
	 <br>
	 <b> Passport Detail List :-</b><br>
			<!---------------------------------- Passport Detail List ---------------------------------------->
		<table class="table table-striped table-bordered" style="width: 100%;">

				<thead>
					 <tr>	
						<th>Passport Type</th>
						<th>Valid From</th>
						<th>Valid To</th>
					    <th>Passport No</th>
					 </tr>	
				   </thead>
				   <tbody>
				    <%if(PassportDetails!=null){%>
				    <tr>
						<td> <%=PassportDetails[1]%></td>
						<td align="center"> <%if(PassportDetails[2]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(PassportDetails[2].toString()));}%></td>
					    <td align="center"> <%if(PassportDetails[3]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(PassportDetails[3].toString()));}%></td>
						<td align="center"> <%=PassportDetails[4]%></td>
					</tr>
					<%}else{%>
					<tr>
						<td colspan="4" align="center"> <h5>Passport Details Not Added!</h5> </td>
					</tr>
					<%}%>	
				</tbody>
			</table>
<br>
		<b> Foreign Visit Detail List :-</b><br>
<!---------------------------------- Foreign Visit Detail List ---------------------------------------->
				<table class="table table-striped table-bordered" style="width: 100%;">

				<thead>
					<tr>	
						<th>Country Name</th>
						<th>Visit From</th>
						<th>Visit To</th>
						<th>Purpose</th>
						<th>NOC Letter No</th>
				   </tr>	
				   </thead>
				   <tbody>
					<%if(foreignVisitDetails!=null&&foreignVisitDetails.size()>0){
					for(Object[] ls:foreignVisitDetails){%>
					<tr>
						<td><%=ls[2]%></td>
						<td align="center"><%if(ls[6]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[6].toString()));}%></td>
						<td align="center"><%if(ls[7]!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(ls[7].toString()));}%></td>
						<td><%=ls[5]%></td>
						<td align="center"><%=ls[3]%></td>
					</tr>
				  <%}}else{%>
				    <tr>
						<td colspan="5" align="center"> <h5>Foreign Visit Details Not Added!</h5> </td>
					</tr>
					<%}%>	
				</tbody>
			</table>		
		  
		   </div>
	</div>	
</body>
</html>