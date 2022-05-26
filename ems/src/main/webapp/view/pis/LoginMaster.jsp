<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<script src="webresources/js/master.js" type="text/javascript"></script>
</head>
<body>

<%List<Object[]> loginmatser= (List<Object[]> )request.getAttribute("loginmaster");%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Login Master</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item active " aria-current="page">Login List</li>
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
				
					<form action="LoginMasterAddEdit.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<th>Select</th>
										<th>Employee</th>
										<th>User Name</th>
										<th>Division Name</th>
										<th>Login Type</th>
										
									</tr>
								</thead>
								<tbody>
									<%	long slno=0;
									for(Object[] obj : loginmatser){ 
									slno++;%>
										<tr>
											<td style="text-align: center;"><input type="radio" name="loginid" value="<%=obj[0]%>"> </td>
											<td><%=obj[4] %></td>
											<td><%=obj[1] %></td>
											<td><%=obj[2] %></td>
											<td><%=obj[6] %></td>
										</tr>
									<%} %>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
							<button type="submit" class="btn btn-sm add-btn" name="action" value="Add"   >CREATE </button>
							<button type="submit" class="btn btn-sm edit-btn" name="action" value="Edit"  Onclick="Edit(empForm)" >EDIT </button>
                           <button type="button" class="btn btn-sm delete-btn" name="action" value="Delete" Onclick=" Delete(empForm)" >DELETE </button>
                           <!--  <button type="submit" class="btn btn-sm view-btn" name="action" value="view" formaction="EmployeeDetails.htm" Onclick="Edit(empForm)" >VIEW </button> -->
						<button type="submit" class="btn btn-sm reset-btn"  name="action" value="ResetPwd" formaction="Resetpassword.htm" Onclick="Edit(empForm)" >RESET PASSWORD </button>
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

		var fields = $("input[name='loginid']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One Employee ");

			event.preventDefault();
			return false;
		}
		return true;
	}

	function Delete(myfrm) { 
		
		var fields = $("input[name='loginid']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One Employee");
			event.preventDefault();
			return false;
		}
		var cnf = confirm("Are You Sure To Delete!");

		if (cnf) {
			
			document.getElementById("empForm").submit();
			return true;

		} else {
			
			event.preventDefault();
			return false;
		}
	}
	

</script>

</html>