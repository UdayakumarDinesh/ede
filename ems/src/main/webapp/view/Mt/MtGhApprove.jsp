<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<% List<Object[]> ghapprovelist=(List<Object[]>)request.getAttribute("GhApproveList") ;
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
String empname = (String)request.getAttribute("empname");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>MT Gh Approve <small> <%if(empname!=null){%> <%=empname%> <%}%> </small> </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Gh Approve</li>
					</ol>
				</div>
			</div>
</div>


 <%String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){%>
	<center><div class="alert alert-danger" role="alert"><%=ses1 %> </div></center>
	<%}if(ses!=null){%>
	<center><div class="alert alert-success" role="alert" ><%=ses %></div></center>
     <%}%>

<div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body main-card  " >
				
					<form  action="MtGhApprove.htm" method="POST"  id="empForm" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
								<thead>
									<tr>
										 <th>Name & Desg</th>
										  <th>Date of Travel</th>
										  <th>Source</th>
										  <th>Destination</th>
										  <th>Comment</th>
										  <th> All <input type="checkbox" id="ghapp"  onclick="changemyapp('ghapp')" ><button  class="btn btn-success btn-sm "  type="Submit" name="GH_ApprovalApplied" value="GH_ApprovalApplied" style="float: right;" onclick="return confirm('Are you sure to submit?')" >Submit</button></th>								
									</tr>
								</thead>
									  <tbody>
										  <%int count=1;
										  for(Object[] obj:ghapprovelist){ %>
										  <tr>
											  <td><%=obj[1] %>&nbsp;(<%=obj[2] %>)</td>
											  <td align="center"><%=sdf.format(obj[4]) %>  To  <%=sdf.format(obj[12]) %>  </td>
											  <td><%=obj[7] %></td>
											  <td><%=obj[8] %></td>
											  <td><textarea type="text" name="<%=obj[3]%>"></textarea></td>
										      <td> <!-- CBY:checkbox yes -->
											        <span class="app ghapp">
													<label for="ResponseSaidCBY<%=count%>">Recommend</label> 
													<input type="checkbox" class="ghapp"  id="Approve<%=count%>" name="Approve" value="<%=obj[3]%>-<%=obj[1] %>" onclick="changemyclick('<%=count%>')" >
											        </span>
											 </td>                                         
										  </tr>
										  <%count++;} %>
										  </tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
				
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	       </div>
</body>
<script type="text/javascript">
function changemyapp(a)
{
	$('.'+a+'dis input[type=checkbox]').prop('checked',false); 
	if(document.getElementById(a).checked) {
		$('.'+a+' input[type=checkbox]').prop('checked',true); 
	} else {
		$('.'+a+' input[type=checkbox]').prop('checked',false); 
	} 
	

}
</script>
</html>