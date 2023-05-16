<%@page import="java.text.Format"%>
<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Bank Details</title>
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
	List<Object[]> BankDetailList = (List<Object[]>) request.getAttribute("BankDetailList");
	Object[] empNameAndDesi=(Object[]) request.getAttribute("empNameAndDesi");
	
	List<String> DGMs = (List<String>) request.getAttribute("DGMs");
	List<String> PAndAs = (List<String>) request.getAttribute("PAndAs");
	
	String EmpNo = (String) request.getAttribute("EmpNo");
	String EmpName = (String) request.getAttribute("EmpName");
	String DGMName = (String) request.getAttribute("DGMName");
	String PANDAName = (String) request.getAttribute("PANDAName");
	
	String ses = (String) request.getParameter("result");
	String ses1 = (String) request.getParameter("resultfail");
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-5">
				<h5>Bank Details <small><b>&nbsp; - &nbsp;<%if(empNameAndDesi !=null) %> <%=empNameAndDesi[0] %> (<%=empNameAndDesi[1] %>)</b></small> </h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<!-- <li class="breadcrumb-item "><a href="BankDetails.htm">
							Bank Details </a></li> -->
					<li class="breadcrumb-item active " aria-current="page">Bank Details
						List</li>
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



		<div class="card">
			<div class="card-body">
				<h5>Bank Detail List</h5>
				<hr>
				<form action="#">
					<div class="table-responsive">
						<table
							class="table table-bordered table-hover table-striped table-condensed"
							id="myTable1">
							<thead>
								<tr>
									<th style="width: 5%;">Select</th>
									<!-- <th>Employee Name</th> -->
									<th>Bank Name</th>
									<th>Branch</th>
									<th>IFSC</th>
									<th>Account No</th>
									<th>Valid From</th>
									<th>Valid To</th>
									<th>Status</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (BankDetailList != null && BankDetailList.size() > 0) {
									for (Object ls[] : BankDetailList) {
								%>
								<tr>
									<%
									if (ls[11].toString().equalsIgnoreCase("INI")) {
									%>
									<td style="text-align: center;"><input type="radio"
										name="bankId" value="<%=ls[0]%>" required="required"></td>
									<%
									} else {
									%>
									<td style="text-align: center;"><input type="radio"
										name="bankId" value="<%=ls[0]%>" required="required"
										disabled="disabled"></td>
									<%
									}
									%>
									<td><%=ls[2]%></td>
									<td><%=ls[3]%></td>
									<td><%=ls[4]%></td>
									<td><%=ls[5]%></td>
									<td style="text-align: center;"><%=sdf.format(ls[6])%></td>
									<%
									if (ls[7] != null) {
									%>
									<td style="text-align: center;"><%=sdf.format(ls[7])%></td>
									<%
									} else {
									%>
									<td style="text-align: center;">---</td>
									<%
									}
									%>
									<td>
										<%
										if (ls[10] != null) {
										%> <%
 if (ls[8] != null && ls[8].toString().equalsIgnoreCase("A")) {
 %>
										<button type="submit" class="btn btn-sm btn-link w-100"
											formaction="BankDetTransacStatus.htm" value="<%=ls[0]%>"
											name="bankId" data-toggle="tooltip" data-placement="top"
											title="Transaction History" formnovalidate="formnovalidate"
											style="color: green; font-weight: 600;" formtarget="_blank">
											&nbsp; Approved <i
												class="fa-solid fa-arrow-up-right-from-square"
												style="float: right;"></i>
										</button> <%
 } else if (ls[8] != null && ls[8].toString().equalsIgnoreCase("E")) {
 %>
										<button type="submit" class="btn btn-sm btn-link w-100"
											formaction="BankDetTransacStatus.htm" value="<%=ls[0]%>"
											name="bankId" data-toggle="tooltip" data-placement="top"
											title="Transaction History" formnovalidate="formnovalidate"
											style="color: red; font-weight: 600;" formtarget="_blank">
											&nbsp; Expired <i
												class="fa-solid fa-arrow-up-right-from-square"
												style="float: right;"></i>
										</button> <%
 } else {
 %>
										<button type="submit" class="btn btn-sm btn-link w-100"
											formaction="BankDetTransacStatus.htm" value="<%=ls[0]%>"
											name="bankId" data-toggle="tooltip" data-placement="top"
											title="Transaction History" formnovalidate="formnovalidate"
											style=" color: <%=ls[0]%>; font-weight: 600;"
											formtarget="_blank">
											&nbsp;
											<%=ls[10]%>
											<i class="fa-solid fa-arrow-up-right-from-square"
												style="float: right;"></i>
										</button> <%
 }
 %> <%
 }
 %>
									</td>
									<td align="center"><button type="submit" class="btn btn-sm view-icon"
											formaction="GetBankDetailForm.htm" name="bankId"
											value="<%=ls[0]%>" formnovalidate="formnovalidate"
											data-toggle="tooltip" data-placement="top"
											title="Form For Bank Details Change"
											style="font-weight: 600;">
											<i class="fa-solid fa-eye"></i>
										</button>
										<button type="submit" class="btn btn-sm" name="bankId"
											value="<%=ls[0]%>" formaction="BankFormDownload.htm"
											formtarget="blank" formnovalidate="formnovalidate"
											data-toggle="tooltip" data-placement="top" title="Download">
											<i style="color: #019267" class="fa-solid fa-download"></i>
										</button></td>
								</tr>
								<%
								}
								}
								%>
							</tbody>
						</table>
					</div>
					<div>
						<div align="center">
							<button type="submit" class="btn btn-sm add-btn"
								formnovalidate="formnovalidate"
								formaction="BankDetailAddEdit.htm" name="Action" value="Add">Add</button>
							<%
							if (BankDetailList != null && BankDetailList.size() > 0) {
							%>
							<button type="submit" formaction="BankDetailAddEdit.htm"
								class="btn btn-sm edit-btn" name="Action" value="Edit"
								Onclick="EditPer()">Edit</button>
							<%
							}
							%>
						</div>
					</div>
				</form>
				<hr>
				<div class="row">
					<div class="col-md-12" style="text-align: center;">
						<b>Approval Flow</b>
					</div>
				</div>

				<div class="row"
					style="text-align: center; padding-top: 10px; padding-bottom: 15px;">
					<table align="center">
						<tr>
							<%if(  EmpName !=null ) {%>
							<td class="trup" style="background: #E8E46E;">User - <%=EmpName %>
							</td>

							<%} %>
							<%if(DGMs !=null  && !DGMs.contains(EmpNo) && !PAndAs.contains(EmpNo) ){ %>
							<td rowspan="2"><i class="fa fa-long-arrow-right "
								aria-hidden="true"></i></td>
							<td class="trup" style="background: #FBC7F7;">DGM - <%=DGMName %>
							</td>
							<%} %>
							<%if(PAndAs !=null && !PAndAs.contains(EmpNo) ){ %>
							<td rowspan="2"><i class="fa fa-long-arrow-right"
								aria-hidden="true"></i></td>
							<td class="trup" style="background: #4DB6AC;">P&A - <%=PANDAName %>
							</td>
							<%} %>
						</tr>

					</table>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#myTable1").DataTable({
			"lengthMenu" : [ 5, 10, 25, 50, 75, 100 ],
			"pagingType" : "simple"

		});

		function EditPer() {

			var fieldsperadd = $("input[name='bankId']").serializeArray();

			if (fieldsperadd.length === 0) {
				alert("Please Select Atleast One Item");

				event.preventDefault();
				return false;
			}
			return true;
		}
	</script>
</body>
</html>