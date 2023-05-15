
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Bank Detail</title>
</head>
<body>

	<%
	Object[] oneVehicle = (Object[]) request.getAttribute("oneVehicle");
	Object[] empNameAndDesi = (Object[]) request.getAttribute("empNameAndDesi");
	%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>Vehicle Parking <small><b>&nbsp; - &nbsp;<%if(empNameAndDesi!=null){%> <%=empNameAndDesi[0]%> (<%=empNameAndDesi[1]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="BankDetails.htm">
							Bank Details </a></li>
					<li class="breadcrumb-item active " aria-current="page"><%if (oneVehicle != null) {%> Edit<%} else {%> Add
						<%}
						%>
						Vehicle Parking</li>
				</ol>
			</div>
		</div>
	</div>

	<div class="page card dashboard-card">
		<div class="card-body">
			<div class="card">
				<%
				if (oneVehicle != null) {
				%>
				<form action="VehicleParkAddEditSave.htm">
					<input type="hidden" name="vehicleAppId"
						value="<%=oneVehicle[0]%>">
					<%
					} else {
					%>
					<form action="VehicleParkAddEditSave.htm">
						<%
						}
						%>
						<div class="card-body " align="center">

							<div class="form-group">
								<div class="table-responsive">
									<table class="table table-bordered table-hover table-striped table-condensed" style="width: 65%;">

										<tr>
											<th><label>Vehicle No : <span class="mandatory"
													style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Vehicle No" type="text" id="" 
												name="vehicleNo"
												value="<%if (oneVehicle != null) {%><%=oneVehicle[3]%><%}%>"
												required="required" maxlength="255"
												style="font-size: 15px; text-transform: capitalize;"></td>
										</tr>
										<tr>
											<th><label>From Date & Time : <span class="mandatory"
													style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" 
												placeholder=" Enter From Date & Time" type="datetime-local" 
												name="FromDateTime" 
												value="<%if (oneVehicle != null) {%><%=oneVehicle[4]%><%}%>"
												required="required" style="font-size: 15px;"></td>
										</tr>
										<tr>
											<th><label>To Date & Time :<span class="mandatory"
													style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder="Enter To Date & Time" type="datetime-local"
												name="ToDateTime"
												value="<%if (oneVehicle != null) {%><%=oneVehicle[5]%><%}%>"
												required="required" style="font-size: 15px;"></td>
										</tr>
									
									</table>
									<%
									if (oneVehicle != null) {
									%>
									<button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Submit?');"
										name="Action" value="Edit" onclick="">SUBMIT</button>
									<%
									} else {
									%>
									<button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Submit?');"
										name="Action" value="Add" onclick="">SUBMIT</button>
									<%
									}
									%>
								</div>
							</div>
						</div>
					</form>
			</div>
		</div>
	</div>

	<script type="text/javascript"></script>
</body>
</html>