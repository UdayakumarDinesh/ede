<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>

<%
	List<Object[]> EmployeeDetailsList = (List<Object[]> )request.getAttribute("EmployeeDetailsList") ;
%>

 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>ADMIN DASHBOARD</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm">Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item active " aria-current="page">Employee List</li>
					</ol>
				</div>
			</div>
	</div>	
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " >
				
					<form action="#" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<td>SNo</td>
										<td>EmpNo</td>
										<td>Name</td>
										<td>Designation</td>
										<td>Division</td>
										<td>Group</td>
										<td>Action</td>
									</tr>
								</thead>
								<tbody>
									<%	long slno=0;
									for(Object[] obj : EmployeeDetailsList){ 
									slno++;%>
										<tr>
											<td><%= slno%></td>
											<td><%=obj[1] %></td>
											<td><%=obj[2] %></td>
											<td><%=obj[8] %></td>
											<td><%=obj[5] %></td>
											<td><%=obj[6] %></td>
											<td>
												<button type="submit" name="empid" value="<%=obj[0] %>" formaction="EmployeeDetails.htm" class="btn btn-sm" data-toggle="tooltip" data-placement="top" title="View Employee Details" > 
													<i class="  fa-solid fa-eye"></i>
												</button>
											</td>
										</tr>
									<%} %>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
							<button type="submit" class="btn btn-sm add-btn" name="action" value="add" formaction="EmployeeAdd.htm">ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="action" value="add">EDIT </button>
						</div>
					</div>
				</form>	
			</div>
		</div>		
		
	</div>

 </div>

</body>
</html>