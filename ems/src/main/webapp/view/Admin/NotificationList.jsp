<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Notification List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>



<body>
<%
List<Object[]> notification  =  (List<Object[]>)request.getAttribute("notificationlist"); 
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Notification List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>					
						<li class="breadcrumb-item active " aria-current="page">Notification List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 
		  <div class="page card dashboard-card">
	     	
			<div class="card" >
				<div class="card-body">
				
					<form action="#" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th >SlNo </th>
										<th>Date </th>
										<th> Notification </th>
									</tr>
								</thead>
								<tbody>
									<%	if(notification!=null){int slno=0;
									for(Object[] obj : notification){ 
									%>
										<tr>
											<td style="text-align: center"> <%=++slno %></td>
											<td><%=DateTimeFormatUtil.SqlToRegularDate(obj[0].toString())%></td>
											<td> <a onclick="test(<%=obj[3] %>)" id=<%=obj[3] %> href="<%=obj[2] %>" ><%=obj[1] %></a></td>
											
										</tr>
									 <%} }%> 
								</tbody>
							</table>
						</div>			
			   </form>		
			  </div>
		   	 </div>				
	        </div>
</body>
<script type="text/javascript">

function test(id){
	
	 var notificationid=id;
	
	$.ajax({
		type : "GET",
		url : "NotificationUpdate.htm",
		data : {
			notificationid : notificationid,
				
			},
		datatype : 'json',
		success : function(result) {
			
		}
	});
	
}
</script>
</html>