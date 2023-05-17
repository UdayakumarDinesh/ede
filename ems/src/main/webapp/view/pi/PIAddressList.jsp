<%@page import="com.vts.ems.pis.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List,com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Personal Intimations</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 
<style type="text/css">

.trup
		{
			padding:6px 10px 6px 10px ;			

			border-radius: 5px;
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
	String LoginType = (String)session.getAttribute("LoginType");
	List<Object[]> resAddress = (List<Object[]>)request.getAttribute("resAddress");
	List<Object[]> perAddress = (List<Object[]>)request.getAttribute("perAddress");
	String edit = (String)request.getAttribute("edit");
	
	Object[] CeoName = (Object[])request.getAttribute("CeoName");
	Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
	Object[] PandAEmpName = (Object[])request.getAttribute("PandAEmpName");
	
	String CEO = (String)request.getAttribute("CEOEmpNos");
	List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
	
	
	/* Employee emp=(Employee)request.getAttribute("EmployeeD"); */
	Object[] empData=(Object[])request.getAttribute("EmpData");
	List<String> toUserStatus  = Arrays.asList("INI","RDG","RPA");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-7">
				<h5>Address List<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-5" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Address</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	

<div class="page card dashboard-card">
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
		 	
<div class="card">					
		<div class="card-body">
			<h5>Permanent Address List</h5>
			  <hr>
				<form action="PermanentAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable2"> 
					   <thead>
									
					    <tr align="center">
					       <th>Select</th>
					       <th>Permanent Address</th>
					       <th>Per.From</th>
					       <th>Per.To</th>
						   <th>Mobile No.</th>							
						   <th>Status</th>
						   <th>Action</th> 
					   </tr>
					   </thead>
					   <tbody>
					   		<%
					        if(perAddress!=null){
					   		for(Object[] obj : perAddress){ %>
							<tr align="center">
							<td style="text-align: center;width:4%;">
							<%if( toUserStatus.contains(obj[15].toString()) ){ %>
							<input type="radio" name="peraddressid" value="<%=obj[1]%>"> <%}else{ %>
							<input type="radio" name="peraddressid" value="<%=obj[1]%>" disabled>
							<%} %>							
							</td>					    
						    <td style="text-align: left;"><%=obj[2]%> , <%=obj[7]%> , <%=obj[6]%> - <%=obj[8]%></td>
							<td style="width:10%;"><%if(obj[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[3]+"")%><%}else{%>--<%}%></td>							
							<td style="width:10%;"><%if(obj[4]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[4]+"")%><%}else{%>--<%}%></td>																								
							<td style="width:8%;"> <%if(obj[5]!=null){ %> <%=obj[5] %> <%}else{ %>--<%} %> </td>	
							<td style="width:20%;">
							<%if(obj[12]!=null){%>
								  
								 	<%if(obj[11]!=null && obj[11].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="PerAddrTransactionStatus.htm" value="<%=obj[1] %>" name="peraddressid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
								    	
							    	<%}else if(obj[11]!=null && obj[11].toString().equalsIgnoreCase("E") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="PerAddrTransactionStatus.htm" value="<%=obj[1] %>" name="peraddressid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: red; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Expired <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    		
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="PerAddrTransactionStatus.htm" value="<%=obj[1] %>" name="peraddressid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[13] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[12] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
							</td>
							
							<td style="text-align: left;width:9%;">						
							<button type="submit" class="btn btn-sm view-icon" formaction="PersonalIntimation.htm" name="peraddressId" value="<%=obj[1] %>" data-toggle="tooltip" data-placement="top" title="Form For Permanent Address Change" style="font-weight: 600;" >
									<i class="fa-solid fa-eye"></i>
							</button>
							<button type="submit" class="btn btn-sm" name="peraddressId" value="<%=obj[1] %>" formaction="AddressFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
									<i style="color: #019267" class="fa-solid fa-download"></i>
							</button>
                            </td>		
					   </tr>
					   <%} }%>
					</tbody>
					</table>
					</div>	
					<div class="row text-center">
						<div class="col-md-12">
						     <%-- <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>"> --%>
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADDPerAddress"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDITPerAddress"  Onclick="EditPer(empForm)" >EDIT </button>
					    	<!-- <button type="submit" class="btn btn-sm delete-btn" name="Action" value="DELETE" Onclick="Delete(empForm)" >DELETE </button> -->
					    </div>
					    </div>							
				</form>
             </div>
		</div>			 
		 
		 
		 
		 
<div class="card">					
		<div class="card-body">
			<h5>Residential Address List</h5>
			  <hr>
				<form action="ResidentialAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable3"> 
					   <thead>
						    <tr align="center">
						        <th>Select</th>
								<th>Residential Address</th>
								<th>Res.From</th>
								<th>Res.To</th>
								<th>Mobile No.</th>	
								<th>Status</th>
								<th>Action</th> 
						   </tr>
					   </thead>
					   <tbody>
					   		<% 
					   		if(resAddress!=null){
					   		for(Object[] obj : resAddress){ %>
							<tr align="center">
							<td style="text-align: center;width:4%;">
								<%if(toUserStatus.contains(obj[18].toString()) ){ %>
									<input type="radio" name="resaddressid" value="<%=obj[1]%>"> <%}else{ %>
									<input type="radio" name="resaddressid" value="<%=obj[1]%>" disabled>
								<%} %>
							</td>												    
						    <td style="text-align: left;"><%=obj[2]%> , <%=obj[10]%> , <%=obj[9]%> - <%=obj[11]%></td>
							<td style="width:10%;"><%if(obj[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[3]+"")%><%}else{%>--<%}%></td>							
							<td style="width:10%;"><%if(obj[4]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[4]+"")%><%}else{%>--<%}%></td>							
						    <td style="width:8%;"><%if(obj[5]!=null){ %> <%=obj[5] %> <%}else{ %>--<%} %> </td>																		
							
							<td style="width:20%;">
													
								<%if(obj[15]!=null){%>
								  
								 	<%if(obj[14]!=null && obj[14].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="ResAddrTransactionStatus.htm" value="<%=obj[1] %>" name="addressresid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%}else if(obj[14]!=null && obj[14].toString().equalsIgnoreCase("E") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="ResAddrTransactionStatus.htm" value="<%=obj[1] %>" name="addressresid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: red; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Expired <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    		
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="ResAddrTransactionStatus.htm" value="<%=obj[1] %>" name="addressresid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[16] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[15] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
							</td>
							
							<td style="text-align: left;width:9%;">
								<button type="submit" class="btn btn-sm view-icon" formaction="PersonalIntimation.htm" name="resaddressId" value="<%=obj[1] %>" data-toggle="tooltip" data-placement="top" title="Form For Residential Address Change" style="font-weight: 600;" >
								   <i class="fa-solid fa-eye"></i>
								</button>
								<button type="submit" class="btn btn-sm" name="resaddressId" value="<%=obj[1] %>" formaction="AddressFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								   <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>
                            </td>	
                            	
					   </tr>
					   <%} }%>
					</tbody>
					</table>
					</div>	
					<div class="row text-center">
						<div class="col-md-12">
						     <%-- <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>"> --%>
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="EditRes(empForm)" >EDIT </button>
					    	<!-- <button type="submit" class="btn btn-sm delete-btn" name="Action" value="DELETE" Onclick="Delete(empForm)" >DELETE </button> -->
					    </div>
					    </div>							
				</form>
				
				
		<hr>
			<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow</b></div>
		 	</div>
		 	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               		<tr>
	               		<%if( !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ) {%>
	                		<td class="trup" style="background: #E8E46E;">
	                			User -  <%=empData[1] %>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                	<%} %>
	               		<%if(DGMEmpName!=null && !DGMs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: #FBC7F7;">
	                			DGM -  <%=DGMEmpName[1] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<%if(PandAEmpName!=null && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: #4DB6AC;" >
	                			P&A - <%=PandAEmpName[1] %>
	                		</td>
	               		<%} %>
	               		<%if(CeoName!=null && CEO.equalsIgnoreCase(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: #4DB6AC;" >
	                			CEO - <%=CeoName[1] %>
	                		</td>
	               		<%} %>
	               	</tr>			   
		                	
	               	<%-- <tr>
	               	    <%if( !PandAs.contains(emp.getEmpNo()) && !CEO.equalsIgnoreCase(emp.getEmpNo())  ) {%>
	               		<td class="trdown" style="background: #E8E46E;" >	
				              <%=emp.getEmpName() %>
	                	</td>
	                    <%} %>
	               		<%if(DGMEmpName!=null && !DGMs.contains(emp.getEmpNo()) && !PandAs.contains(emp.getEmpNo()) && !CEO.equalsIgnoreCase(emp.getEmpNo()) ){ %>
	                		<td class="trdown" style="background: #FBC7F7;" >	
				                <%=DGMEmpName[1] %>
	                		</td>
	               		 <%} %>
	               		 <%if(PandAEmpName!=null && !CEO.equalsIgnoreCase(emp.getEmpNo()) ){ %>
	               			<td class="trdown" style="background: #4DB6AC;" >	
			                	<%=PandAEmpName[1] %>
		           			</td>
		           		 <%} %>
		           		  <%if(CeoName!=null && CEO.equalsIgnoreCase(emp.getEmpNo()) ){ %>
	               			<td class="trdown" style="background: #4DB6AC;" >	
			                	<%=CeoName[1] %>
		           			</td>
		           		 <%} %>
		            	</tr>              --%>	
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
function EditRes(myfrm) {

	var fields = $("input[name='resaddressid']").serializeArray();
	if (fields.length === 0) {
		alert("Please Select Atleast One Residential Address");

		event.preventDefault();
		return false;
	}
	return true;
}

</script>
<script type="text/javascript">
function EditPer(myfrm) {

	var fieldsperadd = $("input[name='peraddressid']").serializeArray();
 
	if (fieldsperadd.length === 0) {
		alert("Please Select Atleast One Permanent Address");

		event.preventDefault();
		return false;
	}
	return true;
}

</script> 
</body>
</html>