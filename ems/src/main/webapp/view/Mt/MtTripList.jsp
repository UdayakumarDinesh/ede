<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Trip List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
.custom-navbar{
	border-top-left-radius: 7px;
	border-top-right-radius: 7px;
	background-color: white !important;
}
</style>
</head>
<body>
<%
List<Object[]> TripList=(List<Object[]>)request.getAttribute("TripList");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String fromdate = (String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");
String empname = (String)request.getAttribute("empname");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>MT Trip List <small> <%if(empname!=null){%> <%=empname%> <%}%> </small> </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Trip List</li>	
					</ol>
				</div>
			</div>
</div>

<div class="container-fluid">	
 <form name="myfrm" action="MtTripList.htm" method="GET"id="myform">
	<div class="nav navbar bg-light dashboard-margin custom-navbar">
	
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="col-md-7"></div>
		
		<label style=" font-weight: 800">From Date : &nbsp; </label>
		<input  class="form-control form-control" <%if(fromdate!=null && fromdate!=""){%>value="<%=fromdate%>"<%}%>  data-date-format="dd-mm-yyyy" id="fromdate"  name="Fromdate"  required="required"  style="width: 120px;">
	
		<label style="font-weight: 800;padding-left: 5px">To Date :  &nbsp; </label>
		<input  class="form-control form-control" data-date-format="dd-mm-yyyy" <%if(todate!=null && todate!=""){%>value="<%=todate%>"<%}%>  id="todate"  name="Todate"  style="width: 120px;">
		
		<!-- <div class="col-md-4 d-flex justify-content-center" >
			<button type="submit" class="btn btn-sm submit-btn" style="margin-left: -50%;" name="action" value="submit" > Submit</button>
		</div> -->
		
	</div>
</form>

<div class="card" style="margin-top: 10px;">
				<div class="card-body main-card"  >
						     <div class="card-body">

 
  <div class="form-group">
    <form action="MtTripC.htm" method="POST" name="frm1">
     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="table-responsive">
		 <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
		  	  <thead>
				  <tr>
						 <!--  <th>Select</th> -->
						  <th>Trip No</th>
						  <th>Place</th>
						  <th>Trip Date</th>
						  <th>Trip Time</th>
						  <th>BA No</th>
				  </tr>
		  </thead>
	  <tbody>
	  <%for(Object[] hlo :TripList){ %>
			  <tr>
					  <%-- <td align="center"><input type="radio" name="Aid" value=<%=hlo[1]%> required="required" ></td> --%> 
					  <td align="center"><%=hlo[2] %></td>
					  <td><%=hlo[4] %></td>
					  <td align="center"><%=sdf.format(hlo[5]) %></td>
					  <td align="center"><%=hlo[6] %></td>
					  <td><%=hlo[8] %></td>
		
			  </tr>
	  <%} %>  
	  </tbody>
   </table>
    </div>
    <!-- <div align="center">
              <button type="submit" class="btn btn-sm delete-btn" name="sub" value="delete"  onclick="Delete(frm1)">DELETE</button>
    </div> -->
    </form>
    </div>
  
   
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	
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
	"minDate" :$('#fromdate').val(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
$(document).ready(function(){
	   $('#fromdate, #todate').change(function(){
	       $('#myform').submit();
	    });
	});

/* $( "#fromdate" ).change(function() {
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : $('#fromdate').val(), 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
}); */
</script>
</html>