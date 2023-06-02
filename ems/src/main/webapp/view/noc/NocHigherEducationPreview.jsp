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


body {
  
   overflow-x: hidden;
  overflow-y: hidden; 
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
	
	 String CEOempno=(String)request.getAttribute("CEOempno");
	 List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	 
	 Object[] empData=(Object[])request.getAttribute("EmpData");
	
	
	 List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
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
  <div class="card-body" align="center">
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

			<div class="card-body">
				<div class="card" style="padding-top: 0px; margin-top: -36px; width: 75%;">
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
								   <td >Academic Year</td>
								   <td colspan="2" style="color: blue;"><%=obj[9].toString().substring(0,4) %></td>
								 </tr>
								 
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Name of Course</td>
								 	<td colspan="2" style="color: blue;"><%=obj[10] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Specialization</td>
								 	<td colspan="2" style="color: blue;"><%=obj[11] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:20%;text-align: left">Education Type</td>
								 	<td colspan="2" style="color: blue;"><%=obj[12] %></td>
								</tr> 
								
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Essential Education Qualification</td>
								 	<td colspan="2"style="color: blue;"><%=obj[13] %></td>
								</tr> 
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td colspan="3" style="width:20%;text-align: left">Declaration:
									
									<br>
									
									
									a)&nbsp; All expenses for the course shall be borne by me.<br>
									b)&nbsp;  No official / special leave /flexible working hours will use for attending classes
									
									    or for appearing in examinations / Projects etc.<br>
									
									c)&nbsp;  I will not refuse any official duty / outstation duty during the period.<br>
									
									d)&nbsp;  Pursuing the course will not hamper date -to-day official activities.<br>
									
									e)&nbsp;  Publication of research papers ,if any will be with the prior approval of CEO.<br>
									
									f)&nbsp;&nbsp; I will not utilize Society resources for research purposes.<br>
									
									g)&nbsp; I will obtain prior permission for visiting abroad for course work.<br>
									
									
									</td>
								 	
								</tr> 
								 
						</table>
						</div>	
					
					
					   <% if(RemarksHistory.size()>1){ %>
				  
				  <div class="col-md-5" align="left" style="margin-left:1rem;margin-top:2rem; padding:0px;border: 1px solid black;border-radius: 5px;">
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
					
					<% if(obj[17]!=null && toUserStatus.contains(obj[17].toString())) { %>
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
					<% } else if (PandAs.contains(empData[0].toString())){ %>
					
				     <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCHigherEducationForward.htm" name="Action" value="A"  onclick=" return verify()"  >
							Verify
						</button>
						
					<%}else{%>
						
                           <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="NOCHigherEducationForward.htm" name="Action" value="A"  onclick=" return message()" >
								Recommend
							</button>
						
					<%} %>
				  
				  <button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="NOCHigherEducationForward.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
						
					 </div> 
				<%} %>
					
					 <input type="hidden" name="EducationId" value="<%=obj[15]%>"> 
					</form>
				</div>
			</div>
		</div>
</div>

<script>

function validateTextBox() {
    if (document.getElementById("remarksarea").value.trim() != "") {
    	return confirm('Are You Sure To Return?');
    	
    } else {
        alert("Please enter Remarks");
        return false;
    }
}

</script>

</body>
</html>