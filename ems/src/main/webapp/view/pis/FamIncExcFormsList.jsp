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
	List<Object[]> formslist = (List<Object[]>)request.getAttribute("formslist");
	String empid = (String)request.getAttribute("empid");
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	String formmoduleid =(String) session.getAttribute("formmoduleid");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Forms</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<!-- <li class="breadcrumb-item "><a href="EmployeeDetails.htm"> Profile </a></li> -->
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
				<form method="Post" >
					<div class="table-responsive">
					   	<table class="table table-bordered table-hover table-striped table-condensed"  id=""> 
							<thead>
								<tr>
									<th style="width: 5%">SN</th>
									<th> Inclusion/Exclusion </th>
									<th>Status</th>
									<th>Forwarded Date</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<% int i=0;
								for( Object[] form:formslist){ 
									if(!form[3].toString().equals("A")){%>
								<tr>
									<td style="width: 5%"><%=++i %></td>
									<td>
										<%if(form[2].toString().equals("I") ){ %>
											Inclusion
										<%}else if(form[2].toString().equals("E") ){ %>
											Exclusion
										<%}else if(form[2].toString().equals("D") ){ %>
											Declaration
										<%} %>				
									</td>
									<td>
										<%if(form[3].toString().equals("C") ){ %>
											Created
										<%}else if(form[3].toString().equals("F") ){  %>
											Forwarded
										<%}else if(form[3].toString().equals("R") ){  %>
											Returned
										<%} %>	
									</td>
									<td><%if(form[4]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(form[4].toString()) %><%}else{ %>-<%} %></td>
									<td>
										<%if(form[2].toString().equals("I") ){ %>
											<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepInclusionFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>	
											<button type="submit" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DependentIncForm.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
												<i class="fa-solid fa-download " style="color: green;"></i>
											</button>
										<%}else if(form[2].toString().equals("E") ){ %>
											<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepExclusionFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>	
											<button type="submit" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DependentExcForm.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
												<i class="fa-solid fa-download " style="color: green;"></i>
											</button>
										
										<%}else if(form[2].toString().equals("D") ){ %>
											<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepDeclareFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>	
											<button type="button" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DependentExcForm.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
												<i class="fa-solid fa-download " style="color: green;"></i>
											</button>
										
										<%} %>
									</td>
								</tr>
								<%} }%>
								<%if(i==0){ %>
									<tr><td colspan="5" style="text-align: center;">No Records Found</td></tr>
								<%} %>
							</tbody>
						</table>
					</div>	
					<input type="hidden" name="empid" value="<%=empid%>">
					<input type="hidden" name="isApprooval" value="N">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div align="center">
						<button type="submit" class="btn btn-sm " style=" background: #231955; color: #ffffff " name="formid" value="0" formaction="DepInclusionFormView.htm"  name="action" value="add" >Inclusion Form</button>
						<button type="submit" class="btn btn-sm " style=" background: #A10035; color: #ffffff " name="formid" value="0" formaction="DepExclusionFormView.htm"  name="action" value="add" >Exclusion Form</button>
						<%if(LocalDate.now().getMonthValue()==1 || LocalDate.now().getMonthValue()==2){ %>
						<button type="submit" class="btn btn-sm " style=" background: #37783B; color: #ffffff " name="formid" value="0" formaction="DepDeclareFormView.htm"  name="action" value="add" >Initial Admission / Annual Declaration </button>
						<%} %> 
					</div>
					
				</form>
				<br>
				
				<form method="Post" >
					<div class="table-responsive">
					   	<table class="table table-bordered table-hover table-striped table-condensed"  id=""> 
							<thead>
								<tr> <td colspan="5" style="text-align:center; ">Approved Forms</td></tr>
								<tr>
									<th style="width: 5%">SN</th>
									<th> Inclusion/Exclusion </th>
									<th>Status</th>
									<th>Approved Date</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<% i=0;
								for( Object[] form:formslist){
									if(form[3].toString().equals("A")){%>
								<tr>
									<td style="width: 5%"><%=++i %></td>
									<td>
										<%if(form[2].toString().equals("I") ){ %>
											Inclusion
										<%}else if(form[2].toString().equals("E") ){ %>
											Exclusion
										<%}else if(form[2].toString().equals("D") ){ %>
											Declaration
										<%} %>		
									</td>
									<td>
										Confirmed
									</td>
									<td><%=DateTimeFormatUtil.SqlToRegularDate(form[6].toString()) %></td>
									<td>
										
										<%if(form[2].toString().equals("I") ){ %>
											<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepInclusionFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>	
											<button type="submit" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DepDeclareFormDownload.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
												<i class="fa-solid fa-download " style="color: green;"></i>
											</button>
										<%}else if(form[2].toString().equals("E") ){ %>
											<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepExclusionFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>	
											<button type="submit" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DepDeclareFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
												<i class="fa-solid fa-download " style="color: green;"></i>
											</button>
										
										<%}else if(form[2].toString().equals("D") ){ %>
											<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepDeclareFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>	
											<button type="submit" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DepDeclareFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="" data-original-title="Download">
												<i class="fa-solid fa-download " style="color: green;"></i>
											</button>
										<%} %>
									</td>
								</tr>
								<%}} %>
								<%if(i==0){ %>
									<tr><td colspan="5" style="text-align: center;">No Records Found</td></tr>
								<%} %>
							</tbody>
						</table>
					</div>	
					<input type="hidden" name="empid" value="<%=empid%>">
					<input type="hidden" name="isApprooval" value="N">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				
			</div>
		</div>				
	</div>
	       
</body>
</html>