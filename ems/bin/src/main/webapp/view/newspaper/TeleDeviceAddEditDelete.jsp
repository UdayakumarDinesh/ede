<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
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
<%
List<Object[]> DeviceList=(List<Object[]>)request.getAttribute("DeviceList");
List<Object[]> TeleDeviceList=(List<Object[]>)request.getAttribute("TeleDeviceList");
Object[] TeleDeviceEditDetails=(Object[])request.getAttribute("TeleDeviceEditDetails");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Telephone Devices</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TelephoneDashBoard.htm">Telephone</a></li>
						<li class="breadcrumb-item active " aria-current="page">Telephone Devices</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		<div align="center">
			<%String ses=(String)request.getParameter("result"); 
			String ses1=(String)request.getParameter("resultfail");
			if(ses1!=null){ %>
				<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
					<%=ses1 %>
				</div>
				
			<%}if(ses!=null){ %>
				
				<div class="alert alert-success" role="alert" style="margin-top: 5px;">
					<%=ses %>
				</div>
			<%} %>
		</div>
	
 
 	<div class="page card dashboard-card">
			
		<div class="card-body" > 
 <!-- List Form -->
 			<div class="row">
	     		<div class="col-md-8">
			
					<table class="table table-hover table-striped  table-condensed  table-bordered  " id="" >
	                	<thead> 
	                    	<tr>            
	                        	<th>Device Type</th>
	                            <th>Device No.</th>
	                            <th>Edit & Delete</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                    	<%if(TeleDeviceList!=null&&TeleDeviceList.size()>0){ 
			                	for(Object ls[]:TeleDeviceList){%>
	                            	<tr>
	                                	<td style="text-align:center;"><%=ls[1]%></td>
	                                	<td style="text-align:center;"><%=ls[2]%></td>
	                                	<td style="text-align:center;">
	                                 	<form class="lv-action" action="TelephoneDeviceList.htm" method="post">
	                                		<input type="hidden" value="<%=ls[0]%>" name="TeleUsersId">
	                                		<button type="submit" class="btn btn-sm" name="EditDevice"  value="EditDevice" data-toggle="tooltip" data-placement="top"  data-original-title="Update">
	                                		    <i class="fa-solid fa-pen-to-square" style="color: #FF7800;"></i>
	                                		</button>
		                                 <!-- 	<button class="delete    btn-danger" type="submit" name="DeleteDevice"  value="DeleteDevice"> 
		                                		<i class="fa fa-2x  fa-trash-o" aria-hidden="true"></i>
		                                 	</button> -->
		                                 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	                                	</form>
	                                	</td>
	                              	</tr>
	                             <%}}else{%>
	                             <tr>
		                             <td colspan="3" style="text-align: center;">
		                            	No Device Present Please Add The Device First
		                             </td>
	                             </tr>
	                             <%}%>
	                    </tbody> 
					</table>
	                     
	                 
	                <div class="row" align="center">
	                	<div class="col-md-12" >
	            		<form action="TelephoneList.htm" method="post"  >
	            	    	<button   type="button" onclick="showhide()" name="action" class="btn btn-primary btn-sm">ADD</button>
	            	        <button  type="submit"  class="btn btn-info btn-sm">Back</button>
	            	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
						</div>
	            	</div>
	                  
				</div>
           
 <!-- List Form -->

<!-- Add Form -->
				<div class="col-md-4">
					<div class="card" id="newpost" style="display: none">
						<div class="card-body">

							<form action="TelephoneDeviceSave.htm" method="post">
								<div class="panel-heading">
									<span class="" style="font-size: 20px;">Add Device</span>
								</div>
								<div class="panel-body">
									<div class="form-group">
										<label>Select Device</label> <select name="DeviceId"
											class="form-control input-sm" required="required">
											<%
											if (DeviceList != null && DeviceList.size() > 0) {
												for (Object ls[] : DeviceList) {
											%>
											<option value="<%=ls[0]%>"><%=ls[1]%></option>
											<%
											}
											}
											%>
										</select>
									</div>
									<div class="form-group">
										<label>Enter Number</label> <input type="text"
											class="form-control input-sm" name="DeviceNo"
											required="required" maxlength="10">
									</div>

								</div>
								<div class="panel-footer">
									<button class="btn btn-success btn-sm btn-block" >Submit</button>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
					</div>
				</div>
				<!-- //add form  -->
		

<!-- Edit Form -->
<%if(TeleDeviceEditDetails!=null){ %>
		<div  id="editTime" class="col-md-4">
				<div class="card">
		
		<div class="card-body">
		
		<form action="TelephoneDeviceEdit.htm" method="post">
		    
		    <div class="panel-heading">
				<span class="" style="font-size: 20px;">Edit Device</span>
			</div>
			<div class="panel-body">
			
				<div class="form-group">
					<label>Device</label>
					<input type="text" value="<%=TeleDeviceEditDetails[1]%>" class="form-control input-sm" readonly="readonly">
				</div>
				<div class="form-group">
					<label>Enter Number</label>	
					<input type="text"   value="<%=TeleDeviceEditDetails[2]%>"  class="form-control input-sm" name="DeviceNo" required="required" maxlength="10">
				</div>
				
			
			</div>
			<div class="panel-footer">
				<input type="hidden" name="TeleUsersId" value="<%=TeleDeviceEditDetails[0]%>" >
				<button type="submit" class="btn btn-success btn-sm btn-block" >Update</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
		</div>	
	</div>
	</div>
	<%}%>
	<!-- //Edit Form -->

 </div>

</div>
</div>

 <script>  
 function showhide() 
   {  
     var div = document.getElementById("newpost");  
    
        $("#editTime").hide();
     
       if (div.style.display !== "none")
            {  
            div.style.display = "none";  
            }  
       else
            {  
            div.style.display = "block";  
 
            }  
   }
 </script>
</body>
</html>

