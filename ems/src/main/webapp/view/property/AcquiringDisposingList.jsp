<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List,com.vts.ems.pis.model.Employee,com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<style type="text/css">

.trup
		{
			padding:5px 10px 0px 10px ;			
			border-top-left-radius : 5px; 
			border-top-right-radius: 5px;
			font-size: 14px;
			font-weight: 600;
		}
		
		.trdown
		{
			padding:0px 10px 5px 10px ;			
			border-bottom-left-radius : 5px; 
			border-bottom-right-radius: 5px;
			font-size: 14px;
			font-weight: 600;
		}


</style>
</head>
<body>
<%
   List<Object[]> ImmPropDetails = (List<Object[]>)request.getAttribute("ImmPropDetails");
   List<Object[]> MovPropDetails = (List<Object[]>)request.getAttribute("MovPropDetails");
 
    Object[] CeoName = (Object[])request.getAttribute("CeoName");
	Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
	Object[] PandAEmpName = (Object[])request.getAttribute("PandAEmpName");
	
	String CEO = (String)request.getAttribute("CEOEmpNos");
	List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
	Object[] EmpApprFlow = (Object[])request.getAttribute("EmpApprFlow");
	
	Employee emp=(Employee)request.getAttribute("Employee");
	Object[] empData=(Object[])request.getAttribute("EmpData");
	
	List<String> toUserStatus  = Arrays.asList("INI","RDG","RPA","RCE");
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-7">
				<h5>Acquiring / Disposing List<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-5" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PropertyDashBoard.htm">Property</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Acquiring &nbsp;/ &nbsp; Disposing List</li>
				  </ol>
				</nav>
			</div>			
		</div>
</div>

<div class="page card dashboard-card">
  <div class="card-body" >
   <div align="center">
		   <% String ses=(String)request.getParameter("result"); 
			  String ses1=(String)request.getParameter("resultfail");
			  if(ses1!=null){ %>
				<div class="alert alert-danger" role="alert"  style="margin-top: 5px;">
					<%=ses1 %>
				</div>
			  <%}if(ses!=null){ %>
				<div class="alert alert-success" role="alert"  style="margin-top: 5px;">
					<%=ses %>
				</div>
			 <%} %>
	</div>
	<div class="row w-100" style="margin-bottom: 10px;">
	 <div class="col-12">
	<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist" style="background-color: #E1E5E8;padding:0px;">
		  <li class="nav-item" style="width: 50%;"  >
		    <div class="nav-link active" style="text-align: center;" id="pills-mov-property-tab" data-toggle="pill" data-target="#pills-mov-property" role="tab" aria-controls="pills-mov-property" aria-selected="true">
			   <span>Movable Property 	</span> 
				
		    </div>
		  </li>
		  <li class="nav-item"  style="width: 50%;">
		    <div class="nav-link" style="text-align: center;" id="pills-imm-property-tab" data-toggle="pill" data-target="#pills-imm-property" role="tab" aria-controls="pills-imm-property" aria-selected="false">
		    	 <span>Immovable Property </span> 
		    </div>
		  </li>
		</ul>
	</div>
	</div>
	<!-- Movable Property List -->
	<div class="card">					
		<div class="card-body">
		<div class="container-fluid" >
            <div class="tab-content" id="pills-tabContent">
               <div class="tab-pane fade show active" id="pills-mov-property" role="tabpanel" aria-labelledby="pills-mov-property-tab">
		    <h5>Movable Property List</h5>
			  <hr>
				<form action="MovablePropAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
					   <thead>
									
					    <tr align="center">
					       <th>Select</th>
					       <th>Purpose</th>
					       <th>Transaction </th>
					       <th>Date</th>	
					       <th>Mode</th>					       								
					       <th>Description</th>					       								
					       <th>Price</th>					       								
						   <th>Status</th>
						   <th>Action</th> 
					   </tr>
					   </thead>
					   <tbody>
                           <%if(MovPropDetails!=null){
					        for(Object[] obj : MovPropDetails){ %>
					   		<tr align="center">
					   		<td style="text-align: center;width:4%;">
								<%if(toUserStatus.contains(obj[14].toString()) ){ %>
									<input type="radio" name="movpropertyid" value="<%=obj[0]%>"> <%}else{ %>
									<input type="radio" name="movpropertyid" value="<%=obj[0]%>" disabled>
								<%} %>
							</td>
						    <td style="text-align: left;width:8%;"><%if(obj[2]!=null && obj[2].toString().equalsIgnoreCase("I")){ %><%="Intimation"%><%}else{%><%="Permission"%> <%}%> </td>
						    <td style="text-align: left;width:6%;"><%if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("A")){ %><%="Acquisition"%><%}else{%><%="Disposing"%> <%}%> </td>
						    <td style="text-align: center;width:11%;"><%if(obj[4]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(obj[4]+"") %> <%} %> </td>
						    <td style="text-align: left;width:10%;"><%if(obj[7]!=null){ %> <%=obj[7] %> <%} %> </td>
						    <td style="text-align: left;">
						    <%if(obj[5]!=null){ %> <%=obj[5] %> <%} %>
						    <%if(obj[6]!=null || !obj[6].toString().isEmpty()){ %>	<%="("+obj[6]+")" %> <%} %>					 
						     </td>
						    <td style="text-align: right;width:10%;"><%if(obj[8]!=null){ %> <%=obj[8] %> <%} %> </td>
					   		<td style="width:17%;">
													
								<%if(obj[12]!=null){%>
								  
								 	<%if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="" value="<%=obj[0] %>" name="movpropertyid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="" value="<%=obj[0] %>" name="movpropertyid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[13] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[12] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
							</td>
					   		
					   		<td style="text-align: left;width:10%;">
								<button type="submit" class="btn btn-sm view-icon" formaction="" name="movPropertyId" value="<%=obj[0] %>" data-toggle="tooltip" data-placement="top" title="Form For Movable Property" style="font-weight: 600;" >
								   <i class="fa-solid fa-eye"></i>
								</button>
								<button type="submit" class="btn btn-sm" name="movPropertyId" value="<%=obj[0] %>" formaction="" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								   <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>
                            </td>	
                            
					   		</tr>
					   		<%} }%>
					   </tbody>
				    </table>
				   </div>	
				   <div>
				   </div>				
				    <div class="row text-center">
						<div class="col-md-12">
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADDMov">ADD </button>		
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDITMov" Onclick="EditMov(empForm)" >EDIT </button>
					    </div>
				   </div>
				  
				</form>
				
			  </div>
 
	<!-- Immovable Property List -->	
		<div class="tab-pane fade" id="pills-imm-property" role="tabpanel" aria-labelledby="pills-imm-property-tab">		
		  <h5>Immovable Property List</h5>
			  <hr>
				<form action="ImmovablePropAddEdit.htm" method="POST" id="empForm">
				<input type="hidden" name="tab" value="closed"/>
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable2"> 
					   <thead>
									
					    <tr align="center">
					       <th>Select</th>
					       <th>Purpose</th>
					       <th>Transaction</th>
					       <th>Date</th>	
					       <th>Mode</th>					       								
					       <th>Location</th>					       								
					       <th>Price</th>					       								
						   <th>Status</th>
						   <th>Action</th> 
					   </tr>
					   </thead>
					   <tbody>
                           <%if(ImmPropDetails!=null){
					        for(Object[] obj : ImmPropDetails){ %>
					   		<tr align="center">
					   		<td style="text-align: center;width:4%;">
								<%if(toUserStatus.contains(obj[16].toString()) ){ %>
									<input type="radio" name="immpropertyid" value="<%=obj[0]%>"> <%}else{ %>
									<input type="radio" name="immpropertyid" value="<%=obj[0]%>" disabled>
								<%} %>
							</td>
						    <td style="text-align: left;width:8%;"><%if(obj[2]!=null && obj[2].toString().equalsIgnoreCase("I")){ %><%="Intimation"%><%}else{%><%="Permission"%> <%}%> </td>
						    <td style="text-align: left;width:6%;"><%if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("A")){ %><%="Acquisition"%><%}else{%><%="Disposing"%> <%}%> </td>
						    <td style="text-align: center;width:11%;"><%if(obj[4]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(obj[4]+"") %> <%} %> </td>
						    <td style="text-align: left;width: 10%;"><%if(obj[5]!=null){ %> <%=obj[5] %> <%} %> </td>
						    <td style="text-align: left;width: 23%;">
						    <%if(obj[6]!=null){ %> <%=obj[6] %> <%} %>
						    <%if(obj[7]!=null){ %> <%=", "+obj[7] %> <%} %>
						    <%if(obj[8]!=null){ %> <%=", "+obj[8] %> <%} %>
						    <%if(obj[9]!=null){ %> <%=" - "+obj[9] %> <%} %>
						     </td>
						    <td style="text-align: right;width:10%;"><%if(obj[10]!=null){ %> <%=obj[10] %> <%} %> </td>
					   		<td style="width:17%;">
													
								<%if(obj[14]!=null){%>
								  
								 	<%if(obj[11]!=null && obj[11].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="ImmovablePropTransStatus.htm" value="<%=obj[0] %>" name="immpropertyid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="ImmovablePropTransStatus.htm" value="<%=obj[0] %>" name="immpropertyid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[15] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[14] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
							</td>
					   		
					   		<td style="text-align: left;width:10%;">
								<button type="submit" class="btn btn-sm view-icon" formaction="ImmovablePropPreview.htm" name="immPropertyId" value="<%=obj[0] %>" data-toggle="tooltip" data-placement="top" title="Form For Immovable Property" style="font-weight: 600;" >
								   <i class="fa-solid fa-eye"></i>
								</button>
								<button type="submit" class="btn btn-sm" name="immPropertyId" value="<%=obj[0] %>" formaction="ImmovablePropFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								   <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>
                            </td>	
                            
					   		</tr>
					   		<%} }%>
					   </tbody>
				    </table>
				   </div>	
				   <div>
				   </div>				
				    <div class="row text-center">
						<div class="col-md-12">
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD"   >ADD </button>		
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="EditImm(empForm)" >EDIT </button>
					    </div>
				   </div>
				  
				</form>				
			   </div>
			   </div>
			   </div>
			   <hr>
			<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow For Property Acquisition / Disposal</b></div>
		 	</div>
		 	<%-- <div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               		 <tr>
	               		<%if( !PandAs.contains(emp.getEmpNo()) && !CEO.equalsIgnoreCase(emp.getEmpNo()) ) {%>
	                		<td class="trup" style="background: #E8E46E;">
	                			User - <%=emp.getEmpName() %>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                		<%} %>             		
	               		<%if(PandAEmpName!=null && !CEO.equalsIgnoreCase(emp.getEmpNo()) ){ %>
	                		<td class="trup" style="background: #FBC7F7;" >
	                			P&A - <%=PandAEmpName[1] %>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<%if(CeoName!=null  ){ %>
	                		<td class="trup" style="background: #4DB6AC;" >
	                			CEO - <%=CeoName[1] %>
	                		</td>
	               		<%} %>
	                	</tr> --%> 
	                	
	                	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               		<tr>
	               		<%if( !CEO.equalsIgnoreCase(empData[0].toString()) ) {%>
	                		<td class="trup" style="background: linear-gradient(to top, #3c96f7 10%, transparent 115%);">
	                			User <br> <%=session.getAttribute("EmpName")%>
	                		</td>
	                		<td rowspan="2" >
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               			<%} %>
	               		<%if(DGMEmpName!=null && !DGMs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %>                		
	               			<td class="trup"  style="background: linear-gradient(to top, #eb76c3 10%, transparent 115%);">
	                			DGM <br> <%=EmpApprFlow[1]%>
	                		</td>
	                		
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		 <%} %>
	                		<%-- <td class="trup"  style="background: linear-gradient(to top, #6ba5df 10%, transparent 115%);" >
	                			F & A <br> <%=EmpApprFlow[2]%>
	                		</td> 
	                		
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td> --%>
	                		<%if(PandAEmpName!=null && !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: linear-gradient(to top, #42f2f5 10%, transparent 115%);">
	                			P & A <br> <%=EmpApprFlow[3]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>	                		 
	                		<%} %>
	                		<%if(CeoName!=null ){ %>
	                		<td class="trup" style="background: linear-gradient(to top, #4DB6AC 10%, transparent 115%);">
	                			CEO <br> <%=EmpApprFlow[4]%>
	                		</td> 
	                		<%} %>
	               		
	               	</tr>			
			         </table>			             
			   </div>
       </div>
</div>	
				
</div>	
<script type="text/javascript">
$("#myTable2").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});
$("#myTable3").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});

</script>
<script type="text/javascript">
function EditImm(myfrm) {

	var fieldsimmprop = $("input[name='immpropertyid']").serializeArray();
 
	if (fieldsimmprop.length === 0) {
		alert("Please Select Atleast One Property");

		event.preventDefault();
		return false;
	}
	return true;
}

function EditMov(myfrm) {

	var fieldsmovprop = $("input[name='movpropertyid']").serializeArray();
 
	if (fieldsmovprop.length === 0) {
		alert("Please Select Atleast One Property");

		event.preventDefault();
		return false;
	}
	return true;
}

<%-- <%if(tab!=null && tab.equals("closed")){%>

$('#pills-imm-property-tab').click();

<%}%> --%>

</script> 
</body>
</html>