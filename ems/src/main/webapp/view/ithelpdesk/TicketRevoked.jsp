<%@page import="java.util.List"%>
<%@ page import="java.util.*, java.text.DateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.text.SimpleDateFormat"%> 
<%@page import="java.time.LocalDate"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

	  <%   String fromdate =(String)request.getAttribute("frmDt");		
	       String todate = (String)request.getAttribute("toDt");
	       List<Object[]> RevokedList=(List<Object[]>)request.getAttribute("revokedlist");
	       SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	  
	%>
	<div></div>
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>Ticket Revoked</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
							<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active" aria-current="page">Ticket
						Revoked</li>
				</ol>
			</div>

		</div>
		<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
		<div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
		</div>
		<%}if(ses!=null){ %>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
		<%} %>
		
	<div class="card">
			<div class="card-header" style="height: 4rem" >
				<form action="TicketRevokeList.htm" method="POST" action="myform">
				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	              
					<div class="row justify-content-right">
						<div class="col-7">
						<div class="col-md-4 d-flex justify-content-center"  >
			            </div>
						</div>
						
						<div class="col-2" style="margin-left: 7%; font-color: black;">
							<h6 style="color:#000000;" >From Date : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -9%">
							<input type="text"
								style="width: 147%; background-color: white; text-align: left;"
								class="form-control input-sm"
								onchange="this.form.submit()" 
								 <%if(fromdate!=null){%>
								value="<%=fromdate%>" <%} %>
								readonly="readonly" 
								 id="fromdate" name="FromDate"
								required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>


						<div class="col-2" style="margin-left: 1%">
							<h6 style="color:#000000;">To Date : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -11%">
							<input type="text" style="width: 147%; background-color: white;"
								class="form-control input-sm mydate"
								
								onchange="this.form.submit()"  <%if(todate!=null){ %>  value="<%=todate%>"<%}%> 
								readonly="readonly"
								  id="todate" name="ToDate"
								required="required"> <label
								class="input-group-addon btn" for="testdate"></label>
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>
	
		<div class="card-body main-card" style="max-height: 42rem; overflow-y:auto;" >

			<form action="#" method="POST" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="table-responsive"      >
					<table
						class="table table-hover  table-striped table-condensed table-bordered table-fixed"
						id="myTable" style="width:100% ;" >
						<thead>
							<tr>
								<th style="width: 4%">SN&nbsp;</th>
								<th style="width: 7%">Raised By</th>
								<th style="width: 7%">Category</th>
								<th style="width: 10%">Sub-Category</th>
								<th style="width: 20%">Description</th>
								<th style="width: 8%">Raised Date</th>
								<th style="width: 8%">Action</th>
								
						   </tr>
						</thead>
						<tbody>
							 <%int count=0;
							 for(Object[] UserList:RevokedList){
							  String description=UserList[5].toString();	%>
						
							<tr>
							    <td style="text-align: center;"><%=++count %></td>
							    <td style="text-align: left;"><%=UserList[2]%></td>
								<td style="text-align: center;"><%=UserList[7]%></td>
								<td style="text-align: center;"><%=UserList[8]%></td>
								<td style="text-align: left;word-wrap: break-word;word-break: break-all;white-space: normal !important;"><%if(description.length()<90){%> <%=description%> <%}else{%><%=description.substring(0,90)%>
								<button type="button" class="editable-click" style="border-style:none;" name="TicketId"  id="TicketId" value="<%=UserList[0]%>" onclick="descmodal('<%=UserList[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										</button><%}%> 
								</td>
								<td style="text-align: center;"><%=rdf.format(sdf.parse(UserList[6].toString()))%></td>
								<td style="text-align: center;padding-top:5px; padding-bottom: 5px;">
								<%if(! UserList[9].toString().equalsIgnoreCase("")) {%>
												<button type="submit" class="btn btn-sm" name="TICKETID"  value="<%=UserList[0]%>" formaction="TicketFormDownload.htm"   formmethod="post" data-toggle="tooltip" data-placement="top" data-original-title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
											</button>
										<%} %> 
							</tr>
							 
							<%} %> 
						</tbody>
					</table>
                       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</div>
          </form>
      </div>
  </div>

</body>


<script type="text/javascript">

$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
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

</script>
</html>