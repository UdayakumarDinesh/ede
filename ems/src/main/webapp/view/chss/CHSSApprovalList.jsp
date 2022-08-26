<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<style>
	body{
		overflow-x:hidden !important; 
	}
	
.count-badge {
    position: absolute;
    transform: scale(.8);
    transform-origin: top right;
    margin-left: 0rem;
    margin-top: -0.25rem;
    background: red;
    font-family: 'Lato', sans-serif;
 	padding: 0.25em 0.5em;
   	font-size: 13px;
}
    
}
</style>

</head>
<body>

<%
	List<Object[]> OPDclaimlist=(List<Object[]>)request.getAttribute("OPDclaimlist");
	List<Object[]> IPDclaimlist=(List<Object[]>)request.getAttribute("IPDclaimlist");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	
	String tab   = (String)request.getAttribute("tab");
%>
 
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Claims Pending</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item active " aria-current="page">Claims Pending</li>
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
				
						
		<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist" style="background-color: #E1E5E8;padding:0px;">
		  <li class="nav-item" style="width: 50%;"  >
		    <div class="nav-link active" style="text-align: center;" id="pills-OPD-tab" data-toggle="pill" data-target="#pills-OPD" role="tab" aria-controls="pills-OPD" aria-selected="true">
			   <span>OPD Claims   
				   <span class="badge badge-danger badge-counter count-badge">
				   		<%if(OPDclaimlist.size()>99){ %>
				   			99+
				   		<%}else{ %>
				   			<%=OPDclaimlist.size() %>
						<%} %>				   			
				  </span>  
				</span> 
		    </div>
		  </li>
		  <li class="nav-item"  style="width: 50%;">
		    <div class="nav-link" style="text-align: center;" id="pills-IPD-tab" data-toggle="pill" data-target="#pills-IPD" role="tab" aria-controls="pills-IPD" aria-selected="false">
		    	 <span>IPD Claims   
				   <span class="badge badge-danger badge-counter count-badge">
				   		<%if(IPDclaimlist.size()>99){ %>
				   			99+
				   		<%}else{ %>
				   			<%=IPDclaimlist.size() %>
						<%} %>				   			
				   </span>  
				</span> 
		    
		    
		    </div>
		  </li>
		</ul>
		
		
		<div class="tab-content" id="pills-tabContent">
			<div class=" tab-pane  show active" id="pills-OPD" role="tabpanel" aria-labelledby="pills-OPD-tab" >
			
				<div class="card" >
					<div class="card-body main-card " >
						
						<form action="CHSSApprovalForward.htm" method="post" id="ClaimForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							
							<br>
							<div class="table-responsive">
					   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
									<thead>
										<tr>
											<th style="text-align: center;padding-top:5px; padding-bottom: 5px;">
												SN
											</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Claim No</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Employee</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Patient Name</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Claim Date</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Status</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Action</th>
										</tr>
									</thead>
									<tbody>
										<%long slno=0;
										for(Object[] obj : OPDclaimlist){ 
											slno++; %>
											<tr>
												<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" >
													<%=slno %>
													<%-- <input type="checkbox" class="checkbox" name="chssapplyidcb" value="<%=obj[0] %>" checked> --%>
												</td>
												<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
												<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[19] %></td>
												<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[12] %></td>
												<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
												<td >
												
													 <button class="btn btn-sm btn-link w-100 " formaction="Chss-Status-details.htm" name="chssapplyid" value="<%=obj[0]%>" formtarget="_blank" 
														 data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color:<%=obj[20] %>; font-weight: 600;" >
													  &nbsp;<%=obj[18] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
												</td>
												
												
												<td style="padding-top:5px; padding-bottom: 5px;">
													
													<%if(obj[6].toString().equalsIgnoreCase("OPD")){ %>
													
														<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
													<%} else if(obj[6].toString().equalsIgnoreCase("IPD")){ %>
														<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
													
													<%} %>
													<input type="hidden" name="view_mode" value="E">
															
												</td>
											</tr>
										<%} %>
										
									</tbody>
								</table>
								<input type="hidden" name="isapproval" value="Y">
							</div>
	
						</form>
						
						
					</div>
				</div>		
				
			</div>
			
			
			
			<div class="card tab-pane " id="pills-IPD" role="tabpanel" aria-labelledby="pills-IPD-tab" >	
			
				<div class="card" >
					<div class="card-body main-card " >
						
						<form action="CHSSApprovalForward.htm" method="post" id="ClaimForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							
							<br>
							<div class="table-responsive">
					   			<table class="table table-bordered table-hover table-striped table-condensed" style="width:100%"  id="myTable1"> 
									<thead>
										<tr>
											<th style="text-align: center;padding-top:5px; padding-bottom: 5px;">
												SN
											</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Claim No</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Employee</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Patient Name</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Claim Date</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Status</th>
											<th style="padding-top:5px; padding-bottom: 5px;">Action</th>
										</tr>
									</thead>
									<tbody>
										<%slno=0;
										for(Object[] obj : IPDclaimlist){ 
											slno++; %>
											<tr>
												<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" >
													<%=slno %>
													<%-- <input type="checkbox" class="checkbox" name="chssapplyidcb" value="<%=obj[0] %>" checked> --%>
												</td>
												<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
												<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[19] %></td>
												<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[12] %></td>
												<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[15].toString()))%></td>
												<td >
												
													 <button class="btn btn-sm btn-link w-100 " formaction="Chss-Status-details.htm" name="chssapplyid" value="<%=obj[0]%>" formtarget="_blank" 
														 data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color:<%=obj[20] %>; font-weight: 600;" >
													  &nbsp;<%=obj[18] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i></button>
												</td>
												
												
												<td style="padding-top:5px; padding-bottom: 5px;">
													
													<%if(obj[6].toString().equalsIgnoreCase("OPD")){ %>
													
														<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
													<%} else if(obj[6].toString().equalsIgnoreCase("IPD")){ %>
														<button type="submit" class="btn btn-sm view-icon" name="chssapplyid" value="<%=obj[0] %>" formaction="CHSSIPDFormEdit.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
															<i class="fa-solid fa-eye"></i>
														</button>	
													
													<%} %>
													<input type="hidden" name="view_mode" value="E">
															
												</td>
											</tr>
										<%} %>
										
									</tbody>
								</table>
								<input type="hidden" name="isapproval" value="Y">
							</div>
	
						</form>
						
						
					</div>
				</div>
			
			</div>
		</div>	
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		</div>
	
	 </div>


<script type="text/javascript">


$("#myTable1").DataTable({
    "lengthMenu": [ 50, 75, 100],
    "pagingType": "simple",
    "language": {
	      "emptyTable": "No Record Found"
	    }

});



<%if(tab!=null){%>

	$('#pills-<%=tab%>-tab').click();

<%}%>


</script>




</body>
</html>