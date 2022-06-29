<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.time.LocalDate"%>
    <%@page import="java.util.List" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%
		List<Object[]> empFwdList = (List<Object[]>)request.getAttribute("empFwdList");

%>
	<div class="card-header page-top">
		<div class="row">
			<div class="col-5">
				<h5>
					Family Members Include / Exclude
				</h5>
			</div>
			<div class="col-md-7">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminDashboard.htm">Admin</a></li>
					<li class="breadcrumb-item active " aria-current="page">Family Members Include / Exclude</li>
				</ol>
			</div>
		</div>
	</div>
		
	<div class="page card dashboard-card">
		<div align="center">
				<%String ses=(String)request.getParameter("result"); 
				String ses1=(String)request.getParameter("resultfail");
				if(ses1!=null){ %>
					<div class="alert alert-danger" role="alert">
						<%=ses1 %>
					</div>
				<%}if(ses!=null){ %>
					<div class="alert alert-success" role="alert">
						<%=ses %>
					</div>
				<%} %>
			</div>
		<div class="row">
			<div class="col-md-12">
				
				<div class=" card" style="padding: 10px;">
					<div class="row" align="center" ><div class="col-md-12" ><h5 style="background-color: #FFE7BF; padding: 3px;  border-radius: 5px;" >To Be Confirmed</h5></div> </div>
					<table class="table table-striped table-bordered" id="">
						<thead>
						<tr>
							<th style="width: 5%;text-align: center;">SN</th>
							<th style="width: 10%;" >EmpNo</th>
							<th style="width: 30%;" >Employee</th>
							<th style="width: 20%;">Designation</th>
							<th style="width: 15%;">Action</th>
						</tr>
						</thead>
						<tbody>			
						<%if(empFwdList!=null && empFwdList.size()>0){for(Object[] emp:empFwdList){ %>
							<tr>
								<td style="text-align: center;" ><%= empFwdList.indexOf(emp)+1 %></td>
								<td><%=emp[1]%></td>
								<td><%=emp[2]%></td>
								<td><%=emp[4]%></td>
								<td>
									
								</td>
							</tr>	
						<%}}
						else
						{%>
							<tr><td colspan="5" style="text-align: center;" >No record found</td></tr>
						<%} %>
						</tbody>
					</table>
				</div>	
			
			</div>
	
		</div>
	</div>
	
	
</body>
</html>