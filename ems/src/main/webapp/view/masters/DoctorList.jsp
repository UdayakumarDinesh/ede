<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Doctor List</title>
</head>
<body>
<%List<Object[]>  doctorlist = (List<Object[]>)request.getAttribute("doctorlist");

%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Doctor List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">Doctor List</li>
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
				
					<form action="##" method="POST" id="empForm" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
								<thead>
									<tr>
										<th>Select</th>
										<th>Name </th>
										<th>Address </th>
										<th>Contact Details</th>																		
									</tr>
								</thead>
								<tbody>
									<%if(doctorlist!=null && doctorlist.size()>0){ 
										for(Object[] obj : doctorlist){
									%>
										<tr>
											<td style="text-align:center;  width: 5%;"> <input type="radio" name="doctorId" value="<%=obj[0]%>"> </td>
											<td ><%=obj[1]%></td>
										    <td ><%=obj[3]%></td>
											<td ><%=obj[4]%></td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						
						<button type="submit" class="btn btn-sm add-btn" formaction="DoctorList.htm" name="action" value="ADD" >ADD</button>
						<button type="submit" class="btn btn-sm edit-btn" formaction="DoctorList.htm" name="action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
								
					    </div>						 
					</div>
					
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	        </div>
</body>
<script type="text/javascript">
function Edit(empForm)
{
	var fields = $("input[name='doctorId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Doctor ");

		event.preventDefault();
		return false;
	}
	return true;
	
}

</script>
</html>