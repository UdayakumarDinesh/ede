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
		      <form action="" method="post">
				 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<div class="card-body main-card" style="padding-top: 0px; margin-top: -15px;" align="center">
					   <div style="width: 10%; height: 75px; border: 0; display: inline-block;margin:2% 0 10px -90%;"><img style="width: 80px; height: 90px; margin: 5px;" align="left" src="data:image/png;base64,<%=LabLogo%>"></div>									
                          <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-5%;margin-left:5%;">
                            <h4 style="text-decoration: underline">
                              Form of report / application for permission for 
                              <%if(con!=null && con.getTransactionState()!=null) {%>
                                <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>Construction of house
                                <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>Addition of house
                                <%}else{ %>Renovation of an existing house<%} %>
                              <%} %>
                            </h4>
                          </div>
					</div>
					<table style="border: 0px; border-collapse: collapse;width: 100%;">
					   <tr>
					     <td style="border: 0;width:80%;"></td>
					     <td style="border: 0;width:20%;">Date:</td>
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
					     <td style="border: 0;">This is to report to you that I propose to 
					     <%if(con!=null && con.getTransactionState()!=null) {%>
                                <%if(con.getTransactionState().equalsIgnoreCase("C")) {%>construct a house
                                <%}else if(con.getTransactionState().equalsIgnoreCase("A")){ %>to make an addition of my house   
                                <%}else{ %>renovation of my house<%} %>
                              <%} %>.
                          </td>
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
					       <%}else if(con!=null && con.getSupervisedBy()!=null) {%>done by &nbsp;<label style="color: blue;"><%=con.getSupervisedBy() %></label><%} %>.
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
					     <td style="border: 0;color: blue;">&emsp;&emsp;<%if(con.getNatureOfDealings()!=null) {%><%=con.getNatureOfDealings() %><%} %></td>
					   </tr>
					   <%} }%>
					   
					</table>
					<table style="border: 0px; border-collapse: collapse;width: 100%;">
					  <tr>
					     <td style="border: 0;">3. The cost of the proposed construction will be as under:- </td>
					   </tr>
					   <tr>
					     <td style="border: 0;width: 60%;"></td>
					     <td style="border: 0;width: 40%;">Amount(Rs.)</td>
					   </tr>
					   <tr>
					     <td style="border: 0;">&emsp;(i) Own savings</td>
					     <td style="border: 0;"><%if(con!=null && con.getOwnSavings()!=null) {%><%=con.getOwnSavings() %> <%} %></td>
					   </tr>
					   <tr>
					     <td style="border: 0;">&emsp;(ii) Loans / Advances with full details</td>
					     <td style="border: 0;"></td>
					   </tr>
					   <tr>
					     <td style="border: 0;">&emsp;(i) Other sources with full details</td>
					     <td style="border: 0;"></td>
					   </tr>
					</table>
			  </form>
		   </div>
		</div>
    </div>
</div> 
</body>
</html>