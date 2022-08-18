<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.* ,java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.Mt.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>MT Director Trip</title>
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
	List<Object[]> driverlist=(List<Object[]>)request.getAttribute("DriverList");
	List<Object[]> dutylist=(List<Object[]>)request.getAttribute("dutylist");

	String fromdate =(String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
	System.out.println(fromdate);
	System.out.println(todate);
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
 %>

<div class="card-header page-top ">
		<div class="row">
				<div class="col-md-3">
					<h5>MT Director Trip</h5>
				</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Director Trip</li>	
					</ol>
				</div>
		</div>
</div>
  <%String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){%>
	<div align="center"><div class="alert alert-danger" role="alert"><%=ses1 %> </div></div>
	<%}if(ses!=null){%>
	<div align="center"><div class="alert alert-success" role="alert" ><%=ses %></div></div>
     <%}%>
 <div class="card" >
	<div class="card-body" >
       <form name="myForm" id="myForm" action="DirectorTrip.htm" method="POST">
             <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
      <div class="form-group">
		<div class="row"  >
		     <div class="col-md-2" align="left"></div>
				    <div class="col-md-3" align="left">
						   <label style=" font-weight: 800">Vehicle Operator:<span class="mandatory" style="color: red;">*</span></label>
					        <div class=" input-group">				
					      <select class="form-control select2"  name="DriverId" id="driver" data-container="body" data-live-search="true"  required="required" >
							
							<option value="" disabled="disabled" selected="selected" >Select</option>
							<%if(driverlist!=null){
							for(Object[] drvlist:driverlist){%>
							<option value="<%=drvlist[0]%>" ><%=drvlist[1]%></option>
							<%}}%>
					      </select>
					     </div>
					</div>
		
				<div class="col-md-2" align="left">
					<label style=" font-weight: 800">From Date: <span class="mandatory" style="color: red;">*</span></label>
					<div class=" input-group">	
					<input type="text" class="form-control input-sm fromdate" <%if(fromdate!=null && fromdate!=""){%> value="<%=fromdate %>" <%}%>  readonly="readonly"   id="fromdate" name="fromdate"  required="required"  > 
				    </div>
				</div>

				<div class="col-md-2" align="left">
					<label style=" font-weight: 800">To Date: <span class="mandatory" style="color: red;">*</span></label>
					<div class=" input-group">	
					<input type="text" class="form-control input-sm todate" <%if(todate!=null && todate!=""){%> value="<%=todate %>" <%}%>    readonly="readonly"  id="todate" name="todate"  required="required" > 			
			        </div>
				</div>
				<div class="col-md-2" align="left">
				<button type="submit" class="btn btn-sm submit-btn" style="margin-left: 40px;margin-bottom: -67px;"  id="submit">Create</button>
				</div>
		 </div>
	</div>
			
	</form>
</div>
</div>
<br>
<div class="container-fluid">	
 <form  class="navbar-form  ml-auto " action="DirectorTrip.htm" method="GET">
	<div class="nav navbar bg-light dashboard-margin custom-navbar" style="padding-top: 10px;">
	
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="col-md-3"></div>
		
		<label style=" font-weight: 800">From Date : &nbsp; </label>
		<input  class="form-control form-control" data-date-format="dd-mm-yyyy" id="Fdate"  name="Fromdate"  required="required" <%if(fromdate!=null && fromdate!=""){%> value="<%=fromdate%>" <%}%>  style="width: 120px;">
	
		<label style="font-weight: 800;padding-left: 5px">To Date :  &nbsp; </label>
		<input  class="form-control form-control" data-date-format="dd-mm-yyyy" id="Tdate"  name="Todate" <%if(todate!=null && todate!=""){%> value="<%=todate%>" <%}%>  style="width: 120px;">
		
		<div class="col-md-4 d-flex justify-content-center" >
			<button type="submit" class="btn btn-sm submit-btn" style="margin-left: -50%;" name="action" value="submit" > Submit</button>
		</div>
		
	</div>
</form>

<div class="card" style="margin-top: 10px;">
<div  class="card header"><h4  style="font-weight: 800; font-size: 1.5rem; font-family: Poppins; color: #005C97;margin-top: 10px;">&nbsp;&nbsp; Director Trip List</h4></div>
	<div class="card-body main-card">
		
             <div class="form-group">
              <%if(dutylist!=null&&dutylist.size()!=0){%> 
                  <div class="table-responsive">
  						<table class="table table-bordered table-hover table-striped table-condensed" id="myTable"> 
		           <thead>
			           <tr>
			               <th>Vehicle Operator</th>
			               <th>From</th>
			               <th>To</th>
			               <th>Assigned By</th>
			               <th>Assigned On</th>
			           </tr>
		           </thead>
		           <tbody>
				           <%for(Object[] obj:dutylist){%>
				       <tr>
					        <td><%=obj[0]%></td>
					        <td align="center"><%=sdf.format(obj[1])%></td>
					        <td align="center"><%=sdf.format(obj[2])%></td>
					        <td><%=obj[3]%></td>
					        <td align="center"><%=sdf.format(obj[4])%></td>
				       </tr>
				           <%}%>
		           </tbody>
     </table>
  </div>
  <%}else{%>
   <div align="center"><b class="badge badge-warning">No Data Found</b></div>
  <%}%>
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


$( "#fromdate" ).change(function() {
	
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
});



$('#Fdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#Tdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#Fdate').val(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$( "#Fdate" ).change(function() {
	
	$('#Tdate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : $('#Fdate').val(), 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});
</script>

</html>