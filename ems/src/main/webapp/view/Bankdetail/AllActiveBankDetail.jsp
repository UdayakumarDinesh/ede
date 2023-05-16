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
	List<Object[]> allActivBankDetList = (List<Object[]>) request.getAttribute("allActivBankDetList");
	
	Object[] empNameAndDesi=(Object[]) request.getAttribute("empNameAndDesi");

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-5">
				<h5>Active Bank Details <small><b>&nbsp; - &nbsp;<%if(empNameAndDesi !=null) %> <%=empNameAndDesi[0] %> (<%=empNameAndDesi[1] %>)</b></small></h5>
			</div>
			<div class="col-md-7">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
							<li class="breadcrumb-item "> <a href="BankDetails.htm">
							Bank Details </a></li>
					<li class="breadcrumb-item active " aria-current="page">
							Active Bank Details </a></li>
				</ol>
			</div>
		</div>
	</div>

	<div class="page card dashboard-card">



		<div class="card">
			<div class="card-body">
				<!-- <h5>Bank Detail List</h5>
				<hr> -->
				<form action="#">
					<div class="table-responsive">
						<table
							class="table table-bordered table-hover table-striped table-condensed"
							id="myTable1">
							<thead>
								<tr>
									<th>EmpNo</th>
									<th>Employee Name</th>
									<th>Bank Name</th>
									<th>Branch</th>
									<th>IFSC</th>
									<th>Account No</th>
									<th>Valid From</th>
									<th>Application</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (allActivBankDetList != null && allActivBankDetList.size() > 0) {
									for (Object ls[] : allActivBankDetList) {
								%>
								<tr>						
									<td><%=ls[1]%></td>
									<td><%=ls[2]%></td>
									<td><%=ls[3]%></td>
									<td><%=ls[4]%></td>
									<td><%=ls[5]%></td>
									<td><%=ls[6]%></td>
									<td style="text-align: center;"><%=sdf.format(ls[7])%></td>															
									<td align="center"><button type="submit"
											class="btn btn-sm view-icon"
											formaction="GetBankDetailForm.htm" name="bankId"
											value="<%=ls[0]%>" formnovalidate="formnovalidate"
											data-toggle="tooltip" data-placement="top"
											title="Form For Bank Details Change"
											style="font-weight: 600;">
											<i class="fa-solid fa-eye"></i>
										</button>
								</tr>
								<%
								}
								}
								%>
							</tbody>
						</table>
					</div>
					<input type="hidden" name="isApproval" value="Y">
					<input type="hidden" name="AllActive" value="AllActive">
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#myTable1").DataTable({
			"lengthMenu" : [ 5, 10, 25, 50, 75, 100 ],
			"pagingType" : "simple"

		});
	
	</script>
</body>
</html>