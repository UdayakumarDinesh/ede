<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Doctors List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%
List<Object[]> doctorlist = (List<Object[]>)request.getAttribute("doctorlist");
%>


<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Doctors List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">CHSS Doctors List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 
		 <div class="card-body">
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
		 <div class="card">
		 <div class="card-body">
		 		<form action="##" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>SlNo.</th>
										<th>Treatment</th>
										<th>Doctor-Qualification</th>
										<!-- <th>Doctor-Rating</th> -->
										<th>Consultation-1</th>
										<th>Consultation-2</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
							for(Object[] obj : doctorlist){ 
								slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=slno%>.</td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[2]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;text-align: right">&#8377;<%=obj[4]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;text-align: right">&#8377;<%=obj[5]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;" align="center">
											<input type="hidden" name="Action"	value="EDIT" />
											<button type="submit" class="btn btn-sm" name="DocRateid" value="<%=obj[0]%>" formaction="DoctorsMaster.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Edit">
												<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
											</button>
											</td>
										</tr>
									<%}%>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>				
			   </form>
		 </div>
		 </div>
		 </div>
</div>
</body>
</html>