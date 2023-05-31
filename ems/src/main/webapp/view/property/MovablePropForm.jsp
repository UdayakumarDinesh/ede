<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.vts.ems.property.model.PisMovableProperty,com.vts.ems.pis.model.Employee" %>    
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

</style>
</head>
<body>
<%
String LabLogo=(String) request.getAttribute("LabLogo");
PisMovableProperty mov =(PisMovableProperty)request.getAttribute("movPropFormData");
String CEO = (String)request.getAttribute("CEOEmpNo");
String empNo = (String)session.getAttribute("EmpNo");
Object[] emp = (Object[])request.getAttribute("EmpData");
List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");
List<Object[]> MovIntimationRemarks = (List<Object[]>)request.getAttribute("MovIntimationRemarks");
String isApproval = (String)request.getAttribute("isApproval");

SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");

List<String> toUserStatus  = Arrays.asList("INI","RPA","RCE");
 List<String> adminRemarks  = Arrays.asList("VDG","VPA","APR"); 
int slno=0;
%>

<div class="page card dashboard-card">
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
                          <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-8%;margin-left:5%;"><h4 style="text-decoration: underline">Form for giving <%if(mov!=null && "I".equalsIgnoreCase(mov.getPurpose())) {%> intimation <%}else{ %> permission<%} %> for transaction of <%if(mov!=null && "A".equalsIgnoreCase(mov.getTransState())){ %> Acquiring <%}else {%> Disposing <%} %> of Movable Property</h4></div>
						  <!-- <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-4%;"><h4>(Eg. Land / Plot / Flat / House / Building / Shop etc.)</h4></div>	
						  <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-4%;margin-left:5%;"><h4>To be submitted at least 30 days before the proposed date of the transaction</h4></div> -->	
							<br>
							<table style="border: 0px; width: 100%;">
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
								   <td >Purpose of application </td>
								    <%if(mov!=null && mov.getPurpose()!=null && "I".equalsIgnoreCase(mov.getPurpose())) {%> <td colspan="2" style="color: blue;">(a) Prior Intimation of transaction</td>
								  <%}else{%> 
								  <td colspan="2" style="color: blue;">(b) Prior permission of  transaction</td> 
								  <%}%>
								</tr>
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Whether property is being Acquired or Disposed off ?</td>
									<%if(mov!=null&& mov.getTransState()!=null && "A".equalsIgnoreCase(mov.getTransState())) {%><td colspan="2" style="color: blue;">Acquired</td>
									<%} else{%><td colspan="2" style="color: blue;">Disposed off</td><%} %>
								</tr>
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Date of <%if(mov!=null&& mov.getTransState()!=null && "A".equalsIgnoreCase(mov.getTransState())) {%> Acquisition <%}else{ %> Disposal <%} %> of property</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getTransDate()!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(mov.getTransDate().toString())%><%}else{ %>-<%} %></td>
									
								</tr>
								
								<tr>
								    <td style="width: 5%;border-bottom: 0"><%=++slno%>.</td>
								    <td>(a) Description of the property</td>
								    <td colspan="2" style="color: blue;"><%if(mov!=null && mov.getDescription()!=null){ %><%=mov.getDescription()%> <%}else{ %>-<%} %></td>
								</tr>
								
								<tr>
								    <td style="border-top: 0"></td>
								    <td>(b) Make & Model (Registration No.)</td>
								    <td colspan="2" style="color: blue;"><%if(mov!=null && mov.getMakeAndModel()!=null) {%><%=mov.getMakeAndModel() %><%}else{ %>-<%} %></td>
								</tr>
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Mode of <%if(mov!=null&& mov.getTransState()!=null && "A".equalsIgnoreCase(mov.getTransState())) {%> Acquisition <%}else{ %> Disposal <%} %></td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getMode()!=null) {%> <%=mov.getMode()%><%}else{ %>-<%} %></td>							
								</tr>	
								
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Sale/Purchase price of the property (Market value in the case of gifts)</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getPrice()!=null) {%> <%=mov.getPrice()%><%} else{%>-<%} %></td>
								</tr>
								
								<%if(mov!=null&& mov.getTransState()!=null && "A".equalsIgnoreCase(mov.getTransState())) {%>
								<tr>
								    <td style="width: 5%;border-bottom: 0;"><%=++slno%>.</td>
								    <td>In case of  acquisition, source or sources from which financed / proposed to be financed - (Attach supporting documents)</td>
								    <td colspan="2" style="color: blue">
								     <%if(mov!=null && mov.getFinanceSource()!=null && "Personal savings".equalsIgnoreCase(mov.getFinanceSource())) {%>
								      Personal savings
								     <%} else if(mov!=null && mov.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(mov.getFinanceSource()) ) {%>
								     <%=mov.getFinanceSource()%>
								     <%} %>
								    </td>
								</tr>
								 <%-- <%if(mov!=null && mov.getFinanceSource()!=null && "Personal savings".equalsIgnoreCase(mov.getFinanceSource())) {%>
								<tr>
								    <td style="border-bottom: 1;border-top: 0;"></td>
								    <td>(a) Personal savings</td>
								    <td colspan="2" style="color: blue;">YES</td>
								</tr>	
								<%}else if(mov!=null && mov.getFinanceSource()!=null && !"Personal savings".equalsIgnoreCase(mov.getFinanceSource()) ) {%>
								<tr>
								    <td style="border-bottom: 1;border-top: 0;"></td>
								    <td>(b) Other sources give details	</td>
								    <td colspan="2" style="color: blue;"><%=mov.getFinanceSource()%></td>
								 </tr>  --%>
								 <% }else if(mov!=null&& mov.getTransState()!=null && "D".equalsIgnoreCase(mov.getTransState())) {%>
								 <tr>
								    <td style="width: 5%;"><%=++slno%>.</td>
								    <td>In case of disposal of the property, was requisite sanction/intimation obtained/given for its acquisition (A copy of the sanction/acknowledgement should be attached)</td>
								    <td colspan="2" style="color: blue"><%if(mov!=null && mov.getRequisiteSanction()!=null && "Y".equalsIgnoreCase(mov.getRequisiteSanction())){ %>Yes<%} else{%>No<%} %></td>
								 </tr>  
								 <%} %>	 
								 
								 <tr>
									<td style="width: 5%;border-bottom: 0;"><%=++slno%>.</td>
									<td>(a) Name & Address of the party with whom transaction is made / proposed to be made</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getPartyName()!=null) %><%=mov.getPartyName()+"<br>"+mov.getPartyAddress() %></td>
								</tr>
								<tr>
									<td style="width: 5%;border-top:0"></td>
									<td>(b) How the transaction was arranged / to be arranged (whether through any statutory body or a private agency through advertisement or through friends and relatives, full particulars to be given).</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getTransArrangement()!=null) {%> <%=mov.getTransArrangement() %> <%} %></td>
								</tr>
								
								<tr>
									<td style="width: 5%;border-bottom: 0;"><%=++slno%>.</td>
									<td>(a) Is the party related to the applicant</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getPartyRelated()!=null && "Y".equalsIgnoreCase(mov.getPartyRelated())){ %>Yes<%} else{%>No<%} %></td>
								</tr>
								<%if(mov!=null && mov.getPartyRelated()!=null && "Y".equalsIgnoreCase(mov.getPartyRelated())){ %>
								<tr>
									<td style="border-bottom: 0;border-top: 0;"></td>
									<td>(b) if so state the relationship</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getRelationship()!=null) %><%=mov.getRelationship()%></td>
								</tr>
								<%} %>
								<tr>
									<td style="border-bottom: 0;border-top: 0;"></td>
									<td>(c) Did the applicant have any dealings with the party in his/her official capacity at any time, or is the applicant likely to have any dealing with the party in the near future?</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getFutureDealings()!=null && "Y".equalsIgnoreCase(mov.getFutureDealings())) {%>Yes<%} else{%>No<%} %></td>
								</tr>
								<tr>
									<td style="border-top: 0;"></td>
									<td>(d) Nature of official dealing with the party  </td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getDealingNature()!=null ) {%> <%=mov.getDealingNature() %> <%}%></td>
								</tr>
								
								<%if(mov!=null&& mov.getTransState()!=null && "A".equalsIgnoreCase(mov.getTransState()) && mov.getMode()!=null && "Gift".equalsIgnoreCase(mov.getMode())) {%>
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>In case of acquisition by gift, whether sanction is also required under SITAR Conduct, Discipline & Appeal Rules.</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && mov.getSanctionRequired()!=null && "Y".equalsIgnoreCase(mov.getSanctionRequired())) {%>Yes<%} else{%>No<%} %></td>
								</tr>
								<%} %>
								<tr>
									<td style="width: 5%;"><%=++slno%>.</td>
									<td>Any other relevant fact which the applicant may like to mention.</td>
									<td colspan="2" style="color: blue;"><%if(mov!=null && !mov.getRelavantFacts().isEmpty()) {%> <%=mov.getRelavantFacts() %> <%} else{%>-<%} %></td>
								</tr>
							</table>
							
							<!--DECLARATION  -->
							<div style="width: 100%;border: 0;text-align: center;margin-top:5%;"> <b style="font-size:18px;text-decoration:underline">DECLARATION</b> </div>
							<br>
							<%if(mov!=null && "I".equalsIgnoreCase(mov.getPurpose())) {%> 
						    <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						    I, <input type="text" style="width: 20%;color: blue;text-align: center;" value="<%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %>" readonly> hereby intimate the proposed <%if(mov!=null && "A".equalsIgnoreCase(mov.getTransState())){ %> Acquisition <%}else {%> Disposal <%} %> of Immovable property by me as detailed above. I declare that the particulars given above are true.
						    </div>
						    <%}else{ %>
						      <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						    I, <input type="text" style="width: 20%;color: blue;text-align: center;" value="<%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %>" readonly> hereby declare that the particulars given above are true. I request that I may be given permission to <%if(mov!=null && "A".equalsIgnoreCase(mov.getTransState())){ %> acquire <%}else {%> dispose <%} %> of property as described above <%if(mov!=null && "A".equalsIgnoreCase(mov.getTransState())){ %> from <%}else {%> to <%} %> the party whose name is mentioned at Sl. No. 13 above.  
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
						     <% if(  CEO.equalsIgnoreCase(empNo) || mov!=null && adminRemarks.contains(mov.getPisStatusCode()) ){ %>	
						     <hr style="border: solid 1px;">				     
						     <div style="width: 100%;border: 0;text-align: center;margin-top:5%;"> <b style="font-size:18px;text-decoration:underline">Remarks of Administration</b> </div>	
						     <br>
						     <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						       1. The details of proposed <%if(mov!=null && "A".equalsIgnoreCase(mov.getTransState())){ %> acquisition <%}else {%> disposal <%} %> of Immovable property furnished above by <%if(emp!=null && emp[5]!=null) {%><%=emp[5]%><%} %><input type="text" style="width: 20%;color: blue;text-align: center;" value="<%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %>" readonly>, Emp No <input type="text" style="width: 10%;color: blue;text-align: center;" value="<%if(emp!=null && emp[0]!=null){%> <%=emp[0]%><%} %>" readonly> has been perused and filed in <%if(emp!=null && emp[5]!=null && (emp[5].toString().equalsIgnoreCase("Mr.") || (emp[5].toString().equalsIgnoreCase("Dr.") ) ) ) {%>his <%}else {%>her <%} %> service records.
						     </div>
						     <%if(mov!=null && "A".equalsIgnoreCase(mov.getTransState())){ %>
						     <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 2%;" align="left">
						       2. As per Sl. No. 13 above, the applicant is proposing acquisition of the property with a person having official dealing with the employee / with a foreigner which may be permitted. 
						     </div>	
						     <%} %>	
						      <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 2%;" align="left">Submitted for kind information.</div>	
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
						<%if(MovIntimationRemarks.size()>0){ %>
							<div class="col-md-8" align="center" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
								<%if(MovIntimationRemarks.size()>0){ %>
									<table style="margin: 3px;padding: 0px">
										<tr>
											<td style="border:none;padding: 0px">
												<h6 style="text-decoration: underline;">Remarks :</h6> 
											</td>											
										</tr>
										<%for(Object[] obj : MovIntimationRemarks){%>
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
				             long diff = DateTimeFormatUtil.dayDifference(mov.getTransDate().toString());
				             
				             if(mov!=null && toUserStatus.contains(mov.getPisStatusCode()) && diff<30){ %>				           				   		      
					          <div align="left">
						        <b >Remarks :</b><br>
						        <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					          </div>
				   	
				   		      <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="MovablePropFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							     <i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						      </button>
					        <%} %>

					        <% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ 
					         if(mov!=null && ( mov.getPisStatusCode().toString().equalsIgnoreCase("FWD") ||
					        		 mov.getPisStatusCode().toString().equalsIgnoreCase("VDG") ) && !CEO.equalsIgnoreCase(empNo)  ){%>						     
						     <div align="left">
						        <b >Remarks :</b><br>
						        <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					         </div>
				   		     <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="MovablePropFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Verify	
						     </button>
					
				   		    <button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="MovablePropFormSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						    </button>
					        <%} %>	
					        <% if( (mov!=null && mov.getPisStatusCode().toString().equalsIgnoreCase("VPA")) || CEO.equalsIgnoreCase(empNo) ){ %>
					        <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="MovablePropFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Approve	
						</button>
					
				   		<button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="MovablePropFormSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
					<%} }%>
					<%if(( CEO.equalsIgnoreCase(empNo) || ( mov!=null && mov.getPisStatusCode().toString().equalsIgnoreCase("APR") )) ) {%>
                      <div class="row" style="margin-top: 5%;">
                         <div class="col-md-12"><b style="color: black"> SANCTIONED / NOT SANCTIONED <br>CEO</b><br></div>
                      </div>
                      <div class="row">
                         <div class="col-md-12" style="color: blue"><%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("APR")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;} %>
				   		<%} %> </div>
                      </div>
                       <%} %>		     
						</div>
						<input type="hidden" name="movPropertyId" value="<%if(mov!=null){ %><%=mov.getMovPropertyId()%><%}%>">
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
        alert("Please enter Remarks");
        return false;
    }
}

</script>

</body>
</html>