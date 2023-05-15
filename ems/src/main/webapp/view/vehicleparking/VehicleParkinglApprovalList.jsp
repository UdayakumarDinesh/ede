<%@page import="java.text.Format"%>
<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page
	import="java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List, java.text.DateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Vehicle Parking</title>
<style type="text/css">
.trup {
	padding: 6px 10px 6px 10px;
	border-radius: 5px;
	font-size: 14px;
	font-weight: 600;
}

.trdown {
	padding: 0px 10px 5px 10px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-size: 14px;
	font-weight: 600;
}
</style>
</head>
<body>

	<%
	List<Object[]> VehicleParkList = (List<Object[]>) request.getAttribute("VehicleParkList");
	Object[] empNameAndDesi = (Object[]) request.getAttribute("empNameAndDesi");
	
	
	String Pending = (String)request.getAttribute("Pending");
	String fromdate = (String)request.getAttribute("fromdate");
	String todate   = (String)request.getAttribute("todate");
	
	String ses = (String) request.getParameter("result");
	String ses1 = (String) request.getParameter("resultfail");

	SimpleDateFormat time = new SimpleDateFormat("HH:mm");
	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	%>


	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-5">
				<h5>
					Vehicle Parking <small><b>&nbsp; - &nbsp;<%if(empNameAndDesi!=null){%><%=empNameAndDesi[0]%>
							(<%=empNameAndDesi[1]%>)<%}%>
					</b></small>
				</h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item " ><a href="VehicleParking.htm">	
							Vehicle Parking </a></li>	
					<%if(Pending !=null && Pending.equalsIgnoreCase("Pending")) {%>		
					<li class="breadcrumb-item active " aria-current="page" ><a
						href="VehicleParkinglApproval.htm"> Parking Pending List</a></li>
					<%} else { %>
					<li class="breadcrumb-item active " aria-current="page" ><a
						href="VehicleParkinglApproval.htm"> Parking Approved List</a></li>
					<%} %>		
				</ol>
			</div>
		</div>
	</div>

	<div class="page card dashboard-card">

		<div align="center">
			<%
			if (ses1 != null) {
			%>
			<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
				<%=ses1%>
			</div>
			<%
			}
			if (ses != null) {
			%>
			<div class="alert alert-success" role="alert"
				style="margin-top: 5px;">
				<%=ses%>
			</div>
			<%
			}
			%>
		</div>

		<%if(fromdate !=null && todate !=null) {%>
		<div class="card">
			<div class="card-header" style="height: 4rem">
				<form action=VehicleParkinglApproved.htm>
					<div class="row justify-content-end">

						<div class="col-2" align="right">
							<h6>From Date :</h6>
						</div>
						<div class="col-1">
							<input type="text" style="width: 145%;"
								class="form-control input-sm mydate"
								onchange="this.form.submit()" readonly="readonly"
								<%if(fromdate!=null){%> value="<%=fromdate%>" <%}%>
								id="fromdate" name="fromdate" required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>

						<div class="col-2" align="right">
							<h6>To Date :</h6>
						</div>

						<div class="col-1">
							<input type="text" style="width: 145%;"
								class="form-control input-sm mydate"
								onchange="this.form.submit()" readonly="readonly"
								<%if(todate!=null){%> value="<%=todate%>" <%}%> id="todate"
								name="todate" required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>

						<div class="col-3" align="right"></div>


					</div>
				</form>
			</div>
		</div>
		<%} %>

		<div class="card">
			<div class="card-body">
				<!-- <h5>Vehicle Parking List</h5> -->
				<hr>
				<form action="#">
					<div class="table-responsive">
						<table
							class="table table-bordered table-hover table-striped table-condensed"
							id="myTable1">
							<thead>
								<tr>
									<th style="width: 5%;">SN</th>
									<th>Emp No</th>
									<th>Employee Name</th>
									<th>Vehicle No</th>
									<th>From Date</th>
									<th>From Time</th>
									<th>To Date</th>
									<th>To Time</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (VehicleParkList != null && VehicleParkList.size() > 0) {
									int SN=0;
									for (Object ls[] : VehicleParkList) {
								%>
								<%if(fromdate !=null && todate !=null) {%>
								<%if(format.parse(DateTimeFormatUtil.getDateTimeToRegularDate(ls[4].toString())).after(format.parse(fromdate)) && format.parse(DateTimeFormatUtil.getDateTimeToRegularDate(ls[4].toString())).before(format.parse(todate))) { %>

								<tr>
									<td style="text-align: center;"><%=++SN%></td>
									<td><%=ls[1]%></td>
									<td><%=ls[2]%></td>
									<td><%=ls[3]%></td>
									<td style="text-align: center;"><%=DateTimeFormatUtil.getDateTimeToRegularDate(ls[4].toString())%></td>
									<td style="text-align: center;"><%=time.format(ls[4])%></td>
									<td style="text-align: center;"><%=DateTimeFormatUtil.getDateTimeToRegularDate(ls[5].toString())%></td>
									<td style="text-align: center;"><%=time.format(ls[5])%></td>
									<td style="text-align: center;"><button type="submit"
											class="btn btn-sm" formaction="GetVehicleParkingForm.htm"
											name="vehicleAppId" value="<%=ls[0]%>"
											formnovalidate="formnovalidate" data-toggle="tooltip"
											data-placement="top" title="Vehicle Parking Application"
											style="font-weight: 600;">
											<i class="fa-solid fa-eye"></i>
										</button></td>
								</tr>

								<%
								}
								
									}
								else{ %>
									<tr>
									<td style="text-align: center;"><%=++SN%></td>
									<td><%=ls[1]%></td>
									<td><%=ls[2]%></td>
									<td><%=ls[3]%></td>
									<td style="text-align: center;"><%=DateTimeFormatUtil.getDateTimeToRegularDate(ls[4].toString())%></td>
									<td style="text-align: center;"><%=time.format(ls[4])%></td>
									<td style="text-align: center;"><%=DateTimeFormatUtil.getDateTimeToRegularDate(ls[5].toString())%></td>
									<td style="text-align: center;"><%=time.format(ls[5])%></td>
									<td style="text-align: center;"><button type="submit"
											class="btn btn-sm" formaction="GetVehicleParkingForm.htm"
											name="vehicleAppId" value="<%=ls[0]%>"
											formnovalidate="formnovalidate" data-toggle="tooltip"
											data-placement="top" title="Vehicle Parking Application"
											style="font-weight: 600;">
											<i class="fa-solid fa-eye"></i>
										</button></td>
								</tr>
								<% 
								}
									}
								}
								%>
							</tbody>
						</table>
					</div>
					<input type="hidden" name="isApproval" value="Y">
					<%if(fromdate != null ) {%>
					<input type="hidden" name="TypeOfAction" value="">
					<%} else { %>
					<input type="hidden" name="TypeOfAction" value="Pending">
					<%} %>
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#myTable1").DataTable({
			"lengthMenu" : [ 5, 10, 25, 50, 75, 100 ],
			"pagingType" : "simple"
		});

		function EditPer() {

			var fieldsperadd = $("input[name='vehicleAppId']").serializeArray();

			if (fieldsperadd.length === 0) {
				alert("Please Select Atleast One Item");

				event.preventDefault();
				return false;
			}
			return true;
		}

		$('#fromdate').daterangepicker({
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,
			/* "minDate" :datearray,   */
			/* "startDate" : fdate, */
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

			
			$('#todate').daterangepicker({
				"singleDatePicker" : true,
				"linkedCalendars" : false,
				"showCustomRangeLabel" : true,
				"minDate" :$("#fromdate").val(),  
				"cancelClass" : "btn-default",
				showDropdowns : true,
				locale : {
					format : 'DD-MM-YYYY'
				}
			});
	</script>
</body>
</html>