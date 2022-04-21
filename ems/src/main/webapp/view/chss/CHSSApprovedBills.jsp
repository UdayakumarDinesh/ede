<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
    <%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Approved Bills</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%
	
	
	List<Object[]> ApprovedBiils=(List<Object[]>)request.getAttribute("ApprovedBiils");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
%>

 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Approved Bills</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item"><a href="ContingentApprovals.htm">CHSS Contingent List</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Approved Bills</li>
					</ol>
				</div>
			</div>
	</div>	
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
					
					<form action="#" method="post" id="ClaimForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<td style="padding-top:5px; padding-bottom: 5px;">SNo</td>
										<td style="padding-top:5px; padding-bottom: 5px;" >ContingentBill No</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Date</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Remarks</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
										<td style="padding-top:5px; padding-bottom: 5px;">View</td>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;if(ApprovedBiils!=null){
									for(Object[] obj : ApprovedBiils){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1] %></td>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[2].toString()))%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[6] %></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[7] %></td>
											
											<td style="padding-top:5px; padding-bottom: 5px;">
												
												<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBill.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
													<i class="fa-solid fa-eye"></i>
												</button>	
												<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBillDownload.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Download">
													<i style="color: #019267" class="fa-solid fa-download"></i>
												</button>
												
											</td>
										</tr>
									<%} }%>
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