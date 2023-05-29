
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.vts.ems.pis.model.Employee"%>
    <%@page import="java.util.List,com.vts.ems.master.model.PisAdmins"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>P&A And F&A</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
  <%
  List<Object[]> list = (List<Object[]>)request.getAttribute("emplist");
  PisAdmins PandA =(PisAdmins) request.getAttribute("PandA");
  %>
  
  <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(PandA!=null){ %>
				<h5>P&A And F&A Edit</h5>
				<%}else{ %>
				<h5>P&A And F&A Add</h5>
				<%}%>
			</div>
			
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					    <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> 
						<li class="breadcrumb-item "><a href="PandAFandAAdmin.htm">P&A And F&A</a></li>
						<%if(PandA!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">P&A And F&A Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">P&A And F&A Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
    <div class="page card dashboard-card">
		<div class="card-body">
		   <div class="card" >
				<div class="card-body" align="center" >
					<%if(PandA!=null){ %>
					<form name="myfrm" action="PandAFandAEdit.htm" method="POST" id="addfrm1" autocomplete="off" enctype="multipart/form-data">	
						<%}else{%>
					<form name="myfrm" action="PandAFandAAdd.htm" method="POST" id="addfrm1" autocomplete="off">	
						<%}%>
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">								
										<tr>
											<th><label>P & A <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="panda" id="panda" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option disabled="disabled" selected="selected" hidden="true" value="">--Select--</option>
												<%if(list!=null&&list.size()>0){for(Object[] O:list){ %>
												<option value="<%=O[2]%>" <%if(PandA!=null){if( PandA.getPandAAdmin().equalsIgnoreCase(O[2].toString())  ){%> selected   <%}}%> > <%=O[1]%></option>
												<%}}%>
											    </select></td>
										</tr>	
									    <tr>
											<th><label>F & A <span class="mandatory"	style="color: red;">*</span></label></th>
											<td ><select class="form-control select2"  name="fanda" id="fanda" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
												<option disabled="disabled" selected="selected" hidden="true" value="">--Select--</option>
												<%if(list!=null&&list.size()>0){for(Object[] O:list){ %>
												<option value="<%=O[2]%>" <%if(PandA!=null){if( PandA.getFandAAdmin().equalsIgnoreCase(O[2].toString())  ){%> selected   <%}}%> > <%=O[1]%></option>
												<%}}%>
											    </select></td>
										</tr>
										<tr>
										  <th><label>From & To <span class="mandatory"	style="color: red;">*</span></label></th>
										   <td style="width: 50%;"></td>
										   <td style="width: 50%;"></td>
										</tr>
									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
							<%if(PandA!=null){ %>				
							<input type="hidden" id="adminsId" name="adminsId" value="<%=PandA.getAdminsId()%>">
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return checkEdit('addfrm1');"
									name="action"  value="EDIT">SUBMIT</button>
									<%}else{ %>
									
									<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are you Sure to Submit');"
									name="action"  value="ADD">SUBMIT</button>
									
									<%} %>
							</div>

						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		          <%if(PandA!=null){ %>
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="EDIT" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
			<!----------------------------- container Close ---------------------------->	
						<%}%>		
					<%if(PandA!=null){ %>
					</form>
					<%}else{ %>
					</form>
					<%} %>
				</div>
	        </div>
	 </div>

</div>

<script type="text/javascript">

function checkEdit(frmid){
	if(confirm('Are you Sure To Submit ?') ){
		$('#myModal').modal('show');
		return true;
	}
	else{
		return false;
	}
	
}

</script>

</body>
</html>