<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>CHSS Claims List</title>
</head>
<body>
<body>
<%
List<Object[]> claimslist = (List<Object[]>)request.getAttribute("claimslist");
List<Object[]> emplist = (List<Object[]>)request.getAttribute("emplist");
String fromdate = (String)request.getAttribute("fromdate");
String todate   = (String)request.getAttribute("todate");
String empid = (String)request.getAttribute("empid");
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Claims List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> CHSS </a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Claims List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	
 <div class="page card dashboard-card">	

			<div class="card" >
			
				<div class="card-header" style="height: 4rem">
					<form action="ClaimsList.htm" method="POST" id="myform">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="row justify-content-end">
					 <div class="col-1" align="right"></div>
						<div class="col-2"><h6>Employee </h6></div>
						<div class="col-2" align="left" style="margin-left: -10%;">
							<select class="form-control form-control select2" name="empid"   required="required" onchange="this.form.submit();" >
							<option value="0"<% if("0".toString().equalsIgnoreCase(empid)){%> selected<%}%> >All</option>
					    	<%if(emplist!=null){ %>
						    <%for(Object[] obj:emplist){%>
							<option value="<%=obj[0]%>"<% if(obj[0].toString().equalsIgnoreCase(empid)){%> selected<%}%> ><%=obj[1]%></option>
							<%}}%>
							</select>
					   </div>
					
						     <div class="col-2"  ><h6>From Date :</h6></div>
					         <div class="col-1" style="margin-left: -10%;"> 
								    <input type="text" style="width: 145%;"  class="form-control input-sm mydate"   readonly="readonly"  value=""  id="fromdate" name="fromdate"  required="required"   > 
								    <label class="input-group-addon btn" for="testdate"></label>              
							 </div>
							 
							  <div class="col-2"  style="margin-left: 1%;"><h6>To Date :</h6></div>
							  <div class="col-1" style="margin-left: -10%;">						
								     <input type="text" style="width: 145%;"  class="form-control input-sm mydate"  readonly="readonly"   value=""  id="todate" name="todate"  required="required"  > 							
							 		 <label class="input-group-addon btn" for="testdate"></label>    
							 </div>

							 <div class="col-3" align="right"></div>
						
							
					</div>
							 
				   </form>
				
				</div>
			
				<div class="card-body">
			 	

					<form action="#" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>SN</th>
										<th>Applicant</th>
										<th>Patient </th>
										<th>Applied Date</th>
										<th>Treatment Type</th>
										<th>Status</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<%long i=0;
										for(Object[] obj : claimslist)
										{i++; %>
										<tr>
											<td><%=i %></td>
											<td><%=obj[19] %></td>
											<td><%=obj[12] %>(<%=obj[14] %>)</td>
											<td><%=obj[15] %></td>
											<td><%=obj[10] %></td>
											<td>
												 <button class="btn btn-sm btn-link w-100 " formaction="Chss-Status-details.htm" name="chssapplyid" value="<%=obj[0]%>" formtarget="_blank" 
													 data-toggle="tooltip" data-placement="top" title="Transaction History"
														<%if("1".equals(obj[9].toString()) || "2".equals(obj[9].toString()) ){%>  
														    style=" color:#2155CD; font-weight: 600;"				
															<%}else if("3".equals(obj[9].toString())||"5".equals(obj[9].toString()) ||"7".equals(obj[9].toString())||"9".equals(obj[9].toString()) || "11".equals(obj[9].toString())||"13".equals(obj[9].toString())){%>
															 style="  color:#B20600; font-weight: 600;"     
															<%}else  if("4".equals(obj[9].toString())||"6".equals(obj[9].toString())||"8".equals(obj[9].toString())|| "10".equals(obj[9].toString()) || "12".equals(obj[9].toString())){%>
																style=" color:#F66B0E; font-weight: 600;"
															<%}else if("14".equals(obj[9].toString())){ %>
															style=" color:#125C13; font-weight: 600;"
															<%}else{ %>
															 style=" color:#4700D8; font-weight: 600;"
															<%} %>
													>  &nbsp;<%=obj[18] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
											</td>
											<td>
												<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSForm.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="" data-original-title="View">
													<i class="fa-solid fa-eye"></i>
												</button>
												<button type="submit" class="btn btn-sm" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEmpDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="" data-original-title="Download">
													<i style="color: #019267" class="fa-solid fa-download"></i>
												</button>
											
											</td>
										</tr>
									<%} %>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
						<input type="hidden" name="isapproval" value="Y">
						<input type="hidden" name="show-edit" value="N">
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
</html>