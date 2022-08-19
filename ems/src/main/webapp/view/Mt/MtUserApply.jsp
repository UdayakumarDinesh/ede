<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="java.time.LocalDate , java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="com.vts.ems.Mt.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MT Apply</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
List<Object[]> dutylist = (List<Object[]>)request.getAttribute("typedutylist"); 
List<Object[]> projectlist = (List<Object[]>)request.getAttribute("projectlist");
MtUserApply apply=(MtUserApply)request.getAttribute("editdata");
String username= (String)request.getAttribute("empname");
String groupname=(String)request.getAttribute("groupcode");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Request Trip <small><%if(username!=null){%><%=username%><%}%></small> </h5>
			</div>
				<div class="col-md-6 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Request Trip</li>
					</ol>
				</div>
			</div>
</div>	

 <div class=" page card dashboard-card">
	
	
	<div class="card-body" >
	<div class="row">
	<div class="col-9">
		<div class="card" >					
			<div class="card-body">
				<form action="MtUserApplyAddEdit.htm" method="post" autocomplete="off" id="requesttripform">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				    <div class="form-group">
				        <div class="row">
				        	<div class="col-md-3">
						 	<label>Date of Start:<span class="mandatory">*</span></label>
							<div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="dos" name="Dtravel"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">						      
							    </label>                    
							</div>
						 </div>
						 
						 <div class="col-md-3">
						 	<label>Start Time (in Hrs):<span class="mandatory">*</span></label>
							<div class=" input-group">
							     <input  class="form-control form-control-sm" id="stime" placeholder="Start Time"   name="Dtime" <%if(apply!=null&&apply.getStartTime()!=null){%>value="<%=apply.getStartTime()%>"<%}%>  maxlength="250" required="required">                     
							</div>
						 </div>
						 
						 <div class="col-md-3">
						 	<label>Date of End:<span class="mandatory">*</span></label>
							<div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="doe" name="Etravel"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">  
							    </label>                    
							</div>
						 </div>
						 
						 
						 <div class="col-md-3">
						 	<label>Return Time (in Hrs):<span class="mandatory">*</span></label>
							<div class=" input-group">
						        <input  class="form-control form-control-sm" <%if(apply!=null&&apply.getEndTime()!=null){%>value="<%=apply.getEndTime()%>"<%}%>    id="etime" name="Rtime"  maxlength="250" required="required">
						 	</div>
						 </div>
						 
						</div>
					</div>
					
					<div class="form-group">
				        <div class="row">
				        	<div class="col-md-3">					        	
					                <label>Source:<span class=" mandatory ">*</span></label>
					                <input type="text" <%if(apply!=null&&apply.getSource()!=null){%>value="<%=apply.getSource()%>"<%}%> name="Source" id="source" class=" form-control input-sm " maxlength="99"   placeholder="Source "  required="required">
							</div>
							
							<div class="col-md-3">					        	
					                <label>Destination:<span class=" mandatory ">*</span></label>
					                <input type="text" <%if(apply!=null&&apply.getDestination()!=null){%>value="<%=apply.getDestination()%>"<%}%> name="Destination" id="destination" class=" form-control input-sm " maxlength="99"   placeholder="Destination "  required="required">
							</div>
							
							<div class="col-md-3">
					                <label>Budget:<span class=" mandatory ">*</span></label>
					                <select name="Budget"  id="budget" class="form-control select2" required data-live-search="true" >
										   <option value="" disabled="disabled" selected="selected">Select</option>
										   <option value="0" <%if(apply!=null && 0 == apply.getProjectId()){%>selected="selected"<%}%>> General</option>
										<%if(projectlist!=null){ for(Object[] obj : projectlist){ %>
											<option value="<%=obj[0]%>" <%if(apply!=null && obj[0].toString().equals(String.valueOf(apply.getProjectId()))){%>selected="selected"<%}%> ><%=obj[1]%> </option>
										<%}}%>
					                </select>
			                </div>
			                
			                <div class="col-md-3">
					                <label>Type of Duty:<span class=" mandatory ">*</span></label>
					                <select name="TypeOfDuty" id="typeofduty" class="form-control select2" required data-live-search="true" >
											<option value="" disabled="disabled" selected="selected"> Select</option>
										<%if(dutylist!=null){for(Object[] obj: dutylist){ %>
											<option value="<%=obj[0]%>" <%if(apply!=null && obj[0].toString().equals(String.valueOf(apply.getDutyTypeId()))){%>selected="selected"<%}%>><%=obj[1]%></option>
										<%}}%>
					                </select>
			                </div>	
						</div>
					</div>
					
					<div class="form-group">
				        <div class="row">
				        	<div class="col-md-3">					        	
					                <label>Any Reason:<span class=" mandatory ">*</span></label>
					                <input type="text" <%if(apply!=null&&apply.getMtReason()!=null){%>value="<%=apply.getMtReason()%>"<%}%> name="Reason" class=" form-control input-sm " maxlength="99"   placeholder="Reason"  >
							</div>
							
						   <div class=" col-md-3">
					                <label>One way distance(Km):<span class=" mandatory ">*</span></label>
					                <input type="text" name="Distance"  id="distance" <%if(apply!=null&&apply.getOneWayDistance()!=0){%>value="<%=apply.getOneWayDistance()%>"<%}%> maxlength="5"  class=" form-control input-sm Distance" placeholder="Distance " required="required"  onblur="checknegative(this) ">
			              </div>
			              
			              <div class=" col-md-3">
					                <label>Internal Contact No:<span class=" mandatory ">*</span></label>
					                <input type="text" name="Contact" id="contact"  <%if(apply!=null&&apply.getContactNo()!=null){%>value="<%=apply.getContactNo()%>"<%}%> maxlength="4"  class=" form-control input-sm Distance" placeholder="Internal Contact " required="required" onblur="checknegative(this) ">
			              </div>
			              
			              <div class="col-md-3" >					        	
					                <label style="margin-left: -13.2px;">Traveler Name/Phone No:<span class=" mandatory ">*</span></label>
					                <input type="text" <%if(apply!=null&&apply.getUserRemarks()!=null){%>value="<%=apply.getUserRemarks()%>"<%}%> name="Remarks" id="remarks" class=" form-control input-sm " maxlength="99"   placeholder="Source "  required="required">
							</div>
						</div>
					</div>
					
					 <div class="row" >
					    	<div class="col-12" align="center"></br>
					    	   <input type="hidden" name="mtapplId" <%if(apply!=null&&apply.getMtApplId()!=0){%>value="<%=apply.getMtApplId()%>"<%}%>> 
							   <input type="hidden" name="ReqNo" <%if(apply!=null&&apply.getMtReqNo()!=null){%>value="<%=apply.getMtReqNo()%>"<%}%>>
							   <%--<input class="form-control input-sm"  name="PDemanding" value="<%=Empname%>" type="hidden"/>
							    <input  class="form-control"  name="Gcode" value="<%=GroupCode%>" type="hidden"/> --%>
					    	<button type="button" class="btn btn-sm submit-btn"  name="action" value="submit" onclick="CheckData()" >SUBMIT</button>
					 </div>
			    </div> 		
				</form>	
		   </div>
	    </div>
	    </div>
	   
		<div class="col-md-3" >
					<div class="row">
						<div class="card" style="background-repeat: no-repeat; background-size: cover; background-color: #e9ecef;">
							<div class="card-body text-black ">
								<div class="col-md-12" style="height: 152px;">
									<div align="center">
										<img src="view/images/note.jpg" style="border: 1px solid #ddd; border-radius: 50%; padding: 0px; width: 50px;">
									</div>
									<div style="font-size: small; font-style: italic; color: #f5210a;" align="center">
										<b>ALL * marked field are mandatory For Innova Crysta
											vehicles:</br> Reasons should be picked from the following set
										</b>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12" style="top: 0px;">
										<div class="text-black" align="center" style="font-weight: bold; font-style: italic; font-size: x-small; font-family:" Times NewRoman", Times, serif;">
											</br>  
											NOTE. Testing of HPM system at ATR, Chitradurga<br>
											NOTE. Collection of fuel / Periodic maintenance/ Tradeenquiry <br> 
											NOTE. (Collection of / Inspection &acceptance of) stores for project<br> 
											NOTE. Conveyance for (officer/member/chairman/Dy FA/ACDA/CDA) for attending
											(EB/PMRC/TPC/Technical discussion/Technical review) meetings<br>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>

 <%
 SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
List<Object[]> firstapply=(List<Object[]>)request.getAttribute("firstapply"); 
List<Object[]> secondapply=(List<Object[]>)request.getAttribute("secondapply");
List<Object[]> thirdapply=(List<Object[]>)request.getAttribute("thirdapply");
List<Object[]> fourthapply=(List<Object[]>)request.getAttribute("fourthapply");
List<Object[]> fifthpply=(List<Object[]>)request.getAttribute("fifthpply");%>
													
   <div class="row">
<div class="col-md-12"  style="margin-top: 10px;">

     
     <div class="col-md-3" style="margin-top: 20px;">
				    <button style="font-size:small;margin-left: 250px; background-color: #17a2b8; height: 35px;"  class="btn  btn-info btn-xs " data-toggle="modal" data-target="#statusFullForm">Click Me To See LIST OF REQUEST FOR  NEXT 5 DAYS</button>
				    </div>
				    <!-- //Status Full Form Button -->
				    <!-- Status Full Form Modal -->
				                             
				    <div class="modal fade" id="statusFullForm" role="dialog" >
           <div class="modal-dialog modal-lg">
            <div class="modal-content">
             <div class="modal-header"><h5 class="modal-litle" >LIST OF REQUEST FOR  NEXT 5 DAYS</h5>
                 <button  type="button"  class="close" data-dismiss="modal">&times;</button>
                </div>
     
   <div class="modal-body">
             
         <div class="row">
              
          <div class="col-md-12" >
              <div class="row" style="margin-top: 15px;">
    
     <div class="col-md-12">
<%if(firstapply.size()==0&&secondapply.size()==0&&thirdapply.size()==0&&fourthapply.size()==0&&fifthpply.size()==0){%>
	<div align="center"><b class="badge badge-warning">No Request Found</b></div>
	<%}else{ 
	 
  if(firstapply!=null&&firstapply.size()!=0){
  
  %>	
	 <div align="center"><b>Requests for Date :</b><%=sdf.format(new Date()) %></div>
	    <div class="table-responsive">
	  <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1">
   	
    <thead>
    	
	             <tr style="color: #000080;">
	               	
	                 <th style="font-size: 12px;">Indentor</th>
	        	     <th style="font-size: 12px;">Date</th>
	        	     <th style="font-size: 12px;">Time</th>
	                 <th style="font-size: 12px;">Source</th>
	                 <th style="font-size: 12px;">Destination</th>
	                 <th style="font-size: 12px;">Nature</th>
	               	 <th style="font-size: 12px;">Specific Reason</th>
	                 <th style="font-size: 12px;">Traveler Name and Phone Number</th>
	               </tr>
	         </thead>
	  <tbody>
		<%for(Object[] alist:firstapply){%>
			
	    <tr>

	     	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[0]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %> ><%=sdf.format(alist[1])%> To   <%=sdf.format(alist[11])%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[2]%>-<%=alist[3]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[4]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[5]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[6]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>  style="overflow-wrap: break-word; word-break: break-all; white-space: normal;"  >
	      	<%if(alist[7]!=null){%>
	      	<%=alist[7]%>
	      	<%}else{ %><span>No Reason</span><%} %>
	      	</td>
	      	 <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>   style="overflow-wrap: break-word; word-break: break-all; white-space: normal;" >
	      	<%if(alist[8]!=null){%>
	    	<%=alist[8]%>
	      	<%}else{ %><span>No Comment</span><%} %>
	      	</td>
	  </tr>
	 <%} %>
	 
</tbody>
	 </table>
	 </div>
	 <%} 
	if( secondapply!=null&&secondapply.size()!=0){%>	 
	 <div align="center"><b>Requests for Date :</b><%=sdf.format(new Date().getTime()+(24*60*60*1000)) %></div>
	    <div class="table-responsive">
	    <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable">
    <thead>
    	
	      <tr style="color: red;">
	               	
	                 <th style="font-size: 12px;">Indentor</th>
	        	     <th style="font-size: 12px;">Date</th>
	        	     <th style="font-size: 12px;">Time</th>
	                 <th style="font-size: 12px;">Source</th>
	                 <th style="font-size: 12px;">Destination</th>
	                 <th style="font-size: 12px;">Nature</th>
	                 <th style="font-size: 12px;">Reason</th>
	                 <th style="font-size: 12px;">User Comments</th>		
	      </tr>
	</thead>
	         <tbody>
		<%for(Object[] alist:secondapply){%>
		<tr>
	      	
	      	
	     	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[0]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %> ><%=sdf.format(alist[1])%> To   <%=sdf.format(alist[11])%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[2]%>-<%=alist[3]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[4]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[5]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[6]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>  style="overflow-wrap: break-word; word-break: break-all; white-space: normal;"  >
	      	<%if(alist[7]!=null){%>
	      	<%=alist[7]%>
	      	<%}else{ %><span>NO REASON</span><%} %>
	      	</td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>   style="overflow-wrap: break-word; word-break: break-all; white-space: normal;" >
	      	<%if(alist[8]!=null){%>
	    	<%=alist[8]%>
	      	<%}else{ %><span>NO COMMENT</span><%} %>
	      	</td>
	 </tr>
	 <%}%>
	 </tbody>
	 </table>
	 </div>
<%} 
	
	
	if( thirdapply!=null&&thirdapply.size()!=0){%>	 
	 
	  <div align="center"><b>Requests for Date :</b><%=sdf.format(new Date().getTime()+(2*24*60*60*1000)) %></div>
	   <div class="table-responsive">
	   <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable">
    <thead>
    	
	             <tr>
	               	  <th style="font-size: 12px;">Indentor</th>
	        	      <th style="font-size: 12px;">Date</th>
	        	      <th style="font-size: 12px;">Time</th>
	                  <th style="font-size: 12px;">Source</th>
	                  <th style="font-size: 12px;">Destination</th>
	                  <th style="font-size: 12px;">Nature</th>
	                  <th style="font-size: 12px;">Reason</th>
	                  <th style="font-size: 12px;">User Comments</th>
	            </tr>
	         </thead>
	         <tbody>
		<%for(Object[] alist:thirdapply){%>
		<tr>
	      	
	   
	     	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[0]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %> ><%=sdf.format(alist[1])%> To   <%=sdf.format(alist[11])%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[2]%>-<%=alist[3]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[4]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[5]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"    <%} %>><%=alist[6]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>  style="overflow-wrap: break-word; word-break: break-all; white-space: normal;"  >
	      	<%if(alist[7]!=null){%>
	      	<%=alist[7]%>
	      	<%}else{ %><span>No Reason</span><%} %>
	      	</td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>   style="overflow-wrap: break-word; word-break: break-all; white-space: normal;" >
	      	<%if(alist[8]!=null){%>
	    	<%=alist[8]%>
	      	<%}else{ %><span>No Comment</span><%} %>
	      	</td>
			
	 </tr>
	 <%} %>
	 <tbody>
	 </table>
	 </div>
	 <%} 
	if( fourthapply!=null&&fourthapply.size()!=0){%>	 
	 
	  <div align="center"><b>Requests for Date :</b><%=sdf.format(new Date().getTime()+(3*24*60*60*1000)) %></div>
	    <div class="table-responsive">
	  <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable">
    <thead>
    	
	             <tr>
	                  <th style="font-size: 12px;">Indentor</th>
	        	      <th style="font-size: 12px;">Date</th>
	        	      <th style="font-size: 12px;">Time</th>
	                  <th style="font-size: 12px;">Source</th>
	                  <th style="font-size: 12px;">Destination</th>
	                  <th style="font-size: 12px;">Nature</th>
	               	  <th style="font-size: 12px;">Reason</th>
	                  <th style="font-size: 12px;">User Comments</th>
	               </tr>
	         </thead>
		<tbody>
		<%for(Object[] alist:fourthapply){%>
			
	  <tr>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[0]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %> ><%=sdf.format(alist[1])%> To   <%=sdf.format(alist[11])%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[2]%>-<%=alist[3]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[4]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[5]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[6]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>  style="overflow-wrap: break-word; word-break: break-all; white-space: normal;"  >
	      	<%if(alist[7]!=null){%>
	      	<%=alist[7]%>
	      	<%}else{ %><span>No Reason</span><%} %>
	      	</td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>   style="overflow-wrap: break-word; word-break: break-all; white-space: normal;" >
	      	<%if(alist[8]!=null){%>
	    	<%=alist[8]%>
	      	<%}else{ %><span>No Comment</span><%} %>
	      	</td>
			
	 </tr>
	 <%} %>
	 <tbody>
	 </table>
	 </div>
	<% } if( fifthpply!=null&&fifthpply.size()!=0){%>	 
	 <div align="center"><b>Requests for Date :</b><%=sdf.format(new Date().getTime()+(4*24*60*60*1000)) %></div>
	   <div class="table-responsive">
	    <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable">
   	
    <thead>
    	
	             <tr>
	               	
	                 <th style="font-size: 12px;">Indentor</th>
	        	     <th style="font-size: 12px;">Date</th>
	        	     <th style="font-size: 12px;">Time</th>
	                 <th style="font-size: 12px;">Source</th>
	                 <th style="font-size: 12px;">Destination</th>
	                 <th style="font-size: 12px;">Nature</th>
	               	 <th style="font-size: 12px;">Reason</th>
	                 <th style="font-size: 12px;">User Comments</th>
	               </tr>
	         </thead>
		<tbody>
		<%for(Object[] alist: fifthpply){%>
		<tr>
	      	
	      	
	     	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[0]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %> ><%=sdf.format(alist[1])%> To   <%=sdf.format(alist[11])%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[2]%>-<%=alist[3]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[4]%></td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[5]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue;"   <%} %>><%=alist[6]%></td>
	        <td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>  style="overflow-wrap: break-word; word-break: break-all; white-space: normal;"  >
	      	<%if(alist[7]!=null){%>
	      	<%=alist[7]%>
	      	<%}else{ %><span>No Reason</span><%} %>
	      	</td>
	      	<td style="font-size: 12px;" <%if(sdf.format(alist[1]).equalsIgnoreCase(sdf.format(new Date()))){ %> style="color: blue; overflow-wrap: break-word; word-break: break-all; white-space: normal;"   <%} %>   style="overflow-wrap: break-word; word-break: break-all; white-space: normal;" >
	      	<%if(alist[8]!=null){%>
	    	<%=alist[8]%>
	      	<%}else{ %><span>No Comment</span><%} %>
	      	</td>
			
	 </tr>
	 <%} %>
	 <tbody>
	 </table>
	 </div>
	 <%} %>
	 <%} %>











 <div class="row">

<div class="col-md-12"  >

			<div align="center"><b> ALLOTED TRIP NEXT  DAYS</b></div>
			 
   
   
  
	  <%List<Object[]> reqlinkduty=(List<Object[]>)request.getAttribute("linkrequestlist"); %>
	 <%if(reqlinkduty!=null){ %>
	   <div class="table-responsive">
	   <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable1">
    <thead>
	             <tr>
	                
	                  <th style="font-size: 12px;">Indentor</th>
	        	      <th style="font-size: 12px;">Date</th>
	                  <th style="font-size: 12px;">Source</th>
	                  <th style="font-size: 12px;">Destination</th>
	                  <th style="font-size: 12px;">Time</th>
	                  <th style="font-size: 12px;">Vehicle Type</th>
	                  <th style="font-size: 12px;">Operater</th>
	                  <th style="font-size: 12px;">Nature</th>	
	                  <th style="font-size: 12px;">User</th>
	               			
	               </tr>
	         </thead>
	         <tbody>
		<%for(Object[] trip:reqlinkduty){%>
		<tr>
	      	 
	      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%=trip[0]%></td>
	      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%=trip[1]%>  To <%=trip[14]%></td>
	      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%=trip[2]%></td>
	      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%=trip[3]%></td>
	      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%=trip[4]%>-<%=trip[5]%></td>
	      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%if(trip[6]!=null){ %> <%=trip[6]%><%}else{ %>--<%} %></td>
	      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%=trip[7]%></td>
	        <td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%=trip[8]%></td>
	      	<td style="overflow-wrap: break-word; word-break: break-all; white-space: normal;font-size: 12px;"><%=trip[9]%></td>
		</tr>
		 <%}%>
	 
	 
	 
	 </tbody>
	 </table>
	 </div>
	  
	  <%}else{%>
	  <div align="center"><b class="badge badge-warning">No Trip Found</b></div>
	  <%}%>
	 </div>
	 
	 </div>
	  
</div>
</div>
</div> 
              </div><!--//row -->
             </div><!--//modal body -->
                 
             <div class="modal-footer">
               <button type="button" class="btn btn-danger" data-dismiss="modal">close</button> 
             </div>
            </div>
        </div>
     </div>
   </div>
</div>

    </div>    
</div>
</body>
<script type="text/javascript">
$('#dos').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	
	<%if(apply!=null&&apply.getDateOfTravel()!=null){%>
	"minDate" : new Date("<%=apply.getDateOfTravel()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#doe').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#dos').val(),
	<%if(apply!=null&&apply.getEndDateOfTravel()!=null){%>
	"startDate" :new Date("<%=apply.getEndDateOfTravel()%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$( "#dos" ).change(function() {
	
	$('#doe').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : $('#dos').val(), 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});

$(function() {
	   $('#stime,#etime,#stime1,#etime1').daterangepicker({
	            timePicker : true,
	            singleDatePicker:true,
	            timePicker24Hour : true,
	            timePickerIncrement : 1,
	            timePickerSeconds : false,
	            locale : {
	                format : 'HH:mm'
	            }
	        }).on('show.daterangepicker', function(ev, picker) {
	            picker.container.find(".calendar-table").hide();
	   });
	})

function checknegative(str) {
    if (parseFloat(document.getElementById(str.id).value) < 0) {
        document.getElementById(str.id).value = "";
        document.getElementById(str.id).focus();
        alert('Negative Values Not allowed');
        return false;
    }
}

setPatternFilter($(".Distance"), /^-?\d*$/);

function setPatternFilter(obj, pattern) {
	  setInputFilter(obj, function(value) { return pattern.test(value); });
	}

function setInputFilter(obj, inputFilter) {
	  obj.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
	    if (inputFilter(this.value)) {
	      this.oldValue = this.value;
	      this.oldSelectionStart = this.selectionStart;
	      this.oldSelectionEnd = this.selectionEnd;
	    } else if (this.hasOwnProperty("oldValue")) {
	      this.value = this.oldValue;
	      this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	    }
	  });
	}
	


	function CheckData(){
		
		var starttime  = $('#stime').val();
		var returntime = $('#etime').val();
		var source = $('#source').val();
		var destination = $('#destination').val();
		var budget = $('#budget').val();
		var typeofduty = $('#typeofduty').val();
		var distance = $('#distance').val();
		var contact = $('#contact').val();
		var remarks = $('#remarks').val();
		
		if(starttime=="00:00"){
			alert("Please Select Start Time!");
			return false;
		}else if(returntime=="00:00"){
			alert("Please Select Return Time!");
			return false;
		} else if (source==""||source==null ||source=="null"){
			alert("Please Enter the Source Place!");
			return false;
		}else if (destination==""||destination==null ||destination=="null"){
			alert("Please Enter the Destination Place!");
			return false;
		}else if (budget==""||budget==null ||budget=="null"){
			alert("Please Select Budget !");
			return false;
		}else if (typeofduty==""||typeofduty==null ||typeofduty=="null"){
			alert("Please Select Type of Duty !");
			return false;
		}else if (distance==""||distance==null ||distance=="null"){
			alert("Please Enter the Distance!");
			return false;
		}else if (contact==""||contact==null ||contact=="null"){
			alert("Please Enter Internal Contact No!");
			return false;
		}else if (remarks==""||remarks==null ||remarks=="null"){
			alert("Please Enter the Traveler Name/Phone No!");
			return false;
		}else{
			if(confirm("Are you sure to Submit!")){
				$('#requesttripform').submit();
				return true;
			}else{
				return false;
			}
		}
	}
	
	
</script>

</html>