<%@page import="java.util.List"%>
<%@ page import="java.util.*, java.text.DateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.text.SimpleDateFormat"%> 
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

	<%  String fromdate =(String)request.getAttribute("frmDt");		
		  String todate = (String)request.getAttribute("toDt");
	      List<Object[]> HelpDeskUserList=(List<Object[]>)request.getAttribute("HelpDeskUserList");
	      SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	      SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	      List<Object[]> CategoryList=(List<Object[]>)request.getAttribute("CategoryList");
	      String EmpNo=(String)request.getAttribute("EmpNo");
	
	%>
	<div></div>
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>New Ticket</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
							<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active" aria-current="page">Ticket
						List</li>
				</ol>
			</div>

		</div>
		<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
		<div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
		</div>
		<%}if(ses!=null){ %>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
		<%} %>
		
		
		<div class="card" >
				<div class="card-body main-card " >
					<form action="ITHelpDeskAddSubmit.htm" method="post" autocomplete="off" id="ticketsubmit" enctype="multipart/form-data" >
					
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="row">
							<div class="col-3">
									<b>Category :</b><span class="mandatory" style="color: red;">*</span>
							        <select class="form-control select2"  name="Category" required="required" id="Category">
							        <option value="" disabled="disabled" selected="selected"
					                     hidden="true">Select</option>
								
								    <%for(Object[] category :CategoryList) {%>
								    
								    <option value="<%=category[0]%>"><%=category[1] %></option>
								    <%} %>
							</select>
							   </div>
								<div class="col-2">
									<b>Sub-Category :</b><span class="mandatory"	style="color: red;">*</span>
							<select class="form-control select2 " name="SubCategory" required="required"  id="SubCategory">
								  <option value="" disabled="disabled" selected="selected"
					                     hidden="true">Select</option>
							</select>
								</div>
								
								<div class="col-3" style="margin-left:-2px;" >
								<b>Description : </b><span class="mandatory"	style="color: red;">*</span>
								<input type="text" class="form-control w-145" name="Description" value=""   id="Desc" style="width:145%;">
							</div>
							
							   <div class="col-2" style="margin-left:116px;" >
									<b>File :</b> 
							<input type="file"  style="width: 150%;" class="form-control input-sm "  value="" id="formFile" name="FormFile" 
							accept=".xlsx,.xls,.pdf,.doc,.docx ">
							
							  </div>
						</div>
						<br>
						<div class="row">
						</div>
						
						<div class="row justify-content-center">
							<button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Submit ?')" ><!-- onclick="return CheckAll();"  --> SUBMIT</button>  
						</div>
					</form>
				</div>
			</div>	
			
			
			<div class="card">
			<div class="card-header" style="height: 4rem">
				<form action="ITTicketList.htm" method="POST" id="myform">
				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	              
					<div class="row justify-content-right">
						<div class="col-7">
						<div class="col-md-4 d-flex justify-content-center"  >
			<h5 style="color: #005C97;font-weight: 700; margin-left:-24%;margin-top:2%;">Ticket&nbsp;Raised&nbsp;List </h5>
		</div>
						
						</div>
						
						<div class="col-2" style="margin-left: 7%; font-color: black;">
							<h6 style="color:#000000;" >From Date : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -9%">
							<input type="text"
								style="width: 147%; background-color: white; text-align: left;"
								class="form-control input-sm"
								 <%if(fromdate!=null){%>
								value="<%=fromdate%>" <%} %> onchange="this.form.submit()" 
							readonly="readonly"
								 id="fromdate" name="FromDate"
								required="required"> 
								
						</div>


						<div class="col-2" style="margin-left: 1%">
							<h6 style="color:#000000;">To Date : &nbsp;</h6>
						</div>
						<div class="col-1" style="margin-left: -11%">
							<input type="text" style="width: 147%; background-color: white;"
								class="form-control input-sm mydate"
								
								  <%if(todate!=null){ %>  value="<%=todate%>"<%}%> 
								  onchange="this.form.submit()"
								readonly="readonly"
								  id="todate" name="ToDate"> 
								
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>

		
		<div class="card-body main-card" style="max-height: 42rem; overflow-y:auto;" >

			<form action="#" method="POST" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="table-responsive"      >
					<table
						class="table table-hover  table-striped table-condensed table-bordered table-fixed"
						id="myTable" style="width:100% ;" >
						<thead>
							<tr>
								<th style="width: 4%">SN&nbsp;</th>
								<th style="width: 7%">Category</th>
								<th style="width: 10%">Sub-Category</th>
								<th style="width: 20%">Description</th>
								<th style="width: 8%">Raised Date</th>
								<th style="width: 6%">Status</th>
								<th style="width: 10%">Action</th>
						   </tr>
						</thead>
						<tbody>
							<%int count=0;
							for(Object[] UserList:HelpDeskUserList){
							  String description=UserList[4].toString();	%>
						
							<tr>
							<% 
							String Initiated="Initiated";
							String Assigned="Assigned";
							String Forwarded="Forwarded";
							String Returned="Returned";
							String Closed="Closed";
							%>
							
								<td style="text-align: center;"><%=++count %></td>
								<td style="text-align: center;"><%=UserList[7]%></td>
								<td style="text-align: center;"><%=UserList[8]%></td>
								<td style="text-align: left;word-wrap: break-word;word-break: break-all;white-space: normal !important;"><%if(description.length()<90){%> <%=description%> <%}else{%><%=description.substring(0,90)%>
								<button type="button" class="editable-click" style="border-style:none;" name="TicketId"  id="TicketId" value="<%=UserList[0]%>" onclick="descmodal('<%=UserList[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										</button><%}%> 
								
								</td>
								<td style="text-align: center;"><%=rdf.format(sdf.parse(UserList[5].toString()))%></td>
								<td style="text-align: center;"><% if(UserList[6].toString().equalsIgnoreCase("I")){%><%=Initiated%><%} else if(UserList[6].toString().equalsIgnoreCase("A")){%><%=Assigned %><%} else if(UserList[6].toString().equalsIgnoreCase("F")){%><%=Forwarded%><%} else if(UserList[6].toString().equalsIgnoreCase("R")){%><%=Returned%><%} else{%><%=Closed%><%} %></td>
								<td style="text-align: center;padding-top:5px; padding-bottom: 5px;">
								 <span id="dbutton"><%if(! UserList[9].toString().equalsIgnoreCase("")){%>
												<button type="submit" class="btn btn-sm" name="TICKETID" id="TicketId" value="<%=UserList[0]%>" formaction="TicketFormDownload.htm"   formmethod="post" data-toggle="tooltip" data-placement="top" data-original-title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
											</button>
										<%} %> </span>
										&nbsp;
								<%if( UserList[6].toString().equalsIgnoreCase("I")) {%>
								<button type="submit" class="btn btn-sm " name="TicketId" value="<%=UserList[0]%>"  formaction="TicketEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" data-original-title="Edit">
								<i class="fa fa-edit" style="color: #333C83"></i>
													</button>
												<input type="hidden" name="CategoryId1" value="<%=UserList[2]%>">
												&nbsp;
												<button type="submit" class="btn btn-sm " name="TicketId" value="<%=UserList[0]%>" formaction="TicketDelete.htm" onclick="return confirm('Are You Sure to Revoke This Ticket?');"  formmethod="post" data-toggle="tooltip" data-placement="top" data-original-title="Revoke">
													<i class="fa-solid fa-backward" style="color: red"></i>
												</button>
												<%} %>
												<%if( UserList[10].toString().equalsIgnoreCase("Y") && UserList[11]==null){%>
												<button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=UserList[0]%>/<%=UserList[12] %>"  onclick="openfeedback('<%=UserList[0] %>','<%=UserList[12] %>')"  data-toggle="tooltip" title="" data-original-title="Feedback">
															<i class="fa fa-commenting" style="color: black;"></i>
														</button> 
														<%} %>
												<%if(UserList[6].toString().equalsIgnoreCase("C")){ %>		
												<button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=UserList[0]%>"  onclick="openModal('<%=UserList[0] %>','<%=UserList[13] %>')"  data-toggle="tooltip" title="" data-original-title="View Details">
															<i class="fa fa-eye " style="color: black;"></i>
														</button> 
														<%} %>		
												
								</td>
							</tr>
							 
							<%} %>
						</tbody>
					</table>
                       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</div>
          </form>
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
		
		
		<div class="modal fade" id="feedback" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="width: 40% ;height: 45%;">
				<div class="modal-content" style="min-height: 30%;" >
				    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">FeedBack  <!-- By <span id="feedby1"></span> --></h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				     <div class="modal-body">
  	      
  	      	<form action="TicketClosedFeedback.htm" id="Feedbackadd" method="POST">
  	      		<div class="row">
					<div class="col-md-12" style="margin-left:7px;"  >
  	      		    		<p style="font-size:48px">
                             &#128512;&nbsp;&nbsp;&nbsp;&#128522;&nbsp;&nbsp;&nbsp;&#128578;&nbsp;&nbsp;&nbsp;&#128528;&nbsp;&nbsp;&nbsp;&#128524;
                              </p>
                              <input type="radio" id="feedbackselect1"  name="FeedbackType" value="Excellent">
  	      		    		   <label for="feedbackselect1">Excellent</label>&emsp;  
  	      		    		   <input type="radio"  id="feedbackselect2" name="FeedbackType" value="Very Good">
  	      		    		   <label for="feedbackselect2">Very Good</label>&emsp;
  	      		    		  <input type="radio"  id="feedbackselect3" name="FeedbackType" value="Good"  checked="checked">
  	      		    		   <label for="feedbackselect3">Good</label>&emsp;&emsp;
  	      		    		    <input type="radio" id="feedbackselect4" name="FeedbackType" value="Average">
  	      		    		   <label for="feedbackselect4">Average</label>&emsp;&emsp;
  	      		    		    <input type="radio" id="feedbackselect5" name="FeedbackType" value="Poor">
  	      		    		   <label for="feedbackselect5">Poor</label>
  	      		    		  
  	      		    	      <textarea rows="2" style="display:block;" class="form-control"  id="feedbacktext" name="Feedback"  placeholder=" FeedBack is Mandatory!!" ></textarea>
  	      		    	
  	      		    </div>
  	      		</div>
  	      		<br>
  	      		<div align="center">
  	      			<input type="submit" class="btn btn-md submit-btn "  value="Submit" name="action"  onclick="return confirm('Are You Sure To Submit ?')" > 
  	      		</div>
  	      		<input type="hidden" name="TicketId2" id="TicketId2">
  	      		<input type="hidden" name="EmpNo" id="EmpNo">
  	      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  	      	</form>
  	      </div>
				</div>
			</div>
		</div>
		
	<div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="my-ticket-modal">
		   <div class="modal-dialog modal-lg" role="document" style="width: 42% ;height: 60% ;">
			<div class="modal-content">
					<div class="modal-header" style="background-color:#0e6fb6;">
				    	<h3 class="modal-title" id="model-card-header" style="color:#FFFFFF;">Ticket Closed Details</h3>
				        <button type="button" class="close" style="color: #F9F9F9" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div> 
				<div class="modal-body" align="center" style="margin-top:-4px;background-color:#EEEEEE">
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
								<th style="padding: 5px;width:23%;" >Sub-Category :</th>
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
							
					 <tr><th><hr style="width:450%;"/></th></tr>
							<tr>
								<th style="width:17%;padding: 5px;"> Description :</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;" colspan="3" id="modal-Desc"></td>
								
							</tr>
							
							<tr id="remark">
								<th style="width:17%;padding: 5px;"> Assignor&nbsp;Remarks&nbsp;:</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;color:#FF0000;" colspan="3" id="modal-ARemark"></td>
							</tr>
							<tr>
								<th style="width:17%;padding: 5px;"> Assignee&nbsp;Remarks&nbsp;:</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;color:#FF0000;" colspan="3" id="modal-CWRemarks"></td>
								<td id="CEREMARK"><button type="submit" class="btn btn-sm" name="AttachmentId" id="attachmentid" formaction="TicketForwardAttachmentDownload.htm" formmethod="post"  formnovalidate="formnovalidate" data-toggle="tooltip" data-placement="top" data-original-title="Download">
								<i style="color: #019267" class="fa-solid fa-download"></i>
									</button></td>
								</td>
							</tr>
							
							<tr id="feed">
								<th style="width:17%;padding: 5px;"> Feedback :</th>
								<!--  <td style="color:blue;width:32%;"  id="modal-feedbacktype" ></td>  -->
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;color:#0002A1;" colspan="3" id="modal-feedback"></td>
							</tr>
							
						</table>
						
					         <input type="hidden"  name="TicketId1" id="TicketId1" value=""> 
						     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
				</div>
				
			</div>
		</div>
	</div>
		
		

</body>
<script>


$("#ticketsubmit").on('submit', function (e) {

	  var data =$('#Desc').val();
	  if(data.trim()==='' ){
		  alert("Please Enter Description!");
		  return false;
	  }else if(data.length>999){
		  alert("Description data is too long!");
		  return false;
	  }else{
		  return true;
	  }  
});  

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


	$(function(){
	    $("#formFile").on('change', function(event)
		{
	    	
	    	var file = $("#formFile").val();
	    	
	       	var upld = file.split('.').pop(); 
	       	if(!(upld.toLowerCase().trim()==='pdf' || upld.toLowerCase().trim()==='xlsx' 
	       				|| upld.toLowerCase().trim()==='xls' || upld.toLowerCase().trim()==='doc' || upld.toLowerCase().trim()==='docx')  )
	       	{
	    	    alert("Only PDF,Word and Excel documents are allowed to Upload")
	    	    document.getElementById("formFile").value = "";
	    	    return;
	    	}
	        
	    });
	});
	
	$(document).ready(function() {
	    $('#Category').on('change', function() {
	      var selectedValue = $(this).val();
	       $.ajax({
	        type: "GET",
	        url: "GetSubCategoryListAjax.htm",
	        data: { 
	        	
	        	selectedValue:selectedValue,
	        },
	        success: function(result) {
	        
	        var result1 = JSON.parse(result);
	        var values = Object.keys(result1).map(function(e) {
				return result1[e];
			});
	      
	        var s = '';
			for (i = 0; i < values.length; i++) 
			{
			    {
					s += '<option value="'+values[i][0]+'">'+values[i][2]  + '</option>';
				} 
			} 
			$('#SubCategory').html(s);	
	        
	        }
	      });
	    });
	  });
	
	
	function openfeedback(TicketId2,EmpNo)
	{
		$("#TicketId2").val(TicketId2);
		$("#EmpNo").val(EmpNo);
		$('#feedback').modal('toggle');
	}
	
	  $("#Feedbackadd").on('submit', function (e) {
           if(document.getElementById('feedbackselect4').checked || document.getElementById('feedbackselect5').checked)
           {
			  var text=$('#feedbacktext').val();
			  if(text==='' ){
				  alert("Please Enter FeedBack!");
				  return false;
		        }
		    }  
		 else if(text.length>999){
			  alert("Feedback data is too long!");
			  return false;
	  	  }else{
			  return true;
		  }  
});  
	
 	var L=null;
	var M=null;
	var H=null;
	function openModal(TicketId,AttachmentId){
		
	$("#TicketId").val(TicketId);
	$("#attachmentid").val(AttachmentId);
		
		
		$.ajax({

			type : "GET",
			url : "GetTicketClosedDataAjax.htm",
			data : {
					
				TicketId : TicketId,
			},
			datatype : 'json',
			success : function(result) {
			var result1 = $.parseJSON(result);
			$.each(result1, function(key, value) {
				
			    $('#modal-raisedby').html(value[2]);
			    $('#modal-category').html(value[8]);
				$('#modal-subcategory').html(value[9]);
				$('#modal-assignedby').html(value[17]);
				$('#modal-closedby').html(value[18]);
				$('#modal-assignedto').html(value[16]);
				//$('#modal-feedbacktype').html(value[23]);
				
				 if(value[20]===null){
					$("#feed").hide();
				}
				else{
					$("#feed").show();
					$('#modal-feedback').html("["+value[23]+"] "+value[20]);
				} 
				$('#modal-CWRemarks').html(value[21]);
				
				
				 if(value[24]===null ||value[24]=="" )
					{
					   $('#CEREMARK').hide();
					}
				   else{
					   $('#CEREMARK').show();
					}
				
				if(value[22]===null){
					$("#remark").hide();
				}
				else{
					$("#remark").show();
					$('#modal-ARemark').html(value[22]);
				}
				
				
				if(value[7]=="L"){
					L="Low"
					$("#modal-priority").html(L).css('color','#0e6fb6');	
				} 
				 if(value[7]=="M"){
					M="Medium"
					$('#modal-priority').html(M).css('color','green');	
				}
				 if(value[7]=="H"){
					H="High"
					$("#modal-priority").html(H).css('color','red');
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
	 
</script>

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
</html>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     