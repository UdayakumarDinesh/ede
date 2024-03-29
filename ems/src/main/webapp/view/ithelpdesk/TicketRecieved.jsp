<%@page import="org.apache.logging.log4j.core.pattern.EqualsIgnoreCaseReplacementConverter"%>
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
	List<Object[]> RecievedList=(List<Object[]>)request.getAttribute("TicketRecievedList");

%>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Ticket Recieved</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="ITDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active " aria-current="page">Ticket Recieved</li>
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
											<th style="width: 8%">Raised By</th>
											<th style="width: 5%">Category</th>
											<th style="width: 10%">Description</th>
									        <th style="width: 8%;text-align: center;" >Raised Date</th>
											<th style="width: 10%;text-align: center;" >Assigned By</th>
											<th style="width: 8%;text-align: center;" >Assigned Date</th>
											<th style="width: 8%;text-align: center;" >Returned Date</th>
										    <th style="width: 8%" >Action</th>																	
								</tr>
								</thead>
								<tbody>
									<% String Low="Low";
							String Medium="Medium";
							String High="High";
							%>
							<%int count=1; %>
								<%for(Object[] recvdlist:RecievedList) {
									String description=recvdlist[5].toString();
								
								if(recvdlist[15].toString().equalsIgnoreCase("A") || recvdlist[15].toString().equalsIgnoreCase("R"))
								{%>	
							  <tr>
									<td style="width: 3%; text-align: center;"><%=count %></td>
									<td style="width: 8%" ><%=recvdlist[2] %></td>
									<td style="width: 5%"><%=recvdlist[8] %></td>
									
									
									<td style="width: 10%;word-wrap: break-word;word-break: break-all;white-space: normal !important;">
									<%if(description.length()<30){%> <%=description%> <%}else{%><%=description.substring(0,30)%>
								                 <button type="button" class="editable-click" style="border-style:none;" name="TicketId"  id="TicketId" value="<%=recvdlist[0]%>" onclick="descmodal('<%=recvdlist[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										         </button>
										         <%}%> 
									</td>
									<td style="width: 8%;text-align: center;"><%=rdf.format(sdf.parse(recvdlist[6].toString()))%></td>
									<td style="width: 10%"><%=recvdlist[17] %></td>
									<td style="width: 8%;text-align: center;"><%=rdf.format(sdf.parse(recvdlist[11].toString()))%></td>
									<td style="width: 8%;text-align: center;"><%if(recvdlist[12]!=null){%><%=rdf.format(sdf.parse(recvdlist[12].toString()))%><%}else{%>-<%} %>
									</td>	
								    <td style="width: 8%;text-align: center;" >
									<%-- <%if(!recvdlist[10].toString().equals("")) {%>
									<button type="submit" class="btn btn-sm " name="TICKETID" value="<%=recvdlist[0]%>" formaction="TicketFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
									<%} %> --%>
										                <input type="hidden" name="RaisedDate<%=recvdlist[0]%>"  id="RaisedDate<%=recvdlist[0]%>"   value="<%=rdf.format(sdf.parse(recvdlist[6].toString())) %>" >
														<input type="hidden" name="RaisedBy<%=recvdlist[0]%>"  id="RaisedBy<%=recvdlist[0]%>"   value="<%=recvdlist[2] %>" >
														<input type="hidden" name="Description<%=recvdlist[0]%>" id="Description<%=recvdlist[0]%>" value="<%=recvdlist[5] %>" >
														<input type="hidden" name="Category<%=recvdlist[0]%>"  id="Category<%=recvdlist[0]%>"   value="<%=recvdlist[8] %>" >
														<input type="hidden" name="SubCategory<%=recvdlist[0]%>"  id="SubCategory<%=recvdlist[0]%>"   value="<%=recvdlist[9] %>" >
														<input type="hidden" name="FileName<%=recvdlist[0]%>"  id="FileName<%=recvdlist[0]%>"   value="<%=recvdlist[10] %>" >
														<input type="hidden" name="AssignedBy<%=recvdlist[0]%>"  id="AssignedBy<%=recvdlist[0]%>"   value="<%=recvdlist[17] %>" >
														<input type="hidden" name="AssignedDate<%=recvdlist[0]%>"  id="AssignedDate<%=recvdlist[0]%>"   value="<%=rdf.format(sdf.parse(recvdlist[11].toString())) %>" >
														<input type="hidden" name="ReturnedDate<%=recvdlist[0]%>"  id="ReturnedDate<%=recvdlist[0]%>"   value="<%if(recvdlist[12]!=null){%><%=rdf.format(sdf.parse(recvdlist[12].toString()))%><%}%>">
														<input type="hidden" name="Priority<%=recvdlist[0]%>"  id="Priority<%=recvdlist[0]%>"   value="<%=recvdlist[7] %>" >
														<input type="hidden" name="ARemarks<%=recvdlist[0]%>"  id="ARemarks<%=recvdlist[0]%>"   value="<%=recvdlist[13] %>" >
														<input type="hidden" name="CERemarks<%=recvdlist[0]%>"  id="CERemarks<%=recvdlist[0]%>"   value="<%=recvdlist[19] %>" >
														<input type="hidden" name="AttachmentName<%=recvdlist[0]%>"  id="AttachmentName<%=recvdlist[0]%>"   value="<%=recvdlist[21] %>" >
										 <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=recvdlist[0]%>"  onclick="SeeDetails('<%=recvdlist[0]%>','<%=recvdlist[20] %>')"   data-toggle="tooltip" title="" data-original-title="Assigned Details">
															<i class="fa fa-eye " style="color: black;"></i>
														</button>
										 <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=recvdlist[0]%>"  onclick="ForwardTicket('<%=recvdlist[0] %>')"  data-toggle="tooltip" title="" data-original-title="Forward">
															<i class="fas fa-angle-double-right" style="color: black;"></i>
														</button> 
														
																
										<input type="hidden" name="TicketId" value="">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										
									</td>
								</tr>
								 <%count++;%>
								<%}}%>
								
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
		
	<div class="modal fade" id="forwardticket" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 53% !important;height: 45%;">
				<div class="modal-content" style="min-height: 45%;" >
				    <div class="modal-header" style="background-color: #0e6fb6;">
				    	<h3 class="modal-title" id="model-card-header" style="color: #FFFFFF">Forward </h3>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: #F9F9F9">
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				     <div class="modal-body">
  	      
  	      	<form action="TicketForward.htm" method="POST"  id="Remarksadd" enctype="multipart/form-data">
  	      		<div class="row">
					<div class="col-md-12" > <b>Remarks : </b><span class="mandatory"	style="color: red;">*</span><br>
  	      		    		<textarea rows="2" style="display:block; " class="form-control"  id="Remarks" name="Remarks"  placeholder="Enter Remarks..!!"  required="required"></textarea>
  	      		    </div>
  	      		    
  	      		</div>
  	      		 <div class="col-2" style="margin-left:-12px;" >
									<b>File :</b> 
							<input type="file"  style="width: 230%;" class="form-control input-sm "  value="" id="formFile" name="FormFile" 
							accept=".xlsx,.xls,.pdf,.doc,.docx ">
				</div>
  	      		<br>
  	      		<div align="center">
  	      			<input type="submit" class="btn btn-md submit-btn "  value="Submit" name="action"  onclick="return confirm('Are You Sure To Submit ?')" > 
  	      		</div>
  	      		<input type="hidden" name="TicketId1" id="TicketId1">
  	      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  	      	</form>
  	      </div>
				</div>
			</div>
		</div>
	
	
	<div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="assigned-details">
		   <div class="modal-dialog modal-lg" role="document" style="width: 42% ;height: 60% ;">
			<div class="modal-content">
					<div class="modal-header" style="background-color:#0e6fb6 ">
				    	<h3 class="modal-title" id="model-card-header" style="color: #FFFFFF">Ticket Recieved Details</h3>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: #F9F9F9">
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
								<th style="padding: 5px;width:20%;" >Sub-Category :</th>
								<td style="padding: 5px;" class="tabledata" id="subcategory"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" >Assigned By :</th>
								<td style="padding: 5px;" class="tabledata" id="assignedby"></td>
								<th style="padding: 5px" >Assigned Date:</th>
								<td style="padding: 5px;" class="tabledata" id="assigneddate"></td>
							</tr>
							
							<tr>
								<th style="padding: 5px;width:17%;" > Priority :</th>
								<td style="padding: 5px;" class="tabledata" id="priority"></td>
								<th id="return" style="padding: 5px;width:23%;" >Returned Date:</th>
								<td style="padding: 5px;" class="tabledata" > <span id="returneddate"></span> </td>
							</tr>
						<tr><th><hr style="width:600%;"/></th></tr>
							<tr>
								<th style="width:17%;padding: 5px;"> Description :</th>
								<td class="tabledata" style="width:90%;padding: 5px;word-wrap:break-word;" colspan="3" id="Desc"></td>
								 <td id="Fileattach"><button type="submit" class="btn btn-sm " name="TICKETID" id="TicketId2" formaction="TicketFormDownload.htm"  formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
								</button>
							</tr>
							
							<tr id="CEREMARK">
							    <th  style="padding: 5px;" > Assignee&nbsp;Remarks&nbsp;:</th><br>
							    <td style="padding: 5px;width:18%;word-wrap:break-word;color:red" colspan="3" class="tabledata" id="CEremarks"></td>
								<td id="CEAttach"><button type="submit" class="btn btn-sm" name="AttachmentId" id="attachmentid" formaction="TicketForwardAttachmentDownload.htm" formmethod="post"  formnovalidate="formnovalidate" data-toggle="tooltip" data-placement="top" data-original-title="Download">
								<i style="color: #019267" class="fa-solid fa-download"></i>
									</button></td>
							</tr>
							<tr id="REMARK" >
							    <th style="padding: 5px;" > Assignor&nbsp;Remarks&nbsp;:</th>
								<td style="padding: 5px;width:18%;word-wrap:break-word;color:red" colspan="3" class="tabledata" id="Aremarks"></td>
						         
							</tr>
						</table>
						
						  <input type="hidden"  name="TicketId1" id="TicketId2" value=""> 
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
				
			$('#descdata').html(value[5]);
			$('#descmodal').modal('toggle');
		})
		}
	});
}

function ForwardTicket(TicketId1)
{
	$("#TicketId1").val(TicketId1);
	$('#forwardticket').modal('toggle');
}
/* function SeeRemarks(TicketId)
{
	$('#SeeRemarks').modal('toggle');
	$("#REMARKS").html($("#remarks"+TicketId).val());
} */

$("#Remarksadd").on('submit', function (e) {

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

function SeeDetails(TicketId2,AttachmentId)
{
	
	$("#TicketId2").val(TicketId2);
	$("#attachmentid").val(AttachmentId);
	$('#raisedby').html($('#RaisedBy'+TicketId2).val())
	$('#raiseddate').html($('#RaisedDate'+TicketId2).val())
	$('#Desc').html($('#Description'+TicketId2).val())
	$('#category').html($('#Category'+TicketId2).val())
	$('#subcategory').html($('#SubCategory'+TicketId2).val())
	$('#assignedby').html($('#AssignedBy'+TicketId2).val())
	$('#assigneddate').html($('#AssignedDate'+TicketId2).val())
	
	var returndate=$('#ReturnedDate'+TicketId2).val();
   
		   if(returndate=="")
		   {
			 $('#return').hide();
			 $('#returneddate').hide();
		    } 
		   else{
			   $('#return').show();
			   $('#returneddate').show();
			   $('#returneddate').html($('#ReturnedDate'+TicketId2).val())
		   }
	 
	var type=$('#Priority'+TicketId2).val()
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
    var remarks=$('#ARemarks'+TicketId2).val();
  
   if(remarks===null || remarks=="null")
   {
	   $('#REMARK').hide();
   }else{
	   $('#REMARK').show();
   		$('#Aremarks').html($('#ARemarks'+TicketId2).val());
   }
   var name=$('#AttachmentName'+TicketId2).val();
   
   if(name=="" || name===null || name=="null")
	{
	   $('#CEAttach').hide();
	}
   else{
	   $('#CEAttach').show();
   }
   
var filename=$('#FileName'+TicketId2).val()
   
   if(filename=="" || filename===null)
	   {
	   $('#Fileattach').hide();
	   
	   }
   else{
	   $('#Fileattach').show();
   }
   var CERemark=$('#CERemarks'+TicketId2).val();
   if(CERemark=="" || CERemark===null || CERemark=="null") {
	   $('#CEREMARK').hide();
   }
   else{
	   $('#CEREMARK').show();
      $('#CEremarks').html($('#CERemarks'+TicketId2).val());
   }
   
   $('#assigned-details').modal('toggle'); 
}


$(function(){
    $("#formFile").on('change', function(event)
	{
    	
    	var file = $("#formFile").val();
    	console.log(file);
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
</script>

</html>