
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
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

	<%	
	

%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>Passport</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active" aria-current="page">Passport
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


		

<div class="card" style="width:80%">
    <div class="card-body " >
		<!-- <div class="card-body main-card"> -->

			<form action="#" method="POST" id="NoticeForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<!-- <div class="table-responsive"> -->
					<table
						class="table table-hover  table-striped table-condensed table-bordered table-fixed"
						id="myTable" >
						<thead>
							<tr>
								<th style="width: 6%">Select</th>
								<th style="width: 10%">NOC Number</th>
								<th style="width: 25%">NOC Date</th>
								<th style="width: 11%">Status</th>
								<th style="width: 8%">Action</th>
							</tr>
						</thead>
						<tbody>
							
							<tr>
								<td style="text-align: center;"><input type="radio" name="" value=""></td>
								<td></td>
								<td></td>
								<td style="text-align: center;"></td>
								<td style="text-align: center;">
							</td>

							</tr>
							

						</tbody>
					</table>

					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					
					
				<!-- </div> -->

				<div class="row text-center">
					<div class="col-md-12">
						
						<button type="submit" class="btn btn-sm add-btn" formaction="PassportAdd.htm">ADD</button>
						<button type="submit" class="btn btn-sm edit-btn" formaction="" name="action" value="EDITCIR" Onclick="Edit(NoticeForm)">EDIT</button>
						<button type="submit" class="btn btn-sm delete-btn" formaction="" name="action" value="DELETECIR" Onclick="Delete(NoticeForm)">DELETE</button>
						
						<!-- <button type="submit" class="btn btn-sm search-btn" formaction="EMSNoticeSearch.htm" name="action" style="background-color: green; color: white">SEARCH</button> -->
					</div>
				</div>

			</form>


		</div>

	</div>

</div>
<script type="text/javascript">



	

	
	
	
	
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