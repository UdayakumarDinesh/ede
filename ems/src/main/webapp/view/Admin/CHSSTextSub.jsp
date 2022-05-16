<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>CHSS Test Sub </title>
</head>
<body>
<%
List<Object[]> testmain = (List<Object[]>)request.getAttribute("ChssTestMain");
%>




	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Test Sub List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">CHSS Test List</li>
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
			
				<div class="card-body " >
				
					<form action="ChssTestSub.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<th style="width: 10%;">Select</th>
										<th style="width: 25%;"> Code </th>
										<th style="width: 55%;"> Name</th>
										<th style="width: 25%;"> Main Name </th>
										<th style="width: 10%;"><i class="fa fa-inr" aria-hidden="true"></i> Rate </th>
									</tr>
								</thead>
								<tbody>
									<%	
									for(Object[] obj : testmain){ 
									%>
										<tr>
											<td style="text-align: center;"><input type="radio" name="SubId" value="<%=obj[0] %>"> </td>
											<td><%=obj[4] %> </td>
											<td><%=obj[1] %></td>
											<td><%=obj[3] %></td>										
											<td><%=obj[2] %></td>
										</tr>
									 <%} %> 
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						
						
						<button type="submit" class="btn btn-sm add-btn" name="action" value="ADD" >ADD</button>
						<button type="submit" class="btn btn-sm edit-btn" name="action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
								
					    </div>						 
					</div>
					
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	        </div>
	        <script type="text/javascript">
	        function Edit(myfrm) {

	        	var fields = $("input[name='SubId']").serializeArray();

	        	if (fields.length === 0) {
	        		alert("Please Select Atleast One Test ");

	        		event.preventDefault();
	        		return false;
	        	}
	        	return true;
	        }
	        </script>
</body>

</html>