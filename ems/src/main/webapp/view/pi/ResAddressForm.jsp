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
List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");
List<Object[]> ResIntimationRemarks = (List<Object[]>)request.getAttribute("ResIntimationRemarks");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
Date date = new Date();

String isApproval = (String)request.getAttribute("isApproval");

List<String> toUserStatus  = Arrays.asList("INI","RSO","RDG","RPA");
List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");

List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
String empNo = (String)session.getAttribute("EmpNo");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5 style="width:113%;">Residential Address - Form</h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				    <li class="breadcrumb-item active" aria-current="page">Residential Address</li>
				  </ol>
				</nav>
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
						  <td style="border: 0;width:86%">From&emsp;&emsp;&emsp;:&emsp;<label style="color: blue;"><%if(ResFormData!=null && ResFormData[11]!=null){ %> <%=ResFormData[11] %> <%} %></label> </td>
						  <td style="border: 0;width:17%;">To: &nbsp;P&A Dept</td>
						 </tr>					
						 <tr>  <td style="border: 0;">Emp.No.&emsp;&nbsp;&nbsp;:&emsp; <label style="color: blue;"><%if(ResFormData!=null && ResFormData[12]!=null){ %> <%=ResFormData[12] %> <%} %></label></td> </tr>
						 <tr> 
						  	<td style="border: 0;">Date&emsp;&emsp;&emsp;&nbsp;:&emsp; 
						  	<label style="color: blue;">
							  <%for(Object[] apprInfo : ApprovalEmpData){ %>
							  <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
					   				<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %>
					   			<%break;
					   			} %>
						   		<%} %>
						   		</label>
					   		</td>	 
					   	</tr>	
						  <tr> <td style="border: 0;"></td> </tr>
						  <tr> <td style="border: 0;"></td> </tr>
						 <tr> 	
						   <td style="border: 0;margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						     This is to inform you that I have changed my residence w.e.f.&nbsp;<input type="text" value=" <%if(ResFormData!=null && ResFormData[3]!=null){ %><%=DateTimeFormatUtil.SqlToRegularDate(ResFormData[3]+"")%>  <%} %>" readonly style="width:12%;text-align:center;color: blue;">&nbsp; and the present address and the telephone number is as under:
						   </td> 
						 </tr> 
						 
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(ResFormData!=null && ResFormData[2]!=null){ %> <%=ResFormData[2]%> <%} %>	" readonly style="color: blue;"></td> </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="<% if(ResFormData!=null){ %> <%=ResFormData[9]  +", "+ResFormData[8]+" - "+ResFormData[10] %> <%} %> " readonly style="color: blue;"></td> </tr>			
						 	<tr> <td style="border: 0;"> <input type="text" value="<%if(ResFormData!=null && ResFormData[4]!=null){ %> <%=ResFormData[4]%> <%} %>" readonly style="color: blue;"></td> </tr>	
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;">The same may be recorded in the office records.</td> </tr>	
						 						       
					    </tbody>
					</table>
					<div style="width:100%;text-align: right;margin-left:-5%;color: blue;">	<%if(ResFormData!=null && ResFormData[11]!=null){ %><%=ResFormData[11] %> <%} %>	</div>	
					<div style="width:100%;text-align: right;margin-left:-5%;">	
						Signature of Employee<br>
						<%-- <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;} %>
				   		<%} %>  --%>
				   		
				   		
					</div>				
				   <hr style="border:solid 1px;">
				   
				  	<div style="width: 100%;border: 0;text-align: center;"> <b style="font-size:18px;text-decoration:underline">FOR ADMINISTRATION USE</b> </div>
				    <br>
				   <!-- <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						Intimation of change of address received on  &nbsp;<input type="text" value="" style="width:10%;text-align:center;" id="data-input" disabled>&nbsp;. The same has been updated in the personal records.																		
				   </div> -->
				   
				   <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						Intimation of change of address received on  &nbsp;<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
				   				<span style="text-decoration: underline;color: blue;"><%=rdf.format(sdtf.parse(apprInfo[4].toString())) %></span>
				   				
				   			<%
				   				break;
				   			} %>
				   		<%} %> . The same has been updated in<br>  the personal records.																		
				   </div>
				   <br><br>
				   <div style="width:100%;text-align: right;margin-left:-5%;"> </div>	
				   <div style="border:0px;width: 100%; text-align: right;"> 
				   		Incharge - P&A
				   		<br>
				   		<label style="color: blue;">
				   		<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("VPA")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;} %>
				   		<%} %> 
				      </label>
				   </div>
				   <br>
				   <div class="row">
						<%if(ResIntimationRemarks.size()>0){ %>
							<div class="col-md-8" align="center" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
								<%if(ResIntimationRemarks.size()>0){ %>
									<table style="margin: 3px;padding: 0px">
										<tr>
											<td style="border:none;padding: 0px">
												<h6 style="text-decoration: underline;">Remarks :</h6> 
											</td>											
										</tr>
										<%for(Object[] obj : ResIntimationRemarks){%>
										<tr>
											<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
												<%=obj[3]%>&nbsp; :
												<span style="border:none; color: blue;">	<%=obj[1] %></span>
											</td>
										</tr>
										<%} %>
									</table>
								<%} %>
							</div>
							<%} %>
					   </div>
				   <% if(ResFormData!=null && toUserStatus.contains(ResFormData[14].toString())){ %>
				   		<%-- <div align="left">
				   			 <%if(ResFormData[15]!=null){ %> <span style="color: red">Remarks :</span> <%=ResFormData[15] %> <%} %>
				   		
				   		</div> --%>
					   <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   
					   <button style="margin-top: -9%" type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ResAddressFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						</button>
					   
					   </div>
				   	
				   		 
					<%} %>
					
					<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
						<%-- <div align="left">
				   			 <%if(ResFormData[15]!=null){ %> <span style="color: red">Remarks :</span> <%=ResFormData[15] %> <%} %>
				   		
				   		</div> --%>
						<div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
					   
					   <%if(PandAs.contains(empNo)) {%>
					   <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ResAddressFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							Verify 	
						</button>
						<%}else{ %>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="ResAddressFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Recommend	
						</button>
						<%} %>					
				   		<button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="ResAddressFormSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
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

</html>