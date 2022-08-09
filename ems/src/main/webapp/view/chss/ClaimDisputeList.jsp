<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<style>
	body{
		overflow-x:hidden !important; 
	}
</style>

</head>
<body>

<%
	List<Object[]> ClaimDisputeList=(List<Object[]>)request.getAttribute("ClaimDisputeList");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
%>
 
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Claim Disputes</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item active " aria-current="page">Claims Disputes</li>
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
				<div class="card-body main-card " >
				
			
							 <div class="row" >
								<div class="col-md-12">
									<form action="" method="post" id="ClaimForm">
									
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										<div class="table-responsive">
								   			<table class="table table-bordered table-hover table-striped table-condensed" style="min-width:100%" id="myTable"> 
												<thead>
													<tr>
														<td style="text-align: center;width: 5%;">SN</td>
														<td style="width: 35%;">Employee</td>
														<td style="width: 25%;">Claim No</td>
														<td style="width: 10%;">Raised Date</td>
														<td style="width: 15%;">Status</td>
														<td style="width: 10%;">Action</td>
													</tr>
												</thead>
												<tbody>
													<%long slno=0;
													for(Object[] obj : ClaimDisputeList){ 
														slno++; %>
														<tr>
															<td style="text-align: center;" ><%=slno %></td>
															<td ><%=obj[9] %></td>
															<td style="text-align: center;" ><%=obj[10] %></td>
															<td style="text-align: center;" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[4].toString()) %></td>
															<td >
																<%if(obj[5]!=null && Long.parseLong(obj[5].toString())!=0){ %>
																	<span style="color: green;">Response Submitted</span>																
																<%}else { %>
																	<span style="color: red;">Response Pending</span>
																<%} %>
															
															</td>
															<td >
																
																<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[1] %>" formaction="CHSSForm.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
																	<i class="fa-solid fa-eye"></i>
																</button>	
																
																<input type="hidden" name="isapproval" value="Y">
																<input type="hidden" name="show-edit" value="N">
																<input type="hidden" name="dispReplyEnable" value="Y">	
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
<script type="text/javascript">
$("#myTable1").DataTable({
    "lengthMenu": [ 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});

</script>

</body>
</html>