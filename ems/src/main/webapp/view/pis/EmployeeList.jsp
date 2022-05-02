<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<style type="text/css">
 #dis{
 text-align: center;
 }
 
.btn-group1 button {
  background-color: #344b8a; /* Green background */
  color: white; /* White text */
  padding: 10px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  float: left; /* Float the buttons side by side */
}

/* Clear floats (clearfix hack) */
.btn-group1:after {
  content: "";
  clear: both;
  display: table;
}

.btn-group1 button:not(:last-child) {
  border-right:thin;
}

/* Add a background color on hover */
.btn-group1 button:hover {
  background-color: #3e8e41;
}
</style>
</head>
<body>

<%
	List<Object[]> EmployeeDetailsList = (List<Object[]> )request.getAttribute("EmployeeDetailsList") ;
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Employee List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item active " aria-current="page">Employee List</li>
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
				
					<form action="#" method="post" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<th>Select</th>
										<th>SN</th>
										<th>EmpNo</th>
										<th>Name</th>
										<th>Designation</th>
										<th>Division</th>
										<th>Group</th>
										<!-- <th>Action</th> -->
									</tr>
								</thead>
								<tbody>
									<%	long slno=0;
									for(Object[] obj : EmployeeDetailsList){ 
									slno++;%>
										<tr>
											<td style="text-align: center;"><input type="radio" name="empid" value="<%=obj[0] %>"> </td>
											<td><%= slno%>.</td>
											<td><%=obj[1] %></td>
											<td><%=obj[2] %></td>
											<td><%=obj[8] %></td>
											<td><%=obj[5] %></td>
											<td><%=obj[6] %></td>
											<%-- <td>
												<button type="submit" name="empid" value="<%=obj[0] %>"  class="btn btn-sm" data-toggle="tooltip" data-placement="top" title="View Employee Details" > 
													<i class="  fa-solid fa-eye"></i>
												</button>
											</td> --%>
										</tr>
									<%} %>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						
							<button type="submit" class="btn btn-sm add-btn" name="action" value="add" formaction="EmployeeAdd.htm"  >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="action" value="edit" formaction="EmployeeEdit.htm" Onclick="Edit(empForm)" >EDIT </button>

							<!-- <button type="submit" class="btn btn-sm edit-btn" name="action" value="view" formaction="EmployeeDetails.htm" Onclick="ViewEmp(empForm)">VIEW </button> -->

							<button type="submit" class="btn btn-sm view-btn" name="action" value="view" formaction="EmployeeDetails.htm" Onclick="Edit(empForm)" >VIEW </button>
							
							<!-- <button type="button" class="btn btn-sm update-btn" name="action" value="view"  >UPDATE </button>
							<button type="button" class="btn btn-sm submit-btn" name="action" value="view"  >SUBMIT </button>
							<button type="button" class="btn btn-sm delete-btn" name="action" value="view"  >DELETE </button>
							<button type="button" class="btn btn-sm print-btn" name="action" value="view"  >print </button> -->

						</div>
						 
					</div>
					
					<div  class="btn-group1" id = "dis" style="margin-top:20px;" align="center">
					<div class="col-md-12">
				 	<button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;"  name="family" value="family" formaction="FamilyMembersList.htm" Onclick="Edit(empForm)"><i class="fa-solid fa-user-group"></i> &nbsp;&nbsp;Family</button>
			   <!-- <button type="submit" class="btn btnclr"  style="margin-left: 5px;" Onclick="Edit(empForm)">Education</button>
					<button type="submit" class="btn btnclr"  style="margin-left: 5px;" Onclick="Edit(empForm)">Appointment</button>
					<button type="submit" class="btn btnclr"  style="margin-left: 5px;" Onclick="Edit(empForm)">Awards</button>
					<button type="submit" class="btn btnclr"  style="margin-left: 5px;" Onclick="Edit(empForm)">Property</button> -->
					<button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="address" value="address" formaction="Address.htm" Onclick="Edit(empForm)"><i class="fa-solid fa-map-location-dot"></i> &nbsp;&nbsp;Address</button>
			   <!-- <button type="submit" class="btn btnclr"  style="margin-left: 5px;" Onclick="Edit(empForm)"> Publication</button>
					<button type="submit" class="btn btnclr"  style="margin-left: 5px;" Onclick="Edit(empForm)">Passport</button> -->
					</div>
				</div> 
					
					
				</form>	
				
				
			</div>

			</div>		
		
	</div>

 </div>

<script type="text/javascript">

	function ViewEmp(myfrm) {

		var fields = $("input[name='empid']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One Employee ");

			event.preventDefault();
			return false;
		}
		return true;
	}

	function Edit(myfrm) {

		var fields = $("input[name='empid']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One Employee ");

			event.preventDefault();
			return false;
		}
		return true;
	}

	function Delete(myfrm) {

		var fields = $("input[name='empid']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One Employee");
			event.preventDefault();
			return false;
		}
		var cnf = confirm("Are You Sure To Delete!");

		if (cnf) {

			return true;

		} else {
			event.preventDefault();
			return false;
		}

	}
</script>


</body>
</html>