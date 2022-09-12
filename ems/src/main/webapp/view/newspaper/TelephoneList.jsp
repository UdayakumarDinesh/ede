<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<body>
<%
List<Object[]> TeleClaimList=(List<Object[]>)request.getAttribute("TeleClaimList");
List<Object[]> TeleDeviceList=(List<Object[]>)request.getAttribute("TeleDeviceList");
List<Object[]> Sendbackdata=(List<Object[]>)request.getAttribute("Sendbackdata");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Telephone Claims</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="TelephoneDashBoard.htm"> Telephone </a></li>
						<li class="breadcrumb-item active " aria-current="page">Telephone List</li>
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
 
 
<%--      <!-- message -->
        <div class="col-md-4 col-md-offset-4">
 
     <!-- add message -->
     
      
     
     
        <%
	    if(request.getAttribute("AddTelephoneClaimResult")!=null){
	    Integer AddTelephoneClaimResult=(Integer)request.getAttribute("AddTelephoneClaimResult");	
		if(AddTelephoneClaimResult==1){%>
		<div class="alert alert-success"><p class="text-center">Successfully  Added</p></div>
		<%}else{ %>
		<div class="alert alert-danger"><p class="text-center">Some Error Occur While Adding</p></div>
		<%}
	    }%>
    
    
       
       <%
       if(request.getAttribute("PleaseSelectAtLeastOneOption")!=null)
       {%>
       <div class="alert alert-info"><p class="text-center">Please Select At least One Device While Claiming</p></div>
      <%}%> 
    
    
    
    
    
    
    <!-- edit message -->
       
        <%
	    if(request.getAttribute("EditTelephoneResult")!=null){
	    Integer EditTelephoneResult=(Integer)request.getAttribute("EditTelephoneResult");	
		if(EditTelephoneResult==2){%>
		<div class="alert alert-success"><p class="text-center">Successfully  Edited</p></div>
		<%}else{ %>
		<div class="alert alert-danger"><p class="text-center">Some Error Occur While Editing</p></div>
		<%}
	    }%>

   <!-- delete message -->

        <%
	    if(request.getAttribute("DeleteTelephoneResult")!=null){
	    Integer DeleteTelephoneResult=(Integer)request.getAttribute("DeleteTelephoneResult");	
		if(DeleteTelephoneResult==1){%>
		<div class="alert alert-success"><p class="text-center">Successfully  Deleted</p></div>
		<%}else{ %>
		<div class="alert alert-danger"><p class="text-center">Some Error Occur While Deleting</p></div>
		<%}
	    }%>


      <!-- edit restriction -->

       <%
       if(request.getAttribute("EditRestricted")!=null)
       {%>
       <div class="alert alert-danger"><p class="text-center">Claim Already Approved You Cannot Edit</p></div>
      <%}%> 

      <!-- Delete restriction -->

       <%
       if(request.getAttribute("DeleteRestricted")!=null)
       {%>
       <div class="alert alert-danger"><p class="text-center">Claim Already Forwarded/Approved You Cannot Delete</p></div>
      <%}%> 



       <!-- PleaseSelectAtLeastThreeMonthClaim -->
       <%
       if(request.getAttribute("PleaseSelectAtLeastThreeMonthClaim")!=null)
       {%>
       <div class="alert alert-info"><p class="text-center">Please Select At Least Three Month Claim</p></div>
      <%}%> 


<!-- ForwardToAdminResult  -->

      <%
	    if(request.getAttribute("ForwardToAdminResult")!=null){
	    Integer ForwardToAdminResult=(Integer)request.getAttribute("ForwardToAdminResult");	
		if(ForwardToAdminResult==2){%>
		<div class="alert alert-success"><p class="text-center">Successfully  Forwarded</p></div>
		<%}else{ %>
		<div class="alert alert-danger"><p class="text-center">Some Error Occur While Forwarding</p></div>
		<%}
	    }%>

     <!-- SamePeriodRestricted -->
     <%
       if(request.getAttribute("SamePeriodRestricted")!=null){
       String SamePeriodRestricted=(String)request.getAttribute("SamePeriodRestricted");
       %>
       <div class="alert alert-danger"><p class="text-center">You Already Applied For <%=SamePeriodRestricted%></p></div>
      <%}%> 


    <!-- EnterBalanceCorrectly -->
       <%
       if(request.getAttribute("EnterBalanceCorrectly")!=null){
       %>
       <div class="alert alert-danger"><p class="text-center">Please Enter Balance Correctly</p></div>
      <%}%>
       <%
       if(Sendbackdata.size()>0){
    	   for(Object[] sb:Sendbackdata){
       %>
      <div align="center" class="alert alert-danger">
       <h5>Send Back Message From Admin</h5>
       <p><%=sb[3] %></p>
        </div>
       <%break;}}%>

     </div><!-- //message --> --%>
 

  <form action="telephoneedit" method="post" >
                          <table id="addDataTable" class="table table-hover table-striped  table-condensed  table-bordered  " >
                              <thead> 
                                <tr>            
                                   <th>Select</th>
                                   <th>Claim Date</th>
                                   <th>Month</th>
                                   <th>Year</th>
                                   <th>Claim Amount</th>
                                   <th>Admissible Amount</th>
                                   <th>Payable Amount</th>
                                   <th>Status</th>
                                  </tr>
                               </thead>
                               <tbody>
                                <%if(TeleClaimList!=null&&TeleClaimList.size()>0){ 
		                        for(Object ls[]:TeleClaimList){%>
                               <tr>
                                <td><input type="radio" name="TeleId_TeleForwardId" value="<%=ls[0]%>_<%=ls[9]%>" required="required"></td>
                     
                                <td><%out.println(DateTimeFormatUtil.SqlToRegularDate(ls[1].toString()));%></td>
                                <td><%=ls[2]%></td>
                                <td><%=ls[3]%></td>
                                <td><%=ls[4]%></td>
                                <td><%=ls[5]%></td>
                                <td><%=ls[6]%></td>
                                <td><%if((Integer.parseInt(ls[9].toString()))!=0&&(Integer.parseInt(ls[7].toString()))==0&&ls[8]==null){%><span class="label label-warning">Forwarded</span><%}else if((Integer.parseInt(ls[9].toString()))!=0&&(Integer.parseInt(ls[7].toString()))!=0&&ls[8]!=null){%><span class="label label-success">Approved</span><%}else{%><span class="label label-primary">User Applied</span><%}%></td>
                              </tr>
                              <%}} %>
                           </tbody> 
                          </table>
                     
	   <div  style="margin-left:65px; margin-top:0px;"> 
       <%if(TeleClaimList!=null&&TeleClaimList.size()!=0){%>
        <button type="submit" name="EditTeleClaim"  value="EditTeleClaim" class="btn btn-warning" >Edit</button> 
        <button type="submit" name="DeleteTelephone"  value="DeleteTelephone" class="btn btn-danger" formaction="telephonedelete" onclick="return  FunctionToCheckDelete()">Delete</button> 
        <button type="submit" name="TeleClaimPrint" value="TeleClaimPrint" class="btn btn-info" formaction="telephoneprint" formtarget="_blank" >Print</button>
       <%}else{%>
          <button disabled="disabled" class="btn btn-warning">Edit</button> 
          <button disabled="disabled" class="btn btn-danger">Delete</button> 
          <button disabled="disabled" class="btn btn-info">Print</button> 
       <%}%>
       </div> 
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


<!-- add button -->


  
   <div  style="margin-left:10px; margin-top:-34px;">
     <button type="button" class="btn btn-primary"  data-toggle="modal" onclick="check()">Add</button>
   </div>
 



<!-- -add button -->
                     



<!--      Add Telephone model form -->
  
      
     <div class="modal fade" id="add" role="dialog" >
           <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                      <button  type="button" class="close" data-dismiss="modal">&times;</button>
                      <h4 class="modal-litle">List of Devices</h4>
                     
                    </div>
              <form  action="telephoneadd" method="post">       
             <div class="modal-body">
             
                 <%
                 if(TeleDeviceList!=null&&TeleDeviceList.size()>0){
                
                 %>
                     <table class="table table-bordered table-hover table-striped table-condensed">
                        <thead>
                            <tr>
                                <th>Select</th>
                                <th>Device Type</th>
                                <th>Device No.</th>
                            </tr>
                        </thead>
                        <tbody>  
                            <%
                              for(Object[] ls:TeleDeviceList ){ 
                            %>
                            <tr>
                                 <td style="text-align:center;">
                                 <input type="checkbox" name="ChooseDeviceFromlist" value="<%=ls[0]%>" checked="checked">
                                  <input type="hidden" name="devicename<%=ls[0]%>" value="<%=ls[3]%>">
                                  <input type="hidden" name="devicenumber<%=ls[0]%>" value="<%=ls[2]%>">
                                 </td>
                                 <td style="text-align:center;"><%=ls[1]%></td>
                                 <td style="text-align:center;"><%=ls[2]%></td>
                                 
                            </tr>
                           <%} %>
                        </tbody>
                        </table> 
                   <%}else{ %> <!--  //for close -->
                 
                 <div class="alert alert-info"><p class="text-center">No Device Present Please Add Device First</p></div>
                   <%} %>
                  </div>
             
             <div class="modal-footer">
                 <%
                 if(TeleDeviceList!=null&&TeleDeviceList.size()>0){
                 %>
                   <button type="submit" class="btn btn-success btn-sm" name="AddTelephone" value="AddTelephone">Submit</button>
                 <%}else{ %>
                    <button type="submit" class="btn btn-success btn-sm" disabled="disabled">Submit</button>
                 <%} %>
               <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">close</button> 
             </div>
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
           </form>
           
           
           
                    <form action="TelephoneDeviceList.htm" method="post">
                     <button class="btn btnclr pull-left btn-sm" style="margin-top:-45px; margin-left:10px;" name="DeviceDetails" value="DeviceDetails" >Add/Edit Device Details</button>
                     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                     </form>
           
            </div>
        </div>
     </div>

   
  
<!--     Add Telephone model  form -->




<!--Forward To Admin model-->
  
      
     <div class="modal fade" id="ForwardToAdmin" role="dialog" >
           <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                      <button  type="button" class="close" data-dismiss="modal">&times;</button>
                      <h4 class="modal-litle">Forward To Admin</h4>
                     
                    </div>
              <form  action="telephone-claim-forward" method="post">       
             <div class="modal-body">
             
                 <%
                 if(TeleClaimList!=null&&TeleClaimList.size()>0){
                	
                  %>
                      <table id="addDataTable" class="table table-hover table-striped  table-condensed  table-bordered  " >
                              <thead> 
                                <tr>            
                                   <th>Select</th>
                                   <th>Claim Date</th>
                                   <th>Month</th>
                                   <th>Year</th>
                                   <th>Claim Amount</th>
                                   <th>Admissible Amount</th>
                                   <th>Payable Amount</th>
                                   
                                  </tr>
                               </thead>
                               <tbody>
                                <%
		                        for(Object ls[]:TeleClaimList){
		                       if((Integer.parseInt(ls[9].toString()))==0&&(Integer.parseInt(ls[7].toString()))==0&&ls[8]==null){
		                       %>
		                     
                               <tr>
                                <td><input type="checkbox" name="TeleId" value="<%=ls[0]%>"  checked></td>
                     
                                <td><%out.println(DateTimeFormatUtil.SqlToRegularDate(ls[1].toString()));%></td>
                                <td><%=ls[2]%></td>
                                <td><%=ls[3]%></td>
                                <td><%=ls[4]%></td>
                                <td><%=ls[5]%></td>
                                <td><%=ls[6]%></td>
                                
                              </tr>
                              <%}}%>
                           </tbody> 
                          </table>
                   <%}else{ %> <!--  //for close -->
                 
                 <div class="alert alert-info"><p class="text-center">List is Empty, Please Apply Telephone Claim </p></div>
                   <%} %>
                  </div>
             
             <div class="modal-footer">
                 <%
                 if(TeleClaimList!=null&&TeleClaimList.size()>0){
                 %>
                   <button type="submit" class="btn btn-success btn-sm" name="TeleClaimForward" value="TeleClaimForward">Submit</button>
                 <%}else{ %>
                    <button type="submit" class="btn btn-success btn-sm" disabled="disabled">Submit</button>
                 <%} %>
               <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">close</button> 
             </div>
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
           </form>
           
           
           
                     
           
            </div>
        </div>
     </div>

   
<!--      Forward To Admin model-->


<div  class="text-center" style="color:red;text-align: center; font-size:15px;"><marquee><b>*Minimum Three month Claim Is Required To Forward For Approval</b> </marquee></div>
<div  class="text-center" style="color:black;text-align: center; font-size:15px;"><b>*Note :-</b>Please Apply Between 1st To 21st Of The Month!</div>
</div>
</div>

<script src="${pageContext.request.contextPath}/stresource/dist/js/app.min.js"></script>

<script type="text/javascript">

function check(){
	var today = new Date();
    var day = today.getDate();
 
     if(day>=21 && day<=31){
    	
    	alert("You Can't Apply For Telehpone Reimbursement from 21st to 31st of the month!");
    }else{
    	$("#add").modal({show: true});
    } 
}
</script>
	
</body>
</html>
