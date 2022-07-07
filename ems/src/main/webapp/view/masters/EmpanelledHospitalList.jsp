<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Empanelled Hospital</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%List<Object[]>  empanelled = (List<Object[]>)request.getAttribute("empanelledhospital");

%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Empanelled Hospital List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<!-- <li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li> -->
						
						<li class="breadcrumb-item active " aria-current="page">Empanelled Hospital List</li>
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
										<th>Hospital Name </th>
										<th>Hospital Address</th>								
									</tr>
								</thead>
								<tbody>
									<%if(empanelled!=null && empanelled.size()>0){ 
										for(Object[] obj : empanelled){
									%>
										<tr>
											<td style="text-align:center;  width: 10%;"> <input type="radio" name="empanelledId" value="<%=obj[0]%>"> </td>
											<td style=" width: 35%;"><%=obj[1]%></td>
											<td style=" width: 45%;"><%=obj[2]%></td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						
						<button type="submit" class="btn btn-sm add-btn" formaction="EmpanneledHospitalList.htm" name="action" value="ADD" >ADD</button>
						<button type="submit" class="btn btn-sm edit-btn" formaction="EmpanneledHospitalList.htm" name="action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
								
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
	var fields = $("input[name='empanelledId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Hospital ");

		event.preventDefault();
		return false;
	}
	return true;
	
}

</script>
</html>