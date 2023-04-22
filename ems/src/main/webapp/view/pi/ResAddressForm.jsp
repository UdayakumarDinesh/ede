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
<title>Residential Address Form</title>
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
Object[] ResFormData = (Object[])request.getAttribute("ResFormData");
/* Object[] PerFormData = (Object[])request.getAttribute("PerFormData"); */
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
						  <td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h4> FORM FOR INTIMATION OF CHANGE OF <br>RESIDENTIAL ADDRESS</h4> </td>
						  <td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;"> &nbsp;<span class="text-blue"></span></span> </td>
						</tr>
					</table> 
					
					<table style="margin-top: 5%;border-collapse: collapse;width:100%;">	
					  <tbody>
						<tr>
						  <td style="border: 0;width:86%">From&emsp;&emsp;&emsp;:&emsp;<%if(ResFormData!=null && ResFormData[11]!=null){ %> <%=ResFormData[11] %> <%} %> </td>
						  <td style="border: 0;width:17%;">To &emsp;: &nbsp;P&A Dept</td>
						 </tr>					
						 <tr>  <td style="border: 0;">Emp. No.&emsp;&nbsp;&nbsp;:&emsp; <%if(ResFormData!=null && ResFormData[12]!=null){ %> <%=ResFormData[12] %> <%} %></td> </tr>
						  <tr> <td style="border: 0;">Date&emsp;&emsp;&emsp;&nbsp;:&emsp; <%=rdf.format(date) %></td>	 </tr>	
						  <tr> <td style="border: 0;"></td> </tr>
						  <tr> <td style="border: 0;"></td> </tr>
						 <tr> 	
						   <td style="border: 0;margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						     This is to inform you that I have changed my residence w.e.f.&nbsp;<input type="text" value=" <%if(ResFormData!=null && ResFormData[3]!=null){ %><%=DateTimeFormatUtil.SqlToRegularDate(ResFormData[3]+"")%>  <%} %>" readonly style="width:10%;text-align:center;">&nbsp; and the present address and the telephone number is as under:
						   </td> 
						 </tr> 
						 
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(ResFormData!=null && ResFormData[2]!=null){ %> <%=ResFormData[2]%> <%} %>	" readonly></td> </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="<% if(ResFormData!=null){ %> <%=ResFormData[9]  +", "+ResFormData[8]+" - "+ResFormData[10] %> <%} %> " readonly></td> </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(ResFormData!=null && ResFormData[4]!=null){ %> <%=ResFormData[4]%> <%} %>" readonly></td> </tr>	
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;">The same may be recorded in the office records.</td> </tr>	
						 						       
					    </tbody>
					</table>	
					<div style="width:100%;text-align: right;margin-left:-5%;">	<%if(ResFormData!=null && ResFormData[11]!=null){ %><%=ResFormData[11] %> <%} %>	</div>				
					<div style="width:100%;text-align: right;">	Signature of Employee </div>									     
				   <hr style="border:solid 1px;">
				   
				  	<div style="width: 100%;border: 0;text-align: center;"> <b style="font-size:18px;text-decoration:underline">FOR ADMINISTRATION USE</b> </div>
				    <br>
				   <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						Intimation of change of address received on  &nbsp;<input type="text" value="" style="width:10%;text-align:center;" id="data-input" disabled>&nbsp;. The same has been updated in the personal records.																		
				   </div>
				   <br><br>
				   <div style="width:100%;text-align: right;margin-left:-5%;"> </div>	
				   <div style="border:0px;width: 100%; text-align: right;"> Incharge-P&A </div>
				   <br>
				   	<% if(ResFormData!=null && toUserStatus.contains(ResFormData[18].toString()) || (isApproval!=null && isApproval.equalsIgnoreCase("Y"))){ %>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ResAddressFormSubmit.htm" name="Action" value="A" >
							<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						</button>
					<%} %>
	           
			   </div>
			   
			   <input type="hidden" name="empid" value="<%if(ResFormData!=null){ %><%=ResFormData[0] %><% }%>">
			   <input type="hidden" name="resaddressid" value="<%if(ResFormData!=null){ %><%=ResFormData[1] %><% }%>">
			</form>
		</div>
	   </div>  
	  </div>							
	</div>	
 </div>
</body>
<script>
  var loginType = '<%=LoginType%>';
  var dataInput = document.getElementById("data-input");
  if (loginType === "P") {
    dataInput.disabled = false;
  }
</script>
<%if(LoginType.equalsIgnoreCase("p") && LoginType!=null) {%>
<script type="text/javascript">
$('#data-input').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :new Date(), 
	//"startDate" : new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>
<%} %>
<script type="text/javascript">
function DremarkRequired(action)
{
	if(action === 'N'){
		$('#Responcemsg').attr('required', true);
		if($('#Responcemsg').val().trim()===''){
			alert('Please Fill Remarks to Reject! ');
			return false;
		}else{
				return confirm('Are You Sure To Reject?');
		}
		
	}else{
		$('#Responcemsg').attr('required', false);
		return confirm('Are You Sure To Accept?');
	}
	
}
</script>
</html>