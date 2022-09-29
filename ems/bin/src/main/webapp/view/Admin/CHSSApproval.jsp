<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
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




	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5  >CHSS Approval Authority</h5>
			</div>
				<div class="col-md-7 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<!-- <li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li> -->
						
						<li class="breadcrumb-item active " aria-current="page">CHSS Approval Authority</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	 <div class="page card dashboard-card">
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
			
					<div class="card-body main-card">
        			<%if(approval!=null){ %>
        		  <form action="ChssApprovalEdit.htm" method="POST" name="myfrm1" id="myfrm1" enctype="multipart/form-data" >
        		  <%}else{%> 	 						
        		  <form action="ChssApproval.htm" method="POST" name="myfrm1" id="myfrm1"  >
        		  <%}%>
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
                            		<label class="control-label">Authorizing  Officer</label><span class="mandatory">*</span>
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
                        		<%if(approval!=null){%>
                        		<input type="hidden" name="AuthId" value="<%if(approval!=null){%><%=approval[0]%><%}%>"> 
                            		<label class="control-label"></label>
                            		<input type="hidden" name="type" value="AD"/>
                                         <button class="btn btn-sm submit-btn " type="submit" name="Action" value="EDIT" style="margin-top: 2.15rem;" onclick="return CommentsModel();">SUBMIT</button>
                                         <%}else{%> 
                                          <button class="btn btn-sm submit-btn " type="submit" name="Action" value="ADD" style="margin-top: 2.15rem;" onclick="return confirm('Are you sure to submit');">SUBMIT</button>
                                         <%}%>
                        		</div>
                    		</div>        		
                        </div>
                        <%if(approval!=null){ %>
                        <!--------------------------- container ------------------------->
			<div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">The Reason For Edit</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					       <div class="modal-body">
					        <div class="form-inline">
					        	<div class="form-group "  >
					               <label>File : &nbsp;&nbsp;&nbsp;</label> 
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile" > 
					      		</div>
					      		</div>
					        	
					        	<div class="form-inline">
					        	<div class="form-group w-100">
					               <label>Comments : &nbsp;&nbsp;&nbsp;</label> 
					              <textarea  class=" form-control w-100" maxlength="1000" style="text-transform:capitalize;"  id="comments"  name="comments" required="required" ></textarea> 
					      		</div>
					      		</div>
					        </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="Action" value="EDIT" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->   
					<%} %>
					<%if(approval!=null){ %></form>      
                        <%}else{%></form><%}%>
	 		      <br>
	  </div>
	
		   	 </div>				
	        </div>
	        </div>
	    
</body>

<script type="text/javascript">
function CommentsModel()
{
	
	$('#myModal').modal('show');
	return false;
	
}
</script>

</html>