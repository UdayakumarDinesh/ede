<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
       <%@page import="java.util.List"%>
       <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>Audit Stamping</title>
<style type="text/css">

.auditnavbar1{

color: white;
background-color:#6c757d;

}
.card{
    margin-bottom: 10px;
}
</style>
</head>
<body>

<%
String Username =(String)session.getAttribute("Username"); 
String LoginType =(String)session.getAttribute("LoginType"); 
List<Object[]> emplist = (List<Object[]>) request.getAttribute("emplist");
List<Object[]> auditstampinglist = (List<Object[]>) request.getAttribute("auditstampinglist");
SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
SimpleDateFormat sdf2= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
String Fromdate=(String)request.getAttribute("Fromdate");
String Todate=(String)request.getAttribute("Todate");
String loginid=(String)request.getAttribute("loginid");
String ListName=(String)request.getAttribute("Username");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Audit Stamping</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">Audit Stamping</li>
					</ol>
				</div>
			</div>
		 </div>


<br>



<div class="container-fluid">	
<div class="nav navbar auditnavbar" style="background-color: white;margin-top: -18px">


	<form class="form-inline " method="GET" action="AuditStamping.htm" style="margin-left: auto !important">
		
		<div class="row ">
			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
				
				   <label style="margin-left: 70px; margin-right: 10px; color: black;">User Name: <span class="mandatory" style="color: red;">*</span></label>
			
			
					<select class="form-control form-control select2" name="username" style="margin-left: 12px;" required="required" id="username" >
			    	<%if("A".equalsIgnoreCase(LoginType)){ %>
				    <%for(Object[] obj:emplist){%>
					<option value="<%=obj[2]%>-<%=obj[1]%>"<% if(obj[2].toString().equalsIgnoreCase(loginid)){%> selected<%}%> ><%=obj[1]%></option>
					<%}}else{%>
					<option value="<%if(loginid!=null){%><%=loginid%>-<%=ListName%><%}%>"  ><%if(ListName!=null){%><%=ListName%><%}%></option>
					<%}%>
					</select>
					
					<label style="margin-left: 30px; margin-right: 20px;color: black; ">From Date: <span class="mandatory" style="color: red;">*</span></label>
					
					
					<input type="text" class="form-control input-sm fromdate" style="width: 140px;" readonly="readonly" value="<%if(Fromdate!=null){%><%=Fromdate%><%}%>"   id="fromdate" name="fromdate"  required="required"  > 
				
				
					<label style="margin-left: 10px; margin-right: 20px; color: black;">To Date: <span class="mandatory" style="color: red;">*</span></label>
					
					
					<input type="text" class="form-control input-sm todate" style="width: 140px;" readonly="readonly" value="<%if(Todate!=null){%><%=Todate%><%}%>"  id="todate" name="todate"  required="required" > 			
				
				
			    	<button type="submit" class="btn btn-sm submit-btn" style="margin-left: 40px;margin-right: 7px;"  id="submit">SUBMIT</button>
		
		</div>
	
				
	 </form>
					
</div>
	
	
 <div class="row">
	<div class="col-md-9">
		<div class="badge badge-secondary" style="padding: 5px; margin-left: 50%;margin-bottom: 10px; margin-top:10px; ">
		<h6><%if(ListName != null){%>Details of <b><%=ListName.toUpperCase()%></b><%}else{%> Details of <b><%=Username.toUpperCase()%></b> <%}%> from <span class="datefont"><%=Fromdate%></span> to <span class="datefont"><%=Todate %></span></h6>
		</div>
	</div>	
</div> 


<div class="card">
				<div class="card-body "  >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1"> 
								<thead>
									<tr>
										<th>Login Date</th>
										<th>Login Time</th>
										<th>IP Address</th>
										<th>Mac Address</th>
										<th>LogOut Type</th>
										<th>LogOut Date Time</th>
									</tr>
								</thead>
								<tbody>
								<%	long slno=0;
									for(Object[] obj : auditstampinglist){ 
									slno++;%>
										<tr>
											<td><%=DateTimeFormatUtil.SqlToRegularDate(obj[1].toString())%></td>
											<td><%=sdf1.format(obj[2])%></td>
											<td><%=obj[3] %></td>
											<td><%if(obj[4]!=null){%><%=obj[4]%><%}else{%>--<%}%></td>
											<td><%=obj[5] %></td>
											<td><%if(obj[6]!= null){%><%= sdf2.format(obj[6]) %> <% }else{%> - <%} %></td> 
										</tr>
									<%}%>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
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
	//"minDate" :datearray, 
	//"startDate" : new Date(),
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

$("#myTable1").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});
</script>
</html>