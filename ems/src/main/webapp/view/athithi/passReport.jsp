<%@page import="java.util.Date"%>
<%@page import="java.text.Format"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body style="background-image: url('vtsfolder/images/background.png')">
<%
List<Object[]> passList=(List<Object[]>)request.getAttribute("passReportList");
String fdate=request.getAttribute("fdate").toString();
String tdate=request.getAttribute("tdate").toString();
SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12" style="top: 10px;">
				<div class="card shadow-nohover">
					<div class="card-header">
								
						<div class="col-md-12" >
					   			<form class="form-inline" method="post" action="passReport.htm" >
					   			<h5>Pass Report</h5>
								<div class="col-md-4"></div>
								<div class="form-group">
									<label for="fdate">From Date:</label>
									 <input class="form-control" data-date-format="dd/mm/yyyy" id="fdate" name="fdate" required="required" value="<%=fdate%>"  style="margin-left: 5px;">
								</div>
								<div class="form-group">
									<label for="tdate" style="margin-left: 5px;">To Date:</label>
									 <input class="form-control" data-date-format="dd/mm/yyyy" id="tdate" name="tdate" required="required"  value="<%=tdate%>" style="margin-left: 5px;">
								</div>
								<button type="submit" class="btn  btn-sm submit " style="margin-left: 20px;">Submit</button>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
								<div  style="font-size: 20px; margin-top: -35px;"  align="right">
								<button class="btn" onclick="tableToExcel('myTable1', 'passReport.htm')" type="submit">
									<i class="fa-regular fa-file-excel" aria-hidden="true" style="font-size: 1.5em; color: green;"></i></button>
								</div>
		   					</div>
						
					</div>
					<div class="card-body">
						<table class="table table-bordered table-hover table-striped table-condensed" id="myTable1">
							<thead>
							<tr>
							<th colspan="8" style="text-align: center;">PASS REPORT: <%=fdate%>&nbsp;to&nbsp;<%=tdate%></th>
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
				
<script>

$( "#fdate" ).change( function(){
    
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