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
String fromdate =(String)request.getAttribute("frmDt");		
String todate = (String)request.getAttribute("toDt");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	List<Object[]> ClosedList=(List<Object[]>)request.getAttribute("TicketClosedList");
	
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Ticket Closed List</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active " aria-current="page">Ticket Closed</li>
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
	
	<div class="card">
			<div class="card-header" style="height: 4rem" >
				<form action="TicketClosed.htm" method="POST" action="myform">
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
	
		
			<div class="card" >
				<div class="card-body"  style=" max-height: 34rem; overflow-y:auto;" >
				
					<form action="##" method="POST"  >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"   > 
								<thead>
									<tr>
										<th style="width: 1%">SN</th>
										<th style="width: 10%">Raised By</th>
										<th style="width: 10%">Raised Date</th>
										<th style="width: 10%">Closed By</th>
										<th style="width: 10%">Closed Date</th>
										<th style="width: 10%">Action</th>																		
									</tr>
								</thead>
								<tbody>
									
							<%int count=1; %>
								<%for(Object[] closedlist:ClosedList) {
								if(closedlist[5].toString().equalsIgnoreCase("C") ){%>
								<tr>
									<td style="width: 2%; text-align: center;"><%=count %></td>
									<td style="width: 2%" ><%=closedlist[2] %></td>
									<td style="width: 2%"><%=rdf.format(sdf.parse(closedlist[3].toString())) %></td>
									<td style="width: 2%"><%=closedlist[8] %></td>
									
									<td style="width: 3%;text-align: center;"><%=rdf.format(sdf.parse(closedlist[7].toString()))%></td>
									<td style="width: 2%;text-align: center;" >
									<%if(!closedlist[4].toString().equals("")) {%>
									<button type="submit" class="btn btn-sm " name="TicketId" value="<%=closedlist[0]%>" formaction="TicketFormDownload.htm"  formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
										
										<%} %>
										
										<button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=closedlist[0]%>"  onclick="openModal('<%=closedlist[0]%>')"   data-toggle="tooltip" title="" data-original-title="Closed Details">
															<i class="fa fa-eye " style="color: black;"></i>
														</button>
										
										<input type="hidden" name="TicketId" value="">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										
									</td>
								</tr>
								 <%count++;%>
								<%}} %>
								<%if(ClosedList.size()==0){ %>
							<tr><td  colspan="6" style="text-align: center;"> No Closed Forms </td></tr>
								<%} %>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
				</form>		
			  </div>
		   	 </div>				
	        </div>
	        </div>	
		
		 <div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="my-ticket-modal">
		   <div class="modal-dialog modal-lg" role="document" style="width: 42% ;height: 60% ;">
			<div class="modal-content">
					<div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Ticket Closed Details</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div> 
				<div class="modal-body" align="center" style="margin-top:-4px;">
					<form action="#" method="post" autocomplete="off"  >
						<table style="width: 100%;">
							<tr>
								<th style="padding: 5px;" >Raised By :</th>
								<td style="padding: 5px;" id="modal-raisedby"></td>
								<th style="padding: 5px;" >Raised Date :</th>
								<td style="padding: 5px;" id="modal-raiseddate"></td>
							</tr>
							<tr>
								<th style="padding: 5px;width:17%;" >Category :</th>
								<td style="padding: 5px;" class="tabledata" id="modal-category"></td>
								<th style="padding: 5px;width:20%;" >Sub-Category :</th>
								<td style="padding: 5px;" class="tabledata" id="modal-subcategory"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Assigned By :</th>
								<td style="padding: 5px;" class="tabledata" id="modal-assignedby"></td>
								<th style="padding: 5px" >Assigned Date :</th>
								<td style="padding: 5px;" class="tabledata" id="modal-assigneddate"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Closed By :</th>
								<td style="padding: 5px;" class="tabledata" id="modal-closedby"></td>
								<th style="padding: 5px;width:20%;" >Closed Date :</th>
								<td style="padding: 5px;" class="tabledata" id="modal-closeddate"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Assigned To :</th>
								<td style="padding: 5px;" class="tabledata" id="modal-assignedto"></td>
								<th style="padding: 5px;width:20%;" >Priority :</th>
								<td style="padding: 5px;" class="tabledata" id="modal-priority">
								</td>
							</tr>
							
					<tr><th><hr style="width:600%;"/></th></tr>
							<tr>
								<th style="width:17%;padding: 5px;"> Description :</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;" colspan="3" id="modal-Desc"></td>
							</tr>
							
							<tr id="feed">
								<th style="width:17%;padding: 5px;"> Feedback :</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;color:blue;" colspan="3" id="modal-feedback"></td>
							</tr>
							
						</table>
						</form>
					         <input type="hidden"  name="TicketId1" id="TicketId1" value=""> 
						
						
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					
				</div>
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

var L=null;
var M=null;
var H=null;
function openModal(TicketId){
	
$("#TicketId").val(TicketId);
	
	
	$.ajax({

		type : "GET",
		url : "GetTicketClosedDataAjax.htm",
		data : {
				
			TicketId : TicketId,
		},
		datatype : 'json',
		success : function(result) {
		/* var result1 = JSON.parse(result) */
		var result1 = $.parseJSON(result);
		console.log(result1);
		
		$.each(result1, function(key, value) {
			
		    $('#modal-raisedby').html(value[2]);
		
			$('#modal-category').html(value[8]);
			
			$('#modal-subcategory').html(value[9]);
			
			$('#modal-assignedby').html(value[17]);
			$('#modal-closedby').html(value[18]);
			$('#modal-assignedto').html(value[16]);
			
			if(value[20]===null){
				$("#feed").hide();
			}
			else{
				$("#feed").show();
				$('#modal-feedback').html(value[20]);
			}
			
			  if(value[7]=="L"){
				L="Low"
				$("#modal-priority").html(L);	
			} 
			 if(value[7]=="M"){
				M="Medium"
				$('#modal-priority').html(M);	
			}
			 if(value[7]=="H"){
				H="High"
				$("#modal-priority").html(H);
			} 
			  
			 document.getElementById('TicketId1').value= value[0];
			
			  var d = new Date(value[6]),
			 
			        month = '' + (d.getMonth() + 1),
			        day = '' + d.getDate(),
			        year = d.getFullYear();
			 
			    if (month.length < 2){ 
			        month = '0' + month;}
			    if (day.length < 2){ 
			        day = '0' + day;}
			    var demandDate=[day,month,year].join('-');
			    
			 	var d1 = new Date(value[11]),
				 
		        month = '' + (d1.getMonth() + 1),
		        day = '' + d1.getDate(),
		        year = d1.getFullYear();
		 
		    if (month.length < 2){ 
		        month = '0' + month;}
		    if (day.length < 2){ 
		        day = '0' + day;}
		    var demandDate1=[day,month,year].join('-');
		    
		    var d2 = new Date(value[11]),
			 
	        month = '' + (d2.getMonth() + 1),
	        day = '' + d2.getDate(),
	        year = d2.getFullYear();
	 
	    if (month.length < 2){ 
	        month = '0' + month;}
	    if (day.length < 2){ 
	        day = '0' + day;}
	    var demandDate2=[day,month,year].join('-');
			   
	    $('#modal-closeddate').html(demandDate2); 
			  $('#modal-assigneddate').html(demandDate1); 
			$('#modal-raiseddate').html(demandDate);
			$('#modal-Desc').html(value[5]);
			
		}
		
		);
		
		 $('#my-ticket-modal').modal('toggle'); 
		
    }
});
	
}


$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	
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