<%@page import="java.text.DecimalFormat"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<body>
<%
List<Object[]> claimslist = (List<Object[]>)request.getAttribute("claimslist");
List<Object[]> emplist = (List<Object[]>)request.getAttribute("emplist");
String fromdate = (String)request.getAttribute("fromdate");
String todate   = (String)request.getAttribute("todate");
String EmpNO = (String)request.getAttribute("EmpNO");
String status   = (String)request.getAttribute("status");

SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();

IndianRupeeFormat nfc=new IndianRupeeFormat();
DecimalFormat df = new DecimalFormat( "#####################");
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
				<h5>Pending Newspaper Claims</h5>
			</div>
				<div class="col-md-6 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm"> Newspaper </a></li>
						<!-- <li class="breadcrumb-item "><a href="NewspaperApproval.htm"> Newspaper List </a></li> -->
						<li class="breadcrumb-item active " aria-current="page">Claims</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	
 		<div class="page card dashboard-card">	

			<div class="card" >
			
				<div class="card-header" style="height: 4rem">
					<form action="NewspaperClaimsList.htm" method="POST" id="myform">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="row ">
					
						<div class="col-2"><h6>Employee </h6></div>
						<div class="col-2" align="left" style="margin-left: -10%;">
							<select class="form-control select2" name="EmpNO"   required="required" onchange="this.form.submit();" >
								<option value="0"<% if("0".toString().equalsIgnoreCase(EmpNO)){%> selected<%}%> >All</option>
							    	<%if(emplist!=null){ %>
									    <%for(Object[] obj:emplist){%>
											<option value="<%=obj[0]%>"<% if(obj[0].toString().equalsIgnoreCase(EmpNO)){%> selected<%}%> ><%=obj[2]%></option>
										<%}%>
									<%} %>
							</select>
					   </div>
					
						    <div class="col-2"  ><h6>From Date :</h6></div>
					        <div class="col-1" style="margin-left: -10%;"> 
								<input type="text" style="width: 145%;"  class="form-control input-sm mydate"   readonly="readonly"  value=""  id="fromdate" name="fromdate"  required="required"   > 
							</div>
							 
							<div class="col-2"  style="margin-left: 1%;"><h6>To Date :</h6></div>
							<div class="col-1" style="margin-left: -10%;">						
								<input type="text" style="width: 145%;"  class="form-control input-sm mydate"  readonly="readonly"   value=""  id="todate" name="todate"  required="required"  > 							
							</div>
							
							<div class="col-1"  style="margin-left: 1%;"><h6>Status :</h6></div>
							<div class="col-2" style="margin-left: -3%;">						
								<select class="form-control"  name="status" style="width: 100%;"   required="required" onchange="this.form.submit();">
										<option value="I" <%if(status.equalsIgnoreCase("I")){ %>selected <%} %>> In Progress</option>
										<option value="A" <%if(status.equalsIgnoreCase("A")){ %>selected <%} %>> Approved</option>
								</select> 							
							</div>
							
							
					
							
					</div>
							 
				   </form>   
				
				</div>
			
				<div class="card-body main-card">
			 	

					<form action="#" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable">
			            <thead>
			                <tr>
			                    <th>Name &amp; Designation</th>
			                    <th>Claim Month</th>
			                    <th>Claim Year</th>			        
			                    <th>Applied Date</th>			        
			                    <th>Claimed Amt</th>			        
			                    <th>Status</th>			        
			                    <th>Action</th>
			                </tr>
			            </thead>
			           
			            <tbody>
			            <%if(claimslist!=null && claimslist.size()>0){%>
			            <%for(Object[] ls:claimslist){ %>
			            
			             <tr>
			                    <td style="text-align:left;"><%= ls[2]%>,&nbsp;<%=ls[3]%></td>
			                    <td style="text-align: center;"><%=ls[4] %></td>
			                    <td style="text-align: center;"><%=ls[5] %></td>
			                    <td style="text-align: center;"><%=rdf.format(ls[10]) %></td>
			                    <td style="text-align: right ;"><%= nfc.rupeeFormat(df.format(ls[9]))%></td>
			                    <td>
					                    	<button type="submit" class="btn btn-sm btn-link w-100"
											formaction="NewspaperApplyTransacStatus.htm" value="<%=ls[0]%>"
											name="NewspaperId" data-toggle="tooltip" data-placement="top"
											title="Transaction History" formnovalidate="formnovalidate"
											style="color: <%=ls[8]%>; font-weight: 600;"
											formtarget="_blank">
											&nbsp;
											<%=ls[7]%>
											<i class="fa-solid fa-arrow-up-right-from-square"
												style="float: right;"></i>
										</button>
					                    </td>
			                    <td align="center">
			                    		<button type="submit" class="btn btn-sm view-icon"
											formaction="NewspaperClaimPreview.htm" name="NewspaperId"
											value="<%=ls[0]%>" formnovalidate="formnovalidate"
											data-toggle="tooltip" data-placement="top"
											title="Form For Newspaper Claim"
											style="font-weight: 600;">
											<i class="fa-solid fa-eye"></i>
										</button>
										<button type="submit" class="btn btn-sm" name="NewspaperId"
										value="<%=ls[0]%>" formaction="NewspaperPrint.htm"
										 formnovalidate="formnovalidate" formtarget="_blank"
										data-toggle="tooltip" data-placement="top" title="Download">
										<i style="color: #019267" class="fa-solid fa-download"></i>
									</button></td>
			                    </td>
			                    
			                    
			           </tr>
			           <%} }%>
			           </tbody>
			          </table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
						
						<input type="hidden" name="view_mode" value="V">
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