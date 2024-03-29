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
	List<Object[]> PendingList=(List<Object[]>)request.getAttribute("TicketPendList");
	List<Object[]> CaseWorkerList=(List<Object[]>)request.getAttribute("CaseworkerList");
	
%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Ticket Pending List</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active " aria-current="page">Ticket Pending</li>
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
				   			<table class="table table-bordered table-hover table-striped table-condensed"   > 
								<thead>
									<tr>
										<th style="width: 1%">SN</th>
										<th style="width: 10%">Raised By</th>
										<th style="width: 10%">Category</th>
										<th style="width: 10%">Sub-Category</th>
										<th style="width: 25%">Description</th>
										<th style="width: 10%">Raised Date</th>
										<th style="width: 10%">Action</th>																		
									</tr>
								</thead>
								<tbody>
									
							    <%int count=1; %>
								<%for(Object[] pendinglist:PendingList) {
									String description=pendinglist[5].toString();
								
								if(pendinglist[10].toString().equalsIgnoreCase("I") || pendinglist[10].toString().equalsIgnoreCase("R"))
								{%>
								
								<tr>
									<td style="width: 2%; text-align: center;"><%=count %></td>
									<td style="width: 2%" ><%=pendinglist[2] %></td>
									<td style="width: 2%"><%=pendinglist[7] %></td>
									<td style="width: 2%"><%=pendinglist[8] %></td>
									<td style="width: 10%;word-wrap: break-word;word-break: break-all;white-space: normal !important;">
									<%if(description.length()<90){%> <%=description%> <%}else{%><%=description.substring(0,90)%>
								                 <button type="button" class="editable-click" style="border-style:none;" name="TicketId"  id="TicketId" value="<%=pendinglist[0]%>" onclick="descmodal('<%=pendinglist[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										         </button>
										         <%}%> 
									</td>
									<td style="width: 3%;text-align: center;"><%=rdf.format(sdf.parse(pendinglist[6].toString()))%></td>
									<td style="width: 2%;text-align: center;" >
									<%-- <%if(!pendinglist[9].toString().equals("")) {%>
									<button type="submit" class="btn btn-sm " name="TICKETID" value="<%=pendinglist[0]%>" formaction="TicketFormDownload.htm"  formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
										
										<%} %> --%>
										
										<%if(pendinglist[10].toString().equalsIgnoreCase("R")) {%>
										 <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=pendinglist[0]%>"  onclick="SeeDetails('<%=pendinglist[0]%>','<%=pendinglist[11]%>')"   data-toggle="tooltip" title="" data-original-title="Returned Details">
															<i class="fa fa-eye " style="color: black;"></i>
														</button>
														
										
										<%} %>
										<%if(pendinglist[10].toString().equalsIgnoreCase("I")){ %>
										<button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=pendinglist[0]%>"  onclick="openModal('<%=pendinglist[0]%>')"   data-toggle="tooltip" title="" data-original-title="Assign To">
															<i class="fas fa-angle-double-right " style="color: black;"></i>
														</button>
										<%} %>
										<input type="hidden" name="TicketId" value="">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										
									</td>
								</tr>
								 <%count++;%>
								<%}} %>
								<%if(PendingList.size()==0){ %>
							<tr><td  colspan="7" style="text-align: center;"> No Pending Forms </td></tr>
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
								<td id="Fileattach"><button type="submit" class="btn btn-sm " name="TICKETID" id="TicketId1" formaction="TicketFormDownload.htm"  formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
								</td> 
							</tr>
							
						</table>
						         <input type="hidden"  name="TICKETID" id="TicketId1" value="">
						        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
					</form>
						<hr>
						<form action="TicketAssign.htm" method="post" autocomplete="off" id="formsubmit" >
						
						<table   style="width:100%">
							
							<tr>
								<th style="width:23%;padding: 5px;">Assign To :&nbsp;<span class="mandatory" style="color: red;">*</span></th>
								<td style="width:34%;" >
										<select class="form-control select2 modal-select  "  name="AssignTo" id="assignto"  required="required" style="width:232px;" >
													<!-- <option selected="selected" disabled="disabled">Choose..</option> -->
										 </select>
							   </td>
							   <th style="width:16%;padding: 5px;">Priority :<span class="mandatory" style="color: red;">*</span></th>
							   <td   style="width:66%;" >
									<select class="form-control select2  "  name="Priority"  id="priority" required="required" style="width:152px;" >
													
														<option selected="selected" disabled="disabled">Choose..</option>
														<option value="L">Low</option>
														<option value="M">Medium</option>
														<option value="H">High</option>
									 </select>
							</tr>
							
						</table>
						<div align="center" style="margin-top:10px;" >
						
								<button type="submit"  class="btn btn-sm submit-btn"  name="action"  value="submit" onclick="return confirm('Are You Sure to Submit?');" >Submit</button>
		                </div>
		                       <input type="hidden"  name="TicketId1" id="TicketId2" value="">
						       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
						 
			     </form>
				</div>
				
			</div>
		</div>
	</div>
	
	<div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="returned-details">
		   <div class="modal-dialog modal-lg" role="document" style="width: 42% ;height: 60% ;">
			<div class="modal-content">
					<div class="modal-header" style="background-color: #0e6fb6;">
				    	<h3 class="modal-title" id="model-card-header" style="color: #FFFFFF;">Ticket Returned Details</h3>
				        <button type="button" class="close" style="color:#F9F9F9;" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div> 
				<div class="modal-body" align="center" style="margin-top:-4px;">
					<form action="#" method="post" autocomplete="off"  >
						<table style="width: 100%;">
							
							<tr>
								<th style="padding: 5px;" >Raised By :</th>
								<td style="padding: 5px;" id="raisedby"></td>
								<th style="padding: 5px;" >Raised Date :</th>
								<td style="padding: 5px;" id="raiseddate"></td>
							</tr>
							<tr>
								<th style="padding: 5px;width:17%;" >Category :</th>
								<td style="padding: 5px;" class="tabledata" id="category"></td>
								<th style="padding: 5px;width:26%;" >Sub-Category :</th>
								<td style="padding: 5px;" class="tabledata" id="subcategory"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Assigned By :</th>
								<td style="padding: 5px;" class="tabledata" id="assignedby"></td>
								<th style="padding: 5px" >Assigned Date :</th>
								<td style="padding: 5px;" class="tabledata" id="assigneddate"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" > Returned To :</th>
								<td style="padding: 5px;" class="tabledata" id="returnedto"></td>
								 <th style="padding: 5px;width:20%;" > Returned Date :</th>
								<td style="padding: 5px;" class="tabledata" > <span id="returneddate"></span> </td>
							</tr>
							<tr>
								<th style="padding: 5px;width:17%;" > Priority :</th>
								<td style="padding: 5px;" class="tabledata" id="Priority"></td>
							</tr>
							<tr><th><hr style="width:460%;"/></th></tr>
							<tr>
								<th style="width:17%;padding: 5px;"> Description :</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;" colspan="3" id="Desc"></td>
								 <td id="Filename"><button type="submit" class="btn btn-sm " name="TICKETID" id="TicketId5" formaction="TicketFormDownload.htm"  formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
								</td> 
							</tr>
							
							<tr >
							    <th style="padding: 5px;" > Assignee&nbsp;Remarks&nbsp;:</th>
								<td style="padding: 5px;width:18%;word-wrap:break-word;color:red" colspan="3" class="tabledata" id="CWremarks"></td>
						         <td style="margin-top:2px;" id="CEFileName"><button type="submit" class="btn btn-sm" name="AttachmentId" id="attachmentid" formaction="TicketForwardAttachmentDownload.htm" formmethod="post"  formnovalidate="formnovalidate" data-toggle="tooltip" data-placement="top" data-original-title="Download">
								<i style="color: #019267" class="fa-solid fa-download"></i>
									</button></td>
								</td>
							</tr>
							<tr >
							    <th style="padding: 5px;" > Assignor&nbsp;Remarks&nbsp;:</th>
								<td style="padding: 5px;width:18%;word-wrap:break-word;color:red;" colspan="3" class="tabledata" id="Aremarks"></td>
								
						    </tr>
						    
						    
							 
							 
						</table>
						 <input type="hidden"  name="TicketId1" id="TicketId5" value=""> 
						   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> 
						
					</form>
				</div>
				
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript">

function openModal(TicketId){
	
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
			
		    $('#modal-raisedby').html(value[2]);
		
			$('#modal-category').html(value[7]);
			
			$('#modal-subcategory').html(value[8]);
		
		
		    document.getElementById('TicketId1').value= value[0];
		    document.getElementById('TicketId2').value= value[0];
		   
		   if(value[9]==null || value[9]=="")
			   {
			   $('#Fileattach').hide();
			   }
		   else{
			   $('#Fileattach').show();
		   }
		   
		   
		   
		   if(name==""|| name===null || name=="null" )
			{
			   $('#CEREMARK').hide();
			}
		   else{
			   $('#CEREMARK').show();
			   
		   }
			  var d = new Date(value[6]),
			 
			        month = '' + (d.getMonth() + 1),
			        day = '' + d.getDate(),
			        year = d.getFullYear();
			 
			    if (month.length < 2){ 
			        month = '0' + month;}
			    if (day.length < 2){ 
			        day = '0' + day;}
			    var demandDate=[day,month,year].join('-');
			   $('#modal-raiseddate').html(demandDate);
			  $('#modal-Desc').html(value[5]);
		}
		
		);
		
		 $('#my-ticket-modal').modal('toggle'); 
		
    }
});
	
	$.ajax({

		type : "GET",
		url : "GetCaseWorkerListAjax.htm",
		data : {
			
		},
		datatype : 'json',
		success : function(result) {
		var result1 = JSON.parse(result);
		var $select = $('.modal-select');
		$select.find('option').remove();
		 $('.modal-select').append("<option selected='selected' disabled='disabled'>Choose</option>");
		 $.each(result1, function(key, value) {
			 
			 if(value[2]=="P"){
			    $('.modal-select').append("<option value='"+value[0]+"/"+value[1]+"'>"+value[3]+"&emsp;("+value[5]+")</option>");
			 }
			 if(value[2]=="C"){
				 
				 $('.modal-select').append("<option value='"+value[0]+"/"+value[1]+"'>"+value[3]+" [ "+value[2]+" ]" +"&emsp;("+value[5]+")</option>");
			 }
			 
				
		}) ;
		
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
				
			$('#descdata').html(value[5]);
			$('#descmodal').modal('toggle');
		})
		}
	});
}
</script>	  

<script type="text/javascript">

var L=null;
var M=null;
var H=null;

function SeeDetails(TicketId,AttachmentId){

$("#TicketId5").val(TicketId);
$("#attachmentid").val(AttachmentId);
	
	
	$.ajax({

		type : "GET",
		url : "GetReturnTicketDataAjax.htm",
		data : {
				
			TicketId : TicketId,
		},	
		
		datatype : 'json',
		success : function(result) {
		var result1 = JSON.parse(result);
		
		$.each(result1, function(key, value) {
			
			$('#raisedby').html(value[2]);
			$('#Desc').html(value[5]);
			$('#category').html(value[8]);
			$('#subcategory').html(value[9]);
			$('#assignedby').html(value[18]);
			$('#returnedto').html(value[17]);
			$('#Aremarks').html(value[16]);
			$('#CWremarks').html(value[21]);
			
			 var d = new Date(value[6]),
			 
		        month = '' + (d.getMonth() + 1),
		        day = '' + d.getDate(),
		        year = d.getFullYear();
		 
		    if (month.length < 2){ 
		        month = '0' + month;}
		    if (day.length < 2){ 
		        day = '0' + day;}
		    var demandDate=[day,month,year].join('-');
		   $('#raiseddate').html(demandDate);
		   
		   
		   var d1 = new Date(value[13]),
			 
	        month = '' + (d1.getMonth() + 1),
	        day = '' + d1.getDate(),
	        year = d1.getFullYear();
	 
	    if (month.length < 2){ 
	        month = '0' + month;}
	    if (day.length < 2){ 
	        day = '0' + day;}
	    var demandDate1=[day,month,year].join('-');
		   
	    $('#assigneddate').html(demandDate1);
	    
	    var d2 = new Date(value[15]),
		 
        month = '' + (d2.getMonth() + 1),
        day = '' + d2.getDate(),
        year = d2.getFullYear();
 
      if (month.length < 2){ 
        month = '0' + month;}
      if (day.length < 2){ 
        day = '0' + day;}
      var demandDate2=[day,month,year].join('-');
      $('#returneddate').html(demandDate2);  
    
			
		   if(value[7]=="L"){
				L="Low"
				$("#Priority").html(L).css('color','#0e6fb6');	
			} 
			 if(value[7]=="M"){
				M="Medium"
				$('#Priority').html(M).css('color','green');	
			}
			 if(value[7]=="H"){
				H="High"
				$("#Priority").html(H).css('color','red');
			} 
			 
			 if(value[10]==null || value[10]=="")
				 {
				 $('#Filename').hide();
				 }
			 else{
				 $('#Filename').show();
			 }
			
			 
			 if(value[20]==""|| value[20]===null || value[20]=="null" )
				{
				   $('#CEFileName').hide();
				}
			   else{
				   $('#CEFileName').show();
				   
			   }
			 
		}
		);
	     $('#returned-details').modal('toggle'); 
	
}
	});
}


$("#formsubmit").on('submit', function (e) {

	  var data =$('#assignto').val();
	  var data1 =$('#priority').val();
	  if(data==="" || data===null ){
		  alert("Please Select AssignTo!");
		  return false;
	  }else if(data1==="" || data1===null){
		  alert("Please Select Priority");
		  return false;
	  }else{
		  return true;
	  }  
}); 


</script>






</html>