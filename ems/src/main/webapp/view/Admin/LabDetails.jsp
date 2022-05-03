<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Unit Details</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>

<%
Object[] labdetails = (Object[])request.getAttribute("labdetails");
%>

<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Unit Details</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">Unit Details</li>
				</ol>
			   </div>
		</div>
	</div> 

	<div class="card-body">
	
		<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
		if(ses1!=null){%>
			<div class="alert alert-danger" role="alert">
				<%=ses1%>
			</div>
			
		<%}if(ses!=null){%>
			
			<div class="alert alert-success" role="alert">
				<%=ses%>
			</div>
		<%}%>
	     </div>
	     
			<div class="card">
			<form action="LabMaster.htm" method="GET">
				<div class="card-body">			
				 <table class="table table-striped table-bordered" >
					<tbody>
					<tr>
						     <th>Unit Code</th>
	                         <th>Unit Name</th>
	                         <th>Unit UnitCode</th>
	                         <th>Unit Address</th>
	                         <th>Unit City</th>
	                         <th>Unit Pin</th>
					</tr>
						<%if(labdetails!=null){ %>
						<tr>
						<td><%=labdetails[1]%></td>
				     	<td><%=labdetails[2]%></td>
						<td><%=labdetails[3]%></td>
					    <td><%=labdetails[4]%></td>
						<td><%=labdetails[5]%></td>
						<td><%=labdetails[6]%></td>
						</tr>
							<%}else{ %>
							<tr><td></td></tr>
							<%}%>
					</tbody>
				</table>
				<%if(labdetails!=null){%>
				<div align="center">
				<input type="hidden" name="labmasterId" value="<%if(labdetails!=null){%><%=labdetails[0]%><%}%>">
				<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT" >EDIT </button>
				</div>
				<%}%>
				</div>
			</form>
		</div>
	</div>	
</div>
</body>
</html>