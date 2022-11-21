
<%@page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>

	<%	
	String LoginType = (String)request.getAttribute("LoginType");
	String FromDate = (String)request.getAttribute("FromDate");
	String ToDate = (String)request.getAttribute("ToDate");
	List<Object[]> NoticeList=(List<Object[]>)request.getAttribute("NoticeList");
%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>Notice List</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active" aria-current="page">Notice
						List</li>
				</ol>
			</div>

		</div>
		<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
		<div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
		</div>
		<%}if(ses!=null){ %>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
		<%} %>


		<div class="card">
			<div class="card-header" style="height: 4rem">
				<form action="EMSNotices.htm" method="POST">
					<div class="row justify-content-right">
						<div class="col-7"></div>
						<div class="col-2" style="margin-left: 1%; font-color: black;">
							<h6>From Date : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -9%">
							<input type="text"
								style="width: 165%; background-color: white; text-align: left;"
								class="form-control input-sm mydate"
								onchange="this.form.submit()" <%if(FromDate!=null){%>
								value="<%=DateTimeFormatUtil.SqlToRegularDate(FromDate)%>" <%}%>
								readonly="readonly" value="" id="FromDate" name="FromDate"
								required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>


						<div class="col-2" style="margin-left: 4%">
							<h6>To Date : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -10%">
							<input type="text" style="width: 165%; background-color: white;"
								class="form-control input-sm mydate"
								onchange="this.form.submit()" <%if(ToDate!=null){%>
								value="<%=DateTimeFormatUtil.SqlToRegularDate(ToDate)%>" <%}%>
								readonly="readonly" value="" id="ToDate" name="ToDate"
								required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>


		<div class="card-body main-card">

			<form action="#" method="POST" id="NoticeForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="table-responsive">
					<table
						class="table table-hover  table-striped table-condensed table-bordered table-fixed"
						id="myTable">
						<thead>
							<tr>
								<th style="width: 4%">Select</th>
								<th style="width: 10%">Notice No</th>
								<th style="width: 45%">Description</th>
								<th style="width: 11%">Issued Date</th>
								<th style="width: 8%">Download</th>
							</tr>
						</thead>
						<tbody>
							<%
							if (NoticeList != null) {
								int slno = 0;
								for (Object[] ls : NoticeList) {
							%>
							<tr>
								<td style="text-align: center;"><input type="radio" name="NoticeId" value="<%=ls[0]%>"></td>
								<td><%=ls[1]%></td>
								<td><%=ls[2]%></td>
								<td style="text-align: center;"><%=DateTimeFormatUtil.SqlToRegularDate(ls[3].toString())%></td>
								<td>
									<button type="submit" class="btn btn-sm" name="NoticeId" value="<%=ls[0]%>" formaction="EMSNoticeDownload.htm" formmethod="post" data-toggle="tooltip" data-placement="top"
										title="Download" formtarget="_blank">
										<i class="fa-solid fa-download " style="color: green;"></i>
									</button>
								</td>

							</tr>
							<%
							}
							}
							%>

						</tbody>
					</table>

					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					
					<input type="hidden" name="FromDate" value="<%=FromDate %>" />
					<input type="hidden" name="ToDate" value="<%=ToDate %>" />
				</div>

				<div class="row text-center">
					<div class="col-md-12">
						<%if(LoginType.equalsIgnoreCase("A") || LoginType.equalsIgnoreCase("P")){ %>
						<button type="submit" class="btn btn-sm add-btn" formaction="EMSNoticeAdd.htm">ADD</button>
						<button type="submit" class="btn btn-sm edit-btn" formaction="EMSNoticeEdit.htm" name="action" value="EDITCIR" Onclick="Edit(NoticeForm)">EDIT</button>
						<button type="submit" class="btn btn-sm delete-btn" formaction="EMSNoticeDelete.htm" name="action" value="DELETECIR" Onclick="Delete(NoticeForm)">DELETE</button>
						<%} %>
						<!-- <button type="submit" class="btn btn-sm search-btn" formaction="EMSNoticeSearch.htm" name="action" style="background-color: green; color: white">SEARCH</button> -->
					</div>
				</div>

			</form>


		</div>

	</div>

<script type="text/javascript">

$('#FromDate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

	
	$('#ToDate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :$("#FromDate").val(),  
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
	
	
	$(document).ready(function(){
		   $('#FromDate, #ToDate').change(function(){
		       $('#myform').submit();
		    });
		});
	
	
	
function Edit(NoticeForm)
{
	var fields = $("input[name='NoticeId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Notice ");
        event.preventDefault();
		return false;
	}
	return true;
	
}
	   	   
	   

function Delete(NoticeForm){ 
	
	var fields = $("input[name='NoticeId']").serializeArray();

	if (fields.length === 0){
		alert("Please Select Atleast One Notice");
		event.preventDefault();
		return false;
	}
	
	var cnf = confirm("Are You Sure To Delete!");
    if(cnf){
		
		document.getElementById("NoticeForm").submit();
		return true;

	}else{
		
		event.preventDefault();
		return false;
	}
}

</script>

</body>
</html>