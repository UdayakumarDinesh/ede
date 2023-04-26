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
	List<Object[]> FamMemApprovedList = (List<Object[]>)request.getAttribute("FamMemApprovedList");
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	
	String tab = (String)request.getAttribute("tab");
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Forms Approval</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
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
			
			
			<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist" style="background-color: #E1E5E8;padding:0px;">
		  <li class="nav-item" style="width: 50%;"  >
		    <div class="nav-link <%if(tab.equalsIgnoreCase("P")){ %> active <%} %>" style="text-align: center;" id="pills-OPD-tab" data-toggle="pill" data-target="#pills-OPD" role="tab" aria-controls="pills-OPD" aria-selected="true">
			   <span>Pending   
				   <span class="badge badge-danger badge-counter count-badge" style="margin-left: 0px;">
				   		 <%if(formslist.size()>99){ %>
				   			99+
				   		<%}else{ %>
				   			<%=formslist.size() %>
						<%} %>			   			
				  </span>  
				</span> 
				
		    </div>
		  </li>
		  <li class="nav-item"  style="width: 50%;">
		    <div class="nav-link <%if(tab.equalsIgnoreCase("A")){ %> active <%} %>" style="text-align: center;" id="pills-IPD-tab" data-toggle="pill" data-target="#pills-IPD" role="tab" aria-controls="pills-IPD" aria-selected="false">
		    	 <span>Approved 
				   <span class="badge badge-danger badge-counter count-badge" style="margin-left: 0px;">
				   		<%if(FamMemApprovedList.size()>99){ %>
				   			99+
				   		<%}else{ %>
				   			<%=FamMemApprovedList.size() %>
						<%} %>			   			
				   </span>  
				</span> 
		    
		    
		    </div>
		  </li>
		</ul>
			
			
			
			
			
		<div class="tab-content" id="pills-tabContent">
		
			<div class=" tab-pane  show  <%if(tab.equalsIgnoreCase("P")){ %> active <%} %>" id="pills-OPD" role="tabpanel" aria-labelledby="pills-OPD-tab" >
			
			<div class="card">
			
				<div class="card-body main-card">
					<div class="table-responsive">
					<form method="Post" >
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
								
								<tr>
									<td style="width: 5%"><%=formslist.indexOf(form)+1 %></td>
									<td style="width: 5%"><%=form[5] %></td>
									<td style="width: 5%"><%=form[2] %></td>
									<td>
										<%if(form[6].toString().equals("I") ){ %>
											Inclusion
										<%}else if(form[6].toString().equals("E") ){  %>
											Exclusion
										<%}else if(form[6].toString().equals("D") ){  %>
											Declaration
										<%} %>					
									</td>
									
									<td><%=DateTimeFormatUtil.SqlToRegularDate(form[4].toString()) %></td>
									<td>
										<%if(form[6].toString().equals("I") ){ %>
										<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepInclusionFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
											<i class="fa-solid fa-eye"></i>
										</button>	
										<%}else if(form[6].toString().equals("E") ){  %>
											<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepExclusionFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>	
										<%}else if(form[6].toString().equals("D") ){  %>
											<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepDeclareFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
												<i class="fa-solid fa-eye"></i>
											</button>	
										<%} %>	
												
										<input type="hidden" name="empid" value="<%=form[1]%>">
										<input type="hidden" name="isApprooval" value="Y">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										
									</td>
								</tr>
								
								<%} %>
								<%if(formslist.size()==0){ %>
									<tr><td  colspan="6" style="text-align: center;"> No Pending Forms </td></tr>
								<%} %>
							</tbody>
						</table>
						</form>
					</div>	
					
					
				</div>
				
			</div>
		
			</div>				
		
			
			
			<div class="card tab-pane <%if(tab.equalsIgnoreCase("A")){ %> active <%} %>" id="pills-IPD" role="tabpanel" aria-labelledby="pills-IPD-tab" >	
			
				<div class="card" >
					<div class="card-body main-card " >
						
						<form action="CHSSApprovalForward.htm" method="post" id="ClaimForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							
							<br>
							<div class="table-responsive">
					   			<table class="table table-bordered table-hover table-striped table-condensed" style="width:100%"  id="myTable1"> 
									<thead>
										<tr>
											<th style="width: 5%">SN</th>
											<th style="width: 10%">Employee No</th>
											<th style="width: 30%">Employee</th>
											<th style="width: 15%"> Inclusion/Exclusion </th>
											<th style="width: 10%;text-align: center;" >AppliedDate</th>
											<th style="width: 10%;text-align: center;" >Approved Date</th>
											<th style="width: 10%" >Action</th>
										</tr>
									</thead>
									<tbody>
										<%for(Object[] form : FamMemApprovedList){ %>
											<tr>
												<td style="width: 5%"><%=FamMemApprovedList.indexOf(form)+1 %></td>
												<td style="width: 5%"><%=form[5] %></td>
												<td style="width: 5%"><%=form[2] %></td>
												<td>
													<%if(form[6].toString().equals("I") ){ %>
														Inclusion
													<%}else if(form[6].toString().equals("E") ){  %>
														Exclusion
													<%}else if(form[6].toString().equals("D") ){  %>
														Declaration
													<%} %>					
												</td>
												
												<td style="text-align: center;" ><%=DateTimeFormatUtil.SqlToRegularDate(form[4].toString()) %></td>
												<td style="text-align: center;"><%=DateTimeFormatUtil.SqlToRegularDate(form[7].toString()) %></td>
												<td>
													<%if(form[6].toString().equals("I") ){ %>
														<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepInclusionFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
														<button type="submit" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DepDeclareFormDownload.htm" formtarget="_blank" formmethod="get" data-toggle="tooltip" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
													<%}else if(form[6].toString().equals("E") ){  %>
														<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepExclusionFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
														<button type="submit" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DepDeclareFormDownload.htm" formtarget="_blank" formmethod="get" data-toggle="tooltip" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
													<%}else if(form[6].toString().equals("D") ){  %>
														<button type="submit" class="btn btn-sm view-icon" name="formid" value="<%=form[0] %>" formaction="DepDeclareFormView.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
														<button type="submit" class="btn btn-sm " name="formid" value="<%=form[0] %>" formaction="DepDeclareFormDownload.htm" formtarget="_blank" formmethod="get" data-toggle="tooltip" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
													<%} %>
													<input type="hidden" name="empid" value="<%=form[1]%>">
													<input type="hidden" name="isApprooval" value="Y">
													<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
													
												</td>
											</tr>
								
										<%} %>
									</tbody>
								</table>
								<input type="hidden" name="isapproval" value="Y">
							</div>
	
						</form>
						
						
					</div>
				</div>
			
			</div>
		
		
		</div>
	</div>
	</div>
</body>
</html>