<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List,java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%> 
<%@ page import="com.vts.ems.property.model.PisImmovableProperty" %>    
<!DOCTYPE html>
<html>
<%
Object[] emp = (Object[])request.getAttribute("EmpData");
%>
<title>Immovable Property Form</title>
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
				size: 790px 1050px;
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
					content: "Emp No: <%=emp[0] %>";
				
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
<title>Immovable Property Form</title>
</head>
<body>
<%
PisImmovableProperty imm= (PisImmovableProperty)request.getAttribute("ImmPropFormData");
List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");
String LabLogo = (String)request.getAttribute("LabLogo");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
int slno=0;
%>

      <div class="center">
	        <div style="width: 100%;float:left;">
		       <div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
               <div style="width: 90%; height: 75px; border: 0; text-align: center;"><h4 style="">Form for giving <%if(imm!=null && "I".equalsIgnoreCase(imm.getPurpose())) {%> intimation <%}else{ %> permission<%} %> for transaction of <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %> Acquiring <%}else {%> Disposing <%} %> of Immovable Property</h4></div>
		      <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top: -70px;"><h4>(Eg. Land / Plot / Flat / House / Building / Shop etc.)</h4></div>	
		<!-- <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-4%;margin-left:5%;"><h4>To be submitted at least 30 days before the proposed date of the transaction</h4></div> -->	
	        </div>
	                <br><br>
	                       <table style="border: 0px;margin-top: 20px; width: 100%;">
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Name and Designation</td>
									<td colspan="2" style="color: blue;">
									<%if(emp!=null){ %>
									<%=emp[1]+", "+emp[2] %>
									<%}else{ %>-<%} %>
									</td>
									
								</tr>
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Emp.No.</td>
									<td colspan="2" style="color: blue;"> <%if(emp!=null){ %> <%=emp[0] %> <%}else{ %>-<%} %></td>
								</tr>
								
								<tr>
									<td style="border-bottom: 0;width: 5%;"><%=++slno%>.</td>
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
								   <td style="border-bottom: 0;width: 5%;"><%=++slno%>.</td>
								   <td>Purpose of application </td>
								    <%if(imm!=null && imm.getPurpose()!=null && "I".equalsIgnoreCase(imm.getPurpose())) {%> <td colspan="2" style="color: blue;">(a) Prior Intimation of transaction</td>
								  <%}else{%> 
								  <td colspan="2" style="color: blue;">(b) Prior permission of  transaction </td> 
								  <%}%>
								</tr>
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Whether property is being Acquired or Disposed off ?</td>
									<%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%><td colspan="2" style="color: blue;">Acquired</td>
									<%} else{%><td colspan="2" style="color: blue;">Disposed off</td><%} %>
								</tr>
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Date of <%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%> Acquisition <%}else{ %> Disposal <%} %> of property</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getTransDate()!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(imm.getTransDate().toString())%><%}else{ %>-<%} %></td>
									
								</tr>
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Mode of <%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%> Acquisition <%}else{ %> Disposal <%} %></td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getMode()!=null) {%> <%=imm.getMode()%><%}else{ %>-<%} %></td>							
								</tr>	
								
								<tr>
									<td style="width: 5%;border-bottom: 0;"><%=++slno%>.</td>
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
								    <td>(d) Whether the applicant&apos;s interest in the property is in full or part</td>
								    <%if(imm!=null && imm.getApplicantInterest()!=null && "F".equalsIgnoreCase(imm.getApplicantInterest())) {%><td colspan="2" style="color: blue;">Full</td>
								    <%}else if(imm!=null && imm.getApplicantInterest()!=null && "P".equalsIgnoreCase(imm.getApplicantInterest()) ){%><td colspan="2" style="color: blue;"> <%="Part -"+imm.getPartialInterest() %></td> <%} %>
								</tr>
								<tr>
								    <td style="border-bottom: 1;border-top: 0;"></td>
								    <td>(e)  In case the transaction is not exclusively in the name of the employee, particulars of ownership and share of each member.</td>
								    <td colspan="2" style="color: blue;"><%if(imm!=null && !imm.getOsParticulars().isEmpty()) {%> <%=imm.getOsParticulars()%> <%} else{%>-<%} %>
								    <%if(imm!=null && !imm.getOsShare().isEmpty()) {%> <%=", "+imm.getOsShare()%> <%} else{%>-<%} %> </td>
								</tr>
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Sale/Purchase price of the property (Market value in the case of gifts)</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getPrice()!=null) {%> <%=imm.getPrice()%><%} else{%>-<%} %></td>
								</tr>
								
								<%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState())) {%>
								<tr>
								    <td style="width: 5%;border-bottom: 0;"><%=++slno%>.</td>
								    <td>In case of  acquisition, source or sources from which financed / proposed to be financed - (Attach supporting documents)</td>
								    <td colspan="2" style="color: blue;">
								     <%if(imm!=null && imm.getFinanceSource()!=null && "Personal savings".equalsIgnoreCase(imm.getFinanceSource())) {%>
								      Personal savings
								      <%}else if(imm!=null && imm.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(imm.getFinanceSource()) ) {%>
								      <%=imm.getFinanceSource()%>
								      <%} %>
								    </td>
								</tr>
								<%-- <%if(imm!=null && imm.getFinanceSource()!=null && "Personal savings".equalsIgnoreCase(imm.getFinanceSource())) {%>
								<tr>
								    <td style="border-bottom: 1;border-top: 0;"></td>
								    <td>(a) Personal savings</td>
								    <td colspan="2" style="color: blue;">YES</td>
								</tr>	
								<%}else if(imm!=null && imm.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(imm.getFinanceSource()) ) {%>
								<tr>
								    <td style="border-bottom: 1;border-top: 0;"></td>
								    <td>(b) Other sources give details	</td>
								    <td colspan="2" style="color: blue;"><%=imm.getFinanceSource()%></td>
								 </tr>  --%>
								 <%} else if(imm!=null&& imm.getTransState()!=null && "D".equalsIgnoreCase(imm.getTransState())) {%>
								 <tr>
								    <td style="width: 5%;"><%=++slno%>.</td>
								    <td>In case of disposal of the property, was requisite sanction/intimation obtained/given for its acquisition</td>
								    <td colspan="2" style="color: blue"><%if(imm!=null && imm.getRequisiteSanction()!=null && "Y".equalsIgnoreCase(imm.getRequisiteSanction())){ %>Yes<%} else{%>No<%} %></td>
								 </tr>  
								 <%} %>	
								 
								 <tr>
									<td style="width: 5%;border-bottom: 0;"><%=++slno%>.</td>
									<td>(a) Name & Address of the party with whom transaction is made / proposed to be made</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getPartyName()!=null) %><%=imm.getPartyName()+"<br>"+imm.getPartyAddress() %></td>
								</tr>
								<tr>
									<td style="width: 5%;border-top:0"></td>
									<td>(b) How the transaction was arranged / to be arranged</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getTransArrangement()!=null) {%> <%=imm.getTransArrangement() %> <%} %></td>
								</tr>
								
								<tr>
									<td style="width: 5%;border-bottom: 0;"><%=++slno%>.</td>
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
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getDealingNature()!=null ) {%> <%=imm.getDealingNature() %> <%}%></td>
								</tr>
								
								<%if(imm!=null&& imm.getTransState()!=null && "A".equalsIgnoreCase(imm.getTransState()) && imm.getMode()!=null && "Gift".equalsIgnoreCase(imm.getMode())) {%>
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>In case of acquisition by gift, whether sanction is also required under SITAR Conduct, Discipline & Appeal Rules.</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && imm.getSanctionRequired()!=null && "Y".equalsIgnoreCase(imm.getSanctionRequired())) {%>Yes<%} else{%>No<%} %></td>
								</tr>
								<%} %>
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Any other relevant fact which the applicant may like to mention.</td>
									<td colspan="2" style="color: blue;"><%if(imm!=null && !imm.getRelavantFact().isEmpty()) {%> <%=imm.getRelavantFact() %> <%} else{%>-<%} %></td>
								</tr>
						 </table>
						 
						 <%if(slno<=13) {%>
						 <div style="width: 100%;border: 0;text-align: center;margin-top:100px;"> <b style="font-size:18px;text-decoration:underline">DECLARATION</b> </div>
						 <%}else{ %>
						 <div style="width: 100%;border: 0;text-align: center;margin-top:40px;"> <b style="font-size:18px;text-decoration:underline">DECLARATION</b> </div>
						 <%} %>
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
						     <div style="color: blue;" align="right" ><%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %></div>
						     <div style="" align="right">Signature of the employee</div>
						     
						     <% if( imm!=null && "APR".equalsIgnoreCase(imm.getPisStatusCode()) ){ %>	
						     <hr style="border: solid 1px;margin-left:20px;">				     
						     <div style="width: 100%;border: 0;text-align: center;margin-top:20px;"> <b style="font-size:18px;text-decoration:underline">Remarks of Administration</b> </div>	
						     <br>
						     <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						       1. The details of proposed <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %> acquisition <%}else {%> disposal <%} %> of Immovable property furnished above by <%if(emp!=null && emp[5]!=null) {%><%=emp[5]%><%} %><input type="text" style="width: 20%;color: blue;text-align: center;" value="<%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %>" readonly>, Emp No <input type="text" style="width: 10%;color: blue;text-align: center;" value="<%if(emp!=null && emp[0]!=null){%> <%=emp[0]%><%} %>" readonly> has been perused and filed in <%if(emp!=null && emp[5]!=null && (emp[5].toString().equalsIgnoreCase("Mr.") || (emp[5].toString().equalsIgnoreCase("Dr.") ) ) ) {%>his <%}else {%>her <%} %> service records.
						     </div>
						     <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %>
						     <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 10px;" align="left">
						       2. As per Sl. No. 13 above, the applicant is proposing acquisition of the property with a person having official dealing with the employee / with a foreigner which may be permitted. 
						     </div>	
						     <%} %>	
						      <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 10px;" align="left">Submitted for kind information.</div>	
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
				             <%if( imm!=null && imm.getPisStatusCode().toString().equalsIgnoreCase("APR") ) {%>
                      <div class="row" style="margin-top: 40px;">
                         <div class="col-md-12"><b style="color: black"> SANCTIONED / NOT SANCTIONED <br>CEO</b><br></div>
                      </div>
                      <div class="row">
                         <div class="col-md-12" style="color: blue;"><%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("APR")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;} %>
				   		<%} %> </div>
                      </div>
                       <%} %>
       </div>
</body>
</html>