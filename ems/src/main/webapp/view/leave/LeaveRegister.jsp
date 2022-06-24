<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
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
.dataTables_info, .dataTables_length{
  text-align: left !important;
}
.modal-dialog {
    max-width:750px;
    margin: 2rem auto;
}
</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Leave Register</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Leave Register</li>
					</ol>
				</div>
			</div>
	</div>	
<%    List<Employee> emplist=(List<Employee>)request.getAttribute("EmpList");
String empno=(String)request.getAttribute("empNo");
String year=(String)request.getAttribute("year");
String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%><center>
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></center>
	<%}if(ses!=null){ %>
	<center>
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div></center>
                    <%} %>

<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >
   <form action="LeaveRegister.htm" method="POST" name="myfrm">
    <div class="row">
    <div class="col-md-7">
    
    </div>
    
    <div class="col-md-3">
    <div class="group-form">
    <select class="form-control  selectpicker" required="required" name="empNo" title="Select Employee" data-live-search="ture" id="empNo">
    <%for(Employee emp:emplist){ %>
    <option value="<%=emp.getEmpNo() %>" <%if(emp.getEmpId().equals(empno)){ %> selected="selected" <%} %>><%=emp.getEmpName() %></option>
    <%} %>
    </select>
    
    </div>
    </div>
    <div class="col-md-1">
    <div class="group-form">
 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
    <input class="form-control  form-control" type="text" id="year"  value="<%=year %>" name="Year">
    </div>
    </div>
    <div class="col-md-1">
    <input type="submit" value="Submit" class="btn btn-success btn-sm" style="margin-top: 3px;">
    </div>
    </div>
    
    
    </form>
    <%
    List<Object[]> RegisterList=(List<Object[]>)request.getAttribute("RegisterList");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>
	    <div class="row" style="margin-top:7px;"> 
	    <div class="col-md-12">
	 <div class="table-responsive">
		<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
	  <thead>
	  <tr>
	  <th>SN</th>
	  <th>Employee</th>
	  <th>CL</th>
	  <th>EL</th>
	  <th>HPL</th>
	  <th>CML</th>
	  <th>RH</th>
	  <th>Month</th>
	  <th>Year</th>

	  
	  </tr>
	  </thead>
	  <tbody>
	  <%int count=1;
	  for(Object[] hlo :RegisterList){ %>
	  <tr>
	  <td style="width: 100px;text-align: center;"><%=count %></td> 
	  <td style="width: 30%;text-align: center;"><%=hlo[1] %>,<%=hlo[2] %></td>
	  <td style="text-align: center;width: 100px;"><%=hlo[3] %></td>
	  <td style="text-align: center;width: 100px;"><%=hlo[4] %></td>
	   <td style="text-align: center;width: 100px;"><%=hlo[5] %></td>
	    <td style="text-align: center;width: 100px;"><%=hlo[6] %></td>
	     <td style="text-align: center;width: 100px;"><%=hlo[7] %></td>
	      <td style="text-align: center;width: 100px;"><%=hlo[8] %></td>
	       <td style="text-align: center;width: 100px;"><%=hlo[9] %></td>

	  
	  
	  
	  </tr>
	  <%count++;} %>
	  </tbody>
	   </table>
	   
	   </div>
	  
       </div>
       </div>
       

	   </div>
	   </div>
	

<script type="text/javascript">

$('#year').datepicker({
	 format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    autoclose: true,
	    todayHighlight: true
});

</script>
</body>
</html>