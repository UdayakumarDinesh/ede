
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Exam Intimation Preview</title>

<style>
.text-blue
{
	color: blue;
	font-weight:500px;
	font-size: 16px;
}

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

</style>

</head>
<body>

<%

    SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
    SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
    String LabLogo = (String)request.getAttribute("LabLogo");
    String LoginType = (String)session.getAttribute("LoginType");
	Object[] empData=(Object[])request.getAttribute("EmpData");
	String isApproval = (String)request.getAttribute("isApproval");
	
	 Object[] obj=(Object[])request.getAttribute("ExamIntimationDetails");
	 
	 List<String> toUserStatus  = Arrays.asList("INI","RDG","RPA","RSO");
	
	 List<Object[]> RemarksHistory=(List<Object[]>)request.getAttribute("RemarksHistory");
	 
	 List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	 
	 Object[] Ceoname=(Object[])request.getAttribute("CeoName");
	 
	 List<Object[]> IntimationApprovalData=(List<Object[]>)request.getAttribute("IntimationApprovalData");
	 List<String> RecommendStatus = Arrays.asList("VDG","VSO");
	 
	 int slno=0;
	  
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>Exam Intimation Preview <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
							
							<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
							<li class="breadcrumb-item "><a href="NocApproval.htm"> Approval</a></li>
					
						<%}
							else{%>
					<li class="breadcrumb-item "><a href="IntimateExam.htm">
							Intimation For Exam</a></li>
							<%} %>
					<li class="breadcrumb-item active " aria-current="page">
						Exam Intimation Preview</li>
				</ol>
			</div>
		</div>
	</div>

<div class="page card dashboard-card">
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
				<div class="card" style="padding-top: 0px; margin-top: -20px; width: 65%;">
					<form action="" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="card-body main-card" style="padding-top: 0px; margin-top: -15px;" align="center">
						
                          <div style="width: 10%; height: 75px; border: 0; display: inline-block;margin:2% 0 10px -90%;"><img style="width: 70px; height: 80px; margin: 5px;" align="left" src="data:image/png;base64,<%=LabLogo%>"></div>									
                          <div style="width: 90%; height: 75px; border: 0; text-align: center;margin-top:-6%;margin-left:5%;"><h4 style="font-size:23px;">INTIMATION FOR EXAM</h4></div>
						 
							<table style="border: 0px;margin-top:-21px; width: 100%;">
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Name and Designation</td>
									<td colspan="2" style="color: blue;"><%=obj[3] %>&nbsp;&nbsp; <%=obj[4] %>
									</td>
									
								</tr>
								
								<tr>
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td>Emp.No</td> 
									
									<td colspan="2" style="color: blue;"><%=obj[2] %></td>
								</tr>
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Exam Name </td>
								 	<td colspan="2"style="color: blue;"><%=obj[13] %></td>
								</tr> 
								
								<tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Probable Date</td>
								 	<td colspan="2"style="color: blue;"><%=rdf.format(sdf.parse(obj[14].toString())) %></td>
								</tr> 
								
								
								 <tr>
									<td style="border-bottom: 0;width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="border-bottom: 0;width: 5%;text-align: left">Advertisement Number & Date</td>
									<td style="width:20%;text-align: center">Advertisement No</td>
									<td style="width:20%;text-align: center">Advertisement Date</td>
									
								
								</tr>
								<tr>
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[5] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=rdf.format(sdf.parse(obj[6].toString())) %></td>
								
								   
								</tr> 
								
								
								  
								 <tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td>Organization Name </td>
								   <td colspan="2" style="color: blue;"><%=obj[7] %></td>
								 </tr>
								  
								<tr>
								  	<td style="width: 5%;text-align: center"><%=++slno%>.</td>
								   <td>Place </td>
								   <td colspan="2" style="color: blue;"><%=obj[8] %></td>
								 </tr>
								 
								 <tr>
								 
									<td style="border-bottom: 0;width: 5%;text-align: center"><%=++slno%>.</td>
									
									<td style="width:20%;text-align: center">Name of the Post</td>
									<td style="width:20%;text-align: center">Post Code / Post No.</td>
									<td style="width:20%;text-align: center">PayLevel</td>
								 	
								</tr> 
								
								 <tr>
								 
								   <td style="border-bottom:1;border-top:0"></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[9] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[10] %></td>
								   <td style="width:20%;text-align: center;color: blue;"><%=obj[11] %></td>
									
								</tr> 
								
								
								 <tr>
								 
									<td style="width: 5%;text-align: center"><%=++slno%>.</td>
									<td style="width:25%;text-align: left">Application Through  </td>
								 	<td colspan="2"style="color: blue;"><%=obj[12] %></td>
								</tr> 
								
						</table>
						
						</div>	
						
					<% if(obj[18]!=null){ %><div style="width:95%;text-align: right;">Forwarded On : <span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[18].toString().substring(0, 10)) +" "+obj[18].toString().substring(11,19) %></span></div><%} %>									     	
						
				
				 <% if(RemarksHistory.size()>1){ %>
				  
				  <div class="col-md-5" align="left" style="margin-left:-27rem;margin-top:0rem; padding:0px;border: 1px solid black;border-radius: 5px;">
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
				
				<br>
				<% 
	 
	 for(Object[] ad :IntimationApprovalData) {
		 
		if( RecommendStatus.contains(ad[8].toString()) ){%>
				 
	         
		<div align="left" style="margin-left:25px !important;">Recommended By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
	    <div align="left" style="margin-left:25px !important;">Recommended On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
	    <br>
	 
	   <%}
			else if(ad[8].toString().equalsIgnoreCase("VPA")){%>
		
				<div align="left" style="margin-left:500px !important;">Verified By :&nbsp;<span class="text-blue"><%=ad[2] %></span> </div>
			    <div align="left" style="margin-left:500px !important;">Verified On :&nbsp;<span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(ad[4].toString().substring(0, 10)) +" "+ad[4].toString().substring(11,19) %> </span></div>
		
		
	<% } } %> 
		
			
				
			  <% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
			 
				 <div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;"> 
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100" id="remarksarea" name="remarks" maxlength="500"></textarea>
				  </div>
				  
				  
					<% if (PandAs.contains(empData[0].toString())){ %>
					
				     <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="IntimationForExamForward.htm" name="Action" value="A"  onclick="return confirm ('Are You Sure To Submit')"  >
							Verify
						</button>
						
					<%}else{%>
						
                           <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="IntimationForExamForward.htm" name="Action" value="A"  onclick="return confirm ('Are You Sure To Submit')" >
								Recommend
							</button>
						
					<%} %>
				  
				  <button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="IntimationForExamForward.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
						
					 </div> 
				<%} %>
				
				<% if(obj[17]!=null && toUserStatus.contains(obj[17].toString())) { %>
	  	<div class="col-md-6" align="center" style="margin-top: 0%;margin-left:48%;">
				   <div class="col-md-12" align="left" style="margin-bottom: 5px;">Remarks : <br>
					 <textarea class="w-100 form-control" rows="3" cols="100"   name="remarks"  maxlength="500"></textarea>
				  </div>
                <button type="submit" class="btn btn-sm submit-btn"  name="Action" value="A"  formaction="IntimationForExamForward.htm"   onclick="return confirm('Are You Sure To Submit?');" >Forward</button>
                
				</div>
		<%} %>
		
		    <input type="hidden" name="ExamId" value="<%=obj[0]%>"> 
			 
			</form>
			
			
	  	
		</div>
		</div>
	   </div> 
      </div>							
	
	<script type="text/javascript">
	
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