<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Departments List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%List<Object[]> Deplist=(List<Object[]>)request.getAttribute("Deparmentslist"); %>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Departments List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>						
						<li class="breadcrumb-item active " aria-current="page">Departments List</li>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="page card dashboard-card">
		 <div class="card" >
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
				<div class="card-body ">			
					<form action="DepartmentsList.htm" method="POST" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>						
						<div class="table-responsive">					
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th> Select            </th>
										<th>Department Code    </th>
										<th> Department        </th>
										<th> Department Head    </th>
									</tr>
								</thead>
								<tbody>
								       <%if(Deplist!=null){
								    	  for(Object[] obj:Deplist){ 
								    	   %>
								     <tr>
								        <td style="text-align: center;"><input type="radio" value="<%=obj[0] %>" name="Depid" required="required"> </td>
								        <td style="text-align: center;width:14%;"><%=obj[1]%></td>
								        <td><%=obj[2]%></td>
								         <td><%=obj[3]%></td>
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