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
	List<Object[]> ForwardedList=(List<Object[]>)request.getAttribute("TicketForwardedList");
	
%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Ticket Forwarded</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active " aria-current="page">Ticket Forwarded</li>
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
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="" > 
								<thead>
									<tr>
										<th style="width: 3%;">SN</th>
											<th style="width: 8%">Raised By</th>
											<th style="width: 8%">Category</th>
											<th style="width: 12%">Description</th>
											<th style="width: 7%;text-align: center;" >Raised Date</th>
											<th style="width: 10%;text-align: center;" >Forwarded By</th>
											<th style="width: 7%;text-align: center;" >Forwarded Date</th>
											<th style="width: 10%" >Action</th>																	
									</tr>
								</thead>
								<tbody>
									<% String Low="Low";
							String Medium="Medium";
							String High="High";
							%>
							<%int count=1; %>
								<%for(Object[] fwrdlist:ForwardedList) {
									String description=fwrdlist[5].toString();
								   if(fwrdlist[13].toString().equalsIgnoreCase("F") )	{%>
							    <tr>
									<td style="width: 3%; text-align: center;"><%=count %></td>
									<td style="width: 2%" ><%=fwrdlist[2] %></td>
									<td style="width: 2%"><%=fwrdlist[8] %></td>
									<td style="width: 5%;word-wrap: break-word;word-break: break-all;white-space: normal !important;">
									<%if(description.length()<30){%> <%=description%> <%}else{%><%=description.substring(0,30)%>
								                 <button type="button" class="editable-click" style="border-style:none;" name="TicketId"  id="TicketId" value="<%=fwrdlist[0]%>" onclick="descmodal('<%=fwrdlist[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										         </button>
										         <%}%> 
									</td>
									<td style="width: 2%;text-align: center;"><%=rdf.format(sdf.parse(fwrdlist[6].toString()))%></td>
									<td style="width: 2%"><%=fwrdlist[14] %></td>
									<td style="width: 2%;text-align: center;"><%=rdf.format(sdf.parse(fwrdlist[18].toString()))%></td>
									<td style="width: 2%;text-align: center;" >
									<%-- <%if(!fwrdlist[10].toString().equals("")) {%>
									<button type="submit" class="btn btn-sm " name="TICKETID" value="<%=fwrdlist[0]%>" formaction="TicketFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
										<%} %> --%>
										                <input type="hidden" name="RaisedDate<%=fwrdlist[0]%>"  id="RaisedDate<%=fwrdlist[0]%>"   value="<%=rdf.format(sdf.parse(fwrdlist[6].toString())) %>" >
														<input type="hidden" name="RaisedBy<%=fwrdlist[0]%>"  id="RaisedBy<%=fwrdlist[0]%>"   value="<%=fwrdlist[2] %>" >
														<input type="hidden" name="Description<%=fwrdlist[0]%>" id="Description<%=fwrdlist[0]%>" value="<%=fwrdlist[5] %>" >
														<input type="hidden" name="Category<%=fwrdlist[0]%>"  id="Category<%=fwrdlist[0]%>"   value="<%=fwrdlist[8] %>" >
														<input type="hidden" name="SubCategory<%=fwrdlist[0]%>"  id="SubCategory<%=fwrdlist[0]%>"   value="<%=fwrdlist[9] %>" >
														<input type="hidden" name="FileName<%=fwrdlist[0]%>"  id="FileName<%=fwrdlist[0]%>"   value="<%=fwrdlist[10] %>" >
														<input type="hidden" name="ForwardedBy<%=fwrdlist[0]%>"  id="ForwardedBy<%=fwrdlist[0]%>"   value="<%=fwrdlist[14] %>" >
														<input type="hidden" name="ForwardedDate<%=fwrdlist[0]%>"  id="ForwardedDate<%=fwrdlist[0]%>"   value="<%=rdf.format(sdf.parse(fwrdlist[18].toString()))%>" >
														<input type="hidden" name="Priority<%=fwrdlist[0]%>"  id="Priority<%=fwrdlist[0]%>"   value="<%=fwrdlist[7] %>" >
														<input type="hidden" name="Remarks<%=fwrdlist[0]%>"  id="Remarks<%=fwrdlist[0]%>"   value="<%=fwrdlist[17] %>" >
														<input type="hidden" name="ARemarks<%=fwrdlist[0]%>"  id="ARemarks<%=fwrdlist[0]%>"   value="<%=fwrdlist[20] %>" >
														<input type="hidden" name="AttachmentId<%=fwrdlist[0]%>"  id="AttachmentId<%=fwrdlist[0]%>"   value="<%=fwrdlist[19] %>" > 
														<input type="hidden" name="AttachmentName<%=fwrdlist[0]%>"  id="AttachmentName<%=fwrdlist[0]%>"   value="<%=fwrdlist[21] %>" > 
														<button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=fwrdlist[0]%>"  onclick="SeeDetails('<%=fwrdlist[0]%>','<%=fwrdlist[19] %>')"   data-toggle="tooltip" title="" data-original-title="Forwarded Details">
															<i class="fa fa-eye " style="color: black;"></i>
														</button>
										 <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=fwrdlist[0]%>/<%=fwrdlist[12] %>"   onclick="ReturnTicket('<%=fwrdlist[0] %>','<%=fwrdlist[12] %>')"  data-toggle="tooltip" title="" data-original-title="Return">
															<i class="fas fa-reply" style="color: black;"></i>
														</button>		
										 
										 <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=fwrdlist[0]%>/<%=fwrdlist[1] %>"  onclick="CloseTicket('<%=fwrdlist[0] %>','<%=fwrdlist[1] %>')"  data-toggle="tooltip" title="" data-original-title="Close">
															<i class="fas fa-close" style="color: red;"></i>
														</button> 
										 <input id="remarks<%=fwrdlist[0]%>" value="<%=fwrdlist[17]%>" type="hidden"> 
										 
										<input type="hidden" name="TicketId" value="">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										
									</td>
								</tr>
								 <%count++;%>
								 
								<%}} %>
								
								<%if(ForwardedList.size()==0){ %>
							<tr><td  colspan="10" style="text-align: center;"> No Forwarded Forms </td></tr>
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
		
		<!-- 
		 <div class="modal fade" id="SeeRemarks" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 53% !important;height: 45%;">
				<div class="modal-content" style="min-height: 45%;" >
				    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374"> RemarksBy <span id="feedby1"></span></h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				         
				        </button>
				    </div>
				   
				    <div class="modal-body"  style="padding: 0.5rem !important;">
						<div class="card-body" style="min-height:30% ;max-height: 93% !important;overflow-y: auto;">
							<span id="REMARKS"></span>
						</div>
					</div>
				</div>
			</div>
		</div>  -->
		
		
		<div class="modal fade" id="returnticket" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 53% !important;height: 45%;">
				<div class="modal-content" style="min-height: 45%;" >
				    <div class="modal-header" style="background-color:#0e6fb6;">
				    	<h3 class="modal-title" id="model-card-header" style="color: #FFFFFF">Return </h3>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color:#F9F9F9"  >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				     <div class="modal-body">
  	      
  	      	<form action="TicketReturn.htm" method="POST" id="Returnremarks">
  	      		<div class="row">
					<div class="col-md-12" > <b>Remarks : </b><br>
  	      		    		<textarea rows="2" style="display:block; " class="form-control"  id="Remarks" name="Remarks"  placeholder="Enter Remarks..!!"  required="required"></textarea>
  	      		    </div>
  	      		</div>
  	      		<br>
  	      		<div align="center">
  	      			<input type="submit" class="btn btn-md submit-btn "  value="Submit" name="action"  onclick="return confirm('Are You Sure To Submit ?')" > 
  	      		</div>
  	      		<input type="hidden" name="TicketId2" id="TicketId2" value="">
  	      		<input type="hidden" name="ReturnTo" id="ReturnTo" value="">
  	      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  	      	</form>
  	      </div>
				</div>
			</div>
		</div>
		
		 <div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="closeticket">
		   <div class="modal-dialog modal-lg" role="document" style="width: 31% ;height: 62% ;">
			<div class="modal-content">
				
					
				 <div class="modal-header" style="background-color:#0e6fb6;">
				    	<h3 class="modal-title" id="model-card-header" style="color: #FFFFFF">Ticket Close</h3>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color:#F9F9F9">
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				<div class="modal-body" align="center" style="margin-top:-3px;">
					<form action="TicketClose.htm" method="post" autocomplete="off"  >
						<table   style="width:100%">
							<tr>
								<th style="width:28%;padding: 5px;">FeedBack Required :<span class="mandatory" style="color: red;">*</span></th>
							    <td style="width:43%;" >
									<select class="form-control select2  "  name="Feedback"  required="required"  style="width:80px;" >
													   <option value="N">No</option>
														<option value="Y">Yes</option>
									</select>
							</tr>
						</table>
						<div align="center" style="margin-top:21px;" >
								<button type="submit"  class="btn btn-sm submit-btn"  name="action"  value="submit"  onclick="return confirm('Are You Sure To Close The Ticket ?')">Submit</button>
									
						</div>
						<input type="hidden"  name="TicketId3" id="TicketId3" value="">
						<input type="hidden"  name="EmpNo" id="EmpNo" value="">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
				</div>
			</div>
		</div>
	</div>
		
	   <div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="forwarded-details">
		   <div class="modal-dialog modal-lg" role="document" style="width: 42% ;height: 60% ;">
			<div class="modal-content">
					<div class="modal-header" style="background-color: #0e6fb6;">
				    	<h3 class="modal-title" id="model-card-header" style="color: #FFFFFF">Ticket Forwarded Details</h3>
				        <button type="button" class="close" style="color:#F9F9F9" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div> 
				<div class="modal-body" align="center" style="margin-top:-4px;">
					<form action="#" method="post" autocomplete="off"  >
						<table style="width: 100%;">
							
							<tr>
								<th style="padding: 5px;" >Raised By :</th>
								<td style="padding: 5px;" id="raisedby"></td>
								<th style="padding: 5px;width:20%;" >Raised Date :</th>
								<td style="padding: 5px;width:27%;" id="raiseddate"></td>
							</tr>
							<tr>
								<th style="padding: 5px;width:17%;" >Category :</th>
								<td style="padding: 5px;" class="tabledata" id="category"></td>
								<th style="padding: 5px;width:31%;" >Sub-Category :</th>
								<td style="padding: 5px;" class="tabledata" id="subcategory"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:20%;" >Forwarded By :</th>
								<td style="padding: 5px;" class="tabledata" id="forwardedby"></td>
								
								<th style="padding: 5px;width:30%;" >Forwarded Date :</th>
								<td style="padding: 5px;" class="tabledata"  id="forwardeddate"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" > Priority :</th>
								<td style="padding: 5px;" class="tabledata" id="priority"></td>
							</tr>
							<tr><th><hr style="width:500%;"/></th></tr>
							<tr>
								<th style="width:17%;padding: 5px;"> Description :</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;" colspan="3" id="Desc"></td>
								<td id="Fileattach"><button type="submit" class="btn btn-sm " name="TICKETID" id="TicketId1" formaction="TicketFormDownload.htm"  formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: #019267;"></i>
														</button>
								</td> 
							</tr>
							 <tr >
							    <th style="padding: 5px;" > Assignee&nbsp;Remarks&nbsp;:</th>
								<td style="padding: 5px;width:18%;word-wrap:break-word;color:red" colspan="3" class="tabledata" id="remarks"></td>
						         <td style="margin-top:2px;" id="CEREMARK"><button type="submit" class="btn btn-sm" name="AttachmentId" id="attachmentid" formaction="TicketForwardAttachmentDownload.htm" formmethod="post"  formnovalidate="formnovalidate" data-toggle="tooltip" data-placement="top" data-original-title="Download">
								<i style="color: #019267" class="fa-solid fa-download"></i>
									</button></td>
								</td>
							</tr>
							<tr id="AREMARK" >
							    <th style="padding: 5px;" >Assignor&nbsp;Remarks&nbsp;:</th>
								<td style="padding: 5px;width:20%;word-wrap:break-word;color:red;" colspan="3" class="tabledata" id="Aremarks">
								
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
<script type="text/javascript">

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

/* function SeeRemarks(TicketId)
{
	$('#SeeRemarks').modal('toggle');
	$("#REMARKS").html($("#remarks"+TicketId).val());
} */

function ReturnTicket(TicketId2,ReturnTo)
{
	$("#TicketId2").val(TicketId2);
	$("#ReturnTo").val(ReturnTo);
	$('#returnticket').modal('toggle');
}

function CloseTicket(TicketId3,EmpNo)
{
	$("#TicketId3").val(TicketId3);
	$("#EmpNo").val(EmpNo);
	$('#closeticket').modal('toggle');

}

$("#Returnremarks").on('submit', function (e) {

	  var data =$('#Remarks').val();;
	  if(data=='' ){
		  alert("Please Enter Remarks!");
		  return false;
	  }else if(data.length>999){
		  alert("Remarks data is too long!");
		  return false;
	  }else{
		  return true;
	  }  
}); 


function SeeDetails(TicketId,AttachmentId)
{
	$("#TicketId1").val(TicketId);
	$("#attachmentid").val(AttachmentId);
	$('#raisedby').html($('#RaisedBy'+TicketId).val())
	$('#raiseddate').html($('#RaisedDate'+TicketId).val())
	
	$('#Desc').html($('#Description'+TicketId).val())
	$('#category').html($('#Category'+TicketId).val())
	$('#subcategory').html($('#SubCategory'+TicketId).val())
	$('#forwardedby').html($('#ForwardedBy'+TicketId).val())
	$('#forwardeddate').html($('#ForwardedDate'+TicketId).val())
	 $('#remarks').html($('#Remarks'+TicketId).val())
	var type=$('#Priority'+TicketId).val()
	 if(type=="L")
    {
	   L="Low"
	   $('#priority').html(L).css('color','#0e6fb6');
	}
   if(type=="M")
   {
	   M="Medium"
	   $('#priority').html(M).css('color','green');
   }
   if(type=="H")
   {
	   H="High"
	   $('#priority').html(H).css('color','red');
   }
     
   var name=$('#AttachmentName'+TicketId).val()
      
      if(name==""|| name===null || name=="null" )
	{
	   $('#CEREMARK').hide();
	}
   else{
	   $('#CEREMARK').show();
	   
   }
      var filename=$('#FileName'+TicketId).val()
   
   if(filename=="" || filename===null)
	   {
	   $('#Fileattach').hide();
	   
	   }
   else{
	   $('#Fileattach').show();
   }
   
      
      var remarks=$('#ARemarks'+TicketId).val();
      
      if(remarks===null || remarks=="null")
      {
   	   $('#AREMARK').hide();
      }else{
   	   $('#AREMARK').show();
      		$('#Aremarks').html($('#ARemarks'+TicketId).val());
      }
      
     $('#forwarded-details').modal('toggle'); 
}


</script>
</html>