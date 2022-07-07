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
		 		
		 		<form action="DoctorsMasterEdit.htm" method="POST" id="empForm" autocomplete="off"  enctype="multipart/form-data" >
		 
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>SN</th>
										<th>Treatment</th>
										<th>Doctor-Qualification</th>
										<!-- <th>Doctor-Rating</th> -->
										<th>Consultation-1</th>
										<th>Consultation-2</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
							for(Object[] obj : doctorlist){ 
								slno++;
								String  Consultation1= "Consultation1"+String.valueOf(obj[0]); 
								String  Consultation2 = "Consultation2"+String.valueOf(obj[0]); 
								%>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=slno%>.</td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[2]%></td>
											<td style="padding-top:5px; padding-bottom: 5px;text-align: right"><input type="text"  class="form-control " name="<%=Consultation1%>"  value="<%=obj[4]%>"> </td>
											<td style="padding-top:5px; padding-bottom: 5px;text-align: right"><input type="text"  class="form-control " name="<%=Consultation2%>"  value="<%=obj[5]%>"> </td>
											<td style="padding-top:5px; padding-bottom: 5px;" align="center">
											<input type="hidden" name="Action"	value="EDITDOCRATE" />
											<button type="submit" class="btn btn-sm" name="Rateid" value="<%=obj[0]%>"  data-toggle="tooltip" onclick="return CommentModel('<%=obj[0]%>');" data-placement="top" title="Edit">
												<i class="fa-solid fa-pen-to-square" style="color: #E45826"></i>
											</button>
											</td>
										</tr>
									<%}%>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>	
					<%if(doctorlist!=null){ %>	
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
					        <input type="hidden" id="DocRateid" name="DocRateid" value=""> 
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<%} %>
					<!----------------------------- container Close ---------------------------->	
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