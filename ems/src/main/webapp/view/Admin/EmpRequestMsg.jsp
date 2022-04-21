<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Request Message</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%
List<Object[]> Reqlist = (List<Object[]>)request.getAttribute("msglist");
%>

<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Employee Request Message</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>					
						<li class="breadcrumb-item active " aria-current="page"> Request Message</li>
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
        			
        		  <form action="EmpRequestMsg.htm" method="GET" name="myfrm1" id="myfrm1"  > 	 						
               	 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                   <div class="row">              		
                    		<div class="col-md-12">
                        		<div class="form-group">                        		
									<h4>Message To Admin:</h4>
									<textarea class="form-control"  name="message" rows="5" cols="60"></textarea>									  
                        		</div>
                    		</div>       					    		
                        </div>  
                         <div class="row">              		
                    		<div class="col-md-12">
                        		<div class="form-group" align="center">                        		
								<button type="submit" class="btn btn-sm submit-btn"  name="Action" value="MESSAGE" onclick="return confirm('Are you sure to submit');">SUBMIT</button>						  
                        		</div>
                    		</div>       					    		
                        </div>                     
                        <div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>Select</th>
										<th>Request Message</th>
										<th> Response Message </th>
									</tr>
								</thead>
								<tbody>	
								<%if(Reqlist!=null){for(Object[] obj:Reqlist){ %>							
										<tr>
											<td style="text-align: center;"><input type="radio" name="RequestId" value="<%=obj[0]%>"> </td>
											<td> <%=obj[1] %></td>
											<td align="center"><%if(obj[2]!=null){%><%=obj[2]%><%}else{%>--<%}%></td>
										
										</tr>	
											<%}} %>							
								</tbody>
							</table>					
						</div>		
						<%if(Reqlist!=null){ %>			
						<div class="row text-center">
						<div class="col-md-12">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
							 <button type="submit" class="btn btn-sm delete-btn" name="Action" value="Delete" Onclick=" Delete()" >DELETE </button>
						 </div></div>
						 <%} %>
						 </form>     
	             </div>
		   	 </div>				
	        </div>
	        </div>

</body>
<script type="text/javascript">
function Delete() { 
	
	var fields = $("input[name='RequestId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One ");
		event.preventDefault();
		return false;
	}
	var cnf = confirm("Are You Sure To Delete!");

	if (cnf) {
		
		document.getElementById("myfrm1").submit();
		return true;

	} else {
		
		event.preventDefault();
		return false;
	}
}

</script>
</html>