<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List,java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%> 
<%@page import="com.vts.ems.property.model.PisPropertyConstruction"%>
<%@page import="java.util.List,java.util.ArrayList,java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%>       
<!DOCTYPE html>
<html>
<%
Object[] emp = (Object[])request.getAttribute("EmpData");
%>
<title>Permission Form</title>
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
				size: 790px 1160px;
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
</head>
<body>
<%
PisPropertyConstruction con= (PisPropertyConstruction)request.getAttribute("constructionFormData");
Object[] lab = (Object[])request.getAttribute("labDetails");

String empNo = (String)session.getAttribute("EmpNo");
String CEO = (String)request.getAttribute("CEOEmpNo");
List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");

List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");

String LabLogo = (String)request.getAttribute("LabLogo");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");

List<String> adminRemarks  = Arrays.asList("VDG","VPA","VSO","APR");

%>
    
      <div class="center">
         <div style="width: 100%;float:left;">
              <div style="width: 20%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
              <div style="width: 90%; height: 75px; border: 0; text-align: center;"><h3 style="text-decoration: underline">
                 Form of application for permission for 
                              <%if(con!=null && con.getTransactionState()!=null) {%>
                                <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>Construction of house
                                <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>Addition of an existing house
                                <%}else{ %>Renovation of an existing house<%} %>
                              <%} %>
              </h3></div>
         </div>
          <br><br>
          <table style="border: 0px; width: 100%;">
			           <tr>
					     <td style="border: 0;width:80%;"></td>
					     <td style="border: 0;width:20%;">Date&nbsp;:&nbsp;
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
					     <td style="border: 0;width:80%;color: blue;"><%if(emp!=null && emp[0]!=null){%><%=emp[0] %><%} %></td>
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
					          &nbsp;&nbsp;&nbsp;&nbsp;Contractor Name & Business Place &nbsp;:&nbsp;<label style="color: blue;">
					          <%if(con.getContractorName()!=null) {%><%=con.getContractorName()%><%} else{%>-<%} %>,
					          <%if(con.getContractorPlace()!=null) {%><%=con.getContractorPlace()%><%} else{%>-<%} %>
					          </label> 
					        
					       <%} %>
                         </td>
					   </tr>
					   <%
					   if(con!=null && con.getSupervisedBy()!=null && !con.getSupervisedBy().equalsIgnoreCase("Myself")) {
					   if( con.getContractorDealings()!=null && con.getContractorDealings().equalsIgnoreCase("N")) {%>
					   <tr>
					     <td style="border: 0;" colspan="2">
					      &nbsp;&nbsp;&nbsp;&nbsp; I do not have any official dealings with the contractor nor did I have any official dealings with him / her in the past.
                         </td>
					   </tr>
					   <%}else if(con!=null && con.getContractorDealings()!=null && con.getContractorDealings().equalsIgnoreCase("Y")) {%>
					   <tr>
					     <td style="border: 0;" colspan="2">
					       &nbsp;&nbsp;&nbsp;&nbsp; I have / had official dealings with the contractor and nature of my dealings with him / her is / was as under.
                         </td>
					   </tr>
					   <tr>
					     <td style="border: 0;color: blue;" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;<%if(con.getNatureOfDealings()!=null) {%><%=con.getNatureOfDealings() %><%}else{ %>-<%} %></td>
					   </tr>
					   <%} }%>
					   <tr>
					     <td style="border: 0;width: 80%;">3. The cost of the proposed construction will be as under:- </td>
					   </tr>
					</table>					
				
                    <table style="width: 100%;">
					  
					   <tr >
					     <td style="width: 40%;"></td>
					     <th style="width: 10%;text-align: center;">Amount(Rs.)</th>
					     <th style="width: 50%;text-align: center;">Details</th>
					   </tr>
					   <tr>
					     <th style="" >&nbsp;(i) Own savings</th>
					     <td style="color: blue;text-align: right;"><%if(con!=null && con.getOwnSavings()!=null) {%><%=con.getOwnSavings() %> <%} %></td>
					     <td style="color: blue;text-align: left;"><%if(con!=null && con.getOwnSavingsDetails()!=null) {%><%=con.getOwnSavingsDetails() %> <%} %></td>
					   </tr>
					   <tr>
					     <th style="">&nbsp;(ii) Loans / Advances with full details</th>
					     <td style="color: blue;text-align: right;"><%if(con!=null && con.getLoans()!=null){ %><%=con.getLoans()%><%} %></td>
					     <td style="color: blue;text-align: left;"><%if(con!=null && con.getLoansDetails()!=null){ %><%=con.getLoansDetails()%><%} %></td>
					   </tr>
					   <tr>
					     <th style="">&nbsp;(i) Other sources with full details</th>
					     <td style="color: blue;text-align: right;"><%if(con!=null && con.getOtherSources()!=null) {%><%=con.getOtherSources() %><%} %></td>
					     <td style="color: blue;text-align: left;"><%if(con!=null && con.getOtherSourcesDetails()!=null) {%><%=con.getOtherSourcesDetails() %><%} %></td>
					   </tr>
					   <tr>
					     <th style="text-align: right;">Total</th>
					     <td style="color: blue;text-align: right;"><b><%if(con!=null && con.getProposedCost()!=null) {%><%=con.getProposedCost() %><%} %></b></td>
					     <td></td>
					   </tr>
					   <tr> <td style="border: 0;"></td></tr>
					   <tr>
					     <td style="border: 0;"></td>
					     <td style="border: 0;"></td>
					     <td style="border: 0;text-align: right;">Yours faithfully</td>
					   </tr>
					    <tr>
					     <td style="border: 0;"></td> 
					     <td style="border: 0;"></td>
					     <td style="border: 0;text-align: right;color: blue;"><%if(emp!=null && emp[1]!=null){%><%=emp[1]+", "+emp[2] %><%} %> </td>
					   </tr>
					   <tr>
					     <td style="border: 0;">
					       <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			   <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>
				   				<%-- Employee : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %><br></label> --%>
				   				Forwarded On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			    <%} %>
				   		     <%} %> 
					     </td>
					      <td style="border: 0;"></td>
					     <td style="border: 0;text-align: right;">Signature of the applicant</td>
					   </tr>
					</table>
                   <!--Remarks of Administration  -->
	              <% if(  CEO.equalsIgnoreCase(empNo) || PandAs.contains(empNo) || con!=null && adminRemarks.contains(con.getPisStatusCode()) ){ %>	
					<hr style="border: solid 1px;margin-left:20px;">			     
					<div style="width: 100%;border: 0;text-align: center;margin-top: 10px;"> <b style="font-size:18px;text-decoration:underline">Remarks of Administration</b> </div>	
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
				
					<div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 10px;" align="left">
						 2. As per Sl. No. 2 above, the applicant is proposing construction with a person having official dealing with the employee, which may be permitted. 
					</div>	
					<div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;margin-top: 10px;" align="left">Submitted for kind information.</div>	
					 
					 <%if(con!=null && !con.getConstrStatus().equalsIgnoreCase("N")){ %>
					 <br>
				              <div style="width:100%;text-align: left;margin-left: 10px;">
				               <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			    <%if(apprInfo[8].toString().equalsIgnoreCase("VDG")){ %>
				   				Recommended By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %><br></label>
				   				Recommended On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			    <%} %>
				   		        <%} %> 
				             </div>
				             <br>
				             <div style="width:100%;text-align: left;margin-left: 10px;">
				             <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			 <%if(apprInfo[8].toString().equalsIgnoreCase("VSO")){ %>
				   				Recommended By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %><br></label>
				   				Recommended On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			  <%} %>
				   		     <%} %> 
				            </div>
				             <br>
				            <!--  </div>	 -->			            
				             <div style="border:0px;width: 100%; text-align: right;"> 
	
				   		   <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("VPA")){ %>
				   				Verified By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %><br></label>
				   				Verified On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			<%} %>
				   		   <%} %> 
				         
				           </div>				             				          
                       <br>
                       <div class="row">
                         <div class="col-md-12">
                         <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("APR")){ %>
				   			<b style="color: black">APPROVED</b> <br>
				   				Approved By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %><br></label>
				   				Approved On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			<% break;
				   			}else if(apprInfo[8].toString().equalsIgnoreCase("DPR")){ %>
				   			<b style="color: black">NOT APPROVED</b><br><br>
			   				    Disapproved By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %><br></label>
				   			    Disapproved On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
			   			<% break;} %>
				   		<%} }%> </div>
                      </div>
                     <%} %>
      </div>

</body>
</html>