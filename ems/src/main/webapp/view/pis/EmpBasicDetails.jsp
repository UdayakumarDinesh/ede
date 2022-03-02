<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

<style type="text/css">

</style>
</head>
<body>

<%
	String empid = (String) request.getAttribute("empid");
	Object[] employeedetails = (Object[]) request.getAttribute("employeedetails");

%>

 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>ADMIN DASHBOARD</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm">Home</a></li>
					<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
					<li class="breadcrumb-item "><a href="PisAdminEmpList.htm">Employee List</a></li>
					<li class="breadcrumb-item active " aria-current="page">Employee info</li>
				</ol>
			</div>	
		</div>
	</div>	
	<div class="card-body" >
		<div class="card" >
			<div class="card-body " >
				 <table class="table table-striped table-bordered" >
					<tbody>
						<tr>
							<td colspan="6" rowspan="3"></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr>
							<td><b>Name</b></td>
							<td>
								<b><%=employeedetails[3] %></b>
							</td>
							<td> <b>Designation</b> </td>
							<td><%=employeedetails[22] %></td>
							<td> <b>DOB</b> </td>
							<td><%=employeedetails[5] %></td>
						</tr>
						<tr>
							<td> <b>Gender</b> </td>
							<td>
								<%if(employeedetails[9].toString().equalsIgnoreCase("M")){ %>
									Male
								<%}else if(employeedetails[9].toString().equalsIgnoreCase("F")){ %>
									Female
								<%} %> 
							</td>
							<td> <b>UID</b> </td>
							<td><%=employeedetails[15] %></td>
							<td> <b>PAN</b> </td>
							<td><%=employeedetails[13] %></td>
						</tr>
						<tr>
							<td> <b>Division</b> </td>
							<td><%=employeedetails[23] %>(<%=employeedetails[24] %>)</td>
							<td> <b>Group</b> </td>
							<td><%=employeedetails[25] %>(<%=employeedetails[26] %>)</td>
							<td> <b>PunchCard No</b> </td>
							<td><%=employeedetails[14] %></td>
						</tr>
						
						<tr>
							<td> <b>DOJ</b> </td>
							<td><%=employeedetails[6] %></td>
							<td> <b>DOA</b> </td>
							<td><%=employeedetails[7] %></td>
							<td> <b>DOR</b> </td>
							<td><%=employeedetails[8] %> </td>
						</tr>
						
						<tr>
							<td> <b>Email</b> </td>
							<td><%=employeedetails[16] %></td>
							<td> <b>Religion</b> </td>
							<td><%=employeedetails[12] %></td>
							<td> <b>Marital Status</b> </td>
							<td>
								<%if(employeedetails[11].toString().equalsIgnoreCase("M")){ %>
									Married
								<%}else if(employeedetails[11].toString().equalsIgnoreCase("U")){ %>
									UnMarried
								<%} %> 
							</td>
						</tr>
						
						<tr>
							<td> <b>SBI AccNo.</b> </td>
							<td><%=employeedetails[20] %></td>
							<td> <b>Blood Group</b> </td>
							<td><%=employeedetails[10] %></td>
							<td> <b>Home Town</b> </td>
							<td><%=employeedetails[27] %></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

 </div>

</body>
</html>