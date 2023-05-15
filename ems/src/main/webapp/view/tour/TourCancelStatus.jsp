<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Tour Apply List</title>
</head>
<body>
<%
List<Object[]>  tourcancellist = (List<Object[]>)request.getAttribute("cancelStatuslist");
String fromdate = (String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");
String empno = (String)request.getAttribute("empno");
List<Object[]> emplist = (List<Object[]>)request.getAttribute("emplist");

Object[] empdata = (Object[])request.getAttribute("Empdata");

%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Tour Cancel StatusList <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TourCancel.htm">Tour</a></li>
						<li class="breadcrumb-item active " aria-current="page">Tour Cancel List</li>
					</ol>
				</div>
			</div>
</div>
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

<div class="container-fluid">	
      <form action="TourCancel.htm" method="post"  class="navbar-form navbar-right"  style="margin-top:8px;">
  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		  <div  class="row" align="center">
                       <div class="form-group col-sm-1"  ></div>
                         <div class="form-group col-sm-4"  >
	                         <select class="form-control input-sm selectpicker" name="empno" required="required" data-live-search="true">
									<%if(emplist!=null){
										for(Object[] emp:emplist){%>
											<option value="<%=emp[1]%>" <%if(empno.equalsIgnoreCase(emp[1].toString())){%>selected="selected" <%}%> ><%=emp[2]%>(<%=emp[3]%>)</option>
									<%}}%>
	                         </select>
                        </div>   
                      
                        <div class="form-group col-sm-1" style="text-align: right;margin-top: 4px;">
                        	<label style=" font-weight: 800">From  : &nbsp; </label>
						</div>
						<div class="form-group col-sm-1" style="padding-right: 2px;padding-left: 2px;">
							<input  class="form-control form-control"   data-date-format="dd-mm-yyyy" id="fromdate"  name="fromdate"  required="required"  style="width: 120px;">
						</div>
						
						<div class="form-group col-sm-1" style="text-align: right;margin-top: 4px;"> 
							<label style="font-weight: 800;padding-left: 5px">To  :  &nbsp; </label>
						</div>
				        <div class="form-group col-sm-1" style="padding-right: 2px;padding-left: 2px;">
							<input  class="form-control form-control" data-date-format="dd-mm-yyyy" <%if(todate!=null && todate!=""){%>value="<%=todate%>"<%}%>  id="todate"  name="todate"  style="width: 120px;">						
						</div>
						
						<div class="col-sm-1" style="margin-left: 35px;  margin-top: 3px;text-align: left;">
					   		<button type="submit" class="btn  btn-success btn-sm" name="ChooseDate" value="ChooseDate">submit</button>
                       </div>
                 </div>
</form> 

<div class="card">
	<div class="card-body">
<form action="#" method="post" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
					  			 <tr > 
                                    <td  colspan="6" style=" text-align:center; background-color: white; color:#005C97; ">
                                    <h5 style="height: 10px;">List of Canceled Tour   </h5>
                                   
                                    </td>                                  
                                 </tr>
								  <tr>
								  	  <th>SN</th>
									  <th>Date</th>
								      <th>Applied On</th>
								      <th>Name</th>
								      <th>Purpose</th>
								      <th>Status</th>
								  </tr>
					  </thead>
	                  <tbody>
	  
							 <%if(tourcancellist!=null && tourcancellist.size()!=0){
						                      int sn=0;      	   
                               for(Object[] hlo : tourcancellist){
                            	   String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(hlo[1].toString());
                                   String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(hlo[2].toString());
                                   String  applydate=DateTimeFormatUtil.fromDatabaseToActual(hlo[3].toString());
                                   long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(hlo[1].toString(), hlo[2].toString());
                            	   %>
	                             <tr>
	                             	  <td align="center"> <%=++sn%> </td>	
									  <td align="center"><%=stayfromdate%> To <%=staytodate%></td>
                               		  <td align="center"><%=applydate %><br/> for <%=noofdays%> Day(s)</td>
									  <td align="center"><%=hlo[0]+"" %></td>
									  <td><%=hlo[4]+"" %></td>
										<td>
												 <button class="btn btn-sm btn-link w-100 " formaction="Tour-CancelStatus-details.htm" name="tourapplyid" value="<%=hlo[7]%>" formtarget="_blank" 
													 data-toggle="tooltip" data-placement="top" title="Transaction History"  style=" color:<%=hlo[9]%>; font-weight: 600;" >
													  &nbsp;<%=hlo[8]%> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
											</td>
								</tr> 
                  <%}}%>
	            </tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
				</form>	
	</div>
</div>	
</div>
</body>
<script type="text/javascript">
$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	
	<%if(fromdate!=null && fromdate!=""){%>
	"startDate" :new Date("<%=fromdate%>"),
	<%}%>
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
	"minDate" :$('#fromdate').val(),
	<%if(todate!=null && todate!=""){%>
	"startDate" :new Date("<%=todate%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$( "#fromdate" ).change(function() {
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : $('#fromdate').val(), 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});
</script>
</html>