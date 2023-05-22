<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
#button {
   float: left;
   width: 80%;
   padding: 5px;
   background: #dcdfe3;
   color: black;
   font-size: 17px;
   border:none;
   border-left: none;
   cursor: pointer;
}

</style>
</head>
<body>
<%	
	
	List<Object[]> ApprovalList=(List<Object[]>)request.getAttribute("ApprovalList");
 
%>


<div class="card-header page-top ">
	<div class="row">
		<div class="col-md-5">
			<h5>NOC Approval List</h5>
		</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active" aria-current="page">NOC Approval</li>
						
				</ol>
			</div>
		</div>
</div>	
<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
	<div align="center">
		<div class="alert alert-danger" role="alert">
        	<%=ses1 %>
        </div>
   	</div>
	<%}if(ses!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
        	<%=ses %>
        </div>
    </div>
	<%} %>
<br>
  		
   	<div class="card-body main-card">
   		
   		  <form action="" method="POST" >
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:5%">SN</th>
					   <th style="width:40%">Employee Name</th>
                       <th style="width:15%">Type</th>
                       <th style="width:25%">Action</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SN=0;
                         for(Object[] obj:ApprovalList){ %>
                        	 
                      <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            
                            <td ><%=obj[2]%></td>
                            <td style="text-align: center;"><%=obj[3]%></td>
                            <td style="text-align: center;">
                          <%--    <%if(form[3]!=null && form[3].toString().equalsIgnoreCase("Residential Address") ){%>
                            	<button type="submit" class="btn btn-sm" name="resaddressId" value="<%=form[4] %>" formaction="PersonalIntimation.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 	<%} else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Permanent Address")){ %>
						 		<button type="submit" class="btn btn-sm" name="peraddressId" value="<%=form[4] %>" formaction="PersonalIntimation.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 	<%} else if(form[3]!=null && form[3].toString().equalsIgnoreCase("Mobile Number")){ %>
						 		<button type="submit" class="btn btn-sm" name="mobileNumberId" value="<%=form[4] %>" formaction="MobileNumberPreview.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button> --%>
						 	<% if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("Passport")){ %>
						 		<button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4] %>" formaction="PassportPreview.htm"   formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		 <button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4]%>" formaction="PassportNOCPrint.htm"  formmethod="GET" data-toggle="tooltip" data-placement="top" data-original-title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
								</button> 
						 	<%} else if(obj[3]!=null && obj[3].toString().equalsIgnoreCase("Proceeding Abroad")){ %> 
						 	
						 		<button type="submit" class="btn btn-sm" name="ProcAbrId" value="<%=obj[4] %>" formaction="ProcAbroadPreview.htm"   formmethod="post" data-toggle="tooltip" data-placement="top" title="View Form" >
						 			<i class="fa-solid fa-eye"></i>
						 		</button>
						 		
						 		 <button type="submit" class="btn btn-sm" name="ProcAbrId" value="<%=obj[4]%>" formaction="ProcAbroadPrint.htm"  formmethod="GET" data-toggle="tooltip" data-placement="top" data-original-title="Download">
												<i style="color: #019267" class="fa-solid fa-download"></i>
								</button> 
						 		
						 	<%} %>	
						 
						 	<% if(obj[5].toString().equalsIgnoreCase("APR")){ %>
						 	     <button type="submit" class="btn btn-sm" name="Passportid" value="<%=obj[4]%>" formaction="PassportNOCCertificate.htm"  formmethod="GET" formtarget="blank" data-toggle="tooltip" data-placement="top" data-original-title="Certificate">
												<i style="color: #5C469C" class="fa fa-envelope"></i>
								</button> 
						 	   
						 	
						 	<%} %>
						 	</td>
                            
                        </tr>
                      <%} %>
                          
                 </tbody>
   
            </table>
          </div>
          <input type="hidden" name="isApproval" value="Y">
         </form>
     
     
	</div>
 	

  


</body>
</html>