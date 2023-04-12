<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Qualification List</title>
</head>
<body>
<%
List<Object[]> qualificationList=(List<Object[]>)request.getAttribute("QualificationList"); %>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Qualification List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>						
						<li class="breadcrumb-item active " aria-current="page">Qualification List</li>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="card" >
				<div class="card-body ">			
					<form action="Qualification.htm" method="POST" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>						
						<div class="table-responsive">					
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
								
									<tr>
										<th style ="width:10px"> Select            </th>
										<th style ="width:50px"> Qualification   </th>
										
									</tr>
								</thead>
								<tbody>
								<%if(qualificationList!=null){
									for(Object[] obj:qualificationList ) {%>
								
								<tr>
								<td style="text-align: center;"><input type="radio" name="qualificationId" value="<%=obj[0] %>" required="required"></td>
								<td><%=obj[1] %></td>
								</tr>
								<%} }%>
								</tbody>
								</table>
								</div>
								<div align="center" >
								<button  type="submit" class="btn btn-sm add-btn" name="action" value="Add" formnovalidate="formnovalidate">ADD</button>
								<button type="submit" class="btn btn-sm edit-btn" name="action" value="Edit">EDIT</button>
								</div>
								</form>
								
							</div>
								</div>
		<script>
		
		$("#myTable2").DataTable({
			"lengthMenu": [ 5, 10,25, 50, 75, 100 ],
		}),
		
		</script>						
</body>
</html>