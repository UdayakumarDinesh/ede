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
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 
<style type="text/css">

/* .table thead th {
    color: white;
   
    text-align: center;
    padding-bottom: 0.1rem !important;
    padding-top: 0.1rem !important;
} */
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
	String LoginType = (String)session.getAttribute("LoginType");
	
    Object[] CeoName = (Object[])request.getAttribute("CeoName");
    Object[] PandAEmpName = (Object[])request.getAttribute("PandAEmpName");
	Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
	Object[] DivisionHeadName = (Object[])request.getAttribute("DivisionHeadName");
	Object[] GroupHeadName = (Object[])request.getAttribute("GroupHeadName");
	Object[] EmpApprFlow = (Object[])request.getAttribute("EmpApprFlow");
	
	String CEO = (String)request.getAttribute("CEOEmpNos");
	List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
	List<String> DHs = (List<String>)request.getAttribute("DivisionHeadEmpNos");
	List<String> GHs = (List<String>)request.getAttribute("GroupHeadEmpNos");
	
/* 	Employee emp=(Employee)request.getAttribute("EmployeeD"); */
	Object[] empData=(Object[])request.getAttribute("EmpData");
	List<Object[]> mobile =(List<Object[]>)request.getAttribute("MobileDetails");
	List<Object[]> home =(List<Object[]>)request.getAttribute("HometownDetails");
	
	
	List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
	
	List<String> HometownAllowedEmpNo = (List<String>)request.getAttribute("HometownAllowedEmpNo");
	int size = home.size();
	
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-7">
				<h5>Hometown & Mobile List<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-5" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Hometown & Mobile</li>
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
			<h5>Hometown List</h5>
			  <hr>
				<form action="HomeTownAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable2"> 
					   <thead>
									
					    <tr align="center">
					       <th>Select</th>
					       <th>Hometown</th>
					       <th>Nearest Railway Station</th>	
					       <th>State</th>					       								
						   <th>Status</th>
						   <th>Action</th> 
					   </tr>
					   </thead>
					   <tbody>
                           <%if(home!=null){
					        for(Object[] obj : home){ %>
					   		<tr align="center">
					   		<td style="text-align: center;width:4%;">
								<%if(toUserStatus.contains(obj[11].toString()) ){ %>
									<input type="radio" name="hometownid" value="<%=obj[0]%>"> <%}else{ %>
									<input type="radio" name="hometownid" value="<%=obj[0]%>" disabled>
								<%} %>
							</td>
						    <td style="text-align: left;"><%if(obj[2]!=null){ %> <%=obj[2] %> <%} %> </td>
						    <td style="text-align: left;"><%if(obj[3]!=null){ %> <%=obj[3] %> <%} %> </td>
						    <td style="text-align: left;"><%if(obj[4]!=null){ %> <%=obj[4] %> <%} %> </td>
					   		<td style="">
													
								<%if(obj[8]!=null){%>
								  
								 	<%if(obj[5]!=null && obj[5].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="HometownTransStatus.htm" value="<%=obj[0] %>" name="hometownid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%}else if(obj[5]!=null && obj[5].toString().equalsIgnoreCase("E") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="HometownTransStatus.htm" value="<%=obj[0] %>" name="hometownid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: red; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Expired <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    		
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="HometownTransStatus.htm" value="<%=obj[0] %>" name="hometownid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[9] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[8] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
							</td>
					   		
					   		<td style="text-align: left;width:9%;">
								<button type="submit" class="btn btn-sm view-icon" formaction="HometownPreview.htm" name="hometownId" value="<%=obj[0] %>" data-toggle="tooltip" data-placement="top" title="Form For Hometown Change" style="font-weight: 600;" >
								   <i class="fa-solid fa-eye"></i>
								</button>
								<button type="submit" class="btn btn-sm" name="hometownId" value="<%=obj[0] %>" formaction="HometownFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
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
				   <div class="row">
						<div class="col-md-12 w-100" align="left">
							<br><b>NOTE :</b> <b style="color:red;">You Can Change Your Hometown Only Once In A Lifetime. </b>									
						</div>
					</div>
			        
			        <%if(HometownAllowedEmpNo!=null && HometownAllowedEmpNo.contains(empData[0].toString()) ) {%>
				    <div class="row text-center">
						<div class="col-md-12">
						     <%-- <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>"> --%>
							<%if(size<2){ %>
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADDHometown"   >ADD </button>
							<%} %>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDITHometown"  Onclick="EditPer(empForm)" >EDIT </button>
					    	<!-- <button type="submit" class="btn btn-sm delete-btn" name="Action" value="DELETE" Onclick="Delete(empForm)" >DELETE </button> -->
					    </div>
				   </div>
				   	<%} %>
				</form>
					<%--	<hr>
			<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow For Hometown</b></div>
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
	               		<%if(GroupHeadName!=null && !GHs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: #B39DDB;">
	                			Group Head - <%=GroupHeadName[1] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<%if(DivisionHeadName!=null && !DHs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: #90CAF9;">
	                			Division Head - <%=DivisionHeadName[1] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<%if(DGMEmpName!=null && !DGMs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString())  && !CEO.contains(empData[0].toString())){ %>
	                		<td class="trup" style="background: #FBC7F7;">
	                			DGM -  <%=DGMEmpName[1] %>
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<%if(PandAEmpName!=null  && !PandAs.contains(empData[0].toString()) && !CEO.contains(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: #BCAAA3;" >
	                			P&A - <%=PandAEmpName[1] %>
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
		                	
	                	<tr>
	                 	<%if( !CEO.equalsIgnoreCase(emp.getEmpNo()) ) {%>
	               		<td class="trdown" style="background: #E8E46E;" >	
				              <%=emp.getEmpName() %>
	                	</td>
	                	<%} %>
	                	<%if(GroupHeadName!=null && !GHs.contains(emp.getEmpNo()) && !PandAs.contains(emp.getEmpNo()) ){ %>
	                		<td class="trdown" style="background: #B39DDB;" >	
				                <%=GroupHeadName[1] %>
	                		</td>
	               		 <%} %>
	               		<%if(DivisionHeadName!=null && !DHs.contains(emp.getEmpNo()) && !PandAs.contains(emp.getEmpNo()) ){ %>
	                		<td class="trdown" style="background: #90CAF9;" >	
				                <%=DivisionHeadName[1] %>
	                		</td>
	               		 <%} %>
	               		<%if(DGMEmpName!=null && !DGMs.contains(emp.getEmpNo()) && !PandAs.contains(emp.getEmpNo()) ){ %>
	                		<td class="trdown" style="background: #FBC7F7;" >	
				                <%=DGMEmpName[1] %>
	                		</td>
	               		 <%} %>
	               		 <%if(PandAEmpName!=null && !PandAs.contains(emp.getEmpNo()) && !CEO.contains(emp.getEmpNo()) ){ %>
	               			<td class="trdown" style="background: #BCAAA3;" >	
			                	<%=PandAEmpName[1] %>
		           			</td>
		           		 <%} %>
		           		 <%if(CeoName!=null ){ %>
	               			<td class="trdown" style="background: #4DB6AC;" >	
			                	<%=CeoName[1] %>
		           			</td>
		           		 <%} %>
		            	</tr>              	
			           </table>			             
			       </div> --%>
       </div>
</div>			 
		 
		 
		 
		 
<div class="card">					
		<div class="card-body">
			<h5>Mobile Number List</h5>
			  <hr>
				<form action="MobileNumberAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable3"> 
					   <thead>
						    <tr align="center">
						        <th>Select</th>
								<th>Mobile Number</th>
								<th>Mobile.From</th>
								<th>Mobile.To</th>
								<!-- <th>Mobile No.</th>	 -->
								<th>Status</th>
								<th>Action</th> 
						   </tr>
					   </thead>
					   <tbody>
					        <%if(mobile!=null){
					        for(Object[] obj : mobile){ %>
					   		<tr align="center">
					   		<td style="text-align: center;width:4%;">
								<%if(toUserStatus.contains(obj[12].toString()) ){ %>
									<input type="radio" name="mobilenumberid" value="<%=obj[0]%>"> <%}else{ %>
									<input type="radio" name="mobilenumberid" value="<%=obj[0]%>" disabled>
								<%} %>
							</td>
						    <td style=""><%if(obj[2]!=null){ %> <%=obj[2] %> <%} %> </td>
							<td style=""><%if(obj[4]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[4]+"")%><%}else{%>--<%}%></td>	
							<td style=""><%if(obj[5]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[5]+"")%><%}else{%>--<%}%></td>	
					   		<td style="">
													
								<%if(obj[9]!=null){%>
								  
								 	<%if(obj[6]!=null && obj[6].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="MobileNumberTransStatus.htm" value="<%=obj[0] %>" name="mobilenumberid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%}else if(obj[6]!=null && obj[6].toString().equalsIgnoreCase("E") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="MobileNumberTransStatus.htm" value="<%=obj[0] %>" name="mobilenumberid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: red; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Expired <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    		
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="MobileNumberTransStatus.htm" value="<%=obj[0] %>" name="mobilenumberid"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[10] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[9] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
							</td>
					   		
					   		<td style="text-align: left;width:9%;">
								<button type="submit" class="btn btn-sm view-icon" formaction="MobileNumberPreview.htm" name="mobileNumberId" value="<%=obj[0] %>" data-toggle="tooltip" data-placement="top" title="Form For Mobile Number Change" style="font-weight: 600;" >
								   <i class="fa-solid fa-eye"></i>
								</button>
								<button type="submit" class="btn btn-sm" name="mobilenumberId" value="<%=obj[0] %>" formaction="MobileFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
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
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow For Mobile</b></div>
		 	</div>
		 	<%-- <div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               		<tr>
	               		<%if( !DGMs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ) {%>
	                		<td class="trup" style="background: #E8E46E;">
	                			User - <%=empData[1] %>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	                		<%} %>
	               		<%if(DGMEmpName!=null && !DGMs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString())  ){ %>
	                		<td class="trup" style="background: #FBC7F7;">
	                			DGM - <%=DGMEmpName[1] %>
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
	               	</tr>	 --%>
	               	
	               	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               		<tr>
	               		<%if( !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ) {%>
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
	                		<%if(PandAEmpName!=null && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: linear-gradient(to top, #42f2f5 10%, transparent 115%);">
	                			P & A <br> <%=EmpApprFlow[3]%>
	                		</td>	                		 
	                		<%} %>
	                		<%if(CeoName!=null && CEO.equalsIgnoreCase(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: linear-gradient(to top, #eb76c3 10%, transparent 115%);">
	                			CEO <br> <%=EmpApprFlow[4]%>
	                		</td> 
	                		<%} %>
	               		
	               	</tr>		   
		                	
	               <%-- 	<tr>
	               	<%if( !PandAs.contains(emp.getEmpNo()) && !CEO.equalsIgnoreCase(emp.getEmpNo()) ) {%>
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
		           		  <%if(CeoName!=null  && CEO.equalsIgnoreCase(emp.getEmpNo()) ){ %>
	               			<td class="trdown" style="background: #4DB6AC;" >	
			                	<%=CeoName[1] %>
		           			</td>
		           		 <%} %>
		            	</tr>  --%>            	
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

	var fields = $("input[name='mobilenumberid']").serializeArray();
	if (fields.length === 0) {
		alert("Please Select Atleast One Mobile Number");

		event.preventDefault();
		return false;
	}
	return true;
}

</script>
<script type="text/javascript">
function EditPer(myfrm) {

	var fieldsperadd = $("input[name='hometownid']").serializeArray();
 
	if (fieldsperadd.length === 0) {
		alert("Please Select Atleast One HomeTown");

		event.preventDefault();
		return false;
	}
	return true;
}

</script> 
</body>
</html>