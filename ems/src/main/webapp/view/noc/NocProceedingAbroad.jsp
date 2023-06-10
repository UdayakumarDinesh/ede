<%@page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

body{

 overflow-x: hidden;
  overflow-y: hidden;

}

</style>
</head>
<body>

 <%	
	
    SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
    Object[] CeoName = (Object[])request.getAttribute("CeoName");
    Object[] PandAEmpName = (Object[])request.getAttribute("PandAEmpName");
	Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
	Object[] DivisionHeadName = (Object[])request.getAttribute("DivisionHeadName");
	Object[] GroupHeadName = (Object[])request.getAttribute("GroupHeadName");
	
	String CEO = (String)request.getAttribute("CEOEmpNos");
	List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
	List<String> DHs = (List<String>)request.getAttribute("DivisionHeadEmpNos");
	List<String> GHs = (List<String>)request.getAttribute("GroupHeadEmpNos");
	List<String> SOs = (List<String>)request.getAttribute("SOEmpNos");
	
	Object[] empData=(Object[])request.getAttribute("EmpData");
	Object[] NocEmpList= (Object[])request.getAttribute("NocEmpList");
	Object[] EmpPassport= (Object[])request.getAttribute("EmpPassport");
	Employee emp=(Employee)request.getAttribute("EmployeeD");
	
	Object[] emplist = (Object[])request.getAttribute("NocApprovalFlow"); 
	
	
	List<Object[]> NocProcAbraodList=(List<Object[]>)request.getAttribute("NocProcAbraodList");
	List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RSO","RPA","RCE","DPR");
	
	
	
   %>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-5">
				<h5>NOC Proceeding Abroad <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active" aria-current="page">Proceeding
						Abroad</li>
				</ol>
			</div>

		</div>
		
		</div>
	
	
	<!-- <div class="page card dashboard-card"> -->
		<div align="center">
		<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){ %>
		
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
		</div>
		

   <div class="card">					
		<div class="card-body">
		<!-- <div class="card-body main-card"> -->

			<form action="#" method="POST" id="NOCProcAbroad">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				 <div class="table-responsive"> 
					 	<table class="table table-bordered table-hover table-striped table-condensed"  id="table"> 
						<thead>
							<tr>
								<th style="width: 6%">Select</th>
								<th style="width: 10%">NOC Proceeding Abroad</th>
							    <th style="width: 10%">Initiated Date</th> 
								<th style="width: 15%">Status</th>
								<th style="width: 8%">Action</th>
							</tr>
						</thead>
						<tbody>
						
						<% for(Object[] obj:NocProcAbraodList){ %>
						
						<tr>
								<td style="text-align: center;"><%if(toUserStatus.contains(obj[7].toString()) ){ %><input type="radio" name="ProcAbrId" value="<%=obj[0]%>"><%}
								else{%><input type="radio" name="ProcAbrId" value="<%=obj[0]%>" disabled>  <%} %></td>
								<td style="text-align: center;"><%=obj[1] %></td>
								<td style="text-align: center;"><%=rdf.format(sdf.parse(obj[8].toString()))%></td> 
								<td style="text-align: center;">
								
								  
								 	<%if(obj[2]!=null && obj[2].toString().equalsIgnoreCase("A") ){ %>
							    		 <button type="submit" class="btn btn-sm btn-link w-100" formaction="NOCProcTransactionStatus.htm" formmethod="GET"  value="<%=obj[0] %>" name="ProcAbrId"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: blue; font-weight: 600;" formtarget="_blank">
								    		&nbsp;  Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
								    	
							    	<%} else if(obj[2]!=null && obj[2].toString().equalsIgnoreCase("E")){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="NOCProcTransactionStatus.htm" value="<%=obj[0] %>" name="ProcAbrId"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: purple; font-weight: 600;" formtarget="_blank">
								    		&nbsp;Expired<i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    		
							    		<%}
								 	
							    	else{%>
							    	
							    	    <button type="submit" class="btn btn-sm btn-link w-100" formaction="NOCProcTransactionStatus.htm" value="<%=obj[0] %>" name="ProcAbrId"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: purple; font-weight: 600;" formtarget="_blank">
								    		&nbsp;<%=obj[5] %><i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button> 
							    	<%} %>	
							    </td>
							    
								<td style="text-align: center;">
								    <button type="submit" class="btn btn-sm " name="ProcAbrId" value="<%=obj[0] %>"   formaction="ProcAbroadPreview.htm" formmethod="POST" data-toggle="tooltip" title="" data-original-title="View Details">
								    <i class="fa fa-eye " style="color: black;"></i>
							    </button> 
							    
							    <button type="submit" class="btn btn-sm" name="ProcAbrId" value="<%=obj[0] %>"  formaction="ProcAbroadPrint.htm"  formmethod="GET" formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="Download">
								  <i style="color: #019267" class="fa-solid fa-download"></i>
								</button>  
								
								<% if(PandAs.contains(emp.getEmpNo()) && obj[2].toString().equalsIgnoreCase("A")){ %>  
								
								
								 <button type="submit" class="btn btn-sm" name="ProcAbrId" value="<%=obj[0]%>" formaction="NOCProcAbroadCertificate.htm"  formmethod="GET" formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="NOC Certificate">
											<i style="color: #5C469C;font-size:20px;" class="fa fa-envelope-open-text"></i>
							    </button> 
								
								
								<%} %>
							</td>
							<%} %>

							</tr>
							
                       </tbody>
					</table>

					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					
					
				 </div>

				<div class="row text-center">
					<div class="col-md-12">
						
						<button type="submit" class="btn btn-sm add-btn" formaction="ProcAbroadAdd.htm"  <% if(NocEmpList==null){ %> onclick=" return message('E')" <%} else if (EmpPassport==null){%> onclick=" return message('P')" <%} %> >ADD</button>
						<button type="submit" class="btn btn-sm edit-btn" formaction="ProcAbroadEdit.htm" name="action" value="EDITCIR" Onclick="Edit(NOCProcAbroad)">EDIT</button>
						<!-- <button type="submit" class="btn btn-sm delete-btn" formaction="" name="action" value="DELETECIR" Onclick="Delete(NOCPassport)">DELETE</button> -->
						
					</div>
				</div>

			</form>
			
			<hr>
			
            <div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow For Proceeding Abroad</b></div>
		 	</div>
		 	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               			<tr>
	               		<%if(!CEO.equalsIgnoreCase(emp.getEmpNo()) ) {%>
	                		<td class="trup" style="background: #E8E46E;">
	                			User - <%=emp.getEmpName() %>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                	<%} %>
	               		<%if(GroupHeadName!=null && !GHs.contains(emp.getEmpNo()) && !PandAs.contains(emp.getEmpNo()) ){ %>
	                		<td class="trup" style="background: #B39DDB;">
	                			Group Head - <%=emplist[0] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<%if(DivisionHeadName!=null && !DHs.contains(emp.getEmpNo()) && !PandAs.contains(emp.getEmpNo()) ){ %>
	                		<td class="trup" style="background: #90CAF9;">
	                			Division Head - <%=emplist[1] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<%if(DGMEmpName!=null && !DGMs.contains(emp.getEmpNo()) && !PandAs.contains(emp.getEmpNo()) && !CEO.contains(emp.getEmpNo()) ){ %>
	                		<td class="trup" style="background: #FBC7F7;">
	                			DGM -  <%=emplist[2] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		
	               		<% if(!SOs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %>
	               			<td class="trup" style="background: #F99B7D;" >
	                			SO- <%=emplist[3] %>
	                		</td>
	                		
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		
	               		<% if(PandAEmpName!=null  && !PandAs.contains(emp.getEmpNo()) && !CEO.contains(emp.getEmpNo()) ){ %>
	                		<td class="trup" style="background: #BCAAA3;" >
	                			P&A - <%=emplist[4] %>
	                		</td>
	                		
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<% if(CeoName!=null ){ %>
	                		<td class="trup" style="background: #4DB6AC;" >
	                			CEO - <%=emplist[5] %>
	                		</td>
	             
	               		<%} %>
	               	</tr>		   
		                	
	              </table>			             
			  </div>


		</div>

	</div>
<!-- </div> -->



			       
<script type="text/javascript">
	
function Edit(NOCProcAbroad)
{
	var fields = $("input[name='ProcAbrId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One  ");
        event.preventDefault();
		return false;
	}
	return true;
	
}
	   	   
	   

function Delete(NOCProcAbroad){ 
	
	var fields = $("input[name='ProcAbrId']").serializeArray();

	if (fields.length === 0){
		alert("Please Select Atleast One  ");
		event.preventDefault();
		return false;
	}
	
	/* var cnf = confirm("Are You Sure To Delete!");
    if(cnf){
		
		document.getElementById("NOCPassport").submit();
		return true;

	}else{
		
		event.preventDefault();
		return false;
	} */
}

function message(action){
	
	if(action==='E'){
	alert("Approval of Permanent and Residential address is mandatory before applying NOC for Proceeding Abroad");
	event.preventDefault();
	return false;
}
	else if(action==='P'){
		alert("Passport Details Are Not Available.Please contact Admin");
		event.preventDefault();
		return false;
	
	}
}

</script>

<script type="text/javascript">
$("#table").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});


</script>

</body>
</html>