<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*" %> <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>DO </title>
</head>
<body>
<%List<Object[]>  circular = (List<Object[]>)request.getAttribute("circulatlist");
String fromdate = (String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");

%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>ADD DO</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="DoDashboard.htm"> DO </a></li> 
						<li class="breadcrumb-item active " aria-current="page">ADD DO </li>
					</ol>
				</div>
		</div>
</div>
		 	 <div class="page card dashboard-card">
	<div class="card-body" >		
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
		
			<div class="card" >

				<div class="card-body main-card  " >
				
					<form action="##" method="POST" id="empForm" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
								<thead>
									<tr>
										<th>Select</th>
										<th>DO Part Number</th>
										<th>Item Number </th>
										<th>Item Subject</th>
										<th>Name</th>										
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">					
							<button type="submit" class="btn btn-sm add-btn" formaction="##" name="action" value="ADD" >ADD</button>
							<button type="submit" class="btn btn-sm edit-btn" formaction="##" name="action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
							<button type="submit" class="btn btn-sm add-btn" formaction="##" name="action" value="ADD" >ADD ITEM</button>									
					    </div>						 
				</div>					
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	       </div>
</body>
</html>