<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>CHSS Approval Authority</title>
<style type="text/css">

.input-group-text{
font-weight: bold;
}

label{
	font-weight: 600;
	font-size: 16px;
	/* color:#07689f; */
} 

hr{
	margin-top: -2px;
	margin-bottom: 12px;
}
.card b{
	font-size: 20px;
}
</style>
</head>
<body>
<%
Object[] approval = (Object[])request.getAttribute("ApprovalList");
List<Object[]> emplist =(List<Object[]>)request.getAttribute("emplist");

%>



<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Approval Authority</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">CHSS Approval Authority</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	
	<div class="card-body" >		
			<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
		if(ses1!=null){ %>
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
			
		<%}if(ses!=null){ %>
			
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		<%} %>
	</div>
		
			<div class="card" >
			
					<div class="card-body">
        			
        		  <form action="ChssApproval.htm" method="POST" name="myfrm1" id="myfrm1"  onsubmit="return confirm('Are you sure to submit');"> 	 						
               	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                   <div class="row"> 
                		<div class="col-md-1"></div>
                    		<div class="col-md-3">
                        		<div class="form-group">
                            		<label class="control-label">Processing Officer</label><span class="mandatory">*</span>
                              		<select class="form-control select2" id="EmpId1" required="required" name="processing">
    									<option disabled="true"  selected="selected" value="">Select </option>
    									<% if(emplist!=null){ for(Object[] O:emplist){%>
    									<option value="<%=O[0]%>" <%if(approval!=null&&emplist!=null){ if(O[0].equals(approval[1])){ %>  selected <%}}%> ><%=O[1] %> </option>
    									<%} }%>
  									</select>
                        		</div>
                    		</div>
         					<div class="col-md-3">
                        		<div class="form-group">
                            		<label class="control-label">Verification Officer</label><span class="mandatory">*</span>
                              		<select class="form-control select2" id="EmpId2" required="required" name="verification">
    									<option disabled="true"  selected="selected" value="">Select </option>
    										<% if(emplist!=null){ for(Object[] O:emplist){%>
    									<option value="<%=O[0]%>" <%if(approval!=null&&emplist!=null){ if(O[0].equals(approval[2])){ %>  selected<%}}%> ><%=O[1] %> </option>
    									<%} }%>
  									</select>
                        		</div>
                    		</div>
                    		<div class="col-md-3">
                        		<div class="form-group">
                            		<label class="control-label">Approving Officer</label><span class="mandatory">*</span>
                              		<select class="form-control select2" id="EmpId3" required="required" name="approving">
    									<option disabled="true"  selected="selected" value="">Select </option>
    										<% if(emplist!=null){ for(Object[] O:emplist){%>
    									<option value="<%=O[0]%>" <%if(approval!=null&&emplist!=null){ if(O[0].equals(approval[3])){ %>  selected<%}}%> ><%=O[1]%> </option>
    									<%} }%>
  									</select>
                        		</div>
                    		</div>
                    		
                    		<div class="col-md-2">
                        		<div class="form-group">
                        		<input type="hidden" name="AuthId" value="<%if(approval!=null){%><%=approval[0]%><%}%>"> 
                            		<label class="control-label"></label>
                            		<input type="hidden" name="type" value="AD"/>
                                         <button class="btn btn-sm submit-btn" type="submit" name="Action" value="EDIT" style="margin-top: 2.15rem;">Submit</button> 
                        		</div>
                    		</div>        		
                        </div>   
                        </form>      
	 		      <br>
	  </div>
	
		   	 </div>				
	        </div>
	        </div>
	    
</body>
</html>