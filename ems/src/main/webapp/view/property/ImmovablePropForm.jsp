<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.vts.ems.property.model.PisImmovableProperty,com.vts.ems.pis.model.Employee" %>    
<%@ page import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%> 
<%@page import="java.util.List,java.util.ArrayList,java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
table{
	align: left;
	width: 100% !important;
	height:100% !important;
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
input{
border-width: 0 0 1px 0;
width:80%;
}
input:focus {
  outline: none;
}
#dashboardcard{
min-height: 100% !important;
max-height: 489px !important;
}
</style>
</head>
<body>
<%
String LabLogo=(String) request.getAttribute("LabLogo");
PisImmovableProperty imm= (PisImmovableProperty)request.getAttribute("ImmPropFormData");
Object[] emp = (Object[])request.getAttribute("EmpData");

String empNo = (String)session.getAttribute("EmpNo");
String CEO = (String)request.getAttribute("CEOEmpNo");
List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");

List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");
List<Object[]> ImmIntimationRemarks = (List<Object[]>)request.getAttribute("ImmIntimationRemarks");
String isApproval = (String)request.getAttribute("isApproval");

SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");

List<String> toUserStatus  = Arrays.asList("INI","RDG","RSO","RPA","RCE");
List<String> adminRemarks  = Arrays.asList("VDG","VSO","VPA","APR");
List<String> finaceSource  = Arrays.asList("Personal savings","Home loan","Hand loan");
int slno=0;
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5 style="width:113%;">Immovable Property - Form</h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item "><a href="PropertyDashBoard.htm">Property</a></li>
					<li class="breadcrumb-item "><a href="AcquiringDisposing.htm">Acquiring / Disposing </a></li>
					<li class="breadcrumb-item active " aria-current="page">Immovable Property</li>
				  </ol>
				</nav>
			</div>			
		</div>
</div> 
<div class="page card dashboard-card" id="dashboardcard">
  <div class="card-body" align="center">
		<div align="center">
		   <% String ses=(String)request.getParameter("result"); 
			  String ses1=(String)request.getParameter("resultfail");
			  if(ses1!=null){ %>
				<div class="alert alert-danger" role="alert"  style="margin-top: 5px;">
					<%=ses1 %>
				</div>
			  <%}if(ses!=null){ %>
				<div class="alert alert-success" role="alert"  style="margin-top: 5px;">
					<%=ses %>
				</div>
			  <%} %>
		 </div>

			<div class="card-body">
				<div class="card" style="padding-top: 0px; margin-top: -15px; width: 100%;">
					<form action="" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="card-body main-card" style="padding-top: 0px; margin-top: -15px;" align="center">
						
                          <div style="width: 10%; height: 75px; border: 0; display: inline-block;margin:2% 0 10px -90%;"><img style="width: 80px; height: 90px; margin: 5px;" align="left" src="data:image/png;base64,<%=LabLogo%>"></div>									
                          <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-6%;margin-left:5%;"><h4 style="text-decoration: underline">Form for giving <%if(imm!=null && "I".equalsIgnoreCase(imm.getPurpose())) {%> intimation <%}else{ %> permission<%} %> for transaction of <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %> Acquiring <%}else {%> Disposing <%} %> of Immovable Property</h4></div>
						  <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-2.5%;"><h4>(Eg. Land / Plot / Flat / House / Building / Shop etc.)</h4></div>	
						  <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-2.5%;margin-left:5%;"><h4>To be submitted at least 30 days before the proposed date of the transaction</h4></div>	
							<table style="border: 0px; width: 100%;">
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Name and Designation</td>
									<td colspan="2" style="color: blue;">
									<%if(emp!=null){ %>
									<%=emp[1]+", "+emp[2] %>
									<%}else{ %>-<%} %>
									</td>
									
								</tr>
								
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Emp.No.</td>
									<td colspan="2" style="color: blue;"> <%if(emp!=null){ %> <%=emp[0] %> <%}else{ %>-<%} %></td>
								</tr>
								
								<tr>
									<td style="border-bottom: 0;width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="border-bottom: 0">Level in the Pay Matrix & Pay</td>
									<td style="width:20%;text-align: center">Level in the Pay Matrix</td>
									<td style="width:20%;text-align: center">Pay</td>
								</tr>
								<tr>
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="width:20%;text-align: center;color: blue;"><%if(emp!=null && emp[3]!=null){ %> <%=emp[3] %> <%}else{ %>-<%} %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%if(emp!=null && emp[3]!=null){ %> <%=emp[4] %> <%}else{ %>-<%} %></td>
								</tr>
								
							    <tr>
								   <td style="border-bottom: 0;width: 5%;text-align: center"><%=++slno%>.</td>
								   <td >Purpose of application </td>
								    <%if(imm!=null && imm.getPurpose()!=null && "I".equalsIgnoreCase(imm.getPurpose())) {%> <td colspan="2" style="color: blue;">(a) Prior Intimation of transaction</td>
								  <%}else{%> 
								  <td colspan="2" style="color: blue;">(b) Prior permission of  transaction</td> 
								  <%}%>
								</tr>
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Whether property is being Acquired or Disposed off ?</td>
									<%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%><td colspan="2" style="color: blue;">Acquired</td>
									<%} else{%><td colspan="2" style="color: blue;">Disposed off</td><%} %>
								</tr>
								
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Date of <%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%> Acquisition <%}else{ %> Disposal <%} %> of property</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getTransDate()!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(imm.getTransDate().toString())%><%}else{ %>-<%} %></td>
									
								</tr>
								
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Mode of <%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%> Acquisition <%}else{ %> Disposal <%} %></td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getMode()!=null) {%> <%=imm.getMode()%><%}else{ %>-<%} %></td>							
								</tr>	
								
								<tr>
									<td style="width: 5%;border-bottom: 0;text-align: center"><%=++slno%>.</td>
									<td>(a) Full details about location viz. Municipal No. Street/Village, Taluk, District and State in which situated</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null) {%> <%=imm.getLocation()+", "+imm.getDistrict()+", "+imm.getState()+" - "+imm.getPincode()%><%} %></td>
								</tr>
								<tr>
								    <td style="border-bottom: 0;border-top: 0;"></td>
								    <td>(b) Description of the property in the case of Cultivable land, dry or irrigated land</td>
								    <td colspan="2" style="color: blue;"><%if(imm!=null && imm.getDescription()!=null) {%> <%=imm.getDescription()%><%} else{%>-<%} %></td>
								</tr>
								<tr>
								    <td style="border-bottom: 0;border-top: 0;"></td>
								    <td>(c) Whether freehold or leasehold ?</td>
								    <%if(imm!=null && imm.getOwnership()!=null && "F".equalsIgnoreCase(imm.getOwnership())) {%><td colspan="2" style="color: blue;">Freehold</td>
								    <%} else{%><td colspan="2" style="color: blue;">Leasehold</td> <%} %>
								</tr>
								 <tr>
								    <td style="border-bottom: 0;border-top: 0;"></td>
								    <td>(d) Whether the applicant&apos;s interest in the property is in full or part (in case of partial interest, the extent of such interest must be indicated)</td>
								    <%if(imm!=null && imm.getApplicantInterest()!=null && "F".equalsIgnoreCase(imm.getApplicantInterest())) {%><td colspan="2" style="color: blue;">Full</td>
								    <%}else if(imm!=null && imm.getApplicantInterest()!=null && "P".equalsIgnoreCase(imm.getApplicantInterest()) ){%><td colspan="2" style="color: blue;"> <%="Part -"+imm.getPartialInterest() %> (In the Name of <%=imm.getExtentInNameOf()%>)</td> <%} %>
								</tr>
								<tr>
								    <td style="border-bottom: 1;border-top: 0;"></td>
								    <td>(e)  In case the transaction is not exclusively in the name of the employee, particulars of ownership and share of each member.</td>
								    <td colspan="2" style="color: blue;"><%if(imm!=null && !imm.getOsParticulars().isEmpty()) {%> <%=imm.getOsParticulars()%> <%} else{%>-<%} %>
								    <%if(imm!=null && !imm.getOsShare().isEmpty()) {%> <%=", "+imm.getOsShare()%> <%} else{%>-<%} %> </td>
								</tr>
								
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Sale/Purchase price of the property (Market value in the case of gifts)</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getPrice()!=null) {%> <%=imm.getPrice()%><%} else{%>-<%} %></td>
								</tr>
								
								<%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%>
								<tr>
								    <td style="width: 5%;border-bottom: 0;text-align: center"><%=++slno%>.</td>
								    <td>In case of  acquisition, source or sources from which financed / proposed to be financed - (Attach supporting documents)</td>
								    <td colspan="2" style="color: blue">							     
								      <% if(imm!=null && imm.getFinanceSource()!=null && !finaceSource.contains(imm.getFinanceSource()) ) {%>
								      <%=imm.getFinanceSource()%>							      
								      <%}else if(imm!=null && imm.getFinanceSource()!=null) {%>
								      <%=imm.getFinanceSource() %>
								      <%} %>
								    </td>
								</tr>
								<%-- <%if(imm!=null && imm.getFinanceSource()!=null && "Personal savings".equalsIgnoreCase(imm.getFinanceSource())) {%>
								<tr>
								    <td style="border-bottom: 1;border-top: 0;"></td>
								    <td>(a) Personal savings</td>
								    <td colspan="2" style="color: blue;">Yes</td>
								</tr>	
								<%}else if(imm!=null && imm.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(imm.getFinanceSource()) ) {%>
								<tr>
								    <td style="border-bottom: 1;border-top: 0;"></td>
								    <td>(b) Other sources give details	</td>
								    <td colspan="2" style="color: blue;"><%=imm.getFinanceSource()%></td>
								 </tr>  --%>
								 <%} else if(imm!=null&& imm.getTransState()!=null && "D".equalsIgnoreCase(imm.getTransState())) {%>
								 <tr>
								    <td style="width: 5%;text-align: center"><%=++slno%>.</td>
								    <td>In case of disposal of the property, was requisite sanction/intimation obtained/given for its acquisition (A copy of the sanction/acknowledgement should be attached)</td>
								    <td colspan="2" style="color: blue"><%if(imm!=null && imm.getRequisiteSanction()!=null && "Y".equalsIgnoreCase(imm.getRequisiteSanction())){ %>Yes<%} else{%>No<%} %></td>
								 </tr>  
								 <%} %>	
								 
								 <tr>
									<td style="width: 5%;border-bottom: 0;text-align: center"><%=++slno%>.</td>
									<td>(a) Name & Address of the party with whom transaction is made / proposed to be made</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getPartyName()!=null) %><%=imm.getPartyName()+"<br>"+imm.getPartyAddress() %></td>
								</tr>
								<tr>
									<td style="width: 5%;border-top:0"></td>
									<td>(b) How the transaction was arranged / to be arranged (whether through any statutory body or a private agency through advertisement or through friends and relatives, full particulars to be given).</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getTransArrangement()!=null) {%> <%=imm.getTransArrangement() %> <%} %></td>
								</tr>
								
								<tr>
									<td style="width: 5%;border-bottom: 0;text-align: center"><%=++slno%>.</td>
									<td>(a) Is the party related to the applicant</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getPartyRelated()!=null && "Y".equalsIgnoreCase(imm.getPartyRelated())){ %>Yes<%} else{%>No<%} %></td>
								</tr>
								<%if(imm!=null && imm.getPartyRelated()!=null && "Y".equalsIgnoreCase(imm.getPartyRelated())){ %>
								<tr>
									<td style="border-bottom: 0;border-top: 0;"></td>
									<td>(b) if so state the relationship</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getRelationship()!=null) %><%=imm.getRelationship()%></td>
								</tr>
								<%} %>
								<tr>
									<td style="border-bottom: 0;border-top: 0;"></td>
									<td>(c) Did the applicant have any dealings with the party in his/her official capacity at any time, or is the applicant likely to have any dealing with the party in the near future?</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getFutureDealings()!=null && "Y".equalsIgnoreCase(imm.getFutureDealings())) {%>Yes<%} else{%>No<%} %></td>
								</tr>
								<tr>
									<td style="border-top: 0;"></td>
									<td>(d) Nature of official dealing with the party  </td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getDealingNature()!=null && !imm.getDealingNature().isEmpty()) {%> <%=imm.getDealingNature() %> <%}else{%>-<%} %></td>
								</tr>
								
								<%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState()) && imm.getMode()!=null && "Gift".equalsIgnoreCase(imm.getMode())) {%>
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>In case of acquisition by gift, whether sanction is also required under SITAR Conduct, Discipline & Appeal Rules.</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getSanctionRequired()!=null && "Y".equalsIgnoreCase(imm.getSanctionRequired())) {%>Yes<%} else{%>No<%} %></td>
								</tr>
								<%} %>
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Any other relevant fact which the applicant may like to mention.</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && !imm.getRelavantFact().isEmpty()) {%> <%=imm.getRelavantFact() %> <%} else{%>-<%} %></td>
								</tr>
							</table>
							
							<!--DECLARATION  -->
							<div style="width: 100%;border: 0;text-align: center;margin-top:5%;"> <b style="font-size:18px;text-decoration:underline">DECLARATION</b> </div>
							<br>
							<%if(imm!=null && "I".equalsIgnoreCase(imm.getPurpose())) {%> 
						    <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						    I, <input type="text" style="width: 20%;color: blue;text-align: center;" value="<%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %>" readonly> hereby intimate the proposed <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %> Acquisition <%}else {%> Disposal <%} %> of Immovable property by me as detailed above. I declare that the particulars given above are true.
						    </div>
						    <%}else{ %>
						      <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						    I, <input type="text" style="width: 20%;color: blue;text-align: center;" value="<%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %>" readonly> hereby declare that the particulars given above are true. I request that I may be given permission to <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %> acquire <%}else {%> dispose <%} %> of property as described above <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %> from <%}else {%> to <%} %> the party whose name is mentioned at Sl. No. 13 above.  
						    </div>
						     <%} %>
						     <br>
						     <div style="margin-left: 10px;font-size: 14px;" align="left">Date&emsp;:&emsp;
						       <label style="color: blue;">
						      <%for(Object[] apprInfo : ApprovalEmpData){ %>
							  <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
					   				<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %>
					   			<%break;
					   			} %>
						   		<%} %>
						   	  </label>
						     </div>
						     <div style="margin-right: 4%;color: blue;" align="right" ><%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %></div>
						     <div style="" align="right">Signature of the employee</div>
						     
						     <!--Remarks of Administration  -->
						     <% if( CEO.equalsIgnoreCase(empNo) || PandAs.contains(empNo) || imm!=null && adminRemarks.contains(imm.getPisStatusCode()) ){ %>	
						     <hr style="border: solid 1px;">				     
						     <div style="width: 100%;border: 0;text-align: center;margin-top:5%;"> <b style="font-size:18px;text-decoration:underline">Remarks of Administration</b> </div>	
						     <br>
						     <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						       1. The details of proposed <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %> acquisition <%}else {%> disposal <%} %> of Immovable property furnished above by <%if(emp!=null && emp[5]!=null) {%><%=emp[5]%><%} %><input type="text" style="width: 20%;color: blue;text-align: center;" value="<%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %>" readonly>, Emp No <input type="text" style="width: 10%;color: blue;text-align: center;" value="<%if(emp!=null && emp[0]!=null){%> <%=emp[0]%><%} %>" readonly> has been perused and filed in <%if(emp!=null && emp[5]!=null && (emp[5].toString().equalsIgnoreCase("Mr.") || (emp[5].toString().equalsIgnoreCase("Dr.") ) ) ) {%>his <%}else {%>her <%} %> service records.
						     </div>
						     <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %>
						     <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 2%;" align="left">
						       2. As per Sl. No. 12 above, the applicant is proposing acquisition of the property with a person having official dealing with the employee / with a foreigner which may be permitted. 
						     </div>	
						     <%} %>	
						      <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 2%;" align="left">Submitted for kind information.</div>
						      
						      <%if(imm!=null && !imm.getImmStatus().equalsIgnoreCase("N")){ %>
					         <br>
				              <div style="width:100%;text-align: left;margin-left: 10px;">
				               <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			    <%if(apprInfo[8].toString().equalsIgnoreCase("VDG")){ %>
				   				Recommended By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %></label><br>
				   				Recommended On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			    <%} %>
				   		        <%} %> 
				             </div>
				             <br>
				             <div style="width:100%;text-align: left;margin-left: 10px;">
				             <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			 <%if(apprInfo[8].toString().equalsIgnoreCase("VSO")){ %>
				   				Recommended By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %></label><br>
				   				Recommended On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			  <%} %>
				   		     <%} %> 
				            </div>
				             			             				          
                       <br> 
					       <%} %>
					       	
						      <div style="border:0px;width: 100%; text-align: right;"> Incharge-P&A 
				              <br>	
				              <br>
				              <label style="color: blue;">
				   		     <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			 <%if(apprInfo[8].toString().equalsIgnoreCase("VPA")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			 <% break;} %>
				   		     <%} %> 
				   		     </label>
				             </div>
				             <%} %>
				             
				             <br>
				             <div class="row">
					  <br>
						<%if(ImmIntimationRemarks.size()>0){ %>
							<div class="col-md-8" align="center" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
								<%if(ImmIntimationRemarks.size()>0){ %>
									<table style="margin: 3px;padding: 0px">
										<tr>
											<td style="border:none;padding: 0px">
												<h6 style="text-decoration: underline;">Remarks :</h6> 
											</td>											
										</tr>
										<%for(Object[] obj : ImmIntimationRemarks){%>
										<tr>
											<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
												<%=obj[3]%>&nbsp; :
												<span style="border:none; color: blue;">	<%=obj[1] %></span>
											</td>
										</tr>
										<%} %>
									</table>
								<%} %>
							</div>
							<%} %>
					   </div>
					          
				             <%			             
				             long diff = DateTimeFormatUtil.dayDifference(imm.getTransDate().toString());
				             
				             if(imm!=null && toUserStatus.contains(imm.getPisStatusCode()) && diff<=30){ %>	
				             <div align="left">
						        <b >Remarks :</b><br>
						        <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					          </div>	           			
				   		      <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ImmovablePropFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							     <i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						      </button>
						      
					        <%} %>
					        <% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>						     
					         <% if( CEO.equalsIgnoreCase(empNo)){ %>
					         <div align="left">
						        <b >Remarks :</b><br>
						        <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					          </div>
					          <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ImmovablePropFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Approve?');" >
							    Approve	
						      </button>
						      						
						      <button type="submit" class="btn btn-sm delete-btn" id="finalSubmission" formaction="ImmovablePropFormSubmit.htm" name="Action" value="D" onclick="return disapprove();" >
							    Disapprove	
						      </button>
						      
					         <%}else if(PandAs.contains(empNo)) {%>
					         <div align="left">
						        <b >Remarks :</b><br>
						        <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					          </div>
				   		       <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ImmovablePropFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							     Verify	
						       </button>
						       
					         <%}else{ %>
					         <div align="left">
						        <b >Remarks :</b><br>
						        <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					          </div>
				   		       <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ImmovablePropFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Recommend?');" >
							     Recommend	
						       </button>
						       
						      <%} %>	
				   		       <button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="ImmovablePropFormSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							    Return
						       </button>	
						    				       							   		  
					  <%} %>
					<%if( CEO.equalsIgnoreCase(empNo)  && (imm!=null && imm.getPisStatusCode().equalsIgnoreCase("VPA"))) {%> 
                       <div class="row" style="margin-top: 5%;">
                         <div class="col-md-12"><b style="color: black"> APPROVED / NOT APPROVED <br>CEO</b><br></div>
                      </div> 
                      <%} %>
                      <div class="row">
                         <div class="col-md-12" style="color: blue;">
                         <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("APR")){ %>
				   			<b style="color: black">APPROVED</b><br>
				   				<%=apprInfo[2]+", "+apprInfo[3] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;
				   			}else if(apprInfo[8].toString().equalsIgnoreCase("DPR")){ %>
				   			<b style="color: black">NOT APPROVED</b><br>
			   				<%=apprInfo[2]+", "+apprInfo[3] %><br>
			   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
			   			<% break;} %>
				   		<%} %> </div>
                      </div>
                      <%--  <%} %>	 --%>     
						</div>
						<input type="hidden" name="immPropertyId" value="<%if(imm!=null){ %><%=imm.getImmPropertyId()%><%}%>">
					</form>
				</div>
			</div>
		</div>
</div>

<script>

function validateTextBox() {
    if (document.getElementById("remarksarea").value.trim() != "") {
    	return confirm('Are You Sure To Return?');
    	
    } else {
        alert("Please enter Remarks to Return");
        return false;
    }
}

function disapprove() {
    if (document.getElementById("remarksarea").value.trim() != "") {
    	return confirm('Are You Sure To Disappove?');
    	
    } else {
        alert("Please enter Remarks to Disapprove");
        return false;
    }
}
</script>

</body>
</html>