<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">


</style>
</head>
<body>
<% 
Object[] empData=(Object[])request.getAttribute("EmpData");
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-7">
				<h5>Pending Intimations<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
				<div class="col-md-5 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item active">Pending Intimations</li>
					</ol>
				</div>
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
		<div class="alert alert-success" role="alert" >
        	<%=ses %>
        </div>
    </div>
	<%} %>
<%List<Object[]> pendingList =(List<Object[]>)request.getAttribute("pendingList");
SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

%>
<div class="page card dashboard-card">
   
				
					<div class="card-body">
					
						<table class="table table-bordered table-hover table-striped table-condensed" id="myTable">
							<thead>
								<tr>
								    <th>SNo</th>
									<th>Company Name</th>
									<th>Visitor/s</th>
									<th style="width:20%;" >Dates</th>
									<th >Officer</th>
									<th >Purpose</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
							<%if(pendingList!=null){ int count =1;%>
							<%for(Object[] intimation :pendingList){ %>
								<tr>
								<td><%=count++%></td>
								<td><%=intimation[3]%> </td>
							    <td><%=intimation[10]%> </td>
							    <td><%=sdf1.format(intimation[5])%>&nbsp; to &nbsp;<%=sdf1.format(intimation[6])%></td>
				                 <td><%=intimation[9]%></td>
				                 <td><%=intimation[7]%> </td>
				                 <td>
				                 <form action="intimationDetails.htm" method="post">
				                 <input type="hidden" name="intimationId" value="<%=intimation[0]%>">
				                 <button type="submit" class="btn btn-primary btn-sm">Create Pass</button>
				                 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				                 </form>
				                 </td>
								</tr>
							<%}%>
							<%} %>
							</tbody>
						</table>
				
					</div>
				
</div>

 

<script>


	   

</script>

</body>
</html>