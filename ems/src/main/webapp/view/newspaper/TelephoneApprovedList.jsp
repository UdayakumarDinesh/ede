
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
List<Object[]> TelephoneClaimApprovedList=(List<Object[]>)request.getAttribute("TelephoneClaimApprovedList");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Telephone Claims</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TelephoneDashBoard.htm">Telephone</a></li>
						<li class="breadcrumb-item active " aria-current="page">Telephone Approved List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		<div align="center">
			<%String ses=(String)request.getParameter("result"); 
			String ses1=(String)request.getParameter("resultfail");
			if(ses1!=null){ %>
				<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
					<%=ses1 %>
				</div>
				
			<%}if(ses!=null){ %>
				
				<div class="alert alert-success" role="alert" style="margin-top: 5px;">
					<%=ses %>
				</div>
			<%} %>
		</div>
	
	 	<div class="page card dashboard-card">
			
			<div class="card-body" >
    
    
    
    
 

  <form action="" method="post" >
                          <table id="addDataTable" class="table table-hover table-striped  table-condensed  table-bordered  " >
                              <thead> 
                                <tr>            
                                   <th style="width: 5%;">Select</th>
                                   <th>Approved Date</th>
                                   <th>From Date</th>
                                   <th>To Date</th>
                                   <th>Final Amount</th>
                                   
                                  </tr>
                               </thead>
                               <tbody>
                                <%if(TelephoneClaimApprovedList!=null&&TelephoneClaimApprovedList.size()>0){ 
		                        for(Object ls[]:TelephoneClaimApprovedList){%>
                               <tr>
                                <td style="text-align: center;">
                              		<input type="radio" name="TeleBillId" value="<%=ls[0]%>" required="required">
                                </td>
                     
                                <td><%=DateTimeFormatUtil.SqlToRegularDate(ls[1].toString())%></td>
                                <td><%=DateTimeFormatUtil.SqlToRegularDate(ls[2].toString())%></td>
                                <td><%=DateTimeFormatUtil.SqlToRegularDate(ls[3].toString())%></td>
                                <td><%=ls[4]%></td>
                                
                              </tr>
                              <%}} %>
                           </tbody> 
                          </table>
                     
	   	<div  style="margin-left:240px; margin-top:0px;">
	   
	        <button type="submit" class="btn btn-primary" formaction="TelephoneApproval.htm" name="TelephoneApproval" value="TelephoneApproval" formnovalidate="formnovalidate" >Approval <span class="badge" style="color:white; background-color:#FF4500;"><%=request.getAttribute("TeleCount")%></span></button>
   			<button type="submit" class="btn btn-danger" formaction="TelephoneSendback.htm"  formnovalidate="formnovalidate">SendBack <span class="badge" style="color:white; background-color:blue;"><%=request.getAttribute("TeleCount")%></span></button>
      
	       <%if(TelephoneClaimApprovedList!=null&&TelephoneClaimApprovedList.size()!=0){%>
		        <button type="submit" name="TelephoneApprovalPeriodEdit" class="btn btn-warning" formaction="TelephonePeriodEdit.htm" >Edit Approval Period</button>
		        <button type="submit" class="btn btn-info" formaction="TelephonePrintReport.htm" formtarget="blank">Print Report</button> 
		        <button type="submit" class="btn btn-info" formaction="TelephoneContingentBill.htm" formtarget="blank">Print Contingent bill</button> 
		        <button type="submit"  class="btn btn-info" formaction="TelephoneExpenditureSanction.htm" formtarget="blank">Expenditure Sanction</button>
	       <%}else{%>
		        <button  disabled="disabled" class="btn btn-warning">Edit Approval Period</button> 
		        <button disabled="disabled" class="btn btn-info">Print Report</button> 
		        <button disabled="disabled" class="btn btn-info">Print Contingent bill</button> 
		        <button disabled="disabled" class="btn btn-info">Expenditure Sanction</button>
	       <%}%>
       </div> 
       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      
</form>


</div>
</div>

</body>
</html>
