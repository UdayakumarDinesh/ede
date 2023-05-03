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
<title>Permanent Address Form</title>
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
Object[] PerFormData = (Object[])request.getAttribute("PerFormData");
List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");

SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");

Date date = new Date();

String isApproval = (String)request.getAttribute("isApproval");

List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RPA","RCE");
List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");

%>
<!-- <div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5 style="width:113%;">Intimation For Permanent Address </h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				    <li class="breadcrumb-item active" aria-current="page">Permanent Address</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div> -->
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
			 <div class="card" style="padding-top:0px;margin-top: -15px;width: 85%;">
			  <form action="" method="post">
			  	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="card-body main-card " style="padding-top:0px;margin-top: -15px;"  align="center">
			        <table style="border: 0px; width: 100%">
						<tr>
						  <td style="width: 20%; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
						  <td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h4> FORM FOR INTIMATION OF PERMANENT ADDRESS CHANGE</h4> </td>
						  <td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;"> &nbsp;<span class="text-blue"></span></span> </td>
						</tr>
					</table> 
					
					<table style="margin-top: 5%;border-collapse: collapse;width:100%;">	
					  <tbody>
						<tr>
						  <td style="border: 0;width:86%">From&emsp;&emsp;&emsp;:&emsp;<%if(PerFormData!=null && PerFormData[8]!=null){ %> <%=PerFormData[8] %> <%} %> </td>
						  <td style="border: 0;width:17%;">To &emsp;: &nbsp;P&A Dept</td>
						 </tr>					
						 <tr>  <td style="border: 0;">Emp. No.&emsp;&nbsp;&nbsp;:&emsp; <%if(PerFormData!=null && PerFormData[9]!=null){ %> <%=PerFormData[9] %> <%} %></td> </tr>
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
						     This is to inform you that I have changed my residence w.e.f.&nbsp;<input type="text" value=" <%if(PerFormData!=null && PerFormData[6]!=null){ %><%=DateTimeFormatUtil.SqlToRegularDate(PerFormData[6]+"")%>  <%} %>" readonly style="width:12%;text-align:center;">&nbsp; and the present address and the telephone number is as under:
						   </td> 
						 </tr> 
						 
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(PerFormData!=null && PerFormData[2]!=null){ %> <%=PerFormData[2]%> <%} %>	" readonly></td> </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(PerFormData!=null){ %> <%=PerFormData[3]  +", "+PerFormData[4]+" - "+PerFormData[5] %> <%} %> " readonly></td> </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(PerFormData!=null && PerFormData[7]!=null){ %> <%=PerFormData[7]%> <%} %>" readonly></td> </tr>	
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;">The same may be recorded in the office records.</td> </tr>	
						 						       
					    </tbody>
					</table>	
					<div style="width:100%;text-align: right;margin-left:-5%;">	<%if(PerFormData!=null && PerFormData[8]!=null){ %><%=PerFormData[8] %> <%} %>	</div>				
					<div style="width:100%;text-align: right;">	Signature of Employee </div>									     
				   <hr style="border:solid 1px;">
				   
				  	<div style="width: 100%;border: 0;text-align: center;"> <b style="font-size:18px;text-decoration:underline">FOR ADMINISTRATION USE</b> </div>
				    <br>
				   <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						Intimation of change of address received on  &nbsp;
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
				   <% if(PerFormData!=null && toUserStatus.contains(PerFormData[11].toString())){ %>
				   		<div align="left">
				   			 <%if(PerFormData[12]!=null){ %> <span style="color: red">Remarks :</span> <%=PerFormData[12] %> <%} %>
				   		
				   		</div>
					   <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   	
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="PerAddressFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						</button>
					<%} %>
					
					<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
						<div align="left">
				   			 <%if(PerFormData[12]!=null){ %> <span style="color: red">Remarks :</span> <%=PerFormData[12] %> <%} %>
				   		
				   		</div>
						<div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="PerAddressFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Verify	
						</button>
					
				   		<button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="PerAddressFormSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
					<%} %>
				   					   				
			   </div>
			   
			   <input type="hidden" name="empid" value="<%if(PerFormData!=null && PerFormData[0]!=null ){ %><%=PerFormData[0] %> <% }%>">
			   <input type="hidden" name="peraddressid" value="<%if(PerFormData!=null && PerFormData[1]!=null){ %><%=PerFormData[1] %> <% }%>">
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