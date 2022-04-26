<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Handling Over</title>
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>
<%
List<Object[]> handingoverlist = (List<Object[]>)request.getAttribute("handlingoverlist");
String treat = (String)request.getAttribute("treat");
%>



<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5> Handing Over List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item active " aria-current="page">Handing Over List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	
	<div class="card-body" >		
			<div align="center">
		<%String ses=(String)request.getAttribute("result"); 
		String ses1=(String)request.getAttribute("resultfail");
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
			
				<div class="card-body">
			 	
				
				<form action="HandingOver.htm" method="GET">
				
				<div class="row" style="padding-bottom: 10px;">
				<div class="col-5"></div>
				<div class="col-7">
				<div class="row">
					  <div class="col-2"  align="right">FromDate :</div>
					  
				         <div class="col-2"> 
							    <input type="text" style="width: 115%;"  class="form-control input-sm mydate" readonly="readonly" value="" placeholder=""  id="fromdate" name="fromdate"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate"></label>              
						 </div>
						 
						  <div class="col-2" align="right" ><h6>ToDate :</h6></div>
						  <div class="col-2">						
							     <input type="text" style="width: 115%;"  class="form-control input-sm mydate" readonly="readonly" value="" placeholder=""  id="todate" name="todate"  required="required"  > 							
						 		 <label class="input-group-addon btn" for="testdate"></label>    
						 </div>
						 
						 <div class="col-2" align="right">
						    <button type="submit" class="btn btn-sm submit-btn" style="height: 60%; width: 70%;"  name="Action" value="List" >SUBMIT</button>
						 </div>
						 </div>
						 </div>
						 </div>
						 
			   </form>
			   
					<form action="HandingOver.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>Select</th>
										<th>From-Employee</th>
										<th>To-Employee</th>
										<th>Applied Date</th>
										<th>From Date</th>
										<th>To Date</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<%for(Object[] obj:handingoverlist){ %>
										<tr>
											<td style="text-align: center;"><input type="radio" name="HandingOverId" value="<%=obj[0]%>"> </td>
											<td><%=obj[1]%></td>
											<td><%=obj[2]%></td>
											<td><%=DateTimeFormatUtil.SqlToRegularDate(obj[5].toString())%></td>
											<td><%=DateTimeFormatUtil.SqlToRegularDate(obj[3].toString())%></td>
											<td><%=DateTimeFormatUtil.SqlToRegularDate(obj[4].toString())%></td>
											<td><%if("A".equalsIgnoreCase(obj[6].toString())){%>APPLIED<%}else if("R".equalsIgnoreCase(obj[6].toString())){%>REVOKED<%}else{ %> SANTIONED<%} %></td>
										</tr>
									<%}%>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">						
						<button type="submit" class="btn btn-sm add-btn"    name="Action" value="ADD" >ADD    </button>
						<button type="submit" class="btn btn-sm delete-btn" name="Action" value="REVOKE" Onclick="Edit()" >REVOKE </button>
					    </div>						 
					</div>
					
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	        </div>
</body>
<script type="text/javascript">


$(document).ready(function(){

	var financial_year = "";
    var today = new Date();
    if ((today.getMonth() + 1) <= 3) {
        financial_year = (today.getFullYear() - 1) + "-" + today.getFullYear()
    } else {
        financial_year = today.getFullYear() + "-" + (today.getFullYear() + 1)
    }
    
	var date =  financial_year.split("-");
	var fdate = "01-04-"+date[0];
	var tdate = "31-03-"+date[1];
	

$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	"startDate" : fdate,
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
});

$('#todate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


function Edit() {

	var fields = $("input[name='HandingOverId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One ");

		event.preventDefault();
		return false;
	}
	return true;
}
</script>
</html>