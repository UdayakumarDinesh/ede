<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
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
</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Holiday List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Holiday List</li>
					</ol>
				</div>
			</div>
	</div>	
<%String ses=(String)request.getParameter("result"); 
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
   <form action="PisHolidayList.htm" method="POST" name="myfrm">
    <div class="row">
    <div class="col-md-10">
    
    </div>
    <div class="col-md-1">
    <div align="right" class="group-form">
 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
 
    <input class="form-control  form-control" type="text" id="year"  name="Year">
    </div>
    </div>
    <div class="col-md-1">
    <input type="submit" value="Submit" class="btn btn-success btn-sm">
    </div>
    </div>
    
    
    </form>
    <%
    List<Object[]> clist=(List<Object[]>)request.getAttribute("HoliList");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>
	    <div class="row" style="margin-top: 5px;"> 
	    <div class="col-md-12">
	    <form action="PisHolidayForm.htm" method="POST" name="myfrm">
	 <div class="table-responsive">
		<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
	  <thead>
	  <tr>
	  <th>SELECT</th>
	  <th>HOLIDAY DATE</th>
	  <th>HOLIDAY NAME</th>
	  <th>HOLIDAY TYPE</th>
	  
	  </tr>
	  </thead>
	  <tbody>
	  <%for(Object[] hlo :clist){ %>
	  <tr>
	  <td style="width: 100px;text-align: center;"><input type="radio" name="Aid" value=<%=hlo[0]%> ></td> 
	  <td style="width: 150px;text-align: center;"><%=sdf.format(hlo[1]) %></td>
	  <td style="text-align: left;width: 40%;"><%=hlo[2] %></td>
	  <td style="text-align: left;width: 30%;"><%if(hlo[3].toString().equals("G")){ %>General<%} %><%if(hlo[3].toString().equals("R")){ %>Restricted<%} %>
	  <%if(hlo[3].toString().equals("W")){ %>Working Saturday/Sunday<%} %><%if(hlo[3].toString().equals("H")){ %>Holiday For Working Saturday/Sunday<%} %>
	  </td>
	  
	  
	  
	  </tr>
	  <%} %>
	  </tbody>
	   </table>
	   
	   </div>
	  
	   	<div align="center"> 
 	<button type="submit" class="btn btn-info btn-sm" name="sub" value="add" >ADD</button>&nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;
 	 <%if(clist!=null&&clist.size()!=0){ %>
 	<button type="submit" class="btn btn-warning btn-sm" name="sub" value="edit"  onclick="Edit(myfrm)">EDIT</button>&nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;
 	<button type="submit" class="btn btn-danger btn-sm" name="sub" value="delete"  onclick="Delete(myfrm)">DELETE</button>&nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;  &nbsp; &nbsp; &nbsp;
	
	</div> 
	<%} %>
	   </form>
	   </div>
	   </div>
   </div>
   </div>




<script type="text/javascript">
function Edit(myfrm){
	
	 var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	}
	 
	var cnf=confirm("Are U Sure To Edit!");
	  if(cnf){
	
		 
	
		  return true;
	  }
	  
	  
	  else{
		  event.preventDefault();
			return false;
			}
			
	}
function Delete(myfrm){
	

	var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	
	}
	  var cnf=confirm("Are U Sure To Cancel!");
	  if(cnf){
	
	return true;
	
	}
	  else{
		  event.preventDefault();
			return false;
			}
	
	}

$('#year').datepicker({
	 format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    autoclose: true,
	    todayHighlight: true
});
$('#year').datepicker("setDate", new Date());
</script>
</body>
</html>