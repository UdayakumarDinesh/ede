<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%> 
<%@page import="java.util.List,java.util.ArrayList,java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
table{
	align: left;
	width: 100% !important;
	height:100% !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	margin-left:10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
	
}

.text-blue
{
	color: blue;
	font-weight:500px;
	font-size: 16px;
}

body {
  
   overflow-x: hidden; 
  /* overflow-y: hidden;  */
}
</style>
</head>
<body>
<%
	String LabLogo=(String) request.getAttribute("LabLogo");
	Object[] emp = (Object[])request.getAttribute("EmpData");
	String empNo = (String)session.getAttribute("EmpNo");
	String CEO = (String)request.getAttribute("CEOEmpNo");
	Object[] obj=(Object[])request.getAttribute("NOCHigherEducationDetails");
	List<Object[]> RemarksHistory = (List<Object[]>)request.getAttribute("HigherEducationRemarks");
	String isApproval = (String)request.getAttribute("isApproval");
	
	 List<Object[]> approveddata=(List<Object[]>)request.getAttribute("HigherEducationApprovalData");
	 String CEOempno=(String)request.getAttribute("CEOempno");
	 List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	 
	 Object[] empData=(Object[])request.getAttribute("EmpData");
	
	
	 List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
	 
	 List<String> RecommendStatus = Arrays.asList("VGI","VDI","VDG","VSO");
	int slno=0;
	
%>


<div class="card-header page-top "> 
		<div class="row">
			<div class="col-md-4">
				<h5>Higher Education Preview</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
							<li class="breadcrumb-item "><a href="NocApproval.htm">NOC Approval</a></li>
					<li class="breadcrumb-item active" aria-current="page">
						Preview</li>
						<%}
						else{%>
						<li class="breadcrumb-item "><a href="HigherEducation.htm">Higher Education List</a></li>
					<li class="breadcrumb-item active" aria-current="page"> Higher Education Preview
						
						<%} %>
						
				</ol>
			</div>

		</div>
	

</div>

  <div class="page card dashboard-card" > 
 <!--  <div class="card-body" align="center"> -->
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

         
			<div class="card-body" align="center" >
				<div class="card" style="padding-top: 0px; margin-top: -15px; width: 75%;">
					<form action="" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="card-body main-card" style="padding-top: 0px; margin-top: -15px;" align="center">
						
                          <div style="width: 10%; height: 75px; border: 0; display: inline-block;margin:2% 0 10px -90%;"><img style="width: 80px; height: 90px; margin: 5px;" align="left" src="data:image/png;base64,<%=LabLogo%>"></div>									
                          <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-6%;margin-left:5%;"><h4 style="font-size:23px;">NOC FOR HIGHER EDUCATION</h4></div>
						 
							<table style="border: 0px;margin-top:0px; width: 100%;">
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Name and Designation</td>
									<td colspan="2" style="color: blue;"><%=obj[2] %>  -  <%=obj[3] %>
									</td>
									
								</tr>
								
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Emp.No.</td> 
									
									<td colspan="2" style="color: blue;"><%=obj[0] %></td>
								</tr>
								
								<tr>
									<td style="border-bottom: 0;width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: center">Level in the Pay Matrix</td>
									<td style="width:20%;text-align: center">Grade</td>
									<td style="width:20%;text-align: center">Basic Pay</td>
									
								
								</tr>
								<tr>
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[4] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[6] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[5] %></td>
								
								   
								</tr>
								
								<tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td>Educational Qualification</td>
								   <td colspan="2" style="color: blue;"><%=obj[7] %></td>
								 </tr>
								 
								<tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td >Institution Type</td>
								   <td colspan="2" style="color: blue;"><%=obj[8] %></td>
								 </tr>
								  
								  
								 <tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td >Institution Name</td>
								   <td colspan="2" style="color: blue;"><%=obj[9] %></td>
								 </tr>
								  
								<tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td >Academic Year</td>
								   <td colspan="2" style="color: blue;"><%=obj[10] %></td>
								 </tr>
								 
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Name of Course</td>
								 	<td colspan="2" style="color: blue;"><%=obj[11] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Specialization</td>
								 	<td colspan="2" style="color: blue;"><%=obj[12] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Education Type</td>
								 	<td colspan="2" style="color: blue;"><%=obj[13] %></td>
								</tr> 
								
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Essential Education Qualification</td>
								 	<td colspan="2"style="color: blue;"><%=obj[14] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td colspan="3" style="width:20%;text-align: left">Declaration:
									
									<br>
									
									
									a)&nbsp; All expenses for the course shall be borne by me.<br>
									b)&nbsp;  No official / special leave / flexible working hours will use for attending classes
									
									    or for appearing in examinations / Projects etc.<br>
									
									c)&nbsp;  I will not refuse any official duty / outstation duty during the period.<br>
									
									d)&nbsp;  Pursuing the course will not hamper date - to - day official activities.<br>
									
									e)&nbsp;  Publication of research papers ,if any will be with the prior approval of CEO.<br>
									
									f)&nbsp;&nbsp; I will not utilize Society resources for research purposes.<br>
									
									g)&nbsp; I will obtain prior permission for visiting abroad for course work.<br>
									
									
									</td>
								 	
								</tr> 
								 
						</table>
						</div>	
					
					 <% if(obj[21]!=null) {%><div   style="margin-left:500px !important;">Forwarded On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[21].toString().substring(0, 10)) +" "+obj[21].toString().substring(11,19) %></span> </div>
	                    
	                  <%} %>
					
					
					   <% if(RemarksHistory.size()>1){ %>
				  
				  <div class="col-md-5" align="left" style="margin-left:-30rem;margin-top:2rem; padding:0px;border: 1px solid black;border-radius: 5px;">
				<table style="margin: 3px;padding: 0px;">
					<tr>
						<td style="border:none;padding: 0px">
							<h5 style="text-decoration: underline;color:red;">Remarks :</h5> 
						</td>											
					</tr>
					<% for(Object[] rh:RemarksHistory) {%>
					<tr>
					    <% if(rh[1]!=null){ %>
						<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
						    	<span style="font-size:16px;"><%=rh[2] %> :-</span>&nbsp;&nbsp;  
						    	<span style="border:none;" class="text-blue" >	<% if (!rh[1].toString().equals("")){%><%=rh[1] %><%} else{ %>--<%} %></span>
						  </td>
						 <%}%>
					</tr>
					<%} %>
				</table>
					
			</div>
			<%} %>
				
				
				       <% int count=1;
	 
	 for(Object[] ad :approveddata) {
		 
		if(count==1 && RecommendStatus.contains(ad[8].toString())){%>
				 <div align="left" style="margin-left:600px !important;">Recommended By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
	              <div  align="left" style="margin-left:600px !important;">Recommended On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
	              
	              <br>
			<%} else if(RecommendStatus.contains(ad[8].toString())){%>
		
		<div align="left" style="margin-left:25px !important;">Recommended By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
	    <div align="left" style="margin-left:25px !important;">Recommended On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
	    <br>
	 
	   <%}
			else if(ad[8].toString().equalsIgnoreCase("VPA")){%>
		
				<div align="left" style="margin-left:25px !important;">Verified By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
			    <div align="left" style="margin-left:25px !important;">Verified On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
		
		
	<% } 
		
			else if(ad[8].toString().equalsIgnoreCase("APR")){%>
			
		
		<div align="center" style="margin-left:0px;text-align:center;"> 
		                         <span style="font-weight: 600; font-size: 16px;">APPROVED</span><br><br>
						        <span style="font-weight: 500; font-size: 14px;">Approved By:&nbsp;<span class="text-blue" ><%=ad[2] %></span></span><br>
								<span style="font-weight: 400; font-size: 14px;">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %></span></span><br>
		</div>
		
		 <%}
		
			else if(ad[8].toString().equalsIgnoreCase("DPR")){%>
		 
		<br>
		<div align="center" style="margin-left:0px;text-align:center;"> 
		                        <span style="font-weight: 600; font-size: 16px;">DISAPPROVED</span><br><br><br>
						        <span style="font-weight: 500; font-size: 14px;">DisApproved By:&nbsp;<span class="text-blue" ><%=ad[2] %></span></span><br>
								<span style="font-weight: 400; font-size: 14px;">DisApproved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %></span></span><br>
		</div> 
		
	<%}count++;} %>
	
	 	
					<% if(obj[18]!=null && toUserStatus.contains(obj[18].toString())) { %>
	  	      <div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;">
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100"   name="remarks"  maxlength="500"></textarea>
				  </div>
                <button type="submit" class="btn btn-sm submit-btn"  name="Action" value="A"  formaction="NOCHigherEducationForward.htm"  onclick="return confirm('Are You Sure To Submit?');" >Forward</button>
                
				</div>
		<%} %>	
						
						 
			  <% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
			 
				 <div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;"> 
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100" id="remarksarea" name="remarks" maxlength="500"></textarea>
				  </div>
				  
				   <% if(CEOempno.toString().equals(empData[0].toString())){ %>
				  
				  
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCHigherEducationForward.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Approve?');" >
							Approve
						</button>
						
						<button type="submit" class="btn btn-sm  update-btn" id="finalSubmission"  formaction="NOCHigherEducationForward.htm" name="Action" value="D" onclick="return RemarkMandatory();" >
							Disapprove
						</button>
						
					<% } else if (PandAs.contains(empData[0].toString())){ %>
					
				     <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCHigherEducationForward.htm" name="Action" value="A"  onclick="return confirm('Are You Sure To Verify?');"  >
							Verify
						</button>
						
					<%}else{%>
						
                           <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCHigherEducationForward.htm" name="Action" value="A"  onclick="return confirm('Are You Sure To Recommend?');"  >
								Recommend
							</button>
						
					<%} %>
				  
				  <button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="NOCHigherEducationForward.htm" name="Action" value="R" onclick="return validateTextBox();" >
							 Return
						</button>
						
					 </div> 
				<%} %>
				
					 <input type="hidden" name="EducationId" value="<%=obj[16]%>"> 
					 
					</form>
				</div>
			</div>
	 	</div>
<!--  </div>  -->

<script>

function validateTextBox() {
    if (document.getElementById("remarksarea").value.trim() != "") {
    	return confirm('Are You Sure To Return?');
    	
    } else {
        alert("Please enter Remarks To Return");
        return false;
    }
    
}

function RemarkMandatory(){
	 if (document.getElementById("remarksarea").value.trim() != "") {
	    	return confirm('Are You Sure To disapproval?');
	    	
	    } else {
	        alert("Please enter Remarks To DisApprove");
	        return false;
	    }
	
	
}

</script>

</body>
</html>