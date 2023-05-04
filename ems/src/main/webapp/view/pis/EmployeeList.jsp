<%@page import="java.util.List"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
 #dis{
 text-align: center;
 }
 
.btn-group1 button {
  background-color: #344b8a; /* Green background */
  color: white; /* White text */
  padding: 5px 20px; /* Some padding */
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
	String logintype = (String)session.getAttribute("LoginType");
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Employee List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						 <li class="breadcrumb-item "><a href="PIS.htm">PIS</a></li> 
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
		
		<div class="card" style="width:106%;">
				<div class="card-body" >
					<form action="#" method="post" id="empForm">
				<div align="right">
				<%if("P".equalsIgnoreCase(logintype)){ %>
				<button type="submit" class="btn btn-sm btnclr"  style="background-color: #9dbf13; color: white; margin-bottom: 1%;" name="address" value="address" formaction="AdminReplyToReqMsg.htm" ><i class="fa-solid fa-message"></i> &nbsp;&nbsp;View Message</button>
				<%} %>
				</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<th>Select</th>
										<th>Sr.No</th>
										<th>EmpNo</th>
										<th>Name</th>
										<th>Designation</th>
										<th>DOB</th>
										<!-- <th>Action</th> -->
									</tr>
								</thead>
								<tbody>
									<%	long slno=0;
									for(Object[] obj : EmployeeDetailsList){ 
									slno++;%>
										<tr>
											<td style="text-align: center;"><input type="radio" name="empid" value="<%=obj[0] %>"> </td>
											<td><%=obj[3]%></td>
											<td><%=obj[1] %></td>
											<td><%=obj[2] %></td>
											<td><%=obj[4] %></td>
											<td style="text-align: center;"><%if(obj[5]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[5].toString())%><%} %></td>
				
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
					
					
					<div class="row text-center" >
						<div class="col-md-12" >
							<button type="submit" class="btn btn-sm add-btn" style="margin-right: 5px;" name="action" value="add" formaction="EmployeeAdd.htm"  >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" style="margin-right: 5px;" name="action" value="edit" formaction="EmployeeEdit.htm" Onclick="Edit(empForm)" >EDIT </button>


							<button type="submit" class="btn btn-sm view-btn" style="background-color: #4b6a9c;" name="action"  value="UpdateSeniority" formaction="UpdateEmployeeSeniority.htm"  Onclick="Edit(empForm)" >UPDATE SENIORITY </button>
							<button type="submit" class="btn btn-sm view-btn" style="background-color: #609966;" formaction="AllowAnnualDeclaration.htm"  Onclick="AnnualDecEdit(empForm)" >Allow Annual Declaration </button>
							<!-- <button type="button" class="btn btn-sm update-btn" name="action" value="view"  >UPDATE </button>
							<button type="button" class="btn btn-sm submit-btn" name="action" value="view"  >SUBMIT </button>
							<button type="button" class="btn btn-sm delete-btn" name="action" value="view"  >DELETE </button>
							<button type="button" class="btn btn-sm print-btn" name="action" value="view"  >print </button> -->

						</div> 
					</div>
					
				 <div  class="btn-group1" id = "dis" style="margin-top:20px;margin-left:-3%;width:112%" align="center">
					<div class="col-md-12">
					 	<button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="family"      value="family"      formaction="FamilyMembersList.htm" Onclick="Edit(empForm)" formmethod="post"><i class="fa-solid fa-user-group"></i> &nbsp;&nbsp;Family</button>
				        <button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="Education"   value="Education"   formaction="EducationList.htm"     Onclick="Edit(empForm)" formmethod="post"><i class="fa-solid fa-user-graduate"></i>&nbsp;&nbsp; Education</button>
					    <button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="Appointment" value="Appointment" formaction="AppointmentList.htm"   Onclick="Edit(empForm)" formmethod="post"><i class="fa-solid fa-calendar-check"></i> &nbsp;&nbsp;Appointment</button>
					    <button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="Awards"      value="Awards"      formaction="AwardsList.htm"        Onclick="Edit(empForm)" formmethod="post"><i class="fa-solid fa-award"></i> &nbsp;&nbsp;Awards</button>
					    <button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="Property"    value="Property"    formaction="PropertyList.htm"      Onclick="Edit(empForm)" formmethod="post"><i class="fa-regular fa-building"></i>&nbsp;&nbsp;Property</button> 
						<button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="address"     value="address"     formaction="Address.htm"           Onclick="Edit(empForm)" formmethod="post"><i class="fa-solid fa-map-location-dot"></i> &nbsp;&nbsp;Address</button>
				        <button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="Publication" value="Publication" formaction="PublicationList.htm"   Onclick="Edit(empForm)" formmethod="post"><i class="fa-solid fa-earth-asia"></i>&nbsp;&nbsp; Publication</button>
						<button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="Passport"    value="Passport"    formaction="PassportList.htm"      Onclick="Edit(empForm)" formmethod="post"><i class="fa-solid fa-passport"></i>&nbsp;&nbsp; Passport</button> 
					    <button type="submit" class="btn btn-sm btnclr"  style="margin-left: 5px;" name="Hometown"     value="hometown"     formaction="Hometown.htm"        Onclick="Edit(empForm)" formmethod="post"><i class="fa fa-home" aria-hidden="true"></i> &nbsp;&nbsp;Hometown</button>
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

	function AnnualDecEdit(myfrm) {

		var fields = $("input[name='empid']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One Employee ");

			event.preventDefault();
			return confirm('Are You Sure To Allow Annual Declaration to this Employee?');
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