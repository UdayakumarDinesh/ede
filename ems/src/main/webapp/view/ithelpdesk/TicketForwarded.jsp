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
										<th style="width: 5%;">SN</th>
											<th style="width: 8%">Raised By</th>
											<th style="width: 8%">Category</th>
											<th style="width: 8%">Sub-Category</th>
											<th style="width: 12%">Description</th>
											<th style="width: 8%">Priority</th>
											<th style="width: 9%;text-align: center;" >Raised Date</th>
											<th style="width: 10%;text-align: center;" >Forwarded By</th>
											<th style="width: 9%;text-align: center;" >Forwarded Date</th>
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
								
									 
									 
								if(fwrdlist[13].toString().equalsIgnoreCase("F") )
								{%>
								
								<tr>
									<td style="width: 2%; text-align: center;"><%=count %></td>
									<td style="width: 2%" ><%=fwrdlist[2] %></td>
									<td style="width: 2%"><%=fwrdlist[8] %></td>
									<td style="width: 2%"><%=fwrdlist[9] %></td>
									<td style="width: 5%;word-wrap: break-word;word-break: break-all;white-space: normal !important;">
									<%if(description.length()<90){%> <%=description%> <%}else{%><%=description.substring(0,90)%>
								                 <button type="button" class="editable-click" style="border-style:none;" name="TicketId"  id="TicketId" value="<%=fwrdlist[0]%>" onclick="descmodal('<%=fwrdlist[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										         </button>
										         <%}%> 
									</td>
									<td style="width: 2%"><% if(fwrdlist[7].toString().equalsIgnoreCase("L")){%><%=Low %><%} else if(fwrdlist[7].toString().equalsIgnoreCase("M")){%><%=Medium %><%}else{%><%=High%><%} %></td>
									<td style="width: 3%;text-align: center;"><%=rdf.format(sdf.parse(fwrdlist[6].toString()))%></td>
									
									<td style="width: 2%"><%=fwrdlist[14] %></td>
									<td style="width: 2%"><%=rdf.format(sdf.parse(fwrdlist[18].toString()))%></td>
									<td style="width: 2%;text-align: center;" >
									<%if(!fwrdlist[10].toString().equals("")) {%>
									<button type="submit" class="btn btn-sm " name="TicketId" value="<%=fwrdlist[0]%>" formaction="TicketFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
										
										<%} %>
										 <%-- <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=fwrdlist[0]%>"  onclick="ForwardTicket('<%=fwrdlist[0] %>')"  data-toggle="tooltip" title="" data-original-title="Forward">
															<i class="fas fa-angle-double-right" style="color: black;"></i>
														</button> 
										 --%> <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=fwrdlist[0]%>"  onclick="SeeRemarks('<%=fwrdlist[0] %>')"  data-toggle="tooltip" title="" data-original-title="Remarks">
															<i class="fas fa-envelope" style="color: black;"></i>
														</button> 
														
												<button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=fwrdlist[0]%>"   onclick="ReturnTicket('<%=fwrdlist[0] %>')"  data-toggle="tooltip" title="" data-original-title="Return">
															<i class="fas fa-reply" style="color: black;"></i>
														</button>		
										 
										 <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=fwrdlist[0]%>"  onclick="CloseTicket('<%=fwrdlist[0] %>')"  data-toggle="tooltip" title="" data-original-title="Close">
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
		
		
		<div class="modal fade" id="SeeRemarks" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 53% !important;height: 45%;">
				<div class="modal-content" style="min-height: 45%;" >
				    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374"> Remarks<!-- By <span id="feedby1"></span> --></h4>
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
		</div>
		
		
		<div class="modal fade" id="returnticket" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 53% !important;height: 45%;">
				<div class="modal-content" style="min-height: 45%;" >
				    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Return  <!-- By <span id="feedby1"></span> --></h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
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
  	      		<input type="hidden" name="TicketId2" id="TicketId2">
  	      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  	      	</form>
  	      </div>
				</div>
			</div>
		</div>
		
		
		
		
		
		 <div class="modal bd-example-modal-lg" tabindex="-1" role="dialog" id="closeticket">
		   <div class="modal-dialog modal-lg" role="document" style="width: 31% ;height: 62% ;">
			<div class="modal-content">
				
					
				 <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Ticket Close  <!-- By <span id="feedby1"></span> --></h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				<div class="modal-body" align="center" style="margin-top:-3px;">
					<form action="TicketClose.htm" method="post" autocomplete="off"  >
						
						<table   style="width:100%">
							
							<tr>
								
							   <th style="width:16%;padding: 5px;">FeedBack Required :<span class="mandatory" style="color: red;">*</span></th>
							   <td style="width:43%;" >
									<select class="form-control select2  "  name="Feedback"  required="required"  style="width:152px;" >
													
														
														<option value="N">No</option>
														<option value="Y">Yes</option>
														
									 </select>
							</tr>
							
						</table>
						<div align="center" style="margin-top:21px;" >
								<button type="submit"  class="btn btn-sm submit-btn"  name="action"  value="submit"  onclick="return confirm('Are You Sure To Close The Ticket ?')">Submit</button>
									
						</div>
						<input type="hidden"  name="TicketId3" id="TicketId3" value="">
						
						
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

function SeeRemarks(TicketId)
{
	$('#SeeRemarks').modal('toggle');
	$("#REMARKS").html($("#remarks"+TicketId).val());
}

function ReturnTicket(TicketId2)
{
	$("#TicketId2").val(TicketId2);
	$('#returnticket').modal('toggle');
}

function CloseTicket(TicketId3)

{
	$("#TicketId3").val(TicketId3);
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


</script>
</html>