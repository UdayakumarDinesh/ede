<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<body>
<%
List<Object[]> NewspaperClaimList=(List<Object[]>)request.getAttribute("NewspaperClaimList");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Newspaper Claims</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm"> Newspaper </a></li>
						<li class="breadcrumb-item active " aria-current="page">Newspaper List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		<div align="center">
			<%String ses=(String)request.getParameter("result"); 
			String ses1=(String)request.getParameter("resultfail");
			if(ses1!=null){ %>
				<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
					<%=ses1 %>
				</div>
				
			<%}if(ses!=null){ %>
				
				<div class="alert alert-success" role="alert" style="margin-top: 5px;">
					<%=ses %>
				</div>
			<%} %>
		</div>
	
	 	<div class="page card dashboard-card">
			
			<div class="card-body" >
					<form action="#" method="post" >
						<table class="table table-hover table-striped  table-condensed  table-bordered"  id="myTable">
					    	<thead> 
					        	<tr>            
					            	<th style="width: 5%;">Select</th>
					                <th>Claim Date</th>
					                <th>Month Period</th>
					                <th>Year</th>
					                <th>Claim Amount</th>
					                <th>Admissible Amount</th>
					                <th>Payable Amount</th>
					                <th>Status</th>
					            </tr>
					        </thead>
					       	<tbody>
					        	<%if(NewspaperClaimList!=null&&NewspaperClaimList.size()>0){ 
							    	for(Object ls[]:NewspaperClaimList){%>
					                <tr>
					                	<td style="text-align:center; "><input type="radio" name="NewspaperId" value="<%=ls[0]%>" required="required"></td>
					                    <td><%out.println(DateTimeFormatUtil.SqlToRegularDate(ls[8].toString()));%></td>
					                    <td><%=ls[1]%></td>
					                    <td><%=ls[2]%></td>
					                    <td><%=ls[3]%></td>
					                    <td><%=ls[4]%></td>
					                    <td><%=ls[5]%></td>
					                    <td><%if(0!=(Integer.parseInt(ls[6].toString()))&&ls[7]!=null){%><span class="label label-success">Approved</span><%}else{%><span class="label label-primary">User Applied</span><%}%></td>
					                </tr>
					            <%}}else{ %>
					            
					            <%} %>
					       	</tbody> 
						</table>
					   	                  
						<div class="row"  > 
							<div class="col-md-12" align="center">
								<button type="submit" class="btn btn-primary" formaction="NewspaperView.htm"  name="AddNewspaper" value="AddNewspaper" formnovalidate="formnovalidate" >Add</button>
						    	<%if(NewspaperClaimList!=null && NewspaperClaimList.size()!=0){%>
							        <button type="submit" class="btn btn-warning" formaction="NewspaperEditView.htm" >Edit</button> 
							        <button type="submit" class="btn btn-danger" formaction="NewspaperDelete.htm" onclick="return  FunctionToCheckDelete()">Delete</button> 
							        <button type="submit" class="btn btn-info" formaction="NewspaperPrint.htm" formtarget="_blank">Print</button>
						       	<%}%>
					       	</div>
					   	</div> 
					    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>

						
						
						
				</div>

		</div>
	       
</body>

</html>