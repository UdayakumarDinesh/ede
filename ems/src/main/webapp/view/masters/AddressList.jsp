<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Address List</title>
<jsp:include page="../static/header.jsp"></jsp:include>

<style type="text/css">
.card{
    margin-bottom: 10px;
}

/* .btn-group button {
  
  border: 1px solid blue; /* Green border */
  color: 	black; /* White text */
  padding: 10px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  float: right; /* Float the buttons side by side */
}

/* Clear floats (clearfix hack) */
.btn-group:after {
  content: "";
  clear: both;
  display: table;
}

.btn-group button:not(:last-child) {
  border-right: right; /* Prevent double borders */
} */

/* Add a background color on hover */
/* .btn-group button:hover {
  background-color: #3e8e41;
} */
</style>
</head>
<body>
<%
Object[] perAddress   = (Object[])request.getAttribute("perAddress");
Object[] empdata      = (Object[])request.getAttribute("Empdata");
Object[] kinAddress   = (Object[])request.getAttribute("kinAddress");
Object[] EmeAddress   = (Object[])request.getAttribute("EmeAddress");
List<Object[]> resAddress = (List<Object[]>)request.getAttribute("ResAddress");
%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>Address List<small><b>&nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%>(<%=empdata[1]%>)<%}%>
						</b></small></h5>
			</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Address List</li>
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
					
					<h5>Permanent Address</h5>
					<hr>
					<%if(perAddress!=null){ %>
					<table class="table table-hover table-striped  table-condensed  table-bordered"  id="myTable1">
					<tbody>
					    <tr align="center">
							<th>Per.Address</th>
							<th>Mobile No.</th>
							<th>State</th>
							<th>City</th>	
							<th>Per.From</th>
							<th>Edit</th>
					   </tr>
					    <tr align="center">
							<td><%=perAddress[2]%></td>
							<td><%=perAddress[4]%></td>
							<td><%=perAddress[7]%></td>
							<td><%=perAddress[8]%></td>	
							<td><%=DateTimeFormatUtil.SqlToRegularDate(perAddress[3].toString())%></td>
							<td style="padding-top:5px; padding-bottom: 5px;">
							<form action="AddEditPerAddress.htm" method="GET">
							 <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
						<button type="submit" class="btn btn-sm" name="Action" value="EDITPerAddress"  data-toggle="tooltip" data-placement="top" title="Edit">
							<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i></button>	</form></td>
					   </tr>
					</tbody>
					</table>
						<%}else{%>
				<form action="AddEditPerAddress.htm" method="GET">
                     <button  type="submit" name="Action" value="AddAddress"  class="btn btn-sm add-btn" style="margin-bottom:12px;" > Add Permanent Address</button>
                     <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
                </form>
					<%}%>		
					</div>
					</div>					
	
					<div class="card">					
					<div class="card-body">
							<h5>Residential Address List</h5>
					<hr>
					<form action="ResAddressAddEdit.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable2"> 
					<thead>
									
					    <tr align="center">
					        <th>Select</th>
							<th>Res.Address</th>
							<th>Res.From</th>
							<th>Mobile No.</th>	
							<th>Email</th>
							<th>Ext No</th>
					   </tr>
					   </thead>
					   <tbody>
					   		<%for(Object[] obj : resAddress){ %>
							<tr align="center">
							<td style="text-align: center;"><input type="radio" name="addressid" value="<%=obj[1]%>"> </td>					    
						    <td><%=obj[2]%></td>
							<td><%=obj[3]%></td>
							<td><%=obj[4]%></td>
							<td><%=obj[6]%></td>
							<td><%=obj[7]%></td>		
					   </tr>
					   <%} %>
					</tbody>
					</table>
					</div>		
						<div class="row text-center">
						<div class="col-md-12">
						     <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
					    	<button type="submit" class="btn btn-sm delete-btn" name="Action" value="DELETE" Onclick="Delete(empForm)" >DELETE </button>
					    </div>
					    </div>
				</form>
						
					</div>
					
					</div>
					
					
					<div class="card" >		
				<div class="card-body " >
					
					<h5>Next kin Address</h5>
					<hr>
					<%if(kinAddress!=null){ %>
					<table class="table table-hover table-striped  table-condensed  table-bordered"  id="myTable3">
					<tbody>
					    <tr align="center">
							<th>NextKin.Address</th>
							<th>Mobile No.</th>
							<th>State</th>
							<th>City</th>	
							<th>Emer.From</th>
							<th>Edit</th>
					   </tr>
					    <tr align="center">
							<td><%=kinAddress[2]%></td>
							<td><%=kinAddress[4]%></td>
							<td><%=kinAddress[7]%></td>
							<td><%=kinAddress[8]%></td>	
							<td><%=DateTimeFormatUtil.SqlToRegularDate(kinAddress[3].toString())%></td>
							<td style="padding-top:5px; padding-bottom: 5px;">
							<form action="KinAddressAddEdit.htm" method="GET">
							 <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
						<button type="submit" class="btn btn-sm" name="Action" value="EDITNextKinAdd"  data-toggle="tooltip" data-placement="top" title="Edit">
							<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i></button>	</form></td>
					   </tr>
					</tbody>
					</table>
						<%}else{%>
				<form action="KinAddressAddEdit.htm" method="GET">
                     <button  type="submit" name="Action" value="AddAddress"  class="btn btn-sm add-btn" style="margin-bottom:12px;" > Add Next kin Address</button>
                     <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
                </form>
					<%}%>		
					</div>
					</div>
					
					<div class="card" >		
				<div class="card-body " >
					
					<h5>Emergency Address</h5>
					<hr>
					<%if(EmeAddress!=null){ %>
					<table class="table table-hover table-striped  table-condensed  table-bordered"  id="myTable4">
					<tbody>
					    <tr align="center">
							<th>Emer.Address</th>
							<th>Mobile No.</th>
							<th>State</th>
							<th>City</th>	
							<th>Per.From</th>
							<th>Edit</th>
					   </tr>
					    <tr align="center">
							<td><%=EmeAddress[2]%></td>
							<td><%=EmeAddress[4]%></td>
							<td><%=EmeAddress[7]%></td>
							<td><%=EmeAddress[8]%></td>	
							<td><%=DateTimeFormatUtil.SqlToRegularDate(EmeAddress[3].toString())%></td>
							<td style="padding-top:5px; padding-bottom: 5px;">
							<form action="EmecAddressAddEdit.htm" method="GET">
							 <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
						<button type="submit" class="btn btn-sm" name="Action" value="EDITEmecAddress"  data-toggle="tooltip" data-placement="top" title="Edit">
							<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i></button>	</form></td>
					   </tr>
					</tbody>
					</table>
						<%}else{%>
				<form action="EmecAddressAddEdit.htm" method="GET">
                     <button  type="submit" name="Action" value="AddAddress"  class="btn btn-sm add-btn" style="margin-bottom:12px;" > Add Emergency Address</button>
                     <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
                </form>
					<%}%>		
					</div>
					</div>
					
				<!-- <div class="btn-group">
					<div class="col-md-12">
				 	<button type="submit" class="btn btnclr"  name="family" value="family" formaction="FamilyMembersList.htm" Onclick="Edit(empForm)">Family</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)">Education</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)">Appointment</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)">Awards</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)">Property</button>
					<button type="submit" class="btn btnclr" name="address" value="address" formaction="Address.htm" Onclick="Edit(empForm)">Address</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)"> Publication</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)">Passport</button>
					</div>
				</div>  -->
	</div>
</div>
<script src="webresources/js/master.js" type="text/javascript"></script>
<script type="text/javascript">
function Edit(myfrm) {

	var fields = $("input[name='addressid']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Employee ");

		event.preventDefault();
		return false;
	}
	return true;
}

function Delete(myfrm) { 
	
	var fields = $("input[name='addressid']").serializeArray();

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
<script type="text/javascript">
$("#myTable2").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});


</script>
</body>
</html>


