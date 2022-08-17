<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.time.LocalDate ,java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="com.vts.ems.Mt.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>MT Vehicle</title>
</head>
<body>
	    
<%MtVehicle vehiclelist=(MtVehicle)request.getAttribute("vehicledata"); 
	%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
			<%if(vehiclelist!=null){ %>
				<h5>Edit Vehicle</h5>
				<%}else{%>
				<h5>Add Vehicle</h5>
			<%}%>	
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item "><a href="MtVehicle.htm">MT Vehicle List</a></li>
						<%if(vehiclelist!=null){ %>
						<li class="breadcrumb-item active " aria-current="page">Edit Vehicle</li>
						<%}else{%>
						<li class="breadcrumb-item active " aria-current="page">Add Vehicle</li>
						<%}%>
					</ol>
				</div>
			</div>
</div>

<div class=" page card dashboard-card">

	<div class="card-body" >
	<div class="row">
	<div class="col-12">
		<div class="card" >					
			<div class="card-body">
				<form action="MtVehicleAddEdit.htm" method="POST"autocomplete="off"  id="MtVehicle">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


		
		 <div class="form-group">
		 <div class="row"  >
		
		<div class="col-md-1" align="left"></div>
				<div class="col-md-3" align="left">
						<label >Vehicle Name :<span class="mandatory" style="color: red;">*</span></label>
						<div class=" input-group">
						<input  class="form-control input-sm " type="text" <%if(vehiclelist!=null && vehiclelist.getVehicleName()!=null){%>value="<%=vehiclelist.getVehicleName()%>"<%}%>  placeholder="Enter Vehicle Name" id="vehiclename" name="VehicleName" required="required">
				   </div>  
				</div>
				<div class="col-md-2" align="left">
						<label >BA No:<span class="mandatory" style="color: red;">*</span></label>
					<div class=" input-group">
						 <input  class="form-control  "  placeholder="Enter Ba No" <%if(vehiclelist!=null && vehiclelist.getBaNo()!=null){%>value="<%=vehiclelist.getBaNo()%>"<%}%> name="BANO" required="required" id="bano"> 
					</div>
				</div>
				
				<div class="col-md-2" align="left">
						<label >Date Of Purchase :<span class="mandatory" style="color: red;">*</span></label>
						<div class=" input-group">
						<input  class="form-control input-sm mydate"   id="dop" name="DOP" required="required">
				       </div>
				</div>

				 <div class="col-md-2" align="left">
						<label >No Of Seat :<span class="mandatory" style="color: red;">*</span></label>
						 <div class=" input-group">
						 <input  class="form-control"   <%if(vehiclelist!=null && vehiclelist.getNoOfSeat()>0){%>value="<%=vehiclelist.getNoOfSeat()%>"<%}%> type="number" name="NoOfSeat" id="nofseat" required="required" >
				       </div>
		 		</div>
		 </div>
		</div>
				<div align="center">
				<input type="hidden" name="vehicleid" <%if(vehiclelist!=null && vehiclelist.getVehicleId()!=0){%>value="<%=vehiclelist.getVehicleId()%>"<%}%>>
				 <button type="button" class="btn btn-sm submit-btn"  onclick="return CheckData()">Submit</button>
			   </div>
			
			
			</form>
			</div>
			</div>
			</div>
			 </div>
	 </div>
	 </div>
</body>
<script type="text/javascript">
$('#dop').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	<%if(vehiclelist!=null&& vehiclelist.getDateOfPurchase()!=null){%>
	"startDate" : new Date('<%=vehiclelist.getDateOfPurchase()%>'),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

function CheckData(){
	
	var name  = $('#vehiclename').val();
	var bano = $('#bano').val();
	var nofseat = $('#nofseat').val();
	
	
	 if (name==""||name==null ||name=="null"){
		alert("Please Enter Vehicle Name!");
		return false;
	}else if (bano==""||bano==null ||bano=="null"){
		alert("Please Enter Vehicle Ba No!");
		return false;
	}else if ( nofseat==""||nofseat==null ||nofseat=="null"){
		alert("Please Enter Number of Seat !");
		return false;
	}else{
		if(confirm("Are you sure to Submit!")){
			$('#MtVehicle').submit();
			return true;
		}else{
			return false;
		}
	}
}
</script>
</html>