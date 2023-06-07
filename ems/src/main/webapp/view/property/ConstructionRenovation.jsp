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
   List<Object[]> ConstrRenovDetails = (List<Object[]>)request.getAttribute("ConstrRenovDetails");
 
    Object[] CeoName = (Object[])request.getAttribute("CeoName");
	Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
	Object[] PandAEmpName = (Object[])request.getAttribute("PandAEmpName");
	
	String CEO = (String)request.getAttribute("CEOEmpNos");
	List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	List<String> SOs = (List<String>)request.getAttribute("SOEmpNos");
	List<String> DGMs = (List<String>)request.getAttribute("DGMEmpNos");
	Object[] EmpApprFlow = (Object[])request.getAttribute("EmpApprFlow");
	
	Object[] empData=(Object[])request.getAttribute("EmpData");
	
	List<String> toUserStatus  = Arrays.asList("INI","RDG","RPA","RSO","RCE");
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-7">
				<h5>Construction List<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-5" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PropertyDashBoard.htm">Property</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Construction</li>
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
	<div class="card">					
		<div class="card-body">
				<form action="ConstructionAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
					   <thead>
									
					    <tr align="center">
					       <th>Select</th>
					       <th>Letter No</th>
					       <th>Transaction </th>
					       <th>Estimated Cost</th>					      				       								
					       <th>Supervised By</th>					      				       								
					       <th>Completed By</th>					      				       								
						   <th>Status</th>
						   <th>Action</th> 
					   </tr>
					   </thead>
					   <tbody>
                           <%if(ConstrRenovDetails!=null){
					        for(Object[] obj : ConstrRenovDetails){ %>
					   		<tr align="center">
					   		<td style="text-align: center;width:4%;">
								<%if(toUserStatus.contains(obj[14].toString()) ){ %>
									<input type="radio" name="constructionid" value="<%=obj[0]%>"> <%}else{ %>
									<input type="radio" name="constructionid" value="<%=obj[0]%>" disabled>
								<%} %>
							</td>
						    <td style="text-align: center;width:8%;"><%if(obj[3]!=null){%><%=obj[3]%><%}else{ %>-<%} %></td>
						    <td style="text-align: left;width:10%;">
						    <%if(obj[5]!=null && obj[5].toString().equalsIgnoreCase("C")){ %>Construction
						    <%} else if(obj[5]!=null && obj[5].toString().equalsIgnoreCase("A")){%>Addition
						    <%}else{ %>Renovation<%} %>
						    </td>
						    <td style="text-align: right;width:10%;"><%if(obj[6]!=null){ %> <%=obj[6] %> <%} %> </td>
						    <td style="text-align: left;width:10%;"><%if(obj[7]!=null){ %> <%=obj[7] %> <%} %> </td>
						    <td style="text-align: center;width:11%;"><%if(obj[8]!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(obj[8]+"") %> <%} %> </td>
					   		<td style="width:17%;">
													
								<%if(obj[12]!=null){%>
								  
								 	<%if(obj[9]!=null && obj[9].toString().equalsIgnoreCase("A") ){ %>
							    		<button type="submit" class="btn btn-sm btn-link w-100" formaction="ConstructionTransStatus.htm" value="<%=obj[0] %>" name="constructionId"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: green; font-weight: 600;" formtarget="_blank">
								    		&nbsp; Approved <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%}else{ %>
								    	<button type="submit" class="btn btn-sm btn-link w-100" formaction="ConstructionTransStatus.htm" value="<%=obj[0] %>" name="constructionId"  data-toggle="tooltip" data-placement="top" title="Transaction History" style=" color: <%=obj[13] %>; font-weight: 600;" formtarget="_blank">
								    		&nbsp; <%=obj[12] %> <i class="fa-solid fa-arrow-up-right-from-square" style="float: right;" ></i>
								    	</button>
							    	<%} %>  
								 
								<%} %>
							</td>
					   		
					   		<td style="text-align: left;width:10%;">
								<button type="submit" class="btn btn-sm view-icon" formaction="ConstructionPreview.htm" name="constructionId" value="<%=obj[0] %>" data-toggle="tooltip" data-placement="top" title="Application Form For Permission" style="font-weight: 600;" >
								   <i class="fa-solid fa-eye"></i>
								</button>
								<button type="submit" class="btn btn-sm" name="constructionId" value="<%=obj[0] %>" formaction="MovablePropFormDownload.htm" formtarget="blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
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
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD">ADD </button>		
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT" Onclick="EditConstruction(empForm)" >EDIT </button>
					    </div>
				   </div>
				  
				</form>

			<hr>
			<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow For Construction / Addition / Renovation of House</b></div>
		 	</div>

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
	               		<%if(DGMEmpName!=null && !DGMs.contains(empData[0].toString()) && !SOs.contains(empData[0].toString()) 
	               		&& !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %>                		
	               			<td class="trup"  style="background: linear-gradient(to top, #eb76c3 10%, transparent 115%);">
	                			DGM <br> <%=EmpApprFlow[2]%>
	                		</td>
	                		
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		 <%} %>
	                		  <%if( !SOs.contains(empData[0].toString()) && !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %> 
	                		 <td class="trup"  style="background: linear-gradient(to top, #42f2f5 10%, transparent 115%);" >
	                			SO <br> <%=EmpApprFlow[3]%>
	                		</td> 
	                		
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td> 
	                	  <%} %>
	                		<%if(PandAEmpName!=null && !PandAs.contains(empData[0].toString()) && !CEO.equalsIgnoreCase(empData[0].toString()) ){ %>
	                		<td class="trup" style="background: linear-gradient(to top, #ff9966 10%, transparent 115%);">
	                			P & A <br> <%=EmpApprFlow[5]%>
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>	                		 
	                		<%} %>
	                		<%if(CeoName!=null ){ %>
	                		<td class="trup" style="background: linear-gradient(to top, #4DB6AC 10%, transparent 115%);">
	                			CEO <br> <%=EmpApprFlow[6]%>
	                		</td> 
	                		<%} %>
	               		
	               	</tr>			
			         </table>			             
			   </div>
		   </div>
       </div>
     </div>
</div>		

<script type="text/javascript">
function EditConstruction(myfrm) {

	var fieldsimmprop = $("input[name='constructionid']").serializeArray();
 
	if (fieldsimmprop.length === 0) {
		alert("Please Select Atleast One Transaction");

		event.preventDefault();
		return false;
	}
	return true;
}

</script> 
</body>
</html>