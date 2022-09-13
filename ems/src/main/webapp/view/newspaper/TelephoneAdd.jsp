<%@page import="java.util.Map"%>
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
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
</style>
</head>
<body>

	<%
	String name = (String) request.getAttribute("name");
	String designation = (String) request.getAttribute("desig");

	Map<String, String> MapResultofDevices = (Map<String, String>) request.getAttribute("MapResultofDevices");
	Object[] PayLevelAndTeleRectrictAmt = (Object[]) request.getAttribute("PayLevelAndTeleRectrictAmt");
	Object[] TeleSpecialpermission = (Object[]) request.getAttribute("TeleSpecialpermission");
	%>


	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Telephone Claim Add</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="TelephoneDashBoard.htm">Telephone</a></li>
					<li class="breadcrumb-item "><a href="TelephoneList.htm">Telephone List</a></li>
					<li class="breadcrumb-item active " aria-current="page">Telephone Claim Add</li>
				</ol>
			</div>
		</div>
	</div>

	<div align="center">
		<%
		String ses = (String) request.getParameter("result");
		String ses1 = (String) request.getParameter("resultfail");
		if (ses1 != null) {
		%>
		<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
			<%=ses1%>
		</div>

		<%
		}
		if (ses != null) {
		%>

		<div class="alert alert-success" role="alert" style="margin-top: 5px;">
			<%=ses%>
		</div>
		<%
		}
		%>
	</div>


	<div class="page card dashboard-card">

		<div class="card-body" >
			<form action="TeleAddEditClaimSave.htm" method="post" autocomplete="off">
				<div class="row w-100">
					<div class="col-md-6"></div>
					<!-- main header -->
					<div class="col-md-2" style="margin-top: -10px; margin-bottom: -10px;">
						<!-- Month -->
						<div class="form-group">
							<label>Month</label> <select name="ClaimMonth"
								class="form-control input-sm" style="width: 100px;"
								required="required">
								<option value="JAN">JAN</option>
								<option value="FEB">FEB</option>
								<option value="MAR">MAR</option>
								<option value="APR">APR</option>
								<option value="MAY">MAY</option>
								<option value="JUN">JUN</option>
								<option value="JUL">JUL</option>
								<option value="AUG">AUG</option>
								<option value="SEP">SEP</option>
								<option value="OCT">OCT</option>
								<option value="NOV">NOV</option>
								<option value="DEC">DEC</option>

							</select>
						</div>
					</div>
					<!--/// Month -->
					<!-- Year -->
					<div class="col-md-2" style="margin-top: -10px; margin-bottom: -10px;">
						<div class="form-group">
							<label>Year</label>
							<input type="text" name="ClaimYear" class="form-control input-sm selectYear" style="width: 100px;" maxlength="4" required="required">
						</div>
					</div>
					<div class="col-md-2" style="margin-top: -10px; margin-bottom: -10px;">
						<!-- //Year -->

						<!--Broadband Facility  -->
						<div class="form-group">
							<label>BroadBand Facility</label><br> <input type="checkbox" name="IsBroadBand" data-toggle="toggle" data-on="Yes" data-off="No" data-onstyle="success" data-offstyle="danger" data-size="mini">
						</div>
					</div>


				</div>

				<!-- display message -->
				<div id="sp" style="color: Chocolate; text-align: center; font-size: 20px;"></div>

				<div class="card" align="left">
					<div class="card-body" style="padding: 0px;">
						<div class="col-md-12 text-center">
							<br> <b>RE-IMBURSEMENT OF TELEPHONE BILL</b><br> <b>CABS,Bangalore</b>
						</div>


						<div class="col-md-12">
							<div class="row">

								<div class="col-md-3">
									<b>SBI A/c No:</b>
									<%=PayLevelAndTeleRectrictAmt[1]%>
								</div>


								<div class="col-md-3">
									<b>Other A/c No:</b>
									<%=PayLevelAndTeleRectrictAmt[2]%>
								</div>

								<div class="col-md-3">
									<b>GPF/PRAN No:</b><%=PayLevelAndTeleRectrictAmt[3]%>
								</div>


								<div class="col-md-3">
									<b>PayLevel:</b><%=PayLevelAndTeleRectrictAmt[5]%>
								</div>

							</div>
						</div>


						<div class="col-md-12">
							<br>
							<table
								class="table table-hover table-striped  table-condensed  table-bordered "
								style="border-color: green;">
								<thead>
									<tr>
										<th style="text-align: center;" colspan="10">Details</th>
									</tr>
								</thead>

								<tbody>
									<tr>
										<th></th>
										<th colspan="2" class="text-center">Period</th>
										<th rowspan="2">Device</th>
										<th rowspan="2" class="text-center">LandLine/Mob/<br>Broadband
											No.
										</th>
										<th rowspan="2" class="text-center">Bill/Inv No.</th>
										<th rowspan="2" class="text-center">Bill Date</th>
										<th rowspan="2" class="text-center">BillAmount<br>(Without
											Tax)
										</th>
										<th rowspan="2" class="text-center">Tax<br> Amount
										</th>
										<th rowspan="2" class="text-center">Total<br> Amount
										</th>
									</tr>

									<tr>
										<th class="text-center">S.No</th>
										<th class="text-center">From</th>
										<th class="text-center">To</th>
									</tr>

									<%
									int count = 1;
									if (request.getAttribute("MapResultofDevices") != null) {
										for (Map.Entry<String, String> entry : MapResultofDevices.entrySet()) {

											String TeleUsersId = entry.getKey();
											String DeviceNameAndNumber = entry.getValue();

											String[] DeviceNameAndNumberArray = DeviceNameAndNumber.split("_");
											String DeviceName = DeviceNameAndNumberArray[0];
											String DeviceNumber = DeviceNameAndNumberArray[1];
									%>
									<tr>
										<td class="text-center"><%=count%></td>
										<td class="text-center"><input type="text" name="FromDate" class="form-control input-sm currentdate" style="width: 100%;" maxlength="10" required="required">
										</td>
										<td><input type="text" name="ToDate" class="form-control input-sm currentdate" style="width: 100%;" maxlength="10" required="required">
										</td>

										<td><input type="text" value="<%=DeviceName%>" class="form-control input-sm" style="width: 45px;" readonly="readonly"></td>

										<td><input type="text" value="<%=DeviceNumber%>" class="form-control input-sm"  readonly="readonly"> 
										<input type="hidden" name="TeleUsersId" value="<%=TeleUsersId%>"></td>

										<td><input type="text" name="BillNo" class="form-control input-sm "  required="required" maxlength="100"></td>

										<td>
											<input type="text" name="BillDate"  class="form-control input-sm currentdate" style="width: 100%;" maxlength="10" required="required">
										</td>
										<td><input type="text" name="BasicAmount" class="form-control input-sm amount cost-only"  maxlength="6" required="required" onkeydown="crossCheck();" onclick="crossCheck();" onfocus="crossCheck();"></td>
										<td><input type="text" name="TaxAmount" class="form-control input-sm  tax"  required="required" readonly="readonly"></td>
										<td><input type="text" name="TotalAmount" class="form-control input-sm total"  required="required" readonly="readonly"></td>
									</tr>
									<%
									count++;
									}
									}
									%>
								</tbody>

							</table>

							<div class="col-md-12 text-right">
								<b>Admissible Amount:</b>
								<%
								if (TeleSpecialpermission != null) {
								%><%="1416"%>
								<%
								} else {
								%><%=PayLevelAndTeleRectrictAmt[6]%>
								<%
								}
								%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Total
									Bill Amount:</b> <input type="text" name="TotalBasic"
									id="totalAmount" readonly="readonly" style="width: 100px;"
									required="required">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Total
									Tax Amount:</b> <input type="text" name="TotalTax" id="totalTax"
									readonly="readonly" style="width: 100px;" required="required">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Gross
									Total:</b> <input type="text" name="GrossTotal" id="grossTotal"
									readonly="readonly" style="width: 100px;" required="required">
							</div>


						</div>
						<input type="hidden" name="RestrictedAmount" value="<%if (TeleSpecialpermission != null) {%><%="1416"%><%} else {%><%=PayLevelAndTeleRectrictAmt[6]%><%}%>">
						<input type="hidden" name="PayLevelId" value="<%=PayLevelAndTeleRectrictAmt[4]%>">
						<button id="submit" class="btn btn-sm btn-success" style="margin-left: 20px;" name="TeleAddClaimSave" value="TeleAddClaimSave"  onclick="return confirm('Are You Sure to Submit?');" >submit</button>
						<button id="check" type="button" class="btn btn-sm btn-primary" style="margin-left: 20px;" onclick="myFunction()">calculate</button>
						<button class="btn btn-sm btn-info" formaction="TelephoneList.htm" formnovalidate="formnovalidate">Back</button>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					</div>
					<br>
				</div>


			</form>







		</div>



	</div>


	<script>
		$('.currentdate').daterangepicker({
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,
			//"startDate" : new Date(),
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

		$(".selectYear").datepicker({
			autoclose : true,
			format : "yyyy",
			viewMode : "years",
			minViewMode : "years",
			endDate : new Date(),
			
		});
	</script>

	<script>
		$(document).ready(function() {

			$("#submit").hide();

		});

		function crossCheck() {
			$("#submit").hide();
			$("#check").show();
			var sp = document.getElementById("sp");
			sp.innerHTML = " ";
		}

		function myFunction() {

			var TotalAmount = 0.0;
			var TotalTax = 0.0;
			var GrossTotal = 0.0;
			var taxjs = [];

			var Amount = (document.getElementsByClassName("amount"));
			var tax = document.getElementsByClassName("tax");
			var total = document.getElementsByClassName("total");

			for (var i = 0; i < Amount.length; i++) {
				taxjs[i] = parseFloat((Amount[i].value) * 18 / 100);
				TotalAmount += parseFloat(Amount[i].value);
			}

			for (var j = 0; j < taxjs.length; j++) {
				tax[j].value = taxjs[j];
				TotalTax += taxjs[j];
			}

			for (var k = 0; k < Amount.length; k++) {
				total[k].value = (parseFloat(Amount[k].value) + taxjs[k]);
			}

			GrossTotal = TotalAmount + TotalTax;

			document.getElementById("totalAmount").value = Math
					.round(TotalAmount);
			document.getElementById("totalTax").value = Math.round(TotalTax);
			document.getElementById("grossTotal").value = Math
					.round(GrossTotal);

			if (isNaN(TotalAmount) || isNaN(TotalTax) || isNaN(GrossTotal)) {

				var sp = document.getElementById("sp");
				sp.innerHTML = "Please Enter Bill Amount Correctly"
				alert("Please Enter Bill Amount Correctly");
				$("#submit").hide();
				$("#check").show();
			} else {
				$("#submit").show();
				$("#check").hide();
			}

		}
		
		 $('.cost-only').keypress( function (evt) {
				
			    if (evt.which > 31 &&  (evt.which < 48 || evt.which > 57) && evt.which!=46 )
			    {
			        evt.preventDefault();
			    } 
			    
			});
	</script>

</body>
</html>