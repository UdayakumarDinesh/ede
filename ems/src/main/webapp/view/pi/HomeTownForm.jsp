<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ page import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
 <%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Town Form</title>
 <jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>  
</head>
<style type="text/css">
table{
	align: left;
	width: 100% !important;
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
input{
border-width: 0 0 1px 0;
width:47%;
}
input:focus {
  outline: none;
}

</style>
<body>
 <%
String LabLogo = (String)request.getAttribute("LabLogo");
Object[] HomFormData = (Object[])request.getAttribute("HomFormData");
List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");

SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");

Date date = new Date();

String isApproval = (String)request.getAttribute("isApproval");

List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");

%>

 <div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5> Home Town - Form </h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				    <li class="breadcrumb-item"><a href="PIHomeTownMobile.htm">Hometown & Mobile</a></li>
				    <li class="breadcrumb-item active" aria-current="page">Hometown</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	
	<div class="page card dashboard-card">
		 <div class="card-body" align="center">
			<div align="center">
					<%String ses=(String)request.getParameter("result"); 
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
			 <div class="card" style="padding-top:0px;margin-top: -15px;width:80%;">
			  <form action="" method="post">
			  	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"align="center">
			        <table style="border: 0px; width: 100%">
						<tr>
						  <td style="width: 20%; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
						  <td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h4> DECLARATION OF HOME TOWN</h4> </td>
						  <td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;"> &nbsp;<span class="text-blue"></span></span> </td>
						</tr>						
					</table> 
					
					<table style="margin-top: 5%;border:0;">	
					  <tbody>
						<tr> <td style="border: 0;font-weight:bold;font-size:16px;">To,</td> </tr>	
						<tr> <td style="border: 0;font-weight:bold;font-size:16px;">The Chief Executive Officer</td> </tr>	
						<tr> <td style="border: 0;font-size:16px;">STARC</td> </tr>	
						<tr> <td style="border: 0;font-size:16px;">Bangalore</td> </tr>	
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;text-decoration:underline;">Kind Attn: Incharge - P&A</td> </tr>
						<tr> <td style="border: 0;"> I here by declare my hometown as under for entering the same in personal / official records.</td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;">NAME<label style="margin-left:166px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[8]!=null) {%> <%=HomFormData[8] %> <%} %>" readonly>  </td> </tr>
						<tr> <td style="border: 0;">EMP. NO.<label style="margin-left:148px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[1]!=null) {%> <%=HomFormData[1] %> <%} %>" readonly> </td> </tr>
						<tr> <td style="border: 0;">DESIGNATION<label style="margin-left:110px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[9]!=null) {%> <%=HomFormData[9] %> <%} %>" readonly> </td> </tr>
						<tr> <td style="border: 0;">HOMETOWN<label style="margin-left:119px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[2]!=null) {%> <%=HomFormData[2] %> <%} %>" readonly> </td> </tr>
						<tr> <td style="border: 0;">STATE<label style="margin-left:169px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[4]!=null) {%> <%=HomFormData[4] %> <%} %>" readonly> </td> </tr>
						<tr> <td style="border: 0;">NEAREST RAILWAYSTATION<label style="margin-left:18px">:&nbsp;&nbsp;</label> <input type="text" value="<%if(HomFormData!=null && HomFormData[3]!=null) {%> <%=HomFormData[3] %> <%} %>" readonly> </td> </tr>
					    <tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;"><%if(HomFormData!=null && HomFormData[8]!=null) {%> <%=HomFormData[8] %> <%} %></td> </tr>
						<tr> <td style="border: 0;">Signature of Employee</td> </tr>
						<tr> <td style="border: 0;"></td> </tr>
						<tr> <td style="border: 0;">Date &nbsp;&nbsp;:&nbsp;&nbsp;
						                            <%for(Object[] apprInfo : ApprovalEmpData){ %>
							                        <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
					   			                	<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %>
					   		                    	<%break;
					   		                     	} %>
						   	                    	<%} %></td> </tr>
					  </tbody>					  
					</table>
						<% if(HomFormData!=null && toUserStatus.contains(HomFormData[7].toString())) { %>
				   		<div align="left">
				   			 <%if(HomFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=HomFormData[6] %> <%} %>
				   		
				   		</div>
					   <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   	
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="HometownFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						</button>
					<%} %>
					
					<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
						<div align="left">
				   			 <%if(HomFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=HomFormData[6] %> <%} %>
				   		
				   		</div>
				   		
						<div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="HometownFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Verify	
						</button>
					
				   		<button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="HometownFormSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
					<%} %>
				 </div>
				   <input type="hidden" name="EmpNo" value="<%if(HomFormData!=null && HomFormData[1]!=null ){ %><%=HomFormData[1] %> <% }%>">
			       <input type="hidden" name="hometownId" value="<%if(HomFormData!=null && HomFormData[0]!=null){ %><%=HomFormData[0].toString().trim() %> <% }%>">
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