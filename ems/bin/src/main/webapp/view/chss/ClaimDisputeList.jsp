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
	List<Object[]> ClosedDisputesList=(List<Object[]>)request.getAttribute("ClosedDisputesList");
	
	String fromdate = (String)request.getAttribute("fromdate");
	String todate   = (String)request.getAttribute("todate");
	
	String tab   = (String)request.getAttribute("tab");
	
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
	
	<div class="card-body" style="padding-top: 0px;" >
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
				
				
		<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist" style="background-color: #E1E5E8;padding:0px;">
		  <li class="nav-item" style="width: 50%;"  >
		    <a class="nav-link active" style="text-align: center;" id="pills-pending-tab" data-toggle="pill" href="#pills-pending" role="tab" aria-controls="pills-pending" aria-selected="true">Response Pending</a>
		  </li>
		  <li class="nav-item"  style="width: 50%;">
		    <a class="nav-link" style="text-align: center;" id="pills-closed-tab" data-toggle="pill" href="#pills-closed" role="tab" aria-controls="pills-closed" aria-selected="false">Response submitted</a>
		  </li>
		 
		</ul>
		
		<div class="tab-content" id="pills-tabContent">
			<div class="card tab-pane  show active"id="pills-pending" role="tabpanel" aria-labelledby="pills-pending-tab" >
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
												<th style="width: 35%;">Employee</th>
												<th style="width: 25%;">Claim No</th>
												<th style="width: 10%;">Raised Date</th>
												<th style="width: 15%;">Status</th>
												<th style="width: 10%;">Action</th>
											</tr>
										</thead>
										<tbody>
											<%long slno=0;
											for(Object[] obj : ClaimDisputeList){ 
												slno++; 
												
												%>
												<tr>
													<td style="text-align: center;" ><%=slno %></td>
													<td ><%=obj[9] %></td>
													<td style="text-align: center;" ><%=obj[10] %></td>
													<td style="text-align: center;" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[4].toString()) %></td>
													<td ><span style="color: red;">Response Pending</span></td>
													<td >
														<%if(obj[12].toString().equalsIgnoreCase("OPD")){ %>
														<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[1] %>" formaction="CHSSFormEdit.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
														<%}else{ %>
														<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[1] %>" formaction="CHSSIPDFormEdit.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>
														<%} %>
														<input type="hidden" name="view_mode" value="A">
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
		
			
			<div class="card tab-pane " id="pills-closed" role="tabpanel" aria-labelledby="pills-closed-tab" >
				<div class="card-body main-card " >
					
					<form method="post" action="ClaimDisputeList.htm"  >
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="hidden" name="tab" value="closed"/>
					<div class="row w-100" style="margin-top: 10px;margin-bottom: 10px;">
						<div class="col-md-12" style="float: right;">
							<table style="float: right;">
								<tr>
									<td> From Date :&nbsp; </td>
							        <td> 
										<input type="text" class="form-control input-sm mydate"   readonly="readonly"  value=""  id="fromdate" name="fromdate"  required="required"   > 
									</td>
									<td>To Date :&nbsp;</td>
									<td>					
										<input type="text"  class="form-control input-sm mydate"  readonly="readonly"   value=""  id="todate" name="todate"  required="required"  > 							
									</td>
									<td>					
										<button type="submit" class="btn btn-sm submit-btn" > Submit</button> 							
									</td>
								</tr>
							</table>
					 	</div>
					 </div>
					</form>
					 <div class="row" >
					 	
						<div class="col-md-12">
							<form action="" method="post" id="ClaimForm">
										
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="table-responsive">
						   			<table class="table table-bordered table-hover table-striped table-condensed" style="min-width:100%" id="myTable1"> 
										<thead>
											<tr>
												<th style="text-align: center;width: 5%;">SN</th>
												<th style="width: 35%;">Employee</th>
												<th style="width: 25%;">Claim No</th>
												<th style="width: 10%;">Raised Date</th>
												<th style="width: 15%;">Status</th>
												<th style="width: 10%;">Response Date</th>
												<th style="width: 10%;">Action</th>
											</tr>
										</thead>
										<tbody>
											<% slno=0;
											for(Object[] obj : ClosedDisputesList){ 
												slno++; 
												
											%>
												
												<tr>
													<td style="text-align: center;" ><%=slno %></td>
													<td ><%=obj[9] %></td>
													<td style="text-align: center;" ><%=obj[10] %></td>
													<td style="text-align: center;" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[4].toString()) %></td>
													<td ><span style="color: green;">Response Submitted</span></td>
													<td style="text-align: center;" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[6].toString()) %></td> 
													<td >
														<%if(obj[13].toString().equalsIgnoreCase("OPD")){ %>
														<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[1] %>" formaction="CHSSFormEdit.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
														<%}else{ %>
														<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[1] %>" formaction="CHSSIPDFormEdit.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>
														<%} %>
														<input type="hidden" name="view_mode" value="A">
														<input type="hidden" name="dispReplyEnable" value="Y">	
													</td>
												</tr>
											<%}%>
												
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
</div>
<script type="text/javascript">
$("#myTable1").DataTable({
    "lengthMenu": [ 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});

$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	 "startDate" : new Date('<%=fromdate%>'), 
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
	
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"startDate" : new Date('<%=todate%>'), 
		"minDate" :$("#fromdate").val(),  
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});

	$(document).ready(function(){
		   $('#fromdate, #todate').change(function(){
		       $('#myform').submit();
		    });
		});


	
	<%if(tab!=null && tab.equals("closed")){%>
	
		$('#pills-closed-tab').click();
	
	<%}%>
	

</script>

</body>
</html>