<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%> 
<%@page import="java.util.List"%>
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
	List<Object[]> itimationList=(List<Object[]>)request.getAttribute("IntimationList"); 
	SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
	
	List<String> toUserStatus  = Arrays.asList("INI","RDG","RCE");
	
	 Object[] CeoName = (Object[])request.getAttribute("CeoName");
     Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
     
     String CEO = (String)request.getAttribute("CEOEmpNos");
 	 List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
 	Object[] empData=(Object[])request.getAttribute("EmpData");
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-8">
				<h5>Pass Intimation List<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
				<div class="col-md-4">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item active">Visitor pass List</li>
					</ol>
				</div>
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
		<div class="alert alert-success" role="alert" >
        	<%=ses %>
        </div>
    </div>
	<%} %>


<div class="page card dashboard-card">
   <div class="card-body"  >
  		

			<div class="card shadow-nohover">
					
				<div class="card-body">
					<div align="center"> 
						
						<a class="btn btn-sm btn-info" href="NewIntimation.htm"  >Generate Pass</a>
						
					</div>
					<hr>
					<form action="" method="POST" id="empForm">
				    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="table-responsive">			
							<table class="table table-bordered table-hover table-striped table-condensed" id="myTable">
								<thead>
									<tr>
										<th style="width: 5%;text-align: center;">SN</th>
				
										<th>Company Name</th>
										<th style="width:20%;">Dates</th>
										<!-- <th >Purpose</th> -->
										<th >Officer</th>
										<th>Status</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
								<%if(itimationList!=null){ %>
								<%int i=0;
								for(Object[] obj :itimationList){%>
									<tr>
									<td style="text-align: center;"><%-- <input type="radio" name="intimationId" value="<%=intimation[0]%>"> --%> <%=++i %></td>
								    <td><%=obj[3]%> </td>
								    <td style="text-align: center;"><%=sdf1.format(obj[5])%>&nbsp; to &nbsp;<%=sdf1.format(obj[6])%></td>
					              <%--   <td><%=obj[7]%> </td> --%>
					                 <td><%=obj[9]%> </td>
					                 <td>
					                 <%if(obj[13]!=null){%>
								  
								 	<%if(obj[11]!=null && obj[11].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="VpIntimationTransStatus.htm" value="<%=obj[0] %>" name="intimationid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="VpIntimationTransStatus.htm" value="<%=obj[0] %>" name="intimationid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[14] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[13] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
					                  </td>
					                  <td style="width: 10%;" align="center">
					                    <%					                    
					                    if(!obj[8].toString().contains("Not Applicable")) {
					                    if(toUserStatus.contains(obj[15].toString())){
					                    %>
					                    <input type="hidden" name="intimationId" value="<%=obj[0]%>">
					                       <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" data-toggle="tooltip" data-placement="top" title="Forward">
							                 <i class="fa-solid fa-forward" style="color: #125B50"></i>
						                 </button>
					                    <%} }%>
					                   <%--  <button type="submit" class="btn btn-sm" name="" value="<%=obj[0]%>" formaction="" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
								           <i style="color: #019267" class="fa-solid fa-download"></i>
								           </button> --%>
					                  </td>
									</tr>
								<%}%>
								<%} %>
								</tbody>
							</table>
							
						</div>
					 </form>
					 <hr>
			<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow For Visitor Pass ( When Special Permission Included )</b></div>
		 	</div>
		 	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               		<tr>
	               		<%if(!CEO.equalsIgnoreCase(empData[0].toString()) ) {%>
	                		<td class="trup" style="background: #E8E46E;">
	                			User - <%=empData[1]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                	<%} %>	               			               		
	               		<%if(DGMEmpName!=null && !DGMs.contains(empData[0].toString()) && !CEO.contains(empData[0].toString())){ %>
	                		<td class="trup" style="background: #FBC7F7;">
	                			DGM -  <%=DGMEmpName[1] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>	               		
	               		<%if(CeoName!=null ){ %>
	                		<td class="trup" style="background: #4DB6AC;" >
	                			CEO - <%=CeoName[1] %>
	                		</td>
	             
	               		<%} %>
	               	</tr>	
					</div>
				</div>
	   

   </div>
</div>

 

<script>


	   

</script>

</body>
</html>