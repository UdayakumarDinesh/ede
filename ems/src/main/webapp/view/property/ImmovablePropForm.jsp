<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.vts.ems.property.model.PisImmovableProperty,com.vts.ems.pis.model.Employee" %>    
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
input{
border-width: 0 0 1px 0;
width:80%;
}
input:focus {
  outline: none;
}

</style>
</head>
<body>
<%
String LabLogo=(String) request.getAttribute("LabLogo");
PisImmovableProperty imm= (PisImmovableProperty)request.getAttribute("ImmPropFormData");
Employee emp = (Employee)request.getAttribute("EmpData");
SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");
List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");
%>

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
				<div class="card" style="padding-top: 0px; margin-top: -15px; width: 100%;">
					<form action="" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="card-body main-card" style="padding-top: 0px; margin-top: -15px;" align="center">
						
                          <div style="width: 10%; height: 75px; border: 0; display: inline-block;margin:2% 0 10px -90%;"><img style="width: 80px; height: 90px; margin: 5px;" align="left" src="data:image/png;base64,<%=LabLogo%>"></div>									

							<table style="border: 0px; width: 100%;margin-top:-9%;margin-left:5%;">
								<tr>
									<%-- <td style="width: 10%; height: 75px; border: 0; margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin: 5px;" align="left" src="data:image/png;base64,<%=LabLogo%>"></td> --%>
									<td style="width: 90%; height: 75px; border: 0; text-align: center;"><h4>Form for giving intimation for transaction of <%if(imm!=null && "A".equalsIgnoreCase(imm.getTransState())){ %> Acquiring <%}else {%> Disposing <%} %> of Immovable Property</h4></td>
									<!-- <td style="width: 20%; height: 75px; border: 0; vertical-align: bottom;"><span style="float: right;"> &nbsp;<span class="text-blue"></span></span> </td> -->
								</tr>
								<tr>
								  <!--  <td></td> -->
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>


		</div>
</div>
</body>
</html>