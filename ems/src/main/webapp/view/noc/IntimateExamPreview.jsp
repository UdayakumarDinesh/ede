
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
	 
	 List<String> toUserStatus  = Arrays.asList("INI","RDG","RPA");
	
	 List<Object[]> RemarksHistory=(List<Object[]>)request.getAttribute("RemarksHistory");
	 
	 List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
	 
	 Object[] Ceoname=(Object[])request.getAttribute("CeoName");
	  
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
		 
	<div class="card-body" >
			 <div class="card"  align="center" style="padding-top:0px;margin-top: -15px;width: 70%;">
			  <form action="" method="post">
			  	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"  align="center">
			        <div class="" style="padding: 0.5rem 1rem;margin:10px 0px 5px 0px;">
				
								<div align="center">
								<div style="width: 100%;">
									<div style="width: 100%; margin-left:auto; margin-right:auto;border: 0;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></div>
									 <div ><span style="margin-left: 0px;font-weight: 100;font-size: 22px;">INTIMATION FOR EXAM</span></div> 
									
				
								</div>
								</div>
								
					
					<table style="margin-top: 9%;;border-collapse: collapse;width:100%;">	
					  <tbody>
						<tr>
						  <td style="border: 0;width:86%">From&emsp;&emsp;&emsp;:&emsp;<span class="text-blue"><%=obj[2] %></span> </td>
						  <td style="border: 0;width:17%;">To : &nbsp;&nbsp;P&A Dept</td>
						 </tr>					
						 <tr>  <td style="border: 0;">Emp. No.&emsp;&nbsp;&nbsp;:&emsp; <span class="text-blue"><%=obj[1] %></span> </tr>
						  <tr> <td style="border: 0;">Date&emsp;&emsp;&emsp;&nbsp;:&emsp; <span class="text-blue"><%=rdf.format(sdf.parse(obj[5].toString())) %></span>
						 
						  </td>	 </tr>	
						  <tr> <td style="border: 0;"></td> </tr>
						  <tr> <td style="border: 0;"></td> </tr>
						   <tr> <td style="border: 0;"></td> </tr>
						    <tr> <td style="border: 0;"></td> </tr>
						     <tr> <td style="border: 0;"></td> </tr>
						      <tr> <td style="border: 0;"></td> </tr>
						       <tr> <td style="border: 0;"></td> </tr>
						        <tr> <td style="border: 0;"></td> </tr>
						         <tr> <td style="border: 0;"></td> </tr>
						 <tr>
						 <td style="border: 0;">Exam Name &emsp;&emsp;:&emsp; <span class="text-blue"><%=obj[3] %></span></tr>
						   <tr><td style="border: 0;">Probable Date &emsp;:&emsp;<span class="text-blue"><%=rdf.format(sdf.parse(obj[4].toString())) %></span>
						 </td></tr>
						  	
						  <%--  <td style="border: 0;margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 17px;" align="left">
						     This is to inform you that I have <span class="text-blue"><%=obj[3] %> </span> on <span class="text-blue"><%=rdf.format(sdf.parse(obj[4].toString())) %></span>. 
						   </td>  --%>
						
						 
						 	<!-- <tr> <td style="border: 0;"> <input type="text" value="" readonly></td> </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="" readonly></td> </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="" readonly></td> </tr>	
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;">The same may be recorded in the office records.</td> </tr>	 -->
						 						       
					    </tbody>
					</table>
					
						<br>
						<br>
						
						
					<% if(obj[7]!=null){ %><div style="width:100%;text-align: right;">Forwarded On : <span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(obj[7].toString().substring(0, 10)) +" "+obj[7].toString().substring(11,19) %></span></div><%} %>									     
				 
				</div>  
				</div>
				
				
				 <% if(RemarksHistory.size()>1){ %>
				  
				  <div class="col-md-5" align="left" style="margin-left:-27rem;margin-top:2rem; padding:0px;border: 1px solid black;border-radius: 5px;">
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
				<br>
				<% for(Object[] rh:RemarksHistory) {
		 
		 if(rh[4].toString().equalsIgnoreCase("VDG")){%>
		 
	        <div style="margin-left:-460px !important;" >Recommended On : <span class="text-blue"><%=DateTimeFormatUtil.SqlToRegularDate(rh[3].toString().substring(0, 10)) +" "+rh[3].toString().substring(11,19) %></span>
	
	     <%}} %>
		 
				
				
				<br>
				<br>
				 <% if(obj[6].toString().equalsIgnoreCase("VPA")){ %>
		 <div style="margin-left:430px;text-align:center;"> 
		                         <span style="font-weight: 600; font-size: 16px;margin-left:0px;">APPROVED</span><br><br>
						        <span style="font-weight: 500; font-size: 16px;margin-left:0px;">P&A InCharge&nbsp;<span class="text-blue" ></span></span><br>
								<span style="font-weight: 400; font-size: 16px; ">Approved On:&nbsp;<span class="text-blue" ><%=DateTimeFormatUtil.SqlToRegularDate(obj[7].toString().substring(0, 10)) +" "+obj[7].toString().substring(11,19) %></span></span><br>
		</div>	
		<%} %>
				
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
				
				<% if(obj[6]!=null && toUserStatus.contains(obj[6].toString())) { %>
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