<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.* ,java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.Mt.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>MtReport</title>
</head>
<body>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>MT Trip Report</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Trip Report</li>
					</ol>
				</div>
			</div>
</div>
<%List<Object[]> driverlist=(List<Object[]>)request.getAttribute("DriverList");
List<MtVehicle> vehiclelist=(List<MtVehicle>)request.getAttribute("vehiclelist");
List<Object[]> reportlist=(List<Object[]>)request.getAttribute("ReportList");
String driverid =(String)request.getAttribute("driverid");
String vehicleid =(String)request.getAttribute("vehicleid");
String fromdate =(String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
 %>

  <div class="card" >
	<div class="card-body" >
             <form name="myForm" id="myForm" action="TripReport.htm" method="POST">
             <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
 <div class="form-group">
		 <div class="row"  >
		
		<div class="col-md-1" align="left"></div>
				<div class="col-md-2" align="left">
					<label style=" font-weight: 800">From Date: <span class="mandatory" style="color: red;">*</span></label>
					<div class=" input-group">	
					<input type="text" class="form-control input-sm fromdate"  readonly="readonly"   id="fromdate" name="fromdate"  required="required" <%if(fromdate!=null && fromdate!=""){%>   value="<%=fromdate %>" <%}%> > 
				    </div>
				</div>
				
				
				
				<div class="col-md-2" align="left">
						<label style=" font-weight: 800">To Date: <span class="mandatory" style="color: red;">*</span></label>
					<div class=" input-group">	
					<input type="text" class="form-control input-sm todate" <%if(todate!=null && todate!=""){%>   value="<%=todate %>" <%}%>    readonly="readonly" value=""  id="todate" name="todate"  required="required" > 			
			        </div>
				</div>
				
				<div class="col-md-3" align="left">
					   <label style=" font-weight: 800">Vehicle Operator:<span class="mandatory" style="color: red;">*</span></label>
				        <div class=" input-group">				
				        <select class="form-control select2"  name="DriverId" id="driver" data-container="body" data-live-search="true"  required="required" >
						
						<option value="-1"  <%if(driverid!=null &&driverid!="" && "-1".equalsIgnoreCase(driverid)){%> selected="selected" <%}%>>All</option>
						<%if(driverlist!=null){
						for(Object[] drvlist:driverlist){ %>
						<option value="<%=drvlist[0]%>" <%if(driverid!=null && driverid!="" && driverid.equalsIgnoreCase(drvlist[0].toString())){%> selected="selected" <%}%>><%=drvlist[1] %></option>
						<%}} %>
				      </select>
				     </div>
				</div>

				 <div class="col-md-3" align="left">
					<label style=" font-weight: 800">Vehicle Type:<span class="mandatory" style="color: red;">*</span></label>
					<div class=" input-group">
					<select class="form-control select2"   name="VehicleId"  data-container="body" data-live-search="true" required="required" id="vehicle" >
							
							<option value="-1" <%if(vehicleid!=null && vehicleid!="" && "-1".equalsIgnoreCase(vehicleid)){%> selected="selected" <%}%>>All</option>
							<%if(vehiclelist!=null){
							for(MtVehicle vlist:vehiclelist){ %>
							<option value="<%=vlist.getVehicleId()%>" <%if(vehicleid!=null && vehicleid!="" && vehicleid.equalsIgnoreCase(String.valueOf(vlist.getVehicleId()))){%> selected="selected" <%}%>><%=vlist.getVehicleName()%> &nbsp<%=vlist.getBaNo() %></option>
							<%}}%>
					</select>
					</div>
		 		</div>
		 </div>
		</div>
			<div align="center">
				<button type="submit" class="btn btn-sm submit-btn" style="margin-left: 40px;margin-right: 7px;"  id="submit">Submit</button>
			</div>
	</form>

</div>
</div>





<div class="container-fluid">	




<div class="card" style="margin-top: 10px;">
				<div class="card-body main-card"  >
						     <div class="card-body">
   <%--  <%if(null!=null&&1!=0) {%> --%>
    <form  action="MtPrintReport.htm" method="GET" name="frm">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  <div class="form-group">
  
  <div class="table-responsive">
		 <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
	  <thead>
           
            <tr>
	            <th>Date</th>
	            <th>Destination</th>
	            <th>Vehicle</th>
	            <th>Vehicle Operator</th>
	            <th>Assigned By	</th>
	            <th>Indenting Officer</th>
            </tr>
            </thead>
            <tbody>
		            <%if(reportlist!=null && reportlist.size()>0){ for(Object[] object:reportlist){%>
		            <tr>
			            <td align="center"><%=sdf.format(object[0]) %>  <b>To</b> <%=sdf.format(object[6]) %></td>
			            <td><%=object[1]%></td>
			            <td><%=object[2]%></td>
			            <td><%=object[3]%></td>
			            <td><%=object[4]%></td>
			            <td><%=object[5]%></td>
		            </tr>
		            <%}}%>
            </tbody>
   </table>
    </div>
    </div>
   
    </form>
    <%--  <%}else{ %>
          <div align="center">  <b class="badge badge-warning">no data found</b> </div>
            <%} %> --%>
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
	<%-- <%if(fromdate!=null && fromdate!=""){%>
	"minDate" : "<%=fromdate%>",
	<%}%>  --%>
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
    <%-- <%if(todate!=null && todate!=""){%>
	"startDate" :"<%=todate%>",
	<%}%>  --%>
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


</script>
</html>