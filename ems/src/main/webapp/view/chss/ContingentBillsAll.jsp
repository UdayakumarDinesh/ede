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
	List<Object[]> ContingentList=(List<Object[]>)request.getAttribute("ContingentList");
	String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
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
				
			<div class="card" >
				<div class="card-body main-card " >
					<form action="ApprovedBills.htm" method="POST" style="float: right;">
						<div class="row" style="margin-bottom: 10px;">
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
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
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
									<%long slno=0;if(ContingentList!=null){
									for(Object[] obj : ContingentList){ 
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