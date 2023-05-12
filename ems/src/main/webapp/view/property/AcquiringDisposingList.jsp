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
 List<Object[]> ImmPropDetails = (List<Object[]>)request.getAttribute("ImmPropDetails");
 
    Object[] CeoName = (Object[])request.getAttribute("CeoName");
	Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
	Object[] PandAEmpName = (Object[])request.getAttribute("PandAEmpName");
	
	String CEO = (String)request.getAttribute("CEOEmpNos");
	List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
	
	
	Employee emp=(Employee)request.getAttribute("Employee");
	
	List<String> toUserStatus  = Arrays.asList("INI","RPA","RCE");
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5 style="width:115%;">Acquiring / Disposing List</h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PropertyDashBoard.htm">Property</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Acquiring / Disposing List</li>
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
		  <h5>Immovable Property List</h5>
			  <hr>
				<form action="ImmovablePropAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable2"> 
					   <thead>
									
					    <tr align="center">
					       <th>Select</th>
					       <th>Purpose</th>
					       <th>Transaction <br>State </th>
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
						    <td style="text-align: center;width:10%;"><%if(obj[4]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(obj[4]+"") %> <%} %> </td>
						    <td style="text-align: left;width:10%;"><%if(obj[5]!=null){ %> <%=obj[5] %> <%} %> </td>
						    <td style="text-align: left;">
						    <%if(obj[6]!=null){ %> <%=obj[6] %> <%} %>
						    <%if(obj[7]!=null){ %> <%=", "+obj[7] %> <%} %>
						    <%if(obj[8]!=null){ %> <%=", "+obj[8] %> <%} %>
						    <%if(obj[9]!=null){ %> <%=" - "+obj[9] %> <%} %>
						     </td>
						    <td style="text-align: right;width:10%;"><%if(obj[10]!=null){ %> <%=obj[10] %> <%} %> </td>
					   		<td style="width:20%;">
													
								<%if(obj[14]!=null){%>
								  
								 	<%if(obj[11]!=null && obj[11].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="ImmovablePropTransStatus.htm" value="<%=obj[0] %>" name="immpropertyid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%}else if(obj[11]!=null && obj[11].toString().equalsIgnoreCase("E") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="ImmovablePropTransStatus.htm" value="<%=obj[0] %>" name="immpropertyid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: red; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Expired <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    		
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="ImmovablePropTransStatus.htm" value="<%=obj[0] %>" name="immpropertyid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[15] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[14] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
							</td>
					   		
					   		<td style="text-align: left;width:9%;">
								<button type="submit" class="btn btn-sm view-icon" formaction="ImmovablePropPreview.htm" name="immPropertyId" value="<%=obj[0] %>" data-toggle="tooltip" data-placement="top" title="Form For Hometown Change" style="font-weight: 600;" >
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
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="EditPer(empForm)" >EDIT </button>
					    </div>
				   </div>
				  
				</form>
				<hr>
			<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow For Property Acquisition / Disposal</b></div>
		 	</div>
		 	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
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
function EditPer(myfrm) {

	var fieldsperadd = $("input[name='immpropertyid']").serializeArray();
 
	if (fieldsperadd.length === 0) {
		alert("Please Select Atleast One Property");

		event.preventDefault();
		return false;
	}
	return true;
}

</script> 
</body>
</html>