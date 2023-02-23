<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Contract Employee  List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
List<Object[]> ContractEmpList=(List<Object[]>)request.getAttribute("ContractEmpList"); 

%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-4">
				<h5>Contract Employee  List</h5>
			</div>
				<div class="col-md-8 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>						
						<li class="breadcrumb-item active " aria-current="page">Contract Employee  List</li>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="page card dashboard-card">
		 <div class="card">	
		 <div align="center">
		 <%String res=(String)request.getParameter("result"); 
		String res1=(String)request.getParameter("resultfail");
		if(res1!=null){ %>
			<div class="alert alert-danger" role="alert">
				<%=res1 %>
			</div>			
		<%}if(res!=null){ %>
			
			<div class="alert alert-success" role="alert">
				<%=res %>
			</div>
		<%} %>
		 </div>	 
				<div class="card-body ">			
					<form action="ContractEmployeeList.htm" method="POST" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>						
						<div class="table-responsive">					
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th> Select </th>
										<th>Employee Name</th>
										<th style="width: 20%;"> Emp No / UserName </th>
										<!-- <th>User Name</th> -->
										<th>DOB</th>
										<th>Mobile No</th>
										
									</tr>
								</thead>
								<tbody>
								          <%if(ContractEmpList!=null){
								        	 for(Object[] obj:ContractEmpList) 
								        	 { %>
								          
								        <tr>
								             <td style="text-align: center;"><input type="radio" name="ContractEmpId" value="<%=obj[0] %>" required="required"></td>
								             <td><%=obj[1] %></td>
								             <td><%=obj[2] %></td>
								            <%--  <td><%=obj[3] %></td> --%>
								             <td style="text-align: center;"><%=sdf.format(obj[4])%></td>
								             <td style="text-align: center;"><%if(obj[5]!=null){%><%=obj[5] %><%}else{ %>-<%} %></td>
								         </tr>
								         <%}} %>
								</tbody>
								</table>
								</div>
								<div class="row text-center">
					<div class="col-md-12">
					<button type="submit" class="btn btn-sm add-btn" name="action" value="Add" formnovalidate="formnovalidate">ADD</button>
					<button type="submit" class="btn btn-sm edit-btn"  name="action"value="Edit">EDIT</button>
					</div>
					</div>
								</form>
								</div>
								</div>
								</div>
								
								
</body>
</html>