<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
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
			<h5>Intimation Approval List</h5>
		</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					
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

  		<div class="card" >
	        <div class="card-header">
	   
                </div>
            </div>  
            
   	<div class="card-body main-card">
   		
   		  <form action="" method="POST" id="circularForm">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:5%">SN</th>
					  <th style="width:15%">EmpNo</th>
					  <th style="width:40%">Employee Name</th>
                       <th style="width:15%">Type</th>
                       <th style="width:25%">Action</th>
                  	</tr>
				</thead>
                 <tbody>
                       <% int SN=0;
                          for(Object[] form:ApprovalList ){
                       %>
                        <tr>
                             
                            <td style="text-align: center;"><%=++SN%></td>
                            <td style="text-align: center;"><%=form[1]%></td>
                            <td ><%=form[2] %></td>
                            <td ><%=form[3]%></td>
                            <td style="text-align: center;">
                            <input type="hidden" name="intimationId" value="<%=form[4]%>">
					          <%-- <button type="submit" class="btn btn-sm submit-btn" id="intimationId" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" data-toggle="tooltip" data-placement="top" title="Verify">
							     <i class="fa-solid fa-forward" style="color: #125B50"></i>
						       </button>	
						       
						       <button type="submit" class="btn-danger btn-sm" name="Action" value="R" formaction="VpIntimationApprovalSubmit.htm" onclick="return confirm('Are You Sure To Return?)');" formmethod="post" data-toggle="tooltip" data-placement="top" title="Retrun">
									<i class="fa-solid fa-backward" style="color: #333C83"></i>
								</button> --%>	
								
								<button type="submit" class="btn btn-sm submit-btn" formaction="VpIntimationApprovalSubmit.htm" name="Action" value="A" onclick="return confirm('Are You Sure To Verify?');" data-toggle="tooltip" data-placement="top" title="Verify">
							     Verify
						       </button>	
						       
						       <button type="button" class="btn btn-sm delete-btn" name="Action" value="R" formaction="VpIntimationApprovalSubmit.htm" onclick="return validateTextBox(<%=form[4]%>);" formmethod="post" data-toggle="tooltip" data-placement="top" title="Retrun">
								 Return	
								</button>				 	
						 	</td>
                            
                        </tr>
                       <%} %>                          
                 </tbody>   
            </table>
          </div>
        
          <input type="hidden" name="isApproval" value="Y">
         </form>
         <form action="" method="post">   
           <div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">Remarks to return </h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					        <div class="modal-body">
					        	<div class="form-inline">
					        	<div class="form-group w-100">
					               <label>Remarks: &nbsp;&nbsp;&nbsp;</label> 
					              <textarea  class=" form-control w-100" maxlength="1000"  id="remarks"  name="remarks" required="required" ></textarea> 
					      		</div>
					      		</div>
					      </div>
					      
					        <input type="hidden" name="intimationId" id="intimationId">
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="Action" value="R" formaction="VpIntimationApprovalSubmit.htm" formmethod="post" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div> 
				</form>
	</div>
<script>

function validateTextBox(intimationId) {
	
	document.getElementById("intimationId").value=intimationId;
	$('#myModal').modal('show');
	return true; 
   
}

</script>

</body>
</html>