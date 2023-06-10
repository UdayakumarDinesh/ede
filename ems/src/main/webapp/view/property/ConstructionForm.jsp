<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.vts.ems.property.model.PisPropertyConstruction"%>
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
PisPropertyConstruction con= (PisPropertyConstruction)request.getAttribute("constructionFormData");
Object[] emp = (Object[])request.getAttribute("EmpData");
Object[] lab = (Object[])request.getAttribute("labDetails");

String empNo = (String)session.getAttribute("EmpNo");
String CEO = (String)request.getAttribute("CEOEmpNo");
List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");

List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");
List<Object[]> constructionRemarks = (List<Object[]>)request.getAttribute("constructionRemarks");
String isApproval = (String)request.getAttribute("isApproval");

SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");

List<String> toUserStatus  = Arrays.asList("INI","RDG","RSO","RPA","RCE");
List<String> adminRemarks  = Arrays.asList("VDG","VSO","VPA","APR");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-5">
				<h5>Permission Application - Form</h5>
			</div>
			<div class="col-md-7" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item "><a href="PropertyDashBoard.htm">Property</a></li>
					<li class="breadcrumb-item "><a href="ConstructionRenovation.htm">Construction </a></li>
					<li class="breadcrumb-item active " aria-current="page">Permission form</li>
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
		      <form action="" >
				 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div class="card-body main-card" style="padding-top: 0px; margin-top: -15px;" align="center">
					   <div style="width: 10%; height: 75px; border: 0; display: inline-block;margin:2% 0 10px -90%;"><img style="width: 80px; height: 90px; margin: 5px;" align="left" src="data:image/png;base64,<%=LabLogo%>"></div>									
                          <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-5%;margin-left:5%;">
                            <h4 style="text-decoration: underline">
                              Form of application for permission for 
                              <%if(con!=null && con.getTransactionState()!=null) {%>
                                <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>Construction of house
                                <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>Addition of an existing house
                                <%}else{ %>Renovation of an existing house<%} %>
                              <%} %>
                            </h4>
                          </div>
					</div>
					<table style="border: 0px; border-collapse: collapse;width: 100%;">
					   <tr>
					     <td style="border: 0;width:80%;"></td>
					     <td style="border: 0;width:20%;">Date&emsp;:&emsp;
					       <label style="color: blue;">
						      <%for(Object[] apprInfo : ApprovalEmpData){ %>
							  <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
					   				<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %>
					   			<%break;
					   			} %>
						   		<%} %>
						   	  </label>
					     </td>
					   </tr>
					   <tr>
					     <td style="border: 0;width:80%;">From</td>
					     <td style="border: 0;width:20%;">To</td>
					   </tr>
					    <tr>
					     <td style="border: 0;width:80%;color: blue;"><%if(emp!=null && emp[1]!=null){%><%=emp[1] %><%} %></td>
					     <td style="border: 0;width:20%;">P&A Dept.</td>
					   </tr>
					   <tr>
					     <td style="border: 0;width:80%;">EmpNo&nbsp;:&nbsp;<label style="color: blue;"><%if(emp!=null && emp[0]!=null){%><%=emp[0] %><%} %></label></td>
					     <td style="border: 0;width:20%;"><%if(lab!=null && lab[1]!=null) {%><%=lab[1]%><%} %>,</td>
					   </tr>
					    <tr>
					     <td style="border: 0;width:80%;color: blue;"><%if(emp!=null && emp[2]!=null){%><%=emp[2] %><%} %></td>
					     <td style="border: 0;width:20%;"><%if(lab!=null && lab[5]!=null) {%><%=lab[5]%><%} %></td>
					   </tr>
					   <tr>
					     <td style="border: 0;"></td>
					   </tr>
					    <tr>
					     <td style="border: 0;">Sir,</td>
					   </tr>
					   <tr>
					     <td style="border: 0;">This is to request that permission may be granted to me for the 
					     <%if(con!=null && con.getTransactionState()!=null) {%>
                                <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>construction a house
                                <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>to make an addition of the house   
                                <%}else{ %>renovation of the house<%} %>
                              <%} %>.
                          </td>
					   </tr>
					    <tr>
					     <td style="border: 0;">1. The estimated cost of the land and material for the 
					     <%if(con!=null && con.getTransactionState()!=null) {%>
                                <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>construction
                                <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>extension   
                                <%}else{ %>renovation<%} %>
                              <%} %> is &nbsp;: &nbsp;<label style="color: blue;"><%if(con!=null && con.getEstimatedCost()!=null){ %><%=con.getEstimatedCost() %> <%} %></label>.
                          </td>
					   </tr>
					    <tr>
					     <td style="border: 0;">2. The construction will be 
					       <%if(con!=null && con.getSupervisedBy()!=null && con.getSupervisedBy().equalsIgnoreCase("Myself")) {%> supervised by myself
					       <%}else if(con!=null && con.getSupervisedBy()!=null) {%>done by &nbsp;<label style="color: blue;"><%=con.getSupervisedBy() %></label>. <br>
					          &emsp;Contractor Name&emsp;:&emsp;<label style="color: blue;"><%if(con.getContractorName()!=null) {%><%=con.getContractorName()%><%} else{%>-<%} %></label>  <br>
					          &emsp;Contractor Business Place&emsp;:&emsp;<label style="color: blue;"><%if(con.getContractorPlace()!=null) {%><%=con.getContractorPlace()%><%} else{%>-<%} %></label> 
					       <%} %>
                         </td>
					   </tr>
					   <%
					   if(con!=null && con.getSupervisedBy()!=null && !con.getSupervisedBy().equalsIgnoreCase("Myself")) {
					   if( con.getContractorDealings()!=null && con.getContractorDealings().equalsIgnoreCase("N")) {%>
					   <tr>
					     <td style="border: 0;">
					      &emsp; I do not have any official dealings with the contractor nor did I have any official dealings with him / her in the past.
                         </td>
					   </tr>
					   <%}else if(con!=null && con.getContractorDealings()!=null && con.getContractorDealings().equalsIgnoreCase("Y")) {%>
					   <tr>
					     <td style="border: 0;">
					       &emsp; I have / had official dealings with the contractor and nature of my dealings with him / her is / was as under.
                         </td>
					   </tr>
					   <tr>
					     <td style="border: 0;color: blue;">&emsp;&emsp;<%if(con.getNatureOfDealings()!=null) {%><%=con.getNatureOfDealings() %><%} else{%>-<%} %></td>
					   </tr>
					   <%} }%>
					   
					</table>
					<table style="width: 99% !important;">
					  <tr>
					     <td style="border: 0;width: 40%;">3. The cost of the proposed construction will be as under:- </td>
					   </tr>
					   <tr >
					     <td style="width: 30%;"></td>
					     <th style="width: 10%;text-align: center;">Amount(Rs.)</th>
					     <th style="width: 60%;text-align: center;">Details</th>
					   </tr>
					   <tr>
					     <th style="" >&emsp;(i) Own savings</th>
					     <td style="color: blue;text-align: right;"><%if(con!=null && con.getOwnSavings()!=null) {%><%=con.getOwnSavings() %> <%} else{%>-<%} %></td>
					     <td style="color: blue;text-align: left;"><%if(con!=null && con.getOwnSavingsDetails()!=null) {%><%=con.getOwnSavingsDetails() %><%} else{%>-<%} %></td>
					   </tr>
					   <tr>
					     <th style="">&emsp;(ii) Loans / Advances with full details</th>
					     <td style="color: blue;text-align: right;"><%if(con!=null && con.getLoans()!=null){ %><%=con.getLoans()%><%} else{%>-<%} %></td>
					     <td style="color: blue;text-align: left;"><%if(con!=null && con.getLoansDetails()!=null){ %><%=con.getLoansDetails()%><%} else{%>-<%} %></td>
					   </tr>
					   <tr>
					     <th style="">&emsp;(i) Other sources with full details</th>
					     <td style="color: blue;text-align: right;"><%if(con!=null && con.getOtherSources()!=null) {%><%=con.getOtherSources() %><%} else{%>-<%} %></td>
					     <td style="color: blue;text-align: left;"><%if(con!=null && con.getOtherSourcesDetails()!=null) {%><%=con.getOtherSourcesDetails() %><%} else{%>-<%} %></td>
					   </tr>
					   <tr>
					     <th style="text-align: right;">Total</th>
					     <td style="color: blue;text-align: right;"><b><%if(con!=null && con.getProposedCost()!=null) {%><%=con.getProposedCost() %><%} else{%>-<%} %></b></td>
					     <td></td>
					   </tr>
					   <tr> <td style="border: 0;"></td></tr>
					   <tr> <td style="border: 0;"></td></tr>
					   <tr> <td style="border: 0;"></td></tr>
					   <tr>
					     <td style="border: 0;"></td><td style="border: 0;"></td>
					     <td style="border: 0;text-align: right;">Yours faithfully</td>
					   </tr>
					    <tr>
					     <td style="border: 0;"></td> <td style="border: 0;"></td>
					     <td style="border: 0;text-align: right;color: blue;"><%if(emp!=null && emp[1]!=null){%><%=emp[1] %><%} %> </td>
					   </tr>
					   <tr>
					     <td style="border: 0;"></td> <td style="border: 0;"></td>
					     <td style="border: 0;text-align: right;">Signature of the applicant</td>
					   </tr>
					</table>
					<!--Remarks of Administration  -->
					<% if(  CEO.equalsIgnoreCase(empNo) || PandAs.contains(empNo) || con!=null && adminRemarks.contains(con.getPisStatusCode()) ){ %>	
					<hr style="border: solid 1px;">				     
					<div style="width: 100%;border: 0;text-align: center;margin-top:5%;"> <b style="font-size:18px;text-decoration:underline">Remarks of Administration</b> </div>	
					<br>
					<div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						 1. The details of proposed 
						 <%if(con!=null && con.getTransactionState()!=null) {%>
                                <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>Construction of house
                                <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>Addition of an existing house
                                <%}else{ %>Renovation of an existing house<%} %>
                              <%} %>
						  furnished above by <%if(emp!=null && emp[5]!=null) {%><%=emp[5]%><%} %><input type="text" style="width: 20%;color: blue;text-align: center;" value="<%if(emp!=null && emp[1]!=null){%> <%=emp[1]%><%} %>" readonly>, Emp No <input type="text" style="width: 10%;color: blue;text-align: center;" value="<%if(emp!=null && emp[0]!=null){%> <%=emp[0]%><%} %>" readonly> has been perused and filed in <%if(emp!=null && emp[5]!=null && (emp[5].toString().equalsIgnoreCase("Mr.") || (emp[5].toString().equalsIgnoreCase("Dr.") ) ) ) {%>his <%}else {%>her <%} %> service records.
					</div>
				
					<div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 2%;" align="left">
						 2. As per Sl. No. 2 above, the applicant is proposing construction with a person having official dealing with the employee, which may be permitted. 
					</div>	
				
					<div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 2%;" align="left">Submitted for kind information.</div>	
						      
					 <%if(con!=null && !con.getConstrStatus().equalsIgnoreCase("N")){ %>
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
				             				             				          
                       <br> <br>
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
						<%if(constructionRemarks!=null && constructionRemarks.size()>0){ %>
							<div class="col-md-8" align="center" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
								<%if(constructionRemarks.size()>0){ %>
									<table style="margin: 3px;padding: 0px">
										<tr>
											<td style="border:none;padding: 0px">
												<h6 style="text-decoration: underline;">Remarks :</h6> 
											</td>											
										</tr>
										<%for(Object[] obj : constructionRemarks){%>
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
				       <% if(con!=null && toUserStatus.contains(con.getPisStatusCode())){ %>				           				   		      
					   <div align="left">
						  <b >Remarks :</b><br>
						   <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					    </div>
				   	
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ConstructionFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							     <i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						</button>
					   <%} %>

					   <% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){%>						     
					   <% if( CEO.equalsIgnoreCase(empNo)){ %>
					    <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					    </div>
					    <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ConstructionFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Approve?');" >
						    Approve	
						</button>
						      						
						<button type="submit" class="btn btn-sm delete-btn" id="finalSubmission" formaction="ConstructionFormSubmit.htm" name="Action" value="D" onclick="return disapprove();" >
							Disapprove	
						</button>
						      
					    <%}else if(PandAs.contains(empNo)) {%>
					    <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					    </div>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ConstructionFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Verify	
						 </button>
						       
					     <%}else{ %>
					     <div align="left">
						     <b >Remarks :</b><br>
						     <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					     </div>
				   		 <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ConstructionFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Recommend?');" >
							 Recommend	
						 </button>
						       
						  <%} %>	
				   		  <button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="ConstructionFormSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							  Return
						  </button>	
						    				       							   		  
					  <%} %>
					<%if( CEO.equalsIgnoreCase(empNo)  && (con!=null && con.getPisStatusCode().equalsIgnoreCase("VPA"))) {%> 
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
				   		<br>
                      </div>	
                      <input type="hidden" name="constructionId" value="<%if(con!=null){ %><%=con.getConstructionId()%><%}%>">
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