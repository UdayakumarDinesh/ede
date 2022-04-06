<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
       <%@page import="java.util.List"%>
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

</style>
</head>
<body>

<%
String Username =(String)session.getAttribute("Username"); 
List<Object[]> usernamelist = (List<Object[]>) request.getAttribute("usernamelist");
List<Object[]> auditstampinglist = (List<Object[]>) request.getAttribute("auditstampinglist");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm:ss");
SimpleDateFormat sdf2= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
String Fromdate=(String)request.getAttribute("Fromdate");
String Todate=(String)request.getAttribute("Todate");
String ListName=(String)request.getAttribute("Username");
%>


<div class="col page card">
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

</div>
<br>

<div class="container-fluid">	
<div class="nav navbar auditnavbar1" style="background-color: white;">
	<form class="form-inline " method="POST" action="${pageContext.request.contextPath}/AuditStampingView.htm">
				<input type="hidden" name="${_csrf.parameterName}"s	value="${_csrf.token}" /> 
				
				<label style="margin-left: 100px; margin-right: 10px;font-weight: 800">User Name: <span class="mandatory" style="color: red;">*</span></label>
					<select class="form-control form-control" name="username" style="margin-left: 12px;" required="required" id="username" >
				
				
					<option value=""  ></option>
					
					</select>
					<input type="text" class="form-control input-sm fromdate" readonly="readonly" value="" placeholder=""  id="fromdate" name="fromdate"  required="required"  > 
					</form>
</div>
	
	
<%-- <div class="row">
	<div class="col-md-9">
		<div class="badge badge-info" style="padding: 8px; margin-left: 46%">
		<h6><%if(ListName != null){%>Details of <b><%=ListName.toUpperCase()%></b><%}else{%> Details of <b><%=Username.toUpperCase()%></b> <%}%> from <span class="datefont"><%=Fromdate%></span> to <span class="datefont"><%=Todate %></span></h6>
		</div>
	</div>
	
	
	
</div> --%>
</div>	
</body>
<script type="text/javascript">
$('#fromdate').daterangepicker({
	
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :datearray, 
	"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>
</html>