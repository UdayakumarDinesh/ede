<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<%@page import="com.vts.ems.utils.DateTimeFormatUtil,java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
List<Object[]> list   = (List<Object[]>)request.getAttribute("PandAFandAData");

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
				<div class="card-body ">			
					<form action="PandAFandAAdmin.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>						
						<div class="table-responsive">					
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th> Select   </th>
										<th> Admin </th>
										<th> Admin Role </th>
										<th> Admin From </th>
										<th> Admin To </th>
									</tr>
								</thead>
								<tbody>
									<% if(list!=null){ for(Object[] obj:list){ %>
										<tr>										   
											<td style="text-align: center;width: 5%;">
							                  <input type="radio" name="adminsId" value="<%=obj[0] %>"> 
											</td>
											<td style="text-align: left;width: 40%%;"><%=obj[5]+", "+obj[6]%></td>
											<td style="text-align: center;width: 15%;"><%if("P".equalsIgnoreCase(obj[2].toString())){%>P&A Admin<%} else{%>F&A Admin<%} %></td>
											<td style="text-align: center;width: 15%;"><%if(obj[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[3]+"")%><%}else{%>--<%}%></td>							
							                <td style="text-align: center;width: 15%;"><%if(obj[4]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[4]+"")%><%}else{%>--<%}%></td>
										</tr>
									 <%} }%>
								</tbody>
							</table>
						</div>			
						<div class="row text-center">
						<div class="col-md-12">
						
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
						</div>
						</div>
			   </form>		
			  </div>
			  </div>				
	</div>
</div>
</body>
</html>


