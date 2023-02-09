<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.SimpleDateFormat"%>  
  
  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

<%  


List<Object[]> empList = (List<Object[]>)request.getAttribute("emplist");
List<Object[]> claimsReportList = (List<Object[]>)request.getAttribute("claimsReportList");
String employeeId=(String)request.getAttribute("empid");
String frmDt=(String)request.getAttribute("frmDt");
String toDt=(String)request.getAttribute("toDt");
String claimTypeSel=(String)request.getAttribute("claimtype");
String status =(String)request.getAttribute("status");

SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
IndianRupeeFormat nfc=new IndianRupeeFormat();
DecimalFormat df = new DecimalFormat( "#####################");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			      <h5>Claims Report</h5>
			</div>
			   <div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm"> CHSS </a></li>
						<li class="breadcrumb-item active " aria-current="page">Claims Report </li>
					</ol>
				</div>
			</div>
		 </div>
		
	 <div class="page card dashboard-card">	
	    	<div class="card" >
	           <div class="card-header" style="height: 4rem">
	             <form action="ClaimsReport.htm" method="POST" id="myform"> 
	               <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	               
	           	<div class="row ">
	                  <div class="col-2"><h6>Employee : </h6></div>
	                   <div class="col-2" align="left" style="margin-left: -9%;">
	                 	 <select class="form-control form-control select2" name="EmpId"  required="required" onchange="this.form.submit();" >
	                      <option value="0" <%if("0".toString().equalsIgnoreCase(employeeId)) {%>  selected  <%}%> > All </option>
	                      <%if(empList!=null) {
	                          for(Object[] obj:empList){ %>
	                          <option value="<%=obj[0]%>"<% if(obj[0].toString().equalsIgnoreCase(employeeId)){%> selected<%}%> ><%=obj[1]%></option>
	                      <%}}%>
	                    </select>
	                   </div>
	                
	                       <div class="col-2"  ><h6>From Date :</h6></div>
					       <div class="col-1" style="margin-left: -8%;"> 
						     <input type="text" style="width: 165%;"  class="form-control input-sm mydate"   readonly="readonly"  value=""  id="fromdate" name="FromDate"  required="required"   > 
							 <label class="input-group-addon btn" for="testdate"></label>              
						   </div>
							 
						
						    <div class="col-2"  style="margin-left: 2%;"><h6>To Date :</h6></div>
							<div class="col-1" style="margin-left: -10%;">						
							  <input type="text" style="width: 165%;"  class="form-control input-sm mydate"  readonly="readonly"   value=""  id="todate" name="ToDate"  required="required"  > 							
							  <label class="input-group-addon btn" for="testdate"></label>    
							</div>

                             <div class="col-2"  style="margin-left: 2%;"><h6>Claim Type :</h6></div>
                              <div class="col-2" style="margin-left: -8%;">						
								<select class="form-control"  name="ClaimType" style="width: 50%;"  onchange="this.form.submit();"   required="required">
										<option value="All"  <%if("All".toString().equalsIgnoreCase(claimTypeSel)) {%>  selected  <%}%> > All </option>
										<option value="IPD"  <%if("IPD".toString().equalsIgnoreCase(claimTypeSel)) {%>  selected  <%}%> > IPD </option>
										<option value="OPD"  <%if("OPD".toString().equalsIgnoreCase(claimTypeSel)) {%>  selected  <%}%> > OPD </option>
								</select> 							
							</div>

                        <%if(claimsReportList!=null && claimsReportList.size()>0 ) {%>

                            <div class="col-2" style="margin-left: -7%;">
                           <button type="submit" class="btn btn-lg" name="selectedParameters" value="<%=employeeId%>#<%=frmDt%>#<%=toDt%>#<%=claimTypeSel%>#<%=status%>" formaction="ClaimsReportDownload.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Claims Report PDF Download">
							 <i style="color: #cc0000" class="fa-sharp fa-solid fa-download fa-sm"></i>
						  </button>
                         </div>
                         
                        <div class="col-1" style="margin-left: -10%;">
                           <button type="submit" class="btn btn-lg" name="selectedParameters" value="<%=employeeId%>#<%=frmDt%>#<%=toDt%>#<%=claimTypeSel%>#<%=status%>" formaction="ClaimsExcelDownload.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Claims Report Excel Download">
							 <i style="color: #009900" class="fa-sharp fa-solid fa-file-excel fa-sm"></i>
						  </button>
                         </div>
                      <%} %>

	                       </div>     
	                       	<input type="hidden" name="view_mode" value="V">  
                       </form>
	                 
	             </div> <!-- card header close -->   
	             
	             <div class="card-body main-card">
			 	

					<form action="#" method="POST" id="claimReportForm">  
					    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
										  <%if(claimsReportList!=null && claimsReportList.size()>0 ) {%>
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1"> 		
				   				<thead>
									<tr>
										<th>SN</th>
										<th>Claim No</th>
										<th>Type</th>
										<th>Applicant</th>
										<th>Patient </th>
										<th>Applied Date</th>
									<!-- 	<th>No of Bills</th> -->
										<th>Claimed Amt</th>
										<th>Settled Amt</th>
									</tr>
								</thead>
								<tbody>
								     <%long slno =0;
								       long totalClaimedAmt = 0;
									   long totalSettledAmt = 0;
								     for(Object[] obj: claimsReportList)
								     {    slno++;
										  totalClaimedAmt += Math.round( Double.parseDouble(obj[1].toString()));
										  totalSettledAmt += Math.round( Double.parseDouble(obj[2].toString()));
								     %>
								<tr>
								   <td><%=slno %></td>
								  	<td><%=obj[20] %></td>
									<td><%=obj[10] %></td>
									<td><%=obj[23] %></td>
									<td><%=obj[16] %>&nbsp;(<%=obj[18] %>)</td>
									<td style="text-align: center;"><%=rdf.format(sdf.parse(obj[19].toString())) %></td>
									<!-- <td></td> -->
									<td style="text-align: right;"><%= nfc.rupeeFormat(obj[1].toString())%></td>
								    <td style="text-align: right;"><%= nfc.rupeeFormat(df.format(obj[2]))%></td>
								</tr>
								
								
								<%} %>
								
								</tbody>
								<tfoot>
								  <tr>
								   <td  colspan="6"  style="text-align: right;">Total :</td>
								    
								    <td style="text-align: right;"><%=nfc.rupeeFormat(totalClaimedAmt+"")%></td>
								    <td style="text-align: right;"><%=nfc.rupeeFormat(totalSettledAmt+"")%></td>
								  </tr>  
								</tfoot>
				   			</table>
				   			<%}else{ %>
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1"> 		
				   				<thead>
									<tr>
										<th>SN</th>
										<th>Claim No</th>
										<th>Type</th>
										<th>Applicant</th>
										<th>Patient </th>
										<th>Applied Date</th>
									<!-- 	<th>No of Bills</th> -->
										<th>Claimed Amt</th>
										<th>Settled Amt</th>
									</tr>
								</thead>
								<tbody>
								<tr>
								<td colspan="8"  style="text-align: center;">No Record Found.</td>
								</tr>
								</tbody>
								</table>
				   			<%} %>
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
	/* "minDate" :datearray,   */
	 "startDate" : new Date('<%=frmDt%>'), 
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
		"startDate" : new Date('<%=toDt%>'), 
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
//$("#myTable1").DataTable({
    // "lengthMenu": [ 50, 75, 100],
   // "pagingType": "simple",
   // "language": {
	     // "emptyTable": "No Record Found"
	   // }

//});
</script>


</html>

