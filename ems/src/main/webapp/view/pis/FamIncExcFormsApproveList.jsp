<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<body>
<%
	List<Object[]> formslist = (List<Object[]>)request.getAttribute("FamFwdFormsList");
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Forms Approval</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="EmployeeDetails.htm"> Profile </a></li>
					<li class="breadcrumb-item active " aria-current="page">Include / Exclude</li>
				</ol>
			</div>
		</div>
	</div>

	
	<div class="page card dashboard-card">
		<div class="card">
			<div align="center" style="margin-top: 15px;">
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
			<div class="card-body main-card">
				
				<div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id=""> 
						<thead>
							<tr>
								<th style="width: 5%">SN</th>
								<th style="width: 10%">Employee No</th>
								<th style="width: 35%">Employee</th>
								<th style="width: 25%"> Inclusion/Exclusion </th>
								<th style="width: 15%" >Date</th>
								<th style="width: 10%" >Action</th>
							</tr>
						</thead>
						<tbody>
							<%for( Object[] form:formslist){ %>
							<form method="Post" >
							<tr>
								<td style="width: 5%"><%=formslist.indexOf(form)+1 %></td>
								<td style="width: 5%"><%=form[5] %></td>
								<td style="width: 5%"><%=form[2] %></td>
								<td>
									<%if(form[3].toString().equals("A") || form[3].toString().equals("F")){ %>
										Inclusion
									<%}else{ %>
										Exclusion
									<%} %>					
								</td>
								
								<td><%=DateTimeFormatUtil.SqlToRegularDate(form[4].toString()) %></td>
								<td>
									<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepAdmissionCreateView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
										<i class="fa-solid fa-eye"></i>
									</button>			
									<input type="hidden" name="empid" value="<%=form[1]%>">
									<input type="hidden" name="isApprooval" value="Y">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									
								</td>
							</tr>
							</form>
							<%} %>
						</tbody>
					</table>
				</div>	
				
				
			</div>
		</div>				
	</div>
	       
</body>
</html>