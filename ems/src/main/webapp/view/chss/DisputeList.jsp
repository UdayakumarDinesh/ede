<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dispute List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<style>
	body{
		overflow-x:hidden !important; 
	}
</style>
<body>
<%
 List<Object[]> DisputeList =(List<Object[]>)request.getAttribute("DisputeList");
%>
   <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Dispute List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>	
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page">DisputeList</li>
					</ol>
				</div>
			</div>
	</div>	
	<div class="page card dashboard-card">
		
			<div align="center">
					<%String ses=(String)request.getParameter("result"); 
					String ses1=(String)request.getParameter("resultfail");
					if(ses1!=null){ %>
						<div class="alert alert-danger" role="alert"  style="margin-top: 5px;">
							<%=ses1 %>
						</div>
					<%}if(ses!=null){ %>
						<div class="alert alert-success" role="alert"  style="margin-top: 5px;">
							<%=ses %>
						</div>
					<%} %>	
			</div>
			<div class="card-body main-card " >
				
					 <div class="row " >
						<div class="col-md-12">
							<form action="" method="post" id="ClaimForm">	
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="table-responsive">
						   			<table class="table table-bordered table-hover table-striped table-condensed" style="min-width:100%" id="myTable"> 
										<thead>
											<tr>
												<th style="text-align: center;width: 5%;">SN</th>
												<th style="width:12%;">Claim No</th>
												<th style="">Employee</th>
												<th style="">Patient</th>
												<th style="">Aliment</th>
												<th style="width:8%;">Total <br>Claimed</th>
												<th style="width:8%;">Total<br> Settled</th>
												<th >Remarks</th>												
											    <th style="width: 10%;">Action</th>
											</tr>
										</thead>
										<tbody>
											<%long slno=0;
											for(Object[] obj : DisputeList){ 
												slno++; 
												
												%>
												<tr>
													<td style="text-align: center;" ><%=slno %></td>
													<td ><%=obj[3] %></td>
													<td><%=obj[2] %></td>
													<td><%=obj[4] %></td>
													<td><%=obj[5] %></td>
													<td style="text-align:right;"><%=obj[6] %></td>
													<td style="text-align:right;"><%=obj[7] %></td>
													<td style=""><%=obj[8] %></td>										    		
													<td style="text-align:center;">
													<%if(obj[9].toString().equalsIgnoreCase("Y")) {
													if(obj[10].toString().equalsIgnoreCase("OPD")){
													%>								 
													 <button class="btn btn-sm btn-link w-100 " formaction="ClaimReApply.htm" name="chssapplyid" value="<%=obj[1] %>" 
														 data-toggle="tooltip" data-placement="top" title="Re-Apply OPD" style=" color: green; font-weight: 600;" >
													  &nbsp;ReApply <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
														 
													<%} else if(obj[10].toString().equalsIgnoreCase("IPD")) {%> 
													 <button class="btn btn-sm btn-link w-100 " formaction="ClaimReApplyIPD.htm" name="chssapplyid" value="<%=obj[1] %>" 
														 data-toggle="tooltip" data-placement="top" title="Re-Apply IPD" style=" color: green; font-weight: 600;" >
													  &nbsp;ReApply <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
													<%} }%>
													<input type="hidden" name="view_mode" value="U">
													</td>
												</tr>
											<%} %> 											
										</tbody>										
									</table>
								</div>
							</form>
						</div>
					</div>				
				</div>	
	</div>
</body>
</html>