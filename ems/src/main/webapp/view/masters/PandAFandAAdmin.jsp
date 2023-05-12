<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>P&A And F&A</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
.card{
    margin-bottom: 10px;
}

/* .btn-group button {
  
  border: 1px solid blue; /* Green border */
  color: 	black; /* White text */
  padding: 10px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  float: right; /* Float the buttons side by side */
}

/* Clear floats (clearfix hack) */
.btn-group:after {
  content: "";
  clear: both;
  display: table;
}

.btn-group button:not(:last-child) {
  border-right: right; /* Prevent double borders */
} */

/* Add a background color on hover */
/* .btn-group button:hover {
  background-color: #3e8e41;
} */
</style>
</head>
<body>
<%
Object[] PandAFandAData   = (Object[])request.getAttribute("PandAFandAData");

%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>P&A And F&A</h5>
			</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						 <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> 					
						<li class="breadcrumb-item active " aria-current="page">P&A And F&A</li>
					</ol>
				</div>
			</div>
	 </div>

 <div class="page card dashboard-card">
	 

<div class="card-body" >		
		<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
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
				<div class="card-body " >
				 <h5>Add / Edit Admin</h5>	
					<hr>
					<%if(PandAFandAData!=null){ %>
					<table class="table table-hover table-striped  table-condensed  table-bordered"  id="">
					<tbody>
					    <tr align="center">
							<th>P & A</th>
							<th>F & A</th>
							<!-- <th>Revised On</th> -->
							<th>Edit</th>
					   </tr>
					    <tr align="center">
							<td><%=PandAFandAData[1]+", "+PandAFandAData[2]%></td>
							<td><%=PandAFandAData[3]+", "+PandAFandAData[4]%></td>
							<%-- <td><%if(PandAFandAData[5]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(PandAFandAData[5].toString())%><%}else{%>--<%}%></td> --%>
							<td style="padding-top:5px; padding-bottom: 5px;">
							<form action="PandAFandAAdmin.htm" method="GET">
							 <input type="hidden" name="adminsId" value="<%if(PandAFandAData!=null){%><%=PandAFandAData[0]%><%}%>">
						     <button type="submit" class="btn btn-sm" name="Action" value="EDIT" data-toggle="tooltip" data-placement="top" title="Edit">
							<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i></button>	</form></td>
					   </tr>
					</tbody>
					</table>
						<%}else{%>
				          <form action="PandAFandAAdmin.htm" method="GET">
                             <button  type="submit" name="Action" value="ADD"  class="btn btn-sm add-btn" style="margin-bottom:12px;"> Add P&A And F&A Admin</button>
                          </form>
					    <%}%>
						
		  </div>
		</div>					
	</div>
</div>
</body>
</html>


