<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Group</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
  List<Object[]> list =(List<Object[]>) request.getAttribute("divisiongroup");
%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Group List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						 <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> 					
						<li class="breadcrumb-item active " aria-current="page">Group List</li>
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
				<div class="card-body ">			
					<form action="DivisionGroup.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>						
						<div class="table-responsive">					
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th> Select        </th>
										<th> Group Code    </th>
										<th> Group Name    </th>
										<th> Group Head </th>
										<th> Department Name   </th> 
									</tr>
								</thead>
								<tbody>
									<% if(list!=null){ for(Object[] obj:list){ %>
										<tr>
											<td style="text-align: center;"><input type="radio" name="groupId" value="<%=obj[0] %>"> </td>
											<td style="text-align: center;"><%=obj[1]%></td>
											<td><%=obj[2]%></td>
											<td style="text-align: center;"><%=obj[3]+", "+obj[4]%> </td>
											<td><%=obj[5]%> </td>
										</tr>
									 <%} }%>
								</tbody>
							</table>
						</div>			
						<div class="row text-center">
						<div class="col-md-12">
						
							<button type="submit" class="btn btn-sm add-btn" name="action" value="ADD"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
						</div>
						</div>
			   </form>		
			  </div>
			  </div>
		   	 </div>				
	        </div>
	       
</body>
<script type="text/javascript">
function Edit(myfrm) {

	var fields = $("input[name='groupId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Group Code ");

		event.preventDefault();
		return false;
	}
	return true;
}
</script>
</body>
</html>