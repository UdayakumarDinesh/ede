<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List, com.vts.ems.utils.DateTimeFormatUtil"%>
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
	/* List<Object[]> bankDetailList = (List<Object[]>) request.getAttribute("BankDetailList"); */
	Object[] oneBankDeatil = (Object[]) request.getAttribute("oneBankDeatil");
	Object[] empNameAndDesi=(Object[]) request.getAttribute("empNameAndDesi");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>Bank Details <small><b>&nbsp; - &nbsp;<%if(empNameAndDesi !=null) %> <%=empNameAndDesi[0] %> (<%=empNameAndDesi[1] %>)</b></small></h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="BankDetails.htm">
							Bank Details </a></li>	
					<li class="breadcrumb-item active " aria-current="page">Add
						Bank Details</li>
				</ol>
			</div>
		</div>
	</div>

	<div class="page card dashboard-card">
		<div class="card-body">
			<div class="card">
				<%
				if (oneBankDeatil != null) {
				%>
				<form action="BankDetailEditSave.htm">
					<input type="hidden" name="bankId"
						value="<%=oneBankDeatil[0]%>">
					<%
					} else {
					%>
					<form action="BankDetailAddSave.htm">
						<%
						}
						%>
						<div class="card-body " align="center">

							<div class="form-group">
								<div class="table-responsive">
									<table
										class="table table-bordered table-hover table-striped table-condensed"
										style="width: 65%;">


										<tr>
											<th><label>Bank Name: <span class="mandatory"
													style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Your Bank Name" type="text" id="" 
												name="BankName"
												value="<%if (oneBankDeatil != null) {%><%=oneBankDeatil[2]%><%}%>"
												required="required" maxlength="255"
												style="font-size: 15px; text-transform: capitalize;"></td>
										</tr>
										<tr>
											<th><label>Branch Name: <span class="mandatory"
													style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" 
												placeholder=" Enter Your Bank Branch Name" type="text" id="" 
												name="BranchName" 
												value="<%if (oneBankDeatil != null) {%><%=oneBankDeatil[3]%><%}%>"
												maxlength="255" required="required" style="font-size: 15px;"></td>
										</tr>
										<tr>
											<th><label>IFSC Code:<span class="mandatory"
													style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder="Enter Your IFSC Code" type="text" id=""
												name="IFSC"
												value="<%if (oneBankDeatil != null) {%><%=oneBankDeatil[4]%><%}%>"
												required="required" maxlength="50" style="font-size: 15px;"></td>
										</tr>
										<tr>
											<th><label>Account No:<span class="mandatory"
													style="color: red;">*</span></label></th>
											<td><input class="form-control"
												placeholder="Enter Your Account No" type="number" id=""
												name="accNo"
												value="<%if (oneBankDeatil != null) {%><%=oneBankDeatil[5]%><%}%>"
												required="required" maxlength="50" style="font-size: 15px;"></td>
										</tr>
										<tr>
											<th><label>Valid From:<span class="mandatory"
													style="color: red;">*</span></label></th>
											<td><input class="form-control"
												type="text" id="datepicker"
												name="ValidFrom"
												value="<%if (oneBankDeatil != null) {%><%=rdf.format(sdf.parse(oneBankDeatil[6].toString()))%><%}%>"
												required="required" maxlength="30" style="font-size: 15px;" readonly></td>
										</tr>


									</table>
									<%
									if (oneBankDeatil != null) {
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
						
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	
	     $("#datepicker").daterangepicker({ 
	         "minDate": new Date(<%-- <%=DateTimeFormatUtil.fromDatabaseToActual(oneBankDeatil[6].toString())%> --%>),
	     "singleDatePicker" : true,
	 	"linkedCalendars" : false,
	 	"showCustomRangeLabel" : true,
	 	//"minDate" :new Date(), 
	 	"cancelClass" : "btn-default",
	 	showDropdowns : true,
	 	locale : {
	 		format : 'DD-MM-YYYY'
	 	}
	     });

	/* $(function() {
	    $( "#datepicker" ).datepicker({ minDate: 0});
	  }); */


	</script>
</body>
</html>