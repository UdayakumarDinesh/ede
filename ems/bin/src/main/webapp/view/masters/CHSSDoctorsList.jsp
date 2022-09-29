<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Doctors List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
List<Object[]> doctorlist = (List<Object[]>)request.getAttribute("doctorlist");
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Doctors List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Doctors List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		  <div class="page card dashboard-card">
		 <div class="card-body">
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
		 <div class="card">
		 <div class="card-body main-card">
		 		
		 		<form action="DoctorsMasterAdd.htm" method="POST" id="empForm" autocomplete="off"  enctype="multipart/form-data" >
		 
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>Select</th>
										<th>Treatment</th>
										<th>Doctor-Qualification</th>
										<!-- <th>Doctor-Rating</th> -->
										<th>Consultation-1</th>
										<th>Consultation-2</th>
										<!-- <th>Action</th> -->
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
							for(Object[] obj : doctorlist){ 
								
								%>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"> <input type="radio" name="DocRateId" value="<%=obj[0]%>"></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[2]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;text-align: left;"> <%=obj[4]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;text-align: left;"> <%=obj[5]%></td>
											<%-- <td style="padding-top:5px; padding-bottom: 5px;" align="center">
											<input type="hidden" name="Action"	value="EDITDOCRATE" />
											<button type="submit" class="btn btn-sm" name="Rateid" value="<%=obj[0]%>"  data-toggle="tooltip" onclick="return CommentModel('<%=obj[0]%>');" data-placement="top" title="Edit">
												<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
											</button>
											</td> --%>
										</tr>
									<%}%>
								</tbody>
							</table>
			
						</div>	
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			   <div class="row text-center">
						<div class="col-md-12">					
							<button type="submit" class="btn btn-sm add-btn"  name="Action" value="ADD" >ADD</button>
							<button type="submit" class="btn btn-sm edit-btn"  name="Action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>									
					    </div>						 
				</div>

		</form>		
		 </div>
		 </div>
		 </div>
</div>
</body>
<script type="text/javascript">
function CommentModel(docId)
{	
		 $('#myModal').modal('show');
		 $('#DocRateid').val(docId);
         return false; 
}
</script>
</html>