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
/* table{
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
	
} */
input{
border-width: 0 0 1px 0;
width:80%;
}
input:focus {
  outline: none;
}
b{
color: blue;
}
</style>
<body>

<%
String LabLogo = (String)request.getAttribute("LabLogo");
String LoginType = (String)session.getAttribute("LoginType");
Object[] VpFormData = (Object[])request.getAttribute("VpFormData");
List<Object[]> ApprovalEmpData = (List<Object[]>)request.getAttribute("ApprovalEmpData");
List<Object[]> VpIntimationRemarks = (List<Object[]>)request.getAttribute("VpIntimationRemarks");
List<Object[]> visitorList = (List<Object[]>)request.getAttribute("VpVisitorsFormData");
SimpleDateFormat rdf= new SimpleDateFormat("dd-MM-yyyy");
SimpleDateFormat sdtf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");
Date date = new Date();
String CEO = (String)request.getAttribute("CEOEmpNo");
Object[] empData=(Object[])request.getAttribute("EmpData");
String isApproval = (String)request.getAttribute("isApproval");

List<String> toUserStatus  = Arrays.asList("INI","RDG","RCE");
List<String> toDGMStatus  = Arrays.asList("FWD","RPA","RPA","RCE");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5 style="width:113%;">Visitor Pass - Form</h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="IntimationList.htm">Visitor Pass</a></li>
				    <li class="breadcrumb-item active">Visitor Pass Form</li>
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
						  <td style="width: 20%; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px; margin-top: 25px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
						  <td style="width: 60%; height: 75px;border: 0;text-align: center;vertical-align: bottom;"><h4>REQUEST FOR VISITORS PASS</h4> </td>
						  <td style="width: 20%; height: 75px;border: 0;vertical-align: bottom;"> <span style="float: right;"> &nbsp;<span class="text-blue"></span></span> </td>
						</tr>
					</table> 										
					<hr style="margin-top: 3%;" >
					<table class="DateTable" >
                     <tr >
                   
                    <td style="border: 0;">Pass No:</td>
                    <td style="border: 0;"><b><%="" %></b></td>

                    <td style="padding-left:11.5rem;border: 0;">Intimation No:</td>
                    <td style="border: 0;"><%if(VpFormData!=null && VpFormData[11]!=null) {%> <b><%=VpFormData[11] %><%} %> </b></td>
                    
                   <td style="padding-left:11.5rem;border: 0;">Date :</td>
                     <td style="border: 0;"><b><b> <%for(Object[] apprInfo : ApprovalEmpData){ %>
							                        <%if(apprInfo[8].toString().equalsIgnoreCase("FWD")){ %>				   				
					   			                	<%=rdf.format(sdtf.parse(apprInfo[4].toString())) %>
					   		                    	<%break;
					   		                     	} %>
						   	                    	<%} %>
						  </b></b></td>
                </tr>
                <tr>
                    <td style="border: 0;">From :</td>
                    <td style="border: 0;"><b><%if(VpFormData!=null &&VpFormData[5]!=null) {%><%=rdf.format(VpFormData[5])%><%} %> </b></td>

                    <td style="padding-left:11.5rem;border: 0;">To :</td>
                    <td style="border: 0;"><b><%if(VpFormData!=null && VpFormData[6]!=null) {%><%=rdf.format(VpFormData[6])%><%} %></b></td>
                    
                     <td style="padding-left:11.5rem;border: 0;">Duration :</td>
                     <td style="border: 0;"><b><%if(VpFormData!=null && VpFormData[12]!=null) {%> <%=VpFormData[12] %><%} %>&nbsp;hours</b></td>
                </tr>
                
     
            </table>
               <hr>
               
               <div align="center">
         <table style="border: 1px solid black;">
                 <tr  >
                  <td style="width: 20rem; text-align: left;border: 0;"> Receiving Officer : </td>
                  <td style="width: 19rem; text-align: left;border: 0;"><b><%if(VpFormData!=null && VpFormData[9]!=null) {%> <%=VpFormData[9] %><%} %>&nbsp;(<%=VpFormData[14]%>)</b></td>                
                  <td style="width: 19rem; text-align: left;border: 0;"> Expected Time : </td>
                  <td style="width: 10rem; text-align: left;border: 0;"><b><%if(VpFormData!=null && VpFormData[7]!=null) {%><%=VpFormData[7] %><%} %></b> </td>
                </tr>
                <tr  >
                     <td style="width:17rem; text-align: left;border: 0;"> Intimated By : </td>
                     <td style="width:10rem; text-align: left;border: 0;"><b><%if(VpFormData!=null && VpFormData[1]!=null) {%><%=VpFormData[1] %><%} %></b> </td>     
                     <td style="width: 19rem; text-align: left;border: 0;"> Foreigner : </td>
                  <td style="width: 10rem; text-align: left;border: 0;"><b>
                  <%if(VpFormData!=null ) {
                	  if(VpFormData[15]!=null && "Y".equalsIgnoreCase(VpFormData[15].toString())){
                 %>
                  Yes<%}else if(VpFormData[15]!=null && "N".equalsIgnoreCase(VpFormData[15].toString())) {%>
                  No<%} else{%>NA <%}} %>
                  </b> 
                  </td>
                                        
                </tr>
                 <tr  >
                  <td style="width:17rem; text-align: left;border: 0;"> Company: </td>
                     <td style="width:29rem; text-align: left;border: 0;"><b><%if(VpFormData!=null && VpFormData[3]!=null) {%> <%=VpFormData[3] %><%} %>
                                                                  <%if(VpFormData!=null && VpFormData[4]!=null) {%> <%="("+VpFormData[4]+")" %><%} %> </b> </td>                  
                </tr>
                 <tr  >
                  <td style="width:17rem; text-align: left;border: 0;"> Purpose of Visit : </td>
                  <td style="width:29rem; text-align: left;border: 0;"><b><%if(VpFormData!=null && VpFormData[13]!=null) {%> <%=VpFormData[13] %><%} %></b> </td>                  
                </tr>
                 <tr  >
                  <td style="width:17rem; text-align: left;border: 0;"> Special Permission: </td>
                  <td style="width:29rem; text-align: left;border: 0;"><b><%if(VpFormData!=null && VpFormData[8]!=null) {%> <%=VpFormData[8] %><%} %></b> </td>                  
                </tr> 
                
                <tr>
                  <td style="width:17rem; text-align: left;border: 0;"> Visitor/s : </td>
                   <td style="width:29rem; text-align: left;border: 0;"></td>                  
                </tr> 
                <%for(Object[] visitor:visitorList ){ %>
						<tr>
						   <td style="border: 0;width:17rem; text-align: left;">Name :</td>
						   <td style="width:29rem; text-align: left;border: 0;"><b><%=visitor[0]%>&nbsp;(<%=visitor[1]%>)</b></td>
						    <td style="width:17rem; text-align: left;border: 0;">Mobile :</td>
						 <td style="width:29rem; text-align: left;border: 0;"><b><%=visitor[2]%></b></td>
						</tr>
		        <%}%>       
           </table>            
        </div>

                      <div class="row" align="right" style="margin-right: 1%;margin-top: 2%;">
                         <div class="col-md-12" style="color: blue;"><%if(VpFormData!=null && VpFormData[1]!=null){ %><%=VpFormData[1] %><%} %></div>
                      </div>
                      <div class="row" align="right" style="margin-right: 1%;">
                         <div class="col-md-12">Signature of Employee</div>
                      </div>
                      <br>
                      <div class="row" style="margin-left: -2.5%;">
					  <br>
						<%if(VpIntimationRemarks.size()>0){ %>
							<div class="col-md-8" align="left" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
								<%if(VpIntimationRemarks.size()>0){ %>
									<table style="margin: 3px;padding: 0px">
										<tr>
											<td style="border:none;padding: 0px">
												<h6 style="text-decoration: underline;">Remarks :</h6> 
											</td>											
										</tr>
										<%for(Object[] obj : VpIntimationRemarks){%>
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

					   <% if(VpFormData!=null && toUserStatus.contains(VpFormData[10].toString())) { %>
				   		<%-- <div align="left">
				   			 <%if(HomFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=HomFormData[6] %> <%} %>
				   		
				   		</div> --%>
					   <div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   	
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Submit?');" >
							<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit for verification	
						</button>
					<%} %>
					
					<%
					if(isApproval!=null && isApproval.equalsIgnoreCase("Y")){
					if(VpFormData!=null && VpFormData[10].toString().equalsIgnoreCase("FWD") && !CEO.equalsIgnoreCase(empData[0].toString())  ){ %>
						<%-- <div align="left">
				   			 <%if(HomFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=HomFormData[6] %> <%} %>
				   		
				   		</div> --%>
				   		
						<div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Verify	
						</button>
					
				   		<button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
					<%} %>
					<% if(VpFormData!=null &&  VpFormData[10].toString().equalsIgnoreCase("VDG") || CEO.equalsIgnoreCase(empData[0].toString())){ %>
						<%-- <div align="left">
				   			 <%if(HomFormData[6]!=null){ %> <span style="color: red">Remarks :</span> <%=HomFormData[6] %> <%} %>
				   		
				   		</div> --%>
				   		
						<div align="left">
						   <b >Remarks :</b><br>
						   <textarea rows="5" cols="85" name="remarks" id="remarksarea"></textarea>
					   </div>
				   		<button type="submit" class="btn btn-sm submit-btn" id="finalSubmission" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" >
							 Approve	
						</button>
					
				   		<button type="submit" class="btn btn-sm btn-danger" id="finalSubmission" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="R" onclick="return validateTextBox();">
							 Return
						</button>
					<%} }%>
					<%if(( CEO.equalsIgnoreCase(empData[0].toString()) || (VpFormData!=null &&  VpFormData[10].toString().equalsIgnoreCase("APR") && !VpFormData[8].toString().contains("Not Applicable"))) ) {%>
                      <div class="row" style="margin-top: 5%;">
                         <div class="col-md-12"><b style="color: black"> SANCTIONED / NOT SANCTIONED <br>CEO</b><br></div>
                      </div>
                      <div class="row">
                         <div class="col-md-12"><%for(Object[] apprInfo : ApprovalEmpData){ %>
				   			<%if(apprInfo[8].toString().equalsIgnoreCase("APR")){ %>
				   				<%=apprInfo[2] %><br>
				   				<%=rdtf.format(sdtf.parse(apprInfo[4].toString())) %>
				   			<% break;} %>
				   		<%} %> </div>
                      </div>	
                      <%} %>						                    
			   </div>	
			    <input type="hidden" name="intimationId" value="<%if(VpFormData!=null && VpFormData[0]!=null){ %><%=VpFormData[0]%><%}%>">   
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

</html>