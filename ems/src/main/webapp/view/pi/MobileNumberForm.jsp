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
Object[] MobFormData = (Object[])request.getAttribute("MobFormData");
List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");
List<Object[]> MobIntimationRemarks = (List<Object[]>)request.getAttribute("MobIntimationRemarks");

SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");

Date date = new Date();

String isApproval = (String)request.getAttribute("isApproval");

List<String> toUserStatus  = Arrays.asList("INI","RGI","RDI","RDG","RSO","RPA","RCE");
List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");

List<String> PandAs = (List<String>)request.getAttribute("PandAsEmpNos");
String empNo = (String)session.getAttribute("EmpNo");
%>
 <div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5 style="width:113%;">Mobile Number -Form</h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				    <li class="breadcrumb-item"><a href="PIHomeTownMobile.htm">Hometown & Mobile</a></li>				    
				    <li class="breadcrumb-item active" aria-current="page">Mobile Number</li>
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
						  <td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h4> FORM FOR INTIMATION OF MOBILE NUMBER CHANGE</h4> </td>
						  <td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;"> &nbsp;<span class="text-blue"></span></span> </td>
						</tr>
					</table> 
					
					<table style="margin-top: 5%;border-collapse: collapse;width:100%;">	
					  <tbody>
						<tr>
						  <td style="border: 0;width:86%">From&emsp;&emsp;&emsp;:&emsp;<label style="color: blue;"><%if(MobFormData!=null && MobFormData[8]!=null){ %> <%=MobFormData[8] %> <%} %></label> </td>
						  <td style="border: 0;width:17%;">To : &nbsp;&nbsp;P&A Dept</td>
						 </tr>					
						 <tr>  <td style="border: 0;">Emp. No.&emsp;&nbsp;&nbsp;:&emsp; <label style="color: blue;"><%if(MobFormData!=null && MobFormData[1]!=null){ %> <%=MobFormData[1] %> <%} %></label></td> </tr>
						  <tr> <td style="border: 0;">Date&emsp;&emsp;&emsp;&nbsp;:&emsp; 
						  <label style="color: blue;"><%for(Object[] apprInfo : ApprovalEmpData){ %>
							  <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
					   				<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %>
					   			<%break;
					   			} %>
						   		<%} %>
						   		</label>
						  </td>	 </tr>	
						  <tr> <td style="border: 0;"></td> </tr>
						  <tr> <td style="border: 0;"></td> </tr>
						 <tr> 	
						   <td style="border: 0;margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						     This is to inform you that I have changed my mobile number with effect from.&nbsp;<input type="text" value=" <%if(MobFormData!=null && MobFormData[4]!=null){ %><%=DateTimeFormatUtil.SqlToRegularDate(MobFormData[4]+"")%>  <%} %>" readonly style="width:12%;text-align:center;color: blue;">&nbsp; and the new mobile number is as under:
						   </td> 
						 </tr> 
						 
						 	<tr> <td style="border: 0;"><input type="text" value="<%if(MobFormData!=null && MobFormData[2]!=null){ %> <%="Mobile Number : "+MobFormData[2]%> <%} %>" readonly style="color: blue;"></td> </tr>									 				
						 	<tr> <td style="border: 0;"><input type="text" value="<%if(MobFormData!=null && MobFormData[3]!=null){ %> <%="Alt Mobile Number : "+MobFormData[3]%> <%} %>" readonly style="color: blue;"></td> </tr>	
						 	<tr> <td style="border: 0;"></td> </tr>
						 	<tr> <td style="border: 0;">The same may be recorded in the office records.</td> </tr>	
						 						       
					    </tbody>
					</table>	
					<div style="width:100%;text-align: right;margin-left:-5%;color: blue;">	<%if(MobFormData!=null && MobFormData[8]!=null){ %><%=MobFormData[8] %> <%} %>	</div>				
					<div style="width:100%;text-align: right;">	Signature of Employee </div>									     
				   <hr style="border:solid 1px;">
				   
				  	<div style="width: 100%;border: 0;text-align: center;"> <b style="font-size:18px;text-decoration:underline">FOR ADMINISTRATION USE</b> </div>
				    <br>
				   <div style="margin-left: 10px;text-align: justify; text-justify: inter-word;font-size: 14px;" align="left">
						Intimation of change of mobile number received on  &nbsp;
						
						<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
				   				<span style="text-decoration: underline;color: blue;"><%=rdf.format(sdtf.parse(apprInfo[4].toString())) %></span>
				   				
				   			<%
				   				break;
				   			} %>
				   		<%} %> &nbsp;. The same has been updated in the personal records.																		
				   </div>
				   
				   <br>
				   <%if(MobFormData!=null && !MobFormData[5].toString().equalsIgnoreCase("N")){ %>
					         <br>
				              <div style="width:100%;text-align: left;margin-left: 10px;">
				               <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			    <%if(apprInfo[8].toString().equalsIgnoreCase("VDG")){ %>
				   				Recommended By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %></label><br>
				   				Recommended On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			    <%} %>
				   		        <%} %> 
				             </div>
				             <br>
				             <div style="width:100%;text-align: left;margin-left: 10px;">
				             <%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			 <%if(apprInfo[8].toString().equalsIgnoreCase("VSO")){ %>
				   				Recommended By : <label style="color: blue;"><%=apprInfo[2]+", "+apprInfo[3] %></label><br>
				   				Recommended On : <label style="color: blue;"><%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %></label>
				   			  <%} %>
				   		     <%} %> 
				            </div>				             				             				          
                       <br> <br>
					       <%} %> 
				   <br>
				   <div style="width:100%;text-align: right;margin-left:-5%;"> </div>	
				   <div style="border:0px;width: 100%; text-align: right;"> Incharge - P&A 
				   <br>	
				   <br> <label style="color: blue;">
				   		<%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("VPA")){ %>
				   				<%=apprInfo[2]+", "+apprInfo[3] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;} %>
				   		<%} %>
				   		</label> 
				   </div>
				   	 <br>
				   	 <div class="row">
						<%if(MobIntimationRemarks.size()>0){ %>
							<div class="col-md-8" align="center" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
								<%if(MobIntimationRemarks.size()>0){ %>
									<table style="margin: 3px;padding: 0px">
										<tr>
											<td style="border:none;padding: 0px">
												<h6 style="text-decoration: underline;">Remarks :</h6> 
											</td>											
										</tr>
										<%for(Object[] obj : MobIntimationRemarks){%>
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
				   <% if(MobFormData!=null && toUserStatus.contains(MobFormData[7].toString())){ %>
				   		<%-- <div align="left">
				   			 <%if(MobFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=MobFormData[6] %> <%} %>
				   		
				   		</div> --%>
					   <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					   
					   <button style="margin-top: -9%" type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="MobileFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						</button>
					   </div>
				   	
				   	
					<%} %>
					
					<% if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){ %>
						<%-- <div align="left">
				   			 <%if(MobFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=MobFormData[6] %> <%} %>
				   		
				   		</div> --%>
						<div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="3" cols="65" name="remarks" id="remarksarea"></textarea>
					   </div>
				   		 <%if(PandAs.contains(empNo)) {%>
					   <button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="MobileFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							Verify	
						</button>
						<%}else{ %>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="MobileFormSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Recommend	
						</button>
						<%} %>		
					
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
</body>
<script>

function validateTextBox() {
    if (document.getElementById("remarksarea").value.trim() != "") {
    	return confirm('Are You Sure To Return?');
    	
    } else {
        alert("Please enter Remarks to Return");
        return false;
    }
}

</script>

</html>