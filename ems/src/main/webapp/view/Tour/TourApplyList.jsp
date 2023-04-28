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
List<Object[]>  tourapplylist = (List<Object[]>)request.getAttribute("applylist");
String fromdate = (String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");
List<Object[]> emplist = (List<Object[]>)request.getAttribute("emplist");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Tour List </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TourProgram.htm">Tour</a></li>
						<li class="breadcrumb-item active " aria-current="page">Tour List</li>
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
    <%--  <form action="leave-appl-status.htm" method="post"  class="navbar-form navbar-right"  style="margin-top:8px;">
  		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		  <div  class="row" align="center">
                       <div class="form-group col-sm-1"  ></div>
                         <div class="form-group col-sm-4"  >
	                         <select class="form-control input-sm selectpicker" name="employeeId" required="required" data-live-search="true">
									<%if(emplist!=null){
										for(Object[] emp:emplist){ %>
											<option value="<%=emp[1]%>" ><%=emp[2]%>(<%=emp[3]%>)</option>
									<%}} %>
	                         </select>
                        </div>   
                      
                        <div class="form-group col-sm-1" style="text-align: right;margin-top: 4px;">
                        	<label style=" font-weight: 800">From  : &nbsp; </label>
						</div>
						<div class="form-group col-sm-1" style="padding-right: 2px;padding-left: 2px;">
							<input  class="form-control form-control" <%if(fromdate!=null && fromdate!=""){%>value="<%=fromdate%>"<%}%>  data-date-format="dd-mm-yyyy" id="fromdate"  name="Fromdate"  required="required"  style="width: 120px;">
						</div>
						
						<div class="form-group col-sm-1" style="text-align: right;margin-top: 4px;"> 
							<label style="font-weight: 800;padding-left: 5px">To  :  &nbsp; </label>
						</div>
				        <div class="form-group col-sm-1" style="padding-right: 2px;padding-left: 2px;">
							<input  class="form-control form-control" data-date-format="dd-mm-yyyy" <%if(todate!=null && todate!=""){%>value="<%=todate%>"<%}%>  id="todate"  name="Todate"  style="width: 120px;">						
						</div>
						
						<div class="col-sm-1" style="margin-left: 35px;  margin-top: 3px;text-align: left;">
					   		<button type="submit" class="btn  btn-success btn-sm" name="ChooseDate" value="ChooseDate">submit</button>
                       </div>
                 </div>
</form> --%>

<div class="card">
	<div class="card-body">
<form action="TourApplyList.htm" method="post" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
					  			 <tr > 
                                    <td  colspan="6" style=" text-align:center; background-color: white; color:#005C97; ">
                                    <h5 style="height: 10px;">List of Applied Tour   </h5>
                                   
                                    </td>                                  
                                 </tr>
								  <tr>
								  	  <th>SN</th>
									  <th>Date</th>
								      <th>Applied On</th>
								      <th>Name</th>
								      <th>Purpose</th>
								      <th>Status</th>
									 <!--  <th>Action</th> -->
								  </tr>
					  </thead>
	                  <tbody>
	  
							 <%if(tourapplylist!=null&&tourapplylist.size()!=0){
						                            	   
                               for(Object[] hlo :tourapplylist){
                            	   String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(hlo[1].toString());
                                   String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(hlo[2].toString());
                                   String  applydate=DateTimeFormatUtil.fromDatabaseToActual(hlo[3].toString());
                                   long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(hlo[1].toString(), hlo[2].toString());
                            	   %>
	                             <tr>
	                             	  <td align="center"> <input type="radio" name="tourapplyId" value="<%=hlo[7]%>"></td>	
									  <td align="center"><%=stayfromdate%> To <%=staytodate%></td>
                               		  <td align="center"><%=applydate %><br/> for <%=noofdays%> Day(s)</td>
									  <td align="center"><%=hlo[0]+"" %></td>
									  <td><%=hlo[4]+"" %></td>
									  <td align="center"><%if(hlo[6]!=null && hlo[6].toString().equalsIgnoreCase("INI")){out.println("Initiated"); }else{ out.println("");} %></td>
                               </tr> 
                  <%}}%>
	            </tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
	
	              <div class="row text-center">
						<div class="col-md-12">
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
						</div>
						 
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

 <script type="text/javascript">
 
 function Edit(myfrm) {

		var fields = $("input[name='tourapplyId']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One ");

			event.preventDefault();
			return false;
		}
		return true;
	}
 </script>
</html>