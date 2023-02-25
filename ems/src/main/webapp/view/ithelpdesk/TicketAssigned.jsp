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
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	List<Object[]> AssignedList=(List<Object[]>)request.getAttribute("TicketAssignedList");
	
	String tab = (String)request.getAttribute("tab");
	String EmpId=(String)request.getAttribute("EmpId");
	
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Ticket Assigned List</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active " aria-current="page">Ticket Assigned</li>
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
				<div class="card-body " >
				
					<form action="##" method="POST"  >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
								<thead>
									<tr>
										 <th style="width: 3%;">SN</th>
											<th style="width: 7%">Raised By</th>
											<th style="width: 7%">Category</th>
											<th style="width: 7%">Sub-Category</th>
											<th style="width: 7%">Description</th>
											<th style="width: 1%">Priority</th>
											<th style="width: 7%;text-align: center;" >Raised Date</th>
											<th style="width: 8%;text-align: center;" >Assigned To</th>
											<th style="width: 8%;text-align: center;" >Assigned By</th>
											<th style="width: 7%;text-align: center;" >Assigned Date</th>
											<th style="width:8%" >Action</th>																
									</tr>
								</thead>
								<tbody>
									
									
							<% String Low="Low";
							String Medium="Medium";
							String High="High";
							
							int count=1; %>
								<%for(Object[] assignedList:AssignedList) {
									String description=assignedList[5].toString();
								
								if(assignedList[13].toString().equalsIgnoreCase("A"))
								{%>
								
								<tr>
									<td style="width: 3%; text-align: center;"><%=count %></td>
									<td style="width: 7%" ><%=assignedList[2] %></td>
									<td style="width: 7%"><%=assignedList[8] %></td>
									<td style="width: 7%"><%=assignedList[9] %></td>
									
									<td style="width: 7%;word-wrap: break-word;word-break: break-all;white-space: normal !important;">
									<%if(description.length()<90){%> <%=description%> <%}else{%><%=description.substring(0,90)%>
								                 <button type="button" class="editable-click" style="border-style:none;" name="TicketId"  id="TicketId" value="<%=assignedList[0]%>" onclick="descmodal('<%=assignedList[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										         </button>
										         <%}%> 
									</td>
									<td style="text-align: center;width: 1%;"><% if(assignedList[7].toString().equalsIgnoreCase("L")){%><%=Low %><%} else if(assignedList[7].toString().equalsIgnoreCase("M")){%><%=Medium %><%}else{%><%=High%><%} %></td>
									<td style="text-align: center;width: 7%;"><%=rdf.format(sdf.parse(assignedList[6].toString()))%></td>
									<td style="width: 8%;text-align: left;"><%=assignedList[14] %></td>
									<td style="width: 8%;text-align: left;"><%=assignedList[15] %></td>
									<td style="text-align: center;width: 7%;"><%=rdf.format(sdf.parse(assignedList[11].toString()))%></td>
									<td style="width: 8%;text-align: center;">
												<%if(!assignedList[10].toString().equals("")) {%>
									             <button type="submit" class="btn btn-sm " name="TicketId" value="<%=assignedList[0]%>" formaction="TicketFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
										
										
										          <%} %>
										          &nbsp;&nbsp;
										       
										          <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=assignedList[0]%>"  onclick="openModal('<%=assignedList[0]%>')"   data-toggle="tooltip" title="" data-original-title="ReAssign To">
															<i class="fas fa-angle-double-left " style="color: black;"></i>
														</button>
											
													<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
													
												</td>
											</tr>
								 <%count++;%>
									<%}} %>
								
								 <%if(AssignedList.size()==0){ %>
								
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
		
			
		
	<div class="modal fade" id="descmodal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 70% !important;height: 40%;">
				<div class="modal-content" style="min-height: 80%;" >
				    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Description</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				    
					<div class="modal-body"  style="padding: 0.5rem !important;">
						<div class="card-body" style="min-height:30% ;max-height: 25% !important;overflow-y: auto;">
							<div class="row" id="descdata">
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	
	
		    
		    <div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="my-ticket-modal">
		   <div class="modal-dialog modal-lg" role="document" style="width: 42% !important;height: 60% !important;">
			<div class="modal-content">
				
					
					 <!-- <button type="button" class="close" style="margin-left:530px;margin-top:-4px;color:red;" data-dismiss="modal" aria-label="Close"  data-toggle="tooltip"   data-original-title="Close">
						<span aria-hidden="true">&times;</span>
					</button> -->
				 <!-- </div>  -->
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
								<th style="width:17%;padding: 5px;"> Description :</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;" colspan="3" id="modal-Desc"></td>
							</tr>
							
							
						</table>
						</form>
						<hr>
						<form action="TicketReAssigned.htm" method="post" autocomplete="off"  >
						
						<table   style="width:100%">
							
							<tr>
								<th style="width:23%;padding: 5px;">Assign To :&nbsp;<span class="mandatory" style="color: red;">*</span></th>
								<td style="width:34%;" >
										<select class="form-control select2 modal-select  "  name="AssignTo"  required="required" id="modal-assignto" style="width:232px;" >
													<option selected="selected" disabled="disabled">Choose..</option>
										 </select>
							   </td>
							   <th style="width:16%;padding: 5px;">Priority :<span class="mandatory" style="color: red;">*</span></th>
							   <td style="width:66%;" >
									<select class="form-control select2  "  name="Priority"  required="required" id="modal-priority" style="width:152px;" >
													
														<option selected="selected" disabled="disabled">Choose..</option>
														
													  <option value="L">Low</option>
														<option value="M">Medium</option>
														<option value="H">High</option>  
									 </select>
							</tr>
							
						</table>
						<div align="center" style="margin-top:10px;" >
								<button type="submit"  class="btn btn-sm submit-btn"  name="action"  value="submit" >Submit</button>
									
						</div>
						<input type="hidden"  name="TicketId1" id="TicketId1" value="">
						
						
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					
				
						</form>
					
				</div>
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">


var L=null;
var M=null;
var H=null;
var assignTo=null;

function openModal(TicketId){
	
$("#TicketId").val(TicketId);
	
	
	$.ajax({

		type : "GET",
		url : "GetTicketAssignedDataAjax.htm",
		data : {
				
			TicketId : TicketId,
		},
		datatype : 'json',
		success : function(result) {
		/* var result1 = JSON.parse(result) */
		var result1 = $.parseJSON(result);
		
		$.each(result1, function(key, value) {
			
		    $('#modal-raisedby').html(value[2]);
		
			$('#modal-category').html(value[7]);
			
			$('#modal-subcategory').html(value[8]);
			
			 
			 document.getElementById('TicketId1').value= value[0];
			  assignTo= value[10];
			  console.log("assignTo--"+assignTo);
			  
			  priority=value[9];
			  console.log("pp--"+priority);
		
			  var d = new Date(value[6])
			 
			        month = '' + (d.getMonth() + 1),
			        day = '' + d.getDate(),
			        year = d.getFullYear();
			 
			    if (month.length < 2){ 
			        month = '0' + month;}
			    if (day.length < 2){ 
			        day = '0' + day;}
			    var demandDate=[day,month,year].join('-');
			   // console.log("res     :"+demandDate);	
			
									   	    
			$('#modal-raiseddate').html(demandDate);
			$('#modal-Desc').html(value[5]);
			
			
		}
		
		);
		
		 $('#my-ticket-modal').modal('toggle'); 

			$.ajax({
				type : "GET",
				url : "GetCaseWorkerListAjax.htm",
				data : {
					 
				},
				datatype : 'json',
				success : function(result) {
				var result = JSON.parse(result);
				console.log("caseworkerlist--   :"+result);
					var values = Object.keys(result).map(function(e) {
						return result[e];
					});
					$('#modal-assignto').html("");	
					var s = '';
					var val1= assignTo;
					for (i = 0; i < values.length; i++) 
					{
						console.log(val1);
						var EmpNo = values[i][0];
						console.log(EmpNo);
						
						if(EmpNo ===val1){
							s += '<option value="'+values[i][0]+'" selected="selected" >'+values[i][1] +  '</option>';
						}else{
							s += '<option value="'+values[i][0]+'">'+values[i][1]  + '</option>';
						}
					} 
					console.log(s);
					$('#modal-assignto').html(s);	
					
					
					var p='';
					 $('#modal-priority').html("");	
					 M="Medium"
                     H="High"
				     L="Low" 
					 if(priority=="M"){
						
						p +='<option value="L">'+L+'</option>';
						p +='<option value="M" selected="selected">'+M+'</option>';
						p +='<option value="H">'+H+'</option>';
					 }
					 if(priority=="L"){
						
						 p +='<option value="L" selected="selected">'+L+'</option>';
						 p +='<option value="M">'+M+'</option>';
						 p +='<option value="H">'+H+'</option>';
					 }
					 if(priority=="H"){
							
						   p +='<option value="L">'+L+'</option>';
						   p +='<option value="M">'+M+'</option>';
						   p +='<option value="H" selected="selected">'+H+'</option>';
						
						 
					 }
					 
					 
					 console.log(p);
					 $('#modal-priority').html(p);	
					
						/*  if(priority=="M"){
							M="Medium"
							$('#modal-priority').html(M);	
						}
						 if(priority=="H"){
							H="High"
							$("#modal-priority").html(H);
						}  */
					
				/* $('#modal-select').html(result1); */
			/* 	var $select = $('.modal-select');
				$select.find('option').remove(); */
				 //$('.modal-select').append("<option selected='selected' disabled='disabled'>Choose</option>");
				 /* $.each(result1, function(key, value) {
					 if(value[0]==assignTo){
						 $('.modal-select').append("<option selected='selected'>"+value[1]+"</option>");
					 }
					 $('.modal-select').append("<option value='"+value[0]+"'>"+value[1]+"</option>");
					 
				}) ; */
				
				}
			})
		}
	});
}


function descmodal(TicketId)
{
	
	$("#TicketId").val(TicketId);
	
	$.ajax({
		
		type : "GET",
		url : "GetTicketDataAjax.htm",
		data : {
			
			TicketId : TicketId,
			
		},
		datatype : 'json',
		success : function(result) {
			
			var result1 = $.parseJSON(result);
			
			$.each(result1, function(key, value) {
				
			
			console.log("res---"+value[5])
			$('#descdata').html(value[5]);
			$('#descmodal').modal('toggle');
		})
		}
	});
}


</script>


<!-- <script type="text/javascript"> 
$(document).ready(function(){
	  $("#myTable").DataTable({
	 "lengthMenu": [ 5, 10,25, 50, 75, 100 ],
	 "pagingType": "simple",
	 "pageLength": 5
	});
  });


</script>
 -->
</html>