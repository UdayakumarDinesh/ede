<%@page import="java.util.Date"%>
<%@page import="java.text.Format"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
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
<%
List<Object[]> passList=(List<Object[]>)request.getAttribute("passReportList");
String fromdate=request.getAttribute("fromdate").toString();
String todate=request.getAttribute("todate").toString();
SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
Object[] empData=(Object[])request.getAttribute("EmpData");
%>
<div class="page card dashboard-card">
	<div class="container-fluid">
		<div class="card shadow-nohover">
					<div class="card-header">
								
						<%--  <div class="col-md-12" >
					   			<form class="form-inline" >
					   			<div class="col-md-4">
					   			<h5 style="margin-left: -15%;width: 250%;">Pass Report<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						           </b></small></h5>
					   			</div>
								<div class="col-md-3">
								<div class="form-group">
									<label for="fdate" style="margin-left: 18%;">From Date:</label>
									 <input class="form-control" data-date-format="dd/mm/yyyy" id="fdate" name="fdate" required="required" value="<%=fdate%>"  style="margin-left: 5px; width: 47%;" readonly>
								</div>
								</div>
								<div class="col-md-3">
								<div class="form-group">
									<label for="tdate" style="">To Date:</label>
									 <input class="form-control" data-date-format="dd/mm/yyyy" id="tdate" name="tdate" required="required"  value="<%=tdate%>" style="margin-left: 5px;width: 47%;" readonly>
								</div>
								</div>
								<div class="col-md-1">
								    <button type="submit" class="btn  btn-sm submit-btn " style="margin-left: -12%;" >Submit</button>
								</div>
								
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								 					
							</form>
							    <div class="col-md-1" style="font-size: 20px;" align="right">							
								<button class="btn" onclick="tableToExcel('myTable1', 'passReport.htm')" type="submit" style="">
									<i class="fa-regular fa-file-excel" aria-hidden="true" style="font-size: 1.5em; color: green;"></i></button>
								</div>						
					</div>  --%>
					<form id="myTable1" method="post" action="passReport.htm" >
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="row w-100" style="margin-top: 10px;margin-bottom: 10px;">
						<div class="col-md-11" style="float: right;">
							<table style="">
								<tr>
								    <td>
								      <h5 style="width: 250%;">Pass Report<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						              </b></small></h5>
								    </td>
								    <td></td>
								    
									<td> From Date :&nbsp; </td>
							        <td> 
										<input type="text" class="form-control input-sm mydate" onchange="this.form.submit()"  readonly="readonly"  <%if(fromdate!=null){%>
								        value="<%=DateTimeFormatUtil.SqlToRegularDate(fromdate)%>" <%}%> value=""  id="fromdate" name="fromdate"  required="required"   > 
									</td>
									<td></td>
									<td >To Date :&nbsp;</td>
									<td>					
										<input type="text"  class="form-control input-sm mydate" onchange="this.form.submit()"  readonly="readonly" <%if(todate!=null){%>
								         value="<%=DateTimeFormatUtil.SqlToRegularDate(todate)%>" <%}%>  value=""  id="todate" name="todate"  required="required"  > 							
									</td>
									 <td>					
										 <!--  <button class="btn" onclick="tableToExcel('myTable1', 'passReport.htm')" type="submit" style="">
					                       <i class="fa-regular fa-file-excel" aria-hidden="true" style="font-size: 1.5em; color: green;"></i>
					                     </button> 	 -->						
									</td>
								</tr>
							</table>
					 	</div>
					 </div>
		      </form>
		      </div>
		         <div align="right" style="margin-top: -3.3%;margin-right: 6%;">
		            <button class="btn" onclick="tableToExcel('myTable1', 'passReport.htm')" type="submit" style="">
					  <i class="fa-regular fa-file-excel" aria-hidden="true" style="font-size: 1.5em; color: green;"></i>
					</button>
		           </div> 
		           <div class="card">					
					<div class="card-body">
						<table class="table table-bordered table-hover table-striped table-condensed" id="myTable1">
							<thead>
							<tr>
							<th colspan="8" style="text-align: center;background-color: #E91E63">PASS REPORT: <%=DateTimeFormatUtil.SqlToRegularDate(fromdate)%>&nbsp;to&nbsp;<%=DateTimeFormatUtil.SqlToRegularDate(todate)%></th>
							</tr>
								<tr>
								    <th style="text-align: center;">SN</th>
								    <th style="width:10%; text-align: center;">Date</th>
									<th style="text-align: center;">Company Name</th>
									<th style="text-align: center;">Visitor Name (Designation)</th>
									<th style="text-align: center;">Mobile</th>
									<th style="text-align: center;">Batch</th>
									<th style="text-align: center;">In Time</th>
									<th style="text-align: center;">Out Time</th>
								</tr>
							</thead>
							<tbody>
							<%if(passList!=null){ 
							int count=1;
							%>
							<%for(Object[] pass:passList){ %>
								<tr>
									<td  style="text-align: center;"><%=count++%></td>
									<td><%=sdf1.format(pass[0])%></td>
									<td><%=pass[4]%></td>
									<td><%=pass[5]%>&nbsp;(<%=pass[6]%>)</td>
									<td  style="text-align: center;"><%=pass[7]%></td>
									<td  style="text-align: center;"><%=pass[3]%></td>
									<%Format f = new SimpleDateFormat("HH:mm");
								      String str = f.format(pass[1]); %>
									<td  style="text-align: center;"><%=str %></td>
									<% if(pass[2]!=null){
										   String    str1 = f.format(pass[2]);%>
							
									<td style="text-align: center;"><%=str1 %></td>
											<%}else{ %>
											<td  style="text-align: center;">--</td>
											<%} %>
								</tr>
								<%}%>
		                      <%}%>
							</tbody>
						</table>
					</div>
			</div>
		</div>
	</div>
</div>	
</div>			
<script>

/* $( "#fdate" ).change( function(){
    
	$( "#tdate" ).daterangepicker({
		"singleDatePicker" : true,
	    "linkedCalendars" : false,
	    "showCustomRangeLabel" : true,
	    
	    "cancelClass" : "btn-default",
	    showDropdowns : true,
	    	locale : {
	    	format : 'DD-MM-YYYY'
	    } 
	});

	
});

$( "#fdate" ).daterangepicker({
    "singleDatePicker" : true,
    "linkedCalendars" : false,
    "showCustomRangeLabel" : true,
      
    "cancelClass" : "btn-default",
    showDropdowns : true,
    locale : {
    	format : 'DD-MM-YYYY'
    }
});

        
$("#tdate").daterangepicker({
	"singleDatePicker" : true,
    "linkedCalendars" : false,
    "showCustomRangeLabel" : true,
    "minDate" :$("#fdate").val(),  
    "startDate" : $("#fdate").val(),
    "cancelClass" : "btn-default",
    showDropdowns : true,
    	locale : {
    	format : 'DD-MM-YYYY'
    } 
}); */

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


</script>
 
<script type="text/javascript">
var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
  return function(table, name) {
    if (!table.nodeType) table = document.getElementById(table)
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
    window.location.href = uri + base64(format(template, ctx))
  }
})()

</script>

</body>


</html>