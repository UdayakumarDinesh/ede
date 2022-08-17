<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="java.util.*,java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.Mt.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Vehicle</title>
</head>
<body>
<%
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");List<MtVehicle> vehiclelist = (List<MtVehicle>)request.getAttribute("vehiclelist");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>MT Vehicle </h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Vehicle </li>	
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
				<form name="frm" action="MtVehicleC.htm" method="POST" id="empForm">					
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								         <thead>
								                <tr>
									             	 <th>Select</th>
									                <!--  <th>Sr No</th> -->
									                 <th>Vehicle Name</th>
									                 <th>BA NO</th>
									                 <th>Date Of Purchase</th>
									                 <th>Seat No</th>
								               </tr>
								         </thead>
									<%for(MtVehicle Vehicle:vehiclelist){%>
										<tbody>
								      	<tr>
								      	<td align="center"><input type="radio" name="vehicleid" value=<%=Vehicle.getVehicleId() %> autocomplete="off" ></td>
								      	<%-- <td ><%=Vehicle.getVehicleId()%></td> --%>
								      	<td ><%=Vehicle.getVehicleName()%></td>
								      	<td align="center"><%=Vehicle.getBaNo()%></td>
								      	<td align="center"><%=sdf.format(Vehicle.getDateOfPurchase())%></td>
								      	<td ><%=Vehicle.getNoOfSeat()%></td>
								 <%}%>
								 </tr>
								 </tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						   <button type="submit" class="btn btn-sm add-btn " name="action" value="Add"   >ADD </button>
		                   <button type="submit" class="btn btn-sm edit-btn" name="action" value="Edit"  Onclick="Edit(empForm)" >EDIT </button>
                           <button type="submit" class="btn btn-sm delete-btn" name="action" value="Delete" Onclick=" Delete(empForm)" >InActive </button>
						</div>
					</div>
				</form>	
			</div>
		</div>		
		
	</div>
		
</div>

</body>
<script type="text/javascript">

function Edit(myfrm){

	var fields = $("input[name='vehicleid']").serializeArray();

	if (fields.length === 0){
		alert("Please Select Atleast One Vehicle ");

		event.preventDefault();
		return false;
	}
	return true;
}

	function Delete(myfrm){ 
		
		var fields = $("input[name='vehicleid']").serializeArray();

		if (fields.length === 0){
			alert("Please Select Atleast One Vehicle");
			event.preventDefault();
			return false;
		}
		
		var cnf = confirm("Are You Sure To InActive!");

		if(cnf){
			
			document.getElementById("empForm").submit();
			return true;

		}else{
			
			event.preventDefault();
			return false;
		}
	}

</script>
</body>
</html>