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


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Pass Intimation List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
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
<%
	List<Object[]> itimationList=(List<Object[]>)request.getAttribute("IntimationList"); 
	SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
%>

<div class="page card dashboard-card">
   <div class="card-body"  >
  		

			<div class="card shadow-nohover">
					
				<div class="card-body">
					<div align="right"> 
						
						<a class="btn btn-sm btn-info" href="NewIntimation.htm"  >Create Pass</a>
						
					</div>
					<hr>
					<div class="table-responsive">			
							<table class="table table-bordered table-hover table-striped table-condensed" id="myTable">
								<thead>
									<tr>
										<th style="width: 5%;text-align: center;">SN</th>
				
										<th>Company Name</th>
										<th style="width:20%;">Dates</th>
										<th >Purpose</th>
										<th >Officer</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
								<%if(itimationList!=null){ %>
								<%int i=0;
								for(Object[] intimation :itimationList){%>
									<tr>
									<td style="text-align: center;"><%-- <input type="radio" name="intimationId" value="<%=intimation[0]%>"> --%> <%=++i %></td>
								    <td><%=intimation[3]%> </td>
								    <td style="text-align: center;"><%=sdf1.format(intimation[5])%>&nbsp; to &nbsp;<%=sdf1.format(intimation[6])%></td>
					                <td><%=intimation[7]%> </td>
					                 <td><%=intimation[9]%> </td>
					                 <td><%=intimation[10]%> </td>
									</tr>
								<%}%>
								<%} %>
								</tbody>
							</table>
							
						</div>
					</div>
				</div>
	   

   </div>
</div>

 

<script>


	   

</script>

</body>
</html>