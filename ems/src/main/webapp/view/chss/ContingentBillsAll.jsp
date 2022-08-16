<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
    <%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
	List<Object[]> ProgressBills=(List<Object[]>)request.getAttribute("ProgressBills");
	List<Object[]> ApprovedBills=(List<Object[]>)request.getAttribute("ApprovedBills");
	
	String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
	
	String tab   = (String)request.getAttribute("tab");
	if(tab==null)
	{
		tab="progress";
	}
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Contingent Bills</h5>
			</div>
				<div class="col-md-8 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page"> Contingent Bills</li>
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
	
			<form action="ContingentBillsList.htm" method="POST" style="float: right;">
				<div class="row " style="margin-bottom: 10px;">
					<div class="col-12">
						<table style="width: 100%;">
							<tr>
								<td><b>From Date :&nbsp;</b></td>
								<td><input type="text" class="form-control" name="fromdate" id="fromdate" readonly="readonly"> </td>
								<td><b>&nbsp;&nbsp; To date :&nbsp;</b></td>
								<td><td><input type="text" class="form-control" name="todate" id="todate" readonly="readonly"> </td>
								<td>&nbsp;&nbsp;<button class="btn btn-sm submit-btn" >submit</button> </td>
							</tr>
						</table>
					</div>
				</div>			
				<input type="hidden" name="tab" id="def_tab" value="<%=tab%>"/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
			<div class="row w-100" style="margin-bottom: 10px;">
				<div class="col-12">	
					<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist" style="background-color: #E1E5E8;padding:0px;">
						<li class="nav-item" style="width: 50%;"  >
							<a class="nav-link active" style="text-align: center;" id="pills-tab-progress" data-toggle="pill" href="#pills-progress" role="tab" aria-controls="pills-pending" aria-selected="true" onclick="changeTab('progress');" >In Progress</a>
						</li>
						<li class="nav-item"  style="width: 50%;">
							<a class="nav-link" style="text-align: center;" id="pills-tab-approved" data-toggle="pill" href="#pills-approved" role="tab" aria-controls="pills-closed" aria-selected="false" onclick="changeTab('approved');">Approved</a>
						</li>
					</ul>
				</div>
			</div>
		
		
		<div class="tab-content" id="pills-tabContent">
			
			<div class="card tab-pane show active" id="pills-progress" role="tabpanel" aria-labelledby="pills-pending-tab" >
				<div class="card-body main-card " >
				
					 <div class="row " >
						<div class="col-md-12">
									
							<form action="#" method="post" id="ClaimForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="table-responsive">
						   			<table class="table table-bordered table-hover table-striped table-condensed" style="min-width:100%"  id="myTable"> 
										<thead>
											<tr>
												<td style="padding-top:5px; padding-bottom: 5px;">SNo</td>
												<td style="padding-top:5px; padding-bottom: 5px;" >ContingentBill No</td>
												<td style="padding-top:5px; padding-bottom: 5px;">Bill Date</td>
												<!-- <td style="padding-top:5px; padding-bottom: 5px;">Approval Date</td> -->
												<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
												<td style="padding-top:5px; padding-bottom: 5px;max-width: 15% !important">Action</td>
											</tr>
										</thead>
										<tbody>
											<%long slno=0;
											if(ProgressBills!=null){
											for(Object[] obj : ProgressBills){ 
												slno++; %>
												<tr>
													<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
													<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1] %></td>
													<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[2].toString()))%></td>
													<%-- <td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><% if(obj[8]!=null){ %><%=rdf.format(sdf.parse(obj[8].toString()))%><%}else{ %>-<%} %></td> --%>
													<td 
														<%if("1".equals(obj[5].toString()) || "2".equals(obj[5].toString()) ){%>  
													    style=" padding-top:5px; padding-bottom: 5px;color:#4700D8; font-weight: 600;"				
														<%}else if("3".equals(obj[5].toString())||"5".equals(obj[5].toString()) ||"7".equals(obj[5].toString())||"9".equals(obj[5].toString()) || "11".equals(obj[5].toString())||"13".equals(obj[5].toString())){%>
														 style="  padding-top:5px; padding-bottom: 5px;color:#B20600; font-weight: 600;"     
														<%}else  if("4".equals(obj[5].toString())||"6".equals(obj[5].toString())||"8".equals(obj[5].toString())|| "10".equals(obj[5].toString()) || "12".equals(obj[5].toString())){%>
															style="padding-top:5px; padding-bottom: 5px; color:#F66B0E; font-weight: 600;"
														<%}else if("14".equals(obj[5].toString())){ %>
														style=" padding-top:5px; padding-bottom: 5px;color:#6BCB77; font-weight: 600;"
														<%}else{ %>
														 style="padding-top:5px; padding-bottom: 5px; color:#4700D8; font-weight: 600;"
														<%} %>	
													>
														
														<%=obj[7] %>
													
													</td>
													<td style="padding-top:5px; padding-bottom: 5px;">
														
														<button type="submit" class="btn btn-sm view-icon" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBill.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
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
		
			
			<div class="card tab-pane " id="pills-approved" role="tabpanel" aria-labelledby="pills-closed-tab" >
				<div class="card-body main-card " >
					
			
					 <div class="row" >
					 	
						<div class="col-md-12">
							
							<form action="#" method="post" id="ClaimForm">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								<div class="table-responsive">
						   			<table class="table table-bordered table-hover table-striped table-condensed" style="min-width:100%"  id="myTable1"> 
										<thead>
											<tr>
												<td style="padding-top:5px; padding-bottom: 5px;">SNo</td>
												<td style="padding-top:5px; padding-bottom: 5px;" >ContingentBill No</td>
												<td style="padding-top:5px; padding-bottom: 5px;">Bill Date</td>
												<td style="padding-top:5px; padding-bottom: 5px;">Approval Date</td>
												<td style="padding-top:5px; padding-bottom: 5px;max-width: 20% !important">Action</td>
											</tr>
										</thead>
										<tbody>
											<% slno=0;if(ApprovedBills!=null){
											for(Object[] obj : ApprovedBills){ 
												slno++; %>
												<tr>
													<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
													<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1] %></td>
													<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[2].toString()))%></td>
													<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><% if(obj[8]!=null){ %><%=rdf.format(sdf.parse(obj[8].toString()))%><%} %></td>
													<td style="padding-top:5px; padding-bottom: 5px;">
														
														<button type="submit" class="btn btn-sm view-icon" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBill.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Contingent Bill View">
															<i class="fa-solid fa-eye"></i>
														</button>	
														<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBillDownload.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Contingent Bill Download">
															<i style="color: #019267" class="fa-solid fa-download"></i>
														</button>
														
														<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingentBillPayReport.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Consolidated Report View">
															<i class="fa-regular fa-file-lines"></i>
														</button>
														<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingentBillPayReportDownload.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Consolidated Report Download">
															<i style="color: #019267" class="fa-solid fa-file-arrow-down"></i>
														</button>
														
														<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ConsolidatedExcelDownload.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Consolidated Excel Download">
															<i class="fa-solid fa-table"></i>
														</button>
														<input type="hidden" name="isapproval" value="Y">
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
			
		
		</div>
		
		
	
	<%-- 
	
			<div class="card" >
				<div class="card-body main-card " >
					
					<form action="#" method="post" id="ClaimForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<td style="padding-top:5px; padding-bottom: 5px;">SNo</td>
										<td style="padding-top:5px; padding-bottom: 5px;" >ContingentBill No</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Bill Date</td>
										<!-- <td style="padding-top:5px; padding-bottom: 5px;">Approval Date</td> -->
										<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
										<td style="padding-top:5px; padding-bottom: 5px;max-width: 15% !important">Action</td>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
									if(ProgressBills!=null){
									for(Object[] obj : ProgressBills){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1] %></td>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[2].toString()))%></td>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><% if(obj[8]!=null){ %><%=rdf.format(sdf.parse(obj[8].toString()))%><%}else{ %>-<%} %></td>
											<td 
												<%if("1".equals(obj[5].toString()) || "2".equals(obj[5].toString()) ){%>  
											    style=" padding-top:5px; padding-bottom: 5px;color:#4700D8; font-weight: 600;"				
												<%}else if("3".equals(obj[5].toString())||"5".equals(obj[5].toString()) ||"7".equals(obj[5].toString())||"9".equals(obj[5].toString()) || "11".equals(obj[5].toString())||"13".equals(obj[5].toString())){%>
												 style="  padding-top:5px; padding-bottom: 5px;color:#B20600; font-weight: 600;"     
												<%}else  if("4".equals(obj[5].toString())||"6".equals(obj[5].toString())||"8".equals(obj[5].toString())|| "10".equals(obj[5].toString()) || "12".equals(obj[5].toString())){%>
													style="padding-top:5px; padding-bottom: 5px; color:#F66B0E; font-weight: 600;"
												<%}else if("14".equals(obj[5].toString())){ %>
												style=" padding-top:5px; padding-bottom: 5px;color:#6BCB77; font-weight: 600;"
												<%}else{ %>
												 style="padding-top:5px; padding-bottom: 5px; color:#4700D8; font-weight: 600;"
												<%} %>	
											>
												
												<%=obj[7] %>
											
											</td>
											<td style="padding-top:5px; padding-bottom: 5px;">
												
												<button type="submit" class="btn btn-sm view-icon" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBill.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
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
			 --%>
		</div>
	
	 </div>

</body>



<script type="text/javascript">
$("#myTable1").DataTable({
    "lengthMenu": [ 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});

function changeTab(tab)
{
	$('#def_tab').val(tab);
}

$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : true,
	"showCustomRangeLabel" : true,
	"maxDate" :new Date(), 
	"startDate" :new Date('<%=fromdate%>'), 
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
	"maxDate" :new Date(), 
	"startDate" :new Date('<%=todate%>'), 
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

<%if(tab!=null ){%>

$('#pills-tab-<%=tab%>').click();

<%}%>

</script>


		
</html>