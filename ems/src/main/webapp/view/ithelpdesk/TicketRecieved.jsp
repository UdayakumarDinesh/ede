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
	String EmpId=(String)request.getAttribute("EmpId");

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
										<th style="width: 5%;">SN</th>
											<th style="width: 5%">Raised By</th>
											<th style="width: 5%">Category</th>
											<th style="width: 5%">Sub-Category</th>
											<th style="width: 10%">Description</th>
											<th style="width: 8%">Priority</th>
											<th style="width: 9%;text-align: center;" >Raised Date</th>
											<th style="width: 10%;text-align: center;" >Assigned By</th>
											<th style="width: 9%;text-align: center;" >Assigned Date</th>
											<th style="width: 9%;text-align: center;" >Returned Date</th>
											
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
									<td style="width: 5%; text-align: center;"><%=count %></td>
									<td style="width: 5%" ><%=recvdlist[2] %></td>
									<td style="width: 5%"><%=recvdlist[8] %></td>
									<td style="width: 5%"><%=recvdlist[9] %></td>
									
									<td style="width: 10%;word-wrap: break-word;word-break: break-all;white-space: normal !important;">
									<%if(description.length()<90){%> <%=description%> <%}else{%><%=description.substring(0,90)%>
								                 <button type="button" class="editable-click" style="border-style:none;" name="TicketId"  id="TicketId" value="<%=recvdlist[0]%>" onclick="descmodal('<%=recvdlist[0]%>')">
													<b><span style="color:#1176ab;font-size: 14px;">......(View More)</span></b>
										         </button>
										         <%}%> 
									</td>
									<td style="width: 8%"><% if(recvdlist[7].toString().equalsIgnoreCase("L")){%><%=Low %><%} else if(recvdlist[7].toString().equalsIgnoreCase("M")){%><%=Medium %><%}else{%><%=High%><%} %></td>
									<td style="width: 9%;text-align: center;"><%=rdf.format(sdf.parse(recvdlist[6].toString()))%></td>
									<td style="width: 10%"><%=recvdlist[17] %></td>
									<td style="width: 9%;text-align: center;"><%=rdf.format(sdf.parse(recvdlist[11].toString()))%></td>
									<td style="width: 9%;text-align: center;"><%if(recvdlist[12]!=null){%><%=rdf.format(sdf.parse(recvdlist[12].toString()))%><%}else{%>-<%} %></td>	
								
									<td style="width: 8%;text-align: center;" >
									<%if(!recvdlist[10].toString().equals("")) {%>
									<button type="submit" class="btn btn-sm " name="TicketId" value="<%=recvdlist[0]%>" formaction="TicketFormDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip"  formnovalidate="formnovalidate" data-placement="top" title="" data-original-title="Download">
															<i class="fa-solid fa-download " style="color: green;"></i>
														</button>
										
										<%} %>
										 <button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=recvdlist[0]%>"  onclick="ForwardTicket('<%=recvdlist[0] %>')"  data-toggle="tooltip" title="" data-original-title="Forward">
															<i class="fas fa-angle-double-right" style="color: black;"></i>
														</button> 
														<%if(recvdlist[13]!=null){ %>
											<button type="button" class="btn btn-sm " name="TicketId" id="TicketId" value="<%=recvdlist[0]%>"  onclick="SeeRemarks('<%=recvdlist[0] %>')"  data-toggle="tooltip" title="" data-original-title="Remarks">
															<i class="fas fa-envelope" style="color: black;"></i>
														</button> 
														<%} %>			
										
										 <input id="remarks<%=recvdlist[0]%>" value="<%=recvdlist[13]%>" type="hidden">
										<input type="hidden" name="TicketId" value="">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										
									</td>
								</tr>
								 <%count++;%>
								<%}} %>
								<%if(RecievedList.size()==0){ %>
								<tbody></tbody>
							<!-- <tr><td  colspan="11" style="text-align: center;"> No Recieved Forms </td></tr> -->
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
		
		
		
		
		
		
		<div class="modal fade" id="forwardticket" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document" style="max-width: 53% !important;height: 45%;">
				<div class="modal-content" style="min-height: 45%;" >
				    <div class="modal-header" style="background-color: rgba(0,0,0,.03);">
				    	<h4 class="modal-title" id="model-card-header" style="color: #145374">Forward <!-- By <span id="feedby1"></span> --></h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close" >
				          <span aria-hidden="true">&times;</span>
				        </button>
				    </div>
				     <div class="modal-body">
  	      
  	      	<form action="TicketForward.htm" method="POST"  id="Remarksadd">
  	      		<div class="row">
					<div class="col-md-12" > <b>Remarks : </b><br>
  	      		    		<textarea rows="2" style="display:block; " class="form-control"  id="Remarks" name="Remarks"  placeholder="Enter Remarks..!!"  required="required"></textarea>
  	      		    </div>
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

function ForwardTicket(TicketId1)
{
	$("#TicketId1").val(TicketId1);
	$('#forwardticket').modal('toggle');
}
function SeeRemarks(TicketId)
{
	$('#SeeRemarks').modal('toggle');
	$("#REMARKS").html($("#remarks"+TicketId).val());
}

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



</script>
</html>