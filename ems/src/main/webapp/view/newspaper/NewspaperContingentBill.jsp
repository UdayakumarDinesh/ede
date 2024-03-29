<%@page import="java.time.LocalDate"%>
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
	List<Object[]> contingentlist=(List<Object[]>)request.getAttribute("ContingentList");
	
	String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	String logintype = (String)request.getAttribute("logintype");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-4">
				<h5>Pending Contingent Bills</h5>
			</div>
				<div class="col-md-8 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm">Newspaper</a></li>
						<li class="breadcrumb-item active " aria-current="page">Pending Contingent</li>
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
				
					<form action="NewspaperContingentAppro.htm" method="POST" style="float: right;">
						<div class="row" style="margin-bottom: 10px;">
							<div class="col-12">
								<table style="width: 100%;">
									<tr>
										<td><b>From Date :&nbsp;</b></td>
										<td><input type="text" class="form-control" name="fromdate" id="fromdate" readonly="readonly"></td>
										<td><b>&nbsp;&nbsp; To date :&nbsp;</b></td>
										<td><td><input type="text" class="form-control" name="todate" id="todate" readonly="readonly"></td>
										<td>&nbsp;&nbsp;<button class="btn btn-sm submit-btn" >submit</button> </td>
									</tr>
								</table>
							</div>
						</div>							
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
							
					<form action="#" method="post" id="ClaimForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<td style="padding-top:5px; padding-bottom: 5px;">SN</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Contingent No</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Date</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Action</td>
									</tr>
								</thead>
								<tbody>
									<%long slno=0; if (contingentlist != null){
									for(Object[] obj : contingentlist){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1] %></td>
											<%if(obj[2]!=null){ %>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[2].toString()))%></td>
											<%}else{ %>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(LocalDate.now().toString()))%></td>
											<%} %>
											<td style="padding-top:5px; padding-bottom: 5px;" class="editable-click">
														 <button class="btn btn-sm btn-link w-100 " formaction="NewspaperContingentTransacStatus.htm" name="contingentid" value="<%=obj[0]%>" formtarget="_blank" 
														 data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color:<%=obj[8] %>; font-weight: 600;" >
														 
														  &nbsp;<%=obj[6] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
														
											</td>
											<td style="padding-top:5px; padding-bottom: 5px;">
												<button type="submit" class="btn btn-sm view-icon" name="contingentid" value="<%=obj[0] %>" formaction="NewspaperContingetBill.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
													<i class="fa-solid fa-eye"></i>
												</button>	
												<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="NewspaperContingentBillPrint.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Download">
													<i style="color: #019267" class="fa-solid fa-download"></i>
												</button>
												<input type="hidden" name="claim_view_mode" value="E">
											</td>
										</tr>
									<%}} %>
								</tbody>
							</table>
						</div>
					</form>
					<%if(logintype.equalsIgnoreCase("K")){ %>
					<div class="row" style="margin-top: 10px;">
					
						<div class="col-12"  align="center" >
							<form action="NewspaperListToBill.htm" method="post">
								<button class="btn btn-sm" style="background-color: #FFD36E " name="claims_type" value="" >Generate Contingent Bill</button>
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
							
					</div>
					<%} %>
				</div>
			</div>		
			
		</div>
	
	 </div>

</body>
<script type="text/javascript">

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


</script>
</html>