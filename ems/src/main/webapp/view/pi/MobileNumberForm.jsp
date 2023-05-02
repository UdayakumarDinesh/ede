<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mobile Number Form</title>
</head>
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
input{
border-width: 0 0 1px 0;
width:80%;
}
input:focus {
  outline: none;
}

</style>
<body>

<%
String LabLogo = (String)request.getAttribute("LabLogo");
String LoginType = (String)session.getAttribute("LoginType");
Object[] MobFormData = (Object[])request.getAttribute("MobFormData");
List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");

SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");

Date date = new Date();

String isApproval = (String)request.getAttribute("isApproval");

List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");

%>
<div class="page card dashboard-card">
  <div class="card-body" align="center">
     <div class="row">
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
			 <div class="card" style="padding-top:0px;margin-top: -15px;width: 85%;">
			  <form action="" method="post">
			  	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"  align="center">
			        <table style="border: 0px; width: 100%">
						<tr>
						  <td style="width: 20%; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
						  <td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h4> FORM FOR INTIMATION OF MOBILE NUMBER CHANGE</h4> </td>
						  <td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;"> &nbsp;<span class="text-blue"></span></span> </td>
						</tr>
					</table> 
					
					<table style="margin-top: 5%;border-collapse: collapse;width:100%;">	
					  <tbody>
						<tr>
						  <td style="border: 0;width:86%">From&emsp;&emsp;&emsp;:&emsp;<%if(MobFormData!=null && MobFormData[8]!=null){ %> <%=MobFormData[8] %> <%} %> </td>
						  <td style="border: 0;width:17%;">To &emsp;: &nbsp;P&A Dept</td>
						 </tr>					
						 <tr>  <td style="border: 0;">Emp. No.&emsp;&nbsp;&nbsp;:&emsp; <%if(MobFormData!=null && MobFormData[1]!=null){ %> <%=MobFormData[1] %> <%} %></td> </tr>
						  <tr> <td style="border: 0;">Date&emsp;&emsp;&emsp;&nbsp;:&emsp; 
						  <%for(Object[] apprInfo : ApprovalEmpData){ %>
							  <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
					   				<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %>
					   			<%break;
					   			} %>
						   		<%} %>
						  </td>	 </tr>	
						  <tr> <td style="border: 0;"></td> </tr>
						  <tr> <td style="border: 0;"></td> </tr>
						 <tr> 	
						   <td style="border: 0;margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						     This is to inform you that I have changed my mobile number with effect from.&nbsp;<input type="text" value=" <%if(MobFormData!=null && MobFormData[4]!=null){ %><%=DateTimeFormatUtil.SqlToRegularDate(MobFormData[4]+"")%>  <%} %>" readonly style="width:12%;text-align:center;">&nbsp; and the new mobile number is as under:
						   </td> 
						 </tr> 
						 
						 	<tr> <td style="border: 0;"><input type="text" value="<%if(MobFormData!=null && MobFormData[2]!=null){ %> <%="Mobile Number : "+MobFormData[2]%> <%} %>" readonly></td> </tr>									 				
						 	<tr> <td style="border: 0;"><input type="text" value="<%if(MobFormData!=null && MobFormData[3]!=null){ %> <%="Alt Mobile Number : "+MobFormData[3]%> <%} %>" readonly></td> </tr>	
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;">The same may be recorded in the office records.</td> </tr>	
						 						       
					    </tbody>
					</table>	
					<div style="width:100%;text-align: right;margin-left:-5%;">	<%if(MobFormData!=null && MobFormData[8]!=null){ %><%=MobFormData[8] %> <%} %>	</div>				
					<div style="width:100%;text-align: right;">	Signature of Employee </div>									     
				   <hr style="border:solid 1px;">
				   
				  	<div style="width: 100%;border: 0;text-align: center;"> <b style="font-size:18px;text-decoration:underline">FOR ADMINISTRATION USE</b> </div>
				    <br>
				   <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						Intimation of change of mobile number received on  &nbsp;
						<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
				   				<span style="text-decoration: underline;"><%=rdf.format(sdtf.parse(apprInfo[4].toString())) %></span>
				   				
				   			<%
				   				break;
				   			} %>
				   		<%} %> &nbsp;. The same has been updated in the personal records.																		
				   </div>
				   <br><br>
				   <div style="width:100%;text-align: right;margin-left:-5%;"> </div>	
				   <div style="border:0px;width: 100%; text-align: right;"> Incharge-P&A 
				   <br>	
				   <br>
				   		<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("VPA")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;} %>
				   		<%} %> 
				   		</div>
				   						   <br>
				   <% if(MobFormData!=null && toUserStatus.contains(MobFormData[7].toString())){ %>
				   		<div align="left">
				   			 <%if(MobFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=MobFormData[6] %> <%} %>
				   		
				   		</div>
					   <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   	
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="MobileFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						</button>
					<%} %>
					
					<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
						<div align="left">
				   			 <%if(MobFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=MobFormData[6] %> <%} %>
				   		
				   		</div>
						<div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="MobileFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Verify	
						</button>
					
				   		<button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="MobileFormSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
					<%} %>
				   					   				
			   </div>
			   
			   <input type="hidden" name="EmpNo" value="<%if(MobFormData!=null && MobFormData[1]!=null ){ %><%=MobFormData[1] %> <% }%>">
			   <input type="hidden" name="mobileNumberId" value="<%if(MobFormData!=null && MobFormData[0]!=null){ %><%=MobFormData[0] %> <% }%>">
			</form>
		</div>
	   </div>  
	  </div>							
	</div>	
 </div>
</body>
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

</html>